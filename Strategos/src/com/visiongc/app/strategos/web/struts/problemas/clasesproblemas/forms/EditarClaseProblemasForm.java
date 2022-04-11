package com.visiongc.app.strategos.web.struts.problemas.clasesproblemas.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarClaseProblemasForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
  
	private Long claseId;
	private Long organizacionId;
	private Long padreId;
	private String nombrePadre;
	private String nombre;
	private String descripcion;
	private String fechaCreado;
	private String fechaModificado;
	private String nombreUsuarioCreado;
	private String nombreUsuarioModificado;
	private Long numeroProblemas;
	private Integer numeroHijos;
	private Boolean copiarProblemas;
	private Integer tipo;

	public Long getClaseId()
	{
		return this.claseId;
	}

	public void setClaseId(Long claseId) 
	{
		this.claseId = claseId;
	}

	public Long getOrganizacionId() 
	{
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) 
	{
		this.organizacionId = organizacionId;
	}

	public Long getPadreId() 
	{
		return this.padreId;
	}

	public void setPadreId(Long padreId) 
	{
		this.padreId = padreId;
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

	public String getFechaCreado() 
	{
		return this.fechaCreado;
	}

	public void setFechaCreado(String fechaCreado) 
	{
		this.fechaCreado = fechaCreado;
	}

	public String getFechaModificado() 
	{
		return this.fechaModificado;
	}

	public void setFechaModificado(String fechaModificado) 
	{
		this.fechaModificado = fechaModificado;
	}

	public String getNombreUsuarioCreado() 
	{
			return this.nombreUsuarioCreado;
	}

	public void setNombreUsuarioCreado(String nombreUsuarioCreado) 
	{
		this.nombreUsuarioCreado = nombreUsuarioCreado;
	}

	public String getNombreUsuarioModificado() 
	{
		return this.nombreUsuarioModificado;
	}

	public void setNombreUsuarioModificado(String nombreUsuarioModificado) 
	{
		this.nombreUsuarioModificado = nombreUsuarioModificado;
	}

	public Long getNumeroProblemas() 
	{
		return this.numeroProblemas;
	}

	public void setNumeroProblemas(Long numeroProblemas) 
	{
		this.numeroProblemas = numeroProblemas;
	}

	public Integer getNumeroHijos() 
	{
		return this.numeroHijos;
	}

	public void setNumeroHijos(Integer numeroHijos) 
	{
		this.numeroHijos = numeroHijos;
	}

	public String getNombrePadre() 
	{
		return this.nombrePadre;
	}

	public void setNombrePadre(String nombrePadre) 
	{
		this.nombrePadre = nombrePadre;
	}

	public Boolean getCopiarProblemas() 
	{
		return this.copiarProblemas;
	}

	public void setCopiarProblemas(Boolean copiarProblemas) 
	{
		this.copiarProblemas = copiarProblemas;
	}

	public Integer getTipo()
	{
		return this.tipo;
	}

	public void setTipo(Integer tipo) 
	{
		this.tipo = tipo;
	}
  
	public void clear() 
	{
		this.claseId = new Long(0L);
		this.padreId = null;
		this.organizacionId = null;
		this.nombre = null;
		this.descripcion = null;
		this.fechaCreado = null;
		this.fechaModificado = null;
		this.numeroProblemas = new Long(0L);
		this.nombreUsuarioCreado = null;
		this.nombreUsuarioModificado = null;
		this.numeroHijos = null;
		this.nombrePadre = null;
		this.copiarProblemas = new Boolean(false);
    
		setBloqueado(new Boolean(false));
	}
}