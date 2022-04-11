package com.visiongc.framework.message.impl;

import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.message.MessageService;
import com.visiongc.framework.message.persistence.MessagePersistenceSession;
import com.visiongc.framework.model.Message;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public class MessageServiceImpl extends VgcAbstractService
    implements MessageService
{

    public MessageServiceImpl(MessagePersistenceSession inPersistenceSession, boolean persistenceOwned, FrameworkServiceFactory inServiceFactory, VgcMessageResources messageResources)
    {
        super(inPersistenceSession, persistenceOwned, inServiceFactory, messageResources);
        persistenceSession = null;
        persistenceSession = inPersistenceSession;
        persistenceSession = inPersistenceSession;
    }

    public PaginaLista getMessages(int pagina, String orden, String tipoOrden)
    {
        return getMessages(pagina, 20, orden, tipoOrden, true, null);
    }

    public PaginaLista getMessages(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
    {
        String tamanoSetPaginas = "5";
        PaginaLista paginaMessages = persistenceSession.getMessages(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
        if(tamanoSetPaginas != null && !tamanoSetPaginas.equals(""))
            paginaMessages.setTamanoSetPaginas(Integer.parseInt(tamanoSetPaginas));
        if((float)paginaMessages.getTotal() / (float)paginaMessages.getTamanoPagina() + 1.0F <= (float)pagina)
        {
            if(pagina > 1)
                paginaMessages = persistenceSession.getMessages(pagina - 1, tamanoPagina, orden, tipoOrden, getTotal, filtros);
            if(tamanoSetPaginas != null && !tamanoSetPaginas.equals(""))
                paginaMessages.setTamanoSetPaginas(Integer.parseInt(tamanoSetPaginas));
        }
        return paginaMessages;
    }

    public int setVisto(Long usuarioId, Long fecha, Byte estatus)
        throws Exception
    {
        return persistenceSession.setVisto(usuarioId, fecha, estatus);
    }

    public int saveMessage(Message message, Usuario gestor)
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
            fieldNames[0] = "usuarioId";
            fieldValues[0] = message.getUsuarioId();
            fieldNames[1] = "fecha";
            fieldValues[1] = message.getFecha();
            if(persistenceSession.existsObject(message, fieldNames, fieldValues))
                resDb = persistenceSession.update(message, gestor);
            else
                resDb = persistenceSession.insert(message, gestor);
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

    public int deleteMessage(Message message, Usuario gestor)
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
            if(message.getUsuarioId() != null)
                resultado = persistenceSession.delete(message, gestor);
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

    private MessagePersistenceSession persistenceSession;
}