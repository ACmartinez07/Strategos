package com.visiongc.commons.util.xmldata;

import com.visiongc.commons.util.ListaMap;

// Referenced classes of package com.visiongc.commons.util.xmldata:
//            XmlAtributo, XmlLista

public class XmlNodo
{

    public XmlNodo(String id)
    {
        atributos = null;
        xmlLista = null;
        this.id = null;
        atributos = new ListaMap();
        XmlAtributo atributoId = new XmlAtributo();
        atributoId.setNombre("id");
        atributoId.setValor(id);
        atributos.add(atributoId, atributoId.getNombre());
        xmlLista = new XmlLista();
    }

    public XmlNodo()
    {
        atributos = null;
        xmlLista = null;
        id = null;
        atributos = new ListaMap();
        xmlLista = new XmlLista();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
        XmlAtributo atributoId = new XmlAtributo();
        atributoId.setNombre("id");
        atributoId.setValor(id);
        atributos.add(atributoId, atributoId.getNombre());
    }

    public void setAtributos(ListaMap atributos)
    {
        this.atributos = atributos;
    }

    public ListaMap getAtributos()
    {
        return atributos;
    }

    public void setLista(ListaMap xmlLista)
    {
        this.xmlLista.setLista(xmlLista);
    }

    public ListaMap getLista()
    {
        return xmlLista.getLista();
    }

    public void setXmlLista(XmlLista xmlLista)
    {
        this.xmlLista = xmlLista;
    }

    public XmlLista getXmlLista()
    {
        return xmlLista;
    }

    public XmlAtributo getAtributo(String key)
    {
        return (XmlAtributo)atributos.get(key);
    }

    public XmlAtributo getAtributo(int index)
    {
        return (XmlAtributo)atributos.get(index);
    }

    public void addAtributo(XmlAtributo atributo)
    {
        atributos.add(atributo, atributo.getNombre());
    }

    public void addAtributo(Object elem, String key)
    {
        atributos.add(elem, key);
    }

    public String getValorAtributo(int index)
    {
        if(atributos.get(index) != null)
            return ((XmlAtributo)atributos.get(index)).getValor();
        else
            return null;
    }

    public String getValorAtributo(String key)
    {
        if(atributos.get(key) != null)
            return ((XmlAtributo)atributos.get(key)).getValor();
        else
            return null;
    }

    public void setValorAtributo(String nombre, String valor)
    {
        XmlAtributo xmlAtributo = new XmlAtributo();
        xmlAtributo.setNombre(nombre);
        xmlAtributo.setValor(valor);
        atributos.add(xmlAtributo, nombre);
    }

    public Object getElemLista(String key)
    {
        return xmlLista.getXmlElemLista(key);
    }

    public Object getElemLista(int index)
    {
        return xmlLista.getXmlElemLista(index);
    }

    public void addElemLista(Object elem, String key)
    {
        xmlLista.addElemLista(elem, key);
    }

    private ListaMap atributos;
    private XmlLista xmlLista;
    private String id;
}