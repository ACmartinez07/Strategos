package com.visiongc.app.strategos.web.struts.estadosacciones.actions;

import com.visiongc.app.strategos.estadosacciones.StrategosEstadosService;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CambiarOrdenEstadoAccionesAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String estadoId = request.getParameter("estadoId");
    boolean subir = request.getParameter("subir").equals("true");

    StrategosEstadosService strategosEstadosService = StrategosServiceFactory.getInstance().openStrategosEstadosService();

    strategosEstadosService.cambiarOrdenEstadoAcciones(new Long(estadoId), subir, getUsuarioConectado(request));

    strategosEstadosService.close();

    return getForwardBack(request, 1, true);
  }
}