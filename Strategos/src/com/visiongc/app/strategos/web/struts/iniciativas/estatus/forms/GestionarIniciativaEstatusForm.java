/**
 * 
 */
package com.visiongc.app.strategos.web.struts.iniciativas.estatus.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;

/**
 * @author Kerwin
 *
 */
public class GestionarIniciativaEstatusForm  extends VisorListaForm
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