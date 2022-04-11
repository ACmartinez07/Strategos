package com.visiongc.app.strategos.web.struts.problemas.acciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.StrategosSeguimientosService;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.problemas.model.Seguimiento;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.problemas.acciones.forms.GestionarAccionesPendientesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarAccionesPendientesAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

    String urlAccionSeguimiento = "problemas/acciones/gestionarAccionesPendientes.action?tipo=1";
    String urlAccionResponder = "problemas/acciones/gestionarAccionesPendientes.action?tipo=2";
    String urlAccionAprobar = "problemas/acciones/gestionarAccionesPendientes.action?tipo=3";

    if (url.indexOf(urlAccionSeguimiento) > 1) {
      String BarraNavegacionAccionSeguimiento = messageResources.getResource("problema.acciones.gestionaraccionespendientes.seguimiento");

      navBar.agregarUrl(url, BarraNavegacionAccionSeguimiento);
    }

    if (url.indexOf(urlAccionResponder) > 1) {
      String BarraNavegacionAccionResponder = messageResources.getResource("problema.acciones.gestionaraccionespendientes.responder");

      navBar.agregarUrl(url, BarraNavegacionAccionResponder);
    }

    if (url.indexOf(urlAccionAprobar) > 1) {
      String BarraNavegacionAccionAprobar = messageResources.getResource("problema.acciones.gestionaraccionespendientes.aprobar");

      navBar.agregarUrl(url, BarraNavegacionAccionAprobar);
    }
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    GestionarAccionesPendientesForm gestionarAccionesPendientesForm = (GestionarAccionesPendientesForm)form;

    String atributoOrden = gestionarAccionesPendientesForm.getAtributoOrden();
    String tipoOrden = gestionarAccionesPendientesForm.getTipoOrden();
    int pagina = gestionarAccionesPendientesForm.getPagina();
    String tipo = request.getParameter("tipo");
    int tipoAccion = Integer.parseInt(tipo);
    gestionarAccionesPendientesForm.setTipo(new Integer(tipoAccion));

    Usuario usuario = getUsuarioConectado(request);

    if (atributoOrden == null) {
      atributoOrden = "nombre";
      gestionarAccionesPendientesForm.setAtributoOrden(atributoOrden);
    }

    if (tipoOrden == null) {
      tipoOrden = "ASC";
      gestionarAccionesPendientesForm.setTipoOrden(tipoOrden);
    }

    if (pagina < 1) {
      pagina = 1;
    }

    StrategosProblemasService strategosProblemasService = StrategosServiceFactory.getInstance().openStrategosProblemasService();
    StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
    StrategosSeguimientosService strategosSeguimientosService = StrategosServiceFactory.getInstance().openStrategosSeguimientosService();

    List listaProblemasCompleta = new ArrayList();
    List listaSeguimientosCompleta = new ArrayList();
    List listaSeguimientosPorAprobar = new ArrayList();
    List listaResponsables = new ArrayList();

    Map filtros = new HashMap();

    filtros.put("usuarioIdId", usuario.getUsuarioId().toString());

    //listaResponsables = strategosResponsablesService.getResponsables(0, 0, null, null, true, filtros).getLista();
    filtros.clear();

    if (gestionarAccionesPendientesForm.getTipo().byteValue() == 1)
    {
      for (Iterator i = listaResponsables.iterator(); i.hasNext(); ) {
        Responsable responsable = (Responsable)i.next();

        filtros.put("responsableId", responsable.getResponsableId().toString());
        filtros.put("tipoPendiente", "1");

        List listaProblemas = strategosProblemasService.getProblemas(0, 0, null, null, true, filtros).getLista();

        for (Iterator x = listaProblemas.iterator(); x.hasNext(); ) {
          Problema problema = (Problema)x.next();
          if ((problema.getListaAccionesCorrectivas() != null) && (problema.getListaAccionesCorrectivas().size() > 0)) {
            listaProblemasCompleta.add(problema);
          }
        }
      }

    }

    if (gestionarAccionesPendientesForm.getTipo().byteValue() == 2)
    {
      for (Iterator i = listaResponsables.iterator(); i.hasNext(); ) {
        Responsable responsable = (Responsable)i.next();

        filtros.put("responsableAccionId", responsable.getResponsableId().toString());
        filtros.put("tipoPendiente", "2");

        List listaSeguimientos = strategosSeguimientosService.getSeguimientos(0, 0, null, null, true, filtros).getLista();

        for (Iterator y = listaSeguimientos.iterator(); y.hasNext(); ) {
          Seguimiento seguimiento = (Seguimiento)y.next();

          if ((seguimiento.getFechaEmision() != null) && (seguimiento.getFechaRecepcion() == null)) {
            listaSeguimientosCompleta.add(seguimiento);
          }
        }

      }

    }

    if (gestionarAccionesPendientesForm.getTipo().byteValue() == 3)
    {
      for (Iterator i = listaResponsables.iterator(); i.hasNext(); ) {
        Responsable responsable = (Responsable)i.next();

        filtros.put("responsableProblemaId", responsable.getResponsableId().toString());
        filtros.put("tipoPendiente", "3");

        List listaSeguimientos = strategosSeguimientosService.getSeguimientos(0, 0, null, null, true, filtros).getLista();

        for (Iterator y = listaSeguimientos.iterator(); y.hasNext(); ) {
          Seguimiento seguimiento = (Seguimiento)y.next();

          if ((seguimiento.getFechaRecepcion() != null) && (seguimiento.getFechaAprobacion() == null)) {
            listaSeguimientosPorAprobar.add(seguimiento);
          }

        }

      }

    }

    request.setAttribute("listaProblemasCompleta", listaProblemasCompleta);

    request.setAttribute("listaSeguimientosCompleta", listaSeguimientosCompleta);

    request.setAttribute("listaSeguimientosPorAprobar", listaSeguimientosPorAprobar);

    return mapping.findForward(forward);
  }
}