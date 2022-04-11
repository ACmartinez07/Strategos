package com.visiongc.framework.importacion.persistence;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public interface ImportacionPersistenceSession
    extends VgcPersistenceSession
{

    public abstract PaginaLista getImportaciones(int i, int j, String s, String s1, boolean flag, Map map);
}