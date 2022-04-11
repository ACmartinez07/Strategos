package com.visiongc.framework.web.struts.bloqueos.actions;

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
import org.apache.struts.util.MessageResources;

public class EliminarBloqueoAction
  extends VgcAction
{
  private static final String ACTION_KEY = "EliminarBloqueoAction";
  
  public EliminarBloqueoAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    super.execute(mapping, form, request, response);
    

    ActionMessages messages = getMessages(request);
    




    String sessionId = request.getParameter("sessionId");
    String objetoId = request.getParameter("objetoId");
    String tipo = request.getParameter("tipo");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarBloqueoAction.ultimoTs");
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
    
    boolean deLectura = (tipo == null) || ((!tipo.equalsIgnoreCase("D")) && (!tipo.equalsIgnoreCase("U")));
    
    String[] args = new String[2];
    
    if (deLectura) {
      args[0] = objetoId;
      args[1] = sessionId;
    } else {
      args[0] = objetoId;
      if (tipo.equalsIgnoreCase("D")) {
        args[1] = getResources(request).getMessage("action.eliminarbloqueo.tipo.eliminacion");
      } else {
        args[1] = getResources(request).getMessage("action.eliminarbloqueo.tipo.modificacion");
      }
    }
    


    boolean borrado = frameworkService.deleteBloqueo(sessionId, objetoId, tipo);
    
    if (borrado) {
      if (deLectura)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarbloqueo.lectura.success", args));
      }
      else {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarbloqueo.success", args));
      }
    }
    else if (deLectura)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarbloqueo.lectura.fail", args));
    }
    else {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarbloqueo.fail", args));
    }
    

    frameworkService.close();
    

    saveMessages(request, messages);
    

    request.getSession().setAttribute("EliminarBloqueoAction.ultimoTs", sessionId + "&" + ts);
    

    return getForwardBack(request, 1, false);
  }
}
