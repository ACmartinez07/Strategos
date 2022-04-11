package com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPrdProductosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimiento;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoPK;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.forms.RegistrarSeguimientoForm;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.util.RegistroSeguimientoUtil;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class IniciarRegistroSeguimientoAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    RegistrarSeguimientoForm registrarSeguimientoForm = (RegistrarSeguimientoForm)form;

    registrarSeguimientoForm.clear();

    registrarSeguimientoForm.setIniciativaId(new Long(request.getParameter("iniciativaId")));
    registrarSeguimientoForm.setOrganizacionId(new Long(request.getParameter("organizacionId")));

    ActionMessages messages = getMessages(request);

    StrategosPrdProductosService strategosPrdProductosService = StrategosServiceFactory.getInstance().openStrategosPrdProductosService();

    Iniciativa iniciativa = (Iniciativa)strategosPrdProductosService.load(Iniciativa.class, registrarSeguimientoForm.getIniciativaId());

    if (iniciativa != null)
    {
      registrarSeguimientoForm.setPasoActual(new Byte((byte) 1));

      Map filtros = new HashMap();
      filtros.put("iniciativaId", iniciativa.getIniciativaId().toString());

      PaginaLista paginaProductos = strategosPrdProductosService.getProductos(0, 0, "nombre", "ASC", false, filtros);

      if (paginaProductos.getTotal() == 0) {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.productos.iniciarregistroseguimiento.noproductos"));
        forward = "regresar";
      } else {
        registrarSeguimientoForm.setIniciativaNombre(iniciativa.getNombre());

        registrarSeguimientoForm.setFrecuencia(iniciativa.getFrecuencia());

        PaginaLista paginaSeguimientos = new PaginaLista();

        String[] orden = new String[2];
        String[] tipoOrden = new String[2];
        orden[0] = "pk.ano";
        orden[1] = "pk.periodo";
        tipoOrden[0] = "DESC";
        tipoOrden[1] = "DESC";

        paginaSeguimientos.setNroPagina(1);
        paginaSeguimientos.setOrden("periodo");
        paginaSeguimientos.setLista(strategosPrdProductosService.getSeguimientosPorIniciativa(iniciativa.getIniciativaId(), orden, tipoOrden));
        paginaSeguimientos.setTamanoPagina(paginaSeguimientos.getLista().size());
        paginaSeguimientos.setTotal(paginaSeguimientos.getLista().size());
        paginaSeguimientos.setTipoOrden("DESC");
        paginaSeguimientos.setTamanoSetPaginas(paginaSeguimientos.getTotal());
        registrarSeguimientoForm.setPaginaSeguimientos(paginaSeguimientos);

        PrdSeguimiento seguimientoSeleccionado = null;
        if (paginaSeguimientos.getLista().size() > 0) {
          seguimientoSeleccionado = (PrdSeguimiento)paginaSeguimientos.getLista().get(0);

          for (Iterator iter = paginaSeguimientos.getLista().iterator(); iter.hasNext(); ) {
            PrdSeguimiento seguimiento = (PrdSeguimiento)iter.next();
            seguimiento.getSeguimientosProductos().size();
          }
        }

        if (seguimientoSeleccionado != null) {
          registrarSeguimientoForm.setSeguimientoSeleccionadoId("ano" + seguimientoSeleccionado.getPk().getAno() + "periodo" + seguimientoSeleccionado.getPk().getPeriodo());
          registrarSeguimientoForm.setFecha(VgcFormatter.formatearFecha(seguimientoSeleccionado.getFecha(), "formato.fecha.corta"));
          Calendar fechaSeguimiento = Calendar.getInstance();
          fechaSeguimiento.setTime(seguimientoSeleccionado.getFecha());
          registrarSeguimientoForm.setAno(new Integer(fechaSeguimiento.get(1)));
          registrarSeguimientoForm.setPeriodo(new Integer(PeriodoUtil.getPeriodoDeFecha(fechaSeguimiento, iniciativa.getFrecuencia().byteValue())));

          registrarSeguimientoForm.setAlerta(seguimientoSeleccionado.getAlerta());
          registrarSeguimientoForm.setSeguimiento(seguimientoSeleccionado.getSeguimiento());
        } else {
          Calendar ahora = Calendar.getInstance();
          registrarSeguimientoForm.setPeriodo(new Integer(PeriodoUtil.getPeriodoDeFecha(ahora, iniciativa.getFrecuencia().byteValue())));
        }
        registrarSeguimientoForm.setNombrePeriodo(Frecuencia.getNombrePeriodoPorFrecuencia(iniciativa.getFrecuencia().byteValue()));

        paginaProductos.setTamanoSetPaginas(5);
        registrarSeguimientoForm.setPaginaProductos(paginaProductos);

        RegistroSeguimientoUtil.setSeguimientosProductos(registrarSeguimientoForm, seguimientoSeleccionado);
      }
    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
      forward = "regresar";
    }

    saveMessages(request, messages);

    if (forward.equals("regresar")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}