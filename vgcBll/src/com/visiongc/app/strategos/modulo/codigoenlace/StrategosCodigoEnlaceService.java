package com.visiongc.app.strategos.modulo.codigoenlace;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.modulo.codigoenlace.model.CodigoEnlace;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosCodigoEnlaceService
  extends StrategosService
{
  public abstract PaginaLista getCodigoEnlace(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int deleteCodigoEnlace(CodigoEnlace paramCodigoEnlace, Usuario paramUsuario);
  
  public abstract int saveCodigoEnlace(CodigoEnlace paramCodigoEnlace, Usuario paramUsuario);
}
