/**
 * 
 */
package com.visiongc.app.strategos.web.struts.planes.modelos.forms;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;

/**
 * @author Kerwin
 *
 */
public class GestionarModelosForm extends SelectorListaForm 
{
	static final long serialVersionUID = 0;

	private String respuesta = "";
	private String rutaCompletaOrganizacion;
	private String filtroNombre;
	private Long planId;

	public String getFiltroNombre() 
	{
		return filtroNombre;
	}

	public void setFiltroNombre(String filtroNombre) 
	{
		this.filtroNombre = filtroNombre;
	}

	public String getRespuesta() 
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}
	
	public Long getPlanId() 
	{
		return this.planId;
	}

	public void setPlanId(Long planId) 
	{
		this.planId = planId;
	}

	public String getRutaCompletaOrganizacion() 
	{
		return this.rutaCompletaOrganizacion;
	}

	public void setRutaCompletaOrganizacion(String rutaCompletaOrganizacion) 
	{
		this.rutaCompletaOrganizacion = rutaCompletaOrganizacion;
	}
	
	public void clear() 
	{
		setNombreForma(null);
		setNombreCampoValor(null);
		setNombreCampoOculto(null);
		setSeleccionados(null);
		setValoresSeleccionados(null);
		  
		this.rutaCompletaOrganizacion = null;
		this.respuesta = null;
		this.filtroNombre = null;
		this.planId = null;
	}
}
