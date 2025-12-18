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
import AltaResponsablePago from '../../ResponsablePago/Alta/page'

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
    tipo: string,
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

type ItemValues = {
    uiKey: string
    id?: number,
    tipo: 'Estadia' | string,
    descripcion: string,
    monto: number
}

enum EstadosCU07 {
    DatosHabitacion,
    SeleccionResponsable,
    ConfirmarFactura,
    FacturaEmitida,
    EstadiaFacturada
}

export default function CrearFactura() {
    const router = useRouter();
    const form = useForm<FormFactura>()
    const {register, handleSubmit, formState: {errors}} = form;
    const [ alertaCancelarOpen, setAlertaCancelarOpen] = useState(false);
    const [estadia, setEstadia] = useState<Estadia | null>(null)
    const fetchApi = useFetch();
    const [items, setItems] = useState<{
        uiKey: string,
        id?: number,
        tipo: string,
        descripcion: string
        monto: number
    }[]>([])
    const [ selectedHuesped, setSelectedHuesped ] = useState<{
        nombre: string;
        apellido: string;
        cuil: string;
        tipoDoc: string;
        nroDoc: string;
        menorEdad: boolean;
    } | null>(null)
    const [selectedItems, setSelectedItems ] = useState<Array<ItemValues>>([])
    const [huespedes, setHuespedes] = useState<HuespedCheckout[]>([])
    const [estado, setEstado] = useState<EstadosCU07>(EstadosCU07.DatosHabitacion)
    const [responsableSeleccionado, setResponsableSeleccionado] = useState<HuespedCheckout | null>(null)
    const [errorResponsable, setErrorResponsable] = useState<string | null>(null)
    const [errorHabitacion, setErrorHabitacion] = useState(false);
    const [responsablePago, setResponsablePago] = useState("");
    const [isResponsableHuesped, setIsResponsableHuesped] = useState(false);
    const [cuit, setCuit] = useState("");
    const [mostrarCU12, setMostrarCU12] = useState(false);
    const [done, setDone] = useState(false);
    const [razon, setRazon] = useState("");
    const [nroHab, setNroHab] = useState("");
    const [fechaSalida, setFechaSalida] = useState("");
    const [tipoDoc, setTipoDoc] = useState("");
    const [nroDoc, setNroDoc] = useState("");
    const [popUpEmitir, setPopUpEmitir] = useState(false)

    const [opcion, setOpcion] = useState('Huesped');
    const manejarCambio = (e:any) => {
        setRazon("")
        setSelectedHuesped(null)
        setCuit("")
        setDone(false)
        setMostrarCU12(false)
        setOpcion(e.target.value);
    };

    const formateador = new Intl.NumberFormat('es-AR', {
        style: 'currency',
        currency: 'ARS',
        minimumFractionDigits: 2
    });

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



    const submitCheckout = (data: FormFactura) => {
        const [horas, minutos] = data.horaSalida.split(':');
        const dia = new Date(new Date().getTime()-24*3600000);
        dia.setHours(+horas, +minutos, 0, 0)
        const diaCheckout = new Date(dia.getTime()-1*3600000).toISOString();
        console.log(diaCheckout);
        setNroHab(data.idHabitacion);
        setFechaSalida(diaCheckout)
        setErrorHabitacion(false);
        requestHabitacion(data.idHabitacion,diaCheckout);
    }

    const requestHabitacion = (numHabitacion:string, diaCheckOut:string) => {
        fetchApi('/Factura/Checkout', {
            method: 'POST',
            body: JSON.stringify({
                numHabitacion: numHabitacion,
                diaCheckOut: diaCheckOut
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
                            ...(data.montoEstadia > 0 ? [{
                                uiKey: `estadia-${data.id}`,
                                tipo: 'Estadia',
                                descripcion: 'Costo de la estadia',
                                monto: data.montoEstadia,
                            }] : []),

                            ...data.consumos?.map(c => ({
                                uiKey: `consumo-${c.id}`,
                                id: c.id,
                                tipo: c.tipo,
                                descripcion: c.descripcion,
                                monto: c.monto,
                            })) ?? []
                        ];
                        console.log(itemsFactura.map(i => i.uiKey));
                        if (itemsFactura.length === 0) {
                            console.log("CONSUMOS VACIOS LISTO ESTADIA")
                            setEstado(EstadosCU07.EstadiaFacturada);
                        }else{
                            setItems(itemsFactura)
                            setHuespedes(data.huespedes)
                            setSelectedHuesped(null)
                            setEstado(EstadosCU07.SeleccionResponsable)
                        }
                    })
                }else{
                    console.log(res?.status, "NO SE ENCONTRO RESERVA, INDICAR MENSAJE DE ERROR")
                    setErrorHabitacion(true);
                }

            })
    }

    const submitResponsable = (data:any) => {
        fetchApi(`/Factura/BuscarResponsablePago/${data.cuit}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }})
            .then(res => {
                if (res?.ok) {
                    res?.json().then((resdata) => {
                        console.log(resdata)
                        setCuit(data.cuit)
                        setRazon(resdata.message)
                    })
                }else{
                    console.log(res?.status)
                    setCuit("")
                    setRazon("")
                    setDone(false)
                    setMostrarCU12(true)
                }
            })


    }
    const submitHuesped = () => {
        setResponsablePago(selectedHuesped?.apellido + " " + selectedHuesped?.nombre)
        setIsResponsableHuesped(true)
        setEstado(EstadosCU07.ConfirmarFactura)
        if(selectedHuesped){
            setTipoDoc(selectedHuesped?.tipoDoc)
            setNroDoc(selectedHuesped?.nroDoc)
        }
        if(selectedHuesped?.cuil === null || selectedHuesped?.cuil === undefined) {
            setCuit("")
        }else{
            setCuit(selectedHuesped?.cuil)
        }
    }
    const submitJuridica = () => {
        setResponsablePago(razon)
        setIsResponsableHuesped(false)
        setEstado(EstadosCU07.ConfirmarFactura)
    }

    const submitFactura = (f: any) => {

        const emitir = {
            pagaEstadia: (selectedItems.findIndex(h => h.tipo=='Estadia')!=-1),
            consumos: (selectedItems.filter(h => h.tipo!='Estadia').map(h => h.id)),
            numHabitacion: nroHab,
            diaCheckOut: fechaSalida,
            esHuesped: isResponsableHuesped,
            cuit: cuit,
            tipoDoc: tipoDoc,
            nroDoc: nroDoc,
        }
        console.log(emitir)
        fetchApi('/Factura/Emitir', {
            method: 'POST',
            body: JSON.stringify(emitir),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }})
            .then(res => {
                if (res?.ok){
                    alert('Factura emitida correctamente.')
                    setSelectedItems([])
                    setItems([])
                    setEstado(EstadosCU07.ConfirmarFactura)
                    requestHabitacion(nroHab,fechaSalida)
                }
            })

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
                                                        onClick={() => setSelectedHuesped(huesped)} key={`${huesped.tipoDoc}-${huesped.nroDoc}`}>
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
                                {
                                    selectedHuesped?.menorEdad == true? <p style={{textAlign: 'center'}}>La persona seleccionada es menor de edad, por favor elija otra.</p>
                                        :
                                        selectedHuesped===null? <></> : <Row><button className="Button" onClick={submitHuesped}>Confirmar</button></Row>
                                }


                            </>: (mostrarCU12 && !done)? <>
                                    <p style={{textAlign: 'center'}}>El responsable de pago no ha sido encontrado, puede cargar uno nuevo a continuación.</p>
                                    <AltaResponsablePago nested={true} setDone={setDone} setCuit={setCuit}/></>:
                            <>
                                <form onSubmit={handleSubmit(submitResponsable)} noValidate>
                                    <Row><Campo field='CUIT' placeholder='11 - 11222333 - 2' register={register} errors={errors}
                                                  validation={validation['cuit']}
                                                  isRequired/></Row>
                                <Row><button type="submit" className="Button">
                                    Confirmar
                                </button></Row></form>
                                {
                                    (razon != "")? <>
                                        <h3 style={{marginTop: '30px', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>Razon Social: {razon}</h3>
                                        <Row><button type='button' className='Button' onClick={() => setRazon("")}>Cancelar</button>
                                            <button type="button" className="Button" onClick={() => submitJuridica()}>Aceptar</button></Row>
                                    </> : <></>
                                }
                                <div style={{marginTop: '20px', display: 'flex', justifyContent: 'flex-end'}}>

                                </div>
                            </>
                    }
                </div>
            )}


            {/* puntos 6 al 7 del flujo
            + pop up para el punto 9
            (el 8 es solo definicio de que hace el back) */}
            {estado === EstadosCU07.ConfirmarFactura && (
                <div>
                    <div style={{marginTop: '30px', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>
                        <h3 style={{marginTop: '30px', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>Responsable de pago: {responsablePago}</h3>
                        <>
                        <ScrollArea.Root className='ScrollArea'>
                            <ScrollArea.Viewport className='Viewport'>
                                <ScrollArea.Content className='Content'>
                                    <table>
                                        <thead style={{position: 'sticky', top: '0'}}>
                                        <tr>
                                            <th>Nombre</th>
                                            <th>Descripcion</th>
                                            <th>Monto</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        {items.map(item => {
                                            const selected = selectedItems.some(
                                                s => s.uiKey === item.uiKey
                                            );

                                            return (
                                                <tr
                                                    key={item.uiKey}
                                                    className={selected ? 'selected' : ''}
                                                    onClick={() => {
                                                        setSelectedItems(prev => {
                                                            const exists = prev.some(s => s.uiKey === item.uiKey);
                                                            return exists
                                                                ? prev.filter(s => s.uiKey !== item.uiKey)
                                                                : [...prev, item];
                                                        });
                                                    }}
                                                >
                                                    <td>{item.tipo}</td>
                                                    <td>{item.descripcion}</td>
                                                    <td>{formateador.format(item.monto)}</td>
                                                </tr>
                                            );
                                        })}
                                        </tbody>
                                    </table>

                                </ScrollArea.Content>
                            </ScrollArea.Viewport>
                            <ScrollArea.Scrollbar className='Scrollbar'>
                                <ScrollArea.Thumb className='Thumb'/>
                            </ScrollArea.Scrollbar>
                        </ScrollArea.Root>
                        </>
                    </div>
                    <div>
                        { selectedItems.length > 0 ? <>
                            <h4 style={{textAlign: 'center'}}>Subtotal: {formateador.format(selectedItems.reduce((acc, item) => acc + item.monto, 0))}</h4>
                            <h4 style={{textAlign: 'center'}}>IVA: {formateador.format(selectedItems.reduce((acc, item) => acc + item.monto, 0)*.21)} (21%)</h4>
                            <h3 style={{textAlign: 'center'}}>Tipo de Factura: {cuit == "" ? "B" : "A"}</h3>
                            <h2 style={{textAlign: 'center'}}>Total: {formateador.format(selectedItems.reduce((acc, item) => acc + item.monto, 0)*1.21)}</h2>
                            <Row>
                            <button className={"Button"}
                                    onClick={submitFactura}>
                                Aceptar
                            </button>
                        </Row></>: <></> }
                    </div>
                </div>
            )}
            {estado === EstadosCU07.FacturaEmitida && (
                <>
                    <h3 style={{marginTop: '30px', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>La factura ha sido emitida correctamente. Espere un momento...</h3>
                </>
            )}
            {estado === EstadosCU07.EstadiaFacturada && (
                <>
                    <h3 style={{marginTop: '30px', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>La estadia ha sido facturada completamente.</h3>
                    <Row>
                        <button type='button' className='Button' onClick={() => router.push("/")}>Volver al menu</button>
                    </Row>
                </>
            )}
            <AlertaCancelar open={alertaCancelarOpen} setOpen={setAlertaCancelarOpen} text='la facturación de estadía'/>

        </>
    );
}
