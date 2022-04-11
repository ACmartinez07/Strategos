package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendario;
import com.visiongc.app.strategos.planificacionseguimiento.util.PryCalendarioUtil;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms.EditarActividadForm;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms.GestionarActividadesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class EditarActividadFuncionAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		if (request.getParameter("funcion") != null) 
		{
			String funcion = request.getParameter("funcion");
			if (funcion.equals("calcularFechasActividad")) 
				calcularFechasActividad(request);
		}

		return mapping.findForward("ajaxResponse");
	}

	private void calcularFechasActividad(HttpServletRequest request) throws Exception
	{
		GestionarActividadesForm gestionarActividadesForm = (GestionarActividadesForm)request.getSession().getAttribute("gestionarActividadesForm");
		EditarActividadForm editarActividadForm = (EditarActividadForm)request.getSession().getAttribute("editarActividadForm");
		
		String campoModificado = request.getParameter("campoModificado");
		Date dateComienzoPlan = FechaUtil.convertirStringToDate(request.getParameter("comienzoPlan"), "formato.fecha.corta");
		Date dateFinPlan = FechaUtil.convertirStringToDate(request.getParameter("finPlan"), "formato.fecha.corta");
		Integer duracion = Integer.valueOf(request.getParameter("duracion"));
		PryCalendario calendario = editarActividadForm.getCalendario();
		FechaUtil.setHoraInicioDia(dateComienzoPlan);
		FechaUtil.setHoraFinalDia(dateFinPlan);

		if (campoModificado.equals("comienzoPlan")) 
		{
			dateComienzoPlan = PryCalendarioUtil.getProximoDiaLaborable(calendario, dateComienzoPlan);
			dateFinPlan = PryCalendarioUtil.getFechaIntervaloPorDuracion(calendario, dateComienzoPlan, duracion.intValue(), (byte) 0);
		} 
		else if (campoModificado.equals("finPlan")) 
		{
			dateFinPlan = PryCalendarioUtil.getProximoDiaLaborable(calendario, dateFinPlan);
			if (dateFinPlan.after(dateComienzoPlan))
				duracion = new Integer(PryCalendarioUtil.getDuracionEntreFechas(calendario, dateComienzoPlan, dateFinPlan));
			else
				dateComienzoPlan = PryCalendarioUtil.getFechaIntervaloPorDuracion(calendario, dateFinPlan, duracion.intValue(), (byte) 1);
		}
		else if (campoModificado.equals("duracion")) 
		{
			dateComienzoPlan = PryCalendarioUtil.getProximoDiaLaborable(calendario, dateComienzoPlan);
			dateFinPlan = PryCalendarioUtil.getFechaIntervaloPorDuracion(calendario, dateComienzoPlan, duracion.intValue(), (byte) 0);
		}
    
		String resultado = "";
		resultado = "comienzoPlan:" + VgcFormatter.formatearFecha(dateComienzoPlan, "formato.fecha.corta");
		FechaUtil.setHoraInicioDia(dateComienzoPlan);
		Calendar calFecha = Calendar.getInstance();
		calFecha.setTime(dateComienzoPlan);
		resultado = resultado + "comienzoPlanTexto:" + PeriodoUtil.convertirFechaToTexto(calFecha, gestionarActividadesForm.getFrecuenciaIniciativa().byteValue());

		resultado = resultado + "finPlan:" + VgcFormatter.formatearFecha(dateFinPlan, "formato.fecha.corta");
		FechaUtil.setHoraFinalDia(dateFinPlan);
		calFecha = Calendar.getInstance();
		calFecha.setTime(dateFinPlan);
		resultado = resultado + "finPlanTexto:" + PeriodoUtil.convertirFechaToTexto(calFecha, gestionarActividadesForm.getFrecuenciaIniciativa().byteValue());

		resultado = resultado + "duracion:" + duracion;

		request.setAttribute("ajaxResponse", resultado);
	}
}