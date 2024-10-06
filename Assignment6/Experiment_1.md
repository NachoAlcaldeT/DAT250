# Experiment 1: Installation of RabbitMQ

In this experiment, we focused on installing RabbitMQ, a robust messaging broker that facilitates communication between applications. The installation was executed following the guidelines provided on the official RabbitMQ website. We opted for the Docker installation method, which is suitable for experimenting with RabbitMQ on a workstation.

To install RabbitMQ, we used the following command to run the latest RabbitMQ 4.0.x community Docker image:

```bash
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management
```
This command sets up RabbitMQ on the default ports, with the management plugin enabled, allowing us to access the management interface.

## Issues Encountered
During the installation process, we encountered no problems. The RabbitMQ server started successfully, and we were able to access the management interface without any issues.

## Accessing the Management Interface
After running the above command, RabbitMQ was accessible through the management interface at the URL http://localhost:15672. The default username and password are both set to guest.

![Screenshot - RabbitMQ Management Interface](https://github.com/NachoAlcaldeT/DAT250/blob/main/Assignment6/Screenshots_Assignment6/experiment1_RabbitMQ.png)

This interface provides an overview of the server status, including queues, exchanges, and other metrics.

## Conclusion
The installation of RabbitMQ was straightforward, with no significant issues. The management interface was accessible, allowing for easy monitoring and management of the RabbitMQ server. This sets a solid foundation for future experiments with RabbitMQ.
