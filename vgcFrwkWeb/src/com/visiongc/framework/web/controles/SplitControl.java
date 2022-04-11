package com.visiongc.framework.web.controles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;







public class SplitControl
{
  public static final String KEY = "com.visiongc.framework.web.controles.Split";
  
  public SplitControl() {}
  
  public static void setConfiguracion(HttpServletRequest request, String splitId)
  {
    String anchoPanelIzquierdo = request.getParameter("com.visiongc.framework.web.controles.Split." + splitId + ".anchoPanelIzquierdo");
    if (anchoPanelIzquierdo != null) {
      request.getSession().setAttribute("com.visiongc.framework.web.controles.Split." + splitId + ".anchoPanelIzquierdo", anchoPanelIzquierdo);
    }
  }
}
