package com.visiongc.app.strategos.web.struts.responsables.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.responsables.forms.GestionarResponsablesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarResponsablesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarResponsablesForm gestionarResponsablesForm = (GestionarResponsablesForm)form;

		String atributoOrden = gestionarResponsablesForm.getAtributoOrden();
		String tipoOrden = gestionarResponsablesForm.getTipoOrden();
		int pagina = gestionarResponsablesForm.getPagina();

		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			gestionarResponsablesForm.setAtributoOrden(atributoOrden);
		}
    
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarResponsablesForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
		gestionarResponsablesForm.setOrganizacionId((String)request.getSession().getAttribute("organizacionId"));

		Map filtros = new HashMap();

		if ((gestionarResponsablesForm.getFiltroCargo() != null) && (!gestionarResponsablesForm.getFiltroCargo().equals(""))) 
			filtros.put("cargo", gestionarResponsablesForm.getFiltroCargo());
		if ((gestionarResponsablesForm.getFiltroNombre() != null) && (!gestionarResponsablesForm.getFiltroNombre().equals(""))) 
			filtros.put("nombre", gestionarResponsablesForm.getFiltroNombre());

		if ((gestionarResponsablesForm.getOrganizacionId() != null) && (!gestionarResponsablesForm.getOrganizacionId().equals(""))) 
			filtros.put("organizacionId", gestionarResponsablesForm.getOrganizacionId());

		filtros.put("tipo", true);

		PaginaLista paginaResponsables = strategosResponsablesService.getResponsables(pagina, 30, atributoOrden, tipoOrden, true, filtros);
		paginaResponsables.setTamanoSetPaginas(5);

		request.setAttribute("paginaResponsables", paginaResponsables);

		strategosResponsablesService.close();

		if (paginaResponsables.getLista().size() > 0) 
		{
			Responsable responsable = (Responsable)paginaResponsables.getLista().get(0);
			gestionarResponsablesForm.setSeleccionados(responsable.getResponsableId().toString());
			gestionarResponsablesForm.setValoresSeleccionados(responsable.getNombre());
		} 
		else 
			gestionarResponsablesForm.setSeleccionados(null);

		return mapping.findForward(forward);
	}
}