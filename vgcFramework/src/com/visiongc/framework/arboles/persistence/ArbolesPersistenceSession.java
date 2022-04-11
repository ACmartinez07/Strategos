package com.visiongc.framework.arboles.persistence;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.arboles.ObjetoNodoHoja;
import java.util.List;

public interface ArbolesPersistenceSession
    extends VgcPersistenceSession
{

    public abstract List getNodosArbolHijos(NodoArbol nodoarbol);

    public abstract List getNodosArbolHijos(NodoArbol nodoarbol, String s);

    public abstract NodoArbol getNodoArbolRaiz(NodoArbol nodoarbol);

    public abstract NodoArbol getNodoArbolRaiz(NodoArbol nodoarbol, Object aobj[]);

    public abstract List getNodosHoja(NodoArbol nodoarbol, ObjetoNodoHoja objetonodohoja);
}