package com.visiongc.app.strategos.indicadores;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.MedicionValoracion;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.framework.model.Usuario;
import java.util.List;

public abstract interface StrategosMedicionesValoracionService
  extends StrategosService
{
  public abstract int deleteMedicion(MedicionValoracion paramMedicion, Usuario paramUsuario);
  
  public abstract int saveMedicion(MedicionValoracion paramMedicion, Usuario paramUsuario);
  
  public abstract int actualizarUltimaMedicionIndicadores(List<Object> paramList);
  
  public abstract int actualizarUltimaMedicionIndicadores(List<Object> paramList, Long paramLong, Usuario paramUsuario);
  
  public abstract int saveMediciones(List<MedicionValoracion> paramList, Long paramLong, Usuario paramUsuario, Boolean paramBoolean1, Boolean paramBoolean2);
  
  public abstract List<MedicionValoracion> getMedicionesPeriodo(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List<MedicionValoracion> getMedicionesPeriodo(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Byte paramByte);
  
  public abstract List<MedicionValoracion> getMedicionesPeriodo(Long paramLong1, Byte paramByte1, Byte paramByte2, Long paramLong2, Boolean paramBoolean, Byte paramByte3, Byte paramByte4, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List<MedicionValoracion> getMedicionesPorFrecuencia(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Byte paramByte1, Byte paramByte2, Boolean paramBoolean1, Boolean paramBoolean2);
  
  public abstract List<MedicionValoracion> getMedicionesPorFrecuencia(Long paramLong1, Long paramLong2, Boolean paramBoolean1, Boolean paramBoolean2, Byte paramByte1, Byte paramByte2, Byte paramByte3, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract ListaMap getMedicionesPorFrecuenciaInterna(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Byte paramByte1, Byte paramByte2, Boolean paramBoolean);
  
  public abstract int deleteMediciones(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract int deleteMediciones(Long paramLong);
  
  public abstract List<MedicionValoracion> getMedicionesAcumuladasPeriodo(Long paramLong1, Byte paramByte1, Byte paramByte2, Long paramLong2, Boolean paramBoolean, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract MedicionValoracion getUltimaMedicion(Long paramLong1, Long paramLong2);
  
  public abstract MedicionValoracion getUltimaMedicion(Long paramLong1, Byte paramByte1, Byte paramByte2, Long paramLong2, Boolean paramBoolean, Byte paramByte3, Byte paramByte4);
  
  public abstract Long getNumeroMediciones(Long paramLong);
  
  public abstract List<MedicionValoracion> getUltimasMedicionesIndicadores(List<Long> paramList, Long paramLong);
  
  public abstract int copiarMediciones(List<MedicionValoracion> paramList, Usuario paramUsuario);
  
  public abstract int actualizarSeriePorPeriodos(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, List<MedicionValoracion> paramList, Boolean paramBoolean1, Boolean paramBoolean2, Usuario paramUsuario);
  
  public abstract MedicionValoracion getUltimaMedicionConValor(List<MedicionValoracion> paramList);
  
  public abstract MedicionValoracion getUltimaMedicionConValorEnUnPeriodo(List<MedicionValoracion> paramList, Integer paramInteger1, Integer paramInteger2);
  
  public abstract List<MedicionValoracion> getMedicionesPeriodoExactas(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List<MedicionValoracion> getMedicionesPeriodoPorFrecuencia(Long paramLong1, Byte paramByte, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, List<MedicionValoracion> paramList);
  
  public abstract List<MedicionValoracion> getMedicionesIndicadores(List<Indicador> paramList, Long paramLong, Integer paramInteger1, Integer paramInteger2);
  
  public abstract List<MedicionValoracion> getMedicionesBySerie(List<MedicionValoracion> paramList, Long paramLong1, Long paramLong2);
  
  public abstract MedicionValoracion getUltimaMedicion(List<MedicionValoracion> paramList);
  
  public abstract MedicionValoracion getUltimaMedicionByAno(List<MedicionValoracion> paramList, Integer paramInteger);
  
  public abstract MedicionValoracion getUltimaMedicionByAnoByPeriodo(List<MedicionValoracion> paramList, Integer paramInteger1, Integer paramInteger2);

  public abstract double getUltimaMedicionAcumulada(List<MedicionValoracion> paramList);

  public abstract double getValorAcumulado(Long paramLong1, Long paramLong2);


}
