package com.visiongc.app.strategos.web.struts.mediciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadorAsignarInventarioService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.IndicadorAsignarInventario;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm.TipoCargaActividad;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.auditoria.AuditoriaMedicionService;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class GuardarMedicionesAction
  extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    String forward = mapping.getParameter();
    
    EditarMedicionesForm editarMedicionesForm = (EditarMedicionesForm)form;
    
    ActionMessages messages = getMessages(request);
    
    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarMedicionesAction.ultimoTs");
    if ((ts == null) || (ts.equals(""))) {
      cancelar = true;
    } else if ((ultimoTs != null) && (ultimoTs.equals(ts))) {
      cancelar = true;
    }
    boolean desdePlanificacion = editarMedicionesForm.getDesdePlanificacion().booleanValue();
    
    StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
    if (cancelar)
    {
      destruirPoolLocksUsoEdicion(request, strategosMedicionesService);
      
      strategosMedicionesService.close();
      
      return getForwardBack(request, 1, true);
    }
    List<Indicador> indicadores = new ArrayList<Indicador>();
    List<Medicion> medicionesEditadas = new ArrayList();
    Map<?, ?> mapaParametros = request.getParameterMap();
    
    List<PryActividad> actividades = new ArrayList();
    Boolean hayActividad = Boolean.valueOf(false);
    Boolean cargaActividadReal = Boolean.valueOf(false);
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    StrategosIndicadorAsignarInventarioService strategosInsumoService= StrategosServiceFactory.getInstance().openStrategosIndicadorAsignarInventarioService();
    StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
    Integer anoMaximo = Integer.valueOf(0);
    Integer periodoMaximo = Integer.valueOf(0);
    String serieId = null;
    String indicadorId = null;
    Indicador indicador = null;
    for (Iterator<?> iter = mapaParametros.keySet().iterator(); iter.hasNext();)
    {
      String nombreParametro = (String)iter.next();
      String fechaInicial = null;
      String fechaFinal = null;
      if (nombreParametro.indexOf("valorIndId") == 0)
      {
        String valorNuevo = ((String[])mapaParametros.get(nombreParametro))[0];
        String valorViejo = "";
        if (nombreParametro.indexOf("anterior") == -1) {
          valorViejo = ((String[])mapaParametros.get("anterior" + nombreParametro))[0];
        }
        serieId = null;
        indicadorId = null;
        if (!valorNuevo.equals(valorViejo))
        {
          nombreParametro = nombreParametro.replaceAll("\t", "").replaceAll("\r\n", "");
          int indice1 = 10;
          int indice2 = nombreParametro.indexOf("serId");
          indicadorId = nombreParametro.substring(indice1, indice2).trim();
          indice1 = indice2 + 5;
          indice2 = nombreParametro.indexOf("periodo");
          serieId = nombreParametro.substring(indice1, indice2).trim();
          indice1 = indice2 + 7;
          indice2 = nombreParametro.indexOf("ano");
          Integer periodo = Integer.valueOf(Integer.parseInt(nombreParametro.substring(indice1, indice2).trim()));
          Integer ano = Integer.valueOf(Integer.parseInt(nombreParametro.substring(indice2 + 3).trim()));
          if (ano.intValue() > anoMaximo.intValue()) {
            anoMaximo = ano;
          }
          if (periodo.intValue() > periodoMaximo.intValue()) {
            periodoMaximo = periodo;
          }
          if ((desdePlanificacion) && (editarMedicionesForm.getTipoCargaDesdePlanificacion().byteValue() == EditarMedicionesForm.TipoCargaActividad.getTipoCargaActividadReal().byteValue()))
          {
            fechaInicial = ((String[])mapaParametros.get("fechaRealInicioIndId_" + indicadorId))[0];
            if (fechaInicial.equals("")) {
              fechaInicial = null;
            }
            fechaFinal = ((String[])mapaParametros.get("fechaRealFinIndId_" + indicadorId))[0];
            if (fechaFinal.equals("")) {
              fechaFinal = null;
            }
          }
          Double valor = null;
          indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(indicadorId));
          
          if (!indicadores.contains(indicador)) {
		       indicadores.add(indicador);
		  }
          
      
          
          if (((!valorNuevo.equals("")) && (!indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoNominal()))) || (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoOrdinal()))) {
            valor = new Double(VgcFormatter.parsearNumeroFormateado(valorNuevo));
          } else if ((valorNuevo != null) && (!valorNuevo.equals(""))) {
            valor = Double.valueOf(Double.parseDouble(valorNuevo));
          }
          Medicion medicionEditada = new Medicion(new MedicionPK(new Long(indicadorId), new Integer(ano.intValue()), new Integer(periodo.intValue()), new Long(serieId)), valor, new Boolean(false));
          medicionesEditadas.add(medicionEditada);
        }
        if ((desdePlanificacion) && (serieId != null) && (indicadorId != null))
        {
          hayActividad = Boolean.valueOf(true);
          PryActividad act = strategosPryActividadesService.getActividadByIndicador(new Long(indicadorId).longValue());
          if (act != null)
          {
            act.setIndicadorId(new Long(indicadorId));
            if (new Long(serieId).longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
            {
              cargaActividadReal = Boolean.valueOf(true);
              SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
              Calendar calFechaDesde = Calendar.getInstance();
              if (fechaInicial != null)
              {
                calFechaDesde.setTime(simpleDateFormat.parse(fechaInicial));
                calFechaDesde = PeriodoUtil.inicioDelDia(calFechaDesde);
                act.setComienzoReal(calFechaDesde.getTime());
              }
              Calendar calFechaHasta = Calendar.getInstance();
              if (fechaFinal != null)
              {
                calFechaHasta.setTime(simpleDateFormat.parse(fechaFinal));
                calFechaHasta = PeriodoUtil.finDelDia(calFechaHasta);
                act.setFinReal(calFechaHasta.getTime());
              }
            }
            
            if(!existeActividad(actividades, act)){
            	actividades.add(act);
            }
            /*
            if (!actividades.contains(act)) {
              actividades.add(act);
            }
            */
          }
        }
      }
    }
    strategosPryActividadesService.close();
    
    StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();
    UnidadMedida unidad = strategosUnidadesService.getUnidadMedidaPorcentaje();
    strategosUnidadesService.close();
    
    int respuesta = 10000;
    if ((desdePlanificacion) && (hayActividad.booleanValue())) {
      for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();)
      {
        PryActividad act = (PryActividad)iter.next();
        if ((indicador != null) && (unidad != null) && (act.getUnidadId().longValue() != unidad.getUnidadId().longValue()) && (medicionesEditadas.size() > 0))
        {
          Set<SerieIndicador> seriesIndicador = indicador.getSeriesIndicador();
          boolean haySeriePorcentaje = false;
          for (Iterator<?> i = seriesIndicador.iterator(); i.hasNext();)
          {
            SerieIndicador serie = (SerieIndicador)i.next();
            if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSeriePorcentajeReal().getSerieId().byteValue())
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
            serieIndicador.getPk().setSerieId(new Long(SerieTiempo.getSeriePorcentajeReal().getSerieId().longValue()));
            serieIndicador.getPk().setIndicadorId(indicador.getIndicadorId());
            serieIndicador.setFormulas(new HashSet());
            serieIndicador.setNaturaleza(Naturaleza.getNaturalezaSimple());
            indicador.getSeriesIndicador().add(serieIndicador);
            
            serieIndicador = new SerieIndicador();
            serieIndicador.setIndicador(indicador);
            serieIndicador.setPk(new SerieIndicadorPK());
            serieIndicador.getPk().setSerieId(new Long(SerieTiempo.getSeriePorcentajeProgramado().getSerieId().longValue()));
            serieIndicador.getPk().setIndicadorId(indicador.getIndicadorId());
            serieIndicador.setFormulas(new HashSet());
            serieIndicador.setNaturaleza(Naturaleza.getNaturalezaSimple());
            indicador.getSeriesIndicador().add(serieIndicador);
            
            respuesta = strategosIndicadoresService.saveIndicador(indicador, getUsuarioConectado(request));
          }
        }
      }
    }
    if ((respuesta == 10000) && (medicionesEditadas.size() > 0)) {
    	registrarAuditoriaMedicion(indicadores, strategosMedicionesService, strategosIndicadoresService, editarMedicionesForm, getUsuarioConectado(request), medicionesEditadas, request);
    	respuesta = strategosMedicionesService.saveMediciones(medicionesEditadas, editarMedicionesForm.getPlanId(), getUsuarioConectado(request), new Boolean(true), new Boolean(true));
      
    }
    if ((respuesta == 10000) && (medicionesEditadas.size() > 0)) {
      respuesta = strategosMedicionesService.actualizarSeriePorPeriodos(Integer.valueOf(Integer.parseInt(editarMedicionesForm.getAnoDesde())), Integer.valueOf(Integer.parseInt(editarMedicionesForm.getAnoHasta())), editarMedicionesForm.getPeriodoDesde(), editarMedicionesForm.getPeriodoHasta(), medicionesEditadas, Boolean.valueOf(false), Boolean.valueOf(desdePlanificacion), getUsuarioConectado(request));
    }
    if (respuesta == 10000)
    {
      destruirPoolLocksUsoEdicion(request, strategosMedicionesService);
      
      forward = "exito";
      
    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarmediciones.mensaje.guardarmediciones.related"));
    }
    else
    {
      destruirPoolLocksUsoEdicion(request, strategosMedicionesService);
      forward = "exito";
    }
    
    
    
    strategosMedicionesService.close();
    strategosIndicadoresService.close();
    if (respuesta == 10000) {
      if ((desdePlanificacion) && (hayActividad.booleanValue()))
      {
        strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
        strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
        
        String[] series = "0,1".split(",");
        List<Medicion> medicionesACalcular = new ArrayList();
        Double totalMedicion = null;
     
        for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext(); ) 
		{
			PryActividad act = iter.next();
			if (unidad != null && act.getUnidadId().longValue() != unidad.getUnidadId().longValue())
			{
				List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(act.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
  		  		totalMedicion = null;
	  			for (Iterator<Medicion> iterMedicion = mediciones.iterator(); iterMedicion.hasNext(); ) 
	  			{
	  				Medicion medicion = (Medicion)iterMedicion.next();
	  				if (medicion.getValor() != null)
	  				{
						if (totalMedicion != null)
							totalMedicion = totalMedicion + medicion.getValor();
	  					else
	  						totalMedicion = medicion.getValor();
	  				}
	  			}
			
				for (int iSerie = 0; iSerie < series.length; iSerie++)
				{
					Long serId = new Long(series[iSerie]);
					mediciones = strategosMedicionesService.getMedicionesPeriodo(act.getIndicadorId(), serId, new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
	  				Double valor = null;
		  			for (Iterator<Medicion> iterMedicion = mediciones.iterator(); iterMedicion.hasNext(); ) 
		  			{
		  				Medicion medicion = (Medicion)iterMedicion.next();
		  				valor = null;
		  				if (medicion.getValor() != null && totalMedicion != null && totalMedicion != 0D)
	  						valor = (medicion.getValor() * 100D) / totalMedicion;
		  				
		  				Long valorSerieId = null;
		  				if (medicion.getMedicionId().getSerieId().longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
		  					valorSerieId = SerieTiempo.getSeriePorcentajeReal().getSerieId();
		  				else
		  					valorSerieId = SerieTiempo.getSeriePorcentajeProgramado().getSerieId();
		  				
		  				Medicion medicionEditada = new Medicion(new MedicionPK(medicion.getMedicionId().getIndicadorId(), new Integer(medicion.getMedicionId().getAno()), new Integer(medicion.getMedicionId().getPeriodo()), valorSerieId), valor, new Boolean(false));
		  				medicionesACalcular.add(medicionEditada);
		  			}
				}
			}
		}
        
        if ((respuesta == 10000) && (medicionesACalcular.size() > 0)) {
          /*
          registrarAuditoriaMedicion(indicadores, strategosMedicionesService, strategosIndicadoresService, editarMedicionesForm, getUsuarioConectado(request), medicionesACalcular, request);	
          */
          respuesta = strategosMedicionesService.saveMediciones(medicionesACalcular, editarMedicionesForm.getPlanId(), getUsuarioConectado(request), new Boolean(true), new Boolean(true));
        }
        if ((respuesta == 10000) && (medicionesACalcular.size() > 0)) {
          respuesta = strategosMedicionesService.actualizarSeriePorPeriodos(Integer.valueOf(Integer.parseInt(editarMedicionesForm.getAnoDesde())), Integer.valueOf(Integer.parseInt(editarMedicionesForm.getAnoHasta())), editarMedicionesForm.getPeriodoDesde(), editarMedicionesForm.getPeriodoHasta(), medicionesACalcular, Boolean.valueOf(false), Boolean.valueOf(desdePlanificacion), getUsuarioConectado(request));
        }
        strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
        strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
        
        Long actividadId = null;
        Long proyectoId = null;
        Long padreId = null;
        StrategosPryActividadesService servicioActividad = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
        for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();)
        {
          PryActividad actividad = (PryActividad)iter.next();
          if (actividad != null)
          {
            if ((proyectoId == null) && (actividad.getProyectoId() != null)) {
              proyectoId = actividad.getProyectoId();
            }
            actividadId = actividad.getActividadId();
            padreId = actividad.getPadreId();
            
            indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(actividad.getIndicadorId().longValue()));
            if (indicador != null) {
              actividad.setFechaUltimaMedicion(indicador.getFechaUltimaMedicion());
            }
            Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), TipoCorte.getTipoCorteTransversal(), indicador.getTipoCargaMedicion());
            actividad.setAlerta(AlertaIndicador.getAlertaNoDefinible());
            if (medicionReal != null)
            {
              List<Medicion> medicionesAlertas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieAlerta(), medicionReal.getMedicionId().getAno(), medicionReal.getMedicionId().getAno(), medicionReal.getMedicionId().getPeriodo(), medicionReal.getMedicionId().getPeriodo());
              if ((medicionesAlertas.size() > 0) && (((Medicion)medicionesAlertas.get(0)).getValor() != null)) {
                actividad.setAlerta(AlertaIndicador.ConvertDoubleToByte(((Medicion)medicionesAlertas.get(0)).getValor()));
              }
            }
            Double totalReal = null;
            Double totalProgramado = null;
            Double ultimaMedicionReal = null;
            if (actividad.getTipoMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
            {
              List<Medicion> medicionesReales = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), new Integer(0), new Integer(9999), new Integer(0), new Integer(999));
              List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0), new Integer(9999), new Integer(0), new Integer(999));
              for (Iterator<Medicion> iterMediciones = medicionesReales.iterator(); iterMediciones.hasNext();)
              {
                Medicion medicion = (Medicion)iterMediciones.next();
                if ((medicion.getValor() != null) && (totalReal == null)) {
                  totalReal = Double.valueOf(0.0D);
                }
                totalReal = Double.valueOf(totalReal.doubleValue() + medicion.getValor().doubleValue());
                ultimaMedicionReal = totalReal;
                for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada.iterator(); iterMedicionesProgramadas.hasNext();)
                {
                  Medicion medicionProgramada = (Medicion)iterMedicionesProgramadas.next();
                  if ((medicion.getMedicionId().getAno().intValue() == medicionProgramada.getMedicionId().getAno().intValue()) && 
                    (medicion.getMedicionId().getPeriodo().intValue() == medicionProgramada.getMedicionId().getPeriodo().intValue()))
                  {
                    if ((medicionProgramada.getValor() != null) && (totalProgramado == null)) {
                      totalProgramado = Double.valueOf(0.0D);
                    }
                    totalProgramado = Double.valueOf(totalProgramado.doubleValue() + medicionProgramada.getValor().doubleValue());
                    break;
                  }
                }
              }
            }
            else
            {
              totalReal = medicionReal != null ? medicionReal.getValor() : null;
              if ((medicionReal != null) && (medicionReal.getValor() != null)) {
                ultimaMedicionReal = medicionReal.getValor();
              }
              if (totalReal != null)
              {
                List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0), new Integer(9999), new Integer(0), new Integer(999));
                DecimalFormat nf3 = new DecimalFormat("#000");
                int anoPeriodoBuscar = Integer.parseInt(medicionReal.getMedicionId().getAno().toString() + nf3.format(medicionReal.getMedicionId().getPeriodo()).toString());
                for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada.iterator(); iterMedicionesProgramadas.hasNext();)
                {
                  Medicion medProgramada = (Medicion)iterMedicionesProgramadas.next();
                  int anoPeriodo = Integer.parseInt(medProgramada.getMedicionId().getAno().toString() + nf3.format(medProgramada.getMedicionId().getPeriodo()).toString());
                  if (anoPeriodo <= anoPeriodoBuscar) {
                    totalProgramado = medProgramada.getValor();
                  }
                }
              }
            }
            actividad.setPorcentajeCompletado(ultimaMedicionReal);
            actividad.setPorcentajeEjecutado(totalReal);
            actividad.setPorcentajeEsperado(totalProgramado);
            
           
            
            
            respuesta = servicioActividad.saveActividad(actividad, getUsuarioConectado(request), Boolean.valueOf(false));
            if (respuesta != 10000) {
              break;
            }
          }
        }
       
        servicioActividad.close();
        strategosIndicadoresService.close();
        strategosMedicionesService.close();
        if (respuesta == 10000)
        {
          if (cargaActividadReal.booleanValue()) {
            respuesta = new CalcularActividadesAction().CalcularFechasPadres(padreId, proyectoId, getUsuarioConectado(request));
          }
          if ((respuesta == 10000) && (actividadId != null))
          {
            servicioActividad = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
            PryActividad actividad = (PryActividad)servicioActividad.load(PryActividad.class, new Long(actividadId.longValue()));
            servicioActividad.close();
            respuesta = new CalcularActividadesAction().CalcularPadre(actividad, editarMedicionesForm.getIniciativaId(), request);
          }
        }
        else if (respuesta == 10003)
        {
          messages.clear();
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
        }
        
        // validacion porcentaje mayor a 100 
        
        /*
        
        Long iniciativaId = editarMedicionesForm.getIniciativaId();
        
        if(iniciativaId != null ){
        
    		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
    		    
    		Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, iniciativaId);
    		     
    		calcularProcentaje(iniciativa, strategosIniciativasService, getUsuarioConectado(request));
    				    
    		strategosIniciativasService.close();
        }
        
        */
        
      }
    }
    
    
    
    
    if(medicionesEditadas.size() >0){
    	validarInventarioNegativo(indicadores, strategosInsumoService, strategosMedicionesService, strategosIndicadoresService, messages,  getUsuarioConectado(request), medicionesEditadas, request, editarMedicionesForm);
    }
    saveMessages(request, messages);
    if (forward.equals("exito"))
    {
      if (request.getSession().getAttribute("GuardarIndicador") == null) {
        request.getSession().setAttribute("GuardarIndicador", "true");
      }
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
  
  public void validarInventarioNegativo(List<Indicador> indicadores, StrategosIndicadorAsignarInventarioService strategosInsumoService, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService , ActionMessages messages,Usuario usuario, List<Medicion> medicionesEditadas, HttpServletRequest request,EditarMedicionesForm editarMedicionesform) throws Exception{
		boolean validarNegativo = false;
		int resultado = 10000;
		strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		
		List<Long> indicadoresId = new ArrayList();
		List<Indicador> indicadoresFormula = new ArrayList();
		Indicador indicador = null;
		
		/* validar insumos */
		
		for(Iterator<Indicador> iter = indicadores.iterator(); iter.hasNext(); ){
			Indicador ind = iter.next();
			
			validarNegativo = validarInsumoNegativo(indicadores, ind, editarMedicionesform, strategosInsumoService, strategosIndicadoresService, strategosMedicionesService);
			if(validarNegativo){
				IndicadorAsignarInventario insumo= strategosInsumoService.getInsumo(ind.getIndicadorId());
				indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(insumo.getIndicadorId()));
			       
				if (!indicadoresFormula.contains(indicador)) {
			       indicadoresFormula.add(indicador);
			    }
			}
		}		
		
		/* validar formulas */
	 
		if(indicadoresFormula.size() >0){
			Formula formula= new Formula();
			String signo ="-";
			int periodoDesde=editarMedicionesform.getPeriodoDesde();
			int periodoHasta=editarMedicionesform.getPeriodoHasta();
			String anoDesde=editarMedicionesform.getAnoDesde();
			String anoHasta=editarMedicionesform.getAnoHasta();
			int anoIni=Integer.parseInt(anoDesde);
			int anoFin=Integer.parseInt(anoHasta);
			
		    for(Iterator<Indicador> iter = indicadoresFormula.iterator(); iter.hasNext(); ){
				Indicador indicadorf = (Indicador) iter.next();
				String formulaCa = "";
				String formulaIndicadorA ="";
				String formulaIndicadorB ="";
				List<Long> indicadoresA = new ArrayList();
				List<Long> indicadoresB = new ArrayList();
				int intIndex = 0;
				int medicionA =0;
				int medicionB =0;
				int cantidad=0;
				List<Integer> medicionesA = new ArrayList();
				List<Integer> medicionesB = new ArrayList();
				List<String> periodos = new ArrayList<String>();
				
				
				List<IndicadorAsignarInventario> insumosFormula= new ArrayList<IndicadorAsignarInventario>();
				insumosFormula = strategosInsumoService.getIndicadorInventario(indicadorf.getIndicadorId());
				
				formula = strategosIndicadoresService.getFormulaIndicador(indicadorf.getIndicadorId(), new Long(0));
				formulaCa = formula.getExpresion();
				intIndex = formulaCa.indexOf(signo);
				
				formulaIndicadorA=formulaCa.substring(0, intIndex);
				formulaIndicadorB=formulaCa.substring(intIndex+1, formulaCa.length());
				Boolean medicionNulas=false;
				List<String> cadenaPeriodos= getListaPeriodos(anoIni, anoFin, periodoDesde, periodoHasta);
				int cantidadPeriodos=cadenaPeriodos.size();
				
				for(int y=0; y< cantidadPeriodos; y++){
					String cadena=cadenaPeriodos.get(y);
					int ano=0;
					int periodoE=0;
					int index = cadena.indexOf(signo);
					
					ano=Integer.parseInt(cadena.substring(0, index));
					periodoE=Integer.parseInt(cadena.substring(index+1, cadena.length()));
					
					for(IndicadorAsignarInventario inv: insumosFormula){
						if(!medicionNulas){
							medicionNulas=validarNulos(ano, periodoE, inv.getIndicadorInsumoId(), strategosMedicionesService);
						}
					}
					
				}
						
				if(!medicionNulas){
					
				for(IndicadorAsignarInventario inv: insumosFormula){
					boolean respuesta = formulaIndicadorA.contains(inv.getIndicadorInsumoId().toString());
					if(respuesta){
				           indicadoresA.add(inv.getIndicadorInsumoId());
				    }else{
				           indicadoresB.add(inv.getIndicadorInsumoId());
				    }
				}
				
				
				
				periodos=getListaPeriodos(anoIni, anoFin, periodoDesde, periodoHasta);
				cantidad=periodos.size();
				
				medicionesA=getValoresMedicion(indicadoresA, anoIni, anoFin, periodoDesde, periodoHasta, strategosMedicionesService);
				medicionesB=getValoresMedicion(indicadoresB, anoIni, anoFin, periodoDesde, periodoHasta, strategosMedicionesService);
				boolean mensaje= false;
				for(int per=0; per <cantidad; per ++){
					int valora=0;
					int valorb=0;
					int valorc=0;
			
					valora=medicionesA.get(per);
					valorb=medicionesB.get(per);
				
					
					valorc=valora-valorb;
					
					if(valorc <0){
						mensaje = true;
						eliminarMediciones(per, indicadoresA, indicadoresB, strategosMedicionesService, editarMedicionesform, messages, usuario, periodos, indicadorf, strategosIndicadoresService);
					}
				}
				if(!mensaje){
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarmediciones.mensaje.guardarmediciones.exito"));
				}
			  }else{
				  messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarmediciones.mensaje.guardarmediciones.exito"));
			  }
				
		    }
			
		}else{
			  messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarmediciones.mensaje.guardarmediciones.exito"));
		}
		
		
		
		saveMessages(request, messages);
		strategosIndicadoresService.close();
		strategosMedicionesService.close();
	}

	private void eliminarMediciones(int periodo, List<Long> indicadoresA, List<Long> indicadoresB, StrategosMedicionesService strategosMedicionesService, EditarMedicionesForm editarMedicionesForm,  ActionMessages messages, Usuario usuario, List<String> periodos, Indicador indicadorFormula,StrategosIndicadoresService strategosIndicadoresService){
		String cadena=periodos.get(periodo);
		int ano=0;
		int periodoE=0;
		String signo ="-";
		int index = cadena.indexOf(signo);
		
		ano=Integer.parseInt(cadena.substring(0, index));
		periodoE=Integer.parseInt(cadena.substring(index+1, cadena.length()));
		

		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.medicion.indicador", indicadorFormula.getNombreCorto(), cadena));
		for(Long ind: indicadoresA){
			List<Medicion> medicionesInsumo= strategosMedicionesService.getMedicionesPeriodo(ind, SerieTiempo.getSerieRealId().longValue(), ano, ano, periodoE, periodoE);
			for(Medicion med: medicionesInsumo){
				strategosMedicionesService.deleteMedicion(med, usuario);
			}
			Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(ind));
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.mensaje.validar.periodo", cadena, indicador.getNombreCorto()));
		}
		
		for(Long ind: indicadoresB){
			List<Medicion> medicionesInsumo= strategosMedicionesService.getMedicionesPeriodo(ind, SerieTiempo.getSerieRealId().longValue(), ano, ano, periodoE, periodoE);
			for(Medicion med: medicionesInsumo){
				strategosMedicionesService.deleteMedicion(med, usuario);
			}
			Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(ind));
		   
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.mensaje.validar.periodo", cadena, indicador.getNombreCorto()));
		}
	}
	
	
	
	public boolean validarInsumoNegativo(List<Indicador> indicadores, Indicador indicadorValidacion ,EditarMedicionesForm editarmedicionesform, StrategosIndicadorAsignarInventarioService strategosInsumoService, StrategosIndicadoresService strategosIndicadorService, StrategosMedicionesService strategosMedicionesService){
			List<IndicadorAsignarInventario> insumosFormula= new ArrayList<IndicadorAsignarInventario>();
			if(strategosInsumoService.esInsumoInventario(indicadorValidacion.getIndicadorId())){
				IndicadorAsignarInventario insumo= strategosInsumoService.getInsumo(indicadorValidacion.getIndicadorId());
				insumosFormula = strategosInsumoService.getIndicadorInventario(indicadorValidacion.getIndicadorId());
				
				for(Iterator<IndicadorAsignarInventario> iter = insumosFormula.iterator(); iter.hasNext();){
					IndicadorAsignarInventario insumoInd = iter.next();
					Indicador insumoIndicador = (Indicador)strategosIndicadorService.load(Indicador.class, new Long(insumoInd.getIndicadorInsumoId()));
					if(!indicadores.contains(insumoIndicador)){
						List<Medicion> medicionesInsumo= strategosMedicionesService.getMedicionesPeriodo(insumoIndicador.getIndicadorId(), SerieTiempo.getSerieRealId().longValue(), Integer.parseInt(editarmedicionesform.getAnoDesde()), Integer.parseInt(editarmedicionesform.getAnoHasta()), editarmedicionesform.getPeriodoDesde(), editarmedicionesform.getPeriodoHasta());
						if(medicionesInsumo.size() <=0 ){
							return false;
						}
					}
				}
				return true;
			}
		return false;
	}
	
	public List<Integer> getValoresMedicion(List<Long>indicadores, int ano, int anofin, int periodo, int periodofin, StrategosMedicionesService strategosMedicionesService){
		List<Integer> valoresA= new ArrayList();
		int valor =0;
		valor= indicadores.size();
		if(indicadores.size() >1){
			
			for(int i=0; i<=valor; i++){
								
				if(valor>0){
					int valorMed=0;
					List<Medicion> medicionesInsumo= strategosMedicionesService.getMedicionesPeriodo(indicadores.get(i), SerieTiempo.getSerieRealId().longValue(), ano, anofin, periodo, periodofin);
					for(int x=0; x<valoresA.size(); i++){
						valorMed=valoresA.get(x);
						valorMed+=medicionesInsumo.get(x).getValor().intValue();
						valoresA.set(x, valorMed);
					}
					
					for(Medicion med: medicionesInsumo){
						valoresA.add(med.getValor().intValue());
					}
				}else{
					List<Medicion> medicionesInsumo= strategosMedicionesService.getMedicionesPeriodo(indicadores.get(i), SerieTiempo.getSerieRealId().longValue(), ano, anofin, periodo, periodofin);
					for(Medicion med: medicionesInsumo){
						valoresA.add(med.getValor().intValue());
					}
				}
			}
			
		}else{
			if(indicadores.size() >0){
				List<Medicion> medicionesInsumo= strategosMedicionesService.getMedicionesPeriodo(indicadores.get(0), SerieTiempo.getSerieRealId().longValue(), ano, anofin, periodo, periodofin);
				for(Medicion med: medicionesInsumo){
					valoresA.add(med.getValor().intValue());
				}
			}
		}
		return valoresA;
	}
	
	private List<String> getListaPeriodos(int ano, int anofin, int periodo, int periodofin){
		List<String> cantidad =new ArrayList<String>();
		if(ano == anofin){
			for(int i=periodo; i<=periodofin; i++){
				cantidad.add(ano+"-"+i);
			}
			
		}else{
			for(int i=ano; i<=anofin; i++){
				if(i == ano){
					for(int x=periodo; x<=12; x++){
						cantidad.add(i+"-"+x);
					}
				}
				if(i == anofin){
					for(int x=1; x<=periodofin; x++){
						cantidad.add(i+"-"+x);
					}
				}
			}
		}
		return cantidad;
	}
	
	public boolean validarNulos(int ano, int periodo, Long indicador, StrategosMedicionesService strategosMedicionesService){
		
		List<Medicion> medicionesInsumo= strategosMedicionesService.getMedicionesPeriodo(indicador, SerieTiempo.getSerieRealId().longValue(), ano, ano, periodo, periodo);
			if(medicionesInsumo.size() >0){
				return false;
			}
		return true;
	}
	
	public boolean existeActividad(List<PryActividad> actividades, PryActividad actividad){
		
		if(actividades.size() >0){
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext(); ) 
			{
				PryActividad act = iter.next();
				if(act.getActividadId().equals(actividad.getActividadId())){
					return true;
				}
			}
		}	
		return false;
	}
	
	
	private void registrarAuditoriaMedicion(List<Indicador> indicadores, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, EditarMedicionesForm editarMedicionesForm, Usuario usuario, List<Medicion> medicionesEditadas, HttpServletRequest request ){
		
		AuditoriaMedicionService auditoriaMedicionService = FrameworkServiceFactory.getInstance().openAuditoriaMedicionService();
	    	
		int periodoDesde=editarMedicionesForm.getPeriodoDesde();
		int periodoHasta=editarMedicionesForm.getPeriodoHasta();
		String anoDesde=editarMedicionesForm.getAnoDesde();
		String anoHasta=editarMedicionesForm.getAnoHasta();
		
		String periodo = anoDesde+"-"+periodoDesde;
		String periodoFinal = anoHasta+"-"+periodoHasta;
		
		
		
		for(Iterator<Indicador> iter = indicadores.iterator(); iter.hasNext(); ){
			
			boolean ins = false;
			boolean mod = false;
			
			Indicador ind = iter.next();
			List<Medicion> medicionesIndicador = new ArrayList();
		
			medicionesIndicador = obtenerMediciones(ind, medicionesEditadas);
			
			AuditoriaMedicion auditoria = new AuditoriaMedicion();
			
			auditoria.setFechaEjecucion(new Date());
			auditoria.setIndicadorId(ind.getIndicadorId());
			auditoria.setIndicador(ind.getNombre());
			auditoria.setOrganizacion(ind.getOrganizacion().getNombre());
			auditoria.setOrganizacionId(ind.getOrganizacionId());
			auditoria.setPeriodo(periodo);
			auditoria.setPeriodoFinal(periodoFinal);
			auditoria.setUsuario(usuario.getFullName());
			auditoria.setClase(ind.getClase().getNombre());
			auditoria.setSesion("sesionId:"+request.getRequestedSessionId()+ ", server:"+request.getServerName() +", port:"+request.getServerPort());
			
			Long iniciativaId = editarMedicionesForm.getIniciativaId();
		        
		    if(iniciativaId != null ){
		    	StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		    	Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, iniciativaId);
		    	if(iniciativa != null){
		    		auditoria.setIniciativa(iniciativa.getNombre());
		    	}else{
		    		auditoria.setIniciativa("");
		    	}
		    			    	
		    	strategosIniciativasService.close();
		    }
			
			String detalle = "";
			int con =1;
			
			for(Medicion med: medicionesIndicador){
				
				detalle += "[";
				detalle += "ano:" + med.getMedicionId().getAno() + ",periodo:"+ med.getMedicionId().getPeriodo() +",valor:"
				+ med.getValor().longValue() + ",serie:" +med.getMedicionId().getSerieId();
				
				Medicion medicionAnterior = getMedicionAnterior(ind, med);
				
				if(medicionAnterior != null){
					detalle +=",accion:"+"Modificación";
					detalle +=",valorAnterior:" +medicionAnterior.getValor().longValue();
					mod = true;
				}else{
					detalle +=",accion:"+"Inserción";
					ins = true;
				}
				
				detalle += "]";
				
				if(con != medicionesIndicador.size()){
					detalle += ",";
				}				
				con++;
			}
			
			if(ins == true && mod == false){
			   auditoria.setAccion("Inserción");
			}else if(ins == false && mod == true){
			   auditoria.setAccion("Modificación");	
			}else if(ins == true && mod == true){
			   auditoria.setAccion("Inserción-Modificación"); 	 
			}
			
			
			
			
			auditoria.setDetalle(detalle);
			auditoriaMedicionService.saveAuditoriaMedicion(auditoria, usuario);
		}		
		
	} 
	
	private List<Medicion> obtenerMediciones(Indicador indicador, List<Medicion> mediciones){
		
		List<Medicion> medicionesFinal = new ArrayList();
	
		for(Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext(); ){
			
			Medicion med = iter.next();
			int ind = med.getMedicionId().getIndicadorId().intValue();

			if(ind == indicador.getIndicadorId()){
				medicionesFinal.add(med);
			}
			
		}
		
		return medicionesFinal;
	}
	
	private Medicion getMedicionAnterior(Indicador indicador, Medicion medicion){
		
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		
		List<Medicion> mediciones= strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), medicion.getMedicionId().getSerieId(), medicion.getMedicionId().getAno(), medicion.getMedicionId().getAno(), medicion.getMedicionId().getPeriodo(), medicion.getMedicionId().getPeriodo());
		
		Medicion medicionAnterior = new Medicion(); 
		
		if(mediciones.size() >0){
			medicionAnterior = mediciones.get(0);
			return medicionAnterior;
		}
		
		strategosMedicionesService.close();
		return null;
	}
	/*
	private void calcularProcentaje(Iniciativa iniciativa, StrategosIniciativasService strategosIniciativasService, Usuario usuario){
		StrategosPryActividadesService servicioActividad = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		
		String atributoOrden = "";
	    String tipoOrden = "";
		
	    int pagina =1;
	    
		Long ProyectoId =iniciativa.getProyectoId();
		
		Map<String, Object> filtros = new HashMap();
	   	   	    
	    if (ProyectoId != null)
	        filtros.put("proyectoId", ProyectoId);
	    
	    PaginaLista paginaActividades =  servicioActividad.getActividades(pagina, 50, atributoOrden, tipoOrden, true, filtros);
	    
	    Double acumulado = 0.0;
	    
	    for(Iterator<PryActividad> iter = paginaActividades.getLista().iterator(); iter.hasNext(); ){
			
	    	PryActividad act = iter.next();
	    	
	    	if(act.getPadreId() == null || act.getPadreId().equals(new Long(0L))){	
	    		
	    		if(act.getPorcentajeCompletado() != null){
	    			if(act.getPorcentajeCompletado() >0){
	    				
	    				if(act.getPorcentajeCompletado() >100){
	    			    	acumulado+=100;
	    			    }else{
	    			    	acumulado+=act.getPorcentajeCompletado();
	    			    }
	    				
	    			}
	    		}
	    		
	    	}
	    	
		}
	    
	    Double porcentaje = acumulado/paginaActividades.getLista().size();
	    
	    iniciativa.setPorcentajeCompletado(porcentaje);
	    
	    iniciativa.setEstatusId(new com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction().CalcularEstatus(iniciativa.getPorcentajeCompletado()));
       	strategosIniciativasService.saveIniciativa(iniciativa, usuario, Boolean.valueOf(true));
	   
       	servicioActividad.close();
	}
	*/
}
