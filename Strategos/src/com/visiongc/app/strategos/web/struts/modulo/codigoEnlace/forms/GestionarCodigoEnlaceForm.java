/**
 * 
 */
package com.visiongc.app.strategos.web.struts.modulo.codigoEnlace.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;

/**
 * @author Kerwin
 *
 */
public class GestionarCodigoEnlaceForm extends VisorListaForm
{
	static final long serialVersionUID = 0L;
	
	private String filtroNombre;

	public String getFiltroNombre()
	{
		return this.filtroNombre;
	}

	public void setFiltroNombre(String filtroNombre) 
	{
		this.filtroNombre = filtroNombre;
	}
}