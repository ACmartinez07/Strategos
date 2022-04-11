 package com.visiongc.framework.auditoria;

import com.visiongc.commons.VgcService;
import com.visiongc.commons.util.PaginaLista;
import java.util.List;
import java.util.Map;

public interface AuditoriaService
    extends VgcService
{

    public abstract List getObjetosAuditables();

    public abstract PaginaLista getAuditoriasEventos(int i, int j, String as[], String as1[], boolean flag, Map map);

    public abstract PaginaLista getAuditoriasPorAtributo(int i, int j, String as[], String as1[], boolean flag, Map map);
}