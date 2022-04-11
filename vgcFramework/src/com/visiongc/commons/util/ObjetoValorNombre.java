package com.visiongc.commons.util;


public class ObjetoValorNombre
{

    public ObjetoValorNombre()
    {
    }

    public String getValor()
    {
        return valor;
    }

    public void setValor(String valor)
    {
        this.valor = valor;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getValorOculto()
    {
        return valorOculto;
    }

    public void setValorOculto(String valorOculto)
    {
        this.valorOculto = valorOculto;
    }

    private String valor;
    private String nombre;
    private String valorOculto;
}