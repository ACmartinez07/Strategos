package com.visiongc.app.strategos.web.struts.planes.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.planes.forms.EditarPlanForm;
import com.visiongc.app.strategos.web.struts.planes.forms.GestionarPlanesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarPlanAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		if (request.getParameter("funcion") != null) 
		{
			String funcion = request.getParameter("funcion");
			if (funcion.equals("getMetodologiaPlanImpacta")) 
			{
				getMetodologiaPlanImpacta(request);
				return mapping.findForward("ajaxResponse");
			}
		}

		EditarPlanForm editarPlanForm = (EditarPlanForm)form;

		ActionMessages messages = getMessages(request);

		String planId = request.getParameter("planId");

		boolean verForm = getPermisologiaUsuario(request).tienePermiso("PLAN_VIEW");
		boolean editarForm = getPermisologiaUsuario(request).tienePermiso("PLAN_EDIT");
		boolean bloqueado = false;

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

		if ((planId != null) && (!planId.equals("")) && (!planId.equals("0")))
		{
			bloqueado = !strategosPlanesService.lockForUpdate(request.getSession().getId(), planId, null);

			editarPlanForm.setBloqueado(new Boolean(bloqueado));

			Plan plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));

			if (plan != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));

				editarPlanForm.setPlanId(plan.getPlanId());
				editarPlanForm.setOrganizacionId(plan.getOrganizacionId());
				editarPlanForm.setPlanImpactaId(plan.getPlanImpactaId());
				if (plan.getPlanImpactaId() != null) 
					editarPlanForm.setPlanImpactaNombre(plan.getPlanImpacta().getOrganizacion().getNombre() + " / " + plan.getPlanImpacta().getNombre());

				editarPlanForm.setNombre(plan.getNombre());
				editarPlanForm.setAnoInicial(plan.getAnoInicial());
				editarPlanForm.setAnoFinal(plan.getAnoFinal());
				editarPlanForm.setTipo(plan.getTipo());
				editarPlanForm.setActivo(plan.getActivo());
				editarPlanForm.setRevision(plan.getRevision());
				editarPlanForm.setMetodologiaId(plan.getMetodologiaId());
				editarPlanForm.setMetodologiaNombre(plan.getMetodologia().getNombre());
				editarPlanForm.setClaseId(plan.getClaseId());
				editarPlanForm.setClaseIndicadoresNombre(plan.getClase().getNombre());

				StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();

				Perspectiva perspectiva = strategosPerspectivasService.getPerspectivaRaiz(plan.getPlanId());
				if ((perspectiva != null) && (perspectiva.getHijos().size() > 0))
					editarPlanForm.setTienePerspectivas(new Boolean(true));
				else 
					editarPlanForm.setTienePerspectivas(new Boolean(false));
				
				strategosPerspectivasService.close();
			}
			else
			{
				strategosPlanesService.unlockObject(request.getSession().getId(), new Long(planId));
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		else
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			if (!strategosIndicadoresService.checkLicencia(request))
			{
				strategosIndicadoresService.close();
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("action.guardarregistro.limite.exedido"));
				this.saveMessages(request, messages);
				
				return this.getForwardBack(request, 1, false);
			}
			strategosIndicadoresService.close();
			editarPlanForm.clear();
			editarPlanForm.setOrganizacionId(new Long((String)request.getSession().getAttribute("organizacionId")));
			GestionarPlanesForm gestionarPlanesForm = (GestionarPlanesForm)request.getSession().getAttribute("gestionarPlanesForm");
			editarPlanForm.setTipo(gestionarPlanesForm.getTipoPlanes());
		}
		
		strategosPlanesService.close();

		if (!bloqueado && verForm && !editarForm)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
			editarPlanForm.setBloqueado(true);
		}
		else if (!bloqueado && !verForm && !editarForm)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));

		saveMessages(request, messages);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);
    
		return mapping.findForward(forward);
	}

	private void getMetodologiaPlanImpacta(HttpServletRequest request)
	{
		String planImpactaId = request.getParameter("planImpactaId");
		String valores = "";
		
		StrategosPlanesService planesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

		Plan plan = (Plan)planesService.load(Plan.class, new Long(planImpactaId));
		if (plan != null) 
			valores = plan.getMetodologiaId().toString() + "!;!" + plan.getMetodologia().getNombre();

		planesService.close();

		request.setAttribute("ajaxResponse", valores);
	}
}