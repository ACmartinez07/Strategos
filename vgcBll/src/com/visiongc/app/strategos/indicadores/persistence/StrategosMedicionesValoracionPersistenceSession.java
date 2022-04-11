package com.visiongc.app.strategos.indicadores.persistence;

import com.visiongc.app.strategos.indicadores.model.MedicionValoracion;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import java.util.List;

public abstract interface StrategosMedicionesValoracionPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract List<MedicionValoracion> getMedicionesPeriodo(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List<MedicionValoracion> getMedicionesPeriodo(Long paramLong1, Byte paramByte1, Byte paramByte2, Long paramLong2, Boolean paramBoolean, Byte paramByte3, Byte paramByte4, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List<MedicionValoracion> getMedicionesPorFrecuencia(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Byte paramByte1, Byte paramByte2, Boolean paramBoolean1, Boolean paramBoolean2);
  
  public abstract List<MedicionValoracion> getMedicionesPorFrecuencia(Long paramLong1, Long paramLong2, Boolean paramBoolean1, Boolean paramBoolean2, Byte paramByte1, Byte paramByte2, Byte paramByte3, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract ListaMap getMedicionesPorFrecuenciaInterna(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Byte paramByte1, Byte paramByte2, Boolean paramBoolean);
  
  public abstract int deleteMediciones(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract int deleteMedicion(MedicionValoracion paramMedicion)
    throws Throwable;
  
  public abstract List<MedicionValoracion> getMedicionesAcumuladasPeriodo(Long paramLong1, Byte paramByte1, Byte paramByte2, Long paramLong2, Boolean paramBoolean, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract MedicionValoracion getUltimaMedicion(Long paramLong1, Long paramLong2);
  
  public abstract MedicionValoracion getMedicion(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2);
  
  public abstract MedicionValoracion getUltimaMedicion(Long paramLong1, Byte paramByte1, Byte paramByte2, Long paramLong2, Boolean paramBoolean, Byte paramByte3, Byte paramByte4);
  
  public abstract Long getNumeroMediciones(Long paramLong);
  
  public abstract List<MedicionValoracion> getUltimasMedicionesIndicadores(List<Long> paramList, Long paramLong);
  
  public abstract List<MedicionValoracion> getMedicionesPeriodoExactas(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List<MedicionValoracion> getMedicionesPeriodoPorFrecuencia(Long paramLong1, Byte paramByte, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, List<MedicionValoracion> paramList);
  
  public abstract List<MedicionValoracion> getMedicionesIndicadores(List<Long> paramList, Long paramLong, Integer paramInteger1, Integer paramInteger2);
  
  public abstract List<MedicionValoracion> getMedicionesBySerie(List<MedicionValoracion> paramList, Long paramLong1, Long paramLong2);
  
  public abstract MedicionValoracion getUltimaMedicion(List<MedicionValoracion> paramList);
  
  public abstract double getUltimaMedicionAcumulada(List<MedicionValoracion> paramList);
  
  public abstract MedicionValoracion getUltimaMedicionByAno(List<MedicionValoracion> paramList, Integer paramInteger);
  
  public abstract MedicionValoracion getUltimaMedicionByAnoByPeriodo(List<MedicionValoracion> paramList, Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Boolean paramBoolean);

  public abstract double getValorAcumulado(Long paramLong1, Long paramLong2);
}
