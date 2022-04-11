package com.visiongc.app.strategos.organizaciones.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion;
import com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.organizaciones.persistence.StrategosOrganizacionesPersistenceSession;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.presentaciones.StrategosVistasService;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.problemas.StrategosClasesProblemasService;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrategosOrganizacionesServiceImpl
  extends StrategosServiceImpl
  implements StrategosOrganizacionesService
{
  private StrategosOrganizacionesPersistenceSession persistenceSession = null;
  
  public StrategosOrganizacionesServiceImpl(StrategosOrganizacionesPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public int deleteOrganizacion(OrganizacionStrategos organizacion, Usuario usuario)
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
      
      if (organizacion.getOrganizacionId() != null)
      {
        resultado = deleteDependenciasOrganizaciones(organizacion, usuario);
        
        if (resultado == 10000) {
          resultado = persistenceSession.delete(organizacion, usuario);
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
  
  public int saveOrganizacion(OrganizacionStrategos organizacion, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[2];
    Object[] fieldValues = new Object[2];
    loadDependencias(organizacion);
    
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      fieldNames[0] = "nombre";
      fieldNames[1] = "padreId";
      fieldValues[0] = organizacion.getNombre();
      fieldValues[1] = organizacion.getPadreId();
      
      if ((organizacion.getOrganizacionId() == null) || (organizacion.getOrganizacionId().longValue() == 0L))
      {
        if (persistenceSession.existsObject(organizacion, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          organizacion.setOrganizacionId(new Long(persistenceSession.getUniqueId()));
          for (Iterator iter = organizacion.getMemos().iterator(); iter.hasNext();)
          {
            MemoOrganizacion memo = (MemoOrganizacion)iter.next();
            memo.getPk().setOrganizacionId(organizacion.getOrganizacionId());
          }
          organizacion.setCreado(new Date());
          organizacion.setCreadoId(usuario.getUsuarioId());
          resultado = persistenceSession.insert(organizacion, usuario);
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "organizacionId";
        idFieldValues[0] = organizacion.getOrganizacionId();
        if (persistenceSession.existsObject(organizacion, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        }
        else {
          organizacion.setModificado(new Date());
          organizacion.setModificadoId(usuario.getUsuarioId());
          resultado = persistenceSession.update(organizacion, usuario);
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
  
  private int deleteDependenciasOrganizaciones(OrganizacionStrategos organizacion, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    ListaMap dependencias = new ListaMap();
    List listaObjetosRelacionados = new ArrayList();
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService(this);
      frameworkService.deleteDependenciasOrganizacionFramework(organizacion.getOrganizacionId(), usuario);
      frameworkService.close();
      
      dependencias = persistenceSession.getDependenciasOrganizaciones(organizacion);
      
      for (Iterator i = dependencias.iterator(); i.hasNext();)
      {
        listaObjetosRelacionados = (List)i.next();
        
        if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof ClaseProblemas)))
        {
          StrategosClasesProblemasService strategosClasesProblemasService = StrategosServiceFactory.getInstance().openStrategosClasesProblemasService(this);
          
          for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            ClaseProblemas claseProblemas = (ClaseProblemas)j.next();
            
            resultado = strategosClasesProblemasService.deleteClaseProblema(claseProblemas, usuario);
            
            if (resultado != 10000)
              break;
          }
          strategosClasesProblemasService.close();
        }
        else if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Plan)))
        {
          StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(this);
          
          for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            Plan plan = (Plan)j.next();
            
            resultado = strategosPlanesService.deletePlan(plan, usuario, Boolean.valueOf(true));
            
            if (resultado != 10000)
              break;
          }
          strategosPlanesService.close();
        }
        else if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Vista)))
        {
          StrategosVistasService strategosVistasService = StrategosServiceFactory.getInstance().openStrategosVistasService(this);
          
          for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            Vista vista = (Vista)j.next();
            
            resultado = strategosVistasService.deleteVista(vista, usuario);
            
            if (resultado != 10000)
              break;
          }
          strategosVistasService.close();
        }
        else if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Iniciativa)))
        {
          StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(this);
          
          for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            Iniciativa iniciativa = (Iniciativa)j.next();
            
            resultado = strategosIniciativasService.deleteIniciativa(iniciativa, usuario);
            
            if (resultado != 10000)
              break;
          }
          strategosIniciativasService.close();
        }
        else if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof ClaseIndicadores)))
        {
          StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService(this);
          
          for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            ClaseIndicadores claseIndicadores = (ClaseIndicadores)j.next();
            
            resultado = strategosClasesIndicadoresService.deleteClaseIndicadores(claseIndicadores, Boolean.valueOf(true), usuario);
            
            if (resultado != 10000)
              break;
          }
          strategosClasesIndicadoresService.close();
        }
        else if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Responsable)))
        {
          StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService(this);
          
          for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            Responsable responsable = (Responsable)j.next();
            
            resultado = strategosResponsablesService.deleteResponsable(responsable, usuario);
            
            if (resultado != 10000)
              break;
          }
          strategosResponsablesService.close();
        }
        else if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Indicador)))
        {
          StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
          
          for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            Indicador indicador = (Indicador)j.next();
            
            resultado = strategosIndicadoresService.deleteIndicador(indicador, usuario);
            
            if (resultado != 10000)
              break;
          }
          strategosIndicadoresService.close();
        }
        else if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Portafolio)))
        {
          StrategosPortafoliosService strategosPortafoliosService = StrategosServiceFactory.getInstance().openStrategosPortafoliosService(this);
          
          for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            Portafolio portafolio = (Portafolio)j.next();
            
            resultado = strategosPortafoliosService.delete(portafolio, usuario);
            
            if (resultado != 10000)
              break;
          }
          strategosPortafoliosService.close();
        }
        else if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof Problema)))
        {
          StrategosProblemasService strategosProblemasService = StrategosServiceFactory.getInstance().openStrategosProblemasService(this);
          
          for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            Problema problema = (Problema)j.next();
            
            resultado = strategosProblemasService.deleteProblema(problema, usuario);
            
            if (resultado != 10000)
              break;
          }
          strategosProblemasService.close();


        }
        else
        {

          for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();)
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
  
  public List getArbolCompletoOrganizacion(Long organizacionId)
  {
    List arbolCompleto = new ArrayList();
    
    getArbolCompletoOrganizacionAux(organizacionId, arbolCompleto);
    
    return arbolCompleto;
  }
  
  private void getArbolCompletoOrganizacionAux(Long organizacionId, List arbol)
  {
    Organizacion organizacionActual = (Organizacion)load(Organizacion.class, organizacionId);
    
    if (organizacionActual != null)
    {
      arbol.add(organizacionActual);
      Set Hijos = organizacionActual.getHijos();
      for (Iterator i = Hijos.iterator(); i.hasNext();)
      {
        Organizacion hijo = (Organizacion)i.next();
        getArbolCompletoOrganizacionAux(hijo.getOrganizacionId(), arbol);
      }
    }
  }
  
  public String getRutaCompletaNombresOrganizacion(Long organizacionId, String separador)
  {
    ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService(this);
    
    OrganizacionStrategos organizacion = (OrganizacionStrategos)load(OrganizacionStrategos.class, organizacionId);
    String resultado = arbolesService.getRutaCompletaNombres(organizacion, separador);
    
    arbolesService.close();
    
    return resultado;
  }
  
  public OrganizacionStrategos crearOrganizacionRaiz(Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    OrganizacionStrategos organizacion = new OrganizacionStrategos();
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      organizacion.setOrganizacionId(new Long(0L));
      organizacion.setPadreId(null);
      organizacion.setNombre(messageResources.getResource("organizacion.raiz.nombre"));
      organizacion.setVisible(new Boolean(true));
      organizacion.setMesCierre(new Byte("12"));
      organizacion.setPorcentajeZonaAmarillaIniciativas(null);
      organizacion.setPorcentajeZonaAmarillaMetaIndicadores(null);
      organizacion.setPorcentajeZonaAmarillaMinMaxIndicadores(null);
      organizacion.setPorcentajeZonaVerdeIniciativas(null);
      organizacion.setPorcentajeZonaVerdeMetaIndicadores(null);
      organizacion.setMemos(new HashSet());
      
      resultado = saveOrganizacion(organizacion, usuario);
      
      if (resultado == 10000) {
        organizacion = (OrganizacionStrategos)persistenceSession.load(OrganizacionStrategos.class, organizacion.getOrganizacionId());
      } else {
        organizacion = null;
      }
      if (transActiva)
      {
        if ((resultado == 10000) && (organizacion != null)) {
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
    
    return organizacion;
  }
  
  public PaginaLista getOrganizaciones(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    return persistenceSession.getOrganizaciones(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public List getOrganizacionHijas(Long organizacionPadreId, boolean soloVisibles)
  {
    Map filtros = new HashMap();
    
    filtros.put("padreId", organizacionPadreId.toString());
    
    if (soloVisibles) {
      filtros.put("visible", Boolean.valueOf(true));
    }
    String[] orden = new String[1];
    String[] tipoOrden = new String[1];
    orden[0] = "nombre";
    
    tipoOrden[0] = "asc";
    
    return persistenceSession.getOrganizaciones(orden, tipoOrden, filtros);
  }
  
  public void loadDependencias(OrganizacionStrategos organizacion)
  {
    if ((organizacion.getPadreId() != null) && (!organizacion.getPadreId().equals(Long.valueOf("0"))))
    {
      StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
      OrganizacionStrategos organizacionStrategosPadre = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, organizacion.getPadreId());
      strategosOrganizacionesService.close();
      organizacion.setPadre(organizacionStrategosPadre);
    }
  }
}
