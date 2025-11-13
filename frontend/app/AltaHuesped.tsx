'use client';

import { ReactNode, useState, useEffect, ChangeEvent } from 'react';
import { useForm, FormProvider, useFormContext, FieldValues } from 'react-hook-form';
import './AltaHuesped.css'


enum fieldTypes {
    'TEXTBOX',
    'COMBOBOX',
    'DATE',
    'EMAIL',

}

const comboValues = {
    'tipoDocumento': [
        'DNI',
        'LE',
        'LC',
        'Pasaporte',
        'Otro'
    ],
    'posicionIva': [
        'Consumidor Final',
        'Monotributista',
        'Responsable Inscripto'
    ],
    'nacionalidad': [
        'Argentina',
        'Bolivia',
        'Peru'
    ]
}

const validation = {
    'nombre': {
        required: 'El nombre es obligatorio',
        maxLength: {
            value: 100,
            message: 'Máximo 100 caracteres',
        },
        pattern: {
            value: /^[A-ZÁÉÍÓÚ\s]+$/,
            message: 'Ingresar solo letras o espacios'
        },
        validate: {
            notStartingBlank: (value: string) => {
                return value[0] !== ' ' || 'El nombre debe comenzar con una letra';
            },
            notEndingBlank: (value: string) => {
                return value.slice(-1) !== ' ' || 'El nombre debe terminar con una letra';
            }
        },
        setValueAs: (value: string) => value.replace(/\s+/g, ' ').toUpperCase(),
        onChange: (e: ChangeEvent<HTMLInputElement>) => {e.target.value = e.target.value.toUpperCase()}
    },
    'apellido': {
        required: 'El apellido es obligatorio',
        maxLength: {
            value: 30,
            message: 'Máximo 30 caracteres',
        },
        pattern: {
            value: /^[A-ZÁÉÍÓÚ]+$/,
            message: 'Ingresar solo letras'
        },
        setValueAs: (value: string) => value.toUpperCase(),
        onChange: (e: ChangeEvent<HTMLInputElement>) => {e.target.value = e.target.value.toUpperCase()}
    },
    'tipoDocumento': {
        required: 'El tipo de documento es obligatorio',
        validate: (value: string) => {return value !== '' || 'El tipo de documento es obligatorio'}

    },
    'numeroDocumento': {
        required: 'El número de documento es obligatorio',
        maxLength: {
            value: 10,
            message: 'Debe tener menos de 10 números'
        },
        pattern: {
            value: /^[0-9]+$/,
            message: 'Ingresar solo números'
        }
    },
    'cuil': {
        required: {
            value: true,
            message: 'Debe ingresar un CUIL',
        },
        minLength: {
            value: 13,
            message: 'El CUIL debe contener 11 numeros',
        },
        maxLength: {
            value: 13,
            message: 'El CUIL debe contener 11 numeros',
        },
        pattern: {
            value: /^[0-9]{2}-[0-9]{8}-[0-9]$/,
            message: 'El CUIL debe seguir el patron 00-00000000-0'
        },
        setValueAs: (value: string) => value.replace(/\s/g, ''),
        onChange: (e: ChangeEvent<HTMLInputElement>) => {
            console.log(e)
            if (e.target.value.length == 2) {
                e.target.value += ' - '
            }
            else if (e.target.value.length == 4) {
                e.target.value = e.target.value.slice(0, 1);
            }
            else if (e.target.value.length == 13) {
                e.target.value += ' - '
            }
            else if (e.target.value.length == 15) {
                e.target.value = e.target.value.slice(0, 12);
            }
        }

    },
    'posicionIva': {},
    'fechaNacimiento': {
        valueAsDate: true,
        required: true,
        pattern: {
            value: /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/,
            message: 'La fecha debe seguir el patron DD/MM/YYYY'
        },
        validate: {
            validDate: (value: Date) => {return !isNaN(value.getTime()) || 'Debe ingresarse una fecha valida'},
            pastDate: (value: Date) => {return value <= new Date() || 'La fecha debe ser anterior a hoy'}
        },
    },
    'numeroTelefono': {
        required: 'El telefono es obligatorio',
        minLength: {
            value: 3,
            message: 'Longitud minima 3 caracteres',
        },
        maxLength: {
            value: 20,
            message: 'Longitud máxima 20 caracteres',
        },
        pattern: {
            value: /^(\+[0-9]{1,2}\s)?([0-9]\s)?[0-9]+$/,
            message: 'Debe seguir el patron [+xx ][x ][xxxxxxxxxx]'
        }
    },
    'email': {
        minLength: {
            value: 3,
            message: 'Longitud minima 3 caracteres',
        },
        maxLength: {
            value: 30,
            message: 'Longitud máxima 30 caracteres',
        },
        pattern: {
            value: /^[a-zA-Z0-9][a-zA-Z0-9.]*@[a-zA-Z0-9]+(\.a-zA-Z0-9)+$/,
            message: 'Ingrese una email valido',
        }
    },
    'ocupacion': {
        required: 'La ocupacion es obligatoria',
        maxLength: {
            value: 30,
            message: 'Longitud máxima 30 caracteres',
        },
        pattern: {
            value: /^[a-zA-Z0-9\s-_]+$/,
            message: 'Ingrese caracteres válidos',
        }
    },
    'nacionalidad': {
        validate: (value: string) => {return value !== '' || 'El campo es obligatorio'}
    },
    'domicilio': {
        required: 'El domicilio es obligatorio',
        maxLength: {
            value: 30,
            message: 'Longitud máxima 30 caracteres',
        },
        pattern: {
            value: /^[a-zA-Z0-9\s-_]+$/,
            message: 'Ingrese caracteres válidos',
        }
    },
    'departamento': {
        maxLength: {
            value: 30,
            message: 'Longitud máxima 30 caracteres',
        },
        pattern: {
            value: /^[a-zA-Z0-9\s-_]+$/,
            message: 'Ingrese caracteres válidos',
        }
    },
    'codigoPostal': {
        required: 'EL codigo postal es obligatorio',
        maxLength: {
            value: 10,
            message: 'Longitud máxima 10 caracteres',
        },
        pattern: {
            value: /^[0-9]+$/,
            message: 'Ingrese solo números',
        }
    },
    'localidad': {
        required: 'La localidad es obligatoria',
        maxLength: {
            value: 30,
            message: 'Longitud máxima 30 caracteres',
        },
        pattern: {
            value: /^[a-zA-Z0-9\s-_]+$/,
            message: 'Ingrese caracteres válidos',
        }
    },
    'provincia': {
        required: 'La provincia es obligatoria',
        maxLength: {
            value: 30,
            message: 'Longitud máxima 30 caracteres',
        },
        pattern: {
            value: /^[a-zA-Z0-9\s-_]+$/,
            message: 'Ingrese caracteres válidos',
        }
    },
    'pais': {
        required: 'El pais es obligatorio',
        maxLength: {
            value: 30,
            message: 'Longitud máxima 30 caracteres',
        },
        pattern: {
            value: /^[a-zA-Z0-9\s-_]+$/,
            message: 'Ingrese caracteres válidos',
        }
    }


}

function Campo({ field, register, errors, type = fieldTypes.TEXTBOX, isRequired = false, placeholder, comboValues, validation, hidden = false }: { field: string, register: any, errors: any, type?: fieldTypes, isRequired?: boolean, placeholder?: string, comboValues?: Array<string>, validation: object, hidden?: boolean }) {
    if(!hidden)
    return (
        <div style={{marginRight:'5px'}}>
            <div style={{ display: 'flex', marginLeft:'10px', marginBottom:'5px' }}>
                {<p>{field}</p>}
                {isRequired && <p className='error'>*</p>}
                <p className='error' style={{marginLeft: '5px',visibility: errors[field]? 'visible' : 'hidden'}}>{errors[field] ? errors[field].message : 'placeholder'}</p>
            </div>

            {
                (type !== fieldTypes.COMBOBOX) ?
                    <input className={errors[field] && 'error'} {...register(field, validation)} placeholder={placeholder}
                       type={fieldTypes[type].toString() === 'TEXTBOX' ? 'text' : fieldTypes[type].toLowerCase()}/>
                :
                    <select className={errors[field] && 'error'} required defaultValue={placeholder !== undefined ? placeholder : ''} {...register(field, validation)}>
                        <option disabled key='' value='' hidden={true}>No Especificado</option>
                        {comboValues?.map((option) => (<option key={option} value={option}>{option}</option>))}
                    </select>
            }
        </div>
    );
    else
        return(<input style={{marginRight: '5px', visibility:'hidden'}}/>)
}

function Row({ children }: {children: ReactNode}) {
    return (
        <div className='row'>
            {children}
        </div>
    );
}


function AltaHuesped() {

    // function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    //     // Prevent the browser from reloading the page
    //     e.preventDefault();
    //
    //     // Read the form data
    //     // const form = e.target;
    //     const form = e.currentTarget;
    //     const formData = new FormData(form);
    //
    //     // You can pass formData as a fetch body directly:
    //     // fetch('/some-api', { method: form.method, body: formData });
    //
    //     // Or you can work with it as a plain object:
    //     const formJson = Object.fromEntries(formData.entries());
    //     console.log(formJson);
    // }

    const form = useForm();
    const { register, control, handleSubmit, formState, watch, clearErrors, trigger } = form;
    const { errors } = formState;

    // const  [posicionIVA, setPosicionIVA] = useState('Consumidor Final');
    // validation['posicionIva'].onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    //     setPosicionIVA(e.target.value);
    //     clearErrors('Posición frente al IVA');
    // };
    const posicionIVA = watch('Posición frente al IVA');
    if(validation['cuil'].required.value && posicionIVA !== 'Responsable Inscripto'){
        validation['cuil'].required.value = posicionIVA === 'Responsable Inscripto'
        trigger('CUIL')
        // clearErrors('CUIL');
    }else validation['cuil'].required.value = posicionIVA === 'Responsable Inscripto'
    // useEffect(() => {
    //     clearErrors('CUIL');
    //     validation['cuil'].required.value = posicionIVA === 'Responsable Inscripto'
    //     console.log(validation['cuil'].required.value)
    // }, [watch]);

    const onSubmit = (data: FieldValues) => {
        console.log('Form submitted successfully!', data);
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
                            <button type='button' style={{}}>Cancelar</button>
                            <Campo field='Provincia' placeholder='Ej. Entre Ríos' isRequired={true}
                                   validation={validation['provincia']} register={register} errors={errors}/>
                            <Campo field='Pais' placeholder='Ej. Argentina' isRequired={true}
                                   validation={validation['pais']} register={register} errors={errors}/>
                            <button type='submit' style={{}}>Enviar</button>
                        </Row>
                    </div>
                </div>
            </form>
        </>
    );
}

export default AltaHuesped