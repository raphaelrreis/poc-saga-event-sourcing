FROM debezium/connect:2.3.0.Final
ENV DEBEZIUM_DIR=$KAFKA_CONNECT_PLUGINS_DIR/debezium-transformer

RUN mkdir $DEBEZIUM_DIR
COPY target/*.jar $DEBEZIUM_DIR
