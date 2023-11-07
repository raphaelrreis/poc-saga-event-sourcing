## Visão Geral

Este projeto configura uma infraestrutura de microserviços robusta e altamente disponível utilizando Docker Compose. Ele inclui serviços essenciais como bancos de dados MongoDB e MySQL, sistema de mensagens Kafka e ferramentas de monitoramento como Zipkin. A configuração visa implementar padrões arquiteturais modernos, fornecendo um ambiente resiliente e escalável para desenvolvimento e implantação de aplicações.

## Descrição dos Serviços

### MongoDB

- Configurei o serviço MongoDB para rodar como um conjunto de réplicas, garantindo alta disponibilidade e tolerância a falhas. Especifiquei as credenciais de administração e montei um volume para um script de inicialização para configurar o banco de dados com esquemas e dados iniciais.

### MySQL

- O MySQL está configurado para sempre reiniciar, garantindo que o serviço será automaticamente restaurado em caso de falha do contêiner. Um arquivo de configuração personalizado é montado para adequar as configurações padrão do MySQL às necessidades específicas do projeto.

### Zookeeper

- O Zookeeper é configurado como parte do ecossistema Kafka para o gerenciamento do estado dentro do cluster do Kafka. 
- Ele é essencial para a operação do Kafka, pois gerencia líderes de tópicos e mantém o estado do cluster.

### Kafka

- Implantei o Kafka para atuar como a core do processamento de mensagens. 
- Ele está configurado para se comunicar com o Zookeeper e estabeleci várias configurações para otimizar o desempenho, como fatores de replicação e criação automática de tópicos.

### Debezium Connect

- Utilizei uma imagem personalizada para o Debezium Connect para capturar mudanças no banco de dados em tempo real e propagá-las via Kafka, permitindo uma arquitetura orientada a eventos.

### Kafdrop

- Integrei o Kafdrop para fornecer uma interface visual para o Kafka, facilitando a monitorização de tópicos e mensagens, o que é crucial para o debugging e monitoramento em tempo real.

### Zipkin

- Incluí o Zipkin como uma ferramenta de rastreamento distribuído para ajudar a monitorar e solucionar problemas de latência em arquiteturas de microserviços, o que é vital para manter a performance e a confiabilidade.

### Jaeger (Opcional)

- O Jaeger está comentado por padrão, mas pode ser ativado para fornecer mais uma opção de rastreamento distribuído, oferecendo insights sobre como os serviços estão interagindo.

## Rede

- Todos os serviços estão conectados à rede `bubble`, uma rede do tipo bridge que define um espaço de endereçamento isolado para a comunicação interna.

## Instruções de Uso
- Para executar esses serviços: 
- 1 - Certifique-se de ter o Docker e o Docker Compose instalados e configurados na sua máquina. 
- 2 - Navegue até a pasta do projeto e execute `docker-compose up`. 
- 3 - Para iniciar todos os serviços configurados no arquivo `docker-compose.yml`.

## Conclusão

- Este arquivo `docker-compose.yml` é uma representação da minha dedicação em criar uma infraestrutura confiável e escalável. 
- Ele reflete meu conhecimento prático em arquiteturas de sistemas e meu compromisso com as melhores práticas de desenvolvimento.
