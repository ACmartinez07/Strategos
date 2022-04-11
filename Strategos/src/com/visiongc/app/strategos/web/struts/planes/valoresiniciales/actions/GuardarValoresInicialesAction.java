package com.visiongc.app.strategos.web.struts.planes.valoresiniciales.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.MetaPK;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.planes.valoresiniciales.forms.EditarValoresInicialesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.ArrayList;
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

public final class GuardarValoresInicialesAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarValoresInicialesAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    ActionMessages messages = getMessages(request);

    EditarValoresInicialesForm editarValoresInicialesForm = (EditarValoresInicialesForm)form;

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarValoresInicialesAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();

    if (cancelar)
    {
      destruirPoolLocksUsoEdicion(request, strategosMetasService);

      strategosMetasService.close();

      request.getSession().removeAttribute("editarValoresInicialesForm");

      return getForwardBack(request, 1, true);
    }

    List valoresInicialesEditados = new ArrayList();
    Map mapaParametros = request.getParameterMap();
    int respuesta = 10000;

    for (Iterator iter = mapaParametros.keySet().iterator(); iter.hasNext(); )
    {
      String nombreParametro = (String)iter.next();

      if (nombreParametro.indexOf("anoIndicadorId") != 0) {
        continue;
      }
      Long serieId = SerieTiempo.getSerieValorInicialId();
      String indicadorId = nombreParametro.substring(14);

      String ano = ((String[])mapaParametros.get("anoIndicadorId".concat(indicadorId)))[0];
      String periodo = ((String[])mapaParametros.get("periodoIndicadorId".concat(indicadorId)))[0];
      String valorNuevo = ((String[])mapaParametros.get("valorIndicadorId".concat(indicadorId)))[0];

      if ((ano.trim().equals("")) && (periodo.trim().equals(""))) {
        ano = "0";
        periodo = "0";
      }

      Double valor = null;
      if (!valorNuevo.equals("")) {
        valor = new Double(valorNuevo);
      }

      Meta valorInicialEditada = new Meta(new MetaPK(editarValoresInicialesForm.getPlanId(), new Long(indicadorId), serieId, new Byte("3"), new Integer(ano), new Integer(periodo)), valor, new Boolean(false));
      valoresInicialesEditados.add(valorInicialEditada);
    }

    if (valoresInicialesEditados.size() > 0) {
      respuesta = strategosMetasService.saveMetas(valoresInicialesEditados, getUsuarioConectado(request));
    }

    if (respuesta == 10000)
    {
      destruirPoolLocksUsoEdicion(request, strategosMetasService);
      forward = "exito";

      request.getSession().removeAttribute("editarValoresInicialesForm");

      if (valoresInicialesEditados.size() > 0)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarvaloresiniciales.mensaje.guardarvaloresiniciales.exito"));
      }

    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarvaloresiniciales.mensaje.guardarvaloresiniciales.relacionados"));
    }

    strategosMetasService.close();

    saveMessages(request, messages);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}