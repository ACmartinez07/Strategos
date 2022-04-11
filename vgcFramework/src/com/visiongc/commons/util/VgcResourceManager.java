package com.visiongc.commons.util;

import com.visiongc.framework.util.FrwkMessageResources;
import java.util.*;

// Referenced classes of package com.visiongc.commons.util:
//            VgcMessageResources

public class VgcResourceManager
{

    public VgcResourceManager()
    {
    }

    public static VgcMessageResources getMessageResources()
    {
        return VgcMessageResources.getVgcMessageResources();
    }

    public static VgcMessageResources getMessageResources(String sufix)
    {
        VgcMessageResources vmr = (VgcMessageResources)messageResourcesMap.get(sufix);
        if(vmr == null)
        {
            vmr = VgcMessageResources.getVgcMessageResources(sufix);
            messageResourcesMap.put(sufix, vmr);
        }
        return vmr;
    }

    public static VgcMessageResources getMessageResources(String nombre, int tipoNombre)
    {
        VgcMessageResources vmr = (VgcMessageResources)messageResourcesMap.get(nombre);
        if(vmr == null)
        {
            vmr = VgcMessageResources.getVgcMessageResources(nombre, tipoNombre);
            messageResourcesMap.put(nombre, vmr);
        }
        return vmr;
    }

    public static VgcMessageResources getMessageResources(Locale locale, String sufix)
    {
        VgcMessageResources vmr = (VgcMessageResources)messageResourcesMap.get((new StringBuilder(String.valueOf(sufix))).append("_").append(locale.getLanguage()).append("_").append(locale.getCountry()).toString());
        if(vmr == null)
        {
            vmr = VgcMessageResources.getVgcMessageResources(locale, sufix);
            messageResourcesMap.put((new StringBuilder(String.valueOf(sufix))).append("_").append(locale.getLanguage()).append("_").append(locale.getCountry()).toString(), vmr);
        }
        return vmr;
    }

    public static String getResourceApp(String key)
    {
        String argsReemplazo[] = new String[0];
        return getResourceApp(key, argsReemplazo);
    }

    public static String getResourceApp(String key, Locale locale)
    {
        String argsReemplazo[] = new String[0];
        return getResourceApp(key, argsReemplazo, locale);
    }

    public static String getResourceApp(String key, String argsReemplazo[])
    {
        String recursoLenguaje = null;
        String recursoLenguajeFramework = null;
        String recursoLenguajeFrameworkWeb = null;
        int numParam = 0;
        Iterator iter = messageResourcesMap.keySet().iterator();
        while(iter.hasNext()) 
        {
            String clave = (String)iter.next();
            if(clave.indexOf("_") != -1)
                continue;
            VgcMessageResources recursos = (VgcMessageResources)messageResourcesMap.get(clave);
            if(clave.indexOf("FrameworkWeb") > -1)
            {
                try
                {
                    recursoLenguajeFrameworkWeb = recursos.getResource(key);
                }
                catch(Exception exception) { }
                continue;
            }
            if(clave.indexOf("Framework") > -1)
            {
                try
                {
                    recursoLenguajeFramework = recursos.getResource(key);
                }
                catch(Exception exception1) { }
                continue;
            }
            try
            {
                recursoLenguaje = recursos.getResource(key);
            }
            catch(Exception exception2) { }
            if(recursoLenguaje != null)
                break;
        }
        String recursoLenguajeDb = FrwkMessageResources.getInstance().getValor(key);
        if(recursoLenguajeDb != null && !recursoLenguajeDb.equals(""))
            recursoLenguaje = recursoLenguajeDb;
        else
        if(recursoLenguaje == null)
            if(recursoLenguajeFrameworkWeb != null)
                recursoLenguaje = recursoLenguajeFrameworkWeb;
            else
                recursoLenguaje = recursoLenguajeFramework;
        if(recursoLenguaje != null)
        {
            numParam = argsReemplazo.length;
            if(recursoLenguaje.indexOf("{") > 0)
            {
                for(int i = 0; i < numParam; i++)
                    recursoLenguaje = recursoLenguaje.replaceAll((new StringBuilder("\\{")).append(i).append("\\}").toString(), argsReemplazo[i]);

            }
        }
        return recursoLenguaje;
    }

    public static String getResourceApp(String key, String argsReemplazo[], Locale locale)
    {
        String recursoLenguaje = null;
        String recursoLenguajeFramework = null;
        String recursoLenguajeFrameworkWeb = null;
        String strLocale = (new StringBuilder(String.valueOf(locale.getLanguage()))).append("_").append(locale.getCountry()).toString();
        int numParam = 0;
        Iterator iter = messageResourcesMap.keySet().iterator();
        while(iter.hasNext()) 
        {
            String clave = (String)iter.next();
            if(clave.indexOf(strLocale) <= -1)
                continue;
            VgcMessageResources recursos = (VgcMessageResources)messageResourcesMap.get(clave);
            if(clave.indexOf("FrameworkWeb") > -1)
            {
                try
                {
                    recursoLenguajeFrameworkWeb = recursos.getResource(key);
                }
                catch(Exception exception) { }
                continue;
            }
            if(clave.indexOf("Framework") > -1)
            {
                try
                {
                    recursoLenguajeFramework = recursos.getResource(key);
                }
                catch(Exception exception1) { }
                continue;
            }
            try
            {
                recursoLenguaje = recursos.getResource(key);
            }
            catch(Exception exception2) { }
            if(recursoLenguaje != null)
                break;
        }
        if(recursoLenguaje == null)
            if(recursoLenguajeFrameworkWeb != null)
                recursoLenguaje = recursoLenguajeFrameworkWeb;
            else
                recursoLenguaje = recursoLenguajeFramework;
        if(recursoLenguaje != null)
        {
            numParam = argsReemplazo.length;
            if(recursoLenguaje.indexOf("{") > 0)
            {
                for(int i = 0; i < numParam; i++)
                    recursoLenguaje = recursoLenguaje.replaceAll((new StringBuilder("\\{")).append(i).append("\\}").toString(), argsReemplazo[i]);

            }
        } else
        {
            recursoLenguaje = getResourceApp(key, argsReemplazo);
        }
        return recursoLenguaje;
    }

    private static Map messageResourcesMap = null;

    static 
    {
        try
        {
            messageResourcesMap = new HashMap();
            getMessageResources("Framework");
        }
        catch(Exception exception) { }
    }
}