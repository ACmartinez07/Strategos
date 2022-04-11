package com.visiongc.framework.web.struts.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;



public final class LocaleAction
  extends VgcAction
{
  private static final String LANGUAGE = "language";
  private static final String COUNTRY = "country";
  
  public LocaleAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.clear();
  }
  
  private boolean isBlank(String string)
  {
    return (string == null) || (string.trim().length() == 0);
  }
  

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String language = request.getParameter("language");
    String country = request.getParameter("country");
    


    ActionMessages messages = new ActionMessages();
    
    if ((!isBlank(language)) && (!isBlank(country))) {
      Locale locale = new Locale(language, country);
      HttpSession session = request.getSession();
      session.setAttribute("org.apache.struts.action.LOCALE", locale);
    }
    else if (!isBlank(language)) {
      Locale locale = new Locale(language, "");
      HttpSession session = request.getSession();
      session.setAttribute("org.apache.struts.action.LOCALE", locale);
    } else {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", 
        new ActionMessage("action.changelocale.fail"));
      
      saveErrors(request, messages);
    }
    
    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", 
      new ActionMessage("action.changelocale.success"));
    
    saveMessages(request, messages);
    
    String target = mapping.getParameter();
    
    if (isBlank(target))
    {
      return null;
    }
    return mapping.findForward(target);
  }
}
