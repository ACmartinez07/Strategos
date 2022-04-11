package com.visiongc.framework.importacion;

import com.visiongc.commons.VgcService;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Importacion;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public interface ImportacionService
    extends VgcService
{

    public abstract PaginaLista getImportaciones(int i, int j, String s, String s1, boolean flag, Map map);

    public abstract int saveImportacion(Importacion importacion, Usuario usuario);

    public abstract int deleteImportacion(Importacion importacion, Usuario usuario);
}