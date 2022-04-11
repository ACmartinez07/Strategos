package com.visiongc.app.strategos.web.struts.estadosacciones.actions;

import com.visiongc.app.strategos.estadosacciones.StrategosEstadosService;
import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.estadosacciones.forms.GestionarEstadosAccionesForm;
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

public class GestionarEstadosAccionesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarEstadosAccionesForm gestionarEstadoAccionesForm = (GestionarEstadosAccionesForm)form;

		int pagina = gestionarEstadoAccionesForm.getPagina();
		if (pagina < 1) 
			pagina = 1;

		StrategosEstadosService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosEstadosService();

		Map<String, String> filtros = new HashMap<String, String>();

		if ((gestionarEstadoAccionesForm.getFiltroNombre() != null) && (!gestionarEstadoAccionesForm.getFiltroNombre().equals(""))) 
			filtros.put("nombre", gestionarEstadoAccionesForm.getFiltroNombre());

		PaginaLista paginaEstadosAcciones = strategosUnidadesService.getEstadosAcciones(pagina, 30, "orden", "asc", true, filtros);

		paginaEstadosAcciones.setTamanoSetPaginas(5);

		request.setAttribute("paginaEstadosAcciones", paginaEstadosAcciones);

		strategosUnidadesService.close();

		if (paginaEstadosAcciones.getLista().size() > 0) 
		{
			if (gestionarEstadoAccionesForm.getSeleccionados() == null) 
			{
				EstadoAcciones estadoAcciones = (EstadoAcciones)paginaEstadosAcciones.getLista().get(0);
				gestionarEstadoAccionesForm.setSeleccionados(estadoAcciones.getEstadoId().toString());
				gestionarEstadoAccionesForm.setValoresSeleccionados(estadoAcciones.getNombre());
			}
		}
		else gestionarEstadoAccionesForm.setSeleccionados(null);

		return mapping.findForward(forward);
	}
}