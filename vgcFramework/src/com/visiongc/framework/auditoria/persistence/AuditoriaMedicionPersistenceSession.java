package com.visiongc.framework.auditoria.persistence;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;

import java.util.List;
import java.util.Map;

public interface AuditoriaMedicionPersistenceSession
    extends VgcPersistenceSession
{

	 public abstract PaginaLista getAuditoriaMedicion(int paramInt1, int paramInt2, String as[], String as1[], boolean paramBoolean, Map paramMap);

	 public abstract List<AuditoriaMedicion> getAuditoriasMedicion(String as[], String as1[], boolean paramBoolean, Map paramMap);
}