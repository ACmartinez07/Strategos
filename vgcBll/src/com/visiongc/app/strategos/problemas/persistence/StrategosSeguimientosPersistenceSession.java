package com.visiongc.app.strategos.problemas.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.problemas.model.ConfiguracionMensajeEmailSeguimientos;
import com.visiongc.app.strategos.problemas.model.Seguimiento;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosSeguimientosPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getSeguimientos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int getOrdenMaxNumeroReporte(Long paramLong);
  
  public abstract Seguimiento getUltimoSeguimiento(Long paramLong);
  
  public abstract ConfiguracionMensajeEmailSeguimientos getConfiguracionMensajeEmailSeguimientos();
}
