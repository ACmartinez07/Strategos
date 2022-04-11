package com.visiongc.app.strategos.problemas;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosClasesProblemasService
  extends StrategosService
{
  public abstract PaginaLista getClaseProblemas(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteClaseProblema(ClaseProblemas paramClaseProblemas, Usuario paramUsuario);
  
  public abstract int saveClaseProblema(ClaseProblemas paramClaseProblemas, Usuario paramUsuario);
  
  public abstract ClaseProblemas crearClaseRaiz(Long paramLong, Usuario paramUsuario, Integer paramInteger);
}
