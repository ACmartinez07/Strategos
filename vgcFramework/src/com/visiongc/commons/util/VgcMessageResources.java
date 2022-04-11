package com.visiongc.commons.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class VgcMessageResources
{

    public VgcMessageResources(ResourceBundle resourceBundle)
    {
        messages = resourceBundle;
    }

    public static VgcMessageResources getVgcMessageResources()
    {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("MessageResources");
        if(resourceBundle != null)
        {
            VgcMessageResources vmr = new VgcMessageResources(resourceBundle);
            return vmr;
        } else
        {
            return null;
        }
    }

    public static VgcMessageResources getVgcMessageResources(String sufijo)
    {
        ResourceBundle resourceBundle = ResourceBundle.getBundle((new StringBuilder("MessageResources")).append(sufijo).toString());
        if(resourceBundle != null)
        {
            VgcMessageResources vmr = new VgcMessageResources(resourceBundle);
            return vmr;
        } else
        {
            return null;
        }
    }

    public static VgcMessageResources getVgcMessageResources(String nombre, int tipoNombre)
    {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(getNombreArchivoRecursos(nombre, tipoNombre));
        if(resourceBundle != null)
        {
            VgcMessageResources vmr = new VgcMessageResources(resourceBundle);
            return vmr;
        } else
        {
            return null;
        }
    }

    public static VgcMessageResources getVgcMessageResources(String nombre, int tipoNombre, Locale locale)
    {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(getNombreArchivoRecursos(nombre, tipoNombre), locale);
        if(resourceBundle != null && resourceBundle.getLocale().getCountry().equals(locale.getCountry()))
        {
            VgcMessageResources vmr = new VgcMessageResources(resourceBundle);
            return vmr;
        } else
        {
            return null;
        }
    }

    private static String getNombreArchivoRecursos(String nombre, int tipoNombre)
    {
        String nombreArchivoRecursos = "MessageResources";
        if(tipoNombre == 1)
            nombreArchivoRecursos = nombre;
        else
        if(tipoNombre == 2)
            nombreArchivoRecursos = (new StringBuilder(String.valueOf(nombre))).append("MessageResources").toString();
        else
        if(tipoNombre == 3)
            nombreArchivoRecursos = (new StringBuilder("MessageResources")).append(nombre).toString();
        return nombreArchivoRecursos;
    }

    public static VgcMessageResources getVgcMessageResources(Locale locale)
    {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("MessageResources", locale);
        if(resourceBundle != null)
        {
            VgcMessageResources vmr = new VgcMessageResources(resourceBundle);
            return vmr;
        } else
        {
            return null;
        }
    }

    public static VgcMessageResources getVgcMessageResources(Locale locale, String sufix)
    {
        ResourceBundle resourceBundle = ResourceBundle.getBundle((new StringBuilder("MessageResources")).append(sufix).toString(), locale);
        if(resourceBundle != null)
        {
            VgcMessageResources vmr = new VgcMessageResources(resourceBundle);
            return vmr;
        } else
        {
            return null;
        }
    }

    public String getResource(String key)
    {
        String argsReemplazo[] = new String[0];
        return getResource(key, argsReemplazo);
    }

    public String getResource(String key, String argsReemplazo[])
    {
        String resource = messages.getString(key);
        int numParam = argsReemplazo.length;
        if(resource.indexOf("{") > 0)
        {
            for(int i = 0; i < numParam; i++)
                resource = resource.replaceAll((new StringBuilder("\\{")).append(i).append("\\}").toString(), argsReemplazo[i]);

        }
        return resource;
    }

    public ResourceBundle getResourceBundle()
    {
        return messages;
    }

    private ResourceBundle messages;
    public static final int TIPO_NOMBRE_NOMBRE_COMPLETO = 1;
    public static final int TIPO_NOMBRE_PREFIJO = 2;
    public static final int TIPO_NOMBRE_SUFIJO = 3;
}