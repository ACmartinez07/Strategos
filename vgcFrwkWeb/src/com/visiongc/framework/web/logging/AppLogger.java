package com.visiongc.framework.web.logging;

import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;














public class AppLogger
{
  private static Logger instance = null;
  private String file;
  
  public AppLogger() {}
  
  public Logger getInstance(String file) { if (instance == null)
    {
      try
      {
        this.file = (file + "/WEB-INF/classes/log4j.properties");
        instance = Logger.getLogger(AppLogger.class);
        

        Properties properties = new Properties();
        properties.load(new FileInputStream(this.file));
        PropertyConfigurator.configure(properties);
      }
      catch (Exception e)
      {
        throw new ChainedRuntimeException("El archivo logger no se puedo configurar.", e);
      }
    }
    
    return instance;
  }
  
  public Properties getLogger() throws IOException
  {
    Properties props = new Properties();
    InputStream is = new FileInputStream("log4j.properties");
    try
    {
      props.load(is);
    }
    finally
    {
      try
      {
        is.close();
      }
      catch (Exception localException) {}
    }
    





    return props;
  }
  
  public String getFile()
  {
    return file;
  }
}
