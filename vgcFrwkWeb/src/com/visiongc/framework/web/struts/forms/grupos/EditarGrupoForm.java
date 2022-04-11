package com.visiongc.framework.web.struts.forms.grupos;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;








public class EditarGrupoForm
  extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long grupoId;
  private String grupo;
  private String permisos;
  private String listaPadresHijosPermisos;
  private boolean copiar;
  private Integer totalPermisosAsociados;
  public static final String SEPARADOR = ":";
  public static final String SEPARADOR_PADRE_HIJO = "#hijo#";
  
  public EditarGrupoForm() {}
  
  public String getSeparador()
  {
    return ":";
  }
  
  public String getSeparadorPadreHijo() {
    return "#hijo#";
  }
  
  public Long getGrupoId() {
    return grupoId;
  }
  
  public void setGrupoId(Long grupoId) {
    this.grupoId = grupoId;
  }
  
  public String getGrupo() {
    return grupo;
  }
  
  public void setGrupo(String grupo) {
    this.grupo = grupo;
  }
  
  public String getPermisos() {
    return permisos;
  }
  
  public void setPermisos(String permisos) {
    this.permisos = permisos;
  }
  
  public String getListaPadresHijosPermisos() {
    return listaPadresHijosPermisos;
  }
  
  public void setListaPadresHijosPermisos(String listaPadresHijosPermisos) {
    this.listaPadresHijosPermisos = listaPadresHijosPermisos;
  }
  
  public Integer getTotalPermisosAsociados() {
    return totalPermisosAsociados;
  }
  
  public void setTotalPermisosAsociados(Integer totalPermisosAsociados) {
    this.totalPermisosAsociados = totalPermisosAsociados;
  }
  
  public boolean isCopiar() {
    return copiar;
  }
  
  public void setCopiar(boolean copiar) {
    this.copiar = copiar;
  }
  
  public String getPermisoIdRoot() {
    return "PERMISOS";
  }
  
  public void clear() {
    grupoId = new Long(0L);
    grupo = null;
    permisos = null;
    copiar = false;
    listaPadresHijosPermisos = null;
    totalPermisosAsociados = null;
  }
}
