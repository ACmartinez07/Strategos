package com.visiongc.app.strategos.responsables.model;

import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.framework.model.Usuario;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Responsable
{
  private Long responsableId;
  private Long usuarioId;
  private Long organizacionId;
  private String cargo;
  private String nombre;
  private String email;
  private String ubicacion;
  private String notas;
  private Boolean tipo;
  private Boolean esGrupo;
  private Usuario usuario;
  private OrganizacionStrategos organizacion;
  private Set responsables;
  
  public Responsable(Long responsableId, Long usuarioId, Long organizacionId, String cargo, String nombre, String email, String ubicacion, String notas, Boolean global, Boolean soloAsociar, Boolean tipo, Boolean esGrupo)
  {
    this.responsableId = responsableId;
    this.usuarioId = usuarioId;
    this.organizacionId = organizacionId;
    this.cargo = cargo;
    this.nombre = nombre;
    this.email = email;
    this.ubicacion = ubicacion;
    this.notas = notas;
    this.tipo = tipo;
    this.esGrupo = esGrupo;
  }
  

  public Responsable() {}
  

  public String getNombreCargo()
  {
    String str = "";
    if (usuarioId == null) {
      str = str + getNombre();
    } else {
      str = str + getUsuario().getFullName();
    }
    str = str + " - " + getCargo();
    return str;
  }
  
  public Long getResponsableId() {
    return responsableId;
  }
  
  public void setResponsableId(Long responsableId) {
    this.responsableId = responsableId;
  }
  
  public Long getUsuarioId() {
    return usuarioId;
  }
  
  public void setUsuarioId(Long usuarioId) {
    this.usuarioId = usuarioId;
  }
  
  public Long getOrganizacionId() {
    return organizacionId;
  }
  
  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }
  
  public String getCargo() {
    return cargo;
  }
  
  public void setCargo(String cargo) {
    this.cargo = cargo;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getUbicacion() {
    return ubicacion;
  }
  
  public void setUbicacion(String ubicacion) {
    this.ubicacion = ubicacion;
  }
  
  public String getNotas() {
    return notas;
  }
  
  public void setNotas(String notas) {
    this.notas = notas;
  }
  
  public Boolean getTipo() {
    return tipo;
  }
  
  public void setTipo(Boolean tipo) {
    this.tipo = tipo;
  }
  
  public Boolean getEsGrupo() {
    return esGrupo;
  }
  
  public void setEsGrupo(Boolean esGrupo) {
    this.esGrupo = esGrupo;
  }
  
  public Set getResponsables() {
    return responsables;
  }
  
  public void setResponsables(Set responsables) {
    this.responsables = responsables;
  }
  
  public Usuario getUsuario() {
    return usuario;
  }
  
  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }
  
  public OrganizacionStrategos getOrganizacion() {
    return organizacion;
  }
  
  public void setOrganizacion(OrganizacionStrategos organizacion) {
    this.organizacion = organizacion;
  }
  
  public int compareTo(Object o) {
    Responsable or = (Responsable)o;
    return getResponsableId().compareTo(or.getResponsableId());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("responsableId", getResponsableId()).toString();
  }
  
  public int hashCode() {
    int prime = 31;
    int result = 1;
    result = 31 * result + (responsableId == null ? 0 : responsableId.hashCode());
    return result;
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Responsable other = (Responsable)obj;
    if (responsableId == null) {
      if (responsableId != null)
        return false;
    } else if (!responsableId.equals(responsableId))
      return false;
    return true;
  }
}
