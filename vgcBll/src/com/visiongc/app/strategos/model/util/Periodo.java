package com.visiongc.app.strategos.model.util;

import java.io.Serializable;
import java.util.Calendar;

public class Periodo
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Calendar fechaInicio;
  private Calendar fechaFinal;
  private int numeroPeriodo;
  private int anoPeriodo;
  
  public Periodo(Calendar fechaInicio, Calendar fechaFinal, int numeroPeriodo, int anoPeriodo)
  {
    this.fechaInicio = fechaInicio;
    this.fechaFinal = fechaFinal;
    this.numeroPeriodo = numeroPeriodo;
    this.anoPeriodo = anoPeriodo;
  }
  

  public Periodo() {}
  

  public Calendar getFechaInicio()
  {
    return fechaInicio;
  }
  
  public void setFechaInicio(Calendar fechaInicio) {
    this.fechaInicio = fechaInicio;
  }
  
  public Calendar getFechaFinal() {
    return fechaFinal;
  }
  
  public void setFechaFinal(Calendar fechaFinal) {
    this.fechaFinal = fechaFinal;
  }
  
  public int getNumeroPeriodo() {
    return numeroPeriodo;
  }
  
  public void setNumeroPeriodo(int numeroPeriodo) {
    this.numeroPeriodo = numeroPeriodo;
  }
  
  public int getAnoPeriodo() {
    return anoPeriodo;
  }
  
  public void setAnoPeriodo(int anoPeriodo) {
    this.anoPeriodo = anoPeriodo;
  }
}
