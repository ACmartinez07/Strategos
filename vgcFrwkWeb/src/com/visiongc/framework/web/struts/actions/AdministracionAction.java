package com.visiongc.framework.web.struts.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.util.PermisologiaUsuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;







public final class AdministracionAction
  extends VgcAction
{
  public AdministracionAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    request.getSession().removeAttribute("alerta");
    
    String forward = mapping.getParameter();
    
    PermisologiaUsuario pu = (PermisologiaUsuario)request.getSession().getAttribute("com.visiongc.framework.web.PERMISOLOGIA_USUARIO");
    
    if (!pu.tienePermiso("USUARIO"))
    {
      if (pu.tienePermiso("GRUPO")) {
        forward = "gestionarGruposAction";
      } else if (pu.tienePermiso("CONFIGURACION_SET")) {
        forward = "configurarInicioSesionAction";
      } else if (pu.tienePermiso("ERROR")) {
        forward = "gestionarErroresAction";
      } else if (pu.tienePermiso("SERVICIO")) {
        forward = "gestionarServiciosAction";
      } else {
        forward = "gestionarAuditoriasAction";
      }
    }
    return mapping.findForward(forward);
  }
}
