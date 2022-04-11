package com.visiongc.app.strategos.web.struts.problemas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.web.struts.problemas.forms.EditarProblemaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GuardarProblemaSoloLecturaAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarProblemaSoloLecturaAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarProblemaForm editarProblemaForm = (EditarProblemaForm)form;

    boolean cancelar = false;
    String ts = request.getParameter("ts");
    String problemaId = request.getParameter("problemaId");
    Boolean soloLectura = new Boolean(request.getParameter("soloLectura"));
    String ultimoTs = (String)request.getSession().getAttribute("GuardarProblemaSoloLecturaAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosProblemasService strategosProblemasService = StrategosServiceFactory.getInstance().openStrategosProblemasService();

    if (cancelar)
    {
      strategosProblemasService.unlockObject(request.getSession().getId(), editarProblemaForm.getProblemaId());

      strategosProblemasService.close();

      return getForwardBack(request, 1, true);
    }

    Problema problema = new Problema();
    int respuesta = 10000;

    problema = (Problema)strategosProblemasService.load(Problema.class, new Long(problemaId));

    problema.setReadOnly(soloLectura);

    respuesta = strategosProblemasService.saveProblema(problema, getUsuarioConectado(request));

    if (respuesta == 10000) {
      strategosProblemasService.unlockObject(request.getSession().getId(), editarProblemaForm.getProblemaId());
    }

    strategosProblemasService.close();

    request.getSession().setAttribute("GuardarProblemaSoloLecturaAction.ultimoTs", ts);

    return mapping.findForward(forward);
  }
}