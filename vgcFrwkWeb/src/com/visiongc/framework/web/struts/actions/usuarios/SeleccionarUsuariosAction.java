package com.visiongc.framework.web.struts.actions.usuarios;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.forms.usuarios.SeleccionarUsuariosForm;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;








public final class SeleccionarUsuariosAction
  extends VgcAction
{
  public SeleccionarUsuariosAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    SeleccionarUsuariosForm seleccionarUsuariosForm = (SeleccionarUsuariosForm)form;
    
    String atributoOrden = (String)PropertyUtils.getSimpleProperty(form, "atributoOrden");
    
    String tipoOrden = (String)PropertyUtils.getSimpleProperty(form, "tipoOrden");
    

    if (atributoOrden == null)
    {
      atributoOrden = "fullName";
      seleccionarUsuariosForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null)
    {
      tipoOrden = "ASC";
      seleccionarUsuariosForm.setTipoOrden(tipoOrden);
    }
    
    String organizacionId = String.valueOf(PropertyUtils.getSimpleProperty(form, "organizacionId"));
    
    String mostrarAdministradores = String.valueOf(PropertyUtils.getSimpleProperty(form, "mostrarAdministradores"));
    
    String filtroNombre = String.valueOf(PropertyUtils.getSimpleProperty(form, "filtroNombre"));
    
    String desdeResponsables = String.valueOf(PropertyUtils.getSimpleProperty(form, "desdeResponsable"));
    
    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
    
    Map<String, Object> filtros = new HashMap();
    
    if(desdeResponsables != null && desdeResponsables.equals("true")) {
    	filtros.put("estatus", 0);
    	
    	if ((filtroNombre != null) && (!filtroNombre.equals("")) && (!filtroNombre.equals("null")))
   	     filtros.put("fullName", filtroNombre);   
    	
    }else {
    	if ((organizacionId != null) && (!organizacionId.equals("")))
    	     filtros.put("organizacionId", organizacionId);
    	if ((mostrarAdministradores != null) && (!mostrarAdministradores.equals(""))) {
    	     filtros.put("isAdmin", Boolean.valueOf(Boolean.parseBoolean(mostrarAdministradores)));
    	}
    }
    
    
    PaginaLista paginaUsuarios = usuariosService.getUsuarios(0, atributoOrden, tipoOrden, false, filtros);
    
    usuariosService.close();
    
    request.setAttribute("paginaSeleccionarUsuarios", paginaUsuarios);
    
    return mapping.findForward("exito");
  }
}
