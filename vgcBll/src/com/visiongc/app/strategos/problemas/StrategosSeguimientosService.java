package com.visiongc.app.strategos.problemas;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.problemas.model.ConfiguracionMensajeEmailSeguimientos;
import com.visiongc.app.strategos.problemas.model.Seguimiento;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosSeguimientosService
  extends StrategosService
{
  public abstract PaginaLista getSeguimientos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteSeguimiento(Seguimiento paramSeguimiento, Usuario paramUsuario);
  
  public abstract int saveSeguimiento(Seguimiento paramSeguimiento, Usuario paramUsuario);
  
  public abstract int saveConfiguracionMensajeEmailSeguimientos(ConfiguracionMensajeEmailSeguimientos paramConfiguracionMensajeEmailSeguimientos, Usuario paramUsuario);
  
  public abstract ConfiguracionMensajeEmailSeguimientos getConfiguracionMensajeEmailSeguimientos();
  
  public abstract ConfiguracionMensajeEmailSeguimientos crearConfiguracionMensajeEmailSeguimientos(Usuario paramUsuario);
  
  public abstract Boolean seguimientoCerrado(Long paramLong);
  
  public abstract Boolean esDiaSeguimiento(Long paramLong);
  
  public abstract Boolean existeSeguimiento(Long paramLong);
}
