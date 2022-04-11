package com.visiongc.commons.util;

import com.visiongc.framework.arboles.NodoArbol;
import java.util.HashMap;
import java.util.Map;

public class ArbolBean
{

    public ArbolBean()
    {
        nodos = new HashMap();
    }

    public String getListaNodosAbiertos()
    {
        return listaNodosAbiertos;
    }

    public void setListaNodosAbiertos(String listaNodosAbiertos)
    {
        this.listaNodosAbiertos = listaNodosAbiertos;
    }

    public String getListaNodosExcluidos()
    {
        return listaNodosExcluidos;
    }

    public void setListaNodosExcluidos(String listaNodosExcluidos)
    {
        this.listaNodosExcluidos = listaNodosExcluidos;
    }

    public NodoArbol getNodoRaiz()
    {
        return nodoRaiz;
    }

    public void setNodoRaiz(NodoArbol nodoRaiz)
    {
        this.nodoRaiz = nodoRaiz;
    }

    public NodoArbol getNodoSeleccionado()
    {
        return nodoSeleccionado;
    }

    public void setNodoSeleccionado(NodoArbol nodoSeleccionado)
    {
        this.nodoSeleccionado = nodoSeleccionado;
    }

    public String getSeleccionadoId()
    {
        return seleccionadoId;
    }

    public void setSeleccionadoId(String seleccionadoId)
    {
        this.seleccionadoId = seleccionadoId;
    }

    public Map getNodos()
    {
        return nodos;
    }

    public void setNodos(Map nodos)
    {
        this.nodos = nodos;
    }

    public void clear()
    {
        listaNodosAbiertos = null;
        listaNodosExcluidos = null;
        nodoRaiz = null;
        nodoSeleccionado = null;
        seleccionadoId = null;
        nodos = new HashMap();
    }

    private Map nodos;
    private NodoArbol nodoRaiz;
    private NodoArbol nodoSeleccionado;
    private String seleccionadoId;
    private String listaNodosAbiertos;
    private String listaNodosExcluidos;
}