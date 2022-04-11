package com.visiongc.app.strategos.web.struts.unidadesmedida.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.SelectorListaForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarUnidadesMedidaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SelectorListaForm seleccionarUnidadesMedidaForm = (SelectorListaForm)form;

		String atributoOrden = seleccionarUnidadesMedidaForm.getAtributoOrden();
		String tipoOrden = seleccionarUnidadesMedidaForm.getTipoOrden();
		
		seleccionarUnidadesMedidaForm.setFuncionCierre(request.getParameter("funcionCierre"));

		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			seleccionarUnidadesMedidaForm.setAtributoOrden(atributoOrden);
		}
		
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			seleccionarUnidadesMedidaForm.setTipoOrden(tipoOrden);
		}

		if (seleccionarUnidadesMedidaForm.getFuncionCierre() != null) 
		{
			if (!seleccionarUnidadesMedidaForm.getFuncionCierre().equals("")) 
			{
				if (seleccionarUnidadesMedidaForm.getFuncionCierre().indexOf(";") < 0)
					seleccionarUnidadesMedidaForm.setFuncionCierre(seleccionarUnidadesMedidaForm.getFuncionCierre() + ";");
			}
			else 
				seleccionarUnidadesMedidaForm.setFuncionCierre(null);
		}

		StrategosUnidadesService unidadesMedidaService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();

		PaginaLista paginaUnidadesMedida = unidadesMedidaService.getUnidadesMedida(0, 0, atributoOrden, tipoOrden, true, null);
		
		request.setAttribute("paginaUnidades", paginaUnidadesMedida);

		unidadesMedidaService.close();
		
		return mapping.findForward(forward);
	}
}