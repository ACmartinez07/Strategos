package com.visiongc.app.strategos.instrumentos;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosCooperantesService
  extends StrategosService
{
  public abstract PaginaLista getCooperantes(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteCooperantes(Cooperante paramCooperante, Usuario paramUsuario);
  
  public abstract int saveCooperantes(Cooperante paramCooperante, Usuario paramUsuario);
  
  
}
