package com.visiongc.app.strategos.planificacionseguimiento.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendario;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendarioDetalle;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPryProyectosPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrategosPryProyectosServiceImpl
  extends StrategosServiceImpl implements StrategosPryProyectosService
{
  private StrategosPryProyectosPersistenceSession persistenceSession = null;
  
  public StrategosPryProyectosServiceImpl(StrategosPryProyectosPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public int deleteProyecto(PryProyecto pryProyecto, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (pryProyecto.getProyectoId() != null) {
        resultado = deleteDependenciasPryProyectos(pryProyecto, usuario);
        
        if (resultado == 10000)
        {
          resultado = persistenceSession.delete(pryProyecto, usuario);
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
  
  public int saveProyecto(PryProyecto proyecto, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if ((proyecto.getProyectoId() == null) || (proyecto.getProyectoId().longValue() == 0L)) {
        proyecto.setProyectoId(new Long(persistenceSession.getUniqueId()));
        resultado = persistenceSession.insert(proyecto, usuario);
      } else {
        resultado = persistenceSession.update(proyecto, usuario);
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
  
  private PryCalendario getCalendarioPorDefecto(Usuario usuario)
  {
    boolean transActiva = false;
    PryCalendario calendario = null;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      calendario = persistenceSession.getCalendarioPorDefecto();
      if (calendario == null) {
        calendario = new PryCalendario();
        calendario.setCalendarioId(new Long(persistenceSession.getUniqueId()));
        calendario.setNombre(messageResources.getResource("calendario.estandard"));
        calendario.setDomingo(new Boolean(true));
        calendario.setLunes(new Boolean(false));
        calendario.setMartes(new Boolean(false));
        calendario.setMiercoles(new Boolean(false));
        calendario.setJueves(new Boolean(false));
        calendario.setViernes(new Boolean(false));
        calendario.setSabado(new Boolean(true));
        resultado = persistenceSession.insert(calendario, usuario);
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
    
    return calendario;
  }
  
  private PryCalendario getCopiaCalendario(PryCalendario calendarioOriginal, Long calendarioNuevoId)
  {
    PryCalendario calendarioCopia = new PryCalendario();
    
    calendarioCopia.setCalendarioId(calendarioNuevoId);
    calendarioCopia.setNombre(calendarioOriginal.getNombre());
    calendarioCopia.setLunes(calendarioOriginal.getLunes());
    calendarioCopia.setMartes(calendarioOriginal.getMartes());
    calendarioCopia.setMiercoles(calendarioOriginal.getMiercoles());
    calendarioCopia.setJueves(calendarioOriginal.getJueves());
    calendarioCopia.setViernes(calendarioOriginal.getViernes());
    calendarioCopia.setSabado(calendarioOriginal.getSabado());
    calendarioCopia.setDomingo(calendarioOriginal.getDomingo());
    
    calendarioCopia.setExcepciones(new HashSet());
    if (calendarioOriginal.getExcepciones() != null) {
      for (Iterator iter = calendarioOriginal.getExcepciones().iterator(); iter.hasNext();) {
        PryCalendarioDetalle excepcion = (PryCalendarioDetalle)iter.next();
        
        PryCalendarioDetalle excepcionCopia = new PryCalendarioDetalle();
        excepcionCopia.setCalendarioId(calendarioNuevoId);
        excepcionCopia.setFecha(excepcion.getFecha());
        excepcionCopia.setFeriado(excepcion.getFeriado());
        calendarioCopia.getExcepciones().add(excepcionCopia);
      }
    }
    
    return calendarioCopia;
  }
  
  private Long crearProyecto(String nombre, Usuario usuario)
  {
    boolean transActiva = false;
    Long proyectoId = null;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      PryProyecto proyecto = new PryProyecto();
      
      proyecto.setNombre(nombre);
      proyecto.setComienzo(new Date());
      proyecto.setComienzoPlan(new Date());
      proyecto.setFinPlan(new Date());
      proyecto.setDuracionPlan(new Double(1.0D));
      resultado = saveProyecto(proyecto, usuario);
      
      if (resultado == 10000) {
        proyectoId = proyecto.getProyectoId();
        
        PryCalendario calendarioBase = getCalendarioPorDefecto(usuario);
        
        PryCalendario calendarioProyecto = getCopiaCalendario(calendarioBase, new Long(persistenceSession.getUniqueId()));
        
        calendarioProyecto.setProyectoId(proyectoId);
        
        resultado = persistenceSession.insert(calendarioProyecto, usuario);
        
        if (resultado != 10000) {
          proyectoId = null;
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
    
    return proyectoId;
  }
  
  public Long crearProyectoIniciativa(String nombre, Long iniciativaId, Usuario usuario)
  {
    boolean transActiva = false;
    Long proyectoId = null;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      proyectoId = crearProyecto(nombre, usuario);
      
      if (proyectoId != null)
      {
        StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(this);
        
        strategosIniciativasService.actualizarIniciativaProyecto(iniciativaId, proyectoId);
        
        strategosIniciativasService.close();
      }
      
      if (transActiva) {
        if (proyectoId != null) {
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
    
    return proyectoId;
  }
  
  public Iniciativa verificarProyectoIniciativa(Long iniciativaId, Usuario usuario)
  {
    boolean transActiva = false;
    Iniciativa iniciativa = null;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (iniciativaId != null)
      {
        iniciativa = (Iniciativa)load(Iniciativa.class, iniciativaId);
        
        if (iniciativa != null)
        {
          if ((iniciativa.getProyectoId() == null) || (iniciativa.getProyectoId().longValue() == 0L))
          {
            iniciativa.setProyectoId(crearProyectoIniciativa(iniciativa.getNombre(), iniciativa.getIniciativaId(), usuario));
          }
        }
      }
      

      if (transActiva) {
        if (iniciativa.getProyectoId() != null) {
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
    
    return iniciativa;
  }
  
  private int deleteDependenciasPryProyectos(PryProyecto proyecto, Usuario usuario)
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
      
      dependencias = persistenceSession.getDependenciasPryProyectos(proyecto);
      
      for (Iterator i = dependencias.iterator(); i.hasNext();)
      {
        listaObjetosRelacionados = (List)i.next();
        
        if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof PryActividad)))
        {
          StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService(this);
          
          for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();) {
            PryActividad actividad = (PryActividad)j.next();
            
            resultado = strategosPryActividadesService.deleteActividad(actividad, usuario);
            
            if (resultado != 10000) {
              break;
            }
          }
          
          strategosPryActividadesService.close();
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
  
  public PryCalendario getCalendarioProyecto(Long proyectoId, Usuario usuario)
  {
    PryCalendario calendario = persistenceSession.getCalendarioProyecto(proyectoId);
    int resultado = 10000;
    boolean transActiva = false;
    try
    {
      if (calendario == null)
      {
        if (!persistenceSession.isTransactionActive()) {
          persistenceSession.beginTransaction();
          transActiva = true;
        }
        
        PryCalendario calendarioBase = getCalendarioPorDefecto(usuario);
        
        PryCalendario calendarioProyecto = getCopiaCalendario(calendarioBase, new Long(persistenceSession.getUniqueId()));
        
        calendarioProyecto.setProyectoId(proyectoId);
        
        resultado = persistenceSession.insert(calendarioProyecto, usuario);
        
        if (resultado == 10000) {
          calendario = calendarioProyecto;
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
      

      if (calendario != null) {
        calendario.setMapExcepciones(new HashMap());
        if (calendario.getExcepciones() != null) {
          for (Iterator iter = calendario.getExcepciones().iterator(); iter.hasNext();) {
            PryCalendarioDetalle excepcion = (PryCalendarioDetalle)iter.next();
            
            calendario.getMapExcepciones().put(VgcFormatter.formatearFecha(excepcion.getFecha(), "formato.fecha.corta"), excepcion);
          }
          
        }
        
      }
      
    }
    catch (Throwable t)
    {
      if (transActiva) {
        persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    
    return calendario;
  }
}
