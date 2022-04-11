/**
 * 
 */
package com.visiongc.app.strategos.web.struts.util;

/**
 * @author Kerwin
 *
 */
public class Columna 
{
	private String nombre; 
	private String orden;
	private String mostrar;
	private String tamano;

	/** full constructor */
	public Columna(String nombre, String orden, String mostrar, String tamano)
	{
		this.nombre = nombre;
		this.orden = orden;
		this.mostrar = mostrar;
		this.tamano = tamano;
	}
	
	public Columna()
	{
		this.nombre = null;
		this.orden = null;
		this.mostrar = null;
		this.tamano = null;
	}
	
    public String getNombre() 
    {
        return this.nombre;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    public String getOrden() 
    {
        return this.orden;
    }

    public void setOrden(String orden) 
    {
        this.orden = orden;
    }	

    public String getTamano() 
    {
        return this.tamano;
    }

    public void setTamano(String tamano) 
    {
        this.tamano = tamano;
    }	

    public String getMostrar() 
    {
        return this.mostrar;
    }

    public void setMostrar(String mostrar) 
    {
        this.mostrar = mostrar;
    }	
}
