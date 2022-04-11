package com.visiongc.framework.transaccion;

import com.visiongc.commons.VgcService;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public interface TransaccionService
    extends VgcService
{

    public abstract PaginaLista getTransacciones(int i, int j, String s, String s1, boolean flag, Map map);

    public abstract Long getNumeroTransacciones(Map map);

    public abstract int saveTransaccion(Transaccion transaccion, Usuario usuario);

    public abstract int deleteTransaccion(Transaccion transaccion, Usuario usuario);
}