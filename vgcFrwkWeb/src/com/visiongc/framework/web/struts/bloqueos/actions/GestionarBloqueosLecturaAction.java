package com.visiongc.framework.web.struts.bloqueos.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.web.struts.bloqueos.forms.GestionarBloqueosLecturaForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class GestionarBloqueosLecturaAction
  extends VgcAction
{
  public GestionarBloqueosLecturaAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    String forward = mapping.getParameter();
    

    GestionarBloqueosLecturaForm gestionarBloqueosLecturaForm = (GestionarBloqueosLecturaForm)form;
    




    String atributoOrden = gestionarBloqueosLecturaForm.getAtributoOrdenLectura();
    String tipoOrden = gestionarBloqueosLecturaForm.getTipoOrdenLectura();
    

    if (atributoOrden == null) {
      atributoOrden = "pk.objetoId";
      gestionarBloqueosLecturaForm.setAtributoOrdenLectura(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      gestionarBloqueosLecturaForm.setTipoOrdenLectura(tipoOrden);
    }
    


    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    
    PaginaLista paginaBloqueosLectura = frameworkService.getBloqueosLectura(atributoOrden, tipoOrden);
    
    frameworkService.close();
    
    request.getSession().setAttribute("paginaBloqueosLectura", paginaBloqueosLectura);
    
    return mapping.findForward(forward);
  }
}
