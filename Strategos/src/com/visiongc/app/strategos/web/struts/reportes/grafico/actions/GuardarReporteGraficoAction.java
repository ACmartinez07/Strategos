package com.visiongc.app.strategos.web.struts.reportes.grafico.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.reportes.StrategosReportesGraficoService;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.reportes.model.Reporte.ReporteTipo;
import com.visiongc.app.strategos.reportes.model.ReporteGrafico;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoAtributo;
import com.visiongc.app.strategos.web.struts.reportes.grafico.forms.ConfigurarReporteGraficoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoValorNombre;
import com.visiongc.commons.util.xmldata.XmlControl;
import com.visiongc.commons.util.xmldata.XmlNodo;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

public final class GuardarReporteGraficoAction
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
    
    ConfigurarReporteGraficoForm configurarReporteGraficoForm = (ConfigurarReporteGraficoForm)form;
    Boolean cancelar = Boolean.valueOf(Boolean.parseBoolean(request.getParameter("cancelar")));
    
    StrategosReportesGraficoService reportesService = StrategosServiceFactory.getInstance().openStrategosReportesGraficoService();
    
    
    if (cancelar.booleanValue())
    {
      reportesService.unlockObject(request.getSession().getId(), configurarReporteGraficoForm.getReporteId());
      
      destruirPoolLocksUsoEdicion(request, reportesService);
      
      reportesService.close();
      
      return getForwardBack(request, 2, true);
    }
    boolean nuevo = false;
    
    Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
    
    Long reporteId = (request.getParameter("reporteId") != null) && (request.getParameter("reporteId") != "") ? new Long(request.getParameter("reporteId")) : null;
    ReporteGrafico reporte = null;
    if (reporteId != null) {
      reporte = (ReporteGrafico)reportesService.load(ReporteGrafico.class, reporteId);
    }
    if (reporte == null)
    {
      nuevo = true;
      reporte = new ReporteGrafico();
      
      Long organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));
      OrganizacionStrategos organizacion = (OrganizacionStrategos)reportesService.load(OrganizacionStrategos.class, organizacionId);
      reporte.setReporteId(Long.valueOf(0L));
      reporte.setTipo(Reporte.ReporteTipo.getTipoVistaDatos());
      reporte.setUsuario(usuario);
      reporte.setUsuarioId(usuario.getUsuarioId());
    }
    Boolean showTotalFilas = Boolean.valueOf(request.getParameter("showTotalFilas") != null ? Boolean.parseBoolean(request.getParameter("showTotalFilas")) : false);
    Boolean showTotalColumnas = Boolean.valueOf(request.getParameter("showTotalColumnas") != null ? Boolean.parseBoolean(request.getParameter("showTotalColumnas")) : false);
    configurarReporteGraficoForm.setShowTotalFilas(showTotalFilas);
    configurarReporteGraficoForm.setShowTotalColumnas(showTotalColumnas);
    
    reporte.setNombre(configurarReporteGraficoForm.getNombre());
    reporte.setDescripcion(configurarReporteGraficoForm.getDescripcion());
    reporte.setPublico(configurarReporteGraficoForm.getPublico());
    //reporte.setConfiguracion(obtenerDatos(configurarReporteGraficoForm, request));
    reporte.setConfiguracion(guardarConfiguracion(configurarReporteGraficoForm, request));
    reporte.setIndicadores(obtenerIndicadores(configurarReporteGraficoForm, request));
    reporte.setOrganizaciones(obtenerOrganizaciones(configurarReporteGraficoForm, request));
    reporte.setFecha(new Date());
    reporte.setTiempo(obtenerSerie(configurarReporteGraficoForm));
    reporte.setPeriodoIni(configurarReporteGraficoForm.getAnoInicial() + "/"+ configurarReporteGraficoForm.getPeriodoInicial());
    reporte.setPeriodoFin(configurarReporteGraficoForm.getAnoFinal() + "/" + configurarReporteGraficoForm.getPeriodoFinal());
    reporte.setReporteId(null);
    
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
  
  public String obtenerSerie(ConfigurarReporteGraficoForm configurarReporteGraficoForm) {
	  
	  String datos="";
	  
	  List<ObjetoValorNombre> objetos= new ArrayList();
	  objetos= configurarReporteGraficoForm.getMiembrosVariable();
	  
	  if(objetos != null) {
		  for(ObjetoValorNombre obj: objetos) {
			  datos+=obj.getValor()+",";	  
		  }
	  }
	  
	  
	  return datos;
  }
 
  
  public String obtenerIndicadores(ConfigurarReporteGraficoForm configurarReporteGraficoForm, HttpServletRequest request) {
	  
	  String datos="";
	  
	  List<ObjetoValorNombre> objetos= new ArrayList();
	  objetos= configurarReporteGraficoForm.getMiembrosIndicador();
	  
	  if(objetos != null) {
		  for(ObjetoValorNombre obj: objetos) {
			  datos+=obj.getValor()+",";	  
		  }
	  }
	  
	  return datos;
  
  }
  
  public String obtenerOrganizaciones(ConfigurarReporteGraficoForm configurarReporteGraficoForm, HttpServletRequest request) {
	  
	  String datos="";
	  
	  List<ObjetoValorNombre> objetos= new ArrayList();
	  objetos= configurarReporteGraficoForm.getMiembrosOrganizacion();
	  
	  if(objetos != null) {
		  for(ObjetoValorNombre obj: objetos) {
			  datos+=obj.getValor()+",";	  
		  }
	  }
	  
	  return datos;
  
  }
  
  public String guardarConfiguracion(ConfigurarReporteGraficoForm configurarVistaDatosForm, HttpServletRequest request)
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
  
  private String getAtributos(ConfigurarReporteGraficoForm configurarVistaDatosForm, String xmlAtributos, Usuario usuario)
  {
    List<TipoAtributo> atributos = new ConfigurarReporteGraficoAction().buscarAtributos(configurarVistaDatosForm, xmlAtributos, usuario);
    
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
