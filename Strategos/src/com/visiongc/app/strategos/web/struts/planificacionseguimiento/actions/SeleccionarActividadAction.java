/**
 * 
 */
package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms.SeleccionarActividadForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public final class SeleccionarActividadAction  extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		String actualizar = request.getParameter("actualizar");
		if ((actualizar != null) && (actualizar.equalsIgnoreCase("true")))
			return mapping.findForward(forward);

		SeleccionarActividadForm seleccionarActividadForm = (SeleccionarActividadForm)form;

		seleccionarActividadForm.clear();

		String permitirCambiarOrganizacion = request.getParameter("permitirCambiarOrganizacion");
		String organizacionId = request.getParameter("organizacionId");
		String permitirCambiarPlan = request.getParameter("permitirCambiarPlan");
		String planId = request.getParameter("planId");
		String permitirCambiarIniciativa = request.getParameter("permitirCambiarIniciativa");
		String iniciativaId = request.getParameter("iniciativaId");
		String frecuencia = request.getParameter("frecuencia");
		
		seleccionarActividadForm.setNombreForma(request.getParameter("nombreForma"));
		seleccionarActividadForm.setNombreCampoOculto(request.getParameter("nombreCampoOculto"));
		seleccionarActividadForm.setNombreCampoValor(request.getParameter("nombreCampoValor"));
		seleccionarActividadForm.setNombreCampoRutasCompletas(request.getParameter("nombreCampoRutasCompletas"));
		seleccionarActividadForm.setNombreCampoPlanId(request.getParameter("nombreCampoPlanId"));
		seleccionarActividadForm.setSeleccionados(request.getParameter("seleccionados"));
		seleccionarActividadForm.setFuncionCierre(request.getParameter("funcionCierre"));
		seleccionarActividadForm.setExcluirIds(iniciativaId);

		if (seleccionarActividadForm.getExcluirIds() != null && (frecuencia == null || frecuencia.equals("")))
		{
			StrategosIniciativasService iniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			Iniciativa iniciativa = (Iniciativa)iniciativasService.load(Iniciativa.class, new Long(seleccionarActividadForm.getExcluirIds()));
			frecuencia = iniciativa.getFrecuencia().toString();
			iniciativasService.close();
		}

		if ((permitirCambiarOrganizacion != null) && (permitirCambiarOrganizacion.equalsIgnoreCase("true"))) 
			seleccionarActividadForm.setPermitirCambiarOrganizacion(new Boolean(true));
		if ((permitirCambiarPlan != null) && (permitirCambiarPlan.equalsIgnoreCase("true"))) 
			seleccionarActividadForm.setPermitirCambiarPlan(new Boolean(true));
		if ((permitirCambiarIniciativa != null) && (permitirCambiarIniciativa.equalsIgnoreCase("true"))) 
			seleccionarActividadForm.setPermitirCambiarIniciativa(new Boolean(true));
		
		if ((planId != null) && (!planId.equals("")) && (!planId.equals("0"))) 
		{
			StrategosIniciativasService iniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			Plan plan = (Plan)iniciativasService.load(Plan.class, new Long(planId));
			if (plan != null) 
			{
				seleccionarActividadForm.setPlanSeleccionadoId(plan.getPlanId());
				seleccionarActividadForm.setOrganizacionSeleccionadaId(plan.getOrganizacionId());
			}
			iniciativasService.close();
		}
		if (seleccionarActividadForm.getPlanSeleccionadoId() == null) 
		{
			if ((organizacionId != null) && (!organizacionId.equals("")) && (!organizacionId.equals("0")))
				seleccionarActividadForm.setOrganizacionSeleccionadaId(new Long(organizacionId));
			else 
				seleccionarActividadForm.setOrganizacionSeleccionadaId(new Long((String)request.getSession().getAttribute("organizacionId")));
		}
		if ((frecuencia != null) && (!frecuencia.equals(""))) 
			seleccionarActividadForm.setFrecuenciaSeleccionada(new Byte(frecuencia));
		if (seleccionarActividadForm.getFuncionCierre() != null) 
		{
			if (!seleccionarActividadForm.getFuncionCierre().equals("")) 
			{
				if (seleccionarActividadForm.getFuncionCierre().indexOf(";") < 0)
					seleccionarActividadForm.setFuncionCierre(seleccionarActividadForm.getFuncionCierre() + ";");
			}
			else 
				seleccionarActividadForm.setFuncionCierre(null);
		}

		return mapping.findForward(forward);
	}
}