package com.visiongc.app.strategos.iniciativas;

import java.util.Map;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;

public abstract interface StrategosTipoProyectoService extends StrategosService{
	
	 public abstract PaginaLista getTiposProyecto(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
	  
     public abstract int deleteTipoProyecto(TipoProyecto paramCategoriaMedicion, Usuario paramUsuario);
	  
	 public abstract int saveTipoProyecto(TipoProyecto paramCategoriaMedicion, Usuario paramUsuario);

}
