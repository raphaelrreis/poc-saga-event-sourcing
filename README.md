# poc-saga-event-sourcing
Poc saga event sourcing

Contains implementations of the following microservices patterns:

- Circuit Breaker
- Event Sourcing
- Idempotent Consumer
- Saga Pattern
- Transactional Outbox
- Distributed Tracing (also mentioned in [Spring Sleuth for Header Propagation])

A sequence diagram of the main flow can be found in ``ms.drawio``, available at the root of this project. It can be opened by accessing the [Draw.io](https://draw.io/) website.

To run the solution, follow the steps below:

- if you are using Windows, it is important to use a terminal that supports ``sh`` scripts, such as [MobaXterm](https://mobaxterm.mobatek.net/), for example.
- access the ``mensagem-customizada`` folder
- run the ``dockerize.sh`` script
- access the ``docker`` folder
- run the ``infra-up.sh`` script (if you are using Windows, run the ``infra-up.bat`` script. I tried running the ``sh`` script on Windows and it didn't work, but who knows, maybe you can :D)
- once all the containers are up, access the ``mensagem-customizada`` folder again and run the ``criar-conector.sh`` script
- in the IDE, access each of the applications whose names start with ``ms-`` and run each one of them. The order in which they are started is not important.
- access Postman and load the project ``poc-saga-event-sourcing.postman_collection.json``
- run the "start of saga" call. You can follow the progress of the process through the application logs, the database, or even the Kafka topics.
