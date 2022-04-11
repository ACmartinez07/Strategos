package com.visiongc.app.strategos.web.struts.responsables.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
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

public class EliminarResponsableAction extends VgcAction
{
	private static final String ACTION_KEY = "EliminarResponsableAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		ActionMessages messages = getMessages(request);
		
		String responsableId = request.getParameter("responsableId");
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("EliminarResponsableAction.ultimoTs");
		boolean bloqueado = false;
		boolean cancelar = false;
		
		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((responsableId == null) || (responsableId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(responsableId + "&" + ts))) 
			cancelar = true;

		if (cancelar)
			return getForwardBack(request, 1, true);

		StrategosResponsablesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
		
		bloqueado = !strategosUnidadesService.lockForDelete(request.getSession().getId(), responsableId);
		
		Responsable responsable = (Responsable)strategosUnidadesService.load(Responsable.class, new Long(responsableId));
		
		String nombreResponsable = responsable.getNombre();
		if ((responsable.getUsuario() != null && responsable.getNombre() == null) || (responsable.getNombre() != null && responsable.getNombre().equals(""))) 
			nombreResponsable = responsable.getUsuario().getFullName();
		if (nombreResponsable == null && responsable.getCargo() != null)
			nombreResponsable = responsable.getCargo();

		if (responsable != null)
		{
			if (bloqueado)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", nombreResponsable));
			else
			{
				responsable.setResponsableId(Long.valueOf(responsableId));
				int respuesta = strategosUnidadesService.deleteResponsable(responsable, getUsuarioConectado(request));
				
				strategosUnidadesService.unlockObject(request.getSession().getId(), responsableId);
				
				if (respuesta == 10004)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", nombreResponsable));
				else
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", nombreResponsable));
			}
		}
		else
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

		strategosUnidadesService.close();
		
		saveMessages(request, messages);

		request.getSession().setAttribute("EliminarResponsableAction.ultimoTs", responsableId + "&" + ts);
		
		return getForwardBack(request, 1, true);
	}
}