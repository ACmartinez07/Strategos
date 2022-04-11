package com.visiongc.framework.usuarios.persistence;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.PwdHistoria;
import java.util.Map;

public abstract interface UsuariosPersistenceSession
  extends VgcPersistenceSession
{
  public abstract PaginaLista getUsuarios(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, Object> paramMap);
  
  public abstract PaginaLista getGrupos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, Object> paramMap);
  
  public abstract int getTotalOrganizacionesPorUsuario(Long paramLong);
  
  public abstract int getTotalGruposPorUsuario(Long paramLong);
  
  public abstract int getTotalOrganizacionesPorGrupo(Long paramLong);
  
  public abstract int getTotalUsuariosPorGrupo(Long paramLong);
  
  public abstract int bloquearUsuario(Long paramLong);
  
  public abstract int deshabilitarUsuario(Long paramLong);
  
  public abstract boolean checkPwdHistoria(PwdHistoria paramPwdHistoria, int paramInt);
  
  public abstract int deleteDependencias(Long paramLong);
}
