package com.visiongc.framework.web.struts.actions.usuarios;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.PwdHistoria;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.forms.usuarios.CambiarClaveUsuarioForm;
import com.visiongc.framework.web.struts.iniciosesion.forms.ConfigurarInicioSesionForm;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;








public class GuardarClaveUsuarioAction
  extends VgcAction
{
  private static final String ACTION_KEY = "GuardarClaveUsuarioAction";
  
  public GuardarClaveUsuarioAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    String forward = mapping.getParameter();
    
    boolean cancelar = request.getParameter("cancelar") != null ? Boolean.parseBoolean(request.getParameter("cancelar")) : false;
    if (cancelar) {
      return getForwardBack(request, 1, true);
    }
    String ts = request.getParameter("ts");
    

    CambiarClaveUsuarioForm cambiarClaveUsuarioForm = (CambiarClaveUsuarioForm)form;
    

    ActionMessages messages = getMessages(request);
    

    Boolean desdeLogin = Boolean.valueOf(false);
    String pwdActual = "";
    String pwd = "";
    int reutilizacionContrasena = 0;
    Long usuarioId;
    if (request.getParameter("desdeLogin") != null)
    {
      desdeLogin = Boolean.valueOf(Boolean.parseBoolean(request.getParameter("desdeLogin").toString()));
      if (desdeLogin.booleanValue()) {
        usuarioId = ((Usuario)request.getSession().getAttribute("usuario")).getUsuarioId();
      } else
        usuarioId = new Long(request.getParameter("usuarioId").toString());
      pwdActual = request.getParameter("pwdActual").toString();
      pwd = request.getParameter("pwd").toString();
      reutilizacionContrasena = Integer.parseInt(request.getParameter("reutilizacionContrasena").toString());
    }
    else
    {
      usuarioId = cambiarClaveUsuarioForm.getUsuarioId();
      pwdActual = cambiarClaveUsuarioForm.getPwdActual();
      pwd = cambiarClaveUsuarioForm.getPwd();
      reutilizacionContrasena = ((ConfigurarInicioSesionForm)request.getSession().getAttribute("configurarInicioSesionForm")).getReutilizacionContrasena().intValue();
    }
    

    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
    
    Usuario usuario = null;
    Usuario gestor = getUsuarioConectado(request);
    int respuesta = 10000;
    
    usuario = (Usuario)usuariosService.load(Usuario.class, usuarioId);
    String[] nombre = new String[1];
    nombre[0] = usuario.getFullName();
    
    if (usuario.getPwdPlain().equals(pwdActual))
    {
      usuario.setPwdPlain(pwd);
      usuario.setUltimaModifPwd(new Date());
      usuario.setForzarCambiarpwd(new Boolean(false));
      

      PwdHistoria pwdHistoria = new PwdHistoria();
      pwdHistoria.setFecha(new Date());
      pwdHistoria.setUsuarioId(usuarioId);
      pwdHistoria.setPwdPlain(pwd);
      if (usuariosService.checkPwdHistoria(pwdHistoria, reutilizacionContrasena)) {
        respuesta = 99999;
      }
      else
      {
        try {
          respuesta = usuariosService.savePwdHistoria(pwdHistoria);
          if (respuesta == 10000) {
            respuesta = usuariosService.saveUsuario(usuario, gestor);
          } else {
            respuesta = 99999;
          }
        }
        catch (Throwable t) {
          respuesta = 10003;
        }
        
        if (respuesta == 10000)
        {
          usuariosService.unlockObject(request.getSession().getId(), usuarioId);
          forward = "exito";
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.cambiarclaveusuario.success", nombre));
        }
        else if (respuesta == 10003)
        {
          nombre[0] = usuario.getUId();
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.guardarusuario.error.dupkey", nombre));
        }
      }
    }
    else {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.cambiarclaveusuario.pwdincorrecto"));
    }
    usuariosService.close();
    
    saveMessages(request, messages);
    

    request.getSession().setAttribute("GuardarClaveUsuarioAction.ultimoTs", ts);
    cambiarClaveUsuarioForm.setRespuesta(usuario.getFullName() + "|" + respuesta);
    
    request.setAttribute("ajaxResponse", usuario.getFullName() + "|" + respuesta);
    

    return mapping.findForward("ajaxResponse");
  }
}
