package com.visiongc.app.strategos.explicaciones.impl;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacionPK;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacionPK;
import com.visiongc.app.strategos.explicaciones.persistence.StrategosExplicacionesPersistenceSession;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrategosExplicacionesServiceImpl
  extends StrategosServiceImpl
  implements StrategosExplicacionesService
{
  private StrategosExplicacionesPersistenceSession persistenceSession = null;
  
  public StrategosExplicacionesServiceImpl(StrategosExplicacionesPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getExplicaciones(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    PaginaLista explicaciones = this.persistenceSession.getExplicaciones(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
    for (Iterator i = explicaciones.getLista().iterator(); i.hasNext();)
    {
      Explicacion explicacion = (Explicacion)i.next();
      explicacion.setNumeroAdjuntos(this.persistenceSession.getNumeroAdjuntos(explicacion.getExplicacionId()));
    }
    return explicaciones;
  }
  
  public int deleteExplicacion(Explicacion explicacion, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      if (explicacion.getExplicacionId() != null) {
        resultado = this.persistenceSession.delete(explicacion, usuario);
      }
      if (resultado == 10000)
      {
        if (transActiva)
        {
          this.persistenceSession.commitTransaction();
          transActiva = false;
        }
      }
      else if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    return resultado;
  }
  
  public int saveExplicacion(Explicacion explicacion, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[3];
    Object[] fieldValues = new Object[3];
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      fieldNames[0] = "titulo";
      fieldValues[0] = explicacion.getTitulo();
      fieldNames[1] = "tipo";
      fieldValues[1] = explicacion.getTipo();
      fieldNames[2] = "objetoId";
      fieldValues[2] = explicacion.getObjetoId();
      Set adjuntosExplicacion = prepareAdjuntosExplicacion(explicacion);
      
      if ((explicacion.getExplicacionId() == null) || (explicacion.getExplicacionId().longValue() == 0L))
      {
        if (this.persistenceSession.existsObject(explicacion, fieldNames, fieldValues))
        {
          resultado = 10003;
        }
        else
        {
          explicacion.setExplicacionId(new Long(this.persistenceSession.getUniqueId()));
          for (Iterator iter = explicacion.getMemosExplicacion().iterator(); iter.hasNext();)
          {
            MemoExplicacion memoExplicacion = (MemoExplicacion)iter.next();
            memoExplicacion.getPk().setExplicacionId(explicacion.getExplicacionId());
          }
          explicacion.setCreado(new Date());
          explicacion.setCreadoId(usuario.getUsuarioId());
          resultado = this.persistenceSession.insert(explicacion, usuario);
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "explicacionId";
        idFieldValues[0] = explicacion.getExplicacionId();
        if (this.persistenceSession.existsObject(explicacion, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          resultado = this.persistenceSession.update(explicacion, usuario);
        }
      }
     
      if (resultado == 10000) {
        resultado = saveAdjuntosExplicacion(adjuntosExplicacion, explicacion.getExplicacionId(), usuario);
      }
      
      if (transActiva)
      {
        if (resultado == 10000) {
          this.persistenceSession.commitTransaction();
        } else {
          this.persistenceSession.rollbackTransaction();
        }
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        this.persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    return resultado;
  }
  
  private Set prepareAdjuntosExplicacion(Explicacion explicacion)
  {
    Set adjuntosExplicacion = new HashSet();
    try
    {
      for (Iterator i = explicacion.getAdjuntosExplicacion().iterator(); i.hasNext();)
      {
        AdjuntoExplicacion adjuntoOriginal = (AdjuntoExplicacion)i.next();
        
        AdjuntoExplicacion adjuntoCopia = new AdjuntoExplicacion();
        if (adjuntoOriginal.getPk() != null)
        {
          AdjuntoExplicacionPK adjuntoPK = new AdjuntoExplicacionPK();
          adjuntoPK.setAdjuntoId(adjuntoOriginal.getPk().getAdjuntoId());
          adjuntoPK.setExplicacionId(adjuntoOriginal.getPk().getExplicacionId());
          adjuntoCopia.setPk(adjuntoPK);
          AdjuntoExplicacion adjuntoTemp = (AdjuntoExplicacion)load(AdjuntoExplicacion.class, adjuntoPK);
          
        }
        adjuntoCopia.setRuta(adjuntoOriginal.getRuta());
        adjuntoCopia.setTitulo(adjuntoOriginal.getTitulo());
        adjuntosExplicacion.add(adjuntoCopia);
      }
      explicacion.getAdjuntosExplicacion().clear();
    }
    catch (Throwable t)
    {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    return adjuntosExplicacion;
  }
  
  private int saveAdjuntosExplicacion(Set adjuntosExplicacion, Long explicacionId, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      for (Iterator i = adjuntosExplicacion.iterator(); i.hasNext();)
      {
        AdjuntoExplicacion adjuntoExplicacion = (AdjuntoExplicacion)i.next();
        
        resultado = saveAdjuntoExplicacion(adjuntoExplicacion, explicacionId, usuario);
        if (resultado != 10000) {
          break;
        }
      }
      if (transActiva)
      {
        if (resultado == 10000) {
          this.persistenceSession.commitTransaction();
        } else {
          this.persistenceSession.rollbackTransaction();
        }
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        this.persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    return resultado;
  }
  
  private int saveAdjuntoExplicacion(AdjuntoExplicacion adjuntoExplicacion, Long explicacionId, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      if (adjuntoExplicacion.getPk() == null)
      {
        AdjuntoExplicacionPK adjuntoExplicacionPk = new AdjuntoExplicacionPK();
        adjuntoExplicacionPk.setAdjuntoId(new Long(this.persistenceSession.getUniqueId()));
        adjuntoExplicacionPk.setExplicacionId(explicacionId);
        adjuntoExplicacion.setPk(adjuntoExplicacionPk);
      }
      resultado = this.persistenceSession.insert(adjuntoExplicacion, usuario);
      if (resultado == 10000)
      {
        String[] fieldNames = new String[2];
        Object[] fieldValues = new Object[2];
        fieldNames[0] = "adjunto_id";
        fieldNames[1] = "explicacion_id";
        fieldValues[0] = adjuntoExplicacion.getPk().getAdjuntoId();
        fieldValues[1] = adjuntoExplicacion.getPk().getExplicacionId();
       }
      if (transActiva)
      {
        if (resultado == 10000) {
          this.persistenceSession.commitTransaction();
        } else {
          this.persistenceSession.rollbackTransaction();
        }
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        this.persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    return resultado;
  }
  
  public Long getNumeroAdjuntos(Long explicacionId)
  {
    return this.persistenceSession.getNumeroAdjuntos(explicacionId);
  }
  
  public Long getNumeroExplicaciones(Long indicadorId)
  {
    return this.persistenceSession.getNumeroExplicaciones(indicadorId);
  }
  
  public int deleteAdjuntoExplicacion(AdjuntoExplicacion adjunto, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      if (adjunto.getPk().getAdjuntoId() != null) {
        resultado = this.persistenceSession.delete(adjunto, usuario);
      }
      if (resultado == 10000)
      {
        if (transActiva)
        {
          this.persistenceSession.commitTransaction();
          transActiva = false;
        }
      }
      else if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    return resultado;
  }
  
  public AdjuntoExplicacion getAdjunto(Long AdjuntoId){
	  return this.persistenceSession.getAdjunto(AdjuntoId);
  }
}
