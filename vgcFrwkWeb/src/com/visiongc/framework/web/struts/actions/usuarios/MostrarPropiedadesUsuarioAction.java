package com.visiongc.framework.web.struts.actions.usuarios;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.UsuarioGrupo;
import com.visiongc.framework.model.UsuarioGrupoPK;
import com.visiongc.framework.web.struts.forms.usuarios.EditarUsuarioForm;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class MostrarPropiedadesUsuarioAction
  extends VgcAction
{
  public MostrarPropiedadesUsuarioAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    String forward = mapping.getParameter();
    

    EditarUsuarioForm editarUsuarioForm = (EditarUsuarioForm)form;
    

    String usuarioId = request.getParameter("usuarioId");
    

    ActionMessages messages = getMessages(request);
    

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    
    if ((usuarioId == null) || (usuarioId.equals(""))) {
      cancelar = true;
    }
    

    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    
    if (cancelar)
    {

      frameworkService.unlockObject(request.getSession().getId(), editarUsuarioForm.getUsuarioId());
      

      frameworkService.close();
      

      return getForwardBack(request, 1, true);
    }
    


    Usuario usuario = (Usuario)frameworkService.load(Usuario.class, new Long(usuarioId));
    if (usuario != null)
    {

      editarUsuarioForm.setFullName(usuario.getFullName());
      editarUsuarioForm.setUId(usuario.getUId());
      
      if (usuario.getCreado() != null) {
        editarUsuarioForm.setCreado(VgcFormatter.formatearFecha(usuario.getCreado(), "formato.fecha.larga"));
        Usuario creadoUsuario = (Usuario)frameworkService.load(Usuario.class, usuario.getCreadoId());
        if (creadoUsuario != null) {
          editarUsuarioForm.setCreadoUsuarioNombre(creadoUsuario.getFullName());
        }
      } else {
        editarUsuarioForm.setCreado(null);
      }
      if (editarUsuarioForm.getModificado() != null) {
        editarUsuarioForm.setModificado(VgcFormatter.formatearFecha(usuario.getModificado(), "formato.fecha.larga"));
        Usuario modificadoUsuario = (Usuario)frameworkService.load(Usuario.class, usuario.getModificadoId());
        if (modificadoUsuario != null) {
          editarUsuarioForm.setModificadoUsuarioNombre(modificadoUsuario.getFullName());
        }
      } else {
        editarUsuarioForm.setModificado(null);
      }
      editarUsuarioForm.setIsAdmin(usuario.getIsAdmin());
      editarUsuarioForm.setBloqueado(usuario.getBloqueado());
      editarUsuarioForm.setEstatus(usuario.getEstatus());
      editarUsuarioForm.setTotalGruposAsociados(new Integer(usuario.getUsuarioGrupos().size()));
      Map organizacionesAsociadas = new HashMap();
      for (Iterator iter = usuario.getUsuarioGrupos().iterator(); iter.hasNext();) {
        UsuarioGrupo usuarioGrupo = (UsuarioGrupo)iter.next();
        organizacionesAsociadas.put(usuarioGrupo.getPk().getOrganizacionId(), usuarioGrupo.getPk().getOrganizacionId());
      }
      editarUsuarioForm.setTotalOrganizacionesAsociadas(new Integer(organizacionesAsociadas.size()));
      editarUsuarioForm.setBloqueado(new Boolean(false));
    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.propiedadesusuario.notfound"));
      
      forward = "noencontrado";
    }
    

    frameworkService.close();
    

    saveMessages(request, messages);
    
    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, false);
    }
    return mapping.findForward(forward);
  }
}
