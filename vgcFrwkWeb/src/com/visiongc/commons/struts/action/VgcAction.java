package com.visiongc.commons.struts.action;

import com.visiongc.commons.VgcService;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.util.PermisologiaUsuario;
import com.visiongc.framework.web.NavegadorInfo;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;


public abstract class VgcAction
  extends Action
{
  public VgcAction() {}
  
  private final String getResourceKeyPorURI(String uri)
  {
    int punto = uri.indexOf("/", 1);
    uri = uri.substring(punto + 1);
    uri = uri.replaceAll("/", ".");
    uri = uri.toLowerCase();
    
    uri = uri.substring(0, uri.length() - "action".length() - 1);
    
    return uri;
  }
  
  public abstract void updateNavigationBar(NavigationBar paramNavigationBar, String paramString1, String paramString2);
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Enumeration<?> enumeracion = request.getSession().getAttributeNames();
    
    while (enumeracion.hasMoreElements())
    {
      String nombreAtributo = (String)enumeracion.nextElement();
      if (nombreAtributo.startsWith("com.visiongc.framework.web.app.request.attribute."))
      {
        String nombreAtributoRequest = nombreAtributo.substring("com.visiongc.framework.web.app.request.attribute.".length());
        request.setAttribute(nombreAtributoRequest, request.getSession().getAttribute(nombreAtributo));
        request.getSession().removeAttribute(nombreAtributo);
      }
    }
    
    NavigationBar navBar = (NavigationBar)request.getSession().getAttribute("navigationBar");
    if (navBar == null)
    {
      navBar = new NavigationBar();
      request.getSession().setAttribute("navigationBar", navBar);
    }
    
    String url = request.getRequestURI();
    if (request.getQueryString() != null) {
      url = url + "?" + request.getQueryString();
    }
    MessageResources mensajes = getResources(request);
    
    String resourceKey = getResourceKeyPorURI(request.getRequestURI());
    
    String mensaje = mensajes.getMessage(getLocale(request), "titulourl." + resourceKey);
    
    if (mensaje == null) {
      mensaje = mensajes.getMessage(getLocale(request), resourceKey);
    }
    ActionForward res = super.execute(mapping, form, request, response);
    
    Stack<String> pilaActionForwards = (Stack)request.getAttribute("pilaActionForwards");
    
    if (pilaActionForwards == null)
    {
      pilaActionForwards = new Stack();
      request.setAttribute("pilaActionForwards", pilaActionForwards);
    }
    
    pilaActionForwards.push(mapping.getPath());
    
    updateNavigationBar(navBar, url, mensaje);
    

    setEnviroment(request);
    
    return res;
  }
  
  private void setEnviroment(HttpServletRequest request)
  {
    Boolean defaultLoader = request.getParameter("defaultLoader") != null ? Boolean.valueOf(Boolean.parseBoolean(request.getParameter("defaultLoader"))) : null;
    if (defaultLoader != null) {
      request.getSession().setAttribute("defaultLoader", defaultLoader);
    }
  }
  



  public ActionForward getForwardBack(HttpServletRequest request, int nivel, boolean redirect)
  {
    NavigationBar navBar = (NavigationBar)request.getSession().getAttribute("navigationBar");
    
    String url = "";
    if (navBar != null)
      url = navBar.getUrl(nivel);
    if (!url.equals("")) {
      url = url.substring(url.indexOf("/", 1));
    }
    ActionForward act = new ActionForward(url);
    
    if (redirect)
    {


      Enumeration<?> enumeracion = request.getAttributeNames();
      
      while (enumeracion.hasMoreElements())
      {
        String nombreAtributo = (String)enumeracion.nextElement();
        if ((!nombreAtributo.equals("org.apache.struts.action.MESSAGE")) && (!nombreAtributo.equals("org.apache.struts.action.mapping.instance")) && (!nombreAtributo.equals("pilaActionForwards")) && (!nombreAtributo.equals("org.apache.struts.action.MODULE"))) {
          request.getSession().setAttribute("com.visiongc.framework.web.app.request.attribute." + nombreAtributo, request.getAttribute(nombreAtributo));
        }
      }
    }
    act.setName(null);
    act.setModule(null);
    act.setRedirect(redirect);
    
    return act;
  }
  


  public PermisologiaUsuario getPermisologiaUsuario(HttpServletRequest request)
  {
    return (PermisologiaUsuario)request.getSession().getAttribute("com.visiongc.framework.web.PERMISOLOGIA_USUARIO");
  }
  


  public Usuario getUsuarioConectado(HttpServletRequest request)
  {
    return (Usuario)request.getSession().getAttribute("usuario");
  }
  


  public void inicializarPoolLocksUsoEdicion(HttpServletRequest request, VgcService service)
  {
    Map<Object, Object> poolLocks = getPoolLocksUsoEdicion(request);
    
    for (Iterator<Object> iter = poolLocks.keySet().iterator(); iter.hasNext();)
    {
      Object lockId = iter.next();
      service.unlockObject(request.getSession().getId(), lockId);
    }
    
    poolLocks.clear();
  }
  

  private Map<Object, Object> getPoolLocksUsoEdicion(HttpServletRequest request)
  {
    Map<Object, Object> poolLocks = (Map)request.getSession().getAttribute("poolLocksUsoEdicion");
    
    if (poolLocks == null)
    {

      poolLocks = new HashMap();
      request.getSession().setAttribute("poolLocksUsoEdicion", poolLocks);
    }
    
    return poolLocks;
  }
  


  public boolean agregarLockPoolLocksUsoEdicion(HttpServletRequest request, VgcService service, Object objetoId)
  {
    Map<Object, Object> poolLocks = getPoolLocksUsoEdicion(request);
    
    boolean resultado = false;
    
    if (!poolLocks.containsKey(objetoId))
    {
      poolLocks.put(objetoId, objetoId);
      resultado = true;
    }
    
    return resultado;
  }
  

  public void destruirPoolLocksUsoEdicion(HttpServletRequest request, VgcService service)
  {
    Map<?, ?> poolLocks = (Map)request.getSession().getAttribute("poolLocksUsoEdicion");
    
    if (poolLocks != null)
    {
      for (Iterator<?> iter = poolLocks.values().iterator(); iter.hasNext();)
      {
        Object lockId = iter.next();
        
        service.unlockObject(request.getSession().getId(), lockId);
      }
      poolLocks.clear();
      request.getSession().removeAttribute("poolLocksUsoEdicion");
    }
  }
  





  public boolean actionEjectuado(HttpServletRequest request, String pathAction)
  {
    boolean llamado = false;
    
    Stack<?> pilaActionForwards = (Stack)request.getAttribute("pilaActionForwards");
    
    if (pilaActionForwards != null)
    {
      for (Iterator<?> iter = pilaActionForwards.iterator(); iter.hasNext();)
      {
        String path = (String)iter.next();
        
        if (path.equalsIgnoreCase(pathAction))
        {
          llamado = true;
          break;
        }
      }
    }
    
    return llamado;
  }
  
  public BarraAreaInfo getBarraAreas(HttpServletRequest request, String nombre)
  {
    BarraAreaInfo barraAreaInfo = (BarraAreaInfo)request.getSession().getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraAreas" + nombre);
    if (barraAreaInfo == null)
    {
      barraAreaInfo = new BarraAreaInfo();
      request.getSession().setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.BarraAreas" + nombre, barraAreaInfo);
    }
    return barraAreaInfo;
  }
  
  public NavegadorInfo getNavegadorInfo(HttpServletRequest request)
  {
    return (NavegadorInfo)request.getSession().getAttribute("com.visiongc.app.web.navegadorinfo");
  }
}
