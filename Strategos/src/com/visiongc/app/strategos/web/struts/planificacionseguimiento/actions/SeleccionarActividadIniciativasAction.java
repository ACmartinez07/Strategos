/**
 * 
 */
package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms.SeleccionarActividadForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public final class SeleccionarActividadIniciativasAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SeleccionarActividadForm seleccionarActividadForm = (SeleccionarActividadForm)form;

		if (seleccionarActividadForm.getAtributoOrden() == null) 
			seleccionarActividadForm.setAtributoOrden("nombre");
    
		if (seleccionarActividadForm.getTipoOrden() == null) 
			seleccionarActividadForm.setTipoOrden("ASC");

		String llamadaDesde = request.getParameter("llamadaDesde");
		if (llamadaDesde != null) 
		{
			if (llamadaDesde.equals("Organizaciones"))
				seleccionarActividadForm.setPanelSeleccionado("panelOrganizaciones");
			else if (llamadaDesde.equals("Planes")) 
				seleccionarActividadForm.setPanelSeleccionado("panelPlanes");
			else if (llamadaDesde.equals("Iniciativa")) 
				seleccionarActividadForm.setPanelSeleccionado("panelIniciativas");
		}
		
		StrategosIniciativasService iniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();

		Map<String, String> filtros = new HashMap<String, String>();

		if ((seleccionarActividadForm.getOrganizacionSeleccionadaId() != null) && (seleccionarActividadForm.getOrganizacionSeleccionadaId().byteValue() != 0)) 
			filtros.put("organizacionId", seleccionarActividadForm.getOrganizacionSeleccionadaId().toString());
		if (seleccionarActividadForm.getFrecuenciaSeleccionada() != null) 
			filtros.put("frecuencia", seleccionarActividadForm.getFrecuenciaSeleccionada().toString());
		if (seleccionarActividadForm.getExcluirIds() != null) 
			filtros.put("excluirIds", seleccionarActividadForm.getExcluirIds());
		filtros.put("historicoDate", "IS NULL");
		
		if ((seleccionarActividadForm.getPlanSeleccionadoId() != null) && (seleccionarActividadForm.getPlanSeleccionadoId().byteValue() != 0)) 
		{
			StrategosPlanesService planesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
			seleccionarActividadForm.setNombrePlan(((Plan)planesService.load(Plan.class, seleccionarActividadForm.getPlanSeleccionadoId())).getNombre());
			planesService.close();
		} 
		else 
			seleccionarActividadForm.setNombrePlan(null);

		PaginaLista paginaIniciativas = iniciativasService.getIniciativas(0, 0, seleccionarActividadForm.getAtributoOrden(), seleccionarActividadForm.getTipoOrden(), true, filtros);

		request.setAttribute("paginaIniciativas", paginaIniciativas);

		iniciativasService.close();

		if (paginaIniciativas.getLista().size() > 0) 
	    {
			Long iniciativaId = null;
	    	if ((seleccionarActividadForm.getIniciativaSeleccionadaId() == null) || (seleccionarActividadForm.getIniciativaSeleccionadaId().equals(""))) 
	    	{
	    		iniciativaId = ((Iniciativa)paginaIniciativas.getLista().get(0)).getIniciativaId();
	    		seleccionarActividadForm.setCambioIniciativa(true);
	    	}
	    	else if (seleccionarActividadForm.isCambioOrganizacion() && !seleccionarActividadForm.getCambioPlan()) 
	    	{
	    		iniciativaId = ((Iniciativa)paginaIniciativas.getLista().get(0)).getIniciativaId();
	    		seleccionarActividadForm.setCambioIniciativa(true);
	    	}
	    	else if (seleccionarActividadForm.getCambioPlan())
	    	{
	    		iniciativaId = ((Iniciativa)paginaIniciativas.getLista().get(0)).getIniciativaId();
	    		seleccionarActividadForm.setCambioIniciativa(true);
	    	}

	    	if (seleccionarActividadForm.getIniciativaSeleccionadaId() == null || (iniciativaId != null && seleccionarActividadForm.getIniciativaSeleccionadaId().longValue() != iniciativaId.longValue()))
	    		seleccionarActividadForm.setIniciativaSeleccionadaId(iniciativaId);

	    }
	    else 
	    	seleccionarActividadForm.setIniciativaSeleccionadaId(null);

		seleccionarActividadForm.setCambioOrganizacion(false);
		seleccionarActividadForm.setCambioPlan(false);
		
		return mapping.findForward(forward);
	}
}