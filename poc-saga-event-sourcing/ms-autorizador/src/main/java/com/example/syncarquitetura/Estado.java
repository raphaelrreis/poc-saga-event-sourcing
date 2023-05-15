package com.example.syncarquitetura;

import java.util.UUID;

public enum Estado {

    PENDENTE {
		@Override
		public VendaEvent toEvent(UUID correlationId) {
			// TODO Auto-generated method stub
			return null;
		}
	},
    APROVADA {
		@Override
		public VendaEvent toEvent(UUID correlationId) {
			return new VendaAprovadaEvent(correlationId);
		}
	},
    REPROVADA {
		@Override
		public VendaEvent toEvent(UUID correlationId) {
			return new VendaReprovadaEvent(correlationId);
		}
	};
	
	public abstract VendaEvent toEvent(UUID correlationId);
}
