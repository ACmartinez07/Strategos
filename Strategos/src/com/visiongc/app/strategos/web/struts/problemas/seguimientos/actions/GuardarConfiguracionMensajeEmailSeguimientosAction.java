package com.visiongc.app.strategos.web.struts.problemas.seguimientos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosSeguimientosService;
import com.visiongc.app.strategos.problemas.model.ConfiguracionMensajeEmailSeguimientos;
import com.visiongc.app.strategos.web.struts.problemas.seguimientos.forms.EditarConfiguracionMensajeEmailSeguimientosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarConfiguracionMensajeEmailSeguimientosAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarConfiguracionMensajeEmailSeguimientosAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    EditarConfiguracionMensajeEmailSeguimientosForm editarConfiguracionMensajeEmailSeguimientosForm = (EditarConfiguracionMensajeEmailSeguimientosForm)form;

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarConfiguracionMensajeEmailSeguimientosAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosSeguimientosService strategosSeguimientosService = StrategosServiceFactory.getInstance().openStrategosSeguimientosService();

    if (cancelar)
    {
      strategosSeguimientosService.unlockObject(request.getSession().getId(), editarConfiguracionMensajeEmailSeguimientosForm.getMensajeId());

      strategosSeguimientosService.close();

      return getForwardBack(request, 1, true);
    }

    ConfiguracionMensajeEmailSeguimientos configuracionMensajeEmailSeguimientos = new ConfiguracionMensajeEmailSeguimientos();
    int respuesta = 10000;

    if ((editarConfiguracionMensajeEmailSeguimientosForm.getMensajeId() != null) && (!editarConfiguracionMensajeEmailSeguimientosForm.getMensajeId().equals(Long.valueOf("0"))))
    {
      configuracionMensajeEmailSeguimientos = (ConfiguracionMensajeEmailSeguimientos)strategosSeguimientosService.load(ConfiguracionMensajeEmailSeguimientos.class, editarConfiguracionMensajeEmailSeguimientosForm.getMensajeId());
    }
    else
    {
      configuracionMensajeEmailSeguimientos = new ConfiguracionMensajeEmailSeguimientos();
      configuracionMensajeEmailSeguimientos.setMensajeId(new Long(0L));
    }

    configuracionMensajeEmailSeguimientos.setDescripcion(editarConfiguracionMensajeEmailSeguimientosForm.getDescripcion());
    configuracionMensajeEmailSeguimientos.setMensaje(editarConfiguracionMensajeEmailSeguimientosForm.getMensaje());
    configuracionMensajeEmailSeguimientos.setAcuseRecibo(editarConfiguracionMensajeEmailSeguimientosForm.getAcuseRecibo());
    configuracionMensajeEmailSeguimientos.setSoloAuxiliar(editarConfiguracionMensajeEmailSeguimientosForm.getSoloAuxiliar());

    respuesta = strategosSeguimientosService.saveConfiguracionMensajeEmailSeguimientos(configuracionMensajeEmailSeguimientos, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      strategosSeguimientosService.unlockObject(request.getSession().getId(), editarConfiguracionMensajeEmailSeguimientosForm.getMensajeId());

      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }

    strategosSeguimientosService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarConfiguracionMensajeEmailSeguimientosAction.ultimoTs", ts);

    return getForwardBack(request, 1, true);
  }
}