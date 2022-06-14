package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms.GestionarActividadesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarPlanificacionSeguimientosAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		
		

		GestionarActividadesForm gestionarActividadesForm = (GestionarActividadesForm)form;
		gestionarActividadesForm.clear();

		String iniciativaId = request.getParameter("iniciativaId");
		Boolean desdeInstrumento = (request.getParameter("desdeInstrumento") != null && request.getParameter("desdeInstrumento") != "") ? Boolean.valueOf(request.getParameter("desdeInstrumento")) : null;
		

		if(desdeInstrumento != null ) {
			gestionarActividadesForm.setDesdeInstrumento(true);		
		}else {
			gestionarActividadesForm.setDesdeInstrumento(false);	
		}
		
		gestionarActividadesForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("ACTIVIDAD_VIEWALL"));
		gestionarActividadesForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("ACTIVIDAD_EDIT"));
		gestionarActividadesForm.setPagina(0);
		
		if ((iniciativaId != null) && (!iniciativaId.equals("")))
		{
			StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance().openStrategosPryProyectosService();

			Iniciativa iniciativa = strategosPryProyectosService.verificarProyectoIniciativa(new Long(iniciativaId), getUsuarioConectado(request));

			gestionarActividadesForm.setIniciativaId(iniciativa.getIniciativaId());
			gestionarActividadesForm.setOrganizacionId(iniciativa.getOrganizacionId());
			gestionarActividadesForm.setProyectoId(iniciativa.getProyectoId());
			if (iniciativa != null) 
				gestionarActividadesForm.setFrecuenciaIniciativa(iniciativa.getFrecuencia());

			boolean bloqueado = !strategosPryProyectosService.lockForUpdate(request.getSession().getId(), iniciativa.getProyectoId(), null);

			if (bloqueado)
				gestionarActividadesForm.setSoloLectura(new Boolean(true));
			else 
				gestionarActividadesForm.setSoloLectura(new Boolean(false));

			if (gestionarActividadesForm.getPlanId() != null) 
			{
				Plan plan = (Plan)strategosPryProyectosService.load(Plan.class, gestionarActividadesForm.getPlanId());

				if ((plan != null) && (plan.getMetodologia() != null)) 
				{
					gestionarActividadesForm.setNombreActividadSingular(plan.getMetodologia().getNombreActividadSingular());
					gestionarActividadesForm.setNombreActividadPlural(plan.getMetodologia().getNombreActividadPlural());
					gestionarActividadesForm.setNombreIniciativaSingular(plan.getMetodologia().getNombreIniciativaSingular());
					gestionarActividadesForm.setNombreIniciativaPlural(plan.getMetodologia().getNombreIniciativaPlural());
				}
			}

			strategosPryProyectosService.close();
		}

		return mapping.findForward(forward);
	}
}