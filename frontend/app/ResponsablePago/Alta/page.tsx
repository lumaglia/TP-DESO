'use client'

import React, { useState, useRef } from 'react';
import { useForm } from 'react-hook-form'
import Encabezado from '../../Encabezado'
import Row from '../../Row'
import Campo from '../../Campo'
import { validation} from '../../../public/constants'
import {AlertaCancelar, AlertaDocumentoRP} from "../../Alertas";
import { useRouter, useSearchParams } from 'next/navigation'
import { useFetch } from '@/hooks/useFetch'

type FormResponsablePago = {
    razonSocial: string;
    cuit: string;
    direccion: {
        domicilio: string;
        depto: string;
        codigoPostal: string;
        localidad: string;
        provincia: string;
        pais: string;
    };
    telefono: string
}

export default function AltaResponsablePago({nested=false, setDone} : {nested?: boolean, setDone?: React.Dispatch<React.SetStateAction<boolean>>}) {
    const form = useForm<FormResponsablePago>();
    const { register, control, handleSubmit, formState, watch, clearErrors, trigger } = form;
    const { errors } = formState;
    const [ alertaCancelarOpen, setAlertaCancelarOpen] = useState(false);
    const [ alertaDocumentoOpen, setAlertaDocumentoOpen] = useState(false);
    const formRef = useRef<FormResponsablePago>(null);
    const router = useRouter();
    const searchParams = useSearchParams();
    const returnTo = searchParams.get('returnTo');
    const fetchApi = useFetch();

    const onSubmit = (data: FormResponsablePago) => {
        fetchApi('/ResponsablePago/Alta', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res?.status === 409) {
                setAlertaDocumentoOpen(true);
            }
            else if (res?.ok) {
                if(nested){
                    if(setDone)setDone(true);
                }else{
                    router.push(
                        `/ResponsablePago/Alta/success?responsablePago=${encodeURIComponent(data.razonSocial)}&returnTo=${encodeURIComponent(returnTo ?? '/')}`
                    );
                }

            }
        });
    };


    return (
        <>
            <Encabezado titulo={"Dar de alta Responsable de Pago"} />
            <h2 style={{textAlign:'center'}}>Ingresar los datos del nuevo responsable de pago</h2>

            <form method='post' onSubmit={handleSubmit(onSubmit)} noValidate>
                <div style={{display:'flex', justifyContent:'center', alignItems:'flex-start'}}>
                    <div style={{display: 'inline-flex', flexDirection: 'column', justifyContent: 'flex-start', alignItems: 'start'}}>
                        <Row>
                            <Campo field='Razon Social' placeholder='Razon Social Ejemplo' isRequired={true} register={register}
                                   errors={errors} validation={validation['razonSocial']}/>
                            <Campo field='CUIT' placeholder='11 - 11222333 - 2' register={register} errors={errors}
                                   validation={validation['cuil']}
                                   isRequired/>
                        </Row>
                        <Row>
                            <Campo field='Telefono' placeholder='+54 9 xxxx xxxx' isRequired={true}
                                   validation={validation['numeroTelefono']} register={register} errors={errors}/>
                            <Campo field='Telefono' placeholder='+54 9 xxxx xxxx' isRequired={true} hidden={true}
                                   validation={validation['numeroTelefono']} register={register} errors={errors}/>
                        </Row>
                        <Row>
                            <Campo field='Domicilio' placeholder='Calle y Numero' isRequired={true}
                                   validation={validation['domicilio']} register={register} errors={errors}/>
                            <Campo field='Departamento' placeholder='Depto y/o Nro de piso'
                                   validation={validation['departamento']} register={register} errors={errors}/>
                        </Row>
                        <Row>
                            <Campo field='Código Postal' placeholder='Ej. 3252' isRequired={true}
                                   validation={validation['codigoPostal']} register={register} errors={errors}/>
                            <Campo field='Localidad' placeholder='Ej. Villa Clara' isRequired={true}
                                   validation={validation['localidad']} register={register} errors={errors}/>
                        </Row>
                        <Row>
                            <Campo field='Provincia' placeholder='Ej. Entre Ríos' isRequired={true}
                                   validation={validation['provincia']} register={register} errors={errors}/>
                            <Campo field='Pais' placeholder='Ej. Argentina' isRequired={true}
                                   validation={validation['pais']} register={register} errors={errors}/>
                        </Row>
                        <Row>
                            <button type='button' className='Button' onClick={() => setAlertaCancelarOpen(true)}>Cancelar</button>
                            <button type='submit' className='Button'>Enviar</button>
                        </Row>
                    </div>
                </div>
            </form>

            <AlertaCancelar open={alertaCancelarOpen} setOpen={setAlertaCancelarOpen} text='el alta de Responsable de Pago'/>
            <AlertaDocumentoRP open={alertaDocumentoOpen} setOpen={setAlertaDocumentoOpen} data={formRef.current}/>
        </>
    )
}