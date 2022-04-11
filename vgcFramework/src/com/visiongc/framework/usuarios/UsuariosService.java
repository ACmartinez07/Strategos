package com.visiongc.framework.usuarios;

import com.visiongc.commons.VgcService;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Grupo;
import com.visiongc.framework.model.PwdHistoria;
import com.visiongc.framework.model.Usuario;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface UsuariosService
  extends VgcService
{
  public abstract PaginaLista getUsuarios(int paramInt, String paramString1, String paramString2);
  
  public abstract PaginaLista getUsuarios(int paramInt, String paramString1, String paramString2, boolean paramBoolean, Map<String, Object> paramMap);
  
  public abstract int saveUsuario(Usuario paramUsuario1, Usuario paramUsuario2);
  
  public abstract int savePwdHistoria(PwdHistoria paramPwdHistoria);
  
  public abstract int deleteUsuario(Usuario paramUsuario1, Usuario paramUsuario2);
  
  public abstract int bloquearUsuario(Long paramLong);
  
  public abstract int deshabilitarUsuario(Long paramLong);
  
  public abstract boolean checkPwdHistoria(PwdHistoria paramPwdHistoria, int paramInt);
  
  public abstract int getTotalOrganizacionesPorUsuario(Long paramLong);
  
  public abstract int getTotalGruposPorUsuario(Long paramLong);
  
  public abstract PaginaLista getGrupos(int paramInt, String paramString1, String paramString2);
  
  public abstract int saveGrupo(Grupo paramGrupo, Usuario paramUsuario);
  
  public abstract int deleteGrupo(Grupo paramGrupo, Usuario paramUsuario);
  
  public abstract int getTotalOrganizacionesPorGrupo(Long paramLong);
  
  public abstract int getTotalUsuariosPorGrupo(Long paramLong);
  
  public abstract void close();
  
  public abstract boolean checkLicencia(HttpServletRequest paramHttpServletRequest);
}
