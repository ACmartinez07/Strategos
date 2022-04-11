package com.visiongc.commons.struts.tag;

import javax.servlet.jsp.JspException;

public class TienePermisoTag
  extends VgcBaseTag
{
  static final long serialVersionUID = 0L;
  protected String permisoId = null;
  

  public TienePermisoTag() {}
  
  protected String aplicaOrganizacion = null;
  
  public String getPermisoId() {
    return permisoId; }
  
  public void setPermisoId(String permisoId)
  {
    this.permisoId = permisoId;
  }
  
  public String getAplicaOrganizacion() {
    return aplicaOrganizacion;
  }
  
  public void setAplicaOrganizacion(String aplicaOrganizacion) {
    this.aplicaOrganizacion = aplicaOrganizacion;
  }
  
  public int doStartTag() throws JspException
  {
    boolean aplicaOrganizacion = (this.aplicaOrganizacion != null) && (this.aplicaOrganizacion.equalsIgnoreCase("true"));
    boolean continuar = tienePermiso(permisoId, aplicaOrganizacion);
    if (continuar) {
      return 1;
    }
    return 0;
  }
  




  public void release()
  {
    super.release();
    permisoId = null;
    aplicaOrganizacion = null;
    id = null;
  }
}
