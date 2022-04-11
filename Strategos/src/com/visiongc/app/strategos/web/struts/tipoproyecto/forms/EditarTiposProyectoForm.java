package com.visiongc.app.strategos.web.struts.tipoproyecto.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarTiposProyectoForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
  	private Long tipoProyectoId;
  	private String nombre;
  
  	public Long getTipoProyectoId() {
		return tipoProyectoId;
  	}
  
  	public void setTipoProyectoId(Long tipoProyectoId) {
		this.tipoProyectoId = tipoProyectoId;
  	}
  
  	public String getNombre() {
		return nombre;
  	}
  
	public void setNombre(String nombre) {
			this.nombre = nombre;
	}

	public void clear() {
		this.tipoProyectoId = new Long(0L);
	    this.nombre = null;
	    setBloqueado(new Boolean(false));
		
	}
  
  
 
}