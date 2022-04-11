package com.visiongc.app.strategos.web.struts.seriestiempo.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.seriestiempo.forms.GestionarSeriesTiempoForm;
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

public class GestionarSeriesTiempoAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();

		GestionarSeriesTiempoForm gestionarSeriesTiempoForm = (GestionarSeriesTiempoForm)form;
		
		String atributoOrden = gestionarSeriesTiempoForm.getAtributoOrden();
		String tipoOrden = gestionarSeriesTiempoForm.getTipoOrden();
		int pagina = gestionarSeriesTiempoForm.getPagina();

		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			gestionarSeriesTiempoForm.setAtributoOrden(atributoOrden);
		}

		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarSeriesTiempoForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService();

		Map<Object, Object> filtros = new HashMap<Object, Object>();

		filtros.put("oculta", false);
		if ((gestionarSeriesTiempoForm.getFiltroNombre() != null) && (!gestionarSeriesTiempoForm.getFiltroNombre().equals(""))) 
			filtros.put("nombre", gestionarSeriesTiempoForm.getFiltroNombre());

		PaginaLista paginaSeriesTiempo = strategosSeriesTiempoService.getSeriesTiempo(pagina, 30, atributoOrden, tipoOrden, true, filtros);

		paginaSeriesTiempo.setTamanoSetPaginas(5);

		request.setAttribute("paginaSeriesTiempo", paginaSeriesTiempo);

		strategosSeriesTiempoService.close();

		if (paginaSeriesTiempo.getLista().size() > 0) 
		{
			SerieTiempo serieTiempo = (SerieTiempo)paginaSeriesTiempo.getLista().get(0);
			gestionarSeriesTiempoForm.setSeleccionados(serieTiempo.getSerieId().toString());
			gestionarSeriesTiempoForm.setValoresSeleccionados(serieTiempo.getIdentificador());
		} 
		else 
			gestionarSeriesTiempoForm.setSeleccionados(null);

		return mapping.findForward(forward);
	}
}