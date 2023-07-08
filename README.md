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

## Configuracion postman para ejecutar la solucion de la prueba

### Paso 1: Descargar la Colección de Pruebas

1. Ve al directorio `collectionPostman` de este repositorio.
2. Descarga el archivo de la colección de pruebas `ColeccionTestNexus.postman_collection.json`.
3. Descarga el archivo de variables de entorno de pruebas `card.postman_environment.json`"

### Paso 2: Importar la Colección en Postman

1. Abre Postman en tu máquina.
2. Haz clic en el botón "Import" en la esquina superior izquierda de la interfaz.
3. Selecciona el archivo `ColeccionTestNexus.postman_collection.json`  y el archivo `card.postman_environment.json` que descargaste anteriormente.
4. La colección de pruebas y variables de entorno se importarán en Postman.

### Paso 3: Ejecutar las Pruebas

1. Abre la colección de pruebas en Postman.
2. Haz clic en el botón "Runner" en la esquina superior derecha de la interfaz.
3. Se abrirá el "Postman Collection Run Collection".
4. Asegúrate de que la colección de pruebas esté seleccionada en el campo `ColleccionTestNexus`.
5. Asegúrate de que la las variables de entorno de pruebas `Card` estén seleccionadas .
6. Haz clic en el botón "Run ColleccionTestNexus" para ejecutar las pruebas.
7. Postman ejecutará automáticamente las solicitudes y mostrará los resultados de las pruebas.

## Documentación de la API
La API de este proyecto está documentada utilizando Swagger. Swagger es una especificación y conjunto de herramientas populares para diseñar, crear y documentar APIs RESTful. Proporciona una interfaz interactiva para explorar y probar los endpoints de la API, así como una documentación clara y detallada de cada uno de ellos.

Para acceder a la documentación de la API, sigue los siguientes pasos:

### Ejecuta la aplicación.
1. Abre un navegador web y navega a la siguiente URL: http://localhost:puerto/doc/swagger-ui.html. Reemplaza puerto por el número de puerto en el que se ejecuta la aplicación.
2. En la interfaz de Swagger, encontrarás una lista de los endpoints disponibles, junto con información detallada sobre cada uno de ellos, incluyendo parámetros, respuestas y ejemplos.
3. La documentación de la API te ayudará a comprender y utilizar de manera efectiva los diferentes recursos y funcionalidades disponibles en el proyecto.

## Información

Esta sección proporciona información adicional sobre el proyecto, incluyendo detalles técnicos, requisitos y cualquier otra información relevante.

### Documentacion sobre la prueba tecnica

- Para documentar la api se utilizo swagger con el fin de 
- Tecnología 2
- Tecnología 3

### Requisitos

- Requisito 1
- Requisito 2
- Requisito 3

### Instalación

1. Clona este repositorio.
2. Instala las dependencias utilizando `npm install`.
3. Configura las variables de entorno en el archivo `.env`.
4. Ejecuta la aplicación con `npm start`.

### Contribución

Contribuye al proyecto siguiendo estos pasos:

1. Haz un fork de este repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-caracteristica`).
3. Realiza tus cambios y haz commits (`git commit -am 'Agrega nueva característica'`).
4. Sube tus cambios a la rama (`git push origin feature/nueva-caracteristica`).
5. Abre una Pull Request.

### Licencia

Este proyecto está bajo la Licencia MIT. Para más detalles, consulta el archivo [LICENSE](LICENSE).

### Contacto

- Nombre: Tu Nombre
- Email: tuemail@example.com
- Proyecto: [Nombre del Proyecto](https://github.com/tuusuario/tuproyecto)

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
