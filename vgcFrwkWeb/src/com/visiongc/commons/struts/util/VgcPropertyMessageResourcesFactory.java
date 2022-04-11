package com.visiongc.commons.struts.util;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;






public class VgcPropertyMessageResourcesFactory
  extends MessageResourcesFactory
{
  private static final long serialVersionUID = 0L;
  
  public VgcPropertyMessageResourcesFactory() {}
  
  public MessageResources createResources(String config)
  {
    return new VgcPropertyMessageResources(this, config, returnNull);
  }
}
