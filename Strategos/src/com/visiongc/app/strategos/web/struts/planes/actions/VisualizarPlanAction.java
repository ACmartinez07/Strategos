package com.visiongc.app.strategos.web.struts.planes.actions;

import java.util.ArrayList;
import java.util.List;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguration;
import com.visiongc.app.strategos.web.struts.planes.forms.VisualizarPlanForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.util.PermisologiaUsuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VisualizarPlanAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		VisualizarPlanForm visualizarPlanForm = (VisualizarPlanForm)form;

		visualizarPlanForm.inicializar(request);
		Usuario usuario = getUsuarioConectado(request);
		if (usuario.getPermitirConeccionVirtual())
			visualizarPlanForm.setPlanId(Long.parseLong((String)request.getSession().getAttribute("planId")));

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

		PermisologiaUsuario permisologiaUsuario = getPermisologiaUsuario(request);

		boolean autorizado = permisologiaUsuario.tienePermiso("PLAN", visualizarPlanForm.getOrganizacionId().longValue());

		if (!autorizado) 
			autorizado = strategosPlanesService.tienePermisoPlan(getUsuarioConectado(request), visualizarPlanForm.getPlanId(), "PLAN");
		if (!autorizado) 
			return mapping.findForward("permisoNegado");

		getBarraAreas(request, "strategos").setBotonSeleccionado("planes");

		String forward = mapping.getParameter();
		
		if (visualizarPlanForm.getMostrarTipoVistaDetallada() == null) 
		{
			visualizarPlanForm.setMostrarTipoVistaDetallada(new Boolean(StrategosWebConfiguration.getInstance().getBoolean("com.visiongc.app.strategos.web.planes.mostrartipovistadetallada")));
			visualizarPlanForm.setMostrarTipoVistaResumen(new Boolean(StrategosWebConfiguration.getInstance().getBoolean("com.visiongc.app.strategos.web.planes.mostrartipovistaresumen")));
			visualizarPlanForm.setMostrarTipoVistaEjecutiva(new Boolean(StrategosWebConfiguration.getInstance().getBoolean("com.visiongc.app.strategos.web.planes.mostrartipovistaejecutiva")));
			visualizarPlanForm.setMostrarTipoVistaArbol(new Boolean(StrategosWebConfiguration.getInstance().getBoolean("com.visiongc.app.strategos.web.planes.mostrartipovistaarbol")));
		}

		if ((visualizarPlanForm.getPlanId() != null) && (visualizarPlanForm.getPlanId().byteValue() != 0)) 
		{
			Plan plan = (Plan)strategosPlanesService.load(Plan.class, visualizarPlanForm.getPlanId());
			visualizarPlanForm.setNombrePlan(plan.getNombre());
			visualizarPlanForm.setPlan(plan);
			visualizarPlanForm.setClaseId(plan.getClaseId());
			PlantillaPlanes plantillaPlan = (PlantillaPlanes)strategosPlanesService.load(PlantillaPlanes.class, plan.getMetodologiaId());
			if (plantillaPlan != null)
				plantillaPlan.getElementos().size();
			visualizarPlanForm.setPlantillaPlan(plantillaPlan);
			visualizarPlanForm.setAnos(PeriodoUtil.getListaNumeros(new Integer(plan.getAnoInicial()), new Integer(plan.getAnoFinal())));
			
			strategosPlanesService.close();
		} 
		else 
			return getForwardBack(request, 2, true);

		return mapping.findForward(forward);
	}
}