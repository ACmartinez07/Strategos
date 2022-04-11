 package com.visiongc.framework.auditoria;


import com.visiongc.commons.VgcService;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.model.Usuario;

import java.util.List;
import java.util.Map;

public interface AuditoriaMedicionService
    extends VgcService
{

	 public abstract PaginaLista getAuditoriaMedicion(int paramInt1, int paramInt2, String as[], String as1[], boolean paramBoolean, Map paramMap);
	  
	 public abstract List<AuditoriaMedicion> getAuditoriasMedicion(String as[], String as1[], boolean paramBoolean, Map paramMap);
	 
     public abstract int deleteAuditoriaMedicion(AuditoriaMedicion paramCategoriaMedicion, Usuario paramUsuario);
	  
	 public abstract int saveAuditoriaMedicion(AuditoriaMedicion paramCategoriaMedicion, Usuario paramUsuario);
	 
}