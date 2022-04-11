package com.visiongc.app.strategos.problemas.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.problemas.StrategosAccionesService;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.StrategosSeguimientosService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.MemoProblema;
import com.visiongc.app.strategos.problemas.model.MemoProblemaPK;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.problemas.persistence.StrategosProblemasPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrategosProblemasServiceImpl
  extends StrategosServiceImpl implements StrategosProblemasService
{
  private StrategosProblemasPersistenceSession persistenceSession = null;
  
  public StrategosProblemasServiceImpl(StrategosProblemasPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getProblemas(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
      PaginaLista paginaProblemas = persistenceSession.getProblemas(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
      if(filtros != null)
      {
          Iterator iter = filtros.keySet().iterator();
          String fieldName = null;
          String fieldValue = null;
          while(iter.hasNext()) 
          {
              fieldName = (String)iter.next();
              fieldValue = (String)filtros.get(fieldName);
              if(fieldName.equals("tipoPendiente") && fieldValue.equals("1"))
              {
                  StrategosAccionesService strategosAccionesService = StrategosServiceFactory.getInstance().openStrategosAccionesService();
                  for(Iterator i = paginaProblemas.getLista().iterator(); i.hasNext();)
                  {
                      Problema problema = (Problema)i.next();
                      List listaAccionesCorrectivas = strategosAccionesService.getAccionesCorrectivas(problema.getProblemaId());
                      StrategosSeguimientosService strategosSeguimientosService;
                      for(Iterator n = listaAccionesCorrectivas.iterator(); n.hasNext(); strategosSeguimientosService.close())
                      {
                          Accion accionCorrectiva = (Accion)n.next();
                          strategosSeguimientosService = StrategosServiceFactory.getInstance().openStrategosSeguimientosService();
                          if(strategosSeguimientosService.esDiaSeguimiento(accionCorrectiva.getAccionId()).booleanValue())
                              problema.getListaAccionesCorrectivas().add(accionCorrectiva);
                      }

                  }

                  strategosAccionesService.close();
              }
          }
      }
      return paginaProblemas;
  }
  
  public Long getNumeroProblemas(Long claseId)
  {
    return persistenceSession.getNumeroProblemas(claseId);
  }
  
  public int deleteProblema(Problema problema, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (problema.getProblemaId() != null) {
        resultado = deleteDependenciasProblemas(problema, usuario);
        
        resultado = persistenceSession.delete(problema, usuario);
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
  
  public int saveProblema(Problema problema, Usuario usuario)
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
      
      fieldNames[0] = "nombre";
      fieldNames[1] = "claseId";
      fieldNames[2] = "organizacionId";
      fieldValues[0] = problema.getNombre();
      fieldValues[1] = problema.getClaseId();
      fieldValues[2] = problema.getOrganizacionId();
      
      if ((problema.getProblemaId() == null) || (problema.getProblemaId().longValue() == 0L)) {
        if (persistenceSession.existsObject(problema, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          problema.setProblemaId(new Long(persistenceSession.getUniqueId()));
          
          for (Iterator iter = problema.getMemosProblema().iterator(); iter.hasNext();) {
            MemoProblema memo = (MemoProblema)iter.next();
            memo.getPk().setProblemaId(problema.getProblemaId());
          }
          problema.setCreado(new Date());
          problema.setCreadoId(usuario.getUsuarioId());
          problema.setModificado(new Date());
          problema.setModificadoId(usuario.getUsuarioId());
          resultado = persistenceSession.insert(problema, usuario);
        }
      } else {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "problemaId";
        idFieldValues[0] = problema.getProblemaId();
        
        if (persistenceSession.existsObject(problema, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          problema.setModificado(new Date());
          problema.setModificadoId(usuario.getUsuarioId());
          resultado = persistenceSession.update(problema, usuario);
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
  
  private int deleteDependenciasProblemas(Problema problema, Usuario usuario)
  {
    int resultado = 10000;
    ListaMap dependencias = new ListaMap();
    List listaObjetosRelacionados = new ArrayList();
    
    dependencias = persistenceSession.getDependenciasProblema(problema);
    
    for (Iterator i = dependencias.iterator(); i.hasNext();) {
      listaObjetosRelacionados = (List)i.next();
      if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Accion)))
      {

        StrategosAccionesService strategosAccionesService = StrategosServiceFactory.getInstance().openStrategosAccionesService(this);
        
        for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();) {
          Accion accion = (Accion)j.next();
          
          resultado = strategosAccionesService.deleteAccion(accion, usuario);
          
          if (resultado != 10000) {
            break;
          }
        }
        
        strategosAccionesService.close();
      }
    }
    return resultado;
  }
  
  public int updateCampo(Long problemaId, Map<?, ?> filtros) throws Throwable
  {
    return persistenceSession.updateCampo(problemaId, filtros);
  }
}
