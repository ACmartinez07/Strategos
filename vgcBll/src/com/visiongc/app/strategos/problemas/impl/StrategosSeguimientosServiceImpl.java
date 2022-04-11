package com.visiongc.app.strategos.problemas.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.problemas.StrategosSeguimientosService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.ConfiguracionMensajeEmailSeguimientos;
import com.visiongc.app.strategos.problemas.model.MemoSeguimiento;
import com.visiongc.app.strategos.problemas.model.MemoSeguimientoPK;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.problemas.model.ResponsableAccion;
import com.visiongc.app.strategos.problemas.model.Seguimiento;
import com.visiongc.app.strategos.problemas.model.util.PeriodoDia;
import com.visiongc.app.strategos.problemas.persistence.StrategosSeguimientosPersistenceSession;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StrategosSeguimientosServiceImpl
  extends StrategosServiceImpl implements StrategosSeguimientosService
{
  private StrategosSeguimientosPersistenceSession persistenceSession = null;
  
  public StrategosSeguimientosServiceImpl(StrategosSeguimientosPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getSeguimientos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
      PaginaLista paginaSeguimientos = persistenceSession.getSeguimientos(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
      if(filtros != null)
      {
          Iterator iter = filtros.keySet().iterator();
          String fieldName = null;
          String fieldValue = null;
          Seguimiento ultimoSeguimiento = null;
          while(iter.hasNext()) 
          {
              fieldName = (String)iter.next();
              fieldValue = (String)filtros.get(fieldName);
              if(fieldName.equals("accionId"))
              {
                  ultimoSeguimiento = persistenceSession.getUltimoSeguimiento(new Long(fieldValue));
                  if(ultimoSeguimiento != null)
                  {
                      for(Iterator i = paginaSeguimientos.getLista().iterator(); i.hasNext();)
                      {
                          Seguimiento seguimiento = (Seguimiento)i.next();
                          if(seguimiento != null && ultimoSeguimiento.getSeguimientoId() == seguimiento.getSeguimientoId())
                              seguimiento.setUltimoRegistro(new Boolean(true));
                      }

                  }
              }
              if(fieldName.equals("tipoPendiente") && (fieldValue.equals("2") || fieldValue.equals("3")))
              {
                  for(Iterator i = paginaSeguimientos.getLista().iterator(); i.hasNext();)
                  {
                      Seguimiento seguimiento = (Seguimiento)i.next();
                      if(seguimiento != null)
                          seguimiento.getListaproblemas().add(seguimiento.getAccion().getProblema().getProblemaId());
                  }

              }
          }
      }
      return paginaSeguimientos;
  }

  
  public int deleteSeguimiento(Seguimiento seguimiento, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (seguimiento.getSeguimientoId() != null)
      {
        resultado = persistenceSession.delete(seguimiento, usuario);
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
  
  public int saveSeguimiento(Seguimiento seguimiento, Usuario usuario)
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
      
      fieldNames[0] = "seguimientoId";
      fieldValues[0] = seguimiento.getSeguimientoId();
      
      if ((seguimiento.getSeguimientoId() == null) || (seguimiento.getSeguimientoId().longValue() == 0L)) {
        if (persistenceSession.existsObject(seguimiento, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else
        {
          seguimiento.setSeguimientoId(new Long(persistenceSession.getUniqueId()));
          
          for (Iterator i = seguimiento.getMemosSeguimiento().iterator(); i.hasNext();) {
            MemoSeguimiento memoSeguimiento = (MemoSeguimiento)i.next();
            memoSeguimiento.getPk().setSeguimientoId(seguimiento.getSeguimientoId());
          }
          
          seguimiento.setNumeroReporte(new Integer(persistenceSession.getOrdenMaxNumeroReporte(seguimiento.getAccionId()) + 1));
          
          seguimiento.setCreado(new Date());
          seguimiento.setCreadoId(usuario.getUsuarioId());
          resultado = persistenceSession.insert(seguimiento, usuario);
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "seguimientoId";
        idFieldValues[0] = seguimiento.getSeguimientoId();
        
        if (persistenceSession.existsObject(seguimiento, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        }
        else
        {
          seguimiento.setModificado(new Date());
          seguimiento.setModificadoId(usuario.getUsuarioId());
          resultado = persistenceSession.update(seguimiento, usuario);
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
      
      enviarCorreoSeguimiento(seguimiento);
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
  
  private int enviarCorreoSeguimiento(Seguimiento seguimiento)
  {
    int resultado = 10000;
    PeriodoDia periodoDia = new PeriodoDia();
    Responsable responsableAccionCorrectiva = null;
    Responsable auxiliarAccionCorrectiva = null;
    
    Accion accionCorrectiva = (Accion)persistenceSession.load(Accion.class, seguimiento.getAccionId());
    Problema problema = (Problema)persistenceSession.load(Problema.class, accionCorrectiva.getProblemaId());
    
    Responsable responsableProblema = problema.getResponsable();
    
    for (Iterator i = accionCorrectiva.getResponsablesAccion().iterator(); i.hasNext();)
    {
      ResponsableAccion responsableAccion = (ResponsableAccion)i.next();
      if (responsableAccion.getTipo().equals(new Byte((byte)1)))
        responsableAccionCorrectiva = (Responsable)persistenceSession.load(Responsable.class, responsableAccion.getResponsable().getResponsableId());
      if (responsableAccion.getTipo().equals(new Byte((byte)2))) {
        auxiliarAccionCorrectiva = (Responsable)persistenceSession.load(Responsable.class, responsableAccion.getResponsable().getResponsableId());
      }
    }
    ConfiguracionMensajeEmailSeguimientos configuracionMensajeEmailSeguimientos = getConfiguracionMensajeEmailSeguimientos();
    
    if ((responsableProblema != null) && (responsableProblema.getEmail() != null))
    {
      if ((seguimiento.getEmisionEnviado() != null) && (seguimiento.getEmisionEnviado().booleanValue()) && (seguimiento.getRecepcionEnviado() == null) && (responsableProblema.getEmail() != null))
      {
        String emailFrom = responsableProblema.getEmail();
        String emailTo = responsableAccionCorrectiva.getEmail();
        String emailSubject = messageResources.getResource("configuracion.mensajeemail.seguimientos.recodatorio.responder") + ": " + accionCorrectiva.getNombre();
        String emailSaludo = periodoDia.saludoEmail() + ", Sr(a) " + responsableAccionCorrectiva.getNombre() + "\n\n";
        
        String mensaje = configuracionMensajeEmailSeguimientos != null ? configuracionMensajeEmailSeguimientos.getMensaje() : "";
        mensaje = mensaje.replaceAll("&ACCION", accionCorrectiva.getNombre());
        mensaje = mensaje.replaceAll("&RESPONSABLE", responsableAccionCorrectiva.getNombre());
        mensaje = mensaje.replaceAll("&SUPERVISOR", responsableProblema.getNombre());
        mensaje = mensaje.replaceAll("&REPORTE", seguimiento.getNumeroReporte().toString());
        
        if ((accionCorrectiva.getDescripcion() != null) && (!accionCorrectiva.getDescripcion().equals("")))
          mensaje = mensaje.replaceAll("&DESCRIPCION", accionCorrectiva.getDescripcion());
        if ((auxiliarAccionCorrectiva != null) && (!auxiliarAccionCorrectiva.getNombre().equals("")))
          mensaje = mensaje.replaceAll("&AUXILIAR", auxiliarAccionCorrectiva.getNombre());
        if ((seguimiento.getNota() != null) && (!seguimiento.getNota().equals("")))
          mensaje = mensaje + "\n\n" + seguimiento.getNota();
        String emailBody = emailSaludo + mensaje;
        resultado = sendMail(emailFrom, emailTo, null, null, emailSubject, emailBody);
      }
      
      if ((seguimiento.getEmisionEnviado() != null) && (seguimiento.getEmisionEnviado().booleanValue()) && (seguimiento.getRecepcionEnviado() != null) && (seguimiento.getRecepcionEnviado().booleanValue()) && (seguimiento.getFechaAprobacion() == null) && (responsableProblema.getEmail() != null))
      {
        String emailFrom = responsableProblema.getEmail();
        String emailTo = responsableAccionCorrectiva.getEmail();
        String emailSubject = messageResources.getResource("configuracion.mensajeemail.seguimientos.recodatorio.aprobar") + ": " + accionCorrectiva.getNombre();
        String emailSaludo = periodoDia.saludoEmail() + ", Sr(a) " + responsableProblema.getNombre() + "\n\n";
        String emailBody = emailSaludo + messageResources.getResource("configuracion.mensajeemail.seguimientos.recodatorio.aprobar");
        resultado = sendMail(emailFrom, emailTo, null, null, emailSubject, emailBody);
      }
    }
    
    return resultado;
  }
  
  public int saveConfiguracionMensajeEmailSeguimientos(ConfiguracionMensajeEmailSeguimientos configuracionMensajeEmailSeguimientos, Usuario usuario)
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
      
      fieldNames[0] = "descripcion";
      fieldValues[0] = configuracionMensajeEmailSeguimientos.getDescripcion();
      
      if ((configuracionMensajeEmailSeguimientos.getMensajeId() == null) || (configuracionMensajeEmailSeguimientos.getMensajeId().longValue() == 0L)) {
        if (persistenceSession.existsObject(configuracionMensajeEmailSeguimientos, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          configuracionMensajeEmailSeguimientos.setMensajeId(new Long(persistenceSession.getUniqueId()));
          configuracionMensajeEmailSeguimientos.setCreado(new Date());
          configuracionMensajeEmailSeguimientos.setCreadoId(usuario.getUsuarioId());
          configuracionMensajeEmailSeguimientos.setModificado(new Date());
          configuracionMensajeEmailSeguimientos.setModificadoId(usuario.getUsuarioId());
          resultado = persistenceSession.insert(configuracionMensajeEmailSeguimientos, usuario);
        }
      } else {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "mensajeId";
        idFieldValues[0] = configuracionMensajeEmailSeguimientos.getMensajeId();
        
        if (persistenceSession.existsObject(configuracionMensajeEmailSeguimientos, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          configuracionMensajeEmailSeguimientos.setModificado(new Date());
          configuracionMensajeEmailSeguimientos.setModificadoId(usuario.getUsuarioId());
          resultado = persistenceSession.update(configuracionMensajeEmailSeguimientos, usuario);
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
  
  public ConfiguracionMensajeEmailSeguimientos crearConfiguracionMensajeEmailSeguimientos(Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    ConfiguracionMensajeEmailSeguimientos configuracion = new ConfiguracionMensajeEmailSeguimientos();
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      configuracion.setMensajeId(new Long(0L));
      configuracion.setDescripcion(messageResources.getResource("configuracion.mensajeemail.seguimientos.descripcion"));
      configuracion.setMensaje(messageResources.getResource("configuracion.mensajeemail.seguimientos.mensaje"));
      configuracion.setAcuseRecibo(new Boolean(true));
      configuracion.setSoloAuxiliar(new Boolean(false));
      
      resultado = saveConfiguracionMensajeEmailSeguimientos(configuracion, usuario);
      
      if (resultado == 10000) {
        configuracion = (ConfiguracionMensajeEmailSeguimientos)persistenceSession.load(ConfiguracionMensajeEmailSeguimientos.class, configuracion.getMensajeId());
      } else {
        configuracion = null;
      }
      
      if (transActiva) {
        if ((resultado == 10000) && (configuracion != null)) {
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
    
    return configuracion;
  }
  
  public ConfiguracionMensajeEmailSeguimientos getConfiguracionMensajeEmailSeguimientos()
  {
    return persistenceSession.getConfiguracionMensajeEmailSeguimientos();
  }
  
  public Boolean seguimientoCerrado(Long accionId)
  {
    Calendar calendar = Calendar.getInstance();
    Accion accion = (Accion)persistenceSession.load(Accion.class, accionId);
    
    int diaActual = calendar.get(5);
    int mesActual = calendar.get(2);
    int annoActual = calendar.get(1);
    
    calendar.setTime(accion.getFechaEstimadaFinal());
    
    int diaFinal = calendar.get(5);
    int mesFinal = calendar.get(2);
    int annoFinal = calendar.get(1);
    
    if (annoActual > annoFinal)
      return new Boolean(true);
    if (annoActual == annoFinal) {
      if (mesActual > mesFinal)
        return new Boolean(true);
      if ((mesActual == mesFinal) && 
        (diaActual > diaFinal)) {
        return new Boolean(true);
      }
    }
    

    return new Boolean(false);
  }
  
  public Boolean esDiaSeguimiento(Long accionId)
  {
    Calendar calendar = Calendar.getInstance();
    Accion accion = (Accion)persistenceSession.load(Accion.class, accionId);
    
    int diaActual = calendar.get(5);
    int mesActual = calendar.get(2);
    int annoActual = calendar.get(1);
    
    calendar.setTime(accion.getFechaEstimadaInicio());
    int frecuencia = accion.getFrecuencia().intValue() * persistenceSession.getOrdenMaxNumeroReporte(accionId);
    calendar.add(6, frecuencia);
    
    int diaSumado = calendar.get(5);
    int mesSumado = calendar.get(2);
    int annoSumado = calendar.get(1);
    
    if (annoSumado > annoActual)
      return new Boolean(false);
    if (annoSumado == annoActual) {
      if (mesSumado > mesActual)
        return new Boolean(false);
      if ((mesSumado == mesActual) && 
        (diaSumado > diaActual)) {
        return new Boolean(false);
      }
    }
    

    return new Boolean(true);
  }
  
  public Boolean existeSeguimiento(Long accionId)
  {
    Seguimiento ultimoSeguimiento = persistenceSession.getUltimoSeguimiento(accionId);
    Accion accion = (Accion)persistenceSession.load(Accion.class, accionId);
    Calendar calendar = Calendar.getInstance();
    
    if (ultimoSeguimiento == null) {
      return new Boolean(false);
    }
    
    calendar.setTime(ultimoSeguimiento.getFechaEmision());
    int diaEmisionSeguimiento = calendar.get(5);
    int mesEmisionSeguimiento = calendar.get(2);
    int annoEmisionSeguimiento = calendar.get(1);
    
    calendar.setTime(accion.getFechaEstimadaInicio());
    int rangoInferior = ultimoSeguimiento.getNumeroReporte().intValue() * accion.getFrecuencia().intValue();
    calendar.add(6, rangoInferior);
    int diaRangoInferiorAccion = calendar.get(5);
    int mesRangoInferiorAccion = calendar.get(2);
    int annoRangoInferiorAccion = calendar.get(1);
    
    calendar.setTime(accion.getFechaEstimadaInicio());
    int rangoSuperior = ultimoSeguimiento.getNumeroReporte().intValue() * accion.getFrecuencia().intValue() + accion.getFrecuencia().intValue();
    calendar.add(6, rangoSuperior);
    int diaRangoSuperiorAccion = calendar.get(5);
    int mesRangoSuperiorAccion = calendar.get(2);
    int annoRangoSuperiorAccion = calendar.get(1);
    
    if ((annoRangoSuperiorAccion >= annoEmisionSeguimiento) && (annoRangoInferiorAccion <= annoEmisionSeguimiento)) {
      if ((mesRangoSuperiorAccion >= mesEmisionSeguimiento) && (mesRangoInferiorAccion <= mesEmisionSeguimiento)) {
        if ((diaRangoSuperiorAccion >= diaEmisionSeguimiento) && (diaRangoInferiorAccion <= diaEmisionSeguimiento)) {
          return new Boolean(true);
        }
        return new Boolean(false);
      }
      
      return new Boolean(false);
    }
    
    return new Boolean(false);
  }
}
