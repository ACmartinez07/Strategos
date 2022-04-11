package com.visiongc.app.strategos.indicadores;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosClasesIndicadoresService
  extends StrategosService
{
  public abstract int deleteClaseIndicadores(ClaseIndicadores paramClaseIndicadores, Boolean paramBoolean, Usuario paramUsuario);
  
  public abstract ClaseIndicadores crearClaseRaiz(Long paramLong, Usuario paramUsuario);
  
  public abstract ClaseIndicadores getClaseRaiz(Long paramLong, Byte paramByte, Usuario paramUsuario);
  
  public abstract List<ClaseIndicadores> getClasesHijas(Long paramLong, Boolean paramBoolean);
  
  public abstract int saveClaseIndicadores(ClaseIndicadores paramClaseIndicadores, Usuario paramUsuario);
  
  public abstract List getArbolCompletoClaseIndicadores(Long paramLong);
  
  public abstract String getRutaCompletaNombresClaseIndicadores(Long paramLong, String paramString);
  
  public abstract List<ClaseIndicadores> getClases(Map<String, Object> paramMap);
  
  public abstract ClaseIndicadores getClaseRaizIniciativa(Long paramLong, Byte paramByte, String paramString, Usuario paramUsuario);
  
  public abstract ClaseIndicadores getClaseRaizPlan(Long paramLong, Byte paramByte, String paramString, Usuario paramUsuario);
}
