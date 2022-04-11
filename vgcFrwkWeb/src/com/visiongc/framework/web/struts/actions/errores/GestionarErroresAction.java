package com.visiongc.framework.web.struts.actions.errores;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.web.struts.forms.errores.GestionarErroresForm;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



public final class GestionarErroresAction
  extends VgcAction
{
  public GestionarErroresAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.clear();
    navBar.agregarUrl(url, nombre);
  }
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    getBarraAreas(request, "administracion").setBotonSeleccionado("errores");
    

    String forward = mapping.getParameter();
    

    GestionarErroresForm gestionarErroresForm = (GestionarErroresForm)form;
    
    String atributoOrden = gestionarErroresForm.getAtributoOrden();
    
    String tipoOrden = gestionarErroresForm.getTipoOrden();
    
    int pagina = gestionarErroresForm.getPagina();
    

    if (atributoOrden == null)
    {
      atributoOrden = "errTimestamp";
      gestionarErroresForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null)
    {
      tipoOrden = "DESC";
      gestionarErroresForm.setTipoOrden(tipoOrden);
    }
    

    if (pagina < 1) {
      pagina = 1;
    }
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    
    PaginaLista paginaErrores = frameworkService.getErrores(pagina, 30, atributoOrden, tipoOrden, null);
    
    request.getSession().setAttribute("paginaErrores", paginaErrores);
    
    frameworkService.close();
    
    return mapping.findForward(forward);
  }
}
