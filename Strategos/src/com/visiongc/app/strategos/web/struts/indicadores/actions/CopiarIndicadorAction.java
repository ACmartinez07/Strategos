package com.visiongc.app.strategos.web.struts.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.CategoriaIndicador;
import com.visiongc.app.strategos.indicadores.model.CategoriaIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.InsumoFormulaPK;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.PrioridadIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm;
import com.visiongc.app.strategos.web.struts.indicadores.validators.IndicadorValidator;
import com.visiongc.app.strategos.web.struts.util.ObjetosCopia;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class CopiarIndicadorAction
  extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    String forward = mapping.getParameter();
    
    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    if (cancelar) {
      return getForwardBack(request, 1, true);
    }
    if ((ts != null) && (!ts.equals(""))) {
      forward = "finalizarCopiarIndicador";
    }
    ActionMessages messages = getMessages(request);
    
    EditarIndicadorForm editarIndicadorForm = (EditarIndicadorForm)form;
    
    String showPresentacion = request.getParameter("showPresentacion") != null ? request.getParameter("showPresentacion").toString() : "0";
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    ConfiguracionUsuario presentacion = new ConfiguracionUsuario();
    ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
    pk.setConfiguracionBase("Strategos.Wizar.Copiar.ShowPresentacion");
    pk.setObjeto("ShowPresentacion");
    pk.setUsuarioId(getUsuarioConectado(request).getUsuarioId());
    presentacion.setPk(pk);
    presentacion.setData(showPresentacion);
    frameworkService.saveConfiguracionUsuario(presentacion, getUsuarioConectado(request));
    frameworkService.close();
    
    int respuesta = 10000;
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    List<ObjetosCopia> clasesCopiadas = new ArrayList();
    clasesCopiadas.add(new ObjetosCopia(editarIndicadorForm.getClaseId(), editarIndicadorForm.getClaseId(), editarIndicadorForm.getOrganizacionId()));
    
    respuesta = SalvarIndicador(editarIndicadorForm, clasesCopiadas, strategosIndicadoresService, request);
    if (respuesta == 10000)
    {
      strategosIndicadoresService.unlockObject(request.getSession().getId(), editarIndicadorForm.getIndicadorId());
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
      destruirPoolLocksUsoEdicion(request, strategosIndicadoresService);
      forward = "finalizarCopiarIndicador";
    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }
    strategosIndicadoresService.close();
    
    saveMessages(request, messages);
    
    request.getSession().setAttribute("GuardarIndicadorAction.ultimoTs", ts);
    if (forward.equals("finalizarCopiarIndicador")) {
      return getForwardBack(request, 1, true);
    }
    return getForwardBack(request, 1, true);
  }
  
  public int CopiarIndicador(Plan planOrigen, Plan planDestino, List<ObjetosCopia> clasesCopiadas, HttpServletRequest request)
  {
    int respuesta = 10000;
    
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    Indicador indicadorFuente = new Indicador();
    Map<String, Object> filtros = new HashMap();
    Indicador indicadorCopia = new Indicador();
    List<Indicador> indicadores = new ArrayList();
    List<Indicador> indicadoresCopia = new ArrayList();
    List<Indicador> indicadoresFuentes = new ArrayList();
    if (planOrigen.getIndTotalImputacionId() != null)
    {
      indicadorFuente = (Indicador)strategosIndicadoresService.load(Indicador.class, planOrigen.getIndTotalImputacionId());
      
      filtros.put("organizacionId", planDestino.getOrganizacionId());
      filtros.put("nombre", indicadorFuente.getNombre());
      filtros.put("claseId", planDestino.getClaseIdIndicadoresTotales());
      indicadores = strategosIndicadoresService.getIndicadores(filtros);
      if (indicadores.size() > 0)
      {
        Iterator<?> iter = indicadores.iterator();
        if (iter.hasNext())
        {
          indicadorCopia = (Indicador)iter.next();
          planDestino.setIndTotalImputacionId(indicadorCopia.getIndicadorId());
          respuesta = 10000;
        }
      }
      else
      {
        indicadorCopia = new Indicador();
        
        indicadorCopia.setIndicadorId(new Long(0L));
        indicadorCopia.setClaseId(planDestino.getClaseIdIndicadoresTotales());
        indicadorCopia.setOrganizacionId(planDestino.getOrganizacionId());
        indicadorCopia.setNombre(indicadorFuente.getNombre());
        indicadorCopia.setNombreCorto(indicadorFuente.getNombreCorto());
        
        indicadorCopia.setEscalaCualitativa(new ArrayList());
        indicadorCopia.setSeriesIndicador(new HashSet());
        indicadorCopia.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionNormal());
        
        setDatosBasicos(indicadorCopia, indicadorFuente, request);
        setAsociar(indicadorCopia, indicadorFuente, request);
        setResponsables(indicadorCopia, indicadorFuente);
        setParametros(indicadorCopia, indicadorFuente, request);
        setAlertas(indicadorCopia, indicadorFuente, request);
        SerieIndicador serieReal = setSeriesTiempo(indicadorCopia, indicadorFuente);
        respuesta = setDefinicion(indicadorCopia, indicadorFuente, clasesCopiadas, serieReal, false, false, indicadoresCopia, indicadoresFuentes, strategosIndicadoresService, request);
        setRelaciones(indicadorCopia, indicadorFuente);
        
        respuesta = strategosIndicadoresService.saveIndicador(indicadorCopia, (Usuario)request.getSession().getAttribute("usuario"));
        if (respuesta == 10003)
        {
          filtros = new HashMap();
          
          filtros.put("claseId", indicadorCopia.getClaseId());
          filtros.put("nombre", indicadorCopia.getNombre());
          List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
          if (inds.size() > 0)
          {
            indicadorCopia = (Indicador)inds.get(0);
            respuesta = 10000;
          }
        }
        if (respuesta == 10000) {
          planDestino.setIndTotalImputacionId(indicadorCopia.getIndicadorId());
        }
      }
    }
    if ((respuesta == 10000) && (planOrigen.getIndTotalIniciativaId() != null))
    {
      indicadorFuente = (Indicador)strategosIndicadoresService.load(Indicador.class, planOrigen.getIndTotalIniciativaId());
      filtros = new HashMap();
      
      filtros.put("organizacionId", planDestino.getOrganizacionId());
      filtros.put("nombre", indicadorFuente.getNombre());
      filtros.put("claseId", planDestino.getClaseIdIndicadoresTotales());
      indicadores = strategosIndicadoresService.getIndicadores(filtros);
      if (indicadores.size() > 0)
      {
        Iterator<?> iter = indicadores.iterator();
        if (iter.hasNext())
        {
          indicadorCopia = (Indicador)iter.next();
          planDestino.setIndTotalIniciativaId(indicadorCopia.getIndicadorId());
          respuesta = 10000;
        }
      }
      else
      {
        indicadorCopia = new Indicador();
        
        indicadorCopia.setIndicadorId(new Long(0L));
        indicadorCopia.setClaseId(planDestino.getClaseIdIndicadoresTotales());
        indicadorCopia.setOrganizacionId(planDestino.getOrganizacionId());
        indicadorCopia.setNombre(indicadorFuente.getNombre());
        indicadorCopia.setNombreCorto(indicadorFuente.getNombreCorto());
        
        indicadorCopia.setEscalaCualitativa(new ArrayList());
        indicadorCopia.setSeriesIndicador(new HashSet());
        indicadorCopia.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionNormal());
        
        setDatosBasicos(indicadorCopia, indicadorFuente, request);
        setAsociar(indicadorCopia, indicadorFuente, request);
        setResponsables(indicadorCopia, indicadorFuente);
        setParametros(indicadorCopia, indicadorFuente, request);
        setAlertas(indicadorCopia, indicadorFuente, request);
        SerieIndicador serieReal = setSeriesTiempo(indicadorCopia, indicadorFuente);
        setDefinicion(indicadorCopia, indicadorFuente, clasesCopiadas, serieReal, false, false, indicadoresCopia, indicadoresFuentes, strategosIndicadoresService, request);
        setRelaciones(indicadorCopia, indicadorFuente);
        
        respuesta = strategosIndicadoresService.saveIndicador(indicadorCopia, (Usuario)request.getSession().getAttribute("usuario"));
        if (respuesta == 10003)
        {
          filtros = new HashMap();
          
          filtros.put("claseId", indicadorCopia.getClaseId());
          filtros.put("nombre", indicadorCopia.getNombre());
          List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
          if (inds.size() > 0)
          {
            indicadorCopia = (Indicador)inds.get(0);
            respuesta = 10000;
          }
        }
        if (respuesta == 10000) {
          planDestino.setIndTotalIniciativaId(indicadorCopia.getIndicadorId());
        }
      }
    }
    if ((respuesta == 10000) && (planOrigen.getNlAnoIndicadorId() != null))
    {
      indicadorFuente = (Indicador)strategosIndicadoresService.load(Indicador.class, planOrigen.getNlAnoIndicadorId());
      filtros = new HashMap();
      
      filtros.put("organizacionId", planDestino.getOrganizacionId());
      filtros.put("nombre", indicadorFuente.getNombre());
      filtros.put("claseId", planDestino.getClaseId());
      indicadores = strategosIndicadoresService.getIndicadores(filtros);
      if (indicadores.size() > 0)
      {
        Iterator<?> iter = indicadores.iterator();
        if (iter.hasNext())
        {
          indicadorCopia = (Indicador)iter.next();
          planDestino.setNlAnoIndicadorId(indicadorCopia.getIndicadorId());
          respuesta = 10000;
        }
      }
      else
      {
        indicadorCopia = new Indicador();
        
        indicadorCopia.setIndicadorId(new Long(0L));
        indicadorCopia.setClaseId(planDestino.getClaseId());
        indicadorCopia.setOrganizacionId(planDestino.getOrganizacionId());
        indicadorCopia.setNombre(indicadorFuente.getNombre());
        indicadorCopia.setNombreCorto(indicadorFuente.getNombreCorto());
        
        indicadorCopia.setEscalaCualitativa(new ArrayList());
        indicadorCopia.setSeriesIndicador(new HashSet());
        indicadorCopia.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionNormal());
        
        setDatosBasicos(indicadorCopia, indicadorFuente, request);
        setAsociar(indicadorCopia, indicadorFuente, request);
        setResponsables(indicadorCopia, indicadorFuente);
        setParametros(indicadorCopia, indicadorFuente, request);
        setAlertas(indicadorCopia, indicadorFuente, request);
        SerieIndicador serieReal = setSeriesTiempo(indicadorCopia, indicadorFuente);
        setDefinicion(indicadorCopia, indicadorFuente, clasesCopiadas, serieReal, false, false, indicadoresCopia, indicadoresFuentes, strategosIndicadoresService, request);
        setRelaciones(indicadorCopia, indicadorFuente);
        
        respuesta = strategosIndicadoresService.saveIndicador(indicadorCopia, (Usuario)request.getSession().getAttribute("usuario"));
        if (respuesta == 10000) {
          planDestino.setNlAnoIndicadorId(indicadorCopia.getIndicadorId());
        }
      }
    }
    if ((respuesta == 10000) && (planOrigen.getNlParIndicadorId() != null))
    {
      indicadorFuente = (Indicador)strategosIndicadoresService.load(Indicador.class, planOrigen.getNlParIndicadorId());
      filtros = new HashMap();
      
      filtros.put("organizacionId", planDestino.getOrganizacionId());
      filtros.put("nombre", indicadorFuente.getNombre());
      filtros.put("claseId", planDestino.getClaseId());
      indicadores = strategosIndicadoresService.getIndicadores(filtros);
      if (indicadores.size() > 0)
      {
        Iterator<?> iter = indicadores.iterator();
        if (iter.hasNext())
        {
          indicadorCopia = (Indicador)iter.next();
          planDestino.setNlParIndicadorId(indicadorCopia.getIndicadorId());
          respuesta = 10000;
        }
      }
      else
      {
        indicadorCopia = new Indicador();
        
        indicadorCopia.setIndicadorId(new Long(0L));
        indicadorCopia.setClaseId(planDestino.getClaseId());
        indicadorCopia.setOrganizacionId(planDestino.getOrganizacionId());
        indicadorCopia.setNombre(indicadorFuente.getNombre());
        indicadorCopia.setNombreCorto(indicadorFuente.getNombreCorto());
        
        indicadorCopia.setEscalaCualitativa(new ArrayList());
        indicadorCopia.setSeriesIndicador(new HashSet());
        indicadorCopia.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionNormal());
        
        setDatosBasicos(indicadorCopia, indicadorFuente, request);
        setAsociar(indicadorCopia, indicadorFuente, request);
        setResponsables(indicadorCopia, indicadorFuente);
        setParametros(indicadorCopia, indicadorFuente, request);
        setAlertas(indicadorCopia, indicadorFuente, request);
        SerieIndicador serieReal = setSeriesTiempo(indicadorCopia, indicadorFuente);
        setDefinicion(indicadorCopia, indicadorFuente, clasesCopiadas, serieReal, false, false, indicadoresCopia, indicadoresFuentes, strategosIndicadoresService, request);
        setRelaciones(indicadorCopia, indicadorFuente);
        
        respuesta = strategosIndicadoresService.saveIndicador(indicadorCopia, (Usuario)request.getSession().getAttribute("usuario"));
        if (respuesta == 10003)
        {
          filtros = new HashMap();
          
          filtros.put("claseId", indicadorCopia.getClaseId());
          filtros.put("nombre", indicadorCopia.getNombre());
          List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
          if (inds.size() > 0)
          {
            indicadorCopia = (Indicador)inds.get(0);
            respuesta = 10000;
          }
        }
        if (respuesta == 10000) {
          planDestino.setNlParIndicadorId(indicadorCopia.getIndicadorId());
        }
      }
    }
    strategosIndicadoresService.close();
    
    return respuesta;
  }
  
  public int CopiarIndicador(Long organizacionIdDestino, Perspectiva perspectivaOrigen, Perspectiva perspectivaDestino, List<ObjetosCopia> clasesCopiadas, HttpServletRequest request)
  {
    int respuesta = 10000;
    
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    Map<String, Object> filtros = new HashMap();
    Indicador indicadorFuente = new Indicador();
    Indicador indicadorCopia = new Indicador();
    List<Indicador> indicadores = new ArrayList();
    List<Indicador> indicadoresCopia = new ArrayList();
    List<Indicador> indicadoresFuentes = new ArrayList();
    if ((perspectivaOrigen.getNlAnoIndicadorId() != null) && (perspectivaDestino.getClaseId() != null))
    {
      indicadorFuente = (Indicador)strategosIndicadoresService.load(Indicador.class, perspectivaOrigen.getNlAnoIndicadorId());
      
      filtros.put("organizacionId", organizacionIdDestino.toString());
      filtros.put("nombre", indicadorFuente.getNombre());
      filtros.put("claseId", perspectivaDestino.getClaseId().toString());
      indicadores = strategosIndicadoresService.getIndicadores(filtros);
      if (indicadores.size() > 0)
      {
        Iterator<?> iter = indicadores.iterator();
        if (iter.hasNext())
        {
          indicadorCopia = (Indicador)iter.next();
          perspectivaDestino.setNlAnoIndicadorId(indicadorCopia.getIndicadorId());
          respuesta = 10000;
        }
      }
      else
      {
        indicadorCopia = new Indicador();
        indicadorCopia.setIndicadorId(new Long(0L));
        indicadorCopia.setClaseId(perspectivaDestino.getClaseId());
        indicadorCopia.setOrganizacionId(organizacionIdDestino);
        indicadorCopia.setNombre(indicadorFuente.getNombre());
        indicadorCopia.setNombreCorto(indicadorFuente.getNombreCorto());
        
        indicadorCopia.setEscalaCualitativa(new ArrayList());
        indicadorCopia.setSeriesIndicador(new HashSet());
        indicadorCopia.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionNormal());
        
        setDatosBasicos(indicadorCopia, indicadorFuente, request);
        setAsociar(indicadorCopia, indicadorFuente, request);
        setResponsables(indicadorCopia, indicadorFuente);
        setParametros(indicadorCopia, indicadorFuente, request);
        setAlertas(indicadorCopia, indicadorFuente, request);
        SerieIndicador serieReal = setSeriesTiempo(indicadorCopia, indicadorFuente);
        setDefinicion(indicadorCopia, indicadorFuente, clasesCopiadas, serieReal, false, false, indicadoresCopia, indicadoresFuentes, strategosIndicadoresService, request);
        setRelaciones(indicadorCopia, indicadorFuente);
        
        respuesta = strategosIndicadoresService.saveIndicador(indicadorCopia, (Usuario)request.getSession().getAttribute("usuario"));
        if (respuesta == 10003)
        {
          filtros = new HashMap();
          
          filtros.put("claseId", indicadorCopia.getClaseId());
          filtros.put("nombre", indicadorCopia.getNombre());
          List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
          if (inds.size() > 0)
          {
            indicadorCopia = (Indicador)inds.get(0);
            respuesta = 10000;
          }
        }
        if (respuesta == 10000) {
          perspectivaDestino.setNlAnoIndicadorId(indicadorCopia.getIndicadorId());
        }
      }
    }
    if ((respuesta == 10000) && (perspectivaOrigen.getNlParIndicadorId() != null) && (perspectivaDestino.getClaseId() != null))
    {
      indicadorFuente = (Indicador)strategosIndicadoresService.load(Indicador.class, perspectivaOrigen.getNlParIndicadorId());
      
      filtros = new HashMap();
      
      filtros.put("organizacionId", organizacionIdDestino.toString());
      filtros.put("nombre", indicadorFuente.getNombre());
      filtros.put("claseId", perspectivaDestino.getClaseId().toString());
      indicadores = strategosIndicadoresService.getIndicadores(filtros);
      if (indicadores.size() > 0)
      {
        Iterator<?> iter = indicadores.iterator();
        if (iter.hasNext())
        {
          indicadorCopia = (Indicador)iter.next();
          perspectivaDestino.setNlParIndicadorId(indicadorCopia.getIndicadorId());
          respuesta = 10000;
        }
      }
      else
      {
        indicadorCopia = new Indicador();
        
        indicadorCopia.setIndicadorId(new Long(0L));
        indicadorCopia.setClaseId(perspectivaDestino.getClaseId());
        indicadorCopia.setOrganizacionId(organizacionIdDestino);
        indicadorCopia.setNombre(indicadorFuente.getNombre());
        indicadorCopia.setNombreCorto(indicadorFuente.getNombreCorto());
        
        indicadorCopia.setEscalaCualitativa(new ArrayList());
        indicadorCopia.setSeriesIndicador(new HashSet());
        indicadorCopia.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionNormal());
        
        setDatosBasicos(indicadorCopia, indicadorFuente, request);
        setAsociar(indicadorCopia, indicadorFuente, request);
        setResponsables(indicadorCopia, indicadorFuente);
        setParametros(indicadorCopia, indicadorFuente, request);
        setAlertas(indicadorCopia, indicadorFuente, request);
        SerieIndicador serieReal = setSeriesTiempo(indicadorCopia, indicadorFuente);
        setDefinicion(indicadorCopia, indicadorFuente, clasesCopiadas, serieReal, false, false, indicadoresCopia, indicadoresFuentes, strategosIndicadoresService, request);
        setRelaciones(indicadorCopia, indicadorFuente);
        
        respuesta = strategosIndicadoresService.saveIndicador(indicadorCopia, (Usuario)request.getSession().getAttribute("usuario"));
        if (respuesta == 10003)
        {
          filtros = new HashMap();
          
          filtros.put("claseId", indicadorCopia.getClaseId());
          filtros.put("nombre", indicadorCopia.getNombre());
          List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
          if (inds.size() > 0)
          {
            indicadorCopia = (Indicador)inds.get(0);
            respuesta = 10000;
          }
        }
        if (respuesta == 10000) {
          perspectivaDestino.setNlParIndicadorId(indicadorCopia.getIndicadorId());
        }
      }
    }
    strategosIndicadoresService.close();
    
    return respuesta;
  }
  
  public int SalvarIndicador(EditarIndicadorForm editarIndicadorForm, List<ObjetosCopia> clasesCopiadas, StrategosIndicadoresService strategosIndicadoresService, HttpServletRequest request)
  {
    int respuesta = 10000;
    List<Indicador> indicadoresCopia = new ArrayList();
    List<Indicador> indicadoresFuentes = new ArrayList();
    Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
    Indicador indicadorFuente = (Indicador)strategosIndicadoresService.load(Indicador.class, editarIndicadorForm.getIndicadorId());
    Indicador indicadorCopia = new Indicador();
    if (respuesta == 10000)
    {
      indicadorCopia.setIndicadorId(new Long(0L));
      indicadorCopia.setClaseId(indicadorFuente.getClaseId());
      indicadorCopia.setOrganizacionId(indicadorFuente.getOrganizacionId());
      indicadorCopia.setNombre(editarIndicadorForm.getNuevoNombre());
      indicadorCopia.setNombreCorto(editarIndicadorForm.getNuevoNombre());
      
      indicadorCopia.setEscalaCualitativa(new ArrayList());
      indicadorCopia.setSeriesIndicador(new HashSet());
      indicadorCopia.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionNormal());
      
      setDatosBasicos(indicadorCopia, indicadorFuente, request);
      setAsociar(indicadorCopia, indicadorFuente, request);
      setResponsables(indicadorCopia, indicadorFuente);
      setParametros(indicadorCopia, indicadorFuente, request);
      setAlertas(indicadorCopia, indicadorFuente, request);
      SerieIndicador serieReal = setSeriesTiempo(indicadorCopia, indicadorFuente);
      setDefinicion(indicadorCopia, indicadorFuente, clasesCopiadas, serieReal, editarIndicadorForm.getCopiarMediciones().booleanValue(), editarIndicadorForm.getCopiarInsumos().booleanValue(), indicadoresCopia, indicadoresFuentes, strategosIndicadoresService, request);
      setRelaciones(indicadorCopia, indicadorFuente);
      if ((indicadorFuente.getPlanId() != null) && (indicadorFuente.getPlanId().longValue() != 0L) && (indicadorFuente.getPerspectivaId() != null) && (indicadorFuente.getPerspectivaId().longValue() != 0L))
      {
        indicadorCopia.setPlanId(indicadorFuente.getPlanId());
        indicadorCopia.setPerspectivaId(indicadorFuente.getPerspectivaId());
      }
      respuesta = strategosIndicadoresService.saveIndicador(indicadorCopia, usuario);
    }
    if (respuesta == 10003)
    {
      Map<String, Object> filtros = new HashMap();
      
      filtros.put("claseId", indicadorCopia.getClaseId());
      filtros.put("nombre", indicadorCopia.getNombre());
      List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
      if (inds.size() > 0)
      {
        indicadorCopia = (Indicador)inds.get(0);
        respuesta = 10000;
      }
    }
    if ((respuesta == 10000) && (editarIndicadorForm.getCopiarMediciones().booleanValue()))
    {
      CopiarMediciones(indicadorCopia, indicadorFuente, strategosIndicadoresService, request);
      
      indicadoresCopia.add(indicadorCopia);
      StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
      List<Object> indicadores = new ArrayList();
      indicadores.addAll(indicadoresCopia);
      respuesta = strategosMedicionesService.actualizarUltimaMedicionIndicadores(indicadores, null, getUsuarioConectado(request));
      strategosMedicionesService.close();
    }
    return respuesta;
  }
  
  public int CopiarIndicadores(List<ObjetosCopia> clasesCopiadas, boolean copiarMediciones, boolean copiarInsumos, HttpServletRequest request)
  {
    int respuesta = 10000;
    
    List<Indicador> indicadoresCopia = new ArrayList();
    List<Indicador> indicadoresFuentes = new ArrayList();
    Map<String, Object> filtros = new HashMap();
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    for (Iterator<ObjetosCopia> iterClase = clasesCopiadas.iterator(); iterClase.hasNext();)
    {
      ObjetosCopia claseCopiada = (ObjetosCopia)iterClase.next();
      filtros.put("claseId", claseCopiada.getObjetoOriginalId());
      
      List<?> indicadores = strategosIndicadoresService.getIndicadores(0, 0, "naturaleza", "desc", true, filtros, null, null, Boolean.valueOf(false)).getLista();
      for (Iterator<?> iter = indicadores.iterator(); iter.hasNext();)
      {
        Indicador indicadorFuente = (Indicador)iter.next();
        
        boolean existeInidicador= buscarIndicadorOriginal(indicadoresFuentes,indicadorFuente);
        if (!existeInidicador)
        {
          Indicador indicadorCopia = new Indicador();
          
          indicadorCopia.setIndicadorId(new Long(0L));
          indicadorCopia.setClaseId(claseCopiada.getObjetoCopiaId());
          indicadorCopia.setOrganizacionId(claseCopiada.getOrganizacionDestinoId());
          indicadorCopia.setNombre(indicadorFuente.getNombre());
          indicadorCopia.setNombreCorto(indicadorFuente.getNombreCorto());
          
          indicadorCopia.setEscalaCualitativa(new ArrayList());
          indicadorCopia.setSeriesIndicador(new HashSet());
          indicadorCopia.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionNormal());
          
          setDatosBasicos(indicadorCopia, indicadorFuente, request);
          setAsociar(indicadorCopia, indicadorFuente, request);
          setResponsables(indicadorCopia, indicadorFuente);
          setParametros(indicadorCopia, indicadorFuente, request);
          setAlertas(indicadorCopia, indicadorFuente, request);
          SerieIndicador serieReal = setSeriesTiempo(indicadorCopia, indicadorFuente);
          respuesta = setDefinicion(indicadorCopia, indicadorFuente, clasesCopiadas, serieReal, copiarMediciones, copiarInsumos, indicadoresCopia, indicadoresFuentes, strategosIndicadoresService, request);
          if (respuesta != 10000) {
            break;
          }
          setRelaciones(indicadorCopia, indicadorFuente);
          if ((indicadorFuente.getPlanId() != null) && (indicadorFuente.getPlanId().longValue() != 0L) && (indicadorFuente.getPerspectivaId() != null) && (indicadorFuente.getPerspectivaId().longValue() != 0L))
          {
            indicadorCopia.setPlanId(indicadorFuente.getPlanId());
            indicadorCopia.setPerspectivaId(indicadorFuente.getPerspectivaId());
          }
          respuesta = strategosIndicadoresService.saveIndicador(indicadorCopia, getUsuarioConectado(request));
          if (respuesta == 10003)
          {
            filtros = new HashMap();
            
            filtros.put("claseId", indicadorCopia.getClaseId());
            filtros.put("nombre", indicadorCopia.getNombre());
            List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
            if (inds.size() > 0)
            {
              indicadorCopia = (Indicador)inds.get(0);
              respuesta = 10000;
            }
          }
          if (respuesta == 10000)
          {
            indicadoresCopia.add(indicadorCopia);
            indicadoresFuentes.add(indicadorCopia);
          }
          if ((respuesta == 10000) && (copiarMediciones)) {
            CopiarMediciones(indicadorCopia, indicadorFuente, strategosIndicadoresService, request);
          } else {
            if (respuesta != 10000) {
              break;
            }
          }
        }
      }
    }
    if (indicadoresCopia.size() > 0)
    {
      StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
      List<Object> indicadoresIds = new ArrayList();
      indicadoresIds.addAll(indicadoresCopia);
      respuesta = strategosMedicionesService.actualizarUltimaMedicionIndicadores(indicadoresIds, null, getUsuarioConectado(request));
      strategosMedicionesService.close();
    }
    strategosIndicadoresService.close();
    
    return respuesta;
  }
  
  private boolean buscarIndicadorOriginal(List<Indicador> indicadoresFuentes, Indicador indicadorFuente){
	  for(Indicador i:indicadoresFuentes) if (i.getIndicadorId()== indicadorFuente.getIndicadorId()) return true; return false;
  }
  
  public Indicador CopiarIndicador(Indicador indicadorFuente, Long clasePadreDestinoId, List<ObjetosCopia> clasesCopiadas, boolean copiarMediciones, boolean copiarInsumos, List<Indicador> indicadoresCopia, List<Indicador> indicadoresFuentes, StrategosIndicadoresService strategosIndicadoresService, HttpServletRequest request)
  {
    int respuesta = 10000;
    
    Indicador indicadorCopia = new Indicador();
    Long organizacionId = null;
    Long claseDestinoId = clasePadreDestinoId;
    for (Iterator<ObjetosCopia> iterClase = clasesCopiadas.iterator(); iterClase.hasNext();)
    {
      ObjetosCopia claseCopiada = (ObjetosCopia)iterClase.next();
      if (indicadorFuente.getClaseId().longValue() == claseCopiada.getObjetoOriginalId().longValue())
      {
        claseDestinoId = claseCopiada.getObjetoCopiaId();
        organizacionId = claseCopiada.getOrganizacionDestinoId();
        break;
      }
    }
    if (organizacionId == null)
    {
      ClaseIndicadores clase = (ClaseIndicadores)strategosIndicadoresService.load(ClaseIndicadores.class, clasePadreDestinoId);
      if (clase != null) {
        organizacionId = clase.getOrganizacionId();
      } else {
        organizacionId = indicadorFuente.getOrganizacionId();
      }
    }
    indicadorCopia.setIndicadorId(new Long(0L));
    indicadorCopia.setClaseId(claseDestinoId);
    indicadorCopia.setOrganizacionId(organizacionId);
    indicadorCopia.setNombre(indicadorFuente.getNombre());
    indicadorCopia.setNombreCorto(indicadorFuente.getNombreCorto());
    
    indicadorCopia.setEscalaCualitativa(new ArrayList());
    indicadorCopia.setSeriesIndicador(new HashSet());
    indicadorCopia.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionNormal());
    
    setDatosBasicos(indicadorCopia, indicadorFuente, request);
    setAsociar(indicadorCopia, indicadorFuente, request);
    setResponsables(indicadorCopia, indicadorFuente);
    setParametros(indicadorCopia, indicadorFuente, request);
    setAlertas(indicadorCopia, indicadorFuente, request);
    SerieIndicador serieReal = setSeriesTiempo(indicadorCopia, indicadorFuente);
    setDefinicion(indicadorCopia, indicadorFuente, clasesCopiadas, serieReal, copiarMediciones, copiarInsumos, indicadoresCopia, indicadoresFuentes, strategosIndicadoresService, request);
    setRelaciones(indicadorCopia, indicadorFuente);
    if ((indicadorFuente.getPlanId() != null) && (indicadorFuente.getPlanId().longValue() != 0L) && (indicadorFuente.getPerspectivaId() != null) && (indicadorFuente.getPerspectivaId().longValue() != 0L))
    {
      indicadorCopia.setPlanId(indicadorFuente.getPlanId());
      indicadorCopia.setPerspectivaId(indicadorFuente.getPerspectivaId());
    }
    respuesta = strategosIndicadoresService.saveIndicador(indicadorCopia, getUsuarioConectado(request));
    if (respuesta == 10003)
    {
      Map<String, Object> filtros = new HashMap();
      
      filtros.put("claseId", indicadorCopia.getClaseId());
      filtros.put("nombre", indicadorCopia.getNombre());
      List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
      if (inds.size() > 0)
      {
        indicadorCopia = (Indicador)inds.get(0);
        respuesta = 10000;
      }
    }
    if (respuesta == 10000)
    {
      indicadoresCopia.add(indicadorCopia);
      indicadoresFuentes.add(indicadorFuente);
    }
    else
    {
      indicadorCopia = null;
    }
    if ((respuesta == 10000) && (copiarMediciones)) {
      CopiarMediciones(indicadorCopia, indicadorFuente, strategosIndicadoresService, request);
    }
    return indicadorCopia;
  }
  
  private void setDatosBasicos(Indicador indicadorCopia, Indicador indicadorFuente, HttpServletRequest request)
  {
    indicadorCopia.setNaturaleza(indicadorFuente.getNaturaleza());
    indicadorCopia.setFrecuencia(indicadorFuente.getFrecuencia());
    indicadorCopia.setTipoSumaMedicion(indicadorFuente.getTipoSumaMedicion());
    indicadorCopia.setOrden(indicadorFuente.getOrden());
    if ((indicadorFuente.getUnidadId() != null) && (indicadorFuente.getUnidadId().longValue() > 0L)) {
      indicadorCopia.setUnidadId(indicadorFuente.getUnidadId());
    } else {
      indicadorCopia.setUnidadId(null);
    }
    if (indicadorFuente.getPrioridad() != null) {
      indicadorCopia.setPrioridad(indicadorFuente.getPrioridad());
    } else {
      indicadorCopia.setPrioridad(PrioridadIndicador.getPrioridadIndicadorBaja());
    }
    indicadorCopia.setMostrarEnArbol(indicadorFuente.getMostrarEnArbol());
    indicadorCopia.setNumeroDecimales(indicadorFuente.getNumeroDecimales());
    if ((indicadorFuente.getDescripcion() != null) && (!indicadorFuente.getDescripcion().equals(""))) {
      indicadorCopia.setDescripcion(indicadorFuente.getDescripcion());
    } else {
      indicadorCopia.setDescripcion(null);
    }
    if ((indicadorFuente.getComportamiento() != null) && (!indicadorFuente.getComportamiento().equals(""))) {
      indicadorCopia.setComportamiento(indicadorFuente.getComportamiento());
    } else {
      indicadorCopia.setComportamiento(null);
    }
    if ((indicadorFuente.getFuente() != null) && (!indicadorFuente.getFuente().equals(""))) {
      indicadorCopia.setFuente(indicadorFuente.getFuente());
    } else {
      indicadorCopia.setFuente(null);
    }
  }
  
  private void setAsociar(Indicador indicadorCopia, Indicador indicadorFuente, HttpServletRequest request)
  {
    indicadorCopia.setIndicadorAsociadoTipo(indicadorFuente.getIndicadorAsociadoTipo());
    indicadorCopia.setIndicadorAsociadoId(indicadorFuente.getIndicadorAsociadoId());
    indicadorCopia.setIndicadorAsociadoRevision(indicadorFuente.getIndicadorAsociadoRevision());
  }
  
  private void setResponsables(Indicador indicadorCopia, Indicador indicadorFuente)
  {
    indicadorCopia.setResponsableFijarMetaId(indicadorFuente.getResponsableFijarMetaId());
    indicadorCopia.setResponsableLograrMetaId(indicadorFuente.getResponsableLograrMetaId());
    indicadorCopia.setResponsableSeguimientoId(indicadorFuente.getResponsableSeguimientoId());
    indicadorCopia.setResponsableCargarMetaId(indicadorFuente.getResponsableCargarMetaId());
    indicadorCopia.setResponsableCargarEjecutadoId(indicadorFuente.getResponsableCargarEjecutadoId());
  }
  
  private void setParametros(Indicador indicadorCopia, Indicador indicadorFuente, HttpServletRequest request)
  {
    indicadorCopia.setCaracteristica(indicadorFuente.getCaracteristica());
    indicadorCopia.setCorte(indicadorFuente.getCorte());
    indicadorCopia.setTipoCargaMedicion(indicadorFuente.getTipoCargaMedicion());
    indicadorCopia.setGuia(indicadorFuente.getGuia());
    indicadorCopia.setValorInicialCero(indicadorFuente.getValorInicialCero());
    indicadorCopia.setParametroSuperiorValorFijo(indicadorFuente.getParametroSuperiorValorFijo());
    indicadorCopia.setParametroSuperiorIndicadorId(indicadorFuente.getParametroSuperiorIndicadorId());
    indicadorCopia.setParametroInferiorValorFijo(indicadorFuente.getParametroInferiorValorFijo());
    indicadorCopia.setParametroInferiorIndicadorId(indicadorFuente.getParametroInferiorIndicadorId());
  }
  
  private void setAlertas(Indicador indicadorCopia, Indicador indicadorFuente, HttpServletRequest request)
  {
    indicadorCopia.setAlertaTipoZonaAmarilla(indicadorFuente.getAlertaTipoZonaAmarilla());
    indicadorCopia.setAlertaTipoZonaVerde(indicadorFuente.getAlertaTipoZonaVerde());
    indicadorCopia.setAlertaMetaZonaVerde(indicadorFuente.getAlertaMetaZonaVerde());
    indicadorCopia.setAlertaMetaZonaAmarilla(indicadorFuente.getAlertaMetaZonaAmarilla());
    indicadorCopia.setAlertaIndicadorIdZonaVerde(indicadorFuente.getAlertaIndicadorIdZonaVerde());
    indicadorCopia.setAlertaIndicadorIdZonaAmarilla(indicadorFuente.getAlertaIndicadorIdZonaAmarilla());
    indicadorCopia.setAlertaValorVariableZonaAmarilla(indicadorFuente.getAlertaValorVariableZonaAmarilla());
    indicadorCopia.setAlertaValorVariableZonaVerde(indicadorFuente.getAlertaValorVariableZonaVerde());
  }
  
  private SerieIndicador setSeriesTiempo(Indicador indicadorCopia, Indicador indicadorFuente)
  {
    SerieIndicador serieReal = null;
    Set<SerieIndicador> seriesIndicador = indicadorFuente.getSeriesIndicador();
    indicadorCopia.getSeriesIndicador().clear();
    for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext();)
    {
      SerieIndicador serie = (SerieIndicador)i.next();
      SerieIndicador serieIndicador = new SerieIndicador();
      serieIndicador.setIndicador(indicadorCopia);
      serieIndicador.setPk(new SerieIndicadorPK());
      serieIndicador.getPk().setSerieId(serie.getPk().getSerieId());
      serieIndicador.getPk().setIndicadorId(indicadorCopia.getIndicadorId());
      serieIndicador.setFormulas(new HashSet());
      if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue())
      {
        serieIndicador.setNaturaleza(indicadorFuente.getNaturaleza());
        serieReal = new SerieIndicador(serie.getPk(), serie.getNaturaleza(), serie.getFechaBloqueo(), serie.getSerieTiempo(), serie.getIndicador(), serie.getFormulas(), serie.getMediciones(), serie.getIndicadoresCeldas(), serie.getNombre());
      }
      else
      {
        serieIndicador.setNaturaleza(Naturaleza.getNaturalezaSimple());
      }
      indicadorCopia.getSeriesIndicador().add(serieIndicador);
    }
    return serieReal;
  }
  
  private void setRelaciones(Indicador indicadorCopia, Indicador indicadorFuente)
  {
    indicadorCopia.setUrl(indicadorFuente.getUrl());
  }
  
  
  
  private int setDefinicion(Indicador indicadorCopia, Indicador indicadorFuente, List<ObjetosCopia> clasesCopiadas, SerieIndicador serieReal, boolean copiarMediciones, boolean copiarInsumos, List<Indicador> indicadoresCopia, List<Indicador> indicadoresFuentes, StrategosIndicadoresService strategosIndicadoresService, HttpServletRequest request)
  {
    int respuesta = 10000;
    indicadorCopia.setCodigoEnlace(null);
    indicadorCopia.setEnlaceParcial(null);
    indicadorCopia.getEscalaCualitativa().clear();
    indicadorCopia.setAsignarInventario(indicadorFuente.getAsignarInventario());
    if (indicadorCopia.getNaturaleza().equals(Naturaleza.getNaturalezaSimple())) {
      setDefinicionSimple(indicadorCopia, indicadorFuente);
    } else if (indicadorCopia.getNaturaleza().equals(Naturaleza.getNaturalezaFormula())) {
      respuesta = setDefinicionFormula(indicadorCopia, indicadorFuente, clasesCopiadas, serieReal, copiarMediciones, copiarInsumos, indicadoresCopia, indicadoresFuentes, strategosIndicadoresService, request);
    } else if ((indicadorCopia.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoOrdinal())) || (indicadorCopia.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoNominal()))) {
      setDefinicionCualitativo(indicadorCopia, indicadorFuente);
    } else if (indicadorCopia.getNaturaleza().equals(Naturaleza.getNaturalezaSumatoria())) {
      setDefinicionSumatoria(indicadorCopia, indicadorFuente, serieReal, strategosIndicadoresService);
    } else if (indicadorCopia.getNaturaleza().equals(Naturaleza.getNaturalezaPromedio())) {
      setDefinicionPromedio(indicadorCopia, indicadorFuente, serieReal, strategosIndicadoresService);
    } else if (indicadorCopia.getNaturaleza().equals(Naturaleza.getNaturalezaIndice())) {
      setDefinicionIndice(indicadorCopia, indicadorFuente, serieReal, strategosIndicadoresService);
    }
    return respuesta;
  }
  
  private void setDefinicionSimple(Indicador indicadorCopia, Indicador indicadorFuente)
  {
    if ((indicadorFuente.getCodigoEnlace() != null) && (!indicadorFuente.getCodigoEnlace().equals(""))) {
      indicadorCopia.setCodigoEnlace(indicadorFuente.getCodigoEnlace());
    }
    if ((indicadorFuente.getEnlaceParcial() != null) && (!indicadorFuente.getEnlaceParcial().equals(""))) {
      indicadorCopia.setEnlaceParcial(indicadorFuente.getEnlaceParcial());
    }
  }
  
  private void setDefinicionCualitativo(Indicador indicadorCopia, Indicador indicadorFuente)
  {
    int order = 0;
    for (Iterator<CategoriaIndicador> k = indicadorFuente.getEscalaCualitativa().iterator(); k.hasNext();)
    {
      order++;
      CategoriaIndicador categoria = (CategoriaIndicador)k.next();
      CategoriaIndicador categoriaIndicador = new CategoriaIndicador();
      categoriaIndicador.setOrden(Integer.valueOf(order));
      categoriaIndicador.setPk(new CategoriaIndicadorPK());
      categoriaIndicador.getPk().setIndicadorId(indicadorCopia.getIndicadorId());
      categoriaIndicador.getPk().setCategoriaId(categoria.getPk().getCategoriaId());
      indicadorCopia.getEscalaCualitativa().add(categoriaIndicador);
    }
  }
  
  private int setDefinicionFormula(Indicador indicadorCopia, Indicador indicadorFuente, List<ObjetosCopia> clasesCopiadas, SerieIndicador serieReal, boolean copiarMediciones, boolean copiarInsumos, List<Indicador> indicadoresCopia, List<Indicador> indicadoresFuentes, StrategosIndicadoresService strategosIndicadoresService, HttpServletRequest request)
  {
    int respuesta = 10000;
    
    SerieIndicador serieCopiaReal = null;
    Set<SerieIndicador> seriesIndicador = indicadorCopia.getSeriesIndicador();
    
    StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService(strategosIndicadoresService);
    List<?> seriesTiempo = strategosSeriesTiempoService.getSeriesTiempo(0, 0, "serieId", null, false, null).getLista();
    for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext();)
    {
      SerieIndicador serie = (SerieIndicador)i.next();
      if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue())
      {
        serieCopiaReal = new SerieIndicador(serie.getPk(), serie.getNaturaleza(), serie.getFechaBloqueo(), serie.getSerieTiempo(), serie.getIndicador(), serie.getFormulas(), serie.getMediciones(), serie.getIndicadoresCeldas(), serie.getNombre());
        break;
      }
    }
    Formula formulaIndicador = null;
    String insumosFormula2 = "";
    int indice = 1;
    EditarIndicadorForm editarIndicadorForm = new EditarIndicadorForm();
    if (serieReal.getFormulas().size() > 0) {
      formulaIndicador = new Formula(((Formula)serieReal.getFormulas().toArray()[0]).getPk(), ((Formula)serieReal.getFormulas().toArray()[0]).getExpresion(), ((Formula)serieReal.getFormulas().toArray()[0]).getSerieIndicador(), ((Formula)serieReal.getFormulas().toArray()[0]).getInsumos());
    }
    if (formulaIndicador != null)
    {
      for (Iterator<?> k = formulaIndicador.getInsumos().iterator(); k.hasNext();)
      {
        InsumoFormula insumo = (InsumoFormula)k.next();
        Indicador indicadorInsumoOrinal = (Indicador)strategosIndicadoresService.load(Indicador.class, insumo.getPk().getIndicadorId());
        Indicador indicadorInsumo = new Indicador();
        if (!copiarInsumos)
        {
          indicadorInsumo = indicadorInsumoOrinal;
        }
        else
        {
          Map<Object, Object> filtros = new HashMap();
          filtros.put("claseId", indicadorCopia.getClaseId());
          filtros.put("nombre", indicadorInsumoOrinal.getNombre());
          
          List<?> indicadores = strategosIndicadoresService.getIndicadores(0, 0, null, null, true, filtros, null, null, Boolean.valueOf(false)).getLista();
          if (indicadores.size() > 0)
          {
            indicadorInsumo = (Indicador)indicadores.get(0);
          }
          else
          {
            indicadorInsumo = CopiarIndicador(indicadorInsumoOrinal, indicadorCopia.getClaseId(), clasesCopiadas, copiarMediciones, copiarInsumos, indicadoresCopia, indicadoresFuentes, strategosIndicadoresService, request);
            if (indicadorInsumo == null) {
              return 10001;
            }
          }
          formulaIndicador.setExpresion(formulaIndicador.getExpresion().replaceAll(insumo.getPk().getIndicadorId().toString(), indicadorInsumo.getIndicadorId().toString()));
        }
        String nombreSerie = null;
        for (Iterator<?> j = seriesTiempo.iterator(); j.hasNext();)
        {
          SerieTiempo serie = (SerieTiempo)j.next();
          if (serie.getSerieId().equals(insumo.getPk().getInsumoSerieId()))
          {
            nombreSerie = serie.getNombre();
            break;
          }
        }
        if ((insumo != null) && (indicadorInsumo != null)) {
          insumosFormula2 = insumosFormula2 + "[" + indice + "]" + "[indicadorId:" + indicadorInsumo.getIndicadorId() + "]" + "[serieId:" + insumo.getPk().getInsumoSerieId() + "]" + "[indicadorNombre:" + indicadorInsumo.getNombre() + "][serieNombre:" + nombreSerie + "]" + "[rutaCompleta:" + strategosIndicadoresService.getRutaCompletaIndicador(indicadorInsumo.getIndicadorId(), editarIndicadorForm.getSeparadorRuta()) + "]" + editarIndicadorForm.getSeparadorIndicadores();
        }
        indice++;
      }
      if (!insumosFormula2.equals("")) {
        editarIndicadorForm.setInsumosFormula(insumosFormula2.substring(0, insumosFormula2.length() - editarIndicadorForm.getSeparadorIndicadores().length()));
      }
      if (formulaIndicador.getExpresion() != null)
      {
        String formula = IndicadorValidator.reemplazarIdsPorCorrelativosFormula(formulaIndicador.getExpresion(), insumosFormula2);
        editarIndicadorForm.setFormula(formula);
      }
    }
    formulaIndicador = new Formula();
    formulaIndicador.setInsumos(new HashSet());
    serieCopiaReal.getFormulas().clear();
    
    formulaIndicador.setExpresion(IndicadorValidator.reemplazarCorrelativosFormula(editarIndicadorForm.getFormula(), editarIndicadorForm.getInsumosFormula()));
    if ((editarIndicadorForm.getInsumosFormula() != null) && (!editarIndicadorForm.getInsumosFormula().equals("")))
    {
      String[] insumos = editarIndicadorForm.getInsumosFormula().split(editarIndicadorForm.getSeparadorIndicadores());
      String[] strInsumo = (String[])null;
      for (int i = 0; i < insumos.length; i++) {
        if (insumos[i].length() > 0)
        {
          strInsumo = insumos[i].split("\\]\\[");
          InsumoFormula insumoFormula = new InsumoFormula();
          insumoFormula.setPk(new InsumoFormulaPK());
          insumoFormula.getPk().setPadreId(indicadorCopia.getIndicadorId());
          insumoFormula.getPk().setSerieId(new Long("0"));
          insumoFormula.getPk().setIndicadorId(new Long(strInsumo[1].substring("indicadorId:".length())));
          insumoFormula.getPk().setInsumoSerieId(new Long(strInsumo[2].substring("serieId:".length())));
          formulaIndicador.getInsumos().add(insumoFormula);
        }
      }
    }
    serieCopiaReal.getFormulas().add(formulaIndicador);
    
    return respuesta;
  }
  
  private void setDefinicionSumatoria(Indicador indicadorCopia, Indicador indicadorFuente, SerieIndicador serieReal, StrategosIndicadoresService strategosIndicadoresService)
  {
    SerieIndicador serieCopiaReal = null;
    Set<SerieIndicador> seriesIndicador = indicadorCopia.getSeriesIndicador();
    for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext();)
    {
      SerieIndicador serie = (SerieIndicador)i.next();
      if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue())
      {
        serieCopiaReal = new SerieIndicador(serie.getPk(), serie.getNaturaleza(), serie.getFechaBloqueo(), serie.getSerieTiempo(), serie.getIndicador(), serie.getFormulas(), serie.getMediciones(), serie.getIndicadoresCeldas(), serie.getNombre());
        break;
      }
    }
    Formula formulaIndicador = null;
    EditarIndicadorForm editarIndicadorForm = new EditarIndicadorForm();
    if (serieReal.getFormulas().size() > 0) {
      formulaIndicador = (Formula)serieReal.getFormulas().toArray()[0];
    }
    if (formulaIndicador != null) {
      for (Iterator<?> k = formulaIndicador.getInsumos().iterator(); k.hasNext();)
      {
        InsumoFormula insumo = (InsumoFormula)k.next();
        Indicador indicadorInsumo = (Indicador)strategosIndicadoresService.load(Indicador.class, insumo.getPk().getIndicadorId());
        
        editarIndicadorForm.setIndicadorSumatoriaId("[indicadorId:" + indicadorInsumo.getIndicadorId().toString() + "][serieId:" + insumo.getPk().getSerieId().toString() + "]");
        editarIndicadorForm.setIndicadorSumatoria(indicadorInsumo.getNombre());
        editarIndicadorForm.setIndicadorSumatoriaFrecuenciaId(indicadorInsumo.getFrecuencia());
        editarIndicadorForm.setIndicadorSumatoriaFrecuencia(indicadorInsumo.getFrecuenciaNombre());
        if (indicadorInsumo.getUnidad() != null) {
          editarIndicadorForm.setIndicadorSumatoriaUnidad(indicadorInsumo.getUnidad().getNombre());
        }
      }
    }
    formulaIndicador = new Formula();
    formulaIndicador.setInsumos(new HashSet());
    serieCopiaReal.getFormulas().clear();
    
    formulaIndicador.setExpresion("");
    if ((editarIndicadorForm.getIndicadorSumatoriaId() != null) && (!editarIndicadorForm.getIndicadorSumatoriaId().equals("")))
    {
      String[] valoresInsumo = editarIndicadorForm.getIndicadorSumatoriaId().split("\\]\\[");
      
      InsumoFormula insumoFormula = new InsumoFormula();
      insumoFormula.setPk(new InsumoFormulaPK());
      insumoFormula.getPk().setPadreId(indicadorCopia.getIndicadorId());
      insumoFormula.getPk().setSerieId(new Long("0"));
      insumoFormula.getPk().setIndicadorId(new Long(valoresInsumo[0].substring("[indicadorId:".length())));
      insumoFormula.getPk().setInsumoSerieId(new Long(valoresInsumo[1].substring("serieId:".length(), valoresInsumo[1].length() - 1)));
      formulaIndicador.getInsumos().add(insumoFormula);
    }
    serieCopiaReal.getFormulas().add(formulaIndicador);
  }
  
  private void setDefinicionPromedio(Indicador indicadorCopia, Indicador indicadorFuente, SerieIndicador serieReal, StrategosIndicadoresService strategosIndicadoresService)
  {
    SerieIndicador serieCopiaReal = null;
    Set<SerieIndicador> seriesIndicador = indicadorCopia.getSeriesIndicador();
    for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext();)
    {
      SerieIndicador serie = (SerieIndicador)i.next();
      if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue())
      {
        serieCopiaReal = serie;
        break;
      }
    }
    Formula formulaIndicador = null;
    EditarIndicadorForm editarIndicadorForm = new EditarIndicadorForm();
    if (serieReal.getFormulas().size() > 0) {
      formulaIndicador = (Formula)serieReal.getFormulas().toArray()[0];
    }
    if (formulaIndicador != null) {
      for (Iterator<?> k = formulaIndicador.getInsumos().iterator(); k.hasNext();)
      {
        InsumoFormula insumo = (InsumoFormula)k.next();
        Indicador indicadorInsumo = (Indicador)strategosIndicadoresService.load(Indicador.class, insumo.getPk().getIndicadorId());
        
        editarIndicadorForm.setIndicadorPromedioId("[indicadorId:" + indicadorInsumo.getIndicadorId().toString() + "][serieId:" + insumo.getPk().getSerieId().toString() + "]");
        editarIndicadorForm.setIndicadorPromedio(indicadorInsumo.getNombre());
        editarIndicadorForm.setIndicadorPromedioFrecuenciaId(indicadorInsumo.getFrecuencia());
        editarIndicadorForm.setIndicadorPromedioFrecuencia(indicadorInsumo.getFrecuenciaNombre());
        if (indicadorInsumo.getUnidad() != null) {
          editarIndicadorForm.setIndicadorPromedioUnidad(indicadorInsumo.getUnidad().getNombre());
        }
      }
    }
    formulaIndicador = new Formula();
    formulaIndicador.setInsumos(new HashSet());
    formulaIndicador.setExpresion("");
    if ((editarIndicadorForm.getIndicadorPromedioId() != null) && (!editarIndicadorForm.getIndicadorPromedioId().equals("")))
    {
      String[] valoresInsumo = editarIndicadorForm.getIndicadorPromedioId().split("\\]\\[");
      InsumoFormula insumoFormula = new InsumoFormula();
      insumoFormula.setPk(new InsumoFormulaPK());
      insumoFormula.getPk().setPadreId(indicadorCopia.getIndicadorId());
      insumoFormula.getPk().setSerieId(new Long("0"));
      insumoFormula.getPk().setIndicadorId(new Long(valoresInsumo[0].substring("[indicadorId:".length())));
      insumoFormula.getPk().setInsumoSerieId(new Long(valoresInsumo[1].substring("serieId:".length(), valoresInsumo[1].length() - 1)));
      formulaIndicador.getInsumos().add(insumoFormula);
    }
    serieCopiaReal.getFormulas().add(formulaIndicador);
  }
  
  private void setDefinicionIndice(Indicador indicadorCopia, Indicador indicadorFuente, SerieIndicador serieReal, StrategosIndicadoresService strategosIndicadoresService)
  {
    SerieIndicador serieCopiaReal = null;
    Set<SerieIndicador> seriesIndicador = indicadorCopia.getSeriesIndicador();
    for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext();)
    {
      SerieIndicador serie = (SerieIndicador)i.next();
      if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue())
      {
        serieCopiaReal = serie;
        break;
      }
    }
    Formula formulaIndicador = null;
    EditarIndicadorForm editarIndicadorForm = new EditarIndicadorForm();
    if (serieReal.getFormulas().size() > 0)
    {
      formulaIndicador = (Formula)serieReal.getFormulas().toArray()[0];
      String expresion = formulaIndicador.getExpresion();
      int index = expresion.indexOf("\\");
      editarIndicadorForm.setIndicadorIndiceTipoVariacion(expresion.substring(0, index));
      editarIndicadorForm.setIndicadorIndiceTipoComparacion(expresion.substring(index + 1));
    }
    if (formulaIndicador != null) {
      for (Iterator<?> k = formulaIndicador.getInsumos().iterator(); k.hasNext();)
      {
        InsumoFormula insumo = (InsumoFormula)k.next();
        Indicador indicadorInsumo = (Indicador)strategosIndicadoresService.load(Indicador.class, insumo.getPk().getIndicadorId());
        
        editarIndicadorForm.setIndicadorIndiceId("[indicadorId:" + indicadorInsumo.getIndicadorId().toString() + "][serieId:" + insumo.getPk().getSerieId().toString() + "]");
        editarIndicadorForm.setIndicadorIndice(indicadorInsumo.getNombre());
        editarIndicadorForm.setIndicadorIndiceFrecuenciaId(indicadorInsumo.getFrecuencia());
        editarIndicadorForm.setIndicadorIndiceFrecuencia(indicadorInsumo.getFrecuenciaNombre());
        if (indicadorInsumo.getUnidad() != null) {
          editarIndicadorForm.setIndicadorIndiceUnidad(indicadorInsumo.getUnidad().getNombre());
        }
      }
    }
    formulaIndicador = new Formula();
    formulaIndicador.setInsumos(new HashSet());
    
    formulaIndicador.setExpresion(editarIndicadorForm.getIndicadorIndiceTipoVariacion() + "\\" + editarIndicadorForm.getIndicadorIndiceTipoComparacion());
    if ((editarIndicadorForm.getIndicadorIndiceId() != null) && (!editarIndicadorForm.getIndicadorIndiceId().equals("")))
    {
      String[] valoresInsumo = editarIndicadorForm.getIndicadorIndiceId().split("\\]\\[");
      InsumoFormula insumoFormula = new InsumoFormula();
      insumoFormula.setPk(new InsumoFormulaPK());
      insumoFormula.getPk().setPadreId(indicadorCopia.getIndicadorId());
      insumoFormula.getPk().setSerieId(new Long("0"));
      insumoFormula.getPk().setIndicadorId(new Long(valoresInsumo[0].substring("[indicadorId:".length())));
      insumoFormula.getPk().setInsumoSerieId(new Long(valoresInsumo[1].substring("serieId:".length(), valoresInsumo[1].length() - 1)));
      formulaIndicador.getInsumos().add(insumoFormula);
    }
    serieCopiaReal.getFormulas().add(formulaIndicador);
  }
  
  private int CopiarMediciones(Indicador indicadorCopia, Indicador indicadorFuente, StrategosIndicadoresService strategosIndicadoresService, HttpServletRequest request)
  {
    int respuesta = 10000;
    Medicion medicion = null;
    Set<SerieIndicador> seriesIndicador = indicadorFuente.getSeriesIndicador();
    StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
    for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext();)
    {
      SerieIndicador serie = (SerieIndicador)i.next();
      List<?> medicionesFuente = strategosMedicionesService.getMedicionesPeriodo(indicadorFuente.getIndicadorId(), serie.getPk().getSerieId(), null, null, null, null);
      List<Medicion> medicionesCopia = new ArrayList();
      for (Iterator<?> iter = medicionesFuente.iterator(); iter.hasNext();)
      {
        Medicion med = (Medicion)iter.next();
        
        medicion = new Medicion();
        MedicionPK medicionPk = new MedicionPK();
        medicionPk.setIndicadorId(indicadorCopia.getIndicadorId());
        medicionPk.setSerieId(serie.getPk().getSerieId());
        medicionPk.setAno(med.getMedicionId().getAno());
        medicionPk.setPeriodo(med.getMedicionId().getPeriodo());
        medicion.setMedicionId(medicionPk);
        medicion.setProtegido(med.getProtegido());
        medicion.setValor(med.getValor());
        
        medicionesCopia.add(medicion);
      }
      respuesta = strategosMedicionesService.copiarMediciones(medicionesCopia, getUsuarioConectado(request));
      if (respuesta != 10000) {
        break;
      }
    }
    return respuesta;
  }
}
