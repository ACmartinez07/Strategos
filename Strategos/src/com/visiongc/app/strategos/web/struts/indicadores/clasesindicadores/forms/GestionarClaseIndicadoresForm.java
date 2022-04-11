/**
 * 
 */
package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.forms;

import com.visiongc.framework.web.struts.forms.VisorArbolForm;

/**
 * @author Kerwin
 *
 */
public class GestionarClaseIndicadoresForm extends VisorArbolForm
{
	static final long serialVersionUID = 0L;
	
	private String respuesta;
	
	public String getRespuesta() 
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}
}
