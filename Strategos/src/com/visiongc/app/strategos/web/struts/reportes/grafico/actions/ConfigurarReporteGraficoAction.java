package com.visiongc.app.strategos.web.struts.reportes.grafico.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.reportes.StrategosReportesGraficoService;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.reportes.model.ReporteGrafico;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoAtributo;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoDimension;
import com.visiongc.app.strategos.web.struts.reportes.grafico.forms.ConfigurarReporteGraficoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoValorNombre;
import com.visiongc.commons.util.xmldata.XmlControl;
import com.visiongc.commons.util.xmldata.XmlNodo;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.util.PermisologiaUsuario;
import com.visiongc.framework.web.struts.alertas.actions.GestionarAlertasAction;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class ConfigurarReporteGraficoAction
  extends VgcAction
{
  public static final String ACTION_KEY = "ConfigurarVistaDatosAction";
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    getBarraAreas(request, "strategos").setBotonSeleccionado("reporteGrafico");
    
    String forward = mapping.getParameter();
    ActionMessages messages = getMessages(request);
    request.getSession().setAttribute("alerta", new GestionarAlertasAction().getAlerta(getUsuarioConectado(request)));
    
    ConfigurarReporteGraficoForm configurarReporteGraficoForm = (ConfigurarReporteGraficoForm)form;
    configurarReporteGraficoForm.clear();
    
    cargarConfiguracion(configurarReporteGraficoForm, request);
    
    boolean verForm = getPermisologiaUsuario(request).tienePermiso("VISTA_DATOS_VIEW");
    boolean editarForm = getPermisologiaUsuario(request).tienePermiso("VISTA_DATOS_EDIT");
    if ((verForm) && (!editarForm))
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
      configurarReporteGraficoForm.setBloqueado(Boolean.valueOf(true));
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
  
  public void cargarConfiguracion(ConfigurarReporteGraficoForm configurarReporteGraficoForm, HttpServletRequest request)
  {
    XmlNodo nodoVistaDatos = getConfiguracionVistaDatos(configurarReporteGraficoForm, request);
    if (nodoVistaDatos != null)
    {
      configurarReporteGraficoForm.setTextoMiembrosVariable(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoMiembrosVariable")));
      configurarReporteGraficoForm.setTextoMiembrosIndicador(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoMiembrosIndicador")));
      configurarReporteGraficoForm.setTextoMiembrosOrganizacion(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoMiembrosOrganizacion")));
      configurarReporteGraficoForm.setTextoMiembrosPlan(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoMiembrosPlan")));
      configurarReporteGraficoForm.setTextoMiembrosAtributo(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoMiembrosAtributo")));
      configurarReporteGraficoForm.setFilasId(getValoresNulosString(nodoVistaDatos.getValorAtributo("filasId")));
      configurarReporteGraficoForm.setNombreFilas(getValoresNulosString(nodoVistaDatos.getValorAtributo("nombreFilas")));
      configurarReporteGraficoForm.setColumnasId(getValoresNulosString(nodoVistaDatos.getValorAtributo("columnasId")));
      configurarReporteGraficoForm.setNombreColumnas(getValoresNulosString(nodoVistaDatos.getValorAtributo("nombreColumnas")));
      configurarReporteGraficoForm.setFrecuencia(getValoresNulosByte(nodoVistaDatos.getValorAtributo("frecuencia")));
      configurarReporteGraficoForm.setNombreFrecuencia(getValoresNulosString(nodoVistaDatos.getValorAtributo("nombreFrecuencia")));
      configurarReporteGraficoForm.setValorSelector1(getValoresNulosString(nodoVistaDatos.getValorAtributo("valorSelector1")));
      configurarReporteGraficoForm.setValorSelector3(getValoresNulosString(nodoVistaDatos.getValorAtributo("valorSelector3")));
      configurarReporteGraficoForm.setValorSelector4(getValoresNulosString(nodoVistaDatos.getValorAtributo("valorSelector4")));
      configurarReporteGraficoForm.setValorSelectorTiempoDesde(getValoresNulosString(nodoVistaDatos.getValorAtributo("valorSelectorTiempoDesde")));
      configurarReporteGraficoForm.setValorSelectorTiempoHasta(getValoresNulosString(nodoVistaDatos.getValorAtributo("valorSelectorTiempoHasta")));
      configurarReporteGraficoForm.setTextoDimensiones(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoDimensiones")));
      configurarReporteGraficoForm.setTextoSelectores(getValoresNulosString(nodoVistaDatos.getValorAtributo("textoSelectores")));
      configurarReporteGraficoForm.setShowTotalFilas(Boolean.valueOf(getValoresNulosString(nodoVistaDatos.getValorAtributo("showTotalFilas")) != "" ? Boolean.parseBoolean(getValoresNulosString(nodoVistaDatos.getValorAtributo("showTotalFilas"))) : false));
      configurarReporteGraficoForm.setShowTotalColumnas(Boolean.valueOf(getValoresNulosString(nodoVistaDatos.getValorAtributo("showTotalColumnas")) != "" ? Boolean.parseBoolean(getValoresNulosString(nodoVistaDatos.getValorAtributo("showTotalColumnas"))) : false));
      configurarReporteGraficoForm.setAcumularPeriodos(Boolean.valueOf(getValoresNulosString(nodoVistaDatos.getValorAtributo("acumularPeriodos")) != "" ? Boolean.parseBoolean(getValoresNulosString(nodoVistaDatos.getValorAtributo("acumularPeriodos"))) : false));
      configurarReporteGraficoForm.setAtributos(buscarAtributos(configurarReporteGraficoForm, "", getUsuarioConectado(request)));
    }
    else
    {
      configurarReporteGraficoForm.setFrecuencia(Frecuencia.getFrecuenciaMensual());
    }
    configurarReporteGraficoForm.setFrecuencias(Frecuencia.getFrecuencias());
   
    Calendar cal= Calendar.getInstance();
    int year= cal.get(Calendar.YEAR);
    
    if(configurarReporteGraficoForm.getAnoInicial() == null) {
    	configurarReporteGraficoForm.setAnoInicial(year);
    }
    
    if(configurarReporteGraficoForm.getAnoFinal() == null) {
    	configurarReporteGraficoForm.setAnoFinal(year);
    }
    
    int numeroMaximoPeriodos = 0;

    if ((configurarReporteGraficoForm.getAnoInicial().intValue() % 4 == 0) && (configurarReporteGraficoForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
      numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(configurarReporteGraficoForm.getFrecuencia().byteValue(), configurarReporteGraficoForm.getAnoInicial().intValue());
    else if ((configurarReporteGraficoForm.getAnoFinal().intValue() % 4 == 0) && (configurarReporteGraficoForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
      numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(configurarReporteGraficoForm.getFrecuencia().byteValue(), configurarReporteGraficoForm.getAnoFinal().intValue());
    else {
      numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(configurarReporteGraficoForm.getFrecuencia().byteValue(), configurarReporteGraficoForm.getAnoInicial().intValue());
    }
    
    if (configurarReporteGraficoForm.getPeriodoFinal() == null || configurarReporteGraficoForm.getPeriodoFinal() != null && configurarReporteGraficoForm.getPeriodoFinal().intValue() > numeroMaximoPeriodos) {
    	configurarReporteGraficoForm.setPeriodoFinal(new Integer(numeroMaximoPeriodos));
    }

    if (configurarReporteGraficoForm.getPeriodoInicial() == null || configurarReporteGraficoForm.getPeriodoInicial() != null && configurarReporteGraficoForm.getPeriodoInicial().intValue() > numeroMaximoPeriodos) {
        configurarReporteGraficoForm.setPeriodoInicial(new Integer(1));
    }
        
    
    configurarReporteGraficoForm.setListaAnos(getListaAnos(new Integer(Calendar.getInstance().get(1))));
    configurarReporteGraficoForm.setListaPeriodos(getListaPeriodos(new Integer(numeroMaximoPeriodos)));
    
  }
  
  public List<TipoAtributo> buscarAtributos(ConfigurarReporteGraficoForm configurarReporteGraficoForm, String xmlAtributos, Usuario usuario)
  {
    List<TipoAtributo> listaAtributos = new ArrayList();
    if ((configurarReporteGraficoForm.getConfiguracion() != null) && (!configurarReporteGraficoForm.getConfiguracion().equals("")))
    {
      XmlControl xmlControl = new XmlControl();
      XmlNodo nodo = xmlControl.readXml(configurarReporteGraficoForm.getConfiguracion());
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
    if ((configurarReporteGraficoForm.getTextoSelectores() != null) && (!configurarReporteGraficoForm.getTextoSelectores().equals("")))
    {
      String[] selectores = configurarReporteGraficoForm.getTextoSelectores().split("\\|");
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
  
  private XmlNodo getConfiguracionVistaDatos(ConfigurarReporteGraficoForm configurarReporteGraficoForm, HttpServletRequest request)
  {
    XmlNodo nodo = null;
    Long reporteId = (request.getParameter("reporteId") != null) && (request.getParameter("reporteId") != "") ? new Long(request.getParameter("reporteId")) : null;
    String configuracion = (request.getParameter("configuracion") != null) && (request.getParameter("configuracion") != "") ? request.getParameter("configuracion") : null;
    String nombre = (request.getParameter("nombre") != null) && (request.getParameter("nombre") != "") ? request.getParameter("nombre") : null;
   
    
    StrategosReportesGraficoService reportesGraficoService = StrategosServiceFactory.getInstance().openStrategosReportesGraficoService();
    String xmlConfig = "";
    if (reporteId != null)
    {
    	ReporteGrafico reporte= reportesGraficoService.obtenerReporte(new Long(reporteId));
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
        
        configurarReporteGraficoForm.setNombre(nombre);
        configurarReporteGraficoForm.setDescripcion(reporte.getDescripcion());
        configurarReporteGraficoForm.setPublico(reporte.getPublico());
        configurarReporteGraficoForm.setConfiguracion(configuracion);
        configurarReporteGraficoForm.setAnoInicial(new Integer(reporte.getPeriodoIni().substring(0, 4)));
        configurarReporteGraficoForm.setPeriodoInicial(new Integer(reporte.getPeriodoIni().substring(5)));
        configurarReporteGraficoForm.setAnoFinal(new Integer(reporte.getPeriodoFin().substring(0, 4)));
        configurarReporteGraficoForm.setPeriodoFinal(new Integer(reporte.getPeriodoFin().substring(5)));
                     
                
      }
      else if ((configuracion != null) && (configuracion != ""))
      {
        xmlConfig = configuracion;
        XmlControl xmlControl = new XmlControl();
        nodo = xmlControl.readXml(xmlConfig);
        
        configurarReporteGraficoForm.setNombre(nombre);
        configurarReporteGraficoForm.setConfiguracion(configuracion);
        
      }
    }
    else if ((configuracion != null) && (configuracion != ""))
    {
      xmlConfig = configuracion;
      XmlControl xmlControl = new XmlControl();
      nodo = xmlControl.readXml(xmlConfig);
      
      configurarReporteGraficoForm.setNombre(nombre);
      configurarReporteGraficoForm.setConfiguracion(configuracion);
      
    }
    reportesGraficoService.close();
    
    return nodo;
  }
  
  private List getListaAnos(Integer anoBase)
  {
    List listaAnos = new ArrayList();

    ObjetoValorNombre elementoAno = new ObjetoValorNombre();
    for (int ano = anoBase.intValue() - 10; ano <= anoBase.intValue() + 10; ano++) {
      elementoAno = new ObjetoValorNombre();
      elementoAno.setNombre(String.valueOf(ano));
      elementoAno.setValor(String.valueOf(ano));

      listaAnos.add(elementoAno);
    }

    return listaAnos;
  }

  private List getListaPeriodos(Integer periodoFinal)
  {
    List listaPeriodos = new ArrayList();

    ObjetoValorNombre elementoPeriodo = new ObjetoValorNombre();
    for (int periodo = 1; periodo <= periodoFinal.intValue(); periodo++) {
      elementoPeriodo = new ObjetoValorNombre();
      elementoPeriodo.setNombre(String.valueOf(periodo));
      elementoPeriodo.setValor(String.valueOf(periodo));

      listaPeriodos.add(elementoPeriodo);
    }

    return listaPeriodos;
  }

  private List getListaAnosPeriodos(Integer periodoInicial, Integer anoInicial, Integer periodoFinal, Integer anoFinal, int numeroMaximoPeriodos) {
    List listaAnosPeriodos = new ArrayList();
    int inicio = 1;
    int fin = 1;

    for (int ano = anoInicial.intValue(); ano <= anoFinal.intValue(); ano++) {
      if (anoInicial.intValue() != anoFinal.intValue()) {
        if (ano == anoInicial.intValue()) {
          inicio = periodoInicial.intValue();
          fin = numeroMaximoPeriodos;
        } else if (ano == anoFinal.intValue()) {
          inicio = 1;
          fin = periodoFinal.intValue();
        } else {
          inicio = 1;
          fin = numeroMaximoPeriodos;
        }
      } else {
        inicio = periodoInicial.intValue();
        fin = periodoFinal.intValue();
      }
      for (int periodo = inicio; periodo <= fin; periodo++) {
        ObjetoValorNombre elementoAnoPeriodo = new ObjetoValorNombre();
        elementoAnoPeriodo.setNombre(String.valueOf(periodo) + '/' + String.valueOf(ano));
        elementoAnoPeriodo.setValor(String.valueOf(periodo) + '_' + String.valueOf(ano));

        listaAnosPeriodos.add(elementoAnoPeriodo);
      }
    }

    return listaAnosPeriodos;
  }
  
}
