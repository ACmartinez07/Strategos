package com.visiongc.framework.web.struts.actions.grupos;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Grupo;
import com.visiongc.framework.model.Permiso;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.forms.grupos.EditarGrupoForm;
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







public class GuardarGrupoAction
  extends VgcAction
{
  private static final String ACTION_KEY = "GuardarGrupoAction";
  
  public GuardarGrupoAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    String forward = mapping.getParameter();
    

    EditarGrupoForm editarGrupoForm = (EditarGrupoForm)form;
    

    ActionMessages messages = getMessages(request);
    




    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarGrupoAction.ultimoTs");
    

    if ((ts == null) || (ts.equals(""))) {
      cancelar = true;
    } else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }
    


    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
    
    if (cancelar) {
      usuariosService.unlockObject(request.getSession().getId(), editarGrupoForm.getGrupoId());
      
      request.getSession().removeAttribute("editarGrupoForm");
      request.getSession().removeAttribute("editarGrupoPermisoRoot");
      request.getSession().removeAttribute("editarGrupoArbolPermisos");
      
      usuariosService.close();
      
      return getForwardBack(request, 1, true);
    }
    
    Grupo grupo = null;
    boolean nuevo = false;
    int respuesta = 10000;
    
    if ((editarGrupoForm.getGrupoId() != null) && (!editarGrupoForm.getGrupoId().equals(Long.valueOf("0"))))
    {
      grupo = (Grupo)usuariosService.load(Grupo.class, editarGrupoForm.getGrupoId());
    } else {
      nuevo = true;
      grupo = new Grupo();
      grupo.setGrupoId(new Long(0L));
      grupo.setPermisos(new HashSet());
    }
    
    grupo.setGrupo(editarGrupoForm.getGrupo());
    grupo.getPermisos().clear();
    

    String permisoRootId = editarGrupoForm.getPermisoIdRoot();
    if ((editarGrupoForm.getPermisos() != null) && (!editarGrupoForm.getPermisos().equals(""))) {
      String[] permisos = editarGrupoForm.getPermisos().split(editarGrupoForm.getSeparador());
      for (int i = 0; i < permisos.length; i++) {
        String permiso = permisos[i];
        if ((permiso != null) && (!permiso.equals("")) && (!permiso.equals(permisoRootId))) {
          Permiso per = new Permiso();
          per.setPermisoId(permiso);
          grupo.getPermisos().add(per);
        }
      }
    }
    
    respuesta = usuariosService.saveGrupo(grupo, (Usuario)request.getSession().getAttribute("usuario"));
    
    String[] nombre = new String[1];
    nombre[0] = grupo.getGrupo();
    
    if (respuesta == 10000)
    {
      usuariosService.unlockObject(request.getSession().getId(), editarGrupoForm.getGrupoId());
      forward = "exito";
      
      if (nuevo) {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.guardargrupo.insert.success", nombre));
        if (!editarGrupoForm.isCopiar()) {
          forward = "crearGrupo";
        }
      } else {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.guardargrupo.update.success", nombre));
        



        request.getSession().removeAttribute("editarGrupoForm");
        request.getSession().removeAttribute("editarGrupoPermisoRoot");
        request.getSession().removeAttribute("editarGrupoArbolPermisos");
      }
      
    }
    else if (respuesta == 10003) {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.guardargrupo.error.dupkey", nombre));
    }
    

    usuariosService.close();
    
    saveMessages(request, messages);
    

    request.getSession().setAttribute("GuardarGrupoAction.ultimoTs", ts);
    

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, false);
    }
    return mapping.findForward(forward);
  }
}
