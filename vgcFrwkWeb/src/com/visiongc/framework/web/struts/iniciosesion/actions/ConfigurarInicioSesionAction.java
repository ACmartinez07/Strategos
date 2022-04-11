package com.visiongc.framework.web.struts.iniciosesion.actions;

import com.visiongc.authentication.ExternalAuthenticator;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.Password;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.web.struts.iniciosesion.forms.ConfigurarInicioSesionForm;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;
import java.io.ByteArrayInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ConfigurarInicioSesionAction extends VgcAction
{
  public ConfigurarInicioSesionAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.clear();
    navBar.agregarUrl(url, nombre);
  }
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    getBarraAreas(request, "administracion").setBotonSeleccionado("inicioSesion");
    

    String forward = mapping.getParameter();
    

    ActionMessages messages = getMessages(request);
    
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    Configuracion configuracion = new Configuracion();
    ConfigurarInicioSesionForm configurarInicioSesionForm = (ConfigurarInicioSesionForm)form;
    
    String accion = request.getParameter("accion");
    
    if ((accion != null) && (accion.equals("testConexion")))
    {
      String usuario = request.getParameter("user");
      String pwd = request.getParameter("pwd");
      String cn = request.getParameter("cn");
      String dn = request.getParameter("dn");
      String servidor = request.getParameter("servidor");
      String puerto = request.getParameter("puerto");
      
      Integer resultAuthenticationActiveDirectory = Integer.valueOf(ExternalAuthenticator.getInstance().testWindowsActiveDirectory(usuario, pwd, cn, dn, servidor, puerto, null));
      
      request.setAttribute("ajaxResponse", resultAuthenticationActiveDirectory.toString());
      return mapping.findForward("ajaxResponse");
    }
    if ((accion != null) && (accion.equals("Guardar")))
    {
      StringBuilder xml = new StringBuilder();
      xml.append("<?xml version='1.0' encoding='UTF-8' standalone='no'?><Configuraciones><Configuracion>");
      xml.append("<minimoCaracteres>" + configurarInicioSesionForm.getMinimoCaracteres().toString() + "</minimoCaracteres>");
      xml.append("<maximoReintentos>" + configurarInicioSesionForm.getMaximoReintentos().toString() + "</maximoReintentos>");
      xml.append("<expiraContrasena>" + configurarInicioSesionForm.getExpiraContrasena().toString() + "</expiraContrasena>");
      xml.append("<periodoExpiracion>" + configurarInicioSesionForm.getPeriodoExpiracion().toString() + "</periodoExpiracion>");
      xml.append("<duracionExpiracion>" + configurarInicioSesionForm.getDuracionExpiracion().toString() + "</duracionExpiracion>");
      xml.append("<advertenciaCaducidad>" + configurarInicioSesionForm.getAdvertenciaCaducidad().toString() + "</advertenciaCaducidad>");
      xml.append("<reutilizacionContrasena>" + configurarInicioSesionForm.getReutilizacionContrasena().toString() + "</reutilizacionContrasena>");
      xml.append("<tipoContrasena>" + configurarInicioSesionForm.getTipoContrasena().toString() + "</tipoContrasena>");
      xml.append("<deshabilitarUsuarios>" + configurarInicioSesionForm.getDeshabilitarUsuarios().toString() + "</deshabilitarUsuarios>");
      xml.append("<restriccionUsoDiaLunes>" + (configurarInicioSesionForm.getRestriccionUsoDiaLunes() != null ? configurarInicioSesionForm.getRestriccionUsoDiaLunes().toString() : "false") + "</restriccionUsoDiaLunes>");
      xml.append("<restriccionUsoDiaMartes>" + (configurarInicioSesionForm.getRestriccionUsoDiaMartes() != null ? configurarInicioSesionForm.getRestriccionUsoDiaMartes().toString() : "false") + "</restriccionUsoDiaMartes>");
      xml.append("<restriccionUsoDiaMiercoles>" + (configurarInicioSesionForm.getRestriccionUsoDiaMiercoles() != null ? configurarInicioSesionForm.getRestriccionUsoDiaMiercoles().toString() : "false") + "</restriccionUsoDiaMiercoles>");
      xml.append("<restriccionUsoDiaJueves>" + (configurarInicioSesionForm.getRestriccionUsoDiaJueves() != null ? configurarInicioSesionForm.getRestriccionUsoDiaJueves().toString() : "false") + "</restriccionUsoDiaJueves>");
      xml.append("<restriccionUsoDiaViernes>" + (configurarInicioSesionForm.getRestriccionUsoDiaViernes() != null ? configurarInicioSesionForm.getRestriccionUsoDiaViernes().toString() : "false") + "</restriccionUsoDiaViernes>");
      xml.append("<restriccionUsoDiaSabado>" + (configurarInicioSesionForm.getRestriccionUsoDiaSabado() != null ? configurarInicioSesionForm.getRestriccionUsoDiaSabado().toString() : "false") + "</restriccionUsoDiaSabado>");
      xml.append("<restriccionUsoDiaDomingo>" + (configurarInicioSesionForm.getRestriccionUsoDiaDomingo() != null ? configurarInicioSesionForm.getRestriccionUsoDiaDomingo().toString() : "false") + "</restriccionUsoDiaDomingo>");
      xml.append("<restriccionUsoHoraDesde>" + configurarInicioSesionForm.getRestriccionUsoHoraDesde().toString() + "</restriccionUsoHoraDesde>");
      xml.append("<restriccionUsoHoraHasta>" + configurarInicioSesionForm.getRestriccionUsoHoraHasta().toString() + "</restriccionUsoHoraHasta>");
      xml.append("<habilitarAuditoria>" + configurarInicioSesionForm.getHabilitarAuditoria().toString() + "</habilitarAuditoria>");
      

      xml.append("<tipoConexion>" + configurarInicioSesionForm.getTipoConexion().toString() + "</tipoConexion>");
      if (configurarInicioSesionForm.getTipoConexion().intValue() == 1)
      {
        xml.append("<conexionPropiaValidacion>" + configurarInicioSesionForm.getConexionPropiaValidacion() + "</conexionPropiaValidacion>");
        xml.append("<conexionPropiaParametros>" + configurarInicioSesionForm.getConexionPropiaParametros() + "</conexionPropiaParametros>");
      }
      else if (configurarInicioSesionForm.getTipoConexion().intValue() == 2)
      {
        xml.append("<conexionLDAPUser>" + configurarInicioSesionForm.getConexionLDAPUser() + "</conexionLDAPUser>");
        xml.append("<conexionLDAPServidor>" + configurarInicioSesionForm.getConexionLDAPServidor() + "</conexionLDAPServidor>");
        xml.append("<conexionLDAPPuerto>" + configurarInicioSesionForm.getConexionLDAPPuerto() + "</conexionLDAPPuerto>");
        xml.append("<conexionLDAPDn>" + configurarInicioSesionForm.getConexionLDAPDn() + "</conexionLDAPDn>");
      }
      

      String string = "";
      string = configurarInicioSesionForm.getConexionMAILHost();
      xml.append("<conexionMAILHost>" + (string != null ? string : "") + "</conexionMAILHost>");
      string = configurarInicioSesionForm.getConexionMAILPort();
      xml.append("<conexionMAILPort>" + (string != null ? string : "") + "</conexionMAILPort>");
      string = configurarInicioSesionForm.getConexionMAILUser();
      xml.append("<conexionMAILUser>" + (string != null ? string : "") + "</conexionMAILUser>");
      String password = configurarInicioSesionForm.getConexionMAILPassword();
      if (!password.equals(""))
      {
        password = Password.encriptaCadena(password);
        xml.append("<conexionMAILPassword>" + TextEncoder.uRLEncode(password) + "</conexionMAILPassword>");
      }
      else {
        xml.append("<conexionMAILPassword></conexionMAILPassword>");
      }
      xml.append("</Configuracion></Configuraciones>");
      

      configuracion = new Configuracion();
      configuracion.setParametro("Strategos.Configuracion.Login");
      configuracion.setValor(xml.toString());
      frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
      frameworkService.setAuditoria(configurarInicioSesionForm.getHabilitarAuditoria());
      

      configuracion = new Configuracion();
      configuracion.setParametro("mail.smtp.host");
      if ((configurarInicioSesionForm.getConexionMAILHost() != null) && (!configurarInicioSesionForm.getConexionMAILHost().equals("")))
      {
        configuracion.setValor(configurarInicioSesionForm.getConexionMAILHost());
        frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
      }
      else {
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
      }
      configuracion = new Configuracion();
      configuracion.setParametro("mail.smtp.port");
      if ((configurarInicioSesionForm.getConexionMAILPort() != null) && (!configurarInicioSesionForm.getConexionMAILPort().equals("")))
      {
        configuracion.setValor(configurarInicioSesionForm.getConexionMAILPort());
        frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
      }
      else {
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
      }
      configuracion = new Configuracion();
      configuracion.setParametro("mail.smtp.user");
      if ((configurarInicioSesionForm.getConexionMAILUser() != null) && (!configurarInicioSesionForm.getConexionMAILUser().equals("")))
      {
        configuracion.setValor(configurarInicioSesionForm.getConexionMAILUser());
        frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
      }
      else {
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
      }
      configuracion = new Configuracion();
      configuracion.setParametro("mail.smtp.password");
      if ((configurarInicioSesionForm.getConexionMAILPassword() != null) && (!configurarInicioSesionForm.getConexionMAILPassword().equals("")))
      {
        configuracion.setValor(TextEncoder.uRLEncode(Password.encriptaCadena(configurarInicioSesionForm.getConexionMAILPassword())));
        frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
      }
      else {
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
      }
      
      configuracion = new Configuracion();
      configuracion.setParametro("autenticacion.tipo");
      if (configurarInicioSesionForm.getTipoConexion().intValue() == 0)
      {
        configuracion.setValor("propia");
        frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.queryValidacion");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.queryParametros");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.cn");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.servidor");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.puerto");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.autenticacion");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.dn");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.version");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
      }
      else if (configurarInicioSesionForm.getTipoConexion().intValue() == 1)
      {
        configuracion.setValor("particular");
        frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.queryValidacion");
        if ((configurarInicioSesionForm.getConexionPropiaValidacion() != null) && (!configurarInicioSesionForm.getConexionPropiaValidacion().equals("")))
        {
          configuracion.setValor(configurarInicioSesionForm.getConexionPropiaValidacion());
          frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
        }
        else {
          frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        }
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.queryParametros");
        if ((configurarInicioSesionForm.getConexionPropiaParametros() != null) && (!configurarInicioSesionForm.getConexionPropiaParametros().equals("")))
        {
          configuracion.setValor(configurarInicioSesionForm.getConexionPropiaParametros());
          frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
        }
        else {
          frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        }
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.cn");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.servidor");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.puerto");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.autenticacion");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.dn");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.version");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
      }
      else if (configurarInicioSesionForm.getTipoConexion().intValue() == 2)
      {
        configuracion.setValor("ldap");
        frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.cn");
        if ((configurarInicioSesionForm.getConexionLDAPUser() != null) && (!configurarInicioSesionForm.getConexionLDAPUser().equals("")))
        {
          configuracion.setValor(configurarInicioSesionForm.getConexionLDAPUser());
          frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
        }
        else {
          frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        }
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.servidor");
        if ((configurarInicioSesionForm.getConexionLDAPServidor() != null) && (!configurarInicioSesionForm.getConexionLDAPServidor().equals("")))
        {
          configuracion.setValor(configurarInicioSesionForm.getConexionLDAPServidor());
          frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
        }
        else {
          frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        }
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.puerto");
        if ((configurarInicioSesionForm.getConexionLDAPPuerto() != null) && (!configurarInicioSesionForm.getConexionLDAPPuerto().equals("")))
        {
          configuracion.setValor(configurarInicioSesionForm.getConexionLDAPPuerto());
          frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
        }
        else {
          frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        }
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.autenticacion");
        configuracion.setValor("simple");
        frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.dn");
        if ((configurarInicioSesionForm.getConexionLDAPDn() != null) && (!configurarInicioSesionForm.getConexionLDAPDn().equals("")))
        {
          configuracion.setValor(configurarInicioSesionForm.getConexionLDAPDn());
          frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request));
        }
        else {
          frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        }
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.queryValidacion");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.queryParametros");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
        
        configuracion = new Configuracion();
        configuracion.setParametro("autenticacion.ldap.version");
        frameworkService.deleteConfiguracion(configuracion, getUsuarioConectado(request));
      }
      
      request.getSession().setAttribute("com.visiongc.app.web.configiniciosesion", configurarInicioSesionForm);
      
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.iniciosesion.update.success"));
    }
    
    configuracion = frameworkService.getConfiguracion("Strategos.Configuracion.Login");
    if (configuracion != null)
    {

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
      doc.getDocumentElement().normalize();
      NodeList nList = doc.getElementsByTagName("Configuracion");
      Element eElement = (Element)nList.item(0);
      

      configurarInicioSesionForm.setMinimoCaracteres(Integer.valueOf(Integer.parseInt(VgcAbstractService.getTagValue("minimoCaracteres", eElement))));
      configurarInicioSesionForm.setMaximoReintentos(Integer.valueOf(Integer.parseInt(VgcAbstractService.getTagValue("maximoReintentos", eElement))));
      configurarInicioSesionForm.setExpiraContrasena(Integer.valueOf(Integer.parseInt(VgcAbstractService.getTagValue("expiraContrasena", eElement))));
      configurarInicioSesionForm.setPeriodoExpiracion(Integer.valueOf(Integer.parseInt(VgcAbstractService.getTagValue("periodoExpiracion", eElement))));
      configurarInicioSesionForm.setDuracionExpiracion(Integer.valueOf(Integer.parseInt(VgcAbstractService.getTagValue("duracionExpiracion", eElement))));
      configurarInicioSesionForm.setAdvertenciaCaducidad(Integer.valueOf(Integer.parseInt(VgcAbstractService.getTagValue("advertenciaCaducidad", eElement))));
      configurarInicioSesionForm.setReutilizacionContrasena(Integer.valueOf(Integer.parseInt(VgcAbstractService.getTagValue("reutilizacionContrasena", eElement))));
      configurarInicioSesionForm.setTipoContrasena(Integer.valueOf(Integer.parseInt(VgcAbstractService.getTagValue("tipoContrasena", eElement))));
      configurarInicioSesionForm.setDeshabilitarUsuarios(Integer.valueOf(Integer.parseInt(VgcAbstractService.getTagValue("deshabilitarUsuarios", eElement))));
      configurarInicioSesionForm.setRestriccionUsoDiaLunes(Boolean.valueOf(Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaLunes", eElement))));
      configurarInicioSesionForm.setRestriccionUsoDiaMartes(Boolean.valueOf(Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaMartes", eElement))));
      configurarInicioSesionForm.setRestriccionUsoDiaMiercoles(Boolean.valueOf(Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaMiercoles", eElement))));
      configurarInicioSesionForm.setRestriccionUsoDiaJueves(Boolean.valueOf(Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaJueves", eElement))));
      configurarInicioSesionForm.setRestriccionUsoDiaViernes(Boolean.valueOf(Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaViernes", eElement))));
      configurarInicioSesionForm.setRestriccionUsoDiaSabado(Boolean.valueOf(Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaSabado", eElement))));
      configurarInicioSesionForm.setRestriccionUsoDiaDomingo(Boolean.valueOf(Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaDomingo", eElement))));
      configurarInicioSesionForm.setRestriccionUsoHoraDesde(VgcAbstractService.getTagValue("restriccionUsoHoraDesde", eElement));
      configurarInicioSesionForm.setRestriccionUsoHoraHasta(VgcAbstractService.getTagValue("restriccionUsoHoraHasta", eElement));
      configurarInicioSesionForm.setHabilitarAuditoria(Integer.valueOf(Integer.parseInt(VgcAbstractService.getTagValue("habilitarAuditoria", eElement))));
      

      configurarInicioSesionForm.setTipoConexion(Integer.valueOf(Integer.parseInt(VgcAbstractService.getTagValue("tipoConexion", eElement) != "" ? VgcAbstractService.getTagValue("tipoConexion", eElement) : "0")));
      configurarInicioSesionForm.setConexionPropiaValidacion(VgcAbstractService.getTagValue("conexionPropiaValidacion", eElement));
      configurarInicioSesionForm.setConexionPropiaParametros(VgcAbstractService.getTagValue("conexionPropiaParametros", eElement));
      configurarInicioSesionForm.setConexionLDAPUser(VgcAbstractService.getTagValue("conexionLDAPUser", eElement));
      configurarInicioSesionForm.setConexionLDAPServidor(VgcAbstractService.getTagValue("conexionLDAPServidor", eElement));
      configurarInicioSesionForm.setConexionLDAPPuerto(VgcAbstractService.getTagValue("conexionLDAPPuerto", eElement));
      configurarInicioSesionForm.setConexionLDAPDn(VgcAbstractService.getTagValue("conexionLDAPDn", eElement));
      

      configurarInicioSesionForm.setConexionMAILHost(VgcAbstractService.getTagValue("conexionMAILHost", eElement));
      configurarInicioSesionForm.setConexionMAILPort(VgcAbstractService.getTagValue("conexionMAILPort", eElement));
      configurarInicioSesionForm.setConexionMAILUser(VgcAbstractService.getTagValue("conexionMAILUser", eElement));
      String password = TextEncoder.uRLDecode(VgcAbstractService.getTagValue("conexionMAILPassword", eElement));
      if (!password.equals(""))
        password = Password.desencripta(password);
      configurarInicioSesionForm.setConexionMAILPassword(password);
    }
    else {
      configurarInicioSesionForm.clear();
    }
    frameworkService.close();
    
    saveMessages(request, messages);
    
    return mapping.findForward(forward);
  }
}
