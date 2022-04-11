package com.visiongc.app.strategos.web.struts.foros.actions;

import com.visiongc.app.strategos.foros.StrategosForosService;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.web.struts.foros.forms.GestionarForosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarForosAction extends VgcAction
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

    GestionarForosForm gestionarForosForm = (GestionarForosForm)form;

    String objetoId = request.getParameter("objetoId");
    String objetoKey = request.getParameter("objetoKey");
    boolean cancelar = false;

    if ((objetoId == null) || (objetoId.equals("")))
    {
      objetoId = (String)request.getSession().getAttribute("objetoId");
      if ((objetoId == null) || (objetoId.equals(""))) {
        cancelar = true;
      }
    }
    else if (!objetoId.equals((String)request.getSession().getAttribute("objetoId"))) {
      gestionarForosForm.setForoId(null);
    }

    if ((objetoKey == null) || (objetoKey.equals("")))
    {
      objetoKey = (String)request.getSession().getAttribute("objetoKey");
      if ((objetoKey == null) || (objetoKey.equals(""))) {
        cancelar = true;
      }

    }

    if (cancelar)
    {
      return getForwardBack(request, 2, true);
    }

    StrategosForosService strategosForosService = StrategosServiceFactory.getInstance().openStrategosForosService();

    Map filtros = new HashMap();

    List listaForos = new ArrayList();

    if ((gestionarForosForm.getForoId() != null) && (!gestionarForosForm.getForoId().equals("")) && (gestionarForosForm.getForoId().byteValue() != 0)) {
      filtros.put("padreId", gestionarForosForm.getForoId().toString());
      listaForos.add(strategosForosService.getRutaCompletaForos(gestionarForosForm.getForoId(), listaForos));
      gestionarForosForm.setListaForos(listaForos);
    }
    else {
      if (listaForos.size() == 0) {
        gestionarForosForm.setListaForos(null);
      }

      if (objetoKey.equals("Indicador"))
      {
        filtros.put("tipo", "0");
        gestionarForosForm.setTipo(new Byte("0"));
        filtros.put("objetoKey", "0");
      }

      if (objetoKey.equals("Celda"))
      {
        filtros.put("tipo", "0");
        gestionarForosForm.setTipo(new Byte("0"));
        filtros.put("objetoKey", "1");
      }
    }

    if ((gestionarForosForm.getObjetoId() != null) && (!gestionarForosForm.getObjetoId().equals("")) && (gestionarForosForm.getObjetoId().byteValue() != 0)) {
      filtros.put("objetoId", gestionarForosForm.getObjetoId().toString());
    }

    PaginaLista paginaForos = strategosForosService.getForos(0, 0, null, null, true, filtros);

    request.setAttribute("paginaForos", paginaForos);

    if (objetoKey.equals("Indicador"))
    {
      StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
      Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(objetoId));

      request.getSession().setAttribute("indicador", indicador);
      request.getSession().setAttribute("objetoKey", objetoKey);
      request.getSession().setAttribute("objetoId", objetoId);

      gestionarForosForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
      gestionarForosForm.setNombreObjetoKey(indicador.getNombre());
      gestionarForosForm.setTipoObjetoKey(objetoKey);

      strategosIndicadoresService.close();
    }

    if (objetoKey.equals("Celda"))
    {
      StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
      Celda celda = (Celda)strategosCeldasService.load(Celda.class, new Long(objetoId));

      String nombreObjetoKey = "";

      if (celda.getIndicadoresCelda() != null) {
        if ((celda.getIndicadoresCelda().size() == 0) || (celda.getIndicadoresCelda().size() > 1)) {
          nombreObjetoKey = celda.getTitulo();
        } else if (celda.getIndicadoresCelda().size() == 1) {
          IndicadorCelda indicadorCelda = (IndicadorCelda)celda.getIndicadoresCelda().toArray()[0];
          StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
          Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorCelda.getIndicador().getIndicadorId());
          nombreObjetoKey = indicador.getNombre();
        }
      }
      else nombreObjetoKey = celda.getTitulo();

      request.getSession().setAttribute("celda", celda);
      request.getSession().setAttribute("objetoKey", objetoKey);
      request.getSession().setAttribute("objetoId", objetoId);

      gestionarForosForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
      gestionarForosForm.setNombreObjetoKey(nombreObjetoKey);
      gestionarForosForm.setTipoObjetoKey(objetoKey);

      strategosCeldasService.close();
    }

    strategosForosService.close();

    return mapping.findForward(forward);
  }
}