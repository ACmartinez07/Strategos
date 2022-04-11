package com.visiongc.framework.message;

import com.visiongc.commons.VgcService;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Message;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public interface MessageService
    extends VgcService
{

    public abstract PaginaLista getMessages(int i, String s, String s1);

    public abstract PaginaLista getMessages(int i, int j, String s, String s1, boolean flag, Map map);

    public abstract int setVisto(Long long1, Long long2, Byte byte1)
        throws Exception;

    public abstract int saveMessage(Message message, Usuario usuario);

    public abstract int deleteMessage(Message message, Usuario usuario);
}