package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.StrategosPlantillasPlanesService;
import com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.PerspectivaEstado;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.planes.forms.GestionarPlanForm;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.GestionarPerspectivasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class GestionarPerspectivasAction extends VgcAction
{
	public static final String ACTION_KEY = "GestionarPerspectivasAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre, new Integer(2));
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		ActionMessages messages = getMessages(request);

		String selectedPerspectivaId = request.getParameter("selectedPerspectivaId");
		String openPerspectivaId = request.getParameter("openPerspectivaId");
		String closePerspectivaId = request.getParameter("closePerspectivaId");
		Boolean actualizarForma = request.getParameter("actualizarForma") != null ? Boolean.parseBoolean((String)request.getParameter("actualizarForma")) : false;

		GestionarPerspectivasForm gestionarPerspectivasForm = (GestionarPerspectivasForm)form;
		GestionarPlanForm gestionarPlanForm = (GestionarPlanForm)request.getSession().getAttribute("gestionarPlanForm");

		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Panel.Plan", "Ancho");
		if (configuracionUsuario == null)
		{
			configuracionUsuario = new ConfiguracionUsuario(); 
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase("Strategos.Panel.Plan");
			pk.setObjeto("Ancho");
			pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
			configuracionUsuario.setPk(pk);
			configuracionUsuario.setData("400");
			
			frameworkService.saveConfiguracionUsuario(configuracionUsuario, this.getUsuarioConectado(request));
		}
		gestionarPlanForm.setAnchoPorDefecto(configuracionUsuario.getData());
		
		configuracionUsuario = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Panel.Plan", "Alto");		
		if (configuracionUsuario == null)
		{
			configuracionUsuario = new ConfiguracionUsuario(); 
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase("Strategos.Panel.Plan");
			pk.setObjeto("Alto");
			pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
			configuracionUsuario.setPk(pk);
			configuracionUsuario.setData("400");
			
			frameworkService.saveConfiguracionUsuario(configuracionUsuario, this.getUsuarioConectado(request));
		}
		gestionarPlanForm.setAltoPorDefecto(configuracionUsuario.getData());
		
		request.setAttribute("gestionarIniciativasDePlanPlanId", gestionarPlanForm.getPlanId());
		
		gestionarPerspectivasForm.setVerIndicadoresLogroPlan(true);
		
		boolean ver = new Boolean(request.getParameter("ejecucion"));
		
		if(ver){
			gestionarPerspectivasForm.setVerIndicadoresLogroPlan(new Boolean(request.getParameter("verIndicadoresLogroPlan")));
		}		
		
		
		

		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("PERSPECTIVA_VIEWALL");

		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();

		Perspectiva perspectivaSeleccionada = null;
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan(); 
		strategosPlanesService.close();
		
		ConfiguracionUsuario foco = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Foco.Perspectiva.Arbol", "PerspectivaId");
		if (request.getSession().getAttribute("perspectiva") == null)
		{
			Object[] arregloIdentificadores = new Object[2];
			arregloIdentificadores[0] = "planId";
			arregloIdentificadores[1] = gestionarPlanForm.getPlanId();

			Perspectiva perspectivaRoot = new Perspectiva();

			perspectivaRoot = (Perspectiva)arbolesService.getNodoArbolRaiz(perspectivaRoot, arregloIdentificadores);

			if (perspectivaRoot == null)
			{
				StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
				perspectivaRoot = strategosPerspectivasService.crearPerspectivaRaiz(gestionarPlanForm.getPlanId(), getUsuarioConectado(request));
				strategosPerspectivasService.close();
			}

			perspectivaRoot.setConfiguracionPlan(configuracionPlan);
			TreeviewWeb.publishTree("arbolPerspectivas", perspectivaRoot.getPerspectivaId().toString(), "session", request, true);
			arbolesService.refreshNodosArbol(perspectivaRoot, (String)request.getSession().getAttribute("arbolPerspectivas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas));

			request.getSession().setAttribute("perspectivaRoot", perspectivaRoot);

    		if (foco != null)
    		{
    			Perspectiva perspectiva = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(foco.getData()));
    			if (perspectiva != null && perspectiva.getPlanId().longValue() == perspectivaRoot.getPlanId().longValue())
    			{
        			perspectivaSeleccionada = SetFoco(new Long(foco.getData()), arbolesService, perspectivaRoot, request);
        			arbolesService.refreshNodosArbol(perspectivaRoot, (String)request.getSession().getAttribute("arbolPerspectivas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas));
    			}
    			else
        			perspectivaSeleccionada = perspectivaRoot;
    		}
    		else
    			perspectivaSeleccionada = perspectivaRoot;
		}
		else
		{
			if ((selectedPerspectivaId != null) && (!selectedPerspectivaId.equals("")))
			{
				Object[] arregloIdentificadores = new Object[2];
				arregloIdentificadores[0] = "planId";
				arregloIdentificadores[1] = gestionarPlanForm.getPlanId();
				Perspectiva perspectivaRoot = new Perspectiva();
				perspectivaRoot = (Perspectiva)arbolesService.getNodoArbolRaiz(perspectivaRoot, arregloIdentificadores);
				if (perspectivaRoot != null)
				{
					arbolesService.refreshNodosArbol(perspectivaRoot, (String)request.getSession().getAttribute("arbolPerspectivas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas));
					request.getSession().setAttribute("perspectivaRoot", perspectivaRoot);
				}
			}
			
			if (request.getAttribute("GestionarPerspectivasAction.reloadId") != null) 
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, (Long)request.getAttribute("GestionarPerspectivasAction.reloadId"));
			else if ((selectedPerspectivaId != null) && (!selectedPerspectivaId.equals(""))) 
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(selectedPerspectivaId));
			else if ((openPerspectivaId != null) && (!openPerspectivaId.equals(""))) 
			{
				TreeviewWeb.publishTreeOpen("arbolPerspectivas", openPerspectivaId, "session", request);
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(openPerspectivaId));
			} 
			else if ((closePerspectivaId != null) && (!closePerspectivaId.equals(""))) 
			{
				TreeviewWeb.publishTreeClose("arbolPerspectivas", closePerspectivaId, "session", request);
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(closePerspectivaId));
			} 
			else 
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long((String)request.getSession().getAttribute("perspectivaId")));

			Long reloadId;
			if (perspectivaSeleccionada == null) 
			{
				perspectivaSeleccionada = (Perspectiva)request.getSession().getAttribute("perspectivaRoot");
				reloadId = perspectivaSeleccionada.getPerspectivaId();
				TreeviewWeb.publishTree("arbolPerspectivas", perspectivaSeleccionada.getPerspectivaId().toString(), "session", request, true);

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
			} 
			else 
			{
				reloadId = perspectivaSeleccionada.getPerspectivaId();
				if (closePerspectivaId == null) 
					TreeviewWeb.publishTreeOpen("arbolPerspectivas", reloadId.toString(), "session", request);
			}
			perspectivaSeleccionada.setConfiguracionPlan(configuracionPlan);

			if (foco == null)
			{
				foco = new ConfiguracionUsuario(); 
				ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
				pk.setConfiguracionBase("Strategos.Foco.Perspectiva.Arbol");
				pk.setObjeto("PerspectivaId");
				pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
				foco.setPk(pk);
			}
			foco.setData(reloadId.toString());
			frameworkService.saveConfiguracionUsuario(foco, this.getUsuarioConectado(request));
			
			arbolesService.refreshNodosArbol((Perspectiva)request.getSession().getAttribute("perspectivaRoot"), (String)request.getSession().getAttribute("arbolPerspectivas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas), reloadId);
		}
		
		frameworkService.close();

		gestionarPlanForm.setPerspectivaId(perspectivaSeleccionada.getPerspectivaId());

		StrategosPlantillasPlanesService strategosPlantillasPlanesService = StrategosServiceFactory.getInstance().openStrategosPlantillasPlanesService();
		List nivelesPlantillaPlan = strategosPlantillasPlanesService.getNivelesPlantillaPlan(gestionarPlanForm.getPlantillaPlanes().getPlantillaPlanesId());
		gestionarPerspectivasForm.setNivelesPlantillaPlan(nivelesPlantillaPlan);
		
		getPadres(perspectivaSeleccionada, arbolesService);

		gestionarPerspectivasForm.setNivelSeleccionadoArbol(new Integer(arbolesService.getRutaCompleta(perspectivaSeleccionada).size()));

		ElementoPlantillaPlanes elementoPlantillaPlanes = null;
		if (gestionarPerspectivasForm.getNivelesPlantillaPlan().size() >= gestionarPerspectivasForm.getNivelSeleccionadoArbol().intValue()) 
		{
			elementoPlantillaPlanes = (ElementoPlantillaPlanes)gestionarPerspectivasForm.getNivelesPlantillaPlan().get(gestionarPerspectivasForm.getNivelSeleccionadoArbol().intValue() - 1);
			gestionarPerspectivasForm.setElementoPlantillaPlanes(elementoPlantillaPlanes);
		} 
		else 
			gestionarPerspectivasForm.setElementoPlantillaPlanes(null);

		Perspectiva perspectivaSeleccionadaArbol = (Perspectiva)arbolesService.findNodoArbol((Perspectiva)request.getSession().getAttribute("perspectivaRoot"), perspectivaSeleccionada.getPerspectivaId().toString());
		if (perspectivaSeleccionadaArbol != null)
		{
			perspectivaSeleccionadaArbol.setTipoCalculo(perspectivaSeleccionada.getTipoCalculo());
			
			request.getSession().setAttribute("perspectiva", perspectivaSeleccionadaArbol);
			request.getSession().setAttribute("perspectivaId", perspectivaSeleccionadaArbol.getPerspectivaId().toString());
		}

		arbolesService.close();
		
		request.getSession().setAttribute("verPlan", getPermisologiaUsuario(request).tienePermiso("PLAN_VIEW"));
		request.getSession().setAttribute("editarPlan", getPermisologiaUsuario(request).tienePermiso("PLAN_EDIT"));
		request.getSession().setAttribute("verPerspectiva", getPermisologiaUsuario(request).tienePermiso("PLAN_PERSPECTIVA_VIEW"));
		request.getSession().setAttribute("editarPerspectiva", getPermisologiaUsuario(request).tienePermiso("PLAN_PERSPECTIVA_EDIT"));
		request.getSession().setAttribute("actualizarForma", actualizarForma.toString());

		saveMessages(request, messages);

		actualizarAlertas(request, configuracionPlan);

		return mapping.findForward(forward);
	}

	private void actualizarAlertas(HttpServletRequest request, ConfiguracionPlan configuracionPlan)
	{
		Perspectiva raiz = (Perspectiva)request.getSession().getAttribute("perspectivaRoot");

		Map<String, String> perspectivaIds = new HashMap<String, String>();
		getIds(raiz.getNodoArbolHijos(), perspectivaIds);
		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
		Plan plan = (Plan) strategosPerspectivasService.load(Plan.class, raiz.getPlanId());
		List<PerspectivaEstado> estados = strategosPerspectivasService.getPerspectivaEstadosUltimoPeriodo(plan.getPlanId(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), plan.getAnoFinal());
		Map<Long, Byte> alertasParcial = new HashMap<Long, Byte>();
		Map<Long, Double> nivelParcial = new HashMap<Long, Double>();
		for (Iterator<PerspectivaEstado> iter = estados.iterator(); iter.hasNext(); ) 
		{
			PerspectivaEstado estado = (PerspectivaEstado)iter.next();
			if (estado.getPk().getTipo().byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial())
			{
				if (estado.getPk().getPerspectivaId().longValue() == raiz.getPerspectivaId().longValue() && estado.getAlerta() == null)
				{
					alertasParcial.put(estado.getPk().getPerspectivaId(), raiz.getAlertaParcial());
					nivelParcial.put(estado.getPk().getPerspectivaId(), raiz.getUltimaMedicionParcial());
				}
				else
				{
					alertasParcial.put(estado.getPk().getPerspectivaId(), estado.getAlerta());
					nivelParcial.put(estado.getPk().getPerspectivaId(), estado.getEstado());
				}
			}
		}
		
		estados = strategosPerspectivasService.getPerspectivaEstadosUltimoPeriodo(plan.getPlanId(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), plan.getAnoFinal());
		Map<Long, Byte> alertasAnual = new HashMap<Long, Byte>();
		Map<Long, Double> nivelAnual = new HashMap<Long, Double>();
		for (Iterator<PerspectivaEstado> iter = estados.iterator(); iter.hasNext(); ) 
		{
			PerspectivaEstado estado = (PerspectivaEstado)iter.next();
			if (estado.getPk().getTipo().byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoAnual())
			{
				if (estado.getPk().getPerspectivaId().longValue() == raiz.getPerspectivaId().longValue() && estado.getAlerta() == null)
				{
					alertasAnual.put(estado.getPk().getPerspectivaId(), raiz.getAlertaAnual());
					nivelAnual.put(estado.getPk().getPerspectivaId(), raiz.getUltimaMedicionAnual());
				}
				else
				{
					alertasAnual.put(estado.getPk().getPerspectivaId(), estado.getAlerta());
					nivelAnual.put(estado.getPk().getPerspectivaId(), estado.getEstado());
				}
			}
		}
		
		
		/* solucion error planes porcentaje */
		
		Double planMedicionAnual=0.0;
		Double planMedicionParcial=0.0;
		
		if(plan.getUltimaMedicionAnual() != null) {
			
			planMedicionAnual =(Double)nivelAnual.get(raiz.getPerspectivaId());
			if(planMedicionAnual ==null) {
				planMedicionAnual = plan.getUltimaMedicionAnual();
			}
		
		}
		
		if(plan.getUltimaMedicionParcial() != null) {
			
			planMedicionParcial =(Double)nivelParcial.get(raiz.getPerspectivaId());
			if(planMedicionParcial ==null) {
				planMedicionParcial = plan.getUltimaMedicionParcial();
			}
		}
		
		// Setear Valores al nodo Padre
		perspectivaIds = new HashMap<String, String>();
		perspectivaIds.put(raiz.getNodoArbolId(), raiz.getNodoArbolId());
		raiz.setAlertaParcial((Byte)alertasParcial.get(raiz.getPerspectivaId()));
		raiz.setAlertaAnual((Byte)alertasAnual.get(raiz.getPerspectivaId()));
		raiz.setConfiguracionPlan(configuracionPlan);
		raiz.setUltimaMedicionParcial(planMedicionParcial);
		raiz.setUltimaMedicionAnual(planMedicionAnual);
		
		strategosPerspectivasService.close();
		
		actualizarAlertas(raiz.getNodoArbolHijos(), alertasParcial, nivelParcial, TipoIndicadorEstado.getTipoIndicadorEstadoParcial());
		actualizarAlertas(raiz.getNodoArbolHijos(), alertasAnual, nivelAnual, TipoIndicadorEstado.getTipoIndicadorEstadoAnual());
		setConfiguracion(raiz.getNodoArbolHijos(), configuracionPlan, null);
	}

	private void getIds(Collection<NodoArbol> nodos, Map<String, String> ids) 
	{
		if (nodos != null)
		{
			for (Iterator<NodoArbol> iter = nodos.iterator(); iter.hasNext(); ) 
			{
				NodoArbol nodo = (NodoArbol)iter.next();
				ids.put(nodo.getNodoArbolId(), nodo.getNodoArbolId());
				getIds(nodo.getNodoArbolHijos(), ids);
			}
		}
	}

	public void actualizarAlertas(Collection<NodoArbol> perspectivas, Map<Long, Byte> alertas, Map<Long, Double> nivel, Byte tipo)
	{
		if (perspectivas != null)
		{
			for (Iterator<NodoArbol> iter = perspectivas.iterator(); iter.hasNext(); ) 
			{
				Perspectiva perspectiva = (Perspectiva)iter.next();
				Byte alerta = (Byte)alertas.get(perspectiva.getPerspectivaId());
				Double estado = (Double)nivel.get(perspectiva.getPerspectivaId());
				
				if (alerta != null && tipo.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
					perspectiva.setAlertaParcial(alerta);
				else if (alerta != null && tipo.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoAnual().byteValue())
					perspectiva.setAlertaAnual(alerta);
				if (estado != null && tipo.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
					perspectiva.setUltimaMedicionParcial(estado);
				else if (estado != null && tipo.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoAnual().byteValue())
					perspectiva.setUltimaMedicionAnual(estado);
					
				actualizarAlertas(perspectiva.getNodoArbolHijos(), alertas, nivel, tipo);
			}
		}
	}
	
	public void setConfiguracion(Collection<NodoArbol> perspectivas, ConfiguracionPlan configuracionPlan, Integer ano)
	{
		if (perspectivas != null)
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
			StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
			
			for (Iterator<NodoArbol> iter = perspectivas.iterator(); iter.hasNext(); ) 
			{
				Perspectiva perspectiva = (Perspectiva)iter.next();
				setConfiguracion(perspectiva, configuracionPlan, ano, strategosIndicadoresService, strategosMedicionesService, strategosMetasService);
			}
			
			strategosIndicadoresService.close();
			strategosMedicionesService.close();
			strategosMetasService.close();
		}
	}
	
	public void setConfiguracion(Perspectiva perspectiva, ConfiguracionPlan configuracionPlan, Integer ano, StrategosIndicadoresService strategosIndicadoresService, StrategosMedicionesService strategosMedicionesService, StrategosMetasService strategosMetasService)
	{
		perspectiva.setConfiguracionPlan(configuracionPlan);
		setConfiguracion(perspectiva.getNodoArbolHijos(), configuracionPlan, ano);
		
		if (ano != null)
		{
			perspectiva.setUltimaMedicionParcial(null);
			perspectiva.setUltimaMedicionAnual(null);
			perspectiva.setAlertaAnual(null);
			perspectiva.setAlertaParcial(null);

			LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(ano, ano, 1, 12, perspectiva.getFrecuencia().byteValue());
	    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
	    	int periodoFin = periodoInicio;

	    	Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(perspectiva.getNlAnoIndicadorId()));
			List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), ano, ano, null, null, null);
			if (mediciones != null && mediciones.size() > 0)
			{
				perspectiva.setUltimaMedicionAnual(mediciones.get(mediciones.size() - 1).getValor());
				Double ejecutado = perspectiva.getUltimaMedicionAnual();
				List<Meta> metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(), perspectiva.getPlanId(), indicador.getFrecuencia(), indicador.getOrganizacion().getMesCierre(), indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(), ano, ano, periodoInicio, periodoFin);
				Double valorMeta = null;
				if (metas != null && metas.size() > 0)
					valorMeta = metas.get(metas.size() - 1).getValor(); 
				
				Byte alerta = AlertaIndicador.getAlertaNoDefinible();
				if (ejecutado != null && indicador != null && valorMeta != null)
				{
					Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, ano, periodoFin, valorMeta, strategosMedicionesService);
  					Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, ano, periodoFin, valorMeta, zonaVerde, strategosMedicionesService);
  					alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, ejecutado, valorMeta, null, null);
				}
				perspectiva.setAlertaAnual(alerta);
			}
			
			indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(perspectiva.getNlParIndicadorId()));
			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), ano, ano, null, null, null);
			if (mediciones != null && mediciones.size() > 0)
			{
				perspectiva.setUltimaMedicionParcial(mediciones.get(mediciones.size() - 1).getValor());
				Double ejecutado = perspectiva.getUltimaMedicionParcial(); 
				List<Meta> metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(), perspectiva.getPlanId(), indicador.getFrecuencia(), indicador.getOrganizacion().getMesCierre(), indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(), ano, ano, periodoInicio, periodoFin);
				Double valorMeta = null;
				if (metas != null && metas.size() > 0)
					valorMeta = metas.get(metas.size() - 1).getValor(); 
				
				Byte alerta = AlertaIndicador.getAlertaNoDefinible();
				if (ejecutado != null && indicador != null && valorMeta != null)
				{
					Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, ano, periodoFin, valorMeta, strategosMedicionesService);
  					Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, ano, periodoFin, valorMeta, zonaVerde, strategosMedicionesService);
  					alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, ejecutado, valorMeta, null, null);
				}
				perspectiva.setAlertaParcial(alerta);
			}
		}
	}
	
	private void getPadres(Perspectiva perspectiva, ArbolesService arbolesService)
	{
		Perspectiva perspectivaPadre = null;
		if (perspectiva.getPadreId() != null)
		{
			perspectivaPadre = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(perspectiva.getPadreId()));
			perspectiva.setPadre(perspectivaPadre);
		}
		
		if (perspectivaPadre != null && perspectivaPadre.getPadreId() != null)
			getPadres(perspectivaPadre, arbolesService);
	}
	
	private Perspectiva SetFoco(Long perspectivaId, ArbolesService arbolesService, Perspectiva perspectivaRoot, HttpServletRequest request) throws Exception
	{
		Perspectiva perspectiva = null;
		if (perspectivaRoot.getPerspectivaId().longValue() != perspectivaId.longValue())
		{
			String [] idsPadres = GetPathPadre(perspectivaId, arbolesService, perspectivaId.toString()).split(",");
			for (int i = 0; i < idsPadres.length; i++)
				if (!idsPadres[i].equals(""))
					TreeviewWeb.publishTreeOpen("arbolPerspectivas", idsPadres[i], "session", request);
			
			perspectiva = (Perspectiva)arbolesService.load(Perspectiva.class, perspectivaId);
		}

		if (perspectiva == null)
			perspectiva = perspectivaRoot;
		
		return perspectiva;
	}
	
	private String GetPathPadre(Long perspectivaId, ArbolesService arbolesService, String path)
	{
		Perspectiva perspectiva = (Perspectiva)arbolesService.load(Perspectiva.class, perspectivaId);
		if (perspectiva.getPadreId() != null)
			path = GetPathPadre(perspectiva.getPadreId(), arbolesService, perspectiva.getPadreId().toString() + "," + path);
		
		return path;
	}
}