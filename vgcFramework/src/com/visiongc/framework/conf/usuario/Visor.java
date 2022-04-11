package com.visiongc.framework.conf.usuario;

import java.util.List;

public class Visor
{

    public Visor()
    {
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Integer getTamanoPagina()
    {
        return tamanoPagina;
    }

    public void setTamanoPagina(Integer tamanoPagina)
    {
        this.tamanoPagina = tamanoPagina;
    }

    public Integer getTamanoConjPaginas()
    {
        return tamanoConjPaginas;
    }

    public void setTamanoConjPaginas(Integer tamanoConjPaginas)
    {
        this.tamanoConjPaginas = tamanoConjPaginas;
    }

    public List getColumnas()
    {
        return columnas;
    }

    public void setColumnas(List columnas)
    {
        this.columnas = columnas;
    }

    private String nombre;
    private Integer tamanoPagina;
    private Integer tamanoConjPaginas;
    private List columnas;
}