/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.commons.util.HistoricoType;

/**
 * @author Kerwin
 *
 */
public class PlanEjecucionAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		ReporteForm reporteForm = (ReporteForm)form;
		reporteForm.clear();
	  
		/* Parametros para el reporte */
		String planId = request.getParameter("planId");
		String source = request.getParameter("source");
		Long iniciativaId = null;
		Long perspectivaId = null;

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		if (source.equals("Plan"))
			perspectivaId = request.getParameter("perspectivaId") != null ? Long.parseLong(request.getParameter("perspectivaId")) : null;
		else if (source.equals("Iniciativa") || source.equals("IniciativaGeneral") || source.equals("IniciativaPlan"))
		{
			iniciativaId = request.getParameter("iniciativaId") != null ? Long.parseLong(request.getParameter("iniciativaId")) : null;
			String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
			Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();

			FiltroForm filtro = new FiltroForm();
			filtro.setHistorico(selectHitoricoType);
			if (filtroNombre.equals(""))
				filtro.setNombre(null);
			else
				filtro.setNombre(filtroNombre);
			reporteForm.setFiltro(filtro);
		}
				   
	    /*Asigna a la Forma que genera reportes, el nombre de la organizacion y plan seleccionados*/
	    reporteForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
		reporteForm.setSource(source);
		
		Plan plan = null;
		Calendar ahora = Calendar.getInstance();
		if (planId != null && !planId.equals(""))
		{
		    plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));

			reporteForm.setNombrePlan(plan.getNombre());
			reporteForm.setPlanId(plan.getPlanId());
			reporteForm.setGrupoAnos(PeriodoUtil.getListaNumeros(plan.getAnoInicial(), plan.getAnoFinal()));
		}			
		else
			reporteForm.setGrupoAnos(PeriodoUtil.getListaNumeros(ahora.get(1) - 5, ahora.get(1) + 5));
    	reporteForm.setGrupoMeses(PeriodoUtil.getListaMeses());
    	reporteForm.setMesInicial("1");
    	reporteForm.setMesFinal("12");
    	   	
		if (source.equals("Plan"))
		{
			reporteForm.setObjetoSeleccionadoId(perspectivaId);
	    	reporteForm.setAnoInicial(reporteForm.getGrupoAnos().get(0).getValor());
	    	reporteForm.setAnoFinal(reporteForm.getGrupoAnos().get((reporteForm.getGrupoAnos().size() - 1)).getValor());
		}
		else if (source.equals("Iniciativa") || source.equals("IniciativaGeneral") || source.equals("IniciativaPlan"))
		{
			reporteForm.setObjetoSeleccionadoId(iniciativaId);
			reporteForm.setVisualizarIniciativas(true);
			reporteForm.setVisualizarIniciativas(true);
			reporteForm.setVisualizarIniciativasEjecutado(true);
			reporteForm.setVisualizarIniciativasMeta(true);
			reporteForm.setVisualizarIniciativasAlerta(true);
			reporteForm.setVisualizarActividad(true);
			reporteForm.setVisualizarActividadEjecutado(true);
			reporteForm.setVisualizarActividadMeta(true);
			reporteForm.setVisualizarActividadAlerta(true);
			
	    	reporteForm.setAnoInicial(((Integer) ahora.get(1)).toString());
			reporteForm.setAnoFinal(((Integer) ahora.get(1)).toString());
		}
	    
	    strategosPlanesService.close();

		return mapping.findForward(forward);
	}
}
