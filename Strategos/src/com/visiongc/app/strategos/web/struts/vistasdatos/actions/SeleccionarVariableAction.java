package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

import com.visiongc.app.strategos.vistasdatos.model.util.TipoVariable;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.SeleccionarVariableForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoValorNombre;
import com.visiongc.commons.web.NavigationBar;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarVariableAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SeleccionarVariableForm seleccionarVariableForm = (SeleccionarVariableForm)form;

		String atributoOrden = seleccionarVariableForm.getAtributoOrden();
		String tipoOrden = seleccionarVariableForm.getTipoOrden();

		seleccionarVariableForm.setFuncionCierre(request.getParameter("funcionCierre"));

		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			seleccionarVariableForm.setAtributoOrden(atributoOrden);
		}
    
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			seleccionarVariableForm.setTipoOrden(tipoOrden);
		}

		if (seleccionarVariableForm.getFuncionCierre() != null) 
		{
			if (!seleccionarVariableForm.getFuncionCierre().equals("")) 
			{
				if (seleccionarVariableForm.getFuncionCierre().indexOf(";") < 0)
					seleccionarVariableForm.setFuncionCierre(seleccionarVariableForm.getFuncionCierre() + ";");
			}
			else 
				seleccionarVariableForm.setFuncionCierre(null);
		}

		if (request.getParameter("seleccionMultiple") != null) 
			seleccionarVariableForm.setSeleccionMultiple(new Byte(request.getParameter("seleccionMultiple")));

		seleccionarVariableForm.setListaVariables(getListaVariables(seleccionarVariableForm.getFiltroNombre()));

		if (seleccionarVariableForm.getListaVariables().size() > 0) 
		{
			seleccionarVariableForm.setSeleccionados(((ObjetoValorNombre)seleccionarVariableForm.getListaVariables().get(0)).getValor());
			seleccionarVariableForm.setValoresSeleccionados(((ObjetoValorNombre)seleccionarVariableForm.getListaVariables().get(0)).getNombre());
		}

		return mapping.findForward(forward);
	}

	private List<ObjetoValorNombre> getListaVariables(String filtroNombre)
	{
		List<ObjetoValorNombre> listaVariables = new ArrayList<ObjetoValorNombre>();
		List<TipoVariable> variables = TipoVariable.getTiposVariables();
		
		for (int i = 0; i < variables.size(); i++) 
		{
			TipoVariable tipoVariable = (TipoVariable)variables.get(i);
			ObjetoValorNombre elementoVariable = new ObjetoValorNombre();
			elementoVariable.setNombre(tipoVariable.getNombre());
			elementoVariable.setValor(tipoVariable.getTipoVariableId().toString());

			if ((filtroNombre != null) && (!filtroNombre.equals(""))) 
			{
				if (tipoVariable.getNombre().toLowerCase().indexOf(filtroNombre.toLowerCase()) > -1)
					listaVariables.add(elementoVariable);
			}
			else 
				listaVariables.add(elementoVariable);
		}

		return listaVariables;
	}
}