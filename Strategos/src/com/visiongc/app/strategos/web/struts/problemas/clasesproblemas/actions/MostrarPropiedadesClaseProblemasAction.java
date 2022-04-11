package com.visiongc.app.strategos.web.struts.problemas.clasesproblemas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosClasesProblemasService;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas;
import com.visiongc.app.strategos.web.struts.problemas.clasesproblemas.forms.EditarClaseProblemasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class MostrarPropiedadesClaseProblemasAction extends VgcAction
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

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String claseId = request.getParameter("claseId");

		StrategosClasesProblemasService strategosClasesProblemasService = StrategosServiceFactory.getInstance().openStrategosClasesProblemasService();

		StrategosProblemasService strategosProblemasService = StrategosServiceFactory.getInstance().openStrategosProblemasService();

		if (cancelar)
		{
			strategosClasesProblemasService.unlockObject(request.getSession().getId(), editarClaseProblemasForm.getClaseId());
			
			strategosClasesProblemasService.close();

			return getForwardBack(request, 1, true);
		}

		ClaseProblemas claseProblemas = (ClaseProblemas)strategosClasesProblemasService.load(ClaseProblemas.class, new Long(claseId));

		if (claseProblemas != null)
		{
			if (claseProblemas.getCreadoId() != null) 
			{
				Usuario nombreUsuarioCreado = (Usuario)strategosClasesProblemasService.load(Usuario.class, claseProblemas.getCreadoId());
				editarClaseProblemasForm.setNombreUsuarioCreado(nombreUsuarioCreado.getFullName());
			}

			if (claseProblemas.getModificadoId() != null) 
			{
				Usuario nombreUsuarioModificado = (Usuario)strategosClasesProblemasService.load(Usuario.class, claseProblemas.getModificadoId());
				editarClaseProblemasForm.setNombreUsuarioModificado(nombreUsuarioModificado.getFullName());
			}

			if (claseProblemas.getHijos() != null)
				editarClaseProblemasForm.setNumeroHijos(new Integer(claseProblemas.getHijos().size()));
			else 
				editarClaseProblemasForm.setNumeroHijos(null);

			if (claseProblemas.getCreado() != null)
				editarClaseProblemasForm.setFechaCreado(VgcFormatter.formatearFecha(claseProblemas.getCreado(), "formato.fecha.larga"));
			else 
				editarClaseProblemasForm.setFechaCreado(null);

			if (claseProblemas.getModificado() != null)
				editarClaseProblemasForm.setFechaModificado(VgcFormatter.formatearFecha(claseProblemas.getModificado(), "formato.fecha.larga"));
			else 
				editarClaseProblemasForm.setFechaModificado(null);

			editarClaseProblemasForm.setNombre(claseProblemas.getNombre());
			Long numeroProblemas = strategosProblemasService.getNumeroProblemas(new Long(claseId));
			editarClaseProblemasForm.setNumeroProblemas(numeroProblemas);
			editarClaseProblemasForm.setTipo(claseProblemas.getTipo());
		}

		strategosClasesProblemasService.close();
		strategosProblemasService.close();

		saveMessages(request, messages);

		return mapping.findForward(forward);
	}
}