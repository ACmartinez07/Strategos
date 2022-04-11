package com.visiongc.app.strategos.web.struts.planes.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
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

public class ActivarDesactivarPlanAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String planId = request.getParameter("planId");

    boolean bloqueado = false;

    StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

    if ((planId != null) && (!planId.equals("")) && (!planId.equals("0")))
    {
      bloqueado = !strategosPlanesService.lockForUpdate(request.getSession().getId(), planId, null);

      Plan plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));

      if (plan != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        boolean activar = !plan.getActivo().booleanValue();
        int resultado = strategosPlanesService.activarPlan(plan.getPlanId(), activar);
        if (resultado != 10000)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        }
      }
      else
      {
        strategosPlanesService.unlockObject(request.getSession().getId(), new Long(planId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
      }

    }

    strategosPlanesService.close();

    saveMessages(request, messages);

    return getForwardBack(request, 1, true);
  }
}