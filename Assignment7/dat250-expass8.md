# DAT250: Tarea 7 del Experimento de Tecnología de Software

## Introducción
En esta tarea hemos trabajado con Docker para contenedorización de software, con el objetivo de entender tanto el uso de imágenes Docker oficiales como la creación de nuestras propias imágenes. La tarea se divide en dos partes:

1. Utilizar una imagen Docker oficial de PostgreSQL y configurarla para que funcione con una aplicación Java.
2. Crear nuestra propia imagen Docker para una aplicación Spring Boot, empaquetándola en un contenedor.

### Errores Encontrados
Durante la realización de la tarea, nos encontramos con los siguientes problemas:
- **Problemas de conexión con PostgreSQL**: Al principio no configuramos correctamente las variables de entorno, lo que causaba errores de autenticación al conectarnos a la base de datos. Se resolvió al asegurarnos de que las variables `POSTGRES_USER`, `POSTGRES_PASSWORD` y `POSTGRES_DB` estaban correctamente definidas.
- **Incompatibilidades con versiones de dependencias**: En el proyecto Java, hubo algunos problemas al cambiar el controlador de H2 a PostgreSQL. Esto se solucionó agregando la dependencia correcta de PostgreSQL y ajustando el archivo `persistence.xml`.

Finalmente, hemos sido capaces de completar ambas partes de la tarea con éxito, incluyendo la ejecución de nuestras pruebas unitarias usando PostgreSQL y el empaquetado de la aplicación Spring Boot en un contenedor Docker funcional.

## Parte 1: Uso de una imagen Dockerizada: PostgreSQL

### Configuración
Primero, aseguramos que Docker esté instalado y funcionando. Para verificarlo, ejecutamos el siguiente comando:

```bash
docker system info
```

### Descargar y ejecutar la imagen de PostgreSQL
Descargamos la imagen oficial de PostgreSQL desde Docker Hub con el siguiente comando:

```bash
docker pull postgres
```

Luego, para iniciar un contenedor de PostgreSQL, ejecutamos el comando docker run con los siguientes argumentos:

```bash
docker run -p 5432:5432 \
  -e POSTGRES_USER=jpa_client \
  -e POSTGRES_PASSWORD=secret \
  -e POSTGRES_DB=postgres \
  -d --name my-postgres --rm postgres
````
- **p 5432:5432: Mapea el puerto del contenedor al host.**
- **e POSTGRES_USER, POSTGRES_PASSWORD, POSTGRES_DB: Variables de entorno para configurar el usuario, contraseña y la base de datos inicial.**
- **d: Inicia el contenedor en segundo plano.**
- **name my-postgres: Asigna un nombre al contenedor.**
- **rm: Elimina el contenedor automáticamente al detenerse.**

### Verificar el estado del contenedor
Usamos el siguiente comando para comprobar que el contenedor esté en ejecución:
```bash
docker ps
```

### Logs del contenedor
Para ver los logs y asegurarnos de que PostgreSQL está funcionando correctamente:
```bash
docker logs my-postgres
```

### Conectar a PostgreSQL desde psql
Para conectarnos a PostgreSQL usando el cliente psql, utilizamos el siguiente comando:
```bash
psql -h 127.0.0.1 -p 5432 -U jpa_client -d postgres
```
Las credenciales configuradas anteriormente son utilizadas aquí (jpa_client, secret).

### Configuración en la Aplicación Java
Cambiamos de H2 a PostgreSQL en el proyecto Java agregando la siguiente dependencia en build.gradle.kts:
```bash
implementation("org.postgresql:postgresql:42.7.4")
```

Luego, modificamos el archivo persistence.xml para que utilice PostgreSQL:
```bash
<property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
<property name="hibernate.connection.driver_class" value="org.postgresql.Driver"/>
<property name="hibernate.connection.url" value="jdbc:postgresql://127.0.0.1:5432/postgres"/>
<property name="hibernate.connection.username" value="jpa_client"/>
<property name="hibernate.connection.password" value="secret"/>
```

## Resultados de las Pruebas:

Después de realizar todos los ajustes necesarios en la configuración de PostgreSQL y cambiar de la base de datos H2 a PostgreSQL, procedimos a ejecutar nuestras pruebas unitarias. **Inicialmente, las pruebas fallaron** debido a que las tablas requeridas por JPA no existían en la base de datos.

El error que se generó indicaba que las tablas no estaban definidas. Este problema ocurrió porque en la configuración inicial no habíamos habilitado la creación automática de esquemas en `persistence.xml`.

### Resolución del Problema

Para resolver este problema, añadimos las siguientes propiedades en el archivo `persistence.xml`, lo que permitió generar los scripts SQL necesarios para crear y eliminar las tablas:

```xml
<property name="jakarta.persistence.schema-generation.scripts.action" value="drop-and-create"/>
<property name="jakarta.persistence.schema-generation.scripts.create-target" value="schema.up.sql"/>
<property name="jakarta.persistence.schema-generation.scripts.drop-target" value="schema.down.sql"/>
```

## Generación de Scripts SQL

Al agregar estas líneas, se generaron dos archivos: `schema.up.sql` y `schema.down.sql`. Estos archivos contienen las sentencias SQL necesarias para crear (y eliminar) las tablas que nuestro modelo de datos JPA espera.

Una vez generados estos archivos, el siguiente paso fue aplicar las sentencias SQL manualmente en la base de datos a través de un cliente SQL.

## Aplicación Manual de los Scripts

Usamos nuestro cliente SQL para conectarnos a la base de datos PostgreSQL y ejecutar las sentencias `CREATE TABLE` del archivo `schema.up.sql`. De esta forma, se crearon las tablas en la base de datos.

## Segunda Ejecución de Pruebas: Éxito

Finalmente, después de haber creado manualmente las tablas en la base de datos, volvimos a ejecutar las pruebas unitarias. Esta vez las pruebas pasaron correctamente, lo que confirmó que la configuración estaba ahora funcionando como se esperaba con PostgreSQL en lugar de H2.
![Screenshot of H2 Console (createTable)](https://github.com/NachoAlcaldeT/DAT250/blob/main/Assignment4/createTable_screenshot.png)
