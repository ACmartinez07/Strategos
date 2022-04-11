package com.visiongc.app.strategos.web.struts.presentaciones.celdas.forms;

import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.presentaciones.model.util.TipoCelda;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.List;

public class EditarCeldaForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long celdaId;
  private Long paginaId;
  private Byte tipo;
  private Byte fila;
  private Byte columna;
  private Boolean ejeY;
  private String titulo;
  private Integer anoInicio;
  private Byte mesInicio;
  private Integer anoFin;
  private Byte mesFin;
  private Byte frecuencia;
  private Boolean acumulado;
  private Integer numeroForos;
  private Integer numeroExplicaciones;
  private Long frecuenciaId;
  private String nombreVista;
  private Long indicadorId;
  private Long serieId;
  private String serieColor;
  private Byte serieEstilo;
  private String leyenda;
  private String indicadorCelda;
  private String nombresIndicadoresSeries;
  private String idsIndicadoresSeries;
  private static final String SEPARADOR_INDICADORES = "!;!";
  private static final String SEPARADOR_SERIES = "!@!";
  private static final String SEPARADOR_PERIODOS = "/";
  private List tiposCelda;
  private List grupoAnos;
  private List meses;
  private List frecuencias;

  public Long getCeldaId()
  {
    return this.celdaId;
  }

  public void setCeldaId(Long celdaId) {
    this.celdaId = celdaId;
  }

  public Long getPaginaId() {
    return this.paginaId;
  }

  public void setPaginaId(Long paginaId) {
    this.paginaId = paginaId;
  }

  public Byte getTipo() {
    return this.tipo;
  }

  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }

  public Byte getFila() {
    return this.fila;
  }

  public void setFila(Byte fila) {
    this.fila = fila;
  }

  public Byte getColumna() {
    return this.columna;
  }

  public void setColumna(Byte columna) {
    this.columna = columna;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Byte getFrecuencia() {
    return this.frecuencia;
  }

  public void setFrecuencia(Byte frecuencia) {
    this.frecuencia = frecuencia;
  }

  public Boolean getEjeY() {
    return this.ejeY;
  }

  public void setEjeY(Boolean ejeY) {
    this.ejeY = ejeY;
  }

  public Boolean getAcumulado() {
    return this.acumulado;
  }

  public void setAcumulado(Boolean acumulado) {
    this.acumulado = acumulado;
  }

  public String getSerieColor() {
    return this.serieColor;
  }

  public void setSerieColor(String serieColor) {
    this.serieColor = serieColor;
  }

  public Byte getSerieEstilo() {
    return this.serieEstilo;
  }

  public void setSerieEstilo(Byte serieEstilo) {
    this.serieEstilo = serieEstilo;
  }

  public String getLeyenda() {
    return this.leyenda;
  }

  public void setLeyenda(String leyenda) {
    this.leyenda = leyenda;
  }

  public String getIndicadorCelda() {
    return this.indicadorCelda;
  }

  public void setIndicadorCelda(String indicadorCelda) {
    this.indicadorCelda = indicadorCelda;
  }

  public Long getIndicadorId() {
    return this.indicadorId;
  }

  public void setIndicadorId(Long indicadorId) {
    this.indicadorId = indicadorId;
  }

  public Long getSerieId() {
    return this.serieId;
  }

  public void setSerieId(Long serieId) {
    this.serieId = serieId;
  }

  public Integer getNumeroForos() {
    return this.numeroForos;
  }

  public void setNumeroForos(Integer numeroForos) {
    this.numeroForos = numeroForos;
  }

  public Integer getNumeroExplicaciones() {
    return this.numeroExplicaciones;
  }

  public void setNumeroExplicaciones(Integer numeroExplicaciones) {
    this.numeroExplicaciones = numeroExplicaciones;
  }

  public String getSeparadorIndicadores() {
    return "!;!";
  }

  public String getSeparadorSeries() {
    return "!@!";
  }

  public String getSeparador_Periodos() {
    return "/";
  }

  public Long getFrecuenciaId() {
    return this.frecuenciaId;
  }

  public void setFrecuenciaId(Long frecuenciaId) {
    this.frecuenciaId = frecuenciaId;
  }

  public String getNombresIndicadoresSeries() {
    return this.nombresIndicadoresSeries;
  }

  public void setNombresIndicadoresSeries(String nombresIndicadoresSeries) {
    this.nombresIndicadoresSeries = nombresIndicadoresSeries;
  }

  public String getIdsIndicadoresSeries() {
    return this.idsIndicadoresSeries;
  }

  public void setIdsIndicadoresSeries(String idsIndicadoresSeries) {
    this.idsIndicadoresSeries = idsIndicadoresSeries;
  }

  public String getNombreVista() {
    return this.nombreVista;
  }

  public void setNombreVista(String nombreVista) {
    this.nombreVista = nombreVista;
  }

  public Integer getAnoInicio() {
    return this.anoInicio;
  }

  public void setAnoInicio(Integer anoInicio) {
    this.anoInicio = anoInicio;
  }

  public Byte getMesInicio() {
    return this.mesInicio;
  }

  public void setMesInicio(Byte mesInicio) {
    this.mesInicio = mesInicio;
  }

  public Integer getAnoFin() {
    return this.anoFin;
  }

  public void setAnoFin(Integer anoFin) {
    this.anoFin = anoFin;
  }

  public Byte getMesFin() {
    return this.mesFin;
  }

  public void setMesFin(Byte mesFin) {
    this.mesFin = mesFin;
  }

  public Byte getTipoCeldaGrafico() {
    return TipoCelda.getTipoCeldaGrafico();
  }

  public Byte getTipoCeldaMedidor() {
    return TipoCelda.getTipoCeldaMedidor();
  }

  public List getFrecuencias()
  {
    return this.frecuencias;
  }

  public void setFrecuencias(List frecuencias) {
    this.frecuencias = frecuencias;
  }

  public List getGrupoAnos() {
    return this.grupoAnos;
  }

  public void setGrupoAnos(List grupoAnos) {
    this.grupoAnos = grupoAnos;
  }

  public List getMeses() {
    return this.meses;
  }

  public void setMeses(List meses) {
    this.meses = meses;
  }

  public List getTiposCelda() {
    return this.tiposCelda;
  }

  public void setTiposCelda(List tiposCelda) {
    this.tiposCelda = tiposCelda;
  }

  public void clear() {
    this.celdaId = new Long(0L);
    this.paginaId = new Long(0L);
    this.tipo = null;
    this.ejeY = new Boolean(true);
    this.titulo = null;
    this.frecuencia = Frecuencia.getFrecuenciaMensual();
    this.acumulado = new Boolean(false);
    this.numeroExplicaciones = null;
    this.numeroForos = null;
    this.nombresIndicadoresSeries = null;
    this.idsIndicadoresSeries = null;
    this.mesInicio = null;
    this.mesFin = null;
    this.anoInicio = null;
    this.anoFin = null;
  }
}