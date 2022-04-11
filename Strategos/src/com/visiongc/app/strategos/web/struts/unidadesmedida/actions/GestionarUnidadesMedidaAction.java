package com.visiongc.app.strategos.web.struts.unidadesmedida.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.web.struts.unidadesmedida.forms.GestionarUnidadesMedidaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarUnidadesMedidaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();

		GestionarUnidadesMedidaForm gestionarUnidadesMedidaForm = (GestionarUnidadesMedidaForm)form;

		String atributoOrden = gestionarUnidadesMedidaForm.getAtributoOrden();
		String tipoOrden = gestionarUnidadesMedidaForm.getTipoOrden();
		int pagina = gestionarUnidadesMedidaForm.getPagina();

		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			gestionarUnidadesMedidaForm.setAtributoOrden(atributoOrden);
		}
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarUnidadesMedidaForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();

		Map<String, String> filtros = new HashMap<String, String>();

		if ((gestionarUnidadesMedidaForm.getFiltroNombre() != null) && (!gestionarUnidadesMedidaForm.getFiltroNombre().equals(""))) 
			filtros.put("nombre", gestionarUnidadesMedidaForm.getFiltroNombre());

		PaginaLista paginaUnidades = strategosUnidadesService.getUnidadesMedida(pagina, 30, atributoOrden, tipoOrden, true, filtros);
		
		paginaUnidades.setTamanoSetPaginas(5);
		
		request.setAttribute("paginaUnidades", paginaUnidades);
		
		strategosUnidadesService.close();

		if (paginaUnidades.getLista().size() > 0) 
		{
			UnidadMedida unidadMedida = (UnidadMedida)paginaUnidades.getLista().get(0);
			gestionarUnidadesMedidaForm.setSeleccionados(unidadMedida.getUnidadId().toString());
			gestionarUnidadesMedidaForm.setValoresSeleccionados(unidadMedida.getNombre());
		} 
		else 
			gestionarUnidadesMedidaForm.setSeleccionados(null);

		return mapping.findForward(forward);
	}
}