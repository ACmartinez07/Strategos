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
public final class SeleccionarActividadPlanesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

	    String forward = mapping.getParameter();

	    SeleccionarActividadForm seleccionarActividadForm = (SeleccionarActividadForm)form;

	    if (!seleccionarActividadForm.getPermitirCambiarPlan().booleanValue())
	    	return mapping.findForward(forward);

	    if (seleccionarActividadForm.getAtributoOrdenPlanes() == null) 
	    	seleccionarActividadForm.setAtributoOrdenPlanes("nombre");
	    if (seleccionarActividadForm.getTipoOrdenPlanes() == null) 
	    	seleccionarActividadForm.setTipoOrdenPlanes("ASC");

	    StrategosPlanesService planesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

	    Map<String, String> filtros = new HashMap<String, String>();

	    if ((seleccionarActividadForm.getOrganizacionSeleccionadaId() != null) && (!seleccionarActividadForm.getOrganizacionSeleccionadaId().equals(""))) 
	    	filtros.put("organizacionId", seleccionarActividadForm.getOrganizacionSeleccionadaId().toString());

	    PaginaLista paginaPlanes = planesService.getPlanes(0, 0, seleccionarActividadForm.getAtributoOrdenPlanes(), seleccionarActividadForm.getTipoOrdenPlanes(), true, filtros);

	    request.getSession().setAttribute("paginaPlanes", paginaPlanes);

	    planesService.close();

	    if (paginaPlanes.getLista().size() > 0) 
	    {
	    	seleccionarActividadForm.setCambioPlan(false);
	    	Long planId = null;
	    	if ((seleccionarActividadForm.getPlanSeleccionadoId() == null) || (seleccionarActividadForm.getPlanSeleccionadoId().equals(""))) 
	    	{
	    		planId = ((Plan)paginaPlanes.getLista().get(0)).getPlanId();
	    		seleccionarActividadForm.setCambioPlan(true);
	    	}
	    	else if (seleccionarActividadForm.isCambioOrganizacion()) 
	    	{
	    		planId = ((Plan)paginaPlanes.getLista().get(0)).getPlanId();
	    		seleccionarActividadForm.setCambioPlan(true);
	    	}

	    	if (seleccionarActividadForm.getPlanSeleccionadoId() == null || (planId != null && seleccionarActividadForm.getPlanSeleccionadoId().longValue() != planId.longValue()))
	    		seleccionarActividadForm.setPlanSeleccionadoId(planId);
	    }
	    else 
	    	seleccionarActividadForm.setPlanSeleccionadoId(null);
	    
	    seleccionarActividadForm.setCambioOrganizacion(false);
	    seleccionarActividadForm.setCambioIniciativa(false);

	    return mapping.findForward(forward);
	}
}