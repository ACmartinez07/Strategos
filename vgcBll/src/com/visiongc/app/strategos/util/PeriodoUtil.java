package com.visiongc.app.strategos.util;

import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.model.util.Periodo;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PeriodoUtil
{
  private static final Integer[][][] matrizPeriodos = getMatrizPeriodos();
  
  public PeriodoUtil() {}
  
  public static List<LapsoTiempo> getLapsosTiempoEnPeriodosPorFrecuenciaMes(int anoInicio, int anoFinal, int mesInicio, int mesFinal) { List<LapsoTiempo> lapsos = new ArrayList();
    
    lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaDiaria().byteValue()));
    lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaSemanal().byteValue()));
    lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaQuincenal().byteValue()));
    lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaMensual().byteValue()));
    lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaBimensual().byteValue()));
    lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaTrimestral().byteValue()));
    lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaCuatrimestral().byteValue()));
    lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaSemestral().byteValue()));
    lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaAnual().byteValue()));
    
    return lapsos;
  }
  
  public static LapsoTiempo getLapsoTiempoEnPeriodosPorMes(int anoInicio, int anoFinal, int mesInicio, int mesFinal, byte frecuencia)
  {
    List<Periodo> periodos = new ArrayList();
    
    Periodo periodo = null;
    LapsoTiempo lapso = new LapsoTiempo();
    
    Calendar fechaInicio = fechaCalendario(anoInicio, mesInicio, 1, false);
    Calendar fechaFinal = fechaCalendario(anoFinal, mesFinal, ultimoDiaMes(mesFinal, anoFinal), true);
    
    periodos = generarPeriodosFrecuencia(anoInicio, frecuencia);
    
    int c = 1;
    for (Iterator<Periodo> iter = periodos.iterator(); iter.hasNext();)
    {
      periodo = (Periodo)iter.next();
      
      boolean mayorIgual = fechaInicio.getTimeInMillis() >= periodo.getFechaInicio().getTimeInMillis();
      boolean menorIgual = fechaInicio.getTimeInMillis() <= periodo.getFechaFinal().getTimeInMillis();
      
      if ((mayorIgual) && (menorIgual))
      {
        lapso.setPeriodoInicio(new Integer(c));
        break;
      }
      c++;
    }
    
    if (anoInicio != anoFinal) {
      periodos = generarPeriodosFrecuencia(anoFinal, frecuencia);
    }
    c = 1;
    for (Iterator<Periodo> iter = periodos.iterator(); iter.hasNext();)
    {
      periodo = (Periodo)iter.next();
      
      boolean mayorIgual = fechaFinal.getTimeInMillis() >= periodo.getFechaInicio().getTimeInMillis();
      boolean menorIgual = fechaFinal.getTimeInMillis() <= periodo.getFechaFinal().getTimeInMillis();
      
      if ((mayorIgual) && (menorIgual))
      {
        lapso.setPeriodoFin(new Integer(c));
        break;
      }
      c++;
    }
    
    if ((frecuencia == Frecuencia.getFrecuenciaSemanal().byteValue()) && (lapso.getPeriodoFin() == null)) {
      lapso.setPeriodoFin(new Integer(periodos.size()));
    }
    return lapso;
  }
  
  public static int getPeriodoMesAnoDesde(int ano, int mes, byte frecuencia)
  {
    List<Periodo> periodos = new ArrayList();
    
    Periodo periodo = null;
    
    Calendar fechaInicio = fechaCalendario(ano, mes, 1, false);
    
    periodos = generarPeriodosFrecuencia(ano, frecuencia);
    
    int c = 1;
    for (Iterator<Periodo> iter = periodos.iterator(); iter.hasNext();)
    {
      periodo = (Periodo)iter.next();
      
      boolean mayorIgual = fechaInicio.getTimeInMillis() >= periodo.getFechaInicio().getTimeInMillis();
      boolean menorIgual = fechaInicio.getTimeInMillis() <= periodo.getFechaFinal().getTimeInMillis();
      
      if ((mayorIgual) && (menorIgual))
        break;
      c++;
    }
    
    return c;
  }
  
  public static int getPeriodoMesAnoHasta(int ano, int mes, byte frecuencia)
  {
    List<Periodo> periodos = new ArrayList();
    
    int periodoFinal = 0;
    
    Periodo periodo = null;
    
    Calendar fechaFinal = fechaCalendario(ano, mes, ultimoDiaMes(mes, ano), true);
    
    periodos = generarPeriodosFrecuencia(ano, frecuencia);
    
    int c = 1;
    for (Iterator<Periodo> iter = periodos.iterator(); iter.hasNext();)
    {
      periodo = (Periodo)iter.next();
      
      boolean mayorIgual = fechaFinal.getTimeInMillis() >= periodo.getFechaInicio().getTimeInMillis();
      boolean menorIgual = fechaFinal.getTimeInMillis() <= periodo.getFechaFinal().getTimeInMillis();
      
      if ((mayorIgual) && (menorIgual))
      {
        periodoFinal = c;
        break;
      }
      c++;
    }
    
    if ((frecuencia == Frecuencia.getFrecuenciaSemanal().byteValue()) && (periodoFinal == 0)) {
      periodoFinal = periodos.size();
    }
    return periodoFinal;
  }
  
  public static Calendar fechaCalendario(int ano, int mes, int dia, boolean horaMaximaDia)
  {
    try
    {
      Calendar fecha = Calendar.getInstance();
      fecha.set(ano, mes - 1, dia);
      
      if (horaMaximaDia) {
        fecha = finDelDia(fecha);
      }
      return inicioDelDia(fecha);
    }
    catch (Exception localException) {}
    




    return null;
  }
  
  public static int ultimoDiaMes(int mes, int ano)
  {
    Calendar fecha = Calendar.getInstance();
    
    fecha.set(5, 1);
    
    fecha.set(2, mes);
    fecha.set(1, ano);
    
    fecha.add(5, -1);
    
    return fecha.get(5);
  }
  
  public static Date getFechaFinMes(int mes, int ano)
  {
    Calendar fecha = Calendar.getInstance();
    
    fecha.set(ano, mes, 1, 23, 59, 59);
    fecha.add(5, -1);
    
    return fecha.getTime();
  }
  
  public static Calendar getCalendarFinMes(int mes, int ano)
  {
    Calendar fecha = Calendar.getInstance();
    
    fecha.set(ano, mes, 1);
    fecha = finDelDia(fecha);
    fecha.add(5, -1);
    
    return fecha;
  }
  
  public static List<Periodo> generarPeriodosFrecuencia(int ano, byte frecuencia)
  {
    Periodo periodo = null;
    List<Periodo> colPeriodos = new ArrayList();
    int numeroPeriodo = 0;
    
    int diaFinal = -1;
    int mes = 0;
    
    Calendar fechaInicio = fechaCalendario(ano, 1, 1, false);
    Calendar fechaFinal = fechaCalendario(ano, 12, ultimoDiaMes(12, ano), true);
    Calendar fechaGuia = null;
    switch (frecuencia)
    {
    case 0: 
      fechaGuia = fechaInicio;
      fechaGuia = sumarDias(fechaGuia, -1, false);
      do
      {
        numeroPeriodo++;
        periodo = new Periodo();
        fechaGuia = sumarDias(fechaGuia, 1, false);
        periodo.setFechaInicio(fechaGuia);
        periodo.setAnoPeriodo(ano);
        periodo.setNumeroPeriodo(numeroPeriodo);
        Calendar fechaFinalPeriodo = (Calendar)fechaGuia.clone();
        periodo.setFechaFinal(finDelDia(fechaFinalPeriodo));
        fechaGuia = periodo.getFechaInicio();
        colPeriodos.add(periodo);
        
        if (periodo.getFechaInicio().equals(fechaFinal)) return colPeriodos;
      } while (!
      













        periodo.getFechaInicio().after(fechaFinal));
      break;
    case 1: 
      fechaGuia = fechaInicio;
      while (fechaGuia.before(fechaFinal))
      {
        periodo = new Periodo();
        
        numeroPeriodo++;
        periodo.setAnoPeriodo(ano);
        periodo.setNumeroPeriodo(numeroPeriodo);
        periodo.setFechaInicio(fechaGuia);
        Calendar fechaFinalPeriodo = null;
        if (fechaGuia.get(7) == 1) {
          fechaFinalPeriodo = sumarDias(fechaGuia, 6, true);
        } else
          fechaFinalPeriodo = sumarDias(fechaGuia, 7 - fechaGuia.get(7), true);
        periodo.setFechaFinal(fechaFinalPeriodo);
        fechaGuia = sumarDias(fechaFinalPeriodo, 1, false);
        
        colPeriodos.add(periodo);
      }
      break;
    case 2: 
      for (mes = 1; mes <= 12; mes++)
      {
        switch (mes)
        {
        case 1: 
        case 3: 
        case 5: 
        case 7: 
        case 8: 
        case 10: 
        case 12: 
          diaFinal = 31;
          break;
        case 2: 
          diaFinal = ultimoDiaMes(2, ano);
          break;
        case 4: 
        case 6: 
        case 9: 
        case 11: 
          diaFinal = 30;
        }
        periodo = new Periodo();
        periodo.setAnoPeriodo(ano);
        periodo.setNumeroPeriodo(mes * 2 - 1);
        periodo.setFechaInicio(fechaCalendario(ano, mes, 1, false));
        periodo.setFechaFinal(fechaCalendario(ano, mes, 15, true));
        colPeriodos.add(periodo);
        
        periodo = new Periodo();
        periodo.setAnoPeriodo(ano);
        periodo.setNumeroPeriodo(mes * 2);
        periodo.setFechaInicio(fechaCalendario(ano, mes, 16, false));
        periodo.setFechaFinal(fechaCalendario(ano, mes, diaFinal, true));
        
        colPeriodos.add(periodo);
      }
      break;
    case 3: 
      for (mes = 1; mes <= 12; mes++)
      {
        switch (mes)
        {
        case 1: 
        case 3: 
        case 5: 
        case 7: 
        case 8: 
        case 10: 
        case 12: 
          diaFinal = 31;
          break;
        case 2: 
          diaFinal = ultimoDiaMes(2, ano);
          break;
        case 4: 
        case 6: 
        case 9: 
        case 11: 
          diaFinal = 30;
        }
        
        periodo = new Periodo();
        periodo.setAnoPeriodo(ano);
        periodo.setNumeroPeriodo(mes);
        periodo.setFechaInicio(fechaCalendario(ano, mes, 1, false));
        periodo.setFechaFinal(fechaCalendario(ano, mes, diaFinal, true));
        colPeriodos.add(periodo);
      }
      break;
    case 4: 
      for (mes = 1; mes <= 12; mes++)
      {
        switch (mes)
        {
        case 1: 
        case 3: 
        case 5: 
        case 7: 
        case 8: 
        case 10: 
        case 12: 
          diaFinal = 31;
          break;
        case 2: 
          diaFinal = ultimoDiaMes(2, ano);
          break;
        case 4: 
        case 6: 
        case 9: 
        case 11: 
          diaFinal = 30;
        }
        
        if (mes % 2 != 0)
        {
          periodo = new Periodo();
          periodo.setFechaInicio(fechaCalendario(ano, mes, 1, false));
        }
        else
        {
          periodo.setAnoPeriodo(ano);
          periodo.setNumeroPeriodo(mes / 2);
          periodo.setFechaFinal(fechaCalendario(ano, mes, diaFinal, true));
          colPeriodos.add(periodo);
        }
      }
      break;
    case 5: 
      for (mes = 1; mes <= 12; mes++)
      {
        switch (mes)
        {
        case 1: 
        case 3: 
        case 5: 
        case 7: 
        case 8: 
        case 10: 
        case 12: 
          diaFinal = 31;
          break;
        case 2: 
          diaFinal = ultimoDiaMes(2, ano);
          break;
        case 4: 
        case 6: 
        case 9: 
        case 11: 
          diaFinal = 30;
        }
        
        if ((mes == 1) || (mes == 4) || (mes == 7) || (mes == 10))
        {
          periodo = new Periodo();
          periodo.setFechaInicio(fechaCalendario(ano, mes, 1, false));
        }
        if ((mes == 3) || (mes == 6) || (mes == 9) || (mes == 12))
        {
          periodo.setFechaFinal(fechaCalendario(ano, mes, diaFinal, true));
          periodo.setAnoPeriodo(ano);
          periodo.setNumeroPeriodo(mes / 3);
          colPeriodos.add(periodo);
        }
      }
      break;
    case 6: 
      for (mes = 1; mes <= 12; mes++)
      {
        switch (mes)
        {
        case 1: 
        case 3: 
        case 5: 
        case 7: 
        case 8: 
        case 10: 
        case 12: 
          diaFinal = 31;
          break;
        case 2: 
          diaFinal = ultimoDiaMes(2, ano);
          break;
        case 4: 
        case 6: 
        case 9: 
        case 11: 
          diaFinal = 30;
        }
        
        if ((mes == 1) || (mes == 5) || (mes == 9))
        {
          periodo = new Periodo();
          periodo.setFechaInicio(fechaCalendario(ano, mes, 1, false));
        }
        if ((mes == 4) || (mes == 8) || (mes == 12))
        {
          periodo.setFechaFinal(fechaCalendario(ano, mes, diaFinal, true));
          periodo.setAnoPeriodo(ano);
          periodo.setNumeroPeriodo(mes / 4);
          colPeriodos.add(periodo);
        }
      }
      break;
    case 7: 
      for (mes = 1; mes <= 12; mes++)
      {
        switch (mes)
        {
        case 1: 
        case 3: 
        case 5: 
        case 7: 
        case 8: 
        case 10: 
        case 12: 
          diaFinal = 31;
          break;
        case 2: 
          diaFinal = ultimoDiaMes(2, ano);
          break;
        case 4: 
        case 6: 
        case 9: 
        case 11: 
          diaFinal = 30;
        }
        
        if ((mes == 1) || (mes == 7))
        {
          periodo = new Periodo();
          periodo.setFechaInicio(fechaCalendario(ano, mes, 1, false));
        }
        if ((mes == 6) || (mes == 12))
        {
          periodo.setFechaFinal(fechaCalendario(ano, mes, diaFinal, true));
          periodo.setAnoPeriodo(ano);
          periodo.setNumeroPeriodo(mes / 6);
          colPeriodos.add(periodo);
        }
      }
      break;
    case 8: 
      periodo = new Periodo();
      periodo.setFechaInicio(fechaInicio);
      periodo.setFechaFinal(fechaFinal);
      periodo.setAnoPeriodo(ano);
      periodo.setNumeroPeriodo(1);
      
      colPeriodos.add(periodo);
    }
    
    return colPeriodos;
  }
  
  public static Calendar sumarDias(Calendar fecha, int ndays, boolean horaMaximaDia)
  {
    Calendar fecha2 = Calendar.getInstance();
    
    int day = fecha.get(5);
    int month = fecha.get(2);
    int year = fecha.get(1);
    
    fecha2.set(5, day);
    fecha2.set(2, month);
    fecha2.set(1, year);
    
    fecha2.add(5, ndays);
    
    if (horaMaximaDia) {
      return finDelDia(fecha2);
    }
    return inicioDelDia(fecha2);
  }
  
  public static Calendar inicioDelDia(Calendar fecha)
  {
    Calendar fecha2 = Calendar.getInstance();
    
    fecha2.set(fecha.get(1), fecha.get(2), fecha.get(5), 0, 0, 0);
    fecha2.set(14, 0);
    
    return fecha2;
  }
  
  public static Calendar finDelDia(Calendar fecha)
  {
    Calendar fecha2 = Calendar.getInstance();
    
    fecha2.set(fecha.get(1), fecha.get(2), fecha.get(5), 23, 59, 59);
    fecha2.set(14, 0);
    
    return fecha2;
  }
  
  public static int getNumeroMaximoPeriodosPorFrecuencia(byte frecuencia, int ano)
  {
    switch (frecuencia)
    {
    case 0: 
      if (ano == -1)
        return 365;
      if (ano % 4 == 0)
        return 366;
      return 365;
    case 1: 
      return 52;
    case 2: 
      return 24;
    case 3: 
      return 12;
    case 4: 
      return 6;
    case 5: 
      return 4;
    case 6: 
      return 3;
    case 7: 
      return 2;
    case 8: 
      return 1;
    }
    
    return -1;
  }
  
  public static int getPeriodoDeFecha(Calendar fecha, byte frecuencia)
  {
    int periodoCalculado = 0;
    boolean mayorIgual = false;
    boolean menorIgual = false;
    Periodo periodo = null;
    
    List<Periodo> periodos = generarPeriodosFrecuencia(fecha.get(1), frecuencia);
    
    int c = 1;
    for (Iterator<Periodo> ite = periodos.iterator(); ite.hasNext();)
    {
      periodo = (Periodo)ite.next();
      
      Calendar fechaOriginal = inicioDelDia(fecha);
      Calendar fechaFechaInicio = inicioDelDia(periodo.getFechaInicio());
      Calendar fechaFechaFinal = finDelDia(periodo.getFechaFinal());
      
      mayorIgual = (fechaOriginal.after(fechaFechaInicio)) || (fechaOriginal.equals(fechaFechaInicio));
      menorIgual = (fechaOriginal.before(fechaFechaFinal)) || (fechaOriginal.equals(fechaFechaFinal));
      
      if ((mayorIgual) && (menorIgual))
      {
        periodoCalculado = c;
        break;
      }
      c++;
    }
    
    return periodoCalculado;
  }
  
  public static int getPeriodoDate(Date fecha, byte frecuencia)
  {
    Calendar fechaCal = Calendar.getInstance();
    fechaCal.setTime(fecha);
    
    return getPeriodoDeFecha(fechaCal, frecuencia);
  }
  
  public void ConvertirPeriodosPorFrecuencia(byte inSrcFrecuencia, int inSrcAnoDesde, int inSrPeriodoDesde, int inSrcAnoHasta, int inSrPeriodoHasta, byte inDstFrecuencia, int[] outPerDesde, int[] outPerHasta) throws Exception
  {
    String CLASS_METHOD = "ControlPeriodo.ConvertirPeriodosPorFrecuencia";
    
    List<Periodo> SrPeriodo = new ArrayList();
    List<Periodo> DstPeriodo = new ArrayList();
    try
    {
      int NumMaxPer = getNumeroMaximoPeriodosPorFrecuencia(inSrcFrecuencia, inSrcAnoHasta);
      
      if (inSrPeriodoHasta > NumMaxPer) {
        inSrPeriodoHasta = NumMaxPer;
      }
      SrPeriodo = generarPeriodosFrecuencia(inSrcAnoDesde, inSrcFrecuencia);
      DstPeriodo = generarPeriodosFrecuencia(inSrcAnoDesde, inDstFrecuencia);
      
      for (int i = 0; i < DstPeriodo.size(); i++)
      {
        if (((Periodo)SrPeriodo.get(inSrPeriodoDesde - 1)).getFechaInicio().before(((Periodo)DstPeriodo.get(i)).getFechaInicio()))
        {
          outPerDesde[0] = (i + 1);
          break;
        }
        if (((Periodo)SrPeriodo.get(inSrPeriodoDesde - 1)).getFechaInicio().equals(((Periodo)DstPeriodo.get(i)).getFechaInicio()))
        {
          outPerDesde[0] = (i + 1);
          break;
        }
      }
      
      if (inSrcAnoDesde != inSrcAnoHasta)
      {
        SrPeriodo = generarPeriodosFrecuencia(inSrcAnoHasta, inSrcFrecuencia);
        DstPeriodo = generarPeriodosFrecuencia(inSrcAnoHasta, inDstFrecuencia);
      }
      
      for (int i = 0; i < DstPeriodo.size(); i++)
      {
        if (((Periodo)SrPeriodo.get(inSrPeriodoHasta - 1)).getFechaFinal().before(((Periodo)DstPeriodo.get(i)).getFechaFinal()))
        {
          outPerHasta[0] = (i + 1);
          break;
        }
        if (((Periodo)SrPeriodo.get(inSrPeriodoHasta - 1)).getFechaFinal().equals(((Periodo)DstPeriodo.get(i)).getFechaFinal()))
        {
          outPerHasta[0] = (i + 1);
          break;
        }
      }
    }
    catch (Exception e) {
      throw new Exception(CLASS_METHOD + "\\" + e.getMessage());
    }
  }
  
  public static int getNumeroPeriodosPorFrecuenciaEnFrecuencia(int frecuencia, int frecuenciaContenida)
  {
    if (frecuencia == Frecuencia.getFrecuenciaDiaria().byteValue())
    {
      if (frecuenciaContenida == Frecuencia.getFrecuenciaDiaria().byteValue()) {
        return 1;
      }
    } else if (frecuencia == Frecuencia.getFrecuenciaSemanal().byteValue())
    {
      if (frecuenciaContenida == Frecuencia.getFrecuenciaDiaria().byteValue())
        return 7;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaSemanal().byteValue()) {
        return 1;
      }
    } else if (frecuencia == Frecuencia.getFrecuenciaQuincenal().byteValue())
    {
      if (frecuenciaContenida == Frecuencia.getFrecuenciaDiaria().byteValue())
        return 15;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaSemanal().byteValue())
        return 2;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaQuincenal().byteValue()) {
        return 1;
      }
    } else if (frecuencia == Frecuencia.getFrecuenciaMensual().byteValue())
    {
      if (frecuenciaContenida == Frecuencia.getFrecuenciaDiaria().byteValue())
        return 30;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaSemanal().byteValue())
        return 4;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaQuincenal().byteValue())
        return 2;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaMensual().byteValue()) {
        return 1;
      }
    } else if (frecuencia == Frecuencia.getFrecuenciaBimensual().byteValue())
    {
      if (frecuenciaContenida == Frecuencia.getFrecuenciaDiaria().byteValue())
        return 60;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaSemanal().byteValue())
        return 8;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaQuincenal().byteValue())
        return 4;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaMensual().byteValue())
        return 2;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaBimensual().byteValue()) {
        return 1;
      }
    } else if (frecuencia == Frecuencia.getFrecuenciaTrimestral().byteValue())
    {
      if (frecuenciaContenida == Frecuencia.getFrecuenciaDiaria().byteValue())
        return 90;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaSemanal().byteValue())
        return 13;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaQuincenal().byteValue())
        return 6;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaMensual().byteValue())
        return 3;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaBimensual().byteValue())
        return 0;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaTrimestral().byteValue()) {
        return 1;
      }
    } else if (frecuencia == Frecuencia.getFrecuenciaCuatrimestral().byteValue())
    {
      if (frecuenciaContenida == Frecuencia.getFrecuenciaDiaria().byteValue())
        return 120;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaSemanal().byteValue())
        return 17;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaQuincenal().byteValue())
        return 8;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaMensual().byteValue())
        return 4;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaBimensual().byteValue())
        return 2;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaTrimestral().byteValue())
        return 0;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaCuatrimestral().byteValue()) {
        return 1;
      }
    } else if (frecuencia == Frecuencia.getFrecuenciaSemestral().byteValue())
    {
      if (frecuenciaContenida == Frecuencia.getFrecuenciaDiaria().byteValue())
        return 182;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaSemanal().byteValue())
        return 26;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaQuincenal().byteValue())
        return 12;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaMensual().byteValue())
        return 6;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaBimensual().byteValue())
        return 3;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaTrimestral().byteValue())
        return 2;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaCuatrimestral().byteValue())
        return 0;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaSemestral().byteValue()) {
        return 1;
      }
    } else if (frecuencia == Frecuencia.getFrecuenciaAnual().byteValue())
    {
      if (frecuenciaContenida == Frecuencia.getFrecuenciaDiaria().byteValue())
        return 365;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaSemanal().byteValue())
        return 52;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaQuincenal().byteValue())
        return 24;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaMensual().byteValue())
        return 12;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaBimensual().byteValue())
        return 6;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaTrimestral().byteValue())
        return 4;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaCuatrimestral().byteValue())
        return 3;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaSemestral().byteValue())
        return 2;
      if (frecuenciaContenida == Frecuencia.getFrecuenciaAnual().byteValue()) {
        return 1;
      }
    }
    return -1;
  }
  
  public static void contencionPeriodosFrecuenciaEnFrecuencia(int Frecuencia1, int Frecuencia2, int periodoDesde, int periodoHasta, int[] periodoInicioDesde, int[] periodoFinalDesde, int[] periodoInicioHasta, int[] periodoFinalHasta)
  {
    int valor = 0;
    int multiploAgrupacion = 0;
    
    multiploAgrupacion = getNumeroPeriodosPorFrecuenciaEnFrecuencia(Frecuencia1, Frecuencia2);
    valor = periodoDesde * multiploAgrupacion;
    periodoInicioDesde[0] = (valor - (multiploAgrupacion - 1));
    periodoFinalDesde[0] = valor;
    valor = periodoHasta * multiploAgrupacion;
    periodoInicioHasta[0] = (valor - (multiploAgrupacion - 1));
    periodoFinalHasta[0] = valor;
  }
  
  public static String convertirPeriodoToTexto(int periodo, byte frecuencia, int ano)
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    String texto = "";
    Calendar fecha = Calendar.getInstance();
    switch (frecuencia)
    {
    case 0: 
      List<Periodo> periodos = new ArrayList();
      periodos = generarPeriodosFrecuencia(ano, frecuencia);
      texto = messageResources.getResource("periodo.novalido");
      for (Iterator<Periodo> i = periodos.iterator(); i.hasNext();)
      {
        Periodo per = (Periodo)i.next();
        if (per.getNumeroPeriodo() == periodo)
        {
          SimpleDateFormat formateadorFecha = new SimpleDateFormat("E, dd/MM/yyyy");
          texto = formateadorFecha.format(new Date(per.getFechaInicio().getTimeInMillis()));
          break;
        }
      }
      break;
    case 1: 
      fecha = getDateByPeriodo(Byte.valueOf(frecuencia), ano, periodo, false);
      



      if (periodo == 1) {
        texto = messageResources.getResource("semana.uno");
      } else if (periodo == 2) {
        texto = messageResources.getResource("semana.dos");
      } else if (periodo == 3) {
        texto = messageResources.getResource("semana.tres");
      } else if (periodo == 4) {
        texto = messageResources.getResource("semana.cuatro");
      } else if (periodo == 5) {
        texto = messageResources.getResource("semana.cinco");
      } else if (periodo == 6) {
        texto = messageResources.getResource("semana.seis");
      } else if (periodo == 7) {
        texto = messageResources.getResource("semana.siete");
      } else if (periodo == 8) {
        texto = messageResources.getResource("semana.ocho");
      } else if (periodo == 9) {
        texto = messageResources.getResource("semana.nueve");
      } else if (periodo == 10) {
        texto = messageResources.getResource("semana.diez");
      } else if (periodo == 11) {
        texto = messageResources.getResource("semana.once");
      } else if (periodo == 12) {
        texto = messageResources.getResource("semana.doce");
      } else if (periodo == 13) {
        texto = messageResources.getResource("semana.trece");
      } else if (periodo == 14) {
        texto = messageResources.getResource("semana.catorce");
      } else if (periodo == 15) {
        texto = messageResources.getResource("semana.quince");
      } else if (periodo == 16) {
        texto = messageResources.getResource("semana.diezseis");
      } else if (periodo == 17) {
        texto = messageResources.getResource("semana.diezsiete");
      } else if (periodo == 18) {
        texto = messageResources.getResource("semana.diezocho");
      } else if (periodo == 19) {
        texto = messageResources.getResource("semana.dieznueve");
      } else if (periodo == 20) {
        texto = messageResources.getResource("semana.veinte");
      } else if (periodo == 21) {
        texto = messageResources.getResource("semana.veinteuno");
      } else if (periodo == 22) {
        texto = messageResources.getResource("semana.veintedos");
      } else if (periodo == 23) {
        texto = messageResources.getResource("semana.veintetres");
      } else if (periodo == 24) {
        texto = messageResources.getResource("semana.veintecuatro");
      } else if (periodo == 25) {
        texto = messageResources.getResource("semana.veintecinco");
      } else if (periodo == 26) {
        texto = messageResources.getResource("semana.veinteseis");
      } else if (periodo == 27) {
        texto = messageResources.getResource("semana.veintesiete");
      } else if (periodo == 28) {
        texto = messageResources.getResource("semana.veinteocho");
      } else if (periodo == 29) {
        texto = messageResources.getResource("semana.veintenueve");
      } else if (periodo == 30) {
        texto = messageResources.getResource("semana.treinta");
      } else if (periodo == 31) {
        texto = messageResources.getResource("semana.treintauno");
      } else if (periodo == 32) {
        texto = messageResources.getResource("semana.treintados");
      } else if (periodo == 33) {
        texto = messageResources.getResource("semana.treintatres");
      } else if (periodo == 34) {
        texto = messageResources.getResource("semana.treintacuatro");
      } else if (periodo == 35) {
        texto = messageResources.getResource("semana.treintacinco");
      } else if (periodo == 36) {
        texto = messageResources.getResource("semana.treintaseis");
      } else if (periodo == 37) {
        texto = messageResources.getResource("semana.treintasiete");
      } else if (periodo == 38) {
        texto = messageResources.getResource("semana.treintaocho");
      } else if (periodo == 39) {
        texto = messageResources.getResource("semana.treintanueve");
      } else if (periodo == 40) {
        texto = messageResources.getResource("semana.cuarenta");
      } else if (periodo == 41) {
        texto = messageResources.getResource("semana.cuarentauno");
      } else if (periodo == 42) {
        texto = messageResources.getResource("semana.cuarentados");
      } else if (periodo == 43) {
        texto = messageResources.getResource("semana.cuarentatres");
      } else if (periodo == 44) {
        texto = messageResources.getResource("semana.cuarentacuatro");
      } else if (periodo == 45) {
        texto = messageResources.getResource("semana.cuarentacinco");
      } else if (periodo == 46) {
        texto = messageResources.getResource("semana.cuarentaseis");
      } else if (periodo == 47) {
        texto = messageResources.getResource("semana.cuarentasiete");
      } else if (periodo == 48) {
        texto = messageResources.getResource("semana.cuarentaocho");
      } else if (periodo == 49) {
        texto = messageResources.getResource("semana.cuarentanueve");
      } else if (periodo == 50) {
        texto = messageResources.getResource("semana.cincuenta");
      } else if (periodo == 51) {
        texto = messageResources.getResource("semana.cincuentauno");
      } else if (periodo == 52) {
        texto = messageResources.getResource("semana.cincuentados");
      } else
        texto = messageResources.getResource("periodo.novalido");
      texto = texto + "(" + convertirPeriodoToTexto(fecha.get(2) + 1, Frecuencia.getFrecuenciaMensual().byteValue(), ano) + ")";
      break;
    case 2: 
      fecha = getDateByPeriodo(Byte.valueOf(frecuencia), ano, periodo, false);
      if (periodo == 1) {
        texto = messageResources.getResource("quincena.uno");
      } else if (periodo == 2) {
        texto = messageResources.getResource("quincena.dos");
      } else if (periodo == 3) {
        texto = messageResources.getResource("quincena.tres");
      } else if (periodo == 4) {
        texto = messageResources.getResource("quincena.cuatro");
      } else if (periodo == 5) {
        texto = messageResources.getResource("quincena.cinco");
      } else if (periodo == 6) {
        texto = messageResources.getResource("quincena.seis");
      } else if (periodo == 7) {
        texto = messageResources.getResource("quincena.siete");
      } else if (periodo == 8) {
        texto = messageResources.getResource("quincena.ocho");
      } else if (periodo == 9) {
        texto = messageResources.getResource("quincena.nueve");
      } else if (periodo == 10) {
        texto = messageResources.getResource("quincena.diez");
      } else if (periodo == 11) {
        texto = messageResources.getResource("quincena.once");
      } else if (periodo == 12) {
        texto = messageResources.getResource("quincena.dos");
      } else if (periodo == 13) {
        texto = messageResources.getResource("quincena.tres");
      } else if (periodo == 14) {
        texto = messageResources.getResource("quincena.catorce");
      } else if (periodo == 15) {
        texto = messageResources.getResource("quincena.quince");
      } else if (periodo == 16) {
        texto = messageResources.getResource("quincena.diezseis");
      } else if (periodo == 17) {
        texto = messageResources.getResource("quincena.diezsiete");
      } else if (periodo == 18) {
        texto = messageResources.getResource("quincena.diezocho");
      } else if (periodo == 19) {
        texto = messageResources.getResource("quincena.dieznueve");
      } else if (periodo == 20) {
        texto = messageResources.getResource("quincena.veinte");
      } else if (periodo == 21) {
        texto = messageResources.getResource("quincena.veinteuno");
      } else if (periodo == 22) {
        texto = messageResources.getResource("quincena.veintedos");
      } else if (periodo == 23) {
        texto = messageResources.getResource("quincena.veintetres");
      } else if (periodo == 24)
        texto = messageResources.getResource("quincena.veintecuatro"); else
        texto = messageResources.getResource("periodo.novalido");
      texto = texto + "(" + convertirPeriodoToTexto(fecha.get(2) + 1, Frecuencia.getFrecuenciaMensual().byteValue(), ano) + ")";
      break;
    case 3: 
      if (periodo == 1) {
        texto = messageResources.getResource("mes.enero") + " " + ano;
      } else if (periodo == 2) {
        texto = messageResources.getResource("mes.febrero") + " " + ano;
      } else if (periodo == 3) {
        texto = messageResources.getResource("mes.marzo") + " " + ano;
      } else if (periodo == 4) {
        texto = messageResources.getResource("mes.abril") + " " + ano;
      } else if (periodo == 5) {
        texto = messageResources.getResource("mes.mayo") + " " + ano;
      } else if (periodo == 6) {
        texto = messageResources.getResource("mes.junio") + " " + ano;
      } else if (periodo == 7) {
        texto = messageResources.getResource("mes.julio") + " " + ano;
      } else if (periodo == 8) {
        texto = messageResources.getResource("mes.agosto") + " " + ano;
      } else if (periodo == 9) {
        texto = messageResources.getResource("mes.septiembre") + " " + ano;
      } else if (periodo == 10) {
        texto = messageResources.getResource("mes.octubre") + " " + ano;
      } else if (periodo == 11) {
        texto = messageResources.getResource("mes.noviembre") + " " + ano;
      } else if (periodo == 12)
        texto = messageResources.getResource("mes.diciembre") + " " + ano; else {
        texto = messageResources.getResource("periodo.novalido");
      }
      break;
    case 4: 
      if (periodo == 1) {
        texto = messageResources.getResource("bimestre.uno") + ano;
      } else if (periodo == 2) {
        texto = messageResources.getResource("bimestre.dos") + ano;
      } else if (periodo == 3) {
        texto = messageResources.getResource("bimestre.tres") + ano;
      } else if (periodo == 4) {
        texto = messageResources.getResource("bimestre.cuatro") + ano;
      } else if (periodo == 5) {
        texto = messageResources.getResource("bimestre.cinco") + ano;
      } else if (periodo == 6)
        texto = messageResources.getResource("bimestre.seis") + ano; else {
        texto = messageResources.getResource("periodo.novalido");
      }
      break;
    case 5: 
      if (periodo == 1) {
        texto = messageResources.getResource("trimestre.uno") + ano;
      } else if (periodo == 2) {
        texto = messageResources.getResource("trimestre.dos") + ano;
      } else if (periodo == 3) {
        texto = messageResources.getResource("trimestre.tres") + ano;
      } else if (periodo == 4)
        texto = messageResources.getResource("trimestre.cuatro") + ano; else {
        texto = messageResources.getResource("periodo.novalido");
      }
      break;
    case 6: 
      if (periodo == 1) {
        texto = messageResources.getResource("cuatrimestre.uno") + ano;
      } else if (periodo == 2) {
        texto = messageResources.getResource("cuatrimestre.dos") + ano;
      } else if (periodo == 3)
        texto = messageResources.getResource("cuatrimestre.tres") + ano; else {
        texto = messageResources.getResource("periodo.novalido");
      }
      break;
    case 7: 
      if (periodo == 1) {
        texto = messageResources.getResource("semestre.uno") + ano;
      } else if (periodo == 2)
        texto = messageResources.getResource("semestre.dos") + ano; else {
        texto = messageResources.getResource("periodo.novalido");
      }
      break;
    case 8: 
      texto = Integer.toString(ano);
    }
    
    return texto;
  }
  
  public static String convertirFechaToTexto(Calendar fecha, byte frecuencia)
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    String texto = "";
    int ano = fecha.get(1);
    int mes = fecha.get(2) + 1;
    
    switch (frecuencia)
    {
    case 0: 
      List<Periodo> periodos = new ArrayList();
      periodos = generarPeriodosFrecuencia(ano, frecuencia);
      texto = "Periodo no válida";
      for (Iterator<Periodo> i = periodos.iterator(); i.hasNext();)
      {
        Periodo per = (Periodo)i.next();
        if (((fecha.after(per.getFechaInicio())) && (fecha.before(per.getFechaFinal()))) || (fecha.equals(per.getFechaInicio())) || (fecha.equals(per.getFechaFinal())))
        {
          SimpleDateFormat formateadorFecha = new SimpleDateFormat("E, dd/MM/yyyy");
          texto = formateadorFecha.format(new Date(per.getFechaInicio().getTimeInMillis()));
        }
      }
      break;
    case 1: 
      int semana = fecha.get(3);
      if (semana == 1) {
        texto = messageResources.getResource("semana.uno") + ano;
      } else if (semana == 2) {
        texto = messageResources.getResource("semana.dos") + ano;
      } else if (semana == 3) {
        texto = messageResources.getResource("semana.tres") + ano;
      } else if (semana == 4) {
        texto = messageResources.getResource("semana.cuatro") + ano;
      } else if (semana == 5) {
        texto = messageResources.getResource("semana.cinco") + ano;
      } else if (semana == 6) {
        texto = messageResources.getResource("semana.seis") + ano;
      } else if (semana == 7) {
        texto = messageResources.getResource("semana.siete") + ano;
      } else if (semana == 8) {
        texto = messageResources.getResource("semana.ocho") + ano;
      } else if (semana == 9) {
        texto = messageResources.getResource("semana.nueve") + ano;
      } else if (semana == 10) {
        texto = messageResources.getResource("semana.diez") + ano;
      } else if (semana == 11) {
        texto = messageResources.getResource("semana.once") + ano;
      } else if (semana == 12) {
        texto = messageResources.getResource("semana.doce") + ano;
      } else if (semana == 13) {
        texto = messageResources.getResource("semana.trece") + ano;
      } else if (semana == 14) {
        texto = messageResources.getResource("semana.catorce") + ano;
      } else if (semana == 15) {
        texto = messageResources.getResource("semana.quince") + ano;
      } else if (semana == 16) {
        texto = messageResources.getResource("semana.diezseis") + ano;
      } else if (semana == 17) {
        texto = messageResources.getResource("semana.diezsiete") + ano;
      } else if (semana == 18) {
        texto = messageResources.getResource("semana.diezocho") + ano;
      } else if (semana == 19) {
        texto = messageResources.getResource("semana.dieznueve") + ano;
      } else if (semana == 20) {
        texto = messageResources.getResource("semana.veinte") + ano;
      } else if (semana == 21) {
        texto = messageResources.getResource("semana.veinteuno") + ano;
      } else if (semana == 22) {
        texto = messageResources.getResource("semana.veintedos") + ano;
      } else if (semana == 23) {
        texto = messageResources.getResource("semana.veintetres") + ano;
      } else if (semana == 24) {
        texto = messageResources.getResource("semana.veintecuatro") + ano;
      } else if (semana == 25) {
        texto = messageResources.getResource("semana.veintecinco") + ano;
      } else if (semana == 26) {
        texto = messageResources.getResource("semana.veinteseis") + ano;
      } else if (semana == 27) {
        texto = messageResources.getResource("semana.veintesiete") + ano;
      } else if (semana == 28) {
        texto = messageResources.getResource("semana.veinteocho") + ano;
      } else if (semana == 29) {
        texto = messageResources.getResource("semana.veintenueve") + ano;
      } else if (semana == 30) {
        texto = messageResources.getResource("semana.treinta") + ano;
      } else if (semana == 31) {
        texto = messageResources.getResource("semana.treintauno") + ano;
      } else if (semana == 32) {
        texto = messageResources.getResource("semana.treintados") + ano;
      } else if (semana == 33) {
        texto = messageResources.getResource("semana.treintatres") + ano;
      } else if (semana == 34) {
        texto = messageResources.getResource("semana.treintacuatro") + ano;
      } else if (semana == 35) {
        texto = messageResources.getResource("semana.treintacinco") + ano;
      } else if (semana == 36) {
        texto = messageResources.getResource("semana.treintaseis") + ano;
      } else if (semana == 37) {
        texto = messageResources.getResource("semana.treintasiete") + ano;
      } else if (semana == 38) {
        texto = messageResources.getResource("semana.treintaocho") + ano;
      } else if (semana == 39) {
        texto = messageResources.getResource("semana.treintanueve") + ano;
      } else if (semana == 40) {
        texto = messageResources.getResource("semana.cuarenta") + ano;
      } else if (semana == 41) {
        texto = messageResources.getResource("semana.cuarentauno") + ano;
      } else if (semana == 42) {
        texto = messageResources.getResource("semana.cuarentados") + ano;
      } else if (semana == 43) {
        texto = messageResources.getResource("semana.cuarentatres") + ano;
      } else if (semana == 44) {
        texto = messageResources.getResource("semana.cuarentacuatro") + ano;
      } else if (semana == 45) {
        texto = messageResources.getResource("semana.cuarentacinco") + ano;
      } else if (semana == 46) {
        texto = messageResources.getResource("semana.cuarentaseis") + ano;
      } else if (semana == 47) {
        texto = messageResources.getResource("semana.cuarentasiete") + ano;
      } else if (semana == 48) {
        texto = messageResources.getResource("semana.cuarentaocho") + ano;
      } else if (semana == 49) {
        texto = messageResources.getResource("semana.cuarentanueve") + ano;
      } else if (semana == 50) {
        texto = messageResources.getResource("semana.cincuenta") + ano;
      } else if (semana == 51) {
        texto = messageResources.getResource("semana.cincuentauno") + ano;
      } else if (semana == 52) {
        texto = messageResources.getResource("semana.cincuentados") + ano;
      } else
        texto = messageResources.getResource("periodo.novalido");
      break;
    case 2: 
      int dia = fecha.get(5);
      mes = fecha.get(2);
      mes = (mes + 1) * 2;
      int quincena = dia < 16 ? mes - 1 : mes;
      if (quincena == 1) {
        texto = messageResources.getResource("quincena.uno") + ano;
      } else if (quincena == 2) {
        texto = messageResources.getResource("quincena.dos") + ano;
      } else if (quincena == 3) {
        texto = messageResources.getResource("quincena.tres") + ano;
      } else if (quincena == 4) {
        texto = messageResources.getResource("quincena.cuatro") + ano;
      } else if (quincena == 5) {
        texto = messageResources.getResource("quincena.cinco") + ano;
      } else if (quincena == 6) {
        texto = messageResources.getResource("quincena.seis") + ano;
      } else if (quincena == 7) {
        texto = messageResources.getResource("quincena.siete") + ano;
      } else if (quincena == 8) {
        texto = messageResources.getResource("quincena.ocho") + ano;
      } else if (quincena == 9) {
        texto = messageResources.getResource("quincena.nueve") + ano;
      } else if (quincena == 10) {
        texto = messageResources.getResource("quincena.diez") + ano;
      } else if (quincena == 11) {
        texto = messageResources.getResource("quincena.once") + ano;
      } else if (quincena == 12) {
        texto = messageResources.getResource("quincena.doce") + ano;
      } else if (quincena == 13) {
        texto = messageResources.getResource("quincena.trece") + ano;
      } else if (quincena == 14) {
        texto = messageResources.getResource("quincena.catorce") + ano;
      } else if (quincena == 15) {
        texto = messageResources.getResource("quincena.quince") + ano;
      } else if (quincena == 16) {
        texto = messageResources.getResource("quincena.diezseis") + ano;
      } else if (quincena == 17) {
        texto = messageResources.getResource("quincena.diezsiete") + ano;
      } else if (quincena == 18) {
        texto = messageResources.getResource("quincena.diezocho") + ano;
      } else if (quincena == 19) {
        texto = messageResources.getResource("quincena.dieznueve") + ano;
      } else if (quincena == 20) {
        texto = messageResources.getResource("quincena.veinte") + ano;
      } else if (quincena == 21) {
        texto = messageResources.getResource("quincena.veinteuno") + ano;
      } else if (quincena == 22) {
        texto = messageResources.getResource("quincena.veintedos") + ano;
      } else if (quincena == 23) {
        texto = messageResources.getResource("quincena.veintetres") + ano;
      } else if (quincena == 24) {
        texto = messageResources.getResource("quincena.veintecuatro") + ano;
      } else
        texto = messageResources.getResource("periodo.novalido");
      break;
    case 3: 
      if (mes == 1) {
        texto = messageResources.getResource("mes.enero") + " " + ano;
      } else if (mes == 2) {
        texto = messageResources.getResource("mes.febrero") + " " + ano;
      } else if (mes == 3) {
        texto = messageResources.getResource("mes.marzo") + " " + ano;
      } else if (mes == 4) {
        texto = messageResources.getResource("mes.abril") + " " + ano;
      } else if (mes == 5) {
        texto = messageResources.getResource("mes.mayo") + " " + ano;
      } else if (mes == 6) {
        texto = messageResources.getResource("mes.junio") + " " + ano;
      } else if (mes == 7) {
        texto = messageResources.getResource("mes.julio") + " " + ano;
      } else if (mes == 8) {
        texto = messageResources.getResource("mes.agosto") + " " + ano;
      } else if (mes == 9) {
        texto = messageResources.getResource("mes.septiembre") + " " + ano;
      } else if (mes == 10) {
        texto = messageResources.getResource("mes.octubre") + " " + ano;
      } else if (mes == 11) {
        texto = messageResources.getResource("mes.noviembre") + " " + ano;
      } else if (mes == 12) {
        texto = messageResources.getResource("mes.diciembre") + " " + ano;
      } else
        texto = messageResources.getResource("periodo.novalido");
      break;
    case 4: 
      if (mes < 3) {
        texto = messageResources.getResource("bimestre.uno") + ano;
      } else if (mes < 5) {
        texto = messageResources.getResource("bimestre.dos") + ano;
      } else if (mes < 7) {
        texto = messageResources.getResource("bimestre.tres") + ano;
      } else if (mes < 9) {
        texto = messageResources.getResource("bimestre.cuatro") + ano;
      } else if (mes < 11) {
        texto = messageResources.getResource("bimestre.cinco") + ano;
      } else if (mes < 13) {
        texto = messageResources.getResource("bimestre.seis") + ano;
      } else
        texto = "Período no válido";
      break;
    case 5: 
      if (mes < 4) {
        texto = messageResources.getResource("trimestre.uno") + ano;
      } else if (mes < 7) {
        texto = messageResources.getResource("trimestre.dos") + ano;
      } else if (mes < 10) {
        texto = messageResources.getResource("trimestre.tres") + ano;
      } else if (mes < 13) {
        texto = messageResources.getResource("trimestre.cuatro") + ano;
      } else
        texto = messageResources.getResource("periodo.novalido");
      break;
    case 6: 
      if (mes < 5) {
        texto = messageResources.getResource("cuatrimestre.uno") + ano;
      } else if (mes < 9) {
        texto = messageResources.getResource("cuatrimestre.dos") + ano;
      } else if (mes < 13) {
        texto = messageResources.getResource("cuatrimestre.tres") + ano;
      } else
        texto = messageResources.getResource("periodo.novalido");
      break;
    case 7: 
      if (mes < 7) {
        texto = messageResources.getResource("semestre.uno") + ano;
      } else if (mes < 13) {
        texto = messageResources.getResource("semestre.dos") + ano;
      } else
        texto = messageResources.getResource("periodo.novalido");
      break;
    case 8: 
      texto = Integer.toString(ano);
    }
    
    
    return texto;
  }
  
  public static int compareFechaToPeriodo(Date fecha, int periodo, int ano, byte frecuencia)
  {
    int anoFecha = 0;
    int periodoFecha = 0;
    
    if (fecha != null) {
      Calendar fechaCalendar = Calendar.getInstance();
      fechaCalendar.setTime(fecha);
      anoFecha = fechaCalendar.get(1);
      periodoFecha = getPeriodoMesAnoHasta(anoFecha, fechaCalendar.get(2) + 1, frecuencia);
      
      if (anoFecha > ano)
        return 1;
      if (anoFecha < ano)
        return -1;
      if (periodoFecha > periodo)
        return 1;
      if (periodoFecha < periodo) {
        return -1;
      }
      return 0;
    }
    
    return -1;
  }
  
  public static int getPeriodoAnterior(int periodo, int ano, byte frecuencia)
  {
    int periodoAnterior = 0;
    if (periodo > 1) {
      periodoAnterior = periodo - 1;
    } else {
      periodoAnterior = getNumeroMaximoPeriodosPorFrecuencia(frecuencia, ano - 1);
    }
    return periodoAnterior;
  }
  
  public static int getAnoPeriodoAnterior(int periodo, int ano)
  {
    int anoPeriodoAnterior = 0;
    if (periodo > 1) {
      anoPeriodoAnterior = ano;
    } else {
      anoPeriodoAnterior = ano - 1;
    }
    return anoPeriodoAnterior;
  }
  
  public static int transformarPeriodoPorFrecuencia(byte frecuenciaOrigen, byte frecuenciaDestino, int periodo, boolean esPeriodoSuperior)
  {
    double periodoResultado = 0.0D;
    
    if (frecuenciaOrigen == Frecuencia.getFrecuenciaMensual().byteValue())
    {
      if (frecuenciaDestino == Frecuencia.getFrecuenciaMensual().byteValue()) {
        periodoResultado = periodo;
      }
    } else if (frecuenciaOrigen == Frecuencia.getFrecuenciaBimensual().byteValue())
    {
      if (frecuenciaDestino == Frecuencia.getFrecuenciaMensual().byteValue())
      {
        periodoResultado = periodo * 2;
        if (!esPeriodoSuperior) {
          periodoResultado -= 1.0D;
        }
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaBimensual().byteValue()) {
        periodoResultado = periodo;
      }
    } else if (frecuenciaOrigen == Frecuencia.getFrecuenciaTrimestral().byteValue())
    {
      if (frecuenciaDestino == Frecuencia.getFrecuenciaMensual().byteValue())
      {
        periodoResultado = periodo * 3;
        if (!esPeriodoSuperior) {
          periodoResultado -= 2.0D;
        }
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaBimensual().byteValue()) {
        periodoResultado = 0.0D;
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaTrimestral().byteValue()) {
        periodoResultado = periodo;
      }
    } else if (frecuenciaOrigen == Frecuencia.getFrecuenciaCuatrimestral().byteValue())
    {
      if (frecuenciaDestino == Frecuencia.getFrecuenciaMensual().byteValue())
      {
        periodoResultado = periodo * 4;
        if (!esPeriodoSuperior) {
          periodoResultado -= 3.0D;
        }
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaBimensual().byteValue())
      {
        periodoResultado = periodo * 2;
        if (!esPeriodoSuperior) {
          periodoResultado -= 1.0D;
        }
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaTrimestral().byteValue()) {
        periodoResultado = 0.0D;
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaCuatrimestral().byteValue()) {
        periodoResultado = periodo;
      }
    } else if (frecuenciaOrigen == Frecuencia.getFrecuenciaSemestral().byteValue())
    {
      if (frecuenciaDestino == Frecuencia.getFrecuenciaMensual().byteValue())
      {
        periodoResultado = periodo * 6;
        if (!esPeriodoSuperior) {
          periodoResultado -= 5.0D;
        }
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaBimensual().byteValue())
      {
        periodoResultado = periodo * 3;
        if (!esPeriodoSuperior) {
          periodoResultado -= 2.0D;
        }
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaTrimestral().byteValue())
      {
        periodoResultado = periodo * 2;
        if (!esPeriodoSuperior) {
          periodoResultado -= 1.0D;
        }
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaCuatrimestral().byteValue()) {
        periodoResultado = 0.0D;
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaSemestral().byteValue()) {
        periodoResultado = periodo;
      }
    } else if (frecuenciaOrigen == Frecuencia.getFrecuenciaAnual().byteValue())
    {
      if (frecuenciaDestino == Frecuencia.getFrecuenciaMensual().byteValue())
      {
        periodoResultado = periodo * 12;
        if (!esPeriodoSuperior) {
          periodoResultado -= 11.0D;
        }
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaBimensual().byteValue())
      {
        periodoResultado = periodo * 6;
        if (!esPeriodoSuperior) {
          periodoResultado -= 5.0D;
        }
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaTrimestral().byteValue())
      {
        periodoResultado = periodo * 4;
        if (!esPeriodoSuperior) {
          periodoResultado -= 3.0D;
        }
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaCuatrimestral().byteValue())
      {
        periodoResultado = periodo * 3;
        if (!esPeriodoSuperior) {
          periodoResultado -= 2.0D;
        }
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaSemestral().byteValue())
      {
        periodoResultado = periodo * 2;
        if (!esPeriodoSuperior) {
          periodoResultado -= 1.0D;
        }
      } else if (frecuenciaDestino == Frecuencia.getFrecuenciaAnual().byteValue()) {
        periodoResultado = periodo;
      }
    }
    if ((periodoResultado == 0.0D) || (periodoResultado == Integer.parseInt(Double.toString(periodoResultado)))) {
      throw new ChainedRuntimeException("La transformaciÃ³n de perÃ­odos no es compatible");
    }
    return Integer.parseInt(Double.toString(periodoResultado));
  }
  
  private static boolean anoBisiesto(int ano)
  {
    double divisionExacta = ano / 4;
    int divisionEntera = ano / 4;
    return divisionEntera == divisionExacta;
  }
  
  public static int getNumeroPeriodosFrecuenciaDiariaEnFrecuencia(byte frecuencia, byte frecuenciaContenida, int ano, int periodo)
  {
    int numeroPeriodos = 0;
    if (frecuenciaContenida == Frecuencia.getFrecuenciaDiaria().intValue())
    {
      if (frecuencia == Frecuencia.getFrecuenciaSemanal().intValue()) {
        numeroPeriodos = 7;
      } else if (frecuencia == Frecuencia.getFrecuenciaQuincenal().intValue()) {
        numeroPeriodos = 15;
      } else if (frecuencia == Frecuencia.getFrecuenciaMensual().intValue())
      {
        if ((periodo == 1) || (periodo == 3) || (periodo == 5) || (periodo == 7) || (periodo == 8) || (periodo == 10) || (periodo == 12)) {
          numeroPeriodos = 31;
        } else if (periodo == 2)
        {
          if (anoBisiesto(ano)) {
            numeroPeriodos = 29;
          } else {
            numeroPeriodos = 28;
          }
        } else {
          numeroPeriodos = 30;
        }
      } else if (frecuencia == Frecuencia.getFrecuenciaBimensual().intValue())
      {
        if (periodo == 1)
        {
          if (anoBisiesto(ano)) {
            numeroPeriodos = 60;
          } else {
            numeroPeriodos = 59;
          }
        } else if (periodo == 4) {
          numeroPeriodos = 62;
        } else {
          numeroPeriodos = 61;
        }
      } else if (frecuencia == Frecuencia.getFrecuenciaTrimestral().intValue())
      {
        if (periodo == 1)
        {
          if (anoBisiesto(ano)) {
            numeroPeriodos = 91;
          } else {
            numeroPeriodos = 90;
          }
        } else if (periodo == 3) {
          numeroPeriodos = 91;
        } else {
          numeroPeriodos = 92;
        }
      } else if (frecuencia == Frecuencia.getFrecuenciaCuatrimestral().intValue())
      {
        if (periodo == 1)
        {
          if (anoBisiesto(ano)) {
            numeroPeriodos = 121;
          } else {
            numeroPeriodos = 120;
          }
        } else if (periodo == 2) {
          numeroPeriodos = 123;
        } else {
          numeroPeriodos = 122;
        }
      } else if (frecuencia == Frecuencia.getFrecuenciaSemestral().intValue())
      {
        if (periodo == 1)
        {
          if (anoBisiesto(ano)) {
            numeroPeriodos = 182;
          } else {
            numeroPeriodos = 181;
          }
        } else {
          numeroPeriodos = 184;
        }
      } else if (frecuencia == Frecuencia.getFrecuenciaAnual().intValue())
      {
        if (anoBisiesto(ano)) {
          numeroPeriodos = 366;
        }
        else {
          numeroPeriodos = 365;
        }
      }
    }
    
    return numeroPeriodos;
  }
  
  public static Byte getMesInicio(Byte mesCierre)
  {
    int mesInicio = 1;
    if (mesCierre.byteValue() < 12) {
      mesInicio = mesCierre.byteValue() + 1;
    }
    return new Byte(Integer.toString(mesInicio));
  }
  
  public static Integer getPeriodoCompatible(byte frecuenciaOriginal, int periodoOriginal, byte frecuenciaInsumo)
  {
    Integer periodoCompatible = null;
    
    if ((frecuenciaOriginal < 9) && (periodoOriginal < 13) && (frecuenciaInsumo < 9))
    {
      if (frecuenciaOriginal == frecuenciaInsumo)
        return new Integer(periodoOriginal);
      periodoCompatible = matrizPeriodos[frecuenciaOriginal][frecuenciaInsumo][periodoOriginal];
    }
    
    return periodoCompatible;
  }
  
  public static Integer[][][] getMatrizPeriodos()
  {
    Integer[][][] matriz = new Integer[9][9][13];
    
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][2] = new Integer("1");
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][4] = new Integer("2");
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][6] = new Integer("3");
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][8] = new Integer("4");
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][10] = new Integer("5");
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][12] = new Integer("6");
    
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][3] = new Integer("1");
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][6] = new Integer("2");
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][9] = new Integer("3");
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][12] = new Integer("4");
    
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][4] = new Integer("1");
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][8] = new Integer("2");
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][12] = new Integer("3");
    
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][6] = new Integer("1");
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][12] = new Integer("2");
    
    matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaAnual().byteValue()][12] = new Integer("1");
    
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][1] = new Integer("2");
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][2] = new Integer("4");
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][3] = new Integer("6");
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][4] = new Integer("8");
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][5] = new Integer("10");
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][6] = new Integer("12");
    
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][3] = new Integer("2");
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][6] = new Integer("4");
    
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][2] = new Integer("1");
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][4] = new Integer("2");
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][6] = new Integer("3");
    
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][3] = new Integer("1");
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][6] = new Integer("2");
    
    matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaAnual().byteValue()][6] = new Integer("1");
    
    matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][1] = new Integer("3");
    matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][2] = new Integer("6");
    matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][3] = new Integer("9");
    matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][4] = new Integer("12");
    
    matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][2] = new Integer("3");
    matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][4] = new Integer("6");
    
    matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][4] = new Integer("3");
    
    matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][2] = new Integer("1");
    matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][4] = new Integer("2");
    
    matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaAnual().byteValue()][4] = new Integer("1");
    
    matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][1] = new Integer("4");
    matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][2] = new Integer("8");
    matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][3] = new Integer("12");
    
    matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][1] = new Integer("2");
    matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][2] = new Integer("4");
    matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][3] = new Integer("6");
    
    matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][3] = new Integer("4");
    
    matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][3] = new Integer("2");
    
    matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaAnual().byteValue()][3] = new Integer("1");
    
    matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][1] = new Integer("6");
    matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][2] = new Integer("12");
    
    matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][1] = new Integer("3");
    matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][2] = new Integer("6");
    
    matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][1] = new Integer("2");
    matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][2] = new Integer("4");
    
    matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][2] = new Integer("3");
    
    matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaAnual().byteValue()][2] = new Integer("1");
    
    matriz[Frecuencia.getFrecuenciaAnual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][1] = new Integer("12");
    
    matriz[Frecuencia.getFrecuenciaAnual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][1] = new Integer("6");
    
    matriz[Frecuencia.getFrecuenciaAnual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][1] = new Integer("4");
    
    matriz[Frecuencia.getFrecuenciaAnual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][1] = new Integer("3");
    
    matriz[Frecuencia.getFrecuenciaAnual().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][1] = new Integer("2");
    
    return matriz;
  }
  
  public static Calendar getDateByPeriodo(Byte frecuencia, int ano, int periodo, boolean firstDate)
  {
    Calendar fecha = Calendar.getInstance();
    
    if (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue())
    {
      fecha.set(1, ano);
      fecha.set(6, periodo);

    }
    else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaSemanal().byteValue())
    {
      fecha.setMinimalDaysInFirstWeek(1);
      fecha.setFirstDayOfWeek(2);
      if (firstDate) {
        fecha.set(7, fecha.getFirstDayOfWeek());
      } else
        fecha.set(7, 1);
      fecha.set(1, ano);
      fecha.set(3, periodo);
    }
    else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaQuincenal().byteValue())
    {
      if (periodo % 2 == 0)
      {
        fecha.set(ano, periodo / 2 - 1, 1);
        if (firstDate) {
          fecha.set(5, fecha.getActualMinimum(5));
        } else {
          fecha.set(5, fecha.getActualMaximum(5));
        }
      } else {
        fecha.set(ano, periodo / 2, 15);
      }
    } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaMensual().byteValue())
    {
      fecha.set(1, ano);
      fecha.set(2, periodo - 1);
      if (firstDate) {
        fecha.set(5, 1);
      } else {
        fecha.set(5, ultimoDiaMes(periodo, ano));
      }
    } else if (frecuencia.byteValue() != Frecuencia.getFrecuenciaAnual().byteValue())
    {
      int factorFrecuencia = 0;
      
      if (frecuencia.byteValue() == Frecuencia.getFrecuenciaBimensual().byteValue()) {
        factorFrecuencia = 2;
      } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaTrimestral().byteValue()) {
        factorFrecuencia = 3;
      } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaCuatrimestral().byteValue()) {
        factorFrecuencia = 4;
      } else if (frecuencia.byteValue() == Frecuencia.getFrecuenciaSemestral().byteValue()) {
        factorFrecuencia = 6;
      }
      fecha.set(1, ano);
      if (firstDate)
      {
        fecha.set(2, factorFrecuencia * periodo - factorFrecuencia);
        fecha.set(5, 1);
      }
      else
      {
        fecha.set(2, factorFrecuencia * periodo - 1);
        
        fecha.set(5, ultimoDiaMes(factorFrecuencia * periodo, ano));
      }
    }
    else
    {
      fecha.set(1, ano);
      if (firstDate)
      {
        fecha.set(2, 0);
        fecha.set(5, 1);
      }
      else
      {
        fecha.set(2, 11);
        fecha.set(5, 31);
      }
    }
    
    return fecha;
  }
  
  public static String getMesNombre(Byte mes)
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    String nombre = null;
    if (mes.byteValue() == 1) {
      nombre = messageResources.getResource("mes.enero");
    } else if (mes.byteValue() == 2) {
      nombre = messageResources.getResource("mes.febrero");
    } else if (mes.byteValue() == 3) {
      nombre = messageResources.getResource("mes.marzo");
    } else if (mes.byteValue() == 4) {
      nombre = messageResources.getResource("mes.abril");
    } else if (mes.byteValue() == 5) {
      nombre = messageResources.getResource("mes.mayo");
    } else if (mes.byteValue() == 6) {
      nombre = messageResources.getResource("mes.junio");
    } else if (mes.byteValue() == 7) {
      nombre = messageResources.getResource("mes.julio");
    } else if (mes.byteValue() == 8) {
      nombre = messageResources.getResource("mes.agosto");
    } else if (mes.byteValue() == 9) {
      nombre = messageResources.getResource("mes.septiembre");
    } else if (mes.byteValue() == 10) {
      nombre = messageResources.getResource("mes.octubre");
    } else if (mes.byteValue() == 11) {
      nombre = messageResources.getResource("mes.noviembre");
    } else if (mes.byteValue() == 12) {
      nombre = messageResources.getResource("mes.diciembre");
    }
    return nombre;
  }
  
  public static List<ObjetoClaveValor> getListaMeses()
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    ObjetoClaveValor elementoClaveValor = new ObjetoClaveValor();
    
    List<ObjetoClaveValor> listaMeses = new ArrayList();
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("1");
    elementoClaveValor.setValor(messageResources.getResource("mes.enero"));
    listaMeses.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("2");
    elementoClaveValor.setValor(messageResources.getResource("mes.febrero"));
    listaMeses.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("3");
    elementoClaveValor.setValor(messageResources.getResource("mes.marzo"));
    listaMeses.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("4");
    elementoClaveValor.setValor(messageResources.getResource("mes.abril"));
    listaMeses.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("5");
    elementoClaveValor.setValor(messageResources.getResource("mes.mayo"));
    listaMeses.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("6");
    elementoClaveValor.setValor(messageResources.getResource("mes.junio"));
    listaMeses.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("7");
    elementoClaveValor.setValor(messageResources.getResource("mes.julio"));
    listaMeses.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("8");
    elementoClaveValor.setValor(messageResources.getResource("mes.agosto"));
    listaMeses.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("9");
    elementoClaveValor.setValor(messageResources.getResource("mes.septiembre"));
    listaMeses.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("10");
    elementoClaveValor.setValor(messageResources.getResource("mes.octubre"));
    listaMeses.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("11");
    elementoClaveValor.setValor(messageResources.getResource("mes.noviembre"));
    listaMeses.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("12");
    elementoClaveValor.setValor(messageResources.getResource("mes.diciembre"));
    listaMeses.add(elementoClaveValor);
    
    return listaMeses;
  }
  
  public static List<ObjetoClaveValor> getListaNumeros(Integer numeroCentral, Byte rango)
  {
    ObjetoClaveValor elementoClaveValor = new ObjetoClaveValor();
    
    List<ObjetoClaveValor> listaNumeros = new ArrayList();
    
    for (int i = numeroCentral.intValue() - rango.byteValue(); i <= numeroCentral.intValue() + rango.byteValue(); i++)
    {
      elementoClaveValor = new ObjetoClaveValor();
      elementoClaveValor.setClave(String.valueOf(i));
      elementoClaveValor.setValor(String.valueOf(i));
      listaNumeros.add(elementoClaveValor);
    }
    
    return listaNumeros;
  }
  
  public static List<ObjetoClaveValor> getListaNumeros(Integer inicio, Integer fin)
  {
    ObjetoClaveValor elementoClaveValor = new ObjetoClaveValor();
    
    List<ObjetoClaveValor> listaNumeros = new ArrayList();
    
    for (int i = inicio.intValue(); i <= fin.intValue(); i++)
    {
      elementoClaveValor = new ObjetoClaveValor();
      elementoClaveValor.setClave(String.valueOf(i));
      elementoClaveValor.setValor(String.valueOf(i));
      listaNumeros.add(elementoClaveValor);
    }
    
    return listaNumeros;
  }
}
