'use client'

import React, { useState } from 'react'
import { ChangeEvent } from 'react'
import { useForm } from 'react-hook-form'
import { ScrollArea } from '@base-ui-components/react/scroll-area';
import Encabezado from '../../Encabezado'
import Row from '../../Row'
import Campo from '../../Campo'
import { AlertaCancelar } from '../../Alertas.tsx'
import {comboValues, fieldTypes, MapNameToApi, validation} from '../../../public/constants'
import { useRouter, useSearchParams } from 'next/navigation'
import { useFetch } from '@/hooks/useFetch'

type FormFactura = {
    idHabitacion : string;
    horaSalida : string;
}

type HuespedCheckout = {
    nombre: string,
    apellido: string,
    cuit: string
    esMenor: boolean
}

type ConsumoFactura = {
    id: number,
    descripcion: string,
    monto: number,
}

type Estadia = {
    id: number
    nroHabitacion: string,
    montoEstadia: number,
    huespedes: HuespedCheckout[],
    consumos: ConsumoFactura[]
}

type FacturaPreviewDTO = {
    nombreResponsablePago : string;
    tipoFactura: string;
    total: number;
}

enum EstadosCU07 {
    DatosHabitacion,
    SeleccionResponsable,
    ConfirmarFactura,
}

export default function CrearFactura() {
    const router = useRouter();
    const form = useForm<FormFactura>()
    const {register, handleSubmit, formState: {errors}} = form;
    const [ alertaCancelarOpen, setAlertaCancelarOpen] = useState(false);
    const [estadia, setEstadia] = useState<Estadia | null>(null)
    const fetchApi = useFetch();
    const [items, setItems] = useState<{
        id: number,
        tipo: string,
        descripcion: string
        monto: number
        seleccionado: boolean,
        procesado: boolean
    }[]>([])
    const [ selectedHuesped, setSelectedHuesped ] = useState<{
        nombre: string;
        apellido: string;
        cuil: string;
        tipoDoc: string;
        nroDoc: string;
        menorEdad: boolean;
    } | null>(null)
    const [huespedes, setHuespedes] = useState<HuespedCheckout[]>([])
    const [estado, setEstado] = useState<EstadosCU07>(EstadosCU07.DatosHabitacion)
    const [responsableSeleccionado, setResponsableSeleccionado] = useState<HuespedCheckout | null>(null)
    const [errorResponsable, setErrorResponsable] = useState<string | null>(null)
    const [errorHabitacion, setErrorHabitacion] = useState(false);

    const [opcion, setOpcion] = useState('Huesped');
    const manejarCambio = (e:any) => {
        setOpcion(e.target.value);
    };

    const validation = {
        idHabitacion: {
            valueAsNumber: true,
            required: 'El id de la habitacion es obligatoria',
            pattern: {
                value: /^[0-9]+$/,
                message: 'Debe ser un numero entre 1 al 48'
            },
        },
        horaSalida: {
            required: 'La hora de salida es obligatoria'
        },
        cuit: {
            required: {
                value: true,
                message: 'Debe ingresar un CUIT',
            },
            minLength: {
                value: 13,
                message: 'El CUIT debe contener 11 numeros',
            },
            maxLength: {
                value: 13,
                message: 'El CUIT debe contener 11 numeros',
            },
            pattern: {
                value: /^[0-9]{2}-[0-9]{8}-[0-9]$/,
                message: 'El CUIT debe seguir el patron 00-00000000-0'
            },
            setValueAs: (value: string) => value.replace(/\s/g, ''),
            onChange: (e: ChangeEvent<HTMLInputElement>) => {
                console.log(e)
                if (e.target.value.length == 2) {
                    e.target.value += ' - '
                } else if (e.target.value.length == 4) {
                    e.target.value = e.target.value.slice(0, 1);
                } else if (e.target.value.length == 13) {
                    e.target.value += ' - '
                } else if (e.target.value.length == 15) {
                    e.target.value = e.target.value.slice(0, 12);
                }
            }

        },
    }

    const seleccionarResponsable = (h: any) => {
        setErrorResponsable(null)

        if (responsableSeleccionado?.cuit == h.cuit) {
            setResponsableSeleccionado(null)
        } else {
            setResponsableSeleccionado(h)
        }
    }

    const submitCheckout = (data: FormFactura) => {
        const diaCheckout = new Date(new Date().getTime()-24*3600000).toISOString().split('T')[0];
        setErrorHabitacion(false);
        fetchApi('/Factura/Checkout', {
            method: 'POST',
            body: JSON.stringify({
                numHabitacion: data.idHabitacion,
                diaCheckout: diaCheckout
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }})
            .then(res => {
                if (res?.ok) {
                    res?.json().then((data: Estadia) => {
                        console.log(data)
                        setEstadia(data)

                        const itemsFactura = [
                            {
                                id: data.id,
                                tipo: 'Estadia',
                                descripcion: 'Costo de la estadia',
                                monto: data.montoEstadia,
                                seleccionado: true,
                                procesado: false
                            },
                            ...data.consumos?.map(c => ({
                                id: c.id,
                                tipo: 'Consumo',
                                descripcion: c.descripcion,
                                monto: c.monto,
                                seleccionado: true,
                                procesado: false
                            })) ?? []
                        ]
                        setItems(itemsFactura)
                        setHuespedes(data.huespedes)
                        setEstado(EstadosCU07.SeleccionResponsable)
                    })
                }else{
                    console.log(res?.status, "NO SE ENCONTRO RESERVA, INDICAR MENSAJE DE ERROR")
                    setErrorHabitacion(true);
                }

            })

    }

    const submitResponsable = () => {
        if (responsableSeleccionado === null) {
            router.push('/ResponsablePago/Alta')
            return
        } else if (responsableSeleccionado.esMenor) {
            setErrorResponsable('No se puede seleccionar a un menor de edad como responsable de pago')
            return;
        } else {
            fetchApi('/Factura/Checkout', {
                method: 'POST',
                body: JSON.stringify({
                    estadia: estadia,
                    responsablePago: responsableSeleccionado,
                    consumos: estadia!.consumos,
                }),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }})
                .then(res => {
                    if (res?.ok) {
                        res?.json().then(() => {
                            setEstado(EstadosCU07.ConfirmarFactura)
                        })
                    }else{
                        console.log(res?.status)
                    }
                })

        }
    }

    const submitFactura = (f: any) => {
        const seleccionados = items.filter(i => i.seleccionado)
        if(seleccionados.length===0){
            alert('Debe seleccionar al menos un item')
            return
        }
        else{
            const emitir = {
                idFactura: null,
                idNota: null,
                idEstadia: estadia!.id,
                idPago: null,
                idResponsable: responsableSeleccionado!.cuit,
            }

            fetchApi('/Factura/Emitir', {
                method: 'POST',
                body: JSON.stringify(emitir),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }})
                .then(res => res?.json())
                .then(() => {
                    if (items.filter(i => !i.seleccionado).length > 0) {
                        alert('Factura emitida. Quedan ítems pendientes.')
                        setEstado(EstadosCU07.SeleccionResponsable)
                    } else {
                        alert('Factura emitida correctamente')
                        router.push('/')
                    }
                })
        }
    }

    return (
        <>
            <Encabezado titulo={'Facturar'}/>
            {/* puntos 1 al 3 del flujo */}
            {estado === EstadosCU07.DatosHabitacion && (
                <>
                    <h2 style={{marginTop: '30px', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>Ingrese datos de la habitación</h2>
                    <form onSubmit={handleSubmit(submitCheckout)} noValidate>
                        <Row>
                            <Campo
                                field={"Numero de habitación"}
                                register={register}
                                errors={errors}
                                validation={validation.idHabitacion}
                                placeholder={"Numero de habitación"}
                                isRequired={true}
                                type={fieldTypes.TEXTBOX}
                            />
                            <Campo
                                field={"Hora de salida"}
                                register={register}
                                errors={errors}
                                validation={validation.horaSalida}
                                placeholder={"HH:MM"}
                                isRequired={true}
                                type={fieldTypes.TIME}
                            />
                        </Row>
                        <Row>
                            <button type='button' className='Button' onClick={() => setAlertaCancelarOpen(true)}>Cancelar</button>
                            <button type="submit" className="Button">Confirmar</button>
                        </Row>
                    </form>
                    { errorHabitacion?<Row><p>No se ha encontrado una estadía para la habitación ingresada que haga checkout hoy.</p></Row>:<></> }
                </>
            )}

            {/* puntos 4 al 5 del flujo */}
            {estado === EstadosCU07.SeleccionResponsable && (
                <div style={{marginTop: '30px', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>

                    <h3 style={{marginTop: '30px', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>
                        Seleccionar un responsable de Pago
                    </h3>
                    <p style={{textAlign: 'center'}}>Puede seleccionar un huesped o un tercero.</p>

                    <Row>
                        <select required defaultValue={'Huesped'} onChange={manejarCambio}>
                        {comboValues['Responsable Pago'].map((option) => (<option key={option} value={option}>{option}</option>))}
                        </select>
                    </Row>

                    {
                        (opcion=='Huesped') ?
                            <>
                                <ScrollArea.Root className='ScrollArea'>
                                    <ScrollArea.Viewport className='Viewport'>
                                        <ScrollArea.Content className='Content'>
                                            <table className='TablaBuscar'>
                                                <thead>
                                                <tr>
                                                    <th>Nombre</th>
                                                    <th>Apellido</th>
                                                    <th>Tipo de documento</th>
                                                    <th>Numero de documento</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                {huespedes.map((huesped: any) => (
                                                    <tr className={selectedHuesped === huesped ? 'selected' : ''}
                                                        onClick={() => setSelectedHuesped(huesped)} key={huesped.nroDoc}>
                                                        <td>{huesped.nombre}</td>
                                                        <td>{huesped.apellido}</td>
                                                        <td>{huesped.tipoDoc}</td>
                                                        <td>{huesped.nroDoc}</td>
                                                    </tr>
                                                ))}
                                                </tbody>
                                            </table>

                                        </ScrollArea.Content>
                                    </ScrollArea.Viewport>
                                    <ScrollArea.Scrollbar className='Scrollbar' orientation='vertical'>
                                        <ScrollArea.Thumb className='Thumb'/>
                                    </ScrollArea.Scrollbar>
                                </ScrollArea.Root>
                            <div style={{marginTop: '20px', display: 'flex', justifyContent: 'flex-end'}}>
                                <button className="Button" onClick={submitResponsable}>
                                    ACEPTAR
                                </button>
                            </div>
                            </>:
                            <><p>hola</p></>
                    }
                </div>
            )}


            {/* puntos 6 al 7 del flujo
            + pop up para el punto 9
            (el 8 es solo definicio de que hace el back) */}
            {estado === EstadosCU07.ConfirmarFactura && (
                <div>
                    <div>
                        <h3>Responsable de pago: {responsableSeleccionado?.nombre} {responsableSeleccionado?.apellido}</h3>

                        <table>
                            <thead>
                            <tr>
                                <th></th>
                                <th>Descripción</th>
                                <th>Monto</th>
                                <th>IVA</th>
                            </tr>
                            </thead>
                            <tbody>
                            {items.map(i => (
                                <tr key={i.id}>
                                    <td>
                                        <input
                                            type="checkbox"
                                            checked={i.seleccionado}
                                            onChange={() =>
                                                setItems(prev =>
                                                    prev.map(it =>
                                                        it.id === i.id && !it.procesado
                                                            ? { ...it, seleccionado: !it.seleccionado }
                                                            : it
                                                    )
                                                )
                                            }
                                        />
                                    </td>
                                    <td>{i.descripcion}</td>
                                    <td>${i.monto}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                    <div>
                        <Row>
                            <button className={"Button"}
                                    onClick={submitFactura}>
                                ACEPTAR
                            </button>
                        </Row>
                    </div>
                </div>
            )}
            <AlertaCancelar open={alertaCancelarOpen} setOpen={setAlertaCancelarOpen} text='la facturación de estadía'/>
        </>
    );
}
