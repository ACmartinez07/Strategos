package com.visiongc.commons.util;

import java.util.ArrayList;
import java.util.HashMap;

public class ListaMap extends ArrayList
{

    public ListaMap()
    {
        hashIndex = new HashMap();
        hashIndiceClave = new HashMap();
    }

    public boolean containsKey(String key)
    {
        return hashIndex.containsKey(key);
    }

    public void add(Object object, String key)
    {
        if(!hashIndex.containsKey(key))
        {
            add(object);
            hashIndex.put(key, new Integer(size() - 1));
            hashIndiceClave.put(new Integer(size() - 1), key);
        }
    }

    public Object get(String key)
    {
        if(hashIndex.containsKey(key))
            return get(Integer.parseInt(hashIndex.get(key).toString()));
        else
            return null;
    }

    public String getKey(int index)
    {
        return (String)hashIndiceClave.get(new Integer(index));
    }

    public boolean remove(String key)
    {
        if(hashIndex.containsKey(key))
        {
            int index = Integer.parseInt(hashIndex.get(key).toString());
            remove(index);
            hashIndiceClave.remove(hashIndex.get(key));
            hashIndex.remove(key);
            for(int i = ++index; i <= size(); i++)
            {
                Integer indice = new Integer(i);
                String clave = (String)hashIndiceClave.get(indice);
                hashIndiceClave.remove(indice);
                hashIndiceClave.put(new Integer(i - 1), clave);
                hashIndex.remove(clave);
                hashIndex.put(clave, new Integer(i - 1));
            }

            return true;
        } else
        {
            return false;
        }
    }

    static final long serialVersionUID = 0L;
    private HashMap hashIndex;
    private HashMap hashIndiceClave;
}