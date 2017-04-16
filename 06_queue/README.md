# Workshop
Raspberry PI Workshop


Run RabbitMQ
```sh
sudo docker run -d --hostname my-rabbit --name rabbit-server -p 15672:15672 -p 15674:15674 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3-management
```
```sh
sudo docker run -d --hostname my-rabbit --name rabbit-server -p 15672:15672 -p 15674:15674 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:stomp
```

Rabbitmq manager
http://192.168.0.25:15672/

