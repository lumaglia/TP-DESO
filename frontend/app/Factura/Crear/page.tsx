'use client'

import React, { useState } from 'react'
import { useForm } from 'react-hook-form'
import Encabezado from '../../Encabezado'
import Row from '../../Row'
import Campo from '../../Campo'
import { fieldTypes, FormFactura, HuespedCheckout } from '../../../public/constants'

export default function CrearFactura(){
    const form = useForm<FormFactura>()
    const {register, handleSubmit, formState : {errors}} = form;
    const [mostrarGrilla, setMostrarGrilla] = useState(false)
    const [huespedes, setHuespedes] = useState<HuespedCheckout[]>([])
    const [cuitIngresado, setCuitIngresado] = useState('')

    const validation ={
        idHabitacion : {
            valueAsNumber: true,
            required : 'El id de la habitacion es obligatoria',
            pattern: {
                value: /^[0-9]+$/,
                message: 'Debe ser un numero entre 1 al 48'
            },
        },
        horaSalida : {
            required: 'La hora de salida es obligatoria'
        }
    }

    const submitHabitacionHoraSalida = (data : FormFactura) => {
        const diaCheckout = new Date().toISOString().split('T')[0];
        fetch('http://localhost:8081/Factura/Crear', {
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
            .then((data: HuespedCheckout[]) => {
                setHuespedes(data)
                setMostrarGrilla(true)
            })
    }


        const submitCUIT = () => {
        console.log('CUIT confirmado:', cuitIngresado)

        fetch('http://localhost:8081/Factura/Confirmar', {
            method: 'POST',
            body: JSON.stringify({
                cuit: cuitIngresado
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
    }

    return(
        <>
            <Encabezado titulo = 'Crear una factura'/>

            <form onSubmit={handleSubmit(submitHabitacionHoraSalida)} noValidate>
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
            {mostrarGrilla && (
                <div style={{ marginTop: '30px', width: 'fit-content', marginLeft: 'auto', marginRight: 'auto' }}>

                    <h3 style={{ textAlign: 'center' }}>
                        Huéspedes que realizan checkout
                    </h3>

                    <table className="TablaBuscar">
                        <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Apellido</th>
                            <th>CUIT</th>
                        </tr>
                        </thead>
                        <tbody>
                        {huespedes.length > 0 ? (
                            huespedes.map((h, idx) => (
                                <tr key={idx}>
                                    <td>{h.nombre}</td>
                                    <td>{h.apellido}</td>
                                    <td>{h.cuit}</td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan={3} style={{ textAlign: 'center' }}>
                                    No hay huéspedes para facturar en esta habitación
                                </td>
                            </tr>
                        )}
                        </tbody>

                    </table>

                    <div style={{ marginTop: '20px', display: 'flex', gap: '10px', alignItems: 'center' }}>
                        <label>Ingresar CUIT:</label>

                        <input
                            type="text"
                            value={cuitIngresado}
                            placeholder="00-00000000-0"
                            onChange={e => setCuitIngresado(e.target.value)}
                        />

                        <button className="Button" onClick={submitCUIT}>
                            ACEPTAR
                        </button>
                    </div>
                </div>
            )}
        </>
    )
};
