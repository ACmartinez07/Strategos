package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.SeleccionarIniciativasForm;
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

public final class SeleccionarIniciativasIniciativasAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SeleccionarIniciativasForm seleccionarIniciativasForm = (SeleccionarIniciativasForm)form;

		if (seleccionarIniciativasForm.getAtributoOrden() == null) 
			seleccionarIniciativasForm.setAtributoOrden("nombre");
    
		if (seleccionarIniciativasForm.getTipoOrden() == null) 
			seleccionarIniciativasForm.setTipoOrden("ASC");

		String llamadaDesde = request.getParameter("llamadaDesde");
		if (llamadaDesde != null) 
		{
			if (llamadaDesde.equals("Organizaciones"))
				seleccionarIniciativasForm.setPanelSeleccionado("panelOrganizaciones");
			else if (llamadaDesde.equals("Planes")) 
				seleccionarIniciativasForm.setPanelSeleccionado("panelPlanes");
		}
		
		StrategosIniciativasService iniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();

		Map<String, String> filtros = new HashMap<String, String>();

		if ((seleccionarIniciativasForm.getOrganizacionSeleccionadaId() != null) && (seleccionarIniciativasForm.getOrganizacionSeleccionadaId().byteValue() != 0)) 
			filtros.put("organizacionId", seleccionarIniciativasForm.getOrganizacionSeleccionadaId().toString());
		if (seleccionarIniciativasForm.getFrecuenciaSeleccionada() != null) 
			filtros.put("frecuencia", seleccionarIniciativasForm.getFrecuenciaSeleccionada().toString());
		if (seleccionarIniciativasForm.getExcluirIds() != null) 
			filtros.put("excluirIds", seleccionarIniciativasForm.getExcluirIds());
		filtros.put("historicoDate", "IS NULL");
		
		if ((seleccionarIniciativasForm.getPlanSeleccionadoId() != null) && (seleccionarIniciativasForm.getPlanSeleccionadoId().byteValue() != 0)) 
		{
			StrategosPlanesService planesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
			seleccionarIniciativasForm.setNombrePlan(((Plan)planesService.load(Plan.class, seleccionarIniciativasForm.getPlanSeleccionadoId())).getNombre());
			planesService.close();
		} 
		else 
			seleccionarIniciativasForm.setNombrePlan(null);

		PaginaLista paginaIniciativas = iniciativasService.getIniciativas(0, 0, seleccionarIniciativasForm.getAtributoOrden(), seleccionarIniciativasForm.getTipoOrden(), true, filtros);

		request.setAttribute("paginaIniciativas", paginaIniciativas);

		iniciativasService.close();

		if (paginaIniciativas.getLista().size() > 0) 
		{
			Iniciativa iniciativa = (Iniciativa)paginaIniciativas.getLista().get(0);
			seleccionarIniciativasForm.setSeleccionados(iniciativa.getIniciativaId().toString());
			seleccionarIniciativasForm.setValoresSeleccionados(iniciativa.getNombre());
		} 
		else 
			seleccionarIniciativasForm.setSeleccionados(null);

		seleccionarIniciativasForm.setIniciado(new Boolean(true));

		return mapping.findForward(forward);
	}
}