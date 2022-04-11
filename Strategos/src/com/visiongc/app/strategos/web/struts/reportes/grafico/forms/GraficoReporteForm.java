package com.visiongc.app.strategos.web.struts.reportes.grafico.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class GraficoReporteForm extends EditarObjetoForm{

	static final long serialVersionUID = 0L;

	private Long reporteId;
	private String indicadoresId;
	private String series;
	private String titulo;
	private Integer mesInicial;
	private Integer mesFinal;
	private Integer periodoInicial;
	private Integer periodoFinal;
	
	
	public Long getReporteId() {
		return reporteId;
	}

	public void setReporteId(Long reporteId) {
		this.reporteId = reporteId;
	}

	public String getIndicadoresId() {
		return indicadoresId;
	}

	public void setIndicadoresId(String indicadoresId) {
		this.indicadoresId = indicadoresId;
	}
	
	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getMesInicial() {
		return mesInicial;
	}

	public void setMesInicial(Integer mesInicial) {
		this.mesInicial = mesInicial;
	}

	public Integer getMesFinal() {
		return mesFinal;
	}

	public void setMesFinal(Integer mesFinal) {
		this.mesFinal = mesFinal;
	}

	public Integer getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(Integer periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public Integer getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(Integer periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	public void clear() {
		this.reporteId = null;
		this.indicadoresId = "";	
		this.series = "";
		this.titulo = "";
		this.periodoInicial = 0;
		this.periodoFinal = 0;
		this.mesInicial =0;
		this.mesFinal = 0;
	}
	
	
	
}
