package com.visiongc.framework.web.struts.actions.servicio;

import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.Password;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.util.FrameworkConnection;
import com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm;
import com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm.GestionarServiciosStatus;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;










public class ConfigurarServiciosAction
  extends VgcAction
{
  public ConfigurarServiciosAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    ActionMessages messages = getMessages(request);
    String forward = mapping.getParameter();
    GestionarServiciosForm gestionarServiciosForm = (GestionarServiciosForm)form;
    
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    
    Connection connection = FrameworkServiceFactory.getInstance().getCurrentSession();
    Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
    
    String accion = request.getParameter("accion");
    Boolean changePassword = Boolean.valueOf(request.getParameter("changePassword") != null ? Boolean.parseBoolean(request.getParameter("changePassword")) : false);
    if (accion == null)
    {
      gestionarServiciosForm.clear();
      
      gestionarServiciosForm.setStringConexion(connection.getMetaData().getURL());
      gestionarServiciosForm.setUsuarioConexion(connection.getMetaData().getUserName());
      
      String driverName = connection.getMetaData().getDriverName();
      if (driverName.toLowerCase().indexOf("postgresql") > -1) {
        gestionarServiciosForm.setDriverConexion("org.postgresql.Driver");
      } else if (driverName.toLowerCase().indexOf("oracle") > -1) {
        gestionarServiciosForm.setDriverConexion("oracle.jdbc.driver.OracleDriver");
      } else if (driverName.toLowerCase().indexOf("sqlserver") > -1) {
        gestionarServiciosForm.setDriverConexion("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      } else if (driverName.toLowerCase().indexOf("odbc") > -1) {
        gestionarServiciosForm.setDriverConexion("sun.jdbc.odbc.JdbcOdbcDriver");
      } else {
        gestionarServiciosForm.setDriverConexion(null);
      }
      if (configuracion != null)
      {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("properties");
        Element eElement = (Element)nList.item(0);
        

        gestionarServiciosForm.setStringConexion(VgcAbstractService.getTagValue("url", eElement));
        gestionarServiciosForm.setDriverConexion(VgcAbstractService.getTagValue("driver", eElement));
        gestionarServiciosForm.setUsuarioConexion(VgcAbstractService.getTagValue("user", eElement));
        gestionarServiciosForm.setPasswordConexion(VgcAbstractService.getTagValue("password", eElement));
        
        String url = gestionarServiciosForm.getStringConexion();
        String driver = gestionarServiciosForm.getDriverConexion();
        String user = gestionarServiciosForm.getUsuarioConexion();
        String password = gestionarServiciosForm.getPasswordConexion();
        if (!password.substring(0, 2).equalsIgnoreCase("&H")) {
          password = Password.encriptPassWord(password);
        }
        if (new FrameworkConnection().testConnection(url, driver, user, gestionarServiciosForm.getPasswordConexionDecriptado(password)))
        {
          gestionarServiciosForm.setStatus(GestionarServiciosForm.GestionarServiciosStatus.getStatusSuccess());
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.configurar.conexion.mensaje.conexion.exitoso"));
          saveMessages(request, messages);
          frameworkService.close();
          
          return mapping.findForward(forward);
        }
      }
      
      gestionarServiciosForm.setStatus(GestionarServiciosForm.GestionarServiciosStatus.getStatusViewSuccess());
    }
    else if (accion.equals("SAVE"))
    {
      String url = gestionarServiciosForm.getStringConexion();
      String driver = gestionarServiciosForm.getDriverConexion();
      String user = gestionarServiciosForm.getUsuarioConexion();
      String password = "";
      if (changePassword.booleanValue()) {
        password = gestionarServiciosForm.getPasswordConexionEncript();
      } else {
        password = gestionarServiciosForm.getPasswordConexion();
      }
      if (!password.substring(0, 2).equalsIgnoreCase("&H")) {
        password = Password.encriptPassWord(password);
      }
      if (!new FrameworkConnection().testConnection(url, driver, user, gestionarServiciosForm.getPasswordConexionDecriptado(password)))
      {
        gestionarServiciosForm.setStatus(GestionarServiciosForm.GestionarServiciosStatus.getStatusConexionInvalida());
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.configurar.conexion.mensaje.conexion.invalida"));
        saveMessages(request, messages);
        frameworkService.close();
        
        return mapping.findForward(forward);
      }
      
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      DOMImplementation implementation = builder.getDOMImplementation();
      Document document = implementation.createDocument(null, "servicios", null);
      document.setXmlVersion("1.0");
      
      Element raiz = document.createElement("properties");
      document.getDocumentElement().appendChild(raiz);
      
      Element elemento = document.createElement("url");
      Text text = document.createTextNode(url);
      elemento.appendChild(text);
      raiz.appendChild(elemento);
      
      elemento = document.createElement("driver");
      text = document.createTextNode(driver);
      elemento.appendChild(text);
      raiz.appendChild(elemento);
      
      elemento = document.createElement("user");
      text = document.createTextNode(user);
      elemento.appendChild(text);
      raiz.appendChild(elemento);
      
      elemento = document.createElement("password");
      text = document.createTextNode(password);
      elemento.appendChild(text);
      raiz.appendChild(elemento);
      
      Source source = new DOMSource(document);
      
      StringWriter writer = new StringWriter();
      Result result = new StreamResult(writer);
      
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.transform(source, result);
      
      configuracion = new Configuracion();
      configuracion.setParametro("Strategos.Servicios.Configuracion");
      configuracion.setValor(writer.toString().trim());
      if (frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request)) == 10000) {
        gestionarServiciosForm.setStatus(GestionarServiciosForm.GestionarServiciosStatus.getStatusSuccess());
      } else
        gestionarServiciosForm.setStatus(GestionarServiciosForm.GestionarServiciosStatus.getStatusNoSuccess());
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
      
      saveMessages(request, messages);
    }
    else if (accion.equals("TEST"))
    {
      String url = gestionarServiciosForm.getStringConexion();
      String driver = gestionarServiciosForm.getDriverConexion();
      String user = gestionarServiciosForm.getUsuarioConexion();
      String password = "";
      boolean passwordChange = false;
      if (changePassword.booleanValue())
      {
        passwordChange = true;
        password = gestionarServiciosForm.getPasswordConexionEncript();
      }
      else {
        password = gestionarServiciosForm.getPasswordConexion();
      }
      if (!password.substring(0, 2).equalsIgnoreCase("&H")) {
        password = Password.encriptPassWord(password);
      }
      if (!new FrameworkConnection().testConnection(url, driver, user, gestionarServiciosForm.getPasswordConexionDecriptado(password)))
      {
        gestionarServiciosForm.setStatus(GestionarServiciosForm.GestionarServiciosStatus.getStatusTestNoSuccess());
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.configurar.conexion.mensaje.conexion.invalida"));
        saveMessages(request, messages);
        frameworkService.close();
        
        return mapping.findForward(forward);
      }
      

      if (passwordChange) {
        gestionarServiciosForm.setStatus(GestionarServiciosForm.GestionarServiciosStatus.getStatusTestSuccessPasswordChange());
      } else
        gestionarServiciosForm.setStatus(GestionarServiciosForm.GestionarServiciosStatus.getStatusTestSuccess());
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.configurar.conexion.mensaje.conexion.sucess"));
      saveMessages(request, messages);
      frameworkService.close();
      
      return mapping.findForward(forward);
    }
    

    frameworkService.close();
    
    return mapping.findForward(forward);
  }
}
