package com.visiongc.app.strategos.web.struts.vistasdatos.forms;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;
import java.util.List;

public class SeleccionarTiempoForm extends SelectorListaForm
{
  static final long serialVersionUID = 0L;
  private Integer anoInicial;
  private Integer anoFinal;
  private Integer periodoInicial;
  private Integer periodoFinal;
  private String nombreFrecuencia;
  private Byte frecuencia;
  private List frecuencias;
  private Byte frecuenciaBloqueada;
  private List listaAnos;
  private List listaPeriodos;
  private List anosPeriodos;
  private Byte seleccionMultiple;

  public Byte getSeleccionMultiple()
  {
    return this.seleccionMultiple;
  }

  public void setSeleccionMultiple(Byte seleccionMultiple) {
    this.seleccionMultiple = seleccionMultiple;
  }

  public Integer getAnoFinal() {
    return this.anoFinal;
  }

  public void setAnoFinal(Integer anoFinal) {
    this.anoFinal = anoFinal;
  }

  public Integer getAnoInicial() {
    return this.anoInicial;
  }

  public void setAnoInicial(Integer anoInicial) {
    this.anoInicial = anoInicial;
  }

  public String getNombreFrecuencia()
  {
    return this.nombreFrecuencia;
  }

  public void setNombreFrecuencia(String nombreFrecuencia) {
    this.nombreFrecuencia = nombreFrecuencia;
  }

  public Byte getFrecuencia() {
    return this.frecuencia;
  }

  public void setFrecuencia(Byte frecuencia) {
    this.frecuencia = frecuencia;
  }

  public List getFrecuencias() {
    return this.frecuencias;
  }

  public void setFrecuencias(List frecuencias) {
    this.frecuencias = frecuencias;
  }

  public Integer getPeriodoFinal() {
    return this.periodoFinal;
  }

  public void setPeriodoFinal(Integer periodoFinal) {
    this.periodoFinal = periodoFinal;
  }

  public Integer getPeriodoInicial() {
    return this.periodoInicial;
  }

  public void setPeriodoInicial(Integer periodoInicial) {
    this.periodoInicial = periodoInicial;
  }

  public Byte getFrecuenciaBloqueada()
  {
    return this.frecuenciaBloqueada;
  }

  public void setFrecuenciaBloqueada(Byte frecuenciaBloqueada) {
    this.frecuenciaBloqueada = frecuenciaBloqueada;
  }

  public List getListaAnos() {
    return this.listaAnos;
  }

  public void setListaAnos(List listaAnos) {
    this.listaAnos = listaAnos;
  }

  public List getListaPeriodos() {
    return this.listaPeriodos;
  }

  public void setListaPeriodos(List listaPeriodos) {
    this.listaPeriodos = listaPeriodos;
  }

  public List getAnosPeriodos() {
    return this.anosPeriodos;
  }

  public void setAnosPeriodos(List anosPeriodos) {
    this.anosPeriodos = anosPeriodos;
  }

  public void clear()
  {
  }
}