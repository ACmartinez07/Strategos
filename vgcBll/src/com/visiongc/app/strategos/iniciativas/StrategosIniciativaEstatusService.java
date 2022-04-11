package com.visiongc.app.strategos.iniciativas;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosIniciativaEstatusService
  extends StrategosService
{
  public abstract int delete(IniciativaEstatus paramIniciativaEstatus, Usuario paramUsuario);
  
  public abstract int save(IniciativaEstatus paramIniciativaEstatus, Usuario paramUsuario);
  
  public abstract PaginaLista getIniciativaEstatus(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract Long calcularEstatus(Double paramDouble);
}
