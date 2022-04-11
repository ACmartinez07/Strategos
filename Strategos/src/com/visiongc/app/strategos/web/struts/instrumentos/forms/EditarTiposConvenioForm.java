package com.visiongc.app.strategos.web.struts.instrumentos.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarTiposConvenioForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	
	private Long tiposConvenioId;
	private String descripcion;
	private String nombre;
		
	public Long getTiposConvenioId() {
		return tiposConvenioId;
	}

	public void setTiposConvenioId(Long tiposConvenioId) {
		this.tiposConvenioId = tiposConvenioId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void clear() 
	{
		this.tiposConvenioId = new Long(0L);
		this.descripcion = null;
		this.nombre = null;
		setBloqueado(new Boolean(false));
	}
}