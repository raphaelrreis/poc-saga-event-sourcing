package br.com.transformadorCustomizado;

public class TransformadorCustomizadoTest {
	
//	@Test
//	void converter() {
//        final SourceRecord expected = logDoBanco();
//        final String afterJson = ((Struct) expected.value()).getString(AFTER_VALUE);
//
//        final String correlationId = readMongoDBUUIDValue(afterJson, format("/%s/%s", CORRELATION_ID_VALUE, MONGO_BINARY_VALUE));
//        final String createdAt = readSimpleValueOf(afterJson, format("/%s/%s", CREATED_AT_VALUE, MONGO_DATE_VALUE));
//
//        String outboxEvent = removeNode(afterJson, Constants.FIELD_MONGO_ID.getValue());
//        outboxEvent = removeNode(outboxEvent, Constants.FIELD_MONGO_CLASS.getValue());
//        outboxEvent = removeNode(outboxEvent, CORRELATION_ID_VALUE);
//        outboxEvent = removeNode(outboxEvent, CREATED_AT_VALUE);
//
//        outboxEvent = addStringValueOf(outboxEvent, CORRELATION_ID_VALUE, correlationId);
//        outboxEvent = addStringValueOf(outboxEvent, CREATED_AT_VALUE, new Date(Long.parseLong(createdAt)).toInstant().toString());
//        final OutboxEvent event = jsonToObject(outboxEvent, OutboxEvent.class);
//
//        final ConnectRecord actual = sentinela.apply(expected);
//        final Struct bodyStruct = ((Struct) actual.value()).getStruct(BODY_VALUE);
//
//        assertHeaders(actual, correlationId);
//	}
//
//	private SourceRecord logDoBanco() {
//        final Object mongoJson = "{\"_id\": {\"$binary\": \"XPoQVMPHQAG5znPBpLn1jg==\",\"$type\": \"04\"},\"correlation_id\": {\"$binary\": \"6uOI0r9bRY61NAWP9/FSyA==\",\"$type\": \"04\"},\"operation\": \"cashout\",\"status\": \"AGUARDANDO_PAGAMENTO\",\"micro_service\": \"withdraw\",\"payload\": \"{\\\"k\\\":\\\"Y\\\"}\",\"created_at\": {\"$date\": 1616545680636},\"_class\": \"com.picpay.conciliation.sdk.model.OutboxEntity\"}";
//
//        final Schema sourceSchema = sourceSchema(SourceReader.MONGODB_OUTBOX);
//        final Schema initialSchema = initialSchema(SchemaBuilder.struct().build(), STRING_SCHEMA, sourceSchema);
//        final Struct fullStruct = new Struct(initialSchema);
//
//        fullStruct.put("before", new Struct(SchemaBuilder.struct().build()));
//        fullStruct.put("after", mongoJson);
//        fullStruct.put("source", defaultSourceValues(new Struct(sourceSchema), SourceReader.MONGODB_OUTBOX));
//        fullStruct.put("op", "c");
//        fullStruct.put("ts_ms", 1614860217932L);
//        fullStruct.put("transaction", "");
//
//        return new SourceRecord(Map.of(), Map.of(), TOPIC_NAME_AGREED_BY_DEBEZIUM, 0, fullStruct.schema(), fullStruct);
//
//	}
}
