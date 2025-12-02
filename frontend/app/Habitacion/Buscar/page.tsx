'use client';

import React, { useState, useRef } from 'react';
import { useForm, FieldValues } from 'react-hook-form';
import { useRouter } from 'next/navigation';
import Row from "../../Row";
import Campo from "../../Campo";
import {fieldTypes, DateValues, tiposTablaHabitacion} from "../../../public/constants";
import {AlertaCancelar} from "../../Alertas";
import {TablaHabitacion} from "../TablaHabitacion";



export default function BuscarHabitacion({tipo} : {tipo: tiposTablaHabitacion}) {

    const form = useForm<DateValues>();
    const { register, control, handleSubmit, formState, watch, clearErrors, trigger, getValues } = form;
    const { errors } = formState;
    const [ alertaCancelarOpen, setAlertaCancelarOpen] = useState(false);
    const [ solicitudValida, setSolicitudValida] = useState(false);
    const formRef = useRef<FieldValues>(null);
    const router = useRouter();

    const [ fechaInicio, setFechaInicio ] = useState("");
    const [ fechaFin, setFechaFin ] = useState("");

    const validation =
        {
            'fechaInicio': {
            valueAsDate: true,
                required: 'La fecha inicial es obligatoria',
                pattern: {
                value: /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/,
                    message: 'La fecha debe seguir el patron DD/MM/YYYY'
            },
            validate: {
                validDate: (value: Date) => {return !isNaN(value.getTime()) || 'Debe ingresarse una fecha valida'},

            },
        },
            'fechaFin': {
            valueAsDate: true,
                required: 'La fecha de fin es obligatoria',
                pattern: {
                value: /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/,
                    message: 'La fecha debe seguir el patron DD/MM/YYYY'
            },
            validate: {
                validDate: (value: Date) => {return !isNaN(value.getTime()) || 'Debe ingresarse una fecha valida'},
                    afterStartDate: (value: Date) => {return value >= getValues("fechaInicio") || 'La fecha fin debe ser posterior a la inicial'},
            },
        },
        };

    const onSubmit = (data: FieldValues) => {
        data.fechaInicio.setTime(data.fechaInicio.getTime()+4*3600000);
        data.fechaFin.setTime(data.fechaFin.getTime()+4*3600000);
        setFechaInicio(data.fechaInicio = data.fechaInicio.toLocaleDateString('en-GB'));
        setFechaFin(data.fechaFin = data.fechaFin.toLocaleDateString('en-GB'));
        formRef.current = data
        console.log(formRef.current);
        // PONER EL FETCH A LA API ACA
        setSolicitudValida(true); //Si sale bien cambia el contenido mostrado

    };

    return (
        <>
            {
                solicitudValida? (
                    <>
                    <h2 style={{textAlign: 'center'}}>Disponibilidad de habitaciones entre {fechaInicio} y {fechaFin}</h2>
                    <TablaHabitacion tipo={tipo}/>
                    </>
                ): (
                    <><h2 style={{textAlign: 'center'}}>Ingresar el periodo a revisar</h2>
                        <form method='post' onSubmit={handleSubmit(onSubmit)} noValidate>
                            <div style={{display: 'flex', justifyContent: 'center', alignItems: 'flex-start'}}>
                                <div style={{
                                    display: 'inline-flex',
                                    flexDirection: 'column',
                                    justifyContent: 'flex-start',
                                    alignItems: 'start'
                                }}>
                                    <Row>
                                        <Campo field='Desde Fecha' placeholder='DD / MM / YYYY' isRequired={true}
                                               type={fieldTypes.DATE} validation={validation['fechaInicio']}
                                               register={register} errors={errors}/>
                                        <Campo field='Hasta Fecha' placeholder='DD / MM / YYYY' isRequired={true}
                                               type={fieldTypes.DATE} validation={validation['fechaFin']}
                                               register={register} errors={errors}/>
                                    </Row>
                                    <Row>
                                        <button type='button' className='Button'
                                                onClick={() => setAlertaCancelarOpen(true)}>Cancelar
                                        </button>

                                        <button type='submit' className='Button'>Enviar</button>
                                    </Row>
                                </div>
                            </div>
                        </form>
                        <AlertaCancelar open={alertaCancelarOpen} setOpen={setAlertaCancelarOpen}
                                        text='la busqueda de habitaciones'/></>
                )
            }
        </>
    )
}