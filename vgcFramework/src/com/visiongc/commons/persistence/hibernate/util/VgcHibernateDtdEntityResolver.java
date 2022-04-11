package com.visiongc.commons.persistence.hibernate.util;

import java.io.InputStream;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class VgcHibernateDtdEntityResolver
    implements EntityResolver
{

    public VgcHibernateDtdEntityResolver()
    {
        PREFIJO1 = "file:///";
        PREFIJO2 = "file://";
        PREFIJO3 = "file:";
    }

    public InputSource resolveEntity(String publicId, String systemId)
    {
        if(publicId != null && publicId.length() > 0)
        {
            InputStream in = getClass().getResourceAsStream(publicId);
            return new InputSource(in);
        }
        if(systemId != null && systemId.length() > 0)
        {
            if(systemId.startsWith(PREFIJO1))
                systemId = systemId.substring(PREFIJO1.length());
            if(systemId.startsWith(PREFIJO2))
                systemId = systemId.substring(PREFIJO2.length());
            if(systemId.startsWith(PREFIJO3))
                systemId = systemId.substring(PREFIJO3.length());
            for(int i = systemId.indexOf("/"); i >= 0; i = systemId.indexOf("/"))
                systemId = systemId.substring(i + 1);

            InputStream in = getClass().getResourceAsStream(systemId);
            if(in == null)
                in = getClass().getClassLoader().getResourceAsStream(systemId);
            return new InputSource(in);
        } else
        {
            return null;
        }
    }

    private String PREFIJO1;
    private String PREFIJO2;
    private String PREFIJO3;
}