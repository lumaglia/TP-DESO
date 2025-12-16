'use client'

import React, { useState } from 'react'
import { ChangeEvent } from 'react'
import { useForm } from 'react-hook-form'
import Encabezado from '../../Encabezado'
import Row from '../../Row'
import Campo from '../../Campo'
import { fieldTypes } from '../../../public/constants'
import { useRouter, useSearchParams } from 'next/navigation'

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
    DatosResponsablePago,
    SeleccionResponsable,
    ConfirmarFactura,
}

export default function CrearFactura() {
    const router = useRouter();
    const form = useForm<FormFactura>()
    const {register, handleSubmit, formState: {errors}} = form;
    const [estadia, setEstadia] = useState<Estadia | null>(null)
    const [items, setItems] = useState<{
        id: number,
        tipo: string,
        descripcion: string
        monto: number
        iva: number
        seleccionado: boolean,
        procesado: boolean
    }[]>([])
    const [huespedes, setHuespedes] = useState<HuespedCheckout[]>([])
    const [estado, setEstado] = useState<EstadosCU07>(EstadosCU07.DatosResponsablePago)
    const [responsableSeleccionado, setResponsableSeleccionado] = useState<HuespedCheckout | null>(null)
    const [errorResponsable, setErrorResponsable] = useState<string | null>(null)

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
        const diaCheckout = new Date().toISOString().split('T')[0];
        fetch('http://localhost:8081/Factura/Checkout', {
            method: 'POST',
            body: JSON.stringify({
                idHabitacion: data.idHabitacion,
                diaCheckout: diaCheckout
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(res => {
                if (res.status === 204) {
                    return []
                }
                return res.json()
            })
            .then((data: Estadia) => {
                setEstadia(data)

                const itemsFactura = [
                    {
                        id: data.id,
                        tipo: 'Estadia',
                        descripcion: 'Costo de la estadia',
                        monto: data.montoEstadia,
                        iva: Math.round(data.montoEstadia * 0.21),
                        seleccionado: true,
                        procesado: false
                    },
                    ...data.consumos.map(c => ({
                        id: c.id,
                        tipo: 'Consumo',
                        descripcion: c.descripcion,
                        monto: c.monto,
                        iva: Math.round(c.monto * 0.21),
                        seleccionado: true,
                        procesado: false
                    }))
                ]
                setItems(itemsFactura)
                setHuespedes(data.huespedes)
                setEstado(EstadosCU07.SeleccionResponsable)
            })
    }

    const submitResponsable = () => {
        if (responsableSeleccionado === null) {
            router.push('Responsable/Alta')
            return
        } else if (responsableSeleccionado.esMenor) {
            setErrorResponsable('No se puede seleccionar a un menor de edad como responsable de pago')
            return;
        } else {
            fetch('http://localhost:8081/Factura/Checkout', {
                method: 'POST',
                body: JSON.stringify({
                    estadia: estadia,
                    responsablePago: responsableSeleccionado,
                    consumos: estadia!.consumos,
                }),
                headers: {'Content-Type': 'application/json'}
            })
                .then(res => res.json())
                .then(() => {
                    setEstado(EstadosCU07.ConfirmarFactura)
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

            fetch("http://localhost:8081/Factura/Emitir", {
                method: 'POST',
                body: JSON.stringify(emitir),
                headers: { 'Content-Type': 'application/json' }
            })
                .then(res => res.json())
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
            <Encabezado titulo={'Crear una factura'}/>
            <h2 style={{marginTop: '30px', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>Ingrese los
                datos para crear una factura</h2>
            {/* puntos 1 al 3 del flujo */}
            {estado === EstadosCU07.DatosResponsablePago && (
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
                        <button type="submit" className="Button">BUSCAR</button>
                    </Row>
                </form>
            )}

            {/* puntos 4 al 5 del flujo */}
            {estado === EstadosCU07.SeleccionResponsable && (
                <div style={{marginTop: '30px', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto'}}>

                    <h3 style={{textAlign: 'center'}}>
                        Huéspedes que realizan checkout
                    </h3>

                    {errorResponsable && (
                        <p className="ErrorText" style={{textAlign: 'center'}}>
                            {errorResponsable}
                        </p>
                    )}

                    <table className="TablaBuscar">
                        <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Apellido</th>
                            <th>CUIT</th>
                        </tr>
                        </thead>
                        <tbody>
                        {huespedes.map((h, idx) => (
                            <tr
                                key={idx}
                                className={responsableSeleccionado?.cuit === h.cuit ? 'selected' : ''}
                                onClick={() => seleccionarResponsable(h)}
                            >
                                <td>{h.nombre}</td>
                                <td>{h.apellido}</td>
                                <td>{h.cuit}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>

                    <div style={{marginTop: '20px', display: 'flex', justifyContent: 'flex-end'}}>
                        <button className="Button" onClick={submitResponsable}>
                            ACEPTAR
                        </button>
                    </div>
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
                                    <td>${i.iva}</td>
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
        </>
    );
}
