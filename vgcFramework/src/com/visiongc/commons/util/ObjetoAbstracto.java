package com.visiongc.commons.util;

import java.lang.reflect.Method;

public class ObjetoAbstracto
{

    public ObjetoAbstracto()
    {
    }

    public static boolean implementaInterface(Object objeto, String nombreInterface)
    {
        Class interfaces[] = objeto.getClass().getInterfaces();
        for(int i = 0; i < interfaces.length; i++)
        {
            Class clase = interfaces[i];
            if(getClassSimpleName(clase).equalsIgnoreCase(nombreInterface))
                return true;
        }

        return false;
    }

    public static String ejecutarReturnString(Object objetoBase, String nombreMetodo)
    {
        return ejecutarReturnString(objetoBase, nombreMetodo, new Class[0], new String[0]);
    }

    public static String ejecutarReturnString(Object objetoBase, String nombreMetodo, Class tipoParametros[], String valoresParametros[])
    {
        String valor = "";
        Class claseBase = objetoBase.getClass();
        try
        {
            valor = claseBase.getMethod(nombreMetodo, tipoParametros).invoke(objetoBase, valoresParametros).toString();
        }
        catch(Exception exception) { }
        return valor;
    }

    public static Object ejecutarReturnObject(Object objetoBase, String nombreMetodo)
    {
        return ejecutarReturnObject(objetoBase, nombreMetodo, new Class[0], new Object[0]);
    }

    public static Object ejecutarReturnObject(Object objetoBase, String nombreMetodo, Class tipoParametros[], Object valoresParametros[])
    {
        Object valor = null;
        Class claseBase = objetoBase.getClass();
        try
        {
            valor = claseBase.getMethod(nombreMetodo, tipoParametros).invoke(objetoBase, valoresParametros);
        }
        catch(Exception exception) { }
        return valor;
    }

    public static void ejecutarVoid(Object objetoBase, String nombreMetodo)
    {
        ejecutarVoid(objetoBase, nombreMetodo, new Class[0], new Object[0]);
    }

    public static void ejecutarVoid(Object objetoBase, String nombreMetodo, Class tipoParametros[], Object valoresParametros[])
    {
        Class claseBase = objetoBase.getClass();
        try
        {
            claseBase.getMethod(nombreMetodo, tipoParametros).invoke(objetoBase, valoresParametros);
        }
        catch(Exception exception) { }
    }

    public static String getClassSimpleName(Object objeto)
    {
        return getClassSimpleName(objeto.getClass());
    }

    public static String getClassSimpleName(Class clase)
    {
        String nombre = clase.getName();
        for(int posicion = nombre.indexOf("."); posicion > -1; posicion = nombre.indexOf("."))
            nombre = nombre.substring(posicion + 1);

        return nombre;
    }
}
