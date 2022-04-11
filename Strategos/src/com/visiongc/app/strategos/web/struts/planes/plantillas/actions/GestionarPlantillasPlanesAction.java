package com.visiongc.app.strategos.web.struts.planes.plantillas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlantillasPlanesService;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.web.struts.planes.plantillas.forms.GestionarPlantillasPlanesForm;
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

public class GestionarPlantillasPlanesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarPlantillasPlanesForm gestionarPlantillasPlanesForm = (GestionarPlantillasPlanesForm)form;

		String atributoOrden = gestionarPlantillasPlanesForm.getAtributoOrden();
		String tipoOrden = gestionarPlantillasPlanesForm.getTipoOrden();
		int pagina = gestionarPlantillasPlanesForm.getPagina();
		gestionarPlantillasPlanesForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("METODOLOGIA_VIEW"));
		gestionarPlantillasPlanesForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("METODOLOGIA_EDIT"));

		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			gestionarPlantillasPlanesForm.setAtributoOrden(atributoOrden);
		}
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarPlantillasPlanesForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosPlantillasPlanesService strategosPlantillasPlanesService = StrategosServiceFactory.getInstance().openStrategosPlantillasPlanesService();

		Map<String, String> filtros = new HashMap<String, String>();

		if ((gestionarPlantillasPlanesForm.getFiltroNombre() != null) && (!gestionarPlantillasPlanesForm.getFiltroNombre().equals(""))) 
			filtros.put("nombre", gestionarPlantillasPlanesForm.getFiltroNombre());

		PaginaLista paginaPlantillasPlanes = strategosPlantillasPlanesService.getPlantillasPlanes(pagina, 30, atributoOrden, tipoOrden, true, filtros);

		paginaPlantillasPlanes.setTamanoSetPaginas(5);

		request.setAttribute("paginaPlantillasPlanes", paginaPlantillasPlanes);

		strategosPlantillasPlanesService.close();

		if (paginaPlantillasPlanes.getLista().size() > 0) 
		{
			PlantillaPlanes platilla = (PlantillaPlanes)paginaPlantillasPlanes.getLista().get(0);
			gestionarPlantillasPlanesForm.setSeleccionados(platilla.getPlantillaPlanesId().toString());
			gestionarPlantillasPlanesForm.setValoresSeleccionados(platilla.getNombre());
		} 
		else 
			gestionarPlantillasPlanesForm.setSeleccionados(null);

		return mapping.findForward(forward);
	}
}