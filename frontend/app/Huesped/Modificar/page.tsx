'use client';

import React, { useState, useEffect } from 'react';
import { useForm, FieldValues } from 'react-hook-form';
import { useRouter, useSearchParams } from 'next/navigation';
import Campo from '../../Campo.tsx'
import Encabezado from '../../Encabezado.tsx'
import { AlertaCancelar } from '../../Alertas.tsx'
import Row from '../../Row.tsx'
import { validation, comboValues, FormValues, fieldTypes } from '../../../public/constants.ts'
import '../Alta/AltaHuesped.css'

export default function ModificarHuesped() {

    const router = useRouter();
    const searchParams = useSearchParams();

    const tipoDocParam = searchParams.get('tipo');
    const nroDocParam = searchParams.get('nro');

    const form = useForm<FormValues>();
    const { register, handleSubmit, formState, watch, trigger, reset } = form;
    const { errors } = formState;
    const [ alertaCancelarOpen, setAlertaCancelarOpen] = useState(false);
    const [ alertaDocumentoOpen, setAlertaDocumentoOpen] = useState(false);

    const posicionIVA = watch('posicionIva');
    if(validation['cuil'].required.value && posicionIVA !== 'Responsable Inscripto'){
        validation['cuil'].required.value = posicionIVA === 'Responsable Inscripto'
        trigger('cuil')
    }else validation['cuil'].required.value = posicionIVA === 'Responsable Inscripto'

    useEffect(() => {
        if (tipoDocParam && nroDocParam) {
            const filtro = {
                tipoDoc: tipoDocParam,
                nroDoc: nroDocParam
            };

            fetch('http://localhost:8081/Huesped/Obtener', {
                method: 'POST',
                body: JSON.stringify(filtro),
                headers: { 'Content-Type': 'application/json' }
            })
                .then(res => {
                    if (res.status === 204) throw new Error("No encontrado");
                    return res.json();
                })
                .then(huespedBackend => {
                    if (huespedBackend) {
                        console.log("Datos COMPLETOS recibidos:", huespedBackend);

                        const datosParaForm = {
                            ...huespedBackend,
                            cuil: huespedBackend.cuil || '',
                            telefono: huespedBackend.telefono || '',
                            email: huespedBackend.email || '',
                            ocupacion: huespedBackend.ocupacion || '',
                            fechaNac: huespedBackend.fechaNac,
                            domicilio: huespedBackend.direccion?.domicilio || '',
                            departamento: huespedBackend.direccion?.depto || '',
                            codigoPostal: huespedBackend.direccion?.codigoPostal || '',
                            localidad: huespedBackend.direccion?.localidad || '',
                            provincia: huespedBackend.direccion?.provincia || '',
                            pais: huespedBackend.direccion?.pais || ''
                        };

                        reset(datosParaForm);
                    }
                })
                .catch(err => console.error("Error cargando huesped", err));
        }
    }, [tipoDocParam, nroDocParam, reset]);


    const onSubmit = (data: FieldValues) => {
        console.log(data);
        if (data.fechaNac) data.fechaNac = new Date(data.fechaNac).toISOString().split('T')[0];
        for (let key in data) {
            if (data[key] === '') {
                data[key] = null;
            }
        }

        fetch('http://localhost:8081/Huesped/Alta?modify=true', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: { 'Content-Type': 'application/json' }
        }).then(async res => {
            if (res.status === 409) {
                setAlertaDocumentoOpen(true);
            } else if(res.ok) {
                const nombreCompleto = `${data.nombre} ${data.apellido}`;
                router.push(`/Huesped/Modificar/success?huesped=${encodeURIComponent(nombreCompleto)}`)
            } else {
                alert("Error al modificar el huésped");
            }
        })
    };


    const handleDelete = () => {
        if(!confirm("¿Está seguro de eliminar este huésped?")) return;

        const datosBorrar = {
            tipoDoc: tipoDocParam,
            nroDoc: nroDocParam
        };

        fetch('http://localhost:8081/Huesped/Baja', {
            method: 'DELETE',
            body: JSON.stringify(datosBorrar),
            headers: { 'Content-Type': 'application/json' }
        }).then(async res => {
            if (res.ok) {
                alert("Huésped eliminado con éxito");
                router.push('/');
            } else {
                const errorTexto = await res.text();
                alert("No se puede eliminar: " + errorTexto);
            }
        });
    }

    return(
        <>
            <Encabezado titulo='Modificar Huésped' />
            <h2 style={{textAlign:'center'}}>Datos del huésped</h2>
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
                            <Campo field='Número de documento' placeholder='Ej. 11222333' isRequired={true}
                                   register={register} errors={errors} validation={validation['numeroDocumento']} />
                        </Row>
                        <Row>
                            <Campo field='CUIL' placeholder='11 - 11222333 - 2' register={register} errors={errors}
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
                        <Campo field='Provincia' placeholder='Ej. Entre Ríos' isRequired={true}
                               validation={validation['provincia']} register={register} errors={errors}/>
                        <Campo field='Pais' placeholder='Ej. Argentina' isRequired={true}
                               validation={validation['pais']} register={register} errors={errors}/>
                        </Row>

                        <Row>
                             <button type='button' className='Button'
                            onClick={() => setAlertaCancelarOpen(true)}>Cancelar
                             </button>
                            <button type='button' className='Button' data-backcolor='red' onClick={handleDelete}>
                             Eliminar
                            </button>

                             <button type='submit' className='Button'>Guardar Cambios</button>
                         </Row>
                    </div>
                </div>
            </form>
            <AlertaCancelar open={alertaCancelarOpen} setOpen={setAlertaCancelarOpen} text='la Modificacion del Huesped'/>
        </>
    );
}