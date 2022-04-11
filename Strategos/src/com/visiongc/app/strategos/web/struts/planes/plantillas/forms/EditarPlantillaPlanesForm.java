package com.visiongc.app.strategos.web.struts.planes.plantillas.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarPlantillaPlanesForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	private Long plantillaPlanesId;
	private String nombre;
	private String descripcion;
	private String nombreIndicadorSingular;
	private String nombreIniciativaSingular;
	private String nombreActividadSingular;
  
	private String textoNiveles;
	public static final String SEPARADOR_PERSPECTIVAS = "!@!";
	public static final String SEPARADOR_ATRIBUTOS_PERSPECTIVAS = "!-!";

	public Long getPlantillaPlanesId()
	{
		return this.plantillaPlanesId;
	}

	public void setPlantillaPlanesId(Long plantillaPlanesId) 
	{
		this.plantillaPlanesId = plantillaPlanesId;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getDescripcion() 
	{
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}

	public String getNombreActividadSingular() 
	{
		return this.nombreActividadSingular;
	}

	public void setNombreActividadSingular(String nombreActividadSingular) 
	{
		this.nombreActividadSingular = nombreActividadSingular;
	}

	public String getNombreIndicadorSingular() 
	{
		return this.nombreIndicadorSingular;
	}

	public void setNombreIndicadorSingular(String nombreIndicadorSingular) 
	{
		this.nombreIndicadorSingular = nombreIndicadorSingular;
	}

	public String getNombreIniciativaSingular() 
	{
		return this.nombreIniciativaSingular;
	}

	public void setNombreIniciativaSingular(String nombreIniciativaSingular) 
	{
		this.nombreIniciativaSingular = nombreIniciativaSingular;
	}

	public String getTextoNiveles() 
	{
		return this.textoNiveles;
	}

	public void setTextoNiveles(String textoNiveles) 
	{
		this.textoNiveles = textoNiveles;
	}

	public String getSeparadorPerspectivas() 
	{
		return "!@!";
	}

	public String getSeparadorAtributosPerspectivas() 
	{
		return "!-!";
	}

	public void clear() 
	{
		this.plantillaPlanesId = new Long(0L);
		this.nombre = null;
		this.descripcion = null;
		this.nombreIndicadorSingular = null;
		this.nombreIniciativaSingular = null;
		this.nombreActividadSingular = null;
		this.textoNiveles = null;
		setBloqueado(new Boolean(false));
	}
}