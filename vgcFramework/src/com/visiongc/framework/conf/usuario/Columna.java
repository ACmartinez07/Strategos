package com.visiongc.framework.conf.usuario;


public class Columna
{

    public Columna(String nombre, int orden, boolean mostrar, Integer tamano)
    {
        this.nombre = nombre;
        this.orden = Integer.valueOf(orden);
        this.mostrar = Boolean.valueOf(mostrar);
        this.tamano = tamano;
    }

    public Columna()
    {
        nombre = null;
        orden = null;
        mostrar = Boolean.valueOf(false);
        tamano = null;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Integer getOrden()
    {
        return orden;
    }

    public void setOrden(Integer orden)
    {
        this.orden = orden;
    }

    public Integer getTamano()
    {
        return tamano;
    }

    public void setTamano(Integer tamano)
    {
        this.tamano = tamano;
    }

    public Boolean getMostrar()
    {
        return mostrar;
    }

    public void setMostrar(Boolean mostrar)
    {
        this.mostrar = mostrar;
    }

    private String nombre;
    private Integer orden;
    private Boolean mostrar;
    private Integer tamano;
}