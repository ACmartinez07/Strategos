package com.visiongc.framework.auditoria;

import com.visiongc.commons.VgcService;

import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.auditoria.model.Auditoria;
import com.visiongc.framework.model.Usuario;

import java.util.List;
import java.util.Map;

public interface AuditoriasService
    extends VgcService
{

	 public abstract PaginaLista getAuditoria(int paramInt1, int paramInt2, String as[], String as1[], boolean paramBoolean, Map paramMap);
	  
	 public abstract List<Auditoria> getAuditorias(String as[], String as1[], boolean paramBoolean, Map paramMap);
	 
     public abstract int deleteAuditoria(Auditoria paramAuditoria, Usuario paramUsuario);
	  
	 public abstract int saveAuditoria(Auditoria paramAuditoria, Usuario paramUsuario);
	 
}