package com.visiongc.app.strategos.iniciativas;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.indicadores.model.util.ConfiguracionNegativo;
import com.visiongc.app.strategos.iniciativas.model.IndicadorIniciativa;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.ConfiguracionIniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.CorreoIniciativa;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosIniciativasService
  extends StrategosService
{
  public abstract PaginaLista getIniciativas(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract PaginaLista getIniciativasResponsable(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteIniciativa(Iniciativa paramIniciativa, Usuario paramUsuario);
  
  public abstract int saveIniciativa(Iniciativa paramIniciativa, Usuario paramUsuario, Boolean paramBoolean);
  
  public abstract int asociarIndicador(Iniciativa paramIniciativa, Usuario paramUsuario);
  
  public abstract int desasociarIndicadores(IndicadorIniciativa paramIndicadorIniciativa, Usuario paramUsuario);
  
  public abstract ConfiguracionIniciativa getConfiguracionIniciativa();
  
  public abstract Iniciativa getIniciativaAndLockForUse(Long paramLong, String paramString);
  
  public abstract List getRutaCompletaIniciativa(Long paramLong);
  
  public abstract String getRutaCompletaIniciativa(Long paramLong, String paramString);
  
  public abstract String getRutaCompletaIniciativa(Iniciativa paramIniciativa, String paramString);
  
  public abstract List getIniciativasPorPlan(Long paramLong, Byte paramByte, String paramString1, String paramString2);
  
  public abstract boolean verificarOrganizacionIniciativaPorNombre(String paramString, Long paramLong);
  
  public abstract Iniciativa getIniciativaVinculacion(Long paramLong1, Long paramLong2);
  
  public abstract boolean actualizarIniciativaProyecto(Long paramLong1, Long paramLong2);
  
  public abstract Byte getAlertaIniciativa(Long paramLong1, Byte paramByte, Long paramLong2);
  
  public abstract Iniciativa getIniciativaByIndicador(long paramLong);
  
  public abstract int updateCampo(Long paramLong, Map<?, ?> paramMap)
    throws Throwable;
  
  public abstract Iniciativa getIniciativaByProyecto(long paramLong);
  
  public abstract Iniciativa getValoresOriginales(Long paramLong);
  
  public abstract List<Iniciativa> getIniciativasAsociadas(Long paramLong);
  
  public abstract Map<Long, Byte> getAlertasIniciativas(Map<String, String> paramMap);
  
  public abstract int updateIndicadorAutomatico(Iniciativa paramIniciativa, Byte paramByte, ConfiguracionIniciativa paramConfiguracionIniciativa, Usuario paramUsuario);
  
  public abstract int marcarHistorico(String paramString);
  
  public abstract int desMarcarHistorico(String paramString);
  
  public abstract List<Iniciativa> getIniciativasParaEjecutar(Long paramLong1, Long paramLong2, Long paramLong3, Integer paramInteger1);
  
  public abstract CorreoIniciativa getCorreoIniciativa();
}
