package com.visiongc.app.strategos.web.struts.reportes.grafico.actions;


import com.visiongc.app.strategos.web.struts.reportes.grafico.forms.ConfigurarReporteGraficoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public final class ListarMiembrosGraficoAction extends VgcAction
{
  public static final String ACTION_KEY = "ConfigurarReporteGraficoAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    ActionMessages messages = getMessages(request);

    ConfigurarReporteGraficoForm configurarReporteGraficoForm = (ConfigurarReporteGraficoForm)form;

    saveMessages(request, messages);

    return mapping.findForward(forward);
  }
}