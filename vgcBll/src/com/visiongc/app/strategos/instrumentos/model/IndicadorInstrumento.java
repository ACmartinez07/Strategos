package com.visiongc.app.strategos.instrumentos.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.model.IndicadorIniciativa;

public class IndicadorInstrumento implements Serializable {

	static final long serialVersionUID = 0L;	
	private IndicadorInstrumentoPK pk;
	private Byte tipo;
	private Instrumentos instrumento;
	private Indicador indicador;

	public IndicadorInstrumento(IndicadorInstrumentoPK pk, Byte tipo) {
		this.pk = pk;
		this.tipo = tipo;
	}

	public IndicadorInstrumento() {
	}

	public IndicadorInstrumentoPK getPk() {
		return pk;
	}

	public void setPk(IndicadorInstrumentoPK pk) {
		this.pk = pk;
	}

	public Byte getTipo() {
		return tipo;
	}

	public void setTipo(Byte tipo) {
		this.tipo = tipo;
	}

	public Instrumentos getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumentos instrumento) {
		this.instrumento = instrumento;
	}

	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public String toString() {
		return new ToStringBuilder(this).append("pk", getPk()).toString();
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof IndicadorInstrumento))
			return false;
		IndicadorInstrumento castOther = (IndicadorInstrumento) other;
		return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getPk()).toHashCode();
	}

}
