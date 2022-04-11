package com.visiongc.app.strategos.presentaciones;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosCeldasService
  extends StrategosService
{
  public abstract PaginaLista getCeldas(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap, Usuario paramUsuario);
  
  public abstract int saveCelda(Celda paramCelda, Usuario paramUsuario);
  
  public abstract int deleteCelda(Celda paramCelda, Usuario paramUsuario);
}
