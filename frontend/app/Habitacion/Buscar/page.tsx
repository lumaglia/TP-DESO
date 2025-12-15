'use client';

import React, {useRef, useState} from 'react';
import {FieldValues, useForm} from 'react-hook-form';
import {useRouter} from 'next/navigation';
import Row from "../../Row";
import Campo from "../../Campo";
import {DateValues, fieldTypes, infoDisponibilidad, tiposTablaHabitacion} from "../../../public/constants";
import {AlertaCancelar} from "../../Alertas";
import {TablaHabitacion} from "../TablaHabitacion";


export default function BuscarHabitacion({tipo=tiposTablaHabitacion.CU05, seleccionadas,
                                             setSeleccionadas, onNext}
                                         : {tipo?: tiposTablaHabitacion, seleccionadas?: Map<string, Array<Array<Date>>>,
                                            setSeleccionadas?: Function, onNext?: Function}) {

    const form = useForm<DateValues>();
    const { register, control, handleSubmit, formState, watch, clearErrors, trigger, getValues } = form;
    const { errors } = formState;
    const [ alertaCancelarOpen, setAlertaCancelarOpen] = useState(false);
    const [ solicitudValida, setSolicitudValida] = useState(false);
    const formRef = useRef<FieldValues>(null);
    const router = useRouter();

    const [ fechaInicio, setFechaInicio ] = useState<Date>(new Date());
    const [ fechaFin, setFechaFin ] = useState<Date>(new Date());
    const [disponibilidad, setDisponibilidad] = useState<Array<infoDisponibilidad> | undefined>(undefined);
    const [hayDisponibles, setHayDisponibles] = useState(true);

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
        setFechaInicio(data.fechaInicio);
        setFechaFin(data.fechaFin);
        formRef.current = data
        setSolicitudValida(false);
        setHayDisponibles(true);
        fetch(`http://localhost:8081/Habitacion/Buscar/${data.fechaInicio.toLocaleDateString('en-CA')}/${data.fechaFin.toLocaleDateString('en-CA')}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if(res.ok) {
                res.json().then(response => {
                    response.sort((a:any, b:any) => parseInt(a.habitacion.nroHabitacion) > parseInt(b.habitacion.nroHabitacion) ? 1 : -1);
                    response = response.map((i:any) => {
                        i.reservas = i.reservas.filter((r: any) => r.cancelada == false)
                        return i;
                    })
                    setDisponibilidad(response)
                    setSolicitudValida(true); //Si sale bien cambia el contenido mostrado
                    if(tipo != tiposTablaHabitacion.CU05){
                        let disponibilidad = false;
                        let fechaApertura = new Date(data.fechaInicio.toLocaleDateString('en-CA')).getTime();
                        let fechaCierre = new Date(data.fechaFin.toLocaleDateString('en-CA')).getTime();
                        response.forEach((i:any)=>{
                            if(disponibilidad)return;
                            let inicio = Array();
                            let fin = Array();
                            i.estadias.forEach((r: { fechaInicio: any; fechaFin: any; }) => {
                                inicio.push(new Date(r.fechaInicio).getTime()-8*3600000);
                                fin.push(new Date(r.fechaFin).getTime()+8*3600000);});

                            if(tipo == tiposTablaHabitacion.CU04)i.reservas.forEach((r: { fechaInicio: any; fechaFin: any; }) => {
                                inicio.push(new Date(r.fechaInicio).getTime()-8*3600000);
                                fin.push(new Date(r.fechaFin).getTime()+8*3600000);});

                            if(inicio.length == 0){
                                disponibilidad = true;
                            }
                            inicio.sort();
                            fin.sort();
                            let j = 0;
                            let stack = 0;
                            let fueInicio = false;
                            while(inicio.length > 0 || fin.length > 0) {
                                if(inicio.length == 0) {
                                    j = fin[0];
                                    fin = fin.slice(1);
                                    stack--;
                                    fueInicio = true;
                                }else if(fin.length == 0) {
                                    j = inicio[0];
                                    inicio = inicio.slice(1);
                                    stack++;
                                    fueInicio = false;
                                }else{
                                    if(inicio[0]<fin[0]){
                                        j = inicio[0];
                                        inicio = inicio.slice(1);
                                        stack++;
                                        fueInicio = true;
                                    }else{
                                        j = fin[0];
                                        fin = fin.slice(1);
                                        stack--;
                                        fueInicio = false;
                                    }
                                }
                                if(fechaApertura < j && j < fechaCierre){
                                    if(fueInicio && stack == 1){
                                        disponibilidad = true;
                                        break;
                                    }
                                    if(stack == 0){
                                        disponibilidad = true;
                                        break;
                                    }
                                }
                            }
                        });
                        setHayDisponibles(disponibilidad);
                    }
                })
            }
        })
    };

    return (
        <>
            {
                (solicitudValida && hayDisponibles)? (
                    <>
                    <h2 style={{textAlign: 'center'}}>Disponibilidad de habitaciones entre {fechaInicio.toLocaleDateString('en-GB')} y {fechaFin.toLocaleDateString('en-GB')}</h2>
                    <TablaHabitacion fechaInicio={fechaInicio} fechaFin={fechaFin} infoDisponibilidad={disponibilidad ?? []} tipo={tipo}
                                     {...(seleccionadas != undefined && setSeleccionadas != undefined
                                         &&{seleccionadas: seleccionadas, setSeleccionadas: setSeleccionadas})} />
                        {seleccionadas != undefined && setSeleccionadas != undefined && onNext !== undefined &&
                                <Row>
                                    <button style={{marginLeft: 'auto', marginRight:'100px'}}
                                            className='Button' onClick={() => onNext(disponibilidad)}>Siguiente</button>
                                </Row>}
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
                            {
                                hayDisponibles?
                                    <></>:
                                    <><p style={{textAlign: 'center'}}>No existen habitaciones disponibles con las comodidades deseadas para el rango de fechas solicitado.</p></>

                            }
                        </form>
                        <AlertaCancelar open={alertaCancelarOpen} setOpen={setAlertaCancelarOpen}
                                        text='la busqueda de habitaciones'/></>
                )
            }
        </>
    )
}