package com.visiongc.app.strategos.web.struts.responsables.forms;

import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.Set;

public class EditarResponsableForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long responsableId;
  private Long usuarioId;
  private Long organizacionId;
  private OrganizacionStrategos organizacion;
  private String cargo;
  private String nombre;
  private String email;
  private String ubicacion;
  private String notas;
  private Boolean soloAsociar;
  private Boolean tipo;
  private Usuario usuario;
  private String nombreUsuario;
  private Set responsables;

  public String getNombreUsuario()
  {
    return this.nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public Long getResponsableId() {
    return this.responsableId;
  }

  public void setResponsableId(Long responsableId) {
    this.responsableId = responsableId;
  }

  public Long getUsuarioId() {
    return this.usuarioId;
  }

  public void setUsuarioId(Long usuarioId) {
    this.usuarioId = usuarioId;
  }

  public Long getOrganizacionId() {
    return this.organizacionId;
  }

  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }

  public String getCargo() {
    return this.cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUbicacion() {
    return this.ubicacion;
  }

  public void setUbicacion(String ubicacion) {
    this.ubicacion = ubicacion;
  }

  public String getNotas() {
    return this.notas;
  }

  public void setNotas(String notas) {
    this.notas = notas;
  }

  public Boolean getSoloAsociar() {
    return this.soloAsociar;
  }

  public void setSoloAsociar(Boolean soloAsociar) {
    this.soloAsociar = soloAsociar;
  }

  public Boolean getTipo() {
    return this.tipo;
  }

  public void setTipo(Boolean tipo) {
    this.tipo = tipo;
  }

  public Usuario getUsuario() {
    return this.usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public Set getResponsables() {
    return this.responsables;
  }

  public void setResponsables(Set responsables) {
    this.responsables = responsables;
  }

  public OrganizacionStrategos getOrganizacion() {
    return this.organizacion;
  }

  public void setOrganizacion(OrganizacionStrategos organizacion) {
    this.organizacion = organizacion;
  }

  public void clear() {
    this.responsableId = new Long(0L);
    this.usuarioId = new Long(0L);
    this.organizacionId = new Long(0L);
    this.nombre = null;
    this.cargo = null;
    this.email = null;
    this.ubicacion = null;
    this.notas = null;
    this.nombreUsuario = null;
    this.soloAsociar = new Boolean(false);
    this.tipo = new Boolean(false);
    this.responsables = null;
    setBloqueado(new Boolean(false));
  }
}