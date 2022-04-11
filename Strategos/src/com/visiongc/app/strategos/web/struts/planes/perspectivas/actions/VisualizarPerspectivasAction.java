package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.planes.forms.VisualizarPlanForm;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.actions.util.PerspectivasActionsUtil;
import com.visiongc.app.strategos.web.struts.planes.util.TipoVistaPlan;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class VisualizarPerspectivasAction extends VgcAction
{
	public static final String ACTION_KEY = "VisualizarPerspectivasAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrlSinParametros(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		ActionMessages messages = getMessages(request);

		VisualizarPlanForm visualizarPlanForm = (VisualizarPlanForm)request.getSession().getAttribute("visualizarPlanForm");

		if ((visualizarPlanForm.getAno() != null) && (visualizarPlanForm.getAno().intValue() == 0)) 
			visualizarPlanForm.setAno(null);

		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("PERSPECTIVA_VIEWALL");

		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();

		if (visualizarPlanForm.getTipoVista().byteValue() == TipoVistaPlan.getTipoVistaPlanDetallada().byteValue()) 
		{
			vistaResumen(request, visualizarPlanForm, arbolesService, mostrarTodas, messages);
			vistaDetallada(request, visualizarPlanForm);
		} 
		else if (visualizarPlanForm.getTipoVista().byteValue() == TipoVistaPlan.getTipoVistaPlanResumen().byteValue()) 
		{
			vistaResumen(request, visualizarPlanForm, arbolesService, mostrarTodas, messages);
		} 
		else if (visualizarPlanForm.getTipoVista().byteValue() == TipoVistaPlan.getTipoVistaPlanEjecutiva().byteValue()) 
		{
			vistaResumen(request, visualizarPlanForm, arbolesService, mostrarTodas, messages);
			vistaEjecutiva(request, visualizarPlanForm);
		} 
		else if (visualizarPlanForm.getTipoVista().byteValue() == TipoVistaPlan.getTipoVistaPlanArbol().byteValue()) 
		{
			forward = vistaArbol(request, visualizarPlanForm, arbolesService, mostrarTodas, messages, forward);
			
			Perspectiva raiz = (Perspectiva)request.getSession().getAttribute("visualizarPerspectivas.perspectivaRoot");
			
			StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
			ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan(); 
			strategosPlanesService.close();
			
			new com.visiongc.app.strategos.web.struts.planes.perspectivas.actions.GestionarPerspectivasAction().setConfiguracion(raiz.getNodoArbolHijos(), configuracionPlan, visualizarPlanForm.getAno());
			
			// Setear Valores al nodo Padre
			Map<String, String> perspectivaIds = new HashMap<String, String>();
			perspectivaIds.put(raiz.getNodoArbolId(), raiz.getNodoArbolId());
			StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
			Map<Long, Byte> alertasParcialPadre = strategosPerspectivasService.getAlertasPerspectivas(perspectivaIds, TipoIndicadorEstado.getTipoIndicadorEstadoParcial());
			Map<Long, Byte> alertasAnualPadre = strategosPerspectivasService.getAlertasPerspectivas(perspectivaIds, TipoIndicadorEstado.getTipoIndicadorEstadoAnual());
			raiz.setAlertaParcial((Byte)alertasParcialPadre.get(raiz.getPerspectivaId()));
			raiz.setAlertaAnual((Byte)alertasAnualPadre.get(raiz.getPerspectivaId()));
			raiz.setConfiguracionPlan(configuracionPlan);
			raiz.setUltimaMedicionParcial(null);
			raiz.setUltimaMedicionAnual(null);
			
			Plan plan = (Plan) strategosPerspectivasService.load(Plan.class, raiz.getPlanId());
			if (visualizarPlanForm.getAno() != null)
			{
				StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
				StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
				StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();

				LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(visualizarPlanForm.getAno(), visualizarPlanForm.getAno(), 1, 12, raiz.getFrecuencia().byteValue());
		    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
		    	int periodoFin = periodoInicio;

		    	Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(plan.getNlAnoIndicadorId()));
				List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), visualizarPlanForm.getAno(), visualizarPlanForm.getAno(), null, null, null);
				if (mediciones != null && mediciones.size() > 0)
				{
					raiz.setUltimaMedicionAnual(mediciones.get(mediciones.size() - 1).getValor());
					Double ejecutado = raiz.getUltimaMedicionAnual();
					List<Meta> metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(), raiz.getPlanId(), indicador.getFrecuencia(), indicador.getOrganizacion().getMesCierre(), indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(), visualizarPlanForm.getAno(), visualizarPlanForm.getAno(), periodoInicio, periodoFin);
					Double valorMeta = null;
					if (metas != null && metas.size() > 0)
						valorMeta = metas.get(metas.size() - 1).getValor(); 
					
					Byte alerta = AlertaIndicador.getAlertaNoDefinible();
					if (ejecutado != null && indicador != null && valorMeta != null)
					{
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, visualizarPlanForm.getAno(), periodoFin, valorMeta, strategosMedicionesService);
	  					Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, visualizarPlanForm.getAno(), periodoFin, valorMeta, zonaVerde, strategosMedicionesService);
	  					alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, ejecutado, valorMeta, null, null);
					}
					raiz.setAlertaAnual(alerta);
				}
				
				indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(raiz.getNlParIndicadorId()));
				mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), visualizarPlanForm.getAno(), visualizarPlanForm.getAno(), null, null, null);
				if (mediciones != null && mediciones.size() > 0)
				{
					raiz.setUltimaMedicionParcial(mediciones.get(mediciones.size() - 1).getValor());
					Double ejecutado = raiz.getUltimaMedicionParcial(); 
					List<Meta> metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(), raiz.getPlanId(), indicador.getFrecuencia(), indicador.getOrganizacion().getMesCierre(), indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(), visualizarPlanForm.getAno(), visualizarPlanForm.getAno(), periodoInicio, periodoFin);
					Double valorMeta = null;
					if (metas != null && metas.size() > 0)
						valorMeta = metas.get(metas.size() - 1).getValor(); 
					
					Byte alerta = AlertaIndicador.getAlertaNoDefinible();
					if (ejecutado != null && indicador != null && valorMeta != null)
					{
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, visualizarPlanForm.getAno(), periodoFin, valorMeta, strategosMedicionesService);
	  					Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, visualizarPlanForm.getAno(), periodoFin, valorMeta, zonaVerde, strategosMedicionesService);
	  					alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, ejecutado, valorMeta, null, null);
					}
					raiz.setAlertaParcial(alerta);
				}
			}
			else
			{
				raiz.setUltimaMedicionParcial(plan.getUltimaMedicionParcial());
				raiz.setUltimaMedicionAnual(plan.getUltimaMedicionAnual());
			}
			
			strategosPerspectivasService.close();
		}
		
		arbolesService.close();
		saveMessages(request, messages);

		return mapping.findForward(forward);
	}

	private void vistaResumen(HttpServletRequest request, VisualizarPlanForm visualizarPlanForm, ArbolesService arbolesService, boolean mostrarTodas, ActionMessages messages)
	{
		Object[] arregloIdentificadores = new Object[2];
		arregloIdentificadores[0] = "planId";
		arregloIdentificadores[1] = visualizarPlanForm.getPlanId();
		
		Perspectiva perspectivaRoot = new Perspectiva();
		perspectivaRoot = (Perspectiva)arbolesService.getArbolCompleto(perspectivaRoot, arregloIdentificadores);

		List<Perspectiva> perspectivas = new ArrayList<Perspectiva>();
		Set<?> elementosPlantilla = null;
		if (visualizarPlanForm.getPlantillaPlan() != null) 
			elementosPlantilla = visualizarPlanForm.getPlantillaPlan().getElementos();
		byte maximoNivel = 0;

		if (perspectivaRoot != null)
			maximoNivel = getListaPerspectivas(elementosPlantilla, perspectivaRoot, perspectivas, (byte) 1);

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan(); 
		strategosPlanesService.close();
		setConfiguracion(perspectivas, configuracionPlan);
		visualizarPlanForm.setConfiguracionPlan(configuracionPlan);

		request.setAttribute("listaPerspectivas", perspectivas);
		request.setAttribute("maximoNivelPerspectiva", new Integer(maximoNivel));
	}
	
	public void setConfiguracion(List<Perspectiva> perspectivas, ConfiguracionPlan configuracionPlan)
	{
		if (perspectivas != null)
		{
			for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext(); ) 
			{
				Perspectiva perspectiva = (Perspectiva)iter.next();
				perspectiva.setConfiguracionPlan(configuracionPlan);
				setConfiguracion(perspectiva.getListaHijos(), configuracionPlan);
			}
		}
	}

	private void vistaDetallada(HttpServletRequest request, VisualizarPlanForm visualizarPlanForm) 
	{
		setIndicadoresAsociados(request, visualizarPlanForm);
		setIniciativas(request);
	}

	private void vistaEjecutiva(HttpServletRequest request, VisualizarPlanForm visualizarPlanForm) 
	{
		setIndicadoresAsociados(request, visualizarPlanForm);
		setIniciativas(request);
	}

	private void setIniciativas(HttpServletRequest request) 
	{
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		
		List<?> perspectivas = (List<?>)request.getAttribute("listaPerspectivas");
		for (Iterator<?> iterPer = perspectivas.iterator(); iterPer.hasNext(); ) 
		{
			Perspectiva perspectiva = (Perspectiva)iterPer.next();
			PerspectivasActionsUtil.setIniciativas(request, strategosIniciativasService, perspectiva);
		}

		strategosIniciativasService.close();
	}

	private void setIndicadoresAsociados(HttpServletRequest request, VisualizarPlanForm visualizarPlanForm) 
	{
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(strategosIndicadoresService);
		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService(strategosIndicadoresService);
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		
		List<?> perspectivas = (List<?>)request.getAttribute("listaPerspectivas");
		boolean filtrarIndicadoresLogro = false;
		
		for (Iterator<?> iterPer = perspectivas.iterator(); iterPer.hasNext(); ) 
		{
			Perspectiva perspectiva = (Perspectiva)iterPer.next();
			if (visualizarPlanForm.getAno() != null)
			{
				perspectiva.setUltimaMedicionParcial(null);
				perspectiva.setUltimaMedicionAnual(null);
				perspectiva.setAlertaAnual(null);
				perspectiva.setAlertaParcial(null);

				LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(visualizarPlanForm.getAno(), visualizarPlanForm.getAno(), 1, 12, perspectiva.getFrecuencia().byteValue());
		    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
		    	int periodoFin = periodoInicio;

		    	Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(perspectiva.getNlAnoIndicadorId()));
				List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), visualizarPlanForm.getAno(), visualizarPlanForm.getAno(), null, null, null);
				if (mediciones != null && mediciones.size() > 0)
				{
					perspectiva.setUltimaMedicionAnual(mediciones.get(mediciones.size() - 1).getValor());
					Double ejecutado = perspectiva.getUltimaMedicionAnual();
					List<Meta> metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(), perspectiva.getPlanId(), indicador.getFrecuencia(), indicador.getOrganizacion().getMesCierre(), indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(), visualizarPlanForm.getAno(), visualizarPlanForm.getAno(), periodoInicio, periodoFin);
					Double valorMeta = null;
					if (metas != null && metas.size() > 0)
						valorMeta = metas.get(metas.size() - 1).getValor(); 
					
					Byte alerta = AlertaIndicador.getAlertaNoDefinible();
					if (ejecutado != null && indicador != null && valorMeta != null)
					{
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, visualizarPlanForm.getAno(), periodoFin, valorMeta, strategosMedicionesService);
	  					Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, visualizarPlanForm.getAno(), periodoFin, valorMeta, zonaVerde, strategosMedicionesService);
	  					alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, ejecutado, valorMeta, null, null);
					}
					perspectiva.setAlertaAnual(alerta);
				}
				
				indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(perspectiva.getNlParIndicadorId()));
				mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), visualizarPlanForm.getAno(), visualizarPlanForm.getAno(), null, null, null);
				if (mediciones != null && mediciones.size() > 0)
				{
					perspectiva.setUltimaMedicionParcial(mediciones.get(mediciones.size() - 1).getValor());
					Double ejecutado = perspectiva.getUltimaMedicionParcial(); 
					List<Meta> metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(), perspectiva.getPlanId(), indicador.getFrecuencia(), indicador.getOrganizacion().getMesCierre(), indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(), visualizarPlanForm.getAno(), visualizarPlanForm.getAno(), periodoInicio, periodoFin);
					Double valorMeta = null;
					if (metas != null && metas.size() > 0)
						valorMeta = metas.get(metas.size() - 1).getValor(); 
					
					Byte alerta = AlertaIndicador.getAlertaNoDefinible();
					if (ejecutado != null && indicador != null && valorMeta != null)
					{
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, visualizarPlanForm.getAno(), periodoFin, valorMeta, strategosMedicionesService);
	  					Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, visualizarPlanForm.getAno(), periodoFin, valorMeta, zonaVerde, strategosMedicionesService);
	  					alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, ejecutado, valorMeta, null, null);
					}
					perspectiva.setAlertaParcial(alerta);
				}
			}
				
			PerspectivasActionsUtil.setIndicadoresAsociados(request, strategosIndicadoresService, strategosPlanesService, strategosPerspectivasService, perspectiva, visualizarPlanForm.getPlan(), filtrarIndicadoresLogro, visualizarPlanForm.getAno());
		}
    
		strategosPerspectivasService.close();
		strategosPlanesService.close();
		strategosIndicadoresService.close();
		strategosMedicionesService.close();
		strategosMetasService.close();
	}
	
	private byte getListaPerspectivas(Set<?> elementosPlantillaPlanes, Perspectiva perspectiva, List<Perspectiva> perspectivas, byte nivel) 
	{
		byte maximoNivel = nivel;
		perspectiva.setNivel(new Byte(nivel));
		if ((elementosPlantillaPlanes != null) && (nivel > 1)) 
		{
			for (Iterator<?> iterElemento = elementosPlantillaPlanes.iterator(); iterElemento.hasNext(); ) 
			{
				ElementoPlantillaPlanes elemento = (ElementoPlantillaPlanes)iterElemento.next();
				if (elemento.getOrden().intValue() == nivel - 2) 
				{
					perspectiva.setNombreObjetoPerspectiva(elemento.getNombre());
					break;
				}
			}
		}
    
		perspectivas.add(perspectiva);
		if (perspectiva.getNodoArbolHijos() != null) 
		{
			for (Iterator<?> iter = perspectiva.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
			{
				Perspectiva hija = (Perspectiva)iter.next();
				byte nivelHija = getListaPerspectivas(elementosPlantillaPlanes, hija, perspectivas, (byte)(nivel + 1));
				if (nivelHija > maximoNivel) 
					maximoNivel = nivelHija;
			}
		}
		
		return maximoNivel;
	}

	private String vistaArbol(HttpServletRequest request, VisualizarPlanForm visualizarPlanForm, ArbolesService arbolesService, boolean mostrarTodas, ActionMessages messages, String forward) throws Exception
	{
		String selectedPerspectivaId = request.getParameter("selectedPerspectivaId");
		String openPerspectivaId = request.getParameter("openPerspectivaId");
		String closePerspectivaId = request.getParameter("closePerspectivaId");
		
		Perspectiva perspectivaSeleccionada = null;

		if (request.getSession().getAttribute("visualizarPerspectivas.perspectiva") == null)
		{
			Object[] arregloIdentificadores = new Object[2];
			arregloIdentificadores[0] = "planId";
			arregloIdentificadores[1] = visualizarPlanForm.getPlanId();

			Perspectiva perspectivaRoot = new Perspectiva();

			perspectivaRoot = (Perspectiva)arbolesService.getNodoArbolRaiz(perspectivaRoot, arregloIdentificadores);

			if (perspectivaRoot == null)
			{
				StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
				perspectivaRoot = strategosPerspectivasService.crearPerspectivaRaiz(visualizarPlanForm.getPlanId(), getUsuarioConectado(request));
				strategosPerspectivasService.close();
			}

			TreeviewWeb.publishTree("visualizarPerspectivas.arbolPerspectivas", perspectivaRoot.getPerspectivaId().toString(), "session", request, true);

			arbolesService.refreshNodosArbol(perspectivaRoot, (String)request.getSession().getAttribute("visualizarPerspectivas.arbolPerspectivas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas));

			request.getSession().setAttribute("visualizarPerspectivas.perspectivaRoot", perspectivaRoot);

			perspectivaSeleccionada = perspectivaRoot;

			request.getSession().setAttribute("visualizarPerspectivas.perspectiva", perspectivaSeleccionada);
			request.getSession().setAttribute("visualizarPerspectivas.perspectivaId", perspectivaSeleccionada.getPerspectivaId().toString());
		}	
		else
		{
			if (request.getAttribute("VisualizarPerspectivasAction.reloadId") != null) 
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, (Long)request.getAttribute("VisualizarPerspectivasAction.reloadId"));
			else if ((selectedPerspectivaId != null) && (!selectedPerspectivaId.equals(""))) 
			{
				forward = "visualizarPerspectivaAction";
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(selectedPerspectivaId));
			} 
			else if ((openPerspectivaId != null) && (!openPerspectivaId.equals(""))) 
			{
				TreeviewWeb.publishTreeOpen("visualizarPerspectivas.arbolPerspectivas", openPerspectivaId, "session", request);
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(openPerspectivaId));
			} 
			else if ((closePerspectivaId != null) && (!closePerspectivaId.equals(""))) 
			{
				TreeviewWeb.publishTreeClose("visualizarPerspectivas.arbolPerspectivas", closePerspectivaId, "session", request);
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(closePerspectivaId));
			} 
			else 
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long((String)request.getSession().getAttribute("visualizarPerspectivas.perspectivaId")));
			Long reloadId;
			if (perspectivaSeleccionada == null) 
			{
				perspectivaSeleccionada = (Perspectiva)request.getSession().getAttribute("visualizarPerspectivas.perspectivaRoot");
				reloadId = perspectivaSeleccionada.getPerspectivaId();
				TreeviewWeb.publishTree("visualizarPerspectivas.arbolPerspectivas", perspectivaSeleccionada.getPerspectivaId().toString(), "session", request, true);
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
			} 
			else 
			{
				reloadId = perspectivaSeleccionada.getPerspectivaId();
				if (closePerspectivaId == null) 
					TreeviewWeb.publishTreeOpen("visualizarPerspectivas.arbolPerspectivas", reloadId.toString(), "session", request);
			}

			arbolesService.refreshNodosArbol((Perspectiva)request.getSession().getAttribute("visualizarPerspectivas.perspectivaRoot"), (String)request.getSession().getAttribute("visualizarPerspectivas.arbolPerspectivas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas), reloadId);
		}

		perspectivaSeleccionada = (Perspectiva)arbolesService.findNodoArbol((Perspectiva)request.getSession().getAttribute("visualizarPerspectivas.perspectivaRoot"), perspectivaSeleccionada.getPerspectivaId().toString());

		return forward;
	}
}