# Informe de DAT250 - Experimento 4

## Base de Datos Utilizada

Para este experimento, se utilizó una base de datos H2, que es una base de datos en memoria ligera y fácil de usar, ideal para el desarrollo y las pruebas. La base de datos se ejecuta automáticamente al iniciar la aplicación y se mantiene en memoria durante la ejecución. Cuando se cierra la aplicación, todos los datos se pierden, a menos que se configure para guardar los datos en disco.

## Inspección de las Tablas de la Base de Datos

Para inspeccionar las tablas creadas, utilicé la consola H2, que se puede iniciar junto con la aplicación. Esto permite conectarse a la base de datos y ejecutar consultas SQL para visualizar las tablas y sus contenidos.

### Tablas Creadas

Las tablas creadas durante la ejecución del programa incluyen:

- **customer**
- **address**
- **creditcard**
- **bank**
- **pincode**

Aquí hay una captura de pantalla de la consola H2 mostrando las tablas:

![Captura de pantalla de la consola H2](ruta/a/tu/captura.png)

## Problemas Técnicos Encontrados

Durante la instalación y uso de la Java Persistence Architecture (JPA), encontré algunos problemas:

1. **Configuración de Dependencias**: Inicialmente, no se añadieron las dependencias correctas para Hibernate y H2 en el archivo `build.gradle`, lo que causó errores de clase no encontrada. Esto se resolvió añadiendo las dependencias adecuadas.

2. **Problemas de Conexión a la Base de Datos**: Al intentar conectarme a la base de datos H2, obtuve un error de conexión. Esto se resolvió asegurando que el URL de conexión en `persistence.xml` estaba correctamente configurado.

3. **Errores de Persistencia**: Algunos objetos no se estaban persistiendo correctamente. Esto fue resuelto revisando el flujo de creación de objetos y asegurando que se utilizaban los métodos de adición para mantener las relaciones entre las entidades.

## Código del Experimento 2

El código para el experimento se puede encontrar en el siguiente enlace: [Código del Experimento 2](link_a_tu_repositorio).

Asegúrate de que el caso de prueba incluido en el código pasa exitosamente.

## Problemas Pendientes

1. **Manejo de Relaciones Complejas**: Aunque la mayoría de las relaciones se han implementado correctamente, todavía hay algunos problemas menores con la sincronización de las colecciones entre entidades.

2. **Persistencia en Disco**: Aún estoy evaluando cómo persistir datos en disco para pruebas futuras, ya que actualmente solo utilizo la base de datos en memoria.

## Conclusión

Este experimento ha sido un gran aprendizaje sobre la implementación de JPA y cómo gestionar relaciones entre entidades. A través de la inspección de la base de datos y la resolución de problemas, se ha podido consolidar el conocimiento en la materia.
