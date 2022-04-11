package com.visiongc.app.strategos.portafolios;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativa;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosPortafoliosService
  extends StrategosService
{
  public abstract PaginaLista getPortafolios(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int delete(Portafolio paramPortafolio, Usuario paramUsuario);
  
  public abstract int save(Portafolio paramPortafolio, Boolean paramBoolean, Usuario paramUsuario);
  
  public abstract int asociarIniciativa(Long paramLong1, Long paramLong2, Usuario paramUsuario);
  
  public abstract int desasociarIniciativa(Long paramLong1, Long paramLong2, Usuario paramUsuario);
  
  public abstract Portafolio getValoresOriginales(Long paramLong);
  
  public abstract int calcular(Portafolio paramPortafolio, Usuario paramUsuario);
  
  public abstract List<PortafolioIniciativa> getIniciativasPortafolio(Long paramLong);
  
  public abstract int saveIniciativasPortafolio(List<PortafolioIniciativa> paramList, Usuario paramUsuario);
  
  public abstract int updatePesos(PortafolioIniciativa paramPortafolioIniciativa, Usuario paramUsuario);
}
