package com.visiongc.app.strategos;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSessionFactory;
import com.visiongc.commons.VgcConfiguration;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class StrategosConfiguration extends VgcConfiguration
{
  public Map getAbbreviatedClassNames()
  {
    Map abbreviatedClassNames = new HashMap();
    Map propertyClassNames = new HashMap();
    
    propertyClassNames.put("hibernateStrategos", "com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSessionFactory");
    abbreviatedClassNames.put("com.visiongc.app.strategos.persistence.session.factory", propertyClassNames);
    
    return abbreviatedClassNames;
  }
  
  public Properties getDefaultProperties()
  {
    Properties properties = new Properties();
    
    properties.put("com.visiongc.app.strategos.persistence.session.factory", "hibernateStrategos");
    
    return properties;
  }
  
  public StrategosConfiguration() throws ChainedRuntimeException
  {
    super(StrategosConfiguration.class);
  }
  
  public static StrategosConfiguration getInstance()
  {
    StrategosConfiguration conf = (StrategosConfiguration)VgcConfiguration.getConfiguration(StrategosConfiguration.class);
    
    if (conf == null)
    {
      try
      {
        new StrategosConfiguration();
        conf = (StrategosConfiguration)VgcConfiguration.getConfiguration(StrategosConfiguration.class);
      }
      catch (Exception e)
      {
        throw new ChainedRuntimeException("La aplicación no está¡ configurada correctamente.", e);
      }
    }
    
    return conf;
  }
  
  public StrategosPersistenceSessionFactory getStrategosPersistenceSessionFactory()
  {
    return (StrategosPersistenceSessionFactory)instantiate("com.visiongc.app.strategos.persistence.session.factory", StrategosPersistenceSessionFactory.class);
  }
}
