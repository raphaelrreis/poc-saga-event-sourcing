package br.com.transformadorCustomizado;

import java.io.IOException;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.header.Headers;
import org.apache.kafka.connect.transforms.Transformation;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransformadorCustomizado<R extends ConnectRecord<R>> implements Transformation<R> {

	@Override
	public R apply(R registro) {
		Struct struct = (Struct)registro.value();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("VALOR DA STRUCT: " + struct.toString());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		String operacao = struct.getString("op");
		
		if(OperacoesMongoDB.de(operacao).insercao()){
			String encodedAfter = struct.getString("after");
			JsonNode after = null;
			try {
				after = new ObjectMapper().readTree(encodedAfter);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String uuidBase64 = after.get("correlationId").get("$binary").asText();			
			String correlationId = ConversorUUID.uuidBase64ParaUUIDString(uuidBase64);
			String valor = after.get("valor").textValue();	
			Headers cabecalhos = LeitorDeSpanDeCabecalhos
					.obterCabecalhos(after.at(JsonPointer.valueOf("/cabecalhosDaRequisicao")));
			
			Struct novoStruct = new Struct(SchemaBuilder.struct()
					.field("correlation_id", Schema.STRING_SCHEMA)
					.field("valor", Schema.STRING_SCHEMA)
					.build())
				.put("correlation_id", correlationId)
				.put("valor", valor);
			
			registro = registro.newRecord(
					"pedidos", registro.kafkaPartition(), 
					null, correlationId,
					novoStruct.schema(), novoStruct, 
					registro.timestamp(),
					cabecalhos);
			
		}
		
		return registro;
	}

	@Override
	public ConfigDef config() {
		return new ConfigDef();
	}

	@Override
	public void close() {
	}
	
	@Override
	public void configure(Map<String, ?> configs) {
	}
}
