package com.visiongc.framework.web.struts.forms.usuarios;

import java.util.List;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarUsuarioForm
  extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long usuarioId;
  private String fullName;
  private String UId;
  private Boolean isAdmin;
  private Boolean bloqueado;
  private Integer estatus;
  private String pwd;
  private String pwdConfirm;
  private String grupos;
  private boolean copiar;
  private Integer totalGruposAsociados;
  private Integer totalOrganizacionesAsociadas;
  private Integer reutilizacionContrasena;
  public static final String SEPARADOR_CAMPOS = ":";
  public static final String SEPARADOR_FILAS = ";";
  private Boolean restriccionUsoDiaLunes;
  private Boolean restriccionUsoDiaMartes;
  private Boolean restriccionUsoDiaMiercoles;
  private Boolean restriccionUsoDiaJueves;
  private Boolean restriccionUsoDiaViernes;
  private Boolean restriccionUsoDiaSabado;
  private Boolean restriccionUsoDiaDomingo;
  private String restriccionUsoHoraDesde = "";
  private String restriccionUsoHoraHasta = "";
  private Boolean deshabilitado;
  private Boolean forzarCambiarpwd;
  private String organizacionesId;
  
  public EditarUsuarioForm() {}
  
  public Long getUsuarioId() { return usuarioId; }
  

  public void setUsuarioId(Long usuarioId)
  {
    this.usuarioId = usuarioId;
  }
  
  public String getFullName()
  {
    return fullName;
  }
  
  public void setFullName(String fullName)
  {
    this.fullName = fullName;
  }
  
  public String getUId()
  {
    return UId;
  }
  
  public void setUId(String UId)
  {
    this.UId = UId;
  }
  
  public Boolean getIsAdmin()
  {
    return isAdmin;
  }
  
  public void setIsAdmin(Boolean inIsAdmin)
  {
    isAdmin = inIsAdmin;
  }
  
  public Boolean getBloqueado()
  {
    return bloqueado;
  }
  
  public void setBloqueado(Boolean bloqueado)
  {
    if (bloqueado == null) {
      this.bloqueado = new Boolean(false);
    } else {
      this.bloqueado = bloqueado;
    }
  }
  
  public Integer getEstatus() {
    return estatus;
  }
  
  public void setEstatus(Integer estatus)
  {
    this.estatus = estatus;
  }
  
  public String getPwd()
  {
    return pwd;
  }
  
  public void setPwd(String pwd)
  {
    if ((pwd != null) && (!pwd.equals(""))) {
      this.pwd = pwd;
    }
  }
  
  public String getPwdConfirm() {
    return pwdConfirm;
  }
  
  public void setPwdConfirm(String pwdConfirm)
  {
    if ((pwdConfirm != null) && (!pwdConfirm.equals(""))) {
      this.pwdConfirm = pwdConfirm;
    }
  }
  
  public String getGrupos() {
    return grupos;
  }
  
  public void setGrupos(String grupos)
  {
    this.grupos = grupos;
  }
  
  public boolean isCopiar()
  {
    return copiar;
  }
  
  public void setCopiar(boolean copiar)
  {
    this.copiar = copiar;
  }
  
  public String getSeparadorFilas()
  {
    return ";";
  }
  
  public Integer getTotalGruposAsociados()
  {
    return totalGruposAsociados;
  }
  
  public void setTotalGruposAsociados(Integer totalGruposAsociados)
  {
    this.totalGruposAsociados = totalGruposAsociados;
  }
  
  public Integer getTotalOrganizacionesAsociadas()
  {
    return totalOrganizacionesAsociadas;
  }
  
  public void setTotalOrganizacionesAsociadas(Integer totalOrganizacionesAsociadas)
  {
    this.totalOrganizacionesAsociadas = totalOrganizacionesAsociadas;
  }
  
  public Integer getReutilizacionContrasena()
  {
    return reutilizacionContrasena;
  }
  
  public void setReutilizacionContrasena(Integer reutilizacionContrasena)
  {
    this.reutilizacionContrasena = reutilizacionContrasena;
  }
  
  public String getSeparadorCampos()
  {
    return ":";
  }
  
  public Boolean getRestriccionUsoDiaLunes()
  {
    return restriccionUsoDiaLunes;
  }
  
  public void setRestriccionUsoDiaLunes(Boolean restriccionUsoDiaLunes)
  {
    this.restriccionUsoDiaLunes = restriccionUsoDiaLunes;
  }
  
  public Boolean getRestriccionUsoDiaMartes()
  {
    return restriccionUsoDiaMartes;
  }
  
  public void setRestriccionUsoDiaMartes(Boolean restriccionUsoDiaMartes)
  {
    this.restriccionUsoDiaMartes = restriccionUsoDiaMartes;
  }
  
  public Boolean getRestriccionUsoDiaMiercoles()
  {
    return restriccionUsoDiaMiercoles;
  }
  
  public void setRestriccionUsoDiaMiercoles(Boolean restriccionUsoDiaMiercoles)
  {
    this.restriccionUsoDiaMiercoles = restriccionUsoDiaMiercoles;
  }
  
  public Boolean getRestriccionUsoDiaJueves()
  {
    return restriccionUsoDiaJueves;
  }
  
  public void setRestriccionUsoDiaJueves(Boolean restriccionUsoDiaJueves)
  {
    this.restriccionUsoDiaJueves = restriccionUsoDiaJueves;
  }
  
  public Boolean getRestriccionUsoDiaViernes()
  {
    return restriccionUsoDiaViernes;
  }
  
  public void setRestriccionUsoDiaViernes(Boolean restriccionUsoDiaViernes)
  {
    this.restriccionUsoDiaViernes = restriccionUsoDiaViernes;
  }
  
  public Boolean getRestriccionUsoDiaSabado()
  {
    return restriccionUsoDiaSabado;
  }
  
  public void setRestriccionUsoDiaSabado(Boolean restriccionUsoDiaSabado)
  {
    this.restriccionUsoDiaSabado = restriccionUsoDiaSabado;
  }
  
  public Boolean getRestriccionUsoDiaDomingo()
  {
    return restriccionUsoDiaDomingo;
  }
  
  public void setRestriccionUsoDiaDomingo(Boolean restriccionUsoDiaDomingo)
  {
    this.restriccionUsoDiaDomingo = restriccionUsoDiaDomingo;
  }
  
  public String getRestriccionUsoHoraDesde()
  {
    return restriccionUsoHoraDesde;
  }
  
  public void setRestriccionUsoHoraDesde(String restriccionUsoHoraDesde)
  {
    this.restriccionUsoHoraDesde = restriccionUsoHoraDesde;
  }
  
  public String getRestriccionUsoHoraHasta()
  {
    return restriccionUsoHoraHasta;
  }
  
  public void setRestriccionUsoHoraHasta(String restriccionUsoHoraHasta)
  {
    this.restriccionUsoHoraHasta = restriccionUsoHoraHasta;
  }
  
  public Boolean getDeshabilitado()
  {
    return deshabilitado;
  }
  
  public void setDeshabilitado(Boolean deshabilitado)
  {
    this.deshabilitado = deshabilitado;
  }
  
  public Boolean getForzarCambiarpwd()
  {
    return forzarCambiarpwd;
  }
  
  public void setForzarCambiarpwd(Boolean forzarCambiarpwd)
  {
    this.forzarCambiarpwd = forzarCambiarpwd;
  }
  
  public String getOrganizacionesId() {
	return organizacionesId;
  }

  public void setOrganizacionesId(String organizacionesId) {
	this.organizacionesId = organizacionesId;
  }

public void clear()
  {
    usuarioId = new Long(0L);
    fullName = null;
    UId = null;
    isAdmin = new Boolean(false);
    bloqueado = new Boolean(false);
    estatus = null;
    pwd = null;
    pwdConfirm = null;
    grupos = null;
    copiar = false;
    deshabilitado = new Boolean(false);
    forzarCambiarpwd = new Boolean(true);
    totalGruposAsociados = null;
    totalOrganizacionesAsociadas = null;
  }
}
