package br.com.transformadorCustomizado;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ConversorUUIDTest {

	@Test
	void converterBase64ParaUUID() {
		String uuidBase64 = "ckvoKai369I9WQfU+lLprA==";
		String uuidEsperado = "724be829-a8b7-ebd2-3d59-07d4fa52e9ac";
		String uuid = ConversorUUID.uuidBase64ParaUUIDString(uuidBase64);
		
		assertThat(uuid).isEqualTo(uuidEsperado);
	}
}
