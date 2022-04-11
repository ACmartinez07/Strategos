package com.visiongc.app.strategos.responsables;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.responsables.model.util.ConfiguracionResponsable;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface StrategosResponsablesService
  extends StrategosService
{
  public abstract PaginaLista getResponsables(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract PaginaLista getResponsables(int paramInt1, int paramInt2, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteResponsable(Responsable paramResponsable, Usuario paramUsuario);
  
  public abstract int saveResponsable(Responsable paramResponsable, Usuario paramUsuario);
  
  public abstract int guardarGrupoResponsables(long paramLong, long[] paramArrayOfLong, Usuario paramUsuario);
  
  public abstract List getResponsablesAsociables(boolean paramBoolean, long paramLong, Set paramSet, Responsable paramResponsable);
  
  public abstract ConfiguracionResponsable getConfiguracionResponsable();
  
  public abstract Byte getTipoCorreoPorDefectoSent(Long paramLong);
}
