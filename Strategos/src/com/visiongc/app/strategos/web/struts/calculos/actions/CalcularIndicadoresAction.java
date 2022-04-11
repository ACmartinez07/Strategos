package com.visiongc.app.strategos.web.struts.calculos.actions;

import com.visiongc.app.strategos.web.struts.calculos.forms.CalculoIndicadoresForm;
import com.visiongc.app.strategos.web.struts.calculos.forms.CalculoIndicadoresForm.CalcularStatus;
import com.visiongc.app.strategos.web.struts.servicio.forms.ServicioForm;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoEvento;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.util.FrameworkConnection;
import com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm;
import com.visiongc.servicio.strategos.calculos.CalcularManager;
import com.visiongc.servicio.strategos.servicio.model.Servicio;
import java.io.ByteArrayInputStream;
import java.util.Calendar;
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

public class CalcularIndicadoresAction
  extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    boolean cancel = (request.getParameter("cancel") != null) && (request.getParameter("cancel").equals("1"));
    if (cancel)
    {
      request.getSession().removeAttribute("calculoIndicadoresForm");
      request.getSession().removeAttribute("verArchivoLog");
      
      return getForwardBack(request, 1, true);
    }
    String forward = mapping.getParameter();
    
    CalculoIndicadoresForm calculoIndicadoresForm = (CalculoIndicadoresForm)form;
    if ((calculoIndicadoresForm.getIndicadorId() != null) && (calculoIndicadoresForm.getIndicadorId().longValue() == 0L)) {
      calculoIndicadoresForm.setIndicadorId(null);
    }
    if ((calculoIndicadoresForm.getClaseId() != null) && (calculoIndicadoresForm.getClaseId().longValue() == 0L)) {
      calculoIndicadoresForm.setClaseId(null);
    }
    if ((calculoIndicadoresForm.getOrganizacionId() != null) && (calculoIndicadoresForm.getOrganizacionId().longValue() == 0L)) {
      calculoIndicadoresForm.setOrganizacionId(null);
    }
    if ((calculoIndicadoresForm.getPerspectivaId() != null) && (calculoIndicadoresForm.getPerspectivaId().longValue() == 0L)) {
      calculoIndicadoresForm.setPerspectivaId(null);
    }
    if ((calculoIndicadoresForm.getPlanId() != null) && (calculoIndicadoresForm.getPlanId().longValue() == 0L)) {
      calculoIndicadoresForm.setPlanId(null);
    }
    if ((calculoIndicadoresForm.getIniciativaId() != null) && (calculoIndicadoresForm.getIniciativaId().longValue() == 0L)) {
      calculoIndicadoresForm.setIniciativaId(null);
    }
    Calcular(request, calculoIndicadoresForm);
    calculoIndicadoresForm.setCalculado(new Boolean(true));
    if (request.getSession().getAttribute("actualizarForma") == null) {
      request.getSession().setAttribute("actualizarForma", "true");
    }
    return mapping.findForward(forward);
  }
  
  private void Calcular(HttpServletRequest request, CalculoIndicadoresForm calculoIndicadoresForm)
    throws Exception
  {
    ServicioForm servicioForm = new ServicioForm();
    StringBuffer log = new StringBuffer();
    
    ActionMessages messages = getMessages(request);
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
    
    String showPresentacion = request.getParameter("showPresentacion") != null ? request.getParameter("showPresentacion").toString() : "0";
    ConfiguracionUsuario presentacion = new ConfiguracionUsuario();
    ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
    pk.setConfiguracionBase("Strategos.Wizar.Calcular.ShowPresentacion");
    pk.setObjeto("ShowPresentacion");
    pk.setUsuarioId(getUsuarioConectado(request).getUsuarioId());
    presentacion.setPk(pk);
    presentacion.setData(showPresentacion);
    frameworkService.saveConfiguracionUsuario(presentacion, getUsuarioConectado(request));
    if (configuracion == null)
    {
      calculoIndicadoresForm.setStatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusNoConfigurado());
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
      saveMessages(request, messages);
    }
    else
    {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
      doc.getDocumentElement().normalize();
      NodeList nList = doc.getElementsByTagName("properties");
      Element eElement = (Element)nList.item(0);
      
      String url = VgcAbstractService.getTagValue("url", eElement);
      String driver = VgcAbstractService.getTagValue("driver", eElement);
      String user = VgcAbstractService.getTagValue("user", eElement);
      String password = VgcAbstractService.getTagValue("password", eElement);
      password = new GestionarServiciosForm().getPasswordConexionDecriptado(password);
      if (!new FrameworkConnection().testConnection(url, driver, user, password))
      {
        calculoIndicadoresForm.setStatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusNoConfigurado());
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
        saveMessages(request, messages);
      }
      else
      {
        Usuario usuario = getUsuarioConectado(request);
        
        com.visiongc.commons.util.VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
        log.append(messageResources.getResource("jsp.asistente.calculo.log.titulocalculo") + "\n");
        
        Calendar ahora = Calendar.getInstance();
        String[] argsReemplazo = new String[2];
        argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
        argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
        log.append(messageResources.getResource("jsp.asistente.calculo.log.fechainiciocalculo", argsReemplazo) + "\n\n");
        
        servicioForm.setProperty("url", url);
        servicioForm.setProperty("driver", driver);
        servicioForm.setProperty("user", user);
        servicioForm.setProperty("password", password);
        
        servicioForm.setProperty("logMediciones", calculoIndicadoresForm.getReportarIndicadores().toString());
        servicioForm.setProperty("logErrores", calculoIndicadoresForm.getReportarErrores().toString());
        servicioForm.setProperty("tomarPeriodosSinMedicionConValorCero", calculoIndicadoresForm.getPeriodosCero().toString());
        servicioForm.setProperty("planId", calculoIndicadoresForm.getPlanId() != null ? calculoIndicadoresForm.getPlanId().toString() : "");
        servicioForm.setProperty("organizacionId", calculoIndicadoresForm.getOrganizacionId() != null ? calculoIndicadoresForm.getOrganizacionId().toString() : "");
        servicioForm.setProperty("perspectivaId", calculoIndicadoresForm.getPerspectivaId() != null ? calculoIndicadoresForm.getPerspectivaId().toString() : "");
        servicioForm.setProperty("frecuencia", calculoIndicadoresForm.getFrecuencia() != null ? calculoIndicadoresForm.getFrecuencia().toString() : "");
        if ((calculoIndicadoresForm.getOrigen().byteValue() != calculoIndicadoresForm.getOrigenIniciativas().byteValue()) && (calculoIndicadoresForm.getClaseId() != null))
        {
          servicioForm.setProperty("claseId", (calculoIndicadoresForm.getAlcance().byteValue() == calculoIndicadoresForm.getAlcanceClase().byteValue()) && (calculoIndicadoresForm.getClaseId() != null) ? calculoIndicadoresForm.getClaseId().toString() : "");
        }
        else if (calculoIndicadoresForm.getIniciativaId() != null)
        {
          servicioForm.setProperty("claseId", (calculoIndicadoresForm.getAlcance().byteValue() == calculoIndicadoresForm.getAlcanceIniciativa().byteValue()) && (calculoIndicadoresForm.getIniciativaId() != null) ? calculoIndicadoresForm.getClaseId().toString() : "");
          servicioForm.setProperty("desdeIniciativas", "true");
        }
        else
        {
          servicioForm.setProperty("claseId", "");
        }
        servicioForm.setProperty("ano", calculoIndicadoresForm.getAno());
        servicioForm.setProperty("mesInicial", calculoIndicadoresForm.getMesInicial().toString());
        servicioForm.setProperty("mesFinal", calculoIndicadoresForm.getMesFinal().toString());
        servicioForm.setProperty("arbolCompletoOrganizacion", Boolean.valueOf(calculoIndicadoresForm.getAlcance().byteValue() == calculoIndicadoresForm.getAlcanceOrganizacion().byteValue()).toString());
        servicioForm.setProperty("todasOrganizaciones", Boolean.valueOf(calculoIndicadoresForm.getAlcance().byteValue() == calculoIndicadoresForm.getAlcanceOrganizacionTodas().byteValue()).toString());
        servicioForm.setProperty("indicadorId", calculoIndicadoresForm.getIndicadorId() != null ? calculoIndicadoresForm.getIndicadorId().toString() : "");
        servicioForm.setProperty("porNombre", calculoIndicadoresForm.getPorNombre().toString());
        servicioForm.setProperty("nombreIndicador", calculoIndicadoresForm.getNombreIndicador() != null ? calculoIndicadoresForm.getNombreIndicador() : "");
        
        servicioForm.setProperty("logConsolaMetodos", Boolean.valueOf(false).toString());
        servicioForm.setProperty("logConsolaDetallado", Boolean.valueOf(false).toString());
        servicioForm.setProperty("usuarioId", usuario.getUsuarioId().toString());
        
        servicioForm.setProperty("activarSheduler", Boolean.valueOf(true).toString());
        servicioForm.setProperty("unidadTiempo", Integer.valueOf(3).toString());
        servicioForm.setProperty("numeroEjecucion", Integer.valueOf(1).toString());
        
        StringBuffer logBefore = log;
        
        // ejecucion calculo 
        
        new CalcularManager(servicioForm.Get(), log, com.visiongc.servicio.web.importar.util.VgcMessageResources.getVgcMessageResources("StrategosWeb")).Ejecutar();
        log = logBefore;
        
        calculoIndicadoresForm.setStatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusCalculado());
        
        ahora = Calendar.getInstance();
        argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
        argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
        
        log.append("\n\n");
        log.append(messageResources.getResource("jsp.asistente.calculo.log.fechafin.programada", argsReemplazo) + "\n\n");
        
        request.getSession().setAttribute("verArchivoLog", log);
        if (usuario != null)
        {
          byte tipoEvento = AuditoriaTipoEvento.getAuditoriaTipoEventoCalculo();
          Servicio servicio = new Servicio();
          servicio.setUsuarioId(usuario.getUsuarioId());
          servicio.setFecha(VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy") + " " + VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a"));
          servicio.setNombre(messageResources.getResource("jsp.asistente.calculo.log.titulocalculo"));
          servicio.setMensaje(messageResources.getResource("jsp.asistente.calculo.log.fechafin.programada", argsReemplazo));
          servicio.setEstatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusCalculado());
          
          frameworkService.registrarAuditoriaEvento(servicio, usuario, tipoEvento);
        }
      }
    }
    frameworkService.close();
  }
}