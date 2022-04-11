package com.visiongc.app.strategos.web.struts.seriestiempo.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EliminarSerieTiempoAction extends VgcAction
{
	private static final String ACTION_KEY = "EliminarSerieTiempoAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		ActionMessages messages = getMessages(request);

		String serieId = request.getParameter("serieId");
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("EliminarSerieTiempoAction.ultimoTs");
		boolean bloqueado = false;
		boolean cancelar = false;

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((serieId == null) || (serieId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(serieId + "&" + ts))) 
			cancelar = true;

		if (cancelar)
			return getForwardBack(request, 1, true);

		StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService();

		bloqueado = !strategosSeriesTiempoService.lockForDelete(request.getSession().getId(), serieId);
		
		SerieTiempo serieTiempo = (SerieTiempo)strategosSeriesTiempoService.load(SerieTiempo.class, new Long(serieId));

		if (serieTiempo != null)
		{
			if (bloqueado)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", serieTiempo.getNombre()));
			else
			{
				if (!serieTiempo.getFija())
				{
					serieTiempo.setSerieId(Long.valueOf(serieId));
					Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
					int res = strategosSeriesTiempoService.deleteSerieTiempo(serieTiempo, usuario);
					
					strategosSeriesTiempoService.unlockObject(request.getSession().getId(), serieId);
	
					if (res == 10004)
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", serieTiempo.getNombre()));
					else
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", serieTiempo.getNombre()));
				}
				else
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.fijo", serieTiempo.getNombre()));
			}
		}
		else
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
		
		strategosSeriesTiempoService.close();

		saveMessages(request, messages);
		
		request.getSession().setAttribute("EliminarSerieTiempoAction.ultimoTs", serieId + "&" + ts);

		return getForwardBack(request, 1, true);
	}
}