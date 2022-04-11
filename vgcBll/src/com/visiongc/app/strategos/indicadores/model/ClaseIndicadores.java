package com.visiongc.app.strategos.indicadores.model;

import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.framework.arboles.NodoArbol;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ClaseIndicadores
  implements Serializable, NodoArbol
{
  static final long serialVersionUID = 0L;
  private Long claseId;
  private Long padreId;
  private Long organizacionId;
  private String nombre;
  private String descripcion;
  private String enlaceParcial;
  private Date creado;
  private Date modificado;
  private Long creadoId;
  private Long modificadoId;
  private Byte tipo;
  private Boolean visible;
  private ClaseIndicadores padre;
  private Set hijos;
  private Boolean hijosCargados;
  private Collection nodoArbolHijos;
  private OrganizacionStrategos organizacion;
  private Set indicadores;
  private Integer nivel;
  
  public ClaseIndicadores(Long claseId, Long padreId, Long organizacionId, String nombre, String descripcion, String enlaceParcial, Long creadoId, Long modificadoId, Integer nivel, Byte tipo, Boolean visible, ClaseIndicadores padre, Set hijos, Boolean hijosCargados)
  {
    this.claseId = claseId;
    this.padreId = padreId;
    this.organizacionId = organizacionId;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.enlaceParcial = enlaceParcial;
    this.creadoId = creadoId;
    this.modificadoId = modificadoId;
    this.tipo = tipo;
    this.visible = visible;
    this.padre = padre;
    this.hijos = hijos;
    this.hijosCargados = hijosCargados;
  }
  

  public ClaseIndicadores() {}
  

  public Long getClaseId()
  {
    return claseId;
  }
  
  public void setClaseId(Long claseId) {
    this.claseId = claseId;
  }
  
  public Date getCreado() {
    return creado;
  }
  
  public void setCreado(Date creado) {
    this.creado = creado;
  }
  
  public Long getCreadoId() {
    return creadoId;
  }
  
  public void setCreadoId(Long creadoId) {
    this.creadoId = creadoId;
  }
  
  public String getDescripcion() {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  
  public String getEnlaceParcial() {
    return enlaceParcial;
  }
  
  public void setEnlaceParcial(String enlaceParcial) {
    this.enlaceParcial = enlaceParcial;
  }
  
  public Set getHijos() {
    return hijos;
  }
  
  public void setHijos(Set hijos) {
    this.hijos = hijos;
  }
  
  public Boolean getHijosCargados() {
    return hijosCargados;
  }
  
  public void setHijosCargados(Boolean hijosCargados) {
    this.hijosCargados = hijosCargados;
  }
  
  public Date getModificado() {
    return modificado;
  }
  
  public void setModificado(Date modificado) {
    this.modificado = modificado;
  }
  
  public Long getModificadoId() {
    return modificadoId;
  }
  
  public void setModificadoId(Long modificadoId) {
    this.modificadoId = modificadoId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public Long getOrganizacionId() {
    return organizacionId;
  }
  
  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }
  
  public ClaseIndicadores getPadre() {
    return padre;
  }
  
  public void setPadre(ClaseIndicadores padre) {
    this.padre = padre;
  }
  
  public Long getPadreId() {
    return padreId;
  }
  
  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }
  
  public Byte getTipo() {
    return tipo;
  }
  
  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }
  
  public Boolean getVisible() {
    return visible;
  }
  
  public void setVisible(Boolean visible) {
    this.visible = visible;
  }
  
  public Set getIndicadores() {
    return indicadores;
  }
  
  public void setIndicadores(Set indicadores) {
    this.indicadores = indicadores;
  }
  
  public Integer getNivel()
  {
    return nivel;
  }
  
  public void setNivel(Integer nivel)
  {
    this.nivel = nivel;
  }
  
  public OrganizacionStrategos getOrganizacion() {
    return organizacion;
  }
  
  public void setOrganizacion(OrganizacionStrategos organizacion) {
    this.organizacion = organizacion;
  }
  
  public Collection getNodoArbolHijos() {
    if (nodoArbolHijos != null) {
      return nodoArbolHijos;
    }
    return hijos;
  }
  
  public boolean getNodoArbolHijosCargados()
  {
    if (hijosCargados == null) {
      hijosCargados = new Boolean(false);
    }
    return hijosCargados.booleanValue();
  }
  
  public String getNodoArbolId() {
    if (claseId != null) {
      return claseId.toString();
    }
    return null;
  }
  
  public String getNodoArbolNombre()
  {
    return nombre;
  }
  
  public String getNodoArbolNombreCampoId() {
    return "claseId";
  }
  
  public String getNodoArbolNombreCampoNombre() {
    return "nombre";
  }
  
  public String getNodoArbolNombreCampoPadreId() {
    return "padreId";
  }
  
  public String getNodoArbolNombreImagen(Byte tipo)
  {
    return null;
  }
  
  public String getNodoArbolNombreToolTipImagen(Byte tipo)
  {
    return "";
  }
  
  public void setNodoArbolPadre(NodoArbol nodoArbol) {
    setPadre((ClaseIndicadores)nodoArbol);
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
    if ((nodoArbolHijos instanceof Set)) {
      hijos = ((Set)nodoArbolHijos);
    } else {
      this.nodoArbolHijos = nodoArbolHijos;
    }
  }
  
  public void setNodoArbolHijosCargados(boolean cargados) {
    hijosCargados = new Boolean(cargados);
  }
  
  public void setNodoArbolNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public int compareTo(Object o) {
    ClaseIndicadores or = (ClaseIndicadores)o;
    return getClaseId().compareTo(or.getClaseId());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("claseId", getClaseId()).toString();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ClaseIndicadores other = (ClaseIndicadores)obj;
    if (claseId == null) {
      if (claseId != null)
        return false;
    } else if (!claseId.equals(claseId))
      return false;
    return true;
  }
}
