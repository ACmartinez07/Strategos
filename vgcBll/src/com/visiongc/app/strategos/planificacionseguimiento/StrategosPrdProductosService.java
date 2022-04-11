package com.visiongc.app.strategos.planificacionseguimiento;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimiento;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosPrdProductosService
  extends StrategosService
{
  public abstract PaginaLista getProductos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteProducto(PrdProducto paramPrdProducto, Usuario paramUsuario);
  
  public abstract int saveProducto(PrdProducto paramPrdProducto, Usuario paramUsuario);
  
  public abstract List getProductosPorIniciativa(Long paramLong);
  
  public abstract List getSeguimientosPorIniciativa(Long paramLong, String[] paramArrayOfString1, String[] paramArrayOfString2);
  
  public abstract int savePrdSeguimiento(PrdSeguimiento paramPrdSeguimiento, Usuario paramUsuario);
  
  public abstract int deletePrdSeguimiento(PrdSeguimiento paramPrdSeguimiento, Usuario paramUsuario);
}
