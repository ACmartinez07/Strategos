package com.visiongc.app.strategos.web.struts.unidadesmedida.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarUnidadMedidaForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	
	private Long unidadId;
	private String nombre;  
	private Boolean tipo;

	public Long getUnidadId()
	{
		return this.unidadId;
	}

	public void setUnidadId(Long unidadId) 
	{
		this.unidadId = unidadId;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public Boolean getTipo() 
	{
		return this.tipo;
	}

	public void setTipo(Boolean tipo) 
	{
		this.tipo = tipo;
	}

	public void clear() 
	{
		this.unidadId = new Long(0L);
		this.nombre = null;
		this.tipo = new Boolean(false);
		setBloqueado(new Boolean(false));
	}
}