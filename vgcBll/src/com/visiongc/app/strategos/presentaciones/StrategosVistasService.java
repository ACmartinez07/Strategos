package com.visiongc.app.strategos.presentaciones;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosVistasService
  extends StrategosService
{
  public abstract PaginaLista getVistas(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteVista(Vista paramVista, Usuario paramUsuario);
  
  public abstract int saveVista(Vista paramVista, Usuario paramUsuario);
}
