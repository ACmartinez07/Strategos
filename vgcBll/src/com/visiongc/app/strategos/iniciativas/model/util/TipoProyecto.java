package com.visiongc.app.strategos.iniciativas.model.util;

import java.io.Serializable;

public class TipoProyecto implements Serializable {
	
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
	
	public TipoProyecto(){}
	
	public TipoProyecto(Long tipoProyectoId, String nombre) {
		super();
		this.tipoProyectoId = tipoProyectoId;
		this.nombre = nombre;
	}
	  
	  
	  
}
