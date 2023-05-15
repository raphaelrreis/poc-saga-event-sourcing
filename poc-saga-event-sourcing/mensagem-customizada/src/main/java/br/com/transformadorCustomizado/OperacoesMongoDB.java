package br.com.transformadorCustomizado;

import java.util.Arrays;

public enum OperacoesMongoDB implements OperacoesBancoDeDados {
	INSERCAO("c"){
		@Override
		public boolean insercao() {return true;}
	},
	OUTRAS("") {
		@Override
		public boolean insercao() {return false;}
	};
	
	private String sigla;
	
	private OperacoesMongoDB(String sigla) {
		this.sigla = sigla;
	}

	@Override
	public abstract boolean insercao();

	static OperacoesBancoDeDados de(String sigla) {
		return Arrays
				.stream(values())
				.filter(op -> op.sigla.equals(sigla))
				.findFirst()
				.orElse(OperacoesMongoDB.OUTRAS);
	}
}
