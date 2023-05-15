Contém implementações dos seguintes padrões de microserviços:

- Circuit Breaker
- Event Sourcing
- Idempotent Consumer
- Saga Pattern
- Transactional Outbox
- Distributed Tracing (citado inclusive em [Spring Sleuth para Propagação de Headers])

Um diagrama de sequência do fluxo principal encontra-se em ``ms.drawio``, disponível na raiz deste projeto. Ele pode ser aberto acessando o site do [Draw.io](https://draw.io/)

Para rodar a solução, siga os passos abaixo:

- se você estiver usando o windows, é importante utilizar um terminal que forneça suporte a scripts ``sh``, como o [MobaXterm](https://mobaxterm.mobatek.net/) por exemplo.
- acessar a pasta ``mensagem-customizada``
- rodar o ``dockerize.sh`` 
- acessar a pasta ``docker``
- rodar o ``infra-up.sh`` (caso você esteja usando windows, rode o ``infra-up.bat``. Tentei rodar o script ``sh`` no windows e não rolou, mas quem sabe você consiga :D)
- assim que todos os containeres subirem, acessar a pasta ``mensagem-customizada`` novamente e rodar o script ``criar-conector.sh``
- na IDE, acessar cada uma das aplicações cujos nomes começem com ``ms-`` e rodar cada uma delas. A ordem que elas subirem não é importante.
- acessar o postman e carregar o projeto ``poc-saga-event-sourcing.postman_collection.json``
- rodar a chamada "início da saga". É possível acompanhar o andamento do processo pelos logs das aplicações, pelo banco de dados ou mesmo pelos tópicos do kafka. 
