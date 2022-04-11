package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EliminarActividadAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		ActionMessages messages = getMessages(request);

		String actividadId = request.getParameter("actividadId");
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("EliminarActividadAction.ultimoTs");
		boolean bloqueado = false;
		boolean cancelar = false;
		
		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((actividadId == null) || (actividadId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(actividadId + "&" + ts))) 
			cancelar = true;

		if (cancelar)
			return getForwardBack(request, 1, true);

		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

		bloqueado = !strategosPryActividadesService.lockForDelete(request.getSession().getId(), actividadId);

		PryActividad pryActividad = (PryActividad)strategosPryActividadesService.load(PryActividad.class, new Long(actividadId));

		if (pryActividad != null)
		{
			if (bloqueado)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", pryActividad.getNombre()));
			else
			{
				int res = strategosPryActividadesService.deleteActividad(pryActividad, getUsuarioConectado(request));
				
				strategosPryActividadesService.unlockObject(request.getSession().getId(), actividadId);
				
				// Recalculamos las actividades padres y la iniciativa por medio de una actividad Hermana
				if (res == 10000)
				{
					StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(strategosPryActividadesService);
			    	Iniciativa iniciativa = null;
					if (pryActividad.getProyectoId() != null)
						iniciativa = (Iniciativa)strategosIniciativasService.getIniciativaByProyecto(pryActividad.getProyectoId());
					strategosIniciativasService.close();

					if (iniciativa != null)
						res = new com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction().CalcularPadre(pryActividad, iniciativa.getIniciativaId(), request);
				}

				if (res == 10004)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", pryActividad.getNombre()));
				else if (pryActividad.getObjetoAsociadoId() != null)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.desasociadook", pryActividad.getNombre()));
				else
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", pryActividad.getNombre()));
			}
		}
		else
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

		strategosPryActividadesService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("EliminarActividadAction.ultimoTs", actividadId + "&" + ts);

		return getForwardBack(request, 1, true);
	}
}