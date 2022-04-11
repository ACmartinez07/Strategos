package com.visiongc.framework.web.struts.sesionesusuario.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EliminarBloqueosPorSesionUsuarioAction
  extends VgcAction
{
  private static final String ACTION_KEY = "EliminarBloqueosPorSesionUsuarioAction";
  
  public EliminarBloqueosPorSesionUsuarioAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    ActionMessages messages = getMessages(request);
    




    String sessionId = request.getParameter("sessionId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarBloqueosPorSesionUsuarioAction.ultimoTs");
    boolean cancelar = false;
    

    if ((ts == null) || (ts.equals(""))) {
      cancelar = true;
    } else if ((sessionId == null) || (sessionId.equals(""))) {
      cancelar = true;
    } else if ((ultimoTs != null) && 
      (ultimoTs.equals(sessionId + "&" + ts))) {
      cancelar = true;
    }
    

    if (cancelar)
    {
      return getForwardBack(request, 1, false);
    }
    
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    
    boolean borrados = frameworkService.unlockObject(sessionId);
    
    String[] args = new String[1];
    args[0] = sessionId;
    
    if (borrados)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.eliminarbloqueosporsesionusuario.success", args));
    }
    else {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.eliminarbloqueosporsesionusuario.fail", args));
    }
    

    frameworkService.close();
    

    saveMessages(request, messages);
    

    request.getSession().setAttribute("EliminarBloqueosPorSesionUsuarioAction.ultimoTs", sessionId + "&" + ts);
    

    return getForwardBack(request, 1, false);
  }
}
