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
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms.SeleccionarActividadForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public final class SeleccionarActividadActividadesAction extends VgcAction
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
		
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

		Map<String, String> filtros = new HashMap<String, String>();

		if ((seleccionarActividadForm.getOrganizacionSeleccionadaId() != null) && (seleccionarActividadForm.getOrganizacionSeleccionadaId().byteValue() != 0)) 
			filtros.put("organizacionId", seleccionarActividadForm.getOrganizacionSeleccionadaId().toString());
		if (seleccionarActividadForm.getFrecuenciaSeleccionada() != null) 
			filtros.put("frecuencia", seleccionarActividadForm.getFrecuenciaSeleccionada().toString());
		if (seleccionarActividadForm.getExcluirIds() != null) 
			filtros.put("excluirIds", seleccionarActividadForm.getExcluirIds());
		
		if ((seleccionarActividadForm.getPlanSeleccionadoId() != null) && (seleccionarActividadForm.getPlanSeleccionadoId().byteValue() != 0)) 
		{
			filtros.put("planId", seleccionarActividadForm.getPlanSeleccionadoId().toString());
			StrategosPlanesService planesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
			seleccionarActividadForm.setNombrePlan(((Plan)planesService.load(Plan.class, seleccionarActividadForm.getPlanSeleccionadoId())).getNombre());
			planesService.close();
		} 
		else 
			seleccionarActividadForm.setNombrePlan(null);
		
		if ((seleccionarActividadForm.getIniciativaSeleccionadaId() != null) && (seleccionarActividadForm.getIniciativaSeleccionadaId().byteValue() != 0)) 
		{
			filtros.put("iniciativaId", seleccionarActividadForm.getIniciativaSeleccionadaId().toString());
			Iniciativa iniciativa = (Iniciativa) strategosPryActividadesService.load(Iniciativa.class, seleccionarActividadForm.getIniciativaSeleccionadaId());
			if (iniciativa.getProyectoId() != null)
				filtros.put("proyectoId", iniciativa.getProyectoId().toString());
			
			seleccionarActividadForm.setNombreIniciativa(iniciativa.getNombre());
		} 
		else 
			seleccionarActividadForm.setNombreIniciativa(null);
		
		PaginaLista paginaActividades = strategosPryActividadesService.getActividades(0, 0, seleccionarActividadForm.getAtributoOrden(), seleccionarActividadForm.getTipoOrden(), true, filtros);

		request.setAttribute("paginaActividades", paginaActividades);

		strategosPryActividadesService.close();

		if (paginaActividades.getLista().size() > 0) 
		{
			PryActividad actividad = (PryActividad)paginaActividades.getLista().get(0);
			seleccionarActividadForm.setSeleccionados(actividad.getActividadId().toString());
			seleccionarActividadForm.setValoresSeleccionados(actividad.getNombre());
		} 
		else 
			seleccionarActividadForm.setSeleccionados(null);

		seleccionarActividadForm.setIniciado(new Boolean(true));
		
		return mapping.findForward(forward);
	}
}