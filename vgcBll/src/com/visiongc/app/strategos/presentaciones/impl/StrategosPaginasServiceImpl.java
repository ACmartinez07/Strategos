package com.visiongc.app.strategos.presentaciones.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.presentaciones.persistence.StrategosPaginasPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StrategosPaginasServiceImpl extends StrategosServiceImpl implements StrategosPaginasService
{
  private StrategosPaginasPersistenceSession persistenceSession = null;
  
  public StrategosPaginasServiceImpl(StrategosPaginasPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getPaginas(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, String> filtros)
  {
    return persistenceSession.getPaginas(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  private int deleteDependencias(Long paginaId, Usuario usuario)
  {
    int resultado = 10000;
    
    StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
    
    Map<String, String> filtroCelda = new java.util.HashMap();
    filtroCelda.put("paginaId", paginaId.toString());
    
    PaginaLista paginaCeldas = strategosCeldasService.getCeldas(1, 30, "celdaId", "ASC", true, filtroCelda, null);
    
    for (Iterator<Celda> i = paginaCeldas.getLista().iterator(); i.hasNext();)
    {
      Celda celda = (Celda)i.next();
      resultado = strategosCeldasService.deleteCelda(celda, usuario);
      if (resultado != 10000) {
        break;
      }
    }
    strategosCeldasService.close();
    
    return resultado;
  }
  
  public int deletePagina(Pagina pagina, Usuario usuario)
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
      
      if (pagina.getPaginaId() != null)
      {
        resultado = deleteDependencias(pagina.getPaginaId(), usuario);
        
        Integer numeroPagina = pagina.getNumero();
        if (resultado == 10000) {
          resultado = persistenceSession.delete(pagina, usuario);
        }
        if (resultado == 10000)
        {
          Vista vista = pagina.getVista();
          Portafolio portafolio = pagina.getPortafolio();
          if (vista != null)
            cambiarNumeroPaginas(vista, numeroPagina, usuario);
          if (portafolio != null) {
            cambiarNumeroPaginas(portafolio, numeroPagina, usuario);
          }
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
  
  private int cambiarNumeroPaginas(Portafolio portafolio, Integer numeroPagina, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    
    if (!persistenceSession.isTransactionActive())
    {
      persistenceSession.beginTransaction();
      transActiva = true;
    }
    
    for (Iterator<Pagina> i = portafolio.getPaginas().iterator(); i.hasNext();)
    {
      Pagina paginaActual = (Pagina)i.next();
      if (paginaActual.getNumero().intValue() > numeroPagina.intValue())
      {
        paginaActual.setNumero(new Integer(paginaActual.getNumero().intValue() - 1));
        savePagina(paginaActual, usuario);
      }
    }
    
    if (transActiva)
    {
      persistenceSession.commitTransaction();
      transActiva = false;
    }
    
    return resultado;
  }
  
  private int cambiarNumeroPaginas(Vista vista, Integer numeroPagina, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    
    if (!persistenceSession.isTransactionActive())
    {
      persistenceSession.beginTransaction();
      transActiva = true;
    }
    
    for (Iterator<Pagina> i = vista.getPaginas().iterator(); i.hasNext();)
    {
      Pagina paginaActual = (Pagina)i.next();
      if (paginaActual.getNumero().intValue() > numeroPagina.intValue())
      {
        paginaActual.setNumero(new Integer(paginaActual.getNumero().intValue() - 1));
        savePagina(paginaActual, usuario);
      }
    }
    
    if (transActiva)
    {
      persistenceSession.commitTransaction();
      transActiva = false;
    }
    
    return resultado;
  }
  
  public int savePagina(Pagina pagina, Usuario usuario)
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
      
      if ((pagina.getPaginaId() == null) || (pagina.getPaginaId().longValue() == 0L))
      {
        pagina.setPaginaId(new Long(persistenceSession.getUniqueId()));
        resultado = persistenceSession.insert(pagina, usuario);
      }
      else
      {
        eliminarCeldasSobrantes(pagina, usuario);
        resultado = persistenceSession.update(pagina, usuario);
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
  
  private void eliminarCeldasSobrantes(Pagina pagina, Usuario usuario)
  {
    StrategosPaginasService strategosPaginasService = StrategosServiceFactory.getInstance().openStrategosPaginasService();
    StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
    
    boolean eliminar = false;
    int totalCeldas = pagina.getCeldas().size();
    
    for (byte k = 1; k <= totalCeldas; k = (byte)(k + 1))
    {
      eliminar = false;
      Celda celda = null;
      for (Iterator<Celda> i = pagina.getCeldas().iterator(); i.hasNext();)
      {
        celda = (Celda)i.next();
        
        if ((celda.getFila().byteValue() > pagina.getFilas().byteValue()) || (celda.getColumna().byteValue() > pagina.getColumnas().byteValue())) {
          eliminar = true;
          break;
        }
      }
      if (eliminar) {
        pagina.getCeldas().remove(celda);
      }
    }
    strategosPaginasService.close();
    strategosCeldasService.close();
  }
  
  public int cambiarOrdenPaginas(Long paginaId, boolean subir, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    
    if (!persistenceSession.isTransactionActive())
    {
      persistenceSession.beginTransaction();
      transActiva = true;
    }
    
    Pagina paginaActual = (Pagina)persistenceSession.load(Pagina.class, paginaId);
    Vista vista = paginaActual.getVista();
    
    int totalPaginas = vista.getPaginas().size();
    int numeroPaginaActual = paginaActual.getNumero().intValue();
    
    int numeroPaginaSiguiente = numeroPaginaActual + 1;
    int numeroPaginaPrevia = numeroPaginaActual - 1;
    
    if (numeroPaginaPrevia < 1)
      numeroPaginaPrevia = totalPaginas;
    if (numeroPaginaSiguiente > totalPaginas) {
      numeroPaginaSiguiente = 1;
    }
    Pagina paginaSiguiente = null;
    Pagina paginaPrevia = null;
    
    for (Iterator i = vista.getPaginas().iterator(); i.hasNext();)
    {
      Pagina objetoPagina = (Pagina)i.next();
      
      if (subir)
      {
        if (objetoPagina.getNumero().intValue() == numeroPaginaPrevia)
        {
          paginaPrevia = objetoPagina;
          break;
        }
      }
      else if (objetoPagina.getNumero().intValue() == numeroPaginaSiguiente)
      {
        paginaSiguiente = objetoPagina;
        break;
      }
    }
    
    if (subir)
    {
      paginaPrevia.setNumero(new Integer(numeroPaginaActual));
      paginaActual.setNumero(new Integer(numeroPaginaPrevia));
    }
    else
    {
      paginaSiguiente.setNumero(new Integer(numeroPaginaActual));
      paginaActual.setNumero(new Integer(numeroPaginaSiguiente));
    }
    
    resultado = persistenceSession.update(vista, usuario);
    
    if (transActiva)
    {
      persistenceSession.commitTransaction();
      transActiva = false;
    }
    
    return resultado;
  }
  
  public int getOrdenMaximoPaginas(Long vistaId)
  {
    return persistenceSession.getOrdenMaximoPaginas(vistaId) + 1;
  }
}
