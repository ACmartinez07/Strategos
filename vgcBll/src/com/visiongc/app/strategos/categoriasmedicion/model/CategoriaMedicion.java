package com.visiongc.app.strategos.categoriasmedicion.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class CategoriaMedicion
{
  private Long categoriaId;
  private String nombre;
  
  public CategoriaMedicion(Long categoriaId, String nombre)
  {
    this.categoriaId = categoriaId;
    this.nombre = nombre;
  }
  

  public CategoriaMedicion() {}
  

  public Long getCategoriaId()
  {
    return categoriaId;
  }
  
  public void setCategoriaId(Long categoriaId) {
    this.categoriaId = categoriaId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public int compareTo(Object o) {
    CategoriaMedicion or = (CategoriaMedicion)o;
    return getCategoriaId().compareTo(or.getCategoriaId());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("categoriaId", getCategoriaId()).toString();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass()) {
      return false;
    }
    CategoriaMedicion other = (CategoriaMedicion)obj;
    if (categoriaId == null) {
      if (categoriaId != null)
        return false;
    } else if (!categoriaId.equals(categoriaId))
      return false;
    return true;
  }
}
