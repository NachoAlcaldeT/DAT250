# Experiment 2: RabbitMQ "Hello World" Implementation

## Introduction

In this experiment, we explored the basic functionality of RabbitMQ by creating a simple producer-consumer application in Java. The goal was to establish communication between a producer that sends a message ("Hello World") and a consumer that receives and displays the message.

RabbitMQ acts as a message broker, handling messages similar to a post office. Producers send messages to queues, and consumers receive messages from those queues. This decoupling of message producers and consumers allows for flexible and scalable applications.

## Code Implementation

### Producer: Send.java

The producer connects to RabbitMQ, declares a queue, sends a message, and then exits.

```java
package dat250;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
```
### Consumer: Recv.java

The consumer listens for messages from the queue, receiving them asynchronously and printing them out.

```java
package dat250;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
```

## Issues Encountered

There were no significant issues encountered during this experiment. The RabbitMQ setup and communication between the producer and consumer worked as expected. The straightforward nature of the "Hello World" implementation made it a smooth experience.

## Screenshot

Below is a screenshot demonstrating the successful sending and receiving of messages:
![Screenshot - Hello World message](https://github.com/NachoAlcaldeT/DAT250/blob/main/Assignment6/Screenshots_Assignment6/experiment2_RabbitMQ.png)

## Conclusion
This experiment successfully demonstrated the basic operations of RabbitMQ, including message sending and receiving. The implementation of a simple producer-consumer model in Java provided valuable insights into how messaging systems work. Understanding these fundamentals is crucial as we move forward to more complex implementations in future experiments.
