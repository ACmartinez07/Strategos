package com.visiongc.framework.configuracion;

import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Usuario;
import java.util.*;

public class VgcConfiguraciones
{

    public VgcConfiguraciones()
    {
    }

    public static VgcConfiguraciones getInstance()
    {
        if(instance == null)
        {
            instance = new VgcConfiguraciones();
            instance.fillMap();
        }
        return instance;
    }

    private void fillMap()
    {
        configuraciones = new HashMap();
        FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
        List l = frameworkService.getConfiguraciones();
        Configuracion configuracion;
        for(Iterator i = l.iterator(); i.hasNext(); configuraciones.put(configuracion.getParametro(), configuracion))
            configuracion = (Configuracion)i.next();

        frameworkService.close();
    }

    public String getValor(String parametro, String valorPorDefecto, Usuario usuario)
    {
        String valor = null;
        Configuracion configuracion = (Configuracion)configuraciones.get(parametro);
        if(configuracion != null)
        {
            valor = configuracion.getValor();
        } else
        {
            FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
            configuracion = frameworkService.getConfiguracion(parametro);
            boolean saveConfiguracion = false;
            if(configuracion == null)
            {
                configuracion = new Configuracion();
                configuracion.setParametro(parametro);
                configuracion.setValor(valorPorDefecto);
                saveConfiguracion = true;
            } else
            if(configuracion != null && configuracion.getValor() == null)
            {
                configuracion.setValor(valorPorDefecto);
                saveConfiguracion = true;
            } else
            if(configuracion != null && configuracion.getValor() != null)
                valor = configuracion.getValor();
            if(valor != null && !valor.equals("") && saveConfiguracion)
                frameworkService.saveConfiguracion(configuracion, usuario);
            frameworkService.close();
            fillMap();
        }
        return valor;
    }

    private static VgcConfiguraciones instance = null;
    private HashMap configuraciones;

}