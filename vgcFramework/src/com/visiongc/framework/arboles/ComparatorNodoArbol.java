package com.visiongc.framework.arboles;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.Collator;
import java.util.Comparator;

public class ComparatorNodoArbol
    implements Comparator
{

    public ComparatorNodoArbol(String atributo)
    {
        doCompare = Boolean.valueOf(true);
        this.atributo = atributo;
        this.atributo = (new StringBuilder(String.valueOf(atributo.substring(0, 1).toUpperCase()))).append(atributo.substring(1)).toString();
    }

    public ComparatorNodoArbol(String atributo, Boolean doCompare)
    {
        this.doCompare = Boolean.valueOf(true);
        this.atributo = atributo;
        this.atributo = (new StringBuilder(String.valueOf(atributo.substring(0, 1).toUpperCase()))).append(atributo.substring(1)).toString();
        this.doCompare = doCompare;
    }

    public int compare(Object obj1, Object obj2)
    {
        String valor1 = "";
        String valor2 = "";
        Class claseBase = obj1.getClass();
        Class tipoParametros[] = new Class[0];
        String parametros[] = new String[0];
        try
        {
            valor1 = ((Serializable)claseBase.getMethod((new StringBuilder("get")).append(atributo).toString(), tipoParametros).invoke(obj1, parametros)).toString();
            valor2 = ((Serializable)claseBase.getMethod((new StringBuilder("get")).append(atributo).toString(), tipoParametros).invoke(obj2, parametros)).toString();
        }
        catch(Exception exception) { }
        if(doCompare.booleanValue())
            return Collator.getInstance().compare(valor1, valor2);
        else
            return 1;
    }

    private String atributo;
    private Boolean doCompare;
}