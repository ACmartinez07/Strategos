package com.visiongc.app.strategos.estadosacciones.impl;

import com.visiongc.app.strategos.estadosacciones.StrategosEstadosService;
import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.estadosacciones.persistence.StrategosEstadosPersistenceSession;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StrategosEstadosServiceImpl
  extends StrategosServiceImpl implements StrategosEstadosService
{
  private StrategosEstadosPersistenceSession persistenceSession = null;
  
  public StrategosEstadosServiceImpl(StrategosEstadosPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getEstadosAcciones(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    return persistenceSession.getEstadosAcciones(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public Boolean estadoAccionesEstaEnUso(Long estadoId)
  {
    return persistenceSession.estadoAccionesEstaEnUso(estadoId);
  }
  
  public EstadoAcciones estadoAccionesIndicaFinalizacion()
  {
    return persistenceSession.estadoAccionesIndicaFinalizacion();
  }
  
  public int deleteEstadoAcciones(EstadoAcciones estadoAcciones, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (estadoAcciones.getEstadoId() != null)
      {
        resultado = persistenceSession.delete(estadoAcciones, usuario);
        if (resultado == 10000) {
          resultado = actualizarOrdenEstadosAcciones(usuario);
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
  
  public int saveEstadoAcciones(EstadoAcciones estadoAcciones, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[1];
    Object[] fieldValues = new Object[1];
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      fieldNames[0] = "nombre";
      fieldValues[0] = estadoAcciones.getNombre();
      
      if ((estadoAcciones.getEstadoId() == null) || (estadoAcciones.getEstadoId().longValue() == 0L)) {
        if (persistenceSession.existsObject(estadoAcciones, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          estadoAcciones.setEstadoId(new Long(persistenceSession.getUniqueId()));
          estadoAcciones.setOrden(new Integer(persistenceSession.getOrdenMaximoEstadosAcciones() + 1));
          resultado = persistenceSession.insert(estadoAcciones, usuario);
        }
      } else {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "estadoId";
        idFieldValues[0] = estadoAcciones.getEstadoId();
        if (persistenceSession.existsObject(estadoAcciones, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          resultado = persistenceSession.update(estadoAcciones, usuario);
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
  
  private int actualizarOrdenEstadosAcciones(Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String orden = "orden";
    String tipoOrden = "asc";
    boolean getTotal = true;
    
    Map filtros = new HashMap();
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      List estadosAcciones = persistenceSession.getEstadosAcciones(0, 0, orden, tipoOrden, getTotal, filtros).getLista();
      int ordenEstado = 1;
      for (Iterator i = estadosAcciones.iterator(); i.hasNext(); ordenEstado++) {
        EstadoAcciones estadoAcciones = (EstadoAcciones)i.next();
        estadoAcciones.setOrden(new Integer(ordenEstado));
        resultado = persistenceSession.update(estadoAcciones, usuario);
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
  
  public int cambiarOrdenEstadoAcciones(Long estadoId, boolean subir, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      EstadoAcciones estadoAcciones = (EstadoAcciones)persistenceSession.load(EstadoAcciones.class, estadoId);
      
      int ordenMaximo = persistenceSession.getOrdenMaximoEstadosAcciones();
      
      if (subir) {
        if (estadoAcciones.getOrden().intValue() > 1)
        {
          EstadoAcciones estadoAccionesOrden = persistenceSession.getEstadoAccionesPorOrden(estadoAcciones.getOrden().intValue() - 1);
          estadoAcciones.setOrden(new Integer(estadoAcciones.getOrden().intValue() - 1));
          estadoAccionesOrden.setOrden(new Integer(estadoAcciones.getOrden().intValue() + 1));
          resultado = persistenceSession.update(estadoAcciones, usuario);
          resultado = persistenceSession.update(estadoAccionesOrden, usuario);
        }
      } else if (estadoAcciones.getOrden().intValue() < ordenMaximo)
      {
        EstadoAcciones estadoAccionesOrden = persistenceSession.getEstadoAccionesPorOrden(estadoAcciones.getOrden().intValue() + 1);
        estadoAcciones.setOrden(new Integer(estadoAcciones.getOrden().intValue() + 1));
        estadoAccionesOrden.setOrden(new Integer(estadoAcciones.getOrden().intValue() - 1));
        resultado = persistenceSession.update(estadoAcciones, usuario);
        resultado = persistenceSession.update(estadoAccionesOrden, usuario);
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
}
