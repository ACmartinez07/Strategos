package com.visiongc.framework.web.struts.actions.usuarios;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.CondicionType;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.forms.usuarios.GestionarUsuariosForm;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class GestionarUsuariosAction
  extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.clear();
    navBar.agregarUrl(url, nombre);
  }
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    getBarraAreas(request, "administracion").setBotonSeleccionado("usuarios");
    
    String forward = mapping.getParameter();
    
    String nombre1= request.getParameter("filtroNombre");
    GestionarUsuariosForm gestionarUsuariosForm = (GestionarUsuariosForm)form;
    
    String atributoOrden = gestionarUsuariosForm.getAtributoOrden();
    Byte filtroCondicionType = (gestionarUsuariosForm != null) && (gestionarUsuariosForm.getFiltro() != null) ? gestionarUsuariosForm.getFiltro().getCondicion() : null;
    Byte selectCondicionType = Byte.valueOf(filtroCondicionType != null ? filtroCondicionType.byteValue() : (request.getParameter("selectCondicionType") != null) && (request.getParameter("selectCondicionType") != "") ? Byte.parseByte(request.getParameter("selectCondicionType")) : CondicionType.getFiltroCondicionActivo());
    String filtroNombre = (gestionarUsuariosForm != null) && (gestionarUsuariosForm.getFiltro() != null) ? gestionarUsuariosForm.getFiltro().getNombre() : "";
    String nombre = filtroNombre != null ? filtroNombre : request.getParameter("filtroNombre") != null ? request.getParameter("filtroNombre") : "";
    String tipoOrden = gestionarUsuariosForm.getTipoOrden();
    
    int pagina = gestionarUsuariosForm.getPagina();
    if (atributoOrden == null)
    {
      atributoOrden = "fullName";
      gestionarUsuariosForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null)
    {
      tipoOrden = "ASC";
      gestionarUsuariosForm.setTipoOrden(tipoOrden);
    }
    if (pagina < 1) {
      pagina = 1;
    }
    FiltroForm filtro = new FiltroForm();
    filtro.setCondicion(selectCondicionType);
    if (nombre.equals("")) {
      if(nombre1 != null){
    	  filtro.setNombre(nombre1);
      }else{
    	  filtro.setNombre(null);
      }
      
    } else {
      filtro.setNombre(nombre);
    }
    gestionarUsuariosForm.setFiltro(filtro);
    
    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
    
    Map filtros = new HashMap();
    if (gestionarUsuariosForm.getFiltro().getNombre() != null) {
      filtros.put("fullName", gestionarUsuariosForm.getFiltro().getNombre());
    }
    if ((selectCondicionType != null) && (selectCondicionType.byteValue() != 0)) {
      filtros.put("estatus", selectCondicionType.byteValue() == 1 ? "0" : "1");
    }
    PaginaLista paginaUsuarios = usuariosService.getUsuarios(pagina, atributoOrden, tipoOrden, true, filtros);
    
    request.getSession().setAttribute("paginaUsuarios", paginaUsuarios);
    
    usuariosService.close();
    
    return mapping.findForward(forward);
  }
}
