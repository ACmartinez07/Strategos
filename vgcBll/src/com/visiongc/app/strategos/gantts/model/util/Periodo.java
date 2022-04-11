package com.visiongc.app.strategos.gantts.model.util;

import com.visiongc.app.strategos.model.util.Frecuencia;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Periodo
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Byte frecuencia;
  private Calendar fechaDesde;
  private Calendar fechaHasta;
  private Integer numeroPeriodo;
  private Integer anoPeriodo;
  private Integer pixeles;
  private Integer periodosAno;
  
  public Periodo(Calendar fechaDesde, Calendar fechaHasta, Integer numeroPeriodo, Integer anoPeriodo, Integer pixeles)
  {
    this.fechaDesde = fechaDesde;
    this.fechaHasta = fechaHasta;
    this.numeroPeriodo = numeroPeriodo;
    this.anoPeriodo = anoPeriodo;
    this.pixeles = pixeles;
  }
  

  public Periodo() {}
  

  public String getStrPeriodo()
  {
    String strPeriodo = "";
    
    if (frecuencia.equals(Frecuencia.getFrecuenciaDiaria())) {
      if (numeroPeriodo != null)
      {
        Calendar fecha = Calendar.getInstance();
        
        fecha.set(1, anoPeriodo.intValue());
        fecha.set(6, numeroPeriodo.intValue());
        
        SimpleDateFormat formateadorFecha = new SimpleDateFormat("E dd MMM");
        strPeriodo = formateadorFecha.format(
          new Date(fecha
          .getTimeInMillis()));
      }
      
    }
    else if (frecuencia.equals(Frecuencia.getFrecuenciaMensual())) {
      if (numeroPeriodo != null)
      {
        Calendar fecha = Calendar.getInstance();
        
        fecha.set(1, anoPeriodo.intValue());
        fecha.set(2, numeroPeriodo.intValue() - 1);
        fecha.set(5, 1);
        
        SimpleDateFormat formateadorFecha = new SimpleDateFormat("MMM");
        strPeriodo = formateadorFecha.format(
          new Date(fecha
          .getTimeInMillis()));
      }
    }
    else if (!frecuencia.equals(Frecuencia.getFrecuenciaAnual())) {
      if (numeroPeriodo != null)
      {
        int factorFrecuencia = 0;
        
        if (frecuencia.equals(Frecuencia.getFrecuenciaBimensual())) {
          factorFrecuencia = 2;
        } else if (frecuencia.equals(Frecuencia.getFrecuenciaTrimestral())) {
          factorFrecuencia = 3;
        } else if (frecuencia.equals(Frecuencia.getFrecuenciaCuatrimestral())) {
          factorFrecuencia = 4;
        } else if (frecuencia.equals(Frecuencia.getFrecuenciaSemestral())) {
          factorFrecuencia = 6;
        }
        
        Calendar fechaInicio = Calendar.getInstance();
        fechaInicio = Calendar.getInstance();
        fechaInicio.set(1, anoPeriodo.intValue());
        fechaInicio.set(2, factorFrecuencia * numeroPeriodo.intValue() - factorFrecuencia);
        fechaInicio.set(5, 1);
        
        SimpleDateFormat formateadorFecha = new SimpleDateFormat("MMM");
        strPeriodo = formateadorFecha.format(
          new Date(fechaInicio
          .getTimeInMillis()));
        
        Calendar fechaFin = Calendar.getInstance();
        fechaFin = Calendar.getInstance();
        fechaFin.set(1, anoPeriodo.intValue());
        fechaFin.set(2, factorFrecuencia * numeroPeriodo.intValue() - 1);
        fechaFin.set(5, 1);
        
        formateadorFecha = new SimpleDateFormat("MMM");
        strPeriodo = strPeriodo + "-" + formateadorFecha.format(
          new Date(fechaFin
          .getTimeInMillis()));
      }
    }
    else if (numeroPeriodo != null) {
      strPeriodo = numeroPeriodo.toString();
      
      if (strPeriodo.length() == 1) {
        strPeriodo = "0" + strPeriodo;
      }
    }
    

    return strPeriodo;
  }
  
  public Integer getNumeroPeriodo() {
    return numeroPeriodo;
  }
  
  public void setNumeroPeriodo(Integer numeroPeriodo) {
    this.numeroPeriodo = numeroPeriodo;
  }
  
  public Integer getAnoPeriodo() {
    return anoPeriodo;
  }
  
  public void setAnoPeriodo(Integer anoPeriodo) {
    this.anoPeriodo = anoPeriodo;
  }
  
  public Integer getPixeles() {
    return pixeles;
  }
  
  public void setPixeles(Integer pixeles) {
    this.pixeles = pixeles;
  }
  
  public Calendar getFechaDesde() {
    return fechaDesde;
  }
  
  public void setFechaDesde(Calendar fechaDesde) {
    this.fechaDesde = fechaDesde;
  }
  
  public Calendar getFechaHasta() {
    return fechaHasta;
  }
  
  public void setFechaHasta(Calendar fechaHasta) {
    this.fechaHasta = fechaHasta;
  }
  
  public Integer getPeriodosAno() {
    return periodosAno;
  }
  
  public void setPeriodosAno(Integer periodosAno) {
    this.periodosAno = periodosAno;
  }
  
  public Byte getFrecuencia() {
    return frecuencia;
  }
  
  public void setFrecuencia(Byte frecuencia) {
    this.frecuencia = frecuencia;
  }
}
