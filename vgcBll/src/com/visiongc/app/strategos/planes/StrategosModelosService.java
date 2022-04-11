package com.visiongc.app.strategos.planes;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.planes.model.Modelo;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosModelosService
  extends StrategosService
{
  public abstract PaginaLista getModelos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, Object> paramMap);
  
  public abstract int deleteModelo(Modelo paramModelo, Usuario paramUsuario);
  
  public abstract int saveModelo(Modelo paramModelo, Usuario paramUsuario);
}
