/**
 * 
 */
package com.visiongc.app.strategos.web.struts.organizaciones.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.organizaciones.forms.EditarOrganizacionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class MoverOrganizacionAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		ActionMessages messages = getMessages(request);

		Long organizacionId = Long.parseLong(request.getParameter("organizacionId").toString());
		Long organizacionSeleccionId = Long.parseLong(request.getParameter("organizacionPadreId").toString());

		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(organizacionId));

		int respuesta = 10000;
		Byte status = StatusUtil.getStatusNoSuccess();
		if (organizacionSeleccionId.longValue() != organizacionStrategos.getPadreId().longValue())
		{
			if (organizacionSeleccionId.longValue() != organizacionStrategos.getOrganizacionId())
			{
				if (!organizacionSeleccionEsHijo(organizacionSeleccionId, organizacionId, strategosOrganizacionesService))
				{
					organizacionStrategos.setPadreId(organizacionSeleccionId);
					respuesta = strategosOrganizacionesService.saveOrganizacion(organizacionStrategos, getUsuarioConectado(request));
				    if (respuesta == 10000)
				    {
				    	strategosOrganizacionesService.unlockObject(request.getSession().getId(), new Long(organizacionId));
				    	status = StatusUtil.getStatusSuccess();
				    }
				}
			}
		}
		
	    strategosOrganizacionesService.close();

	    saveMessages(request, messages);

	    request.setAttribute("ajaxResponse", status.toString());
		return mapping.findForward("ajaxResponse");
	}
	
	private boolean organizacionSeleccionEsHijo(Long organizacionSeleccionId, Long organizacionId, StrategosOrganizacionesService strategosOrganizacionesService)
	{
		OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(organizacionSeleccionId));
		if (organizacionStrategos.getPadreId() != null && organizacionStrategos.getPadreId().longValue() == organizacionId.longValue())
			return true;
		else if (organizacionStrategos.getPadreId() != null)
			return organizacionSeleccionEsHijo(organizacionStrategos.getPadreId(), organizacionId, strategosOrganizacionesService);
		else
			return false;
	}
}