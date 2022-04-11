package com.visiongc.app.strategos.presentaciones.impl;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.foros.StrategosForosService;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCeldaPK;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.presentaciones.persistence.StrategosCeldasPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrategosCeldasServiceImpl extends StrategosServiceImpl implements StrategosCeldasService
{
  private StrategosCeldasPersistenceSession persistenceSession = null;
  
  public StrategosCeldasServiceImpl(StrategosCeldasPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getCeldas(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros, Usuario usuario)
  {
    PaginaLista paginaListaCeldas = persistenceSession.getCeldas(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
    
    StrategosForosService strategosForosService = StrategosServiceFactory.getInstance().openStrategosForosService();
    StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
    StrategosPaginasService strategosPaginasService = StrategosServiceFactory.getInstance().openStrategosPaginasService();
    
    for (Iterator<Celda> i = paginaListaCeldas.getLista().iterator(); i.hasNext();)
    {
      Celda celda = (Celda)i.next();
      celda.setNumeroForos(strategosForosService.getNumeroForos(celda.getCeldaId()));
      celda.setNumeroExplicaciones(strategosExplicacionesService.getNumeroExplicaciones(celda.getCeldaId()));
    }
    
    Long paginaId = null;
    if (filtros != null)
    {
      Iterator<?> iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        fieldValue = (String)filtros.get(fieldName);
        if (fieldName.equals("paginaId")) {
          paginaId = new Long(fieldValue);
        }
      }
    }
    Pagina paginaCeldas = (Pagina)strategosPaginasService.load(Pagina.class, paginaId);
    strategosPaginasService.close();
    int resultado = 10000;
    boolean transActiva = false;
    
    if (paginaCeldas != null)
    {
      List<Celda> listaCeldasCargadas = paginaListaCeldas.getLista();
      List<Celda> listaCeldasNueva = new ArrayList();
      List<Celda> listaCeldasDelete = new ArrayList();
      
      try
      {
        if (!persistenceSession.isTransactionActive())
        {
          persistenceSession.beginTransaction();
          transActiva = true;
        }
        
        for (byte fila = 1; fila <= paginaCeldas.getFilas().byteValue(); fila = (byte)(fila + 1))
        {
          for (byte columna = 1; columna <= paginaCeldas.getColumnas().byteValue(); columna = (byte)(columna + 1))
          {
            boolean celdaExiste = false;
            Celda celdaCargada = null;
            
            int index = 0;
            for (Iterator<Celda> iter = listaCeldasCargadas.iterator(); iter.hasNext();)
            {
              celdaCargada = (Celda)iter.next();
              if ((celdaCargada.getFila().byteValue() == fila) && (celdaCargada.getColumna().byteValue() == columna))
              {
                celdaExiste = true;
                listaCeldasCargadas.remove(index);
                break;
              }
              index++;
            }
            
            if (!celdaExiste)
            {
              Celda nuevaCelda = new Celda();
              
              nuevaCelda.setCeldaId(new Long(0L));
              nuevaCelda.setPaginaId(paginaId);
              nuevaCelda.setIndicadoresCelda(new HashSet());
              nuevaCelda.setTitulo(null);
              nuevaCelda.setColumna(new Byte(columna));
              nuevaCelda.setFila(new Byte(fila));
              
              if (usuario != null)
                resultado = saveCelda(nuevaCelda, usuario);
              if (resultado != 10000) break;
              listaCeldasNueva.add(nuevaCelda);

            }
            else
            {
              listaCeldasNueva.add(celdaCargada);
            }
          }
          if (resultado != 10000)
          {
            listaCeldasNueva = new ArrayList();
            break;
          }
        }
        
        if (usuario != null)
        {
          boolean borrarCelda = true;
          for (Iterator<Celda> i = paginaListaCeldas.getLista().iterator(); i.hasNext();)
          {
            Celda celda = (Celda)i.next();
            for (Iterator<Celda> j = listaCeldasNueva.iterator(); j.hasNext();)
            {
              Celda celdaNueva = (Celda)j.next();
              if (celda.getCeldaId() == celdaNueva.getCeldaId())
              {
                borrarCelda = false;
                break;
              }
            }
            if (borrarCelda) {
              listaCeldasDelete.add(celda);
            }
          }
          for (Iterator<Celda> i = listaCeldasDelete.iterator(); i.hasNext();)
          {
            Celda celda = (Celda)i.next();
            deleteCelda(celda, usuario);
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
        
        paginaListaCeldas.setLista(listaCeldasNueva);
        paginaListaCeldas.setTotal(listaCeldasNueva.size());

      }
      catch (Throwable t)
      {
        strategosForosService.close();
        strategosExplicacionesService.close();
        if (transActiva)
          persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    
    strategosForosService.close();
    strategosExplicacionesService.close();
    
    return paginaListaCeldas;
  }
  
  public int saveCelda(Celda celda, Usuario usuario)
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
      
      if ((celda.getCeldaId() == null) || (celda.getCeldaId().longValue() == 0L))
      {
        celda.setCeldaId(new Long(persistenceSession.getUniqueId()));
        
        for (Iterator i = celda.getIndicadoresCelda().iterator(); i.hasNext();)
        {
          IndicadorCelda indicadorCelda = (IndicadorCelda)i.next();
          indicadorCelda.getPk().setCeldaId(celda.getCeldaId());
        }
        
        resultado = persistenceSession.insert(celda, usuario);
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "celdaId";
        idFieldValues[0] = celda.getCeldaId();
        resultado = persistenceSession.update(celda, usuario);
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
  
  public int deleteCelda(Celda celda, Usuario usuario)
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
      
      if (celda.getCeldaId() != null)
      {
        resultado = deleteDependenciasGenericas(celda.getCeldaId(), usuario);
        
        if (resultado == 10000) {
          resultado = persistenceSession.delete(celda, usuario);
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
