Este proyecto está compuesto por un backend desarrollado en Java utilizando Spring Boot y un frontend desarrollado con Next.js. Para poder ejecutar la aplicación es necesario tener instalado previamente Java 25 y Node.js, el cual incluye npm de forma predeterminada.

Para ejecutar el backend, simplemente se debe abrir el proyecto en un IDE (como IntelliJ IDEA) y ejecutar la clase principal que contiene el método main y la anotación @SpringBootApplication. Esto iniciará automáticamente la aplicación Spring Boot.

Para ejecutar el frontend, se debe abrir una terminal y posicionarse en el directorio TP-DESO\frontend\. La primera vez que se ejecuta el proyecto, es necesario instalar las dependencias del frontend ejecutando el comando npm install. Una vez finalizada la instalación, la aplicación se puede iniciar en modo desarrollo ejecutando el comando 'npm run dev' desde ese mismo directorio.

El backend y el frontend deben ejecutarse en paralelo, cada uno en su propio proceso. 

La base de datos se popula mediante una clase Java 'PopulateBDD' ubicada al lado del archivo main. Para ejecutar el seed, se debe correr dicha clase manualmente desde el IDE (por ejemplo IntelliJ IDEA), ejecutándola como una aplicación Java. Esta clase se encarga de inicializar la base de datos con datos de prueba necesarios para el correcto funcionamiento del sistema. Una vez finalizada la ejecucion de la clase, se puede inicializar el proyecto con los datos.

Las pruebas unitarias del backend se pueden ejecutar utilizando Maven. Desde el directorio raíz del backend (TP-DESO/api), se debe abrir una terminal y ejecutar el comando './mvnw test' en sistemas Unix/Linux o 'mvnw test' o '.\mvnw test' en Windows. Esto ejecutará todas las pruebas unitarias configuradas en el proyecto y generará los reportes correspondientes (JaCoCo). Como pide el enunciado, las prubas son hechas en la capa service, especificamente en las clases 'GestorHuespued', 'GestorHabitacion' y 'GestorReserva'. En caso de querer ver las pruebas manualmente, estas se encuentran en el apartado 'TP-DESO\api\src\test\java\org\example\TP_DESO\service', donde se encontraran cada clase de de tipo test, y se pueden ejecutar tanto las clases con todos los test en ella, o cada metodo (test) que contiene la clase.

Todos los endpoints para los caso de uso existen en http://localhost:3000 y son llamados por el Front para los casos de uso, a continuación se lista cada End Point y los casos de usos en los que se emplea.

CU01 - Auntenticar Usuario

Registrar Usuario
Método: POST
URL: api/auth/register
Body (JSON):
json
{
    "usuario": "Conserje"
    "contrasenna": "contraseña1234"
}

Login
Método: POST
URL: api/auth/login
Body (JSON):
json
{
    "usuario": "Conserje"
    "contrasenna": "contraseña1234"
}