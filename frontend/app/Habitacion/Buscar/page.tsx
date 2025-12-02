'use client';

import React, { useState, useRef } from 'react';
import { useForm, FieldValues } from 'react-hook-form';
import { useRouter } from 'next/navigation';
import Row from "../../Row";
import Campo from "../../Campo";
import {fieldTypes, DateValues, validation} from "../../../public/constants";
import {AlertaCancelar} from "../../Alertas";

export default function BuscarHabitacion() {

    const form = useForm<DateValues>();
    const { register, control, handleSubmit, formState, watch, clearErrors, trigger, getValues } = form;
    const { errors } = formState;
    const [ alertaCancelarOpen, setAlertaCancelarOpen] = useState(false);
    const formRef = useRef<FieldValues>(null);
    const router = useRouter();

    const onSubmit = (data: FieldValues) => {
        data.fechaInicio = data.fechaInicio.toLocaleDateString('en-CA');
        data.fechaFin = data.fechaFin.toLocaleDateString('en-CA');
        formRef.current = data
        console.log(formRef.current);
        /*fetch('http://localhost:8081/Huesped/Alta', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.status === 409) {
                setAlertaDocumentoOpen(true);
            }else if(res.ok) {
                router.push(`/AltaHuesped/success?huesped=${encodeURIComponent(data.nombre+' '+data.apellido)}`)
            }
        })*/
    };

    return (
        <>
            <h2 style={{textAlign:'center'}}>Ingresar el periodo a revisar</h2>
            <form method='post' onSubmit={handleSubmit(onSubmit)} noValidate>
                <div style={{display:'flex', justifyContent:'center', alignItems:'flex-start'}}>
                    <div style={{display: 'inline-flex', flexDirection: 'column', justifyContent: 'flex-start', alignItems: 'start'}}>
                        <Row>
                            <Campo field='Desde Fecha' placeholder='DD / MM / YYYY' isRequired={true}
                                   type={fieldTypes.DATE} validation={validation['fechaInicio']} register={register} errors={errors}/>
                            <Campo field='Hasta Fecha' placeholder='DD / MM / YYYY' isRequired={true}
                                   type={fieldTypes.DATE} validation={validation['fechaFin']} register={register} errors={errors}/>
                        </Row>
                        <Row>
                            <button type='button' className='Button' onClick={() => setAlertaCancelarOpen(true)}>Cancelar</button>

                            <button type='submit' className='Button'>Enviar</button>
                        </Row>
                    </div>
                </div>
            </form>
            <AlertaCancelar open={alertaCancelarOpen} setOpen={setAlertaCancelarOpen} text='la busqueda de habitaciones'/>
        </>
    )
}