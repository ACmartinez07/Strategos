package com.visiongc.app.strategos.web.struts.reportes.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class ReporteComiteEjecutivoForm extends EditarObjetoForm {

	static final long serialVersionUID = 0L;

	/** nullable persistent field */
	private String organizaciones;
	
	private String fecha;
	
	private String indicadores;
	
	private String clases;
	
	private Integer totalOrganizacionesAsociadas;
	
	private Integer vista;

	public final static String SEPARADOR_CAMPOS = ":";

	public final static String SEPARADOR_FILAS = ";";
	
	public String getOrganizaciones() {
		return organizaciones;
	}

	public void setOrganizaciones(String organizaciones) {
		this.organizaciones = organizaciones;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(String indicadores) {
		this.indicadores = indicadores;
	}

	public Integer getTotalOrganizacionesAsociadas() {
		return totalOrganizacionesAsociadas;
	}

	public void setTotalOrganizacionesAsociadas(
			Integer totalOrganizacionesAsociadas) {
		this.totalOrganizacionesAsociadas = totalOrganizacionesAsociadas;
	}
	
	public String getSeparadorCampos() {
		return SEPARADOR_CAMPOS;
	}
	
	public String getSeparadorFilas() {
		return SEPARADOR_FILAS;
	}
	
	public void clear() 
	{
		this.organizaciones = null;
		this.fecha = null;
		this.indicadores = null;
		this.clases = null;
	}

	public Integer getVista() {
		return vista;
	}

	public void setVista(Integer vista) {
		this.vista = vista;
	}

	public String getClases() {
		return clases;
	}

	public void setClases(String clases) {
		this.clases = clases;
	}

}
