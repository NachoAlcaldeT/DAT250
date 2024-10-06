# Report for Experiment 3: RabbitMQ Integration

## Introduction

In this experiment, a messaging system was implemented using RabbitMQ to manage tasks in a Java application. The objective was to create a producer (`NewTask`) that sends messages and a consumer (`Recv`) that processes those tasks in a distributed environment.

## Objectives

1. Implement a messaging system using RabbitMQ.
2. Create classes for sending and receiving tasks.
3. Test the functionality of the system with various messages.

## Process

The development process involved the following steps:

1. **Project Setup**:
   - Gradle was used as the build tool, and the `build.gradle` file was configured to include the necessary dependencies for RabbitMQ.

2. **Building the JAR**:
   - A `.jar` file was built using the Shadow plugin to bundle all the required dependencies.

3. **Class Implementation**:
   - The `NewTask` class was developed to send messages, and the `Recv` class was created to receive them.

4. **Running the Application**:
   - The worker (`Recv`) was run in one terminal, and the producer (`NewTask`) was executed in another to send messages.

## Problems Encountered

During development, several issues were encountered:

1. **Compilation Problems**:
   - **Description**: When trying to run the `NewTask` class, the error `Error: Could not find or load main class dat250.NewTask` was encountered.
   - **Cause**: This error occurred because the JAR file was not being built correctly or the file path was incorrect.
   - **Solution**: The `build.gradle` file was reviewed, and the command `./gradlew clean shadowJar` was executed to regenerate the JAR file. It was verified that the generated `.jar` file contained the necessary classes using the command `jar tf build/libs/assignment6_RabbitMQ-1.0-SNAPSHOT-all.jar | grep NewTask`.

2. **Incorrect Worker Configuration**:
   - **Description**: When running the worker (`Recv`), it was not receiving messages sent from `NewTask`.
   - **Cause**: The main cause was an incorrect configuration in the connection to RabbitMQ. The RabbitMQ server was not running or the connection credentials were incorrect.
   - **Solution**: It was ensured that RabbitMQ was active on the system, and the connection configurations in the code were reviewed. After correcting the credentials and ensuring the connection, the worker began receiving messages correctly.

3. **Unprocessed Messages**:
   - **Description**: Some messages sent were not processed by the worker, resulting in a loss of information.
   - **Cause**: This was due to synchronization issues between sending and receiving messages. In some cases, the worker was not active at the time the messages were sent.
   - **Solution**: Checks were implemented in the code to ensure that messages were sent after the worker was active. Tests were also conducted to verify that the worker was ready to receive messages before sending.

4. **Error Handling**:
   - **Description**: The system lacked adequate error handling, which caused failures in sending messages to not be reported correctly.
   - **Cause**: Appropriate `try-catch` blocks were not implemented in the code to handle exceptions during message sending and receiving.
   - **Solution**: Exception handling was added to the `NewTask` and `Recv` code, allowing for capturing and managing connection or sending errors more effectively.

## Results

After resolving the mentioned issues, messages were successfully sent and received. The following commands were used to send the tasks:

```bash
java -cp build/libs/assignment6_RabbitMQ-1.0-SNAPSHOT-all.jar dat250.NewTask "First task completed."
java -cp build/libs/assignment6_RabbitMQ-1.0-SNAPSHOT-all.jar dat250.NewTask "Second task in progress."
java -cp build/libs/assignment6_RabbitMQ-1.0-SNAPSHOT-all.jar dat250.NewTask "Third task is successfully sent."
java -cp build/libs/assignment6_RabbitMQ-1.0-SNAPSHOT-all.jar dat250.NewTask "Fourth task has been executed."
java -cp build/libs/assignment6_RabbitMQ-1.0-SNAPSHOT-all.jar dat250.NewTask "Fifth task finished without errors."
```
## Conclusion

The integration of RabbitMQ into the Java application was successfully achieved, allowing for efficient messaging between the producer and consumer. Despite initial challenges with compilation and message processing, the implementation of robust error handling and connection configurations ensured smooth operation. The experiment demonstrated the effectiveness of RabbitMQ in managing distributed tasks and provided valuable insights into the messaging system's capabilities.

A screenshot demonstrating the successful sending and receiving of messages will be attached below:

![Screenshot - Sending and Receiving Messages](https://github.com/NachoAlcaldeT/DAT250/blob/main/Assignment6/Screenshots_Assignment6/experiment3_RabbitMQ.png)

