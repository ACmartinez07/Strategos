package com.visiongc.app.strategos.unidadesmedida;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosUnidadesService
  extends StrategosService
{
  public abstract PaginaLista getUnidadesMedida(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteUnidadMedida(UnidadMedida paramUnidadMedida, Usuario paramUsuario);
  
  public abstract int saveUnidadMedida(UnidadMedida paramUnidadMedida, Usuario paramUsuario);
  
  public abstract UnidadMedida getUnidadMedidaPorcentaje();
}
