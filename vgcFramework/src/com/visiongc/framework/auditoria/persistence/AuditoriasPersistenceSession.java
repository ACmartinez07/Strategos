package com.visiongc.framework.auditoria.persistence;

import java.util.List;
import java.util.Map;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.auditoria.model.Auditoria;


public interface AuditoriasPersistenceSession 
extends VgcPersistenceSession{
	
	public abstract PaginaLista getAuditoria(int paramInt1, int paramInt2, String as[], String as1[], boolean paramBoolean, Map paramMap);

	 public abstract List<Auditoria> getAuditorias(String as[], String as1[], boolean paramBoolean, Map paramMap);	
	
}
