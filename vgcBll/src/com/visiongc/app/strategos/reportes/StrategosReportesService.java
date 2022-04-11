package com.visiongc.app.strategos.reportes;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosReportesService
  extends StrategosService
{
  public abstract int deleteReporte(Reporte paramReporte, Usuario paramUsuario);
  
  public abstract PaginaLista getReportes(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, Object> paramMap, Long paramLong);
  
  public abstract int save(Reporte paramReporte, Usuario paramUsuario);
}
