package com.visiongc.app.strategos.foros;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.foros.model.Foro;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosForosService
  extends StrategosService
{
  public abstract PaginaLista getForos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int deleteForo(Foro paramForo, Usuario paramUsuario);
  
  public abstract int saveForo(Foro paramForo, Usuario paramUsuario);
  
  public abstract Long getNumeroForos(Long paramLong);
  
  public abstract Foro getRutaCompletaForos(Long paramLong, List<Foro> paramList);
}
