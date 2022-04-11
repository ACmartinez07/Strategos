package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoAtributo;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoDimension;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.ConfigurarVistaDatosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.xmldata.XmlControl;
import com.visiongc.commons.util.xmldata.XmlNodo;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.util.PermisologiaUsuario;
import com.visiongc.framework.web.struts.alertas.actions.GestionarAlertasAction;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class ConfigurarVistaDatosAction
  extends VgcAction
{
  public static final String ACTION_KEY = "ConfigurarVistaDatosAction";
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    getBarraAreas(request, "strategos").setBotonSeleccionado("vistasDatos");
    
    String forward = mapping.getParameter();
    ActionMessages messages = getMessages(request);
    request.getSession().setAttribute("alerta", new GestionarAlertasAction().getAlerta(getUsuarioConectado(request)));
    
    ConfigurarVistaDatosForm configurarVistaDatosForm = (ConfigurarVistaDatosForm)form;
    configurarVistaDatosForm.clear();
    
    cargarConfiguracion(configurarVistaDatosForm, request);
    
    boolean verForm = getPermisologiaUsuario(request).tienePermiso("VISTA_DATOS_VIEW");
    boolean editarForm = getPermisologiaUsuario(request).tienePermiso("VISTA_DATOS_EDIT");
    if ((verForm) && (!editarForm))
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
      configurarVistaDatosForm.setBloqueado(Boolean.valueOf(true));
    }
    else if ((!verForm) && (!editarForm))
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));
    }
    saveMessages(request, messages);
    
    return mapping.findForward(forward);
  }
  
  private String getValoresNulosString(String valor)
  {
    return valor == null ? "" : valor;
  }
  
  private Byte getValoresNulosByte(String valor)
  {
    return (valor == null) || (valor.equals("")) ? Frecuencia.getFrecuenciaMensual() : new Byte(valor);
  }
  
  public void cargarConfiguracion(ConfigurarVistaDatosForm configurarVistaDatosForm, HttpServletRequest request)
  {
    XmlNodo nodoVistaDatos = getConfiguracionVistaDatos(configurarVistaDatosForm, request);
    if (nodoVistaDatos != null)
    {
      configurarVistaDatosForm.setTextoMiembrosVariable(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoMiembrosVariable")));
      configurarVistaDatosForm.setTextoMiembrosTiempo(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoMiembrosTiempo")));
      configurarVistaDatosForm.setTextoMiembrosIndicador(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoMiembrosIndicador")));
      configurarVistaDatosForm.setTextoMiembrosOrganizacion(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoMiembrosOrganizacion")));
      configurarVistaDatosForm.setTextoMiembrosPlan(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoMiembrosPlan")));
      configurarVistaDatosForm.setTextoMiembrosAtributo(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoMiembrosAtributo")));
      configurarVistaDatosForm.setFilasId(getValoresNulosString(nodoVistaDatos.getValorAtributo("filasId")));
      configurarVistaDatosForm.setNombreFilas(getValoresNulosString(nodoVistaDatos.getValorAtributo("nombreFilas")));
      configurarVistaDatosForm.setColumnasId(getValoresNulosString(nodoVistaDatos.getValorAtributo("columnasId")));
      configurarVistaDatosForm.setNombreColumnas(getValoresNulosString(nodoVistaDatos.getValorAtributo("nombreColumnas")));
      configurarVistaDatosForm.setFrecuencia(getValoresNulosByte(nodoVistaDatos.getValorAtributo("frecuencia")));
      configurarVistaDatosForm.setNombreFrecuencia(getValoresNulosString(nodoVistaDatos.getValorAtributo("nombreFrecuencia")));
      configurarVistaDatosForm.setValorSelector1(getValoresNulosString(nodoVistaDatos.getValorAtributo("valorSelector1")));
      configurarVistaDatosForm.setValorSelector2(getValoresNulosString(nodoVistaDatos.getValorAtributo("valorSelector2")));
      configurarVistaDatosForm.setValorSelector3(getValoresNulosString(nodoVistaDatos.getValorAtributo("valorSelector3")));
      configurarVistaDatosForm.setValorSelector4(getValoresNulosString(nodoVistaDatos.getValorAtributo("valorSelector4")));
      configurarVistaDatosForm.setValorSelectorTiempoDesde(getValoresNulosString(nodoVistaDatos.getValorAtributo("valorSelectorTiempoDesde")));
      configurarVistaDatosForm.setValorSelectorTiempoHasta(getValoresNulosString(nodoVistaDatos.getValorAtributo("valorSelectorTiempoHasta")));
      configurarVistaDatosForm.setTextoDimensiones(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoDimensiones")));
      configurarVistaDatosForm.setTextoSelectores(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoSelectores")));
      configurarVistaDatosForm.setShowTotalFilas(Boolean.valueOf(getValoresNulosString(nodoVistaDatos.getValorAtributo("showTotalFilas")) != "" ? Boolean.parseBoolean(getValoresNulosString(nodoVistaDatos.getValorAtributo("showTotalFilas"))) : false));
      configurarVistaDatosForm.setShowTotalColumnas(Boolean.valueOf(getValoresNulosString(nodoVistaDatos.getValorAtributo("showTotalColumnas")) != "" ? Boolean.parseBoolean(getValoresNulosString(nodoVistaDatos.getValorAtributo("showTotalColumnas"))) : false));
      configurarVistaDatosForm.setAcumularPeriodos(Boolean.valueOf(getValoresNulosString(nodoVistaDatos.getValorAtributo("acumularPeriodos")) != "" ? Boolean.parseBoolean(getValoresNulosString(nodoVistaDatos.getValorAtributo("acumularPeriodos"))) : false));
      configurarVistaDatosForm.setAtributos(buscarAtributos(configurarVistaDatosForm, "", getUsuarioConectado(request)));
    }
    else
    {
      configurarVistaDatosForm.setFrecuencia(Frecuencia.getFrecuenciaMensual());
    }
    configurarVistaDatosForm.setFrecuencias(Frecuencia.getFrecuencias());
  }
  
  public List<TipoAtributo> buscarAtributos(ConfigurarVistaDatosForm configurarVistaDatosForm, String xmlAtributos, Usuario usuario)
  {
    List<TipoAtributo> listaAtributos = new ArrayList();
    if ((configurarVistaDatosForm.getConfiguracion() != null) && (!configurarVistaDatosForm.getConfiguracion().equals("")))
    {
      XmlControl xmlControl = new XmlControl();
      XmlNodo nodo = xmlControl.readXml(configurarVistaDatosForm.getConfiguracion());
      String atributos = (xmlAtributos != null) && (!xmlAtributos.equals("")) ? xmlAtributos : getValoresNulosString(nodo.getValorAtributo("atributos"));
      if (!atributos.equals(""))
      {
        atributos = atributos.replace("*sepRow*", "|");
        String[] tipos = atributos.split("\\|");
        for (int i = 0; i < tipos.length; i++)
        {
          String[] campos = tipos[i].split(",");
          if (campos.length == 6)
          {
            TipoAtributo columna = new TipoAtributo();
            columna.setOrden(campos[0]);
            columna.setTipoAtributoId(Byte.valueOf(Byte.parseByte(campos[1])));
            columna.setNombre(campos[2]);
            columna.setVisible(Boolean.valueOf(campos[3].equals("1")));
            columna.setAncho(campos[4]);
            columna.setAgrupar(Boolean.valueOf(campos[5].equals("1")));
            
            listaAtributos.add(columna);
          }
          else
          {
            listaAtributos = TipoAtributo.getTiposAtributos();
            break;
          }
        }
      }
      else
      {
        listaAtributos = TipoAtributo.getTiposAtributos();
      }
    }
    else
    {
      listaAtributos = TipoAtributo.getTiposAtributos();
    }
    if ((configurarVistaDatosForm.getTextoSelectores() != null) && (!configurarVistaDatosForm.getTextoSelectores().equals("")))
    {
      String[] selectores = configurarVistaDatosForm.getTextoSelectores().split("\\|");
      boolean haySerie = false;
      for (int i = 0; i < selectores.length; i++) {
        if (selectores[i].equals(TipoDimension.getTipoDimensionVariable().toString()))
        {
          haySerie = true;
          break;
        }
      }
      if (haySerie) {
        for (int f = 0; f < listaAtributos.size(); f++)
        {
          TipoAtributo tipoAtributo = (TipoAtributo)listaAtributos.get(f);
          if (tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoSerie().byteValue())
          {
            listaAtributos.remove(tipoAtributo);
            break;
          }
        }
      }
    }
    return listaAtributos;
  }
  
  private XmlNodo getConfiguracionVistaDatos(ConfigurarVistaDatosForm configurarVistaDatosForm, HttpServletRequest request)
  {
    XmlNodo nodo = null;
    Long reporteId = (request.getParameter("reporteId") != null) && (request.getParameter("reporteId") != "") ? new Long(request.getParameter("reporteId")) : null;
    String configuracion = (request.getParameter("configuracion") != null) && (request.getParameter("configuracion") != "") ? request.getParameter("configuracion") : null;
    String nombre = (request.getParameter("nombre") != null) && (request.getParameter("nombre") != "") ? request.getParameter("nombre") : null;
    Byte corte = (request.getParameter("corte") != null) && (request.getParameter("corte") != "") ? new Byte(request.getParameter("corte")) : null;
    
    StrategosReportesService reportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();
    String xmlConfig = "";
    if (reporteId != null)
    {
      Reporte reporte = (Reporte)reportesService.load(Reporte.class, reporteId);
      if (reporte != null)
      {
        if ((configuracion != null) && (configuracion != ""))
        {
          xmlConfig = configuracion;
        }
        else
        {
          xmlConfig = reporte.getConfiguracion();
          nombre = reporte.getNombre();
          configuracion = reporte.getConfiguracion();
        }
        XmlControl xmlControl = new XmlControl();
        
        nodo = xmlControl.readXml(xmlConfig);
        
        configurarVistaDatosForm.setNombre(nombre);
        configurarVistaDatosForm.setDescripcion(reporte.getDescripcion());
        configurarVistaDatosForm.setPublico(reporte.getPublico());
        configurarVistaDatosForm.setCorte(reporte.getCorte());
        configurarVistaDatosForm.setConfiguracion(configuracion);
      }
      else if ((configuracion != null) && (configuracion != ""))
      {
        xmlConfig = configuracion;
        XmlControl xmlControl = new XmlControl();
        nodo = xmlControl.readXml(xmlConfig);
        
        configurarVistaDatosForm.setNombre(nombre);
        configurarVistaDatosForm.setConfiguracion(configuracion);
        configurarVistaDatosForm.setCorte(corte);
      }
    }
    else if ((configuracion != null) && (configuracion != ""))
    {
      xmlConfig = configuracion;
      XmlControl xmlControl = new XmlControl();
      nodo = xmlControl.readXml(xmlConfig);
      
      configurarVistaDatosForm.setNombre(nombre);
      configurarVistaDatosForm.setConfiguracion(configuracion);
      configurarVistaDatosForm.setCorte(corte);
    }
    reportesService.close();
    
    return nodo;
  }
}
