package com.visiongc.framework.auditoria.persistence;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.List;
import java.util.Map;

public interface AuditoriaPersistenceSession
    extends VgcPersistenceSession
{

    public abstract List getObjetosAuditables();

    public abstract PaginaLista getAuditoriasEventos(int i, int j, String as[], String as1[], boolean flag, Map map);

    public abstract PaginaLista getAuditoriasPorAtributo(int i, int j, String as[], String as1[], boolean flag, Map map);
}