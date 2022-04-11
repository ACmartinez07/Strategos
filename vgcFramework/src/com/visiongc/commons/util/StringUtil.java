package com.visiongc.commons.util;

import java.util.*;

public class StringUtil
{

    public StringUtil()
    {
    }

    public static String replace(String source, String pattern, String newText)
    {
        if(pattern == null || pattern.length() == 0)
            return source;
        StringBuffer buf = new StringBuffer(2 * source.length());
        int previndex = 0;
        int index = 0;
        int flen = pattern.length();
        do
        {
            index = source.indexOf(pattern, previndex);
            if(index == -1)
            {
                buf.append(source.substring(previndex));
                break;
            }
            buf.append(source.substring(previndex, index)).append(newText);
            previndex = index + flen;
        } while(true);
        return buf.toString();
    }

    public static String[] split(String str, String splitStr)
    {
        Vector _v = new Vector();
        String _stmp = new String();
        int i = 0;
        int j = 0;
        int cnt = 0;
        while((i = str.indexOf(splitStr, i)) != -1) 
            if(++cnt % 2 == 1)
            {
                if(j == 0)
                {
                    _stmp = str.substring(j, i);
                    _v.add(_stmp);
                    _stmp = new String();
                }
                i = j = i + splitStr.length();
            } else
            {
                _stmp = str.substring(j, i);
                _v.add(_stmp);
                _stmp = new String();
                j = i + splitStr.length();
            }
        if(j <= str.length() - 1)
        {
            _stmp = str.substring(j, str.length());
            _v.add(_stmp);
        }
        if(j == str.length())
        {
            _stmp = "";
            _v.add(_stmp);
        }
        String _array[] = new String[_v.size()];
        for(int k = 0; k < _array.length; k++)
            _array[k] = new String((String)_v.elementAt(k));

        return _array;
    }

    public static String deleteStrings(String str, String cadenasBuscadas[])
    {
        String resultado = "";
        resultado = str;
        for(int i = 0; i < cadenasBuscadas.length; i++)
        {
            String cadenaBuscada = cadenasBuscadas[i];
            resultado = replace(resultado, cadenaBuscada, "");
        }

        return resultado;
    }

    public static String getPlural(String singular)
    {
        String ultimaLetra = singular.substring(singular.length() - 1);
        String plural = singular;
        if(ultimaLetra.equalsIgnoreCase("o") || ultimaLetra.equalsIgnoreCase("a"))
            plural = (new StringBuilder(String.valueOf(plural))).append("s").toString();
        else
            plural = (new StringBuilder(String.valueOf(plural))).append("es").toString();
        return plural;
    }

    public static String trim(String str)
    {
        String resultado = null;
        if(str != null)
        {
            resultado = str;
            boolean continuar = resultado.length() > 0;
            while(continuar) 
            {
                String caracter = resultado.substring(0, 1);
                if(caracter.equals(" "))
                {
                    resultado = resultado.substring(1);
                    continuar = resultado.length() > 0;
                } else
                {
                    continuar = false;
                }
            }
            continuar = resultado.length() > 0;
            while(continuar) 
            {
                int posicion = resultado.length();
                String caracter = resultado.substring(posicion - 1, posicion);
                if(caracter.equals(" "))
                {
                    resultado = resultado.substring(0, posicion - 1);
                    continuar = resultado.length() > 0;
                } else
                {
                    continuar = false;
                }
            }
        }
        return resultado;
    }

    public String getValorCondicionConsulta(Object fieldValue)
    {
        String valor = "";
        if(fieldValue instanceof String)
            valor = (String)fieldValue;
        else
        if(fieldValue instanceof Byte)
            valor = ((Byte)fieldValue).toString();
        else
        if(fieldValue instanceof Boolean)
            valor = ((Boolean)fieldValue).booleanValue() ? "1" : "0";
        else
        if(fieldValue instanceof Long)
            valor = ((Long)fieldValue).toString();
        else
        if(fieldValue instanceof Double)
            valor = ((Double)fieldValue).toString();
        else
        if(fieldValue instanceof Integer)
            valor = ((Integer)fieldValue).toString();
        else
        if(fieldValue instanceof List)
        {
            for(Iterator iter = ((List)fieldValue).iterator(); iter.hasNext();)
            {
                Object valorLista = iter.next();
                valor = (new StringBuilder(String.valueOf(valor))).append(", ").append(getValorCondicionConsulta(valorLista)).toString();
            }

            if(valor.length() > 1)
                valor = (new StringBuilder("(")).append(valor.substring(2)).append(")").toString();
        }
        return valor;
    }
}