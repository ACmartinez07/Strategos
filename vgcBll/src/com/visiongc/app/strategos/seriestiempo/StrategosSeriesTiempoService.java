package com.visiongc.app.strategos.seriestiempo;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosSeriesTiempoService
  extends StrategosService
{
  public abstract PaginaLista getSeriesTiempo(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<Object, Object> paramMap, Usuario paramUsuario);
  
  public abstract PaginaLista getSeriesTiempo(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<Object, Object> paramMap);
  
  public abstract int deleteSerieTiempo(SerieTiempo paramSerieTiempo, Usuario paramUsuario);
  
  public abstract int saveSerieTiempo(SerieTiempo paramSerieTiempo, Usuario paramUsuario);
  
  public abstract PaginaLista getSeriesTiempoByList(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<Object, Object> paramMap, List<SerieTiempo> paramList, Usuario paramUsuario);
}
