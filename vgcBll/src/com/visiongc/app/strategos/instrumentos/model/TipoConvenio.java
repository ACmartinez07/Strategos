package com.visiongc.app.strategos.instrumentos.model;

import com.visiongc.framework.model.Usuario;
import java.io.Serializable;
import java.util.Date;

public class TipoConvenio
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long tiposConvenioId;
  private String descripcion;
  private String nombre;
  
  public TipoConvenio() {}

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
    
	
}
