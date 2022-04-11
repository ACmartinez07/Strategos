package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.PerspectivaRelacion;
import com.visiongc.app.strategos.planes.model.PerspectivaRelacionPK;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.indicadores.actions.CopiarIndicadorAction;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.EditarPerspectivaForm;
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

public class CopiarPerspectivaAction
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
      forward = "finalizarCopiarPerspectiva";
    }
    ActionMessages messages = getMessages(request);
    
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    if (!strategosIndicadoresService.checkLicencia(request))
    {
      strategosIndicadoresService.close();
      
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.limite.exedido"));
      saveMessages(request, messages);
      
      return getForwardBack(request, 1, false);
    }
    strategosIndicadoresService.close();
    
    EditarPerspectivaForm editarPerspectivaForm = (EditarPerspectivaForm)form;
    
    String showPresentacion = request.getParameter("showPresentacion") != null ? request.getParameter("showPresentacion").toString() : "0";
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    ConfiguracionUsuario presentacion = new ConfiguracionUsuario();
    ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
    pk.setConfiguracionBase("Strategos.Wizar.Perspectivas.Copiar.ShowPresentacion");
    pk.setObjeto("ShowPresentacion");
    pk.setUsuarioId(getUsuarioConectado(request).getUsuarioId());
    presentacion.setPk(pk);
    presentacion.setData(showPresentacion);
    frameworkService.saveConfiguracionUsuario(presentacion, getUsuarioConectado(request));
    frameworkService.close();
    
    int respuesta = 10000;
    StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
    respuesta = SalvarPerspectiva(editarPerspectivaForm, strategosPerspectivasService, request);
    if (respuesta == 10000)
    {
      strategosPerspectivasService.unlockObject(request.getSession().getId(), editarPerspectivaForm.getPerspectivaId());
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
      destruirPoolLocksUsoEdicion(request, strategosPerspectivasService);
      forward = "finalizarCopiarPerspectiva";
      
      request.setAttribute("GestionarPerspectivasAction.reloadId", editarPerspectivaForm.getPadreId());
    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }
    strategosPerspectivasService.close();
    
    saveMessages(request, messages);
    
    request.getSession().setAttribute("GuardarPerspectivaAction.ultimoTs", ts);
    if (forward.equals("finalizarCopiarPerspectiva")) {
      return getForwardBack(request, 1, true);
    }
    return getForwardBack(request, 1, true);
  }
  
  public int SalvarPerspectiva(EditarPerspectivaForm editarPerspectivaForm, StrategosPerspectivasService strategosPerspectivasService, HttpServletRequest request)
  {
    int respuesta = 10000;
    
    Perspectiva perspectivaOrigen = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, new Long(editarPerspectivaForm.getPerspectivaId().longValue()));
    Perspectiva perspectivaDestino = new Perspectiva();
    
    perspectivaDestino.setPerspectivaId(new Long(0L));
    perspectivaDestino.setPlanId(perspectivaOrigen.getPlanId());
    perspectivaDestino.setPadreId(perspectivaOrigen.getPadreId());
    perspectivaDestino.setTipo(perspectivaOrigen.getTipo());
    
    StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
    Plan plan = (Plan)strategosPlanesService.load(Plan.class, perspectivaOrigen.getPlanId());
    perspectivaDestino.setPlan(plan);
    strategosPlanesService.close();
    
    perspectivaDestino.setNombre(editarPerspectivaForm.getNuevoNombre());
    perspectivaDestino.setFrecuencia(perspectivaOrigen.getFrecuencia());
    perspectivaDestino.setTipoCalculo(perspectivaOrigen.getTipoCalculo());
    if ((perspectivaOrigen.getResponsableId() != null) && (!perspectivaOrigen.getResponsableId().equals("")) && (perspectivaOrigen.getResponsableId().byteValue() != 0)) {
      perspectivaDestino.setResponsableId(perspectivaOrigen.getResponsableId());
    } else {
      perspectivaDestino.setResponsableId(null);
    }
    if ((perspectivaOrigen.getDescripcion() != null) && (!perspectivaOrigen.getDescripcion().equals(""))) {
      perspectivaDestino.setDescripcion(perspectivaOrigen.getDescripcion());
    } else {
      perspectivaDestino.setDescripcion(null);
    }
    respuesta = strategosPerspectivasService.savePerspectiva(perspectivaDestino, getUsuarioConectado(request));
    
    StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
    if (respuesta == 10000) {
      respuesta = CopiarPerspectivaHijas(plan.getOrganizacionId(), perspectivaOrigen, perspectivaDestino, strategosPerspectivasService, strategosClasesIndicadoresService, strategosIndicadoresService, strategosIniciativasService, request, false, false, false, false);
    }
    strategosIniciativasService.close();
    strategosIndicadoresService.close();
    strategosClasesIndicadoresService.close();
    
    return respuesta;
  }
  
  public int CopiarPerspectiva(Plan planOrigen, Plan planDestino, HttpServletRequest request)
  {
    int respuesta = 10000;
    
    StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
    Perspectiva perspectivaOrigen = strategosPerspectivasService.getPerspectivaRaiz(planOrigen.getPlanId());
    Perspectiva perspectivaCopia = new Perspectiva();
    
    perspectivaCopia.setPerspectivaId(new Long(0L));
    perspectivaCopia.setPlanId(planDestino.getPlanId());
    perspectivaCopia.setPadreId(null);
    perspectivaCopia.setTipo(perspectivaOrigen.getTipo());
    if ((perspectivaOrigen.getResponsableId() != null) && (!perspectivaOrigen.getResponsableId().equals("")) && (perspectivaOrigen.getResponsableId().byteValue() != 0)) {
      perspectivaCopia.setResponsableId(perspectivaOrigen.getResponsableId());
    } else {
      perspectivaCopia.setResponsableId(null);
    }
    perspectivaCopia.setNombre(perspectivaOrigen.getNombre());
    if ((perspectivaOrigen.getDescripcion() != null) && (!perspectivaOrigen.getDescripcion().equals(""))) {
      perspectivaCopia.setDescripcion(perspectivaOrigen.getDescripcion());
    } else {
      perspectivaCopia.setDescripcion(null);
    }
    perspectivaCopia.setFrecuencia(perspectivaOrigen.getFrecuencia());
    perspectivaCopia.setTipoCalculo(perspectivaOrigen.getTipoCalculo());
    perspectivaCopia.setPlan(planDestino);
    
    StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
    respuesta = CopiarClase(planDestino.getOrganizacionId(), planDestino.getClaseIdIndicadoresTotales(), perspectivaOrigen, perspectivaCopia, strategosClasesIndicadoresService, request);
    if (respuesta == 10000)
    {
      respuesta = strategosPerspectivasService.savePerspectiva(perspectivaCopia, getUsuarioConectado(request));
      if (respuesta == 10000) {
        CopiarPerspectivaHijas(planDestino.getOrganizacionId(), perspectivaOrigen, perspectivaCopia, strategosPerspectivasService, strategosClasesIndicadoresService, strategosIndicadoresService, strategosIniciativasService, request, false, false, false, false);
      }
    }
    strategosIniciativasService.close();
    strategosIndicadoresService.close();
    strategosClasesIndicadoresService.close();
    strategosPerspectivasService.close();
    
    return respuesta;
  }
  
  public int CopiarClase(Long organizacionIdDestino, Long ClasePadreId, Perspectiva perspectivaOrigen, Perspectiva perspectivaDestino, StrategosClasesIndicadoresService strategosClasesIndicadoresService, HttpServletRequest request)
  {
    int respuesta = 10000;
    if (perspectivaOrigen.getClaseId() != null)
    {
      ClaseIndicadores claseOrigen = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, perspectivaOrigen.getClaseId());
      ClaseIndicadores claseDestino = new ClaseIndicadores();
      
      Map<String, Object> filtros = new HashMap();
      
      filtros.put("organizacionId", organizacionIdDestino.toString());
      filtros.put("nombre", claseOrigen.getNombre());
      filtros.put("padreId", ClasePadreId != null ? ClasePadreId.toString() : null);
      List<ClaseIndicadores> clases = strategosClasesIndicadoresService.getClases(filtros);
      if (clases.size() > 0)
      {
        Iterator<?> iter = clases.iterator();
        if (iter.hasNext())
        {
          claseDestino = (ClaseIndicadores)iter.next();
          perspectivaDestino.setClaseId(claseDestino.getClaseId());
          respuesta = 10000;
        }
      }
      else
      {
        claseDestino = new ClaseIndicadores();
        claseDestino.setPadreId(ClasePadreId);
        
        claseDestino.setClaseId(new Long(0L));
        claseDestino.setNombre(claseOrigen.getNombre());
        claseDestino.setOrganizacionId(organizacionIdDestino);
        claseDestino.setDescripcion(claseOrigen.getDescripcion());
        claseDestino.setEnlaceParcial(claseOrigen.getEnlaceParcial());
        claseDestino.setVisible(claseOrigen.getVisible());
        claseDestino.setTipo(claseOrigen.getTipo());
        
        respuesta = strategosClasesIndicadoresService.saveClaseIndicadores(claseDestino, (Usuario)request.getSession().getAttribute("usuario"));
        if (respuesta == 10000) {
          perspectivaDestino.setClaseId(claseDestino.getClaseId());
        }
      }
    }
    if (respuesta == 10000)
    {
      List<ObjetosCopia> clasesCopiadas = new ArrayList();
      clasesCopiadas.add(new ObjetosCopia(perspectivaOrigen.getClaseId(), perspectivaDestino.getClaseId(), organizacionIdDestino));
      
      respuesta = new CopiarIndicadorAction().CopiarIndicador(organizacionIdDestino, perspectivaOrigen, perspectivaDestino, clasesCopiadas, request);
    }
    return respuesta;
  }
  
  public int CopiarPerspectivaHijas(Long organizacionIdDestino, Perspectiva perspectivaOrigen, Perspectiva perspectivaDestino, StrategosPerspectivasService strategosPerspectivasService, StrategosClasesIndicadoresService strategosClasesIndicadoresService, StrategosIndicadoresService strategosIndicadoresService, StrategosIniciativasService strategosIniciativasService, HttpServletRequest request, boolean asociarIndicadores, boolean asociarIniciativas, boolean asociarPerspectivas, boolean copiarIndicadores)
  {
    int respuesta = 10000;
    
    Map<String, Object> filtros = new HashMap();
    
    filtros.put("padreId", perspectivaOrigen.getPerspectivaId());
    
    String[] orden = new String[1];
    String[] tipoOrden = new String[1];
    orden[0] = "nombre";
    tipoOrden[0] = "asc";
    
    List<Perspectiva> perspectivasHijas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);
    if ((perspectivasHijas != null) && (perspectivasHijas.size() > 0)) {
      for (Iterator<?> iter = perspectivasHijas.iterator(); iter.hasNext();)
      {
        Perspectiva perspectiva = (Perspectiva)iter.next();
        Perspectiva perspectivaCopia = new Perspectiva();
        
        perspectivaCopia.setPerspectivaId(new Long(0L));
        perspectivaCopia.setPlanId(perspectivaDestino.getPlanId());
        perspectivaCopia.setPadreId(perspectivaDestino.getPerspectivaId());
        perspectivaCopia.setTipo(perspectiva.getTipo());
        
        StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
        Plan plan = (Plan)strategosPlanesService.load(Plan.class, perspectivaDestino.getPlanId());
        perspectivaCopia.setPlan(plan);
        strategosPlanesService.close();
        
        perspectivaCopia.setNombre(perspectiva.getNombre());
        perspectivaCopia.setFrecuencia(perspectiva.getFrecuencia());
        perspectivaCopia.setTipoCalculo(perspectiva.getTipoCalculo());
        if (asociarPerspectivas)
        {
          perspectivaCopia.setRelacion(new HashSet());
          perspectivaCopia.getRelacion().clear();
          PerspectivaRelacion relacion = new PerspectivaRelacion();
          relacion.setPk(new PerspectivaRelacionPK());
          relacion.getPk().setPerspectivaId(perspectivaCopia.getPerspectivaId());
          relacion.getPk().setRelacionId(perspectiva.getPerspectivaId());
          relacion.setPerspectiva(perspectivaCopia);
          relacion.setRelacion(perspectiva);
          
          perspectivaCopia.getRelacion().add(relacion);
        }
        else if ((perspectiva.getRelacion() != null) && (perspectiva.getRelacion().size() > 0))
        {
          perspectivaCopia.setRelacion(new HashSet());
          perspectivaCopia.getRelacion().clear();
          for (Iterator<PerspectivaRelacion> i = perspectiva.getRelacion().iterator(); i.hasNext();)
          {
            PerspectivaRelacion relacion = (PerspectivaRelacion)i.next();
            
            PerspectivaRelacion relacionCopia = new PerspectivaRelacion();
            relacionCopia.setPk(new PerspectivaRelacionPK());
            relacionCopia.getPk().setPerspectivaId(perspectivaCopia.getPerspectivaId());
            relacionCopia.getPk().setRelacionId(relacion.getPk().getRelacionId());
            relacionCopia.setPerspectiva(perspectivaCopia);
            relacionCopia.setRelacion(relacion.getRelacion());
            
            perspectivaCopia.getRelacion().add(relacionCopia);
          }
        }
        if ((perspectiva.getResponsableId() != null) && (!perspectiva.getResponsableId().equals("")) && (perspectiva.getResponsableId().byteValue() != 0)) {
          perspectivaCopia.setResponsableId(perspectiva.getResponsableId());
        } else {
          perspectivaCopia.setResponsableId(null);
        }
        if ((perspectiva.getDescripcion() != null) && (!perspectiva.getDescripcion().equals(""))) {
          perspectivaCopia.setDescripcion(perspectiva.getDescripcion());
        } else {
          perspectivaCopia.setDescripcion(null);
        }
        respuesta = CopiarClase(organizacionIdDestino, perspectivaDestino.getClaseId(), perspectiva, perspectivaCopia, strategosClasesIndicadoresService, request);
        if (respuesta == 10000)
        {
          respuesta = strategosPerspectivasService.savePerspectiva(perspectivaCopia, getUsuarioConectado(request));
          if (respuesta == 10000)
          {
            if (((asociarIndicadores) || (copiarIndicadores)) && (respuesta == 10000))
            {
              filtros = new HashMap();
              filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
              filtros.put("tipoFuncionNotIn", TipoFuncionIndicador.getTipoFuncionPerspectiva().toString());
              List<?> indicadoresAsociados = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false)).getLista();
              for (Iterator<?> iterIndicador = indicadoresAsociados.iterator(); iterIndicador.hasNext();)
              {
                Indicador indicador = (Indicador)iterIndicador.next();
                if ((perspectiva.getNlAnoIndicadorId().longValue() != indicador.getIndicadorId().longValue()) && (perspectiva.getNlParIndicadorId().longValue() != indicador.getIndicadorId().longValue())) {
                  if (copiarIndicadores)
                  {
                    List<Indicador> indicadoresCopia = new ArrayList();
                    List<Indicador> indicadoresFuentes = new ArrayList();
                    
                    List<ObjetosCopia> clasesCopiadas = new ArrayList();
                    clasesCopiadas.add(new ObjetosCopia(perspectivaOrigen.getClaseId(), perspectivaCopia.getClaseId(), organizacionIdDestino));
                    
                    Indicador indicadorDestino = new CopiarIndicadorAction().CopiarIndicador(indicador, perspectivaCopia.getClaseId(), clasesCopiadas, false, false, indicadoresCopia, indicadoresFuentes, strategosIndicadoresService, request);
                    if (indicadorDestino != null) {
                      strategosPerspectivasService.asociarIndicador(perspectivaCopia.getPlanId(), perspectivaCopia, indicadorDestino.getIndicadorId(), new Boolean(true), getUsuarioConectado(request));
                    }
                  }
                  else
                  {
                    strategosPerspectivasService.asociarIndicador(perspectivaCopia.getPlanId(), perspectivaCopia, indicador.getIndicadorId(), new Boolean(true), getUsuarioConectado(request));
                  }
                }
              }
            }
            if ((asociarIniciativas) && (respuesta == 10000))
            {
              filtros = new HashMap();
              filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
              filtros.put("historicoDate", "IS NULL");
              List<?> iniciativasAsociadas = strategosIniciativasService.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();
              for (Iterator<?> iterIniciativa = iniciativasAsociadas.iterator(); iterIniciativa.hasNext();)
              {
                Iniciativa iniciativa = (Iniciativa)iterIniciativa.next();
                strategosPerspectivasService.asociarIniciativa(perspectivaCopia, new Long(iniciativa.getIniciativaId().longValue()), new Long(perspectivaCopia.getPlanId().longValue()), plan.getOrganizacionId(), getUsuarioConectado(request));
              }
            }
            CopiarPerspectivaHijas(organizacionIdDestino, perspectiva, perspectivaCopia, strategosPerspectivasService, strategosClasesIndicadoresService, strategosIndicadoresService, strategosIniciativasService, request, asociarIndicadores, asociarIniciativas, asociarPerspectivas, copiarIndicadores);
          }
        }
        if (respuesta != 10000) {
          return respuesta;
        }
      }
    }
    return respuesta;
  }
}
