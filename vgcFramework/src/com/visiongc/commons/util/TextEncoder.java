package com.visiongc.commons.util;


public class TextEncoder
{

    public TextEncoder()
    {
    }

    public static String encodeForJavascript(String source)
        throws Throwable
    {
        String target = source;
        String strfinal = "";
        target = target.replaceAll("'", "&#39");
        char arrStr[] = target.toCharArray();
        for(int i = 0; i < arrStr.length; i++)
            if(arrStr[i] == '\\')
                strfinal = (new StringBuilder(String.valueOf(strfinal))).append(arrStr[i]).append(arrStr[i]).toString();
            else
                strfinal = (new StringBuilder(String.valueOf(strfinal))).append(arrStr[i]).toString();

        target = strfinal;
        return target;
    }

    public static String uRLDecode(String html)
    {
        html = html.replace("%26", "&");
        html = html.replace("%23", "#");
        html = html.replace("%2B", "+");
        html = html.replace("%24", "$");
        return html;
    }

    public static String uRLEncode(String html)
    {
        html = html.replace("&", "%26");
        html = html.replace("#", "%23");
        html = html.replace("+", "%2B");
        html = html.replace("$", "%24");
        return html;
    }

    public static String deleteCharInvalid(String palabra)
    {
        String resultado = palabra;
        if(palabra != null)
        {
            String palabraReplace = " ";
            for(int i = 0; i < palabra.length(); i++)
                if(palabra.codePointAt(i) <= 31)
                    resultado = resultado.replace(palabra.charAt(i), palabraReplace.charAt(0));

        }
        return resultado;
    }
}