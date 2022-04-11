/**
 * 
 */
package com.visiongc.app.strategos.web.struts.iniciativas.estatus.actions;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.web.struts.iniciativas.estatus.forms.GestionarIniciativaEstatusForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class GestionarIniciativaEstatusAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarIniciativaEstatusForm gestionarIniciativaEstatusForm = (GestionarIniciativaEstatusForm)form;

		int pagina = gestionarIniciativaEstatusForm.getPagina();
		if (pagina < 1) 
			pagina = 1;

		StrategosIniciativaEstatusService strategosIniciativaEstatusService = StrategosServiceFactory.getInstance().openStrategosIniciativaEstatusService();

		Map<String, String> filtros = new HashMap<String, String>();

		if ((gestionarIniciativaEstatusForm.getFiltroNombre() != null) && (!gestionarIniciativaEstatusForm.getFiltroNombre().equals(""))) 
			filtros.put("nombre", gestionarIniciativaEstatusForm.getFiltroNombre());

		PaginaLista paginaIniciativaEstatus = strategosIniciativaEstatusService.getIniciativaEstatus(pagina, 30, "id", "asc", true, filtros);

		paginaIniciativaEstatus.setTamanoSetPaginas(5);

		request.setAttribute("paginaIniciativaEstatus", paginaIniciativaEstatus);

		strategosIniciativaEstatusService.close();

		if (paginaIniciativaEstatus.getLista().size() > 0) 
		{
			if (gestionarIniciativaEstatusForm.getSeleccionados() == null) 
			{
				IniciativaEstatus iniciativaEstatus = (IniciativaEstatus)paginaIniciativaEstatus.getLista().get(0);
				gestionarIniciativaEstatusForm.setSeleccionados(iniciativaEstatus.getId().toString());
				gestionarIniciativaEstatusForm.setValoresSeleccionados(iniciativaEstatus.getNombre());
			}
		}
		else gestionarIniciativaEstatusForm.setSeleccionados(null);

		return mapping.findForward(forward);
	}
}
