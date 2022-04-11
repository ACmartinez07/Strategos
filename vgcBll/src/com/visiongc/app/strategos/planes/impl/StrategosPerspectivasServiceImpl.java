package com.visiongc.app.strategos.planes.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.PrioridadIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectivaPK;
import com.visiongc.app.strategos.planes.model.IndicadorPlan;
import com.visiongc.app.strategos.planes.model.IndicadorPlanPK;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectivaPK;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.PerspectivaEstado;
import com.visiongc.app.strategos.planes.model.PerspectivaEstadoPK;
import com.visiongc.app.strategos.planes.model.PerspectivaRelacion;
import com.visiongc.app.strategos.planes.model.PerspectivaRelacionPK;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.TipoCalculoPerspectiva;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.app.strategos.planes.persistence.StrategosPerspectivasPersistenceSession;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.servicio.strategos.indicadores.model.util.TipoAlerta;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrategosPerspectivasServiceImpl
  extends StrategosServiceImpl
  implements StrategosPerspectivasService
{
  private StrategosPerspectivasPersistenceSession persistenceSession = null;
  
  public StrategosPerspectivasServiceImpl(StrategosPerspectivasPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public Perspectiva getPerspectivaAndLockForUse(Long perspectivaId, String instancia)
  {
    Perspectiva perspectiva = null;
    Object[] idsToUse = new Object[1];
    idsToUse[0] = perspectivaId;
    
    if (persistenceSession.lockForUse(instancia, idsToUse))
    {
      perspectiva = (Perspectiva)persistenceSession.load(Perspectiva.class, perspectivaId);
      if (perspectiva == null) {
        persistenceSession.unlockObject(instancia, perspectivaId);
      
      }
    }
    return perspectiva;
  }
  
  public String getRutaCompletaPerspectiva(Long perspectivaId, String separador)
  {
    Perspectiva perspectiva = (Perspectiva)persistenceSession.load(Perspectiva.class, perspectivaId);
    
    return getRutaCompletaPerspectiva(perspectiva, separador);
  }
  
  public String getRutaCompletaPerspectiva(Perspectiva perspectiva, String separador)
  {
    String rutaOrganizaciones = "";
    String rutaPerspectivas = "";
    
    StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService(this);
    
    Plan plan = (Plan)persistenceSession.load(Plan.class, perspectiva.getPlanId());
    rutaOrganizaciones = strategosOrganizacionesService.getRutaCompletaNombresOrganizacion(plan.getOrganizacionId(), separador);
    
    ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService(this);
    rutaPerspectivas = arbolesService.getRutaCompletaNombres(perspectiva, separador);
    
    strategosOrganizacionesService.close();
    arbolesService.close();
    
    return rutaOrganizaciones + separador + plan.getNombre() + separador + rutaPerspectivas;
  }
  
  public String getRutaCompletaPerspectivaSinPorcentajes(Perspectiva perspectiva, String separador)
  {
    String rutaOrganizaciones = "";
    String rutaPerspectivas = "";
    
    StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService(this);
    
    Plan plan = (Plan)persistenceSession.load(Plan.class, perspectiva.getPlanId());
    rutaOrganizaciones = strategosOrganizacionesService.getRutaCompletaNombresOrganizacion(plan.getOrganizacionId(), separador);
    rutaPerspectivas = getRutaCompletaNombres(perspectiva, separador);
    
    strategosOrganizacionesService.close();
    
    return rutaOrganizaciones + separador + plan.getNombre() + separador + rutaPerspectivas;
  }
  
  private String getRutaCompletaNombres(Perspectiva perspectiva, String separador)
  {
    String nombre = "";
    String ruta = "";
    
    nombre = perspectiva.getNombre();
    ruta = nombre;
    
    boolean padreNulo = perspectiva.getPadre() == null;
    
    while (!padreNulo)
    {
      perspectiva = perspectiva.getPadre();
      padreNulo = perspectiva == null;
      if (!padreNulo)
      {
        nombre = perspectiva.getNombre();
        ruta = nombre + separador + ruta;
      }
    }
    
    return ruta;
  }
  
  private int deleteDependenciasPerspectiva(Perspectiva perspectiva, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    ListaMap dependencias = new ListaMap();
    List<?> listaObjetosRelacionados = null;
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      dependencias = persistenceSession.getDependenciasPerspectiva(perspectiva);
      for (Iterator<?> i = dependencias.iterator(); i.hasNext();)
      {
        listaObjetosRelacionados = (List)i.next();
        if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof ClaseIndicadores)))
        {
          StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService(this);
          
          for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            ClaseIndicadores clase = (ClaseIndicadores)j.next();
            resultado = eliminarClasesHijas(clase.getClaseId(), usuario, strategosClasesIndicadoresService);
            if (resultado == 10000)
              resultado = strategosClasesIndicadoresService.deleteClaseIndicadores(clase, Boolean.valueOf(true), usuario);
            if (resultado != 10000)
              break;
          }
          strategosClasesIndicadoresService.close();
        }
        else
        {
          for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();)
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
      resultado = 10004;
      if (transActiva)
      {
        persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    
    return resultado;
  }
  
  private int eliminarClasesHijas(Long claseId, Usuario usuario, StrategosClasesIndicadoresService strategosClasesIndicadoresService)
  {
    int respuesta = 10000;
    
    List<ClaseIndicadores> clases = strategosClasesIndicadoresService.getClasesHijas(claseId, null);
    
    for (Iterator<ClaseIndicadores> iter = clases.iterator(); iter.hasNext();)
    {
      ClaseIndicadores claseHijo = (ClaseIndicadores)iter.next();
      if ((claseHijo.getHijos() != null) && (claseHijo.getHijos().size() >= 0))
        eliminarClasesHijas(claseHijo.getClaseId(), usuario, strategosClasesIndicadoresService);
      respuesta = strategosClasesIndicadoresService.deleteClaseIndicadores(claseHijo, Boolean.valueOf(true), usuario);
      if (respuesta != 10000) {
        break;
      }
    }
    return respuesta;
  }
  
  private int deletePostDependenciasPerspectiva(Perspectiva perspectiva, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
      
      Indicador indicadorLogro = (Indicador)strategosIndicadoresService.load(Indicador.class, perspectiva.getNlAnoIndicadorId());
      if (indicadorLogro != null) {
        resultado = strategosIndicadoresService.deleteIndicador(indicadorLogro, usuario);
        if (resultado != 10000) {
          return resultado;
        }
      }
      

      indicadorLogro = (Indicador)strategosIndicadoresService.load(Indicador.class, perspectiva.getNlParIndicadorId());
      if (indicadorLogro != null) {
        resultado = strategosIndicadoresService.deleteIndicador(indicadorLogro, usuario);
        if (resultado != 10000) {
          return resultado;
        }
      }
      

      strategosIndicadoresService.close();
      
      StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService(this);
      
      ClaseIndicadores claseIndicadores = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, perspectiva.getClaseId());
      if (claseIndicadores != null) {
        resultado = strategosClasesIndicadoresService.deleteClaseIndicadores(claseIndicadores, Boolean.valueOf(true), usuario);
      }
      
      strategosClasesIndicadoresService.close();
      
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
  
  public int deletePerspectiva(Perspectiva perspectiva, Usuario usuario)
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
      
      if (perspectiva.getPerspectivaId() != null)
      {
        if ((perspectiva.getHijos() != null) && (perspectiva.getHijos().size() > 0))
        {
          for (Iterator<Perspectiva> iter = perspectiva.getHijos().iterator(); iter.hasNext();)
          {
            Perspectiva perspectivaHija = (Perspectiva)iter.next();
            resultado = deletePerspectiva(perspectivaHija, usuario);
            if (resultado != 10000) {
              break;
            }
          }
        }
        if (resultado == 10000) {
          resultado = persistenceSession.deleteReferenciasForaneasPerspectiva(perspectiva.getPerspectivaId());
        }
        if (resultado == 10000)
        {
          IndicadorPerspectiva indicadorLogroAnualPerspectiva = new IndicadorPerspectiva();
          indicadorLogroAnualPerspectiva.setPk(new IndicadorPerspectivaPK());
          indicadorLogroAnualPerspectiva.getPk().setIndicadorId(perspectiva.getNlAnoIndicadorId());
          indicadorLogroAnualPerspectiva.getPk().setPerspectivaId(perspectiva.getPadreId());
          IndicadorPerspectiva indicadorLNPerspectiva = (IndicadorPerspectiva)persistenceSession.load(IndicadorPerspectiva.class, indicadorLogroAnualPerspectiva.getPk());
          
          IndicadorPlan indicadorLogroAnualPlan = new IndicadorPlan();
          indicadorLogroAnualPlan.setPk(new IndicadorPlanPK());
          indicadorLogroAnualPlan.getPk().setIndicadorId(perspectiva.getNlAnoIndicadorId());
          indicadorLogroAnualPlan.getPk().setPlanId(perspectiva.getPlanId());
          IndicadorPlan indicadorLNPlan = (IndicadorPlan)persistenceSession.load(IndicadorPlan.class, indicadorLogroAnualPlan.getPk());
          
          resultado = desasociarIndicador(indicadorLNPerspectiva, indicadorLNPlan, usuario, Boolean.valueOf(true));
          if (resultado == 10000)
          {
            IndicadorPerspectiva indicadorLogroParcialPerspectiva = new IndicadorPerspectiva();
            indicadorLogroParcialPerspectiva.setPk(new IndicadorPerspectivaPK());
            indicadorLogroParcialPerspectiva.getPk().setIndicadorId(perspectiva.getNlParIndicadorId());
            indicadorLogroParcialPerspectiva.getPk().setPerspectivaId(perspectiva.getPadreId());
            IndicadorPerspectiva indicadorLPPerspectiva = (IndicadorPerspectiva)persistenceSession.load(IndicadorPerspectiva.class, indicadorLogroParcialPerspectiva.getPk());
            
            IndicadorPlan indicadorLogroParcialPlan = new IndicadorPlan();
            indicadorLogroParcialPlan.setPk(new IndicadorPlanPK());
            indicadorLogroParcialPlan.getPk().setIndicadorId(perspectiva.getNlParIndicadorId());
            indicadorLogroParcialPlan.getPk().setPlanId(perspectiva.getPlanId());
            IndicadorPlan indicadorLPPlan = (IndicadorPlan)persistenceSession.load(IndicadorPlan.class, indicadorLogroParcialPlan.getPk());
            
            resultado = desasociarIndicador(indicadorLPPerspectiva, indicadorLPPlan, usuario, Boolean.valueOf(true));
          }
          
          if (resultado == 10000) {
            resultado = persistenceSession.delete(perspectiva, usuario);
          }
          if (resultado == 10000)
            resultado = deleteDependenciasPerspectiva(perspectiva, usuario);
          if (resultado == 10000) {
            resultado = deletePostDependenciasPerspectiva(perspectiva, usuario);
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
      resultado = 10004;
      if (transActiva)
      {
        persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    
    return resultado;
  }
  
  public int savePerspectiva(Perspectiva perspectiva, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[4];
    Object[] fieldValues = new Object[4];
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      fieldNames[0] = "nombre";
      fieldNames[1] = "padreId";
      fieldNames[2] = "planId";
      fieldNames[3] = "ano";
      fieldValues[0] = perspectiva.getNombre();
      fieldValues[1] = perspectiva.getPadreId();
      fieldValues[2] = perspectiva.getPlanId();
      fieldValues[3] = perspectiva.getAno();
      
      if ((perspectiva.getPerspectivaId() == null) || (perspectiva.getPerspectivaId().longValue() == 0L))
      {
        if (persistenceSession.existsObject(perspectiva, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          perspectiva.setPerspectivaId(new Long(persistenceSession.getUniqueId()));
          
          if ((perspectiva.getRelacion() != null) && (perspectiva.getRelacion().size() > 0))
          {
            Set<PerspectivaRelacion> relaciones = perspectiva.getRelacion();
            
            perspectiva.setRelacion(new HashSet());
            perspectiva.getRelacion().clear();
            
            for (Iterator<PerspectivaRelacion> i = relaciones.iterator(); i.hasNext();)
            {
              PerspectivaRelacion relacion = (PerspectivaRelacion)i.next();
              
              PerspectivaRelacion relacionCopia = new PerspectivaRelacion();
              relacionCopia.setPk(new PerspectivaRelacionPK());
              relacionCopia.getPk().setPerspectivaId(perspectiva.getPerspectivaId());
              relacionCopia.getPk().setRelacionId(relacion.getPk().getRelacionId());
              relacionCopia.setPerspectiva(perspectiva);
              relacionCopia.setRelacion(relacion.getRelacion());
              
              perspectiva.getRelacion().add(relacionCopia);
            }
          }
          
          resultado = saveClaseIndicadoresLogro(perspectiva, usuario);
          if (resultado == 10000) {
            resultado = saveIndicadoresLogro(perspectiva, usuario);
          }
          if (resultado == 10000)
          {
            perspectiva.setCreado(new Date());
            perspectiva.setCreadoId(usuario.getUsuarioId());
            resultado = persistenceSession.insert(perspectiva, usuario);
          }
          
          if (resultado == 10000)
          {
            asociarIndicador(perspectiva.getPlanId(), perspectiva, perspectiva.getNlAnoIndicadorId(), new Boolean(false), usuario);
            asociarIndicador(perspectiva.getPlanId(), perspectiva, perspectiva.getNlParIndicadorId(), new Boolean(false), usuario);
          }
          
          if (resultado == 10000) {
            resultado = saveIndicadoresCrecimiento(perspectiva, usuario);
          }
        }
      }
      else {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "perspectivaId";
        idFieldValues[0] = perspectiva.getPerspectivaId();
        if (persistenceSession.existsObject(perspectiva, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        }
        else {
          if ((perspectiva.getRelacion() != null) && (perspectiva.getRelacion().size() > 0))
          {
            Set<PerspectivaRelacion> relaciones = perspectiva.getRelacion();
            

            boolean existObjeto = false;
            for (Iterator<PerspectivaRelacion> i = relaciones.iterator(); i.hasNext();)
            {
              PerspectivaRelacion relacion = (PerspectivaRelacion)i.next();
              
              PerspectivaRelacion relacionCopia = new PerspectivaRelacion();
              relacionCopia.setPk(new PerspectivaRelacionPK());
              relacionCopia.getPk().setPerspectivaId(perspectiva.getPerspectivaId());
              relacionCopia.getPk().setRelacionId(relacion.getPk().getRelacionId());
              relacionCopia.setPerspectiva(perspectiva);
              relacionCopia.setRelacion(relacion.getRelacion());
              existObjeto = false;
              
              for (Iterator<PerspectivaRelacion> j = perspectiva.getRelacion().iterator(); j.hasNext();)
              {
                PerspectivaRelacion relacionReal = (PerspectivaRelacion)j.next();
                
                if ((relacionReal.getPk().getPerspectivaId().longValue() == relacionCopia.getPk().getPerspectivaId().longValue()) && (relacionReal.getPk().getRelacionId().longValue() == relacionCopia.getPk().getRelacionId().longValue()))
                {
                  existObjeto = true;
                  break;
                }
              }
              
              if (!existObjeto) {
                perspectiva.getRelacion().add(relacionCopia);
              }
            }
          }
          resultado = saveClaseIndicadoresLogro(perspectiva, usuario);
          if (resultado == 10000) {
            resultado = saveIndicadoresLogro(perspectiva, usuario);
          }
          if (resultado == 10000)
          {
            perspectiva.setModificado(new Date());
            perspectiva.setModificadoId(usuario.getUsuarioId());
            
            Perspectiva perspectivaOriginal = persistenceSession.getPerspectivaValoresOriginales(perspectiva.getPerspectivaId());
            
            resultado = persistenceSession.update(perspectiva, usuario);
            
            if (resultado == 10000) {
              resultado = verificarCambiosPerspectiva(perspectiva, perspectivaOriginal);
            }
          }
        }
      }
      if (transActiva)
      {
        if (resultado == 10000) {
          persistenceSession.commitTransaction();
        } else {
          persistenceSession.rollbackTransaction();
        }
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
  
  private int saveIndicadoresLogro(Perspectiva perspectiva, Usuario usuario)
  {
    int resultado = 10000;
    try
    {
      StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
      
      OrganizacionStrategos organizacion = (OrganizacionStrategos)persistenceSession.load(OrganizacionStrategos.class, perspectiva.getPlan().getOrganizacionId());
      
      Integer porcentajeZonaVerde = organizacion.getPorcentajeZonaVerdeMetaIndicadores();
      Integer porcentajeZonaAmarilla = organizacion.getPorcentajeZonaAmarillaMetaIndicadores();
      
      Indicador indicadorLogroAnual = null;
      
      if (perspectiva.getNlAnoIndicadorId() == null) {
        indicadorLogroAnual = new Indicador();
      }
      else {
        indicadorLogroAnual = (Indicador)strategosIndicadoresService.load(Indicador.class, perspectiva.getNlAnoIndicadorId());
        if (indicadorLogroAnual == null)
          indicadorLogroAnual = new Indicador();
      }
      String nombreIndicador = perspectiva.getNombre();
      if (nombreIndicador.length() > 80)
        nombreIndicador = nombreIndicador.substring(0, 80);
      nombreIndicador = nombreIndicador + "... (" + messageResources.getResource("indicador.logroanual") + ")";
      if (nombreIndicador.length() > 100)
        nombreIndicador = nombreIndicador.substring(0, 100);
      indicadorLogroAnual.setNombre(nombreIndicador);
      nombreIndicador = indicadorLogroAnual.getNombre();
      if (nombreIndicador.length() > 50)
        nombreIndicador = nombreIndicador.substring(0, 50);
      indicadorLogroAnual.setNombreCorto(nombreIndicador);
      indicadorLogroAnual.setOrganizacionId(perspectiva.getPlan().getOrganizacionId());
      indicadorLogroAnual.setFrecuencia(perspectiva.getFrecuencia());
      indicadorLogroAnual.setClaseId(perspectiva.getClaseId());
      indicadorLogroAnual.setCorte(TipoCorte.getTipoCorteTransversal());
      indicadorLogroAnual.setNaturaleza(Naturaleza.getNaturalezaSimple());
      indicadorLogroAnual.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionPerspectiva());
      if (porcentajeZonaAmarilla != null)
        indicadorLogroAnual.setAlertaMetaZonaAmarilla(Double.valueOf(porcentajeZonaAmarilla.doubleValue()));
      if (porcentajeZonaVerde != null)
        indicadorLogroAnual.setAlertaMetaZonaVerde(Double.valueOf(porcentajeZonaVerde.doubleValue()));
      indicadorLogroAnual.setAlertaTipoZonaAmarilla(TipoAlerta.getTipoAlertaPorcentaje());
      indicadorLogroAnual.setAlertaTipoZonaVerde(TipoAlerta.getTipoAlertaPorcentaje());
      indicadorLogroAnual.setGuia(new Boolean(false));
      indicadorLogroAnual.setMostrarEnArbol(new Boolean(false));
      indicadorLogroAnual.setPrioridad(PrioridadIndicador.getPrioridadIndicadorAlta());
      indicadorLogroAnual.setCaracteristica(Caracteristica.getCaracteristicaRetoAumento());
      indicadorLogroAnual.setNumeroDecimales(new Byte("2"));
      indicadorLogroAnual.setValorInicialCero(new Boolean(true));
      
      StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService(strategosIndicadoresService);
      UnidadMedida unidad = strategosUnidadesService.getUnidadMedidaPorcentaje();
      strategosUnidadesService.close();
      
      indicadorLogroAnual.setUnidadId(unidad.getUnidadId());
      
      Indicador indicadorOriginal = null;
      if (perspectiva.getNlAnoIndicadorId() != null) {
        indicadorOriginal = strategosIndicadoresService.getIndicadorValoresOriginales(perspectiva.getNlAnoIndicadorId());
      }
      resultado = strategosIndicadoresService.saveIndicador(indicadorLogroAnual, usuario);
      if (resultado == 10003)
      {
        Map<String, Object> filtros = new HashMap();
        
        filtros.put("claseId", indicadorLogroAnual.getClaseId());
        filtros.put("nombre", indicadorLogroAnual.getNombre());
        List<Indicador> indicadores = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
        if (indicadores.size() > 0)
        {
          indicadorLogroAnual = (Indicador)indicadores.get(0);
          resultado = 10000;
        }
      }
      
      if ((resultado == 10000) && (indicadorOriginal != null) && (perspectiva.getNlAnoIndicadorId() != null)) {
        resultado = strategosIndicadoresService.verificarCambiosIndicador(indicadorLogroAnual, indicadorOriginal);
      }
      if (resultado != 10000)
        return resultado;
      if (resultado == 10000) {
        perspectiva.setNlAnoIndicadorId(indicadorLogroAnual.getIndicadorId());
      }
      Indicador indicadorLogroParcial = null;
      
      if (perspectiva.getNlParIndicadorId() == null) {
        indicadorLogroParcial = new Indicador();
      }
      else {
        indicadorLogroParcial = (Indicador)strategosIndicadoresService.load(Indicador.class, perspectiva.getNlParIndicadorId());
        
        if (indicadorLogroParcial == null)
          indicadorLogroParcial = new Indicador();
      }
      nombreIndicador = perspectiva.getNombre();
      if (nombreIndicador.length() > 80)
        nombreIndicador = nombreIndicador.substring(0, 80);
      nombreIndicador = nombreIndicador + "... (" + messageResources.getResource("indicador.logroparcial") + ")";
      if (nombreIndicador.length() > 100)
        nombreIndicador = nombreIndicador.substring(0, 100);
      indicadorLogroParcial.setNombre(nombreIndicador);
      nombreIndicador = indicadorLogroParcial.getNombre();
      if (nombreIndicador.length() > 50)
        nombreIndicador = nombreIndicador.substring(0, 50);
      indicadorLogroParcial.setNombreCorto(nombreIndicador);
      indicadorLogroParcial.setOrganizacionId(perspectiva.getPlan().getOrganizacionId());
      indicadorLogroParcial.setFrecuencia(perspectiva.getFrecuencia());
      indicadorLogroParcial.setClaseId(perspectiva.getClaseId());
      indicadorLogroParcial.setCorte(TipoCorte.getTipoCorteTransversal());
      indicadorLogroParcial.setNaturaleza(Naturaleza.getNaturalezaSimple());
      indicadorLogroParcial.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionPerspectiva());
      if (porcentajeZonaAmarilla != null)
        indicadorLogroParcial.setAlertaMetaZonaAmarilla(Double.valueOf(porcentajeZonaAmarilla.doubleValue()));
      if (porcentajeZonaVerde != null)
        indicadorLogroParcial.setAlertaMetaZonaVerde(Double.valueOf(porcentajeZonaVerde.doubleValue()));
      indicadorLogroParcial.setAlertaTipoZonaAmarilla(TipoAlerta.getTipoAlertaPorcentaje());
      indicadorLogroParcial.setAlertaTipoZonaVerde(TipoAlerta.getTipoAlertaPorcentaje());
      indicadorLogroParcial.setGuia(new Boolean(false));
      indicadorLogroParcial.setMostrarEnArbol(new Boolean(false));
      indicadorLogroParcial.setPrioridad(PrioridadIndicador.getPrioridadIndicadorBaja());
      indicadorLogroParcial.setCaracteristica(Caracteristica.getCaracteristicaRetoAumento());
      indicadorLogroParcial.setNumeroDecimales(new Byte("2"));
      indicadorLogroParcial.setValorInicialCero(new Boolean(true));
      indicadorLogroParcial.setUnidadId(unidad.getUnidadId());
      
      indicadorOriginal = null;
      if (perspectiva.getNlParIndicadorId() != null) {
        indicadorOriginal = strategosIndicadoresService.getIndicadorValoresOriginales(perspectiva.getNlParIndicadorId());
      }
      resultado = strategosIndicadoresService.saveIndicador(indicadorLogroParcial, usuario);
      if (resultado == 10003)
      {
        Map<String, Object> filtros = new HashMap();
        
        filtros.put("claseId", indicadorLogroParcial.getClaseId());
        filtros.put("nombre", indicadorLogroParcial.getNombre());
        List<Indicador> indicadores = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
        if (indicadores.size() > 0)
        {
          indicadorLogroParcial = (Indicador)indicadores.get(0);
          resultado = 10000;
        }
      }
      
      if ((resultado == 10000) && (indicadorOriginal != null) && (perspectiva.getNlParIndicadorId() != null)) {
        resultado = strategosIndicadoresService.verificarCambiosIndicador(indicadorLogroParcial, indicadorOriginal);
      }
      strategosIndicadoresService.close();
      
      if (resultado != 10000)
        return resultado;
      if (resultado == 10000) {
        perspectiva.setNlParIndicadorId(indicadorLogroParcial.getIndicadorId());
      }
    }
    catch (Throwable t) {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return resultado;
  }
  
  private int saveClaseIndicadoresLogro(Perspectiva perspectiva, Usuario usuario)
  {
    int resultado = 10000;
    byte tipo = 3;
    try
    {
      StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
      
      ClaseIndicadores claseIndicadores = null;
      if (perspectiva.getClaseId() == null) {
        claseIndicadores = new ClaseIndicadores();
      }
      else {
        claseIndicadores = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, perspectiva.getClaseId());
        if (claseIndicadores == null) {
          claseIndicadores = new ClaseIndicadores();
        }
      }
      StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
      Plan plan = (Plan)strategosPlanesService.load(Plan.class, perspectiva.getPlanId());
      perspectiva.setPlan(plan);
      strategosPlanesService.close();
      
      String nombreClase = perspectiva.getNombre() + " (" + plan.getNombre() + ")";
      if (nombreClase.length() > 310)
        nombreClase = nombreClase.substring(0, 310);
      claseIndicadores.setNombre(nombreClase);
      claseIndicadores.setOrganizacionId(plan.getOrganizacionId());
      claseIndicadores.setTipo(new Byte(tipo));
      claseIndicadores.setVisible(new Boolean(false));
      
      if (perspectiva.getPadreId() == null) {
        claseIndicadores.setPadreId(plan.getClaseId());
      }
      else {
        Perspectiva perspectivaPadre = (Perspectiva)persistenceSession.load(Perspectiva.class, perspectiva.getPadreId());
        claseIndicadores.setPadreId(perspectivaPadre.getClaseId());
        perspectiva.setPadre(perspectivaPadre);
      }
      
      resultado = strategosClasesIndicadoresService.saveClaseIndicadores(claseIndicadores, usuario);
      if (resultado == 10003)
      {
        Map<String, Object> filtros = new HashMap();
        
        filtros.put("organizacionId", claseIndicadores.getOrganizacionId().toString());
        filtros.put("nombre", claseIndicadores.getNombre());
        filtros.put("padreId", claseIndicadores.getPadreId());
        List<ClaseIndicadores> clases = strategosClasesIndicadoresService.getClases(filtros);
        if (clases.size() > 0)
        {
          claseIndicadores = (ClaseIndicadores)clases.get(0);
          resultado = 10000;
        }
      }
      
      strategosClasesIndicadoresService.close();
      
      if (resultado != 10000)
        return resultado;
      if (resultado == 10000) {
        perspectiva.setClaseId(claseIndicadores.getClaseId());
      }
    }
    catch (Throwable t) {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return resultado;
  }
  
  private int saveIndicadoresCrecimiento(Perspectiva perspectiva, Usuario usuario)
  {
    int resultado = 10000;
    boolean transActiva = false;
    String[] fieldNames = new String[2];
    Object[] fieldValues = new Object[2];
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (perspectiva.getPadreId() != null)
      {
        Perspectiva perspectivaPadre = (Perspectiva)persistenceSession.load(Perspectiva.class, perspectiva.getPadreId());
        
        if (perspectivaPadre.getPadreId() == null)
        {
          IndicadorPlan indicadorPlanLogroAnual = new IndicadorPlan();
          
          indicadorPlanLogroAnual.setPk(new IndicadorPlanPK());
          indicadorPlanLogroAnual.getPk().setIndicadorId(perspectiva.getNlAnoIndicadorId());
          indicadorPlanLogroAnual.getPk().setPlanId(perspectiva.getPlanId());
          
          fieldNames[0] = "pk.indicadorId";
          fieldValues[0] = indicadorPlanLogroAnual.getPk().getIndicadorId();
          fieldNames[1] = "pk.planId";
          fieldValues[1] = indicadorPlanLogroAnual.getPk().getPlanId();
          if (!persistenceSession.existsObject(indicadorPlanLogroAnual, fieldNames, fieldValues)) {
            resultado = persistenceSession.insert(indicadorPlanLogroAnual, usuario);
          }
          
          if (resultado == 10000)
          {
            IndicadorPlan indicadorPlanLogroParcial = new IndicadorPlan();
            
            indicadorPlanLogroParcial.setPk(new IndicadorPlanPK());
            indicadorPlanLogroParcial.getPk().setIndicadorId(perspectiva.getNlParIndicadorId());
            indicadorPlanLogroParcial.getPk().setPlanId(perspectiva.getPlanId());
            
            fieldNames[0] = "pk.indicadorId";
            fieldValues[0] = indicadorPlanLogroParcial.getPk().getIndicadorId();
            fieldNames[1] = "pk.planId";
            fieldValues[1] = indicadorPlanLogroParcial.getPk().getPlanId();
            if (!persistenceSession.existsObject(indicadorPlanLogroParcial, fieldNames, fieldValues)) {
              resultado = persistenceSession.insert(indicadorPlanLogroParcial, usuario);
            }
          }
        }
      }
      



      if (transActiva) {
        if (resultado == 10000) {
          persistenceSession.commitTransaction();
        } else {
          persistenceSession.rollbackTransaction();
        }
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
  
  public Perspectiva crearPerspectivaRaiz(Long planId, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    Perspectiva perspectiva = new Perspectiva();
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if ((planId != null) && (planId.byteValue() != 0))
      {
        perspectiva.setPerspectivaId(new Long(0L));
        perspectiva.setPlanId(planId);
        perspectiva.setPadreId(null);
        
        StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
        Byte frecuenciaMaxima = strategosIndicadoresService.getFrecuenciaMaximaIndicadoresPlan(planId);
        if (frecuenciaMaxima == null)
          frecuenciaMaxima = Frecuencia.getFrecuenciaTrimestral();
        strategosIndicadoresService.close();
        
        perspectiva.setFrecuencia(frecuenciaMaxima);
        perspectiva.setTipo(new Byte((byte)0));
        perspectiva.setTipoCalculo(TipoCalculoPerspectiva.getTipoCalculoPerspectivaAutomatico());
        
        StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(this);
        
        Plan plan = (Plan)strategosPlanesService.load(Plan.class, planId);
        perspectiva.setNombre(plan.getNombre());
        
        strategosPlanesService.close();
        
        resultado = savePerspectiva(perspectiva, usuario);
        
        if (resultado == 10000) {
          perspectiva = (Perspectiva)persistenceSession.load(Perspectiva.class, perspectiva.getPerspectivaId());
        } else {
          perspectiva = null;
        }
      }
      else {
        perspectiva = null;
      }
      
      if (transActiva) {
        if ((resultado == 10000) && (perspectiva != null)) {
          persistenceSession.commitTransaction();
        } else {
          persistenceSession.rollbackTransaction();
        }
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
    
    return perspectiva;
  }
  
  public boolean asociarIniciativa(Perspectiva perspectiva, Long iniciativaId, Long planId, Long organizacionId, Usuario usuario)
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
      
      if (perspectiva.getPadreId() != null)
      {
        IniciativaPerspectiva iniciativaPerspectiva = new IniciativaPerspectiva();
        
        iniciativaPerspectiva.setPk(new IniciativaPerspectivaPK());
        iniciativaPerspectiva.getPk().setIniciativaId(iniciativaId);
        iniciativaPerspectiva.getPk().setPerspectivaId(perspectiva.getPerspectivaId());
        
        fieldNames[0] = "pk.iniciativaId";
        fieldValues[0] = iniciativaPerspectiva.getPk().getIniciativaId();
        fieldNames[1] = "pk.perspectivaId";
        fieldValues[1] = iniciativaPerspectiva.getPk().getPerspectivaId();
        if (!persistenceSession.existsObject(iniciativaPerspectiva, fieldNames, fieldValues)) {
          resultado = persistenceSession.insert(iniciativaPerspectiva, usuario);
        }
        if ((resultado == 10000) && (planId != null))
        {
          StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(this);
          resultado = strategosPlanesService.asociarIniciativa(planId, iniciativaId, organizacionId, usuario);
          strategosPlanesService.close();
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
    
    return resultado == 10000;
  }
  
  public boolean asociarIndicador(Long planId, Perspectiva perspectiva, Long indicadorId, Boolean soloAsociar, Usuario usuario)
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
      
      if (perspectiva.getPadreId() != null)
      {
        if (soloAsociar.booleanValue())
        {
          IndicadorPerspectiva indicadorPerspectiva = new IndicadorPerspectiva();
          
          indicadorPerspectiva.setPk(new IndicadorPerspectivaPK());
          indicadorPerspectiva.getPk().setIndicadorId(indicadorId);
          indicadorPerspectiva.getPk().setPerspectivaId(perspectiva.getPerspectivaId());
          
          fieldNames[0] = "pk.indicadorId";
          fieldValues[0] = indicadorPerspectiva.getPk().getIndicadorId();
          fieldNames[1] = "pk.perspectivaId";
          fieldValues[1] = indicadorPerspectiva.getPk().getPerspectivaId();
          if (!persistenceSession.existsObject(indicadorPerspectiva, fieldNames, fieldValues)) {
            resultado = persistenceSession.insert(indicadorPerspectiva, usuario);
          }
        }
        else {
          Perspectiva perspectivaPadre = (Perspectiva)persistenceSession.load(Perspectiva.class, perspectiva.getPadreId());
          
          if (perspectivaPadre.getPadreId() == null)
          {
            IndicadorPlan indicadorPlan = new IndicadorPlan();
            
            indicadorPlan.setPk(new IndicadorPlanPK());
            indicadorPlan.getPk().setIndicadorId(indicadorId);
            indicadorPlan.getPk().setPlanId(planId);
            
            fieldNames[0] = "pk.indicadorId";
            fieldValues[0] = indicadorPlan.getPk().getIndicadorId();
            fieldNames[1] = "pk.planId";
            fieldValues[1] = indicadorPlan.getPk().getPlanId();
            if (!persistenceSession.existsObject(indicadorPlan, fieldNames, fieldValues)) {
              resultado = persistenceSession.insert(indicadorPlan, usuario);
            }
          }
          if (perspectivaPadre.getPadreId() != null)
          {
            IndicadorPerspectiva indicadorPerspectiva = new IndicadorPerspectiva();
            
            indicadorPerspectiva.setPk(new IndicadorPerspectivaPK());
            indicadorPerspectiva.getPk().setIndicadorId(indicadorId);
            indicadorPerspectiva.getPk().setPerspectivaId(perspectiva.getPadreId());
            
            fieldNames[0] = "pk.indicadorId";
            fieldValues[0] = indicadorPerspectiva.getPk().getIndicadorId();
            fieldNames[1] = "pk.perspectivaId";
            fieldValues[1] = indicadorPerspectiva.getPk().getPerspectivaId();
            if (!persistenceSession.existsObject(indicadorPerspectiva, fieldNames, fieldValues))
            {
              resultado = persistenceSession.insert(indicadorPerspectiva, usuario);
            }
          }
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
    
    return resultado == 10000;
  }
  
  public int desasociarIndicador(IndicadorPerspectiva indicadorPerspectiva, IndicadorPlan indicadorPlan, Usuario usuario)
  {
    return desasociarIndicador(indicadorPerspectiva, indicadorPlan, usuario, Boolean.valueOf(false));
  }
  
  private int desasociarIndicador(IndicadorPerspectiva indicadorPerspectiva, IndicadorPlan indicadorPlan, Usuario usuario, Boolean fromDeletePerspectiva)
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
      
      if (indicadorPerspectiva != null) {
        resultado = persistenceSession.delete(indicadorPerspectiva, usuario);
      }
      if (resultado == 10000)
      {
        if (indicadorPlan != null) {
          resultado = persistenceSession.delete(indicadorPlan, usuario);
        }
        if ((indicadorPerspectiva != null) && (resultado == 10000))
        {
          StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
          StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
          StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(this);
          
          resultado = persistenceSession.deletePerspectivaEstados(indicadorPerspectiva.getPerspectiva().getPerspectivaId(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), null, null, null, null);
          if (resultado == 10000)
          {
            resultado = strategosMedicionesService.deleteMediciones(indicadorPerspectiva.getPerspectiva().getNlParIndicadorId());
            if (resultado == 10000) {
              resultado = strategosIndicadoresService.actualizarDatosIndicador(indicadorPerspectiva.getPerspectiva().getNlParIndicadorId(), null, null, null);
            }
            if (resultado == 10000)
            {
              IndicadorPlanPK indicadorPlanPk = new IndicadorPlanPK();
              indicadorPlanPk.setIndicadorId(indicadorPerspectiva.getPerspectiva().getNlParIndicadorId());
              indicadorPlanPk.setPlanId(indicadorPerspectiva.getPerspectiva().getPlanId());
              indicadorPlan = (IndicadorPlan)persistenceSession.load(IndicadorPlan.class, indicadorPlanPk);
              if (indicadorPlan != null)
              {
                indicadorPlan.setCrecimiento(null);
                resultado = persistenceSession.update(indicadorPlan, usuario);
              }
            }
            if (resultado == 10000)
              resultado = strategosPlanesService.deleteIndicadorEstado(indicadorPerspectiva.getPerspectiva().getNlParIndicadorId(), indicadorPerspectiva.getPerspectiva().getPlanId(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), null, null);
            if (resultado == 10000) {
              resultado = strategosPlanesService.deleteIndicadorEstado(indicadorPerspectiva.getPerspectiva().getNlParIndicadorId(), indicadorPerspectiva.getPerspectiva().getPlanId(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), null, null);
            }
          }
          if (resultado == 10000)
          {
            resultado = persistenceSession.deletePerspectivaEstados(indicadorPerspectiva.getPerspectiva().getPerspectivaId(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), null, null, null, null);
            if (resultado == 10000)
            {
              resultado = strategosMedicionesService.deleteMediciones(indicadorPerspectiva.getPerspectiva().getNlAnoIndicadorId());
              if (resultado == 10000) {
                resultado = strategosIndicadoresService.actualizarDatosIndicador(indicadorPerspectiva.getPerspectiva().getNlAnoIndicadorId(), null, null, null);
              }
              if (resultado == 10000)
              {
                IndicadorPlanPK indicadorPlanPk = new IndicadorPlanPK();
                indicadorPlanPk.setIndicadorId(indicadorPerspectiva.getPerspectiva().getNlAnoIndicadorId());
                indicadorPlanPk.setPlanId(indicadorPerspectiva.getPerspectiva().getPlanId());
                indicadorPlan = (IndicadorPlan)persistenceSession.load(IndicadorPlan.class, indicadorPlanPk);
                if (indicadorPlan != null)
                {
                  indicadorPlan.setCrecimiento(null);
                  resultado = persistenceSession.update(indicadorPlan, usuario);
                }
              }
              if (resultado == 10000)
                resultado = strategosPlanesService.deleteIndicadorEstado(indicadorPerspectiva.getPerspectiva().getNlAnoIndicadorId(), indicadorPerspectiva.getPerspectiva().getPlanId(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), null, null);
              if (resultado == 10000) {
                resultado = strategosPlanesService.deleteIndicadorEstado(indicadorPerspectiva.getPerspectiva().getNlAnoIndicadorId(), indicadorPerspectiva.getPerspectiva().getPlanId(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), null, null);
              }
            }
          }
          strategosPlanesService.close();
          strategosIndicadoresService.close();
          strategosMedicionesService.close();
          
          if ((resultado == 10000) && (!fromDeletePerspectiva.booleanValue())) {
            resultado = persistenceSession.actualizarDatosPerspectiva(indicadorPerspectiva.getPerspectiva().getPerspectivaId(), null, null, null);
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
  
  
  
  
  public boolean desasociarIniciativa(Long perspectivaId, Long iniciativaId, Long planId, Usuario usuario)
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
      
      IniciativaPerspectiva iniciativaPerspectiva = new IniciativaPerspectiva();
      
      iniciativaPerspectiva.setPk(new IniciativaPerspectivaPK());
      iniciativaPerspectiva.getPk().setIniciativaId(iniciativaId);
      iniciativaPerspectiva.getPk().setPerspectivaId(perspectivaId);
      
      resultado = persistenceSession.delete(iniciativaPerspectiva, usuario);
      
      if (resultado == 10000)
      {
        if (persistenceSession.getNumeroAsociacionesIniciativaPerspectivaPorPlan(iniciativaId, planId) == 0L)
        {
          StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(this);
          
          resultado = strategosPlanesService.desasociarIniciativa(planId, iniciativaId, usuario);
          
          strategosPlanesService.close();
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
    
    return resultado == 10000;
  }
  
  public List<Perspectiva> getPerspectivas(String[] orden, String[] tipoOrden, Map<String, Object> filtros)
  {
    return persistenceSession.getPerspectivas(orden, tipoOrden, filtros);
  }
  
  public Perspectiva getPerspectivaRaiz(Long planId) {
    return persistenceSession.getPerspectivaRaiz(planId);
  }
  
  public int deletePerspectivaEstados(Long perspectivaId, Byte tipoEstado, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal) {
    return persistenceSession.deletePerspectivaEstados(perspectivaId, tipoEstado, anoInicio, anoFinal, periodoInicio, periodoFinal);
  }
  
  public List getIndicadoresPerspectiva(Long perspectivaId)
  {
    return persistenceSession.getIndicadoresPerspectiva(perspectivaId);
  }
  
  public List<Perspectiva> getIndicadoresPorPerspectiva(Long indicadorId, Long planId)
  {
    return persistenceSession.getIndicadoresPorPerspectiva(indicadorId, planId);
  }
  
  public int savePerspectivaEstado(Long perspectivaId, Byte tipoEstado, Integer ano, Integer periodo, Double valorEstado, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[4];
    Object[] fieldValues = new Object[4];
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      PerspectivaEstado perspectivaEstado = new PerspectivaEstado();
      PerspectivaEstadoPK perspectivaEstadoPk = new PerspectivaEstadoPK();
      perspectivaEstadoPk.setPerspectivaId(perspectivaId);
      perspectivaEstadoPk.setTipo(tipoEstado);
      perspectivaEstadoPk.setAno(ano);
      perspectivaEstadoPk.setPeriodo(periodo);
      perspectivaEstado.setPk(perspectivaEstadoPk);
      perspectivaEstado.setEstado(valorEstado);
      fieldNames[0] = "pk.perspectivaId";
      fieldNames[1] = "pk.tipo";
      fieldNames[2] = "pk.ano";
      fieldNames[3] = "pk.periodo";
      fieldValues[0] = perspectivaEstadoPk.getPerspectivaId();
      fieldValues[1] = perspectivaEstadoPk.getTipo();
      fieldValues[2] = perspectivaEstadoPk.getAno();
      fieldValues[3] = perspectivaEstadoPk.getPeriodo();
      
      if (persistenceSession.existsObject(perspectivaEstado, fieldNames, fieldValues)) {
        resultado = persistenceSession.update(perspectivaEstado, usuario);
      } else {
        resultado = persistenceSession.insert(perspectivaEstado, usuario);
      }
      
      if (transActiva) {
        if (resultado == 10000) {
          persistenceSession.commitTransaction();
        } else {
          persistenceSession.rollbackTransaction();
        }
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
  
  public int actualizarPerspectivaUltimoEstado(Long perspectivaId) {
    return persistenceSession.actualizarPerspectivaUltimoEstado(perspectivaId);
  }
  
  public int updatePesosIndicadoresPerspectiva(List indicadoresPerspectiva, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      resultado = persistenceSession.updatePesosIndicadoresPerspectiva(indicadoresPerspectiva, usuario);
      
      if (transActiva) {
        if (resultado == 10000) {
          persistenceSession.commitTransaction();
        } else {
          persistenceSession.rollbackTransaction();
        }
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
  
  public Map<Long, Byte> getAlertasPerspectivas(Map<String, String> perspectivaIds, Byte tipo)
  {
    return persistenceSession.getAlertasPerspectivas(perspectivaIds, tipo);
  }
  
  public List<PerspectivaEstado> getPerspectivaEstados(Long perspectivaId, Byte tipo, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta)
  {
    return persistenceSession.getPerspectivaEstados(perspectivaId, tipo, anoDesde, anoHasta, periodoDesde, periodoHasta);
  }
  
  private int verificarCambiosPerspectiva(Perspectiva perspectiva, Perspectiva perspectivaOriginal)
  {
    int resultado = 10000;
    
    boolean eliminarMediciones = false;
    try
    {
      if (perspectiva.getFrecuencia().byteValue() != perspectivaOriginal.getFrecuencia().byteValue()) {
        eliminarMediciones = true;
      }
      if (eliminarMediciones)
      {
        resultado = persistenceSession.deletePerspectivaEstados(perspectiva.getPerspectivaId(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), null, null, null, null);
        if (resultado == 10000) {
          resultado = persistenceSession.deletePerspectivaEstados(perspectiva.getPerspectivaId(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), null, null, null, null);
        }
        resultado = persistenceSession.actualizarDatosPerspectiva(perspectiva.getPerspectivaId(), null, null, null);
      }
    }
    catch (Throwable t)
    {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return resultado;
  }
  
  public List<Perspectiva> getPerspectivasAsociadas(Long perspectivaId)
  {
    return persistenceSession.getPerspectivasAsociadas(perspectivaId);
  }
  
  public List<PerspectivaEstado> getPerspectivaEstadosUltimoPeriodo(Long planId, Byte tipo, Integer ano)
  {
    return persistenceSession.getPerspectivaEstadosUltimoPeriodo(planId, tipo, ano);
  }
  
  public int updateCampo(Long perspectivaId, Map<?, ?> filtros) throws Throwable
  {
    return persistenceSession.updateCampo(perspectivaId, filtros);
  }

  public int desasociarIndicador(Long indicadorId, Long planId,
		IndicadorPerspectiva indicadorPerspectiva,
		IndicadorPlan indicadorPlan, Usuario usuario) {
	  
	  Boolean fromDeletePerspectiva = false;  
	    boolean transActiva = false;
	    int resultado = 10000;
	    try
	    {
	      if (!persistenceSession.isTransactionActive())
	      {
	        persistenceSession.beginTransaction();
	        transActiva = true;
	      }
	      
	      if (indicadorPerspectiva != null) {
	        resultado = persistenceSession.delete(indicadorPerspectiva, usuario);
	      }
	      if (resultado == 10000)
	      {
	        if (indicadorPlan != null) {
	          resultado = persistenceSession.delete(indicadorPlan, usuario);
	        }
	        if ((indicadorPerspectiva != null) && (resultado == 10000))
	        {
	          StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
	          StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
	          StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(this);
	          
	          resultado = persistenceSession.deletePerspectivaEstados(indicadorPerspectiva.getPerspectiva().getPerspectivaId(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), null, null, null, null);
	          if (resultado == 10000)
	          {
	            resultado = strategosMedicionesService.deleteMediciones(indicadorId);
	            if (resultado == 10000) {
	              resultado = strategosIndicadoresService.actualizarDatosIndicador(indicadorId, null, null, null);
	            }
	            if (resultado == 10000)
	            {
	              IndicadorPlanPK indicadorPlanPk = new IndicadorPlanPK();
	              indicadorPlanPk.setIndicadorId(indicadorId);
	              indicadorPlanPk.setPlanId(planId);
	              indicadorPlan = (IndicadorPlan)persistenceSession.load(IndicadorPlan.class, indicadorPlanPk);
	              if (indicadorPlan != null)
	              {
	                indicadorPlan.setCrecimiento(null);
	                resultado = persistenceSession.update(indicadorPlan, usuario);
	              }
	            }
	            if (resultado == 10000)
	              resultado = strategosPlanesService.deleteIndicadorEstado(indicadorId, planId, TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), null, null);
	            if (resultado == 10000) {
	              resultado = strategosPlanesService.deleteIndicadorEstado(indicadorId, planId, TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), null, null);
	            }
	          }
	          if (resultado == 10000)
	          {
	            resultado = persistenceSession.deletePerspectivaEstados(indicadorPerspectiva.getPerspectiva().getPerspectivaId(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), null, null, null, null);
	            if (resultado == 10000)
	            {
	              resultado = strategosMedicionesService.deleteMediciones(indicadorPerspectiva.getPerspectiva().getNlAnoIndicadorId());
	              if (resultado == 10000) {
	                resultado = strategosIndicadoresService.actualizarDatosIndicador(indicadorPerspectiva.getPerspectiva().getNlAnoIndicadorId(), null, null, null);
	              }
	              if (resultado == 10000)
	              {
	                IndicadorPlanPK indicadorPlanPk = new IndicadorPlanPK();
	                indicadorPlanPk.setIndicadorId(indicadorPerspectiva.getPerspectiva().getNlAnoIndicadorId());
	                indicadorPlanPk.setPlanId(planId);
	                indicadorPlan = (IndicadorPlan)persistenceSession.load(IndicadorPlan.class, indicadorPlanPk);
	                if (indicadorPlan != null)
	                {
	                  indicadorPlan.setCrecimiento(null);
	                  resultado = persistenceSession.update(indicadorPlan, usuario);
	                }
	              }
	              if (resultado == 10000)
	                resultado = strategosPlanesService.deleteIndicadorEstado(indicadorPerspectiva.getPerspectiva().getNlAnoIndicadorId(), planId, TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), null, null);
	              if (resultado == 10000) {
	                resultado = strategosPlanesService.deleteIndicadorEstado(indicadorPerspectiva.getPerspectiva().getNlAnoIndicadorId(), planId, TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), null, null);
	              }
	            }
	          }
	          strategosPlanesService.close();
	          strategosIndicadoresService.close();
	          strategosMedicionesService.close();
	          
	          if ((resultado == 10000) && (!fromDeletePerspectiva.booleanValue())) {
	            resultado = persistenceSession.actualizarDatosPerspectiva(indicadorPerspectiva.getPerspectiva().getPerspectivaId(), null, null, null);
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
}
