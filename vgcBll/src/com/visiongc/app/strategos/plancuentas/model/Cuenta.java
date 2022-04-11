package com.visiongc.app.strategos.plancuentas.model;

import com.visiongc.framework.arboles.NodoArbol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Cuenta
  implements Serializable, NodoArbol
{
  static final long serialVersionUID = 0L;
  private Long cuentaId;
  private String codigo;
  private String descripcion;
  private Long padreId;
  private Cuenta padre;
  private Set hijos;
  private boolean hijosCargados;
  
  public Cuenta(Long cuentaId, String codigo, String descripcion, Long pedreId)
  {
    this.cuentaId = cuentaId;
    this.codigo = codigo;
    this.descripcion = descripcion;
    padreId = pedreId;
  }
  

  public Cuenta() {}
  

  public Long getCuentaId()
  {
    return cuentaId;
  }
  
  public void setCuentaId(Long cuentaId) {
    this.cuentaId = cuentaId;
  }
  
  public String getCodigo() {
    return codigo;
  }
  
  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }
  
  public String getDescripcion() {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  
  public String getNombreFormateadoArbol() {
    if ((padreId != null) && (padreId.longValue() != 0L) && (!padreId.equals(""))) {
      return codigo + " - " + descripcion;
    }
    return descripcion;
  }
  
  public Long getPadreId()
  {
    return padreId;
  }
  
  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }
  
  public Cuenta getPadre() {
    return padre;
  }
  
  public void setPadre(Cuenta padre) {
    this.padre = padre;
  }
  
  public Set getHijos() {
    return hijos;
  }
  
  public void setHijos(Set hijos) {
    this.hijos = hijos;
  }
  
  public boolean getHijosCargados() {
    return hijosCargados;
  }
  
  public void setHijosCargados(boolean hijosCargados) {
    this.hijosCargados = hijosCargados;
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
    return hijosCargados;
  }
  
  public String getNodoArbolId() {
    if (cuentaId != null) {
      return cuentaId.toString();
    }
    return null;
  }
  
  public String getNodoArbolNombre()
  {
    return descripcion;
  }
  
  public String getNodoArbolNombreCampoId() {
    return "cuentaId";
  }
  
  public String getNodoArbolNombreCampoNombre() {
    return "descripcion";
  }
  
  public String getNodoArbolNombreCampoPadreId() {
    return "padreId";
  }
  
  public String getNodoArbolNombreImagen(Byte tipo)
  {
    if (tipo.byteValue() == 1) {
      return "Cuenta";
    }
    return "";
  }
  
  public String getNodoArbolNombreToolTipImagen(Byte tipo)
  {
    return "";
  }
  
  public void setNodoArbolPadre(NodoArbol nodoArbol) {
    setPadre((Cuenta)nodoArbol);
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
  
  public void setNodoArbolHijosCargados(boolean cargados)
  {
    hijosCargados = cargados;
  }
  
  public void setNodoArbolNombre(String nombre) {
    descripcion = nombre;
  }
  
  public int compareTo(Object o) {
    Cuenta or = (Cuenta)o;
    return getCuentaId().compareTo(or.getCuentaId());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("cuentaId", getCuentaId()).toString();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Cuenta other = (Cuenta)obj;
    return new EqualsBuilder().append(getCuentaId(), other.getCuentaId()).isEquals();
  }
}
