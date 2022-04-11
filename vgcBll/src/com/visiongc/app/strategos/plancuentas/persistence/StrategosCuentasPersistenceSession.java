package com.visiongc.app.strategos.plancuentas.persistence;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import java.util.List;

public abstract interface StrategosCuentasPersistenceSession
  extends VgcPersistenceSession
{
  public abstract List getMascarasCodigoPlanCuentas();
  
  public abstract List getMaximoNivelGrupo();
}
