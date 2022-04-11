package com.visiongc.app.strategos.web.struts.planes.iniciativas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
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

public class VincularIniciativaPlanAction extends VgcAction
{
	private static final String ACTION_KEY = "VincularIniciativaAction";

	public void updateNavigationBar(NavigationBar arg0, String arg1, String arg2)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);

		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("VincularIniciativaAction.ultimoTs");
		String iniciativaId = request.getParameter("iniciativaId");

		boolean cancelar = false;

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((iniciativaId == null) || (iniciativaId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(iniciativaId + "&" + ts))) 
			cancelar = true;

		if (cancelar)
			return getForwardBack(request, 1, true);

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

		String planId = request.getParameter("planId");
		
		String perspectivaId = request.getParameter("perspectivaId");

		String organizacionId = request.getParameter("organizacionId");

		if ((iniciativaId != null) && (!iniciativaId.equals("")))
		{
			StrategosPerspectivasService strategosPerspectivaService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
			Perspectiva perspectiva = (Perspectiva)strategosPerspectivaService.load(Perspectiva.class, new Long(perspectivaId));
			strategosPerspectivaService.asociarIniciativa(perspectiva, new Long(iniciativaId), new Long(planId), new Long(organizacionId), getUsuarioConectado(request));
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.asociariniciativaplan.ok"));
			
			strategosPerspectivaService.close();
		}

		strategosPlanesService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("VincularIniciativaAction.ultimoTs", ts);

		return getForwardBack(request, 1, true);
	}
}