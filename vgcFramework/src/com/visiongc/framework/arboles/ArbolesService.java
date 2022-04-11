package com.visiongc.framework.arboles;

import com.visiongc.commons.VgcService;
import java.util.List;

// Referenced classes of package com.visiongc.framework.arboles:
//            NodoArbol

public interface ArbolesService
    extends VgcService
{

    public abstract NodoArbol getNodoArbolRaiz(NodoArbol nodoarbol);

    public abstract NodoArbol getNodoArbolRaiz(NodoArbol nodoarbol, Object aobj[]);

    public abstract void refreshNodosArbol(NodoArbol nodoarbol, String s, String s1, Boolean boolean1, Long long1);

    public abstract void refreshNodosArbol(NodoArbol nodoarbol, String s, String s1, Boolean boolean1, Long long1, List list);

    public abstract void refreshNodosArbol(NodoArbol nodoarbol, String s, String s1, Boolean boolean1, Long long1, List list, String s2);

    public abstract void refreshNodosArbol(NodoArbol nodoarbol, String s, String s1, Boolean boolean1, List list);

    public abstract void refreshNodosArbol(NodoArbol nodoarbol, String s, String s1, Boolean boolean1);

    public abstract List getNodosArbolHijos(NodoArbol nodoarbol);

    public abstract List getNodosArbolHijos(NodoArbol nodoarbol, String s);

    public abstract boolean bloquearParaUso(String s, NodoArbol nodoarbol, NodoArbol nodoarbol1);

    public abstract List crearReporteArbol(NodoArbol nodoarbol, Boolean boolean1);

    public abstract List crearReporteArbol(NodoArbol nodoarbol, Boolean boolean1, String s);

    public abstract List getRutaCompleta(NodoArbol nodoarbol);

    public abstract List getRutaCompletaNombres(NodoArbol nodoarbol);

    public abstract String getRutaCompletaNombres(NodoArbol nodoarbol, String s);

    public abstract String getRutaCompletaIds(NodoArbol nodoarbol, String s);

    public abstract NodoArbol findNodoArbol(NodoArbol nodoarbol, String s);

    public abstract NodoArbol getArbolCompleto(NodoArbol nodoarbol, Object aobj[]);
}