package com.visiongc.app.strategos.web.struts.anexos.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarAnexosForm extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  private String objetoKey;
  private Long objetoId;
  private String nombreOrganizacion;
  private String nombreObjeto;

  public String getObjetoKey()
  {
    return this.objetoKey;
  }

  public void setObjetoKey(String objetoKey) {
    this.objetoKey = objetoKey;
  }

  public Long getObjetoId() {
    return this.objetoId;
  }

  public void setObjetoId(Long objetoId) {
    this.objetoId = objetoId;
  }

  public String getNombreOrganizacion() {
    return this.nombreOrganizacion;
  }

  public void setNombreOrganizacion(String nombreOrganizacion) {
    this.nombreOrganizacion = nombreOrganizacion;
  }

  public String getNombreObjeto() {
    return this.nombreObjeto;
  }

  public void setNombreObjeto(String nombreObjeto) {
    this.nombreObjeto = nombreObjeto;
  }
}