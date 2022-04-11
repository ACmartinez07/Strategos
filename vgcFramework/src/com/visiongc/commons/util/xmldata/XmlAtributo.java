// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 31/08/2018 9:39:03 a. m.
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   XmlAtributo.java

package com.visiongc.commons.util.xmldata;


public class XmlAtributo
{

    public XmlAtributo()
    {
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setValor(String valor)
    {
        this.valor = valor;
    }

    public String getValor()
    {
        return valor;
    }

    private String nombre;
    private String valor;
}