package com.visiongc.app.strategos.graficos.impl;

import com.visiongc.app.strategos.graficos.StrategosGraficosService;
import com.visiongc.app.strategos.graficos.model.Duppont;
import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.app.strategos.graficos.persistence.StrategosGraficosPersistenceSession;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.Map;







public class StrategosGraficosServiceImpl
  extends StrategosServiceImpl
  implements StrategosGraficosService
{
  private StrategosGraficosPersistenceSession persistenceSession = null;
  
  public StrategosGraficosServiceImpl(StrategosGraficosPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getGraficos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, String> filtros)
  {
    return persistenceSession.getGraficos(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public int deleteGrafico(Grafico grafico, Usuario usuario)
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
      
      resultado = persistenceSession.delete(grafico, usuario);
      
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
  
  public int saveGrafico(Grafico grafico, Usuario usuario)
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
      fieldValues[0] = grafico.getNombre();
      fieldNames[1] = "organizacionId";
      fieldValues[1] = grafico.getOrganizacionId();
      fieldNames[2] = "usuarioId";
      fieldValues[2] = grafico.getUsuarioId();
      fieldNames[3] = "objetoId";
      fieldValues[3] = grafico.getObjetoId();
      
      if ((grafico.getGraficoId() == null) || (grafico.getGraficoId().longValue() == 0L))
      {
        if (persistenceSession.existsObject(grafico, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          grafico.setGraficoId(new Long(persistenceSession.getUniqueId()));
          resultado = persistenceSession.insert(grafico, usuario);
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "graficoId";
        idFieldValues[0] = grafico.getGraficoId();
        
        if (persistenceSession.existsObject(grafico, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          resultado = persistenceSession.update(grafico, usuario);
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
  
  public int saveDuppont(Duppont duppont, Usuario usuario)
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
      
      resultado = persistenceSession.insert(duppont, usuario);
      
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
  
  public int deleteDuppont(Duppont duppont, Usuario usuario)
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
      
      resultado = persistenceSession.delete(duppont, usuario);
      
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
