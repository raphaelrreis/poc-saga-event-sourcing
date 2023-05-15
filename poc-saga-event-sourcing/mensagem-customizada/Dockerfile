FROM debezium/connect:1.9.3.Final
ENV DEBEZIUM_DIR=$KAFKA_CONNECT_PLUGINS_DIR/debezium-transformer

RUN mkdir $DEBEZIUM_DIR
COPY target/*.jar $DEBEZIUM_DIR