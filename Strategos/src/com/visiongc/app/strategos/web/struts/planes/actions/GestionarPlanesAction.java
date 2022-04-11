package com.visiongc.app.strategos.web.struts.planes.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.TipoPlan;
import com.visiongc.app.strategos.web.struts.planes.forms.GestionarPlanesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.NavigationUrl;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarPlanesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		if ((url.indexOf("Estrategicos") > -1) || (url.indexOf("Operativos") > -1)) 
			navBar.agregarUrl(url, nombre, new Integer(2));
		else 
		{
			NavigationUrl puntoNavegacion = (NavigationUrl)navBar.getBarra().elementAt(1);
			navBar.agregarUrl(puntoNavegacion.getUrl(), puntoNavegacion.getNombre(), new Integer(2));
		}
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarPlanesForm gestionarPlanesForm = (GestionarPlanesForm)form;
		
		String atributoOrden = gestionarPlanesForm.getAtributoOrden();
		String tipoOrden = gestionarPlanesForm.getTipoOrden();
		int pagina = gestionarPlanesForm.getPagina();
		String organizacionId = (String)request.getSession().getAttribute("organizacionId");
		
		if (mapping.getPath().indexOf("Estrategicos") > -1)
			gestionarPlanesForm.setTipoPlanes(TipoPlan.getTipoPlanEstrategico());
		else if (mapping.getPath().indexOf("Operativos") > -1) 
			gestionarPlanesForm.setTipoPlanes(TipoPlan.getTipoPlanOperativo());
		gestionarPlanesForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("PLAN_VIEW"));
		gestionarPlanesForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("PLAN_EDIT"));

		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			gestionarPlanesForm.setAtributoOrden(atributoOrden);
		}
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarPlanesForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

		Map<String, String> filtros = new HashMap<String, String>();

		if ((gestionarPlanesForm.getFiltroNombre() != null) && (!gestionarPlanesForm.getFiltroNombre().equals(""))) 
			filtros.put("nombre", gestionarPlanesForm.getFiltroNombre());

		if ((organizacionId != null) && (!organizacionId.equals(""))) 
			filtros.put("organizacionId", organizacionId);

		filtros.put("tipo", gestionarPlanesForm.getTipoPlanes().toString());

		PaginaLista paginaPlanes = strategosPlanesService.getPlanes(pagina, 30, atributoOrden, tipoOrden, true, filtros);

		paginaPlanes.setTamanoSetPaginas(5);

		request.setAttribute("paginaPlanes", paginaPlanes);

		strategosPlanesService.close();

		if (paginaPlanes.getLista().size() > 0) 
		{
			Plan plan = (Plan)paginaPlanes.getLista().get(0);
			gestionarPlanesForm.setSeleccionados(plan.getPlanId().toString());
			gestionarPlanesForm.setValoresSeleccionados(plan.getNombre());
		} 
		else 
			gestionarPlanesForm.setSeleccionados(null);

		return mapping.findForward(forward);
	}
}