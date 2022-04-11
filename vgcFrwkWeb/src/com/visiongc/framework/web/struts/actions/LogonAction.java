package com.visiongc.framework.web.struts.actions;

import com.visiongc.authentication.ExternalAuthenticator;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.Modulo;
import com.visiongc.framework.model.PersonaBasica;
import com.visiongc.framework.model.UserSession;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.util.PermisologiaUsuario;
import com.visiongc.framework.web.FrameworkWebConfiguration;
import com.visiongc.framework.web.SessionsWatcher;
import com.visiongc.framework.web.struts.iniciosesion.forms.ConfigurarInicioSesionForm;
import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;







public final class LogonAction
  extends VgcAction
{
  public LogonAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.clear();
  }
  



  static String USERNAME = "username";
  



  static String PASSWORD = "password";
  



  static String CHALLENGE = "challenge";
  



  static String ISCONNECTED = "isConnected";
  



  static String PWDENCRIPT = "pwdEncript";
  



  static String TIPOEXPLORADOR = "tipoExplorer";
  


















  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    String username = (String)PropertyUtils.getSimpleProperty(form, USERNAME);
    String password = (String)PropertyUtils.getSimpleProperty(form, PASSWORD);
    String challenge = (String)PropertyUtils.getSimpleProperty(form, CHALLENGE);
    String pwdEncript = (String)PropertyUtils.getSimpleProperty(form, PWDENCRIPT);
    String tipoExplorer = (String)PropertyUtils.getSimpleProperty(form, TIPOEXPLORADOR);
    String forward = "fallo";
    
    ActionMessages messages = getMessages(request);
    

    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
    

    HttpSession httpSession = request.getSession(false);
    ConfigurarInicioSesionForm configInicioSesion = (ConfigurarInicioSesionForm)request.getSession().getAttribute("com.visiongc.app.web.configiniciosesion");
    
    if (configInicioSesion == null)
    {
      ConfigurarInicioSesionForm configurarInicioSesionForm = new ConfigurarInicioSesionForm();
      Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Configuracion.Login");
      
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
      }
      else {
        configurarInicioSesionForm.clear();
      }
      request.getSession().setAttribute("com.visiongc.app.web.configiniciosesion", configurarInicioSesionForm);
      configInicioSesion = (ConfigurarInicioSesionForm)request.getSession().getAttribute("com.visiongc.app.web.configiniciosesion");
    }
    

    if (httpSession != null)
    {
      UserSession us = (UserSession)frameworkService.load(UserSession.class, httpSession.getId());
      
      if (us != null)
      {

        synchronized (httpSession)
        {
          httpSession.invalidate();
        }
        
        request.getSession(true);
      }
    }
    
    Modulo cliente = new Modulo().getModuloActivo("2277419F-4FF0-4CC8-A602-D2619A6AFC47");
    if ((cliente != null) && (cliente.getActivo().booleanValue())) {
      request.getSession().setAttribute("cliente", "BDV");
    }
    else {
      cliente = new Modulo().getModuloActivo("B069CF20-1E8F-4E5F-BDE1-9C28C776511A");
      if ((cliente != null) && (cliente.getActivo().booleanValue())) {
        request.getSession().setAttribute("cliente", "PGN");
      }
      else {
        cliente = new Modulo().getModuloActivo("0DC25625-AF06-4F7C-B981-258AEAF18C01");
        if ((cliente != null) && (cliente.getActivo().booleanValue())) {
          request.getSession().setAttribute("cliente", "MIF");
        }
      }
    }
    
    Usuario usuario = null;
    boolean isUsuarioSystema = false;
    Usuario user = frameworkService.getUsuarioPorLogin(username);
    if ((user != null) && (user.getIsSystem().booleanValue())) {
      isUsuarioSystema = true;
    }
    
    PersonaBasica persona = null;
    String tipoAuthentication = "propia";
    Integer resultAuthenticationActiveDirectory = null;
    if ((ExternalAuthenticator.getInstance().isActive()) && (!isUsuarioSystema))
    {







      tipoAuthentication = ExternalAuthenticator.getInstance().getAuthenticateTipo();
      if (tipoAuthentication.equalsIgnoreCase("ldap"))
      {
        resultAuthenticationActiveDirectory = Integer.valueOf(ExternalAuthenticator.getInstance().authenticate(username, pwdEncript));
        if (resultAuthenticationActiveDirectory.intValue() == 0)
        {




          usuario = frameworkService.getUsuarioPorLogin(username);
          
          if (usuario == null)
          {




            persona = frameworkService.getPersonaPorCedula(username);
            if (persona != null)
            {




              usuario = new Usuario();
              if ((persona.getUsuarioId() != null) && (persona.getUsuarioId().longValue() != 0L))
              {
                usuario.setUsuarioId(persona.getUsuarioId());
                Usuario usuarioTemp = (Usuario)frameworkService.load(Usuario.class, persona.getUsuarioId());
                usuario.setIsAdmin(usuarioTemp.getIsAdmin());
              }
              else
              {
                usuario.setUsuarioId(persona.getPersonaId());
                usuario.setIsAdmin(new Boolean(false));
              }
              usuario.setFullName(persona.getNombre());
              usuario.setUId(persona.getCedula());
            }
            
          }
        }
      }
      else if (tipoAuthentication.equalsIgnoreCase("particular")) {
        usuario = ExternalAuthenticator.getInstance().authenticateParticular(username, password, challenge);
      } else {
        usuario = null;

      }
      

    }
    else
    {

      usuario = frameworkService.getUsuarioPorLoginPwd(username, password, challenge, pwdEncript);
    }
    
    if (password.isEmpty()) {
      usuario = null;
    }
    boolean bloqueado = false;
    boolean restringido = false;
    
    if ((usuario != null) || (persona != null))
    {

      if (usuario.getSesiones().size() >= 1)
      {
        forward = "conectado";
        

        PropertyUtils.setSimpleProperty(form, ISCONNECTED, Boolean.valueOf(true));



      }
      else if (usuario.getBloqueado().booleanValue())
      {
        usuario = null;
        bloqueado = true;
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.user.blocked"));
        saveMessages(request, messages);
      }
      else if (usuario.getDeshabilitado().booleanValue())
      {
        usuario = null;
        bloqueado = true;
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.user.disabled"));
        saveMessages(request, messages);
      }
      else if (usuario.getEstatus().intValue() == 1)
      {
        usuario = null;
        bloqueado = true;
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.user.inactivo"));
        saveMessages(request, messages);


      }
      else
      {

        if (!usuario.getIsAdmin().booleanValue())
        {


          Date dateActual = new Date();
          Calendar c = Calendar.getInstance();
          c.setTime(dateActual);
          int dayOfWeek = c.get(7);
          
          DateFormat formatoHora = new SimpleDateFormat("hh:mm a");
          Calendar horaActual = Calendar.getInstance();
          String sHoraActual = Integer.toString(c.get(10));
          if (sHoraActual.length() == 1) {
            sHoraActual = "0" + sHoraActual;
          }
          sHoraActual = sHoraActual + ":";
          
          if (Integer.toString(c.get(12)).length() == 1) {
            sHoraActual = sHoraActual + "0" + Integer.toString(c.get(12));
          } else {
            sHoraActual = sHoraActual + Integer.toString(c.get(12));
          }
          if (horaActual.get(9) == 0) {
            sHoraActual = sHoraActual + " AM";
          } else {
            sHoraActual = sHoraActual + " PM";
          }
          horaActual.setTime(formatoHora.parse(sHoraActual));
          
          boolean accesoPermitido = true;
          switch (dayOfWeek)
          {
          case 2: 
            if (!configInicioSesion.getRestriccionUsoDiaLunes().booleanValue())
              accesoPermitido = false;
            break;
          case 3: 
            if (!configInicioSesion.getRestriccionUsoDiaMartes().booleanValue())
              accesoPermitido = false;
            break;
          case 4: 
            if (!configInicioSesion.getRestriccionUsoDiaMiercoles().booleanValue())
              accesoPermitido = false;
            break;
          case 5: 
            if (!configInicioSesion.getRestriccionUsoDiaJueves().booleanValue())
              accesoPermitido = false;
            break;
          case 6: 
            if (!configInicioSesion.getRestriccionUsoDiaViernes().booleanValue())
              accesoPermitido = false;
            break;
          case 7: 
            if (!configInicioSesion.getRestriccionUsoDiaSabado().booleanValue())
              accesoPermitido = false;
            break;
          case 1: 
            if (!configInicioSesion.getRestriccionUsoDiaDomingo().booleanValue()) {
              accesoPermitido = false;
            }
            break;
          }
          if (accesoPermitido)
          {
            if ((!configInicioSesion.getRestriccionUsoHoraDesde().equals("")) && (!configInicioSesion.getRestriccionUsoHoraHasta().equals("")))
            {
              Calendar horaDesde = Calendar.getInstance();
              horaDesde.setTime(formatoHora.parse(configInicioSesion.getRestriccionUsoHoraDesde()));
              
              Calendar horaHasta = Calendar.getInstance();
              horaHasta.setTime(formatoHora.parse(configInicioSesion.getRestriccionUsoHoraHasta()));
              
              boolean mayorIgual = horaActual.getTimeInMillis() >= horaDesde.getTimeInMillis();
              boolean menorIgual = horaActual.getTimeInMillis() <= horaHasta.getTimeInMillis();
              
              if ((!mayorIgual) || (!menorIgual)) {
                usuario = null;
                restringido = true;
                messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.login.restriccionuso"));
                saveMessages(request, messages);
              }
            }
          }
          else
          {
            usuario = null;
            restringido = true;
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.login.restriccionuso"));
            saveMessages(request, messages);
          }
          

          if (!restringido)
          {
            ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(usuario.getUsuarioId(), "com.visiongc.framework.web.configuracion.Usuarios", "RestriccionUso");
            
            if (configuracionUsuario != null)
            {
              DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
              DocumentBuilder db = dbf.newDocumentBuilder();
              Document doc = db.parse(new ByteArrayInputStream(configuracionUsuario.getData().getBytes("UTF-8")));
              doc.getDocumentElement().normalize();
              NodeList nList = doc.getElementsByTagName("Configuracion");
              Element eElement = (Element)nList.item(0);
              
              accesoPermitido = true;
              switch (dayOfWeek)
              {
              case 2: 
                if (!Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaLunes", eElement)))
                  accesoPermitido = false;
                break;
              case 3: 
                if (!Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaMartes", eElement)))
                  accesoPermitido = false;
                break;
              case 4: 
                if (!Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaMiercoles", eElement)))
                  accesoPermitido = false;
                break;
              case 5: 
                if (!Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaJueves", eElement)))
                  accesoPermitido = false;
                break;
              case 6: 
                if (!Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaViernes", eElement)))
                  accesoPermitido = false;
                break;
              case 7: 
                if (!Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaSabado", eElement)))
                  accesoPermitido = false;
                break;
              case 1: 
                if (!Boolean.parseBoolean(VgcAbstractService.getTagValue("restriccionUsoDiaDomingo", eElement))) {
                  accesoPermitido = false;
                }
                break;
              }
              if (accesoPermitido)
              {
                if ((!VgcAbstractService.getTagValue("restriccionUsoHoraDesde", eElement).equals("")) && (!VgcAbstractService.getTagValue("restriccionUsoHoraHasta", eElement).equals("")))
                {
                  Calendar horaDesde = Calendar.getInstance();
                  horaDesde.setTime(formatoHora.parse(VgcAbstractService.getTagValue("restriccionUsoHoraDesde", eElement)));
                  
                  Calendar horaHasta = Calendar.getInstance();
                  horaHasta.setTime(formatoHora.parse(VgcAbstractService.getTagValue("restriccionUsoHoraHasta", eElement)));
                  
                  boolean mayorIgual = horaActual.getTimeInMillis() >= horaDesde.getTimeInMillis();
                  boolean menorIgual = horaActual.getTimeInMillis() <= horaHasta.getTimeInMillis();
                  
                  if ((!mayorIgual) || (!menorIgual))
                  {
                    usuario = null;
                    restringido = true;
                    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.login.restriccionuso"));
                    saveMessages(request, messages);
                  }
                }
              }
              else
              {
                usuario = null;
                restringido = true;
                messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.login.restriccionuso"));
                saveMessages(request, messages);
              }
            }
          }
        }
        
        if (!restringido)
        {




          UserSession session = new UserSession();
          
          session.setSessionId(request.getSession().getId());
          
          if (persona != null)
          {

            session.setUsuarioId(persona.getUsuarioId());
            session.setPersonaId(persona.getPersonaId());
          }
          else {
            session.setUsuarioId(usuario.getUsuarioId());
          }
          session.setLoginTs(new Date());
          session.setRemoteAddress(request.getRemoteAddr());
          session.setRemoteHost(request.getRemoteHost());
          session.setRemoteUser(request.getRemoteUser());
          session.setUrl(request.getContextPath());
          session.setUsuario(usuario);
          
          frameworkService.saveUserSession(session, usuario);
          

          boolean watchSessions = false;
          watchSessions = FrameworkWebConfiguration.getInstance().getBoolean("com.visiongc.app.web.watchsessions");
          if (request.getSession().getServletContext().getAttribute("com.visiongc.app.web.watchsessions") == null) {
            request.getSession().getServletContext().setAttribute("com.visiongc.app.web.watchsessions", Boolean.valueOf(watchSessions));
            Long intervaloRefresh = new Long(SessionsWatcher.INTERVALO_REFRESH_SESSION);
            String intervaloSegundos = FrameworkWebConfiguration.getInstance().getString("com.visiongc.app.web.watchsessions.refreshinterval");
            if ((intervaloSegundos != null) && (!intervaloSegundos.equals(""))) {
              intervaloRefresh = new Long(Long.parseLong(intervaloSegundos) * 1000L);
            }
            request.getSession().getServletContext().setAttribute("com.visiongc.app.web.watchsessions.refreshinterval", intervaloRefresh);
          }
          if (watchSessions) {
            SessionsWatcher.getInstance().addSession(request.getSession().getId());
          }
        }
      }
    }
    

    PermisologiaUsuario pu = null;
    if ((usuario == null) && (!bloqueado) && (!restringido) && (!tipoAuthentication.equalsIgnoreCase("ldap")))
    {




      request.getSession().removeAttribute("usuario");
      
      if (request.getSession().getAttribute("reintentos") == null) {
        request.getSession().setAttribute("reintentos", "1");
      }
      
      int reintentos = Integer.parseInt(request.getSession().getAttribute("reintentos").toString());
      int maxReintentos = configInicioSesion.getMaximoReintentos().intValue();
      usuario = frameworkService.getUsuarioPorLogin(username);
      
      if (usuario != null)
      {
        if (request.getSession().getAttribute("reintentosUsuario") != null)
        {
          if (!username.equals(request.getSession().getAttribute("reintentosUsuario").toString()))
          {
            request.getSession().setAttribute("reintentosUsuario", username);
            request.getSession().setAttribute("reintentos", Integer.valueOf(1));
          }
        }
        else {
          request.getSession().setAttribute("reintentosUsuario", username);
        }
        if (reintentos >= maxReintentos)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.password.maxretries"));
          
          usuariosService.bloquearUsuario(usuario.getUsuarioId());
        }
        else
        {
          reintentos++;
          request.getSession().setAttribute("reintentos", Integer.valueOf(reintentos));
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.password.mismatch"));
        }
      }
      else {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.password.mismatch"));
      }
      saveMessages(request, messages);
    }
    else if ((forward != "conectado") && (usuario != null))
    {



      Date datepassword = null;
      int expiraContrasena = configInicioSesion.getExpiraContrasena().intValue();
      
      if ((usuario.getUltimaModifPwd() != null) && (!usuario.getUltimaModifPwd().equals("")) && (expiraContrasena > 0))
      {
        datepassword = usuario.getUltimaModifPwd();
        Calendar c = Calendar.getInstance();
        c.setTime(datepassword);
        
        Calendar fecha2 = Calendar.getInstance();
        int day = c.get(5);
        int month = c.get(2);
        int year = c.get(1);
        
        fecha2.set(5, day);
        fecha2.set(2, month);
        fecha2.set(1, year);
        
        int duracionExpiracion = configInicioSesion.getDuracionExpiracion().intValue();
        switch (configInicioSesion.getPeriodoExpiracion().intValue())
        {
        case 0: 
          fecha2.add(5, duracionExpiracion);
          c = fecha2;
          break;
        case 1: 
          fecha2.add(5, duracionExpiracion * 7);
          c = fecha2;
          break;
        case 2: 
          fecha2.add(2, duracionExpiracion);
          c = fecha2;
          break;
        case 3: 
          fecha2.add(1, duracionExpiracion);
          c = fecha2;
        }
        
        
        datepassword.setTime(c.getTimeInMillis());
      }
      
      if (((!usuario.getIsAdmin().booleanValue()) && (datepassword != null) && (!usuario.getUltimaModifPwd().equals("")) && (datepassword.getTime() <= new Date().getTime()) && (expiraContrasena > 0)) || (usuario.getForzarCambiarpwd().booleanValue()))
      {

        forward = "cambiarClaveUsuario";
        if (usuario.getForzarCambiarpwd().booleanValue()) {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.login.forzarcambioclave"));
        } else {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.login.cambioclave"));
        }
        saveMessages(request, messages);
      }
      else
      {
        int advertenciaCaducidad = configInicioSesion.getAdvertenciaCaducidad().intValue();
        
        if ((datepassword != null) && (advertenciaCaducidad > 0))
        {
          Date dateAdvertencia = new Date();
          Calendar c = Calendar.getInstance();
          c.setTime(datepassword);
          
          Calendar fecha2 = Calendar.getInstance();
          int day = c.get(5);
          int month = c.get(2);
          int year = c.get(1);
          
          fecha2.set(5, day);
          fecha2.set(2, month);
          fecha2.set(1, year);
          
          fecha2.add(5, advertenciaCaducidad * -1);
          c = fecha2;
          dateAdvertencia.setTime(c.getTimeInMillis());
          

          if ((!usuario.getIsAdmin().booleanValue()) && (dateAdvertencia != null) && (dateAdvertencia.getTime() <= new Date().getTime()) && (expiraContrasena > 0))
          {
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.login.advertencia", VgcFormatter.formatearFecha(datepassword, "formato.fecha.corta")));
            saveMessages(request, messages);
          }
        }
      }
      



      pu = new PermisologiaUsuario(usuario.getUsuarioId().longValue());
      if (forward != "cambiarClaveUsuario")
      {
        if (mapping.findForward("home") != null)
        {
          if (!pu.tienePermiso("ORGANIZACION"))
          {
            if (pu.tienePermiso("CONFIGURACION"))
            {
              forward = "configuracion";
              request.getSession().setAttribute("soloAdministracion", "true");
            }
            else {
              forward = "home";
            }
          } else {
            forward = "home";
          }
        } else
          forward = "exito";
      }
      if ((forward == "conectado") || (forward == "configuracion") || (forward == "home") || (forward == "exito"))
      {
        usuario.setIsConnected(Boolean.valueOf(true));
        frameworkService.setConectado(Boolean.valueOf(true), usuario.getUsuarioId());
        request.getSession().setAttribute("defaultLoader", Boolean.valueOf(true));
      }
      
      request.getSession().setAttribute("usuario", usuario);
      request.getSession().setAttribute("tipoExplorer", tipoExplorer);
      if (pu != null) {
        request.getSession().setAttribute("com.visiongc.framework.web.PERMISOLOGIA_USUARIO", pu);
      }
    } else if ((usuario == null) && (tipoAuthentication.equalsIgnoreCase("ldap")))
    {
      if (resultAuthenticationActiveDirectory.intValue() == 7) {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.user.ldap.not.exist"));
      } else if (resultAuthenticationActiveDirectory.intValue() == 8) {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.user.ldap.failure.conetion"));
      } else if (resultAuthenticationActiveDirectory.intValue() == 1)
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("error.user.ldap.invalid.user.system"));
      saveMessages(request, messages);
    }
    

    frameworkService.close();
    usuariosService.close();
    

    return mapping.findForward(forward);
  }
}
