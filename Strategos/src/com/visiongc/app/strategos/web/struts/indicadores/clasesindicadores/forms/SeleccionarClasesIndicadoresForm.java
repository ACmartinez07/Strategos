package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.forms;

import com.visiongc.framework.web.struts.forms.SelectorArbolForm;

public class SeleccionarClasesIndicadoresForm extends SelectorArbolForm
{
	static final long serialVersionUID = 0L;

	private String rutaCompletaOrganizacion;
	private String rutaCompletaClaseIndicadores;
	private Boolean permitirCambiarClase;
	private Boolean permitirCambiarOrganizacion;
	private String panelClases;
	private String panelSeleccionado;
	private Boolean iniciado;
	private Long organizacionSeleccionadaId;
	private Long claseSeleccionadaId;

	public String getRutaCompletaClaseIndicadores() 
	{
    	return this.rutaCompletaClaseIndicadores;
	}

	public void setRutaCompletaClaseIndicadores(String rutaCompletaClaseIndicadores) 
	{
		this.rutaCompletaClaseIndicadores = rutaCompletaClaseIndicadores;
	}

	public String getRutaCompletaOrganizacion() 
	{
		return this.rutaCompletaOrganizacion;
	}

	public void setRutaCompletaOrganizacion(String rutaCompletaOrganizacion) 
	{
		this.rutaCompletaOrganizacion = rutaCompletaOrganizacion;
	}
	
	public Boolean getPermitirCambiarOrganizacion() 
	{
		return this.permitirCambiarOrganizacion;
	}

	public void setPermitirCambiarOrganizacion(Boolean permitirCambiarOrganizacion) 
	{
		this.permitirCambiarOrganizacion = permitirCambiarOrganizacion;
	}

	public Boolean getPermitirCambiarClase() 
	{
		return this.permitirCambiarClase;
	}

	public void setPermitirCambiarClase(Boolean permitirCambiarClase) 
	{
		this.permitirCambiarClase = permitirCambiarClase;
	}
	
	public String getPanelClases() 
	{
		return this.panelClases;
	}

	public void setPanelClases(String panelClases) 
	{
		this.panelClases = panelClases;
	}
	
	public String getPanelSeleccionado() 
	{
		return this.panelSeleccionado;
	}

	public void setPanelSeleccionado(String panelSeleccionado) 
	{
		this.panelSeleccionado = panelSeleccionado;
	}
	
	public Long getClaseSeleccionadaId()
	{
		return this.claseSeleccionadaId;
	}

	public void setClaseSeleccionadaId(Long claseSeleccionadaId) 
	{
		this.claseSeleccionadaId = claseSeleccionadaId;
	}

	public Long getOrganizacionSeleccionadaId() 
	{
	    return this.organizacionSeleccionadaId;
	}

	public void setOrganizacionSeleccionadaId(Long organizacionSeleccionadaId) 
	{
	    this.organizacionSeleccionadaId = organizacionSeleccionadaId;
	}
	
	public Boolean getIniciado() 
	{
		return this.iniciado;
	}

	public void setIniciado(Boolean iniciado) 
	{
		this.iniciado = iniciado;
	}
}