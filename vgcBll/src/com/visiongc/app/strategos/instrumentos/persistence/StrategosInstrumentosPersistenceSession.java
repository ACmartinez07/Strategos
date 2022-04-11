package com.visiongc.app.strategos.instrumentos.persistence;

import java.util.Map;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;

public abstract interface StrategosInstrumentosPersistenceSession extends StrategosPersistenceSession{
	
	  public abstract PaginaLista getInstrumentos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
	  
}
