package com.visiongc.app.strategos.instrumentos.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.visiongc.app.strategos.iniciativas.model.MemoIniciativa;

public class InstrumentoPeso implements Serializable {
	
	static final long serialVersionUID = 0L; 
	
	private Long instrumentoId;
	private String anio;
	private Double peso;
	private Instrumentos instrumento;

	public InstrumentoPeso(Long instrumentoId, String anio, Double peso) {
		this.instrumentoId = instrumentoId;
		this.anio = anio;
		this.peso = peso;
	}
	
	public InstrumentoPeso() {}

	public Long getInstrumentoId() {
		return instrumentoId;
	}
	
	public void setInstrumentoId(Long instrumentoId) {
		this.instrumentoId = instrumentoId;
	}
	
	public String getAnio() {
		return anio;
	}
	
	public void setAnio(String anio) {
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
	    InstrumentoPeso castOther = (InstrumentoPeso)other;
	    return new EqualsBuilder().append(getInstrumento(), 
	      castOther.getInstrumento()).isEquals();
	  }
	  
	  public int hashCode() {
	    return new HashCodeBuilder().append(getInstrumento()).toHashCode();
	  }
}
