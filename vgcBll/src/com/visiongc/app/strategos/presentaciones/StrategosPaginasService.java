package com.visiongc.app.strategos.presentaciones;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosPaginasService
  extends StrategosService
{
  public abstract PaginaLista getPaginas(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int deletePagina(Pagina paramPagina, Usuario paramUsuario);
  
  public abstract int savePagina(Pagina paramPagina, Usuario paramUsuario);
  
  public abstract int cambiarOrdenPaginas(Long paramLong, boolean paramBoolean, Usuario paramUsuario);
  
  public abstract int getOrdenMaximoPaginas(Long paramLong);
}
