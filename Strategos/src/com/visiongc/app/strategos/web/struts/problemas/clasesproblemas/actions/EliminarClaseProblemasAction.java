package com.visiongc.app.strategos.web.struts.problemas.clasesproblemas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosClasesProblemasService;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EliminarClaseProblemasAction extends VgcAction
{
	private static final String ACTION_KEY = "EliminarClaseProblemasAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);

		String claseId = request.getParameter("claseId");
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("EliminarClaseProblemasAction.ultimoTs");
		boolean bloqueado = false;
		boolean cancelar = false;

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((claseId == null) || (claseId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(claseId + "&" + ts))) 
			cancelar = true;

		if (cancelar)
			return getForwardBack(request, 1, true);

		StrategosClasesProblemasService strategosClaseProblemasService = StrategosServiceFactory.getInstance().openStrategosClasesProblemasService();

		strategosClaseProblemasService.unlockObject(request.getSession().getId(), claseId);

		bloqueado = !strategosClaseProblemasService.lockForDelete(request.getSession().getId(), claseId);

		ClaseProblemas claseProblemas = (ClaseProblemas)strategosClaseProblemasService.load(ClaseProblemas.class, new Long(claseId));

		if (claseProblemas != null) 
		{
			if ((claseProblemas.getHijos() == null) || ((claseProblemas.getHijos() != null) && (claseProblemas.getHijos().size() == 0))) 
			{
				if (claseProblemas.getPadre() != null)
				{
					if (bloqueado)
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", claseProblemas.getNombre()));
					else
					{
						strategosClaseProblemasService.unlockObject(request.getSession().getId(), claseId);

						int res = strategosClaseProblemasService.deleteClaseProblema(claseProblemas, getUsuarioConectado(request));

						if (res == 10004)
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", claseProblemas.getNombre()));
						else 
						{
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", claseProblemas.getNombre()));
							
							ClaseProblemas padre = claseProblemas.getPadre();
							request.setAttribute("GestionarClasesProblemasAction.reloadId", padre.getClaseId());
						}
					}
				}
				else
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.nodoraiz", claseProblemas.getNombre()));
			}
			else 
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", claseProblemas.getNombre()));
		}
		else 
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

		strategosClaseProblemasService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("EliminarClaseProblemasAction.ultimoTs", claseId + "&" + ts);

		return getForwardBack(request, 1, true);
	}
}