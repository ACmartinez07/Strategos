package com.visiongc.framework.web.struts.actions.grupos;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Grupo;
import com.visiongc.framework.model.Permiso;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.forms.grupos.EditarGrupoForm;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;














public final class EditarGrupoAction
  extends VgcAction
{
  public EditarGrupoAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    String forward = mapping.getParameter();
    
    EditarGrupoForm editarGrupoForm = (EditarGrupoForm)form;
    

    ActionMessages messages = getMessages(request);
    
    String grupoId = request.getParameter("grupoId");
    
    editarGrupoForm.setCopiar(mapping.getPath().indexOf("copiar") > -1);
    
    boolean bloqueado = false;
    
    if ((grupoId != null) && (!grupoId.equals("")) && (!grupoId.equals("0")))
    {

      UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
      
      if (!editarGrupoForm.isCopiar()) {
        bloqueado = !usuariosService.lockForUpdate(request.getSession().getId(), grupoId, null);
      }
      editarGrupoForm.setBloqueado(new Boolean(bloqueado));
      

      Grupo grupo = (Grupo)usuariosService.load(Grupo.class, new Long(grupoId));
      
      if (grupo != null)
      {
        if (bloqueado)
        {
          String[] nombre = new String[1];
          nombre[0] = grupo.getGrupo();
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.editargrupo.locked", nombre));
        }
        
        Set<?> permisos = grupo.getPermisos();
        

        String listaPermisos = ":";
        for (Iterator<?> i = permisos.iterator(); i.hasNext();)
        {
          Permiso per = (Permiso)i.next();
          listaPermisos = listaPermisos + per.getPermisoId() + ":";
        }
        
        if (!editarGrupoForm.isCopiar())
        {
          editarGrupoForm.setGrupoId(grupo.getGrupoId());
          editarGrupoForm.setGrupo(grupo.getGrupo());
        }
        else
        {
          editarGrupoForm.setGrupoId(new Long(0L));
          editarGrupoForm.setGrupo(getResources(request).getMessage("commons.copyof") + " " + grupo.getGrupo());
        }
        editarGrupoForm.setPermisos(listaPermisos);
        
        usuariosService.close();
      }
      else
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.editargrupo.notfound"));
        saveMessages(request, messages);
        
        usuariosService.close();
        
        return mapping.findForward("noencontrado");
      }
    }
    else {
      editarGrupoForm.clear();
    }
    publishArbolPermisos(request, editarGrupoForm);
    
    saveMessages(request, messages);
    

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, false);
    }
    return mapping.findForward(forward);
  }
  




  private void publishArbolPermisos(HttpServletRequest request, EditarGrupoForm editarGrupoForm)
  {
    try
    {
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
      
      Set<?> roots = frameworkService.getPermisosRoot(true);
      
      frameworkService.close();
      

      Permiso permiso = new Permiso();
      permiso.setPermisoId(editarGrupoForm.getPermisoIdRoot());
      permiso.setPermiso(getResources(request).getMessage("action.framework.editargrupo.permisoroot"));
      
      permiso.setHijos(roots);
      
      request.getSession().setAttribute("editarGrupoPermisoRoot", permiso);
      TreeviewWeb.publishTree("editarGrupoArbolPermisos", "PERMISOS", "session", request, true);
      
      String listaPadresHijos = abrirArbolPermisos(permiso.getHijos(), "editarGrupoArbolPermisos", "session", request);
      
      editarGrupoForm.setListaPadresHijosPermisos(listaPadresHijos);

    }
    catch (Throwable t)
    {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
  }
  









  private String abrirArbolPermisos(Set<?> conj, String nameObject, String scope, HttpServletRequest request)
  {
    String listaPadresHijos = "";
    
    try
    {
      for (Iterator<?> i = conj.iterator(); i.hasNext();)
      {
        Permiso hijo = (Permiso)i.next();
        
        if (hijo.getPadre() != null)
        {
          listaPadresHijos = listaPadresHijos + ":" + hijo.getPadreId() + "#hijo#" + hijo.getPermisoId() + ":";
        }
        if (hijo.getHijos().size() > 0)
        {
          TreeviewWeb.publishTree(nameObject, hijo.getPermisoId(), scope, request);
          listaPadresHijos = listaPadresHijos + abrirArbolPermisos(hijo.getHijos(), nameObject, scope, request);
        }
        
      }
    }
    catch (Throwable t)
    {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return listaPadresHijos;
  }
}
