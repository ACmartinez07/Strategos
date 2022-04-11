package com.visiongc.app.strategos.presentaciones.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;



public class Celda
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long celdaId;
  private Long paginaId;
  private Byte fila;
  private Byte columna;
  private String titulo;
  private Pagina pagina;
  private Long numeroForos;
  private Long numeroExplicaciones;
  private Set indicadoresCelda;
  private Boolean hayArea;
  private List datosSeries;
  private Long ejeyMax;
  private Long ejeyMin;
  private Integer numeroMaximoPeriodos;
  private Integer numeroSeriesVisibles;
  private String unidad;
  private Boolean graficarMultiplesAnos;
  private String graficarAno;
  private Integer indice;
  private Double valor;
  private Boolean showImage;
  private String configuracion;
  private Boolean verLeyenda;
  private Boolean showAlerta = Boolean.valueOf(false);
  private String alerta;
  private Boolean showDuppont = Boolean.valueOf(false);
  private String indicadorId = null;
  
  private Byte tipo;
  
  private String ejeX;
  
  private String serieName;
  private String serieData;
  private String serieColor;
  private String serieTipo;
  private String rangoAlertas;
  private Boolean isAscendente;
  private Integer anoInicial;
  private Integer anoFinal;
  private Integer periodoInicial;
  private Integer periodoFinal;
  private String nombre;
  private String tituloEjeY;
  private String tituloEjeX;
  private Byte frecuencia;
  private Byte frecuenciaAgrupada;
  private String condicion;
  private String verTituloImprimir;
  private String ajustarEscala;
  private List<Long> objetos;
  private Byte tipoGrafico;
  
  public Celda(Long celdaId, Long paginaId, Byte fila, Byte columna, String titulo)
  {
    this.celdaId = celdaId;
    this.paginaId = paginaId;
    this.fila = fila;
    this.columna = columna;
    this.titulo = titulo;
  }
  

  public Celda() {}
  

  public Long getCeldaId()
  {
    return celdaId;
  }
  
  public void setCeldaId(Long celdaId)
  {
    this.celdaId = celdaId;
  }
  
  public Long getPaginaId()
  {
    return paginaId;
  }
  
  public void setPaginaId(Long paginaId)
  {
    this.paginaId = paginaId;
  }
  
  public Byte getFila()
  {
    return fila;
  }
  
  public void setFila(Byte fila)
  {
    this.fila = fila;
  }
  
  public Byte getColumna()
  {
    return columna;
  }
  
  public void setColumna(Byte columna)
  {
    this.columna = columna;
  }
  
  public String getTitulo()
  {
    return titulo;
  }
  
  public void setTitulo(String titulo)
  {
    this.titulo = titulo;
  }
  
  public Pagina getPagina()
  {
    return pagina;
  }
  
  public void setPagina(Pagina pagina)
  {
    this.pagina = pagina;
  }
  
  public Long getNumeroForos()
  {
    return numeroForos;
  }
  
  public void setNumeroForos(Long numeroForos)
  {
    this.numeroForos = numeroForos;
  }
  
  public Long getNumeroExplicaciones()
  {
    return numeroExplicaciones;
  }
  
  public void setNumeroExplicaciones(Long numeroExplicaciones)
  {
    this.numeroExplicaciones = numeroExplicaciones;
  }
  
  public Set getIndicadoresCelda()
  {
    return indicadoresCelda;
  }
  
  public void setIndicadoresCelda(Set indicadoresCelda)
  {
    this.indicadoresCelda = indicadoresCelda;
  }
  
  public List getDatosSeries()
  {
    return datosSeries;
  }
  
  public void setDatosSeries(List datosSeries)
  {
    this.datosSeries = datosSeries;
  }
  
  public Long getEjeyMax()
  {
    return ejeyMax;
  }
  
  public void setEjeyMax(Long ejeyMax)
  {
    this.ejeyMax = ejeyMax;
  }
  
  public Long getEjeyMin()
  {
    return ejeyMin;
  }
  
  public void setEjeyMin(Long ejeyMin)
  {
    this.ejeyMin = ejeyMin;
  }
  
  public String getGraficarAno()
  {
    return graficarAno;
  }
  
  public void setGraficarAno(String graficarAno)
  {
    this.graficarAno = graficarAno;
  }
  
  public Boolean getGraficarMultiplesAnos()
  {
    return graficarMultiplesAnos;
  }
  
  public void setGraficarMultiplesAnos(Boolean graficarMultiplesAnos)
  {
    this.graficarMultiplesAnos = graficarMultiplesAnos;
  }
  
  public Boolean getHayArea()
  {
    return hayArea;
  }
  
  public void setHayArea(Boolean hayArea)
  {
    this.hayArea = hayArea;
  }
  
  public Integer getIndice()
  {
    return indice;
  }
  
  public void setIndice(Integer indice)
  {
    this.indice = indice;
  }
  
  public Integer getNumeroMaximoPeriodos()
  {
    return numeroMaximoPeriodos;
  }
  
  public void setNumeroMaximoPeriodos(Integer numeroMaximoPeriodos)
  {
    this.numeroMaximoPeriodos = numeroMaximoPeriodos;
  }
  
  public Integer getNumeroSeriesVisibles()
  {
    return numeroSeriesVisibles;
  }
  
  public void setNumeroSeriesVisibles(Integer numeroSeriesVisibles)
  {
    this.numeroSeriesVisibles = numeroSeriesVisibles;
  }
  
  public String getUnidad()
  {
    return unidad;
  }
  
  public void setUnidad(String unidad)
  {
    this.unidad = unidad;
  }
  
  public Double getValor()
  {
    return valor;
  }
  
  public void setValor(Double valor)
  {
    this.valor = valor;
  }
  
  public Boolean getShowImage()
  {
    return showImage;
  }
  
  public void setShowImage(Boolean showImage)
  {
    this.showImage = showImage;
  }
  
  public String getConfiguracion()
  {
    return configuracion;
  }
  
  public void setConfiguracion(String configuracion)
  {
    this.configuracion = configuracion;
  }
  
  public Boolean getVerLeyenda()
  {
    return verLeyenda;
  }
  
  public void setVerLeyenda(Boolean verLeyenda)
  {
    this.verLeyenda = verLeyenda;
  }
  
  public Boolean getShowAlerta()
  {
    return showAlerta;
  }
  
  public void setShowAlerta(Boolean showAlerta)
  {
    this.showAlerta = showAlerta;
  }
  
  public String getAlerta()
  {
    return alerta;
  }
  
  public void setAlerta(String alerta)
  {
    this.alerta = alerta;
  }
  
  public Boolean getShowDuppont()
  {
    return showDuppont;
  }
  
  public void setShowDuppont(Boolean showDuppont)
  {
    this.showDuppont = showDuppont;
  }
  
  public String getIndicadorId()
  {
    return indicadorId;
  }
  
  public void setIndicadorId(String indicadorId)
  {
    this.indicadorId = indicadorId;
  }
  
  public String getEjeX()
  {
    return ejeX;
  }
  
  public void setEjeX(String ejeX)
  {
    this.ejeX = ejeX;
  }
  
  public String getSerieData()
  {
    return serieData;
  }
  
  public void setSerieData(String serieData)
  {
    this.serieData = serieData;
  }
  
  public Byte getTipo()
  {
    return tipo;
  }
  
  public void setTipo(Byte tipo)
  {
    this.tipo = tipo;
  }
  
  public String getSerieName()
  {
    return serieName;
  }
  
  public void setSerieName(String serieName)
  {
    this.serieName = serieName;
  }
  

  public String getSerieColor()
  {
    return serieColor;
  }
  
  public void setSerieColor(String serieColor)
  {
    this.serieColor = serieColor;
  }
  
  public String getSerieTipo()
  {
    return serieTipo;
  }
  
  public void setSerieTipo(String serieTipo)
  {
    this.serieTipo = serieTipo;
  }
  
  public String getRangoAlertas()
  {
    return rangoAlertas;
  }
  
  public void setRangoAlertas(String rangoAlertas)
  {
    this.rangoAlertas = rangoAlertas;
  }
  
  public Boolean getIsAscendente()
  {
    return isAscendente;
  }
  
  public void setIsAscendente(Boolean isAscendente)
  {
    this.isAscendente = isAscendente;
  }
  
  public Integer getAnoInicial()
  {
    return anoInicial;
  }
  
  public void setAnoInicial(Integer anoInicial)
  {
    this.anoInicial = anoInicial;
  }
  
  public Integer getAnoFinal()
  {
    return anoFinal;
  }
  
  public void setAnoFinal(Integer anoFinal)
  {
    this.anoFinal = anoFinal;
  }
  
  public Integer getPeriodoInicial()
  {
    return periodoInicial;
  }
  
  public void setPeriodoInicial(Integer periodoInicial)
  {
    this.periodoInicial = periodoInicial;
  }
  
  public Integer getPeriodoFinal()
  {
    return periodoFinal;
  }
  
  public void setPeriodoFinal(Integer periodoFinal)
  {
    this.periodoFinal = periodoFinal;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public String getTituloEjeY()
  {
    return tituloEjeY;
  }
  
  public void setTituloEjeY(String tituloEjeY)
  {
    this.tituloEjeY = tituloEjeY;
  }
  
  public String getTituloEjeX()
  {
    return tituloEjeX;
  }
  
  public void setTituloEjeX(String tituloEjeX)
  {
    this.tituloEjeX = tituloEjeX;
  }
  
  public Byte getFrecuencia()
  {
    return frecuencia;
  }
  
  public void setFrecuencia(Byte frecuencia)
  {
    this.frecuencia = frecuencia;
  }
  
  public Byte getFrecuenciaAgrupada()
  {
    return frecuenciaAgrupada;
  }
  
  public void setFrecuenciaAgrupada(Byte frecuenciaAgrupada)
  {
    this.frecuenciaAgrupada = frecuenciaAgrupada;
  }
  
  public String getCondicion()
  {
    return condicion;
  }
  
  public void setCondicion(String condicion)
  {
    this.condicion = condicion;
  }
  
  public String getVerTituloImprimir()
  {
    return verTituloImprimir;
  }
  
  public void setVerTituloImprimir(String verTituloImprimir)
  {
    this.verTituloImprimir = verTituloImprimir;
  }
  
  public String getAjustarEscala()
  {
    return ajustarEscala;
  }
  
  public void setAjustarEscala(String ajustarEscala)
  {
    this.ajustarEscala = ajustarEscala;
  }
  
  public List<Long> getObjetos()
  {
    return objetos;
  }
  
  public void setObjetos(List<Long> objetos)
  {
    this.objetos = objetos;
  }
  
  public void setTipoGrafico(Byte tipoGrafico)
  {
    this.tipoGrafico = tipoGrafico;
  }
  
  public Byte getTipoGrafico()
  {
    return tipoGrafico;
  }
  
  public int compareTo(Object o)
  {
    Celda or = (Celda)o;
    return getCeldaId().compareTo(or.getCeldaId());
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("celdaId", getCeldaId()).toString();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Celda other = (Celda)obj;
    
    return new EqualsBuilder().append(getCeldaId(), other.getCeldaId()).isEquals();
  }
}
