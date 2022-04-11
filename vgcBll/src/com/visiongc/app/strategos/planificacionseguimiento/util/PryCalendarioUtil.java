package com.visiongc.app.strategos.planificacionseguimiento.util;

import com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendario;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendarioDetalle;
import com.visiongc.commons.util.FechaUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class PryCalendarioUtil
{
  private static final long DIA_MILISEG = 86400000L;
  public static final byte ORIENTACION_INTERVALO_ADELANTE = 0;
  public static final byte ORIENTACION_INTERVALO_ATRAS = 1;
  
  public PryCalendarioUtil() {}
  
  private static boolean esCalendarioDiaNoLaborable(PryCalendario calendario, Date fecha)
  {
    PryCalendarioDetalle excepcion = null;
    boolean diaNoLaborable = false;
    
    if (calendario.getMapExcepciones() != null) {
      excepcion = (PryCalendarioDetalle)calendario.getMapExcepciones().get(com.visiongc.commons.util.VgcFormatter.formatearFecha(fecha, "formato.fecha.corta"));
    } else {
      for (Iterator iter = calendario.getExcepciones().iterator(); iter.hasNext();) {
        excepcion = (PryCalendarioDetalle)iter.next();
        
        int resultado = FechaUtil.compareFechasAnoMesDia(excepcion.getFecha(), fecha);
        if (resultado == 0)
          break;
        if (resultado > 0) {
          excepcion = null;
          break;
        }
      }
    }
    if (excepcion != null) {
      if (excepcion.getFeriado().booleanValue()) {
        diaNoLaborable = true;
      }
    } else {
      Calendar calFecha = Calendar.getInstance();
      calFecha.setTime(fecha);
      int diaSemana = calFecha.get(7);
      if (diaSemana == 1) {
        diaNoLaborable = calendario.getDomingo().booleanValue();
      } else if (diaSemana == 2) {
        diaNoLaborable = calendario.getLunes().booleanValue();
      } else if (diaSemana == 3) {
        diaNoLaborable = calendario.getMartes().booleanValue();
      } else if (diaSemana == 4) {
        diaNoLaborable = calendario.getMiercoles().booleanValue();
      } else if (diaSemana == 5) {
        diaNoLaborable = calendario.getJueves().booleanValue();
      } else if (diaSemana == 6) {
        diaNoLaborable = calendario.getViernes().booleanValue();
      } else if (diaSemana == 7) {
        diaNoLaborable = calendario.getSabado().booleanValue();
      }
    }
    return diaNoLaborable;
  }
  
  private static boolean esCalendarioDiaLaborable(PryCalendario calendario, Date fecha) {
    return !esCalendarioDiaNoLaborable(calendario, fecha);
  }
  
  public static Date getProximoDiaLaborable(PryCalendario calendario, Date fecha)
  {
    Date diaLaborable = new Date(fecha.getTime());
    
    while (esCalendarioDiaNoLaborable(calendario, diaLaborable)) {
      diaLaborable.setTime(diaLaborable.getTime() + 86400000L);
    }
    
    return diaLaborable;
  }
  
  public static Date getFechaIntervaloPorDuracion(PryCalendario calendario, Date fecha, int duracion, byte orientacion)
  {
    Date fechaResultado = new Date(fecha.getTime());
    int duracionCalculada = 1;
    if (orientacion == 0) {
      while (duracionCalculada < duracion) {
        fechaResultado.setTime(fechaResultado.getTime() + 86400000L);
        if (esCalendarioDiaLaborable(calendario, fechaResultado)) {
          duracionCalculada++;
        }
      }
    } else {
      do {
        fechaResultado.setTime(fechaResultado.getTime() - 86400000L);
        if (esCalendarioDiaLaborable(calendario, fechaResultado)) {
          duracionCalculada++;
        }
      } while (duracionCalculada < duracion);
    }
    
    return fechaResultado;
  }
  
  public static int getDuracionEntreFechas(PryCalendario calendario, Date fechaComienzo, Date fechaFin) {
    Date inicio = fechaComienzo;
    Date fin = fechaFin;
    if (inicio.after(fin)) {
      inicio = fechaFin;
      fin = fechaComienzo;
    }
    FechaUtil.setHoraInicioDia(inicio);
    FechaUtil.setHoraFinalDia(fin);
    
    long diferencia = fin.getTime() - inicio.getTime();
    
    double resultado = new Double(diferencia).doubleValue() / new Double(8.64E7D).doubleValue();
    
    long duracion = new Double(resultado).longValue();
    
    if (resultado > duracion) {
      duracion += 1L;
    }
    
    Date fecha = new Date(inicio.getTime());
    while (FechaUtil.compareFechasAnoMesDia(fecha, fin) != 0) {
      fecha.setTime(fecha.getTime() + 86400000L);
      if (esCalendarioDiaNoLaborable(calendario, fecha)) {
        duracion -= 1L;
      }
    }
    return Integer.parseInt(Long.toString(duracion));
  }
}
