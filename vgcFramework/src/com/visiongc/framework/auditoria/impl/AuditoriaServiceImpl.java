package com.visiongc.framework.auditoria.impl;

import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.framework.auditoria.AuditoriaService;
import com.visiongc.framework.auditoria.persistence.AuditoriaPersistenceSession;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import java.util.List;
import java.util.Map;

public class AuditoriaServiceImpl extends VgcAbstractService
    implements AuditoriaService
{

    public AuditoriaServiceImpl(AuditoriaPersistenceSession persistenceSession, boolean persistenceOwned, FrameworkServiceFactory serviceFactory, VgcMessageResources messageResources)
    {
        super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
        this.persistenceSession = null;
        this.persistenceSession = persistenceSession;
    }

    public List getObjetosAuditables()
    {
        return persistenceSession.getObjetosAuditables();
    }

    public PaginaLista getAuditoriasEventos(int pagina, int tamanoPagina, String orden[], String tipoOrden[], boolean getTotal, Map filtros)
    {
        return persistenceSession.getAuditoriasEventos(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
    }

    public PaginaLista getAuditoriasPorAtributo(int pagina, int tamanoPagina, String orden[], String tipoOrden[], boolean getTotal, Map filtros)
    {
        return persistenceSession.getAuditoriasPorAtributo(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
    }

    private AuditoriaPersistenceSession persistenceSession;
}