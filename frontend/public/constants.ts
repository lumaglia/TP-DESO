import { ChangeEvent } from 'react'

export enum fieldTypes {
    'TEXTBOX',
    'COMBOBOX',
    'DATE',
    'EMAIL',
    'TIME'

}

export const comboValues = {
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

export const validation = {
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
        required: 'La fecha de nacimiento es obligatoria',
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
            value: /^[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)+$/,
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
        required: 'El codigo postal es obligatorio',
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
    },
    'razonSocial': {
        required: 'Debe ingresar una razon social',
        maxLength: {
            value: 100,
            message: 'Longitud máxima 100 caracteres',
        },
        pattern: {
            value: /^[a-zA-Z0-9\s-_]+$/,
            message: 'Ingrese caracteres válidos',
        }
    },
}

export const MapNameToApi : {[key: string]: string} = {
    'Nombre': 'nombre',
    'Apellido': 'apellido',
    'Tipo de documento': 'tipoDoc',
    'Número de documento': 'nroDoc',
    'CUIL': 'cuil',
    'Posición frente al IVA': 'posicionIva',
    'Fecha de Nacimiento': 'fechaNac',
    'Telefono': 'telefono',
    'Email': 'email',
    'Ocupación': 'ocupacion',
    'Nacionalidad': 'nacionalidad',
    'Domicilio': 'direccion.domicilio',
    'Departamento': 'direccion.depto',
    'Código Postal': 'direccion.codigoPostal',
    'Localidad': 'direccion.localidad',
    'Provincia': 'direccion.provincia',
    'Pais': 'direccion.pais',
    'Desde Fecha': 'fechaInicio',
    'Hasta Fecha': 'fechaFin',
    'Numero de habitación' : "idHabitacion",
    "Hora de salida" : "horaSalida",
    "Razon Social": "razonSocial",
    "CUIT": "cuit"
};

export type FormValues = {
    nombre: string;
    apellido: string;
    tipoDoc: string;
    nroDoc: string;
    cuil?: string;
    posicionIva: string;
    fechaNac: string;
    telefono: string;
    email?: string;
    ocupacion: string;
    nacionalidad: string;
    domicilio: string;
    depto?: string;
    codigoPostal: string;
    localidad: string;
    provincia: string;
    pais: string;
    direccion: {
        domicilio: string;
        depto: string;
        codigoPostal: string;
        localidad: string;
        provincia: string;
        pais: string;
    };
};

export type DateValues = {
    fechaInicio: Date;
    fechaFin: Date;
}

export enum tiposTablaHabitacion {
    CU04,
    CU05,
    CU015
}
