package com.visiongc.app.strategos.web.struts.explicaciones.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarExplicacionesForm extends VisorListaForm
{
	static final long serialVersionUID = 0L;
  
	private String filtroTitulo;
	private String filtroAutor;
	private String nombreOrganizacion;
	private Long objetoId;
	private String tipoObjetoKey;
	private String nombreObjetoKey;
	private Integer tipo;
	private Boolean desdeInstrumento;

	public String getNombreOrganizacion()
	{
		return this.nombreOrganizacion;
	}

	public void setNombreOrganizacion(String nombreOrganizacion) 
	{
		this.nombreOrganizacion = nombreOrganizacion;
	}

	public String getTipoObjetoKey() 
	{
		return this.tipoObjetoKey;
	}

	public void setTipoObjetoKey(String tipoObjetoKey) 
	{
		this.tipoObjetoKey = tipoObjetoKey;
	}

	public String getNombreObjetoKey() 
	{
		return this.nombreObjetoKey;
	}

	public void setNombreObjetoKey(String nombreObjetoKey) 
	{
		this.nombreObjetoKey = nombreObjetoKey;
	}

	public String getFiltroTitulo() 
	{
		return this.filtroTitulo;
	}

	public void setFiltroTitulo(String filtroTitulo) 
	{
		this.filtroTitulo = filtroTitulo;
	}

	public String getFiltroAutor() 
	{
		return this.filtroAutor;
	}

	public void setFiltroAutor(String filtroAutor) 
	{
		this.filtroAutor = filtroAutor;
	}

	public Long getObjetoId() 
	{
		return this.objetoId;
	}

	public void setObjetoId(Long objetoId) 
	{
		this.objetoId = objetoId;
	}
  
	public Integer getTipo()
	{
		return this.tipo;
	}

	public void setTipo(Integer tipo) 
	{
		this.tipo = tipo;
	}

	public Boolean getDesdeInstrumento() {
		return desdeInstrumento;
	}

	public void setDesdeInstrumento(Boolean desdeInstrumento) {
		this.desdeInstrumento = desdeInstrumento;
	}
		
}