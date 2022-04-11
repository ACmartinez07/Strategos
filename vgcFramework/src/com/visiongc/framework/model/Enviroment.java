package com.visiongc.framework.model;

import java.io.Serializable;

public class Enviroment
    implements Serializable
{

    public Enviroment()
    {
        tipo = null;
        pageLoad = null;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public String getPageLoad()
    {
        return pageLoad;
    }

    public void setPageLoad(String pageLoad)
    {
        this.pageLoad = pageLoad;
    }

    static final long serialVersionUID = 0L;
    private String tipo;
    private String pageLoad;
}