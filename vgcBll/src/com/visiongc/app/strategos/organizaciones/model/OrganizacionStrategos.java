package com.visiongc.app.strategos.organizaciones.model;

import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.model.OrganizacionBasica;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.util.SoloLectura;
import com.visiongc.framework.model.util.Visibilidad;
import com.visiongc.framework.permisologia.OrganizacionPermisologia;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OrganizacionStrategos extends OrganizacionBasica implements Serializable, NodoArbol, OrganizacionPermisologia, Visibilidad, SoloLectura
{
  static final long serialVersionUID = 0L;
  private String rif;
  private String telefono;
  private String fax;
  private Byte mesCierre;
  private String enlaceParcial;
  private Integer porcentajeZonaAmarillaMinMaxIndicadores;
  private Integer porcentajeZonaVerdeMetaIndicadores;
  private Integer porcentajeZonaAmarillaMetaIndicadores;
  private Integer porcentajeZonaVerdeIniciativas;
  private Integer porcentajeZonaAmarillaIniciativas;
  private Boolean visible;
  private Boolean soloLectura;
  private Set hijos;
  private Set memos;
  private OrganizacionStrategos padre;
  private Boolean hijosCargados;
  private Set clases;
  private Usuario usuarioCreado;
  private Usuario usuarioModificado;
  private String rutaCompleta;
  
  public OrganizacionStrategos(Long organizacionId, Long padreId, String nombre, String direccion, Date creado, Date modificado, Long creadoId, Long modificadoId, String rif, String telefono, String fax, Byte mesCierre, Integer porcentajeZonaAmarillaMinMaxIndicadores, Integer porcentajeZonaVerdeMetaIndicadores, Integer porcentajeZonaAmarillaMetaIndicadores, Integer porcentajeZonaVerdeIniciativas, Integer porcentajeZonaAmarillaIniciativas)
  {
    super.setOrganizacionId(organizacionId);
    super.setPadreId(padreId);
    super.setNombre(nombre);
    super.setDireccion(direccion);
    super.setCreado(creado);
    super.setModificado(modificado);
    super.setCreadoId(creadoId);
    super.setModificadoId(modificadoId);
    this.rif = rif;
    this.telefono = telefono;
    this.fax = fax;
    this.mesCierre = mesCierre;
    this.porcentajeZonaAmarillaMinMaxIndicadores = porcentajeZonaAmarillaMinMaxIndicadores;
    this.porcentajeZonaVerdeMetaIndicadores = porcentajeZonaVerdeMetaIndicadores;
    this.porcentajeZonaAmarillaMetaIndicadores = porcentajeZonaAmarillaMetaIndicadores;
    this.porcentajeZonaVerdeIniciativas = porcentajeZonaVerdeIniciativas;
    this.porcentajeZonaAmarillaIniciativas = porcentajeZonaAmarillaIniciativas;
  }
  

  public OrganizacionStrategos() {}
  

  public String getFax()
  {
    return fax;
  }
  
  public void setFax(String fax) {
    this.fax = fax;
  }
  
  public Set getHijos() {
    return hijos;
  }
  
  public void setHijos(Set hijos) {
    this.hijos = hijos;
  }
  
  public Byte getMesCierre() {
    return mesCierre;
  }
  
  public void setMesCierre(Byte mesCierre) {
    this.mesCierre = mesCierre;
  }
  
  public String getEnlaceParcial() {
    return enlaceParcial;
  }
  
  public void setEnlaceParcial(String enlaceParcial) {
    this.enlaceParcial = enlaceParcial;
  }
  
  public OrganizacionStrategos getPadre() {
    return padre;
  }
  
  public void setPadre(OrganizacionStrategos padre) {
    this.padre = padre;
  }
  
  public Integer getPorcentajeZonaAmarillaIniciativas() {
    return porcentajeZonaAmarillaIniciativas;
  }
  
  public void setPorcentajeZonaAmarillaIniciativas(Integer porcentajeZonaAmarillaIniciativas) {
    this.porcentajeZonaAmarillaIniciativas = porcentajeZonaAmarillaIniciativas;
  }
  
  public Integer getPorcentajeZonaAmarillaMetaIndicadores() {
    return porcentajeZonaAmarillaMetaIndicadores;
  }
  
  public void setPorcentajeZonaAmarillaMetaIndicadores(Integer porcentajeZonaAmarillaMetaIndicadores) {
    this.porcentajeZonaAmarillaMetaIndicadores = porcentajeZonaAmarillaMetaIndicadores;
  }
  
  public Integer getPorcentajeZonaAmarillaMinMaxIndicadores() {
    return porcentajeZonaAmarillaMinMaxIndicadores;
  }
  
  public void setPorcentajeZonaAmarillaMinMaxIndicadores(Integer porcentajeZonaAmarillaMinMaxIndicadores) {
    this.porcentajeZonaAmarillaMinMaxIndicadores = porcentajeZonaAmarillaMinMaxIndicadores;
  }
  
  public Integer getPorcentajeZonaVerdeIniciativas() {
    return porcentajeZonaVerdeIniciativas;
  }
  
  public void setPorcentajeZonaVerdeIniciativas(Integer porcentajeZonaVerdeIniciativas) {
    this.porcentajeZonaVerdeIniciativas = porcentajeZonaVerdeIniciativas;
  }
  
  public Integer getPorcentajeZonaVerdeMetaIndicadores() {
    return porcentajeZonaVerdeMetaIndicadores;
  }
  
  public void setPorcentajeZonaVerdeMetaIndicadores(Integer porcentajeZonaVerdeMetaIndicadores) {
    this.porcentajeZonaVerdeMetaIndicadores = porcentajeZonaVerdeMetaIndicadores;
  }
  
  public String getRif() {
    return rif;
  }
  
  public void setRif(String rif) {
    this.rif = rif;
  }
  
  public String getTelefono() {
    return telefono;
  }
  
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }
  
  public Boolean getVisible() {
    return visible;
  }
  
  public void setVisible(Boolean visible) {
    this.visible = visible;
  }
  
  public Boolean getSoloLectura() {
    return soloLectura;
  }
  
  public void setSoloLectura(Boolean soloLectura) {
    this.soloLectura = soloLectura;
  }
  
  public Boolean getHijosCargados() {
    return hijosCargados;
  }
  
  public void setHijosCargados(Boolean hijosCargados) {
    this.hijosCargados = hijosCargados;
  }
  
  public Set getMemos() {
    return memos;
  }
  
  public void setMemos(Set memos) {
    this.memos = memos;
  }
  
  public Set getClases() {
    return clases;
  }
  
  public void setClases(Set clases) {
    this.clases = clases;
  }
  
  public Usuario getUsuarioCreado() {
    return usuarioCreado;
  }
  
  public void setUsuarioCreado(Usuario usuarioCreado) {
    this.usuarioCreado = usuarioCreado;
  }
  
  public Usuario getUsuarioModificado() {
    return usuarioModificado;
  }
  
  public void setUsuarioModificado(Usuario usuarioModificado) {
    this.usuarioModificado = usuarioModificado;
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
    if (getOrganizacionId() != null) {
      return getOrganizacionId().toString();
    }
    return null;
  }
  
  public String getNodoArbolNombre()
  {
    return getNombre();
  }
  
  public String getNodoArbolNombreCampoId() {
    return "organizacionId";
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
      return "Organizacion";
    }
    return "";
  }
  
  public String getNodoArbolNombreToolTipImagen(Byte tipo)
  {
    return "";
  }
  
  public void setNodoArbolPadre(NodoArbol nodoArbol) {
    setPadre((OrganizacionStrategos)nodoArbol);
  }
  
  public NodoArbol getNodoArbolPadre() {
    return padre;
  }
  
  public String getNodoArbolPadreId() {
    if (getPadreId() != null) {
      return getPadreId().toString();
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
    setNombre(nombre);
  }
  
  public String getRutaCompleta()
  {
    return rutaCompleta;
  }
  
  public void setRutaCompleta(String rutaCompleta) {
    this.rutaCompleta = rutaCompleta;
  }
  
  public int compareTo(Object o) {
    OrganizacionStrategos or = (OrganizacionStrategos)o;
    return getOrganizacionId().compareTo(or.getOrganizacionId());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("organizacionId", getOrganizacionId()).toString();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    OrganizacionStrategos other = (OrganizacionStrategos)obj;
    return new EqualsBuilder().append(getOrganizacionId(), other.getOrganizacionId()).isEquals();
  }
}
