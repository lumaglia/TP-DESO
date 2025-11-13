'use client';

import { ReactNode, useState, useRef } from 'react';
import { useForm, FieldValues } from 'react-hook-form';
import Campo from './Campo.tsx'
import { AlertaCancelar, AlertaDocumento } from './Alertas.tsx'
import { validation, comboValues, FormValues, fieldTypes } from '../public/constants.ts'
import './AltaHuesped.css'

function Row({ children }: {children: ReactNode}) {
    return (
        <div className='row'>
            {children}
        </div>
    );
}

function AltaHuesped() {

    const form = useForm<FormValues>();
    const { register, control, handleSubmit, formState, watch, clearErrors, trigger } = form;
    const { errors } = formState;
    const [ alertaCancelarOpen, setAlertaCancelarOpen] = useState(false);
    const [ alertaDocumentoOpen, setAlertaDocumentoOpen] = useState(false);
    const formRef = useRef<FieldValues>(null);

    const posicionIVA = watch('posicionIva');
    if(validation['cuil'].required.value && posicionIVA !== 'Responsable Inscripto'){
        validation['cuil'].required.value = posicionIVA === 'Responsable Inscripto'
        trigger('cuil')
        // clearErrors('CUIL');
    }else validation['cuil'].required.value = posicionIVA === 'Responsable Inscripto'

    const onSubmit = (data: FieldValues) => {
        data.fechaNac = data.fechaNac.toLocaleDateString('en-CA');
        formRef.current = data
        fetch('http://localhost:8081/Alta/Huesped', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.status === 409) {
                setAlertaDocumentoOpen(true);
            }else{
                res.json().then(res => console.log(res))
            }
        })
    };

    return (
        <>
            <h3 style={{textAlign:'center'}}>Dar Alta de Huésped</h3>
            <h2 style={{textAlign:'center'}}>Ingresar los datos del nuevo huésped</h2>
            <form method='post' onSubmit={handleSubmit(onSubmit)} noValidate>
                <div style={{display:'flex', justifyContent:'center', alignItems:'flex-start'}}>
                    <div style={{display: 'inline-flex', flexDirection: 'column', justifyContent: 'flex-start', alignItems: 'start'}}>
                        <Row>
                            <Campo field='Nombre' placeholder='Ej: Juan' isRequired={true} register={register}
                                   errors={errors} validation={validation['nombre']} />
                            <Campo field='Apellido' placeholder='Ej: Martinez' isRequired={true} register={register}
                                   errors={errors} validation={validation['apellido']} />
                        </Row>
                        <Row>
                            <Campo field='Tipo de documento' isRequired={true} type={fieldTypes.COMBOBOX}
                                   register={register} errors={errors} comboValues={comboValues['tipoDocumento']} validation={validation['tipoDocumento']} />
                            <Campo field='Número de documento' placeholder='Ej. 11.222.333' isRequired={true}
                                   register={register} errors={errors} validation={validation['numeroDocumento']} />
                        </Row>
                        <Row>
                            <Campo field='CUIL' placeholder='11 - 11.222.333 - 2' register={register} errors={errors}
                                   validation={validation['cuil']}
                                   isRequired={posicionIVA === 'Responsable Inscripto'} />
                            <Campo field='Posición frente al IVA' placeholder='Consumidor Final' type={fieldTypes.COMBOBOX}
                                   comboValues={comboValues['posicionIva']} register={register} errors={errors} validation={validation['posicionIva']} />
                        </Row>
                        <Row>
                            <Campo field='Fecha de Nacimiento' placeholder='DD / MM / YYYY' isRequired={true}
                                   type={fieldTypes.DATE} validation={validation['fechaNacimiento']} register={register} errors={errors}/>
                            <Campo field='Telefono' placeholder='+54 9 xxxx xxxx' isRequired={true}
                                   validation={validation['numeroTelefono']} register={register} errors={errors}/>
                        </Row>
                        <Row>
                            <Campo field='Email' placeholder='Ej. jaimitohernandez@gmail.com' type={fieldTypes.EMAIL}
                                   validation={validation['email']} register={register} errors={errors}/>
                            <Campo field='Ocupación' placeholder='Trabajo / Estudio' isRequired={true}
                                   validation={validation['ocupacion']} register={register} errors={errors}/>
                        </Row>
                        <Row>
                            <Campo field='Nacionalidad' placeholder={undefined} isRequired={true} comboValues={comboValues['nacionalidad']}
                                   type={fieldTypes.COMBOBOX} validation={validation['nacionalidad']} register={register} errors={errors}/>
                            <Campo field='Nacionalidad' placeholder={undefined} isRequired={true} comboValues={comboValues['nacionalidad']}
                                   type={fieldTypes.COMBOBOX} validation={validation['nacionalidad']}
                                   hidden={true} register={register} errors={errors}/>
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
                            <button type='button' className='Button' onClick={() => setAlertaCancelarOpen(true)}>Cancelar</button>
                            <Campo field='Provincia' placeholder='Ej. Entre Ríos' isRequired={true}
                                   validation={validation['provincia']} register={register} errors={errors}/>
                            <Campo field='Pais' placeholder='Ej. Argentina' isRequired={true}
                                   validation={validation['pais']} register={register} errors={errors}/>
                            <button type='submit' className='Button'>Enviar</button>
                        </Row>
                    </div>
                </div>
            </form>
            <AlertaCancelar open={alertaCancelarOpen} setOpen={setAlertaCancelarOpen} />
            <AlertaDocumento open={alertaDocumentoOpen} setOpen={setAlertaDocumentoOpen} data={formRef.current}/>
            </>
    );
}

export default AltaHuesped