docker exec -it kafka.dev kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic pedidos
docker exec -it kafka.dev kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic validacoes
docker exec -it kafka.dev kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic vendas
docker exec -it kafka.dev kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic vendas_reprovadas
docker exec -it kafka.dev kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic consumidores
docker exec -it kafka.dev kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic comandas
docker exec -it kafka.dev kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic zipkin