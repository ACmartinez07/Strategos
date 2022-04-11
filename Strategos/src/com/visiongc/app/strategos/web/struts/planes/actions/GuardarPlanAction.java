package com.visiongc.app.strategos.web.struts.planes.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.PerspectivaRelacion;
import com.visiongc.app.strategos.planes.model.PerspectivaRelacionPK;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.planes.forms.EditarPlanForm;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.actions.CopiarPerspectivaAction;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarPlanAction
  extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    String forward = mapping.getParameter();
    
    EditarPlanForm editarPlanForm = (EditarPlanForm)form;
    
    ActionMessages messages = getMessages(request);
    
    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    Boolean copiar = Boolean.valueOf((request.getParameter("copiar") != null) && (request.getParameter("copiar") != "") ? Boolean.parseBoolean(request.getParameter("copiar")) : false);
    String ts = request.getParameter("ts");
    String organizacionId = (String)request.getSession().getAttribute("organizacionId");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarPlanAction.ultimoTs");
    if ((ts == null) || (ts.equals(""))) {
      cancelar = true;
    } else if ((ultimoTs != null) && (ultimoTs.equals(ts))) {
      cancelar = true;
    }
    if (copiar.booleanValue()) {
      cancelar = false;
    }
    StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
    if (cancelar)
    {
      strategosPlanesService.unlockObject(request.getSession().getId(), editarPlanForm.getPlanId());
      strategosPlanesService.close();
      return getForwardBack(request, 1, true);
    }
    StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
    StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
    
    Plan plan = new Plan();
    
    boolean nuevo = false;
    int respuesta = 10000;
    if ((editarPlanForm.getPlanId() != null) && (!editarPlanForm.getPlanId().equals(Long.valueOf("0"))))
    {
      plan = (Plan)strategosPlanesService.load(Plan.class, editarPlanForm.getPlanId());
    }
    else if (copiar.booleanValue())
    {
      nuevo = false;
      plan = new Plan();
      plan.setPlanId(new Long(0L));
      plan.setOrganizacionId(editarPlanForm.getOrganizacionDestinoId());
      if (editarPlanForm.getCrearClaseAutomaticamente().booleanValue())
      {
        ClaseIndicadores claseRootPlan = strategosClasesIndicadoresService.getClaseRaiz(editarPlanForm.getOrganizacionDestinoId(), TipoClaseIndicadores.getTipoClaseIndicadores(), getUsuarioConectado(request));
        editarPlanForm.setClaseId(claseRootPlan.getClaseId());
      }
    }
    else
    {
      nuevo = true;
      plan = new Plan();
      plan.setPlanId(new Long(0L));
      plan.setOrganizacionId(new Long(organizacionId));
    }
    boolean asociarPlan = false;
    if ((editarPlanForm.getPlanImpactaId() != null) && (editarPlanForm.getPlanImpactaId().byteValue() != 0) && (nuevo))
    {
      plan.setPlanImpactaId(editarPlanForm.getPlanImpactaId());
      asociarPlan = true;
    }
    else if (nuevo)
    {
      plan.setPlanImpactaId(null);
    }
    plan.setNombre(editarPlanForm.getNombre());
    plan.setAnoInicial(editarPlanForm.getAnoInicial());
    plan.setAnoFinal(editarPlanForm.getAnoFinal());
    plan.setTipo(editarPlanForm.getTipo());
    plan.setActivo(editarPlanForm.getActivo());
    plan.setRevision(editarPlanForm.getRevision());
    plan.setMetodologiaId(editarPlanForm.getMetodologiaId());
    plan.setClaseId(editarPlanForm.getClaseId());
    
    respuesta = strategosPlanesService.savePlan(plan, getUsuarioConectado(request));
    if (respuesta == 10000)
    {
      Perspectiva perspectiva = strategosPerspectivasService.getPerspectivaRaiz(plan.getPlanId());
      if (perspectiva != null)
      {
        perspectiva.setNombre(plan.getNombre());
        respuesta = strategosPerspectivasService.savePerspectiva(perspectiva, getUsuarioConectado(request));
      }
    }
    if ((respuesta == 10000) && ((copiar.booleanValue()) || (asociarPlan)))
    {
      Plan planOriginal = null;
      if (copiar.booleanValue()) {
        planOriginal = (Plan)strategosPlanesService.load(Plan.class, editarPlanForm.getOriginalPlanId());
      } else if (asociarPlan) {
        planOriginal = (Plan)strategosPlanesService.load(Plan.class, editarPlanForm.getPlanImpactaId());
      }
      if ((copiar.booleanValue()) && (editarPlanForm.getAsociar() != null) && (!editarPlanForm.getAsociar().booleanValue()) && (editarPlanForm.getOrganizacionDestinoId() != null) && (new Long(organizacionId).longValue() != editarPlanForm.getOrganizacionDestinoId().longValue()))
      {
        editarPlanForm.setCopiarIndicador(editarPlanForm.getAsociarIndicador());
        editarPlanForm.setAsociarIndicador(Boolean.valueOf(false));
      }
      editarPlanForm.setCopiarIniciativa(editarPlanForm.getAsociarIniciativa());
      respuesta = CopiarPerpectivaRoot(planOriginal, plan, strategosPerspectivasService, strategosClasesIndicadoresService, request, editarPlanForm.getAsociarIndicador() != null ? editarPlanForm.getAsociarIndicador().booleanValue() : false, editarPlanForm.getAsociarIniciativa() != null ? editarPlanForm.getAsociarIniciativa().booleanValue() : false, asociarPlan, editarPlanForm.getCopiarIndicador() != null ? editarPlanForm.getCopiarIndicador().booleanValue() : false);
    }
    if (copiar.booleanValue()) {
      forward = "copiarPlan";
    }
    if (respuesta == 10000)
    {
      strategosPlanesService.unlockObject(request.getSession().getId(), editarPlanForm.getPlanId());
      editarPlanForm.setStatus(VgcReturnCode.FORM_SAVE);
      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearPlan";
      }
      else if (copiar.booleanValue())
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.copiar.ok"));
      }
      else
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
      }
    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }
    strategosClasesIndicadoresService.close();
    strategosPerspectivasService.close();
    strategosPlanesService.close();
    
    saveMessages(request, messages);
    
    request.getSession().setAttribute("GuardarPlanAction.ultimoTs", ts);
    
    return mapping.findForward(forward);
  }
  
  public int CopiarPerpectivaRoot(Plan planOriginal, Plan planCopia, StrategosPerspectivasService strategosPerspectivasService, StrategosClasesIndicadoresService strategosClasesIndicadoresService, HttpServletRequest request, boolean asociarIndicadores, boolean asociarIniciativas, boolean asociarPerspectivas, boolean copiarIndicadores)
  {
    int respuesta = 10000;
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
    
    Perspectiva perspectiva = strategosPerspectivasService.getPerspectivaRaiz(planOriginal.getPlanId());
    if (perspectiva != null)
    {
      Perspectiva perspectivaCopia = new Perspectiva();
      
      perspectivaCopia.setPerspectivaId(new Long(0L));
      perspectivaCopia.setPlanId(planCopia.getPlanId());
      perspectivaCopia.setPadreId(perspectiva.getPadreId());
      perspectivaCopia.setTipo(perspectiva.getTipo());
      perspectivaCopia.setPlan(planCopia);
      perspectivaCopia.setNombre(planCopia.getNombre());
      perspectivaCopia.setFrecuencia(perspectiva.getFrecuencia());
      perspectivaCopia.setTipoCalculo(perspectiva.getTipoCalculo());
      if (perspectiva.getTipoCalculo() == null) {
        perspectivaCopia.setTipoCalculo(perspectiva.getTipoCalculo());
      }
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
      respuesta = strategosPerspectivasService.savePerspectiva(perspectivaCopia, getUsuarioConectado(request));
      if (respuesta == 10000) {
        respuesta = new CopiarPerspectivaAction().CopiarPerspectivaHijas(planCopia.getOrganizacionId(), perspectiva, perspectivaCopia, strategosPerspectivasService, strategosClasesIndicadoresService, strategosIndicadoresService, strategosIniciativasService, request, asociarIndicadores, asociarIniciativas, asociarPerspectivas, copiarIndicadores);
      }
    }
    strategosIndicadoresService.close();
    strategosIniciativasService.close();
    
    return respuesta;
  }
}
