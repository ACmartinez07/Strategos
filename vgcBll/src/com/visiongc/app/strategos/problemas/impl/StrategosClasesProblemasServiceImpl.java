package com.visiongc.app.strategos.problemas.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.problemas.StrategosClasesProblemasService;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas.TipoClaseProblema;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.problemas.persistence.StrategosClasesProblemasPersistenceSession;
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

public class StrategosClasesProblemasServiceImpl extends StrategosServiceImpl implements StrategosClasesProblemasService
{
  private StrategosClasesProblemasPersistenceSession persistenceSession = null;
  
  public StrategosClasesProblemasServiceImpl(StrategosClasesProblemasPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getClaseProblemas(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    return persistenceSession.getClaseProblemas(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public int deleteClaseProblema(ClaseProblemas claseProblemas, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    ListaMap dependencias = null;
    List<?> listaObjetosRelacionados = new ArrayList();
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (claseProblemas.getClaseId() != null)
      {
        if ((claseProblemas.getHijos() != null) && (claseProblemas.getHijos().size() > 0)) {
          for (Iterator<?> iter = claseProblemas.getHijos().iterator(); iter.hasNext();) {
            ClaseProblemas claseHija = (ClaseProblemas)iter.next();
            
            resultado = deleteClaseProblema(claseHija, usuario);
            
            if (resultado != 10000) {
              break;
            }
          }
        }
        
        if (resultado == 10000) {
          dependencias = persistenceSession.getDependenciasClaseProblemas(claseProblemas);
          
          for (Iterator<?> i = dependencias.iterator(); i.hasNext();) {
            listaObjetosRelacionados = (List)i.next();
            if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Problema)))
            {
              StrategosProblemasService strategosProblemasService = StrategosServiceFactory.getInstance().openStrategosProblemasService(this);
              for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();) {
                Problema problema = (Problema)j.next();
                resultado = strategosProblemasService.deleteProblema(problema, usuario);
                if (resultado != 10000) {
                  break;
                }
              }
              
              strategosProblemasService.close();
            }
          }
          if (resultado == 10000)
          {
            resultado = persistenceSession.delete(claseProblemas, usuario);
          }
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
  
  public int saveClaseProblema(ClaseProblemas claseProblemas, Usuario usuario)
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
      fieldNames[1] = "padreId";
      fieldNames[2] = "organizacionId";
      fieldValues[0] = claseProblemas.getNombre();
      fieldValues[1] = claseProblemas.getPadreId();
      fieldValues[2] = claseProblemas.getOrganizacionId();
      
      if ((claseProblemas.getClaseId() == null) || (claseProblemas.getClaseId().longValue() == 0L)) {
        if (persistenceSession.existsObject(claseProblemas, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          claseProblemas.setClaseId(new Long(persistenceSession.getUniqueId()));
          claseProblemas.setCreado(new Date());
          claseProblemas.setCreadoId(usuario.getUsuarioId());
          resultado = persistenceSession.insert(claseProblemas, usuario);
        }
      } else {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "claseId";
        idFieldValues[0] = claseProblemas.getClaseId();
        if (persistenceSession.existsObject(claseProblemas, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          claseProblemas.setModificado(new Date());
          claseProblemas.setModificadoId(usuario.getUsuarioId());
          resultado = persistenceSession.update(claseProblemas, usuario);
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
  
  public ClaseProblemas crearClaseRaiz(Long organizacionId, Usuario usuario, Integer tipo)
  {
    boolean transActiva = false;
    int resultado = 10000;
    ClaseProblemas claseProblemas = new ClaseProblemas();
    
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      claseProblemas.setClaseId(new Long(0L));
      claseProblemas.setPadreId(null);
      if (tipo.intValue() == ClaseProblemas.TipoClaseProblema.getTipoProblema().intValue()) {
        claseProblemas.setNombre(messageResources.getResource("claseproblemas.raiz.nombre"));
      } else if (tipo.intValue() == ClaseProblemas.TipoClaseProblema.getTipoRiesgo().intValue())
        claseProblemas.setNombre(messageResources.getResource("claseriesgo.raiz.nombre"));
      claseProblemas.setOrganizacionId(organizacionId);
      claseProblemas.setDescripcion(null);
      claseProblemas.setTipo(tipo);
      
      resultado = saveClaseProblema(claseProblemas, usuario);
      
      if (resultado == 10000) {
        claseProblemas = (ClaseProblemas)persistenceSession.load(ClaseProblemas.class, claseProblemas.getClaseId());
      } else {
        claseProblemas = null;
      }
      if (transActiva)
      {
        if ((resultado == 10000) && (claseProblemas != null)) {
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
    
    return claseProblemas;
  }
}
