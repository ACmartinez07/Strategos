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

public class EliminarPlanAction extends VgcAction
{
	private static final String ACTION_KEY = "EliminarPlanAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);

		String planId = request.getParameter("planId");
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("EliminarPlanAction.ultimoTs");
		boolean bloqueado = false;
		boolean cancelar = false;

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((planId == null) || (planId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(planId + "&" + ts))) 
			cancelar = true;

		if (cancelar)
			return getForwardBack(request, 1, true);

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

		Plan plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));

		if (plan != null)
		{
			if (bloqueado)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", plan.getNombre()));
			else
			{
				int respuesta = strategosPlanesService.deletePlan(plan, getUsuarioConectado(request), true);
				
				strategosPlanesService.unlockObject(request.getSession().getId(), planId);

				if (respuesta == 10004)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", plan.getNombre()));
				else 
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", plan.getNombre()));
			}
		}
		else
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

		strategosPlanesService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("EliminarPlanAction.ultimoTs", planId + "&" + ts);
		
		return getForwardBack(request, 1, true);
	}
}