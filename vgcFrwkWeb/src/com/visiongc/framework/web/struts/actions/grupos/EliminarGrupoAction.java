package com.visiongc.framework.web.struts.actions.grupos;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Grupo;
import com.visiongc.framework.usuarios.UsuariosService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class EliminarGrupoAction
  extends VgcAction
{
  private static final String ACTION_KEY = "EliminarGrupoAction";
  
  public EliminarGrupoAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    ActionMessages messages = getMessages(request);
    




    String grupoId = request.getParameter("grupoId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarGrupoAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;
    

    if ((ts == null) || (ts.equals(""))) {
      cancelar = true;
    } else if ((grupoId == null) || (grupoId.equals(""))) {
      cancelar = true;
    } else if ((ultimoTs != null) && 
      (ultimoTs.equals(grupoId + "&" + ts))) {
      cancelar = true;
    }
    

    if (cancelar)
    {
      return getForwardBack(request, 1, false);
    }
    

    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
    

    bloqueado = !usuariosService.lockForDelete(request.getSession().getId(), grupoId);
    
    Grupo grupo = (Grupo)usuariosService.load(Grupo.class, new Long(grupoId));
    
    if (grupo != null) {
      String[] nombre = new String[1];
      nombre[0] = grupo.getGrupo();
      
      if (bloqueado)
      {

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.eliminargrupo.locked", nombre));
      }
      else {
        grupo.setGrupoId(Long.valueOf(grupoId));
        
        int resultado = usuariosService.deleteGrupo(grupo, getUsuarioConectado(request));
        
        usuariosService.unlockObject(request.getSession().getId(), grupoId);
        
        if (resultado == 10004)
        {



          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.eliminargrupo.related", nombre));
        }
        else {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.eliminargrupo.success", nombre));
        }
      }
    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.eliminargrupo.noencontrado"));
    }
    


    usuariosService.close();
    

    saveMessages(request, messages);
    

    request.getSession().setAttribute("EliminarGrupoAction.ultimoTs", grupoId + "&" + ts);
    

    return getForwardBack(request, 1, false);
  }
}
