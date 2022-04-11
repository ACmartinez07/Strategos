package com.visiongc.app.strategos.web.struts.problemas.seguimientos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosSeguimientosService;
import com.visiongc.app.strategos.problemas.model.Seguimiento;
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

public class EliminarSeguimientoAction extends VgcAction
{
  private static final String ACTION_KEY = "EliminarSeguimientoAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String seguimientoId = request.getParameter("seguimientoId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarSeguimientoAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((seguimientoId == null) || (seguimientoId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(seguimientoId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }

    StrategosSeguimientosService strategosSeguimientosService = StrategosServiceFactory.getInstance().openStrategosSeguimientosService();

    bloqueado = !strategosSeguimientosService.lockForDelete(request.getSession().getId(), seguimientoId);

    Seguimiento seguimiento = (Seguimiento)strategosSeguimientosService.load(Seguimiento.class, new Long(seguimientoId));

    if (seguimiento != null) {
      if (!seguimiento.getReadOnly().booleanValue())
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", seguimiento.getNumeroReporte()));
        }
        else
        {
          seguimiento.setSeguimientoId(Long.valueOf(seguimientoId));
          int res = strategosSeguimientosService.deleteSeguimiento(seguimiento, getUsuarioConectado(request));

          strategosSeguimientosService.unlockObject(request.getSession().getId(), seguimientoId);

          if (res == 10004)
          {
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", seguimiento.getNumeroReporte()));
          }
          else {
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", seguimiento.getNumeroReporte()));
          }
        }

      }
      else
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.sololectura", seguimiento.getNumeroReporte()));
      }
    }
    else {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosSeguimientosService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarSeguimientoAction.ultimoTs", seguimientoId + "&" + ts);

    return getForwardBack(request, 1, true);
  }
}