package com.visiongc.app.strategos.organizaciones;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosOrganizacionesService
  extends StrategosService
{
  public abstract int deleteOrganizacion(OrganizacionStrategos paramOrganizacionStrategos, Usuario paramUsuario);
  
  public abstract int saveOrganizacion(OrganizacionStrategos paramOrganizacionStrategos, Usuario paramUsuario);
  
  public abstract List getArbolCompletoOrganizacion(Long paramLong);
  
  public abstract String getRutaCompletaNombresOrganizacion(Long paramLong, String paramString);
  
  public abstract OrganizacionStrategos crearOrganizacionRaiz(Usuario paramUsuario);
  
  public abstract PaginaLista getOrganizaciones(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract List getOrganizacionHijas(Long paramLong, boolean paramBoolean);
}
