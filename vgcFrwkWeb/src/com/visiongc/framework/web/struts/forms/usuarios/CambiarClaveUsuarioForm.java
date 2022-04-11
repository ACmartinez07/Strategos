package com.visiongc.framework.web.struts.forms.usuarios;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class CambiarClaveUsuarioForm
  extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long usuarioId;
  private String pwdActual;
  private String pwd;
  private String pwdConfirm;
  private Boolean desdeLogin = Boolean.valueOf(true);
  private String respuesta;
  
  public CambiarClaveUsuarioForm() {}
  
  public Long getUsuarioId() { return usuarioId; }
  

  public void setUsuarioId(Long usuarioId)
  {
    this.usuarioId = usuarioId;
  }
  
  public String getPwdActual()
  {
    return pwdActual;
  }
  
  public void setPwdActual(String pwdActual)
  {
    this.pwdActual = pwdActual;
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
  
  public Boolean getDesdeLogin() {
    return desdeLogin;
  }
  
  public void setDesdeLogin(Boolean desdeLogin)
  {
    this.desdeLogin = desdeLogin;
  }
  
  public String getRespuesta()
  {
    return respuesta;
  }
  
  public void setRespuesta(String respuesta)
  {
    this.respuesta = respuesta;
  }
  
  public void clear()
  {
    usuarioId = new Long(0L);
    pwdActual = null;
    pwd = null;
    pwdConfirm = null;
    desdeLogin = Boolean.valueOf(true);
    respuesta = null;
  }
}
