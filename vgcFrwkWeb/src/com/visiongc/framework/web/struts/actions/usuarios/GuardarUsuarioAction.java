package com.visiongc.framework.web.struts.actions.usuarios;

import com.visiongc.authentication.ExternalAuthenticator;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.PwdHistoria;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.UsuarioGrupo;
import com.visiongc.framework.model.UsuarioGrupoPK;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.forms.usuarios.EditarUsuarioForm;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarUsuarioAction
  extends VgcAction
{
  private static final String ACTION_KEY = "GuardarUsuarioAction";
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    String forward = mapping.getParameter();
    
    EditarUsuarioForm editarUsuarioForm = (EditarUsuarioForm)form;
    
    ActionMessages messages = getMessages(request);
    
    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarUsuarioAction.ultimoTs");
    if ((ts == null) || (ts.equals(""))) {
      cancelar = true;
    } else if (ultimoTs != null) {
      if (ultimoTs.equals(ts)) {
        cancelar = true;
      }
    }
    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
    if (cancelar)
    {
      usuariosService.unlockObject(request.getSession().getId(), editarUsuarioForm.getUsuarioId());
      
      request.getSession().removeAttribute("editarUsuarioGrupos");
      request.getSession().removeAttribute("editarUsuarioOrganizacionRoot");
      request.getSession().removeAttribute("editarUsuarioArbolOrganizaciones");
      
      usuariosService.close();
      
      return getForwardBack(request, 1, true);
    }
    Usuario usuario = null;
    boolean nuevo = false;
    boolean salvarPwd = false;
    Usuario gestor = getUsuarioConectado(request);
    int respuesta = 10000;
    if ((editarUsuarioForm.getUsuarioId() != null) && (!editarUsuarioForm.getUsuarioId().equals(Long.valueOf("0"))))
    {
      usuario = (Usuario)usuariosService.load(Usuario.class, editarUsuarioForm.getUsuarioId());
    }
    else
    {
      if (!usuariosService.checkLicencia(request))
      {
        usuariosService.close();
        
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.editarusuario.limite.exedido"));
        saveMessages(request, messages);
        
        return getForwardBack(request, 1, false);
      }
      if (ExternalAuthenticator.getInstance().isActive())
      {
        String tipoAuthentication = ExternalAuthenticator.getInstance().getAuthenticateTipo();
        FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
        if ((tipoAuthentication.equals("particular")) && (!ExternalAuthenticator.getInstance().checkUserAuthenticateParticular(editarUsuarioForm.getUId(), frameworkService)))
        {
          frameworkService.close();
          usuariosService.close();
          
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.framework.usuarios.activodirectorio.validation.noexiste"));
          saveMessages(request, messages);
          
          return mapping.findForward(forward);
        }
        frameworkService.close();
      }
      nuevo = true;
      salvarPwd = true;
      usuario = new Usuario();
      usuario.setUsuarioId(new Long(0L));
      usuario.setUsuarioGrupos(new HashSet());
    }
    PwdHistoria pwdHistoria = new PwdHistoria();
    pwdHistoria.setFecha(new Date());
    pwdHistoria.setPwdPlain(editarUsuarioForm.getPwd());
    if ((!nuevo) && (!editarUsuarioForm.getPwd().equals(usuario.getPwdPlain())))
    {
      pwdHistoria.setUsuarioId(editarUsuarioForm.getUsuarioId());
      if (usuariosService.checkPwdHistoria(pwdHistoria, editarUsuarioForm.getReutilizacionContrasena().intValue()))
      {
        usuariosService.close();
        
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.framework.usuarios.cambiarclaveusuario.validation.pwdusado"));
        saveMessages(request, messages);
        forward = "crearUsuario";
        
        return mapping.findForward(forward);
      }
      salvarPwd = true;
    }
    usuario.setFullName(editarUsuarioForm.getFullName());
    usuario.setUId(editarUsuarioForm.getUId());
    usuario.setPwdPlain(editarUsuarioForm.getPwd());
    usuario.setUltimaModifPwd(new Date());
    usuario.setIsAdmin(editarUsuarioForm.getIsAdmin());
    usuario.setBloqueado(editarUsuarioForm.getBloqueado());
    usuario.setEstatus(editarUsuarioForm.getEstatus());
    usuario.setDeshabilitado(editarUsuarioForm.getDeshabilitado());
    usuario.setForzarCambiarpwd(editarUsuarioForm.getForzarCambiarpwd());
    usuario.getUsuarioGrupos().clear();
    
    String listaGrupos = editarUsuarioForm.getGrupos();
    if ((listaGrupos != null) && (!listaGrupos.equals("")))
    {
      String[] indexFila = listaGrupos.split(editarUsuarioForm.getSeparadorFilas());
      for (int i = 0; i < indexFila.length; i++) {
        if (indexFila[i].length() > 0)
        {
          String[] datosGrupo = indexFila[i].split(editarUsuarioForm.getSeparadorCampos());
          if (datosGrupo.length > 1)
          {
            UsuarioGrupo ug = new UsuarioGrupo();
            ug.setPk(new UsuarioGrupoPK(editarUsuarioForm.getUsuarioId(), new Long(datosGrupo[1]), new Long(datosGrupo[0])));
            
            ug.setModificado(new Date());
            ug.setModificadoId(gestor.getUsuarioId());
            usuario.getUsuarioGrupos().add(ug);
          }
        }
      }
    }
    respuesta = usuariosService.saveUsuario(usuario, gestor);
    if ((respuesta == 10000) && (nuevo)) {
      pwdHistoria.setUsuarioId(usuario.getUsuarioId());
    }
    if ((respuesta == 10000) && (salvarPwd)) {
      usuariosService.savePwdHistoria(pwdHistoria);
    }
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    ConfiguracionUsuario confUsuario = new ConfiguracionUsuario();
    ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
    pk.setConfiguracionBase("com.visiongc.framework.web.configuracion.Usuarios");
    pk.setObjeto("RestriccionUso");
    pk.setUsuarioId(usuario.getUsuarioId());
    
    confUsuario.setPk(pk);
    
    StringBuilder xml = new StringBuilder();
    xml.append("<?xml version='1.0' encoding='UTF-8' standalone='no'?><Configuraciones><Configuracion>");
    xml.append("<restriccionUsoDiaLunes>" + (editarUsuarioForm.getRestriccionUsoDiaLunes() != null ? editarUsuarioForm.getRestriccionUsoDiaLunes().toString() : "false") + "</restriccionUsoDiaLunes>");
    xml.append("<restriccionUsoDiaMartes>" + (editarUsuarioForm.getRestriccionUsoDiaMartes() != null ? editarUsuarioForm.getRestriccionUsoDiaMartes().toString() : "false") + "</restriccionUsoDiaMartes>");
    xml.append("<restriccionUsoDiaMiercoles>" + (editarUsuarioForm.getRestriccionUsoDiaMiercoles() != null ? editarUsuarioForm.getRestriccionUsoDiaMiercoles().toString() : "false") + "</restriccionUsoDiaMiercoles>");
    xml.append("<restriccionUsoDiaJueves>" + (editarUsuarioForm.getRestriccionUsoDiaJueves() != null ? editarUsuarioForm.getRestriccionUsoDiaJueves().toString() : "false") + "</restriccionUsoDiaJueves>");
    xml.append("<restriccionUsoDiaViernes>" + (editarUsuarioForm.getRestriccionUsoDiaViernes() != null ? editarUsuarioForm.getRestriccionUsoDiaViernes().toString() : "false") + "</restriccionUsoDiaViernes>");
    xml.append("<restriccionUsoDiaSabado>" + (editarUsuarioForm.getRestriccionUsoDiaSabado() != null ? editarUsuarioForm.getRestriccionUsoDiaSabado().toString() : "false") + "</restriccionUsoDiaSabado>");
    xml.append("<restriccionUsoDiaDomingo>" + (editarUsuarioForm.getRestriccionUsoDiaDomingo() != null ? editarUsuarioForm.getRestriccionUsoDiaDomingo().toString() : "false") + "</restriccionUsoDiaDomingo>");
    xml.append("<restriccionUsoHoraDesde>" + editarUsuarioForm.getRestriccionUsoHoraDesde().toString() + "</restriccionUsoHoraDesde>");
    xml.append("<restriccionUsoHoraHasta>" + editarUsuarioForm.getRestriccionUsoHoraHasta().toString() + "</restriccionUsoHoraHasta>");
    xml.append("</Configuracion></Configuraciones>");
    confUsuario.setData(xml.toString());
    if (respuesta == 10000) {
      frameworkService.saveConfiguracionUsuario(confUsuario, getUsuarioConectado(request));
    }
    frameworkService.close();
    String[] nombre = new String[1];
    nombre[0] = editarUsuarioForm.getFullName();
    if (respuesta == 10000)
    {
      usuariosService.unlockObject(request.getSession().getId(), editarUsuarioForm.getUsuarioId());
      forward = "exito";
      
      request.getSession().removeAttribute("editarUsuarioGrupos");
      request.getSession().removeAttribute("editarUsuarioOrganizacionRoot");
      request.getSession().removeAttribute("editarUsuarioArbolOrganizaciones");
      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.guardarusuario.insert.success", nombre));
        if (!editarUsuarioForm.isCopiar()) {
          forward = "crearUsuario";
        }
      }
      else
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.guardarusuario.update.success", nombre));
      }
    }
    else if (respuesta == 10003)
    {
      nombre[0] = usuario.getUId();
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.guardarusuario.error.dupkey", nombre));
    }
    usuariosService.close();
    
    saveMessages(request, messages);
    
    request.getSession().setAttribute("GuardarUsuarioAction.ultimoTs", ts);
    if (forward.equals("exito")) {
      return getForwardBack(request, 1, false);
    }
    return mapping.findForward(forward);
  }
}
