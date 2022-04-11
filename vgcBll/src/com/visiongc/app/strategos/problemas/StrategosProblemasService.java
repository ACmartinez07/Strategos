package com.visiongc.app.strategos.problemas;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosProblemasService
  extends StrategosService
{
  public abstract PaginaLista getProblemas(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteProblema(Problema paramProblema, Usuario paramUsuario);
  
  public abstract int saveProblema(Problema paramProblema, Usuario paramUsuario);
  
  public abstract Long getNumeroProblemas(Long paramLong);
  
  public abstract int updateCampo(Long paramLong, Map<?, ?> paramMap)
    throws Throwable;
}
