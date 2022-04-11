package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.GestionarIniciativasForm;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.MostrarGestionIniciativaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MostrarGestionIniciativaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarIniciativasForm gestionarIniciativasForm = (GestionarIniciativasForm)request.getSession().getAttribute("gestionarIniciativasForm");

		MostrarGestionIniciativaForm mostrarGestionIniciativaForm = (MostrarGestionIniciativaForm)form;

		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();

		String iniciativaId = gestionarIniciativasForm.getSeleccionadoId();

		if (iniciativaId != null) 
		{
			Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(iniciativaId));

			mostrarGestionIniciativaForm.setNombrePlan(null);

			if (iniciativa.getProyectoId() != null) 
			{
				PryProyecto proyecto = (PryProyecto)strategosIniciativasService.load(PryProyecto.class, iniciativa.getProyectoId());

				mostrarGestionIniciativaForm.setComienzoProgramado(proyecto.getComienzoPlan());
				mostrarGestionIniciativaForm.setFinProgramado(proyecto.getFinPlan());
				mostrarGestionIniciativaForm.setDuracionProgramado(proyecto.getDuracionPlan());
				mostrarGestionIniciativaForm.setComienzoReal(proyecto.getComienzoReal());
				mostrarGestionIniciativaForm.setFinReal(proyecto.getFinReal());
				mostrarGestionIniciativaForm.setDuracionReal(proyecto.getDuracionReal());
			} 
			else 
				mostrarGestionIniciativaForm.clear();
		}
		strategosIniciativasService.close();

		forward = "gestionarIndicadoresIniciativaAction";

		return mapping.findForward(forward);
	}
}