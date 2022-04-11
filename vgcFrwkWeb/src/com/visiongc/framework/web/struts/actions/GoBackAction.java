package com.visiongc.framework.web.struts.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public final class GoBackAction
  extends VgcAction
{
  public GoBackAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    int pasos = 1;
    String str = request.getParameter("pasos");
    
    if ((str != null) && (!str.equals("")))
    {
      try
      {
        pasos = Integer.parseInt(str);
      }
      catch (Exception localException) {}
    }
    


    return getForwardBack(request, pasos, true);
  }
}
