/**
 * 
 */
package com.visiongc.app.strategos.web.struts.indicadores.forms;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;

/**
 * @author Kerwin
 *
 */
public class ImportarSeleccionForm extends SelectorListaForm
{
	static final long serialVersionUID = 0L;

	private Long organizacionId;
	private String rutaCompletaOrganizacion;
	private String nombre;

	public String getRutaCompletaOrganizacion() 
	{
		return this.rutaCompletaOrganizacion;
	}

	public void setRutaCompletaOrganizacion(String rutaCompletaOrganizacion) 
	{
		this.rutaCompletaOrganizacion = rutaCompletaOrganizacion;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	  
	public Long getOrganizacionId() 
	{
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) 
	{
		this.organizacionId = organizacionId;
	}
	  
	public void clear() 
	{
		setNombreForma(null);
		setNombreCampoValor(null);
		setNombreCampoOculto(null);
		setSeleccionados(null);
		setValoresSeleccionados(null);
		  
		this.rutaCompletaOrganizacion = null;
		this.organizacionId = 0L;
	}
}