package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.reportes.model.Reporte.ReporteTipo;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoAtributo;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.ConfigurarVistaDatosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.xmldata.XmlControl;
import com.visiongc.commons.util.xmldata.XmlNodo;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.xml.sax.SAXException;

public final class GuardarConfiguracionVistaDatosAction
  extends VgcAction
{
  public static final String ACTION_KEY = "GuardarConfiguracionVistaDatosAction";
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    String forward = mapping.getParameter();
    
    ActionMessages messages = getMessages(request);
    
    ConfigurarVistaDatosForm configurarVistaDatosForm = (ConfigurarVistaDatosForm)form;
    Boolean cancelar = Boolean.valueOf(Boolean.parseBoolean(request.getParameter("cancelar")));
    
    StrategosReportesService reportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();
    if (cancelar.booleanValue())
    {
      reportesService.unlockObject(request.getSession().getId(), configurarVistaDatosForm.getReporteId());
      
      destruirPoolLocksUsoEdicion(request, reportesService);
      
      reportesService.close();
      
      return getForwardBack(request, 2, true);
    }
    boolean nuevo = false;
    
    Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
    
    Long reporteId = (request.getParameter("reporteId") != null) && (request.getParameter("reporteId") != "") ? new Long(request.getParameter("reporteId")) : null;
    Reporte reporte = null;
    if (reporteId != null) {
      reporte = (Reporte)reportesService.load(Reporte.class, reporteId);
    }
    if (reporte == null)
    {
      nuevo = true;
      reporte = new Reporte();
      
      Long organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));
      OrganizacionStrategos organizacion = (OrganizacionStrategos)reportesService.load(OrganizacionStrategos.class, organizacionId);
      reporte.setOrganizacion(organizacion);
      reporte.setOrganizacionId(organizacionId);
      reporte.setReporteId(Long.valueOf(0L));
      reporte.setTipo(Reporte.ReporteTipo.getTipoVistaDatos());
      reporte.setUsuario(usuario);
      reporte.setUsuarioId(usuario.getUsuarioId());
    }
    Boolean showTotalFilas = Boolean.valueOf(request.getParameter("showTotalFilas") != null ? Boolean.parseBoolean(request.getParameter("showTotalFilas")) : false);
    Boolean showTotalColumnas = Boolean.valueOf(request.getParameter("showTotalColumnas") != null ? Boolean.parseBoolean(request.getParameter("showTotalColumnas")) : false);
    configurarVistaDatosForm.setShowTotalFilas(showTotalFilas);
    configurarVistaDatosForm.setShowTotalColumnas(showTotalColumnas);
    
    reporte.setNombre(configurarVistaDatosForm.getNombre());
    reporte.setDescripcion(configurarVistaDatosForm.getDescripcion());
    reporte.setPublico(configurarVistaDatosForm.getPublico());
    reporte.setCorte(configurarVistaDatosForm.getCorte());
    reporte.setConfiguracion(guardarConfiguracion(configurarVistaDatosForm, request));
    
    int respuesta = reportesService.save(reporte, usuario);
    if (respuesta == 10000)
    {
      reportesService.unlockObject(request.getSession().getId(), reporte.getReporteId());
      forward = "exito";
      if (nuevo) {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
      } else {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
      }
    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }
    reportesService.close();
    
    saveMessages(request, messages);
    
    return mapping.findForward(forward);
  }
  
  private String verificarValoresNulos(String valor)
  {
    return valor == null ? "" : valor;
  }
  
  private String verificarValoresNulos(Byte valor)
  {
    return valor == null ? "" : valor.toString();
  }
  
  public String guardarConfiguracion(ConfigurarVistaDatosForm configurarVistaDatosForm, HttpServletRequest request)
    throws ParserConfigurationException, SAXException, IOException
  {
    XmlNodo nodoVistaDatos = new XmlNodo();
    XmlControl xmlControl = new XmlControl();
    
    nodoVistaDatos.setValorAtributo("nombre", "configuracion vista datos");
    nodoVistaDatos.setValorAtributo("id", "vistadatos" + ((Usuario)request.getSession().getAttribute("usuario")).getUsuarioId().toString());
    nodoVistaDatos.setValorAtributo("textoMiembrosVariable", verificarValoresNulos(configurarVistaDatosForm.getTextoMiembrosVariable()));
    nodoVistaDatos.setValorAtributo("textoMiembrosTiempo", verificarValoresNulos(configurarVistaDatosForm.getTextoMiembrosTiempo()));
    nodoVistaDatos.setValorAtributo("textoMiembrosIndicador", verificarValoresNulos(configurarVistaDatosForm.getTextoMiembrosIndicador()));
    nodoVistaDatos.setValorAtributo("textoMiembrosOrganizacion", verificarValoresNulos(configurarVistaDatosForm.getTextoMiembrosOrganizacion()));
    nodoVistaDatos.setValorAtributo("textoMiembrosPlan", verificarValoresNulos(configurarVistaDatosForm.getTextoMiembrosPlan()));
    nodoVistaDatos.setValorAtributo("textoMiembrosAtributo", verificarValoresNulos(configurarVistaDatosForm.getTextoMiembrosAtributo()));
    nodoVistaDatos.setValorAtributo("filasId", verificarValoresNulos(configurarVistaDatosForm.getFilasId()));
    nodoVistaDatos.setValorAtributo("nombreFilas", verificarValoresNulos(configurarVistaDatosForm.getNombreFilas()));
    nodoVistaDatos.setValorAtributo("columnasId", verificarValoresNulos(configurarVistaDatosForm.getColumnasId()));
    nodoVistaDatos.setValorAtributo("nombreColumnas", verificarValoresNulos(configurarVistaDatosForm.getNombreColumnas()));
    nodoVistaDatos.setValorAtributo("frecuencia", verificarValoresNulos(configurarVistaDatosForm.getFrecuencia()));
    nodoVistaDatos.setValorAtributo("nombreFrecuencia", verificarValoresNulos(configurarVistaDatosForm.getNombreFrecuencia()));
    nodoVistaDatos.setValorAtributo("valorSelector1", verificarValoresNulos(configurarVistaDatosForm.getValorSelector1()));
    nodoVistaDatos.setValorAtributo("valorSelector2", verificarValoresNulos(configurarVistaDatosForm.getValorSelector2()));
    nodoVistaDatos.setValorAtributo("valorSelector3", verificarValoresNulos(configurarVistaDatosForm.getValorSelector3()));
    nodoVistaDatos.setValorAtributo("valorSelector4", verificarValoresNulos(configurarVistaDatosForm.getValorSelector4()));
    nodoVistaDatos.setValorAtributo("valorSelectorTiempoDesde", verificarValoresNulos(configurarVistaDatosForm.getValorSelectorTiempoDesde()));
    nodoVistaDatos.setValorAtributo("valorSelectorTiempoHasta", verificarValoresNulos(configurarVistaDatosForm.getValorSelectorTiempoHasta()));
    nodoVistaDatos.setValorAtributo("textoDimensiones", verificarValoresNulos(configurarVistaDatosForm.getTextoDimensiones()));
    nodoVistaDatos.setValorAtributo("textoSelectores", verificarValoresNulos(configurarVistaDatosForm.getTextoSelectores()));
    nodoVistaDatos.setValorAtributo("showTotalFilas", verificarValoresNulos(configurarVistaDatosForm.getShowTotalFilas().booleanValue() ? "true" : "false"));
    nodoVistaDatos.setValorAtributo("showTotalColumnas", verificarValoresNulos(configurarVistaDatosForm.getShowTotalColumnas().booleanValue() ? "true" : "false"));
    nodoVistaDatos.setValorAtributo("acumularPeriodos", verificarValoresNulos((configurarVistaDatosForm.getAcumularPeriodos() != null) && (configurarVistaDatosForm.getAcumularPeriodos().booleanValue()) ? "true" : "false"));
    nodoVistaDatos.setValorAtributo("atributos", getAtributos(configurarVistaDatosForm, request.getParameter("xmlAtributos"), getUsuarioConectado(request)));
    
    return xmlControl.buildXml(nodoVistaDatos);
  }
  
  private String getAtributos(ConfigurarVistaDatosForm configurarVistaDatosForm, String xmlAtributos, Usuario usuario)
  {
    List<TipoAtributo> atributos = new ConfigurarVistaDatosAction().buscarAtributos(configurarVistaDatosForm, xmlAtributos, usuario);
    
    String valorAtributo = "";
    for (int f = 0; f < atributos.size(); f++)
    {
      TipoAtributo tipoAtributo = (TipoAtributo)atributos.get(f);
      
      valorAtributo = valorAtributo + tipoAtributo.getOrden() + ",";
      valorAtributo = valorAtributo + tipoAtributo.getTipoAtributoId().toString() + ",";
      valorAtributo = valorAtributo + tipoAtributo.getNombre() + ",";
      valorAtributo = valorAtributo + (tipoAtributo.getVisible().booleanValue() ? "1" : "0") + ",";
      valorAtributo = valorAtributo + tipoAtributo.getAncho().toString() + ",";
      valorAtributo = valorAtributo + (tipoAtributo.getAgrupar().booleanValue() ? "1" : "0") + "|";
    }
    valorAtributo = valorAtributo.substring(0, valorAtributo.length() - 1);
    
    configurarVistaDatosForm.setAtributos(atributos);
    
    return valorAtributo;
  }
}
