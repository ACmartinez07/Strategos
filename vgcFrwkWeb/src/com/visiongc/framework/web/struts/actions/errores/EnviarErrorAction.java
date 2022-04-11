package com.visiongc.framework.web.struts.actions.errores;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.configuracion.VgcConfiguraciones;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Error;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;








public final class EnviarErrorAction
  extends VgcAction
{
  public EnviarErrorAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    ActionMessages messages = getMessages(request);
    
    MessageResources messageResources = getResources(request);
    
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService(getLocale(request));
    
    Map filtros = new HashMap();
    
    Date fecha = new Date();
    
    fecha.setTime(Long.parseLong(request.getParameter("errTimestamp")));
    
    filtros.put("objetoCompleto", new Boolean(true));
    filtros.put("errTimestamp", fecha);
    
    PaginaLista pagina = frameworkService.getErrores(0, 0, "errTimestamp", "desc", filtros);
    
    if (pagina.getLista().size() > 0) {
      Error error = (Error)pagina.getLista().get(0);
      enviarError(request, frameworkService, messages, messageResources, error);
    } else {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.enviarerror.noencontrado"));
    }
    
    frameworkService.close();
    
    saveMessages(request, messages);
    
    return getForwardBack(request, 1, false);
  }
  
  private void enviarError(HttpServletRequest request, FrameworkService frameworkService, ActionMessages messages, MessageResources messageResources, Error error)
  {
    String detalleError = "";
    
    if ((error.getErrTimestamp() != null) && (!error.getErrTimestamp().equals(""))) {
      detalleError = detalleError + messageResources.getMessage("reporte.framework.errores.error.fecha") + ":\n";
      detalleError = detalleError + error.getErrTimestamp().toString() + "\n\n";
    }
    if ((error.getErrNumber() != null) && (!error.getErrNumber().equals(""))) {
      detalleError = detalleError + messageResources.getMessage("reporte.framework.errores.error.numero") + ":\n";
      detalleError = detalleError + error.getErrNumber() + "\n\n";
    }
    if ((error.getErrDescription() != null) && (!error.getErrDescription().equals(""))) {
      detalleError = detalleError + messageResources.getMessage("reporte.framework.errores.error.descripcion") + ":\n";
      detalleError = detalleError + error.getErrDescription() + "\n\n";
    }
    if ((error.getErrCause() != null) && (!error.getErrCause().equals(""))) {
      detalleError = detalleError + messageResources.getMessage("reporte.framework.errores.error.causa") + ":\n";
      detalleError = detalleError + error.getErrCause() + "\n\n";
    }
    if ((error.getErrSource() != null) && (!error.getErrSource().equals(""))) {
      detalleError = detalleError + messageResources.getMessage("reporte.framework.errores.error.fuente") + ":\n";
      detalleError = detalleError + error.getErrSource() + "\n\n";
    }
    if ((error.getErrUserId() != null) && (!error.getErrUserId().equals(""))) {
      detalleError = detalleError + messageResources.getMessage("reporte.framework.errores.error.usuario") + ":\n";
      detalleError = detalleError + error.getErrUserId() + "\n\n";
    }
    
    if ((error.getErrStackTrace() != null) && (!error.getErrStackTrace().equals(""))) {
      detalleError = detalleError + messageResources.getMessage("reporte.framework.errores.error.trazapila") + ":\n";
      detalleError = detalleError + error.getErrStackTrace() + "\n\n";
    }
    
    String emailCliente = VgcConfiguraciones.getInstance().getValor("cliente.email", "emailCliente@siteCliente.com", getUsuarioConectado(request));
    String emailSoporte = VgcConfiguraciones.getInstance().getValor("soporte.email", "soporte@visiongc.net", getUsuarioConectado(request));
    String asunto = messageResources.getMessage("action.framework.errores.enviarerror.email.asunto");
    int resultado = frameworkService.sendMail(emailCliente, emailSoporte, null, null, asunto, detalleError);
    
    if (resultado == 10000) {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(VgcResourceManager.getResourceApp("mail.send.ok"), false));
    } else if (resultado == 10020) {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(VgcResourceManager.getResourceApp("mail.send.noconf"), false));
    } else if (resultado == 10006) {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(VgcResourceManager.getResourceApp("mail.send.error.autenticacion"), false));
    } else if (resultado == 10001) {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(VgcResourceManager.getResourceApp("mail.send.error"), false));
    } else {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(VgcResourceManager.getResourceApp("mail.send.error"), false));
    }
  }
}
