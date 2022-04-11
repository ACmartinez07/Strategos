package com.visiongc.framework.web.struts.actions.usuarios;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.forms.usuarios.CambiarClaveUsuarioForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;














public final class CambiarClaveUsuarioAction
  extends VgcAction
{
  public CambiarClaveUsuarioAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    String forward = mapping.getParameter();
    

    CambiarClaveUsuarioForm cambiarClaveUsuarioForm = (CambiarClaveUsuarioForm)form;
    

    ActionMessages messages = getMessages(request);
    








    if (getForwardBack(request, 1, true).getPath().equals("")) {
      cambiarClaveUsuarioForm.setDesdeLogin(Boolean.valueOf(true));
    } else {
      cambiarClaveUsuarioForm.setDesdeLogin(Boolean.valueOf(false));
    }
    



    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
    

    Usuario usuario = (Usuario)usuariosService.load(Usuario.class, getUsuarioConectado(request).getUsuarioId());
    
    boolean bloqueado = !usuariosService.lockForUpdate(request.getSession().getId(), getUsuarioConectado(request).getUsuarioId(), null);
    
    cambiarClaveUsuarioForm.setBloqueado(new Boolean(bloqueado));
    
    if (usuario != null)
    {
      if (bloqueado) {
        String[] nombre = new String[1];
        
        nombre[0] = usuario.getFullName();
        
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.editarusuario.locked", nombre));
      }
      
      cambiarClaveUsuarioForm.setUsuarioId(usuario.getUsuarioId());

    }
    else
    {

      usuariosService.unlockObject(request.getSession().getId(), getUsuarioConectado(request).getUsuarioId());
      
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.editarusuario.notfound"));
      
      usuariosService.close();
      
      forward = "noencontrado";
    }
    usuariosService.close();
    
    saveMessages(request, messages);
    

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, false);
    }
    return mapping.findForward(forward);
  }
}
