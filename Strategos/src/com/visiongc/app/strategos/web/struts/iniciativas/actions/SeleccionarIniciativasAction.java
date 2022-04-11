package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.SeleccionarIniciativasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarIniciativasAction extends VgcAction
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
			if (funcion.equals("getRutaCompletaIniciativasSeleccionadas")) 
			{
				getRutaCompletaIniciativasSeleccionadas(request);
				return mapping.findForward("ajaxResponse");
			}
		}

		String actualizar = request.getParameter("actualizar");
		if ((actualizar != null) && (actualizar.equalsIgnoreCase("true")))
			return mapping.findForward(forward);

		SeleccionarIniciativasForm seleccionarIniciativasForm = (SeleccionarIniciativasForm)form;

		seleccionarIniciativasForm.clear();

		String permitirCambiarOrganizacion = request.getParameter("permitirCambiarOrganizacion");
		String organizacionId = request.getParameter("organizacionId");
		String permitirCambiarPlan = request.getParameter("permitirCambiarPlan");
		String planId = request.getParameter("planId");
		String frecuencia = request.getParameter("frecuencia");
		String seleccionMultiple = request.getParameter("seleccionMultiple");
		
		seleccionarIniciativasForm.setNombreForma(request.getParameter("nombreForma"));
		seleccionarIniciativasForm.setNombreCampoOculto(request.getParameter("nombreCampoOculto"));
		seleccionarIniciativasForm.setNombreCampoValor(request.getParameter("nombreCampoValor"));
		seleccionarIniciativasForm.setNombreCampoRutasCompletas(request.getParameter("nombreCampoRutasCompletas"));
		seleccionarIniciativasForm.setNombreCampoPlanId(request.getParameter("nombreCampoPlanId"));
		seleccionarIniciativasForm.setSeleccionados(request.getParameter("seleccionados"));
		seleccionarIniciativasForm.setFuncionCierre(request.getParameter("funcionCierre"));
		seleccionarIniciativasForm.setExcluirIds(request.getParameter("iniciativaId"));

		if (seleccionarIniciativasForm.getExcluirIds() != null && (frecuencia == null || frecuencia.equals("")))
		{
			StrategosIniciativasService iniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			Iniciativa iniciativa = (Iniciativa)iniciativasService.load(Iniciativa.class, new Long(seleccionarIniciativasForm.getExcluirIds()));
			frecuencia = iniciativa.getFrecuencia().toString();
			iniciativasService.close();
		}

		if ((permitirCambiarOrganizacion != null) && (permitirCambiarOrganizacion.equalsIgnoreCase("true"))) 
			seleccionarIniciativasForm.setPermitirCambiarOrganizacion(new Boolean(true));
		if ((permitirCambiarPlan != null) && (permitirCambiarPlan.equalsIgnoreCase("true"))) 
			seleccionarIniciativasForm.setPermitirCambiarPlan(new Boolean(true));
		if ((planId != null) && (!planId.equals("")) && (!planId.equals("0"))) 
		{
			StrategosIniciativasService iniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			Plan plan = (Plan)iniciativasService.load(Plan.class, new Long(planId));
			if (plan != null) 
			{
				seleccionarIniciativasForm.setPlanSeleccionadoId(plan.getPlanId());
				seleccionarIniciativasForm.setOrganizacionSeleccionadaId(plan.getOrganizacionId());
			}
			iniciativasService.close();
		}
		if (seleccionarIniciativasForm.getPlanSeleccionadoId() == null) 
		{
			if ((organizacionId != null) && (!organizacionId.equals("")) && (!organizacionId.equals("0")))
				seleccionarIniciativasForm.setOrganizacionSeleccionadaId(new Long(organizacionId));
			else 
				seleccionarIniciativasForm.setOrganizacionSeleccionadaId(new Long((String)request.getSession().getAttribute("organizacionId")));
		}
		if ((frecuencia != null) && (!frecuencia.equals(""))) 
			seleccionarIniciativasForm.setFrecuenciaSeleccionada(new Byte(frecuencia));
		if ((seleccionMultiple != null) && (seleccionMultiple.equalsIgnoreCase("true"))) 
			seleccionarIniciativasForm.setSeleccionMultiple(new Boolean(true));
		if (seleccionarIniciativasForm.getFuncionCierre() != null) 
		{
			if (!seleccionarIniciativasForm.getFuncionCierre().equals("")) 
			{
				if (seleccionarIniciativasForm.getFuncionCierre().indexOf(";") < 0)
					seleccionarIniciativasForm.setFuncionCierre(seleccionarIniciativasForm.getFuncionCierre() + ";");
			}
			else 
				seleccionarIniciativasForm.setFuncionCierre(null);
		}

		return mapping.findForward(forward);
	}

	private void getRutaCompletaIniciativasSeleccionadas(HttpServletRequest request)
	{
		String seleccionados = request.getParameter("seleccionados");
		String rutasCompletasIniciativasSeleccionadas = "";
		String[] arregloIniciativasSeleccionadas = seleccionados.split("!;!");

		StrategosIniciativasService iniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();

		for (int i = 0; i < arregloIniciativasSeleccionadas.length; i++) 
		{
			String seleccionadoId = arregloIniciativasSeleccionadas[i];
			String rutaCompletaIniciativaSeleccionada = "";
			Iniciativa iniciativa = (Iniciativa) iniciativasService.load(Iniciativa.class, new Long(seleccionadoId));
			if (iniciativa != null) 
			{
				rutaCompletaIniciativaSeleccionada = iniciativasService.getRutaCompletaIniciativa(iniciativa, "!#!");

				agregarLockPoolLocksUsoEdicion(request, iniciativasService, iniciativa.getIniciativaId());
			} 
			else 
				rutaCompletaIniciativaSeleccionada = "!ELIMINADO!";
      
			rutasCompletasIniciativasSeleccionadas = rutasCompletasIniciativasSeleccionadas + "!;!" + rutaCompletaIniciativaSeleccionada;
		}

		rutasCompletasIniciativasSeleccionadas = rutasCompletasIniciativasSeleccionadas.substring("!;!".length());
		
		iniciativasService.close();
		
		request.setAttribute("ajaxResponse", rutasCompletasIniciativasSeleccionadas);
	}
}