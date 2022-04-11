package com.visiongc.app.strategos.web.struts.estadosacciones.actions;

import com.visiongc.app.strategos.estadosacciones.StrategosEstadosService;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.SelectorListaForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarEstadosAccionesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SelectorListaForm seleccionarEstadosAccionesForm = (SelectorListaForm)form;

		seleccionarEstadosAccionesForm.setFuncionCierre(request.getParameter("funcionCierre"));

		if (seleccionarEstadosAccionesForm.getFuncionCierre() != null) 
		{
			if (!seleccionarEstadosAccionesForm.getFuncionCierre().equals("")) 
			{
				if (seleccionarEstadosAccionesForm.getFuncionCierre().indexOf(";") < 0)
					seleccionarEstadosAccionesForm.setFuncionCierre(seleccionarEstadosAccionesForm.getFuncionCierre() + ";");
			}
			else 
				seleccionarEstadosAccionesForm.setFuncionCierre(null);
		}

		StrategosEstadosService estadosAccionesService = StrategosServiceFactory.getInstance().openStrategosEstadosService();

		PaginaLista paginaEstadosAcciones = estadosAccionesService.getEstadosAcciones(0, 0, "orden", "asc", true, null);

		request.setAttribute("paginaEstadosAcciones", paginaEstadosAcciones);

		estadosAccionesService.close();

		return mapping.findForward(forward);
	}
}