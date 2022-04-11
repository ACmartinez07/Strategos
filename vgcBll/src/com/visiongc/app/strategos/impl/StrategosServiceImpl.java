package com.visiongc.app.strategos.impl;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.foros.StrategosForosService;
import com.visiongc.app.strategos.foros.model.Foro;
import com.visiongc.app.strategos.graficos.StrategosGraficosService;
import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.Iterator;
import java.util.List;


public class StrategosServiceImpl
  extends VgcAbstractService
  implements StrategosService
{
  protected StrategosPersistenceSession persistenceSession;
  
  public StrategosServiceImpl(StrategosPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public StrategosPersistenceSession getStrategosPersistenceSession()
  {
    return persistenceSession;
  }
  
  public int deleteDependenciasGenericas(Long objetoId, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    List objetosDependientes = null;
    
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      ListaMap dependencias = persistenceSession.getDependenciasGenericas(objetoId);
      
      for (Iterator i = dependencias.iterator(); i.hasNext();)
      {
        objetosDependientes = (List)i.next();
        
        if ((objetosDependientes.size() > 0) && ((objetosDependientes.get(0) instanceof Explicacion)))
        {
          StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService(this);
          
          for (Iterator j = objetosDependientes.iterator(); j.hasNext();)
          {
            Explicacion explicacion = (Explicacion)j.next();
            
            resultado = strategosExplicacionesService.deleteExplicacion(explicacion, usuario);
            
            if (resultado != 10000)
              break;
          }
          strategosExplicacionesService.close();
        }
        else if ((objetosDependientes.size() > 0) && ((objetosDependientes.get(0) instanceof Foro)))
        {
          StrategosForosService strategosForosService = StrategosServiceFactory.getInstance().openStrategosForosService(this);
          
          for (Iterator j = objetosDependientes.iterator(); j.hasNext();)
          {
            Foro foro = (Foro)j.next();
            
            resultado = strategosForosService.deleteForo(foro, usuario);
            
            if (resultado != 10000)
              break;
          }
          strategosForosService.close();
        }
        else if ((objetosDependientes.size() > 0) && ((objetosDependientes.get(0) instanceof Grafico)))
        {
          StrategosGraficosService strategosGraficosService = StrategosServiceFactory.getInstance().openStrategosGraficosService();
          
          for (Iterator j = objetosDependientes.iterator(); j.hasNext();)
          {
            Grafico grafico = (Grafico)j.next();
            
            resultado = strategosGraficosService.deleteGrafico(grafico, usuario);
            
            if (resultado != 10000)
              break;
          }
          strategosGraficosService.close();
        }
        else
        {
          for (Iterator j = objetosDependientes.iterator(); j.hasNext();)
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
