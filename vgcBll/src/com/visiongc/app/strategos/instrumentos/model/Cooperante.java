package com.visiongc.app.strategos.instrumentos.model;

import com.visiongc.framework.model.Usuario;
import java.io.Serializable;
import java.util.Date;

public class Cooperante
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long cooperanteId;
  private String nombre;
  private String descripcion;
  private String pais;
  
  
  public Cooperante() {}


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
  
   
  
}
