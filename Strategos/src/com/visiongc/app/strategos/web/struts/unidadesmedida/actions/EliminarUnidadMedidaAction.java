package com.visiongc.app.strategos.web.struts.unidadesmedida.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
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

public class EliminarUnidadMedidaAction extends VgcAction
{
  private static final String ACTION_KEY = "EliminarUnidadMedidaAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String unidadId = request.getParameter("unidadId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarUnidadMedidaAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((unidadId == null) || (unidadId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(unidadId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }

    StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();

    bloqueado = !strategosUnidadesService.lockForDelete(request.getSession().getId(), unidadId);

    UnidadMedida unidadMedida = (UnidadMedida)strategosUnidadesService.load(UnidadMedida.class, new Long(unidadId));

    if (unidadMedida != null)
    {
      if (bloqueado)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", unidadMedida.getNombre()));
      }
      else
      {
        unidadMedida.setUnidadId(Long.valueOf(unidadId));
        int res = strategosUnidadesService.deleteUnidadMedida(unidadMedida, getUsuarioConectado(request));

        strategosUnidadesService.unlockObject(request.getSession().getId(), unidadId);

        if (res == 10004)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", unidadMedida.getNombre()));
        }
        else
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", unidadMedida.getNombre()));
        }

      }

    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosUnidadesService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarUnidadMedidaAction.ultimoTs", unidadId + "&" + ts);

    return getForwardBack(request, 1, true);
  }
}