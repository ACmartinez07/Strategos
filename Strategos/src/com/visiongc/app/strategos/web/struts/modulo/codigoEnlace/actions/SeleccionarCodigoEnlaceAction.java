/**
 * 
 */
package com.visiongc.app.strategos.web.struts.modulo.codigoEnlace.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.modulo.codigoenlace.StrategosCodigoEnlaceService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.SelectorListaForm;

/**
 * @author Kerwin
 *
 */
public class SeleccionarCodigoEnlaceAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SelectorListaForm seleccionarCodigoEnlaceForm = (SelectorListaForm)form;

		String atributoOrden = seleccionarCodigoEnlaceForm.getAtributoOrden();
		String tipoOrden = seleccionarCodigoEnlaceForm.getTipoOrden();
		
		seleccionarCodigoEnlaceForm.setFuncionCierre(request.getParameter("funcionCierre"));

		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			seleccionarCodigoEnlaceForm.setAtributoOrden(atributoOrden);
		}
		
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			seleccionarCodigoEnlaceForm.setTipoOrden(tipoOrden);
		}

		if (seleccionarCodigoEnlaceForm.getFuncionCierre() != null) 
		{
			if (!seleccionarCodigoEnlaceForm.getFuncionCierre().equals("")) 
			{
				if (seleccionarCodigoEnlaceForm.getFuncionCierre().indexOf(";") < 0)
					seleccionarCodigoEnlaceForm.setFuncionCierre(seleccionarCodigoEnlaceForm.getFuncionCierre() + ";");
			}
			else 
				seleccionarCodigoEnlaceForm.setFuncionCierre(null);
		}
		seleccionarCodigoEnlaceForm.setSeleccionados(null);

		StrategosCodigoEnlaceService strategosCodigoEnlaceService = StrategosServiceFactory.getInstance().openStrategosCodigoEnlaceService();
		
		Map<String, String> filtros = new HashMap<String, String>();
		String valorBusqueda = (String)request.getParameter("valorBusqueda"); 
		if (valorBusqueda != null && !valorBusqueda.equals("")) 
			filtros.put("valorBusqueda", valorBusqueda);
		
		PaginaLista paginaCodigoEnlace = strategosCodigoEnlaceService.getCodigoEnlace(0, 0, atributoOrden, tipoOrden, true, filtros);
		request.setAttribute("paginaCodigoEnlace", paginaCodigoEnlace);

		strategosCodigoEnlaceService.close();
		
		return mapping.findForward(forward);
	}
}