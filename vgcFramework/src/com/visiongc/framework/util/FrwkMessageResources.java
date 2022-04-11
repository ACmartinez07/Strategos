package com.visiongc.framework.util;

import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.MessageResource;
import java.util.*;

public class FrwkMessageResources
{

    public FrwkMessageResources()
    {
    }

    public static FrwkMessageResources getInstance()
    {
        if(instance == null)
        {
            instance = new FrwkMessageResources();
            instance.fillMap();
        }
        return instance;
    }

    private void fillMap()
    {
        resources = new HashMap();
        FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
        List l = frameworkService.getMessageResources();
        MessageResource messageResource;
        for(Iterator i = l.iterator(); i.hasNext(); resources.put(messageResource.getClave(), messageResource))
            messageResource = (MessageResource)i.next();

        frameworkService.close();
    }

    public String getValor(String clave)
    {
        String valor = null;
        MessageResource mr = (MessageResource)resources.get(clave);
        if(mr != null)
            valor = mr.getValor();
        return valor;
    }

    private static FrwkMessageResources instance = null;
    private HashMap resources;

}