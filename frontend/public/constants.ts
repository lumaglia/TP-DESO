import { ChangeEvent } from 'react'

export enum fieldTypes {
    'TEXTBOX',
    'COMBOBOX',
    'DATE',
    'EMAIL',
    'PASSWORD',
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
    'nacionalidad': ["Afghanistan","Albania","Algeria","Andorra","Angola","Anguilla","Antigua","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia","Botswana","Brazil","British Virgin Islands","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Cape Verde","Cayman Islands","Chad","Chile","China","Colombia","Congo","Cook Islands","Costa Rica","Cote D Ivoire","Croatia","Cruise Ship","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Estonia","Ethiopia","Falkland Islands","Faroe Islands","Fiji","Finland","France","French Polynesia","French West Indies","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guam","Guatemala","Guernsey","Guinea","Guinea Bissau","Guyana","Haiti","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Isle of Man","Israel","Italy","Jamaica","Japan","Jersey","Jordan","Kazakhstan","Kenya","Kuwait","Kyrgyz Republic","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macau","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Mauritania","Mauritius","Mexico","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Namibia","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","Norway","Oman","Pakistan","Palestine","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania","Russia","Rwanda","Saint Pierre","Samoa","San Marino","Satellite","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","South Africa","South Korea","Spain","Sri Lanka","St Kitts","St Lucia","St Vincent","St. Lucia","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Timor L'Este","Togo","Tonga","Trinidad","Tunisia","Turkey","Turkmenistan","Turks","Uganda","Ukraine","United Arab Emirates","United Kingdom","Uruguay","Uzbekistan","Venezuela","Vietnam","Virgin Islands (US)","Yemen","Zambia","Zimbabwe"]
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
    'requerido': {
        required: true,
    },
    'usuario': {
        required: true,
        minLength: {
            value: 3,
        },
        maxLength: {
            value: 30,
            message: 'Usuario demasiado largo',
        },
        pattern: {
            value: /^[a-zA-Z0-9_-]+$/,
            message: "Solo letras, números, guiones."
        },
    },
    'contrasenna': {
        required: true,
        minLength: {
            value: 8,
            message: 'Minimo de 8 caracteres',
        },
        maxLength: {
            value: 30,
            message: 'Contraseña demasiado larga',
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
    'Usuario': 'usuario',
    'Contraseña': 'contrasenna',
    'Introduzca de nuevo la contraseña': 'verificarContrasenna',
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
    CU15
}

export type infoDisponibilidad = {
    habitacion: {
        nroHabitacion: string,
        tipo: string,
    },
    estadias: Array<
        {
            fechaInicio: string,
            fechaFin: string,
        }
    >,
    reservas: Array<
        {
            fechaInicio: string,
            fechaFin: string,
            apellido: string,
            nombre: string,
        }
    >
}