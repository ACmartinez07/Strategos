package com.visiongc.framework.web.struts.forms.usuarios;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;


public class SeleccionarUsuariosForm
  extends SelectorListaForm
{
  static final long serialVersionUID = 0L;
  private String mostrarAdministradores;
  private String organizacionId;
  private String desdeResponsable;
  private String funcionCierre;
  private String filtroNombre;
  
  public SeleccionarUsuariosForm() {}
  
  public String getDesdeResponsable() {
	return desdeResponsable;
  }

  public void setDesdeResponsable(String desdeResponsable) {
	this.desdeResponsable = desdeResponsable;
  }

  public String getMostrarAdministradores()
  {
    return mostrarAdministradores;
  }
  
  public void setMostrarAdministradores(String mostrarAdministradores) {
    this.mostrarAdministradores = mostrarAdministradores;
  }
  
  public String getOrganizacionId() {
    return organizacionId;
  }
  
  public void setOrganizacionId(String organizacionId) {
    this.organizacionId = organizacionId;
  }
  
  public String getFuncionCierre() {
    return funcionCierre;
  }
  
  public void setFuncionCierre(String funcionCierre) {
    this.funcionCierre = funcionCierre;
  }

  public String getFiltroNombre() {
	return filtroNombre;
  }
	
  public void setFiltroNombre(String filtroNombre) {
	this.filtroNombre = filtroNombre;
  }
  
  
}
