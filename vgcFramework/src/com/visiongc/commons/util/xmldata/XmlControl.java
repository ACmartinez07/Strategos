package com.visiongc.commons.util.xmldata;

import com.visiongc.commons.util.ListaMap;
import java.util.Iterator;
import java.util.List;
import org.dom4j.*;

// Referenced classes of package com.visiongc.commons.util.xmldata:
//            XmlNodo, XmlLista, XmlAtributo

public class XmlControl
{

    public XmlControl()
    {
    }

    public String buildXml(XmlNodo xmlNodo)
    {
        Document document = DocumentHelper.createDocument();
        buildXmlElem(xmlNodo, document);
        return document.asXML();
    }

    private void buildXmlElem(Object xmlElem, Document document)
    {
        boolean esXmlNodo = xmlElem.getClass().equals(XmlNodo.class);
        boolean esXmlLista = xmlElem.getClass().equals(XmlLista.class);
        if(esXmlNodo)
            buildXmlNodo((XmlNodo)xmlElem, document.addElement("xmlNodo"));
        else
        if(esXmlLista)
            buildXmlLista((XmlLista)xmlElem, document.addElement("xmlLista"));
    }

    private void buildXmlElem(Object xmlElem, Element element)
    {
        boolean esXmlNodo = xmlElem.getClass().equals(XmlNodo.class);
        boolean esXmlLista = xmlElem.getClass().equals(XmlLista.class);
        if(esXmlNodo)
            buildXmlNodo((XmlNodo)xmlElem, element.addElement("xmlNodo"));
        else
        if(esXmlLista)
            buildXmlLista((XmlLista)xmlElem, element.addElement("xmlLista").addAttribute("id", ((XmlLista)xmlElem).getId()));
    }

    private void buildXmlNodo(XmlNodo xmlNodo, Element element)
    {
        XmlAtributo xmlAtributo;
        for(Iterator atrib = xmlNodo.getAtributos().iterator(); atrib.hasNext(); element.addAttribute(xmlAtributo.getNombre(), xmlAtributo.getValor()))
            xmlAtributo = (XmlAtributo)atrib.next();

        buildXmlElem(xmlNodo.getXmlLista(), element);
    }

    private void buildXmlLista(XmlLista xmlLista, Element element)
    {
        Object xmlElem;
        for(Iterator elem = xmlLista.getLista().iterator(); elem.hasNext(); buildXmlElem(xmlElem, element))
            xmlElem = elem.next();

    }

    public XmlNodo readXml(String xmlData)
    {
        XmlNodo xmlNodo = new XmlNodo();
        Document document = null;
        try
        {
            document = DocumentHelper.parseText(xmlData);
        }
        catch(Exception exception) { }
        Element element = document.getRootElement();
        readXmlElem(xmlNodo, element);
        return xmlNodo;
    }

    private void readXmlNodo(XmlNodo xmlNodo, Element element)
    {
        XmlAtributo xmlAtributo;
        for(Iterator atrib = element.attributes().iterator(); atrib.hasNext(); xmlNodo.addAtributo(xmlAtributo, xmlAtributo.getNombre()))
        {
            Attribute atributo = (Attribute)atrib.next();
            xmlAtributo = new XmlAtributo();
            xmlAtributo.setNombre(atributo.getName());
            xmlAtributo.setValor(atributo.getValue());
        }

        readXmlElem(xmlNodo.getXmlLista(), element.element("xmlLista"));
    }

    private void readXmlLista(XmlLista xmlLista, Element element)
    {
        for(Iterator elem = element.elementIterator(); elem.hasNext();)
        {
            Element readElem = (Element)elem.next();
            XmlNodo xmlNodo = new XmlNodo();
            XmlLista xmlListaElem = new XmlLista();
            if(readElem.getName().equals("xmlNodo"))
            {
                readXmlElem(xmlNodo, readElem);
                xmlLista.addElemLista(xmlNodo, xmlNodo.getValorAtributo("id"));
            } else
            if(readElem.getName().equals("xmlLista"))
            {
                readXmlElem(xmlListaElem, readElem);
                xmlLista.addElemLista(xmlListaElem, xmlListaElem.getId());
            }
        }

    }

    private void readXmlElem(Object xmlElem, Element element)
    {
        boolean esXmlNodo = xmlElem.getClass().equals(XmlNodo.class);
        boolean esXmlLista = xmlElem.getClass().equals(XmlLista.class);
        if(esXmlNodo)
            readXmlNodo((XmlNodo)xmlElem, element);
        else
        if(esXmlLista)
            readXmlLista((XmlLista)xmlElem, element);
    }
}