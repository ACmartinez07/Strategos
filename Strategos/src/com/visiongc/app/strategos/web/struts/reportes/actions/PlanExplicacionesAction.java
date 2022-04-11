/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.explicaciones.model.Explicacion.ObjetivoKey;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class PlanExplicacionesAction  extends VgcAction
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
				   
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
	    Plan plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));
	    PlantillaPlanes plantillaPlanes = ((PlantillaPlanes)strategosPlanesService.load(PlantillaPlanes.class, new Long(plan.getMetodologiaId())));
	     
	    /*Asigna a la Forma que genera reportes, el nombre de la organizacion y plan seleccionados*/
	    reporteForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
		reporteForm.setNombrePlan(plan.getNombre());
		reporteForm.setPlanId(plan.getPlanId());
    	
		ObjetoClaveValor elementoClaveValor;
		List<ObjetoClaveValor> listaObjetos = new ArrayList<ObjetoClaveValor>();

		// Todos
		elementoClaveValor = new ObjetoClaveValor();
		elementoClaveValor.setClave("-1");
		elementoClaveValor.setValor("Todos");
		listaObjetos.add(elementoClaveValor);

		// Indicador
		elementoClaveValor = new ObjetoClaveValor();
		elementoClaveValor.setClave(ObjetivoKey.getKeyIndicador().toString());
		elementoClaveValor.setValor(plantillaPlanes.getNombreIndicadorSingular());
		listaObjetos.add(elementoClaveValor);
		
		// Iniciativa
		elementoClaveValor = new ObjetoClaveValor();
		elementoClaveValor.setClave(ObjetivoKey.getKeyIniciativa().toString());
		elementoClaveValor.setValor(plantillaPlanes.getNombreIniciativaSingular());
		listaObjetos.add(elementoClaveValor);
		
		reporteForm.setGrupoStatus(listaObjetos);
    	
	    strategosPlanesService.close();

		return mapping.findForward(forward);
	}
}
