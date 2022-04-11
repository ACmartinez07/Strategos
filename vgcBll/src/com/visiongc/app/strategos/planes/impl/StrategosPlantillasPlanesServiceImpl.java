package com.visiongc.app.strategos.planes.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.planes.StrategosPlantillasPlanesService;
import com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.persistence.StrategosPlantillasPlanesPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrategosPlantillasPlanesServiceImpl
  extends StrategosServiceImpl implements StrategosPlantillasPlanesService
{
  private StrategosPlantillasPlanesPersistenceSession persistenceSession = null;
  
  public StrategosPlantillasPlanesServiceImpl(StrategosPlantillasPlanesPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getPlantillasPlanes(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Map filtroLocal = new HashMap();
    filtroLocal.put("nombre", messageResources.getResource("balancedscoreCard.nombre"));
    PaginaLista paginaPlantillas = persistenceSession.getPlantillasPlanes(0, 0, null, null, true, filtroLocal);
    if ((paginaPlantillas.getLista() == null) || (paginaPlantillas.getLista().size() < 1)) {
      crearPlantillaPlanBalancedScoreCard();
    }
    
    return persistenceSession.getPlantillasPlanes(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public int deletePlantillaPlanes(PlantillaPlanes plantillaPlanes, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (plantillaPlanes.getPlantillaPlanesId() != null)
      {
        resultado = persistenceSession.delete(plantillaPlanes, usuario);
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
  
  public int savePlantillaPlanes(PlantillaPlanes plantillaPlanes, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[1];
    Object[] fieldValues = new Object[1];
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      fieldNames[0] = "nombre";
      fieldValues[0] = plantillaPlanes.getNombre();
      
      if ((plantillaPlanes.getPlantillaPlanesId() == null) || (plantillaPlanes.getPlantillaPlanesId().longValue() == 0L)) {
        if (persistenceSession.existsObject(plantillaPlanes, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          plantillaPlanes.setPlantillaPlanesId(new Long(persistenceSession.getUniqueId()));
          plantillaPlanes.setCreado(new Date());
          plantillaPlanes.setCreadoId(usuario.getUsuarioId());
          
          for (Iterator k = plantillaPlanes.getElementos().iterator(); k.hasNext();) {
            ElementoPlantillaPlanes elementoPlantillaPlanes = (ElementoPlantillaPlanes)k.next();
            elementoPlantillaPlanes.setElementoId(new Long(persistenceSession.getUniqueId()));
            elementoPlantillaPlanes.setPlantillaPlanesId(plantillaPlanes.getPlantillaPlanesId());
          }
          
          resultado = persistenceSession.insert(plantillaPlanes, usuario);
        }
      } else {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "plantillaPlanesId";
        idFieldValues[0] = plantillaPlanes.getPlantillaPlanesId();
        
        if (persistenceSession.existsObject(plantillaPlanes, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          plantillaPlanes.setModificado(new Date());
          plantillaPlanes.setModificadoId(usuario.getUsuarioId());
          resultado = persistenceSession.update(plantillaPlanes, usuario);
        }
      }
      

      if (transActiva) {
        persistenceSession.commitTransaction();
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
  
  public List getNivelesPlantillaPlan(Long plantillaPlanesId)
  {
    List niveles = persistenceSession.getNivelesPlantillaPlan(plantillaPlanesId);
    
    return niveles;
  }
  
  private PlantillaPlanes crearPlantillaPlanBalancedScoreCard()
  {
    boolean transActiva = false;
    int resultado = 10000;
    PlantillaPlanes plantillaPlanes = new PlantillaPlanes();
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      plantillaPlanes.setPlantillaPlanesId(new Long(0L));
      plantillaPlanes.setNombre(messageResources.getResource("balancedscoreCard.nombre"));
      plantillaPlanes.setNombreIndicadorSingular(messageResources.getResource("balancedscoreCard.indicador"));
      plantillaPlanes.setNombreIniciativaSingular(messageResources.getResource("balancedscoreCard.iniciativa"));
      plantillaPlanes.setNombreActividadSingular(messageResources.getResource("balancedscoreCard.actividad"));
      
      plantillaPlanes.setElementos(new HashSet());
      ElementoPlantillaPlanes elementoPlantillaPlanes = new ElementoPlantillaPlanes();
      
      elementoPlantillaPlanes = new ElementoPlantillaPlanes();
      elementoPlantillaPlanes.setElementoId(new Long(0L));
      elementoPlantillaPlanes.setOrden(new Integer(3));
      elementoPlantillaPlanes.setTipo(new Byte("1"));
      elementoPlantillaPlanes.setNombre(messageResources.getResource("balancedscoreCard.niveles.tercero"));
      plantillaPlanes.getElementos().add(elementoPlantillaPlanes);
      
      elementoPlantillaPlanes = new ElementoPlantillaPlanes();
      elementoPlantillaPlanes.setElementoId(new Long(0L));
      elementoPlantillaPlanes.setOrden(new Integer(2));
      elementoPlantillaPlanes.setTipo(new Byte("0"));
      elementoPlantillaPlanes.setNombre(messageResources.getResource("balancedscoreCard.niveles.segundo"));
      plantillaPlanes.getElementos().add(elementoPlantillaPlanes);
      
      elementoPlantillaPlanes = new ElementoPlantillaPlanes();
      elementoPlantillaPlanes.setElementoId(new Long(0L));
      elementoPlantillaPlanes.setOrden(new Integer(1));
      elementoPlantillaPlanes.setTipo(new Byte("0"));
      elementoPlantillaPlanes.setNombre(messageResources.getResource("balancedscoreCard.niveles.primero"));
      plantillaPlanes.getElementos().add(elementoPlantillaPlanes);
      
      Usuario usuario = new Usuario();
      
      resultado = savePlantillaPlanes(plantillaPlanes, usuario);
      
      if (resultado == 10000) {
        plantillaPlanes = (PlantillaPlanes)persistenceSession.load(PlantillaPlanes.class, plantillaPlanes.getPlantillaPlanesId());
      } else {
        plantillaPlanes = null;
      }
      
      if (transActiva) {
        if ((resultado == 10000) && (plantillaPlanes != null)) {
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
    
    return plantillaPlanes;
  }
}
