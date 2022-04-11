/**
 * 
 */
package com.visiongc.app.strategos.web.struts.general.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class EditarGeneralInformeForms extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	
	private Boolean hayInformen = false;
	private Boolean hayCualitativos = false;
	private Boolean hayEjecutivos = false;
	private Boolean hayEventos = false;
	private Boolean hayNoticias = false;
	private Boolean mostrarAlerta = false;
	private String alerta;
	private String descripcion;
	
	public Boolean getHayInformen()
	{
		return this.hayInformen;
	}

	public void setHayInformen(Boolean hayInformen) 
	{
		this.hayInformen = hayInformen;
	}

	public Boolean getHayCualitativos()
	{
		return this.hayCualitativos;
	}

	public void setHayCualitativos(Boolean hayCualitativos) 
	{
		this.hayCualitativos = hayCualitativos;
	}

	public Boolean getHayEjecutivos()
	{
		return this.hayEjecutivos;
	}

	public void setHayEjecutivos(Boolean hayEjecutivos) 
	{
		this.hayEjecutivos = hayEjecutivos;
	}

	public Boolean getHayEventos()
	{
		return this.hayEventos;
	}

	public void setHayEventos(Boolean hayEventos) 
	{
		this.hayEventos = hayEventos;
	}
	
	public Boolean getHayNoticias()
	{
		return this.hayNoticias;
	}

	public void setHayNoticias(Boolean hayNoticias) 
	{
		this.hayNoticias = hayNoticias;
	}
	
	public Boolean getMostrarAlerta()
	{
		return this.mostrarAlerta;
	}

	public void setMostrarAlerta(Boolean mostrarAlerta) 
	{
		this.mostrarAlerta = mostrarAlerta;
	}
	
	public String getAlerta()
	{
		return this.alerta;
	}

	public void setAlerta(String alerta) 
	{
		this.alerta = alerta;
	}

	public String getDescripcion()
	{
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}
	
	public void clear() 
	{
		this.hayInformen = false;
		this.hayCualitativos = false;
		this.hayEjecutivos = false;
		this.hayEventos = false;
		this.hayNoticias = false;
		this.mostrarAlerta = false;
		this.alerta = null;
		this.descripcion = null;
	}
}