package com.visiongc.app.strategos.web.struts.planes.valoresiniciales.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.ValorInicialIndicador;
import com.visiongc.app.strategos.web.struts.planes.valoresiniciales.forms.EditarValoresInicialesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class ConfigurarEdicionValoresInicialesAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarValoresInicialesForm editarValoresInicialesForm = (EditarValoresInicialesForm)form;

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;

    if (cancelar)
    {
      request.getSession().removeAttribute("editarValoresInicialesForm");

      return getForwardBack(request, 1, true);
    }

    Long[] indicadorId = new Long[0];

    editarValoresInicialesForm.clear();

    String strIndicadorId = request.getParameter("indicadorId");
    String planId = request.getParameter("planId");
    String perspectivaId = (String)request.getSession().getAttribute("perspectivaId");
    String organizacionId = (String)request.getSession().getAttribute("organizacionId");

    if (((strIndicadorId != null ? 1 : 0) & (strIndicadorId.equals("") ? 0 : 1)) != 0) {
      String[] ids = strIndicadorId.split(",");
      indicadorId = new Long[ids.length];
      for (int i = 0; i < ids.length; i++) {
        indicadorId[i] = new Long(ids[i]);
      }

    }

    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

    Plan plan = (Plan)strategosIndicadoresService.load(Plan.class, new Long(planId));

    Perspectiva perspectiva = (Perspectiva)strategosIndicadoresService.load(Perspectiva.class, new Long(perspectivaId));

    OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosIndicadoresService.load(OrganizacionStrategos.class, new Long(organizacionId));

    editarValoresInicialesForm.setPlanId(plan.getPlanId());
    editarValoresInicialesForm.setNombrePlan(plan.getNombre());
    editarValoresInicialesForm.setNombreOrganizacion(organizacion.getNombre());

    if (indicadorId.length > 0)
    {
      for (int i = indicadorId.length - 1; i > -1; i--) {
        Long id = indicadorId[i];

        Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, id);

        if (indicador == null) {
          continue;
        }
        ValorInicialIndicador valorInicialIndicador = new ValorInicialIndicador();
        valorInicialIndicador.setIndicador(indicador);
        editarValoresInicialesForm.getValoresInicialesIndicadores().add(valorInicialIndicador);
      }

    }
    else if (perspectiva.getPadreId() == null) {
      if (strategosIndicadoresService.getNumeroIndicadoresPorPlan(editarValoresInicialesForm.getPlanId(), null) == 0)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.configuraredicionvaloresiniciales.mensajeplan.noindicadores"));
      }
    }
    else if (strategosIndicadoresService.getNumeroIndicadoresPorPerspectiva(perspectiva.getPerspectivaId(), null) == 0)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.configuraredicionvaloresiniciales.mensajeperspectiva.noindicadores"));
    }

    strategosIndicadoresService.close();

    saveMessages(request, messages);

    return mapping.findForward(forward);
  }
}