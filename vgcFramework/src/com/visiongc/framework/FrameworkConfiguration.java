package com.visiongc.framework;

import com.visiongc.commons.VgcConfiguration;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.persistence.FrameworkPersistenceSessionFactory;
import java.util.*;

public class FrameworkConfiguration extends VgcConfiguration
{

    public Map getAbbreviatedClassNames()
    {
        Map abbreviatedClassNames = new HashMap();
        Map propertyClassNames = new HashMap();
        propertyClassNames = new HashMap();
        propertyClassNames.put("hibernateFramework", "com.visiongc.framework.persistence.hibernate.FrameworkHibernateSessionFactory");
        abbreviatedClassNames.put("com.visiongc.framework.persistence.session.factory", propertyClassNames);
        return abbreviatedClassNames;
    }

    public Properties getDefaultProperties()
    {
        Properties properties = new Properties();
        properties.put("com.visiongc.framework.persistence.session.factory", "hibernateFramework");
        return properties;
    }

    public FrameworkConfiguration()
        throws ChainedRuntimeException
    {
        super(FrameworkConfiguration.class);
    }

    public static FrameworkConfiguration getInstance()
    {
        FrameworkConfiguration conf = (FrameworkConfiguration)VgcConfiguration.getConfiguration(FrameworkConfiguration.class);
        if(conf == null)
            try
            {
                new FrameworkConfiguration();
                conf = (FrameworkConfiguration)VgcConfiguration.getConfiguration(FrameworkConfiguration.class);
            }
            catch(Exception e)
            {
                throw new ChainedRuntimeException("La aplicación no está configurada correctamente.", e);
            }
        return conf;
    }

    public FrameworkPersistenceSessionFactory getFrameworkPersistenceSessionFactory()
    {
        return (FrameworkPersistenceSessionFactory)instantiate("com.visiongc.framework.persistence.session.factory", FrameworkPersistenceSessionFactory.class);
    }
}