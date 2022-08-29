package com.visiongc.app.strategos.instrumentos.persistence;

import java.util.List;
import java.util.Map;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoIniciativa;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoPeso;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;

public abstract interface StrategosInstrumentosPersistenceSession extends StrategosPersistenceSession{
	
	  public abstract PaginaLista getInstrumentos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
	  
	  public abstract List<InstrumentoIniciativa> getIniciativasInstrumento(Long paramLong);
	  
	  public abstract List<InstrumentoPeso> getInstrumentoPeso(String paramString);
	  
	  public abstract int updatePesos(InstrumentoIniciativa paramInstrumentoIniciativa, Usuario paramUsuario);
	  
	  public abstract Instrumentos getValoresOriginales(Long paramLong);
	  
	  public abstract Instrumentos getInstrumentoByIndicador(long indicadorId);
	  
	  public abstract ListaMap getDependenciasCiclicasInstrumento(Instrumentos paramInstrumento);
	  
	  public abstract ListaMap getDependenciasInstrumento(Instrumentos paramInstrumento);
}
