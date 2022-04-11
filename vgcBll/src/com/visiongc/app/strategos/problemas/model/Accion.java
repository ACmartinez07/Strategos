package com.visiongc.app.strategos.problemas.model;

import com.visiongc.framework.arboles.NodoArbol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Accion
  implements Serializable, NodoArbol
{
  static final long serialVersionUID = 0L;
  private Long accionId;
  private Long estadoId;
  private Long problemaId;
  private String nombre;
  private String descripcion;
  private Date fechaEstimadaInicio;
  private Date fechaRealInicio;
  private Date fechaEstimadaFinal;
  private Date fechaRealFinal;
  private Integer frecuencia;
  private Byte orden;
  private Long padreId;
  private Date creado;
  private Date modificado;
  private Long creadoId;
  private Long modificadoId;
  private Boolean readOnly;
  private Accion padre;
  private Set hijos;
  private Boolean hijosCargados;
  private Problema problema;
  private Set responsablesAccion;
  
  public Accion(Long accionId, Long estadoId, Long problemaId, String nombre, String descripcion, Date fechaEstimadaInicio, Date fechaRealInicio, Date fechaEstimadaFinal, Date fechaRealFinal, Integer frecuencia, Byte orden, Long padreId, Date creado, Date modificado, Long creadoId, Long modificadoId, Boolean readOnly, Accion padre, Set hijos)
  {
    this.accionId = accionId;
    this.estadoId = estadoId;
    this.problemaId = problemaId;
    this.nombre = nombre;
    this.fechaEstimadaInicio = fechaEstimadaInicio;
    this.fechaRealInicio = fechaRealInicio;
    this.fechaEstimadaFinal = fechaEstimadaFinal;
    this.fechaRealFinal = fechaRealFinal;
    this.frecuencia = frecuencia;
    this.orden = orden;
    this.padreId = padreId;
    this.creado = creado;
    this.modificado = modificado;
    this.creadoId = creadoId;
    this.modificadoId = modificadoId;
    this.readOnly = readOnly;
    this.padre = padre;
    this.hijos = hijos;
    this.descripcion = descripcion;
  }
  

  public Accion() {}
  

  public Long getAccionId()
  {
    return accionId;
  }
  
  public void setAccionId(Long accionId) {
    this.accionId = accionId;
  }
  
  public Long getEstadoId() {
    return estadoId;
  }
  
  public void setEstadoId(Long estadoId) {
    this.estadoId = estadoId;
  }
  
  public Long getProblemaId() {
    return problemaId;
  }
  
  public void setProblemaId(Long problemaId) {
    this.problemaId = problemaId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public Date getFechaEstimadaInicio() {
    return fechaEstimadaInicio;
  }
  
  public void setFechaEstimadaInicio(Date fechaEstimadaInicio) {
    this.fechaEstimadaInicio = fechaEstimadaInicio;
  }
  
  public Date getFechaRealInicio() {
    return fechaRealInicio;
  }
  
  public void setFechaRealInicio(Date fechaRealInicio) {
    this.fechaRealInicio = fechaRealInicio;
  }
  
  public Date getFechaEstimadaFinal() {
    return fechaEstimadaFinal;
  }
  
  public void setFechaEstimadaFinal(Date fechaEstimadaFinal) {
    this.fechaEstimadaFinal = fechaEstimadaFinal;
  }
  
  public Date getFechaRealFinal() {
    return fechaRealFinal;
  }
  
  public void setFechaRealFinal(Date fechaRealFinal) {
    this.fechaRealFinal = fechaRealFinal;
  }
  
  public Integer getFrecuencia() {
    return frecuencia;
  }
  
  public void setFrecuencia(Integer frecuencia) {
    this.frecuencia = frecuencia;
  }
  
  public Byte getOrden() {
    return orden;
  }
  
  public void setOrden(Byte orden) {
    this.orden = orden;
  }
  
  public Long getPadreId() {
    return padreId;
  }
  
  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }
  
  public Date getCreado() {
    return creado;
  }
  
  public void setCreado(Date creado) {
    this.creado = creado;
  }
  
  public Date getModificado() {
    return modificado;
  }
  
  public void setModificado(Date modificado) {
    this.modificado = modificado;
  }
  
  public Long getCreadoId() {
    return creadoId;
  }
  
  public void setCreadoId(Long creadoId) {
    this.creadoId = creadoId;
  }
  
  public Long getModificadoId() {
    return modificadoId;
  }
  
  public void setModificadoId(Long modificadoId) {
    this.modificadoId = modificadoId;
  }
  
  public Boolean getReadOnly() {
    return readOnly;
  }
  
  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
  }
  
  public Problema getProblema() {
    return problema;
  }
  
  public void setProblema(Problema problema) {
    this.problema = problema;
  }
  
  public Set getResponsablesAccion() {
    return responsablesAccion;
  }
  
  public void setResponsablesAccion(Set responsablesAccion) {
    this.responsablesAccion = responsablesAccion;
  }
  
  public Accion getPadre() {
    return padre;
  }
  
  public void setPadre(Accion padre) {
    this.padre = padre;
  }
  
  public Set getHijos() {
    return hijos;
  }
  
  public void setHijos(Set hijos) {
    this.hijos = hijos;
  }
  
  public String getDescripcion() {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  
  public List getHijosHoja() {
    return new ArrayList();
  }
  
  public void setHijosHoja(List hijosHoja) {}
  
  public Collection getNodoArbolHijos()
  {
    return hijos;
  }
  
  public boolean getNodoArbolHijosCargados() {
    if (hijosCargados == null) {
      hijosCargados = new Boolean(false);
    }
    return hijosCargados.booleanValue();
  }
  
  public String getNodoArbolId() {
    if (accionId != null) {
      return accionId.toString();
    }
    return null;
  }
  
  public String getNodoArbolNombre()
  {
    return nombre;
  }
  
  public String getNodoArbolNombreCampoId() {
    return "accionId";
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
      return "Accion";
    }
    return "";
  }
  
  public String getNodoArbolNombreToolTipImagen(Byte tipo)
  {
    return "";
  }
  
  public void setNodoArbolPadre(NodoArbol nodoArbol) {
    setPadre((Accion)nodoArbol);
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
    Accion or = (Accion)o;
    return getAccionId().compareTo(or.getAccionId());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("accionId", getAccionId()).toString();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Accion other = (Accion)obj;
    if (accionId == null) {
      if (accionId != null)
        return false;
    } else if (!accionId.equals(accionId))
      return false;
    return true;
  }
}
