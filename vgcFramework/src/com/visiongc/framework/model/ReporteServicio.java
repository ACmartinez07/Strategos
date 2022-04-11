package com.visiongc.framework.model;

import java.io.Serializable;

public class ReporteServicio implements Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long reporteId;
	
	private Long responsableId;
   
    private String medicion;
    
    private Long indicadorId;

    
	public Long getResponsableId() {
		return responsableId;
	}

	public void setResponsableId(Long responsableId) {
		this.responsableId = responsableId;
	}
	
	public String getMedicion() {
		return medicion;
	}

	public void setMedicion(String medicion) {
		this.medicion = medicion;
	}

	public Long getIndicadorId() {
		return indicadorId;
	}

	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}
	
	public Long getReporteId() {
		return reporteId;
	}

	public void setReporteId(Long reporteId) {
		this.reporteId = reporteId;
	}

	public ReporteServicio() {}
}
