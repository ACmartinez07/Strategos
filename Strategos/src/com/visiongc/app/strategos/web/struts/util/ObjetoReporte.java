/**
 * 
 */
package com.visiongc.app.strategos.web.struts.util;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class ObjetoReporte extends EditarObjetoForm
{
	private static final long serialVersionUID = 1L;
	private Byte tipo;
	private String texto;
	private ObjetoTabla tabla;
	private String id;
	
	public Byte getTipo() 
	{
		return this.tipo;
	}

	public void setTipo(Byte tipo) 
	{
		this.tipo = tipo;
	}

	public String getTexto() 
	{
		return this.texto;
	}

	public void setTexto(String texto) 
	{
		this.texto = texto;
	}
	
	public ObjetoTabla getTabla() 
	{
		return this.tabla;
	}

	public void setTabla(ObjetoTabla tabla) 
	{
		this.tabla = tabla;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}
	
  	public void clear()
  	{
  		this.tipo = null;
  		this.texto = null;
  		this.tabla = null;
  		this.id = null;
  	}
}