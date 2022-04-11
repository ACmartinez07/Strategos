package com.visiongc.app.strategos.indicadores.graficos.util;

import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.util.SerieUtil;
import com.visiongc.commons.util.VgcFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AnoPeriodo
{
  private Integer ano;
  private Integer periodo;
  private String nombre;
  private List<SerieUtil> series = new ArrayList();
  private DatosAlerta alerta;
  private Long indicadorId;
  
  public AnoPeriodo() {}
  
  public Integer getAno() { return ano; }
  

  public void setAno(Integer ano)
  {
    this.ano = ano;
  }
  
  public Integer getPeriodo()
  {
    return periodo;
  }
  
  public void setPeriodo(Integer periodo)
  {
    this.periodo = periodo;
  }
  
  public List<SerieUtil> getSeries()
  {
    return series;
  }
  
  public void setSeries(List<SerieUtil> series)
  {
    this.series = series;
  }
  
  public DatosAlerta getAlerta()
  {
    return alerta;
  }
  
  public void setAlerta(DatosAlerta alerta)
  {
    this.alerta = alerta;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public Long getIndicadorId()
  {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId)
  {
    this.indicadorId = indicadorId;
  }
  
  public static List<AnoPeriodo> getListaAnosPeriodos(int anoInicial, int anoFinal, int periodoInicial, int periodoFinal, int numeroMaximoPeriodos)
  {
    List<AnoPeriodo> listaAnosPeriodos = new ArrayList();
    
    for (int ano = anoInicial; ano <= anoFinal; ano++)
    {
      int periodoDesde = 0;
      int periodoHasta = 0;
      
      if (ano == anoInicial) {
        periodoDesde = periodoInicial;
      } else {
        periodoDesde = 1;
      }
      if (ano == anoFinal) {
        periodoHasta = periodoFinal;
      } else {
        periodoHasta = numeroMaximoPeriodos;
      }
      for (int periodo = periodoDesde; periodo <= periodoHasta; periodo++)
      {
        AnoPeriodo anoPeriodo = new AnoPeriodo();
        
        anoPeriodo.setAno(new Integer(ano));
        anoPeriodo.setPeriodo(new Integer(periodo));
        if ((numeroMaximoPeriodos == 365) || (numeroMaximoPeriodos == 366))
        {
          Calendar fecha = PeriodoUtil.getDateByPeriodo(new Byte((byte)0), ano, periodo, false);
          anoPeriodo.setNombre(VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta"));
        }
        else {
          anoPeriodo.setNombre(ano + "/" + periodo);
        }
        listaAnosPeriodos.add(anoPeriodo);
      }
    }
    
    return listaAnosPeriodos;
  }
}
