package com.visiongc.app.strategos.problemas.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.problemas.StrategosAccionesService;
import com.visiongc.app.strategos.problemas.StrategosSeguimientosService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.problemas.model.ResponsableAccion;
import com.visiongc.app.strategos.problemas.model.ResponsableAccionPK;
import com.visiongc.app.strategos.problemas.model.Seguimiento;
import com.visiongc.app.strategos.problemas.persistence.StrategosAccionesPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class StrategosAccionesServiceImpl
  extends StrategosServiceImpl
  implements StrategosAccionesService
{
  private StrategosAccionesPersistenceSession persistenceSession = null;
  
  public StrategosAccionesServiceImpl(StrategosAccionesPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public List getAccionesCorrectivas(Long problemaId)
  {
    return persistenceSession.getAccionesCorrectivas(problemaId);
  }
  
  public int deleteAccion(Accion accion, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (accion.getAccionId() != null)
      {
        resultado = deleteDependenciasAcciones(accion, usuario);
        
        resultado = persistenceSession.delete(accion, usuario);
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
  
  public int saveAccion(Accion accion, Usuario usuario)
  {
    return saveAccion(accion, usuario, Boolean.valueOf(true));
  }
  
  public int saveAccion(Accion accion, Usuario usuario, Boolean validar)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[3];
    Object[] fieldValues = new Object[3];
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      fieldNames[0] = "nombre";
      fieldNames[1] = "padreId";
      fieldNames[2] = "problemaId";
      fieldValues[0] = accion.getNombre();
      fieldValues[1] = accion.getPadreId();
      fieldValues[2] = accion.getProblemaId();
      
      if ((accion.getAccionId() == null) || (accion.getAccionId().longValue() == 0L))
      {
        if (persistenceSession.existsObject(accion, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else
        {
          accion.setAccionId(new Long(persistenceSession.getUniqueId()));
          
          if (accion.getResponsablesAccion() != null)
          {
            for (Iterator i = accion.getResponsablesAccion().iterator(); i.hasNext();)
            {
              ResponsableAccion responsableAccion = (ResponsableAccion)i.next();
              responsableAccion.getPk().setAccionId(accion.getAccionId());
            }
          }
          
          accion.setCreado(new Date());
          accion.setCreadoId(usuario.getUsuarioId());
          resultado = persistenceSession.insert(accion, usuario);
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        idFieldNames[0] = "accionId";
        idFieldValues[0] = accion.getAccionId();
        
        if (validar.booleanValue())
        {
          if (persistenceSession.existsObject(accion, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
            resultado = 10003;
          }
          else {
            accion.setModificado(new Date());
            accion.setModificadoId(usuario.getUsuarioId());
            resultado = persistenceSession.update(accion, usuario);
          }
        }
        else {
          resultado = persistenceSession.update(accion, usuario);
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
  
  private int deleteDependenciasAcciones(Accion accion, Usuario usuario)
  {
    int resultado = 10000;
    ListaMap dependencias = new ListaMap();
    List listaObjetosRelacionados = new ArrayList();
    dependencias = persistenceSession.getDependenciasAcciones(accion);
    
    for (Iterator i = dependencias.iterator(); i.hasNext();) {
      listaObjetosRelacionados = (List)i.next();
      if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Seguimiento)))
      {

        StrategosSeguimientosService strategosSeguimientosService = StrategosServiceFactory.getInstance().openStrategosSeguimientosService(this);
        
        for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();) {
          Seguimiento seguimiento = (Seguimiento)j.next();
          resultado = strategosSeguimientosService.deleteSeguimiento(seguimiento, usuario);
          if (resultado != 10000) {
            break;
          }
        }
        

        strategosSeguimientosService.close();
      }
    }
    return resultado;
  }
  
  public Accion crearAccionRaiz(Long problemaId, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    Accion accion = new Accion();
    Problema problema = (Problema)persistenceSession.load(Problema.class, problemaId);
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      accion.setAccionId(new Long(0L));
      accion.setPadreId(null);
      accion.setProblemaId(problemaId);
      accion.setEstadoId(null);
      accion.setNombre(problema.getNombre());
      accion.setFechaEstimadaInicio(problema.getFechaEstimadaInicio());
      accion.setFechaEstimadaFinal(problema.getFechaEstimadaFinal());
      accion.setFechaRealInicio(problema.getFechaRealInicio());
      accion.setFechaRealFinal(problema.getFechaRealFinal());
      accion.setFrecuencia(new Integer(0));
      accion.setOrden(new Byte("0"));
      accion.setDescripcion(null);
      accion.setReadOnly(new Boolean(false));
      
      resultado = saveAccion(accion, usuario);
      
      if (resultado == 10000) {
        accion = (Accion)persistenceSession.load(Accion.class, accion.getAccionId());
      } else {
        accion = null;
      }
      
      if (transActiva) {
        if ((resultado == 10000) && (accion != null)) {
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
    
    return accion;
  }
  
  public Accion getAccionRaiz(Long problemaId)
  {
    return persistenceSession.getAccionRaiz(problemaId);
  }
}
