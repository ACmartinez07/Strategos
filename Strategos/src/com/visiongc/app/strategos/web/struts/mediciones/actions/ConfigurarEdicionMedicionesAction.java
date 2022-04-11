package com.visiongc.app.strategos.web.struts.mediciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm.TipoSource;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class ConfigurarEdicionMedicionesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarMedicionesForm editarMedicionesForm = (EditarMedicionesForm)form;
		editarMedicionesForm.clear();
		
		ActionMessages messages = getMessages(request);

		boolean noExistenIndicadores = false;
		editarMedicionesForm.setDesdeClase(Boolean.parseBoolean(request.getParameter("desdeClases")));
		editarMedicionesForm.setDesdeIndicadorOrg(false);
		
		Byte source = Byte.parseByte(request.getParameter("source"));
		if (source.byteValue() == TipoSource.SOURCE_CLASE) {
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_CLASE);
		}
		else if (source.byteValue() == TipoSource.SOURCE_PLAN)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_PLAN);
		else if (source.byteValue() == TipoSource.SOURCE_INICIATIVA)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_INICIATIVA);
		else if (source.byteValue() == TipoSource.SOURCE_INDICADOR) {
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_CLASE);	
			editarMedicionesForm.setDesdeIndicadorOrg(true);
		}
		else
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_ACTIVIDAD);

		if (request.getQueryString().indexOf("cambioFrecuencia=") > -1)
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			
			setPeriodos(editarMedicionesForm, strategosIndicadoresService);

			strategosIndicadoresService.close();

			return mapping.findForward(forward);
		}

		Long[] indicadorId = new Long[0];
		editarMedicionesForm.setDesdeIndicador(true);
		editarMedicionesForm.setSoloSeleccionados(false);
		if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_ACTIVIDAD)
		{
			setup(editarMedicionesForm, request);	
			editarMedicionesForm.setDesdeIndicador(false);
		}
		if (request.getQueryString().indexOf("claseId=") > -1) 
			editarMedicionesForm.setClaseId(new Long(request.getParameter("claseId")));
		if (request.getQueryString().indexOf("planId=") > -1) 
			editarMedicionesForm.setPlanId(new Long(request.getParameter("planId")));
		if (request.getQueryString().indexOf("perspectivaId=") > -1) 
			editarMedicionesForm.setPerspectivaId(new Long(request.getParameter("perspectivaId")));

		if (request.getQueryString().indexOf("indicadorId=") > -1)
		{
			String strIndicadorId = request.getParameter("indicadorId");
			if (((strIndicadorId != null ? 1 : 0) & (strIndicadorId.equals("") ? 0 : 1)) != 0) 
			{
				String[] ids = strIndicadorId.split(",");
				indicadorId = new Long[ids.length];
				for (int i = 0; i < ids.length; i++) 
					indicadorId[i] = new Long(ids[i]);
			}
		}
    
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		boolean editarMediciones = false;
		PaginaLista paginaSeries = null;
		
		if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_ACTIVIDAD)
			editarMediciones = true;

		if (indicadorId.length > 0)
		{
			List<Indicador> indicadores = new ArrayList<Indicador>();

			for (int i = indicadorId.length - 1; i > -1; i--) 
			{
				Long id = indicadorId[i];

				Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, id);
				
				if (indicador != null) { 
					indicadores.add(0, indicador);
					if(source.byteValue() == TipoSource.SOURCE_INDICADOR) {
						editarMedicionesForm.setClaseId(indicador.getClaseId());
						editarMedicionesForm.setSoloSeleccionados(true);
					}
				}
					
			}

			if (indicadores.size() > 0) 
			{
				editarMediciones = true;
				editarMedicionesForm.setIndicadores(indicadores);
			}
		}
    
		if (editarMedicionesForm.getClaseId() != null) 
		{
			if (strategosIndicadoresService.getNumeroIndicadoresPorClase(editarMedicionesForm.getClaseId(), null) == 0)
			{
				noExistenIndicadores = true;
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.configuraredicionmediciones.mensaje.noindicadores"));
			}
			else 
			{
				ClaseIndicadores clase = (ClaseIndicadores)strategosIndicadoresService.load(ClaseIndicadores.class, editarMedicionesForm.getClaseId());
				
				if (clase != null) 
				{
					editarMediciones = true;
					editarMedicionesForm.setClase(clase.getNombre());

					editarMedicionesForm.setOrganizacion(clase.getOrganizacion().getNombre());
				}
			}
		} 
		else if (editarMedicionesForm.getPerspectivaId() != null) 
		{
			if (strategosIndicadoresService.getNumeroIndicadoresPorPerspectiva(editarMedicionesForm.getPerspectivaId(), null) == 0)
			{
				noExistenIndicadores = true;
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.configuraredicionmediciones.mensaje.noindicadores"));
			}
			else
			{
				Perspectiva perspectiva = (Perspectiva)strategosIndicadoresService.load(Perspectiva.class, editarMedicionesForm.getPerspectivaId());

				if (perspectiva != null) 
				{
					if ((request.getParameter("nivel") != null) && (!request.getParameter("nivel").equals("")) && (request.getParameter("plantillaPlanesId") != null) && (!request.getParameter("plantillaPlanesId").equals(""))) 
					{
						int nivel = 1;
						if ((request.getParameter("nivel") != null) && (!request.getParameter("nivel").equals(""))) 
							nivel = Integer.parseInt(request.getParameter("nivel"));
						else 
						{
							Perspectiva padre = perspectiva;
							while (padre.getPadre() != null) 
							{
								padre = padre.getPadre();
								nivel++;
							}
						}
            
						PlantillaPlanes plantillaPlanes = (PlantillaPlanes)strategosIndicadoresService.load(PlantillaPlanes.class, new Long(request.getParameter("plantillaPlanesId")));
						Set<?> elementosPlantillaPlanes = plantillaPlanes.getElementos();
						if ((elementosPlantillaPlanes != null) && (nivel > 1)) 
						{
							for (Iterator<?> iterElemento = elementosPlantillaPlanes.iterator(); iterElemento.hasNext(); ) 
							{
								ElementoPlantillaPlanes elemento = (ElementoPlantillaPlanes)iterElemento.next();
								if (elemento.getOrden().intValue() == nivel - 2) 
								{
									perspectiva.setNombreObjetoPerspectiva(elemento.getNombre());
									editarMedicionesForm.setNombreObjetoPerspectiva(elemento.getNombre());
									break;
								}
							}
						}
					}

					editarMediciones = true;
					editarMedicionesForm.setPerspectivaNombre(perspectiva.getNombre());
					Long organizacionId = new Long(request.getSession().getAttribute("organizacionId").toString());
					OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosIndicadoresService.load(OrganizacionStrategos.class, organizacionId);

					editarMedicionesForm.setOrganizacion(organizacion.getNombre());
				}
			}
		}

		if (editarMediciones) 
		{
			StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService(strategosIndicadoresService);

			if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_ACTIVIDAD)
			{
				List<SerieTiempo> series = new ArrayList<SerieTiempo>();
				series.add(SerieTiempo.getSerieReal());
				series.add(SerieTiempo.getSerieProgramado());
				
				paginaSeries = strategosSeriesTiempoService.getSeriesTiempoByList(0, 0, "serieId", "asc", true, null, series, getUsuarioConectado(request));
				PaginaLista paginaSeries2 = strategosSeriesTiempoService.getSeriesTiempoByList(0, 0, "serieId", "asc", true, null, series, getUsuarioConectado(request));
    	  
				Boolean haySerie = null;
				for (Iterator<SerieTiempo> iter = paginaSeries2.getLista().iterator(); iter.hasNext(); )
				{
					SerieTiempo serie = iter.next();
					haySerie = false;
					for (Iterator<SerieTiempo> iter2 = series.iterator(); iter2.hasNext(); )
					{
						SerieTiempo serie2 = iter2.next();
						if (serie.getNombre().equals(serie2.getNombre()))
						{
							haySerie = true;
							break;
						}
					}
    		  
					if (!haySerie)
						paginaSeries.getLista().remove(serie);
				}
    	  
				for (Iterator<SerieTiempo> iter = paginaSeries.getLista().iterator(); iter.hasNext(); )
				{
					SerieTiempo serie = iter.next();
					serie.setPreseleccionada(true);
				}
			}
			else
			{
				Map<Object, Object> filtros = new HashMap<Object, Object>();
				filtros.put("oculta", false);
				
				paginaSeries = strategosSeriesTiempoService.getSeriesTiempo(0, 0, "serieId", "asc", true, filtros);
			}

			editarMedicionesForm.setPaginaSeriesTiempo(paginaSeries);
			
			strategosSeriesTiempoService.close();

			List<Frecuencia> frecuencias = null;
			if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_ACTIVIDAD)
			{
				frecuencias = new ArrayList<Frecuencia>();
				frecuencias.add(Frecuencia.getFrecuencia(editarMedicionesForm.getFrecuencia()));
			}
			else
				frecuencias = Frecuencia.getFrecuencias();
			editarMedicionesForm.setFrecuencias(frecuencias);

			int anoActual = Calendar.getInstance().get(1);
			
			if (!noExistenIndicadores && editarMedicionesForm.getSourceScreen() != TipoSource.SOURCE_ACTIVIDAD)
			{
				FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
				ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Configuracion.Medicion.Editar.Parametros", "EDITARMEDICIONES");
				frameworkService.close();
				
				if (configuracionUsuario != null && configuracionUsuario.getData() != null)
					editarMedicionesForm.setConfiguracion(configuracionUsuario.getData());
				else
				{
					if (editarMedicionesForm.getIndicadores().size() > 0)
						editarMedicionesForm.setFrecuencia(editarMedicionesForm.getIndicadores().get(0).getFrecuencia());
					else
						editarMedicionesForm.setFrecuencia(Frecuencia.getFrecuenciaMensual());
				}
			}
			else
			{
				if (editarMedicionesForm.getIndicadores().size() > 0)
					editarMedicionesForm.setFrecuencia(editarMedicionesForm.getIndicadores().get(0).getFrecuencia());
				else
					editarMedicionesForm.setFrecuencia(Frecuencia.getFrecuenciaMensual());
			}
			
			if (editarMedicionesForm.getSourceScreen() != TipoSource.SOURCE_ACTIVIDAD)
			{
				Calendar ahora = Calendar.getInstance();
				Plan plan = null;
				if (editarMedicionesForm.getPlanId() != null)
					plan = (Plan)strategosIndicadoresService.load(Plan.class, new Long(editarMedicionesForm.getPlanId()));
				else if (editarMedicionesForm.getPerspectivaId() != null)
				{
					Perspectiva perspectiva = (Perspectiva)strategosIndicadoresService.load(Perspectiva.class, new Long(editarMedicionesForm.getPerspectivaId()));
					plan = (Plan)strategosIndicadoresService.load(Plan.class, new Long(perspectiva.getPlanId()));
				}
				if (plan != null && plan.getAnoFinal() < anoActual)
					anoActual = plan.getAnoFinal();
				if (plan != null)
				{
					editarMedicionesForm.setAnoInicial(plan.getAnoInicial().toString());
					editarMedicionesForm.setAnoFinal(plan.getAnoFinal().toString());
				}
				else
				{
					Integer anoInicial = anoActual - 20; 
					Integer anoFinal = anoActual + 20;
					editarMedicionesForm.setAnoInicial(anoInicial.toString());
					editarMedicionesForm.setAnoFinal(anoFinal.toString());
				}
				
				editarMedicionesForm.setIniciativaId(null);
				editarMedicionesForm.setOrganizacionId(null);
				editarMedicionesForm.setFechaDesde(VgcFormatter.formatearFecha(ahora.getTime(), "formato.fecha.corta"));
				editarMedicionesForm.setFechaHasta(VgcFormatter.formatearFecha(ahora.getTime(), "formato.fecha.corta"));
				editarMedicionesForm.setAnoDesde(new Integer(anoActual).toString());
				editarMedicionesForm.setAnoHasta(new Integer(anoActual).toString());

				editarMedicionesForm.setAnos(PeriodoUtil.getListaNumeros(new Integer(anoActual), new Byte((byte) 10)));
				setPeriodos(editarMedicionesForm, strategosIndicadoresService);
			}
			else
			{
				Integer anoInicial = anoActual - 20; 
				Integer anoFinal = anoActual + 20;
				editarMedicionesForm.setAnoInicial(anoInicial.toString());
				editarMedicionesForm.setAnoFinal(anoFinal.toString());
			}
		}
		else
		{
			if (editarMedicionesForm.getSourceScreen() != TipoSource.SOURCE_ACTIVIDAD)
			{
				editarMedicionesForm.setIniciativaId(null);
				editarMedicionesForm.setOrganizacionId(null);
			}
			
			if (!noExistenIndicadores && editarMedicionesForm.getSourceScreen() != TipoSource.SOURCE_ACTIVIDAD)
			{
				FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
				ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Configuracion.Medicion.Editar.Parametros", "EDITARMEDICIONES");
				frameworkService.close();
				
				if (configuracionUsuario != null && configuracionUsuario.getData() != null)
					editarMedicionesForm.setConfiguracion(configuracionUsuario.getData());
			}
			else
			{
				if (editarMedicionesForm.getIndicadores().size() > 0)
					editarMedicionesForm.setFrecuencia(editarMedicionesForm.getIndicadores().get(0).getFrecuencia());
				else
					editarMedicionesForm.setFrecuencia(Frecuencia.getFrecuenciaMensual());
			}
		}

		strategosIndicadoresService.close();

		saveMessages(request, messages);
    
		return mapping.findForward(forward);
	}

	private void setPeriodos(EditarMedicionesForm editarMedicionesForm, StrategosIndicadoresService strategosIndicadoresService)
	{
		int numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(editarMedicionesForm.getFrecuencia().byteValue(), Integer.parseInt(editarMedicionesForm.getAnoHasta()));
		editarMedicionesForm.setNumeroMaximoPeriodos(new Integer(numeroMaximoPeriodos));
		editarMedicionesForm.setPeriodos(PeriodoUtil.getListaNumeros(new Integer(1), new Integer(numeroMaximoPeriodos)));
		editarMedicionesForm.setPeriodoDesde(new Integer(1));
		editarMedicionesForm.setPeriodoHasta(new Integer(numeroMaximoPeriodos));

		Calendar fecha = Calendar.getInstance();
		fecha.set(1, Integer.parseInt(editarMedicionesForm.getAnoDesde()));
		fecha.set(2, 0);
		fecha.set(5, 1);
		editarMedicionesForm.setFechaDesde(VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta"));
		fecha.set(1, Integer.parseInt(editarMedicionesForm.getAnoHasta()));
		fecha.set(2, 11);
		fecha.set(5, 31);
		editarMedicionesForm.setFechaHasta(VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta"));
	}
  
	private String[] setup(EditarMedicionesForm editarMedicionesForm, HttpServletRequest request)
	{
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(strategosPryActividadesService);

		editarMedicionesForm.setSoloSeleccionados(true);
		editarMedicionesForm.setDesdePlanificacion(true);
		
		Long tipo = new Long(request.getParameter("tipo"));
		List<SerieTiempo> series = new ArrayList<SerieTiempo>();
		if (tipo.longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
		{
			series.add(SerieTiempo.getSerieReal());
			series.add(SerieTiempo.getSerieProgramado());
		}
		else if (tipo.longValue() == SerieTiempo.getSerieProgramado().getSerieId().longValue())
			series.add(SerieTiempo.getSerieProgramado());
		
		StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService(strategosIndicadoresService);
		PaginaLista paginaSeries = strategosSeriesTiempoService.getSeriesTiempoByList(0, 0, "serieId", "asc", true, null, series, getUsuarioConectado(request));
		strategosSeriesTiempoService.close();
		
		editarMedicionesForm.setPaginaSeriesTiempo(paginaSeries);
		
		Date fechaIni = null;
		Date fechaFin = null;
		Integer ano = null;
		Integer periodo = null;
		if (request.getQueryString().indexOf("actividadId=") > -1)
		{
			String strActividadId = request.getParameter("actividadId");
			if (((strActividadId != null ? 1 : 0) & (strActividadId.equals("") ? 0 : 1)) != 0) 
			{
				String[] ids = strActividadId.split(",");
				editarMedicionesForm.setIndicadores(new ArrayList<Indicador>());
				StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
				for (int i = 0; i < ids.length; i++) 
				{
					PryActividad actividad = (PryActividad)strategosPryActividadesService.load(PryActividad.class, new Long(ids[i]));
					if (actividad != null)
					{
						if (tipo.longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
						{
							editarMedicionesForm.setDesdeReal(true);
							
							if(getUsuarioConectado(request).getIsAdmin() == true) {
								editarMedicionesForm.setEsAdmin(true);
							}else {
								editarMedicionesForm.setEsAdmin(false);
							}
							
							List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(actividad.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
							if (mediciones.size() > 0)
							{
								if (ano == null)
									ano = ((Medicion)mediciones.get(mediciones.size() -1)).getMedicionId().getAno();
								if (periodo == null)
									periodo = ((Medicion)mediciones.get(mediciones.size() -1)).getMedicionId().getPeriodo();
								
								String anoIni = ano.toString();
								String periodoIni = periodo.toString();
								String periodoIniStr = periodoIni.length() == 1 ? ("00" + periodoIni) : (periodoIni.length() == 2 ? ("0" + periodoIni) : periodoIni);
								String perIni = anoIni + periodoIniStr;
								
								String anoFin = ((Medicion)mediciones.get(mediciones.size() -1)).getMedicionId().getAno().toString();
								String periodoFin = ((Medicion)mediciones.get(mediciones.size() -1)).getMedicionId().getPeriodo().toString();
								String periodoFinStr = periodoFin.length() == 1 ? ("00" + periodoFin) : (periodoFin.length() == 2 ? ("0" + periodoFin) : periodoFin);  
								String perFin = anoFin + periodoFinStr;  
								
								if (Integer.parseInt(perIni) < Integer.parseInt(perFin))
								{
									ano = Integer.parseInt(anoFin);
									periodo = Integer.parseInt(periodoFin);
								}
							}
							
							if (fechaIni == null)
								fechaIni = actividad.getComienzoReal() != null ? actividad.getComienzoReal() : actividad.getComienzoPlan();
							if (fechaFin == null)
								fechaFin = actividad.getFinReal() != null ? actividad.getFinReal() : actividad.getFinPlan();
							
							Date fecha = actividad.getComienzoReal() != null ? actividad.getComienzoReal() : actividad.getComienzoPlan();
							if (fecha != null && fecha.before(fechaIni))
								fechaIni = fecha;
							fecha = actividad.getFinReal() != null ? actividad.getFinReal() : actividad.getFinPlan();
							if (fecha != null && fecha.after(fechaFin))
								fechaFin = fecha;
						}
						else
						{
							List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(actividad.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
							if (mediciones.size() > 0)
							{
								if (ano == null)
									ano = ((Medicion)mediciones.get(mediciones.size() -1)).getMedicionId().getAno();
								if (periodo == null)
									periodo = ((Medicion)mediciones.get(mediciones.size() -1)).getMedicionId().getPeriodo();
								
								String anoIni = ano.toString();
								String periodoIni = periodo.toString();
								String periodoIniStr = periodoIni.length() == 1 ? ("00" + periodoIni) : (periodoIni.length() == 2 ? ("0" + periodoIni) : periodoIni);
								String perIni = anoIni + periodoIniStr;
								
								String anoFin = ((Medicion)mediciones.get(mediciones.size() -1)).getMedicionId().getAno().toString();
								String periodoFin = ((Medicion)mediciones.get(mediciones.size() -1)).getMedicionId().getPeriodo().toString();
								String periodoFinStr = periodoFin.length() == 1 ? ("00" + periodoFin) : (periodoFin.length() == 2 ? ("0" + periodoFin) : periodoFin);  
								String perFin = anoFin + periodoFinStr;  
								
								if (Integer.parseInt(perIni) < Integer.parseInt(perFin))
								{
									ano = Integer.parseInt(anoFin);
									periodo = Integer.parseInt(periodoFin);
								}
							}
							if (fechaIni == null)
								fechaIni = actividad.getComienzoPlan();
							if (fechaFin == null)
								fechaFin = actividad.getFinPlan() != null ? actividad.getFinPlan() : actividad.getComienzoPlan();
							
							if (actividad.getComienzoPlan() != null && actividad.getComienzoPlan().before(fechaIni))
								fechaIni = actividad.getComienzoPlan();
							Date fecha = actividad.getFinPlan() != null ? actividad.getFinPlan() : actividad.getComienzoPlan();
							if (fecha != null && fecha.after(fechaFin))
								fechaFin = fecha;
						}
						
						Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(actividad.getIndicadorId()));
						editarMedicionesForm.setFrecuencia(indicador.getFrecuencia());
						indicador.setActividadId(actividad.getActividadId());
						editarMedicionesForm.getIndicadores().add(indicador);
					}
				}
				strategosMedicionesService.close();
			}
		}
		
		editarMedicionesForm.setIniciativaId(new Long(request.getParameter("iniciativaId")));
		editarMedicionesForm.setOrganizacionId(new Long(request.getParameter("organizacionId")));
	
		Iniciativa iniciativa = (Iniciativa)strategosPryActividadesService.load(Iniciativa.class, new Long(editarMedicionesForm.getIniciativaId()));
		editarMedicionesForm.setIniciativa(iniciativa.getNombre());
		editarMedicionesForm.setOrganizacion(iniciativa.getOrganizacion().getNombre());

		Calendar calFechaDesde = Calendar.getInstance();
		calFechaDesde.setTime(fechaIni);
		
		Calendar calFechaHasta = Calendar.getInstance();
		if (ano != null && periodo != null)
		{
			Calendar calFechaHastaPeriodo = PeriodoUtil.getDateByPeriodo(iniciativa.getFrecuencia(), ano, periodo, false);
			if (fechaFin.before(calFechaHastaPeriodo.getTime()))
				fechaFin = calFechaHastaPeriodo.getTime();
		}
		calFechaHasta.setTime(fechaFin);

		editarMedicionesForm.setFechaDesde(VgcFormatter.formatearFecha(calFechaDesde.getTime(), "formato.fecha.corta"));
		editarMedicionesForm.setAnosD(PeriodoUtil.getListaNumeros(new Integer(editarMedicionesForm.getFechaDesde().split("/")[2]), new Integer(editarMedicionesForm.getFechaDesde().split("/")[2])));
		editarMedicionesForm.setAnoDesde(new Integer(calFechaDesde.get(1)).toString());
		editarMedicionesForm.setPeriodoDesde(PeriodoUtil.getPeriodoDeFecha(calFechaDesde, editarMedicionesForm.getFrecuencia()));
		editarMedicionesForm.setPeriodosD(PeriodoUtil.getListaNumeros(editarMedicionesForm.getPeriodoDesde(), editarMedicionesForm.getPeriodoDesde()));
		
		editarMedicionesForm.setFechaHasta(VgcFormatter.formatearFecha(calFechaHasta.getTime(), "formato.fecha.corta"));
		editarMedicionesForm.setAnosH(PeriodoUtil.getListaNumeros(new Integer(editarMedicionesForm.getFechaHasta().split("/")[2]), new Integer(editarMedicionesForm.getFechaHasta().split("/")[2])));
		editarMedicionesForm.setAnoHasta(new Integer(calFechaHasta.get(1)).toString());
		editarMedicionesForm.setPeriodoHasta(PeriodoUtil.getPeriodoDeFecha(calFechaHasta, editarMedicionesForm.getFrecuencia()));
		editarMedicionesForm.setPeriodosH(PeriodoUtil.getListaNumeros(editarMedicionesForm.getPeriodoHasta(), editarMedicionesForm.getPeriodoHasta()));

		editarMedicionesForm.setAnos(PeriodoUtil.getListaNumeros(new Integer(editarMedicionesForm.getFechaDesde().split("/")[2]), new Integer(editarMedicionesForm.getFechaHasta().split("/")[2])));
		editarMedicionesForm.setPeriodos(PeriodoUtil.getListaNumeros(editarMedicionesForm.getPeriodoDesde(), editarMedicionesForm.getPeriodoHasta()));
  
		int numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(editarMedicionesForm.getFrecuencia().byteValue(), calFechaHasta.get(1));
		editarMedicionesForm.setNumeroMaximoPeriodos(numeroMaximoPeriodos);
		
		strategosIndicadoresService.close();
		strategosPryActividadesService.close();
		
		String[] serie = null;
		if (tipo.longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
		{
			serie = new String[2];
			serie[0] = SerieTiempo.getSerieReal().getSerieId().toString();
			serie[1] = SerieTiempo.getSerieProgramado().getSerieId().toString();
		}
		else
		{
			serie = new String[1];
			serie[0] = tipo.toString();
		}
		
		return serie;
	}
}