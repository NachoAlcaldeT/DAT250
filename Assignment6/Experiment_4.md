# Experiment 4: Publish/Subscribe with RabbitMQ

## Experiment Description

Experiment 4 involved implementing a publish/subscribe system using RabbitMQ in Java. The main idea was to create a distributed logging system where multiple consumers could receive messages emitted by a producer, using a **fanout exchange**. This allowed us to implement a pattern where messages published by a producer are distributed to all connected consumers.

### Project Structure

The project follows the Gradle structure and the source code is organized in the `src/main/java/dat250` directory. Two main classes were developed:

- **EmitLog.java**: A producer that emits log messages to a **fanout exchange** called `logs`.
- **ReceiveLogs.java**: A consumer that subscribes to messages sent through the exchange and prints them to the console.

### build.gradle Configuration

The Shadow plugin was used to package the necessary dependencies into an executable JAR and facilitate the execution of the classes. Here are some important configurations:

- The dependency for the RabbitMQ AMQP client was added:
```java
  implementation("com.rabbitmq:amqp-client:5.16.0")
```
- The default main class was set to `dat250.Worker`, although for this experiment we used direct execution of other classes (`EmitLog` and `ReceiveLogs`).

## Problems Encountered and Solutions

### 1. Error Compiling with javac

Initially, we tried to compile the classes manually with `javac`, but received the error:
```java
error: file not found: EmitLog.java
```
This problem occurred because the `javac` command was not pointing to the correct path of the source files. Instead of compiling manually, we decided to use Gradle to manage the compilation and dependencies.

**Solution**: We executed the command `./gradlew build` to automatically compile all classes within the project.

### 2. Ejecución de las Clases Correctas

The `build.gradle` was configured to run the `dat250.Worker` class by default. However, for this experiment, we needed to run the `EmitLog` and `ReceiveLogs` classes. This created confusion about how to specify the class we wanted to execute.

**Solution**: We used the following Gradle command to run the specific classes:
```java
./gradlew run --args='dat250.EmitLog "Mensaje de prueba"'
```
y
```java
./gradlew run --args='dat250.ReceiveLogs'
```
This allowed me to run both the producer and consumer from the command line without modifying the `build.gradle` configuration.

### 3. Class Packages and Classpath

During execution with `java`, there were issues related to the classpath, as the dependencies were not initially included correctly. This problem was resolved by packaging everything into a JAR using Shadow and then using the following command to execute:

```java
java -cp "build/libs/assignment6_RabbitMQ-1.0-SNAPSHOT-all.jar" dat250.EmitLog "Mensaje de prueba"
```
## Pending Issues

We have not encountered any pending issues upon completion of the experiment. However, it is advisable to always check that RabbitMQ is running correctly on the appropriate port (localhost:5672).

## Source Code

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
## Screenshot

Here is the screenshot showing the correct sending and receiving of a message in the RabbitMQ system:
![Screenshot - Sending and receiving messages in RabbitMQ](https://github.com/NachoAlcaldeT/DAT250/blob/main/Assignment6/Screenshots_Assignment6/experiment4_RabbitMQ.png)

## Conclusions

The experiment was successful, and we managed to implement the publish/subscribe pattern using RabbitMQ and Java. Despite the initial challenges with manual compilation and executing the classes, integrating Gradle facilitated the process. All identified problems were resolved, and the system worked correctly.
