package com.visiongc.app.strategos.planificacionseguimiento.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPrdProductosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimiento;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoPK;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPrdProductosPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StrategosPrdProductosServiceImpl
  extends StrategosServiceImpl implements StrategosPrdProductosService
{
  private StrategosPrdProductosPersistenceSession persistenceSession = null;
  
  public StrategosPrdProductosServiceImpl(StrategosPrdProductosPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getProductos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    return persistenceSession.getProductos(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  private int deleteDependenciasProducto(PrdProducto prdProducto, Usuario usuario)
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
      
      dependencias = persistenceSession.getDependenciasPrdProducto(prdProducto);
      
      for (Iterator i = dependencias.iterator(); i.hasNext();)
      {
        listaObjetosRelacionados = (List)i.next();
        
        for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();) {
          Object objeto = j.next();
          
          resultado = persistenceSession.delete(objeto, usuario);
          
          if (resultado != 10000) {
            break;
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
  
  public int deleteProducto(PrdProducto producto, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (producto.getProductoId() != null)
      {
        resultado = deleteDependenciasProducto(producto, usuario);
        
        if (resultado == 10000)
        {
          resultado = persistenceSession.delete(producto, usuario);
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
  
  public int saveProducto(PrdProducto producto, Usuario usuario)
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
      fieldValues[0] = producto.getNombre();
      
      if ((producto.getProductoId() == null) || (producto.getProductoId().longValue() == 0L)) {
        if (persistenceSession.existsObject(producto, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else
        {
          producto.setProductoId(new Long(persistenceSession.getUniqueId()));
          resultado = persistenceSession.insert(producto, usuario);
        }
      } else {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "productoId";
        idFieldValues[0] = producto.getProductoId();
        
        if (persistenceSession.existsObject(producto, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          resultado = persistenceSession.update(producto, usuario);
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
  
  public List getProductosPorIniciativa(Long iniciativaId) {
    return persistenceSession.getProductosPorIniciativa(iniciativaId);
  }
  
  public List getSeguimientosPorIniciativa(Long iniciativaId, String[] orden, String[] tipoOrden) {
    return persistenceSession.getSeguimientosPorIniciativa(iniciativaId, orden, tipoOrden);
  }
  
  public int savePrdSeguimiento(PrdSeguimiento seguimiento, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[3];
    Object[] fieldValues = new Object[3];
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      fieldNames[0] = "pk.iniciativaId";
      fieldValues[0] = seguimiento.getPk().getIniciativaId();
      fieldNames[1] = "pk.ano";
      fieldValues[1] = seguimiento.getPk().getAno();
      fieldNames[2] = "pk.periodo";
      fieldValues[2] = seguimiento.getPk().getPeriodo();
      
      if (persistenceSession.existsObject(seguimiento, fieldNames, fieldValues)) {
        resultado = persistenceSession.update(seguimiento, usuario);
      } else {
        resultado = persistenceSession.insert(seguimiento, usuario);
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
  
  public int deletePrdSeguimiento(PrdSeguimiento seguimiento, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if ((seguimiento.getPk().getIniciativaId() != null) && (seguimiento.getPk().getAno() != null) && (seguimiento.getPk().getPeriodo() != null))
      {
        resultado = persistenceSession.delete(seguimiento, usuario);
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
