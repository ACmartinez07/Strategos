package com.visiongc.app.strategos.reportes;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.reportes.model.ReporteGrafico;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosReportesGraficoService
  extends StrategosService
{
  public abstract int deleteReporte(ReporteGrafico paramReporte, Usuario paramUsuario);
  
  public abstract PaginaLista getReportes(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, Object> paramMap, Long paramLong);
  
  public abstract int save(ReporteGrafico paramReporte, Usuario paramUsuario);
  
  public abstract ReporteGrafico obtenerReporte(Long paramLong);
  
}
