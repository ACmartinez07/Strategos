package com.visiongc.app.strategos.seriestiempo.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.seriestiempo.persistence.StrategosSeriesTiempoPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StrategosSeriesTiempoServiceImpl extends StrategosServiceImpl implements StrategosSeriesTiempoService
{
  private StrategosSeriesTiempoPersistenceSession persistenceSession = null;
  
  public StrategosSeriesTiempoServiceImpl(StrategosSeriesTiempoPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getSeriesTiempo(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<Object, Object> filtros, Usuario usuario)
  {
    Map<Object, Object> filtroLocal = new HashMap();
    PaginaLista paginaSeries = null;
    
    filtroLocal.put("identificador", SerieTiempo.getSerieReal().getIdentificador());
    paginaSeries = persistenceSession.getSeriesTiempo(0, 0, null, null, true, filtroLocal);
    if ((paginaSeries.getLista() == null) || (paginaSeries.getLista().size() < 1)) {
      crearSerieTiempo(SerieTiempo.getSerieReal().getNombre(), usuario);
    }
    filtroLocal.put("identificador", SerieTiempo.getSerieProgramado().getIdentificador());
    paginaSeries = persistenceSession.getSeriesTiempo(0, 0, null, null, true, filtroLocal);
    if ((paginaSeries.getLista() == null) || (paginaSeries.getLista().size() < 1)) {
      crearSerieTiempo(SerieTiempo.getSerieProgramado().getNombre(), usuario);
    }
    filtroLocal.put("identificador", SerieTiempo.getSerieMinimo().getIdentificador());
    paginaSeries = persistenceSession.getSeriesTiempo(0, 0, null, null, true, filtroLocal);
    if ((paginaSeries.getLista() == null) || (paginaSeries.getLista().size() < 1)) {
      crearSerieTiempo(SerieTiempo.getSerieMinimo().getNombre(), usuario);
    }
    filtroLocal.put("identificador", SerieTiempo.getSerieMaximo().getIdentificador());
    paginaSeries = persistenceSession.getSeriesTiempo(0, 0, null, null, true, filtroLocal);
    if ((paginaSeries.getLista() == null) || (paginaSeries.getLista().size() < 1)) {
      crearSerieTiempo(SerieTiempo.getSerieMaximo().getNombre(), usuario);
    }
    filtroLocal.put("identificador", SerieTiempo.getSerieValorAlerta().getIdentificador());
    paginaSeries = persistenceSession.getSeriesTiempo(0, 0, null, null, true, filtroLocal);
    if ((paginaSeries.getLista() == null) || (paginaSeries.getLista().size() < 1)) {
      crearSerieTiempo(SerieTiempo.getSerieValorAlerta().getNombre(), usuario);
    }
    filtroLocal.put("identificador", SerieTiempo.getSeriePorcentajeReal().getIdentificador());
    paginaSeries = persistenceSession.getSeriesTiempo(0, 0, null, null, true, filtroLocal);
    if ((paginaSeries.getLista() == null) || (paginaSeries.getLista().size() < 1)) {
      crearSerieTiempo(SerieTiempo.getSeriePorcentajeReal().getNombre(), usuario);
    }
    filtroLocal.put("identificador", SerieTiempo.getSeriePorcentajeProgramado().getIdentificador());
    paginaSeries = persistenceSession.getSeriesTiempo(0, 0, null, null, true, filtroLocal);
    if ((paginaSeries.getLista() == null) || (paginaSeries.getLista().size() < 1)) {
      crearSerieTiempo(SerieTiempo.getSeriePorcentajeProgramado().getNombre(), usuario);
    }
    return persistenceSession.getSeriesTiempo(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public PaginaLista getSeriesTiempo(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<Object, Object> filtros)
  {
    Map<Object, Object> filtroLocal = new HashMap();
    
    filtroLocal.put("identificador", SerieTiempo.getSerieReal().getNombre());
    filtroLocal.put("identificador", SerieTiempo.getSerieProgramado().getNombre());
    filtroLocal.put("identificador", SerieTiempo.getSerieMinimo().getNombre());
    filtroLocal.put("identificador", SerieTiempo.getSerieMaximo().getNombre());
    filtroLocal.put("identificador", SerieTiempo.getSerieValorAlerta().getNombre());
    filtroLocal.put("identificador", SerieTiempo.getSeriePorcentajeReal().getNombre());
    filtroLocal.put("identificador", SerieTiempo.getSeriePorcentajeProgramado().getNombre());
    
    return persistenceSession.getSeriesTiempo(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public PaginaLista getSeriesTiempoByList(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<Object, Object> filtros, List<SerieTiempo> seriesTiempo, Usuario usuario)
  {
    Map<Object, Object> filtroLocal = new HashMap();
    PaginaLista paginaSeries = null;
    
    for (Iterator<SerieTiempo> iter = seriesTiempo.iterator(); iter.hasNext();)
    {
      SerieTiempo serie = (SerieTiempo)iter.next();
      filtroLocal.put("identificador", serie.getNombre());
      paginaSeries = persistenceSession.getSeriesTiempo(0, 0, null, null, true, filtroLocal);
      if ((paginaSeries.getLista() == null) || (paginaSeries.getLista().size() < 1)) {
        crearSerieTiempo(serie.getNombre(), usuario);
      }
    }
    return persistenceSession.getSeriesTiempo(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public int deleteSerieTiempo(SerieTiempo serieTiempo, Usuario usuario)
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
      
      if (serieTiempo.getSerieId() != null) {
        resultado = persistenceSession.delete(serieTiempo, usuario);
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
  
  public int saveSerieTiempo(SerieTiempo serieTiempo, Usuario usuario)
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
      
      if ((serieTiempo.getSerieId() == null) || 
        (serieTiempo.getSerieId().longValue() == SerieTiempo.getSerieReal().getSerieId().longValue()) || 
        (serieTiempo.getSerieId().longValue() == SerieTiempo.getSerieProgramado().getSerieId().longValue()) || 
        (serieTiempo.getSerieId().longValue() == SerieTiempo.getSerieMinimo().getSerieId().longValue()) || 
        (serieTiempo.getSerieId().longValue() == SerieTiempo.getSerieMaximo().getSerieId().longValue()) || 
        (serieTiempo.getSerieId().longValue() == SerieTiempo.getSerieValorAlerta().getSerieId().longValue()) || 
        (serieTiempo.getSerieId().longValue() == SerieTiempo.getSeriePorcentajeReal().getSerieId().longValue()) || 
        (serieTiempo.getSerieId().longValue() == SerieTiempo.getSeriePorcentajeProgramado().getSerieId().longValue()))
      {
        fieldNames[0] = "nombre";
        fieldValues[0] = serieTiempo.getNombre();
        boolean existeObjeto = persistenceSession.existsObject(serieTiempo, fieldNames, fieldValues);
        
        if (!existeObjeto)
        {
          fieldNames[0] = "identificador";
          fieldValues[0] = serieTiempo.getIdentificador();
          existeObjeto = persistenceSession.existsObject(serieTiempo, fieldNames, fieldValues);
        }
        
        if (existeObjeto) {
          resultado = 10003;
        }
        else {
          if (serieTiempo.getSerieId() == null)
            serieTiempo.setSerieId(new Long(persistenceSession.getUniqueId()));
          resultado = persistenceSession.insert(serieTiempo, usuario);
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "serieId";
        idFieldValues[0] = serieTiempo.getSerieId();
        if (persistenceSession.existsObject(serieTiempo, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          resultado = persistenceSession.update(serieTiempo, usuario);
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
  
  private SerieTiempo crearSerieTiempo(String identificador, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    SerieTiempo serieTiempo = new SerieTiempo();
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if ((identificador != null) && (!identificador.equals("")) && (identificador.equals(SerieTiempo.getSerieReal().getNombre())))
        serieTiempo = SerieTiempo.getSerieReal();
      if ((identificador != null) && (!identificador.equals("")) && (identificador.equals(SerieTiempo.getSerieProgramado().getNombre())))
        serieTiempo = SerieTiempo.getSerieProgramado();
      if ((identificador != null) && (!identificador.equals("")) && (identificador.equals(SerieTiempo.getSerieMinimo().getNombre())))
        serieTiempo = SerieTiempo.getSerieMinimo();
      if ((identificador != null) && (!identificador.equals("")) && (identificador.equals(SerieTiempo.getSerieMaximo().getNombre())))
        serieTiempo = SerieTiempo.getSerieMaximo();
      if ((identificador != null) && (!identificador.equals("")) && (identificador.equals(SerieTiempo.getSerieValorAlerta().getNombre())))
        serieTiempo = SerieTiempo.getSerieValorAlerta();
      if ((identificador != null) && (!identificador.equals("")) && (identificador.equals(SerieTiempo.getSeriePorcentajeReal().getNombre())))
        serieTiempo = SerieTiempo.getSeriePorcentajeReal();
      if ((identificador != null) && (!identificador.equals("")) && (identificador.equals(SerieTiempo.getSeriePorcentajeProgramado().getNombre()))) {
        serieTiempo = SerieTiempo.getSeriePorcentajeProgramado();
      }
      resultado = saveSerieTiempo(serieTiempo, usuario);
      
      if (resultado == 10000) {
        serieTiempo = (SerieTiempo)persistenceSession.load(SerieTiempo.class, serieTiempo.getSerieId());
      } else {
        serieTiempo = null;
      }
      if (transActiva)
      {
        if ((resultado == 10000) && (serieTiempo != null)) {
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
    
    return serieTiempo;
  }
}
