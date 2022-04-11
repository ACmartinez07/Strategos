package com.visiongc.app.strategos.web.struts.estadosacciones.actions;

import com.visiongc.app.strategos.estadosacciones.StrategosEstadosService;
import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
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

public class EliminarEstadoAccionesAction extends VgcAction
{
  private static final String ACTION_KEY = "EliminarEstadoAccionesAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String estadoId = request.getParameter("estadoId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarEstadoAccionesAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((estadoId == null) || (estadoId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(estadoId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }

    StrategosEstadosService strategosEstadosService = StrategosServiceFactory.getInstance().openStrategosEstadosService();

    bloqueado = !strategosEstadosService.lockForDelete(request.getSession().getId(), estadoId);

    EstadoAcciones estadoAcciones = (EstadoAcciones)strategosEstadosService.load(EstadoAcciones.class, new Long(estadoId));

    if (estadoAcciones != null)
    {
      if (bloqueado)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", estadoAcciones.getNombre()));
      }
      else
      {
        estadoAcciones.setEstadoId(Long.valueOf(estadoId));
        int respuesta = strategosEstadosService.deleteEstadoAcciones(estadoAcciones, getUsuarioConectado(request));

        strategosEstadosService.unlockObject(request.getSession().getId(), estadoId);

        if (respuesta == 10004)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", estadoAcciones.getNombre()));
        }
        else
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", estadoAcciones.getNombre()));
        }

      }

    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosEstadosService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarEstadoAccionesAction.ultimoTs", estadoId + "&" + ts);

    return getForwardBack(request, 1, true);
  }
}