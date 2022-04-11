package com.visiongc.framework.web.struts.actions.grupos;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.forms.grupos.GestionarGruposForm;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public final class GestionarGruposAction extends VgcAction
{
  public GestionarGruposAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.agregarUrl(url, nombre, new Integer(2));
  }
  




  public org.apache.struts.action.ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    getBarraAreas(request, "administracion").setBotonSeleccionado("grupos");
    

    String forward = mapping.getParameter();
    

    GestionarGruposForm gestionarGruposForm = (GestionarGruposForm)form;
    




    String atributoOrden = gestionarGruposForm.getAtributoOrden();
    String tipoOrden = gestionarGruposForm.getTipoOrden();
    int pagina = gestionarGruposForm.getPagina();
    

    if (atributoOrden == null)
    {
      atributoOrden = "grupo";
      gestionarGruposForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null)
    {
      tipoOrden = "ASC";
      gestionarGruposForm.setTipoOrden(tipoOrden);
    }
    

    if (pagina < 1) {
      pagina = 1;
    }
    

    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
    
    PaginaLista paginaGrupos = usuariosService.getGrupos(pagina, atributoOrden, tipoOrden);
    
    gestionarGruposForm.setPagina(paginaGrupos.getNroPagina());
    
    paginaGrupos.setTamanoSetPaginas(5);
    
    usuariosService.close();
    
    request.setAttribute("paginaGrupos", paginaGrupos);
    
    return mapping.findForward(forward);
  }
}
