package com.visiongc.app.strategos.web.struts.calculos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.calculos.forms.CalculoIndicadoresForm;
import com.visiongc.app.strategos.web.struts.calculos.forms.CalculoIndicadoresForm.CalcularStatus;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.util.FrameworkConnection;
import com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm;
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

public final class ConfigurarCalculoIndicadoresAction
  extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    ActionMessages messages = getMessages(request);
    String forward = mapping.getParameter();
    
    CalculoIndicadoresForm calculoIndicadoresForm = (CalculoIndicadoresForm)form;
    String funcion = request.getParameter("funcion");
    if ((funcion == null) || ((funcion != null) && (!funcion.equals("getNumeroIndicadoresPorAlcance")))) {
      calculoIndicadoresForm.clear();
    }
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
    if ((funcion == null) || ((funcion != null) && (!funcion.equals("getNumeroIndicadoresPorAlcance"))))
    {
      ConfiguracionUsuario presentacion = frameworkService.getConfiguracionUsuario(getUsuarioConectado(request).getUsuarioId(), "Strategos.Wizar.Calcular.ShowPresentacion", "ShowPresentacion");
      if ((presentacion != null) && (presentacion.getData() != null)) {
        calculoIndicadoresForm.setShowPresentacion(Boolean.valueOf(presentacion.getData().equals("1")));
      }
    }
    frameworkService.close();
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
      if (!new FrameworkConnection().testConnection(url, driver, user, new GestionarServiciosForm().getPasswordConexionDecriptado(password)))
      {
        calculoIndicadoresForm.setStatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusNoConfigurado());
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
        saveMessages(request, messages);
      }
      else
      {
        calculoIndicadoresForm.setStatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusSuccess());
        if ((funcion != null) && (funcion.equals("getNumeroIndicadoresPorAlcance")))
        {
          if ((calculoIndicadoresForm.getIndicadorId() != null) && (calculoIndicadoresForm.getIndicadorId().longValue() == 0L)) {
            calculoIndicadoresForm.setIndicadorId(null);
          }
          if (calculoIndicadoresForm.getAlcance().byteValue() != calculoIndicadoresForm.getAlcanceClase().byteValue()) {
            calculoIndicadoresForm.setClaseId(null);
          }
          if (calculoIndicadoresForm.getAlcance().byteValue() != calculoIndicadoresForm.getAlcancePerspectiva().byteValue()) {
            calculoIndicadoresForm.setPerspectivaId(null);
          }
          if ((calculoIndicadoresForm.getAlcance().byteValue() != calculoIndicadoresForm.getAlcancePlan().byteValue()) && (calculoIndicadoresForm.getPerspectivaId() == null)) {
            calculoIndicadoresForm.setPlanId(null);
          }
          request.setAttribute("ajaxResponse", "1");
          return mapping.findForward("ajaxResponse");
        }
      }
    }
    String[] indicadorId = request.getParameterValues("indicadorId");
    String organizacionId = request.getParameter("organizacionId");
    String claseId = request.getParameter("claseId");
    String planId = request.getParameter("planId");
    String perspectivaId = request.getParameter("perspectivaId");
    Long iniciativaId = request.getParameter("iniciativaId") != null ? Long.valueOf(Long.parseLong(request.getParameter("iniciativaId"))) : null;
    
    boolean desdeOrganizaciones = (organizacionId != null) && (!organizacionId.equals(""));
    boolean desdeIndicadores = (claseId != null) && (!claseId.equals(""));
    boolean desdePlanes = (planId != null) && (!planId.equals(""));
    boolean desdePerspectivas = (perspectivaId != null) && (!perspectivaId.equals(""));
    Boolean desdeIniciativa = Boolean.valueOf(request.getParameter("desdeIniciativa") != null ? Boolean.parseBoolean(request.getParameter("desdeIniciativa")) : false);
    Plan plan = null;
    if ((desdeIndicadores) || (desdePlanes)) {
      desdeOrganizaciones = false;
    }
    if ((!desdeIniciativa.booleanValue()) && (desdeOrganizaciones))
    {
      calculoIndicadoresForm.setOrigen(calculoIndicadoresForm.getOrigenOrganizaciones());
      calculoIndicadoresForm.setAlcance(calculoIndicadoresForm.getAlcanceOrganizacion());
      calculoIndicadoresForm.setOrganizacionId(new Long(organizacionId));
    }
    else if ((!desdeIniciativa.booleanValue()) && (desdeIndicadores))
    {
      calculoIndicadoresForm.setOrigen(calculoIndicadoresForm.getOrigenIndicadores());
      calculoIndicadoresForm.setAlcance(calculoIndicadoresForm.getAlcanceClase());
      calculoIndicadoresForm.setClaseId(new Long(claseId));
      if ((indicadorId != null) && (indicadorId.length == 1) && (indicadorId[0] != null) && (!indicadorId[0].equals("")) && (indicadorId[0].indexOf(",") < 0))
      {
        StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
        Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(indicadorId[0]));
        calculoIndicadoresForm.setIndicadorId(indicador.getIndicadorId());
        calculoIndicadoresForm.setNombreIndicador(indicador.getNombre());
        calculoIndicadoresForm.setAlcance(calculoIndicadoresForm.getAlcanceIndicador());
        strategosIndicadoresService.close();
      }
      calculoIndicadoresForm.setPorNombre(new Boolean(false));
    }
    else if ((!desdeIniciativa.booleanValue()) && (desdePlanes))
    {
      calculoIndicadoresForm.setOrigen(calculoIndicadoresForm.getOrigenPlanes());
      calculoIndicadoresForm.setAlcance(calculoIndicadoresForm.getAlcancePlan());
      
      StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
      if ((desdePlanes) && (planId != null) && (!planId.equals(""))) {
        plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));
      }
      calculoIndicadoresForm.setPlanId(new Long(planId));
      if (desdePerspectivas) {
        calculoIndicadoresForm.setPerspectivaId(new Long(perspectivaId));
      }
      strategosPlanesService.close();
    }
    else if (desdePerspectivas)
    {
      if ((perspectivaId != null) && (!perspectivaId.equals("")))
      {
        StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
        Perspectiva perspectiva = (Perspectiva)strategosPlanesService.load(Perspectiva.class, new Long(perspectivaId));
        plan = (Plan)strategosPlanesService.load(Plan.class, new Long(perspectiva.getPlanId().longValue()));
        strategosPlanesService.close();
      }
    }
    else if (desdeIniciativa.booleanValue())
    {
      calculoIndicadoresForm.setOrganizacionId(new Long(organizacionId));
      calculoIndicadoresForm.setIniciativaId(new Long(iniciativaId.longValue()));
      calculoIndicadoresForm.setClaseId(new Long(claseId));
      if ((indicadorId != null) && (indicadorId.length == 1) && (indicadorId[0] != null) && (!indicadorId[0].equals("")) && (indicadorId[0].indexOf(",") < 0))
      {
        StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
        Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(indicadorId[0]));
        calculoIndicadoresForm.setIndicadorId(indicador.getIndicadorId());
        calculoIndicadoresForm.setNombreIndicador(indicador.getNombre());
        strategosIndicadoresService.close();
      }
      calculoIndicadoresForm.setOrigen(calculoIndicadoresForm.getOrigenIniciativas());
      calculoIndicadoresForm.setAlcance(calculoIndicadoresForm.getAlcanceIniciativa());
    }
    calculoIndicadoresForm.setPeriodosCero(new Boolean(false));
    calculoIndicadoresForm.setOrganizacionId(Long.valueOf((String)request.getSession().getAttribute("organizacionId")));
    Calendar ahora = Calendar.getInstance();
    Integer ano = Integer.valueOf(ahora.get(1));
    if ((plan != null) && (plan.getAnoFinal().intValue() < ano.intValue())) {
      ano = plan.getAnoFinal();
    }
    calculoIndicadoresForm.setAno(ano.toString());
    calculoIndicadoresForm.setMesInicial(new Byte("1"));
    calculoIndicadoresForm.setMesFinal(new Byte("12"));
    calculoIndicadoresForm.setReportarErrores(new Boolean(true));
    calculoIndicadoresForm.setReportarIndicadores(new Boolean(true));
    calculoIndicadoresForm.setCalculado(new Boolean(false));
    if (plan != null)
    {
      calculoIndicadoresForm.setAnoInicio(plan.getAnoInicial().toString());
      calculoIndicadoresForm.setAnoFin(plan.getAnoFinal().toString());
    }
    else
    {
      Integer anoInicial = Integer.valueOf(ahora.get(1) - 20);
      Integer anoFinal = Integer.valueOf(ahora.get(1) + 20);
      calculoIndicadoresForm.setAnoInicio(anoInicial.toString());
      calculoIndicadoresForm.setAnoFin(anoFinal.toString());
    }
    return mapping.findForward(forward);
  }
}
