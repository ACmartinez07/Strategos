package com.visiongc.app.strategos.indicadores.persistence;

import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import java.util.List;
import java.util.Map;

public abstract interface StrategosIndicadoresPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getIndicadores(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract PaginaLista getIndicadoresResponsables(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract List<Indicador> getIndicadores(Map<?, ?> paramMap);
  
  public abstract int protegerMediciones(Map<?, ?> paramMap);
  
  public abstract int protegerMediciones(Map<?, ?> paramMap, Boolean paramBoolean);
  
  public abstract List getCategoriasIndicador(Long paramLong);
  
  public abstract List<InsumoFormula> getInsumosFormula(Long paramLong1, Long paramLong2);
  
  public abstract Indicador getIndicadorBasico(Long paramLong);
  
  public abstract ListaMap getDependenciasIndicador(Indicador paramIndicador);
  
  public abstract int updateCampo(Long paramLong, Map<?, ?> paramMap)
    throws Throwable;
  
  public abstract int updateAlerta(Long paramLong, Byte paramByte)
    throws Throwable;
  
  public abstract int actualizarDatosIndicador(Long paramLong, Double paramDouble1, Double paramDouble2, String paramString)
    throws Throwable;
  
  public abstract Byte getFrecuenciaMaximaIndicadoresPlan(Long paramLong);
  
  public abstract Indicador getIndicadorValoresOriginales(Long paramLong);
  
  public abstract Long getNumeroIndicadores(Map paramMap);
  
  public abstract int getNumeroUsosComoIndicadorInsumo(Long paramLong);
  
  public abstract int deleteReferenciasForaneasIndicador(Long paramLong);
  
  public abstract Indicador getIndicadorProgramado(Long paramLong, Byte paramByte);
  
  public abstract Indicador getIndicadorMaximo(Long paramLong);
  
  public abstract Indicador getIndicadorMinimo(Long paramLong);
  
  public abstract boolean tieneIndicadorProgramado(Long paramLong);
  
  public abstract boolean tieneIndicadorMinimo(Long paramLong);
  
  public abstract boolean tieneIndicadorMaximo(Long paramLong);
  
  public abstract List<Indicador> getIndicadoresNombreFrecuencia(String paramString1, String paramString2, List<String> paramList, boolean paramBoolean, Map<String, Object> paramMap);
  
  public abstract List<Long> getIndicadoresIds(Map<String, Object> paramMap);
  
  public abstract int saveCodigoEnlace(Long paramLong1, Long paramLong2, String paramString)
    throws Throwable;
  
  public abstract boolean esInsumo(Long paramLong);
  
  public abstract Indicador getIndicador(Long paramLong);
  
  public abstract Formula getFormulaIndicador(Long paramLong1, Long parmLong2);
  
  public abstract Indicador getIndicador(Long paramLong, String paramString);
}
