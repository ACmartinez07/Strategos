package com.visiongc.framework.web.struts.actions.grupos;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Grupo;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.forms.grupos.EditarGrupoForm;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public final class MostrarPropiedadesGrupoAction
  extends VgcAction
{
  public MostrarPropiedadesGrupoAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    String forward = mapping.getParameter();
    

    EditarGrupoForm editarGrupoForm = (EditarGrupoForm)form;
    

    String grupoId = request.getParameter("grupoId");
    

    ActionMessages messages = new ActionMessages();
    

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    
    if ((grupoId == null) || (grupoId.equals(""))) {
      cancelar = true;
    }
    

    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    
    if (cancelar)
    {

      frameworkService.unlockObject(request.getSession().getId(), editarGrupoForm.getGrupoId());
      

      frameworkService.close();
      

      return getForwardBack(request, 1, true);
    }
    


    Grupo grupo = (Grupo)frameworkService.load(Grupo.class, new Long(grupoId));
    if (grupo != null)
    {
      editarGrupoForm.setGrupo(grupo.getGrupo());
      editarGrupoForm.setTotalPermisosAsociados(new Integer(grupo.getPermisos().size()));
      
      if (grupo.getCreado() != null) {
        editarGrupoForm.setCreado(VgcFormatter.formatearFecha(grupo.getCreado(), "formato.fecha.larga"));
        Usuario creadoUsuario = (Usuario)frameworkService.load(Usuario.class, grupo.getCreadoId());
        if (creadoUsuario != null) {
          editarGrupoForm.setCreadoUsuarioNombre(creadoUsuario.getFullName());
        }
      } else {
        editarGrupoForm.setCreado(null);
      }
      if (grupo.getModificado() != null) {
        editarGrupoForm.setModificado(VgcFormatter.formatearFecha(grupo.getModificado(), "formato.fecha.larga"));
        Usuario modificadoUsuario = (Usuario)frameworkService.load(Usuario.class, grupo.getModificadoId());
        if (modificadoUsuario != null) {
          editarGrupoForm.setModificadoUsuarioNombre(modificadoUsuario.getFullName());
        }
      } else {
        editarGrupoForm.setModificado(null);
      }
    } else {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.propiedadesgrupo.notfound"));
    }
    
    saveMessages(request, messages);
    
    return mapping.findForward(forward);
  }
}
