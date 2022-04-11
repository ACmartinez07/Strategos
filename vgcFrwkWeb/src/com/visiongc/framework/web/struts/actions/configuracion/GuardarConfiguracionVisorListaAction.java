package com.visiongc.framework.web.struts.actions.configuracion;

import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.xmldata.XmlAtributo;
import com.visiongc.commons.util.xmldata.XmlNodo;
import com.visiongc.commons.util.xmldata.XmlUtil;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.forms.configuracion.EditarConfiguracionVisorListaForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;







public class GuardarConfiguracionVisorListaAction
  extends VgcAction
{
  private static final String ACTION_KEY = "GuardarConfiguracionVisorListaAction";
  
  public GuardarConfiguracionVisorListaAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    String forward = mapping.getParameter();
    

    EditarConfiguracionVisorListaForm editarConfiguracionVisorListaForm = (EditarConfiguracionVisorListaForm)form;
    

    ActionMessages messages = getMessages(request);
    
    String nombreConfiguracionBase = request.getParameter("nombreConfiguracionBase");
    String nombreVisorLista = request.getParameter("nombreVisorLista");
    Boolean esPropio = Boolean.valueOf(request.getParameter("esPropio") != null ? Boolean.parseBoolean(request.getParameter("esPropio")) : false);
    String data = request.getParameter("data");
    
    String ts = null;
    if (!esPropio.booleanValue())
    {
      boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
      ts = request.getParameter("ts");
      String ultimoTs = (String)request.getSession().getAttribute("GuardarConfiguracionVisorListaAction.ultimoTs");
      

      if ((ts == null) || (ts.equals(""))) {
        cancelar = true;
      } else if (ultimoTs != null)
      {
        if (ultimoTs.equals(ts)) {
          cancelar = true;
        }
      }
      if (cancelar) {
        return getForwardBack(request, 1, true);
      }
    }
    XmlNodo configuracionVisorLista = null;
    
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    
    ConfiguracionUsuario confUsuario = new ConfiguracionUsuario();
    ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
    if (esPropio.booleanValue())
    {
      pk.setConfiguracionBase(nombreConfiguracionBase);
      pk.setObjeto(nombreVisorLista);
      confUsuario.setData(data);
    }
    else
    {
      configuracionVisorLista = construirConfiguracion(request, editarConfiguracionVisorListaForm);
      pk.setConfiguracionBase(editarConfiguracionVisorListaForm.getNombreConfiguracionBase());
      pk.setObjeto(editarConfiguracionVisorListaForm.getNombreVisorLista());
      confUsuario.setData(XmlUtil.buildXml(configuracionVisorLista));
    }
    
    pk.setUsuarioId(getUsuarioConectado(request).getUsuarioId());
    
    confUsuario.setPk(pk);
    
    int resultado = frameworkService.saveConfiguracionUsuario(confUsuario, getUsuarioConectado(request));
    frameworkService.close();
    
    if (resultado == 10000)
    {

      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarconfiguracion.ok"));
      
      if (!esPropio.booleanValue())
        request.getSession().setAttribute(editarConfiguracionVisorListaForm.getNombreConfiguracionBase() + "." + editarConfiguracionVisorListaForm.getNombreVisorLista(), configuracionVisorLista);
      forward = "exito";
    }
    else if (resultado == 10003) {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarconfiguracion.duplicado"));
    }
    
    saveMessages(request, messages);
    
    if (!esPropio.booleanValue())
    {

      request.getSession().setAttribute("GuardarConfiguracionVisorListaAction.ultimoTs", ts);
      

      if (forward.equals("exito")) {
        return getForwardBack(request, 1, false);
      }
      return mapping.findForward(forward);
    }
    

    Byte status = StatusUtil.getStatusNoSuccess();
    String res = status.toString();
    request.setAttribute("ajaxResponse", res);
    
    return mapping.findForward("ajaxResponse");
  }
  

  private XmlNodo construirConfiguracion(HttpServletRequest request, EditarConfiguracionVisorListaForm editarConfiguracionVisorListaForm)
  {
    int index = 1;
    boolean seguir = true;
    String orden = null;
    String nombre = null;
    String titulo = null;
    String visible = null;
    String ancho = null;
    
    XmlNodo configuracionVisorLista = new XmlNodo("configuracion." + editarConfiguracionVisorListaForm.getNombreVisorLista());
    
    XmlNodo columnas = new XmlNodo();
    columnas.setId("columnas");
    




    while (seguir)
    {
      orden = Integer.toString(index);
      if (index < 10)
        orden = "0" + orden;
      nombre = request.getParameter("nombre" + orden);
      if (nombre == null) {
        seguir = false;
      }
      else {
        titulo = request.getParameter("titulo" + orden);
        visible = request.getParameter("visible" + orden);
        if (visible != null) {
          visible = "true";
        } else
          visible = "false";
        ancho = request.getParameter("ancho" + orden);
        
        XmlNodo columna = new XmlNodo();
        columna.setId(nombre);
        XmlAtributo atributo = new XmlAtributo();
        atributo.setNombre("orden");
        atributo.setValor(orden.trim());
        columna.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("titulo");
        atributo.setValor(titulo);
        columna.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("ancho");
        atributo.setValor(ancho.trim());
        columna.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("visible");
        atributo.setValor(visible);
        columna.addAtributo(atributo);
        columnas.addElemLista(columna, nombre);
        
        index++;
      }
    }
    
    configuracionVisorLista.addElemLista(columnas, "columnas");
    
    return configuracionVisorLista;
  }
}
