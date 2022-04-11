package com.visiongc.framework.web.struts.actions.usuarios;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.auditoria.AuditoriaService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;



public class EliminarUsuarioAction
  extends VgcAction
{
  private static final String ACTION_KEY = "EliminarUsuarioAction";
  
  public EliminarUsuarioAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    ActionMessages messages = getMessages(request);
    

    String usuarioId = request.getParameter("usuarioId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarUsuarioAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;
    

    if ((ts == null) || (ts.equals(""))) {
      cancelar = true;
    } else if ((usuarioId == null) || (usuarioId.equals(""))) {
      cancelar = true;
    } else if (ultimoTs != null)
    {
      if (ultimoTs.equals(usuarioId + "&" + ts)) {
        cancelar = true;
      }
    }
    if (cancelar) {
      return getForwardBack(request, 1, false);
    }
    Usuario user = getUsuarioConectado(request);
    if (user.getUsuarioId().toString().equals(usuarioId))
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.eliminarusuario.noco0nectado"));
      saveMessages(request, messages);
      return getForwardBack(request, 1, false);
    }
    
    AuditoriaService auditoriaService = FrameworkServiceFactory.getInstance().openAuditoriaService();
    Map<String, Long> filtros = new HashMap();
    
    String[] ordenArray = new String[1];
    String[] tipoOrdenArray = new String[1];
    ordenArray[0] = "pk.fecha";
    tipoOrdenArray[0] = "DESC";
    
    filtros.put("usuarioId", new Long(usuarioId));
    PaginaLista paginaAuditorias = auditoriaService.getAuditoriasEventos(0, 0, ordenArray, tipoOrdenArray, true, filtros);
    
    auditoriaService.close();
    
    boolean usuarioTieneAuditoria = false;
    if (paginaAuditorias.getLista().size() > 0) {
      usuarioTieneAuditoria = true;
    }
    if (!usuarioTieneAuditoria)
    {

      UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
      

      bloqueado = !usuariosService.lockForDelete(request.getSession().getId(), usuarioId);
      
      Usuario usuario = (Usuario)usuariosService.load(Usuario.class, new Long(usuarioId));
      
      if (usuario != null)
      {
        String[] nombre = new String[1];
        nombre[0] = usuario.getFullName();
        
        if (bloqueado) {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.eliminarusuario.locked", nombre));
        }
        else
        {
          usuario.setUsuarioId(Long.valueOf(usuarioId));
          
          int res = usuariosService.deleteUsuario(usuario, getUsuarioConectado(request));
          
          usuariosService.unlockObject(request.getSession().getId(), usuarioId);
          
          if (res == 10004) {
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.eliminarusuario.related", nombre));
          } else {
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.eliminarusuario.success", nombre));
          }
        }
      }
      else {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.eliminarusuario.noencontrado"));
      }
      
      usuariosService.close();
    }
    else {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.eliminarusuario.related.auditoria"));
    }
    
    saveMessages(request, messages);
    

    request.getSession().setAttribute("EliminarUsuarioAction.ultimoTs", usuarioId + "&" + ts);
    

    return getForwardBack(request, 1, false);
  }
}
