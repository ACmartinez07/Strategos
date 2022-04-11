package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.InsumoFormulaPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.PrioridadIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.ConfiguracionIniciativa;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryInformacionActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.indicadores.validators.IndicadorValidator;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms.EditarActividadForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.StringUtil;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarActividadAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

	    String forward = mapping.getParameter();
	
	    EditarActividadForm editarActividadForm = (EditarActividadForm)form;
	    
	    ActionMessages messages = getMessages(request);
	
	    StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
	
    	StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(strategosPryActividadesService);
    	Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, editarActividadForm.getIniciativaId());
	    
	    boolean nuevo = false;
	    PryActividad pryActividad = new PryActividad();
	    int respuesta = 10000;
	
	    if ((editarActividadForm.getActividadId() != null) && (!editarActividadForm.getActividadId().equals(Long.valueOf("0"))))
	    	pryActividad = (PryActividad)strategosPryActividadesService.load(PryActividad.class, editarActividadForm.getActividadId());
	    else
	    {
	    	nuevo = true;
	    	pryActividad = new PryActividad();
	    	pryActividad.setActividadId(new Long(0L));
	    	pryActividad.setPryInformacionActividad(new PryInformacionActividad());
	
	    	int filaNuevaActividad = 1;
	    	int nivelNuevaActividad = 1;
	    	Long padreId = null;
	
	    	if ((editarActividadForm.getSeleccionados() != null) && (editarActividadForm.getSeleccionados().longValue() != 0L)) 
	    	{
	    		filaNuevaActividad = ((Integer)strategosPryActividadesService.getValoresLimiteAlcanceHijosActividad(editarActividadForm.getSeleccionados().longValue(), new Boolean(false), (Usuario)request.getSession().getAttribute("usuario")).get(0)).intValue();
	    		PryActividad actividadBase = (PryActividad)strategosPryActividadesService.load(PryActividad.class, editarActividadForm.getSeleccionados());
	    		nivelNuevaActividad = actividadBase.getNivel().intValue();
	    		if (actividadBase.getPadre() != null)
	    			padreId = actividadBase.getPadre().getActividadId();
	    		else 
	    			padreId = null;
	    	}
	    	else
	    		filaNuevaActividad = strategosPryActividadesService.getMaximaFila(editarActividadForm.getProyectoId().longValue(), (Usuario)request.getSession().getAttribute("usuario"));
	    	
	    	filaNuevaActividad++;
	
	    	pryActividad.setPadreId(padreId);
	    	pryActividad.setFila(new Integer(filaNuevaActividad));
	    	pryActividad.setNivel(new Integer(nivelNuevaActividad));
	    	pryActividad.setProyectoId(editarActividadForm.getProyectoId());
	    }
	    
    	PryActividad pryActividadPadre = null;
    	if (pryActividad.getPadreId() != null)
    		pryActividadPadre = (PryActividad)strategosPryActividadesService.load(PryActividad.class, new Long(pryActividad.getPadreId()));
	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calFechaDesde = Calendar.getInstance();
		calFechaDesde.setTime(simpleDateFormat.parse(editarActividadForm.getComienzoPlan()));
		calFechaDesde = PeriodoUtil.inicioDelDia(calFechaDesde);
		
		Calendar calFechaHasta = Calendar.getInstance();
		calFechaHasta.setTime(simpleDateFormat.parse(editarActividadForm.getFinPlan()));
		calFechaHasta = PeriodoUtil.finDelDia(calFechaHasta);

	    pryActividad.setUnidadId(editarActividadForm.getUnidadId());
	    pryActividad.setNaturaleza(editarActividadForm.getNaturaleza());
	    pryActividad.setTipoMedicion(editarActividadForm.getTipoMedicion());
	
	    if (editarActividadForm.getNombre() != null)
	    	pryActividad.setNombre(editarActividadForm.getNombre());
	    else 
	    	pryActividad.setNombre(null);
	
	    if ((editarActividadForm.getDescripcion() != null) && (!editarActividadForm.getDescripcion().equals("")))
	    	pryActividad.setDescripcion(editarActividadForm.getDescripcion());
	    else 
	    	pryActividad.setDescripcion(null);
	
	    if (editarActividadForm.getComienzoPlan() != null)
	    {
	    	if ((pryActividad.getHijos() == null) || (pryActividad.getHijos().size() == 0))
	    		pryActividad.setComienzoPlan(calFechaDesde.getTime());
	    }
	    else 
	    	pryActividad.setComienzoPlan(null);
	
	    if (editarActividadForm.getFinPlan() != null)
	    {
	    	if ((pryActividad.getHijos() == null) || (pryActividad.getHijos().size() == 0))
	    		pryActividad.setFinPlan(calFechaHasta.getTime());
	    }
	    else 
	    	pryActividad.setFinPlan(null);
	
	    if (editarActividadForm.getDuracionPlan() != null) 
	    {
	    	if ((pryActividad.getHijos() == null) || (pryActividad.getHijos().size() == 0))
	    		pryActividad.setDuracionPlan(new Double(editarActividadForm.getDuracionPlan().doubleValue()));
	    }
	    else 
	    	pryActividad.setDuracionPlan(null);
	
	    if (editarActividadForm.getUnidadId().equals(new Long(0L)))
	    	pryActividad.setUnidadId(null);
	    else 
	    	pryActividad.setUnidadId(editarActividadForm.getUnidadId());
	
	    if (editarActividadForm.getResponsableFijarMetaId().equals(new Long(0L)))
	    	pryActividad.setResponsableFijarMetaId(null);
	    else 
	    	pryActividad.setResponsableFijarMetaId(editarActividadForm.getResponsableFijarMetaId());
	
	    if (editarActividadForm.getResponsableLograrMetaId().equals(new Long(0L)))
	    	pryActividad.setResponsableLograrMetaId(null);
	    else 
	    	pryActividad.setResponsableLograrMetaId(editarActividadForm.getResponsableLograrMetaId());
	
	    if (editarActividadForm.getResponsableSeguimientoId().equals(new Long(0L)))
	    	pryActividad.setResponsableSeguimientoId(null);
	    else 
	    	pryActividad.setResponsableSeguimientoId(editarActividadForm.getResponsableSeguimientoId());
	
	    if (editarActividadForm.getResponsableCargarMetaId().equals(new Long(0L)))
	    	pryActividad.setResponsableCargarMetaId(null);
	    else 
	    	pryActividad.setResponsableCargarMetaId(editarActividadForm.getResponsableCargarMetaId());
	
	    if (editarActividadForm.getResponsableCargarEjecutadoId().equals(new Long(0L)))
	    	pryActividad.setResponsableCargarEjecutadoId(null);
	    else 
	    	pryActividad.setResponsableCargarEjecutadoId(editarActividadForm.getResponsableCargarEjecutadoId());
	
	    if (editarActividadForm.getUnidadId().equals(new Long(0L)))
	    	pryActividad.setUnidadId(null);
	    else 
	    	pryActividad.setUnidadId(editarActividadForm.getUnidadId());
	
	    if ((editarActividadForm.getProductoEsperado() != null) && (!editarActividadForm.getProductoEsperado().equals("")))
	    	pryActividad.getPryInformacionActividad().setProductoEsperado(editarActividadForm.getProductoEsperado());
	    else 
	    	pryActividad.getPryInformacionActividad().setProductoEsperado(null);
	
	    if ((editarActividadForm.getRecursosHumanos() != null) && (!editarActividadForm.getRecursosHumanos().equals("")))
	    	pryActividad.getPryInformacionActividad().setRecursosHumanos(editarActividadForm.getRecursosHumanos());
	    else 
	    	pryActividad.getPryInformacionActividad().setRecursosHumanos(null);
	
	    if ((editarActividadForm.getRecursosMateriales() != null) && (!editarActividadForm.getRecursosMateriales().equals("")))
	    	pryActividad.getPryInformacionActividad().setRecursosMateriales(editarActividadForm.getRecursosMateriales());
	    else 
	    	pryActividad.getPryInformacionActividad().setRecursosMateriales(null);
	
	    if (editarActividadForm.getPorcentajeVerde() != null && (editarActividadForm.getHayValorPorcentajeVerde() != null && editarActividadForm.getHayValorPorcentajeVerde()))
	    	pryActividad.getPryInformacionActividad().setPorcentajeVerde(editarActividadForm.getPorcentajeVerde());
	    else 
	    	pryActividad.getPryInformacionActividad().setPorcentajeVerde(null);
	
	    if (editarActividadForm.getPorcentajeAmarillo() != null && (editarActividadForm.getHayValorPorcentajeAmarillo() != null && editarActividadForm.getHayValorPorcentajeAmarillo()))
	    	pryActividad.getPryInformacionActividad().setPorcentajeAmarillo(editarActividadForm.getPorcentajeAmarillo());
	    else 
	    	pryActividad.getPryInformacionActividad().setPorcentajeAmarillo(null);
	    
	    if (editarActividadForm.getEliminarMediciones())
	    {
	    	pryActividad.setFechaUltimaMedicion(null);
	    	pryActividad.setPorcentajeCompletado(null);
	    	pryActividad.setPorcentajeEjecutado(null);
	    	pryActividad.setPorcentajeEsperado(null);
	    	pryActividad.setAlerta(null);
	    	pryActividad.setComienzoReal(null);
	    	pryActividad.setFinReal(null);
	    }
	    
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    Indicador indicador = new Indicador();
	    if (pryActividad.getIndicadorId() != null)
	        indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(pryActividad.getIndicadorId()));
	    else
	    {
	        indicador = new Indicador();
	        indicador.setIndicadorId(new Long(0L));

	        ClaseIndicadores claseActvidad = new ClaseIndicadores();
	        claseActvidad.setClaseId(new Long(0L));
	        if (pryActividad.getPadreId() == null)
		        claseActvidad.setPadreId(iniciativa.getClaseId());
	        else
	        {
	        	PryActividad actividadPadre = (PryActividad)strategosPryActividadesService.load(PryActividad.class, pryActividad.getPadreId());
	        	claseActvidad.setPadreId(actividadPadre.getClaseId());
	        }
	        claseActvidad.setNombre(pryActividad.getNombre());
	        claseActvidad.setOrganizacionId(editarActividadForm.getOrganizacionId());
	        claseActvidad.setTipo(TipoClaseIndicadores.TIPO_CLASE_PLANIFICACION_SEGUIMIENTO);
	        
	        StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
	        respuesta = strategosClasesIndicadoresService.saveClaseIndicadores(claseActvidad, getUsuarioConectado(request));
	        strategosClasesIndicadoresService.close();
	        
	        indicador.setClaseId(claseActvidad.getClaseId());
	        indicador.setOrganizacionId(editarActividadForm.getOrganizacionId());
	        
	        indicador.setEscalaCualitativa(new ArrayList<Object>());
	        indicador.setSeriesIndicador(new HashSet<Object>());
	    }

	    indicador.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionSeguimiento());
	    indicador.setPrioridad(PrioridadIndicador.getPrioridadIndicadorBaja());        
	    indicador.setCorte(TipoCorte.getTipoCorteLongitudinal());
	    indicador.setCaracteristica(Caracteristica.getCaracteristicaRetoAumento());
	    indicador.setGuia(new Boolean(false));
	    indicador.setNumeroDecimales((byte) 2);
	    indicador.setMostrarEnArbol(new Boolean(true));
		indicador.setNombre(editarActividadForm.getNombre());
	    indicador.setNombreCorto(editarActividadForm.getNombre());
	    indicador.setNaturaleza(editarActividadForm.getNaturaleza());
	    indicador.setFrecuencia(editarActividadForm.getFrecuencia());
	    indicador.setTipoCargaMedicion(editarActividadForm.getTipoMedicion());
	    indicador.setCodigoEnlace(editarActividadForm.getCodigoEnlace());
	    indicador.setEnlaceParcial(editarActividadForm.getEnlaceParcial());
	    
	    if ((editarActividadForm.getUnidadId() != null) && (editarActividadForm.getUnidadId().longValue() > 0L))
	    	indicador.setUnidadId(editarActividadForm.getUnidadId());
	    else 
	    	indicador.setUnidadId(null);
	    if (editarActividadForm.getPorcentajeVerde() != null && editarActividadForm.getHayValorPorcentajeVerde())
	    	indicador.setAlertaMetaZonaVerde(editarActividadForm.getPorcentajeVerde());
	    else
	    {
			Double alerta = null;  
	    	if (pryActividadPadre != null)
	    	{
	    		if (pryActividadPadre.getPryInformacionActividad().getPorcentajeVerde() != null)
	    			alerta = (pryActividadPadre.getPryInformacionActividad().getPorcentajeVerde());
	    	}
		  
	    	if (alerta == null)
	    	{
      		  	if (iniciativa.getAlertaZonaVerde() != null)
      		  		alerta = (iniciativa.getAlertaZonaVerde());
	    	}
	    	indicador.setAlertaMetaZonaVerde(alerta);
	    }
	    
	    if (editarActividadForm.getPorcentajeAmarillo() != null && editarActividadForm.getHayValorPorcentajeAmarillo())
	    	indicador.setAlertaMetaZonaAmarilla(editarActividadForm.getPorcentajeAmarillo());
	    else
	    {
	    	Double alerta = null;
	    	if (pryActividadPadre != null)
	    	{
	    		if (pryActividadPadre.getPryInformacionActividad().getPorcentajeVerde() != null && pryActividadPadre.getPryInformacionActividad().getPorcentajeAmarillo() != null)
	    			alerta = (pryActividadPadre.getPryInformacionActividad().getPorcentajeAmarillo());
	    	}
	    	
	    	if (alerta == null)
	    	{
	    		if (iniciativa.getAlertaZonaVerde() != null && iniciativa.getAlertaZonaAmarilla() != null)
	    			alerta = (iniciativa.getAlertaZonaAmarilla());
	    	}
	    	indicador.setAlertaMetaZonaAmarilla(alerta);
	    }
	        
	    SerieIndicador serieReal = setSeriesTiempo(indicador, editarActividadForm);
		if (pryActividad.getNaturaleza().equals(Naturaleza.getNaturalezaFormula()))
			setDefinicionFormula(indicador, editarActividadForm, serieReal);
	    
		if (respuesta == VgcReturnCode.DB_OK)
			respuesta = strategosIndicadoresService.saveIndicador(indicador, getUsuarioConectado(request));
	    
	    if (respuesta == VgcReturnCode.DB_OK) 
	    {
	    	pryActividad.setIndicadorId(new Long(indicador.getIndicadorId()));
	    	pryActividad.setClaseId(new Long(indicador.getClaseId()));
	    }
	
	    if (respuesta == VgcReturnCode.DB_OK)
	    	respuesta = strategosPryActividadesService.saveActividad(pryActividad, getUsuarioConectado(request), true);
	    
	    // Setear la actividad padre a carga de medicion en el periodo si todas son del mismo tipo 
	    // o al periodo si todas son del mismo tipo, 
	    // y si no son del mismo tipo se setea en el periodo
	    boolean actualizarMedicion = false;
	    if (respuesta == VgcReturnCode.DB_OK && pryActividadPadre != null && pryActividadPadre.getTipoMedicion().byteValue() != pryActividad.getTipoMedicion().byteValue())
	    {
	    	respuesta = setTipoMedicionPadre(pryActividad.getPadreId(), pryActividad.getTipoMedicion(), getUsuarioConectado(request));
    		if (respuesta == VgcReturnCode.DB_OK)
    			actualizarMedicion = true;
	    }
	    
	    if (respuesta == VgcReturnCode.DB_OK)
	    {
    		respuesta = setTipoMedicionIniciativa(pryActividad.getActividadId(), iniciativa, getUsuarioConectado(request));
    		if (respuesta == VgcReturnCode.DB_OK)
    			actualizarMedicion = true;
	    }

    	if (respuesta == VgcReturnCode.DB_OK && !editarActividadForm.getEliminarMediciones() && actualizarMedicion)
    		respuesta = new com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction().CalcularPadre(pryActividad, iniciativa.getIniciativaId(), request);
	    
		if (respuesta == VgcReturnCode.DB_OK && editarActividadForm.getEliminarMediciones())
		{
			StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(strategosIndicadoresService);

			respuesta = strategosMedicionesService.deleteMediciones(indicador.getIndicadorId());
			strategosMedicionesService.close();
			try 
			{
				respuesta = strategosIndicadoresService.actualizarDatosIndicador(indicador.getIndicadorId(), null, null, null);
			} 
			catch (Throwable e) 
			{
				respuesta = VgcReturnCode.DB_PK_AK_VIOLATED;
			}
			
			if (respuesta == VgcReturnCode.DB_OK)
				respuesta = new com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction().CalcularPadre(pryActividad, iniciativa.getIniciativaId(), request);
		}
	    strategosIndicadoresService.close();
	    
	    if (respuesta == VgcReturnCode.DB_OK)
	    	respuesta = new com.visiongc.app.strategos.web.struts.iniciativas.actions.GuardarIniciativaAction().actualizarActividades(false, iniciativa, getUsuarioConectado(request), strategosIniciativasService);
    	strategosIniciativasService.close();
    	
		if (respuesta == VgcReturnCode.DB_OK) //Calcular Fechas Padres
			respuesta = new com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction().CalcularFechasPadres(pryActividad.getPadreId(), iniciativa.getProyectoId(), getUsuarioConectado(request));
		
	    if (respuesta == VgcReturnCode.DB_OK) 
	    {
	    	strategosPryActividadesService.unlockObject(request.getSession().getId(), editarActividadForm.getActividadId());
	    	forward = "modificarActividad";
	
	      if (nuevo)
	      {
	    	  messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
	    	  forward = "crearActividad";
	      }
	      else 
	    	  messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
	    }
	    else if (respuesta == VgcReturnCode.DB_PK_AK_VIOLATED)
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
	
	    strategosPryActividadesService.close();
	
	    saveMessages(request, messages);
	    
	    if (respuesta == VgcReturnCode.DB_OK)
	    {
	    	editarActividadForm.setStatus(StatusUtil.getStatusSuccess());
	    	if (forward.equals("modificarActividad"))
	    	{
	    		forward = "crearActividad";
	    		editarActividadForm.setStatus(StatusUtil.getStatusSuccessModify());
	    	}
	    }
	    else
	    	editarActividadForm.setStatus(StatusUtil.getStatusInvalido());
	
	    return mapping.findForward(forward);
	}
	  
	private void setDefinicionFormula(Indicador indicador, EditarActividadForm editarActividadForm, SerieIndicador serieReal)
	{
	    Formula formulaIndicador = new Formula();
	    formulaIndicador.setInsumos(new HashSet<Object>());
	    formulaIndicador.setExpresion(IndicadorValidator.reemplazarCorrelativosFormula(editarActividadForm.getFormula(), editarActividadForm.getInsumosFormula()));
	
	    if ((editarActividadForm.getInsumosFormula() != null) && (!editarActividadForm.getInsumosFormula().equals(""))) 
	    {
	    	String[] insumos = editarActividadForm.getInsumosFormula().split(editarActividadForm.getSeparadorIndicadores());
	    	String[] strInsumo = (String[])null;
	    	for (int i = 0; i < insumos.length; i++) 
	    	{
	    		if (insumos[i].length() > 0) 
	    		{
	    			strInsumo = insumos[i].split("\\]\\[");
	    			InsumoFormula insumoFormula = new InsumoFormula();
	    			insumoFormula.setPk(new InsumoFormulaPK());
	    			insumoFormula.getPk().setPadreId(editarActividadForm.getIndicadorId());
	    			insumoFormula.getPk().setSerieId(new Long("0"));
	    			insumoFormula.getPk().setIndicadorId(new Long(strInsumo[1].substring("indicadorId:".length())));
	    			insumoFormula.getPk().setInsumoSerieId(new Long(strInsumo[2].substring("serieId:".length())));
	    			formulaIndicador.getInsumos().add(insumoFormula);
	    		}
	    	}
	    }
	    
	    serieReal.getFormulas().add(formulaIndicador);
	}
  
	private SerieIndicador setSeriesTiempo(Indicador indicador, EditarActividadForm editarActividadForm)
	{
		SerieIndicador serieReal = null;

		String seriesIndicador = editarActividadForm.getSeriesIndicador();
		indicador.getSeriesIndicador().clear();
		if ((seriesIndicador != null) && (!seriesIndicador.equals("")))
		{
			String[] series = StringUtil.split(editarActividadForm.getSeriesIndicador(), editarActividadForm.getSeparadorSeries());
			
			for (int i = 0; i < series.length; i++) 
			{
				String serie = series[i];
				if ((serie != null) && (!serie.equals(""))) 
				{
					SerieIndicador serieIndicador = new SerieIndicador();
					serieIndicador.setIndicador(indicador);
					serieIndicador.setPk(new SerieIndicadorPK());
					serieIndicador.getPk().setSerieId(new Long(serie));
					serieIndicador.getPk().setIndicadorId(indicador.getIndicadorId());
					serieIndicador.setFormulas(new HashSet<Object>());
					if (serieIndicador.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue()) 
					{
						serieIndicador.setNaturaleza(editarActividadForm.getNaturaleza());
						serieReal = serieIndicador;
					} 
					else 
						serieIndicador.setNaturaleza(Naturaleza.getNaturalezaSimple());
					
					indicador.getSeriesIndicador().add(serieIndicador);
				}
			}
			
			StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();
	    	UnidadMedida unidad = strategosUnidadesService.getUnidadMedidaPorcentaje();
	    	strategosUnidadesService.close();
			
			if (unidad != null && editarActividadForm.getUnidadId().longValue() != unidad.getUnidadId().longValue())
			{
				boolean haySeriePorcentaje = false;
				for (Iterator<SerieIndicador> iter = indicador.getSeriesIndicador().iterator(); iter.hasNext(); )
				{
					SerieIndicador serie = iter.next();
					if (serie.getPk().getSerieId().longValue() == unidad.getUnidadId().longValue())
					{
						haySeriePorcentaje = true;
						break;
					}
				}
				
				if (!haySeriePorcentaje)
				{
					SerieIndicador serieIndicador = new SerieIndicador();
					serieIndicador.setIndicador(indicador);
					serieIndicador.setPk(new SerieIndicadorPK());
					serieIndicador.getPk().setSerieId(new Long(SerieTiempo.getSeriePorcentajeReal().getSerieId()));
					serieIndicador.getPk().setIndicadorId(indicador.getIndicadorId());
					serieIndicador.setFormulas(new HashSet<Object>());
					serieIndicador.setNaturaleza(Naturaleza.getNaturalezaSimple());
					
					indicador.getSeriesIndicador().add(serieIndicador);

					serieIndicador = new SerieIndicador();
					serieIndicador.setIndicador(indicador);
					serieIndicador.setPk(new SerieIndicadorPK());
					serieIndicador.getPk().setSerieId(new Long(SerieTiempo.getSeriePorcentajeProgramado().getSerieId()));
					serieIndicador.getPk().setIndicadorId(indicador.getIndicadorId());
					serieIndicador.setFormulas(new HashSet<Object>());
					serieIndicador.setNaturaleza(Naturaleza.getNaturalezaSimple());
					
					indicador.getSeriesIndicador().add(serieIndicador);
				}
			}
		}

		return serieReal;
	}
	
	private int setTipoMedicionPadre(Long actividadPadreId, Byte tipoMedicion, Usuario usuario)
	{
		int respuesta = VgcReturnCode.S_OK;
		
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
    	PryActividad actividadPadre = (PryActividad)strategosPryActividadesService.load(PryActividad.class, new Long(actividadPadreId));
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		  
		filtros.put("proyectoId", actividadPadre.getProyectoId().toString());
		filtros.put("padreId", actividadPadre.getActividadId());
		
		String atributoOrden = "fila";
		String tipoOrden = "ASC";
		int pagina = 1;
		PaginaLista paginaActividades = strategosPryActividadesService.getActividades(pagina, 30, atributoOrden, tipoOrden, true, filtros);
		boolean tipoMedicionDiferente = false;
		
		if (paginaActividades.getLista().size() > 0)
		{
			for (Iterator<PryActividad> iter = paginaActividades.getLista().iterator(); iter.hasNext(); ) 
			{
				PryActividad actividad = iter.next();
				if (actividad.getTipoMedicion().byteValue() != tipoMedicion.byteValue())
				{
					tipoMedicionDiferente = true;
					break;
				}
			}
		}
		
		if (tipoMedicionDiferente)
			actividadPadre.setTipoMedicion(TipoMedicion.getTipoMedicionEnPeriodo());
		else
			actividadPadre.setTipoMedicion(tipoMedicion);
    	respuesta = strategosPryActividadesService.saveActividad(actividadPadre, usuario, false);
    	
    	if (respuesta == VgcReturnCode.DB_OK)
    	{
        	StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
        	Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(actividadPadre.getIndicadorId()));
        	indicador.setTipoCargaMedicion(actividadPadre.getTipoMedicion());
			respuesta = strategosIndicadoresService.saveIndicador(indicador, usuario);
        	strategosIndicadoresService.close();
    	}
    	
    	if (respuesta == VgcReturnCode.DB_OK)
    	{
        	PryActividad pryActividadPadre = null;
        	if (actividadPadre.getPadreId() != null)
        	{
        		pryActividadPadre = (PryActividad)strategosPryActividadesService.load(PryActividad.class, new Long(actividadPadre.getPadreId()));
        		respuesta = setTipoMedicionPadre(pryActividadPadre.getActividadId(), actividadPadre.getTipoMedicion(), usuario);
        	}
    	}
    	strategosPryActividadesService.close();
    	
		return respuesta;
	}
	
	private int setTipoMedicionIniciativa(Long actividadId, Iniciativa iniciativa, Usuario usuario)
	{
		int respuesta = VgcReturnCode.DB_OK;

		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		PryActividad actividad = (PryActividad)strategosPryActividadesService.load(PryActividad.class, new Long(actividadId));
		Map<String, Object> filtros = new HashMap<String, Object>();
		
		String atributoOrden = "fila";
		String tipoOrden = "ASC";
		int pagina = 1;
		filtros.put("proyectoId", actividad.getProyectoId());
		filtros.put("padreId", null);
		
		PaginaLista paginaActividades = strategosPryActividadesService.getActividades(pagina, 30, atributoOrden, tipoOrden, true, filtros);
		boolean tipoMedicionDiferente = false;
		
		if (paginaActividades.getLista().size() > 0)
		{
			for (Iterator<PryActividad> iter = paginaActividades.getLista().iterator(); iter.hasNext(); ) 
			{
				PryActividad act = iter.next();
				if (act.getTipoMedicion().byteValue() != actividad.getTipoMedicion().byteValue())
				{
					tipoMedicionDiferente = true;
					break;
				}
			}
		}
		
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento())));
		if (indicador != null && actividad.getTipoMedicion().byteValue() != indicador.getTipoCargaMedicion().byteValue())
		{
    		boolean updateIndicador = true;
    		if (tipoMedicionDiferente && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
    			updateIndicador = false;
    			
			if (tipoMedicionDiferente)
    			indicador.setTipoCargaMedicion(TipoMedicion.getTipoMedicionEnPeriodo());
    		else
    			indicador.setTipoCargaMedicion(actividad.getTipoMedicion());

			respuesta = strategosIndicadoresService.saveIndicador(indicador, usuario);
			
			if (updateIndicador && respuesta == VgcReturnCode.DB_OK)
			{
				StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
				//Eliminamos todas las mediciones ya que van a ser recalculadas
				respuesta = strategosMedicionesService.deleteMediciones(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficacia()));
				if (respuesta == VgcReturnCode.DB_OK)
					respuesta = strategosMedicionesService.deleteMediciones(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficiencia()));
				strategosMedicionesService.close();
				
				StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
				
				ConfiguracionIniciativa configuracionIniciativa = strategosIniciativasService.getConfiguracionIniciativa();
				if (respuesta == VgcReturnCode.DB_OK)
					respuesta = strategosIniciativasService.updateIndicadorAutomatico(iniciativa, TipoFuncionIndicador.getTipoFuncionEficacia(), configuracionIniciativa, usuario);
				if (respuesta == VgcReturnCode.DB_OK)
					respuesta = strategosIniciativasService.updateIndicadorAutomatico(iniciativa, TipoFuncionIndicador.getTipoFuncionEficiencia(), configuracionIniciativa, usuario);
				strategosIniciativasService.close();
			}
		}
		strategosIndicadoresService.close();
		strategosPryActividadesService.close();
		
		return respuesta;
	}
}
