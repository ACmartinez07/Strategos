package com.visiongc.app.strategos.portafolios.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativa;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosPortafoliosPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getPortafolios(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract Portafolio getValoresOriginales(Long paramLong);
  
  public abstract List<PortafolioIniciativa> getIniciativasPortafolio(Long paramLong);
  
  public abstract int updatePesos(PortafolioIniciativa paramPortafolioIniciativa, Usuario paramUsuario);
}
