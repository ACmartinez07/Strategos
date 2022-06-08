package com.visiongc.app.strategos.portafolios.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.ConfiguracionIniciativa;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.IndicadorPortafolio;
import com.visiongc.app.strategos.portafolios.model.IndicadorPortafolioPK;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativa;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativaPK;
import com.visiongc.app.strategos.portafolios.persistence.StrategosPortafoliosPersistenceSession;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrategosPortafoliosServiceImpl extends StrategosServiceImpl implements StrategosPortafoliosService
{
  private StrategosPortafoliosPersistenceSession persistenceSession = null;
  
  public StrategosPortafoliosServiceImpl(StrategosPortafoliosPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getPortafolios(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, String> filtros)
  {
    return persistenceSession.getPortafolios(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  private int deleteDependenciasRestantes(Portafolio portafolio, Usuario usuario)
  {
    int resultado = 10000;
    
    if (portafolio.getClaseId() != null)
    {
      StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService(this);
      ClaseIndicadores clase = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, portafolio.getClaseId());
      if (clase != null)
        resultado = strategosClasesIndicadoresService.deleteClaseIndicadores(clase, Boolean.valueOf(false), usuario);
      strategosClasesIndicadoresService.close();
    }
    
    return resultado;
  }
  
  private int deleteDependencias(Portafolio portafolio, Usuario usuario)
  {
    int resultado = 10000;
    
    StrategosPaginasService strategosPaginasService = StrategosServiceFactory.getInstance().openStrategosPaginasService();
    
    Map<String, String> filtros = new HashMap();
    filtros.put("portafolioId", portafolio.getId().toString());
    
    PaginaLista paginaPaginas = strategosPaginasService.getPaginas(0, 0, "numero", "ASC", true, filtros);
    
    for (Iterator<Pagina> i = paginaPaginas.getLista().iterator(); i.hasNext();)
    {
      Pagina pagina = (Pagina)i.next();
      resultado = strategosPaginasService.deletePagina(pagina, usuario);
      if (resultado != 10000) {
        break;
      }
    }
    strategosPaginasService.close();
    
    return resultado;
  }
  
  public int delete(Portafolio portafolio, Usuario usuario)
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
      
      if (portafolio.getId() != null)
      {
        if ((resultado == 10000) && (portafolio.getPortafolioIndicadores() != null))
        {
          for (Iterator<IndicadorPortafolio> iter = portafolio.getPortafolioIndicadores().iterator(); iter.hasNext();)
          {
            IndicadorPortafolio indicadorPortafolio = (IndicadorPortafolio)iter.next();
            resultado = desasociarIndicadores(indicadorPortafolio, usuario);
            if (resultado != 10000)
              break;
          }
        }
        if (resultado == 10000)
          resultado = deleteDependencias(portafolio, usuario);
        if (resultado == 10000) {
          resultado = persistenceSession.delete(portafolio, usuario);
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
      
      if (resultado == 10000) {
        resultado = deleteDependenciasRestantes(portafolio, usuario);
      }
    }
    catch (Throwable t) {
      if (transActiva)
      {
        persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    
    return resultado;
  }
  
  public int desasociarIndicadores(IndicadorPortafolio indicadorPortafolio, Usuario usuario)
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
      
      if (indicadorPortafolio != null) {
        resultado = persistenceSession.delete(indicadorPortafolio, usuario);
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
  
  public int save(Portafolio portafolio, Boolean borrarMedicion, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[1];
    Object[] fieldValues = new Object[1];
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      fieldNames[0] = "nombre";
      fieldValues[0] = portafolio.getNombre();
      
      if ((portafolio.getId() == null) || (portafolio.getId().longValue() == 0L))
      {
        if (persistenceSession.existsObject(portafolio, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("StrategosWeb");
          
          portafolio.setId(new Long(persistenceSession.getUniqueId()));
          
          resultado = saveClaseIndicadores(portafolio, usuario);
          if (resultado == 10000)
          {
            StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
            ConfiguracionIniciativa configuracionIniciativa = strategosIniciativasService.getConfiguracionIniciativa();
            strategosIniciativasService.close();
            
            resultado = saveIndicadorAutomatico(portafolio, TipoFuncionIndicador.getTipoFuncionSeguimiento(), configuracionIniciativa, usuario);
          }
          
          if (resultado == 10000)
          {
            resultado = persistenceSession.insert(portafolio, usuario);
            if (resultado == 10000) {
              resultado = asociarIndicador(portafolio, usuario);
            }
            if (resultado == 10000)
            {
              Pagina pagina = new Pagina();
              pagina.setPortafolioId(portafolio.getId());
              pagina.setPortafolio(portafolio);
              pagina.setDescripcion(portafolio.getNombre());
              pagina.setFilas(portafolio.getFilas());
              pagina.setColumnas(portafolio.getColumnas());
              pagina.setAncho(portafolio.getAncho());
              pagina.setAlto(portafolio.getAlto());
              pagina.setNumero(Integer.valueOf(1));
              pagina.setPaginaId(new Long(persistenceSession.getUniqueId()));
              
              resultado = persistenceSession.insert(pagina, usuario);
              if (resultado == 10000)
              {

                Celda celda = new Celda();
                celda.setCeldaId(new Long(persistenceSession.getUniqueId()));
                celda.setPagina(pagina);
                celda.setPaginaId(pagina.getPaginaId());
                celda.setTitulo(messageResources.getResource("jsp.editar.grafico.portafolio.estatus.titulo"));
                celda.setFila(Byte.valueOf((byte)1));
                celda.setColumna(Byte.valueOf((byte)1));
                
                resultado = persistenceSession.insert(celda, usuario);
                if (resultado == 10000)
                {

                  celda = new Celda();
                  celda.setCeldaId(new Long(persistenceSession.getUniqueId()));
                  celda.setPagina(pagina);
                  celda.setPaginaId(pagina.getPaginaId());
                  celda.setTitulo(messageResources.getResource("jsp.editar.grafico.portafolio.porcentajes.titulo"));
                  celda.setFila(Byte.valueOf((byte)1));
                  celda.setColumna(Byte.valueOf((byte)2));
                  
                  resultado = persistenceSession.insert(celda, usuario);
                  if (resultado == 10000)
                  {

                    celda = new Celda();
                    celda.setCeldaId(new Long(persistenceSession.getUniqueId()));
                    celda.setPagina(pagina);
                    celda.setPaginaId(pagina.getPaginaId());
                    celda.setTitulo(messageResources.getResource("jsp.editar.grafico.portafolio.tipos.estatus.titulo"));
                    celda.setFila(Byte.valueOf((byte)2));
                    celda.setColumna(Byte.valueOf((byte)1));
                    
                    resultado = persistenceSession.insert(celda, usuario);
                    
                    if (resultado == 10000)
                    {

                      celda = new Celda();
                      celda.setCeldaId(new Long(persistenceSession.getUniqueId()));
                      celda.setPagina(pagina);
                      celda.setPaginaId(pagina.getPaginaId());
                      celda.setTitulo(messageResources.getResource("jsp.editar.grafico.portafolio.avance.titulo"));
                      celda.setFila(Byte.valueOf((byte)2));
                      celda.setColumna(Byte.valueOf((byte)2));
                      
                      resultado = persistenceSession.insert(celda, usuario);
                    }
                  }
                }
              }
            }
          }
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "id";
        idFieldValues[0] = portafolio.getId();
        
        if (borrarMedicion.booleanValue())
        {
          StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
          StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
          
          for (Iterator<IndicadorPortafolio> iter = portafolio.getPortafolioIndicadores().iterator(); iter.hasNext();)
          {
            IndicadorPortafolio indicadorPortafolio = (IndicadorPortafolio)iter.next();
            
            if (indicadorPortafolio.getPk().getIndicadorId() != null)
            {
              if (resultado == 10000) {
                resultado = strategosMedicionesService.deleteMediciones(indicadorPortafolio.getPk().getIndicadorId());
              }
              try {
                if (resultado == 10000) {
                  resultado = strategosIndicadoresService.actualizarDatosIndicador(indicadorPortafolio.getPk().getIndicadorId(), null, null, null);
                }
              }
              catch (Throwable e) {
                resultado = 10003;
              }
            }
            if (resultado != 10000) {
              break;
            }
          }
          strategosMedicionesService.close();
          strategosIndicadoresService.close();
        }
        
        if (resultado == 10000)
        {
          Portafolio portafolioOriginal = getValoresOriginales(portafolio.getId());
          StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
          ConfiguracionIniciativa configuracionIniciativa = strategosIniciativasService.getConfiguracionIniciativa();
          strategosIniciativasService.close();
          if (portafolioOriginal.getFrecuencia().byteValue() != portafolio.getFrecuencia().byteValue()) {
            resultado = updateIndicadorAutomatico(portafolio, TipoFuncionIndicador.getTipoFuncionSeguimiento(), configuracionIniciativa, usuario);
          }
          else {
            StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
            Indicador indicador = null;
            String nombre = "";
            

            if (resultado == 10000)
            {
              Long indicadorId = portafolio.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento());
              if (indicadorId != null)
              {
                indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorId);
                
                nombre = "";
                if (configuracionIniciativa.getIniciativaIndicadorAvanceAnteponer().booleanValue())
                  nombre = configuracionIniciativa.getIniciativaIndicadorAvanceNombre() + " - ";
                nombre = nombre + portafolio.getNombre();
                if (nombre.length() > 100)
                  nombre = nombre.substring(0, 100);
                indicador.setNombre(nombre);
                if (nombre.length() > 50)
                  nombre = nombre.substring(0, 50);
                indicador.setNombreCorto(nombre);
                
                resultado = strategosIndicadoresService.saveIndicador(indicador, usuario);
                if (resultado == 10003)
                {
                  Map<String, Object> filtros = new HashMap();
                  
                  filtros.put("claseId", indicador.getClaseId());
                  filtros.put("nombre", indicador.getNombre());
                  List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
                  if (inds.size() > 0)
                  {
                    indicador = (Indicador)inds.get(0);
                    resultado = 10000;
                  }
                }
              }
            }
          }
        }
        
        if (resultado == 10000)
        {
          Map<String, String> filtros = new HashMap();
          StrategosPaginasService strategosPaginasService = StrategosServiceFactory.getInstance().openStrategosPaginasService();
          filtros = new HashMap();
          filtros.put("portafolioId", portafolio.getId().toString());
          PaginaLista paginaPaginas = strategosPaginasService.getPaginas(0, 0, "numero", "ASC", true, filtros);
          strategosPaginasService.close();
          List<Pagina> paginas = paginaPaginas.getLista();
          if (paginas.size() > 0)
          {
            Pagina pagina = (Pagina)paginas.toArray()[0];
            pagina.setPortafolioId(portafolio.getId());
            pagina.setPortafolio(portafolio);
            pagina.setDescripcion(portafolio.getNombre());
            pagina.setFilas(portafolio.getFilas());
            pagina.setColumnas(portafolio.getColumnas());
            pagina.setAncho(portafolio.getAncho());
            pagina.setAlto(portafolio.getAlto());
            pagina.setNumero(Integer.valueOf(1));
            
            resultado = persistenceSession.update(pagina, usuario);
          }
          strategosPaginasService.close();
          
          if (resultado == 10000)
          {
            if (persistenceSession.existsObject(portafolio, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
              resultado = 10003;
            } else {
              resultado = persistenceSession.update(portafolio, usuario);
            }
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
    }
    catch (Throwable t)
    {
      if (transActiva) {
        persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return resultado;
  }
  
  public int asociarIndicador(Portafolio portafolio, Usuario usuario)
  {
    boolean transActiva = false;
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
      
      for (Iterator<IndicadorPortafolio> iter = portafolio.getPortafolioIndicadores().iterator(); iter.hasNext();)
      {
        IndicadorPortafolio indicadorPortafolio = (IndicadorPortafolio)iter.next();
        
        fieldNames[0] = "pk.indicadorId";
        fieldValues[0] = indicadorPortafolio.getPk().getIndicadorId();
        fieldNames[1] = "pk.portafolioId";
        fieldValues[1] = indicadorPortafolio.getPk().getPortafolioId();
        if (!persistenceSession.existsObject(indicadorPortafolio, fieldNames, fieldValues))
          resultado = persistenceSession.insert(indicadorPortafolio, usuario);
        if (resultado != 10000) {
          break;
        }
      }
      if (transActiva)
      {
        persistenceSession.commitTransaction();
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
  
  private int saveClaseIndicadores(Portafolio portafolio, Usuario usuario)
  {
    StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService(this);
    
    ClaseIndicadores clase = new ClaseIndicadores();
    ClaseIndicadores claseRoot = strategosClasesIndicadoresService.getClaseRaizIniciativa(portafolio.getOrganizacionId(), TipoClaseIndicadores.getTipoClasePlanificacionSeguimiento(), messageResources.getResource("iniciativa.clase.nombre"), usuario);
    
    clase.setPadreId(claseRoot.getClaseId());
    clase.setNombre(portafolio.getNombre());
    clase.setOrganizacionId(portafolio.getOrganizacionId());
    clase.setTipo(TipoClaseIndicadores.getTipoClasePlanificacionSeguimiento());
    clase.setVisible(new Boolean(true));
    
    int resultado = strategosClasesIndicadoresService.saveClaseIndicadores(clase, usuario);
    if (resultado == 10003)
    {
      Map<String, Object> filtros = new HashMap();
      
      filtros.put("organizacionId", clase.getOrganizacionId().toString());
      filtros.put("nombre", clase.getNombre());
      filtros.put("padreId", clase.getPadreId());
      List<ClaseIndicadores> clases = strategosClasesIndicadoresService.getClases(filtros);
      if (clases.size() > 0)
      {
        clase = (ClaseIndicadores)clases.get(0);
        resultado = 10000;
      }
    }
    
    if (resultado == 10000) {
      portafolio.setClaseId(clase.getClaseId());
    }
    strategosClasesIndicadoresService.close();
    
    return resultado;
  }
  
  private int saveIndicadorAutomatico(Portafolio portafolio, Byte tipo, ConfiguracionIniciativa configuracionIniciativa, Usuario usuario)
  {
    int resultado = 10000;
    
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
    Indicador indicador = new Indicador();
    indicador.setOrganizacionId(portafolio.getOrganizacionId());
    indicador.setClaseId(portafolio.getClaseId());
    String nombre = "";
    if ((tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue()) && (configuracionIniciativa.getIniciativaIndicadorAvanceAnteponer().booleanValue())) {
      nombre = configuracionIniciativa.getIniciativaIndicadorAvanceNombre() + " - ";
    } else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue()) {
      nombre = configuracionIniciativa.getIniciativaIndicadorPresupuestoNombre() + " - ";
    } else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficacia().byteValue()) {
      nombre = configuracionIniciativa.getIniciativaIndicadorEficaciaNombre() + " - ";
    } else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficiencia().byteValue())
      nombre = configuracionIniciativa.getIniciativaIndicadorEficienciaNombre() + " - ";
    nombre = nombre + portafolio.getNombre();
    if (nombre.length() > 100)
      nombre = nombre.substring(0, 100);
    indicador.setNombre(nombre);
    if (nombre.length() > 50)
      nombre = nombre.substring(0, 50);
    indicador.setNombreCorto(nombre);
    indicador.setFrecuencia(portafolio.getFrecuencia());
    if (tipo.byteValue() != TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue())
    {
      StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService(this);
      UnidadMedida porcentaje = strategosUnidadesService.getUnidadMedidaPorcentaje();
      indicador.setUnidadId(porcentaje.getUnidadId());
      strategosUnidadesService.close();
    }
    indicador.setPrioridad(com.visiongc.app.strategos.indicadores.model.util.PrioridadIndicador.getPrioridadIndicadorBaja());
    indicador.setMostrarEnArbol(new Boolean(true));
    if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue()) {
      indicador.setCaracteristica(Caracteristica.getCaracteristicaCondicionValorMaximo());
    } else
      indicador.setCaracteristica(Caracteristica.getCaracteristicaRetoAumento());
    indicador.setTipoFuncion(tipo);
    indicador.setGuia(new Boolean(false));
    indicador.setValorInicialCero(new Boolean(true));
    indicador.setNumeroDecimales(new Byte("2"));
    indicador.setSeriesIndicador(new HashSet());
    setSeriesTiempo(indicador);
    indicador.setNaturaleza(Naturaleza.getNaturalezaSimple());
    if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue())
    {
      indicador.setCorte(TipoCorte.getTipoCorteLongitudinal());
      indicador.setTipoCargaMedicion(TipoMedicion.getTipoMedicionAlPeriodo());
    }
    
    if (resultado == 10000)
      resultado = strategosIndicadoresService.saveIndicador(indicador, usuario);
    if (resultado == 10003)
    {
      Map<String, Object> filtros = new HashMap();
      
      filtros.put("claseId", indicador.getClaseId());
      filtros.put("nombre", indicador.getNombre());
      List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
      if (inds.size() > 0)
      {
        indicador = (Indicador)inds.get(0);
        resultado = 10000;
      }
    }
    
    if (resultado == 10000) {
      portafolio.setIndicadorId(indicador.getIndicadorId(), tipo);
    }
    strategosIndicadoresService.close();
    
    return resultado;
  }
  
  public int updateIndicadorAutomatico(Portafolio portafolio, Byte tipo, ConfiguracionIniciativa configuracionIniciativa, Usuario usuario)
  {
    int resultado = 10000;
    
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    Long indicadorId = portafolio.getIndicadorId(tipo);
    if (indicadorId != null)
    {
      Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorId);
      indicador.setFrecuencia(portafolio.getFrecuencia());
      
      String nombre = "";
      if ((tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue()) && (configuracionIniciativa.getIniciativaIndicadorAvanceAnteponer().booleanValue())) {
        nombre = configuracionIniciativa.getIniciativaIndicadorAvanceNombre() + " - ";
      } else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue()) {
        nombre = configuracionIniciativa.getIniciativaIndicadorPresupuestoNombre() + " - ";
      } else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficacia().byteValue()) {
        nombre = configuracionIniciativa.getIniciativaIndicadorEficaciaNombre() + " - ";
      } else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficiencia().byteValue())
        nombre = configuracionIniciativa.getIniciativaIndicadorEficienciaNombre() + " - ";
      nombre = nombre + portafolio.getNombre();
      if (nombre.length() > 100)
        nombre = nombre.substring(0, 100);
      indicador.setNombre(nombre);
      if (nombre.length() > 50)
        nombre = nombre.substring(0, 50);
      indicador.setNombreCorto(nombre);
      
      resultado = strategosIndicadoresService.saveIndicador(indicador, usuario);
      if (resultado == 10003)
      {
        Map<String, Object> filtros = new HashMap();
        
        filtros.put("claseId", indicador.getClaseId());
        filtros.put("nombre", indicador.getNombre());
        List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
        if (inds.size() > 0)
        {
          indicador = (Indicador)inds.get(0);
          resultado = 10000;
        }
      }
    }
    
    strategosIndicadoresService.close();
    
    return resultado;
  }
  
  private void setSeriesTiempo(Indicador indicador)
  {
    indicador.getSeriesIndicador().clear();
    String[] series = new String[2];
    series[0] = SerieTiempo.getSerieRealId().toString();
    if (indicador.getTipoFuncion().byteValue() == TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue()) {
      series[1] = SerieTiempo.getSerieMaximoId().toString();
    } else {
      series[1] = SerieTiempo.getSerieProgramadoId().toString();
    }
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
        serieIndicador.setFormulas(new HashSet());
        serieIndicador.setNaturaleza(Naturaleza.getNaturalezaSimple());
        
        indicador.getSeriesIndicador().add(serieIndicador);
      }
    }
  }
  
  public int asociarIniciativa(Long portafolioId, Long iniciativaId, Usuario usuario)
  {
    boolean transActiva = false;
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
      
      PortafolioIniciativa portafolioIniciativa = new PortafolioIniciativa();
      
      portafolioIniciativa.setPk(new PortafolioIniciativaPK());
      portafolioIniciativa.getPk().setIniciativaId(iniciativaId);
      portafolioIniciativa.getPk().setPortafolioId(portafolioId);
      
      fieldNames[0] = "pk.iniciativaId";
      fieldValues[0] = portafolioIniciativa.getPk().getIniciativaId();
      fieldNames[1] = "pk.portafolioId";
      fieldValues[1] = portafolioIniciativa.getPk().getPortafolioId();
      if (!persistenceSession.existsObject(portafolioIniciativa, fieldNames, fieldValues)) {
        resultado = persistenceSession.insert(portafolioIniciativa, usuario);
      }
      if (transActiva)
      {
        persistenceSession.commitTransaction();
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
  
  public int desasociarIniciativa(Long portafolioId, Long iniciativaId, Usuario usuario)
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
      
      PortafolioIniciativa portafolioIniciativa = new PortafolioIniciativa();
      
      portafolioIniciativa.setPk(new PortafolioIniciativaPK());
      portafolioIniciativa.getPk().setIniciativaId(iniciativaId);
      portafolioIniciativa.getPk().setPortafolioId(portafolioId);
      
      resultado = persistenceSession.delete(portafolioIniciativa, usuario);
      
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
  
  public Portafolio getValoresOriginales(Long portafolioId)
  {
    return persistenceSession.getValoresOriginales(portafolioId);
  }
  
  public int calcular(Portafolio portafolio, Usuario usuario)
  {
      int resultado = 10000;
      boolean transActiva = false;
      try
      {
          if(!persistenceSession.isTransactionActive())
          {
              persistenceSession.beginTransaction();
              transActiva = true;
          }
          StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(this);
          StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
          resultado = strategosMedicionesService.deleteMediciones(portafolio.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
          List portafolioIniciativas = getIniciativasPortafolio(portafolio.getId());
          Indicador indicador = null;
          List mediciones = null;
          int anoDesde = 0;
          int anoHasta = 9999;
          int periodoDesde = 0;
          int periodoHasta = 999;
          Integer anoInicio = null;
          Integer anoFin = null;
          boolean hayPeso = true;
          int totalIniciativas = portafolioIniciativas.size();
          for(Iterator iter = portafolioIniciativas.iterator(); iter.hasNext();)
          {
              PortafolioIniciativa portafolioIniciativa = (PortafolioIniciativa)iter.next();
              if(portafolioIniciativa.getPeso() == null)
                  hayPeso = false;
          }

          List medicionesModificar = new ArrayList();
          Medicion medicionSincronizada = null;
          for(Iterator iter = portafolioIniciativas.iterator(); iter.hasNext();)
          {
              PortafolioIniciativa portafolioIniciativa = (PortafolioIniciativa)iter.next();
              if(portafolioIniciativa.getMediciones() == null)
                  portafolioIniciativa.setMediciones(new ArrayList());
              indicador = (Indicador)strategosIniciativasService.load(Indicador.class, portafolioIniciativa.getIniciativa().getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
              for(Iterator i = indicador.getSeriesIndicador().iterator(); i.hasNext(); portafolioIniciativa.getMediciones().addAll(medicionesModificar))
              {
                  SerieIndicador serie = (SerieIndicador)i.next();
                  mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), serie.getPk().getSerieId(), new Integer(anoDesde), new Integer(anoHasta), new Integer(periodoDesde), new Integer(periodoHasta));
                  medicionesModificar = new ArrayList();
                  for(Iterator iterAportes = mediciones.iterator(); iterAportes.hasNext(); medicionesModificar.add(medicionSincronizada))
                  {
                      Medicion aporte = (Medicion)iterAportes.next();
                      medicionSincronizada = new Medicion(new MedicionPK(aporte.getMedicionId().getIndicadorId(), aporte.getMedicionId().getAno(), aporte.getMedicionId().getPeriodo(), aporte.getMedicionId().getSerieId()), aporte.getValor());
                      if(anoInicio == null)
                          anoInicio = medicionSincronizada.getMedicionId().getAno();
                      if(anoFin == null)
                          anoFin = medicionSincronizada.getMedicionId().getAno();
                      if(anoInicio.intValue() > medicionSincronizada.getMedicionId().getAno().intValue())
                          anoInicio = medicionSincronizada.getMedicionId().getAno();
                      if(anoFin.intValue() < medicionSincronizada.getMedicionId().getAno().intValue())
                          anoFin = medicionSincronizada.getMedicionId().getAno();
                  }

              }

          }

          List medicionesPortafolio = new ArrayList();
          List medicionesPorFrecuencia = new ArrayList();
          Double valorMedicion = null;
          boolean hayMedicion = false;
          for(Iterator iter = portafolioIniciativas.iterator(); iter.hasNext();)
          {
              PortafolioIniciativa portafolioIniciativa = (PortafolioIniciativa)iter.next();
              indicador = (Indicador)strategosIniciativasService.load(Indicador.class, portafolioIniciativa.getIniciativa().getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
              mediciones = portafolioIniciativa.getMediciones();
              int periodoInicioDesde[] = new int[1];
              int periodoFinalDesde[] = new int[1];
              int periodoInicioHasta[] = new int[1];
              int periodoFinalHasta[] = new int[1];
              int contadorPeriodos = 1;
              boolean obtenerProximaMedicion = true;
              Medicion medicion = null;
              Byte frecuenciaRequerida = null;
              Byte frecuenciaOriginal = null;
              boolean medicionInsertada = false;
              hayMedicion = false;
              Long serieId = null;
              boolean newSerie = true;
              boolean frecuenciaAsendente = true;
              boolean finalizaAno = false;
              if(portafolio.getFrecuencia().byteValue() <= indicador.getFrecuencia().byteValue())
              {
                  frecuenciaRequerida = indicador.getFrecuencia();
                  frecuenciaOriginal = portafolio.getFrecuencia();
              } else
              {
                  frecuenciaRequerida = portafolio.getFrecuencia();
                  frecuenciaOriginal = indicador.getFrecuencia();
                  frecuenciaAsendente = false;
                  portafolioIniciativa.setMediciones(new ArrayList());
                  portafolioIniciativa.getMediciones().clear();
              }
              if(anoInicio != null && anoFin != null)
              {
                  for(int j = anoInicio.intValue(); j <= anoFin.intValue(); j++)
                  {
                      int numeroMaximoPorPeriodo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(portafolio.getFrecuencia().byteValue(), j);
                      if(!frecuenciaAsendente)
                      {
                          medicionesModificar = new ArrayList();
                          boolean acumular = indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue();
                          for(Iterator i = indicador.getSeriesIndicador().iterator(); i.hasNext();)
                          {
                              SerieIndicador serie = (SerieIndicador)i.next();
                              mediciones = strategosMedicionesService.getMedicionesPorFrecuencia(indicador.getIndicadorId(), serie.getPk().getSerieId(), Integer.valueOf(j), Integer.valueOf(j), Integer.valueOf(1), Integer.valueOf(numeroMaximoPorPeriodo), frecuenciaRequerida, frecuenciaOriginal, Boolean.valueOf(acumular), Boolean.valueOf(false));
                              portafolioIniciativa.getMediciones().addAll(mediciones);
                              for(Iterator iterAportes = mediciones.iterator(); iterAportes.hasNext(); medicionesPorFrecuencia.add(medicionSincronizada))
                              {
                                  Medicion aporte = (Medicion)iterAportes.next();
                                  valorMedicion = aporte.getValor();
                                  if(valorMedicion != null && portafolioIniciativa.getPeso() != null && hayPeso)
                                      valorMedicion = Double.valueOf(valorMedicion.doubleValue() * (portafolioIniciativa.getPeso().doubleValue() / 100D));
                                  else
                                  if(valorMedicion != null && !hayPeso)
                                      valorMedicion = Double.valueOf(valorMedicion.doubleValue() / (double)totalIniciativas);
                                  medicionSincronizada = new Medicion(new MedicionPK(aporte.getMedicionId().getIndicadorId(), aporte.getMedicionId().getAno(), aporte.getMedicionId().getPeriodo(), aporte.getMedicionId().getSerieId()), valorMedicion);
                              }

                          }

                      } else
                      {
                          finalizaAno = false;
                          obtenerProximaMedicion = true;
                          medicion = null;
                          medicionInsertada = false;
                          hayMedicion = false;
                          serieId = null;
                          newSerie = true;
                          contadorPeriodos = 1;
                          Iterator i = mediciones.iterator();
                          while(medicion != null || contadorPeriodos == 1) 
                          {
                              if(contadorPeriodos > numeroMaximoPorPeriodo)
                                  contadorPeriodos = 1;
                              valorMedicion = null;
                              if(obtenerProximaMedicion)
                              {
                                  boolean foundMedicion = false;
                                  medicion = null;
                                  while(i.hasNext()) 
                                  {
                                      medicion = (Medicion)i.next();
                                      if(medicion.getMedicionId().getAno().intValue() == j && medicion.getMedicionId().getIndicadorId().longValue() == indicador.getIndicadorId().longValue())
                                      {
                                          if(serieId == null)
                                          {
                                              serieId = medicion.getMedicionId().getSerieId();
                                              newSerie = false;
                                          }
                                          if(serieId.longValue() != medicion.getMedicionId().getSerieId().longValue() && contadorPeriodos != 1)
                                          {
                                              newSerie = true;
                                          } else
                                          {
                                              serieId = medicion.getMedicionId().getSerieId();
                                              newSerie = false;
                                          }
                                          PeriodoUtil.contencionPeriodosFrecuenciaEnFrecuencia(frecuenciaRequerida.intValue(), frecuenciaOriginal.intValue(), medicion.getMedicionId().getPeriodo().intValue(), medicion.getMedicionId().getPeriodo().intValue(), periodoInicioDesde, periodoFinalDesde, periodoInicioHasta, periodoFinalHasta);
                                          foundMedicion = true;
                                          break;
                                      }
                                  }
                                  if(!foundMedicion && contadorPeriodos == 1)
                                      medicion = null;
                                  else
                                  if(!foundMedicion && contadorPeriodos != 1)
                                  {
                                      medicion = new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(j), new Integer(contadorPeriodos), serieId), null);
                                      finalizaAno = true;
                                  }
                                  obtenerProximaMedicion = false;
                              }
                              if(medicion != null && medicion.getMedicionId().getAno().intValue() == j && contadorPeriodos >= periodoInicioDesde[0] && contadorPeriodos <= periodoFinalHasta[0])
                              {
                                  hayMedicion = false;
                                  if(contadorPeriodos == periodoFinalHasta[0] && !newSerie)
                                  {
                                      valorMedicion = medicion.getValor();
                                      if(valorMedicion != null && portafolioIniciativa.getPeso() != null && hayPeso)
                                          valorMedicion = Double.valueOf(valorMedicion.doubleValue() * (portafolioIniciativa.getPeso().doubleValue() / 100D));
                                      else
                                      if(valorMedicion != null && !hayPeso)
                                          valorMedicion = Double.valueOf(valorMedicion.doubleValue() / (double)totalIniciativas);
                                      medicionSincronizada = new Medicion(new MedicionPK(indicador.getIndicadorId(), medicion.getMedicionId().getAno(), new Integer(contadorPeriodos), medicion.getMedicionId().getSerieId()), valorMedicion);
                                      medicionesPorFrecuencia.add(medicionSincronizada);
                                      medicionInsertada = true;
                                      hayMedicion = true;
                                      obtenerProximaMedicion = true;
                                  }
                              }
                              if(medicion != null && !medicionInsertada)
                              {
                                  if(hayMedicion)
                                      hayMedicion = false;
                                  medicionSincronizada = new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(j), new Integer(contadorPeriodos), serieId), valorMedicion);
                                  medicionesPorFrecuencia.add(medicionSincronizada);
                              }
                              medicionInsertada = false;
                              if(++contadorPeriodos > numeroMaximoPorPeriodo && medicion != null && serieId.longValue() != medicion.getMedicionId().getSerieId().longValue())
                              {
                                  serieId = medicion.getMedicionId().getSerieId();
                                  newSerie = false;
                              }
                              if(contadorPeriodos > numeroMaximoPorPeriodo && finalizaAno)
                                  medicion = null;
                          }
                      }
                  }

              }
          }

          List series = new ArrayList();
          series.add(SerieTiempo.getSerieReal());
          series.add(SerieTiempo.getSerieProgramado());
          Long indicadorId = null;
          Double valor = null;
          for(Iterator iterSeries = series.iterator(); iterSeries.hasNext();)
          {
              SerieTiempo serie = (SerieTiempo)iterSeries.next();
              indicadorId = null;
              for(Iterator iterAportes = medicionesPorFrecuencia.iterator(); iterAportes.hasNext();)
              {
                  Medicion aporte = (Medicion)iterAportes.next();
                  if(aporte.getMedicionId().getSerieId().longValue() == serie.getSerieId().longValue())
                  {
                      if(indicadorId == null)
                      {
                          indicadorId = aporte.getMedicionId().getIndicadorId();
                          indicador = (Indicador)strategosIniciativasService.load(Indicador.class, indicadorId);
                          valor = aporte.getValor();
                      }
                      if(indicadorId.longValue() != aporte.getMedicionId().getIndicadorId().longValue())
                      {
                          indicadorId = aporte.getMedicionId().getIndicadorId();
                          indicador = (Indicador)strategosIniciativasService.load(Indicador.class, indicadorId);
                          valor = aporte.getValor();
                      }
                      if(indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionAlPeriodo().byteValue() && aporte.getMedicionId().getIndicadorId().longValue() == indicadorId.longValue())
                      {
                          if(aporte.getValor() == null)
                              aporte.setValor(valor);
                          else
                              valor = aporte.getValor();
                      } else
                      if(indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
                          if(aporte.getValor() == null)
                          {
                              aporte.setValor(valor);
                          } else
                          {
                              if(valor != null)
                                  valor = Double.valueOf(aporte.getValor().doubleValue() + valor.doubleValue());
                              else
                                  valor = aporte.getValor();
                              aporte.setValor(valor);
                          }
                  }
              }

          }

          hayMedicion = false;
          Long indicadorPortafolioId = portafolio.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento());
          for(Iterator iterAportes = medicionesPorFrecuencia.iterator(); iterAportes.hasNext();)
          {
              Medicion aporte = (Medicion)iterAportes.next();
              aporte.getMedicionId().setIndicadorId(indicadorPortafolioId);
              hayMedicion = false;
              for(Iterator iterAportesPortafolio = medicionesPortafolio.iterator(); iterAportesPortafolio.hasNext();)
              {
                  Medicion aportePortafolio = (Medicion)iterAportesPortafolio.next();
                  if(aporte.getMedicionId().getIndicadorId().longValue() == aportePortafolio.getMedicionId().getIndicadorId().longValue() && aporte.getMedicionId().getSerieId().longValue() == aportePortafolio.getMedicionId().getSerieId().longValue() && aporte.getMedicionId().getAno().intValue() == aportePortafolio.getMedicionId().getAno().intValue() && aporte.getMedicionId().getPeriodo().intValue() == aportePortafolio.getMedicionId().getPeriodo().intValue())
                  {
                      hayMedicion = true;
                      if(aportePortafolio.getValor() == null)
                          aportePortafolio.setValor(aporte.getValor());
                      else
                      if(aporte.getValor() != null)
                          aportePortafolio.setValor(Double.valueOf(aportePortafolio.getValor().doubleValue() + aporte.getValor().doubleValue()));
                      break;
                  }
              }

              if(!hayMedicion)
                  medicionesPortafolio.add(aporte);
          }

          if(resultado == 10000 && medicionesPortafolio.size() > 0)
              resultado = strategosMedicionesService.saveMediciones(medicionesPortafolio, null, usuario, new Boolean(true), new Boolean(true));
          if(resultado == 10000 && medicionesPortafolio.size() > 0)
              resultado = strategosMedicionesService.actualizarSeriePorPeriodos(anoInicio, anoFin, Integer.valueOf(periodoDesde), Integer.valueOf(periodoHasta), medicionesPortafolio, Boolean.valueOf(false), Boolean.valueOf(false), usuario);
          indicador = (Indicador)strategosMedicionesService.load(Indicador.class, indicadorPortafolioId);
          Medicion medicion = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), TipoCorte.getTipoCorteTransversal(), indicador.getTipoCargaMedicion());
          if(medicion != null)
          {
              portafolio.setPorcentajeCompletado(medicion.getValor());
              Calendar fecha = Calendar.getInstance();
              portafolio.setFechaUltimoCalculo((new StringBuilder(String.valueOf(fecha.get(2) + 1))).append("/").append(fecha.get(1)).toString());
              StrategosIniciativaEstatusService strategosIniciativaEstatusService = StrategosServiceFactory.getInstance().openStrategosIniciativaEstatusService();
              Long estatusId = strategosIniciativaEstatusService.calcularEstatus(portafolio.getPorcentajeCompletado());
              strategosIniciativaEstatusService.close();
              portafolio.setEstatusId(estatusId);
          }
          strategosMedicionesService.close();
          strategosIniciativasService.close();
          if(resultado == 10000)
              resultado = save(portafolio, Boolean.valueOf(false), usuario);
          if(transActiva)
          {
              if(resultado == 10000)
                  persistenceSession.commitTransaction();
              else
                  persistenceSession.rollbackTransaction();
              transActiva = false;
          }
      }
      catch(Throwable t)
      {
          if(transActiva)
              persistenceSession.rollbackTransaction();
          throw new ChainedRuntimeException(t.getMessage(), t);
      }
      return resultado;
  }
  
  public List<PortafolioIniciativa> getIniciativasPortafolio(Long portafolioId)
  {
    return persistenceSession.getIniciativasPortafolio(portafolioId);
  }
  
  public int saveIniciativasPortafolio(List<PortafolioIniciativa> portafolioIniciativas, Usuario usuario)
  {
    boolean transActiva = false;
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
      
      for (Iterator<PortafolioIniciativa> iter = portafolioIniciativas.iterator(); iter.hasNext();)
      {
        PortafolioIniciativa portafolioIniciativa = (PortafolioIniciativa)iter.next();
        
        fieldNames[0] = "pk.iniciativaId";
        fieldValues[0] = portafolioIniciativa.getPk().getIniciativaId();
        fieldNames[1] = "pk.portafolioId";
        fieldValues[1] = portafolioIniciativa.getPk().getPortafolioId();
        
        if (persistenceSession.existsObject(portafolioIniciativa, fieldNames, fieldValues)) {        	
        	resultado = persistenceSession.updatePesos(portafolioIniciativa, usuario);
        }
        if (resultado != 10000) {
          break;
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
    }
    catch (Throwable t)
    {
      if (transActiva)
        persistenceSession.rollbackTransaction();
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return resultado;
  }
  
  public int updatePesos(PortafolioIniciativa portafolioIniciativa, Usuario usuario)
  {
    return persistenceSession.updatePesos(portafolioIniciativa, usuario);
  }
}
