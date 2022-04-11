package com.visiongc.framework.web.struts.sesionesusuario.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.web.struts.sesionesusuario.forms.GestionarSesionesUsuarioForm;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;





public final class GestionarSesionesUsuarioAction
  extends VgcAction
{
  public GestionarSesionesUsuarioAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.clear();
    navBar.agregarUrl(url, nombre);
  }
  

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    getBarraAreas(request, "administracion").setBotonSeleccionado("sesionesUsuario");
    

    String forward = mapping.getParameter();
    

    GestionarSesionesUsuarioForm gestionarSesionesUsuarioForm = (GestionarSesionesUsuarioForm)form;
    




    String atributoOrden = gestionarSesionesUsuarioForm.getAtributoOrden();
    String tipoOrden = gestionarSesionesUsuarioForm.getTipoOrden();
    int pagina = gestionarSesionesUsuarioForm.getPagina();
    

    if (atributoOrden == null) {
      atributoOrden = "loginTs";
      gestionarSesionesUsuarioForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      gestionarSesionesUsuarioForm.setTipoOrden(tipoOrden);
    }
    

    if (pagina < 1) {
      pagina = 1;
    }
    

    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    
    PaginaLista paginaSesiones = frameworkService.getUserSessions(pagina, 25, atributoOrden, tipoOrden);
    
    paginaSesiones.setTamanoSetPaginas(5);
    
    gestionarSesionesUsuarioForm.setPagina(paginaSesiones.getNroPagina());
    
    request.setAttribute("paginaSesiones", paginaSesiones);
    
    frameworkService.close();
    
    return mapping.findForward(forward);
  }
}
