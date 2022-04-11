package com.visiongc.app.strategos.planes.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.PrioridadIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoAlerta;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosModelosService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IndicadorEstado;
import com.visiongc.app.strategos.planes.model.IndicadorEstadoPK;
import com.visiongc.app.strategos.planes.model.IndicadorPlan;
import com.visiongc.app.strategos.planes.model.IndicadorPlanPK;
import com.visiongc.app.strategos.planes.model.IniciativaPlan;
import com.visiongc.app.strategos.planes.model.IniciativaPlanPK;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.MetaPK;
import com.visiongc.app.strategos.planes.model.Modelo;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlanEstado;
import com.visiongc.app.strategos.planes.model.PlanEstadoPK;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.planes.persistence.StrategosPlanesPersistenceSession;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Usuario;
import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;







public class StrategosPlanesServiceImpl
  extends StrategosServiceImpl
  implements StrategosPlanesService
{
  private StrategosPlanesPersistenceSession persistenceSession = null;
  
  public StrategosPlanesServiceImpl(StrategosPlanesPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getReporteSeguimiento(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    return persistenceSession.getReporteSeguimiento(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public PaginaLista getPlanes(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    return persistenceSession.getPlanes(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  private int deleteDependenciasPlan(Plan plan, Usuario usuario)
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
      
      resultado = persistenceSession.deleteReferenciasForaneasPlan(plan.getPlanId());
      
      if (resultado == 10000)
      {
        dependencias = persistenceSession.getDependenciasPlan(plan);
        
        for (Iterator<?> i = dependencias.iterator(); i.hasNext();)
        {
          listaObjetosRelacionados = (List)i.next();
          
          if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Perspectiva)))
          {
            StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService(this);
            
            for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();)
            {
              Perspectiva perspectiva = (Perspectiva)j.next();
              
              resultado = strategosPerspectivasService.deletePerspectiva(perspectiva, usuario);
              
              if (resultado != 10000)
                break;
            }
            strategosPerspectivasService.close();
          }
          else if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Modelo)))
          {
            StrategosModelosService strategosModelosService = StrategosServiceFactory.getInstance().openStrategosModelosService(this);
            
            for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();)
            {
              Modelo modelo = (Modelo)j.next();
              
              resultado = strategosModelosService.deleteModelo(modelo, usuario);
              
              if (resultado != 10000)
                break;
            }
            strategosModelosService.close();
          }
          else if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Iniciativa)))
          {
            for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();)
            {
              Iniciativa iniciativa = (Iniciativa)j.next();
              
              resultado = desasociarIniciativa(plan.getPlanId(), iniciativa.getIniciativaId(), usuario);
              
              if (resultado != 10000) {
                break;
              }
            }
          }
          else {
            for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();)
            {
              Object objeto = j.next();
              
              resultado = persistenceSession.delete(objeto, usuario);
              
              if (resultado != 10000) {
                break;
              }
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
          return resultado;
        }
      }
      
      if (transActiva)
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
  
  private int deletePostDependenciasPlan(Plan plan, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    List listaObjetosRelacionados = null;
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      ListaMap postDependencias = persistenceSession.getPostDependenciasPlan(plan);
      
      for (Iterator i = postDependencias.iterator(); i.hasNext();)
      {
        listaObjetosRelacionados = (List)i.next();
        
        if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Indicador)))
        {
          StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
          
          for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            Indicador indicador = (Indicador)j.next();
            
            resultado = strategosIndicadoresService.deleteIndicador(indicador, usuario);
            
            if (resultado != 10000) {
              break;
            }
          }
          strategosIndicadoresService.close();
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
  
  public int deletePlan(Plan plan, Usuario usuario, Boolean deletedPlan)
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
      
      if (plan.getPlanId() != null)
      {
        if ((plan.getHijos() != null) && (plan.getHijos().size() > 0))
        {
          for (Iterator<Plan> iter = plan.getHijos().iterator(); iter.hasNext();)
          {
            Plan planHijo = (Plan)iter.next();
            resultado = deletePlan(planHijo, usuario, deletedPlan);
            if (resultado != 10000)
              break;
          }
        }
        if (resultado == 10000) {
          resultado = deleteDependenciasPlan(plan, usuario);
        }
        if (resultado == 10000)
        {
          if (deletedPlan.booleanValue()) {
            resultado = persistenceSession.delete(plan, usuario);
          }
          if (resultado == 10000) {
            resultado = deletePostDependenciasPlan(plan, usuario);
          }
        }
      } else {
        return 10007;
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
  
  public int savePlan(Plan plan, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[5];
    Object[] fieldValues = new Object[5];
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      fieldNames[0] = "nombre";
      fieldNames[1] = "organizacionId";
      fieldNames[2] = "revision";
      fieldNames[3] = "padreId";
      fieldNames[4] = "tipo";
      fieldValues[0] = plan.getNombre();
      fieldValues[1] = plan.getOrganizacionId();
      fieldValues[2] = plan.getRevision();
      fieldValues[3] = plan.getPadreId();
      fieldValues[4] = plan.getTipo();
      
      if ((plan.getPlanId() == null) || (plan.getPlanId().longValue() == 0L))
      {
        if (persistenceSession.existsObject(plan, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          plan.setPlanId(new Long(persistenceSession.getUniqueId()));
          
          resultado = saveIndicadoresLogro(plan, usuario);
          if (resultado == 10000)
          {
            resultado = persistenceSession.insert(plan, usuario);
            
            if (resultado == 10000) {
              resultado = saveIndicadoresCrecimiento(plan, usuario);
            }
          }
        }
      }
      else {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "planId";
        idFieldValues[0] = plan.getPlanId();
        
        if (persistenceSession.existsObject(plan, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        }
        else {
          resultado = saveIndicadoresLogro(plan, usuario);
          if (resultado == 10000) {
            resultado = saveIndicadoresCrecimiento(plan, usuario);
          }
          if (resultado == 10000) {
            resultado = persistenceSession.update(plan, usuario);
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
  
  private int saveIndicadoresLogro(Plan plan, Usuario usuario)
  {
    int resultado = 10000;
    try
    {
      StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
      
      OrganizacionStrategos organizacion = (OrganizacionStrategos)persistenceSession.load(OrganizacionStrategos.class, plan.getOrganizacionId());
      
      Integer porcentajeZonaVerde = organizacion.getPorcentajeZonaVerdeMetaIndicadores();
      Integer porcentajeZonaAmarilla = organizacion.getPorcentajeZonaAmarillaMetaIndicadores();
      Byte frecuenciaMaxima = strategosIndicadoresService.getFrecuenciaMaximaIndicadoresPlan(plan.getPlanId());
      
      if (frecuenciaMaxima == null) {
        frecuenciaMaxima = Frecuencia.getFrecuenciaTrimestral();
      }
      Indicador indicadorLogroAnual = null;
      SerieIndicador serie = new SerieIndicador();
      
      if (plan.getNlAnoIndicadorId() == null) {
        indicadorLogroAnual = new Indicador();
      }
      else {
        indicadorLogroAnual = (Indicador)strategosIndicadoresService.load(Indicador.class, plan.getNlAnoIndicadorId());
        if (indicadorLogroAnual == null) {
          indicadorLogroAnual = new Indicador();
        }
      }
      String nombreIndicador = plan.getNombre();
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
      indicadorLogroAnual.setOrganizacionId(plan.getOrganizacionId());
      indicadorLogroAnual.setFrecuencia(frecuenciaMaxima);
      indicadorLogroAnual.setClaseId(plan.getClaseId());
      indicadorLogroAnual.setCorte(TipoCorte.getTipoCorteTransversal());
      indicadorLogroAnual.setNaturaleza(Naturaleza.getNaturalezaSimple());
      indicadorLogroAnual.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionPerspectiva());
      indicadorLogroAnual.setAlertaTipoZonaAmarilla(TipoAlerta.getTipoAlertaPorcentaje());
      indicadorLogroAnual.setAlertaTipoZonaVerde(TipoAlerta.getTipoAlertaPorcentaje());
      if (porcentajeZonaAmarilla != null)
        indicadorLogroAnual.setAlertaMetaZonaAmarilla(Double.valueOf(porcentajeZonaAmarilla.doubleValue()));
      if (porcentajeZonaVerde != null)
        indicadorLogroAnual.setAlertaMetaZonaVerde(Double.valueOf(porcentajeZonaVerde.doubleValue()));
      indicadorLogroAnual.setGuia(new Boolean(false));
      indicadorLogroAnual.setMostrarEnArbol(new Boolean(false));
      indicadorLogroAnual.setPrioridad(PrioridadIndicador.getPrioridadIndicadorAlta());
      indicadorLogroAnual.setCaracteristica(Caracteristica.getCaracteristicaRetoAumento());
      indicadorLogroAnual.setNumeroDecimales(new Byte("2"));
      
      StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService(strategosIndicadoresService);
      UnidadMedida unidad = strategosUnidadesService.getUnidadMedidaPorcentaje();
      strategosUnidadesService.close();
      
      indicadorLogroAnual.setUnidadId(unidad.getUnidadId());
      
      if (plan.getNlAnoIndicadorId() == null)
      {
        serie = new SerieIndicador();
        serie.setPk(new SerieIndicadorPK());
        serie.getPk().setIndicadorId(new Long(0L));
        serie.getPk().setSerieId(SerieTiempo.getSerieReal().getSerieId());
        serie.setNaturaleza(Naturaleza.getNaturalezaSimple());
        indicadorLogroAnual.setSeriesIndicador(new HashSet());
        indicadorLogroAnual.getSeriesIndicador().clear();
        indicadorLogroAnual.getSeriesIndicador().add(serie);
      }
      
      resultado = strategosIndicadoresService.saveIndicador(indicadorLogroAnual, usuario);
      if (resultado == 10003)
      {
        Map<String, Object> filtros = new HashMap();
        
        filtros.put("claseId", indicadorLogroAnual.getClaseId());
        filtros.put("nombre", indicadorLogroAnual.getNombre());
        List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
        if (inds.size() > 0)
        {
          indicadorLogroAnual = (Indicador)inds.get(0);
          resultado = 10000;
        }
      }
      
      if (resultado != 10000)
        return resultado;
      if (resultado == 10000) {
        plan.setNlAnoIndicadorId(indicadorLogroAnual.getIndicadorId());
      }
      Indicador indicadorLogroParcial = null;
      
      if (plan.getNlParIndicadorId() == null) {
        indicadorLogroParcial = new Indicador();
      }
      else {
        indicadorLogroParcial = (Indicador)strategosIndicadoresService.load(Indicador.class, plan.getNlParIndicadorId());
        
        if (indicadorLogroParcial == null) {
          indicadorLogroParcial = new Indicador();
        }
      }
      nombreIndicador = plan.getNombre();
      if (nombreIndicador.length() > 80) {
        nombreIndicador = nombreIndicador.substring(0, 80);
      }
      nombreIndicador = nombreIndicador + "... (" + messageResources.getResource("indicador.logroparcial") + ")";
      if (nombreIndicador.length() > 100)
        nombreIndicador = nombreIndicador.substring(0, 100);
      indicadorLogroParcial.setNombre(nombreIndicador);
      nombreIndicador = indicadorLogroParcial.getNombre();
      if (nombreIndicador.length() > 50)
        nombreIndicador = nombreIndicador.substring(0, 50);
      indicadorLogroParcial.setNombreCorto(nombreIndicador);
      indicadorLogroParcial.setOrganizacionId(plan.getOrganizacionId());
      indicadorLogroParcial.setFrecuencia(frecuenciaMaxima);
      indicadorLogroParcial.setClaseId(plan.getClaseId());
      indicadorLogroParcial.setCorte(TipoCorte.getTipoCorteTransversal());
      indicadorLogroParcial.setNaturaleza(Naturaleza.getNaturalezaSimple());
      indicadorLogroParcial.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionPerspectiva());
      indicadorLogroParcial.setAlertaTipoZonaAmarilla(TipoAlerta.getTipoAlertaPorcentaje());
      indicadorLogroParcial.setAlertaTipoZonaVerde(TipoAlerta.getTipoAlertaPorcentaje());
      if (porcentajeZonaAmarilla != null)
        indicadorLogroParcial.setAlertaMetaZonaAmarilla(Double.valueOf(porcentajeZonaAmarilla.doubleValue()));
      if (porcentajeZonaVerde != null)
        indicadorLogroParcial.setAlertaMetaZonaVerde(Double.valueOf(porcentajeZonaVerde.doubleValue()));
      indicadorLogroParcial.setGuia(new Boolean(false));
      indicadorLogroParcial.setMostrarEnArbol(new Boolean(false));
      indicadorLogroParcial.setPrioridad(PrioridadIndicador.getPrioridadIndicadorBaja());
      indicadorLogroParcial.setCaracteristica(Caracteristica.getCaracteristicaRetoAumento());
      indicadorLogroParcial.setNumeroDecimales(new Byte("2"));
      indicadorLogroParcial.setUnidadId(unidad.getUnidadId());
      
      if (plan.getNlAnoIndicadorId() == null)
      {
        serie = new SerieIndicador();
        serie.setPk(new SerieIndicadorPK());
        serie.getPk().setIndicadorId(new Long(0L));
        serie.getPk().setSerieId(SerieTiempo.getSerieReal().getSerieId());
        serie.setNaturaleza(Naturaleza.getNaturalezaSimple());
        indicadorLogroParcial.setSeriesIndicador(new HashSet());
        indicadorLogroParcial.getSeriesIndicador().clear();
        indicadorLogroParcial.getSeriesIndicador().add(serie);
      }
      
      resultado = strategosIndicadoresService.saveIndicador(indicadorLogroParcial, usuario);
      if (resultado == 10003)
      {
        Map<String, Object> filtros = new HashMap();
        
        filtros.put("claseId", indicadorLogroParcial.getClaseId());
        filtros.put("nombre", indicadorLogroParcial.getNombre());
        List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
        if (inds.size() > 0)
        {
          indicadorLogroParcial = (Indicador)inds.get(0);
          resultado = 10000;
        }
      }
      
      if (resultado != 10000)
        return resultado;
      if (resultado == 10000) {
        plan.setNlParIndicadorId(indicadorLogroParcial.getIndicadorId());
      }
    }
    catch (Throwable t) {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return resultado;
  }
  
  private int saveIndicadoresCrecimiento(Plan plan, Usuario usuario)
  {
    int resultado = 10000;
    boolean transActiva = false;
    String[] fieldNames = new String[2];
    Object[] fieldValues = new Object[2];
    
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      IndicadorPlan indicadorPlanLogroAnual = new IndicadorPlan();
      
      IndicadorPlanPK pk = new IndicadorPlanPK();
      pk.setIndicadorId(plan.getNlAnoIndicadorId());
      pk.setPlanId(plan.getPlanId());
      indicadorPlanLogroAnual.setPk(pk);
      
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
        indicadorPlanLogroParcial.getPk().setIndicadorId(plan.getNlParIndicadorId());
        indicadorPlanLogroParcial.getPk().setPlanId(plan.getPlanId());
        
        fieldNames[0] = "pk.indicadorId";
        fieldValues[0] = indicadorPlanLogroParcial.getPk().getIndicadorId();
        fieldNames[1] = "pk.planId";
        fieldValues[1] = indicadorPlanLogroParcial.getPk().getPlanId();
        if (!persistenceSession.existsObject(indicadorPlanLogroParcial, fieldNames, fieldValues)) {
          resultado = persistenceSession.insert(indicadorPlanLogroParcial, usuario);
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
  
  public int activarPlan(Long planId, boolean activar)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      resultado = persistenceSession.activarPlan(planId, activar);
      
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
  
  public List getPlanesPorOrganizacion(Long organizacionId, String orden, String tipoOrden)
  {
    Map filtros = new HashMap();
    
    filtros.put("organizacionId", organizacionId.toString());
    
    return persistenceSession.getPlanes(0, 0, "nombre", "asc", false, filtros).getLista();
  }
  
  public int asociarIniciativa(Long planId, Long iniciativaId, Long organizacionId, Usuario usuario)
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
      
      IniciativaPlan iniciativaPlan = new IniciativaPlan();
      iniciativaPlan.setPk(new IniciativaPlanPK());
      iniciativaPlan.getPk().setIniciativaId(iniciativaId);
      iniciativaPlan.getPk().setPlanId(planId);
      
      fieldNames[0] = "pk.iniciativaId";
      fieldValues[0] = iniciativaPlan.getPk().getIniciativaId();
      fieldNames[1] = "pk.planId";
      fieldValues[1] = iniciativaPlan.getPk().getPlanId();
      if (!persistenceSession.existsObject(iniciativaPlan, fieldNames, fieldValues)) {
        resultado = persistenceSession.insert(iniciativaPlan, usuario);
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
  
  public int desasociarIniciativa(Long planId, Long iniciativaId, Usuario usuario)
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
      
      IniciativaPlan iniciativaPlan = new IniciativaPlan();
      iniciativaPlan.setPk(new IniciativaPlanPK());
      iniciativaPlan.getPk().setIniciativaId(iniciativaId);
      iniciativaPlan.getPk().setPlanId(planId);
      
      resultado = persistenceSession.delete(iniciativaPlan, usuario);
      
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
      resultado = 10003;
      if (transActiva)
      {
        persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    
    return resultado;
  }
  
  public List getPlanesAsociadosPorIndicador(Long indicadorId, boolean activos, boolean inactivos)
  {
    return persistenceSession.getPlanesAsociadosPorIndicador(indicadorId, activos, inactivos);
  }
  
  public int deleteIndicadorEstado(Long indicadorId, Long planId, Byte tipo, Integer ano, Integer periodo) {
    return persistenceSession.deleteIndicadorEstados(indicadorId, planId, tipo, ano, ano, periodo, periodo);
  }
  
  public int deleteIndicadorEstados(Long indicadorId, Long planId, Byte tipo, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal) {
    return persistenceSession.deleteIndicadorEstados(indicadorId, planId, tipo, anoInicio, anoFinal, periodoInicio, periodoFinal);
  }
  
  public int saveIndicadorEstado(Long indicadorId, Long planId, Byte tipoEstado, Integer ano, Integer periodo, Double valorEstado, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[5];
    Object[] fieldValues = new Object[5];
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      IndicadorEstado indicadorEstado = new IndicadorEstado();
      IndicadorEstadoPK indicadorEstadoPk = new IndicadorEstadoPK();
      indicadorEstadoPk.setIndicadorId(indicadorId);
      indicadorEstadoPk.setPlanId(planId);
      indicadorEstadoPk.setTipo(tipoEstado);
      indicadorEstadoPk.setAno(ano);
      indicadorEstadoPk.setPeriodo(periodo);
      indicadorEstado.setPk(indicadorEstadoPk);
      indicadorEstado.setEstado(valorEstado);
      fieldNames[0] = "pk.indicadorId";
      fieldNames[1] = "pk.planId";
      fieldNames[2] = "pk.tipo";
      fieldNames[3] = "pk.ano";
      fieldNames[4] = "pk.periodo";
      fieldValues[0] = indicadorEstadoPk.getIndicadorId();
      fieldValues[1] = indicadorEstadoPk.getPlanId();
      fieldValues[2] = indicadorEstadoPk.getTipo();
      fieldValues[3] = indicadorEstadoPk.getAno();
      fieldValues[4] = indicadorEstadoPk.getPeriodo();
      
      if (persistenceSession.existsObject(indicadorEstado, fieldNames, fieldValues)) {
        resultado = persistenceSession.update(indicadorEstado, usuario);
      } else {
        persistenceSession.evictFromSession(indicadorEstado);
        resultado = persistenceSession.insert(indicadorEstado, usuario);
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
  
  public int saveAlertaIndicadorPlan(Long indicadorId, Long planId, Byte alerta, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      IndicadorPlanPK indicadorPlanPk = new IndicadorPlanPK();
      indicadorPlanPk.setPlanId(planId);
      indicadorPlanPk.setIndicadorId(indicadorId);
      IndicadorPlan indicadorPlan = (IndicadorPlan)load(IndicadorPlan.class, indicadorPlanPk);
      if (indicadorPlan != null) {
        indicadorPlan.setCrecimiento(alerta);
        resultado = persistenceSession.update(indicadorPlan, usuario);
      } else {
        indicadorPlan = new IndicadorPlan();
        indicadorPlan.setPk(indicadorPlanPk);
        indicadorPlan.setCrecimiento(alerta);
        resultado = persistenceSession.insert(indicadorPlan, usuario);
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
  
  public Byte getTipoCargaMedicionMeta(Long indicadorId, Long planId) {
    Byte tipoCargaMedicionMeta = null;
    
    IndicadorPlanPK indicadorPlanPk = new IndicadorPlanPK();
    indicadorPlanPk.setIndicadorId(indicadorId);
    indicadorPlanPk.setPlanId(planId);
    
    IndicadorPlan indicadorPlan = (IndicadorPlan)load(IndicadorPlan.class, indicadorPlanPk);
    
    if (indicadorPlan != null) {
      tipoCargaMedicionMeta = indicadorPlan.getTipoMedicion();
    }
    
    return tipoCargaMedicionMeta;
  }
  
  public List getIndicadorEstados(Long indicadorId, Long planId, Byte frecuencia, Byte tipoEstado, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta) {
    return persistenceSession.getIndicadorEstados(indicadorId, planId, frecuencia, tipoEstado, anoDesde, anoHasta, periodoDesde, periodoHasta);
  }
  
  public int deletePlanEstados(Long planId, Byte tipoEstado, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal) {
    return persistenceSession.deletePlanEstados(planId, tipoEstado, anoInicio, anoFinal, periodoInicio, periodoFinal);
  }
  
  public List getIndicadoresPlan(Long planId) {
    return persistenceSession.getIndicadoresPlan(planId);
  }
  
  public Byte getFrecuenciaPlan(Long planId) {
    Byte frecuencia = persistenceSession.getFrecuenciaPlan(planId);
    if (frecuencia == null)
    {
      frecuencia = Frecuencia.getFrecuenciaTrimestral();
    }
    return frecuencia;
  }
  
  public int savePlanEstado(Long planId, Byte tipoEstado, Integer ano, Integer periodo, Double valorEstado, Usuario usuario)
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
      
      PlanEstado planEstado = new PlanEstado();
      PlanEstadoPK planEstadoPk = new PlanEstadoPK();
      planEstadoPk.setPlanId(planId);
      planEstadoPk.setTipo(tipoEstado);
      planEstadoPk.setAno(ano);
      planEstadoPk.setPeriodo(periodo);
      planEstado.setPk(planEstadoPk);
      planEstado.setEstado(valorEstado);
      fieldNames[0] = "pk.planId";
      fieldNames[1] = "pk.tipo";
      fieldNames[2] = "pk.ano";
      fieldNames[3] = "pk.periodo";
      fieldValues[0] = planEstadoPk.getPlanId();
      fieldValues[1] = planEstadoPk.getTipo();
      fieldValues[2] = planEstadoPk.getAno();
      fieldValues[3] = planEstadoPk.getPeriodo();
      
      if (persistenceSession.existsObject(planEstado, fieldNames, fieldValues)) {
        resultado = persistenceSession.update(planEstado, usuario);
      } else {
        resultado = persistenceSession.insert(planEstado, usuario);
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
  
  public int actualizarPlanAlerta(Plan plan)
  {
    return persistenceSession.actualizarPlanAlerta(plan);
  }
  
  public int actualizarPlanUltimoEstado(Long planId)
  {
    return persistenceSession.actualizarPlanUltimoEstado(planId);
  }
  
  public Byte getAlertaIndicadorPorPlan(Long indicadorId, Long planId)
  {
    Byte alerta = null;
    IndicadorPlanPK indicadorPlanPk = new IndicadorPlanPK();
    indicadorPlanPk.setIndicadorId(indicadorId);
    indicadorPlanPk.setPlanId(planId);
    IndicadorPlan indicadorPlan = (IndicadorPlan)load(IndicadorPlan.class, indicadorPlanPk);
    
    if (indicadorPlan != null) {
      alerta = indicadorPlan.getCrecimiento();
    }
    return alerta;
  }
  
  public IndicadorPlan getFirstAlertaIndicadorPorPlan(Long indicadorId)
  {
    return persistenceSession.getFirstAlertaIndicadorPorPlan(indicadorId);
  }
  
  public boolean tienePermisoPlan(Usuario usuario, Long planId, String permisoId)
  {
    if (usuario.getIsAdmin().booleanValue()) {
      return true;
    }
    return persistenceSession.tienePermisoPlan(usuario.getUsuarioId(), planId, permisoId);
  }
  
  public void readDatosIndicadorPlan(Indicador indicador, Byte mesCierre, Integer ano, Plan plan) {
    StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
    StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService(this);
    
    Integer anoActual = new Integer(Calendar.getInstance().get(1));
    
    Integer anoMedicion = ano;
    if (anoMedicion == null) {
      if ((anoActual.intValue() >= plan.getAnoInicial().intValue()) && (anoActual.intValue() <= plan.getAnoFinal().intValue())) {
        anoMedicion = anoActual;
      } else if (anoActual.intValue() < plan.getAnoInicial().intValue()) {
        anoMedicion = plan.getAnoInicial();
      } else if (anoActual.intValue() > plan.getAnoFinal().intValue()) {
        anoMedicion = plan.getAnoFinal();
      }
    }
    List mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), mesCierre, SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), null, anoMedicion, null, null);
    
    Medicion ultimaMedicion = null;
    if (mediciones.size() > 0) {
      ultimaMedicion = (Medicion)mediciones.get(mediciones.size() - 1);
      if (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteTransversal().byteValue()) {
        if ((ano != null) && (ultimaMedicion.getMedicionId().getAno().intValue() < ano.intValue())) {
          ultimaMedicion = null;
        }
      } else if ((indicador.getValorInicialCero().booleanValue()) && 
        (ano != null) && (ultimaMedicion.getMedicionId().getAno().intValue() < ano.intValue())) {
        ultimaMedicion = null;
      }
      
      if (ultimaMedicion != null) {
        String fechaUltimaMedicion = ultimaMedicion.getMedicionId().getPeriodo().toString() + "/" + ultimaMedicion.getMedicionId().getAno().toString();
        indicador.setFechaUltimaMedicion(fechaUltimaMedicion);
        indicador.setUltimaMedicion(ultimaMedicion.getValor());
      } else {
        indicador.setFechaUltimaMedicion(null);
        indicador.setUltimaMedicion(null);
      }
    } else {
      indicador.setFechaUltimaMedicion(null);
      indicador.setUltimaMedicion(null);
    }
    
    if (ultimaMedicion != null)
    {
      List indicadoresEstados = getIndicadorEstados(indicador.getIndicadorId(), plan.getPlanId(), indicador.getFrecuencia(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getAno(), null, null);
      if (indicadoresEstados.size() > 0) {
        IndicadorEstado indicadorEstado = (IndicadorEstado)indicadoresEstados.get(indicadoresEstados.size() - 1);
        indicador.setEstadoAnual(indicadorEstado.getEstado());
      }
      indicadoresEstados = getIndicadorEstados(indicador.getIndicadorId(), plan.getPlanId(), indicador.getFrecuencia(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getPeriodo(), ultimaMedicion.getMedicionId().getPeriodo());
      if (indicadoresEstados.size() > 0) {
        IndicadorEstado indicadorEstado = (IndicadorEstado)indicadoresEstados.get(indicadoresEstados.size() - 1);
        indicador.setEstadoParcial(indicadorEstado.getEstado());
      }
    }
    

    Integer anoMetaAnual = ano;
    if ((anoMetaAnual == null) && 
      (ultimaMedicion != null)) {
      anoMetaAnual = ultimaMedicion.getMedicionId().getAno();
    }
    
    if (anoMetaAnual != null) {
      List metas = strategosMetasService.getMetasAnuales(indicador.getIndicadorId(), plan.getPlanId(), anoMetaAnual, anoMetaAnual, Boolean.valueOf(false));
      if (metas.size() > 0) {
        Meta meta = (Meta)metas.get(metas.size() - 1);
        indicador.setMetaAnual(meta.getValor());
      } else {
        indicador.setMetaAnual(null);
      }
    }
    

    Integer periodoHasta = null;
    Integer periodoDesdeMeta = null;
    Integer anoDesdeMeta = null;
    if (ultimaMedicion != null) {
      anoDesdeMeta = ultimaMedicion.getMedicionId().getAno();
      anoMedicion = ultimaMedicion.getMedicionId().getAno();
      periodoDesdeMeta = ultimaMedicion.getMedicionId().getPeriodo();
      periodoHasta = ultimaMedicion.getMedicionId().getPeriodo();
    }
    List metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(), plan.getPlanId(), indicador.getFrecuencia(), mesCierre, indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(), anoDesdeMeta, anoMedicion, periodoDesdeMeta, periodoHasta);
    Meta metaParcial = null;
    if (metas.size() > 0) {
      metaParcial = (Meta)metas.get(metas.size() - 1);
      if (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteTransversal().byteValue()) {
        if ((ano != null) && (metaParcial.getMetaId().getAno().intValue() < ano.intValue())) {
          metaParcial = null;
        } else if (ultimaMedicion != null) {
          if (metaParcial.getMetaId().getAno().intValue() != ultimaMedicion.getMedicionId().getAno().intValue()) {
            metaParcial = null;
          } else if (metaParcial.getMetaId().getPeriodo().intValue() < ultimaMedicion.getMedicionId().getPeriodo().intValue()) {
            metaParcial = null;
          }
        }
      } else if (indicador.getValorInicialCero().booleanValue()) {
        if ((ano != null) && (metaParcial.getMetaId().getAno().intValue() < ano.intValue())) {
          metaParcial = null;
        } else if ((ultimaMedicion != null) && (metaParcial.getMetaId().getPeriodo().intValue() < ultimaMedicion.getMedicionId().getPeriodo().intValue())) {
          metaParcial = null;
        }
      }
      if (metaParcial != null) {
        indicador.setMetaParcial(metaParcial.getValor());
      }
    } else {
      indicador.setMetaParcial(null);
    }
    
    strategosMedicionesService.close();
    strategosMetasService.close();
  }
  
  public boolean getIndicadorEstaAsociadoPlan(Long indicadorId, Long planId)
  {
    return persistenceSession.getIndicadorEstaAsociadoPlan(indicadorId, planId);
  }
  
  public ConfiguracionPlan getConfiguracionPlan()
  {
    ConfiguracionPlan configuracionPlan = new ConfiguracionPlan();
    try
    {
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
      Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Configuracion.Planes");
      frameworkService.close();
      
      if (configuracion != null)
      {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("properties");
        Element eElement = (Element)nList.item(0);
        
        configuracionPlan.setPlanObjetivoAlertaAnualMostrar(Boolean.valueOf(VgcAbstractService.getTagValue("alertaAnual", eElement).equals("1")));
        configuracionPlan.setPlanObjetivoAlertaParcialMostrar(Boolean.valueOf(VgcAbstractService.getTagValue("alertaParcial", eElement).equals("1")));
        configuracionPlan.setPlanObjetivoLogroAnualMostrar(Boolean.valueOf(VgcAbstractService.getTagValue("logroAnual", eElement).equals("1")));
        configuracionPlan.setPlanObjetivoLogroParcialMostrar(Boolean.valueOf(VgcAbstractService.getTagValue("logroParcial", eElement).equals("1")));
      }
      else
      {
        configuracionPlan.setPlanObjetivoAlertaAnualMostrar(Boolean.valueOf(true));
        configuracionPlan.setPlanObjetivoAlertaParcialMostrar(Boolean.valueOf(true));
        configuracionPlan.setPlanObjetivoLogroAnualMostrar(Boolean.valueOf(true));
        configuracionPlan.setPlanObjetivoLogroParcialMostrar(Boolean.valueOf(true));
      }
    }
    catch (Exception localException) {}
    


    return configuracionPlan;
  }
}
