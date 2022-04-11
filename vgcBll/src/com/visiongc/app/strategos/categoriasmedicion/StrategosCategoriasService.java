package com.visiongc.app.strategos.categoriasmedicion;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosCategoriasService
  extends StrategosService
{
  public abstract PaginaLista getCategoriasMedicion(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteCategoriaMedicion(CategoriaMedicion paramCategoriaMedicion, Usuario paramUsuario);
  
  public abstract int saveCategoriaMedicion(CategoriaMedicion paramCategoriaMedicion, Usuario paramUsuario);
}
