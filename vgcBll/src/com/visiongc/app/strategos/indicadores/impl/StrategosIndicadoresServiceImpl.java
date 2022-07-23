package com.visiongc.app.strategos.indicadores.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.CategoriaIndicador;
import com.visiongc.app.strategos.indicadores.model.CategoriaIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.FormulaPK;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.InsumoFormulaPK;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.ConfiguracionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoAlerta;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.persistence.StrategosIndicadoresPersistenceSession;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.MathUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Licencia;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Usuario;
import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class StrategosIndicadoresServiceImpl
  extends StrategosServiceImpl
  implements StrategosIndicadoresService
{
  private StrategosIndicadoresPersistenceSession persistenceSession = null;
  
  public StrategosIndicadoresServiceImpl(StrategosIndicadoresPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getIndicadores(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros, Integer anoInical, Integer anoFinal, Boolean buscarMediciones)
  {
    PaginaLista indicadores = persistenceSession.getIndicadores(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
    
    if (buscarMediciones.booleanValue())
    {
      StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
      

      List<Medicion> medicionesInternas = null;
      List<Medicion> mediciones = strategosMedicionesService.getMedicionesIndicadores(indicadores.getLista(), null, anoInical, anoFinal);
      StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();
      UnidadMedida unidad = strategosUnidadesService.getUnidadMedidaPorcentaje();
      strategosUnidadesService.close();
      
      for (Iterator<?> i = indicadores.getLista().iterator(); i.hasNext();)
      {
        Indicador indicador = (Indicador)i.next();
        Byte tipoFuncion = indicador.getTipoFuncion();
        boolean acumular= false;
       
        if(!indicador.getValorInicialCero() && indicador.getCorte() ==0 && indicador.getTipoCargaMedicion() !=null && indicador.getTipoCargaMedicion() == 0 ){
        	acumular = true;
        }else{
        	acumular = false;
        }
        
        List<Medicion> medicionesReales = strategosMedicionesService.getMedicionesBySerie(mediciones, indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId());
        List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesBySerie(mediciones, indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId());
        List<Medicion> medicionesMinimo = strategosMedicionesService.getMedicionesBySerie(mediciones, indicador.getIndicadorId(), SerieTiempo.getSerieMinimo().getSerieId());
        List<Medicion> medicionesMaximo = strategosMedicionesService.getMedicionesBySerie(mediciones, indicador.getIndicadorId(), SerieTiempo.getSerieMaximo().getSerieId());
        
        Medicion ultimaMedicionReal = null;
        double valorReal = 0.0;
        double valorProgramado = 0.0;
        
        valorReal= strategosMedicionesService.getValorAcumulado(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId());
        valorProgramado= strategosMedicionesService.getValorAcumulado(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId());
        
        ultimaMedicionReal= strategosMedicionesService.getUltimaMedicion(medicionesReales);
       
        
        
        Medicion ultimaMedicionProgramado = null;
        
        Medicion ultimaMedicionMinimo = null;
        Medicion ultimaMedicionMaximo = null;
        
        if ((ultimaMedicionReal != null) && (medicionesProgramadas.size() > 0)){
        	
        	ultimaMedicionProgramado = strategosMedicionesService.getUltimaMedicionByAnoByPeriodo(medicionesProgramadas, ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getPeriodo());	 
        	
        }	
         
        if ((ultimaMedicionReal != null) && (medicionesMinimo.size() > 0))
          ultimaMedicionMinimo = strategosMedicionesService.getUltimaMedicionByAnoByPeriodo(medicionesMinimo, ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getPeriodo());
        if ((ultimaMedicionReal != null) && (medicionesMaximo.size() > 0)) {
          ultimaMedicionMaximo = strategosMedicionesService.getUltimaMedicionByAnoByPeriodo(medicionesMaximo, ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getPeriodo());
        }
        if (ultimaMedicionReal != null)
        {
          if(acumular){
        	  indicador.setUltimaMedicion(valorReal);
          }else{
        	  indicador.setUltimaMedicion(ultimaMedicionReal.getValor()); 
          }
          
          indicador.setFechaUltimaMedicion(ultimaMedicionReal.getMedicionId().getPeriodo().intValue() + "/" + ultimaMedicionReal.getMedicionId().getAno().intValue());
          
          if ((indicador.getParametroInferiorIndicadorId() != null) || (indicador.getParametroSuperiorIndicadorId() != null))
          {
            if (indicador.getParametroInferiorIndicadorId() != null)
            {
              medicionesInternas = strategosMedicionesService.getMedicionesPeriodo(indicador.getParametroInferiorIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getPeriodo(), ultimaMedicionReal.getMedicionId().getPeriodo());
              if (medicionesInternas.size() > 0) {
                indicador.setParametroInferiorIndicadorValorPeriodo(((Medicion)medicionesInternas.iterator().next()).getValor());
              }
            }
            if (indicador.getParametroSuperiorIndicadorId() != null)
            {
              medicionesInternas = strategosMedicionesService.getMedicionesPeriodo(indicador.getParametroSuperiorIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getPeriodo(), ultimaMedicionReal.getMedicionId().getPeriodo());
              if (medicionesInternas.size() > 0) {
                indicador.setParametroSuperiorIndicadorValorPeriodo(((Medicion)medicionesInternas.iterator().next()).getValor());
              }
            }
          }
          if (tipoFuncion.byteValue() != TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue())
          {
            if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue())
            {
              if (ultimaMedicionMaximo != null) {
                indicador.setUltimoProgramado(ultimaMedicionMaximo.getValor());
              }
            } else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue())
            {
              if (ultimaMedicionMinimo != null) {
                indicador.setUltimoProgramado(ultimaMedicionMinimo.getValor());
              }
            } else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue())
            {
              if (ultimaMedicionMaximo != null)
                indicador.setUltimoProgramado(ultimaMedicionMaximo.getValor());
              if (ultimaMedicionMinimo != null) {
                indicador.setUltimoProgramadoInferior(ultimaMedicionMinimo.getValor());
              }
              
            }
            else if (ultimaMedicionProgramado != null) {
              
            	if(acumular){
              	   indicador.setUltimoProgramado(valorProgramado);
                }else{
                	indicador.setUltimoProgramado(ultimaMedicionProgramado.getValor()); 
                }	
              
            }
            

          }
          else if ((unidad != null) && (indicador.getUnidadId() != null) && (indicador.getUnidadId().longValue() == unidad.getUnidadId().longValue()) && (ultimaMedicionReal.getValor().doubleValue() == 100.0D))
          {
            if ((ultimaMedicionProgramado != null) && (ultimaMedicionProgramado.getValor() != null)) {
            	if(acumular){
               	   indicador.setUltimoProgramado(valorProgramado);
                 }else{
                 	indicador.setUltimoProgramado(ultimaMedicionProgramado.getValor()); 
                 }		
            }
            
          }
          else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue())
          {
            if (ultimaMedicionMaximo != null) {
              indicador.setUltimoProgramado(ultimaMedicionMaximo.getValor());
            }
          } else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue())
          {
            if (ultimaMedicionMinimo != null)
              indicador.setUltimoProgramado(ultimaMedicionMinimo.getValor());
            if (ultimaMedicionMinimo != null) {
              indicador.setUltimoProgramadoInferior(ultimaMedicionMinimo.getValor());
            }
          } else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue())
          {
            if (ultimaMedicionMaximo != null) {
              indicador.setUltimoProgramado(ultimaMedicionMaximo.getValor());
            }
            
          }
          else if ((medicionesProgramadas != null) && (medicionesProgramadas.size() > 0))
          {
            DecimalFormat nf3 = new DecimalFormat("#000");
            int anoPeriodoBuscar = Integer.parseInt(ultimaMedicionReal.getMedicionId().getAno().toString() + nf3.format(ultimaMedicionReal.getMedicionId().getPeriodo()).toString());
            for (Iterator<?> iterMed = medicionesProgramadas.iterator(); iterMed.hasNext();)
            {
              Medicion medicionPro = (Medicion)iterMed.next();
              int anoPeriodo = Integer.parseInt(medicionPro.getMedicionId().getAno().toString() + nf3.format(medicionPro.getMedicionId().getPeriodo()).toString());
              if (anoPeriodo <= anoPeriodoBuscar) {
                indicador.setUltimoProgramado(medicionPro.getValor());
              }
            } 
            
            if(acumular){
               indicador.setUltimoProgramado(valorProgramado);
            }
            
          }
          


          int ano = ultimaMedicionReal.getMedicionId().getAno().intValue() - 1;
          int periodoMaximoIndicador = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), ano);
          Medicion medicionByAno = strategosMedicionesService.getUltimaMedicionByAnoByPeriodo(medicionesReales, Integer.valueOf(ano), Integer.valueOf(periodoMaximoIndicador));
          if (medicionByAno != null) {
            indicador.setUltimaMedicionAnoAnterior(medicionByAno.getValor());
          }
        }
        
       
        
      }
      strategosMedicionesService.close();
    }
    
    return indicadores;
  }
  
  
  public PaginaLista getIndicadoresResponsables(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros, Integer anoInical, Integer anoFinal, Boolean buscarMediciones)
  {
    PaginaLista indicadores = persistenceSession.getIndicadoresResponsables(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
    
    if (buscarMediciones.booleanValue())
    {
      StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
      

      List<Medicion> medicionesInternas = null;
      List<Medicion> mediciones = strategosMedicionesService.getMedicionesIndicadores(indicadores.getLista(), null, anoInical, anoFinal);
      StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();
      UnidadMedida unidad = strategosUnidadesService.getUnidadMedidaPorcentaje();
      strategosUnidadesService.close();
      
      for (Iterator<?> i = indicadores.getLista().iterator(); i.hasNext();)
      {
        Indicador indicador = (Indicador)i.next();
        Byte tipoFuncion = indicador.getTipoFuncion();
        boolean acumular= false;
       
        if(!indicador.getValorInicialCero() && indicador.getCorte() ==0 && indicador.getTipoCargaMedicion() !=null && indicador.getTipoCargaMedicion() == 0 ){
        	acumular = true;
        }else{
        	acumular = false;
        }
        
        List<Medicion> medicionesReales = strategosMedicionesService.getMedicionesBySerie(mediciones, indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId());
        List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesBySerie(mediciones, indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId());
        List<Medicion> medicionesMinimo = strategosMedicionesService.getMedicionesBySerie(mediciones, indicador.getIndicadorId(), SerieTiempo.getSerieMinimo().getSerieId());
        List<Medicion> medicionesMaximo = strategosMedicionesService.getMedicionesBySerie(mediciones, indicador.getIndicadorId(), SerieTiempo.getSerieMaximo().getSerieId());
        
        Medicion ultimaMedicionReal = null;
        double valorReal = 0.0;
        double valorProgramado = 0.0;
        
        valorReal= strategosMedicionesService.getValorAcumulado(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId());
        valorProgramado= strategosMedicionesService.getValorAcumulado(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId());
        
        ultimaMedicionReal= strategosMedicionesService.getUltimaMedicion(medicionesReales);
       
        
        
        Medicion ultimaMedicionProgramado = null;
        
        Medicion ultimaMedicionMinimo = null;
        Medicion ultimaMedicionMaximo = null;
        
        if ((ultimaMedicionReal != null) && (medicionesProgramadas.size() > 0)){
        	
        	ultimaMedicionProgramado = strategosMedicionesService.getUltimaMedicionByAnoByPeriodo(medicionesProgramadas, ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getPeriodo());	 
        	
        }	
         
        if ((ultimaMedicionReal != null) && (medicionesMinimo.size() > 0))
          ultimaMedicionMinimo = strategosMedicionesService.getUltimaMedicionByAnoByPeriodo(medicionesMinimo, ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getPeriodo());
        if ((ultimaMedicionReal != null) && (medicionesMaximo.size() > 0)) {
          ultimaMedicionMaximo = strategosMedicionesService.getUltimaMedicionByAnoByPeriodo(medicionesMaximo, ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getPeriodo());
        }
        if (ultimaMedicionReal != null)
        {
          if(acumular){
        	  indicador.setUltimaMedicion(valorReal);
          }else{
        	  indicador.setUltimaMedicion(ultimaMedicionReal.getValor()); 
          }
          
          indicador.setFechaUltimaMedicion(ultimaMedicionReal.getMedicionId().getPeriodo().intValue() + "/" + ultimaMedicionReal.getMedicionId().getAno().intValue());
          
          if ((indicador.getParametroInferiorIndicadorId() != null) || (indicador.getParametroSuperiorIndicadorId() != null))
          {
            if (indicador.getParametroInferiorIndicadorId() != null)
            {
              medicionesInternas = strategosMedicionesService.getMedicionesPeriodo(indicador.getParametroInferiorIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getPeriodo(), ultimaMedicionReal.getMedicionId().getPeriodo());
              if (medicionesInternas.size() > 0) {
                indicador.setParametroInferiorIndicadorValorPeriodo(((Medicion)medicionesInternas.iterator().next()).getValor());
              }
            }
            if (indicador.getParametroSuperiorIndicadorId() != null)
            {
              medicionesInternas = strategosMedicionesService.getMedicionesPeriodo(indicador.getParametroSuperiorIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getAno(), ultimaMedicionReal.getMedicionId().getPeriodo(), ultimaMedicionReal.getMedicionId().getPeriodo());
              if (medicionesInternas.size() > 0) {
                indicador.setParametroSuperiorIndicadorValorPeriodo(((Medicion)medicionesInternas.iterator().next()).getValor());
              }
            }
          }
          if (tipoFuncion.byteValue() != TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue())
          {
            if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue())
            {
              if (ultimaMedicionMaximo != null) {
                indicador.setUltimoProgramado(ultimaMedicionMaximo.getValor());
              }
            } else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue())
            {
              if (ultimaMedicionMinimo != null) {
                indicador.setUltimoProgramado(ultimaMedicionMinimo.getValor());
              }
            } else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue())
            {
              if (ultimaMedicionMaximo != null)
                indicador.setUltimoProgramado(ultimaMedicionMaximo.getValor());
              if (ultimaMedicionMinimo != null) {
                indicador.setUltimoProgramadoInferior(ultimaMedicionMinimo.getValor());
              }
              
            }
            else if (ultimaMedicionProgramado != null) {
              
            	if(acumular){
              	   indicador.setUltimoProgramado(valorProgramado);
                }else{
                	indicador.setUltimoProgramado(ultimaMedicionProgramado.getValor()); 
                }	
              
            }
            

          }
          else if ((unidad != null) && (indicador.getUnidadId() != null) && (indicador.getUnidadId().longValue() == unidad.getUnidadId().longValue()) && (ultimaMedicionReal.getValor().doubleValue() == 100.0D))
          {
            if ((ultimaMedicionProgramado != null) && (ultimaMedicionProgramado.getValor() != null)) {
            	if(acumular){
               	   indicador.setUltimoProgramado(valorProgramado);
                 }else{
                 	indicador.setUltimoProgramado(ultimaMedicionProgramado.getValor()); 
                 }		
            }
            
          }
          else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue())
          {
            if (ultimaMedicionMaximo != null) {
              indicador.setUltimoProgramado(ultimaMedicionMaximo.getValor());
            }
          } else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue())
          {
            if (ultimaMedicionMinimo != null)
              indicador.setUltimoProgramado(ultimaMedicionMinimo.getValor());
            if (ultimaMedicionMinimo != null) {
              indicador.setUltimoProgramadoInferior(ultimaMedicionMinimo.getValor());
            }
          } else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue())
          {
            if (ultimaMedicionMaximo != null) {
              indicador.setUltimoProgramado(ultimaMedicionMaximo.getValor());
            }
            
          }
          else if ((medicionesProgramadas != null) && (medicionesProgramadas.size() > 0))
          {
            DecimalFormat nf3 = new DecimalFormat("#000");
            int anoPeriodoBuscar = Integer.parseInt(ultimaMedicionReal.getMedicionId().getAno().toString() + nf3.format(ultimaMedicionReal.getMedicionId().getPeriodo()).toString());
            for (Iterator<?> iterMed = medicionesProgramadas.iterator(); iterMed.hasNext();)
            {
              Medicion medicionPro = (Medicion)iterMed.next();
              int anoPeriodo = Integer.parseInt(medicionPro.getMedicionId().getAno().toString() + nf3.format(medicionPro.getMedicionId().getPeriodo()).toString());
              if (anoPeriodo <= anoPeriodoBuscar) {
                indicador.setUltimoProgramado(medicionPro.getValor());
              }
            } 
            
            if(acumular){
               indicador.setUltimoProgramado(valorProgramado);
            }
            
          }
          


          int ano = ultimaMedicionReal.getMedicionId().getAno().intValue() - 1;
          int periodoMaximoIndicador = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), ano);
          Medicion medicionByAno = strategosMedicionesService.getUltimaMedicionByAnoByPeriodo(medicionesReales, Integer.valueOf(ano), Integer.valueOf(periodoMaximoIndicador));
          if (medicionByAno != null) {
            indicador.setUltimaMedicionAnoAnterior(medicionByAno.getValor());
          }
        }
        
       
        
      }
      strategosMedicionesService.close();
    }
    
    return indicadores;
  }
  
  
  
  
  public PaginaLista getIndicadoresLogroPlan(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    return persistenceSession.getIndicadores(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public List<Indicador> getIndicadores(Map<?, ?> filtros)
  {
    return persistenceSession.getIndicadores(filtros);
  }
  
  private int deleteDependenciasIndicador(Indicador indicador, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    ListaMap dependencias = new ListaMap();
    List listaObjetosRelacionados = new ArrayList();
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      resultado = deleteDependenciasGenericas(indicador.getIndicadorId(), usuario);
      
      if (resultado == 10000) {
        resultado = persistenceSession.deleteReferenciasForaneasIndicador(indicador.getIndicadorId());
      }
      if (resultado == 10000)
      {
        dependencias = persistenceSession.getDependenciasIndicador(indicador);
        
        for (Iterator i = dependencias.iterator(); i.hasNext();)
        {
          listaObjetosRelacionados = (List)i.next();
          
          if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Medicion)))
          {
            StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
            
            for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();)
            {
              Medicion medicion = (Medicion)j.next();
              
              resultado = strategosMedicionesService.deleteMedicion(medicion, usuario);
              
              if (resultado != 10000)
                break;
            }
            strategosMedicionesService.close();
          }
          else
          {
            for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();)
            {
              Object objeto = j.next();
              
              resultado = persistenceSession.delete(objeto, usuario);
              
              if (resultado != 10000)
                break;
            }
          }
          if (resultado != 10000) {
            break;
          }
        }
      }
      if (resultado == 10000)
      {
        if (transActiva)
        {
          persistenceSession.commitTransaction();
          transActiva = false;
        }
      }
      else if (transActiva)
      {
        persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva)
      {
        persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    
    return resultado;
  }
  
  public int deleteIndicador(Indicador indicador, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (indicador.getIndicadorId() != null)
      {
        resultado = deleteDependenciasIndicador(indicador, usuario);
        
        if (resultado == 10000)
        {
          resultado = persistenceSession.delete(indicador, usuario);
        }
      }
      

      if (resultado == 10000)
      {
        if (transActiva) {
          persistenceSession.commitTransaction();
          transActiva = false;
        }
        
      }
      else if (transActiva) {
        persistenceSession.rollbackTransaction();
        transActiva = false;
      }
      
    }
    catch (Throwable t)
    {
      if (transActiva) {
        persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    

    return resultado;
  }
  
  private void setSeriesIndicadorForSave(Indicador indicador)
  {	  	          
      if(indicador.getSeriesIndicador() == null)
      {
          indicador.setSeriesIndicador(new HashSet());
          SerieIndicador serieIndicador = new SerieIndicador();
          serieIndicador.setIndicador(indicador);
          serieIndicador.setPk(new SerieIndicadorPK());
          serieIndicador.getPk().setSerieId(SerieTiempo.getSerieRealId());
          serieIndicador.getPk().setIndicadorId(indicador.getIndicadorId());
          serieIndicador.setFormulas(new HashSet());
          serieIndicador.setNaturaleza(indicador.getNaturaleza());
          indicador.getSeriesIndicador().add(serieIndicador);          
      }
      for(Iterator i = indicador.getSeriesIndicador().iterator(); i.hasNext();)
      {
    	      	  
          SerieIndicador serieIndicador = (SerieIndicador)i.next();
          Byte naturaleza = indicador.getNaturaleza();
          serieIndicador.setNaturaleza(naturaleza);
          serieIndicador.getPk().setIndicadorId(indicador.getIndicadorId());         
                    
          if(serieIndicador.getFormulas() != null && serieIndicador.getFormulas().size() > 0)
          {        	  
              for(Iterator j = serieIndicador.getFormulas().iterator(); j.hasNext();)
              {            	  
                  Formula formula = (Formula)j.next();
                  formula.setPk(new FormulaPK());
                  formula.getPk().setSerieId(serieIndicador.getPk().getSerieId());
                  formula.getPk().setIndicadorId(indicador.getIndicadorId());
                  if(formula.getInsumos() != null && formula.getInsumos().size() > 0)
                  {                	  
                      InsumoFormula insumo;
                      for(Iterator k = formula.getInsumos().iterator(); k.hasNext(); insumo.getPk().setPadreId(indicador.getIndicadorId()))
                          insumo = (InsumoFormula)k.next();
                      
                  }                  
              }
          }
      }

  }
  
  public int saveIndicador(Indicador indicador, Usuario usuario)
  {	  	 
    boolean transActiva = false;
    boolean transActivaViene = false;
    int resultado = 10000;
    String[] fieldNames = new String[2];
    Object[] fieldValues = new Object[2];
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      else {
        transActivaViene = true;
      }
      fieldNames[0] = "nombre";
      fieldValues[0] = indicador.getNombre();
      fieldNames[1] = "claseId";
      fieldValues[1] = indicador.getClaseId();
      
      if ((indicador.getIndicadorId() == null) || (indicador.getIndicadorId().longValue() == 0L))
      {    	  
        if (persistenceSession.existsObject(indicador, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          indicador.setIndicadorId(new Long(persistenceSession.getUniqueId()));
          
          setSeriesIndicadorForSave(indicador);                  
          if ((indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoNominal())) || (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoOrdinal())))
          {
            for (Iterator i = indicador.getEscalaCualitativa().iterator(); i.hasNext();) {
              CategoriaIndicador categoriaIndicador = (CategoriaIndicador)i.next();
              categoriaIndicador.getPk().setIndicadorId(indicador.getIndicadorId());              
            }
          }          
          resultado = persistenceSession.insert(indicador, usuario);          
          if ((resultado == 10000) && (indicador.getPlanId() != null) && (indicador.getPerspectivaId() != null))
          {
            StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService(this);
            Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, indicador.getPerspectivaId());
            if (!strategosPerspectivasService.asociarIndicador(indicador.getPlanId(), perspectiva, indicador.getIndicadorId(), new Boolean(true), usuario))
            {
              persistenceSession.rollbackTransaction();
              strategosPerspectivasService.close();
              return 10007;
            }
            
            strategosPerspectivasService.close();
          }
          
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "indicadorId";
        idFieldValues[0] = indicador.getIndicadorId();
        
        if (persistenceSession.existsObject(indicador, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        }
        else {
          Indicador indicadorOriginal = getIndicadorValoresOriginales(indicador.getIndicadorId());
          
          setSeriesIndicadorForSave(indicador);
          
          resultado = persistenceSession.update(indicador, usuario);
          
          if (resultado == 10000) {
            resultado = verificarCambiosIndicador(indicador, indicadorOriginal);
          }
        }
      }
      if (resultado == 10000)
      {
        if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaFormula()))
        {
          InsumoFormula insumoFormula = getPrimeraReferenciaCircular(indicador.getIndicadorId(), new Long("0"));
          
          if (insumoFormula != null)
          {
            persistenceSession.rollbackTransaction();
            indicador.setInsumoFormula(insumoFormula);
            return 10007;
          }
        }
      }
      
      if (transActiva)
      {
        if (resultado == 10000) {
          persistenceSession.commitTransaction();
        } else
          persistenceSession.rollbackTransaction();
        transActiva = false;
      }
      
      if (resultado != 10000) {
        return resultado;
      }
      if ((!transActiva) && (!transActivaViene) && (resultado == 10000))
      {
        List<Object> indicadores = new ArrayList();
        indicadores.add(indicador.getIndicadorId());
        resultado = new Servicio().calcular(Servicio.EjecutarTipo.getEjecucionAlerta().byteValue(), indicadores, usuario);
      }
    }
    catch (Throwable t)
    {
      if (transActiva)
        persistenceSession.rollbackTransaction();
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return resultado;
  }
  
  public Indicador getIndicadorValoresOriginales(Long indicadorId)
  {
    return persistenceSession.getIndicadorValoresOriginales(indicadorId);
  }
  
  public int verificarCambiosIndicador(Indicador indicador, Indicador indicadorOriginal)
  {
    int resultado = 10000;
    
    boolean eliminarMediciones = false;
    try
    {
      if (indicador.getFrecuencia().byteValue() != indicadorOriginal.getFrecuencia().byteValue()) {
        eliminarMediciones = true;
      }
      if (indicador.getNaturaleza().byteValue() != indicadorOriginal.getNaturaleza().byteValue()) {
        eliminarMediciones = true;
      }
      if (eliminarMediciones)
      {
        StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
        
        resultado = strategosMedicionesService.deleteMediciones(indicador.getIndicadorId());
        actualizarDatosIndicador(indicador.getIndicadorId(), null, null, null);
        
        strategosMedicionesService.close();
      }
    }
    catch (Throwable t)
    {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return resultado;
  }
  
  private InsumoFormula getPrimeraReferenciaCircular(Long indicadorId, Long serieId)
  {
    List<InsumoFormula> insumos = persistenceSession.getInsumosFormula(indicadorId, serieId);
    
    for (Iterator<InsumoFormula> i = insumos.iterator(); i.hasNext();)
    {
      InsumoFormula insumo = (InsumoFormula)i.next();
      
      if ((insumo.getPk().getIndicadorId().equals(indicadorId)) && (insumo.getPk().getInsumoSerieId().equals(serieId))) {
        return insumo;
      }
      InsumoFormula resultado = getPrimeraReferenciaCircularAuxiliar(indicadorId, insumo);
      
      if (resultado != null) {
        return insumo;
      }
    }
    return null;
  }
  
  private InsumoFormula getPrimeraReferenciaCircularAuxiliar(Long formulaIndicadorId, InsumoFormula insumoFormula)
  {
    List insumos = persistenceSession.getInsumosFormula(insumoFormula.getPk().getIndicadorId(), insumoFormula.getPk().getSerieId());
    
    for (Iterator i = insumos.iterator(); i.hasNext();)
    {
      InsumoFormula insumo = (InsumoFormula)i.next();
      
      if (insumo.getPk().getIndicadorId().equals(formulaIndicadorId)) {
        return insumoFormula;
      }
      InsumoFormula resultado = getPrimeraReferenciaCircularAuxiliar(formulaIndicadorId, insumo);
      
      if (resultado != null) {
        return resultado;
      }
    }
    return null;
  }
  
  public Indicador getIndicadorReferenciaCircular(SerieIndicador serieReal)
  {
    Formula formulaIndicador = (Formula)serieReal.getFormulas().toArray()[0];
    
    if (formulaIndicador != null)
    {
      for (Iterator<?> i = formulaIndicador.getInsumos().iterator(); i.hasNext();)
      {
        InsumoFormula insumo = (InsumoFormula)i.next();
        
        if (insumo.getPk().getIndicadorId().equals(serieReal.getPk().getIndicadorId())) {
          return (Indicador)persistenceSession.load(Indicador.class, insumo.getPk().getIndicadorId());
        }
        InsumoFormula resultado = getIndicadorReferenciaCircularAuxiliar(serieReal.getPk().getIndicadorId(), insumo);
        
        if (resultado != null) {
          return (Indicador)persistenceSession.load(Indicador.class, insumo.getPk().getIndicadorId());
        }
      }
    }
    return null;
  }
  
  private InsumoFormula getIndicadorReferenciaCircularAuxiliar(Long formulaIndicadorId, InsumoFormula insumoFormula)
  {
    List<?> insumos = persistenceSession.getInsumosFormula(insumoFormula.getPk().getIndicadorId(), insumoFormula.getPk().getSerieId());
    
    for (Iterator<?> i = insumos.iterator(); i.hasNext();)
    {
      InsumoFormula insumo = (InsumoFormula)i.next();
      
      if (insumo.getPk().getIndicadorId().equals(formulaIndicadorId)) {
        return insumoFormula;
      }
      InsumoFormula resultado = getIndicadorReferenciaCircularAuxiliar(formulaIndicadorId, insumo);
      
      if (resultado != null) {
        return resultado;
      }
    }
    return null;
  }
  
  public int getNumeroIndicadoresEnFormula(long indicadorId, long serieId)
  {
    int totalIndicadores = 0;
    
    Indicador indicadorPadre = (Indicador)persistenceSession.load(Indicador.class, new Long(indicadorId));
    
    if (indicadorPadre == null) {
      return -1;
    }
    
    Set conjuntoSeriesIndicador = indicadorPadre.getSeriesIndicador();
    
    for (Iterator i = conjuntoSeriesIndicador.iterator(); i.hasNext();)
    {
      SerieIndicador serieIndicadorFormula = (SerieIndicador)i.next();
      
      if (serieIndicadorFormula.getSerieTiempo().getSerieId().longValue() == serieId)
      {
        if (serieIndicadorFormula.getNaturaleza().byteValue() == Naturaleza.getNaturalezaFormula().byteValue())
        {



          Formula formula = (Formula)serieIndicadorFormula.getFormulas().toArray()[0];
          Set insumosFormula = formula.getInsumos();
          
          for (Iterator k = insumosFormula.iterator(); k.hasNext();) {
            InsumoFormula insumoFormula = (InsumoFormula)k.next();
            
            if (insumoFormula.getInsumo().getMostrarEnArbol().booleanValue()) {
              totalIndicadores++;
              
              totalIndicadores += getNumeroIndicadoresEnFormula(insumoFormula.getInsumo().getIndicadorId().longValue(), serieId);
            }
          }
        }
      }
    }
    
    return totalIndicadores;
  }
  
  private int copiarIndicador(Long indicadorId, Long organizacionDestinoId, boolean copiarMediciones, ListaMap listaClaseIds, ListaMap listaCopiadosIds, Usuario usuario)
  {
    boolean transActiva = false;
    int respuesta = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (listaCopiadosIds.get(indicadorId.toString()) != null) {
        return respuesta;
      }
      
      Indicador indicador = (Indicador)persistenceSession.load(Indicador.class, indicadorId);
      
      Long claseId = (Long)listaClaseIds.get(indicador.getClaseId().toString());
      
      if (claseId == null) {
        listaCopiadosIds.add(indicador.getIndicadorId(), indicador.getIndicadorId().toString());
        return respuesta;
      }
      
      respuesta = agregarCopiaIndicador(indicador, organizacionDestinoId, claseId, copiarMediciones, listaClaseIds, listaCopiadosIds, usuario);
      
      if (respuesta == 10000) {
        if (transActiva) {
          persistenceSession.commitTransaction();
          transActiva = false;
        }
      }
      else if (transActiva) {
        persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    return respuesta;
  }
  
  private int agregarCopiaIndicador(Indicador indicador, Long newOrganizacionId, Long newClaseId, boolean copiarMediciones, ListaMap listaClaseIds, ListaMap listaCopiadosIds, Usuario usuario)
  {
    Long indicadorId = indicador.getIndicadorId();
    ListaMap formulas = new ListaMap();
    Formula formula = null;
    ListaMap mediciones = new ListaMap();
    String expresion = null;
    
    Set series = indicador.getSeriesIndicador();
    indicador.setSeriesIndicador(new HashSet());
    for (Iterator iter = series.iterator(); iter.hasNext();)
    {
      SerieIndicador serie = (SerieIndicador)iter.next();
      if (copiarMediciones)
      {
        serie.getMediciones().size();
        mediciones.add(serie.getMediciones(), serie.getPk().getSerieId().toString());
      }
      if (serie.getNaturaleza().equals(Naturaleza.getNaturalezaFormula()))
      {
        Set formulasSerie = serie.getFormulas();
        if (formulasSerie.size() > 0)
        {
          formula = (Formula)formulasSerie.toArray()[0];
          Set insumosFormula = formula.getInsumos();
          formula.setInsumos(new HashSet());
          formula.getInsumos().addAll(insumosFormula);
          formulas.add(formula, serie.getPk().getSerieId().toString());
          if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue()) {
            expresion = formula.getExpresion();
          }
        }
      }
      serie.getPk().setIndicadorId(new Long(0L));
      serie.setFormulas(new HashSet());
      serie.setMediciones(new HashSet());
      serie.setIndicadoresCeldas(new HashSet());
      indicador.getSeriesIndicador().add(serie);
    }
    
    List escalaCualitativa = indicador.getEscalaCualitativa();
    indicador.setEscalaCualitativa(new ArrayList());
    for (Iterator iter = escalaCualitativa.iterator(); iter.hasNext();)
    {
      CategoriaIndicador cat = (CategoriaIndicador)iter.next();
      cat.getPk().setIndicadorId(new Long(0L));
      indicador.getEscalaCualitativa().add(cat);
    }
    
    indicador.setIndicadorId(new Long(0L));
    indicador.setOrganizacionId(newOrganizacionId);
    indicador.setClaseId(newClaseId);
    
    persistenceSession.clear();
    int respuesta = saveIndicador(indicador, usuario);
    
    if (respuesta == 10000)
    {
      listaCopiadosIds.add(indicador.getIndicadorId(), indicadorId.toString());
      
      if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaFormula()))
      {
        for (Iterator iter = formulas.iterator(); iter.hasNext();)
        {
          formula = (Formula)iter.next();
          Set insumos = formula.getInsumos();
          expresion = formula.getExpresion();
          for (Iterator i = insumos.iterator(); i.hasNext();)
          {
            InsumoFormula insumo = (InsumoFormula)i.next();
            
            Long insumoId = insumo.getPk().getIndicadorId();
            copiarIndicador(insumoId, newOrganizacionId, copiarMediciones, listaClaseIds, listaCopiadosIds, usuario);
            
            insumo.getPk().setPadreId(indicador.getIndicadorId());
            insumo.getPk().setIndicadorId((Long)listaCopiadosIds.get(insumoId.toString()));
            if (expresion != null)
              expresion = expresion.replaceAll("\\[" + insumoId.toString() + ".", "{" + listaCopiadosIds.get(insumoId.toString()) + "}");
          }
          if (expresion != null)
          {
            expresion = expresion.replaceAll("\\{", "[");
            expresion = expresion.replaceAll("\\}", ".");
            
            formula.setExpresion(expresion);
            formula.getPk().setIndicadorId(indicador.getIndicadorId());
            
            respuesta = persistenceSession.insert(formula, usuario);
            if (respuesta != 10000) {
              break;
            }
          }
        }
      }
      

      if (copiarMediciones)
      {
        for (Iterator iter = mediciones.iterator(); iter.hasNext();)
        {
          Set meds = (Set)iter.next();
          for (Iterator jter = meds.iterator(); jter.hasNext();)
          {
            Medicion med = (Medicion)jter.next();
            med.getMedicionId().setIndicadorId(indicador.getIndicadorId());
            respuesta = persistenceSession.insert(med, usuario);
            if (respuesta != 10000) {
              break;
            }
          }
        }
      }
    }
    return respuesta;
  }
  
  public int protegerMedicionesIndicadores(List<Indicador> indicadores, Map<?, ?> filtros, Boolean liberar)
  {
    boolean transActiva = false;
    int resDb = 10000;
    int res = 0;
    boolean updatedRows = false;
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      List<Long> indicadoresIds = null;
      List<Frecuencia> frecuencias = Frecuencia.getFrecuencias();
      for (Iterator<Frecuencia> i = frecuencias.iterator(); i.hasNext();)
      {
        Frecuencia frecuencia = (Frecuencia)i.next();
        indicadoresIds = new ArrayList();
        for (Iterator<Indicador> iter = indicadores.iterator(); iter.hasNext();)
        {
          Indicador indicador = (Indicador)iter.next();
          if (indicador.getFrecuencia().byteValue() == frecuencia.getFrecuenciaId().byteValue()) {
            indicadoresIds.add(indicador.getIndicadorId());
          }
        }
        if (indicadoresIds.size() > 0)
        {
          HashMap<String, Object> newfiltros = new HashMap();
          
          newfiltros.put("indicadores", indicadoresIds);
          
          if (filtros.containsKey("series"))
            newfiltros.put("series", filtros.get("series"));
          if (filtros.containsKey("serieId")) {
            newfiltros.put("serieId", filtros.get("serieId"));
          }
          if (filtros.containsKey("fechaFinal")) {
            newfiltros.put("fechaFinal", filtros.get("fechaFinal"));
          } else if ((filtros.containsKey("anoDesde")) || (filtros.containsKey("anoHasta")))
          {
            if (filtros.containsKey("anoDesde"))
              newfiltros.put("anoDesde", filtros.get("anoDesde"));
            if (filtros.containsKey("anoHasta"))
              newfiltros.put("anoHasta", filtros.get("anoHasta"));
            if (filtros.containsKey("mesDesde"))
              newfiltros.put("periodoDesde", new Integer(PeriodoUtil.getPeriodoMesAnoDesde(((Integer)filtros.get("anoDesde")).intValue(), ((Integer)filtros.get("mesDesde")).intValue(), frecuencia.getFrecuenciaId().byteValue())));
            if (filtros.containsKey("mesHasta")) {
              newfiltros.put("periodoHasta", new Integer(PeriodoUtil.getPeriodoMesAnoHasta(((Integer)filtros.get("anoHasta")).intValue(), ((Integer)filtros.get("mesHasta")).intValue(), frecuencia.getFrecuenciaId().byteValue())));
            }
          }
          res = persistenceSession.protegerMediciones(newfiltros, liberar);
          
          if (res > 0) {
            updatedRows = true;
          }
        }
      }
      if (transActiva)
      {
        persistenceSession.commitTransaction();
        transActiva = false;
      }
      
      if (updatedRows) {
        resDb = 10000;
      } else {
        resDb = 10001;
      }
    }
    catch (Throwable t) {
      if (transActiva)
      {
        persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    
    return resDb;
  }
  
  public List getCategoriasIndicador(Long indicadorId)
  {
    return persistenceSession.getCategoriasIndicador(indicadorId);
  }
  
  public Indicador getIndicadorBasico(Long indicadorId)
  {
    return persistenceSession.getIndicadorBasico(indicadorId);
  }
  
  public Indicador getIndicadorBasicoAndLockForUse(Long indicadorId, String instancia)
  {
    Indicador indicador = null;
    Object[] idsToUse = new Object[1];
    idsToUse[0] = indicadorId;
    if (persistenceSession.lockForUse(instancia, idsToUse))
    {
      indicador = getIndicadorBasico(indicadorId);
      if (indicador == null) {
        persistenceSession.unlockObject(instancia, indicadorId);
      }
    }
    return indicador;
  }
  
  public List getRutaCompletaIndicador(Long indicadorId)
  {
    Indicador indicador = (Indicador)load(Indicador.class, indicadorId);
    
    ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService(this);
    
    List rutaOrganizaciones = arbolesService.getRutaCompletaNombres(indicador.getOrganizacion());
    
    List rutaClases = arbolesService.getRutaCompletaNombres(indicador.getClase());
    
    rutaOrganizaciones.addAll(rutaClases);
    
    return rutaClases;
  }
  
  public String getRutaCompletaIndicador(Long indicadorId, String separador)
  {
    Indicador indicador = (Indicador)load(Indicador.class, indicadorId);
    
    return getRutaCompletaIndicador(indicador, separador);
  }
  
  public String getRutaCompletaIndicador(Indicador indicador, String separador)
  {
    StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService(this);
    
    String resultado = strategosOrganizacionesService.getRutaCompletaNombresOrganizacion(indicador.getOrganizacionId(), separador);
    
    strategosOrganizacionesService.close();
    
    StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService(this);
    
    resultado = resultado + separador + strategosClasesIndicadoresService.getRutaCompletaNombresClaseIndicadores(indicador.getClaseId(), separador);
    
    strategosClasesIndicadoresService.close();
    
    return resultado;
  }
  
  public int actualizarDatosIndicador(Long indicadorId, Double ultimaMedicion, Double ultimoProgramado, String fechaUltimaMedicion) throws Throwable {
    return persistenceSession.actualizarDatosIndicador(indicadorId, ultimaMedicion, ultimoProgramado, fechaUltimaMedicion);
  }
  
  public Byte getFrecuenciaMaximaIndicadoresPlan(Long planId) {
    return persistenceSession.getFrecuenciaMaximaIndicadoresPlan(planId);
  }
  
  public Long getNumeroIndicadoresPorClase(Long claseId, Map filtros)
  {
    if (filtros == null)
      filtros = new HashMap();
    filtros.put("claseId", claseId.toString());
    
    return persistenceSession.getNumeroIndicadores(filtros);
  }
  
  public int getNumeroUsosComoIndicadorInsumo(Long indicadorId) {
    return persistenceSession.getNumeroUsosComoIndicadorInsumo(indicadorId);
  }
  
  public Indicador getIndicadorProgramado(Long indicadorId, Byte revision) {
    return persistenceSession.getIndicadorProgramado(indicadorId, revision);
  }
  
  public Indicador getIndicadorProgramado(Long indicadorId) {
    return persistenceSession.getIndicadorProgramado(indicadorId, null);
  }
  
  public Indicador getIndicadorMaximo(Long indicadorId) {
    return persistenceSession.getIndicadorMaximo(indicadorId);
  }
  
  public Indicador getIndicadorMinimo(Long indicadorId) {
    return persistenceSession.getIndicadorMinimo(indicadorId);
  }
  
  public Long getNumeroIndicadoresPorPlan(Long planId, Map filtros)
  {
    if (filtros == null) {
      filtros = new HashMap();
    }
    filtros.put("planId", planId.toString());
    
    return persistenceSession.getNumeroIndicadores(filtros);
  }
  
  public Long getNumeroIndicadoresPorPerspectiva(Long perspectivaId, Map filtros)
  {
    if (filtros == null) {
      filtros = new HashMap();
    }
    filtros.put("perspectivaId", perspectivaId.toString());
    
    return persistenceSession.getNumeroIndicadores(filtros);
  }
  
  public int updateCampo(Long indicadorId, Map<?, ?> filtros) throws Throwable
  {
    return persistenceSession.updateCampo(indicadorId, filtros);
  }
  
  public int updateAlerta(Long indicadorId, Byte alerta) throws Throwable
  {
    return persistenceSession.updateAlerta(indicadorId, alerta);
  }
  
  public boolean tieneIndicadorProgramado(Long indicadorId) {
    return persistenceSession.tieneIndicadorProgramado(indicadorId);
  }
  
  public boolean tieneIndicadorMinimo(Long indicadorId) {
    return persistenceSession.tieneIndicadorMinimo(indicadorId);
  }
  
  public boolean tieneIndicadorMaximo(Long indicadorId)
  {
    return persistenceSession.tieneIndicadorMaximo(indicadorId);
  }
  
  public List<Indicador> getIndicadoresNombreFrecuencia(String orden, String tipoOrden, List<String> campos, boolean total, Map<String, Object> filtro)
  {
    return persistenceSession.getIndicadoresNombreFrecuencia(orden, tipoOrden, campos, total, filtro);
  }
  
  public List<Long> getIndicadoresIds(Map<String, Object> filtro)
  {
    return persistenceSession.getIndicadoresIds(filtro);
  }
  
  public List<Indicador> getIndicadoresParaEjecutar(Long indicadorId, List<Long> indicadoresIds, Long claseId, boolean subclases, Long organizacionId, boolean arbolCompletoOrganizacion, boolean todasOrganizaciones, Long perspectivaId, Long planId)
  {
    Map<String, Object> filtros = new HashMap();
    List<Long> listaIds = new ArrayList();
    List<Comparable> campoIdList = new ArrayList();
    List<String> orderList = new ArrayList();
    
    List<Indicador> indicadores = null;
    
    if (indicadorId != null)
    {
      filtros.put("indicadorId", indicadorId);
      indicadores = getIndicadores(filtros);
    }
    else if (indicadoresIds != null)
    {
      filtros.put("indicadores", indicadoresIds);
      indicadores = getIndicadores(filtros);
    }
    else if ((claseId != null) && (!subclases))
    {
      ClaseIndicadores clase = (ClaseIndicadores)load(ClaseIndicadores.class, claseId);
      if (clase != null)
      {
        filtros.put("claseId", claseId);
        
        orderList.add("asc");
        orderList.add("claseId");
        filtros.put("orderBy", orderList);
        
        indicadores = getIndicadores(filtros);
      }
    }
    else if ((claseId != null) && (subclases))
    {
      ClaseIndicadores clase = (ClaseIndicadores)load(ClaseIndicadores.class, claseId);
      if (clase != null)
      {
        if (clase.getPadreId() == null)
        {
          filtros.put("claseId", claseId);
          
          orderList.add("asc");
          orderList.add("claseId");
          filtros.put("orderBy", orderList);
        }
        else
        {
          StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService(this);
          listaIds = strategosClasesIndicadoresService.getArbolCompletoClaseIndicadores(claseId);
          
          campoIdList = new ArrayList();
          
          for (Iterator i = listaIds.iterator(); i.hasNext();)
          {
            ClaseIndicadores claseIndicadicadores = (ClaseIndicadores)i.next();
            campoIdList.add(claseIndicadicadores.getClaseId().toString());
          }
          
          filtros.put("claseId", campoIdList);
          orderList.add("asc");
          orderList.add("claseId");
          filtros.put("orderBy", orderList);
          strategosClasesIndicadoresService.close();
        }
        
        indicadores = getIndicadores(filtros);
      }
    }
    else if (perspectivaId != null)
    {
      campoIdList = new ArrayList();
      
      ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
      Perspectiva perspectiva = (Perspectiva)arbolesService.load(Perspectiva.class, perspectivaId);
      if (perspectiva != null)
      {
        String[] ids = arbolesService.getRutaCompletaIds(perspectiva, ",").split(",");
        
        campoIdList = new ArrayList();
        for (int i = 0; i < ids.length; i++) {
          campoIdList.add(ids[i]);
        }
        filtros.put("perspectivaId", campoIdList);
      }
      
      filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
      arbolesService.close();
      
      indicadores = getIndicadores(filtros);
    }
    else if (planId != null)
    {
      StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService(this);
      
      indicadores = new ArrayList();
      Map indicadoresIdsLocal = new HashMap();
      
      campoIdList = new ArrayList();
      Map filtrosPerspectivas = new HashMap();
      String[] orden = new String[1];
      String[] tipoOrden = new String[1];
      orden[0] = "perspectivaId";
      tipoOrden[0] = "asc";
      filtrosPerspectivas.put("planId", planId.toString());
      List perspectivasPlan = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtrosPerspectivas);
      
      for (Iterator iter = perspectivasPlan.iterator(); iter.hasNext();)
      {
        Perspectiva perspectiva = (Perspectiva)iter.next();
        campoIdList.add(perspectiva.getPerspectivaId());
      }
      
      strategosPerspectivasService.close();
      
      filtros.put("perspectivaId", campoIdList);
      filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
      
      List indicadoresPerspectivas = getIndicadores(filtros);
      for (Iterator iter = indicadoresPerspectivas.iterator(); iter.hasNext();)
      {
        Indicador indicador = (Indicador)iter.next();
        if (!indicadoresIdsLocal.containsKey(indicador.getIndicadorId()))
        {
          indicadores.add(indicador);
          indicadoresIdsLocal.put(indicador.getIndicadorId(), indicador.getIndicadorId());
        }
      }
      
      filtros = new HashMap();
      filtros.put("noCualitativos", "true");
      filtros.put("planId", planId);
      filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
      
      List indicadoresPlan = getIndicadores(filtros);
      for (Iterator iter = indicadoresPlan.iterator(); iter.hasNext();)
      {
        Indicador indicador = (Indicador)iter.next();
        
        if (!indicadoresIdsLocal.containsKey(indicador.getIndicadorId()))
        {
          indicadores.add(indicador);
          indicadoresIdsLocal.put(indicador.getIndicadorId(), indicador.getIndicadorId());
        }
      }
    }
    else if (todasOrganizaciones)
    {
      orderList.add("asc");
      orderList.add("organizacionId");
      orderList.add("asc");
      orderList.add("claseId");
      filtros.put("orderBy", orderList);
      
      indicadores = getIndicadores(filtros);
    }
    else if (organizacionId != null)
    {
      if (arbolCompletoOrganizacion)
      {
        StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService(this);
        listaIds = strategosOrganizacionesService.getArbolCompletoOrganizacion(organizacionId);
        strategosOrganizacionesService.close();
        
        campoIdList = new ArrayList();
        
        for (Iterator i = listaIds.iterator(); i.hasNext();)
        {
          Organizacion organizacion = (Organizacion)i.next();
          campoIdList.add(organizacion.getOrganizacionId().toString());
        }
        
        filtros.put("organizacionId", campoIdList);
        
        orderList.add("asc");
        orderList.add("organizacionId");
        orderList.add("asc");
        orderList.add("claseId");
        
        filtros.put("orderBy", orderList);
        
        indicadores = getIndicadores(filtros);
      }
      else
      {
        filtros.put("organizacionId", organizacionId);
        
        orderList.add("asc");
        orderList.add("claseId");
        filtros.put("orderBy", orderList);
        
        indicadores = getIndicadores(filtros);
      }
    }
    
    return indicadores;
  }
  
  public Double zonaAmarilla(Indicador indicador, Integer ano, Integer periodo, Double meta, Double zonaVerde, StrategosMedicionesService strategosMedicionesService)
  {
    Double zonaAmarilla = null;
    Double porcentajeAlertaZonaAmarilla = null;
    
    if ((indicador.getAlertaMetaZonaVerde() == null) && (indicador.getAlertaMetaZonaAmarilla() == null) && (((indicador.getAlertaTipoZonaVerde() == null) && (indicador.getAlertaTipoZonaAmarilla() == null)) || ((indicador.getAlertaTipoZonaVerde().equals(TipoAlerta.getTipoAlertaPorcentaje())) && (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaPorcentaje())))))
    {
      OrganizacionStrategos organizacion = (OrganizacionStrategos)persistenceSession.load(OrganizacionStrategos.class, indicador.getOrganizacionId());
      if (organizacion.getPorcentajeZonaAmarillaMetaIndicadores() != null) {
        porcentajeAlertaZonaAmarilla = new Double(organizacion.getPorcentajeZonaAmarillaMetaIndicadores().doubleValue());
      }
    }
    if (indicador.getCaracteristica().equals(Caracteristica.getCaracteristicaRetoAumento())) {
      zonaAmarilla = zonaAmarillaIndicadorPorProgramadoRetoAumento(indicador, ano, periodo, meta, porcentajeAlertaZonaAmarilla, zonaVerde, strategosMedicionesService);
    } else if (indicador.getCaracteristica().equals(Caracteristica.getCaracteristicaRetoDisminucion())) {
      zonaAmarilla = zonaAmarillaIndicadorPorProgramadoRetoDisminucion(indicador, ano, periodo, meta, porcentajeAlertaZonaAmarilla, zonaVerde, strategosMedicionesService);
    } else if (indicador.getCaracteristica().equals(Caracteristica.getCaracteristicaCondicionValorMaximo())) {
      zonaAmarilla = zonaAmarillaIndicadorPorProgramadoValorMaximo(indicador, ano, periodo, meta, porcentajeAlertaZonaAmarilla, strategosMedicionesService);
    } else {
      zonaAmarilla = zonaAmarillaIndicadorPorProgramadoValorMinimo(indicador, ano, periodo, meta, porcentajeAlertaZonaAmarilla, strategosMedicionesService);
    }
    return zonaAmarilla;
  }
  
  public Double zonaVerde(Indicador indicador, Integer ano, Integer periodo, Double meta, StrategosMedicionesService strategosMedicionesService)
  {
    Double zonaVerde = null;
    Double porcentajeAlertaZonaVerde = null;
    
    if ((indicador.getAlertaMetaZonaVerde() == null) && (indicador.getAlertaMetaZonaAmarilla() == null) && (((indicador.getAlertaTipoZonaVerde() == null) && (indicador.getAlertaTipoZonaAmarilla() == null)) || ((indicador.getAlertaTipoZonaVerde().equals(TipoAlerta.getTipoAlertaPorcentaje())) && (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaPorcentaje())))))
    {
      OrganizacionStrategos organizacion = (OrganizacionStrategos)persistenceSession.load(OrganizacionStrategos.class, indicador.getOrganizacionId());
      if (organizacion.getPorcentajeZonaVerdeMetaIndicadores() != null) {
        porcentajeAlertaZonaVerde = new Double(organizacion.getPorcentajeZonaVerdeMetaIndicadores().doubleValue());
      }
    }
    if (indicador.getCaracteristica().equals(Caracteristica.getCaracteristicaRetoAumento())) {
      zonaVerde = zonaVerdeIndicadorPorProgramadoRetoAumento(indicador, ano, periodo, meta, porcentajeAlertaZonaVerde, strategosMedicionesService);
    } else if (indicador.getCaracteristica().equals(Caracteristica.getCaracteristicaRetoDisminucion())) {
      zonaVerde = zonaVerdeIndicadorPorProgramadoRetoDisminucion(indicador, ano, periodo, meta, porcentajeAlertaZonaVerde, strategosMedicionesService);
    }
    return zonaVerde;
  }
  
  private Double zonaAmarillaIndicadorPorProgramadoRetoAumento(Indicador indicador, Integer ano, Integer periodo, Double meta, Double porcentajeAlertaZonaAmarilla, Double zonaVerde, StrategosMedicionesService strategosMedicionesService)
  {
    Double zonaAmarilla = null;
    
    if ((indicador.getAlertaTipoZonaAmarilla() == null) || (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaPorcentaje())))
    {
      zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
      if ((zonaAmarilla == null) && (porcentajeAlertaZonaAmarilla != null))
        zonaAmarilla = porcentajeAlertaZonaAmarilla;
      if ((zonaAmarilla != null) && (meta != null) && (zonaVerde != null))
        zonaAmarilla = new Double(zonaVerde.doubleValue() - MathUtil.sign(meta.doubleValue()) * meta.doubleValue() * (zonaAmarilla.doubleValue() / 100.0D));
      if ((zonaAmarilla == null) && (porcentajeAlertaZonaAmarilla == null)) {
        zonaAmarilla = Double.valueOf(0.0D);
      }
    } else if (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaValorAbsolutoMagnitud()))
    {
      zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
      if ((zonaAmarilla != null) && (indicador.getAlertaValorVariableZonaAmarilla().booleanValue()) && (zonaVerde != null)) {
        zonaAmarilla = new Double(zonaVerde.doubleValue() - zonaAmarilla.doubleValue());
      } else if (zonaAmarilla != null) {
        zonaAmarilla = new Double(zonaAmarilla.doubleValue());
      }
    }
    else {
      List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getAlertaIndicadorIdZonaAmarilla(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ano, ano, periodo, periodo);
      
      if (mediciones.size() > 0)
      {
        Medicion medicionAlertaZonaAmarilla = (Medicion)mediciones.get(0);
        if (medicionAlertaZonaAmarilla.getValor() != null)
        {
          if ((indicador.getAlertaValorVariableZonaAmarilla().booleanValue()) && (zonaVerde != null)) {
            zonaAmarilla = new Double(zonaVerde.doubleValue() - medicionAlertaZonaAmarilla.getValor().doubleValue());
          } else {
            zonaAmarilla = medicionAlertaZonaAmarilla.getValor();
          }
        }
      }
    }
    return zonaAmarilla;
  }
  
  private Double zonaAmarillaIndicadorPorProgramadoRetoDisminucion(Indicador indicador, Integer ano, Integer periodo, Double meta, Double porcentajeAlertaZonaAmarilla, Double zonaVerde, StrategosMedicionesService strategosMedicionesService)
  {
    Double zonaAmarilla = null;
    
    if ((indicador.getAlertaTipoZonaAmarilla() == null) || (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaPorcentaje())))
    {
      zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
      if ((zonaAmarilla == null) && (porcentajeAlertaZonaAmarilla != null))
        zonaAmarilla = porcentajeAlertaZonaAmarilla;
      if ((zonaAmarilla != null) && (meta != null) && (zonaVerde != null))
        zonaAmarilla = new Double(zonaVerde.doubleValue() + MathUtil.sign(meta.doubleValue()) * meta.doubleValue() * (zonaAmarilla.doubleValue() / 100.0D));
      if ((zonaAmarilla == null) && (porcentajeAlertaZonaAmarilla == null)) {
        zonaAmarilla = Double.valueOf(0.0D);
      }
    } else if (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaValorAbsolutoMagnitud()))
    {
      zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
      if ((zonaAmarilla != null) && (indicador.getAlertaValorVariableZonaAmarilla().booleanValue()) && (zonaVerde != null)) {
        zonaAmarilla = new Double(zonaVerde.doubleValue() + zonaAmarilla.doubleValue());
      } else if (zonaAmarilla != null) {
        zonaAmarilla = new Double(zonaAmarilla.doubleValue());
      }
    }
    else {
      List mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getAlertaIndicadorIdZonaAmarilla(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ano, ano, periodo, periodo);
      
      if (mediciones.size() > 0)
      {
        Medicion medicionAlertaZonaAmarilla = (Medicion)mediciones.get(0);
        if (medicionAlertaZonaAmarilla.getValor() != null)
        {
          if ((indicador.getAlertaValorVariableZonaAmarilla().booleanValue()) && (zonaVerde != null)) {
            zonaAmarilla = new Double(zonaVerde.doubleValue() + medicionAlertaZonaAmarilla.getValor().doubleValue());
          } else {
            zonaAmarilla = medicionAlertaZonaAmarilla.getValor();
          }
        }
      }
    }
    return zonaAmarilla;
  }
  
  private Double zonaAmarillaIndicadorPorProgramadoValorMaximo(Indicador indicador, Integer ano, Integer periodo, Double meta, Double porcentajeAlertaZonaAmarilla, StrategosMedicionesService strategosMedicionesService)
  {
    Double zonaAmarilla = null;
    
    if ((indicador.getAlertaTipoZonaAmarilla() == null) || (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaPorcentaje())))
    {
      zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
      if ((zonaAmarilla == null) && (porcentajeAlertaZonaAmarilla != null))
        zonaAmarilla = porcentajeAlertaZonaAmarilla;
      if ((zonaAmarilla != null) && (meta != null))
        zonaAmarilla = new Double(meta.doubleValue() * (1.0D - MathUtil.sign(meta.doubleValue()) * zonaAmarilla.doubleValue() / 100.0D));
      if ((zonaAmarilla == null) && (porcentajeAlertaZonaAmarilla == null)) {
        zonaAmarilla = Double.valueOf(0.0D);
      }
    } else if (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaValorAbsolutoMagnitud()))
    {
      zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
      if ((zonaAmarilla != null) && (indicador.getAlertaValorVariableZonaAmarilla().booleanValue()) && (meta != null)) {
        zonaAmarilla = new Double(meta.doubleValue() - zonaAmarilla.doubleValue());
      } else if (zonaAmarilla != null) {
        zonaAmarilla = new Double(zonaAmarilla.doubleValue());
      }
    }
    else {
      List mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getAlertaIndicadorIdZonaAmarilla(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ano, ano, periodo, periodo);
      
      if (mediciones.size() > 0)
      {
        Medicion medicionAlertaZonaAmarilla = (Medicion)mediciones.get(0);
        if (medicionAlertaZonaAmarilla.getValor() != null)
        {
          if ((indicador.getAlertaValorVariableZonaAmarilla().booleanValue()) && (meta != null)) {
            zonaAmarilla = new Double(meta.doubleValue() - medicionAlertaZonaAmarilla.getValor().doubleValue());
          } else {
            zonaAmarilla = medicionAlertaZonaAmarilla.getValor();
          }
        }
      }
    }
    return zonaAmarilla;
  }
  
  private Double zonaAmarillaIndicadorPorProgramadoValorMinimo(Indicador indicador, Integer ano, Integer periodo, Double meta, Double porcentajeAlertaZonaAmarilla, StrategosMedicionesService strategosMedicionesService)
  {
    Double zonaAmarilla = null;
    
    if ((indicador.getAlertaTipoZonaAmarilla() == null) || (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaPorcentaje())))
    {
      zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
      if ((zonaAmarilla == null) && (porcentajeAlertaZonaAmarilla != null))
        zonaAmarilla = porcentajeAlertaZonaAmarilla;
      if ((zonaAmarilla != null) && (meta != null))
        zonaAmarilla = new Double(meta.doubleValue() + MathUtil.sign(meta.doubleValue()) * meta.doubleValue() * (zonaAmarilla.doubleValue() / 100.0D));
      if ((zonaAmarilla == null) && (porcentajeAlertaZonaAmarilla == null)) {
        zonaAmarilla = Double.valueOf(0.0D);
      }
    } else if (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaValorAbsolutoMagnitud()))
    {
      zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
      if ((zonaAmarilla != null) && (indicador.getAlertaValorVariableZonaAmarilla().booleanValue()) && (meta != null)) {
        zonaAmarilla = new Double(meta.doubleValue() + zonaAmarilla.doubleValue());
      } else if (zonaAmarilla != null) {
        zonaAmarilla = new Double(zonaAmarilla.doubleValue());
      }
    }
    else
    {
      List mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getAlertaIndicadorIdZonaAmarilla(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ano, ano, periodo, periodo);
      
      if (mediciones.size() > 0)
      {
        Medicion medicionAlertaZonaAmarilla = (Medicion)mediciones.get(0);
        if (medicionAlertaZonaAmarilla.getValor() != null)
        {
          if ((indicador.getAlertaValorVariableZonaAmarilla().booleanValue()) && (meta != null)) {
            zonaAmarilla = new Double(meta.doubleValue() + medicionAlertaZonaAmarilla.getValor().doubleValue());
          } else {
            zonaAmarilla = medicionAlertaZonaAmarilla.getValor();
          }
        }
      }
    }
    return zonaAmarilla;
  }
  
  private Double zonaVerdeIndicadorPorProgramadoRetoAumento(Indicador indicador, Integer ano, Integer periodo, Double meta, Double porcentajeAlertaZonaVerde, StrategosMedicionesService strategosMedicionesService)
  {
    Double zonaVerde = null;
    
    if ((indicador.getAlertaTipoZonaVerde() == null) || (indicador.getAlertaTipoZonaVerde().equals(TipoAlerta.getTipoAlertaPorcentaje())))
    {
      zonaVerde = indicador.getAlertaMetaZonaVerde();
      if ((zonaVerde == null) && (porcentajeAlertaZonaVerde != null))
        zonaVerde = porcentajeAlertaZonaVerde;
      if ((zonaVerde != null) && (meta != null))
        zonaVerde = new Double(meta.doubleValue() * (1.0D - MathUtil.sign(meta.doubleValue()) * zonaVerde.doubleValue() / 100.0D));
      if ((zonaVerde == null) && (porcentajeAlertaZonaVerde == null)) {
        zonaVerde = Double.valueOf(0.0D);
      }
    } else if (indicador.getAlertaTipoZonaVerde().equals(TipoAlerta.getTipoAlertaValorAbsolutoMagnitud()))
    {
      zonaVerde = indicador.getAlertaMetaZonaVerde();
      if ((zonaVerde != null) && (indicador.getAlertaValorVariableZonaVerde().booleanValue()) && (meta != null)) {
        zonaVerde = new Double(meta.doubleValue() - zonaVerde.doubleValue());
      } else if (zonaVerde != null) {
        zonaVerde = new Double(zonaVerde.doubleValue());
      }
    }
    else {
      List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getAlertaIndicadorIdZonaVerde(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ano, ano, periodo, periodo);
      
      if (mediciones.size() > 0)
      {
        Medicion medicionAlertaZonaVerde = (Medicion)mediciones.get(0);
        if (medicionAlertaZonaVerde.getValor() != null)
        {
          if ((indicador.getAlertaValorVariableZonaVerde().booleanValue()) && (meta != null)) {
            zonaVerde = new Double(meta.doubleValue() - medicionAlertaZonaVerde.getValor().doubleValue());
          } else {
            zonaVerde = medicionAlertaZonaVerde.getValor();
          }
        }
      }
    }
    return zonaVerde;
  }
  
  private Double zonaVerdeIndicadorPorProgramadoRetoDisminucion(Indicador indicador, Integer ano, Integer periodo, Double meta, Double porcentajeAlertaZonaVerde, StrategosMedicionesService strategosMedicionesService)
  {
    Double zonaVerde = null;
    
    if ((indicador.getAlertaTipoZonaVerde() == null) || (indicador.getAlertaTipoZonaVerde().equals(TipoAlerta.getTipoAlertaPorcentaje())))
    {
      zonaVerde = indicador.getAlertaMetaZonaVerde();
      if ((zonaVerde == null) && (porcentajeAlertaZonaVerde != null))
        zonaVerde = porcentajeAlertaZonaVerde;
      if ((zonaVerde != null) && (meta != null))
        zonaVerde = new Double(meta.doubleValue() * (1.0D + MathUtil.sign(meta.doubleValue()) * zonaVerde.doubleValue() / 100.0D));
      if ((zonaVerde == null) && (porcentajeAlertaZonaVerde == null)) {
        zonaVerde = Double.valueOf(0.0D);
      }
    } else if (indicador.getAlertaTipoZonaVerde().equals(TipoAlerta.getTipoAlertaValorAbsolutoMagnitud()))
    {
      zonaVerde = indicador.getAlertaMetaZonaVerde();
      if ((zonaVerde != null) && (indicador.getAlertaValorVariableZonaVerde().booleanValue()) && (meta != null)) {
        zonaVerde = new Double(meta.doubleValue() + zonaVerde.doubleValue());
      } else if (zonaVerde != null) {
        zonaVerde = new Double(zonaVerde.doubleValue());
      }
    }
    else {
      List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getAlertaIndicadorIdZonaVerde(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ano, ano, periodo, periodo);
      
      if (mediciones.size() > 0)
      {
        Medicion medicionAlertaZonaVerde = (Medicion)mediciones.get(0);
        if (medicionAlertaZonaVerde.getValor() != null)
        {
          if ((indicador.getAlertaValorVariableZonaVerde().booleanValue()) && (meta != null)) {
            zonaVerde = new Double(meta.doubleValue() + medicionAlertaZonaVerde.getValor().doubleValue());
          } else {
            zonaVerde = medicionAlertaZonaVerde.getValor();
          }
        }
      }
    }
    return zonaVerde;
  }
  
  public int saveCodigoEnlace(Long indicadorId, Long organizacionId, String codigoEnlace) throws Throwable
  {
    boolean transActiva = false;
    int resultado = 10000;
    
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      resultado = persistenceSession.saveCodigoEnlace(indicadorId, organizacionId, codigoEnlace);
      
      if (transActiva)
      {
        if (resultado == 10000) {
          persistenceSession.commitTransaction();
        } else
          persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva)
        persistenceSession.rollbackTransaction();
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return resultado;
  }
  
  public boolean checkLicencia(HttpServletRequest request)
  {
    boolean tienePermiso = true;
    
    try
    {
      Licencia licencia = new Licencia().getLicencia(request);
      if (!licencia.getChequearLicencia().booleanValue())
      {
        Long numeroIndicadores = persistenceSession.getNumeroIndicadores(null);
        if ((numeroIndicadores != null) && (numeroIndicadores.longValue() > 0L))
        {
          if (numeroIndicadores.longValue() >= licencia.getNumeroIndicadores().intValue()) {
            tienePermiso = false;
          }
        }
      }
    }
    catch (Exception localException) {}
    

    return tienePermiso;
  }
  
  public boolean esInsumo(Long indicadorId)
  {
    return persistenceSession.esInsumo(indicadorId);
  }
  
  public ConfiguracionIndicador getConfiguracionIndicador()
  {
    ConfiguracionIndicador configuracionIndicador = new ConfiguracionIndicador();
    try
    {
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
      Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Configuracion.Indicadores");
      frameworkService.close();
      
      if (configuracion != null)
      {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("properties");
        Element eElement = (Element)nList.item(0);
        
        configuracionIndicador.setIndicadorNivel(Integer.valueOf(Integer.parseInt(VgcAbstractService.getTagValue("nivel", eElement))));
      }
      else
      {
        VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
        
        configuracionIndicador.setIndicadorNivel(Integer.valueOf(2));
      }
    }
    catch (Exception localException) {}
    


    return configuracionIndicador;
  }
  
  public int saveSerieIndicador(Long indicadorId, Set<SerieIndicador> seriesIndicador)
  {
    int resDb = 10000;
    boolean updatedRows = false;
    int res = 0;
    try
    {
      List<Long> indicadoresIds = new ArrayList();
      indicadoresIds.add(indicadorId);
      HashMap<String, Object> newfiltros = new HashMap();
      for (Iterator<?> i = seriesIndicador.iterator(); i.hasNext();)
      {
        SerieIndicador serie = (SerieIndicador)i.next();
        
        newfiltros = new HashMap();
        newfiltros.put("indicadores", indicadoresIds);
        newfiltros.put("serieId", serie.getPk().getSerieId());
        newfiltros.put("fechaFinal", serie.getFechaBloqueo());
        res = persistenceSession.protegerMediciones(newfiltros, null);
        if (res > 0) {
          updatedRows = true;
        }
      }
      if (updatedRows) {
        resDb = 10000;
      } else {
        resDb = 10001;
      }
    }
    catch (Throwable localThrowable) {}
    

    return resDb;
  }
  
  public List<InsumoFormula> getInsumosFormula(Long indicadorId, Long serieId)
  {
    List<InsumoFormula> insumos = persistenceSession.getInsumosFormula(indicadorId, serieId);
    
    return insumos;
  }
  
  public Indicador getIndicador(Long indicadorId)
  {
	 return persistenceSession.getIndicador(indicadorId);
  }
  
  public Formula getFormulaIndicador(Long indicadorId, Long serieId){
	return persistenceSession.getFormulaIndicador(indicadorId, serieId);  
  }


  public Indicador getIndicador(Long organizacionId, String nombre) {
	return persistenceSession.getIndicador(organizacionId, nombre);
  }

	
}
