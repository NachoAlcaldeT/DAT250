#Experimento 4: Publicar/Suscribirse con RabbitMQ

## Descripción del Experimento

El Experimento 4 consistió en implementar un sistema de publicación y suscripción (publish/subscribe) utilizando RabbitMQ en Java. La idea principal era crear un sistema de logs distribuido donde múltiples consumidores pudieran recibir los mensajes emitidos por un productor, utilizando un **fanout exchange**. Esto nos permitió implementar un patrón en el que los mensajes publicados por un productor son distribuidos a todos los consumidores conectados.

### Estructura del Proyecto

El proyecto sigue la estructura de Gradle y el código fuente está organizado en el directorio `src/main/java/dat250`. Se desarrollaron dos clases principales:

- **EmitLog.java**: Un productor que emite mensajes de logs a un **fanout exchange** llamado `logs`.
- **ReceiveLogs.java**: Un consumidor que se suscribe a los mensajes enviados a través del exchange y los imprime en la consola.

### Configuración del build.gradle

Se utilizó el plugin Shadow para empaquetar las dependencias necesarias en un JAR ejecutable y facilitar la ejecución de las clases. Aquí se muestran algunas configuraciones importantes:

- Se añadió la dependencia para el cliente AMQP de RabbitMQ:
```java
  implementation("com.rabbitmq:amqp-client:5.16.0")
```
- Se configuró la clase principal por defecto como `dat250.Worker`, aunque para este experimento se utilizó la ejecución directa de otras clases (`EmitLog` y `ReceiveLogs`).

## Problemas Encontrados y Soluciones

### 1. Error al Compilar con javac

Inicialmente intentamos compilar las clases manualmente con `javac`, pero recibimos el error:
```java
error: file not found: EmitLog.java
```
Este problema surgió porque el comando `javac` no estaba apuntando a la ruta correcta de los archivos fuente. En lugar de compilar manualmente, decidimos utilizar Gradle para gestionar la compilación y las dependencias.

**Solución**: Ejecutamos el comando `./gradlew build` para compilar automáticamente todas las clases dentro del proyecto.

### 2. Ejecución de las Clases Correctas

El `build.gradle` estaba configurado para ejecutar la clase `dat250.Worker` por defecto. Sin embargo, para este experimento necesitábamos ejecutar las clases `EmitLog` y `ReceiveLogs`. Esto generó confusión sobre cómo especificar la clase que deseábamos ejecutar.

**Solución**: Utilizamos el siguiente comando de Gradle para ejecutar las clases específicas:
```java
./gradlew run --args='dat250.EmitLog "Mensaje de prueba"'
```
y
```java
./gradlew run --args='dat250.ReceiveLogs'
```
Esto nos permitió ejecutar tanto el productor como el consumidor desde la línea de comandos sin modificar la configuración del `build.gradle`.

### 3. Paquetes de Clases y Classpath

Durante la ejecución con `java`, hubo problemas relacionados con el classpath, ya que inicialmente no se incluían correctamente las dependencias. Este problema fue resuelto empaquetando todo en un JAR utilizando Shadow y luego utilizando el siguiente comando para ejecutar:
```java
java -cp "build/libs/assignment6_RabbitMQ-1.0-SNAPSHOT-all.jar" dat250.EmitLog "Mensaje de prueba"
```
## Problemas Pendientes

No hemos tenido problemas pendientes al finalizar la ejecución del experimento. Sin embargo, es recomendable verificar siempre que RabbitMQ esté ejecutándose correctamente en el puerto adecuado (localhost:5672).

## Código Fuente

### EmitLog.java
```java
import com.rabbitmq.client.Channel;  
import com.rabbitmq.client.Connection;  
import com.rabbitmq.client.ConnectionFactory;  

public class EmitLog {  
    private static final String EXCHANGE_NAME = "logs";  
  
    public static void main(String[] argv) throws Exception {  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("localhost");  
        try (Connection connection = factory.newConnection();  
             Channel channel = connection.createChannel()) {  
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");  
  
            String message = argv.length < 1 ? "info: Hello World!" : String.join(" ", argv);  
  
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));  
            System.out.println(" [x] Sent '" + message + "'");  
        }  
    }  
}
```
### ReceiveLogs.java
```java
import com.rabbitmq.client.Channel;  
import com.rabbitmq.client.Connection;  
import com.rabbitmq.client.ConnectionFactory;  
import com.rabbitmq.client.DeliverCallback;  

public class ReceiveLogs {  
    private static final String EXCHANGE_NAME = "logs";  
  
    public static void main(String[] argv) throws Exception {  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("localhost");  
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
  
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");  
        String queueName = channel.queueDeclare().getQueue();  
        channel.queueBind(queueName, EXCHANGE_NAME, "");  
  
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");  
  
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {  
            String message = new String(delivery.getBody(), "UTF-8");  
            System.out.println(" [x] Received '" + message + "'");  
        };  
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});  
    }  
}
```
## Captura de Pantalla

Aquí está la captura de pantalla mostrando el envío y la recepción correctos de un mensaje en el sistema RabbitMQ:
![Captura de pantalla - Envío y recepción de mensajes en RabbitMQ](https://github.com/NachoAlcaldeT/DAT250/blob/main/Assignment5/Screenshots_Assignment5/MongoDB-True_screenshot.png)

## Conclusiones

El experimento fue exitoso y logramos implementar el patrón de publicación/suscripción utilizando RabbitMQ y Java. A pesar de los desafíos iniciales con la compilación manual y la ejecución de las clases, la integración de Gradle facilitó el proceso. Todos los problemas identificados fueron resueltos y el sistema funcionó correctamente.
