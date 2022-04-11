

package com.visiongc.framework.util;

import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ObjetoSistema;
import java.util.*;

public class ObjetosSistema
{

    public ObjetosSistema()
    {
    }

    public static ObjetosSistema getInstance()
    {
        if(instance == null)
        {
            instance = new ObjetosSistema();
            instance.fillMap();
        }
        return instance;
    }

    private void fillMap()
    {
        objetos = new HashMap();
        FrameworkService fs = FrameworkServiceFactory.getInstance().openFrameworkService();
        List l = fs.getObjetosSistema();
        ObjetoSistema os;
        for(Iterator i = l.iterator(); i.hasNext(); objetos.put(os.getObjetoId(), os))
            os = (ObjetoSistema)i.next();

        fs.close();
    }

    public ObjetoSistema getObjetoSistema(String objetoId)
    {
        return (ObjetoSistema)objetos.get(objetoId);
    }

    public Long GetHeapSizeTotal()
    {
        Long heapSize = Long.valueOf(Runtime.getRuntime().totalMemory() / 0x100000L);
        return heapSize;
    }

    public Long GetHeapSizeFree()
    {
        Long heapSize = Long.valueOf(Runtime.getRuntime().freeMemory() / 0x100000L);
        return heapSize;
    }

    public Long GetHeapSizeMax()
    {
        Long heapSize = Long.valueOf(Runtime.getRuntime().maxMemory() / 0x100000L);
        return heapSize;
    }

    public Long GetHeapSizeUsed()
    {
        Long heapSize = Long.valueOf(GetHeapSizeMax().longValue() - GetHeapSizeFree().longValue());
        return heapSize;
    }

    private static ObjetosSistema instance = null;
    private HashMap objetos;
    private static final int MegaBytes = 0x100000;

}