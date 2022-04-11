package com.visiongc.app.strategos.web.struts.planes.iniciativas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
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

public class DesasociarIniciativaPlanAction extends VgcAction
{
  private static final String ACTION_KEY = "AsociarIniciativaPlanAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String ts = request.getParameter("ts");
    String iniciativaId = request.getParameter("iniciativaId");
    String planId = request.getParameter("planId");
    String perspectivaId = request.getParameter("perspectivaId");

    StrategosPerspectivasService strategosPerspectivaService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();

    boolean respuesta = strategosPerspectivaService.desasociarIniciativa(new Long(perspectivaId), new Long(iniciativaId), new Long(planId), getUsuarioConectado(request));

    if (respuesta)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.desasociariniciativaplan.ok"));
    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion"));
    }

    strategosPerspectivaService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("AsociarIniciativaPlanAction.ultimoTs", ts);

    return getForwardBack(request, 1, true);
  }
}