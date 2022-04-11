/**
 * 
 */
package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import java.util.ArrayList;
import java.util.Date;
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

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryInformacionActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.util.NaturalezaActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class AsociarIniciativaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

	    ActionMessages messages = getMessages(request);
	
	    Long iniciativaId = (request.getParameter("iniciativaId") != null ? Long.parseLong(request.getParameter("iniciativaId")) : 0L);
	    Long proyectoId = (request.getParameter("proyectoId") != null ? Long.parseLong(request.getParameter("proyectoId")) : 0L);
	    Long iniciativaAsociadaId = (request.getParameter("iniciativaAsociadaId") != null ? Long.parseLong(request.getParameter("iniciativaAsociadaId")) : 0L);
	    Long seleccionados = (request.getParameter("seleccionados") != null ? Long.parseLong(request.getParameter("seleccionados")) : 0L);
	    String iniciativaAsociadaNombre = request.getParameter("iniciativaAsociadaNombre");
	    String className = request.getParameter("className");
	    
	    StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
	    Iniciativa iniciativa = (Iniciativa)strategosPryActividadesService.load(Iniciativa.class, iniciativaId);
	    Iniciativa iniciativaAsociada = (Iniciativa)strategosPryActividadesService.load(Iniciativa.class, iniciativaAsociadaId);
	    
	    //Chequear si ya la Iniciativa esta asociada
	    Boolean found = getRecursividad(strategosPryActividadesService, iniciativa, iniciativaAsociadaId, className);;

	    //Chequear recursividad
	    if (!found)
	    	found = getRecursividad(strategosPryActividadesService, iniciativaAsociada, iniciativaId, className);
	    
	    if (found)
	    {
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.circular.actividad.iniciativa", iniciativaAsociadaNombre));
	    	saveMessages(request, messages);
	    	strategosPryActividadesService.close();
	    	
	    	return getForwardBack(request, 1, true);
	    }

		Map<String, Comparable> filtros = new HashMap<String, Comparable>();
		List<PryActividad> actividades = new ArrayList<PryActividad>();
	    int respuesta = 10000;
	    Date comienzoPlan = null;
	    Date finPlan = null;
	    Date comienzoReal = null;
	    Date finReal = null;
		if (iniciativaAsociada.getProyectoId() != null)
		{
			filtros = new HashMap<String, Comparable>();
			filtros.put("proyectoId", iniciativaAsociada.getProyectoId().toString());
			
			String atributoOrden = "fila";
			String tipoOrden = "ASC";
			int pagina = 0;
			actividades = strategosPryActividadesService.getActividades(pagina, 0, atributoOrden, tipoOrden, false, filtros).getLista();
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) 
			{
				PryActividad actividad = (PryActividad)iter.next();
				if (comienzoPlan == null)
					comienzoPlan = actividad.getComienzoPlan();
				if (finPlan == null)
					finPlan = actividad.getFinPlan();
				if (comienzoReal == null)
					comienzoReal = actividad.getComienzoReal();
				if (finReal == null)
					finReal = actividad.getFinReal();
				
				if (actividad.getComienzoPlan() != null && actividad.getComienzoPlan().before(comienzoPlan))
					comienzoPlan = actividad.getComienzoPlan();
				if (actividad.getFinPlan() != null && actividad.getFinPlan().after(finPlan))
					finPlan = actividad.getFinPlan();
				if (actividad.getComienzoReal() != null && actividad.getComienzoReal().before(comienzoReal))
					comienzoReal = actividad.getComienzoReal();
				if (actividad.getFinReal() != null && actividad.getFinReal().after(finReal))
					finReal = actividad.getFinReal();
			}			
		}

	    PryActividad pryActividad = new PryActividad();
	    pryActividad.setActividadId(new Long(0L));
	    pryActividad.setPryInformacionActividad(new PryInformacionActividad());
	
	    int filaNuevaActividad = 1;
	    int nivelNuevaActividad = 1;
	    Long padreId = null;
	
	    if ((seleccionados != null) && (seleccionados.longValue() != 0L)) 
    	{
    		filaNuevaActividad = ((Integer)strategosPryActividadesService.getValoresLimiteAlcanceHijosActividad(seleccionados.longValue(), new Boolean(false), (Usuario)request.getSession().getAttribute("usuario")).get(0)).intValue();
    		PryActividad actividadBase = (PryActividad)strategosPryActividadesService.load(PryActividad.class, seleccionados);
    		nivelNuevaActividad = actividadBase.getNivel().intValue();
    		if (actividadBase.getPadre() != null)
    			padreId = actividadBase.getPadre().getActividadId();
    		else 
    			padreId = null;
    	}
    	else
    		filaNuevaActividad = strategosPryActividadesService.getMaximaFila(proyectoId, (Usuario)request.getSession().getAttribute("usuario"));
	    	
    	filaNuevaActividad++;
	
    	pryActividad.setPadreId(padreId);
    	pryActividad.setFila(new Integer(filaNuevaActividad));
    	pryActividad.setNivel(new Integer(nivelNuevaActividad));
    	pryActividad.setProyectoId(proyectoId);
    	
	    StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService(strategosPryActividadesService);
	    UnidadMedida unidad = strategosUnidadesService.getUnidadMedidaPorcentaje();
	    strategosUnidadesService.close();
	    
	    pryActividad.setUnidadId(unidad.getUnidadId());
	    pryActividad.setNaturaleza(NaturalezaActividad.getNaturalezaAsociado());
	
	    if (iniciativaAsociada.getNombre() != null)
	    	pryActividad.setNombre(iniciativaAsociada.getNombre());
	    else 
	    	pryActividad.setNombre(null);
	
	    if ((iniciativaAsociada.getMemoIniciativa() != null) && (iniciativaAsociada.getMemoIniciativa().getDescripcion() != null) && (!iniciativaAsociada.getMemoIniciativa().getDescripcion().equals("")))
	    	pryActividad.setDescripcion(iniciativaAsociada.getMemoIniciativa().getDescripcion());
	    else 
	    	pryActividad.setDescripcion(null);

	    if (comienzoPlan != null)
	    	pryActividad.setComienzoPlan(comienzoPlan);
	    else 
	    	pryActividad.setComienzoPlan(null);
	
	    if (finPlan != null)
	    	pryActividad.setFinPlan(finPlan);
	    else 
	    	pryActividad.setFinPlan(null);

	    if (comienzoReal != null)
	    	pryActividad.setComienzoReal(comienzoReal);
	    else 
	    	pryActividad.setComienzoReal(null);
	
	    if (finReal != null)
	    	pryActividad.setFinReal(finReal);
	    else 
	    	pryActividad.setFinReal(null);
	    
	    pryActividad.setDuracionPlan(null);
	
	    if (iniciativaAsociada.getResponsableFijarMetaId() != null && iniciativaAsociada.getResponsableFijarMetaId().equals(new Long(0L)))
	    	pryActividad.setResponsableFijarMetaId(null);
	    else 
	    	pryActividad.setResponsableFijarMetaId(iniciativaAsociada.getResponsableFijarMetaId());
	
	    if (iniciativaAsociada.getResponsableLograrMetaId() != null && iniciativaAsociada.getResponsableLograrMetaId().equals(new Long(0L)))
	    	pryActividad.setResponsableLograrMetaId(null);
	    else 
	    	pryActividad.setResponsableLograrMetaId(iniciativaAsociada.getResponsableLograrMetaId());
	
	    if (iniciativaAsociada.getResponsableSeguimientoId() != null && iniciativaAsociada.getResponsableSeguimientoId().equals(new Long(0L)))
	    	pryActividad.setResponsableSeguimientoId(null);
	    else 
	    	pryActividad.setResponsableSeguimientoId(iniciativaAsociada.getResponsableSeguimientoId());
	
	    if (iniciativaAsociada.getResponsableCargarMetaId() != null && iniciativaAsociada.getResponsableCargarMetaId().equals(new Long(0L)))
	    	pryActividad.setResponsableCargarMetaId(null);
	    else 
	    	pryActividad.setResponsableCargarMetaId(iniciativaAsociada.getResponsableCargarMetaId());
	
	    if (iniciativaAsociada.getResponsableCargarEjecutadoId() != null && iniciativaAsociada.getResponsableCargarEjecutadoId().equals(new Long(0L)))
	    	pryActividad.setResponsableCargarEjecutadoId(null);
	    else 
	    	pryActividad.setResponsableCargarEjecutadoId(iniciativaAsociada.getResponsableCargarEjecutadoId());
	
    	pryActividad.getPryInformacionActividad().setProductoEsperado(null);
    	pryActividad.getPryInformacionActividad().setRecursosHumanos(null);
    	pryActividad.getPryInformacionActividad().setRecursosMateriales(null);
	
	    if (iniciativaAsociada.getAlertaZonaVerde() != null)
	    	pryActividad.getPryInformacionActividad().setPorcentajeVerde(iniciativaAsociada.getAlertaZonaVerde());
	    else 
	    	pryActividad.getPryInformacionActividad().setPorcentajeVerde(null);
	
	    if (iniciativaAsociada.getAlertaZonaAmarilla() != null)
	    	pryActividad.getPryInformacionActividad().setPorcentajeAmarillo(iniciativaAsociada.getAlertaZonaAmarilla());
	    else 
	    	pryActividad.getPryInformacionActividad().setPorcentajeAmarillo(null);

	    if (iniciativaAsociada.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()) == null || iniciativaAsociada.getClaseId() == null)
	    {
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.circular.actividad.iniciativa.sinindicador", iniciativaAsociadaNombre));
	    	saveMessages(request, messages);
	    	strategosPryActividadesService.close();
	    	
	    	return getForwardBack(request, 1, true);
	    }

    	pryActividad.setIndicadorId(new Long(iniciativaAsociada.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento())));
    	pryActividad.setClaseId(new Long(iniciativaAsociada.getClaseId()));
    	pryActividad.setObjetoAsociadoClassName(className);
    	pryActividad.setObjetoAsociadoId(iniciativaAsociadaId);
	
    	StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    	StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(pryActividad.getIndicadorId()));
		if (indicador != null)
		{
			pryActividad.setFechaUltimaMedicion(indicador.getFechaUltimaMedicion());
		    pryActividad.setTipoMedicion(indicador.getTipoCargaMedicion() != null ? indicador.getTipoCargaMedicion() : TipoMedicion.getTipoMedicionEnPeriodo());
		}
		else
			pryActividad.setTipoMedicion(TipoMedicion.getTipoMedicionEnPeriodo());

		Medicion medicionAlerta = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieAlerta(), indicador.getValorInicialCero(), TipoCorte.getTipoCorteTransversal(), indicador.getTipoCargaMedicion());
		if (medicionAlerta != null) 
			pryActividad.setAlerta(AlertaIndicador.ConvertDoubleToByte(medicionAlerta.getValor()));
	    			
		Double totalReal = null;
		Double totalProgramado = null;
		Double ultimaMedicionReal = null;
  		if (pryActividad.getTipoMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
  		{
  		  	List<Medicion> medicionesReales = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
  	  		List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
  		  	for (Iterator<Medicion> iterMediciones = medicionesReales.iterator(); iterMediciones.hasNext(); ) 
  		  	{
  		  		Medicion medicion = (Medicion)iterMediciones.next();
  		  		if (medicion.getValor() != null && totalReal == null)
  		  			totalReal = 0D;
  		  		totalReal = totalReal + medicion.getValor();
	  			if (medicion.getValor() != null)
	  				ultimaMedicionReal = medicion.getValor();
  			  	for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada.iterator(); iterMedicionesProgramadas.hasNext(); ) 
  			  	{
  			  		Medicion medicionProgramada = (Medicion)iterMedicionesProgramadas.next();
  			  		if (medicion.getMedicionId().getAno().intValue() == medicionProgramada.getMedicionId().getAno().intValue() &&
  			  			medicion.getMedicionId().getPeriodo().intValue() == medicionProgramada.getMedicionId().getPeriodo().intValue())
  			  		{
  			  			if (medicionProgramada.getValor() != null && totalProgramado == null)
  			  				totalProgramado = 0D;
  			  			totalProgramado = totalProgramado + medicionProgramada.getValor();
  			  			break;
  			  		}
  		  		}
  		  	}
  		}
  		else
  		{
	  		Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), TipoCorte.getTipoCorteTransversal(), indicador.getTipoCargaMedicion());
	  		totalReal = medicionReal != null ? medicionReal.getValor() : null;
	  		if (medicionReal != null && medicionReal.getValor() != null)
  				ultimaMedicionReal = medicionReal.getValor();

	  		if (totalReal != null)
	  		{
		  		List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
  			  	for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada.iterator(); iterMedicionesProgramadas.hasNext(); ) 
  			  	{
  			  		Medicion medProgramada = (Medicion)iterMedicionesProgramadas.next();
  			  		if (medProgramada.getMedicionId().getAno().intValue() <= medicionReal.getMedicionId().getAno().intValue() &&
  			  			medProgramada.getMedicionId().getPeriodo().intValue() <= medicionReal.getMedicionId().getPeriodo().intValue())
  			  			totalProgramado = medProgramada.getValor();
		  		}
	  		}
  		}
	    	
  		pryActividad.setPorcentajeCompletado(ultimaMedicionReal);
	  	pryActividad.setPorcentajeEjecutado(totalReal);
	  	pryActividad.setPorcentajeEsperado(totalProgramado);
	  				
	    respuesta = strategosPryActividadesService.saveActividad(pryActividad, getUsuarioConectado(request), false);

	    if (respuesta == 10000)
	    	respuesta = new com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction().CalcularFechasPadres(padreId, proyectoId, getUsuarioConectado(request));
	    if (respuesta == 10000  && pryActividad.getActividadId() != null && iniciativa != null && iniciativa.getIniciativaId() != null)
	    	respuesta = new com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction().CalcularPadre(pryActividad, iniciativa.getIniciativaId(), request);
	    
	    if (respuesta == 10000) 
    		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
	    else if (respuesta == 10003)
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
	
	    strategosPryActividadesService.close();
	
	    saveMessages(request, messages);
	
	    return getForwardBack(request, 1, true);
	}
	  
	private Boolean getRecursividad(StrategosPryActividadesService strategosPryActividadesService, Iniciativa iniciativa, Long iniciativaId, String className)
	{
		boolean found = false;
		
	    //Chequear si ya la Iniciativa esta asociada
	    List<PryActividad> actividades = new ArrayList<PryActividad>();
	    if (iniciativa.getProyectoId() != null)
	    	actividades = strategosPryActividadesService.getObjetoAsociados(iniciativa.getProyectoId(), className);
	    for (Iterator<PryActividad> i = actividades.iterator(); i.hasNext(); )
	    {
	    	PryActividad actividad = (PryActividad)i.next();
	    	if (actividad.getObjetoAsociadoId().longValue() == iniciativaId.longValue())
	    	{
	    		found = true;
	    		break;
	    	}
	    	else
	    	{
	    		Iniciativa iniciativaHija = (Iniciativa)strategosPryActividadesService.load(Iniciativa.class, actividad.getObjetoAsociadoId());
	    		found = getRecursividad(strategosPryActividadesService, iniciativaHija, iniciativaId, className);
	    		if (found)
	    			break;
	    	}
	    }
		
		return found;
	}
}