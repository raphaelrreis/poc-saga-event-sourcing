package com.example.syncarquitetura;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
@Entity
@DiscriminatorValue("3")
class VendaReprovadaEvent extends Event {

	public VendaReprovadaEvent(UUID correlationId) {
		super(correlationId);
	}
}
