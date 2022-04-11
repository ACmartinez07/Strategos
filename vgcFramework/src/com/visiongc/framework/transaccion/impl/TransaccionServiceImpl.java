package com.visiongc.framework.transaccion.impl;

import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.transaccion.TransaccionService;
import com.visiongc.framework.transaccion.persistence.TransaccionPersistenceSession;
import java.util.List;
import java.util.Map;

public class TransaccionServiceImpl extends VgcAbstractService
    implements TransaccionService
{

    public TransaccionServiceImpl(TransaccionPersistenceSession inPersistenceSession, boolean persistenceOwned, FrameworkServiceFactory inServiceFactory, VgcMessageResources messageResources)
    {
        super(inPersistenceSession, persistenceOwned, inServiceFactory, messageResources);
        persistenceSession = null;
        persistenceSession = inPersistenceSession;
        persistenceSession = inPersistenceSession;
    }

    public PaginaLista getTransacciones(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
    {
        String tamanoSetPaginas = "5";
        PaginaLista paginaRegistros = persistenceSession.getTransacciones(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros, false);
        if(tamanoSetPaginas != null && !tamanoSetPaginas.equals(""))
            paginaRegistros.setTamanoSetPaginas(Integer.parseInt(tamanoSetPaginas));
        if((float)paginaRegistros.getTotal() / (float)paginaRegistros.getTamanoPagina() + 1.0F <= (float)pagina)
        {
            if(pagina > 1)
                paginaRegistros = persistenceSession.getTransacciones(pagina - 1, tamanoPagina, orden, tipoOrden, getTotal, filtros, false);
            if(tamanoSetPaginas != null && !tamanoSetPaginas.equals(""))
                paginaRegistros.setTamanoSetPaginas(Integer.parseInt(tamanoSetPaginas));
        }
        return paginaRegistros;
    }

    public Long getNumeroTransacciones(Map filtros)
    {
        return Long.valueOf(((Long)persistenceSession.getTransacciones(0, 0, null, null, false, filtros, true).getLista().get(0)).longValue());
    }

    public int saveTransaccion(Transaccion transaccion, Usuario gestor)
    {
        boolean transActiva = false;
        int resDb = 10000;
        String fieldNames[] = new String[1];
        Object fieldValues[] = new Object[1];
        try
        {
            if(!persistenceSession.isTransactionActive())
            {
                persistenceSession.beginTransaction();
                transActiva = true;
            }
            fieldNames[0] = "nombre";
            fieldValues[0] = transaccion.getNombre();
            if(transaccion.getId() == null || transaccion.getId().equals(Long.valueOf("0")))
            {
                if(persistenceSession.existsObject(transaccion, fieldNames, fieldValues))
                {
                    resDb = 10003;
                } else
                {
                    transaccion.setId(new Long(persistenceSession.getUniqueId()));
                    resDb = persistenceSession.insert(transaccion, gestor);
                }
            } else
            {
                String idFieldNames[] = new String[1];
                Object idFieldValues[] = new Object[1];
                idFieldNames[0] = "id";
                idFieldValues[0] = transaccion.getId();
                if(persistenceSession.existsObject(transaccion, fieldNames, fieldValues, idFieldNames, idFieldValues))
                    resDb = 10003;
                else
                    resDb = persistenceSession.update(transaccion, gestor);
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

    public int deleteTransaccion(Transaccion transaccion, Usuario gestor)
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
            if(transaccion.getId() != null)
                resultado = persistenceSession.delete(transaccion, gestor);
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

    private TransaccionPersistenceSession persistenceSession;
}