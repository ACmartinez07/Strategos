package com.visiongc.app.strategos.web.struts.foros.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;
import java.util.List;

public class GestionarForosForm extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  private String nombreOrganizacion;
  private String tipoObjetoKey;
  private Long objetoId;
  private String nombreObjetoKey;
  private Byte tipo;
  private Long foroId;
  private Long padreId;
  private List listaForos;

  public Long getForoId()
  {
    return this.foroId;
  }

  public void setForoId(Long foroId) {
    this.foroId = foroId;
  }

  public String getNombreOrganizacion() {
    return this.nombreOrganizacion;
  }

  public void setNombreOrganizacion(String nombreOrganizacion) {
    this.nombreOrganizacion = nombreOrganizacion;
  }

  public String getTipoObjetoKey() {
    return this.tipoObjetoKey;
  }

  public void setTipoObjetoKey(String tipoObjetoKey) {
    this.tipoObjetoKey = tipoObjetoKey;
  }

  public String getNombreObjetoKey() {
    return this.nombreObjetoKey;
  }

  public void setNombreObjetoKey(String nombreObjetoKey) {
    this.nombreObjetoKey = nombreObjetoKey;
  }

  public Byte getTipo() {
    return this.tipo;
  }

  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }

  public Long getObjetoId() {
    return this.objetoId;
  }

  public void setObjetoId(Long objetoId) {
    this.objetoId = objetoId;
  }

  public Long getPadreId() {
    return this.padreId;
  }

  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }

  public List getListaForos() {
    return this.listaForos;
  }

  public void setListaForos(List listaForos) {
    this.listaForos = listaForos;
  }
}