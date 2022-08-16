package com.visiongc.app.strategos.instrumentos.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class InstrumentoPeso implements Serializable {
	static final long serialVersionUID = 0L;
	private Long instrumentoId;
	private Long anio;
	private Double peso;
	private Instrumentos instrumento;

	public InstrumentoPeso(Long instrumentoId, Long anio, Double peso) {
		this.setInstrumentoId(instrumentoId);
		this.setAnio(anio);
		this.setPeso(peso);
	}

	public InstrumentoPeso() {
	}

	public Long getInstrumentoId() {
		return instrumentoId;
	}

	public void setInstrumentoId(Long instrumentoId) {
		this.instrumentoId = instrumentoId;
	}

	public Long getAnio() {
		return anio;
	}

	public void setAnio(Long anio) {
		this.anio = anio;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Instrumentos getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumentos instrumento) {
		this.instrumento = instrumento;
	}

	public String toString() {
		return new ToStringBuilder(this).append("instrumentoId", getInstrumentoId()).toString();
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof InstrumentoPeso))
			return false;
		InstrumentoPeso castOther = (InstrumentoPeso) other;
		return new EqualsBuilder().append(getInstrumentoId(), castOther.getInstrumentoId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getInstrumentoId()).toHashCode();
	}
}
