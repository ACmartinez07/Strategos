package com.visiongc.app.strategos.planificacionseguimiento.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryInformacionActividad;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPryActividadesPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrategosPryActividadesServiceImpl extends StrategosServiceImpl implements com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService
{
  private StrategosPryActividadesPersistenceSession persistenceSession = null;
  
  public StrategosPryActividadesServiceImpl(StrategosPryActividadesPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getActividades(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    return persistenceSession.getActividades(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  private int deleteDependenciasActividad(PryActividad pryActividad, Usuario usuario, Boolean eliminarDependenciasSuperiores)
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
      
      dependencias = persistenceSession.getDependenciasActividad(pryActividad);
      
      for (Iterator i = dependencias.iterator(); i.hasNext();)
      {
        listaObjetosRelacionados = (List)i.next();
        
        if ((listaObjetosRelacionados.size() > 0) && ((listaObjetosRelacionados.get(0) instanceof PryActividad)))
        {
          for (Iterator j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            PryActividad actividadHija = (PryActividad)j.next();
            
            resultado = deleteActividad(actividadHija, usuario);
            
            if (resultado != 10000) {
              break;
            }
            
          }
          
        }
        else {
          for (Iterator<Object> j = listaObjetosRelacionados.iterator(); j.hasNext();)
          {
            Object objeto = j.next();
            if (eliminarDependenciasSuperiores.booleanValue()) {
              resultado = persistenceSession.delete(objeto, usuario);
            } else if ((objeto.getClass().getName().indexOf("ClaseIndicadores") == -1) && (!eliminarDependenciasSuperiores.booleanValue())) {
              resultado = persistenceSession.delete(objeto, usuario);
            }
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
        if (transActiva) {
          persistenceSession.commitTransaction();
          transActiva = false;
          return resultado;
        }
      }
      

      if (transActiva) {
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
  
  public int deleteActividad(PryActividad actividad, Usuario usuario)
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
      
      if (actividad.getActividadId() != null)
      {
        if (actividad.getObjetoAsociadoId() != null)
        {
          actividad.setIndicadorId(null);
          actividad.setClaseId(null);
          resultado = saveActividad(actividad, usuario, Boolean.valueOf(true));
        }
        
        if (resultado == 10000)
          resultado = deleteDependenciasActividad(actividad, usuario, Boolean.valueOf(false));
        if (resultado == 10000)
          resultado = persistenceSession.delete(actividad, usuario);
        if (resultado == 10000) {
          resultado = deleteDependenciasActividad(actividad, usuario, Boolean.valueOf(true));
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
  
  public int saveActividad(PryActividad actividad, Usuario usuario, Boolean actualizarPadre)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[2];
    Object[] fieldValues = new Object[2];
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      fieldNames[0] = "nombre";
      fieldValues[0] = actividad.getNombre();
      
      fieldNames[1] = "proyecto_id";
      fieldValues[1] = actividad.getProyectoId();
      
      if ((actividad.getActividadId() == null) || (actividad.getActividadId().longValue() == 0L))
      {
        if (persistenceSession.existsObject(actividad, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          actividad.setActividadId(new Long(persistenceSession.getUniqueId()));
          actividad.getPryInformacionActividad().setActividadId(actividad.getActividadId());
          
          Date fechaInsercionActividad = new Date();
          actividad.setCreado(new Date(fechaInsercionActividad.getTime()));
          actividad.setCreadoId(usuario.getUsuarioId());
          
          resultado = persistenceSession.insert(actividad, usuario);
          
          if ((resultado == 10000) && (actualizarPadre.booleanValue())) {
            setFilaActividades(actividad.getActividadId().longValue(), actividad.getFila().intValue(), true, usuario);
          }
        }
      }
      else {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "actividadId";
        idFieldValues[0] = actividad.getActividadId();
        if (persistenceSession.existsObject(actividad, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        }
        else {
          Date fechaModificacionIniciativa = new Date();
          actividad.setModificado(new Date(fechaModificacionIniciativa.getTime()));
          
          actividad.setModificadoId(usuario.getUsuarioId());
          
          resultado = persistenceSession.update(actividad, usuario);
        }
      }
      
      if ((actividad.getPadre() != null) && (resultado == 10000) && (actualizarPadre.booleanValue())) {
        resultado = setFechaMinimaMaxima(getActividadRaiz(actividad.getPadre()).getActividadId().longValue(), usuario);
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
  
  public PryActividad getHermanoInmediatoAnterior(long actividadId)
  {
    PryActividad hermanoInmediatoAnterior = null;
    
    PryActividad actividadActual = (PryActividad)persistenceSession.load(PryActividad.class, new Long(actividadId));
    
    List hermanos = new ArrayList();
    if (actividadActual.getPadre() != null) {
      hermanos.addAll(actividadActual.getPadre().getHijos());
    } else {
      hermanos.addAll(persistenceSession.getRaices(actividadActual.getProyectoId().longValue()));
    }
    int j = 0;
    for (Iterator i = hermanos.iterator(); i.hasNext(); j++)
    {
      PryActividad actividad = (PryActividad)i.next();
      
      if (actividad.getActividadId().equals(actividadActual.getActividadId()))
      {
        if (j <= 0) break;
        hermanoInmediatoAnterior = (PryActividad)hermanos.get(j - 1);
        break;
      }
    }
    
    return hermanoInmediatoAnterior;
  }
  
  private PryActividad getHermanoInmediatoPosterior(long actividadId)
  {
    PryActividad hermanoInmediatoPosterior = null;
    
    PryActividad actividadActual = (PryActividad)persistenceSession.load(PryActividad.class, new Long(actividadId));
    
    List hermanos = new ArrayList();
    if (actividadActual.getPadre() != null) {
      hermanos.addAll(actividadActual.getPadre().getHijos());
    } else {
      hermanos.addAll(persistenceSession.getRaices(actividadActual.getProyectoId().longValue()));
    }
    
    int j = 0;
    for (Iterator i = hermanos.iterator(); i.hasNext(); j++)
    {
      PryActividad actividad = (PryActividad)i.next();
      
      if (actividad.getActividadId().equals(actividadActual.getActividadId())) {
        if (j >= hermanos.size() - 1) break;
        hermanoInmediatoPosterior = (PryActividad)hermanos.get(j + 1);
        
        break;
      }
    }
    
    return hermanoInmediatoPosterior;
  }
  
  private void setFilaActividad(long actividadId, Integer[] nuevaFila, Usuario usuario)
  {
    PryActividad actividadActual = (PryActividad)persistenceSession.load(PryActividad.class, new Long(actividadId));
    actividadActual.setFila(new Integer(nuevaFila[0].intValue()));
    
    persistenceSession.update(actividadActual, usuario);
    
    List hijos = new ArrayList();
    hijos.addAll(actividadActual.getHijos());
    
    for (Iterator i = hijos.iterator(); i.hasNext();) {
      PryActividad hijo = (PryActividad)i.next();
      
      nuevaFila[0] = new Integer(nuevaFila[0].intValue() + 1);
      setFilaActividad(hijo.getActividadId().longValue(), nuevaFila, usuario);
    }
  }
  
  private void getValoresLimiteHijosActividad(long actividadId, Object[] maximaFila, boolean excluirNodoRaizCalculoFecha)
  {
    PryActividad actividadActual = (PryActividad)persistenceSession.load(PryActividad.class, new Long(actividadId));
    
    if (((Integer)maximaFila[0]).intValue() < actividadActual.getFila().intValue())
    {
      maximaFila[0] = new Integer(actividadActual.getFila().intValue());
      maximaFila[1] = new Long(actividadActual.getActividadId().longValue());
    }
    
    if (!excluirNodoRaizCalculoFecha)
    {
      Calendar fechaMinima = Calendar.getInstance();
      if (actividadActual.getComienzoPlan() != null) {
        fechaMinima.setTime(actividadActual.getComienzoPlan());
      }
      if (maximaFila[2] == null)
      {
        maximaFila[2] = Calendar.getInstance();
        ((Calendar)maximaFila[2]).setTimeInMillis(fechaMinima.getTimeInMillis());
      }
      else if (fechaMinima.getTimeInMillis() < ((Calendar)maximaFila[2]).getTimeInMillis()) {
        ((Calendar)maximaFila[2]).setTimeInMillis(fechaMinima.getTimeInMillis());
      }
      Calendar fechaMaxima = Calendar.getInstance();
      if (actividadActual.getFinPlan() != null) {
        fechaMaxima.setTime(actividadActual.getFinPlan());
      }
      if (maximaFila[3] == null)
      {
        maximaFila[3] = Calendar.getInstance();
        ((Calendar)maximaFila[3]).setTimeInMillis(fechaMaxima.getTimeInMillis());
      }
      else if (fechaMaxima.getTimeInMillis() > ((Calendar)maximaFila[3]).getTimeInMillis()) {
        ((Calendar)maximaFila[3]).setTimeInMillis(fechaMaxima.getTimeInMillis());
      }
    }
    List hijos = new ArrayList();
    hijos.addAll(actividadActual.getHijos());
    
    for (Iterator i = hijos.iterator(); i.hasNext();)
    {
      PryActividad hijo = (PryActividad)i.next();
      getValoresLimiteHijosActividad(hijo.getActividadId().longValue(), maximaFila, false);
    }
  }
  
  private void moverFilaActividad(long actividadOrigenId, long actividadDestinoId, Usuario usuario)
  {
    PryActividad actividadOrigen = (PryActividad)persistenceSession.load(PryActividad.class, new Long(actividadOrigenId));
    
    PryActividad actividadDestino = (PryActividad)persistenceSession.load(PryActividad.class, new Long(actividadDestinoId));
    
    int filaOrigen = actividadOrigen.getFila().intValue();
    int filaDestino = actividadDestino.getFila().intValue();
    
    Integer[] ultimaFilaInsertada = new Integer[1];
    ultimaFilaInsertada[0] = new Integer(0);
    
    if (filaOrigen > filaDestino) {
      ultimaFilaInsertada[0] = new Integer(filaDestino);
      setFilaActividad(actividadOrigenId, ultimaFilaInsertada, usuario);
      ultimaFilaInsertada[0] = new Integer(ultimaFilaInsertada[0].intValue() + 1);
      setFilaActividad(actividadDestinoId, ultimaFilaInsertada, usuario);
    }
    else {
      ultimaFilaInsertada[0] = new Integer(filaOrigen);
      setFilaActividad(actividadDestinoId, ultimaFilaInsertada, usuario);
      ultimaFilaInsertada[0] = new Integer(ultimaFilaInsertada[0].intValue() + 1);
      setFilaActividad(actividadOrigenId, ultimaFilaInsertada, usuario);
    }
  }
  
  private void setNivelActividad(long actividadId, byte modificacionNivel, Usuario usuario)
  {
    PryActividad actividadActual = (PryActividad)persistenceSession.load(PryActividad.class, new Long(actividadId));
    actividadActual.setNivel(new Integer(actividadActual.getNivel().intValue() + modificacionNivel));
    
    persistenceSession.update(actividadActual, usuario);
    
    List hijos = new ArrayList();
    hijos.addAll(actividadActual.getHijos());
    
    for (Iterator i = hijos.iterator(); i.hasNext();) {
      PryActividad hijo = (PryActividad)i.next();
      setNivelActividad(hijo.getActividadId().longValue(), modificacionNivel, usuario);
    }
  }
  
  private void setFilaActividades(long actividadId, int nuevaFila, boolean actualizaPatronFila, Usuario usuario)
  {
    Map filtros = new HashMap();
    
    PryActividad actividadActual = (PryActividad)persistenceSession.load(PryActividad.class, new Long(actividadId));
    
    filtros.put("proyectoId", actividadActual.getProyectoId().toString());
    
    List todaActividad = new ArrayList();
    todaActividad.addAll(getActividades(0, 0, "fila", "ASC", true, filtros).getLista());
    
    boolean actualizar = false;
    
    for (Iterator i = todaActividad.iterator(); i.hasNext();) {
      PryActividad actividad = (PryActividad)i.next();
      
      if (actualizaPatronFila) {
        if (actividad.getFila().intValue() == nuevaFila) {
          nuevaFila++;
          actualizar = true;
        }
        if ((actualizar) && 
          (!actividad.getActividadId().equals(new Long(actividadId)))) {
          actividad.setFila(new Integer(nuevaFila));
          persistenceSession.update(actividad, usuario);
          nuevaFila++;
        }
      }
      else {
        if (actividad.getActividadId().equals(new Long(actividadId))) {
          actualizar = true;
        }
        
        if (actualizar) {
          actividad.setFila(new Integer(nuevaFila));
          persistenceSession.update(actividad, usuario);
          nuevaFila++;
        }
      }
    }
  }
  
  public List getValoresLimiteAlcanceHijosActividad(long actividadId, Boolean excluirNodoRaizCalculoFecha, Usuario usuario)
  {
    Object[] maximaFila = new Object[4];
    maximaFila[0] = new Integer(0);
    maximaFila[1] = new Long(0L);
    maximaFila[2] = null;
    maximaFila[3] = null;
    
    getValoresLimiteHijosActividad(actividadId, maximaFila, excluirNodoRaizCalculoFecha.booleanValue());
    
    List infoValoresLimite = new ArrayList();
    
    infoValoresLimite.add(maximaFila[0]);
    infoValoresLimite.add(maximaFila[1]);
    infoValoresLimite.add(maximaFila[2]);
    infoValoresLimite.add(maximaFila[3]);
    
    return infoValoresLimite;
  }
  
  public void moverFila(long actividadId, boolean ascender, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      PryActividad hermanoInmediato = null;
      
      if (ascender) {
        hermanoInmediato = getHermanoInmediatoAnterior(actividadId);
      } else {
        hermanoInmediato = getHermanoInmediatoPosterior(actividadId);
      }
      
      if (hermanoInmediato != null) {
        moverFilaActividad(actividadId, hermanoInmediato.getActividadId().longValue(), usuario);
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
  }
  
  public void setHijoHermanoInmediatoAnterior(long actividadId, Usuario usuario)
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
      
      PryActividad hermanoInmediatoAnterior = getHermanoInmediatoAnterior(actividadId);
      
      if (hermanoInmediatoAnterior != null)
      {
        PryActividad actividadActual = (PryActividad)persistenceSession.load(PryActividad.class, new Long(actividadId));
        
        PryActividad padreOriginal = null;
        if (actividadActual.getPadre() != null) {
          padreOriginal = actividadActual.getPadre();
        }
        actividadActual.setPadreId(hermanoInmediatoAnterior.getActividadId());
        resultado = persistenceSession.update(actividadActual, usuario);
        setNivelActividad(actividadActual.getActividadId().longValue(), (byte)1, usuario);
        
        Map<String, Object> filtros = new HashMap();
        
        filtros.put("proyectoId", actividadActual.getProyectoId().toString());
        filtros.put("padreId", actividadActual.getPadreId());
        
        String atributoOrden = "fila";
        String tipoOrden = "ASC";
        int pagina = 1;
        PaginaLista paginaActividades = getActividades(pagina, 30, atributoOrden, tipoOrden, true, filtros);
        boolean tipoMedicionDiferente = false;
        
        if (paginaActividades.getLista().size() > 0)
        {
          for (Iterator<PryActividad> iter = paginaActividades.getLista().iterator(); iter.hasNext();)
          {
            PryActividad actividad = (PryActividad)iter.next();
            if (actividad.getTipoMedicion().byteValue() != actividadActual.getTipoMedicion().byteValue())
            {
              tipoMedicionDiferente = true;
              break;
            }
          }
        }
        
        if (tipoMedicionDiferente) {
          hermanoInmediatoAnterior.setTipoMedicion(TipoMedicion.getTipoMedicionEnPeriodo());
        } else {
          hermanoInmediatoAnterior.setTipoMedicion(actividadActual.getTipoMedicion());
        }
        if (resultado == 10000) {
          resultado = persistenceSession.update(hermanoInmediatoAnterior, usuario);
        }
        if (resultado == 10000)
        {
          StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
          Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(hermanoInmediatoAnterior.getIndicadorId().longValue()));
          indicador.setTipoCargaMedicion(hermanoInmediatoAnterior.getTipoMedicion());
          resultado = strategosIndicadoresService.saveIndicador(indicador, usuario);
          strategosIndicadoresService.close();
        }
        persistenceSession.clear();
        
        if ((padreOriginal != null) && (resultado == 10000)) {
          resultado = setFechaMinimaMaxima(getActividadRaiz(padreOriginal).getActividadId().longValue(), usuario);
        }
        if (resultado == 10000) {
          resultado = setFechaMinimaMaxima(getActividadRaiz(hermanoInmediatoAnterior).getActividadId().longValue(), usuario);
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
  }
  
  public PryActividad getActividadRaiz(PryActividad actividad)
  {
    PryActividad raiz = null;
    
    if (actividad.getPadreId() == null) {
      raiz = actividad;
    }
    else {
      actividad.setPadre((PryActividad)persistenceSession.load(PryActividad.class, new Long(actividad.getPadreId().longValue())));
      raiz = getActividadRaiz(actividad.getPadre());
    }
    
    return raiz;
  }
  
  public void setHijoAbuelo(long actividadId, Usuario usuario)
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
      
      PryActividad actividadActual = (PryActividad)persistenceSession.load(PryActividad.class, new Long(actividadId));
      
      PryActividad padreOriginal = null;
      
      if (actividadActual.getPadreId() != null)
      {
        padreOriginal = (PryActividad)persistenceSession.load(PryActividad.class, new Long(actividadActual.getPadreId().longValue()));
        actividadActual.setPadre(padreOriginal);
        List hermanos = new ArrayList();
        hermanos.addAll(actividadActual.getPadre().getHijos());
        
        if (actividadActual.getPadre().getHijos().size() > 1)
        {
          boolean movimientoFilaAutorizado = false;
          for (Iterator i = hermanos.iterator(); i.hasNext();)
          {
            PryActividad hermanoInmediato = (PryActividad)i.next();
            
            if (hermanoInmediato.getActividadId().longValue() == actividadId)
              movimientoFilaAutorizado = true;
            if ((movimientoFilaAutorizado) && (hermanoInmediato.getActividadId().longValue() != actividadId)) {
              moverFilaActividad(actividadId, hermanoInmediato.getActividadId().longValue(), usuario);
            }
          }
        }
        PryActividad abuelo = null;
        if (actividadActual.getPadre().getPadreId() != null)
        {
          abuelo = (PryActividad)persistenceSession.load(PryActividad.class, new Long(actividadActual.getPadre().getPadreId().longValue()));
          
          actividadActual.setPadreId(abuelo.getActividadId());
        }
        else {
          actividadActual.setPadreId(null);
        }
        resultado = persistenceSession.update(actividadActual, usuario);
        
        setNivelActividad(actividadActual.getActividadId().longValue(), (byte)-1, usuario);
        
        persistenceSession.clear();
        
        if (padreOriginal != null) {
          resultado = setFechaMinimaMaxima(getActividadRaiz(padreOriginal).getActividadId().longValue(), usuario);
        }
        if (abuelo != null) {
          resultado = setFechaMinimaMaxima(getActividadRaiz(abuelo).getActividadId().longValue(), usuario);
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
  }
  
  private int setFechaMinimaMaxima(long actividadId, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      PryActividad actividadActual = (PryActividad)persistenceSession.load(PryActividad.class, new Long(actividadId));
      
      if (actividadActual != null) {
        List InfoValoresLimite = getValoresLimiteAlcanceHijosActividad(actividadActual.getActividadId().longValue(), new Boolean(true), usuario);
        
        actividadActual.setComienzoPlan(((Calendar)InfoValoresLimite.get(2)).getTime());
        
        actividadActual.setFinPlan(((Calendar)InfoValoresLimite.get(3)).getTime());
        
        resultado = persistenceSession.update(actividadActual, usuario);
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
  
  public int getMaximaFila(long proyectoId, Usuario usuario)
  {
    return persistenceSession.getMaximaFila(proyectoId);
  }
  














  public List getActividadesHijasPorPlan(Long actividadPadreId, Long planId, String orden, String tipoOrden)
  {
    Map<String, String> filtros = new HashMap();
    
    if (planId != null)
      filtros.put("planId", planId.toString());
    filtros.put("padreId", actividadPadreId.toString());
    
    return persistenceSession.getActividades(0, 0, orden, tipoOrden, false, filtros).getLista();
  }
  
  public Iniciativa getIniciativaPorProyecto(long proyectoId)
  {
    return new Iniciativa();
  }
  
  public List<PryActividad> getActividades(Long actividadId, byte tipo)
  {
    return persistenceSession.getActividades(actividadId, tipo);
  }
  
  public int updatePesosActividad(List<?> actividades, Usuario usuario)
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
      
      resultado = persistenceSession.updatePesosActividad(actividades, usuario);
      
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
      {
        persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return resultado;
  }
  
  public PryActividad getActividadByIndicador(long indicdorId)
  {
    return persistenceSession.getActividadByIndicador(indicdorId);
  }
  
  public List<PryActividad> getObjetoAsociados(Long proyectoId, String className)
  {
    return persistenceSession.getObjetoAsociados(proyectoId, className);
  }
  
  public int updateCampo(Long actividadId, Map<?, ?> filtros) throws Throwable
  {
    return persistenceSession.updateCampo(actividadId, filtros);
  }

  public List<PryActividad> getActividades(Long proyectoId) {
	return persistenceSession.getActividades(proyectoId);
  }
}
