package com.visiongc.app.strategos.causas.model;

import com.visiongc.framework.arboles.NodoArbol;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Causa
  implements Serializable, NodoArbol
{
  static final long serialVersionUID = 0L;
  private Long causaId;
  private String nombre;
  private String descripcion;
  private Long padreId;
  private Integer nivel;
  private Causa padre;
  private Set hijos;
  private Set problemas;
  private Boolean hijosCargados;
  
  public Causa(Long causaId, String nombre, String descripcion, Long padreId, Integer nivel, Causa padre, Set hijos)
  {
    this.causaId = causaId;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.padreId = padreId;
    this.nivel = nivel;
    this.padre = padre;
    this.hijos = hijos;
  }
  

  public Causa() {}
  

  public Long getCausaId()
  {
    return causaId;
  }
  
  public void setCausaId(Long causaId) {
    this.causaId = causaId;
  }
  
  public String getDescripcion() {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  
  public Integer getNivel() {
    return nivel;
  }
  
  public void setNivel(Integer nivel) {
    this.nivel = nivel;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public Long getPadreId() {
    return padreId;
  }
  
  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }
  
  public Set getHijos() {
    return hijos;
  }
  
  public void setHijos(Set hijos) {
    this.hijos = hijos;
  }
  
  public Causa getPadre() {
    return padre;
  }
  
  public void setPadre(Causa padre) {
    this.padre = padre;
  }
  
  public Set getProblemas() {
    return problemas;
  }
  
  public void setProblemas(Set problemas) {
    this.problemas = problemas;
  }
  
  public Boolean getHijosCargados() {
    return hijosCargados;
  }
  
  public void setHijosCargados(Boolean hijosCargados) {
    this.hijosCargados = hijosCargados;
  }
  
  public Collection getNodoArbolHijos() {
    return hijos;
  }
  
  public boolean getNodoArbolHijosCargados() {
    if (hijosCargados == null) {
      hijosCargados = new Boolean(false);
    }
    return hijosCargados.booleanValue();
  }
  
  public String getNodoArbolId() {
    if (causaId != null) {
      return causaId.toString();
    }
    return null;
  }
  
  public String getNodoArbolNombre()
  {
    return nombre;
  }
  
  public String getNodoArbolNombreCampoId() {
    return "causaId";
  }
  
  public String getNodoArbolNombreCampoNombre() {
    return "nombre";
  }
  
  public String getNodoArbolNombreCampoPadreId() {
    return "padreId";
  }
  
  public String getNodoArbolNombreImagen(Byte tipo)
  {
    if (tipo.byteValue() == 1) {
      return "Causa";
    }
    return "";
  }
  
  public String getNodoArbolNombreToolTipImagen(Byte tipo)
  {
    return "";
  }
  
  public void setNodoArbolPadre(NodoArbol nodoArbol) {
    setPadre((Causa)nodoArbol);
  }
  
  public NodoArbol getNodoArbolPadre() {
    return padre;
  }
  
  public String getNodoArbolPadreId() {
    if (padreId != null) {
      return padreId.toString();
    }
    return null;
  }
  
  public void setNodoArbolHijos(Collection nodoArbolHijos)
  {
    hijos = ((Set)nodoArbolHijos);
  }
  
  public void setNodoArbolHijosCargados(boolean cargados) {
    hijosCargados = new Boolean(cargados);
  }
  
  public void setNodoArbolNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public int compareTo(Object o) {
    Causa or = (Causa)o;
    return getCausaId().compareTo(or.getCausaId());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("causaId", getCausaId()).toString();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Causa other = (Causa)obj;
    if (causaId == null) {
      if (causaId != null)
        return false;
    } else if (!causaId.equals(causaId))
      return false;
    return true;
  }
}
