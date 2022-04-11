package com.visiongc.app.strategos.web.struts.organizaciones.actions;

import java.util.Iterator;
import java.util.List;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EliminarOrganizacionAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);

		String organizacionId = request.getParameter("organizacionId");
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("EliminarOrganizacionAction.ultimoTs");
		boolean bloqueado = false;
		boolean cancelar = false;

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((organizacionId == null) || (organizacionId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(organizacionId + "&" + ts))) 
			cancelar = true;

		if (cancelar)
			return getForwardBack(request, 1, true);

		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();

		strategosOrganizacionesService.unlockObject(request.getSession().getId(), organizacionId);

		bloqueado = !strategosOrganizacionesService.lockForDelete(request.getSession().getId(), organizacionId);

		OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(organizacionId));

		if (organizacionStrategos != null) 
		{
			if (organizacionStrategos.getPadre() == null)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.nodoraiz", organizacionStrategos.getNombre()));
			else if ((organizacionStrategos.getSoloLectura() != null) && (organizacionStrategos.getSoloLectura().booleanValue()))
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.sololectura", organizacionStrategos.getNombre()));
			else if (bloqueado)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", organizacionStrategos.getNombre()));
			else
			{
				int respuesta = VgcReturnCode.DB_OK;
				Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
				if (organizacionStrategos.getHijos() != null && organizacionStrategos.getHijos().size() >= 0)
					respuesta = EliminarHijas(Long.parseLong(organizacionId), usuario); 
				if (respuesta == VgcReturnCode.DB_OK)
					respuesta = strategosOrganizacionesService.deleteOrganizacion(organizacionStrategos, usuario);				
				if (respuesta == VgcReturnCode.DB_FK_VIOLATED)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", organizacionStrategos.getNombre()));
				else
				{
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", organizacionStrategos.getNombre()));
					request.setAttribute("GestionarOrganizacionesAction.reloadId", organizacionStrategos.getPadreId());
				}
			}
		}
		else
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

		strategosOrganizacionesService.unlockObject(request.getSession().getId(), organizacionId);
		
		strategosOrganizacionesService.close();
		
		saveMessages(request, messages);
		
		request.getSession().setAttribute("EliminarOrganizacionAction.ultimoTs", organizacionId + "&" + ts);
		
		return getForwardBack(request, 1, true);
	}

	private int EliminarHijas(Long organizacionId, Usuario usuario)
	{
		int respuesta = VgcReturnCode.DB_OK;
		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		List<OrganizacionStrategos> organizacionesHijas = strategosOrganizacionesService.getOrganizacionHijas(organizacionId, false);
		
		for (Iterator<OrganizacionStrategos> iter = organizacionesHijas.iterator(); iter.hasNext(); ) 
		{
			OrganizacionStrategos organizacion = (OrganizacionStrategos)iter.next();
			if (organizacion.getHijos() != null && organizacion.getHijos().size() >= 0)
				respuesta = EliminarHijas(organizacion.getOrganizacionId(), usuario);
			if (respuesta == VgcReturnCode.DB_OK)
				respuesta = strategosOrganizacionesService.deleteOrganizacion(organizacion, usuario);
			if (respuesta != VgcReturnCode.DB_OK)
				break;
		}
		strategosOrganizacionesService.close();
		
		return respuesta;
	}
}