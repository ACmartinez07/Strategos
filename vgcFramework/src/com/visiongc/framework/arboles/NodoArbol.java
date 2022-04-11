package com.visiongc.framework.arboles;

import java.util.Collection;

public interface NodoArbol
{

    public abstract String getNodoArbolId();

    public abstract String getNodoArbolPadreId();

    public abstract NodoArbol getNodoArbolPadre();

    public abstract void setNodoArbolPadre(NodoArbol nodoarbol);

    public abstract String getNodoArbolNombre();

    public abstract void setNodoArbolNombre(String s);

    public abstract boolean getNodoArbolHijosCargados();

    public abstract void setNodoArbolHijosCargados(boolean flag);

    public abstract Collection getNodoArbolHijos();

    public abstract void setNodoArbolHijos(Collection collection);

    public abstract String getNodoArbolNombreImagen(Byte byte1);

    public abstract String getNodoArbolNombreToolTipImagen(Byte byte1);

    public abstract String getNodoArbolNombreCampoPadreId();

    public abstract String getNodoArbolNombreCampoId();

    public abstract String getNodoArbolNombreCampoNombre();
}