package com.visiongc.app.strategos.instrumentos;

import java.util.Map;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;

public abstract interface StrategosTiposConvenioService extends StrategosService{
	
	 public abstract PaginaLista getTiposConvenio(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
	  
     public abstract int deleteTiposConvenio(TipoConvenio paramTiposConvenio, Usuario paramUsuario);
	  
	 public abstract int saveTiposConvenio(TipoConvenio paramTiposConvenio, Usuario paramUsuario);

}
