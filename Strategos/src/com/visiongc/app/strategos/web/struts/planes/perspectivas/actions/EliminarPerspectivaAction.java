package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EliminarPerspectivaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);

		String perspectivaId = request.getParameter("perspectivaId");
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("EliminarPerspectivaAction.ultimoTs");
		boolean bloqueado = false;
		boolean cancelar = false;

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((perspectivaId == null) || (perspectivaId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(perspectivaId + "&" + ts))) 
			cancelar = true;

		if (cancelar)
			return getForwardBack(request, 1, true);

		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();

		strategosPerspectivasService.unlockObject(request.getSession().getId(), perspectivaId);

		bloqueado = !strategosPerspectivasService.lockForDelete(request.getSession().getId(), perspectivaId);

		Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, new Long(perspectivaId));

		if (perspectiva != null) 
		{
			if ((perspectiva.getHijos() == null) || ((perspectiva.getHijos() != null) && (perspectiva.getHijos().size() == 0))) 
			{
				if (perspectiva.getPadre() != null) 
				{
					if (bloqueado)
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", perspectiva.getNombre()));
					else
					{
						strategosPerspectivasService.unlockObject(request.getSession().getId(), perspectivaId);

						int res = strategosPerspectivasService.deletePerspectiva(perspectiva, getUsuarioConectado(request));

						if (res == 10004)
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", perspectiva.getNombre()));
						else
						{
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", perspectiva.getNombre()));
							request.setAttribute("GestionarPerspectivasAction.reloadId", perspectiva.getPadreId());
						}
					}
				}
				else
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.nodoraiz", perspectiva.getNombre()));
			}
			else
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", perspectiva.getNombre()));
				strategosPerspectivasService.unlockObject(request.getSession().getId(), perspectivaId);
			}
		}
		else 
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

		strategosPerspectivasService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("EliminarPerspectivaAction.ultimoTs", perspectivaId + "&" + ts);

		return getForwardBack(request, 1, true);
	}
}