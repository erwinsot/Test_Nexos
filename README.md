# Prueba tecnica para nexos

## Requisitos
Antes de ejecutar este proyecto, asegúrate de tener lo siguiente instalado y configurado:

- [XAMPP](https://www.apachefriends.org/index.html) o [WAMP](https://www.wampserver.com/en/) (u otra alternativa similar) con MySQL.
- Versión de MySQL: 5.7 o superior.
- Asegúrate de tener [Postman](https://www.postman.com/downloads/) instalado en tu máquina.


## Instalación en local

1. Clona el repositorio en tu máquina local.
2. Inicia tu servidor MySQL a través de XAMPP, WAMP u otra herramienta similar.
3. Crear la base de datos con la que se trabajara la prueba tecnica `poner nombre deseado`.
4. Una vez creada la base de datos dirigirse `\Test_Nexos\src\main\resources\application.properties` y modificar `spring.datasource.url=jdbc:mysql://localhost:3306/dbnexos` cambiar `dbnexos` por nombre de base de datos creada.
5. Configura las credenciales de conexión a la base de datos `spring.datasource.username=` `spring.datasource.password=`.
6. Ejecutar Prueba tecnica


### Paso 1: Descargar la Colección de Pruebas

1. Ve al directorio "tests" de este repositorio.
2. Descarga el archivo de la colección de pruebas "ProjectTests.postman_collection.json".

### Paso 2: Importar la Colección en Postman

1. Abre Postman en tu máquina.
2. Haz clic en el botón "Import" en la esquina superior izquierda de la interfaz.
3. Selecciona el archivo "ProjectTests.postman_collection.json" que descargaste anteriormente.
4. La colección de pruebas se importará en Postman.

### Paso 3: Configurar Variables de Entorno (opcional)

Si el proyecto requiere variables de entorno específicas para las pruebas, configúralas en Postman antes de ejecutar las pruebas. Puedes hacerlo siguiendo estos pasos:

1. Haz clic en la colección de pruebas importada en la barra lateral izquierda de Postman.
2. Haz clic en la pestaña "Variables" en la parte superior de la interfaz.
3. Configura las variables requeridas con sus respectivos valores.

### Paso 4: Ejecutar las Pruebas

1. Abre la colección de pruebas en Postman.
2. Haz clic en el botón "Runner" en la esquina superior derecha de la interfaz.
3. Se abrirá el "Postman Collection Runner".
4. Asegúrate de que la colección de pruebas esté seleccionada en el campo "Collection".
5. Haz clic en el botón "Start Run" para ejecutar las pruebas.
6. Postman ejecutará automáticamente las solicitudes y mostrará los resultados de las pruebas.
## Características

- Característica 1: Descripción de la característica.
- Característica 2: Descripción de la característica.
- Característica 3: Descripción de la característica.

## Tecnologías utilizadas

- Tecnología 1: Descripción de la tecnología y su uso en el proyecto.
- Tecnología 2: Descripción de la tecnología y su uso en el proyecto.
- Tecnología 3: Descripción de la tecnología y su uso en el proyecto.

## Contribución

1. Haz un fork del repositorio.
2. Crea una rama con un nombre descriptivo (`git checkout -b feature/nombre-caracteristica`).
3. Realiza los cambios y realiza un commit con un mensaje descriptivo (`git commit -m "Agregar descripción de los cambios"`).
4. Realiza un push a tu repositorio fork (`git push origin feature/nombre-caracteristica`).
5. Abre una solicitud de extracción en el repositorio original.

## Equipo

- Nombre del colaborador 1
- Nombre del colaborador 2
- Nombre del colaborador 3

- [Guía de Uso](./guide.md)


## Licencia

Este proyecto está licenciado bajo [Nombre de la Licencia](URL de la licencia).
