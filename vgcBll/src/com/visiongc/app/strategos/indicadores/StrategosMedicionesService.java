package com.visiongc.app.strategos.indicadores;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.ConfiguracionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.ConfiguracionNegativo;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.framework.model.Usuario;
import java.util.List;

public abstract interface StrategosMedicionesService
  extends StrategosService
{
  public abstract int deleteMedicion(Medicion paramMedicion, Usuario paramUsuario);
  
  public abstract int saveMedicion(Medicion paramMedicion, Usuario paramUsuario);
  
  public abstract int actualizarUltimaMedicionIndicadores(List<Object> paramList);
  
  public abstract int actualizarUltimaMedicionIndicadores(List<Object> paramList, Long paramLong, Usuario paramUsuario);
  
  public abstract int saveMediciones(List<Medicion> paramList, Long paramLong, Usuario paramUsuario, Boolean paramBoolean1, Boolean paramBoolean2);
  
  public abstract List<Medicion> getMedicionesPeriodo(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List<Medicion> getMedicionesPeriodo(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Byte paramByte);
  
  public abstract List<Medicion> getMedicionesPeriodo(Long paramLong1, Byte paramByte1, Byte paramByte2, Long paramLong2, Boolean paramBoolean, Byte paramByte3, Byte paramByte4, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List<Medicion> getMedicionesPorFrecuencia(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Byte paramByte1, Byte paramByte2, Boolean paramBoolean1, Boolean paramBoolean2);
  
  public abstract List<Medicion> getMedicionesPorFrecuencia(Long paramLong1, Long paramLong2, Boolean paramBoolean1, Boolean paramBoolean2, Byte paramByte1, Byte paramByte2, Byte paramByte3, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract ListaMap getMedicionesPorFrecuenciaInterna(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Byte paramByte1, Byte paramByte2, Boolean paramBoolean);
  
  public abstract int deleteMediciones(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract int deleteMediciones(Long paramLong);
  
  public abstract List<Medicion> getMedicionesAcumuladasPeriodo(Long paramLong1, Byte paramByte1, Byte paramByte2, Long paramLong2, Boolean paramBoolean, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract Medicion getUltimaMedicion(Long paramLong1, Long paramLong2);
  
  public abstract Medicion getUltimaMedicion(Long paramLong1, Byte paramByte1, Byte paramByte2, Long paramLong2, Boolean paramBoolean, Byte paramByte3, Byte paramByte4);
  
  public abstract Long getNumeroMediciones(Long paramLong);
  
  public abstract List<Medicion> getUltimasMedicionesIndicadores(List<Long> paramList, Long paramLong);
  
  public abstract int copiarMediciones(List<Medicion> paramList, Usuario paramUsuario);
  
  public abstract int actualizarSeriePorPeriodos(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, List<Medicion> paramList, Boolean paramBoolean1, Boolean paramBoolean2, Usuario paramUsuario);
  
  public abstract Medicion getUltimaMedicionConValor(List<Medicion> paramList);
  
  public abstract Medicion getUltimaMedicionConValorEnUnPeriodo(List<Medicion> paramList, Integer paramInteger1, Integer paramInteger2);
  
  public abstract List<Medicion> getMedicionesPeriodoExactas(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List<Medicion> getMedicionesPeriodoPorFrecuencia(Long paramLong1, Byte paramByte, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, List<Medicion> paramList);
  
  public abstract List<Medicion> getMedicionesIndicadores(List<Indicador> paramList, Long paramLong, Integer paramInteger1, Integer paramInteger2);
  
  public abstract List<Medicion> getMedicionesBySerie(List<Medicion> paramList, Long paramLong1, Long paramLong2);
  
  public abstract Medicion getUltimaMedicion(List<Medicion> paramList);
  
  public abstract Medicion getUltimaMedicionByAno(List<Medicion> paramList, Integer paramInteger);
  
  public abstract Medicion getUltimaMedicionByAnoByPeriodo(List<Medicion> paramList, Integer paramInteger1, Integer paramInteger2);

  public abstract double getUltimaMedicionAcumulada(List<Medicion> paramList);

  public abstract double getValorAcumulado(Long paramLong1, Long paramLong2);

  public abstract List<Medicion> getMedicionList(List<Long> listParam1,Long paramLong1, Integer paramInteger1, Integer paramInteger2);
  
  public abstract ConfiguracionNegativo getConfiguracionNegativo();

}
