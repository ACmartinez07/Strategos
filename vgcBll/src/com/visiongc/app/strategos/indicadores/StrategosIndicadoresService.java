package com.visiongc.app.strategos.indicadores;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.util.ConfiguracionIndicador;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public abstract interface StrategosIndicadoresService
  extends StrategosService
{
  public abstract PaginaLista getIndicadores(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap, Integer paramInteger1, Integer paramInteger2, Boolean paramBoolean1);
  
  public abstract PaginaLista getIndicadoresResponsables(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap, Integer paramInteger1, Integer paramInteger2, Boolean paramBoolean1);
  
  public abstract int deleteIndicador(Indicador paramIndicador, Usuario paramUsuario);
  
  public abstract int saveIndicador(Indicador paramIndicador, Usuario paramUsuario);
  
  public abstract Indicador getIndicadorValoresOriginales(Long paramLong);
  
  public abstract int verificarCambiosIndicador(Indicador paramIndicador1, Indicador paramIndicador2);
  
  public abstract PaginaLista getIndicadoresLogroPlan(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract List<Indicador> getIndicadores(Map<?, ?> paramMap);
  
  public abstract int protegerMedicionesIndicadores(List<Indicador> paramList, Map<?, ?> paramMap, Boolean paramBoolean);
  
  public abstract List getCategoriasIndicador(Long paramLong);
  
  public abstract Indicador getIndicadorReferenciaCircular(SerieIndicador paramSerieIndicador);
  
  public abstract int getNumeroIndicadoresEnFormula(long paramLong1, long paramLong2);
  
  public abstract Indicador getIndicadorBasico(Long paramLong);
  
  public abstract Indicador getIndicadorBasicoAndLockForUse(Long paramLong, String paramString);
  
  public abstract List getRutaCompletaIndicador(Long paramLong);
  
  public abstract String getRutaCompletaIndicador(Long paramLong, String paramString);
  
  public abstract String getRutaCompletaIndicador(Indicador paramIndicador, String paramString);
  
  public abstract int actualizarDatosIndicador(Long paramLong, Double paramDouble1, Double paramDouble2, String paramString)
    throws Throwable;
  
  public abstract Byte getFrecuenciaMaximaIndicadoresPlan(Long paramLong);
  
  public abstract Long getNumeroIndicadoresPorClase(Long paramLong, Map paramMap);
  
  public abstract int getNumeroUsosComoIndicadorInsumo(Long paramLong);
  
  public abstract Indicador getIndicadorProgramado(Long paramLong, Byte paramByte);
  
  public abstract Indicador getIndicadorProgramado(Long paramLong);
  
  public abstract Indicador getIndicadorMinimo(Long paramLong);
  
  public abstract Indicador getIndicadorMaximo(Long paramLong);
  
  public abstract Long getNumeroIndicadoresPorPlan(Long paramLong, Map paramMap);
  
  public abstract Long getNumeroIndicadoresPorPerspectiva(Long paramLong, Map paramMap);
  
  public abstract int updateCampo(Long paramLong, Map<?, ?> paramMap)
    throws Throwable;
  
  public abstract int updateAlerta(Long paramLong, Byte paramByte)
    throws Throwable;
  
  public abstract boolean tieneIndicadorProgramado(Long paramLong);
  
  public abstract boolean tieneIndicadorMinimo(Long paramLong);
  
  public abstract boolean tieneIndicadorMaximo(Long paramLong);
  
  public abstract List<Indicador> getIndicadoresNombreFrecuencia(String paramString1, String paramString2, List<String> paramList, boolean paramBoolean, Map<String, Object> paramMap);
  
  public abstract List<Long> getIndicadoresIds(Map<String, Object> paramMap);
  
  public abstract List<Indicador> getIndicadoresParaEjecutar(Long paramLong1, List<Long> paramList, Long paramLong2, boolean paramBoolean1, Long paramLong3, boolean paramBoolean2, boolean paramBoolean3, Long paramLong4, Long paramLong5);
  
  public abstract Double zonaAmarilla(Indicador paramIndicador, Integer paramInteger1, Integer paramInteger2, Double paramDouble1, Double paramDouble2, StrategosMedicionesService paramStrategosMedicionesService);
  
  public abstract Double zonaVerde(Indicador paramIndicador, Integer paramInteger1, Integer paramInteger2, Double paramDouble, StrategosMedicionesService paramStrategosMedicionesService);
  
  public abstract int saveCodigoEnlace(Long paramLong1, Long paramLong2, String paramString)
    throws Throwable;
  
  public abstract boolean checkLicencia(HttpServletRequest paramHttpServletRequest);
  
  public abstract boolean esInsumo(Long paramLong);
  
  public abstract ConfiguracionIndicador getConfiguracionIndicador();
  
  public abstract int saveSerieIndicador(Long paramLong, Set<SerieIndicador> paramSet);
  
  public abstract List<InsumoFormula> getInsumosFormula(Long indicadorId, Long serieId);
  
  public abstract Indicador getIndicador(Long paramLong);
  
  public abstract Indicador getIndicador(Long paramLong, String paramString);
  
  public abstract Formula getFormulaIndicador(Long paramLong1, Long parmLong2);
  
  
}
