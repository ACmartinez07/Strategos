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

public class EditarConfiguracionMensajeEmailSeguimientosAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarConfiguracionMensajeEmailSeguimientosForm editarConfiguracionMensajeEmailSeguimientosForm = (EditarConfiguracionMensajeEmailSeguimientosForm)form;

    ActionMessages messages = getMessages(request);

    boolean bloqueado = false;

    StrategosSeguimientosService strategosSeguimientosService = StrategosServiceFactory.getInstance().openStrategosSeguimientosService();

    ConfiguracionMensajeEmailSeguimientos configuracionMensajeEmailSeguimientos = new ConfiguracionMensajeEmailSeguimientos();
    configuracionMensajeEmailSeguimientos = strategosSeguimientosService.getConfiguracionMensajeEmailSeguimientos();

    if (configuracionMensajeEmailSeguimientos == null) {
      configuracionMensajeEmailSeguimientos = strategosSeguimientosService.crearConfiguracionMensajeEmailSeguimientos(getUsuarioConectado(request));
    }

    if ((configuracionMensajeEmailSeguimientos.getMensajeId() != null) && (!configuracionMensajeEmailSeguimientos.getMensajeId().equals("")) && (!configuracionMensajeEmailSeguimientos.getMensajeId().equals("0")))
    {
      bloqueado = !strategosSeguimientosService.lockForUpdate(request.getSession().getId(), configuracionMensajeEmailSeguimientos.getMensajeId(), null);
      editarConfiguracionMensajeEmailSeguimientosForm.setBloqueado(new Boolean(bloqueado));

      if (bloqueado)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
      }

      editarConfiguracionMensajeEmailSeguimientosForm.setMensajeId(configuracionMensajeEmailSeguimientos.getMensajeId());
      editarConfiguracionMensajeEmailSeguimientosForm.setDescripcion(configuracionMensajeEmailSeguimientos.getDescripcion());
      editarConfiguracionMensajeEmailSeguimientosForm.setMensaje(configuracionMensajeEmailSeguimientos.getMensaje());
      editarConfiguracionMensajeEmailSeguimientosForm.setAcuseRecibo(configuracionMensajeEmailSeguimientos.getAcuseRecibo());
      editarConfiguracionMensajeEmailSeguimientosForm.setSoloAuxiliar(configuracionMensajeEmailSeguimientos.getSoloAuxiliar());
    }
    else
    {
      editarConfiguracionMensajeEmailSeguimientosForm.clear();
    }

    strategosSeguimientosService.close();

    saveMessages(request, messages);

    return mapping.findForward(forward);
  }
}