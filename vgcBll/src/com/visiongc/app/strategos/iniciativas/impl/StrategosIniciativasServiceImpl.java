package com.visiongc.app.strategos.iniciativas.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
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
import com.visiongc.app.strategos.iniciativas.model.IndicadorIniciativa;
import com.visiongc.app.strategos.iniciativas.model.IndicadorIniciativaPK;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.MemoIniciativa;
import com.visiongc.app.strategos.iniciativas.model.ResultadoEspecificoIniciativa;
import com.visiongc.app.strategos.iniciativas.model.ResultadoEspecificoIniciativaPK;
import com.visiongc.app.strategos.iniciativas.model.util.ConfiguracionIniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.CorreoIniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosIniciativasPersistenceSession;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectivaPK;
import com.visiongc.app.strategos.planes.model.IniciativaPlan;
import com.visiongc.app.strategos.planes.model.IniciativaPlanPK;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPrdProductosService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Modulo.ModuloType.Iniciativas;
import com.visiongc.framework.model.Usuario;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StrategosIniciativasServiceImpl
  extends StrategosServiceImpl implements StrategosIniciativasService
{
  private StrategosIniciativasPersistenceSession persistenceSession = null;
  
  public StrategosIniciativasServiceImpl(StrategosIniciativasPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getIniciativas(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    return persistenceSession.getIniciativas(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public PaginaLista getIniciativasResponsable(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    return persistenceSession.getIniciativasResponsable(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public int saveIniciativa(Iniciativa iniciativa, Usuario usuario, Boolean actualizarIndicador)
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
      
      fieldNames[0] = "nombre";
      fieldValues[0] = iniciativa.getNombre();
      fieldNames[1] = "organizacionId";
      fieldValues[1] = iniciativa.getOrganizacionId();
      
      if ((iniciativa.getIniciativaId() == null) || (iniciativa.getIniciativaId().longValue() == 0L))
      {
        if (persistenceSession.existsObject(iniciativa, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          iniciativa.setIniciativaId(new Long(persistenceSession.getUniqueId()));
          iniciativa.setEstatusId(com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType.getEstatusSinIniciar());
          
          if (iniciativa.getResultadosEspecificosIniciativa() != null)
          {
            for (Iterator<ResultadoEspecificoIniciativa> i = iniciativa.getResultadosEspecificosIniciativa().iterator(); i.hasNext();)
            {
              ResultadoEspecificoIniciativa resultadoEspecificoIniciativa = (ResultadoEspecificoIniciativa)i.next();
              resultadoEspecificoIniciativa.getPk().setIniciativaId(iniciativa.getIniciativaId());
            }
          }
          
          if (iniciativa.getMemoIniciativa() != null) {
            iniciativa.getMemoIniciativa().setIniciativaId(iniciativa.getIniciativaId());
          }
          if (iniciativa.getIniciativaPlanes() != null)
          {
            for (Iterator<IniciativaPlan> iter = iniciativa.getIniciativaPlanes().iterator(); iter.hasNext();)
            {
              IniciativaPlan iniciativaPlan = (IniciativaPlan)iter.next();
              iniciativaPlan.getPk().setIniciativaId(iniciativa.getIniciativaId());
            }
          }
          
          if (iniciativa.getIniciativaPerspectivas() != null)
          {
            for (Iterator<IniciativaPerspectiva> iter = iniciativa.getIniciativaPerspectivas().iterator(); iter.hasNext();)
            {
              IniciativaPerspectiva iniciativaPerspectiva = (IniciativaPerspectiva)iter.next();
              iniciativaPerspectiva.getPk().setIniciativaId(iniciativa.getIniciativaId());
            }
          }
          
          if (iniciativa.getNaturaleza() == null) {
            iniciativa.setNaturaleza(new Byte((byte)0));
          }
          resultado = saveClaseIndicadores(iniciativa, usuario);
          if (resultado == 10000)
          {
            ConfiguracionIniciativa configuracionIniciativa = getConfiguracionIniciativa();
            resultado = saveIndicadorAutomatico(iniciativa, TipoFuncionIndicador.getTipoFuncionSeguimiento(), configuracionIniciativa, usuario);
            if ((resultado == 10000) && (configuracionIniciativa.getIniciativaIndicadorPresupuestoMostrar().booleanValue()))
              resultado = saveIndicadorAutomatico(iniciativa, TipoFuncionIndicador.getTipoFuncionPresupuesto(), configuracionIniciativa, usuario);
            if ((resultado == 10000) && (configuracionIniciativa.getIniciativaIndicadorEficaciaMostrar().booleanValue()))
              resultado = saveIndicadorAutomatico(iniciativa, TipoFuncionIndicador.getTipoFuncionEficacia(), configuracionIniciativa, usuario);
            if ((resultado == 10000) && (configuracionIniciativa.getIniciativaIndicadorEficienciaMostrar().booleanValue())) {
              resultado = saveIndicadorAutomatico(iniciativa, TipoFuncionIndicador.getTipoFuncionEficiencia(), configuracionIniciativa, usuario);
            }
          }
          if (resultado == 10000)
          {
            resultado = persistenceSession.insert(iniciativa, usuario);
            if (resultado == 10000) {
              resultado = asociarIndicador(iniciativa, usuario);
            }
          }
          if ((resultado == 10000) && (iniciativa.getIniciativaPerspectivas() != null) && (iniciativa.getIniciativaPerspectivas().size() > 0))
          {
            IniciativaPerspectiva iniciativaPerspectiva = (IniciativaPerspectiva)iniciativa.getIniciativaPerspectivas().toArray()[0];
            StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService(this);
            
            Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, iniciativaPerspectiva.getPk().getPerspectivaId());
            
            if (!strategosPerspectivasService.asociarIniciativa(perspectiva, iniciativa.getIniciativaId(), perspectiva.getPlanId(), iniciativa.getOrganizacionId(), usuario))
            {
              persistenceSession.rollbackTransaction();
              strategosPerspectivasService.close();
              return 10007;
            }
            
            strategosPerspectivasService.close();
          }
          else if ((resultado == 10000) && (iniciativa.getIniciativaPlanes() != null) && (iniciativa.getIniciativaPlanes().size() > 0))
          {
            StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(this);
            IniciativaPlan iniciativaPlan = (IniciativaPlan)iniciativa.getIniciativaPlanes().toArray()[0];
            resultado = strategosPlanesService.asociarIniciativa(iniciativaPlan.getPk().getPlanId(), iniciativa.getIniciativaId(), iniciativa.getOrganizacionId(), usuario);
            strategosPlanesService.close();
          }
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "iniciativaId";
        idFieldValues[0] = iniciativa.getIniciativaId();
        if (persistenceSession.existsObject(iniciativa, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        }
        else {
          if (actualizarIndicador.booleanValue())
          {
            if (iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()) != null)
            {
              Iniciativa iniciativaOriginal = getValoresOriginales(iniciativa.getIniciativaId());
              if (iniciativaOriginal.getFrecuencia().byteValue() != iniciativa.getFrecuencia().byteValue())
              {
                ConfiguracionIniciativa configuracionIniciativa = getConfiguracionIniciativa();
                resultado = updateIndicadorAutomatico(iniciativa, TipoFuncionIndicador.getTipoFuncionSeguimiento(), configuracionIniciativa, usuario);
                if ((resultado == 10000) && (configuracionIniciativa.getIniciativaIndicadorPresupuestoMostrar().booleanValue()))
                  resultado = updateIndicadorAutomatico(iniciativa, TipoFuncionIndicador.getTipoFuncionPresupuesto(), configuracionIniciativa, usuario);
                if ((resultado == 10000) && (configuracionIniciativa.getIniciativaIndicadorEficaciaMostrar().booleanValue()))
                  resultado = updateIndicadorAutomatico(iniciativa, TipoFuncionIndicador.getTipoFuncionEficacia(), configuracionIniciativa, usuario);
                if ((resultado == 10000) && (configuracionIniciativa.getIniciativaIndicadorEficienciaMostrar().booleanValue())) {
                  resultado = updateIndicadorAutomatico(iniciativa, TipoFuncionIndicador.getTipoFuncionEficiencia(), configuracionIniciativa, usuario);
                }
              }
              else {
                StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
                Indicador indicador = null;
                String nombre = "";
                ConfiguracionIniciativa configuracionIniciativa = null;
                

                if (resultado == 10000)
                {
                  Long indicadorId = iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento());
                  if (indicadorId != null)
                  {
                    indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorId);
                    if (iniciativa.getAlertaZonaVerde() != null)
                      indicador.setAlertaMetaZonaVerde(new Double(iniciativa.getAlertaZonaVerde().doubleValue()));
                    if (iniciativa.getAlertaZonaAmarilla() != null) {
                      indicador.setAlertaMetaZonaAmarilla(new Double(iniciativa.getAlertaZonaAmarilla().doubleValue()));
                    }
                    configuracionIniciativa = getConfiguracionIniciativa();
                    nombre = "";
                    if (configuracionIniciativa.getIniciativaIndicadorAvanceAnteponer().booleanValue())
                      nombre = configuracionIniciativa.getIniciativaIndicadorAvanceNombre() + " - ";
                    nombre = nombre + iniciativa.getNombre();
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
                

                if (resultado == 10000)
                {
                  Long indicadorId = iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto());
                  if (indicadorId != null)
                  {
                    indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorId);
                    nombre = "";
                    nombre = configuracionIniciativa.getIniciativaIndicadorPresupuestoNombre() + " - ";
                    nombre = nombre + iniciativa.getNombre();
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
                

                if (resultado == 10000)
                {
                  Long indicadorId = iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficacia());
                  if (indicadorId != null)
                  {
                    indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorId);
                    nombre = "";
                    nombre = configuracionIniciativa.getIniciativaIndicadorEficaciaNombre() + " - ";
                    nombre = nombre + iniciativa.getNombre();
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
                

                if (resultado == 10000)
                {
                  Long indicadorId = iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficiencia());
                  if (indicadorId != null)
                  {
                    indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorId);
                    nombre = "";
                    nombre = configuracionIniciativa.getIniciativaIndicadorEficienciaNombre() + " - ";
                    nombre = nombre + iniciativa.getNombre();
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
                
                strategosIndicadoresService.close();
              }
            }
          }
          if (resultado == 10000) {
            resultado = persistenceSession.update(iniciativa, usuario);
          }
        }
      }
      String valorEnteEjecutorVacio = "-";
      if ((iniciativa.getEnteEjecutor() == "") || (iniciativa.getEnteEjecutor() == null)) {
        iniciativa.setEnteEjecutor(valorEnteEjecutorVacio);
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
      
      if ((!transActiva) && (resultado == 10000))
      {
        List<Object> indicadores = new ArrayList();
        indicadores.add(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
        resultado = new Servicio().calcular(Servicio.EjecutarTipo.getEjecucionAlerta().byteValue(), indicadores, usuario);
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
  
  public int asociarIndicador(Iniciativa iniciativa, Usuario usuario)
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
      
      for (Iterator<IndicadorIniciativa> iter = iniciativa.getIniciativaIndicadores().iterator(); iter.hasNext();)
      {
        IndicadorIniciativa iniciativaIndicador = (IndicadorIniciativa)iter.next();
        
        fieldNames[0] = "pk.indicadorId";
        fieldValues[0] = iniciativaIndicador.getPk().getIndicadorId();
        fieldNames[1] = "pk.iniciativaId";
        fieldValues[1] = iniciativaIndicador.getPk().getIniciativaId();
        if (!persistenceSession.existsObject(iniciativaIndicador, fieldNames, fieldValues))
          resultado = persistenceSession.insert(iniciativaIndicador, usuario);
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
  
  public int desasociarIndicadores(IndicadorIniciativa iniciativaIndicador, Usuario usuario)
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
      
      if (iniciativaIndicador != null) {
        resultado = persistenceSession.delete(iniciativaIndicador, usuario);
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
  
  public ConfiguracionIniciativa getConfiguracionIniciativa()
  {
    ConfiguracionIniciativa configuracionIniciativa = new ConfiguracionIniciativa();
    try
    {
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
      Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Configuracion.Iniciativas");
      frameworkService.close();
      
      if (configuracion != null)
      {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("properties");
        Element eElement = (Element)nList.item(0);
        
        configuracionIniciativa.setIniciativaNombre(VgcAbstractService.getTagValue("nombre", eElement));
        
        nList = doc.getElementsByTagName("indicador");
        if (nList.getLength() > 0)
        {
          for (int i = 0; i < nList.getLength(); i++)
          {
            Node node = nList.item(i);
            Element elemento = (Element)node;
            
            Byte tipo = VgcAbstractService.getTagValue("tipo", elemento) != "" ? Byte.valueOf(Byte.parseByte(getTagValue("tipo", elemento))) : null;
            if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue())
            {
              configuracionIniciativa.setIniciativaIndicadorAvanceNombre(VgcAbstractService.getTagValue("nombre", elemento));
              configuracionIniciativa.setIniciativaIndicadorAvanceMostrar(Boolean.valueOf(VgcAbstractService.getTagValue("crear", elemento).equals("1")));
              configuracionIniciativa.setIniciativaIndicadorAvanceAnteponer(Boolean.valueOf(VgcAbstractService.getTagValue("anteponer", elemento).equals("1")));
            }
            else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue())
            {
              configuracionIniciativa.setIniciativaIndicadorPresupuestoNombre(VgcAbstractService.getTagValue("nombre", elemento));
              configuracionIniciativa.setIniciativaIndicadorPresupuestoMostrar(Boolean.valueOf(VgcAbstractService.getTagValue("crear", elemento).equals("1")));
            }
            else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficacia().byteValue())
            {
              configuracionIniciativa.setIniciativaIndicadorEficaciaNombre(VgcAbstractService.getTagValue("nombre", elemento));
              configuracionIniciativa.setIniciativaIndicadorEficaciaMostrar(Boolean.valueOf(VgcAbstractService.getTagValue("crear", elemento).equals("1")));
            }
            else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficiencia().byteValue())
            {
              configuracionIniciativa.setIniciativaIndicadorEficienciaNombre(VgcAbstractService.getTagValue("nombre", elemento));
              configuracionIniciativa.setIniciativaIndicadorEficienciaMostrar(Boolean.valueOf(VgcAbstractService.getTagValue("crear", elemento).equals("1")));
            }
          }
        }
      }
      else
      {
        VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
        
        configuracionIniciativa.setIniciativaNombre(messageResources.getResource("jsp.modulo.iniciativa.titulo.singular"));
        configuracionIniciativa.setIniciativaIndicadorAvanceNombre(messageResources.getResource("jsp.configuracion.sistema.iniciativas.indicador.avance.nombre"));
        configuracionIniciativa.setIniciativaIndicadorAvanceMostrar(Boolean.valueOf(true));
        configuracionIniciativa.setIniciativaIndicadorPresupuestoNombre(messageResources.getResource("jsp.configuracion.sistema.iniciativas.indicador.presupuesto.nombre"));
        configuracionIniciativa.setIniciativaIndicadorPresupuestoMostrar(Boolean.valueOf(true));
        configuracionIniciativa.setIniciativaIndicadorEficaciaNombre(messageResources.getResource("jsp.configuracion.sistema.iniciativas.indicador.eficacia.nombre"));
        configuracionIniciativa.setIniciativaIndicadorEficaciaMostrar(Boolean.valueOf(true));
        configuracionIniciativa.setIniciativaIndicadorEficienciaNombre(messageResources.getResource("jsp.configuracion.sistema.iniciativas.indicador.eficiencia.nombre"));
        configuracionIniciativa.setIniciativaIndicadorEficienciaMostrar(Boolean.valueOf(true));
        configuracionIniciativa.setIniciativaIndicadorAvanceAnteponer(Boolean.valueOf(true));
      }
    }
    catch (Exception localException) {}
    


    return configuracionIniciativa;
  }
  
  private int saveClaseIndicadores(Iniciativa iniciativa, Usuario usuario)
  {
    StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService(this);
    
    ClaseIndicadores clase = new ClaseIndicadores();
    ClaseIndicadores claseRoot = strategosClasesIndicadoresService.getClaseRaizIniciativa(iniciativa.getOrganizacionId(), TipoClaseIndicadores.getTipoClasePlanificacionSeguimiento(), messageResources.getResource("iniciativa.clase.nombre"), usuario);
    
    clase.setPadreId(claseRoot.getClaseId());
    clase.setNombre(iniciativa.getNombre());
    clase.setOrganizacionId(iniciativa.getOrganizacionId());
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
      iniciativa.setClaseId(clase.getClaseId());
    }
    strategosClasesIndicadoresService.close();
    
    return resultado;
  }
  
  private int saveIndicadorAutomatico(Iniciativa iniciativa, Byte tipo, ConfiguracionIniciativa configuracionIniciativa, Usuario usuario)
  {
    int resultado = 10000;
    
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
    Indicador indicador = new Indicador();
    indicador.setOrganizacionId(iniciativa.getOrganizacionId());
    indicador.setClaseId(iniciativa.getClaseId());
    String nombre = "";
    if ((tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue()) && (configuracionIniciativa.getIniciativaIndicadorAvanceAnteponer().booleanValue())) {
      nombre = configuracionIniciativa.getIniciativaIndicadorAvanceNombre() + " - ";
    } else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue()) {
      nombre = configuracionIniciativa.getIniciativaIndicadorPresupuestoNombre() + " - ";
    } else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficacia().byteValue()) {
      nombre = configuracionIniciativa.getIniciativaIndicadorEficaciaNombre() + " - ";
    } else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficiencia().byteValue())
      nombre = configuracionIniciativa.getIniciativaIndicadorEficienciaNombre() + " - ";
    nombre = nombre + iniciativa.getNombre();
    if (nombre.length() > 100)
      nombre = nombre.substring(0, 100);
    indicador.setNombre(nombre);
    if (nombre.length() > 50)
      nombre = nombre.substring(0, 50);
    indicador.setNombreCorto(nombre);
    indicador.setFrecuencia(iniciativa.getFrecuencia());
    if (tipo.byteValue() != TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue())
    {
      StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService(this);
      UnidadMedida porcentaje = strategosUnidadesService.getUnidadMedidaPorcentaje();
      indicador.setUnidadId(porcentaje.getUnidadId());
      strategosUnidadesService.close();
    }
    indicador.setPrioridad(PrioridadIndicador.getPrioridadIndicadorBaja());
    indicador.setMostrarEnArbol(new Boolean(true));
    if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue()) {
      indicador.setCaracteristica(Caracteristica.getCaracteristicaCondicionValorMaximo());
    } else
      indicador.setCaracteristica(Caracteristica.getCaracteristicaRetoAumento());
    indicador.setTipoFuncion(tipo);
    indicador.setGuia(new Boolean(false));
    indicador.setValorInicialCero(new Boolean(true));
    indicador.setResponsableCargarMetaId(iniciativa.getResponsableCargarMetaId());
    indicador.setResponsableCargarEjecutadoId(iniciativa.getResponsableCargarEjecutadoId());
    indicador.setResponsableFijarMetaId(iniciativa.getResponsableFijarMetaId());
    indicador.setResponsableLograrMetaId(iniciativa.getResponsableLograrMetaId());
    indicador.setResponsableSeguimientoId(iniciativa.getResponsableSeguimientoId());
    indicador.setNumeroDecimales(new Byte("2"));
    if (iniciativa.getAlertaZonaVerde() != null)
      indicador.setAlertaMetaZonaVerde(new Double(iniciativa.getAlertaZonaVerde().doubleValue()));
    if (iniciativa.getAlertaZonaAmarilla() != null)
      indicador.setAlertaMetaZonaAmarilla(new Double(iniciativa.getAlertaZonaAmarilla().doubleValue()));
    indicador.setSeriesIndicador(new HashSet());
    setSeriesTiempo(indicador);
    indicador.setNaturaleza(Naturaleza.getNaturalezaSimple());
    if ((tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficacia().byteValue()) || (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficiencia().byteValue()))
    {
      indicador.setCorte(TipoCorte.getTipoCorteTransversal());
      indicador.setTipoCargaMedicion(TipoMedicion.getTipoMedicionAlPeriodo());
      if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficacia().byteValue())
      {
        indicador.setNaturaleza(Naturaleza.getNaturalezaFormula());
        resultado = crearIndicadorFormulaEficacia(iniciativa, indicador);
      }
      else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficiencia().byteValue())
      {
        indicador.setNaturaleza(Naturaleza.getNaturalezaFormula());
        resultado = crearIndicadorFormulaEficiencia(iniciativa, indicador);
      }
    }
    else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue())
    {
      indicador.setCorte(TipoCorte.getTipoCorteTransversal());
      indicador.setTipoCargaMedicion(TipoMedicion.getTipoMedicionAlPeriodo());
    }
    else
    {
      indicador.setCorte(TipoCorte.getTipoCorteLongitudinal());
      indicador.setTipoCargaMedicion(TipoMedicion.getTipoMedicionEnPeriodo());
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
      iniciativa.setIndicadorId(indicador.getIndicadorId(), tipo);
    }
    strategosIndicadoresService.close();
    
    return resultado;
  }
  
  private int crearIndicadorFormulaEficacia(Iniciativa iniciativa, Indicador indicador)
  {
    int resultado = 10000;
    
    SerieIndicador serieReal = null;
    Set<SerieIndicador> seriesIndicador = indicador.getSeriesIndicador();
    for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext();)
    {
      SerieIndicador serie = (SerieIndicador)i.next();
      if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue())
      {
        serieReal = serie;
        break;
      }
    }
    
    Formula formulaIndicador = new Formula();
    formulaIndicador.setInsumos(new HashSet());
    
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
    Indicador indicadorInsumo = (Indicador)strategosIndicadoresService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
    strategosIndicadoresService.close();
    
    String formula = "";
    if (iniciativa.getTipoMedicion().byteValue() == TipoMedicion.getTipoMedicionAlPeriodo().byteValue())
    {
      formula = 
        "([" + iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).toString() + ".0]" + "/" + "[" + iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).toString() + ".1])*100";
    }
    else
    {
      formula = 
        "([" + iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).toString() + ".0]:S" + "/" + "[" + iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).toString() + ".1]:S)*100";
    }
    
    formulaIndicador.setExpresion(formula);
    
    InsumoFormula insumoFormula = new InsumoFormula();
    insumoFormula.setPk(new InsumoFormulaPK());
    insumoFormula.getPk().setPadreId(indicador.getIndicadorId());
    insumoFormula.getPk().setSerieId(new Long("0"));
    insumoFormula.getPk().setIndicadorId(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
    insumoFormula.getPk().setInsumoSerieId(new Long("0"));
    formulaIndicador.getInsumos().add(insumoFormula);
    
    insumoFormula = new InsumoFormula();
    insumoFormula.setPk(new InsumoFormulaPK());
    insumoFormula.getPk().setPadreId(indicador.getIndicadorId());
    insumoFormula.getPk().setSerieId(new Long("0"));
    insumoFormula.getPk().setIndicadorId(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
    insumoFormula.getPk().setInsumoSerieId(new Long("1"));
    formulaIndicador.getInsumos().add(insumoFormula);
    
    serieReal.getFormulas().add(formulaIndicador);
    
    return resultado;
  }
  
  private int crearIndicadorFormulaEficiencia(Iniciativa iniciativa, Indicador indicador)
  {
    int resultado = 10000;
    
    SerieIndicador serieReal = null;
    Set<SerieIndicador> seriesIndicador = indicador.getSeriesIndicador();
    for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext();)
    {
      SerieIndicador serie = (SerieIndicador)i.next();
      if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue())
      {
        serieReal = serie;
        break;
      }
    }
    
    Formula formulaIndicador = new Formula();
    formulaIndicador.setInsumos(new HashSet());
    
    String formula = "";
    if (iniciativa.getTipoMedicion().byteValue() == TipoMedicion.getTipoMedicionAlPeriodo().byteValue())
    {
      formula = 
      
        "([" + iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).toString() + "." + SerieTiempo.getSerieReal().getSerieId().byteValue() + "]" + "*" + "[" + iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()).toString() + "." + SerieTiempo.getSerieMaximo().getSerieId().byteValue() + "])" + "/" + "[" + iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()).toString() + "." + SerieTiempo.getSerieReal().getSerieId().byteValue() + "]";
    }
    else
    {
      formula = 
      
        "([" + iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).toString() + "." + SerieTiempo.getSerieReal().getSerieId().byteValue() + "]:S" + "*" + "[" + iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()).toString() + "." + SerieTiempo.getSerieMaximo().getSerieId().byteValue() + "])" + "/" + "[" + iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()).toString() + "." + SerieTiempo.getSerieReal().getSerieId().byteValue() + "]";
    }
    
    formulaIndicador.setExpresion(formula);
    
    InsumoFormula insumoFormula = new InsumoFormula();
    insumoFormula.setPk(new InsumoFormulaPK());
    insumoFormula.getPk().setPadreId(indicador.getIndicadorId());
    insumoFormula.getPk().setSerieId(SerieTiempo.getSerieReal().getSerieId());
    insumoFormula.getPk().setIndicadorId(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
    insumoFormula.getPk().setInsumoSerieId(SerieTiempo.getSerieReal().getSerieId());
    formulaIndicador.getInsumos().add(insumoFormula);
    
    insumoFormula = new InsumoFormula();
    insumoFormula.setPk(new InsumoFormulaPK());
    insumoFormula.getPk().setPadreId(indicador.getIndicadorId());
    insumoFormula.getPk().setSerieId(SerieTiempo.getSerieReal().getSerieId());
    insumoFormula.getPk().setIndicadorId(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()));
    insumoFormula.getPk().setInsumoSerieId(SerieTiempo.getSerieMaximo().getSerieId());
    formulaIndicador.getInsumos().add(insumoFormula);
    
    insumoFormula = new InsumoFormula();
    insumoFormula.setPk(new InsumoFormulaPK());
    insumoFormula.getPk().setPadreId(indicador.getIndicadorId());
    insumoFormula.getPk().setSerieId(SerieTiempo.getSerieReal().getSerieId());
    insumoFormula.getPk().setIndicadorId(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()));
    insumoFormula.getPk().setInsumoSerieId(SerieTiempo.getSerieReal().getSerieId());
    formulaIndicador.getInsumos().add(insumoFormula);
    
    serieReal.getFormulas().add(formulaIndicador);
    
    return resultado;
  }
  
  public int updateIndicadorAutomatico(Iniciativa iniciativa, Byte tipo, ConfiguracionIniciativa configuracionIniciativa, Usuario usuario)
  {
    int resultado = 10000;
    
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    Long indicadorId = iniciativa.getIndicadorId(tipo);
    if (indicadorId != null)
    {
      Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorId);
      indicador.setFrecuencia(iniciativa.getFrecuencia());
      
      if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue())
      {
        if (iniciativa.getAlertaZonaVerde() != null)
          indicador.setAlertaMetaZonaVerde(new Double(iniciativa.getAlertaZonaVerde().doubleValue()));
        if (iniciativa.getAlertaZonaAmarilla() != null) {
          indicador.setAlertaMetaZonaAmarilla(new Double(iniciativa.getAlertaZonaAmarilla().doubleValue()));
        }
      }
      String nombre = "";
      if ((tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue()) && (configuracionIniciativa.getIniciativaIndicadorAvanceAnteponer().booleanValue())) {
        nombre = configuracionIniciativa.getIniciativaIndicadorAvanceNombre() + " - ";
      } else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue()) {
        nombre = configuracionIniciativa.getIniciativaIndicadorPresupuestoNombre() + " - ";
      } else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficacia().byteValue()) {
        nombre = configuracionIniciativa.getIniciativaIndicadorEficaciaNombre() + " - ";
      } else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficiencia().byteValue())
        nombre = configuracionIniciativa.getIniciativaIndicadorEficienciaNombre() + " - ";
      nombre = nombre + iniciativa.getNombre();
      if (nombre.length() > 100)
        nombre = nombre.substring(0, 100);
      indicador.setNombre(nombre);
      if (nombre.length() > 50)
        nombre = nombre.substring(0, 50);
      indicador.setNombreCorto(nombre);
      
      if ((tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficacia().byteValue()) || (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficiencia().byteValue()))
      {
        if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficacia().byteValue()) {
          resultado = crearIndicadorFormulaEficacia(iniciativa, indicador);
        } else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficiencia().byteValue()) {
          resultado = crearIndicadorFormulaEficiencia(iniciativa, indicador);
        }
      }
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
  
  public int deleteIniciativa(Iniciativa iniciativa, Usuario usuario)
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
      
      if (iniciativa.getIniciativaId() != null)
      {
        persistenceSession.clearReferenciaProyectoIniciativa(iniciativa.getIniciativaId());
        persistenceSession.clearReferenciaIniciativaProblema(iniciativa.getIniciativaId());
        persistenceSession.clearReferenciaIniciativaRelacionada(iniciativa.getIniciativaId());
        
        resultado = deleteDependenciasIniciativa(iniciativa, usuario);
        
        if (resultado == 10000)
        {
          for (Iterator<IndicadorIniciativa> iter = iniciativa.getIniciativaIndicadores().iterator(); iter.hasNext();)
          {
            IndicadorIniciativa iniciativaIndicadores = (IndicadorIniciativa)iter.next();
            resultado = desasociarIndicadores(iniciativaIndicadores, usuario);
            if (resultado != 10000) {
              break;
            }
          }
        }
        if (resultado == 10000) {
          resultado = persistenceSession.delete(iniciativa, usuario);
        }
        if (resultado == 10000) {
          resultado = deleteDependenciasCiclicasIniciativa(iniciativa, usuario);
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
  
  private int deleteDependenciasIniciativa(Iniciativa iniciativa, Usuario usuario)
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
      
      resultado = persistenceSession.deleteReferenciasRelacionalesIniciativa(iniciativa.getIniciativaId());
      
      if (resultado == 10000)
      {
        dependencias = persistenceSession.getDependenciasIniciativa(iniciativa);
        
        for (Iterator<?> i = dependencias.iterator(); i.hasNext();)
        {
          listaObjetosRelacionados = (List)i.next();
          
          if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof PryActividad)))
          {
            StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService(this);
            
            for (Iterator<PryActividad> j = listaObjetosRelacionados.iterator(); j.hasNext();)
            {
              PryActividad actividad = (PryActividad)j.next();
              
              resultado = strategosPryActividadesService.deleteActividad(actividad, usuario);
              if (resultado != 10000)
                break;
            }
            strategosPryActividadesService.close();
          }
          else if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof PryProyecto)))
          {
            StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance().openStrategosProyectosService(this);
            
            for (Iterator<PryProyecto> j = listaObjetosRelacionados.iterator(); j.hasNext();)
            {
              PryProyecto proyecto = (PryProyecto)j.next();
              
              resultado = strategosPryProyectosService.deleteProyecto(proyecto, usuario);
              if (resultado != 10000)
                break;
            }
            strategosPryProyectosService.close();
          }
          else if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof PrdProducto)))
          {
            StrategosPrdProductosService strategosPrdProductosService = StrategosServiceFactory.getInstance().openStrategosPrdProductosService(this);
            
            for (Iterator<PrdProducto> j = listaObjetosRelacionados.iterator(); j.hasNext();)
            {
              PrdProducto prdProducto = (PrdProducto)j.next();
              
              resultado = strategosPrdProductosService.deleteProducto(prdProducto, usuario);
              if (resultado != 10000)
                break;
            }
            strategosPrdProductosService.close();
          }
          else if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof ClaseIndicadores)))
          {
            StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService(this);
            
            for (Iterator<ClaseIndicadores> j = listaObjetosRelacionados.iterator(); j.hasNext();)
            {
              ClaseIndicadores clase = (ClaseIndicadores)j.next();
              
              resultado = strategosClasesIndicadoresService.deleteClaseIndicadores(clase, Boolean.valueOf(true), usuario);
              if (resultado != 10000)
                break;
            }
            strategosClasesIndicadoresService.close();
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
  
  private int deleteDependenciasCiclicasIniciativa(Iniciativa iniciativa, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    ListaMap dependencias = new ListaMap();
    List listaObjetosRelacionados = new ArrayList();
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      dependencias = persistenceSession.getDependenciasCiclicasIniciativa(iniciativa);
      
      for (Iterator i = dependencias.iterator(); i.hasNext();)
      {
        listaObjetosRelacionados = (List)i.next();
        
        if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof ClaseIndicadores)))
        {
          StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService(this);
          
          for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();) {
            ClaseIndicadores clase = (ClaseIndicadores)j.next();
            
            resultado = strategosClasesIndicadoresService.deleteClaseIndicadores(clase, Boolean.valueOf(true), usuario);
            
            if (resultado != 10000) {
              break;
            }
          }
          
          strategosClasesIndicadoresService.close();
        }
        else
        {
          for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();) {
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
  
  public Iniciativa getIniciativaBasico(Long iniciativaId) {
    return persistenceSession.getIniciativaBasica(iniciativaId);
  }
  
  public Iniciativa getIniciativaAndLockForUse(Long iniciativaId, String instancia) {
    Iniciativa iniciativa = null;
    Object[] idsToUse = new Object[1];
    idsToUse[0] = iniciativaId;
    if (persistenceSession.lockForUse(instancia, idsToUse)) {
      iniciativa = getIniciativaBasico(iniciativaId);
      if (iniciativa == null) {
        persistenceSession.unlockObject(instancia, iniciativaId);
      }
    }
    return iniciativa;
  }
  
  public List getRutaCompletaIniciativa(Long iniciativaId) {
    Iniciativa iniciativa = (Iniciativa)load(Iniciativa.class, iniciativaId);
    ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService(this);
    List rutaOrganizaciones = arbolesService.getRutaCompletaNombres(iniciativa.getOrganizacion());
    return rutaOrganizaciones;
  }
  
  public String getRutaCompletaIniciativa(Long iniciativaId, String separador) {
    Iniciativa iniciativa = (Iniciativa)load(Iniciativa.class, iniciativaId);
    return getRutaCompletaIniciativa(iniciativa, separador);
  }
  
  public String getRutaCompletaIniciativa(Iniciativa iniciativa, String separador) {
    StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService(this);
    String resultado = strategosOrganizacionesService.getRutaCompletaNombresOrganizacion(iniciativa.getOrganizacionId(), separador);
    strategosOrganizacionesService.close();
    return resultado;
  }
  
  public List getIniciativasPorPlan(Long planId, Byte tipoCalculo, String orden, String tipoOrden) {
    Map filtros = new HashMap();
    filtros.put("planId", planId.toString());
    filtros.put("tipoAlerta", tipoCalculo.toString());
    return persistenceSession.getIniciativas(0, 0, orden, tipoOrden, false, filtros).getLista();
  }
  
  public boolean verificarOrganizacionIniciativaPorNombre(String nombre, Long organizacionId) {
    return persistenceSession.verificarOrganizacionIniciativaPorNombre(nombre, organizacionId);
  }
  
  public Iniciativa getIniciativaVinculacion(Long iniciativaId, Long planId) {
    return (Iniciativa)load(Iniciativa.class, iniciativaId);
  }
  
  public boolean actualizarIniciativaProyecto(Long iniciativaId, Long proyectoId) {
    return persistenceSession.actualizarIniciativaProyecto(iniciativaId, proyectoId);
  }
  
  public Byte getAlertaIniciativa(Long iniciativaId, Byte tipoCalculo, Long planId)
  {
    Byte alerta = null;
    










    return alerta;
  }
  
  public Iniciativa getIniciativaByIndicador(long indicadorId)
  {
    return persistenceSession.getIniciativaByIndicador(indicadorId);
  }
  
  public int updateCampo(Long iniciativaId, Map<?, ?> filtros) throws Throwable
  {
    return persistenceSession.updateCampo(iniciativaId, filtros);
  }
  
  public Iniciativa getIniciativaByProyecto(long proyectoId)
  {
    return persistenceSession.getIniciativaByProyecto(proyectoId);
  }
  
  public Iniciativa getValoresOriginales(Long iniciativaId)
  {
    return persistenceSession.getValoresOriginales(iniciativaId);
  }
  
  public List<Iniciativa> getIniciativasAsociadas(Long iniciativaId)
  {
    return persistenceSession.getIniciativasAsociadas(iniciativaId);
  }
  
  public Map<Long, Byte> getAlertasIniciativas(Map<String, String> iniciativaIds)
  {
    return persistenceSession.getAlertasIniciativas(iniciativaIds);
  }
  
  public int marcarHistorico(String iniciativas)
  {
    return persistenceSession.marcarHistorico(iniciativas);
  }
  
  public int desMarcarHistorico(String iniciativas)
  {
    return persistenceSession.desMarcarHistorico(iniciativas);
  }


  public List<Iniciativa> getIniciativasParaEjecutar(Long iniciativaId, Long organizacionId, Long planId, Integer ano) 
  {
	return persistenceSession.getIniciativasEjecutar(iniciativaId, organizacionId, planId, ano);
  }

	
  public CorreoIniciativa getCorreoIniciativa() {
		
	  CorreoIniciativa correoIniciativa = new CorreoIniciativa();
	    try
	    {
	      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
	      Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Configuracion.Correo.Iniciativa");
	      frameworkService.close();
	      
	      if (configuracion != null)
	      {

	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
	        doc.getDocumentElement().normalize();
	        NodeList nList = doc.getElementsByTagName("properties");
	        Element eElement = (Element)nList.item(0);
	        
	        
	        correoIniciativa.setEnviarResponsableCargarEjecutado(Boolean.valueOf(VgcAbstractService.getTagValue("cargarEjecutado", eElement).equals("1")));
	        correoIniciativa.setEnviarResponsableCargarMeta(Boolean.valueOf(VgcAbstractService.getTagValue("cargarMeta", eElement).equals("1")));
	        correoIniciativa.setEnviarResponsableFijarMeta(Boolean.valueOf(VgcAbstractService.getTagValue("fijarMeta", eElement).equals("1")));
	        correoIniciativa.setEnviarResponsableLograrMeta(Boolean.valueOf(VgcAbstractService.getTagValue("lograrMeta", eElement).equals("1")));
	        correoIniciativa.setEnviarResponsableSeguimiento(Boolean.valueOf(VgcAbstractService.getTagValue("seguimiento", eElement).equals("1")));
	        correoIniciativa.setTexto(VgcAbstractService.getTagValue("texto", eElement));
	        correoIniciativa.setTitulo(VgcAbstractService.getTagValue("titulo", eElement));
	        correoIniciativa.setHora(VgcAbstractService.getTagValue("hora", eElement));
	        correoIniciativa.setCorreo(VgcAbstractService.getTagValue("correo", eElement));
	        correoIniciativa.setDia(VgcAbstractService.getTagValue("dia", eElement));
	        
	      }
	      else
	      {
	        VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
	        
	        correoIniciativa.setEnviarResponsableCargarEjecutado(Boolean.valueOf(true));
	        correoIniciativa.setEnviarResponsableCargarMeta(Boolean.valueOf(true));
	        correoIniciativa.setEnviarResponsableFijarMeta(Boolean.valueOf(true));
	        correoIniciativa.setEnviarResponsableLograrMeta(Boolean.valueOf(true));
	        correoIniciativa.setEnviarResponsableSeguimiento(Boolean.valueOf(true));
	        correoIniciativa.setTexto("");
	        correoIniciativa.setTitulo("");
	        correoIniciativa.setCorreo("");
	        correoIniciativa.setHora("");
	        correoIniciativa.setDia("");
	        
	        
	      }
	    }
	    catch (Exception localException) {}
	    


	    return correoIniciativa;
	  
	  
  }
}
