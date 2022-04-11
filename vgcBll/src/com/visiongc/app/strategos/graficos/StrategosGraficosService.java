package com.visiongc.app.strategos.graficos;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.graficos.model.Duppont;
import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosGraficosService
  extends StrategosService
{
  public abstract PaginaLista getGraficos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int deleteGrafico(Grafico paramGrafico, Usuario paramUsuario);
  
  public abstract int saveGrafico(Grafico paramGrafico, Usuario paramUsuario);
  
  public abstract int saveDuppont(Duppont paramDuppont, Usuario paramUsuario);
  
  public abstract int deleteDuppont(Duppont paramDuppont, Usuario paramUsuario);
}
