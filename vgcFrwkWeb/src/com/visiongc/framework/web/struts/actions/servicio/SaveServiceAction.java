package com.visiongc.framework.web.struts.actions.servicio;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Servicio;
import com.visiongc.framework.model.Servicio.ServicioStatus;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;






public class SaveServiceAction
  extends VgcAction
{
  public SaveServiceAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    Servicio servicio = new Servicio();
    
    String funcion = request.getParameter("funcion");
    String[] fields = request.getParameter("data").split(servicio.getSeparador());
    Long usuarioId = null;
    Long fecha = null;
    String nombre = null;
    String res = "";
    
    if (fields.length > 0)
    {
      usuarioId = Long.valueOf(Long.parseLong(fields[0]));
      fecha = Long.valueOf(Long.parseLong(fields[1]));
      nombre = fields[2];
      
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
      if ((funcion.equals("completo")) || (funcion.equals("soloReporte")))
      {
        StringBuffer log = frameworkService.getLog(usuarioId, fecha, nombre, Byte.valueOf((byte)1));
        if (!log.toString().equals(""))
        {
          res = "Sucess";
          request.getSession().setAttribute("verArchivoLog", log);
        }
      }
      else {
        res = "Sucess";
      }
      if (res.equals("Sucess"))
      {
        if ((funcion.equals("completo")) || (funcion.equals("soloModificar")))
        {
          ActionMessages messages = getMessages(request);
          int respuesta = frameworkService.setServicioVisto(usuarioId, fecha, nombre, Servicio.ServicioStatus.getServicioStatusVisto());
          if (respuesta != 10000)
          {
            res = "";
            if (funcion.equals("completo")) {
              request.getSession().removeAttribute("verArchivoLog");
            }
            else {
              messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.framework.gestionarservicios.alerta.estatus.noactualizado"));
              saveMessages(request, messages);
              String forward = mapping.getParameter();
              return mapping.findForward(forward);
            }
          }
          else if (funcion.equals("soloModificar"))
          {
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.framework.gestionarservicios.alerta.estatus.actualizado"));
            saveMessages(request, messages);
          }
        }
      }
      frameworkService.close();
    }
    
    request.setAttribute("ajaxResponse", res);
    
    return mapping.findForward("ajaxResponse");
  }
}
