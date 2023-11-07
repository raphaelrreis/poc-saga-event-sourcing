### Contém implementações dos seguintes padrões de microserviços:

- Circuit Breaker
- Event Sourcing
- Idempotent Consumer
- Saga Pattern
- Transactional Outbox
- Distributed Tracing (citado inclusive em [Spring Sleuth para Propagação de Headers])

### Instruções de Uso para rodar a solução, siga os passos abaixo:

- Certifique-se de ter o Docker e o Docker Compose instalados e configurados na sua máquina.
- Se você estiver usando o windows, é importante utilizar um terminal que forneça suporte a scripts ``sh``, como o [MobaXterm](https://mobaxterm.mobatek.net/) por exemplo.
- Acessar a pasta ``mensagem-customizada``
- Rodar o ``dockerize.sh`` 
- Acessar a pasta ``docker``
- Rodar o ``infra-up.sh`` (caso você esteja usando windows, rode o ``infra-up.bat``. Tentei rodar o script ``sh`` no windows e não rolou, mas quem sabe você consiga :D)
- Assim que todos os containeres subirem, acessar a pasta ``mensagem-customizada`` novamente e rodar o script ``criar-conector.sh``
- Na IDE, acessar cada uma das aplicações cujos nomes começem com ``ms-`` e rodar cada uma delas. A ordem que elas subirem não é importante.
- Acessar o postman e carregar o projeto ``poc-saga-event-sourcing.postman_collection.json``
- Rodar a chamada "início da saga". É possível acompanhar o andamento do processo pelos logs das aplicações, pelo banco de dados ou mesmo pelos tópicos do kafka. 

### Conclusão

- Este projeto é uma representação da minha dedicação em criar uma infraestrutura confiável e escalável.
- Ele reflete meu conhecimento prático em arquiteturas de sistemas e meu compromisso com as melhores práticas de desenvolvimento.

### Um diagrama de sequência do fluxo principal encontra-se em ``ms.drawio``, disponível na raiz deste projeto. Ele pode ser aberto acessando o site do [Draw.io](https://draw.io/)
