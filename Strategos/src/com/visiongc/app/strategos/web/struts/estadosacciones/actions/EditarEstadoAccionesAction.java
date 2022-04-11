package com.visiongc.app.strategos.web.struts.estadosacciones.actions;

import com.visiongc.app.strategos.estadosacciones.StrategosEstadosService;
import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.estadosacciones.forms.EditarEstadoAccionesForm;
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

public class EditarEstadoAccionesAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarEstadoAccionesForm editarEstadoAccionesForm = (EditarEstadoAccionesForm)form;

    ActionMessages messages = getMessages(request);

    String estadoId = request.getParameter("estadoId");

    boolean bloqueado = false;

    StrategosEstadosService strategosEstadosService = StrategosServiceFactory.getInstance().openStrategosEstadosService();

    if ((estadoId != null) && (!estadoId.equals("")) && (!estadoId.equals("0")))
    {
      bloqueado = !strategosEstadosService.lockForUpdate(request.getSession().getId(), estadoId, null);

      editarEstadoAccionesForm.setBloqueado(new Boolean(bloqueado));

      EstadoAcciones estadoAcciones = (EstadoAcciones)strategosEstadosService.load(EstadoAcciones.class, new Long(estadoId));

      if (estadoAcciones != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        editarEstadoAccionesForm.setEstadoId(estadoAcciones.getEstadoId());
        editarEstadoAccionesForm.setNombre(estadoAcciones.getNombre());
        editarEstadoAccionesForm.setOrden(estadoAcciones.getOrden());
        editarEstadoAccionesForm.setAplicaSeguimiento(estadoAcciones.getAplicaSeguimiento());
        editarEstadoAccionesForm.setIndicaFinalizacion(estadoAcciones.getCondicion());
      }
      else
      {
        strategosEstadosService.unlockObject(request.getSession().getId(), new Long(estadoId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      editarEstadoAccionesForm.clear();
    }

    strategosEstadosService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}