package com.visiongc.app.strategos.web.struts.reportes.grafico.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.vistasdatos.StrategosVistasDatosService;
import com.visiongc.app.strategos.vistasdatos.model.util.DatoCelda;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoAtributo;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoDimension;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoVariable;
import com.visiongc.app.strategos.web.struts.reportes.grafico.forms.ConfigurarReporteGraficoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoValorNombre;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.StringUtil;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

public final class MostrarReporteGraficoAction
  extends VgcAction
{
  public static final String ACTION_KEY = "MostrarVistaDatosAction";
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    String forward = mapping.getParameter();
    
    ActionMessages messages = getMessages(request);
    
    ConfigurarReporteGraficoForm configurarVistaDatosForm = (ConfigurarReporteGraficoForm)form;
    
    Long reporteId = (request.getParameter("reporteId") != null) && (request.getParameter("reporteId") != "") ? new Long(request.getParameter("reporteId")) : null;
    Byte source = (request.getParameter("source") != null) && (request.getParameter("source") != "") ? new Byte(request.getParameter("source")) : null;
    if (source == null)
    {
      if (reporteId == null)
      {
        configurarVistaDatosForm.setSource(Byte.valueOf(ConfigurarReporteGraficoForm.SourceType.getSourceEditar()));
        configurarVistaDatosForm.setConfiguracion(new GuardarReporteGraficoAction().guardarConfiguracion(configurarVistaDatosForm, request));
      }
      else
      {
        configurarVistaDatosForm.clear();
        configurarVistaDatosForm.setSource(Byte.valueOf(ConfigurarReporteGraficoForm.SourceType.getSourceGestionar()));
        configurarVistaDatosForm.setReporteId(reporteId);
        new ConfigurarReporteGraficoAction().cargarConfiguracion(configurarVistaDatosForm, request);
      }
    }
    else
    {
      Boolean acumularPeriodos = Boolean.valueOf(request.getParameter("acumularPeriodos") != null ? Boolean.parseBoolean(request.getParameter("acumularPeriodos")) : false);
      Boolean showTotalFilas = Boolean.valueOf(request.getParameter("showTotalFilas") != null ? Boolean.parseBoolean(request.getParameter("showTotalFilas")) : false);
      Boolean showTotalColumnas = Boolean.valueOf(request.getParameter("showTotalColumnas") != null ? Boolean.parseBoolean(request.getParameter("showTotalColumnas")) : false);
      configurarVistaDatosForm.setShowTotalFilas(showTotalFilas);
      configurarVistaDatosForm.setShowTotalColumnas(showTotalColumnas);
      configurarVistaDatosForm.setAcumularPeriodos(acumularPeriodos);
      
      configurarVistaDatosForm.setConfiguracion(new GuardarReporteGraficoAction().guardarConfiguracion(configurarVistaDatosForm, request));
    }
    setConfigurarVistaDatosForm(configurarVistaDatosForm, getResources(request));
    
    saveMessages(request, messages);
    
    Boolean showTablaParametro = configurarVistaDatosForm.getShowTablaParametro();
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(getUsuarioConectado(request).getUsuarioId(), "Strategos.Configuracion.Reporte.Editar.Parametros", "Parametros");
    if (configuracionUsuario == null)
    {
      configuracionUsuario = new ConfiguracionUsuario();
      ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
      pk.setConfiguracionBase("Strategos.Configuracion.Reporte.Editar.Parametros");
      pk.setObjeto("Parametros");
      pk.setUsuarioId(getUsuarioConectado(request).getUsuarioId());
      configuracionUsuario.setPk(pk);
    }
    else
    {
      configurarVistaDatosForm.setXml(configuracionUsuario.getData());
    }
    if ((showTablaParametro != null) && (showTablaParametro.booleanValue() != configurarVistaDatosForm.getShowTablaParametro().booleanValue())) {
      configurarVistaDatosForm.setShowTablaParametro(showTablaParametro);
    } else if (configurarVistaDatosForm.getShowTablaParametro() == null) {
      configurarVistaDatosForm.setShowTablaParametro(Boolean.valueOf(true));
    }
    configuracionUsuario.setData(configurarVistaDatosForm.getXml());
    
    frameworkService.saveConfiguracionUsuario(configuracionUsuario, getUsuarioConectado(request));
    frameworkService.close();
    
    PaginaLista paginaColumnas = new PaginaLista();
    paginaColumnas.setLista(configurarVistaDatosForm.getAtributos());
    paginaColumnas.setNroPagina(0);
    paginaColumnas.setTotal(paginaColumnas.getLista().size());
    
    request.setAttribute("paginaColumnas", paginaColumnas);
    
    return mapping.findForward(forward);
  }
  
  public void setConfigurarVistaDatosForm(ConfigurarReporteGraficoForm configurarVistaDatosForm, MessageResources mensajes)
  {
    setSelectores(configurarVistaDatosForm);
    
    setFilas(configurarVistaDatosForm);
    
    setColumnas(configurarVistaDatosForm);
    
    setMatrizDatos(configurarVistaDatosForm, mensajes);
    
    setPeriodos(configurarVistaDatosForm);
    
    configurarVistaDatosForm.setNombreFrecuencia(Frecuencia.getNombre(configurarVistaDatosForm.getFrecuencia().byteValue()));
  }
  
  private void setPeriodos(ConfigurarReporteGraficoForm configurarVistaDatosForm)
  {
    Map<String, String> dimensiones = new HashMap();
    
    configurarVistaDatosForm.getClass();String[] variables = configurarVistaDatosForm.getColumnasId().split("!@!");
    List<ObjetoValorNombre> objetos = new ArrayList();
    Boolean porColumnas = null;
    for (int c = 0; c < variables.length; c++) {
      if (variables[c].equals(TipoDimension.getTipoDimensionTiempo().toString()))
      {
        objetos = configurarVistaDatosForm.getColumnas();
        porColumnas = Boolean.valueOf(true);
        break;
      }
    }
    if (porColumnas == null)
    {
      configurarVistaDatosForm.getClass();variables = configurarVistaDatosForm.getFilasId().split("!@!");
      for (int c = 0; c < variables.length; c++) {
        if (variables[c].equals(TipoDimension.getTipoDimensionTiempo().toString()))
        {
          objetos = configurarVistaDatosForm.getFilas();
          porColumnas = Boolean.valueOf(true);
          break;
        }
      }
    }
    if ((porColumnas != null) && (porColumnas.booleanValue()) && (objetos != null))
    {
      for (int c = 0; c < objetos.size(); c++)
      {
        ObjetoValorNombre columna = (ObjetoValorNombre)objetos.get(c);
        setMiembroDimension(dimensiones, TipoDimension.getTipoDimensionTiempo(), columna.getValor(), null, null);
        
        String tiempoId = (String)dimensiones.get("tiempoId");
        if ((tiempoId != null) && (!tiempoId.equals("")) && (tiempoId.indexOf("_") != -1) && (!tiempoId.equals("0_0")))
        {
          String[] arrPeriodoAno = StringUtil.split(tiempoId, "_");
          Integer ano = new Integer(arrPeriodoAno[1]);
          Integer periodo = new Integer(arrPeriodoAno[0]);
          if (configurarVistaDatosForm.getPeriodoInicial() == null) {
            configurarVistaDatosForm.setPeriodoInicial(periodo);
          }
          if (configurarVistaDatosForm.getPeriodoFinal() == null) {
            configurarVistaDatosForm.setPeriodoFinal(periodo);
          }
          if (configurarVistaDatosForm.getAnoInicial() == null) {
            configurarVistaDatosForm.setAnoInicial(ano);
          }
          if (configurarVistaDatosForm.getAnoFinal() == null) {
            configurarVistaDatosForm.setAnoFinal(ano);
          }
          Integer periodos = new Integer(ano.toString() + (periodo.toString().length() == 2 ? "0" : periodo.toString().length() == 1 ? "00" : "") + periodo.toString());
          Integer periodoComp = new Integer(configurarVistaDatosForm.getAnoInicial().toString() + (configurarVistaDatosForm.getPeriodoInicial().toString().length() == 2 ? "0" : configurarVistaDatosForm.getPeriodoInicial().toString().length() == 1 ? "00" : "") + configurarVistaDatosForm.getPeriodoInicial().toString());
          if (periodoComp.intValue() > periodos.intValue())
          {
            configurarVistaDatosForm.setPeriodoInicial(periodo);
            configurarVistaDatosForm.setAnoInicial(ano);
          }
          periodoComp = new Integer(configurarVistaDatosForm.getAnoFinal().toString() + (configurarVistaDatosForm.getPeriodoFinal().toString().length() == 2 ? "0" : configurarVistaDatosForm.getPeriodoFinal().toString().length() == 1 ? "00" : "") + configurarVistaDatosForm.getPeriodoFinal().toString());
          if (periodoComp.intValue() < periodos.intValue())
          {
            configurarVistaDatosForm.setPeriodoFinal(periodo);
            configurarVistaDatosForm.setAnoFinal(ano);
          }
        }
      }
    }
    else
    {
      dimensiones = new HashMap();
      inicializarListaMiembros(dimensiones);
      setValoresSelectores(dimensiones, configurarVistaDatosForm);
      
      String tiempoId = (String)dimensiones.get("tiempoId");
      if ((tiempoId != null) && (!tiempoId.equals("")) && (tiempoId.indexOf("_") != -1) && (!tiempoId.equals("0_0")))
      {
        String[] arrPeriodoAno = StringUtil.split(tiempoId, "_");
        Integer ano = new Integer(arrPeriodoAno[1]);
        Integer periodo = new Integer(arrPeriodoAno[0]);
        
        configurarVistaDatosForm.setPeriodoInicial(periodo);
        configurarVistaDatosForm.setAnoInicial(ano);
        configurarVistaDatosForm.setPeriodoFinal(periodo);
        configurarVistaDatosForm.setAnoFinal(ano);
      }
    }
  }
  
  private void setSelectores(ConfigurarReporteGraficoForm configurarVistaDatosForm)
  {
    String dimensionId = "";
    String nombreDimension = "";
    List<ObjetoValorNombre> dimension = new ArrayList();
    int i = 0;
    
    configurarVistaDatosForm.setNombreSelector1("");
    configurarVistaDatosForm.setSelector1Id(new Long(-1L));
    configurarVistaDatosForm.setSelector1(new ArrayList());
    
    configurarVistaDatosForm.setNombreSelector2("");
    configurarVistaDatosForm.setSelector2Id(new Long(-1L));
    configurarVistaDatosForm.setSelector2(new ArrayList());
    
    configurarVistaDatosForm.setNombreSelector3("");
    configurarVistaDatosForm.setSelector3Id(new Long(-1L));
    configurarVistaDatosForm.setSelector3(new ArrayList());
    
    configurarVistaDatosForm.setNombreSelector4("");
    configurarVistaDatosForm.setSelector4Id(new Long(-1L));
    configurarVistaDatosForm.setSelector4(new ArrayList());
    for (i = 1; i <= configurarVistaDatosForm.getSelectores().size(); i++)
    {
      ObjetoValorNombre selector = (ObjetoValorNombre)configurarVistaDatosForm.getSelectores().get(i - 1);
      
      nombreDimension = selector.getNombre();
      dimensionId = selector.getValor();
      dimension = getListaDimension(dimensionId, configurarVistaDatosForm);
      if (dimension != null)
      {
        if (i == 1)
        {
          configurarVistaDatosForm.setNombreSelector1(nombreDimension);
          configurarVistaDatosForm.setSelector1Id(new Long(dimensionId));
          configurarVistaDatosForm.setSelector1(dimension);
        }
        else if (i == 2)
        {
          configurarVistaDatosForm.setNombreSelector2(nombreDimension);
          configurarVistaDatosForm.setSelector2Id(new Long(dimensionId));
          configurarVistaDatosForm.setSelector2(dimension);
        }
        else if (i == 3)
        {
          configurarVistaDatosForm.setNombreSelector3(nombreDimension);
          configurarVistaDatosForm.setSelector3Id(new Long(dimensionId));
          configurarVistaDatosForm.setSelector3(dimension);
        }
        else if (i == 4)
        {
          configurarVistaDatosForm.setNombreSelector4(nombreDimension);
          configurarVistaDatosForm.setSelector4Id(new Long(dimensionId));
          configurarVistaDatosForm.setSelector4(dimension);
        }
        configurarVistaDatosForm.getClass();String[] variables = dimensionId.split("!@!");
        if (new Byte(variables[0]).equals(TipoDimension.getTipoDimensionTiempo()))
        {
          configurarVistaDatosForm.setSelectorTiempoDesde(dimension);
          configurarVistaDatosForm.setSelectorTiempoHasta(dimension);
        }
      }
    }
  }
  
  private void setFilas(ConfigurarReporteGraficoForm configurarVistaDatosForm)
  {
    List<ObjetoValorNombre> filas = new ArrayList();
    if ((configurarVistaDatosForm.getFilasId() != null) && (configurarVistaDatosForm.getNombreFilas() != null) && (!configurarVistaDatosForm.getNombreFilas().equals(""))) {
      filas = getListaDimension(configurarVistaDatosForm.getFilasId(), configurarVistaDatosForm);
    }
    configurarVistaDatosForm.setFilas(filas);
  }
  
  private void setColumnas(ConfigurarReporteGraficoForm configurarVistaDatosForm)
  {
    List<ObjetoValorNombre> columnas = new ArrayList();
    if ((configurarVistaDatosForm.getColumnasId() != null) && (configurarVistaDatosForm.getNombreColumnas() != null) && (!configurarVistaDatosForm.getNombreColumnas().equals(""))) {
      columnas = getListaDimension(configurarVistaDatosForm.getColumnasId().toString(), configurarVistaDatosForm);
    }
    configurarVistaDatosForm.setColumnas(columnas);
  }
  
  private List<ObjetoValorNombre> getListaDimension(String dimensiones, ConfigurarReporteGraficoForm configurarVistaDatosForm)
  {
    List<ObjetoValorNombre> miembrosDimension = null;
    List<ObjetoValorNombre> miembroDimension = null;
    
    configurarVistaDatosForm.getClass();String[] variables = dimensiones.split("!@!");
    Byte dimensionId = new Byte(variables[0]);
    if (dimensionId.equals(TipoDimension.getTipoDimensionVariable())) {
      miembroDimension = configurarVistaDatosForm.getMiembrosVariable();
    } else if (dimensionId.equals(TipoDimension.getTipoDimensionTiempo())) {
      miembroDimension = configurarVistaDatosForm.getMiembrosTiempo();
    } else if (dimensionId.equals(TipoDimension.getTipoDimensionIndicador())) {
      miembroDimension = configurarVistaDatosForm.getMiembrosIndicador();
    } else if (dimensionId.equals(TipoDimension.getTipoDimensionOrganizacion())) {
      miembroDimension = configurarVistaDatosForm.getMiembrosOrganizacion();
    }
    if ((miembroDimension != null) && (miembroDimension.size() > 0))
    {
      if (miembrosDimension == null) {
        miembrosDimension = new ArrayList();
      }
      miembrosDimension.addAll(miembroDimension);
    }
    return miembrosDimension;
  }
  
  private String getPlanId(List<ObjetoValorNombre> matriz, String indicadorId)
  {
    String planId = "";
    for (int f = 0; f < matriz.size(); f++)
    {
      ObjetoValorNombre objeto = (ObjetoValorNombre)matriz.get(f);
      if ((objeto.getValor() != null) && (objeto.getValor().equals(indicadorId)) && (objeto.getValorOculto() != null) && (!objeto.getValorOculto().equals("")))
      {
        planId = objeto.getValorOculto();
        break;
      }
    }
    return planId;
  }
  
  private void setMatrizDatos(ConfigurarReporteGraficoForm configurarVistaDatosForm, MessageResources mensajes)
  {
    String tiempoId = "";
    String tiempoDesdeId = "";
    String tiempoHastaId = "";
    String organizacionId = "";
    String planId = "";
    String indicadorId = "";
    boolean hayVariables = false;
    boolean haySerie = false;
    boolean hayIndicador = false;
    for (int f = 0; f < configurarVistaDatosForm.getAtributos().size(); f++)
    {
      TipoAtributo tipoAtributo = (TipoAtributo)configurarVistaDatosForm.getAtributos().get(f);
      if (tipoAtributo.getVisible().booleanValue())
      {
        hayVariables = true;
        break;
      }
    }
    List<ObjetoValorNombre> totalxFilas = new ArrayList();
    List<ObjetoValorNombre> totalxColumnas = new ArrayList();
    if (configurarVistaDatosForm.getShowTotalFilas().booleanValue()) {
      for (int c = 0; c < configurarVistaDatosForm.getFilas().size(); c++)
      {
        ObjetoValorNombre objeto = (ObjetoValorNombre)configurarVistaDatosForm.getFilas().get(c);
        ObjetoValorNombre valor = new ObjetoValorNombre();
        valor.setNombre(objeto.getNombre());
        totalxFilas.add(valor);
      }
    }
    if (configurarVistaDatosForm.getShowTotalColumnas().booleanValue()) {
      for (int c = 0; c < configurarVistaDatosForm.getColumnas().size(); c++)
      {
        ObjetoValorNombre objeto = (ObjetoValorNombre)configurarVistaDatosForm.getColumnas().get(c);
        ObjetoValorNombre valor = new ObjetoValorNombre();
        valor.setNombre(objeto.getNombre());
        totalxColumnas.add(valor);
      }
    }
    if (configurarVistaDatosForm.getShowTotalFilas().booleanValue())
    {
      ObjetoValorNombre totalColumnas = new ObjetoValorNombre();
      totalColumnas.setNombre(mensajes.getMessage("jsp.mostrarvistadatos.tabla.total.fila"));
      totalColumnas.setValor("TF");
      configurarVistaDatosForm.getColumnas().add(totalColumnas);
    }
    if (configurarVistaDatosForm.getShowTotalColumnas().booleanValue())
    {
      ObjetoValorNombre totalFilas = new ObjetoValorNombre();
      totalFilas.setNombre(mensajes.getMessage("jsp.mostrarvistadatos.tabla.total.columna"));
      totalFilas.setValor("TC");
      configurarVistaDatosForm.getFilas().add(totalFilas);
    }
    List<DatoCelda> filaDatos = new ArrayList();
    List<List<DatoCelda>> matrizDatos = new ArrayList();
    List<ObjetoValorNombre> filas = configurarVistaDatosForm.getFilas();
    List<ObjetoValorNombre> columnas = configurarVistaDatosForm.getColumnas();
    
    Map<String, String> dimensiones = new HashMap();
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    
    String valorCelda = "";
    DatoCelda datoCelda = new DatoCelda();
    
    StrategosVistasDatosService strategosVistasDatosService = StrategosServiceFactory.getInstance().openStrategosVistasDatosService();
    if ((filas != null) && (filas.size() > 0) && (columnas != null) && (columnas.size() > 0))
    {
      filaDatos = new ArrayList();
      
      configurarVistaDatosForm.getClass();String[] variables = configurarVistaDatosForm.getColumnasId().split("!@!");
      Boolean porColumnas = null;
      for (int c = 0; c < variables.length; c++) {
        if (variables[c].equals(TipoDimension.getTipoDimensionIndicador().toString()))
        {
          porColumnas = Boolean.valueOf(true);
          break;
        }
      }
      if (porColumnas == null)
      {
        configurarVistaDatosForm.getClass();variables = configurarVistaDatosForm.getFilasId().split("!@!");
        porColumnas = Boolean.valueOf(false);
      }
      boolean hayVariablesCombinadas = variables.length > 1;
      if (hayVariables)
      {
        for (int c = 0; c < variables.length; c++) {
          if (variables[c].equals(TipoDimension.getTipoDimensionVariable().toString())) {
            haySerie = true;
          } else if (variables[c].equals(TipoDimension.getTipoDimensionIndicador().toString())) {
            hayIndicador = true;
          }
        }
        if (!porColumnas.booleanValue())
        {
          configurarVistaDatosForm.setShowVariable(Boolean.valueOf(true));
          for (int f = 0; f < configurarVistaDatosForm.getAtributos().size(); f++)
          {
            TipoAtributo tipoAtributo = (TipoAtributo)configurarVistaDatosForm.getAtributos().get(f);
            if (tipoAtributo.getVisible().booleanValue()) {
              if (tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoSerie().byteValue())
              {
                if (haySerie)
                {
                  datoCelda = new DatoCelda();
                  datoCelda.setValor(tipoAtributo.getNombre());
                  datoCelda.setEsAlerta(new Boolean(false));
                  datoCelda.setAncho(tipoAtributo.getAncho());
                  datoCelda.setEsEncabezado(new Boolean(true));
                  datoCelda.setAlineacion("center");
                  
                  filaDatos.add(datoCelda);
                }
              }
              else
              {
                datoCelda = new DatoCelda();
                datoCelda.setValor(tipoAtributo.getNombre());
                datoCelda.setEsAlerta(new Boolean(false));
                datoCelda.setAncho(tipoAtributo.getAncho());
                datoCelda.setEsEncabezado(new Boolean(true));
                datoCelda.setAlineacion("center");
                
                filaDatos.add(datoCelda);
              }
            }
          }
        }
        else
        {
          valorCelda = configurarVistaDatosForm.getNombreFilas();
          
          datoCelda = new DatoCelda();
          datoCelda.setValor(valorCelda);
          datoCelda.setEsAlerta(new Boolean(false));
          datoCelda.setAlineacion("center");
          
          filaDatos.add(datoCelda);
        }
      }
      else
      {
        valorCelda = configurarVistaDatosForm.getNombreFilas();
        
        datoCelda = new DatoCelda();
        datoCelda.setValor(valorCelda);
        datoCelda.setEsAlerta(new Boolean(false));
        datoCelda.setAlineacion("center");
        
        filaDatos.add(datoCelda);
      }
      List<ObjetoValorNombre> seriesDimension = null;
      if (hayVariablesCombinadas) {
        for (int c = 0; c < variables.length; c++) {
          if (variables[c].equals(TipoDimension.getTipoDimensionVariable().toString()))
          {
            seriesDimension = configurarVistaDatosForm.getMiembrosVariable();
            break;
          }
        }
      }
      if (!porColumnas.booleanValue())
      {
        for (int c = 0; c < columnas.size(); c++)
        {
          ObjetoValorNombre columna = (ObjetoValorNombre)columnas.get(c);
          
          valorCelda = columna.getNombre();
          
          datoCelda = new DatoCelda();
          datoCelda.setValor(valorCelda);
          datoCelda.setEsAlerta(new Boolean(false));
          datoCelda.setAlineacion("center");
          
          filaDatos.add(datoCelda);
        }
      }
      else if (seriesDimension != null)
      {
        for (int k = 0; k < seriesDimension.size(); k++)
        {
          ObjetoValorNombre serie = (ObjetoValorNombre)seriesDimension.get(k);
          for (int c = 0; c < columnas.size(); c++)
          {
            ObjetoValorNombre columna = (ObjetoValorNombre)columnas.get(c);
            setMiembroDimension(dimensiones, new Byte(variables[0]), columna.getValor(), null, null);
            
            tiempoDesdeId = (String)dimensiones.get("tiempoDesdeId");
            tiempoHastaId = (String)dimensiones.get("tiempoHastaId");
            if ((tiempoDesdeId != null) && (tiempoDesdeId.equals("")))
            {
              if (configurarVistaDatosForm.getAcumularPeriodos().booleanValue())
              {
                if (tiempoId.equals("")) {
                  tiempoId = (String)dimensiones.get("tiempoId");
                }
              }
              else {
                tiempoId = (String)dimensiones.get("tiempoId");
              }
              tiempoDesdeId = tiempoId;
              tiempoHastaId = (String)dimensiones.get("tiempoId");
            }
            organizacionId = (String)dimensiones.get("organizacionId");
            indicadorId = (String)dimensiones.get("indicadorId");
            
            valorCelda = columna.getNombre() + " - " + serie.getNombre();
            datoCelda = new DatoCelda();
            datoCelda.setValor(valorCelda);
            datoCelda.setEsAlerta(new Boolean(false));
            datoCelda.setAlineacion("center");
            
            filaDatos.add(datoCelda);
          }
        }
      }
      else
      {
        String variableId = "";
        for (int c = 0; c < columnas.size(); c++)
        {
          ObjetoValorNombre columna = (ObjetoValorNombre)columnas.get(c);
          setMiembroDimension(dimensiones, new Byte(variables[0]), columna.getValor(), null, null);
          
          tiempoDesdeId = (String)dimensiones.get("tiempoDesdeId");
          tiempoHastaId = (String)dimensiones.get("tiempoHastaId");
          if (tiempoDesdeId.equals(""))
          {
            if (configurarVistaDatosForm.getAcumularPeriodos().booleanValue())
            {
              if (tiempoId.equals("")) {
                tiempoId = (String)dimensiones.get("tiempoId");
              }
            }
            else {
              tiempoId = (String)dimensiones.get("tiempoId");
            }
            tiempoDesdeId = tiempoId;
            tiempoHastaId = (String)dimensiones.get("tiempoId");
          }
          organizacionId = (String)dimensiones.get("organizacionId");
          indicadorId = (String)dimensiones.get("indicadorId");
          variableId = (String)dimensiones.get("variableId");
          SerieTiempo serie = null;
          if (variableId != null) {
            serie = (SerieTiempo)strategosIndicadoresService.load(SerieTiempo.class, new Long(variableId));
          }
          valorCelda = columna.getNombre() + (serie != null ? " - " + serie.getNombre() : "");
          datoCelda = new DatoCelda();
          datoCelda.setValor(valorCelda);
          datoCelda.setEsAlerta(new Boolean(false));
          datoCelda.setAlineacion("center");
          
          filaDatos.add(datoCelda);
        }
      }
      matrizDatos.add(filaDatos);
      
      dimensiones = new HashMap();
      inicializarListaMiembros(dimensiones);
      setValoresSelectores(dimensiones, configurarVistaDatosForm);
      boolean pintarColumna = false;
      List<Indicador> indicadoresMatriz = BuscarIndicadores(configurarVistaDatosForm);
      String indicadorNombre = "";
      String organizacionNombre = "";
      String claseNombre = "";
      String serieNombre = "";
      String frecuenciaNombre = "";
      String unidadNombre = "";
      int columnasImpresas = 0;
      if (seriesDimension != null)
      {
        for (int f = 0; f < filas.size(); f++)
        {
          ObjetoValorNombre fila = (ObjetoValorNombre)filas.get(f);
          Indicador indicador = null;
          if ((!fila.getValor().equals("TF")) && (!fila.getValor().equals("TC")))
          {
            if (!porColumnas.booleanValue())
            {
              indicador = (Indicador)indicadoresMatriz.get(f);
              setMiembroDimension(dimensiones, new Byte(variables[0]), fila.getValor(), null, null);
            }
            else
            {
              setMiembroDimension(dimensiones, new Byte(configurarVistaDatosForm.getFilasId().toString()), fila.getValor(), null, null);
            }
            if (porColumnas.booleanValue())
            {
              filaDatos = new ArrayList();
              pintarColumna = true;
            }
            for (int k = 0; k < seriesDimension.size(); k++)
            {
              if (!porColumnas.booleanValue())
              {
                filaDatos = new ArrayList();
                for (int c = 0; c < totalxFilas.size(); c++)
                {
                  ObjetoValorNombre objeto = (ObjetoValorNombre)totalxFilas.get(c);
                  objeto.setValor(null);
                }
              }
              ObjetoValorNombre serie = (ObjetoValorNombre)seriesDimension.get(k);
              
              columnasImpresas = 0;
              for (int c = 0; c < columnas.size(); c++)
              {
                ObjetoValorNombre columna = (ObjetoValorNombre)columnas.get(c);
                if ((!columna.getValor().equals("TF")) && (!columna.getValor().equals("TC")))
                {
                  if (porColumnas.booleanValue())
                  {
                    indicador = null;
                    setMiembroDimension(dimensiones, new Byte(variables[0]), columna.getValor(), null, null);
                    indicadorId = (String)dimensiones.get("indicadorId");
                    if ((indicadorId == null) || (indicadorId.equals("")) || (indicadorId.equals("0"))) {
                      continue;
                    }
                    for (Iterator<?> iterador = indicadoresMatriz.iterator(); iterador.hasNext();)
                    {
                      Indicador ind = (Indicador)iterador.next();
                      if (ind.getIndicadorId().toString().equals(indicadorId))
                      {
                        indicador = ind;
                        break;
                      }
                    }
                    if (indicador == null) {
                      indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(indicadorId));
                    }
                  }
                  else
                  {
                    setMiembroDimension(dimensiones, new Byte(configurarVistaDatosForm.getColumnasId().toString()), columna.getValor(), null, null);
                  }
                  tiempoDesdeId = (String)dimensiones.get("tiempoDesdeId");
                  tiempoHastaId = (String)dimensiones.get("tiempoHastaId");
                  if (tiempoDesdeId.equals(""))
                  {
                    if (configurarVistaDatosForm.getAcumularPeriodos().booleanValue())
                    {
                      if (tiempoId.equals("")) {
                        tiempoId = (String)dimensiones.get("tiempoId");
                      }
                    }
                    else {
                      tiempoId = (String)dimensiones.get("tiempoId");
                    }
                    tiempoDesdeId = tiempoId;
                    tiempoHastaId = (String)dimensiones.get("tiempoId");
                  }
                  organizacionId = (String)dimensiones.get("organizacionId");
                  if (indicador != null) {
                    planId = getPlanId(filas, indicador.getIndicadorId().toString());
                  }
                  if ((c == 0) || (pintarColumna))
                  {
                    if (!porColumnas.booleanValue())
                    {
                      if ((hayVariables) && (indicador != null))
                      {
                        for (Iterator<?> iterador = configurarVistaDatosForm.getAtributos().iterator(); iterador.hasNext();)
                        {
                          valorCelda = "";
                          TipoAtributo tipoAtributo = (TipoAtributo)iterador.next();
                          if (tipoAtributo.getVisible().booleanValue()) {
                            if ((tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoSerie().byteValue()) && (haySerie))
                            {
                              valorCelda = serie.getNombre();
                              if (tipoAtributo.getAgrupar().booleanValue()) {
                                if (!serieNombre.equals(valorCelda))
                                {
                                  serieNombre = valorCelda;
                                  if (tipoAtributo.getOrden().equals("1"))
                                  {
                                    organizacionNombre = "";
                                    claseNombre = "";
                                    frecuenciaNombre = "";
                                    unidadNombre = "";
                                    indicadorNombre = "";
                                  }
                                }
                                else
                                {
                                  valorCelda = "";
                                }
                              }
                              datoCelda = new DatoCelda();
                              datoCelda.setValor(valorCelda);
                              datoCelda.setAncho(tipoAtributo.getAncho());
                              datoCelda.setEsAlerta(new Boolean(false));
                              datoCelda.setEsEncabezado(new Boolean(true));
                              datoCelda.setAlineacion("center");
                              
                              filaDatos.add(datoCelda);
                              columnasImpresas++;
                            }
                            else
                            {
                              if (tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoNombre().byteValue())
                              {
                                valorCelda = indicador.getNombre();
                                if (tipoAtributo.getAgrupar().booleanValue()) {
                                  if (!indicadorNombre.equals(valorCelda))
                                  {
                                    indicadorNombre = valorCelda;
                                    if (tipoAtributo.getOrden().equals("1"))
                                    {
                                      organizacionNombre = "";
                                      claseNombre = "";
                                      serieNombre = "";
                                      frecuenciaNombre = "";
                                      unidadNombre = "";
                                    }
                                  }
                                  else
                                  {
                                    valorCelda = "";
                                  }
                                }
                              }
                              else if (tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoClase().byteValue())
                              {
                                ClaseIndicadores clase = (ClaseIndicadores)strategosIndicadoresService.load(ClaseIndicadores.class, new Long(indicador.getClaseId().longValue()));
                                valorCelda = clase.getNombre();
                                if (tipoAtributo.getAgrupar().booleanValue()) {
                                  if (!claseNombre.equals(valorCelda))
                                  {
                                    claseNombre = valorCelda;
                                    if (tipoAtributo.getOrden().equals("1"))
                                    {
                                      serieNombre = "";
                                      organizacionNombre = "";
                                      frecuenciaNombre = "";
                                      unidadNombre = "";
                                      indicadorNombre = "";
                                    }
                                  }
                                  else
                                  {
                                    valorCelda = "";
                                  }
                                }
                              }
                              else if (tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoFrecuencia().byteValue())
                              {
                                valorCelda = Frecuencia.getNombre(indicador.getFrecuencia().byteValue());
                                if (tipoAtributo.getAgrupar().booleanValue()) {
                                  if (!frecuenciaNombre.equals(valorCelda))
                                  {
                                    frecuenciaNombre = valorCelda;
                                    if (tipoAtributo.getOrden().equals("1"))
                                    {
                                      serieNombre = "";
                                      organizacionNombre = "";
                                      claseNombre = "";
                                      unidadNombre = "";
                                      indicadorNombre = "";
                                    }
                                  }
                                  else
                                  {
                                    valorCelda = "";
                                  }
                                }
                              }
                              else if (tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoOrganizacion().byteValue())
                              {
                                OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosIndicadoresService.load(OrganizacionStrategos.class, new Long(indicador.getOrganizacionId().longValue()));
                                valorCelda = organizacion.getNombre();
                                if (tipoAtributo.getAgrupar().booleanValue()) {
                                  if (!organizacionNombre.equals(valorCelda))
                                  {
                                    organizacionNombre = valorCelda;
                                    if (tipoAtributo.getOrden().equals("1"))
                                    {
                                      serieNombre = "";
                                      claseNombre = "";
                                      frecuenciaNombre = "";
                                      unidadNombre = "";
                                      indicadorNombre = "";
                                    }
                                  }
                                  else
                                  {
                                    valorCelda = "";
                                  }
                                }
                              }
                              else if ((tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoUnidadMedida().byteValue()) && (indicador.getUnidadId() != null))
                              {
                                UnidadMedida unidad = (UnidadMedida)strategosIndicadoresService.load(UnidadMedida.class, new Long(indicador.getUnidadId().longValue()));
                                if (unidad != null)
                                {
                                  valorCelda = unidad.getNombre();
                                  if (tipoAtributo.getAgrupar().booleanValue()) {
                                    if (!unidadNombre.equals(valorCelda))
                                    {
                                      unidadNombre = valorCelda;
                                      if (tipoAtributo.getOrden().equals("1"))
                                      {
                                        serieNombre = "";
                                        organizacionNombre = "";
                                        claseNombre = "";
                                        frecuenciaNombre = "";
                                        indicadorNombre = "";
                                      }
                                    }
                                    else
                                    {
                                      valorCelda = "";
                                    }
                                  }
                                }
                              }
                              datoCelda = new DatoCelda();
                              datoCelda.setValor(valorCelda);
                              datoCelda.setAncho(tipoAtributo.getAncho());
                              datoCelda.setEsAlerta(new Boolean(false));
                              datoCelda.setEsEncabezado(new Boolean(true));
                              datoCelda.setAlineacion("center");
                              
                              filaDatos.add(datoCelda);
                              columnasImpresas++;
                            }
                          }
                        }
                      }
                      else
                      {
                        valorCelda = fila.getNombre() + " - " + serie.getNombre();
                        
                        datoCelda = new DatoCelda();
                        datoCelda.setValor(valorCelda);
                        datoCelda.setEsAlerta(new Boolean(false));
                        datoCelda.setAlineacion("center");
                        
                        filaDatos.add(datoCelda);
                      }
                    }
                    else if (pintarColumna)
                    {
                      valorCelda = fila.getNombre();
                      
                      datoCelda = new DatoCelda();
                      datoCelda.setValor(valorCelda);
                      datoCelda.setEsAlerta(new Boolean(false));
                      datoCelda.setAlineacion("center");
                      
                      filaDatos.add(datoCelda);
                    }
                    pintarColumna = false;
                  }
                  if ((organizacionId != "") && (indicadorId != ""))
                  {
                    Map<String, Comparable<?>> filtros = new HashMap();
                    filtros.put("organizacionId", organizacionId);
                    filtros.put("nombre", indicador.getNombre());
                    List<Indicador> indicadores = new ArrayList();
                    indicadores = strategosIndicadoresService.getIndicadores(filtros);
                    if (indicadores.size() > 0) {
                      indicadorId = ((Indicador)indicadores.get(0)).getIndicadorId().toString();
                    } else {
                      indicadorId = "";
                    }
                  }
                  else if ((organizacionId != "") && (indicador != null))
                  {
                    Map<String, Comparable<?>> filtros = new HashMap();
                    filtros.put("organizacionId", organizacionId);
                    filtros.put("nombre", indicador.getNombre());
                    List<Indicador> indicadores = new ArrayList();
                    indicadores = strategosIndicadoresService.getIndicadores(filtros);
                    if (indicadores.size() > 0) {
                      indicadorId = ((Indicador)indicadores.get(0)).getIndicadorId().toString();
                    } else {
                      indicadorId = "";
                    }
                  }
                  else if ((organizacionId == "") && (indicador != null))
                  {
                    indicadorId = indicador.getIndicadorId().toString();
                  }
                  boolean acumular = true;
                  if ((indicador != null) && (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue()) && (indicador.getTipoCargaMedicion() != null) && (indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())) {
                    acumular = false;
                  }
                  valorCelda = strategosVistasDatosService.getValorDimensiones(serie.getValor(), tiempoDesdeId, tiempoHastaId, indicadorId, planId, organizacionId, Boolean.valueOf(acumular));
                  if (!serie.getValor().equals(TipoVariable.getTipoVariableAlerta().toString()))
                  {
                    if ((configurarVistaDatosForm.getShowTotalColumnas().booleanValue()) && (esDouble(valorCelda) != null) && (c < totalxColumnas.size()))
                    {
                      ObjetoValorNombre total = (ObjetoValorNombre)totalxColumnas.get(c);
                      Double valor = Double.valueOf(0.0D);
                      if (esDouble(total.getValor()) != null) {
                        valor = Double.valueOf(esDouble(total.getValor()).doubleValue() + esDouble(valorCelda).doubleValue());
                      }
                      total.setValor(valor.toString());
                      totalxColumnas.set(c, total);
                    }
                    if ((configurarVistaDatosForm.getShowTotalFilas().booleanValue()) && (esDouble(valorCelda) != null) && (f < totalxFilas.size()))
                    {
                      ObjetoValorNombre total = (ObjetoValorNombre)totalxFilas.get(f);
                      Double valor = Double.valueOf(0.0D);
                      if (esDouble(total.getValor()) != null) {
                        valor = Double.valueOf(esDouble(total.getValor()).doubleValue() + esDouble(valorCelda).doubleValue());
                      }
                      total.setValor(valor.toString());
                      totalxFilas.set(f, total);
                    }
                  }
                  datoCelda = new DatoCelda();
                  if (!serie.getValor().equals(TipoVariable.getTipoVariableAlerta().toString())) {
                    datoCelda.setValor((valorCelda != null) && (!valorCelda.equals("")) ? VgcFormatter.formatearNumero(Double.valueOf(Double.parseDouble(valorCelda.replace(",", ""))), 2) : "");
                  } else {
                    datoCelda.setValor(valorCelda);
                  }
                  datoCelda.setEsAlerta(new Boolean(false));
                  if ((serie.getValor() != null) && (!serie.getValor().equals("")) && (new Long(serie.getValor()).equals(TipoVariable.getTipoVariableAlerta()))) {
                    datoCelda.setEsAlerta(new Boolean(true));
                  }
                  datoCelda.setAlineacion("center");
                  
                  filaDatos.add(datoCelda);
                }
                else
                {
                  ObjetoValorNombre objeto = (ObjetoValorNombre)totalxFilas.get(f);
                  
                  datoCelda = new DatoCelda();
                  datoCelda.setValor(objeto.getValor() != null ? VgcFormatter.formatearNumero(Double.valueOf(Double.parseDouble(objeto.getValor())), 2) : "");
                  datoCelda.setEsAlerta(new Boolean(false));
                  datoCelda.setAlineacion("center");
                  
                  filaDatos.add(datoCelda);
                }
              }
              if (!porColumnas.booleanValue()) {
                matrizDatos.add(filaDatos);
              }
            }
          }
          else
          {
            filaDatos = new ArrayList();
            
            valorCelda = fila.getNombre();
            datoCelda = new DatoCelda();
            datoCelda.setValor(valorCelda);
            datoCelda.setEsAlerta(new Boolean(false));
            datoCelda.setAlineacion("center");
            
            filaDatos.add(datoCelda);
            if ((!porColumnas.booleanValue()) && (hayVariables) && (columnasImpresas - 1 > 0)) {
              for (int c = 0; c < columnasImpresas - 1; c++)
              {
                valorCelda = "";
                datoCelda = new DatoCelda();
                datoCelda.setValor(valorCelda);
                datoCelda.setEsAlerta(new Boolean(false));
                datoCelda.setAlineacion("center");
                
                filaDatos.add(datoCelda);
              }
            }
            for (int c = 0; c < totalxColumnas.size(); c++)
            {
              ObjetoValorNombre objeto = (ObjetoValorNombre)totalxColumnas.get(c);
              
              datoCelda = new DatoCelda();
              datoCelda.setValor(objeto.getValor() != null ? VgcFormatter.formatearNumero(Double.valueOf(Double.parseDouble(objeto.getValor())), 2) : "");
              datoCelda.setEsAlerta(new Boolean(false));
              datoCelda.setAlineacion("center");
              
              filaDatos.add(datoCelda);
            }
            if (!porColumnas.booleanValue()) {
              matrizDatos.add(filaDatos);
            }
          }
          if (porColumnas.booleanValue()) {
            matrizDatos.add(filaDatos);
          }
        }
      }
      else
      {
        String variableId = "";
        Boolean hayDatoVariable = Boolean.valueOf(false);
        for (int f = 0; f < filas.size(); f++)
        {
          ObjetoValorNombre fila = (ObjetoValorNombre)filas.get(f);
          setMiembroDimension(dimensiones, new Byte(configurarVistaDatosForm.getFilasId().toString()), fila.getValor(), null, null);
          Indicador indicador = null;
          if ((!fila.getValor().equals("TF")) && (!fila.getValor().equals("TC")))
          {
            columnasImpresas = 0;
            if ((hayIndicador) && (!porColumnas.booleanValue())) {
              indicador = (Indicador)indicadoresMatriz.get(f);
            }
            filaDatos = new ArrayList();
            for (int c = 0; c < columnas.size(); c++)
            {
              ObjetoValorNombre columna = (ObjetoValorNombre)columnas.get(c);
              if ((!columna.getValor().equals("TF")) && (!columna.getValor().equals("TC")))
              {
                setMiembroDimension(dimensiones, new Byte(configurarVistaDatosForm.getColumnasId().toString()), columna.getValor(), null, null);
                
                variableId = (String)dimensiones.get("variableId");
                tiempoDesdeId = (String)dimensiones.get("tiempoDesdeId");
                tiempoHastaId = (String)dimensiones.get("tiempoHastaId");
                if (tiempoDesdeId.equals(""))
                {
                  if (configurarVistaDatosForm.getAcumularPeriodos().booleanValue())
                  {
                    if (tiempoId.equals("")) {
                      tiempoId = (String)dimensiones.get("tiempoId");
                    }
                  }
                  else {
                    tiempoId = (String)dimensiones.get("tiempoId");
                  }
                  tiempoDesdeId = tiempoId;
                  tiempoHastaId = (String)dimensiones.get("tiempoId");
                }
                organizacionId = (String)dimensiones.get("organizacionId");
                if (indicador == null)
                {
                  indicadorId = (String)dimensiones.get("indicadorId");
                  if ((indicadorId != null) && (!indicadorId.equals("")) && (!indicadorId.equals("0"))) {
                    indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(indicadorId));
                  }
                }
                else
                {
                  planId = getPlanId(filas, indicador.getIndicadorId().toString());
                  if (c == 0) {
                    if ((!porColumnas.booleanValue()) && (hayVariables) && (indicador != null))
                    {
                      for (Iterator<?> iterador = configurarVistaDatosForm.getAtributos().iterator(); iterador.hasNext();)
                      {
                        valorCelda = "";
                        hayDatoVariable = Boolean.valueOf(false);
                        TipoAtributo tipoAtributo = (TipoAtributo)iterador.next();
                        if (tipoAtributo.getVisible().booleanValue()) {
                          if ((tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoSerie().byteValue()) && (!variableId.equals("")) && (haySerie))
                          {
                            hayDatoVariable = Boolean.valueOf(true);
                            SerieTiempo serie = (SerieTiempo)strategosIndicadoresService.load(SerieTiempo.class, new Long(variableId));
                            valorCelda = serie.getNombre();
                            if (tipoAtributo.getAgrupar().booleanValue()) {
                              if (!serieNombre.equals(valorCelda)) {
                                serieNombre = valorCelda;
                              } else {
                                valorCelda = "";
                              }
                            }
                            datoCelda = new DatoCelda();
                            datoCelda.setValor(valorCelda);
                            datoCelda.setAncho(tipoAtributo.getAncho());
                            datoCelda.setEsAlerta(new Boolean(false));
                            datoCelda.setEsEncabezado(new Boolean(true));
                            datoCelda.setAlineacion("center");
                            
                            filaDatos.add(datoCelda);
                            columnasImpresas++;
                          }
                          else
                          {
                            if (tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoNombre().byteValue())
                            {
                              hayDatoVariable = Boolean.valueOf(true);
                              valorCelda = indicador.getNombre();
                              if (tipoAtributo.getAgrupar().booleanValue()) {
                                if (!indicadorNombre.equals(valorCelda))
                                {
                                  indicadorNombre = valorCelda;
                                  organizacionNombre = "";
                                  claseNombre = "";
                                  serieNombre = "";
                                  frecuenciaNombre = "";
                                  unidadNombre = "";
                                }
                                else
                                {
                                  valorCelda = "";
                                }
                              }
                            }
                            else if (tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoClase().byteValue())
                            {
                              hayDatoVariable = Boolean.valueOf(true);
                              ClaseIndicadores clase = (ClaseIndicadores)strategosIndicadoresService.load(ClaseIndicadores.class, new Long(indicador.getClaseId().longValue()));
                              valorCelda = clase.getNombre();
                              if (tipoAtributo.getAgrupar().booleanValue()) {
                                if (!claseNombre.equals(valorCelda)) {
                                  claseNombre = valorCelda;
                                } else {
                                  valorCelda = "";
                                }
                              }
                            }
                            else if (tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoFrecuencia().byteValue())
                            {
                              hayDatoVariable = Boolean.valueOf(true);
                              valorCelda = Frecuencia.getNombre(indicador.getFrecuencia().byteValue());
                              if (tipoAtributo.getAgrupar().booleanValue()) {
                                if (!frecuenciaNombre.equals(valorCelda)) {
                                  frecuenciaNombre = valorCelda;
                                } else {
                                  valorCelda = "";
                                }
                              }
                            }
                            else if (tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoOrganizacion().byteValue())
                            {
                              hayDatoVariable = Boolean.valueOf(true);
                              OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosIndicadoresService.load(OrganizacionStrategos.class, new Long(indicador.getOrganizacionId().longValue()));
                              valorCelda = organizacion.getNombre();
                              if (tipoAtributo.getAgrupar().booleanValue()) {
                                if (!organizacionNombre.equals(valorCelda)) {
                                  organizacionNombre = valorCelda;
                                } else {
                                  valorCelda = "";
                                }
                              }
                            }
                            else if (tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoUnidadMedida().byteValue())
                            {
                              hayDatoVariable = Boolean.valueOf(true);
                              if (indicador.getUnidadId() != null)
                              {
                                UnidadMedida unidad = (UnidadMedida)strategosIndicadoresService.load(UnidadMedida.class, new Long(indicador.getUnidadId().longValue()));
                                if (unidad != null)
                                {
                                  valorCelda = unidad.getNombre();
                                  if (tipoAtributo.getAgrupar().booleanValue()) {
                                    if (!unidadNombre.equals(valorCelda)) {
                                      unidadNombre = valorCelda;
                                    } else {
                                      valorCelda = "";
                                    }
                                  }
                                }
                              }
                            }
                            if (hayDatoVariable.booleanValue())
                            {
                              datoCelda = new DatoCelda();
                              datoCelda.setValor(valorCelda);
                              datoCelda.setAncho(tipoAtributo.getAncho());
                              datoCelda.setEsAlerta(new Boolean(false));
                              datoCelda.setEsEncabezado(new Boolean(true));
                              datoCelda.setAlineacion("center");
                              
                              filaDatos.add(datoCelda);
                              columnasImpresas++;
                            }
                          }
                        }
                      }
                    }
                    else
                    {
                      valorCelda = fila.getNombre();
                      datoCelda = new DatoCelda();
                      datoCelda.setValor(valorCelda);
                      datoCelda.setEsAlerta(new Boolean(false));
                      datoCelda.setAlineacion("center");
                      
                      filaDatos.add(datoCelda);
                    }
                  }
                  if ((organizacionId != "") && (indicadorId != ""))
                  {
                    Map<String, Comparable<?>> filtros = new HashMap();
                    filtros.put("organizacionId", organizacionId);
                    filtros.put("nombre", indicador.getNombre());
                    List<Indicador> indicadores = new ArrayList();
                    indicadores = strategosIndicadoresService.getIndicadores(filtros);
                    if (indicadores.size() > 0) {
                      indicadorId = ((Indicador)indicadores.get(0)).getIndicadorId().toString();
                    } else {
                      indicadorId = "";
                    }
                  }
                  else if ((organizacionId != "") && (indicador != null))
                  {
                    Map<String, Comparable<?>> filtros = new HashMap();
                    filtros.put("organizacionId", organizacionId);
                    filtros.put("nombre", indicador.getNombre());
                    List<Indicador> indicadores = new ArrayList();
                    indicadores = strategosIndicadoresService.getIndicadores(filtros);
                    if (indicadores.size() > 0) {
                      indicadorId = ((Indicador)indicadores.get(0)).getIndicadorId().toString();
                    } else {
                      indicadorId = "";
                    }
                  }
                  else if ((organizacionId == "") && (indicador != null))
                  {
                    indicadorId = indicador.getIndicadorId().toString();
                  }
                  boolean acumular = true;
                  if ((indicador != null) && (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue()) && (indicador.getTipoCargaMedicion() != null) && (indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())) {
                    acumular = false;
                  }
                  valorCelda = strategosVistasDatosService.getValorDimensiones(variableId, tiempoDesdeId, tiempoHastaId, indicadorId, planId, organizacionId, Boolean.valueOf(acumular));
                  if (!variableId.equals(TipoVariable.getTipoVariableAlerta().toString()))
                  {
                    if ((configurarVistaDatosForm.getShowTotalColumnas().booleanValue()) && (esDouble(valorCelda) != null) && (c < totalxColumnas.size()))
                    {
                      ObjetoValorNombre total = (ObjetoValorNombre)totalxColumnas.get(c);
                      Double valor = Double.valueOf(0.0D);
                      if (esDouble(total.getValor()) != null) {
                        valor = Double.valueOf(esDouble(total.getValor()).doubleValue() + esDouble(valorCelda).doubleValue());
                      }
                      total.setValor(valor.toString());
                      totalxColumnas.set(c, total);
                    }
                    if ((configurarVistaDatosForm.getShowTotalFilas().booleanValue()) && (esDouble(valorCelda) != null) && (f < totalxFilas.size()))
                    {
                      ObjetoValorNombre total = (ObjetoValorNombre)totalxFilas.get(f);
                      Double valor = Double.valueOf(0.0D);
                      if (esDouble(total.getValor()) != null) {
                        valor = Double.valueOf(esDouble(total.getValor()).doubleValue() + esDouble(valorCelda).doubleValue());
                      }
                      total.setValor(valor.toString());
                      totalxFilas.set(f, total);
                    }
                  }
                  datoCelda = new DatoCelda();
                  if (!variableId.equals(TipoVariable.getTipoVariableAlerta().toString())) {
                    datoCelda.setValor((valorCelda != null) && (!valorCelda.equals("")) ? VgcFormatter.formatearNumero(Double.valueOf(Double.parseDouble(valorCelda.replace(",", ""))), 2) : "");
                  } else {
                    datoCelda.setValor(valorCelda);
                  }
                  datoCelda.setEsAlerta(new Boolean(false));
                  if ((!variableId.equals("TF")) && (!variableId.equals("TC")) && (variableId != null) && (!variableId.equals("")) && (new Long(variableId).equals(TipoVariable.getTipoVariableAlerta()))) {
                    datoCelda.setEsAlerta(new Boolean(true));
                  }
                  datoCelda.setAlineacion("center");
                  
                  filaDatos.add(datoCelda);
                }
              }
              else
              {
                ObjetoValorNombre objeto = (ObjetoValorNombre)totalxFilas.get(f);
                
                datoCelda = new DatoCelda();
                datoCelda.setValor(objeto.getValor() != null ? VgcFormatter.formatearNumero(Double.valueOf(Double.parseDouble(objeto.getValor())), 2) : "");
                datoCelda.setEsAlerta(new Boolean(false));
                datoCelda.setAlineacion("center");
                
                filaDatos.add(datoCelda);
              }
            }
          }
          else
          {
            filaDatos = new ArrayList();
            
            valorCelda = fila.getNombre();
            datoCelda = new DatoCelda();
            datoCelda.setValor(valorCelda);
            datoCelda.setEsAlerta(new Boolean(false));
            datoCelda.setAlineacion("center");
            
            filaDatos.add(datoCelda);
            if ((!porColumnas.booleanValue()) && (hayVariables) && (columnasImpresas - 1 > 0)) {
              for (int c = 0; c < columnasImpresas - 1; c++)
              {
                valorCelda = "";
                datoCelda = new DatoCelda();
                datoCelda.setValor(valorCelda);
                datoCelda.setEsAlerta(new Boolean(false));
                datoCelda.setAlineacion("center");
                
                filaDatos.add(datoCelda);
              }
            }
            for (int c = 0; c < totalxColumnas.size(); c++)
            {
              ObjetoValorNombre objeto = (ObjetoValorNombre)totalxColumnas.get(c);
              
              datoCelda = new DatoCelda();
              datoCelda.setValor(objeto.getValor() != null ? VgcFormatter.formatearNumero(Double.valueOf(Double.parseDouble(objeto.getValor())), 2) : "");
              datoCelda.setEsAlerta(new Boolean(false));
              datoCelda.setAlineacion("center");
              
              filaDatos.add(datoCelda);
            }
          }
          matrizDatos.add(filaDatos);
        }
      }
      strategosIndicadoresService.close();
    }
    strategosVistasDatosService.close();
    
    configurarVistaDatosForm.setMatrizDatos(matrizDatos);
    
    Integer anchoTablaDatos = Integer.valueOf(0);
    if ((configurarVistaDatosForm.getAtributos() != null) && (configurarVistaDatosForm.getAtributos().size() > 0)) {
      for (int f = 0; f < configurarVistaDatosForm.getAtributos().size(); f++)
      {
        TipoAtributo tipoAtributo = (TipoAtributo)configurarVistaDatosForm.getAtributos().get(f);
        if (tipoAtributo.getVisible().booleanValue()) {
          if ((tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoSerie().byteValue()) && (haySerie)) {
            anchoTablaDatos = Integer.valueOf(anchoTablaDatos.intValue() + new Integer(tipoAtributo.getAncho()).intValue());
          } else if (tipoAtributo.getTipoAtributoId().byteValue() != TipoAtributo.getTipoAtributoSerie().byteValue()) {
            anchoTablaDatos = Integer.valueOf(anchoTablaDatos.intValue() + new Integer(tipoAtributo.getAncho()).intValue());
          }
        }
      }
    } else {
      anchoTablaDatos = Integer.valueOf(150);
    }
    anchoTablaDatos = Integer.valueOf(anchoTablaDatos.intValue() + configurarVistaDatosForm.getColumnas().size() * 150);
    
    configurarVistaDatosForm.setAnchoTablaDatos(anchoTablaDatos);
  }
  
  private void setValoresSelectores(Map<String, String> dimensiones, ConfigurarReporteGraficoForm configurarVistaDatosForm)
  {
    String selectorId = "";
    String valorSelector = "";
    String valorSelectorTiempoDesde = "";
    String valorSelectorTiempoHasta = "";
    if (!configurarVistaDatosForm.getNombreSelector1().equals(""))
    {
      selectorId = configurarVistaDatosForm.getSelector1Id().toString();
      if (new Byte(selectorId).equals(TipoDimension.getTipoDimensionTiempo()))
      {
        valorSelectorTiempoDesde = configurarVistaDatosForm.getValorSelectorTiempoDesde();
        valorSelectorTiempoHasta = configurarVistaDatosForm.getValorSelectorTiempoHasta();
        if (valorSelectorTiempoDesde.equals("")) {
          valorSelectorTiempoDesde = "0_0";
        }
        if (valorSelectorTiempoHasta.equals("")) {
          valorSelectorTiempoHasta = "0_0";
        }
      }
      else
      {
        valorSelector = configurarVistaDatosForm.getValorSelector1();
        if (valorSelector.equals("")) {
          valorSelector = "0";
        }
      }
      setMiembroDimension(dimensiones, new Byte(selectorId), valorSelector, valorSelectorTiempoDesde, valorSelectorTiempoHasta);
    }
    if (!configurarVistaDatosForm.getNombreSelector2().equals(""))
    {
      selectorId = configurarVistaDatosForm.getSelector2Id().toString();
      if (new Byte(selectorId).equals(TipoDimension.getTipoDimensionTiempo()))
      {
        valorSelectorTiempoDesde = configurarVistaDatosForm.getValorSelectorTiempoDesde();
        valorSelectorTiempoHasta = configurarVistaDatosForm.getValorSelectorTiempoHasta();
        if (valorSelectorTiempoDesde.equals("")) {
          valorSelectorTiempoDesde = "0_0";
        }
        if (valorSelectorTiempoHasta.equals("")) {
          valorSelectorTiempoHasta = "0_0";
        }
      }
      else
      {
        valorSelector = configurarVistaDatosForm.getValorSelector2();
        if (valorSelector.equals("")) {
          valorSelector = "0";
        }
      }
      setMiembroDimension(dimensiones, new Byte(selectorId), valorSelector, valorSelectorTiempoDesde, valorSelectorTiempoHasta);
    }
    if (!configurarVistaDatosForm.getNombreSelector3().equals(""))
    {
      selectorId = configurarVistaDatosForm.getSelector3Id().toString();
      if (new Byte(selectorId).equals(TipoDimension.getTipoDimensionTiempo()))
      {
        valorSelectorTiempoDesde = configurarVistaDatosForm.getValorSelectorTiempoDesde();
        valorSelectorTiempoHasta = configurarVistaDatosForm.getValorSelectorTiempoHasta();
        if (valorSelectorTiempoDesde.equals("")) {
          valorSelectorTiempoDesde = "0_0";
        }
        if (valorSelectorTiempoHasta.equals("")) {
          valorSelectorTiempoHasta = "0_0";
        }
      }
      else
      {
        valorSelector = configurarVistaDatosForm.getValorSelector3();
        if (valorSelector.equals("")) {
          valorSelector = "0";
        }
      }
      setMiembroDimension(dimensiones, new Byte(selectorId), valorSelector, valorSelectorTiempoDesde, valorSelectorTiempoHasta);
    }
    if (!configurarVistaDatosForm.getNombreSelector4().equals(""))
    {
      selectorId = configurarVistaDatosForm.getSelector4Id().toString();
      if (new Byte(selectorId).equals(TipoDimension.getTipoDimensionTiempo()))
      {
        valorSelectorTiempoDesde = configurarVistaDatosForm.getValorSelectorTiempoDesde();
        valorSelectorTiempoHasta = configurarVistaDatosForm.getValorSelectorTiempoHasta();
        if (valorSelectorTiempoDesde.equals("")) {
          valorSelectorTiempoDesde = "0_0";
        }
        if (valorSelectorTiempoHasta.equals("")) {
          valorSelectorTiempoHasta = "0_0";
        }
      }
      else
      {
        valorSelector = configurarVistaDatosForm.getValorSelector4();
        if (valorSelector.equals("")) {
          valorSelector = "0";
        }
      }
      setMiembroDimension(dimensiones, new Byte(selectorId), valorSelector, valorSelectorTiempoDesde, valorSelectorTiempoHasta);
    }
  }
  
  private void setMiembroDimension(Map<String, String> dimensiones, Byte dimensionId, String miembroId, String tiempoDesdeId, String tiempoHastaId)
  {
    if (dimensionId.equals(TipoDimension.getTipoDimensionVariable()))
    {
      dimensiones.remove("variableId");
      dimensiones.put("variableId", miembroId);
    }
    else if (dimensionId.equals(TipoDimension.getTipoDimensionAtributo()))
    {
      dimensiones.remove("atributoId");
      dimensiones.put("atributoId", miembroId);
    }
    else if (dimensionId.equals(TipoDimension.getTipoDimensionTiempo()))
    {
      if (tiempoDesdeId != null)
      {
        dimensiones.remove("tiempoDesdeId");
        dimensiones.put("tiempoDesdeId", tiempoDesdeId);
        
        dimensiones.remove("tiempoHastaId");
        dimensiones.put("tiempoHastaId", tiempoHastaId);
      }
      else
      {
        dimensiones.remove("tiempoId");
        dimensiones.put("tiempoId", miembroId);
      }
    }
    else if (dimensionId.equals(TipoDimension.getTipoDimensionOrganizacion()))
    {
      dimensiones.remove("organizacionId");
      dimensiones.put("organizacionId", miembroId);
    }
    else if (dimensionId.equals(TipoDimension.getTipoDimensionPlan()))
    {
      dimensiones.remove("planId");
      dimensiones.put("planId", miembroId);
    }
    else if (dimensionId.equals(TipoDimension.getTipoDimensionIndicador()))
    {
      dimensiones.remove("indicadorId");
      dimensiones.put("indicadorId", miembroId);
    }
  }
  
  private void inicializarListaMiembros(Map<String, String> dimensiones)
  {
    dimensiones.put("variableId", "");
    dimensiones.put("atributoId", "");
    dimensiones.put("tiempoId", "");
    dimensiones.put("tiempoDesdeId", "");
    dimensiones.put("tiempoHastaId", "");
    dimensiones.put("organizacionId", "");
    dimensiones.put("planId", "");
    dimensiones.put("indicadorId", "");
  }
  
  private List<Indicador> BuscarIndicadores(ConfigurarReporteGraficoForm configurarVistaDatosForm)
  {
    List<Indicador> indicadores = new ArrayList();
    List<Long> indicadoresMatriz = new ArrayList();
    if (configurarVistaDatosForm.getMiembrosIndicador() != null)
    {
      for (int k = 0; k < configurarVistaDatosForm.getMiembrosIndicador().size(); k++)
      {
        ObjetoValorNombre indicador = (ObjetoValorNombre)configurarVistaDatosForm.getMiembrosIndicador().get(k);
        indicadoresMatriz.add(new Long(indicador.getValor()));
      }
      if (indicadoresMatriz.size() > 0)
      {
        Map<String, Object> filtros = new HashMap();
        filtros.put("indicadores", indicadoresMatriz);
        
        List<String> orderBy = new ArrayList();
        for (int k = 1; k < 6; k++) {
          for (Iterator<?> iterador = configurarVistaDatosForm.getAtributos().iterator(); iterador.hasNext();)
          {
            TipoAtributo tipoAtributo = (TipoAtributo)iterador.next();
            if (tipoAtributo.getAgrupar().booleanValue())
            {
              if ((tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoOrganizacion().byteValue()) && (k == 1))
              {
                orderBy.add("asc");
                orderBy.add("organizacionId");
                break;
              }
              if ((tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoClase().byteValue()) && (k == 2))
              {
                orderBy.add("asc");
                orderBy.add("claseId");
                break;
              }
              if ((tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoNombre().byteValue()) && (k == 3))
              {
                orderBy.add("asc");
                orderBy.add("nombre");
                break;
              }
              if ((tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoFrecuencia().byteValue()) && (k == 4))
              {
                orderBy.add("asc");
                orderBy.add("frecuencia");
                break;
              }
              if ((tipoAtributo.getTipoAtributoId().byteValue() == TipoAtributo.getTipoAtributoUnidadMedida().byteValue()) && (k == 5))
              {
                orderBy.add("asc");
                orderBy.add("unidadId");
                break;
              }
            }
          }
        }
        if (orderBy.size() > 0) {
          filtros.put("orderBy", orderBy);
        }
        StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
        indicadores = strategosIndicadoresService.getIndicadores(filtros);
        strategosIndicadoresService.close();
      }
    }
    return indicadores;
  }
  
  private Double esDouble(String valorString)
  {
    Double valor = null;
    try
    {
      valor = Double.valueOf(Double.parseDouble(valorString.replace(",", "")));
    }
    catch (Exception e)
    {
      valor = Double.valueOf(0.0D);
    }
    return valor;
  }
}
