package com.visiongc.commons.struts;

import com.visiongc.commons.util.ObjetoAbstracto;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.UserSession;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.permisologia.OrganizacionPermisologia;
import com.visiongc.framework.util.PermisologiaUsuario;
import com.visiongc.framework.web.SessionsWatcher;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.faces.application.FacesTilesRequestProcessor;









public class VgcRequestProcessor
  extends FacesTilesRequestProcessor
{
  private Usuario usuario = null;
  private UserSession userSession = null;
  
  public VgcRequestProcessor() {}
  
  protected boolean processPreprocess(HttpServletRequest request, HttpServletResponse response) {
    boolean sinPermiso = true;
    boolean timeout = false;
    boolean permisoNoConfigurado = false;
    
    SessionsWatcher.getInstance().refreshSession(request.getSession().getId());
    

    Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
    

    OrganizacionPermisologia organizacion = (OrganizacionPermisologia)request.getSession().getAttribute("organizacion");
    

    StrutsActionRight permiso = null;
    
    try
    {
      permiso = DefaultMapStrutsActionRight.getInstance().getPermisoPorPath(processPath(request, response));
    }
    catch (Exception localException) {}
    



    boolean isFaces = request.getRequestURI().indexOf(".faces") > 0;
    
    if (permiso != null)
    {
      isFaces = false;
    }
    
    if ((permiso == null) && (!isFaces))
    {




      permisoNoConfigurado = true;
    }
    else if ((permiso == null) && (isFaces))
    {





      sinPermiso = false;
    }
    else if (permiso.getNoAplica())
    {









      sinPermiso = false;
    }
    else if ((usuario != null) && (usuario.getUsuarioId() != null))
    {

      boolean isAdmin = usuario.getIsAdmin().booleanValue();
      String organizacionId = null;
      String permisoId = null;
      
      if ((permiso != null) && (permiso.getAplicaOrganizacion()))
      {




        if ((organizacion != null) && (organizacion.getOrganizacionId() != null)) {
          organizacionId = organizacion.getOrganizacionId().toString();
        }
      }
      if (usuario.getUsuarioId() != null)
      {
        if (isAdmin) {
          sinPermiso = false;
        }
        else {
          if (permiso != null) {
            permisoId = permiso.getPermisoId();
          }
          
          PermisologiaUsuario pu = (PermisologiaUsuario)request.getSession().getAttribute("com.visiongc.framework.web.PERMISOLOGIA_USUARIO");
          




          if (organizacionId == null)
          {
            sinPermiso = !pu.tienePermiso(permisoId);
            if (sinPermiso)
              sinPermiso = !pu.tienePermiso("CONFIGURACION");
            if (sinPermiso) {
              sinPermiso = !pu.tienePermiso("ORGANIZACION");
            }
            

          }
          else
          {

            sinPermiso = !pu.tienePermiso(permisoId, Long.parseLong(organizacionId));
          }
          
        }
      }
    }
    else if (usuario == null)
    {




      timeout = true;
    }
    


    try
    {
      if (permisoNoConfigurado)
      {
        request.setAttribute("actionUrl", request.getRequestURI());
        request.setAttribute("actionPath", processPath(request, response));
        doForward("/paginas/comunes/permisoNoConfigurado.jsp", request, response);
        return false;
      }
      



      if (timeout)
      {
        doForward("/paginas/comunes/sessionTimeout.jsp", request, response);
        return false;
      }
      
      if (sinPermiso)
      {
        doForward("/paginas/comunes/permisoNegado.jsp", request, response);
        return false;
      }
    }
    catch (Throwable t)
    {
      throw new ChainedRuntimeException("No se pudo hacer forward de permiso negado o timeout de session", t);
    }
    
    return true;
  }
  




  protected ActionForward processActionPerform(HttpServletRequest request, HttpServletResponse response, Action action, ActionForm form, ActionMapping mapping)
    throws IOException, ServletException
  {
    try
    {
      HttpSession httpSession = request.getSession(false);
      if (httpSession != null)
      {
        if (usuario == null)
          usuario = ((Usuario)request.getSession().getAttribute("usuario"));
        if (userSession == null)
        {
          FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
          userSession = ((UserSession)frameworkService.load(UserSession.class, httpSession.getId()));
          frameworkService.close();
        }
        
        if ((usuario != null) && (!usuario.getPermitirConeccionVirtual().booleanValue()) && (userSession == null))
        {
          request.getSession().invalidate();
          return mapping.findForward("ingreso");
        }
      }
      
      return action.execute(mapping, form, request, response);
    }
    catch (Throwable t)
    {
      Exception e = new Exception(ObjetoAbstracto.getClassSimpleName(t) + ": " + t.getMessage(), t.getCause());
      e.setStackTrace(t.getStackTrace());
      return processException(request, response, e, form, mapping);
    }
  }
}
