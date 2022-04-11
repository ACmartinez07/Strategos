package com.visiongc.app.strategos.explicaciones.model;

import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.model.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;



public class Explicacion
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long explicacionId;
  private Date fecha;
  private Date creado;
  private String fechaFormateadaCreado;
  private Long creadoId;
  private Byte objetoKey;
  private Long objetoId;
  private String titulo;
  private Usuario usuarioCreado;
  private Set memosExplicacion;
  private Set adjuntosExplicacion;
  private Long numeroAdjuntos;
  private Integer tipo;
  private Date fechaCompromiso;
  private Date fechaReal;
  private Boolean publico;
  
  public Explicacion() {}
  
  public Long getExplicacionId()
  {
    return explicacionId;
  }
  
  public void setExplicacionId(Long explicacionId)
  {
    this.explicacionId = explicacionId;
  }
  
  public Date getFecha()
  {
    return fecha;
  }
  
  public String getFechaFormateada()
  {
    return VgcFormatter.formatearFecha(fecha, "formato.fecha.corta");
  }
  
  public void setFecha(Date fecha)
  {
    this.fecha = fecha;
  }
  
  public Date getCreado()
  {
    return creado;
  }
  
  public void setCreado(Date creado)
  {
    this.creado = creado;
  }
  
  public Long getCreadoId()
  {
    return creadoId;
  }
  
  public void setCreadoId(Long creadoId)
  {
    this.creadoId = creadoId;
  }
  
  public String getFechaFormateadaCreado()
  {
    fechaFormateadaCreado = VgcFormatter.formatearFecha(creado, "formato.fecha.corta");
    return fechaFormateadaCreado;
  }
  
  public void setFechaFormateadaCreado(String fechaFormateadaCreado)
  {
    this.fechaFormateadaCreado = fechaFormateadaCreado;
  }
  
  public Usuario getUsuarioCreado()
  {
    return usuarioCreado;
  }
  
  public void setUsuarioCreado(Usuario usuarioCreado)
  {
    this.usuarioCreado = usuarioCreado;
  }
  
  public Byte getObjetoKey()
  {
    return objetoKey;
  }
  
  public void setObjetoKey(Byte objetoKey)
  {
    this.objetoKey = ObjetivoKey.getKey(objetoKey);
  }
  
  public Long getObjetoId()
  {
    return objetoId;
  }
  
  public void setObjetoId(Long objetoId)
  {
    this.objetoId = objetoId;
  }
  
  public String getTitulo()
  {
    return titulo;
  }
  
  public void setTitulo(String titulo)
  {
    this.titulo = titulo;
  }
  
  public Set getMemosExplicacion()
  {
    return memosExplicacion;
  }
  
  public void setMemosExplicacion(Set memosExplicacion)
  {
    this.memosExplicacion = memosExplicacion;
  }
  
  public Set getAdjuntosExplicacion()
  {
    return adjuntosExplicacion;
  }
  
  public void setAdjuntosExplicacion(Set adjuntosExplicacion)
  {
    this.adjuntosExplicacion = adjuntosExplicacion;
  }
  
  public Long getNumeroAdjuntos()
  {
    return numeroAdjuntos;
  }
  
  public void setNumeroAdjuntos(Long numeroAdjuntos)
  {
    this.numeroAdjuntos = numeroAdjuntos;
  }
  
  public Integer getTipo()
  {
    return tipo;
  }
  
  public void setTipo(Integer tipo)
  {
    this.tipo = TipoExplicacion.getTipo(tipo.intValue());
  }
  
  public Date getFechaCompromiso()
  {
    return fechaCompromiso;
  }
  
  public String getFechaCompromisoFormateada()
  {
    return VgcFormatter.formatearFecha(fechaCompromiso, "formato.fecha.corta");
  }
  
  public void setFechaCompromiso(Date fechaCompromiso)
  {
    this.fechaCompromiso = fechaCompromiso;
  }
  
  public Date getFechaReal()
  {
    return fechaReal;
  }
  
  public String getFechaRealFormateada()
  {
    return VgcFormatter.formatearFecha(fechaReal, "formato.fecha.corta");
  }
  
  public void setFechaReal(Date fechaReal)
  {
    this.fechaReal = fechaReal;
  }
  
  public Boolean getPublico()
  {
    return publico;
  }
  
  public void setPublico(Boolean publico)
  {
    this.publico = publico;
  }
  
  public int compareTo(Object o)
  {
    Explicacion or = (Explicacion)o;
    return getExplicacionId().compareTo(or.getExplicacionId());
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Explicacion other = (Explicacion)obj;
    
    if (explicacionId == null)
    {
      if (explicacionId != null) {
        return false;
      }
    } else if (!explicacionId.equals(explicacionId)) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("explicacionId", getExplicacionId()).toString();
  }
  
  public static class TipoExplicacion {
    private static final int TIPO_EXPLICACION = 0;
    private static final int TIPO_CUALITATIVO = 1;
    private static final int TIPO_EJECUTIVO = 2;
    private static final int TIPO_EVENTO = 3;
    private static final int TIPO_NOTICIA = 4;
    private static final int TIPO_INSTRUMENTO = 5;
    
    public TipoExplicacion() {}
    
    private static Integer getTipo(int tipo) {
      if (tipo == 0)
        return new Integer(0);
      if (tipo == 1)
        return new Integer(1);
      if (tipo == 2)
        return new Integer(2);
      if (tipo == 3)
        return new Integer(3);
      if (tipo == 4)
          return new Integer(4);
      if (tipo == 5) {
        return new Integer(5);
      }
      return null;
    }
    
    public static Integer getTipoExplicacion()
    {
      return new Integer(0);
    }
    
    public static Integer getTipoCualitativo()
    {
      return new Integer(1);
    }
    
    public static Integer getTipoEjecutivo()
    {
      return new Integer(2);
    }
    
    public static Integer getTipoEventos()
    {
      return new Integer(3);
    }
    
    public static Integer getTipoNoticia()
    {
      return new Integer(4);
    }
    
    public static Integer getTipoInstrumento()
    {
      return new Integer(5);
    }
    
  }
  
  public static class ObjetivoKey
  {
    private static final Byte KEY_INDICADOR = Byte.valueOf((byte)0);
    private static final Byte KEY_CELDA = Byte.valueOf((byte)1);
    private static final Byte KEY_INICIATIVA = Byte.valueOf((byte)2);
    private static final Byte KEY_ORGANIZACION = Byte.valueOf((byte)3);
    private static final Byte KEY_PLAN = Byte.valueOf((byte)4);
    private static final Byte KEY_OBJETIVO = Byte.valueOf((byte)5);
    private static final Byte KEY_INSTRUMENTO = Byte.valueOf((byte)6);
    
    public ObjetivoKey() {}
    
    private static Byte getKey(Byte Key) { if (Key.byteValue() == KEY_INDICADOR.byteValue())
        return new Byte(KEY_INDICADOR.byteValue());
      if (Key.byteValue() == KEY_CELDA.byteValue())
        return new Byte(KEY_CELDA.byteValue());
      if (Key.byteValue() == KEY_INICIATIVA.byteValue())
        return new Byte(KEY_INICIATIVA.byteValue());
      if (Key.byteValue() == KEY_ORGANIZACION.byteValue())
        return new Byte(KEY_ORGANIZACION.byteValue());
      if (Key.byteValue() == KEY_PLAN.byteValue())
        return new Byte(KEY_PLAN.byteValue());
      if (Key.byteValue() == KEY_INSTRUMENTO.byteValue()) 
          return new Byte(KEY_INSTRUMENTO.byteValue());
      if (Key.byteValue() == KEY_OBJETIVO.byteValue()) {
        return new Byte(KEY_OBJETIVO.byteValue());
      }
      return null;
    }
    
    public static Byte getKeyIndicador()
    {
      return new Byte(KEY_INDICADOR.byteValue());
    }
    
    public static Byte getKeyCelda()
    {
      return new Byte(KEY_CELDA.byteValue());
    }
    
    public static Byte getKeyIniciativa()
    {
      return new Byte(KEY_INICIATIVA.byteValue());
    }
    
    public static Byte getKeyOrganizacion()
    {
      return new Byte(KEY_ORGANIZACION.byteValue());
    }
    
    public static Byte getKeyPlan()
    {
      return new Byte(KEY_PLAN.byteValue());
    }
    
    public static Byte getKeyObjetivo()
    {
      return new Byte(KEY_OBJETIVO.byteValue());
    }
    
    public static Byte getKeyInstrumento()
    {
      return new Byte(KEY_INSTRUMENTO.byteValue());
    }
  }
}
