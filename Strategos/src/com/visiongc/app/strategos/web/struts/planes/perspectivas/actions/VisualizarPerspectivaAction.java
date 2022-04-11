package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.planes.forms.VisualizarPlanForm;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.actions.util.PerspectivasActionsUtil;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.VisualizarPerspectivaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class VisualizarPerspectivaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		VisualizarPerspectivaForm visualizarPerspectivaForm = (VisualizarPerspectivaForm)form;

		VisualizarPlanForm visualizarPlanForm = (VisualizarPlanForm)request.getSession().getAttribute("visualizarPlanForm");

		ActionMessages messages = getMessages(request);

		String perspectivaId = request.getParameter("perspectivaId");
		visualizarPerspectivaForm.setVinculoCausaEfecto(request.getParameter("vinculoCausaEfecto") != null ? request.getParameter("vinculoCausaEfecto").equals("1") ? true : false : false);

		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan(); 
		strategosPlanesService.close();
		
		Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, new Long(perspectivaId));
		PaginaLista paginaPerspectivaHijas = new PaginaLista();
		perspectiva.setListaHijos(new ArrayList<Perspectiva>());
		perspectiva.setConfiguracionPlan(configuracionPlan);
		for (Iterator<Perspectiva> p = perspectiva.getHijos().iterator(); p.hasNext(); ) 
		{
			Perspectiva perspectivaHija = (Perspectiva)p.next();
			perspectivaHija.setConfiguracionPlan(configuracionPlan);
			perspectiva.getListaHijos().add(perspectivaHija);
		}
		
		paginaPerspectivaHijas.setLista(perspectiva.getListaHijos());
		paginaPerspectivaHijas.setNroPagina(1);
		paginaPerspectivaHijas.setTamanoPagina(perspectiva.getListaHijos().size());
		paginaPerspectivaHijas.setTamanoSetPaginas(1);
		paginaPerspectivaHijas.setTotal(perspectiva.getListaHijos().size());
		
		request.setAttribute("paginaPerspectivaHijas", paginaPerspectivaHijas);		
		request.getSession().setAttribute("configuracionPlan", configuracionPlan);

		visualizarPerspectivaForm.setTipoVista(new Byte((byte) 1));

		if (perspectiva != null)
		{
			visualizarPerspectivaForm.setPerspectivaId(perspectiva.getPerspectivaId());
			visualizarPerspectivaForm.setResponsableId(perspectiva.getResponsableId());

			Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, perspectiva.getPlanId());

			int nivel = 1;
			Perspectiva padre = perspectiva;
			while (padre.getPadre() != null) 
			{
				padre = padre.getPadre();
				nivel++;
			}

			visualizarPerspectivaForm.setNivel(new Integer(nivel));

			if (plan != null) 
			{
				visualizarPerspectivaForm.setPlan(plan);
				visualizarPerspectivaForm.setPlanId(plan.getPlanId());
				PlantillaPlanes plantillaPlan = (PlantillaPlanes)strategosPerspectivasService.load(PlantillaPlanes.class, plan.getMetodologiaId());
				if (plantillaPlan != null) 
				{
					visualizarPerspectivaForm.setPlantillaPlan(plantillaPlan);
					Set elementosPlantillaPlanes = plantillaPlan.getElementos();
					if ((elementosPlantillaPlanes != null) && (nivel > 1)) 
					{
						for (Iterator iterElemento = elementosPlantillaPlanes.iterator(); iterElemento.hasNext(); ) 
						{
							ElementoPlantillaPlanes elemento = (ElementoPlantillaPlanes)iterElemento.next();
							if (elemento.getOrden().intValue() == nivel - 2) 
							{
								perspectiva.setNombreObjetoPerspectiva(elemento.getNombre());
								visualizarPerspectivaForm.setNombreObjetoPerspectiva(elemento.getNombre());
								break;
							}
						}
					}
				}
			}

			visualizarPerspectivaForm.setNombre(perspectiva.getNombre());
			visualizarPerspectivaForm.setDescripcion(perspectiva.getDescripcion());
			visualizarPerspectivaForm.setFrecuencia(perspectiva.getFrecuencia());
			visualizarPerspectivaForm.setNombreFrecuencia(Frecuencia.getNombre(perspectiva.getFrecuencia().byteValue()));
			visualizarPerspectivaForm.setTipo(perspectiva.getTipo());
			visualizarPerspectivaForm.setTipoCalculo(perspectiva.getTipoCalculo());
			visualizarPerspectivaForm.setEstadoAnual(perspectiva.getUltimaMedicionAnualFormateado());
			visualizarPerspectivaForm.setEstadoParcial(perspectiva.getUltimaMedicionParcialFormateado());

			if ((visualizarPerspectivaForm.getResponsableId() != null) && (!visualizarPerspectivaForm.getResponsableId().equals("")) && (visualizarPerspectivaForm.getResponsableId().byteValue() != 0)) 
			{
				Responsable responsable = (Responsable)strategosPerspectivasService.load(Responsable.class, visualizarPerspectivaForm.getResponsableId());
				visualizarPerspectivaForm.setNombreResponsable(responsable.getNombreCargo());
			}

			Integer ano = null;
			if (visualizarPlanForm != null) 
				ano = visualizarPlanForm.getAno();

			if ((request.getParameter("mostrarObjetosAsociados") != null) && (request.getParameter("mostrarObjetosAsociados").equalsIgnoreCase("true"))) 
				visualizarObjetosAsociados(request, perspectiva, plan, ano, visualizarPerspectivaForm);
			else 
			{
				List perspectivaEstados = strategosPerspectivasService.getPerspectivaEstados(perspectiva.getPerspectivaId(), null, ano, ano, null, null);
				request.setAttribute("perspectivaEstados", perspectivaEstados);
			}
		}
		else
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
			forward = "noencontrado";
		}

		if (visualizarPerspectivaForm.getMostrarObjetosAsociados() == null) 
			visualizarPerspectivaForm.setMostrarObjetosAsociados(new Boolean(false));

		strategosPerspectivasService.close();

		saveMessages(request, messages);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);
    
		return mapping.findForward(forward);
	}

	private void visualizarObjetosAsociados(HttpServletRequest request, Perspectiva perspectiva, Plan plan, Integer ano, VisualizarPerspectivaForm visualizarPerspectivaForm)
	{
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		
		PerspectivasActionsUtil.setIniciativas(request, strategosIniciativasService, perspectiva);

		strategosIniciativasService.close();

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(strategosIndicadoresService);
		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService(strategosIndicadoresService);
		PerspectivasActionsUtil.setIndicadoresAsociados(request, strategosIndicadoresService, strategosPlanesService, strategosPerspectivasService, perspectiva, plan, true, ano);

		String indicadoresIds = "";
		for (Iterator iter = perspectiva.getListaIndicadores().iterator(); iter.hasNext(); ) 
		{
			Indicador indicador = (Indicador)iter.next();
			indicadoresIds = indicadoresIds + indicador.getIndicadorId().toString() + ",";
		}
		
		for (Iterator iter = perspectiva.getListaIndicadoresGuia().iterator(); iter.hasNext(); ) 
		{
			Indicador indicador = (Indicador)iter.next();
			indicadoresIds = indicadoresIds + indicador.getIndicadorId().toString() + ",";
		}
		
		if (indicadoresIds.length() > 0) 
			indicadoresIds = indicadoresIds.substring(0, indicadoresIds.length() - 1);
		visualizarPerspectivaForm.setIndicadoresIds(indicadoresIds);

		strategosPerspectivasService.close();
		strategosPlanesService.close();
		strategosIndicadoresService.close();

		request.setAttribute("perspectiva", perspectiva);
	}
}