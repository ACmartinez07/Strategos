package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.EditarPerspectivaForm;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.SeleccionarPerspectivasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarPerspectivasAction extends VgcAction
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
			if (funcion.equals("getRutaCompletaPerspectivaSeleccionada")) 
			{
				getRutaCompletaPerspectivaSeleccionada(request);
				return mapping.findForward("ajaxResponse");
			}
		}

		String actualizar = request.getParameter("actualizar");
		if ((actualizar != null) && (actualizar.equalsIgnoreCase("true")))
			return mapping.findForward(forward);

		SeleccionarPerspectivasForm seleccionarPerspectivasForm = (SeleccionarPerspectivasForm)form;
		seleccionarPerspectivasForm.clear();

		seleccionarPerspectivasForm.setNombreForma(request.getParameter("nombreForma"));
		seleccionarPerspectivasForm.setNombreCampoOculto(request.getParameter("nombreCampoOculto"));
		seleccionarPerspectivasForm.setNombreCampoValor(request.getParameter("nombreCampoValor"));
		seleccionarPerspectivasForm.setNombreCampoRutaCompleta(request.getParameter("nombreCampoRutaCompleta"));
		seleccionarPerspectivasForm.setFuncionCierre(request.getParameter("funcionCierre"));
		seleccionarPerspectivasForm.setSeleccionado(request.getParameter("seleccionado"));

		String permitirCambiarOrganizacion = request.getParameter("permitirCambiarOrganizacion");
		String organizacionId = request.getParameter("organizacionId");
		String permitirCambiarPlan = request.getParameter("permitirCambiarPlan");
		String planId = request.getParameter("planId");

		if ((permitirCambiarOrganizacion != null) && (permitirCambiarOrganizacion.equalsIgnoreCase("true"))) 
			seleccionarPerspectivasForm.setPermitirCambiarOrganizacion(new Boolean(true));
		if ((permitirCambiarPlan != null) && (permitirCambiarPlan.equalsIgnoreCase("true"))) 
			seleccionarPerspectivasForm.setPermitirCambiarPlan(new Boolean(true));
		if ((planId != null) && (!planId.equals("")) && (!planId.equals("0"))) 
		{
			StrategosPlanesService planesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
			Plan plan = (Plan)planesService.load(Plan.class, new Long(planId));
			if (plan != null) 
			{
				seleccionarPerspectivasForm.setPlanSeleccionadoId(plan.getPlanId());
				seleccionarPerspectivasForm.setOrganizacionSeleccionadaId(plan.getOrganizacionId());
			}
			
			planesService.close();
		}
    
		if (seleccionarPerspectivasForm.getPlanSeleccionadoId() == null) 
		{
			if ((organizacionId != null) && (!organizacionId.equals("")) && (!organizacionId.equals("0")))
				seleccionarPerspectivasForm.setOrganizacionSeleccionadaId(new Long(organizacionId));
			else 
				seleccionarPerspectivasForm.setOrganizacionSeleccionadaId(new Long((String)request.getSession().getAttribute("organizacionId")));
		}
    
		if (seleccionarPerspectivasForm.getFuncionCierre() != null) 
		{
			if (!seleccionarPerspectivasForm.getFuncionCierre().equals("")) 
			{
				if (seleccionarPerspectivasForm.getFuncionCierre().indexOf(";") < 0)
					seleccionarPerspectivasForm.setFuncionCierre(seleccionarPerspectivasForm.getFuncionCierre() + ";");
			}
			else 
				seleccionarPerspectivasForm.setFuncionCierre(null);
		}

		return mapping.findForward(forward);
	}

	private void getRutaCompletaPerspectivaSeleccionada(HttpServletRequest request)
	{
		String seleccionadoId = request.getParameter("seleccionados");
		String rutaCompletaPerspectivaSeleccionada = "";

		StrategosPerspectivasService perspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
		Perspectiva perspectiva = (Perspectiva)perspectivasService.load(Perspectiva.class, new Long(seleccionadoId));
		if (perspectiva != null) 
			rutaCompletaPerspectivaSeleccionada = perspectivasService.getRutaCompletaPerspectivaSinPorcentajes(perspectiva, new EditarPerspectivaForm().getSeparadorRuta());
		else 
			rutaCompletaPerspectivaSeleccionada = "!ELIMINADO!";

		perspectivasService.close();

		request.setAttribute("ajaxResponse", rutaCompletaPerspectivaSeleccionada);
	}
}