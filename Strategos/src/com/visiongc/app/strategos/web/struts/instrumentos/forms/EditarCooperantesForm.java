package com.visiongc.app.strategos.web.struts.instrumentos.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarCooperantesForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	
	private Long cooperanteId;
	private String nombre;  
	private String descripcion;
	private String pais;
			
	public Long getCooperanteId() {
		return cooperanteId;
	}

	public void setCooperanteId(Long cooperanteId) {
		this.cooperanteId = cooperanteId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public void clear() 
	{
		this.cooperanteId = new Long(0L);
		this.nombre = null;
		this.descripcion = null;
		this.pais = null;
		setBloqueado(new Boolean(false));
	}
}