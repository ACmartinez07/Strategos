package com.visiongc.framework.web.struts.actions;

import com.visiongc.authentication.ExternalAuthenticator;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Licencia;
import com.visiongc.framework.model.Sistema;
import com.visiongc.framework.model.Sistema.SistemaTipo;
import com.visiongc.framework.web.struts.forms.NavegadorForm;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;






public final class WelcomeAction
  extends VgcAction
{
  public WelcomeAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.clear();
  }
  


  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    String forward = "ingreso";
    
    String sesId = request.getParameter("sesId");
    String sessionId = request.getSession().getId();
    
    if ((sesId == null) || (!sesId.equals(sessionId))) {
      forward = "inicio";
    }
    
    HttpSession session = request.getSession(false);
    
    if (session != null)
    {

      synchronized (session)
      {
        session.invalidate();
      }
    }
    

    request.getSession(true);
    
    if (!checkVersion(request)) {
      forward = "sincronizar";
    }
    if (!forward.equals("sincronizar"))
    {
      Licencia licencia = new Licencia().getLicencia(request);
      if (licencia == null) {
        forward = "expirado";
      } else if ((licencia != null) && (licencia.getRespuesta() == 10002)) {
        forward = "folderConfiguracionEmpty";
      } else if ((licencia != null) && (licencia.getRespuesta() == 10001)) {
        forward = "folderConfiguracionError";
      } else if ((licencia != null) && (licencia.getExpiracion() == null) && (licencia.getChequearLicencia().booleanValue()) && ((licencia.getTipo().equals(Sistema.SistemaTipo.getSistemaTipoName(Sistema.SistemaTipo.getSistemaTipoDemo()))) || (licencia.getTipo().equals(Sistema.SistemaTipo.getSistemaTipoName(Sistema.SistemaTipo.getSistemaTipoPrototipo()))))) {
        forward = "expirado";
      } else if ((licencia != null) && (licencia.getInstalacionMaquinaDiferentes().booleanValue())) {
        forward = "expirado";
      } else if ((licencia != null) && (licencia.getNumeroUsuarios() == null)) {
        forward = "fullConexion";
      }
    }
    



    if (ExternalAuthenticator.getInstance().isActive()) {
      request.getSession().setAttribute("autenticacionExterna", "true");
    } else {
      request.getSession().setAttribute("autenticacionExterna", "false");
    }
    NavegadorForm navegadorForm = new NavegadorForm();
    
    try
    {
      navegadorForm.setPantallaAlto(Integer.valueOf(request.getParameter("pantallaAlto")));
      navegadorForm.setPantallaAncho(Integer.valueOf(request.getParameter("pantallaAncho")));
      navegadorForm.setNavegadorNombre(request.getParameter("navegadorNombre"));
      navegadorForm.setNavegadorVersion(request.getParameter("navegadorVersion"));
    }
    catch (Exception e)
    {
      forward = "inicio";
    }
    
    if (forward.equals("ingreso"))
    {
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
      frameworkService.getMasterConfiguracion(request);
      frameworkService.close();
    }
    

    return mapping.findForward(forward);
  }
  




  private boolean checkVersion(HttpServletRequest request)
    throws Exception
  {
    String version = request.getParameter("versionApp");
    Long fecha = Long.valueOf(FechaUtil.convertirStringToDate(request.getParameter("fechaApp"), VgcResourceManager.getResourceApp("formato.fecha.corta")).getTime());
    Long build = Long.valueOf(Long.parseLong(request.getParameter("buildApp")));
    
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    Sistema sistema = frameworkService.getSistema(null);
    if (sistema.getVersion() == null)
      sistema.setVersion("");
    if (sistema.getBuild() == null)
      sistema.setBuild(Long.valueOf(0L));
    if (sistema.getFecha() == null)
      sistema.setFecha(new Date());
    String versionActual = null;
    String buildActual = null;
    if (sistema.getActual() != null)
    {
      String[] actual = sistema.getActual().split("-");
      if (actual.length > 0)
      {
        versionActual = actual[0];
        buildActual = actual[1];
      }
    }
    
    int respuesta = 10000;
    if ((version.equals(versionActual)) && (build.toString().equals(buildActual)))
    {
      checkProducto(sistema, request);
      
      DateFormat formatter = new SimpleDateFormat("yyMMdd");
      String dateSistema = formatter.format(sistema.getFecha());
      String dateVersion = formatter.format(new Date(fecha.longValue()));
      if ((!sistema.getVersion().equals(version)) || (sistema.getBuild().longValue() != build.longValue()) || (!dateSistema.equals(dateVersion))) {
        respuesta = frameworkService.setVersion(sistema.getSistemaId(), fecha, build, version);
      }
    }
    frameworkService.close();
    
    return (respuesta == 10000) && (version.equals(versionActual)) && (build.toString().equals(buildActual));
  }
  








  public NavegadorForm checkEstilo()
    throws ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException
  {
    NavegadorForm estilos = new NavegadorForm();
    
    try
    {
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
      Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Estilos.Configuracion");
      frameworkService.close();
      
      if (configuracion != null)
      {
        estilos.clear();
        estilos.setHayConfiguracion(Boolean.valueOf(true));
        

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("properties");
        Element eElement = (Element)nList.item(0);
        

        estilos.setLogo1(VgcAbstractService.getTagValue("logo1", eElement));
        estilos.setLogo2(VgcAbstractService.getTagValue("logo2", eElement));
        estilos.setLogo3(VgcAbstractService.getTagValue("logo3", eElement));
        estilos.setLogo4(VgcAbstractService.getTagValue("logo4", eElement));
        estilos.setEstiloSuperior(VgcAbstractService.getTagValue("estiloSuperior", eElement));
        estilos.setEstiloSuperiorForma(VgcAbstractService.getTagValue("estiloSuperiorForma", eElement));
        estilos.setEstiloSuperiorFormaIzquierda(VgcAbstractService.getTagValue("estiloSuperiorFormaIzquierda", eElement));
        estilos.setEstiloLetras(VgcAbstractService.getTagValue("estiloLetras", eElement));
        estilos.setEstiloInferior(VgcAbstractService.getTagValue("estiloInferior", eElement));
        estilos.setImageBackground(VgcAbstractService.getTagValue("imageBackground", eElement));
        estilos.setEstiloFormaInterna(VgcAbstractService.getTagValue("estiloFormaInterna", eElement));
        estilos.setMouseFueraBarraSuperiorForma(VgcAbstractService.getTagValue("mouseFueraBarraSuperiorForma", eElement));
        estilos.setMouseFueraBarraSuperiorFormaColor(VgcAbstractService.getTagValue("mouseFueraBarraSuperiorFormaColor", eElement));
        
        if ((estilos != null) && (estilos.getEstiloSuperior() != null))
        {
          String[] variables = estilos.getEstiloSuperior().split(";");
          for (int i = 0; i < variables.length; i++)
          {
            if (variables[i].indexOf("background") != -1)
            {
              estilos.setBackground(variables[i]);
              break;
            }
          }
        }
      }
      else {
        estilos = null;
      }
    }
    catch (Exception e) {
      estilos = null;
    }
    
    return estilos;
  }
  
  public void checkProducto(Sistema sistema, HttpServletRequest request) throws Exception
  {
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    
    Licencia licencia = new Licencia().getLicencia(request);
    if (licencia == null)
    {
      if (sistema == null) {
        sistema = frameworkService.getSistema(null);
      }
      request.getSession().setAttribute("licencia", frameworkService.getCheckProducto(sistema, request));
      licencia = (Licencia)request.getSession().getAttribute("licencia");
    }
    
    if (licencia != null)
    {
      if (licencia.getCompanyName() != null)
        request.getSession().setAttribute("companyName", licencia.getCompanyName());
      if (licencia.getProductoId() != null) {
        request.getSession().setAttribute("productoId", licencia.getProductoId());
      }
    }
    request.getSession().setAttribute("enviroment", frameworkService.getEnvironment());
    frameworkService.close();
  }
  
  public Boolean esLicenciaDemostracion()
  {
    boolean esLicenciaDemostracion = false;
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    Sistema sistema = frameworkService.getSistema(null);
    
    try
    {
      Licencia licencia = frameworkService.readProducto(sistema);
      if (!licencia.getChequearLicencia().booleanValue()) {
        esLicenciaDemostracion = true;
      }
    }
    catch (Exception localException) {}
    

    frameworkService.close();
    
    return Boolean.valueOf(esLicenciaDemostracion);
  }
}
