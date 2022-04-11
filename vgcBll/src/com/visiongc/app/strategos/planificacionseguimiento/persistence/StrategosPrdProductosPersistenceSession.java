package com.visiongc.app.strategos.planificacionseguimiento.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import java.util.List;
import java.util.Map;

public abstract interface StrategosPrdProductosPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getProductos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract List getProductosPorIniciativa(Long paramLong);
  
  public abstract List getSeguimientosPorIniciativa(Long paramLong, String[] paramArrayOfString1, String[] paramArrayOfString2);
  
  public abstract ListaMap getDependenciasPrdProducto(PrdProducto paramPrdProducto);
}
