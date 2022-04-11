package com.visiongc.app.strategos.web.struts.responsables.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.responsables.forms.EditarResponsableForm;
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

public class EditarResponsableAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarResponsableForm editarResponsableForm = (EditarResponsableForm)form;

		ActionMessages messages = getMessages(request);

		String responsableId = request.getParameter("responsableId");

		boolean bloqueado = false;

		StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();

		if ((responsableId != null) && (!responsableId.equals("")) && (!responsableId.equals("0")))
		{
			bloqueado = !strategosResponsablesService.lockForUpdate(request.getSession().getId(), responsableId, null);

			editarResponsableForm.setBloqueado(new Boolean(bloqueado));

			Responsable responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(responsableId));

			if (responsable != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
				
				editarResponsableForm.setOrganizacionId(responsable.getOrganizacionId());
				editarResponsableForm.setCargo(responsable.getCargo());
				editarResponsableForm.setNombre(responsable.getNombre());
				editarResponsableForm.setUbicacion(responsable.getUbicacion());
				editarResponsableForm.setEmail(responsable.getEmail());
				if (responsable.getTipo() != null)
					editarResponsableForm.setTipo(responsable.getTipo());
				else
					editarResponsableForm.setTipo(false);
				editarResponsableForm.setNotas(responsable.getNotas());

				if ((responsable.getUsuarioId() != null) && (responsable.getUsuarioId() != new Long(0L))) 
				{
					editarResponsableForm.setUsuarioId(responsable.getUsuarioId());
					editarResponsableForm.setUsuario(responsable.getUsuario());
					editarResponsableForm.setNombreUsuario(responsable.getUsuario().getFullName());
				}
			}
			else
			{
				strategosResponsablesService.unlockObject(request.getSession().getId(), new Long(responsableId));
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		else
			editarResponsableForm.clear();

		strategosResponsablesService.close();

		saveMessages(request, messages);

		if (forward.equals("noencontrado"))
			return getForwardBack(request, 1, true);
    
		return mapping.findForward(forward);
	}
}