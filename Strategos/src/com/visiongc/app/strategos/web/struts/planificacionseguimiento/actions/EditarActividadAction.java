package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import com.visiongc.app.strategos.calculos.model.util.VgcFormulaEvaluator;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.util.NaturalezaActividad;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.indicadores.validators.IndicadorValidator;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms.EditarActividadForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
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

public class EditarActividadAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    super.execute(mapping, form, request, response);
	
	    String forward = mapping.getParameter();
	    Boolean inicializar = false;
    	String funcion = request.getParameter("funcion");
    	if (funcion.equals("Inicializar"))
    		inicializar = true;
    	
    	
	    EditarActividadForm editarActividadForm = (EditarActividadForm)form;
	    
	    if (editarActividadForm.getStatus() != null && editarActividadForm.getStatus().byteValue() == StatusUtil.getStatusSuccessModify() && !inicializar)
	    	return mapping.findForward(forward);
	    
	    ActionMessages messages = getMessages(request);
	
	    String actividadId = request.getParameter("actividadId");
	
	    Long seleccionadoId = editarActividadForm.getSeleccionados() == null ? new Long(0L) : editarActividadForm.getSeleccionados();
	    
	    Boolean desdeInstrumento = (request.getParameter("desdeInstrumento") != null && request.getParameter("desdeInstrumento") != "") ? Boolean.valueOf(request.getParameter("desdeInstrumento")) : null;
		
	    
	    
		boolean verForm = getPermisologiaUsuario(request).tienePermiso("ACTIVIDAD_VIEWALL");
		boolean editarForm = getPermisologiaUsuario(request).tienePermiso("ACTIVIDAD_EDIT");
	    boolean bloqueado = false;
	    boolean asociado = false;
	    boolean actividadNuevo = true;
	    StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

	    if ((actividadId != null) && (!actividadId.equals("")) && (!actividadId.equals("0")))
	    {
	    	bloqueado = !strategosPryActividadesService.lockForUpdate(request.getSession().getId(), actividadId, null);
	    	editarActividadForm.setBloqueado(new Boolean(bloqueado));
	    	editarActividadForm.clear();	    	
	    	PryActividad pryActividad = (PryActividad)strategosPryActividadesService.load(PryActividad.class, new Long(actividadId));

	    	if (pryActividad != null)
	    	{
		    	actividadNuevo = false;
	    		if (bloqueado)
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));

	    		

	    		if (inicializar)
	    			editarActividadForm.setStatus(StatusUtil.getStatusInit());
	    		editarActividadForm.setFuncionesFormula(VgcFormulaEvaluator.getListaFunciones());
        
	    		editarActividadForm.setActividadId(pryActividad.getActividadId());
	    		editarActividadForm.setPadreId(pryActividad.getPadreId());
	    		editarActividadForm.setNombre(pryActividad.getNombre());
	    		editarActividadForm.setDescripcion(pryActividad.getDescripcion());
	    		
	    		editarActividadForm.setUnidadId(pryActividad.getUnidadId());
	    		if (pryActividad.getUnidadMedida() != null)
	    			editarActividadForm.setUnidadMedida(pryActividad.getUnidadMedida().getNombre());
	    		else 
	    			editarActividadForm.setUnidadMedida(null);
	    		setUnidadesMedida(editarActividadForm);
	    		editarActividadForm.setTipoMedicion(pryActividad.getTipoMedicion());
	    		editarActividadForm.setNaturaleza(pryActividad.getNaturaleza());
	    		
	    		editarActividadForm.setResponsableCargarEjecutadoId(pryActividad.getResponsableCargarEjecutadoId());
	    		editarActividadForm.setResponsableCargarMetaId(pryActividad.getResponsableCargarMetaId());
	    		editarActividadForm.setResponsableFijarMetaId(pryActividad.getResponsableFijarMetaId());
	    		editarActividadForm.setResponsableLograrMetaId(pryActividad.getResponsableLograrMetaId());
	    		editarActividadForm.setResponsableSeguimientoId(pryActividad.getResponsableSeguimientoId());
	    		if (pryActividad.getResponsableCargarEjecutado() != null) 
	    			editarActividadForm.setResponsableCargarEjecutado(pryActividad.getResponsableCargarEjecutado().getNombreCargo());
	    		if (pryActividad.getResponsableCargarMeta() != null) 
	    			editarActividadForm.setResponsableCargarMeta(pryActividad.getResponsableCargarMeta().getNombreCargo());
	    		if (pryActividad.getResponsableFijarMeta() != null) 
	    			editarActividadForm.setResponsableFijarMeta(pryActividad.getResponsableFijarMeta().getNombreCargo());
	    		if (pryActividad.getResponsableLograrMeta() != null) 
	    			editarActividadForm.setResponsableLograrMeta(pryActividad.getResponsableLograrMeta().getNombreCargo());
	    		if (pryActividad.getResponsableSeguimiento() != null) 
	    			editarActividadForm.setResponsableSeguimiento(pryActividad.getResponsableSeguimiento().getNombreCargo());

	    		if (pryActividad.getComienzoPlan() != null)
	    			editarActividadForm.setComienzoPlan(VgcFormatter.formatearFecha(pryActividad.getComienzoPlan(), "formato.fecha.corta"));
	    		else 
	    			editarActividadForm.setComienzoPlan(null);

	    		if (pryActividad.getFinPlan() != null)
	    			editarActividadForm.setFinPlan(VgcFormatter.formatearFecha(pryActividad.getFinPlan(), "formato.fecha.corta"));
	    		else 
	    			editarActividadForm.setFinPlan(null);
	    		if (pryActividad.getDuracionPlan() != null)
	    			editarActividadForm.setDuracionPlan(new Integer(pryActividad.getDuracionPlan().intValue()));
	    		else
	    			editarActividadForm.setDuracionPlan(null);

	    		if (pryActividad.getPryInformacionActividad() != null) 
	    		{
	    			editarActividadForm.setProductoEsperado(pryActividad.getPryInformacionActividad().getProductoEsperado());
	    			editarActividadForm.setRecursosHumanos(pryActividad.getPryInformacionActividad().getRecursosHumanos());
	    			editarActividadForm.setRecursosMateriales(pryActividad.getPryInformacionActividad().getRecursosMateriales());
	    			editarActividadForm.setPorcentajeVerde(pryActividad.getPryInformacionActividad().getPorcentajeVerde());
	    			editarActividadForm.setPorcentajeAmarillo(pryActividad.getPryInformacionActividad().getPorcentajeAmarillo());
	    		}

	    		if (pryActividad.getObjetoAsociadoId() != null) 
	    		{
	    			asociado = true;
	    			editarActividadForm.setBloqueado(asociado);
	    			Iniciativa iniciativa = (Iniciativa)strategosPryActividadesService.load(Iniciativa.class, pryActividad.getObjetoAsociadoId());
	    			if (iniciativa != null) 
	    			{
	    				editarActividadForm.setAsociadaNombre(iniciativa.getNombre());
	    				if (iniciativa.getOrganizacion() != null)
	    					editarActividadForm.setAsociadaOrganizacion(iniciativa.getOrganizacion().getNombre());
	    			}
	    			else 
	    			{
	    				List<PryActividad> actividades = strategosPryActividadesService.getActividades(pryActividad.getObjetoAsociadoId(), (byte) 3);
	    				if ((actividades != null) && (actividades.size() > 0)) 
	    				{
	    					PryActividad actividadAsociada = (PryActividad)actividades.get(0);
	    					editarActividadForm.setAsociadaNombre(actividadAsociada.getNombre());
	    				}
	    			}
	    		}
        
	    		if (pryActividad.getIndicadorId() != null && pryActividad.getNaturaleza().equals(Naturaleza.getNaturalezaFormula()))
	    		{
	    			editarActividadForm.setIndicadorId(new Long(pryActividad.getIndicadorId()));
	    			editarActividadForm.setClaseId(pryActividad.getClaseId());
	    			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    			
	    			Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(pryActividad.getIndicadorId()));
	        
	    			if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaFormula()))
	    				setDefinicionFomula(request, indicador, editarActividadForm, strategosIndicadoresService);
	
	    			strategosIndicadoresService.close();
	    		}
	    		else if (pryActividad.getIndicadorId() != null)
	    		{
	    			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    			Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(pryActividad.getIndicadorId()));
	    			strategosIndicadoresService.close();
	    			
	    			editarActividadForm.setCodigoEnlace(indicador.getCodigoEnlace());
	    			editarActividadForm.setEnlaceParcial(indicador.getEnlaceParcial());
	    		}
	    		
	    	    Map<String, Object> filtros = new HashMap<String, Object>();
	    		  
	    		filtros.put("proyectoId", pryActividad.getProyectoId().toString());
	    		filtros.put("padreId", pryActividad.getActividadId().toString());
	    		String atributoOrden = "fila";
	    		String tipoOrden = "ASC";
		    	int pagina = 1;
		    	List<PryActividad> actividades = strategosPryActividadesService.getActividades(pagina, 30, atributoOrden, tipoOrden, true, filtros).getLista();
		    	if (actividades.size() > 0)
		    		editarActividadForm.setEsPadre(new Boolean(true));
		    	else
		    		editarActividadForm.setEsPadre(new Boolean(false));
	    	}
	    	else
	    	{
	    		strategosPryActividadesService.unlockObject(request.getSession().getId(), editarActividadForm.getActividadId());
	    		
	    		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
	    		forward = "noencontrado";
	    	}
	    }
	    else 
	    {
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			if (!strategosIndicadoresService.checkLicencia(request))
			{
				strategosIndicadoresService.close();
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("action.guardarregistro.limite.exedido"));
				this.saveMessages(request, messages);
				
				return this.getForwardBack(request, 1, false);
			}
			strategosIndicadoresService.close();
	    	editarActividadForm.clear();
    		if (inicializar)
    			editarActividadForm.setStatus(StatusUtil.getStatusInit());
	    	editarActividadForm.setSeleccionados(seleccionadoId);
	    	StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService(strategosPryActividadesService);
	    	UnidadMedida unidad = strategosUnidadesService.getUnidadMedidaPorcentaje();
	    	editarActividadForm.setUnidadId(unidad.getUnidadId());
	    	editarActividadForm.setUnidadMedida(unidad.getNombre());
	    	setUnidadesMedida(editarActividadForm);
	    	editarActividadForm.setFuncionesFormula(VgcFormulaEvaluator.getListaFunciones());
	    	strategosUnidadesService.close();
	    }

	    StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance().openStrategosPryProyectosService(strategosPryActividadesService);

	    editarActividadForm.setCalendario(strategosPryProyectosService.getCalendarioProyecto(editarActividadForm.getProyectoId(), getUsuarioConectado(request)));

	    strategosPryProyectosService.close();

	    Iniciativa iniciativa = (Iniciativa)strategosPryActividadesService.load(Iniciativa.class, editarActividadForm.getIniciativaId());
	    if (actividadNuevo && iniciativa != null)
	    	editarActividadForm.setTipoMedicion(iniciativa.getTipoMedicion());

	    if ((editarActividadForm.getComienzoPlan() != null) && (!editarActividadForm.getComienzoPlan().equals(""))) 
	    {
	    	Date dateFecha = FechaUtil.convertirStringToDate(editarActividadForm.getComienzoPlan(), "formato.fecha.corta");
	    	FechaUtil.setHoraInicioDia(dateFecha);
	    	Calendar calFecha = Calendar.getInstance();
	    	calFecha.setTime(dateFecha);
	    	editarActividadForm.setComienzoPlanTexto(PeriodoUtil.convertirFechaToTexto(calFecha, iniciativa.getFrecuencia().byteValue()));
	    	dateFecha = FechaUtil.convertirStringToDate(editarActividadForm.getFinPlan(), "formato.fecha.corta");
	    	FechaUtil.setHoraFinalDia(dateFecha);
	    	calFecha = Calendar.getInstance();
	    	calFecha.setTime(dateFecha);
	    	editarActividadForm.setFinPlanTexto(PeriodoUtil.convertirFechaToTexto(calFecha, iniciativa.getFrecuencia().byteValue()));
	    }

	    editarActividadForm.setOrganizacionId(new Long(iniciativa.getOrganizacionId()));
	    editarActividadForm.setFrecuencia(iniciativa.getFrecuencia());
	    editarActividadForm.setNaturalezas(NaturalezaActividad.getNaturalezas(asociado));
	    		
		if (!editarActividadForm.getBloqueado())
		   	editarActividadForm.setBloqueado(iniciativa.getEstatus().getBloquearMedicion());
		    
	    request.setAttribute("periodo", iniciativa.getFrecuencia());
    
	    strategosPryActividadesService.close();

		if (!bloqueado && verForm && !editarForm)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
			editarActividadForm.setBloqueado(true);
		}
		else if (!bloqueado && !verForm && !editarForm)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));
	    
	    saveMessages(request, messages);

	    if (forward.equals("noencontrado"))
	    {
	    	editarActividadForm.setStatus(StatusUtil.getStatusSuccessModify());
	    	return mapping.findForward(forward);
	    }
    
	    return mapping.findForward(forward);
	}
	
	private void setUnidadesMedida(EditarActividadForm editarActividadForm) 
	{
	    StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();

	    editarActividadForm.setUnidadesMedida(strategosUnidadesService.getUnidadesMedida(0, 0, "nombre", "asc", false, null).getLista());

	    strategosUnidadesService.close();
	}
  
	private void setDefinicionFomula(HttpServletRequest request, Indicador indicador, EditarActividadForm editarActividadForm, StrategosIndicadoresService strategosIndicadoresService)
	{
		String insumosFormula = "";
		int indice = 1;
    
		StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService(strategosIndicadoresService);
		List<?> seriesTiempo = strategosSeriesTiempoService.getSeriesTiempo(0, 0, "serieId", null, false, null).getLista();
    
		SerieIndicador serieReal = setSeriesIndicador(indicador, editarActividadForm);

		Formula formulaIndicador = null;

		if (serieReal.getFormulas().size() > 0) 
			formulaIndicador = (Formula)serieReal.getFormulas().toArray()[0];

		if (formulaIndicador != null)
		{
			for (Iterator<?> k = formulaIndicador.getInsumos().iterator(); k.hasNext(); ) 
			{
				InsumoFormula insumo = (InsumoFormula)k.next();
				Indicador indicadorInsumo = strategosIndicadoresService.getIndicadorBasico(insumo.getPk().getIndicadorId());
				
				String nombreSerie = null;
				for (Iterator<?> j = seriesTiempo.iterator(); j.hasNext(); ) 
				{
					SerieTiempo serie = (SerieTiempo)j.next();
					
					if (serie.getSerieId().equals(insumo.getPk().getInsumoSerieId())) 
					{
						nombreSerie = serie.getNombre();
						break;
					}
				}
				
				if (insumo != null && indicadorInsumo != null)
					insumosFormula = insumosFormula + "[" + indice + "]" + "[indicadorId:" + insumo.getPk().getIndicadorId() + "]" + "[serieId:" + insumo.getPk().getInsumoSerieId() + "]" + "[indicadorNombre:" + indicadorInsumo.getNombre() + "][serieNombre:" + nombreSerie + "]" + "[rutaCompleta:" + strategosIndicadoresService.getRutaCompletaIndicador(insumo.getPk().getIndicadorId(), editarActividadForm.getSeparadorRuta()) + "]" + editarActividadForm.getSeparadorIndicadores();
				
				indice++;
			}

			if (!insumosFormula.equals("")) 
				editarActividadForm.setInsumosFormula(insumosFormula.substring(0, insumosFormula.length() - editarActividadForm.getSeparadorIndicadores().length()));

			if (formulaIndicador.getExpresion() != null) 
			{
				String formula = IndicadorValidator.reemplazarIdsPorCorrelativosFormula(formulaIndicador.getExpresion(), insumosFormula);
				editarActividadForm.setFormula(formula);
			}
		}
	}
  
	private SerieIndicador setSeriesIndicador(Indicador indicador, EditarActividadForm editarActividadForm)
	{
		Set<?> seriesIndicador = indicador.getSeriesIndicador();
		
		String listaSeries = "";

		SerieIndicador serieIndicador = null;

		for (Iterator<?> i = seriesIndicador.iterator(); i.hasNext(); ) 
		{
			SerieIndicador serie = (SerieIndicador)i.next();
			
			if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue()) 
				serieIndicador = serie;

			listaSeries = listaSeries + editarActividadForm.getSeparadorSeries() + serie.getPk().getSerieId() + editarActividadForm.getSeparadorSeries();
		}

		editarActividadForm.setSeriesIndicador(listaSeries);
    
		return serieIndicador;
	}
}