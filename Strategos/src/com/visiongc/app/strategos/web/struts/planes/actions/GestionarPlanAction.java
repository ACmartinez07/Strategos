package com.visiongc.app.strategos.web.struts.planes.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.web.struts.planes.forms.GestionarPlanForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarPlanAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		Usuario usuario = getUsuarioConectado(request);
		if (!usuario.getPermitirConeccionVirtual())
			getBarraAreas(request, "strategos").setBotonSeleccionado("planes");

		String forward = mapping.getParameter();

		if (request.getParameter("funcion") != null) 
		{
			String funcion = request.getParameter("funcion");
			if (funcion.equals("getPlanActivoId")) 
			{
				getPlanActivoId(request);
				return mapping.findForward("ajaxResponse");
			}
		}

		//PermisologiaUsuario permisologiaUsuario = getPermisologiaUsuario(request);
		if (!usuario.getPermitirConeccionVirtual())
			request.getSession().setAttribute("alerta", new com.visiongc.framework.web.struts.alertas.actions.GestionarAlertasAction().getAlerta(usuario));
		
		//boolean puedeGestionar = true;
		
		//long organizacionId = Long.parseLong((String)request.getSession().getAttribute("organizacionId"));

		//if (!permisologiaUsuario.tienePermiso("PLAN_ADD", organizacionId)) 
			//puedeGestionar = false;
		
		//if (!permisologiaUsuario.tienePermiso("PLAN_EDIT", organizacionId)) 
			//puedeGestionar = false;
		
		//if (!puedeGestionar)
			//return mapping.findForward("visualizarPlanAction");

		request.getSession().removeAttribute("perspectiva");
		request.getSession().removeAttribute("perspectivaId");
		
		GestionarPlanForm gestionarPlanForm = (GestionarPlanForm)form;
		
		if ((gestionarPlanForm.getPlanId() != null) && (gestionarPlanForm.getPlanId().longValue() != 0)) 
		{
			StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
			Plan plan = (Plan)strategosPlanesService.load(Plan.class, gestionarPlanForm.getPlanId());
			gestionarPlanForm.setNombrePlan(plan.getNombre());
			gestionarPlanForm.setClaseId(plan.getClaseId());
			PlantillaPlanes plantillaPlanes = (PlantillaPlanes)strategosPlanesService.load(PlantillaPlanes.class, plan.getMetodologiaId());
			gestionarPlanForm.setPlantillaPlanes(plantillaPlanes);
			strategosPlanesService.close();
			
			request.getSession().setAttribute("planActivoId", plan.getPlanId().toString());
		} 
		else 
			return getForwardBack(request, 2, true);

		return mapping.findForward(forward);
	}

	private void getPlanActivoId(HttpServletRequest request)
	{
		String organizacionId = (String)request.getSession().getAttribute("organizacionId");
		String planId = "";

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

		Map<String, String> filtros = new HashMap<String, String>();
		filtros.put("organizacionId", organizacionId);

		List<Plan> planes = strategosPlanesService.getPlanes(0, 0, null, null, true, filtros).getLista();
		
		for (Iterator<Plan> iter = planes.iterator(); iter.hasNext(); ) 
		{
			Plan plan = (Plan)iter.next();
			if (plan.getActivo().booleanValue()) 
				planId = planId + plan.getPlanId().toString() + ",";
		}
		if (planId.length() > 1) 
			planId = planId.substring(0, planId.length() - 1);

		strategosPlanesService.close();
		
		request.setAttribute("ajaxResponse", planId);
	}
}