package com.visiongc.app.strategos.web.struts.presentaciones.vistas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.presentaciones.StrategosVistasService;
import com.visiongc.app.strategos.presentaciones.model.Vista;
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

public class EliminarVistaAction extends VgcAction
{
	private static final String ACTION_KEY = "EliminarVistaAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		ActionMessages messages = getMessages(request);

		String vistaId = request.getParameter("vistaId");
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("EliminarVistaAction.ultimoTs");
		boolean bloqueado = false;
		boolean cancelar = false;
		
		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((vistaId == null) || (vistaId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(vistaId + "&" + ts))) 
			cancelar = true;

		if (cancelar)
			return getForwardBack(request, 1, true);

		StrategosVistasService strategosVistasService = StrategosServiceFactory.getInstance().openStrategosVistasService();

		strategosVistasService.unlockObject(request.getSession().getId(), vistaId);

		bloqueado = !strategosVistasService.lockForDelete(request.getSession().getId(), vistaId);

		Vista vista = (Vista)strategosVistasService.load(Vista.class, new Long(vistaId));

		if (vista != null)
		{
			if (bloqueado)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", vista.getNombre()));
			else
			{
				vista.setVistaId(Long.valueOf(vistaId));
				int respuesta = strategosVistasService.deleteVista(vista, getUsuarioConectado(request));
				
				strategosVistasService.unlockObject(request.getSession().getId(), vistaId);
				
				if (respuesta == 10004)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", vista.getNombre()));
				else
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", vista.getNombre()));
			}
		}
		else
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

		strategosVistasService.close();
		
		saveMessages(request, messages);

		request.getSession().setAttribute("EliminarVistaAction.ultimoTs", vistaId + "&" + ts);

		return getForwardBack(request, 1, true);
	}
}