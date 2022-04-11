package com.visiongc.app.strategos.web.struts.planes.iniciativas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
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

public class AsociarIniciativaPlanAction extends VgcAction
{
	private static final String ACTION_KEY = "AsociarIniciativaPlanAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		ActionMessages messages = getMessages(request);

		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("AsociarIniciativaPlanAction.ultimoTs");
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

		StrategosPerspectivasService strategosPerspectivaService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();

		String planId = request.getParameter("planId");

		String perspectivaId = request.getParameter("perspectivaId");

		Long organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));

		if ((iniciativaId != null) && (!iniciativaId.equals(""))) 
		{
			Perspectiva perspectiva = (Perspectiva)strategosPerspectivaService.load(Perspectiva.class, new Long(perspectivaId));
			
			strategosPerspectivaService.asociarIniciativa(perspectiva, new Long(iniciativaId), new Long(planId), organizacionId, getUsuarioConectado(request));

			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.asociariniciativaplan.ok"));
		}

		strategosPerspectivaService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("AsociarIniciativaPlanAction.ultimoTs", ts);

		return getForwardBack(request, 1, true);
	}
}