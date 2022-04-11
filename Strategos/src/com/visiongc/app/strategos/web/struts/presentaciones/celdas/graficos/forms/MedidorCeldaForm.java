package com.visiongc.app.strategos.web.struts.presentaciones.celdas.graficos.forms;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.ArrayList;
import java.util.List;

public class MedidorCeldaForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Integer anoInicial;
  private Integer anoFinal;
  private Integer periodoInicialAnt;
  private Integer periodoFinalAnt;
  private Integer anoInicialAnt;
  private Integer anoFinalAnt;
  private Integer periodoInicial;
  private Integer periodoFinal;
  private Integer numeroMaximoPeriodos;
  private Byte frecuencia;
  private Indicador indicador;
  private List frecuencias;
  private List tiposSerie;
  private List anosPeriodos;
  private Boolean graficarDesdeCeroEjeY;
  private List series;
  private Boolean yAxisCero;
  private String ln;
  private String unidad;
  private String valores;
  private String hayArea;
  private Integer numeroSeriesVisibles;
  private Integer numeroTotalPeriodos;
  private Long objetoId;
  private Long vistaId;
  private Long paginaId;
  private Long celdaId;
  private Long previaCeldaId;
  private Long siguienteCeldaId;
  private Vista vista;
  private Pagina pagina;
  private Celda celda;
  private Integer totalIndicadoresCelda;

  public Indicador getIndicador()
  {
    return this.indicador;
  }

  public void setIndicador(Indicador indicador) {
    this.indicador = indicador;
  }

  public Integer getAnoInicial() {
    return this.anoInicial;
  }

  public void setAnoInicial(Integer anoInicial) {
    this.anoInicial = anoInicial;
  }

  public Integer getAnoFinal() {
    return this.anoFinal;
  }

  public void setAnoFinal(Integer anoFinal) {
    this.anoFinal = anoFinal;
  }

  public Integer getPriodoInicialAnt() {
    return this.periodoInicialAnt;
  }

  public void setPeriodoInicialAnt(Integer periodoInicialAnt) {
    this.periodoInicialAnt = periodoInicialAnt;
  }

  public Integer getPeriodoInicial() {
    return this.periodoInicial;
  }

  public void setPeriodoInicial(Integer periodoInicial) {
    this.periodoInicial = periodoInicial;
  }

  public Integer getPeriodoFinalAnt() {
    return this.periodoFinalAnt;
  }

  public void setPeriodoFinalAnt(Integer periodoFinalAnt) {
    this.periodoFinalAnt = periodoFinalAnt;
  }

  public Integer getPeriodoFinal() {
    return this.periodoFinal;
  }

  public void setPeriodoFinal(Integer periodoFinal) {
    this.periodoFinal = periodoFinal;
  }

  public Integer getAnoInicialAnt() {
    return this.anoInicialAnt;
  }

  public void setAnoInicialAnt(Integer anoInicialAnt) {
    this.anoInicialAnt = anoInicialAnt;
  }

  public Integer getAnoFinalAnt() {
    return this.anoFinalAnt;
  }

  public void setAnoFinalAnt(Integer anoFinalAnt) {
    this.anoFinalAnt = anoFinalAnt;
  }

  public Integer getNumeroMaximoPeriodos() {
    return this.numeroMaximoPeriodos;
  }

  public void setNumeroMaximoPeriodos(Integer numeroMaximoPeriodos) {
    this.numeroMaximoPeriodos = numeroMaximoPeriodos;
  }

  public Byte getFrecuencia() {
    return this.frecuencia;
  }

  public void setFrecuencia(Byte frecuencia) {
    this.frecuencia = frecuencia;
  }

  public void setPeriodoFinal(Byte frecuencia) {
    this.frecuencia = frecuencia;
  }

  public List getFrecuencias() {
    return this.frecuencias;
  }

  public void setFrecuencias(List frecuencias) {
    this.frecuencias = frecuencias;
  }

  public List getTiposSerie() {
    return this.tiposSerie;
  }

  public void setTiposSerie(List tiposSerie) {
    this.tiposSerie = tiposSerie;
  }

  public List getSeries() {
    return this.series;
  }

  public void setSeries(List series) {
    this.series = series;
  }

  public Boolean getGraficarDesdeCeroEjeY() {
    return this.graficarDesdeCeroEjeY;
  }

  public void setGraficarDesdeCeroEjeY(Boolean graficarDesdeCeroEjeY) {
    this.graficarDesdeCeroEjeY = graficarDesdeCeroEjeY;
  }

  public Boolean getYAxisCero() {
    return this.yAxisCero;
  }

  public void setYAxisCero(Boolean yAxisCero) {
    this.yAxisCero = yAxisCero;
  }

  public String getLn() {
    return this.ln;
  }

  public void setLn(String ln) {
    this.ln = ln;
  }

  public String getUnidad() {
    return this.unidad;
  }

  public void setUnidad(String unidad) {
    this.unidad = unidad;
  }

  public String getValores() {
    return this.valores;
  }

  public void setValores(String valores) {
    this.valores = valores;
  }

  public String getHayArea() {
    return this.hayArea;
  }

  public void setHayArea(String hayArea) {
    this.hayArea = hayArea;
  }

  public List getAnosPeriodos() {
    return this.anosPeriodos;
  }

  public void setAnosPeriodos(List anosPeriodos) {
    this.anosPeriodos = anosPeriodos;
  }

  public Integer getNumeroSeriesVisibles() {
    return this.numeroSeriesVisibles;
  }

  public void setNumeroSeriesVisibles(Integer numeroSeriesVisibles) {
    this.numeroSeriesVisibles = numeroSeriesVisibles;
  }

  public Integer getNumeroTotalPeriodos() {
    return this.numeroTotalPeriodos;
  }

  public void setNumeroTotalPeriodos(Integer numeroTotalPeriodos) {
    this.numeroTotalPeriodos = numeroTotalPeriodos;
  }

  public String getSeparador() {
    return "Ç";
  }

  public Long getObjetoId() {
    return this.objetoId;
  }

  public void setObjetoId(Long objetoId) {
    this.objetoId = objetoId;
  }

  public Long getCeldaId() {
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

  public Long getVistaId() {
    return this.vistaId;
  }

  public void setVistaId(Long vistaId) {
    this.vistaId = vistaId;
  }

  public Long getPreviaCeldaId() {
    return this.previaCeldaId;
  }

  public void setPreviaCeldaId(Long previaCeldaId) {
    this.previaCeldaId = previaCeldaId;
  }

  public Long getSiguienteCeldaId() {
    return this.siguienteCeldaId;
  }

  public void setSiguienteCeldaId(Long siguienteCeldaId) {
    this.siguienteCeldaId = siguienteCeldaId;
  }

  public Celda getCelda() {
    return this.celda;
  }

  public void setCelda(Celda celda) {
    this.celda = celda;
  }

  public Pagina getPagina() {
    return this.pagina;
  }

  public void setPagina(Pagina pagina) {
    this.pagina = pagina;
  }

  public Vista getVista() {
    return this.vista;
  }

  public void setVista(Vista vista) {
    this.vista = vista;
  }

  public Integer getTotalIndicadoresCelda() {
    return this.totalIndicadoresCelda;
  }

  public void setTotalIndicadoresCelda(Integer totalIndicadoresCelda) {
    this.totalIndicadoresCelda = totalIndicadoresCelda;
  }

  public void clear() {
    this.anoInicial = new Integer(0);

    this.anoFinal = new Integer(0);

    this.periodoInicialAnt = new Integer(0);

    this.periodoFinalAnt = new Integer(0);

    this.anoInicialAnt = new Integer(0);

    this.anoFinalAnt = new Integer(0);

    this.periodoInicial = new Integer(0);

    this.periodoFinal = new Integer(0);

    this.numeroMaximoPeriodos = new Integer(0);

    this.frecuencia = Frecuencia.getFrecuenciaMensual();

    this.indicador = null;

    this.frecuencias = new ArrayList();

    this.tiposSerie = new ArrayList();

    this.anosPeriodos = new ArrayList();

    this.graficarDesdeCeroEjeY = new Boolean(false);

    this.series = new ArrayList();

    this.yAxisCero = new Boolean(false);

    this.ln = "";

    this.unidad = "";

    this.valores = "";

    this.hayArea = "";

    this.numeroSeriesVisibles = new Integer(0);

    this.numeroTotalPeriodos = new Integer(0);

    this.objetoId = new Long(0L);

    this.vistaId = new Long(0L);

    this.paginaId = new Long(0L);

    this.celdaId = new Long(0L);
  }
}