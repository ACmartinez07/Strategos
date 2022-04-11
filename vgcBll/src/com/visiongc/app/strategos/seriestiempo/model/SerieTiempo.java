package com.visiongc.app.strategos.seriestiempo.model;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;



public class SerieTiempo
  implements Serializable, Comparable<Object>
{
  static final long serialVersionUID = 0L;
  private static final long TIPO_SERIE_REAL = 0L;
  private static final long TIPO_SERIE_PROGRAMADO = 1L;
  private static final long TIPO_SERIE_MINIMO = 2L;
  private static final long TIPO_SERIE_MAXIMO = 3L;
  private static final long TIPO_SERIE_ALERTA = 4L;
  private static final long TIPO_SERIE_PORCENTAJE_REAL = 5L;
  private static final long TIPO_SERIE_PORCENTAJE_PROGRAMADO = 6L;
  private static final long TIPO_SERIE_META_PLAN = 7L;
  private static final long TIPO_SERIE_VALOR_INICIAL_PLAN = 8L;
  private static final String IDENT_SERIE_REAL = "REAL";
  private static final String IDENT_SERIE_MINIMO = "MIN";
  private static final String IDENT_SERIE_MAXIMO = "MAX";
  private static final String IDENT_SERIE_PROGRAMADO = "PROG";
  private static final String IDENT_SERIE_META_PLAN = "META";
  private static final String IDENT_SERIE_VALOR_INICIAL_PLAN = "VALIN";
  private static final String IDENT_SERIE_ALERTA = "ALERT";
  private static final String IDENT_SERIE_PORCENTAJE_REAL = "PREAL";
  private static final String IDENT_SERIE_PORCENTAJE_PROGRAMADO = "PPROG";
  private Long serieId;
  private String nombre;
  private Boolean fija;
  private Boolean oculta;
  private String identificador;
  private Boolean preseleccionada;
  
  public SerieTiempo(Long serieId, String nombre, Boolean fija, Boolean oculta, String identificador)
  {
    this.serieId = serieId;
    this.nombre = nombre;
    this.fija = fija;
    this.oculta = oculta;
    this.identificador = identificador;
  }
  

  public SerieTiempo() {}
  

  public SerieTiempo(Long serieId, String nombre)
  {
    this.serieId = serieId;
    this.nombre = nombre;
  }
  
  public static Long getSerieRealId()
  {
    return new Long(0L);
  }
  
  public static Long getSerieProgramadoId()
  {
    return new Long(1L);
  }
  
  public static Long getSerieMinimoId()
  {
    return new Long(2L);
  }
  
  public static Long getSerieMaximoId()
  {
    return new Long(3L);
  }
  
  public static Long getSerieMetaId()
  {
    return new Long(7L);
  }
  
  public static Long getSerieValorInicialId()
  {
    return new Long(8L);
  }
  
  public static Long getSerieAlerta()
  {
    return new Long(4L);
  }
  
  public static Long getSeriePorcentajeRealId()
  {
    return new Long(5L);
  }
  
  public static Long getSeriePorcentajeProgramadoId()
  {
    return new Long(6L);
  }
  
  public static SerieTiempo getSerieReal()
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    return new SerieTiempo(Long.valueOf(0L), messageResources.getResource("serietiempo.real"), new Boolean(true), new Boolean(false), "REAL");
  }
  
  public static SerieTiempo getSerieMinimo()
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    return new SerieTiempo(Long.valueOf(2L), messageResources.getResource("serietiempo.minimo"), new Boolean(true), new Boolean(false), "MIN");
  }
  
  public static SerieTiempo getSerieMaximo()
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    return new SerieTiempo(Long.valueOf(3L), messageResources.getResource("serietiempo.maximo"), new Boolean(true), new Boolean(false), "MAX");
  }
  
  public static SerieTiempo getSerieProgramado()
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    return new SerieTiempo(Long.valueOf(1L), messageResources.getResource("serietiempo.programado"), new Boolean(true), new Boolean(false), "PROG");
  }
  
  public static SerieTiempo getSerieMeta()
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    return new SerieTiempo(Long.valueOf(7L), messageResources.getResource("serietiempo.meta"), new Boolean(true), new Boolean(false), "META");
  }
  
  public static SerieTiempo getSerieValorInicial()
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    return new SerieTiempo(Long.valueOf(8L), messageResources.getResource("serietiempo.valorinicial"), new Boolean(true), new Boolean(false), "VALIN");
  }
  
  public static SerieTiempo getSerieValorAlerta()
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    return new SerieTiempo(Long.valueOf(4L), messageResources.getResource("serietiempo.valoralerta"), new Boolean(true), new Boolean(true), "ALERT");
  }
  
  public static SerieTiempo getSeriePorcentajeReal()
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    return new SerieTiempo(Long.valueOf(5L), messageResources.getResource("serietiempo.valorporcentaje.real"), new Boolean(true), new Boolean(true), "PREAL");
  }
  
  public static SerieTiempo getSeriePorcentajeProgramado()
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    return new SerieTiempo(Long.valueOf(6L), messageResources.getResource("serietiempo.valorporcentaje.programado"), new Boolean(true), new Boolean(true), "PPROG");
  }
  
  public Long getSerieId()
  {
    return serieId;
  }
  
  public void setSerieId(Long serieId)
  {
    this.serieId = serieId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public Boolean getFija()
  {
    return fija;
  }
  
  public void setFija(Boolean fija)
  {
    this.fija = fija;
  }
  
  public Boolean getOculta()
  {
    return oculta;
  }
  
  public void setOculta(Boolean oculta)
  {
    this.oculta = oculta;
  }
  
  public String getIdentificador()
  {
    return identificador;
  }
  
  public void setIdentificador(String identificador)
  {
    this.identificador = identificador;
  }
  
  public Boolean getPreseleccionada()
  {
    return preseleccionada;
  }
  
  public void setPreseleccionada(Boolean preseleccionada)
  {
    this.preseleccionada = preseleccionada;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("serieId", getSerieId()).toString();
  }
  
  public int compareTo(Object o)
  {
    SerieTiempo st = (SerieTiempo)o;
    
    return getSerieId().compareTo(st.getSerieId());
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass()) {
      return false;
    }
    SerieTiempo other = (SerieTiempo)obj;
    
    return new EqualsBuilder().append(getSerieId(), other.getSerieId()).isEquals();
  }
}
