package com.visiongc.app.strategos.reportes.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.reportes.persistence.StrategosReportesPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.Map;









public class StrategosReportesServiceImpl
  extends StrategosServiceImpl
  implements StrategosReportesService
{
  private StrategosReportesPersistenceSession persistenceSession = null;
  
  public StrategosReportesServiceImpl(StrategosReportesPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getReportes(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, Object> filtros, Long usuarioId)
  {
    return persistenceSession.getReportes(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros, usuarioId);
  }
  
  public int deleteReporte(Reporte reporte, Usuario usuario)
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
      
      resultado = persistenceSession.delete(reporte, usuario);
      
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
  
  public int save(Reporte reporte, Usuario usuario)
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
      fieldNames[1] = "organizacionId";
      fieldValues[0] = reporte.getNombre();
      fieldValues[1] = reporte.getOrganizacionId();
      
      if ((reporte.getReporteId() == null) || (reporte.getReporteId().longValue() == 0L))
      {
        if (persistenceSession.existsObject(reporte, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          reporte.setReporteId(new Long(persistenceSession.getUniqueId()));
          
          resultado = persistenceSession.insert(reporte, usuario);
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "reporteId";
        idFieldValues[0] = reporte.getReporteId();
        
        if (persistenceSession.existsObject(reporte, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          resultado = persistenceSession.update(reporte, usuario);
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
}
