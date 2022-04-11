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
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm.PeriodoStatus;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.commons.web.NavigationBar;

/**
 * Documentacion:
 * 
 * Clase para la forma de parametros del reporte consolidado.
 * 
 * @author Kerwin
 *
 */
public class PlanConsolidadoAction  extends VgcAction
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
		Long perspectivaId = request.getParameter("perspectivaId") != null ? Long.parseLong(request.getParameter("perspectivaId")) : null;
				   
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
	    Plan plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));
	     
	    /*Asigna a la Forma que genera reportes, el nombre de la organizacion y plan seleccionados*/
	    reporteForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
		reporteForm.setNombrePlan(plan.getNombre());
		reporteForm.setPlanId(plan.getPlanId());
		reporteForm.setObjetoSeleccionadoId(perspectivaId);		
    	reporteForm.setGrupoAnos(PeriodoUtil.getListaNumeros(plan.getAnoInicial(), plan.getAnoFinal()));
    	reporteForm.setAno(Integer.parseInt(((ObjetoClaveValor)reporteForm.getGrupoAnos().get(0)).getValor()));
    	reporteForm.setTipoPeriodo(reporteForm.getPeriodoAlCorte());
		reporteForm.setGrupoAnos(PeriodoUtil.getListaNumeros(plan.getAnoInicial(), plan.getAnoFinal()));
    	reporteForm.setGrupoMeses(PeriodoUtil.getListaMeses());
    	reporteForm.setMesInicial("1");
    	reporteForm.setAnoInicial(plan.getAnoInicial().toString());
    	reporteForm.setAlcance(reporteForm.getAlcanceObjetivo());
    	
	    strategosPlanesService.close();

		return mapping.findForward(forward);
	}
}
