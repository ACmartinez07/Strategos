package com.visiongc.app.strategos.responsables.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.problemas.StrategosAccionesService;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.problemas.model.ResponsableAccion;
import com.visiongc.app.strategos.problemas.model.ResponsableAccionPK;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.responsables.model.util.ConfiguracionResponsable;
import com.visiongc.app.strategos.responsables.persistence.StrategosResponsablesPersistenceSession;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.Usuario;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class StrategosResponsablesServiceImpl
  extends StrategosServiceImpl
  implements StrategosResponsablesService
{
  private StrategosResponsablesPersistenceSession persistenceSession = null;
  
  public StrategosResponsablesServiceImpl(StrategosResponsablesPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getResponsables(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    String[] arregloOrden = (String[])null;
    String[] arregloTipoOrden = (String[])null;
    
    if ((orden != null) && (tipoOrden != null))
    {
      arregloOrden = new String[1];
      arregloTipoOrden = new String[1];
      
      arregloOrden[0] = orden;
      arregloTipoOrden[0] = tipoOrden;
    }
    
    return persistenceSession.getResponsables(pagina, tamanoPagina, arregloOrden, arregloTipoOrden, getTotal, filtros);
  }
  
  public PaginaLista getResponsables(int pagina, int tamanoPagina, String[] orden, String[] tipoOrden, boolean getTotal, Map filtros)
  {
    return persistenceSession.getResponsables(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public int deleteResponsable(Responsable responsable, Usuario usuario)
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
      
      if (responsable.getResponsableId() != null)
      {
        resultado = deleteDependencias(responsable, usuario);
        if (resultado == 10000) {
          resultado = persistenceSession.delete(responsable, usuario);
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
  
  private int deleteDependencias(Responsable responsable, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    ListaMap dependencias = new ListaMap();
    List<?> listaObjetosRelacionados = null;
    int index = -1;
    
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
    StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(this);
    StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService(this);
    StrategosProblemasService strategosProblemasService = StrategosServiceFactory.getInstance().openStrategosProblemasService(this);
    StrategosAccionesService strategosAccionesService = StrategosServiceFactory.getInstance().openStrategosAccionesService(this);
    StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService(this);
    
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      dependencias = persistenceSession.getDependencias(responsable);
      for (Iterator<?> i = dependencias.iterator(); i.hasNext();)
      {
        listaObjetosRelacionados = (List)i.next();
        index++;
        
        for (Iterator<?> j = listaObjetosRelacionados.iterator(); j.hasNext();)
        {
          Object objeto = j.next();
          if ((objeto instanceof Indicador))
          {
            String responsableCampo = dependencias.getKey(index).replace("Indicador", "");
            
            Indicador indicador = (Indicador)objeto;
            Map<String, Object> filtros = new HashMap();
            Long responsableId = null;
            filtros.put(responsableCampo, responsableId);
            
            resultado = strategosIndicadoresService.updateCampo(indicador.getIndicadorId(), filtros);
            if (resultado != 10000) {
              break;
            }
          } else if ((objeto instanceof Iniciativa))
          {
            String responsableCampo = dependencias.getKey(index).replace("Iniciativa", "");
            
            Iniciativa iniciativa = (Iniciativa)objeto;
            Map<String, Object> filtros = new HashMap();
            Long responsableId = null;
            filtros.put(responsableCampo, responsableId);
            
            resultado = strategosIniciativasService.updateCampo(iniciativa.getIniciativaId(), filtros);
            if (resultado != 10000) {
              break;
            }
          } else if ((objeto instanceof PryActividad))
          {
            String responsableCampo = dependencias.getKey(index).replace("Actividad", "");
            
            PryActividad actividad = (PryActividad)objeto;
            Map<String, Object> filtros = new HashMap();
            Long responsableId = null;
            filtros.put(responsableCampo, responsableId);
            
            resultado = strategosPryActividadesService.updateCampo(actividad.getActividadId(), filtros);
            if (resultado != 10000) {
              break;
            }
          } else if ((objeto instanceof Problema))
          {
            String responsableCampo = dependencias.getKey(index).replace("Problema", "");
            
            Problema problema = (Problema)objeto;
            Map<String, Object> filtros = new HashMap();
            Long responsableId = null;
            filtros.put(responsableCampo, responsableId);
            
            resultado = strategosProblemasService.updateCampo(problema.getProblemaId(), filtros);
            if (resultado != 10000) {
              break;
            }
          } else if ((objeto instanceof ResponsableAccion))
          {
            ResponsableAccion accionResponsable = (ResponsableAccion)objeto;
            Accion accion = (Accion)strategosAccionesService.load(Accion.class, accionResponsable.getPk().getAccionId());
            
            List<ResponsableAccion> responsablesAcciones = new ArrayList();
            for (Iterator<?> k = accion.getResponsablesAccion().iterator(); k.hasNext();)
            {
              ResponsableAccion responsableAccion = (ResponsableAccion)k.next();
              if (responsableAccion.getPk().getResponsableId().longValue() == responsable.getResponsableId().longValue()) {
                responsablesAcciones.add(responsableAccion);
              }
            }
            for (Iterator<ResponsableAccion> k = responsablesAcciones.iterator(); k.hasNext();)
            {
              ResponsableAccion responsableAccion = (ResponsableAccion)k.next();
              accion.getResponsablesAccion().remove(responsableAccion);
            }
            
            resultado = strategosAccionesService.saveAccion(accion, usuario, Boolean.valueOf(false));
            if (resultado != 10000) {
              break;
            }
          } else if ((objeto instanceof Perspectiva))
          {
            String responsableCampo = dependencias.getKey(index).replace("Perspectiva", "");
            
            Perspectiva perspectiva = (Perspectiva)objeto;
            Map<String, Object> filtros = new HashMap();
            Long responsableId = null;
            filtros.put(responsableCampo, responsableId);
            
            resultado = strategosPerspectivasService.updateCampo(perspectiva.getPerspectivaId(), filtros);
            if (resultado != 10000) {
              break;
            }
          }
        }
        if (resultado != 10000) {
          break;
        }
      }
      strategosPerspectivasService.close();
      strategosIndicadoresService.close();
      strategosProblemasService.close();
      strategosPryActividadesService.close();
      strategosIniciativasService.close();
      strategosAccionesService.close();
      
      if (resultado == 10000)
      {
        if (transActiva)
        {
          persistenceSession.commitTransaction();
          transActiva = false;
          return resultado;
        }
      }
      
      if (transActiva)
      {
        persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      strategosIndicadoresService.close();
      strategosProblemasService.close();
      strategosPryActividadesService.close();
      strategosIniciativasService.close();
      strategosAccionesService.close();
      
      resultado = 10001;
      if (transActiva)
      {
        persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    
    return resultado;
  }
  
  public int saveResponsable(Responsable responsable, Usuario usuario)
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
      fieldValues[0] = responsable.getNombre();
      fieldNames[1] = "cargo";
      fieldValues[1] = responsable.getCargo();
      fieldNames[2] = "organizacionId";
      fieldValues[2] = responsable.getOrganizacionId();
      
      if (responsable.getResponsables() != null)
      {
        if (responsable.getResponsables().size() > 0) {
          responsable.setEsGrupo(new Boolean(true));
        } else {
          responsable.setEsGrupo(new Boolean(false));
        }
      } else {
        responsable.setEsGrupo(new Boolean(false));
      }
      if ((responsable.getResponsableId() == null) || (responsable.getResponsableId().longValue() == 0L))
      {
        if (persistenceSession.existsObject(responsable, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          responsable.setResponsableId(new Long(persistenceSession.getUniqueId()));
          resultado = persistenceSession.insert(responsable, usuario);
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "responsableId";
        idFieldValues[0] = responsable.getResponsableId();
        
        if (persistenceSession.existsObject(responsable, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          resultado = persistenceSession.update(responsable, usuario);
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
  
  public int guardarGrupoResponsables(long responsableId, long[] asociadoId, Usuario usuario)
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
      
      Responsable responsable = (Responsable)persistenceSession.load(Responsable.class, new Long(responsableId));
      Set grupo = responsable.getResponsables();
      grupo.clear();
      
      for (int i = 0; i < asociadoId.length; i++)
      {
        Responsable asociado = new Responsable();
        asociado.setResponsableId(new Long(asociadoId[i]));
        grupo.add(asociado);
      }
      
      if (responsable.getResponsables().size() > 0) {
        responsable.setEsGrupo(new Boolean(true));
      } else {
        responsable.setEsGrupo(new Boolean(false));
      }
      resultado = persistenceSession.update(responsable, usuario);
      
      if (transActiva)
      {
        persistenceSession.commitTransaction();
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
  
  public List getResponsablesAsociables(boolean global, long organizacionId, Set responsablesAsociados, Responsable responsableSeleccionado)
  {
    List responsablesAsociables = persistenceSession.getResponsablesAsociables(global, organizacionId);
    
    for (Iterator i = responsablesAsociados.iterator(); i.hasNext();)
    {
      Responsable responsableAsociado = (Responsable)i.next();
      responsablesAsociables.remove(responsableAsociado);
    }
    
    responsablesAsociables.remove(responsableSeleccionado);
    
    return responsablesAsociables;
  }
  
  public ConfiguracionResponsable getConfiguracionResponsable()
  {
    ConfiguracionResponsable configuracionResponsable = new ConfiguracionResponsable();
    try
    {
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
      Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Configuracion.Responsable");
      frameworkService.close();
      
      if (configuracion != null)
      {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("properties");
        Element eElement = (Element)nList.item(0);
        
        configuracionResponsable.setEnviarResponsableFijarMeta(Boolean.valueOf(VgcAbstractService.getTagValue("fijarMeta", eElement).equals("1")));
        configuracionResponsable.setEnviarResponsableLograrMeta(Boolean.valueOf(VgcAbstractService.getTagValue("lograrMeta", eElement).equals("1")));
        configuracionResponsable.setEnviarResponsableSeguimiento(Boolean.valueOf(VgcAbstractService.getTagValue("seguimiento", eElement).equals("1")));
        configuracionResponsable.setEnviarResponsableCargarMeta(Boolean.valueOf(VgcAbstractService.getTagValue("cargarMeta", eElement).equals("1")));
        configuracionResponsable.setEnviarResponsableCargarEjecutado(Boolean.valueOf(VgcAbstractService.getTagValue("cargarEjecutado", eElement).equals("1")));
      }
      else
      {
        configuracionResponsable.setEnviarResponsableFijarMeta(Boolean.valueOf(true));
        configuracionResponsable.setEnviarResponsableLograrMeta(Boolean.valueOf(true));
        configuracionResponsable.setEnviarResponsableSeguimiento(Boolean.valueOf(true));
        configuracionResponsable.setEnviarResponsableCargarMeta(Boolean.valueOf(true));
        configuracionResponsable.setEnviarResponsableCargarEjecutado(Boolean.valueOf(true));
      }
    }
    catch (Exception localException) {}
    


    return configuracionResponsable;
  }
  
  public Byte getTipoCorreoPorDefectoSent(Long usuarioId)
  {
    Byte tipoCorreoDefaultSent = null;
    try
    {
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
      ConfiguracionUsuario configuracion = frameworkService.getConfiguracionUsuario(usuarioId, "Strategos.Configuracion.TipoCorreoPorDefectoSent", "TipoCorreo");
      frameworkService.close();
      
      if (configuracion != null)
      {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(configuracion.getData().getBytes("UTF-8")));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("properties");
        Element eElement = (Element)nList.item(0);
        
        tipoCorreoDefaultSent = Byte.valueOf(Byte.parseByte(VgcAbstractService.getTagValue("tipoDefaultSentEmail", eElement)));
      }
      else
      {
        tipoCorreoDefaultSent = Byte.valueOf((byte)1);
      }
    }
    catch (Exception e)
    {
      tipoCorreoDefaultSent = Byte.valueOf((byte)1);
    }
    
    return tipoCorreoDefaultSent;
  }
}
