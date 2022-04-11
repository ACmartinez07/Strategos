package com.visiongc.commons.util.xmldata;

import com.visiongc.commons.util.ListaMap;

public class XmlLista
{

    public XmlLista()
    {
        lista = new ListaMap();
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setLista(ListaMap lista)
    {
        this.lista = lista;
    }

    public ListaMap getLista()
    {
        return lista;
    }

    public Object getXmlElemLista(String key)
    {
        return lista.get(key);
    }

    public Object getXmlElemLista(int index)
    {
        return lista.get(index);
    }

    public void addElemLista(Object elem, String key)
    {
        lista.add(elem, key);
    }

    private String id;
    private ListaMap lista;
}