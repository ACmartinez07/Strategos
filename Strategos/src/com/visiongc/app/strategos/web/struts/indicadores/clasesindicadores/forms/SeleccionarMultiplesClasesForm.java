/**
 * 
 */
package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.forms;

import com.visiongc.framework.web.struts.forms.SelectorArbolForm;

/**
 * @author Kerwin
 *
 */
public class SeleccionarMultiplesClasesForm  extends SelectorArbolForm
{
	static final long serialVersionUID = 0L;

	private String rutaCompletaOrganizacion;
	private String rutaCompletaClaseIndicadores;
	private Boolean iniciado;
	private Long organizacionSeleccionadaId;
	private Long claseSeleccionadaId;
	private String panelSeleccionado;
	private String respuesta;

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
	
	public String getPanelSeleccionado() 
	{
		return this.panelSeleccionado;
	}

	public void setPanelSeleccionado(String panelSeleccionado) 
	{
		this.panelSeleccionado = panelSeleccionado;
	}

	public String getRespuesta() 
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}
	
	public String getSeparadorClases() 
	{
		return "!C!";
	}

	public String getSeparadorCampos() 
	{
		return "!@!";
	}
}