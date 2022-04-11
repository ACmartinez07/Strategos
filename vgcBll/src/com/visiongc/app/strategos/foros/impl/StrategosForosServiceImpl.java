package com.visiongc.app.strategos.foros.impl;

import com.visiongc.app.strategos.foros.model.Foro;
import com.visiongc.app.strategos.foros.persistence.StrategosForosPersistenceSession;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrategosForosServiceImpl extends StrategosServiceImpl implements com.visiongc.app.strategos.foros.StrategosForosService
{
  private StrategosForosPersistenceSession persistenceSession = null;
  
  public StrategosForosServiceImpl(StrategosForosPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getForos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, String> filtros)
  {
    PaginaLista foros = persistenceSession.getForos(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
    for (Iterator<?> i = foros.getLista().iterator(); i.hasNext();)
    {
      Foro foro = (Foro)i.next();
      foro.setNumeroRespuestas(persistenceSession.getNumeroHijosForo(foro.getForoId()));
      foro.setUltimaRepuestaForo(persistenceSession.getUltimaRepuestaForo(foro.getForoId()));
    }
    
    return foros;
  }
  
  public int deleteForo(Foro foro, Usuario usuario)
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
      
      if (foro.getForoId() != null)
      {
        if ((foro.getHijos() != null) && (foro.getHijos().size() > 0)) {
          for (Iterator<?> iter = foro.getHijos().iterator(); iter.hasNext();) {
            Foro hijo = (Foro)iter.next();
            resultado = deleteForo(hijo, usuario);
            
            if (resultado != 10000)
              break;
          }
        }
        if (resultado == 10000) {
          resultado = persistenceSession.delete(foro, usuario);
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
  
  public Foro getRutaCompletaForos(Long foroId, List<Foro> listaForos)
  {
    Foro foro = (Foro)load(Foro.class, foroId);
    Long padreId = foro.getPadreId();
    if (padreId != null) {
      listaForos.add(getRutaCompletaForos(padreId, listaForos));
    }
    return foro;
  }
  
  public int saveForo(Foro foro, Usuario usuario)
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
      
      if ((foro.getForoId() == null) || (foro.getForoId().longValue() == 0L))
      {
        foro.setForoId(new Long(persistenceSession.getUniqueId()));
        foro.setCreado(new Date());
        foro.setCreadoId(usuario.getUsuarioId());
        foro.setModificado(new Date());
        foro.setModificadoId(usuario.getUsuarioId());
        resultado = persistenceSession.insert(foro, usuario);
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "foroId";
        idFieldValues[0] = foro.getForoId();
        if (persistenceSession.existsObject(foro, idFieldNames, idFieldValues)) {
          resultado = 10003;
        }
        else {
          foro.setModificado(new Date());
          foro.setModificadoId(usuario.getUsuarioId());
          resultado = persistenceSession.update(foro, usuario);
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
  
  public Long getNumeroForos(Long indicadorId)
  {
    return persistenceSession.getNumeroForos(indicadorId);
  }
}
