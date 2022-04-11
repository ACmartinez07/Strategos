package com.visiongc.framework.web.struts.forms.servicio;

import com.visiongc.commons.util.Password;
import com.visiongc.framework.model.Servicio.ServicioStatus;
import com.visiongc.framework.model.Servicio.ServicioTypeEvent;
import com.visiongc.framework.web.struts.forms.VisorListaForm;
import java.util.List;

public class GestionarServiciosForm
  extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  private String stringConexion;
  private String driverConexion;
  private String usuarioConexion;
  private String passwordConexion;
  private String respuesta;
  private Byte status;
  private String fechaDesde;
  private String fechaHasta;
  private Byte tipoEvento;
  private List tiposEventos;
  private Byte tipoEstatus;
  private List tiposEstatus;
  private Long usuarioId;
  private String usuarioNombre;
  
  public GestionarServiciosForm() {}
  
  public String getFechaDesde()
  {
    return fechaDesde;
  }
  
  public void setFechaDesde(String fechaDesde)
  {
    this.fechaDesde = fechaDesde;
  }
  
  public String getFechaHasta()
  {
    return fechaHasta;
  }
  
  public void setFechaHasta(String fechaHasta)
  {
    this.fechaHasta = fechaHasta;
  }
  
  public List getTiposEventos()
  {
    return tiposEventos;
  }
  
  public void setTiposEventos(List tiposEventos)
  {
    this.tiposEventos = tiposEventos;
  }
  
  public Byte getTipoEvento()
  {
    return tipoEvento;
  }
  
  public void setTipoEvento(Byte tipoEvento)
  {
    this.tipoEvento = tipoEvento;
  }
  
  public List getTiposEstatus()
  {
    return tiposEstatus;
  }
  
  public void setTiposEstatus(List tiposEstatus)
  {
    this.tiposEstatus = tiposEstatus;
  }
  
  public Byte getTipoEstatus()
  {
    return tipoEstatus;
  }
  
  public void setTipoEstatus(Byte tipoEstatus)
  {
    this.tipoEstatus = tipoEstatus;
  }
  
  public Long getUsuarioId()
  {
    return usuarioId;
  }
  
  public void setUsuarioId(Long usuarioId)
  {
    this.usuarioId = usuarioId;
  }
  
  public String getUsuarioNombre()
  {
    return usuarioNombre;
  }
  
  public void setUsuarioNombre(String usuarioNombre)
  {
    this.usuarioNombre = usuarioNombre;
  }
  
  public String getStringConexion()
  {
    return stringConexion;
  }
  
  public void setStringConexion(String stringConexion)
  {
    this.stringConexion = stringConexion;
  }
  
  public String getDriverConexion()
  {
    return driverConexion;
  }
  
  public void setDriverConexion(String driverConexion)
  {
    this.driverConexion = driverConexion;
  }
  
  public String getUsuarioConexion()
  {
    return usuarioConexion;
  }
  
  public void setUsuarioConexion(String usuarioConexion)
  {
    this.usuarioConexion = usuarioConexion;
  }
  
  public String getPasswordConexion()
  {
    return passwordConexion;
  }
  
  public void setPasswordConexion(String passwordConexion)
  {
    this.passwordConexion = passwordConexion;
  }
  
  public String getPasswordConexionEncript()
  {
    return Password.encriptPassWord(passwordConexion);
  }
  
  public String getPasswordConexionDecriptado(String passwordConexion)
  {
    return Password.decriptPassWord(passwordConexion);
  }
  
  public String getRespuesta()
  {
    return respuesta;
  }
  
  public void setRespuesta(String respuesta)
  {
    this.respuesta = respuesta;
  }
  
  public Byte getStatus()
  {
    return status;
  }
  
  public void setStatus(Byte status)
  {
    this.status = GestionarServiciosStatus.getImportarStatus(status);
  }
  
  public void clear()
  {
    stringConexion = "";
    driverConexion = "";
    usuarioConexion = "";
    passwordConexion = "";
    respuesta = "";
  }
  
  public static class GestionarServiciosStatus {
    private static final byte STATUS_SUCCESS = 0;
    private static final byte STATUS_NOSUCCESS = 1;
    private static final byte STATUS_CONEXIONINVALIDA = 2;
    private static final byte STATUS_VIEWSUCCESS = 3;
    private static final byte STATUS_TESTSUCCESS = 4;
    private static final byte STATUS_TESTNOSUCCESS = 5;
    private static final byte STATUS_TESTSUCCESSPASSWORDCHANGE = 6;
    
    public GestionarServiciosStatus() {}
    
    private static Byte getImportarStatus(Byte status) {
      if (status.byteValue() == 0)
        return new Byte((byte)0);
      if (status.byteValue() == 1)
        return new Byte((byte)1);
      if (status.byteValue() == 2)
        return new Byte((byte)2);
      if (status.byteValue() == 3)
        return new Byte((byte)3);
      if (status.byteValue() == 4)
        return new Byte((byte)4);
      if (status.byteValue() == 5)
        return new Byte((byte)5);
      if (status.byteValue() == 6) {
        return new Byte((byte)6);
      }
      return null;
    }
    
    public static Byte getStatusSuccess()
    {
      return new Byte((byte)0);
    }
    
    public static Byte getStatusNoSuccess()
    {
      return new Byte((byte)1);
    }
    
    public static Byte getStatusConexionInvalida()
    {
      return new Byte((byte)2);
    }
    
    public static Byte getStatusViewSuccess()
    {
      return new Byte((byte)3);
    }
    
    public static Byte getStatusTestSuccess()
    {
      return new Byte((byte)4);
    }
    
    public static Byte getStatusTestNoSuccess()
    {
      return new Byte((byte)5);
    }
    
    public static Byte getStatusTestSuccessPasswordChange()
    {
      return new Byte((byte)6);
    }
  }
}
