package com.visiongc.framework.web.struts.bloqueos.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.web.controles.SplitControl;
import com.visiongc.framework.web.struts.bloqueos.forms.GestionarBloqueosForm;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



public final class GestionarBloqueosAction
  extends VgcAction
{
  public GestionarBloqueosAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.clear();
    navBar.agregarUrl(url, nombre);
  }
  

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    getBarraAreas(request, "administracion").setBotonSeleccionado("bloqueos");
    

    String forward = mapping.getParameter();
    

    GestionarBloqueosForm gestionarBloqueosForm = (GestionarBloqueosForm)form;
    




    String atributoOrden = gestionarBloqueosForm.getAtributoOrden();
    String tipoOrden = gestionarBloqueosForm.getTipoOrden();
    

    if (atributoOrden == null) {
      atributoOrden = "instancia";
      gestionarBloqueosForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      gestionarBloqueosForm.setTipoOrden(tipoOrden);
    }
    


    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    
    PaginaLista paginaBloqueos = frameworkService.getBloqueos(atributoOrden, tipoOrden);
    
    frameworkService.close();
    
    request.getSession().setAttribute("paginaBloqueos", paginaBloqueos);
    
    SplitControl.setConfiguracion(request, "splitBloqueos");
    
    return mapping.findForward(forward);
  }
}
