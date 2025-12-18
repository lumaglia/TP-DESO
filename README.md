Este proyecto está compuesto por un backend desarrollado en Java utilizando Spring Boot y un frontend desarrollado con Next.js. Para poder ejecutar la aplicación es necesario tener instalado previamente Java 25 y Node.js, el cual incluye npm de forma predeterminada.

Para ejecutar el backend, simplemente se debe abrir el proyecto en un IDE (como IntelliJ IDEA) y ejecutar la clase principal que contiene el método main y la anotación @SpringBootApplication. Esto iniciará automáticamente la aplicación Spring Boot.

Para ejecutar el frontend, se debe abrir una terminal y posicionarse en el directorio TP-DESO\frontend\. La primera vez que se ejecuta el proyecto, es necesario instalar las dependencias del frontend ejecutando el comando npm install. Una vez finalizada la instalación, la aplicación se puede iniciar en modo desarrollo ejecutando el comando 'npm run dev' desde ese mismo directorio.

El backend y el frontend deben ejecutarse en paralelo, cada uno en su propio proceso. 

La base de datos se popula mediante una clase Java 'PopulateBDD' ubicada al lado del archivo main. Para ejecutar el seed, se debe correr dicha clase manualmente desde el IDE (por ejemplo IntelliJ IDEA), ejecutándola como una aplicación Java. Esta clase se encarga de inicializar la base de datos con datos de prueba necesarios para el correcto funcionamiento del sistema. Una vez finalizada la ejecucion de la clase, se puede inicializar el proyecto con los datos.

Las pruebas unitarias del backend se pueden ejecutar utilizando Maven. Desde el directorio raíz del backend (TP-DESO/api), se debe abrir una terminal y ejecutar el comando './mvnw test' en sistemas Unix/Linux o 'mvnw test' o '.\mvnw test' en Windows. Esto ejecutará todas las pruebas unitarias configuradas en el proyecto y generará los reportes correspondientes (JaCoCo). Como pide el enunciado, las prubas son hechas en la capa service, especificamente en las clases 'GestorHuespued', 'GestorHabitacion' y 'GestorReserva'. En caso de querer ver las pruebas manualmente, estas se encuentran en el apartado 'TP-DESO\api\src\test\java\org\example\TP_DESO\service', donde se encontraran cada clase de de tipo test, y se pueden ejecutar tanto las clases con todos los test en ella, o cada metodo (test) que contiene la clase.

Todos los endpoints para los caso de uso existen en http://localhost:3000 y son llamados por el Front para los casos de uso, a continuación se lista cada End Point y los casos de usos en los que se emplea.

CU 01 - Auntenticar Usuario

Registrar Usuario
Método: POST
URL: api/auth/register
Body (JSON):

{
    "usuario": "Conserje",
    "contrasenna": "contraseña1234"
}

Registra un usuario a la base de datos y devuelve un token de login

Login
Método: POST
URL: api/auth/login
Body (JSON):

{
    "usuario": "Conserje",
    "contrasenna": "contraseña1234"
}

Valida el usuario en la base de datos y devuelve un token de login si existe el usuario

CU 02 - Buscar Huesped

Método: POST
URL: http://localhost:3000/Huesped/Buscar
Body (JSON):

{
    "nombre": null,
    "apellido": null,
    "tipoDoc": "DNI",
    "nroDOc": null
}

Devuelve una lista de huespedes que contengan los datos no nulos enviados en el body

CU 04 - Reservar Habitación

Método: POST
URL: http://localhost:3000/Habitacion/Reservar
Body (JSON):

[
    {
        "nroHabitacion": "2",
        "fechaInicio": "2025-12-20",
        "fechaFin": "2025-12-25",
        "nombre": "MAXIMO",
        "apellido": "SAVOGIN",
        "telefono": "12345678"
    },
    {
        "nroHabitacion": "3",
        "fechaInicio": "2025-12-20",
        "fechaFin": "2025-12-25",
        "nombre": "MAXIMO",
        "apellido": "SAVOGIN",
        "telefono": "12345678"
    }
]

Crea las reservas suministradas en el array

CU 05 - Mostrar Estado Habitación

Método: GET
URL: http://localhost:3000/Habitacion/Buscar/{fechaInicio}/{fechaFin}
Recupera todas las reservas y estadías para todas las habitaciones tales que existan dentro del rango de fechas.

CU 06 - Cancelar Reserva

Método: POST
URL: http://localhost:3000/Habitacion/Reserva/Buscar
Body (JSON):

{
    "apellido": "PEREZ",
    "nombre": null
}

Busca la reserva que este a nombre de alguien del apellido enviado y del nombre si este se pasa no nulo.

Método: DELETE
URL: http://localhost:3000/Habitacion/Reserva/Cancelar
Body (JSON):

{
    "nroHabitacion": "11",
    "fechaInicio": "2025-03-01",
    "fechaFin": "2025-03-04",
    "nombre": "ANDRES",
    "apellido": "PEREZ",
    "tipoHabitacion": "Doble Estandar"
}

Cancela la reserva suministrada cambiandole el estado a cancelada.

CU 07 - Facturar

Método: POST
URL: http://localhost:3000/Factura/Checkout
Body (JSON):

{
    "nroHabitacion": "2",
    "diaCheckout": "2025-12-17T12:00:00.000Z"
}

Obtiene para la estadia del numero de habitación provisto y cuya fechaFin fue el día enviado y la hora de salida es la hora del día de hoy:
Los huespedes asociados a la estadía, los consumos pendientes de ser facturados y el valor de la estadía contemplando la hora tardia (en el timestamp es pasado las T13:00:00.000Z) a menos que el valor de la estadia ya se ha facturado caso en el cual devuelve 0.

Método: GET
URL: http://localhost:3000/Factura/BuscarResponsablePago/{cuit}

Retorna la razon social del responsable de pago cuyo CUIT fue provisto o No Content si no lo encuentra. 

Método: POST
URL: http://localhost:3000/Factura/Emitir
Body (JSON):

{
    "pagaEstadia": false,
    "consumos": [ "1","3" ],
    "nroHabitacion": "2",
    "diaCheckout": "2025-12-17T12:00:00.000Z",
    "esHuesped": true,
    "cuit": "20-28000000-4",
    "tipoDoc": "DNI",
    "nroDoc": "28000000"
}

Carga una factura en el sistema y genera su valor en base a los consumos declarados y si paga la estadia, 
segun el booleano se asocia el Responsable de pago a una Persona Fisica de tipoDoc y nroDoc o a una Persona Juridica del cuit, los valores no usados se ignoran y tratan como basura.

CU 09 - Dar de Alta Huesped

Método: POST
URL: http://localhost:3000/Huesped/Alta
Body (JSON):

{
    "nombre": "Maximo",
    "apellido": "Savogin",
    "tipoDoc": "DNI",
    "nroDoc": "45058033",
    "cuil": "20-45058033-4",
    "posicionIva": "Responsable Inscripto",
    "fechaNac": "2003-08-11",
    "telefono": "23456789",
    "email": "",
    "ocupacion": "Estudiante",
    "nacionalidad": "Argentina",
    "direccion": {
        "domicilio": "Matheu 536",
        "depto": "",
        "codigoPostal": "3000",
        "localidad": "Santa Fe",
        "provincia": "Santa Fe",
        "pais": "Argentina"
    }
}

Carga un nuevo huesped al sistema

CU 10 - Modificar Huesped (primer endpoint también utilizado por CU 09)

Método: PUT
URL: http://localhost:3000/Huesped/Modificar
Body (JSON):

{
    "tipoDocViejo": "DNI"
    "nroDocViejo": "45058033"
    "nombre": "Maximo",
    "apellido": "Savogin",
    "tipoDoc": "LC",
    "nroDoc": "45058000",
    "cuil": "20-45058033-4",
    "posicionIva": "Responsable Inscripto",
    "fechaNac": "2003-08-11",
    "telefono": "23456789",
    "email": "",
    "ocupacion": "Estudiante",
    "nacionalidad": "Argentina",
    "direccion": {
        "domicilio": "Matheu 536",
        "depto": "",
        "codigoPostal": "3000",
        "localidad": "Santa Fe",
        "provincia": "Santa Fe",
        "pais": "Argentina"
    }
}

Modifica al tipoDocViejo nroDocViejo con los datos del huesped nuevos provistos y cambia el documento si puede,
si encuentra que el nuevo documento sobre-escribira a otro huesped no se ejecuta a menos que la URL sea http://localhost:3000/Huesped/Modificar/Override 

Método: POST
URL: http://localhost:3000/Huesped/Obtener
Body (JSON):

{
    "nombre": null,
    "apellido": null,
    "tipoDoc": "DNI",
    "nroDoc": "45058033"
}

Obtiene el Huesped Completo cuyo documento coincide con el suministrado.

CU 11 - Dar de Baja Huesped

Método: DELETE
URL: http://localhost:3000/Huesped/Baja
Body (JSON):

{
    "nombre": null,
    "apellido": null,
    "tipoDoc": "DNI",
    "nroDoc": "45058033",
    "cuil": null,
    "posicionIva": null,
    "fechaNac": null,
    "telefono": null,
    "email": null,
    "ocupacion": null,
    "nacionalidad": null,
    "direccion": null
}

Elimina el huesped cuyo Documento se provee si existe y no tiene estadías asociadas

CU 12 - Dar de Alta Responsable de Pago

Método: POST
URL: http://localhost:3000/ResponsablePago/Alta
Body (JSON):

{
    "razonSocial": "Mi Empresa Razon",
    "cuit": "11-11111111-1",
    "telefono": "23456789",
    "direccion": {
        "domicilio": "Calle Original 123",
        "depto": "",
        "codigoPostal": "3200",
        "localidad": "Desarrollolandia",
        "provincia": "Sistemia",
        "pais": "Ingeni"
    }
}

Carga una nueva persona juridica al sistema

Método: PUT
URL: http://localhost:3000/ResponsablePago/Modificar
Body (JSON):

{
    "razonSocial": "Nueva Razon Empresa",
    "cuit": "11-11111111-1",
    "telefono": "23456000",
    "direccion": {
        "domicilio": "Calle Original 123",
        "depto": "",
        "codigoPostal": "3200",
        "localidad": "Desarrollolandia",
        "provincia": "Sistemia",
        "pais": "Ingeni"
    }
}

Modifica la Persona Juridica de CUIT provisto cambiandole todos sus datos a los nuevos

CU 15 - Ocupar Habitación

Método: POST
URL: http://localhost:3000/Habitacion/Ocupar
Body (JSON):

{
    "nroHabitacion": "2",
    "fechaInicio": "2025-12-20",
    "fechaFin": "2025-12-25",
    "huespedes": [
        {
            nombre: 'MANOLO', 
            apellido: 'GARCIA', 
            tipoDoc: 'DNI', 
            nroDoc: '23644968'
        },
        {
            nombre: 'MARTINCHO', 
            apellido: 'PERENCHO', 
            tipoDoc: 'DNI', 
            nroDoc:'23654678'
        }
    ]
}

Crea la estadia asignada a los huespedes suministrados