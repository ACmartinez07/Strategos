package com.visiongc.app.strategos.instrumentos.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IndicadorInstrumentoPK implements Serializable {

	static final long serialVersionUID = 0L;
	private Long instrumentoId;
	private Long indicadorId;

	public IndicadorInstrumentoPK(Long instrumentoId, Long indicadorId) {
		this.instrumentoId = instrumentoId;
		this.indicadorId = indicadorId;
	}

	public IndicadorInstrumentoPK() {
	}

	public Long getInstrumentoId() {
		return instrumentoId;
	}

	public void setInstrumentoId(Long instrumentoId) {
		this.instrumentoId = instrumentoId;
	}

	public Long getIndicadorId() {
		return indicadorId;
	}

	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}
	
	public String toString() {
		return new ToStringBuilder(this).append("instrumentoId", getInstrumentoId()).append("indicadorId", getIndicadorId()).toString();
	}
	
	public boolean equals(Object other) {
		if(this == other)
			return true;
		if(!(other instanceof IndicadorInstrumentoPK))
			return false;
		IndicadorInstrumentoPK castOther = (IndicadorInstrumentoPK)other;
		return new EqualsBuilder().append(getInstrumentoId(), castOther.getInstrumentoId()).append(getIndicadorId(), castOther.getIndicadorId()).isEquals();
	}
	
	public int hasCode() {
		return new HashCodeBuilder().append(getInstrumentoId()).append(getIndicadorId()).toHashCode();
	}
}
