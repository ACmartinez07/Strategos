package com.visiongc.framework.importacion.impl;

import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.importacion.ImportacionService;
import com.visiongc.framework.importacion.persistence.ImportacionPersistenceSession;
import com.visiongc.framework.model.Importacion;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public class ImportacionServiceImpl extends VgcAbstractService
    implements ImportacionService
{

    public ImportacionServiceImpl(ImportacionPersistenceSession inPersistenceSession, boolean persistenceOwned, FrameworkServiceFactory inServiceFactory, VgcMessageResources messageResources)
    {
        super(inPersistenceSession, persistenceOwned, inServiceFactory, messageResources);
        persistenceSession = null;
        persistenceSession = inPersistenceSession;
        persistenceSession = inPersistenceSession;
    }

    public PaginaLista getImportaciones(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
    {
        String tamanoSetPaginas = "5";
        PaginaLista paginaRegistros = persistenceSession.getImportaciones(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
        if(tamanoSetPaginas != null && !tamanoSetPaginas.equals(""))
            paginaRegistros.setTamanoSetPaginas(Integer.parseInt(tamanoSetPaginas));
        if((float)paginaRegistros.getTotal() / (float)paginaRegistros.getTamanoPagina() + 1.0F <= (float)pagina)
        {
            if(pagina > 1)
                paginaRegistros = persistenceSession.getImportaciones(pagina - 1, tamanoPagina, orden, tipoOrden, getTotal, filtros);
            if(tamanoSetPaginas != null && !tamanoSetPaginas.equals(""))
                paginaRegistros.setTamanoSetPaginas(Integer.parseInt(tamanoSetPaginas));
        }
        return paginaRegistros;
    }

    public int saveImportacion(Importacion importacion, Usuario gestor)
    {
        boolean transActiva = false;
        int resDb = 10000;
        String fieldNames[] = new String[2];
        Object fieldValues[] = new Object[2];
        try
        {
            if(!persistenceSession.isTransactionActive())
            {
                persistenceSession.beginTransaction();
                transActiva = true;
            }
            fieldNames[0] = "nombre";
            fieldValues[0] = importacion.getNombre();
            fieldNames[0] = "usuarioId";
            fieldValues[0] = importacion.getUsuarioId();
            if(importacion.getId() == null || importacion.getId().equals(Long.valueOf("0")))
            {
                if(persistenceSession.existsObject(importacion, fieldNames, fieldValues))
                {
                    resDb = 10003;
                } else
                {
                    importacion.setId(new Long(persistenceSession.getUniqueId()));
                    resDb = persistenceSession.insert(importacion, gestor);
                }
            } else
            {
                String idFieldNames[] = new String[1];
                Object idFieldValues[] = new Object[1];
                idFieldNames[0] = "id";
                idFieldValues[0] = importacion.getId();
                if(persistenceSession.existsObject(importacion, fieldNames, fieldValues, idFieldNames, idFieldValues))
                    resDb = 10003;
                else
                    resDb = persistenceSession.update(importacion, gestor);
            }
            if(transActiva)
            {
                if(resDb == 10000)
                    persistenceSession.commitTransaction();
                else
                    persistenceSession.rollbackTransaction();
                transActiva = false;
            }
        }
        catch(Throwable t)
        {
            if(transActiva)
                persistenceSession.rollbackTransaction();
            throw new ChainedRuntimeException(t.getMessage(), t);
        }
        return resDb;
    }

    public int deleteImportacion(Importacion importacion, Usuario gestor)
    {
        boolean transActiva = false;
        int resultado = 10001;
        try
        {
            if(!persistenceSession.isTransactionActive())
            {
                persistenceSession.beginTransaction();
                transActiva = true;
            }
            if(importacion.getId() != null)
                resultado = persistenceSession.delete(importacion, gestor);
            if(resultado == 10000 || resultado == 10001)
            {
                if(transActiva)
                {
                    persistenceSession.commitTransaction();
                    transActiva = false;
                }
            } else
            if(transActiva)
            {
                persistenceSession.rollbackTransaction();
                transActiva = false;
            }
        }
        catch(Throwable t)
        {
            if(transActiva)
            {
                persistenceSession.rollbackTransaction();
                throw new ChainedRuntimeException(t.getMessage(), t);
            }
        }
        return resultado;
    }

    private ImportacionPersistenceSession persistenceSession;
}