package com.visiongc.app.strategos.indicadores.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.indicadores.persistence.StrategosClasesIndicadoresPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrategosClasesIndicadoresServiceImpl
  extends StrategosServiceImpl
  implements StrategosClasesIndicadoresService
{
  private StrategosClasesIndicadoresPersistenceSession persistenceSession = null;
  
  public StrategosClasesIndicadoresServiceImpl(StrategosClasesIndicadoresPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public int deleteClaseIndicadores(ClaseIndicadores clase, Boolean borrarIndicadores, Usuario usuario)
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
      if (clase.getClaseId() != null)
      {
        if (borrarIndicadores.booleanValue()) {
          resultado = deleteDependenciasClasesIndicadores(clase, usuario);
        }
        if (resultado == 10000) {
          resultado = this.persistenceSession.delete(clase, usuario);
        }
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
      resultado = 10004;
      if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    return resultado;
  }
  
  private ClaseIndicadores crearClaseRaiz(Long organizacionId, Byte tipo, Usuario usuario)
  {
    ClaseIndicadores clase = new ClaseIndicadores();
    
    clase.setClaseId(new Long(0L));
    clase.setPadreId(null);
    clase.setNombre(this.messageResources.getResource("claseindicadores.raiz.nombre"));
    clase.setOrganizacionId(organizacionId);
    clase.setDescripcion(null);
    clase.setEnlaceParcial(null);
    clase.setTipo(tipo);
    clase.setVisible(new Boolean(true));
    
    int respuesta = saveClaseIndicadores(clase, usuario);
    if (respuesta == 10000) {
      clase = (ClaseIndicadores)this.persistenceSession.load(ClaseIndicadores.class, clase.getClaseId());
    } else {
      clase = null;
    }
    return clase;
  }
  
  private ClaseIndicadores crearClaseRaizIniciativa(Long organizacionId, Byte tipo, Long padreId, String nombre, Usuario usuario)
  {
    ClaseIndicadores clase = new ClaseIndicadores();
    
    clase.setClaseId(new Long(0L));
    clase.setPadreId(padreId);
    clase.setNombre(nombre);
    clase.setOrganizacionId(organizacionId);
    clase.setDescripcion(null);
    clase.setEnlaceParcial(null);
    clase.setTipo(tipo);
    clase.setVisible(new Boolean(true));
    
    int respuesta = saveClaseIndicadores(clase, usuario);
    if (respuesta == 10000) {
      clase = (ClaseIndicadores)this.persistenceSession.load(ClaseIndicadores.class, clase.getClaseId());
    } else {
      clase = null;
    }
    return clase;
  }
  
  private ClaseIndicadores crearClaseRaizInstrumento(Long organizacionId, Byte tipo, Long padreId, String nombre, Usuario usuario) {

		ClaseIndicadores clase = new ClaseIndicadores();

		clase.setClaseId(new Long(0L));
		clase.setPadreId(padreId);
		clase.setNombre(nombre);
		clase.setOrganizacionId(organizacionId);
		clase.setDescripcion(null);
		clase.setEnlaceParcial(null);
		clase.setTipo(tipo);
		clase.setVisible(new Boolean(false));
		
		int respuesta = saveClaseIndicadores(clase, usuario);
		if (respuesta == 10000) {
			clase = (ClaseIndicadores) this.persistenceSession.load(ClaseIndicadores.class, clase.getClaseId());
		} else {
			clase = null;
		}

		return clase;
	}
  
  private ClaseIndicadores crearClaseRaizPlan(Long organizacionId, Byte tipo, Long padreId, String nombre, Usuario usuario)
  {
    ClaseIndicadores clase = new ClaseIndicadores();
    
    clase.setClaseId(new Long(0L));
    clase.setPadreId(padreId);
    clase.setNombre(nombre);
    clase.setOrganizacionId(organizacionId);
    clase.setDescripcion(null);
    clase.setEnlaceParcial(null);
    clase.setTipo(tipo);
    clase.setVisible(new Boolean(false));
    
    int respuesta = saveClaseIndicadores(clase, usuario);
    if (respuesta == 10000) {
      clase = (ClaseIndicadores)this.persistenceSession.load(ClaseIndicadores.class, clase.getClaseId());
    } else {
      clase = null;
    }
    return clase;
  }
  
  public ClaseIndicadores crearClaseRaiz(Long organizacionId, Usuario usuario)
  {
    return crearClaseRaiz(organizacionId, TipoClaseIndicadores.getTipoClaseIndicadores(), usuario);
  }
  
  public int saveClaseIndicadores(ClaseIndicadores clase, Usuario usuario)
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
      fieldNames[0] = "nombre";
      fieldNames[1] = "padreId";
      fieldNames[2] = "organizacionId";
      fieldValues[0] = clase.getNombre();
      fieldValues[1] = clase.getPadreId();
      fieldValues[2] = clase.getOrganizacionId();
      if ((clase.getClaseId() == null) || (clase.getClaseId().longValue() == 0L))
      {    	      	
        if (this.persistenceSession.existsObject(clase, fieldNames, fieldValues))
        {
          resultado = 10003;
        }
        else
        {        	
          clase.setClaseId(new Long(this.persistenceSession.getUniqueId()));
          
          Date fechaInsercionIniciativa = new Date();
          clase.setCreado(new Date(fechaInsercionIniciativa.getTime()));
          clase.setCreadoId(usuario.getUsuarioId());                              
          resultado = this.persistenceSession.insert(clase, usuario);
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "claseId";
        idFieldValues[0] = clase.getClaseId();
        if (this.persistenceSession.existsObject(clase, fieldNames, fieldValues, idFieldNames, idFieldValues))
        {
          resultado = 10003;
        }
        else
        {
          Date fechaModificacionIniciativa = new Date();
          clase.setModificado(new Date(fechaModificacionIniciativa.getTime()));
          clase.setModificadoId(usuario.getUsuarioId());
          
          resultado = this.persistenceSession.update(clase, usuario);
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
  
  public List getArbolCompletoClaseIndicadores(Long claseIndicadorId)
  {
    List arbol = new ArrayList();
    ClaseIndicadores claseIndicadoresActual = (ClaseIndicadores)load(ClaseIndicadores.class, claseIndicadorId);
    if (claseIndicadoresActual != null)
    {
      arbol.add(claseIndicadoresActual);
      Set<?> hijos = claseIndicadoresActual.getHijos();
      for (Iterator<?> i = hijos.iterator(); i.hasNext();)
      {
        ClaseIndicadores hijo = (ClaseIndicadores)i.next();
        getArbolCompletoClaseIndicadoresInterno(hijo.getClaseId(), arbol);
      }
    }
    return arbol;
  }
  
  private void getArbolCompletoClaseIndicadoresInterno(Long claseIndicadorId, List<ClaseIndicadores> arbol)
  {
    ClaseIndicadores claseIndicadoresActual = (ClaseIndicadores)load(ClaseIndicadores.class, claseIndicadorId);
    if (claseIndicadoresActual != null)
    {
      arbol.add(claseIndicadoresActual);
      Set<?> hijos = claseIndicadoresActual.getHijos();
      for (Iterator<?> i = hijos.iterator(); i.hasNext();)
      {
        ClaseIndicadores hijo = (ClaseIndicadores)i.next();
        getArbolCompletoClaseIndicadoresInterno(hijo.getClaseId(), arbol);
      }
    }
  }
  
  private int deleteDependenciasClasesIndicadores(ClaseIndicadores claseIndicadores, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    ListaMap dependencias = new ListaMap();
    List<?> listaObjetosRelacionados = new ArrayList();
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      dependencias = this.persistenceSession.getDependenciasClasesIndicadores(claseIndicadores);
      for (Iterator<?> i = dependencias.iterator(); i.hasNext();)
      {
        listaObjetosRelacionados = (List)i.next();
        if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Indicador)))
        {
          StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
          for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            Indicador indicador = (Indicador)j.next();
            
            resultado = strategosIndicadoresService.deleteIndicador(indicador, usuario);
            if (resultado != 10000) {
              break;
            }
          }
          strategosIndicadoresService.close();
        }
        else
        {
          for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            Object objeto = j.next();
            
            resultado = this.persistenceSession.delete(objeto, usuario);
            if (resultado != 10000) {
              break;
            }
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
      resultado = 10004;
      if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    return resultado;
  }
  
  public String getRutaCompletaNombresClaseIndicadores(Long claseIndicadoresId, String separador)
  {
    ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService(this);
    
    ClaseIndicadores claseIndicadores = (ClaseIndicadores)load(ClaseIndicadores.class, claseIndicadoresId);
    String resultado = arbolesService.getRutaCompletaNombres(claseIndicadores, separador);
    arbolesService.close();
    
    return resultado;
  }
  
  public ClaseIndicadores getClaseRaiz(Long organizacionId, Byte tipo, Usuario usuario)
  {
    Map<String, Object> filtros = new HashMap();
    
    filtros.put("organizacionId", organizacionId.toString());
    filtros.put("tipo", tipo.toString());
    filtros.put("padreId", null);
    
    ClaseIndicadores claseRaiz = null;
    
    List<ClaseIndicadores> clases = this.persistenceSession.getClases(null, null, filtros);
    if (clases.size() == 0) {
      claseRaiz = crearClaseRaiz(organizacionId, usuario);
    } else if (clases.size() == 1) {
      claseRaiz = (ClaseIndicadores)clases.get(0);
    }
    return claseRaiz;
  }
  
  public ClaseIndicadores getClaseRaizPlan(Long organizacionId, Byte tipo, String nombre, Usuario usuario)
  {
    ClaseIndicadores claseRoot = getClaseRaiz(organizacionId, TipoClaseIndicadores.getTipoClaseIndicadores(), usuario);
    
    Map<String, Object> filtros = new HashMap();
    
    filtros.put("organizacionId", organizacionId.toString());
    filtros.put("padreId", claseRoot.getClaseId());
    filtros.put("nombre", nombre);
    
    ClaseIndicadores claseRaizPlan = null;
    
    List<ClaseIndicadores> clases = this.persistenceSession.getClases(null, null, filtros);
    if (clases.size() == 0) {
      claseRaizPlan = crearClaseRaizPlan(organizacionId, tipo, claseRoot.getClaseId(), nombre, usuario);
    } else if (clases.size() == 1) {
      claseRaizPlan = (ClaseIndicadores)clases.get(0);
    }
    return claseRaizPlan;
  }
  
  public ClaseIndicadores getClaseRaizIniciativa(Long organizacionId, Byte tipo, String nombre, Usuario usuario)
  {
    ClaseIndicadores claseRoot = getClaseRaiz(organizacionId, TipoClaseIndicadores.getTipoClaseIndicadores(), usuario);
    
    Map<String, Object> filtros = new HashMap();
    
    filtros.put("organizacionId", organizacionId.toString());
    filtros.put("padreId", claseRoot.getClaseId());
    filtros.put("nombre", nombre);
    
    ClaseIndicadores claseRaizIniciativa = null;
    
    List<ClaseIndicadores> clases = this.persistenceSession.getClases(null, null, filtros);
    if (clases.size() == 0) {
      claseRaizIniciativa = crearClaseRaizIniciativa(organizacionId, tipo, claseRoot.getClaseId(), nombre, usuario);
    } else if (clases.size() == 1) {
      claseRaizIniciativa = (ClaseIndicadores)clases.get(0);
    }
    return claseRaizIniciativa;
  }
  
  public ClaseIndicadores getClaseRaizInstrumento(Long organizacionId, Byte tipo, String nombre, Usuario usuario) {		
		
		ClaseIndicadores claseRoot = getClaseRaiz(organizacionId, TipoClaseIndicadores.getTipoClaseIndicadores(),
				usuario);
		
		Map<String, Object> filtros = new HashMap();
		
		filtros.put("organizacionId", organizacionId.toString());
		filtros.put("padreId", claseRoot.getClaseId());
		filtros.put("nombre", nombre);
		
		ClaseIndicadores claseRaizInstrumento = null;
		
		List<ClaseIndicadores> clases = this.persistenceSession.getClases(null, null, filtros);
		if (clases.size() == 0) {
			claseRaizInstrumento = crearClaseRaizInstrumento(organizacionId, tipo, claseRoot.getClaseId(), nombre, usuario);			
		} else if (clases.size() == 1) {
			claseRaizInstrumento = (ClaseIndicadores) clases.get(0);
		}
		
		return claseRaizInstrumento;
	}
  
  public List<ClaseIndicadores> getClasesHijas(Long clasePadreId, Boolean visible)
  {
    Map<String, Object> filtros = new HashMap();
    
    filtros.put("padreId", clasePadreId.toString());
    if ((visible != null) && (visible.booleanValue())) {
      filtros.put("visible", visible);
    }
    String[] orden = new String[1];
    String[] tipoOrden = new String[1];
    orden[0] = "nombre";
    tipoOrden[0] = "asc";
    
    return this.persistenceSession.getClases(orden, tipoOrden, filtros);
  }
  
  public List<ClaseIndicadores> getClases(Map<String, Object> filtros)
  {
    return this.persistenceSession.getClases(null, null, filtros);
  }
}
