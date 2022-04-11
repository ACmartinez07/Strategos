package com.visiongc.app.strategos.gantts.util;

import com.visiongc.app.strategos.gantts.model.util.ActividadPeriodo;
import com.visiongc.app.strategos.gantts.model.util.Periodo;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class VgcGanttGrafico
{
  public VgcGanttGrafico() {}
  
  public static boolean rangoFechasValido(Calendar fechaDesde, Calendar fechaHasta)
  {
    int diaDesde = fechaDesde.get(5);
    int mesDesde = fechaDesde.get(2) + 1;
    int anoDesde = fechaDesde.get(1);
    
    int diaHasta = fechaHasta.get(5);
    int mesHasta = fechaHasta.get(2) + 1;
    int anoHasta = fechaHasta.get(1);
    
    boolean rangoValido = (anoHasta > anoDesde) || ((anoHasta == anoDesde) && (mesHasta > mesDesde)) || ((anoHasta == anoDesde) && (mesHasta == mesDesde) && (diaHasta >= diaDesde));
    
    return rangoValido;
  }
  
  public static List<List<Periodo>> obtenerEscalaPeriodos(Calendar fechaDesde, Calendar fechaHasta, Byte frecuencia, boolean escalaSuperiorDoble)
  {
    List<List<Periodo>> escala = new java.util.ArrayList();
    List<Periodo> escalaInferior = new java.util.ArrayList();
    List<Periodo> escalaSuperior = new java.util.ArrayList();
    
    int diaDesde = fechaDesde.get(5);
    int mesDesde = fechaDesde.get(2) + 1;
    int anoDesde = fechaDesde.get(1);
    Periodo periodoDesde = obtenerPeriodoFecha(fechaDesde, frecuencia);
    
    int diaHasta = fechaHasta.get(5);
    int mesHasta = fechaHasta.get(2) + 1;
    int anoHasta = fechaHasta.get(1);
    Periodo periodoHasta = obtenerPeriodoFecha(fechaHasta, frecuencia);
    
    int mesCicloDesde = 0;
    int mesCicloHasta = 0;
    int periodoCicloDesde = 0;
    int periodoCicloHasta = 0;
    int pixelesPeriodo = obtenerPixelesPeriodo(frecuencia);
    int pixelesPeriodosAcumulados = 0;
    
    Periodo objetoPeriodo = new Periodo();
    
    if (escalaSuperiorDoble)
    {
      for (int ano = anoDesde; ano <= anoHasta; ano++)
      {
        mesCicloDesde = 1;
        mesCicloHasta = 12;
        
        if (ano == anoDesde)
          mesCicloDesde = mesDesde;
        if (ano == anoHasta) {
          mesCicloHasta = mesHasta;
        }
        for (int mes = mesCicloDesde; mes <= mesCicloHasta; mes++)
        {
          periodoCicloDesde = 1;
          periodoCicloHasta = obtenerMaximoDia(new Integer(mes), new Integer(ano));
          
          if ((ano == anoDesde) && (mes == mesDesde))
            periodoCicloDesde = diaDesde;
          if ((ano == anoHasta) && (mes == mesHasta)) {
            periodoCicloHasta = diaHasta;
          }
          pixelesPeriodosAcumulados = 0;
          for (int periodo = periodoCicloDesde; periodo <= periodoCicloHasta; periodo++)
          {
            objetoPeriodo = new Periodo();
            objetoPeriodo.setAnoPeriodo(new Integer(ano));
            objetoPeriodo.setNumeroPeriodo(new Integer(periodo));
            objetoPeriodo.setPixeles(new Integer(pixelesPeriodo));
            
            pixelesPeriodosAcumulados += pixelesPeriodo;
            objetoPeriodo.setFrecuencia(frecuencia);
            escalaInferior.add(objetoPeriodo);
          }
          
          objetoPeriodo = new Periodo();
          objetoPeriodo.setPeriodosAno(new Integer(periodoCicloHasta - periodoCicloDesde + 1));
          objetoPeriodo.setAnoPeriodo(new Integer(ano));
          objetoPeriodo.setNumeroPeriodo(new Integer(mes));
          objetoPeriodo.setPixeles(new Integer(pixelesPeriodosAcumulados));
          objetoPeriodo.setFrecuencia(frecuencia);
          escalaSuperior.add(objetoPeriodo);
        }
      }
    } else {
      for (int ano = anoDesde; ano <= anoHasta; ano++)
      {
        periodoCicloDesde = 1;
        periodoCicloHasta = obtenerMaximoNumeroPeriodos(new Integer(ano), frecuencia);
        
        if (ano == anoDesde)
          periodoCicloDesde = periodoDesde.getNumeroPeriodo().intValue();
        if (ano == anoHasta) {
          periodoCicloHasta = periodoHasta.getNumeroPeriodo().intValue();
        }
        pixelesPeriodosAcumulados = 0;
        for (int periodo = periodoCicloDesde; periodo <= periodoCicloHasta; periodo++)
        {
          objetoPeriodo = new Periodo();
          objetoPeriodo.setAnoPeriodo(new Integer(ano));
          objetoPeriodo.setNumeroPeriodo(new Integer(periodo));
          objetoPeriodo.setPixeles(new Integer(pixelesPeriodo));
          
          pixelesPeriodosAcumulados += pixelesPeriodo;
          objetoPeriodo.setFrecuencia(frecuencia);
          escalaInferior.add(objetoPeriodo);
        }
        
        objetoPeriodo = new Periodo();
        objetoPeriodo.setPeriodosAno(new Integer(periodoCicloHasta - periodoCicloDesde + 1));
        objetoPeriodo.setAnoPeriodo(new Integer(ano));
        objetoPeriodo.setPixeles(new Integer(pixelesPeriodosAcumulados));
        objetoPeriodo.setFrecuencia(frecuencia);
        escalaSuperior.add(objetoPeriodo);
      }
    }
    escala.add(escalaSuperior);
    escala.add(escalaInferior);
    
    return escala;
  }
  
  public static boolean esBisiesto(Integer ano)
  {
    return ano.intValue() % 4 == 0;
  }
  
  public static int obtenerMaximoDia(Integer mes, Integer ano)
  {
    int maximoDia = 0;
    boolean bisiesto = esBisiesto(ano);
    switch (mes.intValue())
    {
    case 1: 
      maximoDia = 31;
      break;
    case 2: 
      maximoDia = 28;
      if (bisiesto)
        maximoDia = 29;
      break;
    case 3: 
      maximoDia = 31;
      break;
    case 4: 
      maximoDia = 30;
      break;
    case 5: 
      maximoDia = 31;
      break;
    case 6: 
      maximoDia = 30;
      break;
    case 7: 
      maximoDia = 31;
      break;
    case 8: 
      maximoDia = 31;
      break;
    case 9: 
      maximoDia = 30;
      break;
    case 10: 
      maximoDia = 31;
      break;
    case 11: 
      maximoDia = 30;
      break;
    case 12: 
      maximoDia = 31;
    }
    
    return maximoDia;
  }
  
  public static List<Calendar> obtenerFechasLimitePeriodo(Calendar fecha, Integer periodo, Integer ano, Byte frecuencia)
  {
    List<Calendar> fechasLimite = new java.util.ArrayList();
    
    Calendar fechaDesde = Calendar.getInstance();
    Calendar fechaHasta = Calendar.getInstance();
    
    if (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue())
    {
      fechaDesde = fecha;
      fechaHasta = fecha;
    }
    else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaMensual().byteValue())
    {
      fechaDesde.set(5, 1);
      fechaDesde.set(2, periodo.intValue() - 1);
      fechaDesde.set(1, ano.intValue());
      
      fechaHasta.set(5, obtenerMaximoDia(periodo, ano));
      fechaHasta.set(2, periodo.intValue() - 1);
      fechaHasta.set(1, ano.intValue());
    }
    else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaBimensual().byteValue())
    {
      fechaDesde.set(5, 1);
      fechaDesde.set(2, periodo.intValue() * 2 - 2);
      fechaDesde.set(1, ano.intValue());
      fechaHasta.set(5, obtenerMaximoDia(new Integer(periodo.intValue() * 2), ano));
      fechaHasta.set(2, periodo.intValue() * 2 - 1);
      fechaHasta.set(1, ano.intValue());
    }
    else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaTrimestral().byteValue())
    {
      fechaDesde.set(5, 1);
      fechaDesde.set(2, periodo.intValue() * 3 - 3);
      fechaDesde.set(1, ano.intValue());
      fechaHasta.set(5, obtenerMaximoDia(new Integer(periodo.intValue() * 3), ano));
      fechaHasta.set(2, periodo.intValue() * 3 - 1);
      fechaHasta.set(1, ano.intValue());
    }
    else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaCuatrimestral().byteValue())
    {
      fechaDesde.set(5, 1);
      fechaDesde.set(2, periodo.intValue() * 4 - 4);
      fechaDesde.set(1, ano.intValue());
      fechaHasta.set(5, obtenerMaximoDia(new Integer(periodo.intValue() * 4), ano));
      fechaHasta.set(2, periodo.intValue() * 4 - 1);
      fechaHasta.set(1, ano.intValue());
    }
    else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaSemestral().byteValue())
    {
      fechaDesde.set(5, 1);
      fechaDesde.set(2, periodo.intValue() * 6 - 6);
      fechaDesde.set(1, ano.intValue());
      fechaHasta.set(5, obtenerMaximoDia(new Integer(periodo.intValue() * 6), ano));
      fechaHasta.set(2, periodo.intValue() * 6 - 1);
      fechaHasta.set(1, ano.intValue());
    }
    else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaAnual().byteValue())
    {
      fechaDesde.set(5, 1);
      fechaDesde.set(2, 0);
      fechaDesde.set(1, ano.intValue());
      fechaHasta.set(5, obtenerMaximoDia(new Integer(12), ano));
      fechaHasta.set(2, 11);
      fechaHasta.set(1, ano.intValue());
    }
    
    fechasLimite.add(fechaDesde);
    fechasLimite.add(fechaHasta);
    
    return fechasLimite;
  }
  
  public static int obtenerPixelesPeriodoEntreFechas(Calendar fechaDesde, Calendar fechaHasta, Integer pixelesPeriodo, Byte frecuencia)
  {
    int diferenciaPeriodos = obtenerDiferenciaPeriodos(fechaDesde, fechaHasta, frecuencia);
    int pixeles = diferenciaPeriodos * pixelesPeriodo.intValue();
    
    return pixeles;
  }
  
  public static int obtenerPixelesEntreFechas(Calendar fechaDesde, Calendar fechaHasta, Integer pixelesPeriodo, Byte frecuencia)
  {
    int pixeles = 0;
    
    if (frecuencia.equals(Frecuencia.getFrecuenciaDiaria())) {
      pixeles = obtenerPixelesPeriodoEntreFechas(fechaDesde, fechaHasta, pixelesPeriodo, frecuencia);
    }
    else {
      int diferenciaMeses = obtenerDiferenciaPeriodos(fechaDesde, fechaHasta, Frecuencia.getFrecuenciaMensual());
      
      int diferenciaDias = 0;
      int diaDesde = fechaDesde.get(5);
      int diaHasta = fechaHasta.get(5);
      
      if (diferenciaMeses == 0)
      {
        diferenciaDias = diaHasta - diaDesde;
        diferenciaDias = diferenciaMeses * 30 + diferenciaDias;
      }
      else
      {
        diferenciaDias = diaHasta;
        
        if (diferenciaDias == 31)
          diferenciaDias = 30;
        diferenciaDias = diferenciaMeses * 30 - diaDesde + diferenciaDias;
      }
      
      int variante = 0;
      switch (frecuencia.byteValue())
      {
      case 0: 
        variante = 0;
        break;
      case 3: 
        variante = 0;
        break;
      case 4: 
        variante = 90;
        break;
      case 5: 
        variante = 180;
        break;
      case 6: 
        variante = 60;
        break;
      case 7: 
        variante = 240;
        break;
      case 8: 
        variante = 240;
      }
      
      
      pixeles = diferenciaDias * obtenerPixelesDia(pixelesPeriodo, frecuencia) + (diferenciaDias != 0 ? variante : diferenciaDias);
    }
    
    return pixeles;
  }
  
  public static int obtenerPixelesDia(Integer pixelesPeriodo, Byte frecuencia)
  {
    int pixelesDia = 0;
    
    if (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()) {
      pixelesDia = pixelesPeriodo.intValue();
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaMensual().byteValue()) {
      pixelesDia = pixelesPeriodo.intValue() / 30;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaBimensual().byteValue()) {
      pixelesDia = pixelesPeriodo.intValue() / 60;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaTrimestral().byteValue()) {
      pixelesDia = pixelesPeriodo.intValue() / 90;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaCuatrimestral().byteValue()) {
      pixelesDia = pixelesPeriodo.intValue() / 120;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaSemestral().byteValue()) {
      pixelesDia = pixelesPeriodo.intValue() / 180;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaAnual().byteValue()) {
      pixelesDia = pixelesPeriodo.intValue() / 360;
    }
    return pixelesDia;
  }
  
  public static int obtenerPixelesPeriodo(Byte frecuencia)
  {
    int pixelesPeriodo = 0;
    
    if (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()) {
      pixelesPeriodo = 90;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaMensual().byteValue()) {
      pixelesPeriodo = 60;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaBimensual().byteValue()) {
      pixelesPeriodo = 120;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaTrimestral().byteValue()) {
      pixelesPeriodo = 180;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaCuatrimestral().byteValue()) {
      pixelesPeriodo = 120;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaSemestral().byteValue()) {
      pixelesPeriodo = 180;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaAnual().byteValue()) {
      pixelesPeriodo = 360;
    }
    return pixelesPeriodo;
  }
  
  public static int obtenerMaximoNumeroPeriodos(Integer ano, Byte frecuencia)
  {
    int maximoPeriodo = 0;
    boolean bisiesto = esBisiesto(ano);
    
    if (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue())
    {
      if (bisiesto) {
        maximoPeriodo = 366;
      } else {
        maximoPeriodo = 365;
      }
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaMensual().byteValue()) {
      maximoPeriodo = 12;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaBimensual().byteValue()) {
      maximoPeriodo = 6;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaTrimestral().byteValue()) {
      maximoPeriodo = 4;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaCuatrimestral().byteValue()) {
      maximoPeriodo = 3;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaSemestral().byteValue()) {
      maximoPeriodo = 2;
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaAnual().byteValue()) {
      maximoPeriodo = 1;
    }
    return maximoPeriodo;
  }
  
  public static Periodo obtenerPeriodoFecha(Calendar fecha, Byte frecuencia)
  {
    Periodo periodo = new Periodo();
    
    int dia = fecha.get(5);
    int mes = fecha.get(2) + 1;
    int ano = fecha.get(1);
    
    int periodoActual = 0;
    
    for (int mesBase = 1; mesBase <= mes; mesBase++)
    {
      if (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue())
      {
        if (mesBase < mes)
          periodoActual += obtenerMaximoDia(new Integer(mesBase), new Integer(ano)); else {
          periodoActual += dia;
        }
      } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaMensual().byteValue()) {
        periodoActual++;
      } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaBimensual().byteValue())
      {
        if (mesBase < mes) {
          periodoActual = mesBase % 2 == 0 ? periodoActual + 1 : periodoActual;
        } else {
          periodoActual++;
        }
      } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaTrimestral().byteValue())
      {
        if (mesBase < mes) {
          periodoActual = mesBase % 3 == 0 ? periodoActual + 1 : periodoActual;
        } else {
          periodoActual++;
        }
      } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaCuatrimestral().byteValue())
      {
        if (mesBase < mes) {
          periodoActual = mesBase % 4 == 0 ? periodoActual + 1 : periodoActual;
        } else {
          periodoActual++;
        }
      } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaSemestral().byteValue())
      {
        if (mesBase < mes) {
          periodoActual = mesBase % 6 == 0 ? periodoActual + 1 : periodoActual;
        } else {
          periodoActual++;
        }
        
      }
      else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaAnual().byteValue()) {
        periodoActual++;
        break;
      }
    }
    
    periodo.setNumeroPeriodo(new Integer(periodoActual));
    periodo.setAnoPeriodo(new Integer(ano));
    
    List<Calendar> fechasLimite = obtenerFechasLimitePeriodo(fecha, periodo.getNumeroPeriodo(), new Integer(ano), frecuencia);
    
    periodo.setFechaDesde((Calendar)fechasLimite.get(0));
    periodo.setFechaHasta((Calendar)fechasLimite.get(1));
    
    return periodo;
  }
  
  public static int obtenerDiferenciaPeriodos(Calendar fechaDesde, Calendar fechaHasta, Byte frecuencia)
  {
    int diferenciaPeriodos = 0;
    
    Periodo periodoDesde = obtenerPeriodoFecha(fechaDesde, frecuencia);
    Periodo periodoHasta = obtenerPeriodoFecha(fechaHasta, frecuencia);
    
    int anoDesde = fechaDesde.get(1);
    int anoHasta = fechaHasta.get(1);
    
    if (anoDesde == anoHasta) {
      diferenciaPeriodos = periodoHasta.getNumeroPeriodo().intValue() - periodoDesde.getNumeroPeriodo().intValue();
    } else if (anoDesde < anoHasta)
    {
      int maximoNumeroPeriodos = obtenerMaximoNumeroPeriodos(new Integer(anoDesde), frecuencia);
      diferenciaPeriodos = maximoNumeroPeriodos - periodoDesde.getNumeroPeriodo().intValue();
      
      for (int anoBase = anoDesde + 1; anoBase < anoHasta; anoBase++)
      {
        maximoNumeroPeriodos = obtenerMaximoNumeroPeriodos(new Integer(anoBase), frecuencia);
        diferenciaPeriodos += maximoNumeroPeriodos;
      }
      
      diferenciaPeriodos += periodoHasta.getNumeroPeriodo().intValue();
    }
    
    return diferenciaPeriodos;
  }
  
  public static List<ActividadPeriodo> obtenerActividadesPeriodo(List<PryActividad> actividades, Calendar fechaInicioGantt, Byte frecuencia)
  {
    List<ActividadPeriodo> actividadesPeriodo = new java.util.ArrayList();
    int pixelesPeriodo = obtenerPixelesPeriodo(frecuencia);
    for (Iterator<PryActividad> i = actividades.iterator(); i.hasNext();)
    {
      PryActividad pryActividad = (PryActividad)i.next();
      
      Calendar fechaComienzoPlan = Calendar.getInstance();
      Calendar fechaFinPlan = Calendar.getInstance();
      fechaComienzoPlan.setTime(pryActividad.getComienzoPlan() == null ? fechaInicioGantt.getTime() : pryActividad.getComienzoPlan());
      fechaFinPlan.setTime(pryActividad.getFinPlan() == null ? fechaInicioGantt.getTime() : pryActividad.getFinPlan());
      
      Calendar fechaComienzoReal = Calendar.getInstance();
      Calendar fechaFinReal = Calendar.getInstance();
      fechaComienzoReal.setTime(pryActividad.getComienzoReal() == null ? fechaInicioGantt.getTime() : pryActividad.getComienzoReal());
      fechaFinReal.setTime(pryActividad.getFinReal() == null ? fechaInicioGantt.getTime() : pryActividad.getFinReal());
      
      int longitudLineaBasePlan = obtenerPixelesEntreFechas(fechaInicioGantt, fechaComienzoPlan, new Integer(pixelesPeriodo), frecuencia);
      int longitudLineaBaseReal = obtenerPixelesEntreFechas(fechaInicioGantt, fechaComienzoReal, new Integer(pixelesPeriodo), frecuencia);
      int longitudLineaTiempoPlan = obtenerPixelesEntreFechas(fechaComienzoPlan, fechaFinPlan, new Integer(pixelesPeriodo), frecuencia);
      int longitudLineaTiempoReal = obtenerPixelesEntreFechas(fechaComienzoReal, fechaFinReal, new Integer(pixelesPeriodo), frecuencia);
      
      if (longitudLineaBasePlan > 0)
        longitudLineaTiempoPlan += obtenerPixelesDia(new Integer(pixelesPeriodo), frecuencia);
      if (longitudLineaBaseReal > 0) {
        longitudLineaTiempoReal += obtenerPixelesDia(new Integer(pixelesPeriodo), frecuencia);
      }
      pryActividad.setTieneHijos(new Boolean(pryActividad.getHijos().size() > 0));
      
      ActividadPeriodo actividadPeriodo = new ActividadPeriodo();
      
      actividadPeriodo.setLongitudLineaBasePlan(new Integer(longitudLineaBasePlan));
      actividadPeriodo.setLongitudLineaBaseReal(new Integer(longitudLineaBaseReal));
      actividadPeriodo.setLongitudLineaTiempoPlan(new Integer(longitudLineaTiempoPlan));
      actividadPeriodo.setLongitudLineaTiempoReal(new Integer(longitudLineaTiempoReal));
      
      actividadPeriodo.setActividad(pryActividad);
      
      actividadesPeriodo.add(actividadPeriodo);
    }
    
    return actividadesPeriodo;
  }
  
  public static List<Calendar> obtenerFechaInicioFinEscala(List<PryActividad> actividades, Byte frecuencia)
  {
    List<Calendar> fechaInicioFin = new java.util.ArrayList();
    
    Calendar fechaMin = null;
    Calendar fechaMax = null;
    
    Calendar fechaComparacion = Calendar.getInstance();
    
    for (Iterator<PryActividad> i = actividades.iterator(); i.hasNext();)
    {
      PryActividad pryActividad = (PryActividad)i.next();
      
      if (pryActividad.getComienzoPlan() != null)
      {
        fechaComparacion.setTime(pryActividad.getComienzoPlan());
        if (fechaMin == null)
        {
          fechaMin = Calendar.getInstance();
          fechaMin.setTime(fechaComparacion.getTime());
          fechaMax = Calendar.getInstance();
          fechaMax.setTime(fechaComparacion.getTime());
        }
        
        if (fechaComparacion.compareTo(fechaMin) < 0)
          fechaMin.setTime(fechaComparacion.getTime());
        if (fechaComparacion.compareTo(fechaMax) > 0) {
          fechaMax.setTime(fechaComparacion.getTime());
        }
      }
      if (pryActividad.getFinPlan() != null)
      {
        fechaComparacion.setTime(pryActividad.getFinPlan());
        if (fechaMin == null)
        {
          fechaMin = Calendar.getInstance();
          fechaMin.setTime(fechaComparacion.getTime());
          fechaMax = Calendar.getInstance();
          fechaMax.setTime(fechaComparacion.getTime());
        }
        
        if (fechaComparacion.compareTo(fechaMin) < 0)
          fechaMin.setTime(fechaComparacion.getTime());
        if (fechaComparacion.compareTo(fechaMax) > 0) {
          fechaMax.setTime(fechaComparacion.getTime());
        }
      }
      if (pryActividad.getComienzoReal() != null)
      {
        fechaComparacion.setTime(pryActividad.getComienzoReal());
        if (fechaMin == null)
        {
          fechaMin = Calendar.getInstance();
          fechaMin.setTime(fechaComparacion.getTime());
          fechaMax = Calendar.getInstance();
          fechaMax.setTime(fechaComparacion.getTime());
        }
        
        if (fechaComparacion.compareTo(fechaMin) < 0)
          fechaMin.setTime(fechaComparacion.getTime());
        if (fechaComparacion.compareTo(fechaMax) > 0) {
          fechaMax.setTime(fechaComparacion.getTime());
        }
      }
      if (pryActividad.getFinReal() != null)
      {
        fechaComparacion.setTime(pryActividad.getFinReal());
        if (fechaMin == null)
        {
          fechaMin = Calendar.getInstance();
          fechaMin.setTime(fechaComparacion.getTime());
          fechaMax = Calendar.getInstance();
          fechaMax.setTime(fechaComparacion.getTime());
        }
        if (fechaComparacion.compareTo(fechaMin) < 0)
          fechaMin.setTime(fechaComparacion.getTime());
        if (fechaComparacion.compareTo(fechaMax) > 0) {
          fechaMax.setTime(fechaComparacion.getTime());
        }
      }
    }
    if (fechaMin == null)
    {
      fechaMin = Calendar.getInstance();
      fechaMin.set(5, 1);
      fechaMin.set(2, 0);
    }
    
    if (fechaMax == null)
    {
      fechaMax = Calendar.getInstance();
      fechaMax.set(5, 31);
      fechaMax.set(2, 11);
    }
    
    fechaInicioFin.add(fechaMin);
    fechaInicioFin.add(fechaMax);
    
    return fechaInicioFin;
  }
}
