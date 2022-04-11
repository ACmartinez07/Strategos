package com.visiongc.app.strategos.web.struts.problemas.clasesproblemas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosClasesProblemasService;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas;
import com.visiongc.app.strategos.web.struts.problemas.clasesproblemas.forms.EditarClaseProblemasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarClaseProblemasAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarClaseProblemasForm editarClaseProblemasForm = (EditarClaseProblemasForm)form;

		ActionMessages messages = getMessages(request);

		String claseId = request.getParameter("claseId");
		Integer tipo = Integer.parseInt(request.getParameter("tipo"));

		StrategosClasesProblemasService strategosClasesProblemasService = StrategosServiceFactory.getInstance().openStrategosClasesProblemasService();

		boolean verForm = getPermisologiaUsuario(request).tienePermiso("CLASE_PROBLEMA_VIEWALL");
		boolean editarForm = getPermisologiaUsuario(request).tienePermiso("CLASE_PROBLEMA_ADD");
		boolean bloqueado = false;

		if ((claseId != null) && (!claseId.equals("")) && (!claseId.equals("0")))
		{
			editarForm = getPermisologiaUsuario(request).tienePermiso("CLASE_PROBLEMA_EDIT");
			bloqueado = !strategosClasesProblemasService.lockForUpdate(request.getSession().getId(), claseId, null);

			editarClaseProblemasForm.setBloqueado(new Boolean(bloqueado));

			ClaseProblemas claseProblemas = (ClaseProblemas)strategosClasesProblemasService.load(ClaseProblemas.class, new Long(claseId));

			if (claseProblemas != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));

				ClaseProblemas padre = claseProblemas.getPadre();
				long padreId = 0L;
				if (padre != null) 
					padreId = padre.getClaseId().longValue();

				editarClaseProblemasForm.setPadreId(new Long(padreId));
				editarClaseProblemasForm.setClaseId(claseProblemas.getClaseId());
				editarClaseProblemasForm.setOrganizacionId(claseProblemas.getOrganizacionId());
				editarClaseProblemasForm.setNombre(claseProblemas.getNombre());
				editarClaseProblemasForm.setDescripcion(claseProblemas.getDescripcion());
				editarClaseProblemasForm.setTipo(claseProblemas.getTipo());
			}
			else
			{
				strategosClasesProblemasService.unlockObject(request.getSession().getId(), new Long(claseId));
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		else
		{
			editarClaseProblemasForm.clear();
			ClaseProblemas padre = (ClaseProblemas)request.getSession().getAttribute("claseProblemas");
			editarClaseProblemasForm.setPadreId(padre.getClaseId());
			editarClaseProblemasForm.setTipo(tipo);
		}

		strategosClasesProblemasService.close();

		if (!bloqueado && verForm && !editarForm)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
			editarClaseProblemasForm.setBloqueado(true);
		}
		else if (!bloqueado && !verForm && !editarForm)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));
		
		saveMessages(request, messages);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);
		
		return mapping.findForward(forward);
	}
}