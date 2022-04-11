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

public class GuardarClaseProblemasAction extends VgcAction
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
		boolean copiar = mapping.getPath().toLowerCase().indexOf("copia") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("GuardarClaseProblemasAction.ultimoTs");

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts))) 
			cancelar = true;

		StrategosClasesProblemasService strategosClasesProblemasService = StrategosServiceFactory.getInstance().openStrategosClasesProblemasService();

		if (cancelar)
		{
			strategosClasesProblemasService.unlockObject(request.getSession().getId(), editarClaseProblemasForm.getClaseId());
			strategosClasesProblemasService.close();
			
			return getForwardBack(request, 1, true);
		}

		ClaseProblemas claseProblemas = new ClaseProblemas();
		boolean nuevo = false;

		claseProblemas.setClaseId(editarClaseProblemasForm.getClaseId());

		if ((editarClaseProblemasForm.getClaseId() != null) && (!editarClaseProblemasForm.getClaseId().equals(Long.valueOf("0"))))
			claseProblemas = (ClaseProblemas)strategosClasesProblemasService.load(ClaseProblemas.class, editarClaseProblemasForm.getClaseId());
		else if (copiar)
		{
			claseProblemas.setClaseId(new Long(0L));

			ClaseProblemas claseProblemasPadre = (ClaseProblemas)strategosClasesProblemasService.load(ClaseProblemas.class, editarClaseProblemasForm.getPadreId());
			claseProblemas.setPadreId(claseProblemasPadre.getClaseId());
		}
		else
		{
			nuevo = true;
			claseProblemas.setClaseId(new Long(0L));

			ClaseProblemas claseProblemasPadre = (ClaseProblemas)strategosClasesProblemasService.load(ClaseProblemas.class, editarClaseProblemasForm.getPadreId());
			claseProblemas.setPadreId(claseProblemasPadre.getClaseId());
		}

		claseProblemas.setNombre(editarClaseProblemasForm.getNombre());
		claseProblemas.setTipo(editarClaseProblemasForm.getTipo());
		Long organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));
		claseProblemas.setOrganizacionId(organizacionId);

		if ((editarClaseProblemasForm.getDescripcion() != null) && (!editarClaseProblemasForm.getDescripcion().equals("")))
			claseProblemas.setDescripcion(editarClaseProblemasForm.getDescripcion());
		else 
			claseProblemas.setDescripcion(null);

		int respuesta = 10000;
		respuesta = strategosClasesProblemasService.saveClaseProblema(claseProblemas, getUsuarioConectado(request));

		if (respuesta == 10000)
		{
			strategosClasesProblemasService.unlockObject(request.getSession().getId(), editarClaseProblemasForm.getClaseId());
			forward = "exito";

			if (nuevo)
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
				forward = "crearClaseProblemas";
			}
			else if (copiar)
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.copiar.ok"));
				request.setAttribute("GestionarClasesProblemasAction.reloadId", claseProblemas.getPadreId());
			}
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
		}
		else if (respuesta == 10003)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));

		strategosClasesProblemasService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("GuardarClaseProblemasAction.ultimoTs", ts);
		
		if (forward.equals("exito")) 
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
	}
}