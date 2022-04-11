package com.visiongc.framework.web;

import com.visiongc.commons.VgcConfiguration;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



public class FrameworkWebConfiguration
  extends VgcConfiguration
{
  public Map getAbbreviatedClassNames()
  {
    Map abbreviatedClassNames = new HashMap();
    
    return abbreviatedClassNames;
  }
  
  public Properties getDefaultProperties()
  {
    Properties properties = new Properties();
    
    return properties;
  }
  
  public FrameworkWebConfiguration() throws ChainedRuntimeException
  {
    super(FrameworkWebConfiguration.class);
  }
  







  public static FrameworkWebConfiguration getInstance()
  {
    FrameworkWebConfiguration conf = (FrameworkWebConfiguration)VgcConfiguration.getConfiguration(FrameworkWebConfiguration.class);
    
    if (conf == null)
    {
      try
      {
        new FrameworkWebConfiguration();
        
        conf = (FrameworkWebConfiguration)VgcConfiguration.getConfiguration(FrameworkWebConfiguration.class);
      }
      catch (Exception e)
      {
        throw new ChainedRuntimeException("La aplicación no está configurada correctamente.", e);
      }
    }
    
    return conf;
  }
}
