package com.visiongc.app.strategos.web.struts.estadosacciones.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarEstadosAccionesForm extends VisorListaForm
{
	static final long serialVersionUID = 0L;
	
	private String filtroNombre;
	private Boolean ordenPag;

	public String getFiltroNombre()
	{
		return this.filtroNombre;
	}

	public void setFiltroNombre(String filtroNombre) 
	{
		this.filtroNombre = filtroNombre;
	}

	public Boolean getOrdenPag() 
	{
		return this.ordenPag;
	}

	public void setOrdenPag(Boolean ordenPag) 
	{
		this.ordenPag = ordenPag;
	}
}