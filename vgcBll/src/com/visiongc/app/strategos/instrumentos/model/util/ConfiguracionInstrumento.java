package com.visiongc.app.strategos.instrumentos.model.util;

public class ConfiguracionInstrumento {

	private String instrumentoNombre;

	private String instrumentoIndicadorAvanceNombre;

	private Boolean instrumentoIndicadorAvanceMostrar;

	private Boolean instrumentoIndicadorAvanceAnteponer;

	public ConfiguracionInstrumento() {

	}

	public String getInstrumentoNombre() {
		return instrumentoNombre;
	}

	public void setInstrumentoNombre(String instrumentoNombre) {
		this.instrumentoNombre = instrumentoNombre;
	}

	public String getInstrumentoIndicadorAvanceNombre() {
		return instrumentoIndicadorAvanceNombre;
	}

	public void setInstrumentoIndicadorAvanceNombre(String instrumentoIndicadorAvanceNombre) {
		this.instrumentoIndicadorAvanceNombre = instrumentoIndicadorAvanceNombre;
	}

	public Boolean getInstrumentoIndicadorAvanceMostrar() {
		return instrumentoIndicadorAvanceMostrar;
	}

	public void setInstrumentoIndicadorAvanceMostrar(Boolean instrumentoIndicadorAvanceMostrar) {
		this.instrumentoIndicadorAvanceMostrar = instrumentoIndicadorAvanceMostrar;
	}

	public Boolean getInstrumentoIndicadorAvanceAnteponer() {
		return instrumentoIndicadorAvanceAnteponer;
	}

	public void setInstrumentoIndicadorAvanceAnteponer(Boolean instrumentoIndicadorAvanceAnteponer) {
		this.instrumentoIndicadorAvanceAnteponer = instrumentoIndicadorAvanceAnteponer;
	}
}
