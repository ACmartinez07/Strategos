package com.visiongc.commons.struts.util;

import com.visiongc.commons.util.VgcResourceManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;

public class VgcPropertyMessageResources
  extends PropertyMessageResources
{
  private static final long serialVersionUID = 0L;
  protected static final Log log = LogFactory.getLog(VgcPropertyMessageResources.class);
  
  protected HashMap frameworkWebLocales = new HashMap();
  
  public VgcPropertyMessageResources(MessageResourcesFactory factory, String config)
  {
    super(factory, config);
    VgcResourceManager.getMessageResources(config.substring("MessageResources".length()));
    log.trace("Initializing, config='" + config + "'");
  }
  
  public VgcPropertyMessageResources(MessageResourcesFactory factory, String config, boolean returnNull)
  {
    super(factory, config, returnNull);
    VgcResourceManager.getMessageResources(config.substring("MessageResources".length()));
    log.trace("Initializing, config='" + config + "', returnNull=" + returnNull);
  }
  
  public String getMessage(Locale locale, String key)
  {
    if (log.isDebugEnabled()) {
      log.debug("getMessage(" + locale + "," + key + ")");
    }
    

    String localeKey = localeKey(locale);
    String originalKey = messageKey(localeKey, key);
    String messageKey = null;
    String message = null;
    int underscore = 0;
    boolean addIt = false;
    

    for (;;)
    {
      loadFrameworkResourcesWeb(localeKey);
      loadLocale(localeKey);
      

      messageKey = messageKey(localeKey, key);
      
      synchronized (messages) {
        message = (String)messages.get(messageKey);
        
        if (message != null) {
          if (addIt) {
            messages.put(originalKey, message);
          }
          
          return message;
        }
      }
      

      addIt = true;
      underscore = localeKey.lastIndexOf("_");
      
      if (underscore < 0) {
        break;
      }
      
      localeKey = localeKey.substring(0, underscore);
    }
    

    if (!defaultLocale.equals(locale)) {
      localeKey = localeKey(defaultLocale);
      messageKey = messageKey(localeKey, key);
      loadFrameworkResourcesWeb(localeKey);
      loadLocale(localeKey);
      
      synchronized (messages) {
        message = (String)messages.get(messageKey);
        
        if (message != null) {
          messages.put(originalKey, message);
          
          return message;
        }
      }
    }
    

    localeKey = "";
    messageKey = messageKey(localeKey, key);
    loadFrameworkResourcesWeb(localeKey);
    loadLocale(localeKey);
    
    synchronized (messages) {
      message = (String)messages.get(messageKey);
      
      if (message != null) {
        messages.put(originalKey, message);
        
        return message;
      }
    }
    

    if (returnNull) {
      return null;
    }
    return "???" + messageKey(locale, key) + "???";
  }
  


  protected synchronized void loadFrameworkResourcesWeb(String localeKey)
  {
    if (log.isTraceEnabled()) {
      log.trace("loadLocale(" + localeKey + ")");
    }
    
    if (frameworkWebLocales.get(localeKey) != null) {
      return;
    }
    frameworkWebLocales.put(localeKey, localeKey);
    

    String name = "MessageResourcesFrameworkWeb";
    if (localeKey.length() > 0) {
      name = name + "_" + localeKey;
    }
    name = name + ".properties";
    
    InputStream is = null;
    Properties props = new Properties();
    

    if (log.isTraceEnabled()) {
      log.trace("  Loading resource '" + name + "'");
    }
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (classLoader == null) {
      classLoader = getClass().getClassLoader();
    }
    is = classLoader.getResourceAsStream(name);
    if (is != null)
    {
      try
      {
        props.load(is);
      }
      catch(IOException e)
      {
          log.error("loadLocale()", e);
      }
      try
      {
          is.close();
      }
      catch(IOException e)
      {
          log.error("loadLocale()", e);
      }
      
      finally
      {
        try
        {
          is.close();
        }
        catch (IOException e)
        {
          log.error("loadLocale()", e);
        }
      }
      
      if (log.isTraceEnabled()) {
        log.trace("  Loading resource completed");
      }
      
    }
    else if (log.isWarnEnabled()) {
      log.warn("  Resource " + name + " Not Found.");
    }
    

    if (props.size() < 1) {
      return;
    }
    synchronized (messages)
    {
      Iterator names = props.keySet().iterator();
      while (names.hasNext())
      {
        String key = (String)names.next();
        
        if (log.isTraceEnabled()) {
          log.trace("  Saving message key '" + messageKey(localeKey, key));
        }
        messages.put(messageKey(localeKey, key), props.getProperty(key));
      }
    }
  }
}
