package com.visiongc.app.strategos.web.struts.anexos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.web.struts.anexos.forms.GestionarAnexosForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarAnexosAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.agregarUrl(url, nombre);
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    GestionarAnexosForm gestionarAnexosForm = (GestionarAnexosForm)form;

    String objetoKey = request.getParameter("objetoKey");
    Long objetoId = new Long(request.getParameter("objetoId"));

    Boolean cancelar = new Boolean(false);

    if ((objetoId == null) || (objetoId == new Long(0L))) {
      cancelar = new Boolean(true);
    }

    if ((objetoKey == null) || (objetoKey.equals(""))) {
      cancelar = new Boolean(true);
    }

    if (cancelar.booleanValue()) {
      return getForwardBack(request, 1, true);
    }

    gestionarAnexosForm.setObjetoId(objetoId);
    gestionarAnexosForm.setObjetoKey(objetoKey);
    gestionarAnexosForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());

    request.getSession().setAttribute("objetoKey", objetoKey);

    if (objetoKey.equals("Indicador"))
    {
      StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
      Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, objetoId);

      request.getSession().setAttribute("indicador", indicador);

      gestionarAnexosForm.setNombreObjeto(indicador.getNombre());

      strategosIndicadoresService.close();
    }

    if (objetoKey.equals("Celda"))
    {
      StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
      Celda celda = (Celda)strategosCeldasService.load(Celda.class, objetoId);

      request.getSession().setAttribute("celda", celda);

      String nombreObjeto = "";

      if (celda.getIndicadoresCelda() != null) {
        if ((celda.getIndicadoresCelda().size() == 0) || (celda.getIndicadoresCelda().size() > 1)) {
          nombreObjeto = celda.getTitulo();
        } else if (celda.getIndicadoresCelda().size() == 1) {
          IndicadorCelda indicadorCelda = (IndicadorCelda)celda.getIndicadoresCelda().toArray()[0];

          StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
          Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorCelda.getIndicador().getIndicadorId());

          nombreObjeto = indicador.getNombre();
        }
      }
      else nombreObjeto = celda.getTitulo();

      gestionarAnexosForm.setNombreObjeto(nombreObjeto);
    }

    if (objetoKey.equals("Iniciativa"))
    {
      StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
      Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, objetoId);

      request.getSession().setAttribute("iniciativa", iniciativa);

      gestionarAnexosForm.setNombreObjeto(iniciativa.getNombre());

      strategosIniciativasService.close();
    }

    return mapping.findForward(forward);
  }
}