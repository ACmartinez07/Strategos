package com.visiongc.app.strategos.web.struts.responsables.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.responsables.forms.AsociarResponsablesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class AsociarResponsablesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		AsociarResponsablesForm asociarResponsablesForm = (AsociarResponsablesForm)form;

		ActionMessages messages = getMessages(request);

		String responsableId = request.getParameter("responsableId");

		boolean bloqueado = false;

		StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();

		if ((responsableId != null) && (!responsableId.equals("")) && (!responsableId.equals("0")))
		{
			bloqueado = !strategosResponsablesService.lockForUpdate(request.getSession().getId(), responsableId, null);
			asociarResponsablesForm.setBloqueado(new Boolean(bloqueado));
			
			asociarResponsablesForm.setResponsableId(new Long(responsableId));

			Responsable responsableSeleccionado = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(responsableId));

			if (responsableSeleccionado != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));

				Set responsablesAsociados = responsableSeleccionado.getResponsables();
				
				List responsablesAsociables = strategosResponsablesService.getResponsablesAsociables(responsableSeleccionado.getTipo().booleanValue(), responsableSeleccionado.getOrganizacionId().longValue(), responsablesAsociados, responsableSeleccionado);
				
				request.setAttribute("responsablesAsociados", responsablesAsociados);
				request.setAttribute("responsablesAsociables", responsablesAsociables);
			}
			else
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		else
			asociarResponsablesForm.clear();

		strategosResponsablesService.close();

		saveMessages(request, messages);

		if (forward.equals("noencontrado"))
			return getForwardBack(request, 1, true);
		
		return mapping.findForward(forward);
	}
}