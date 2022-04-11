package com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPrdProductosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimiento;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoPK;
import com.visiongc.app.strategos.planificacionseguimiento.model.util.AlertaIniciativaProducto;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.forms.RegistrarSeguimientoForm;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.util.RegistroSeguimientoUtil;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class ActualizarRegistroSeguimientoAction extends VgcAction
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

    ActionMessages messages = getMessages(request);

    StrategosPrdProductosService strategosPrdProductosService = StrategosServiceFactory.getInstance().openStrategosPrdProductosService();

    if (registrarSeguimientoForm.getEliminarSeguimientoSeleccionado().booleanValue()) {
      eliminarSeguimiento(registrarSeguimientoForm, strategosPrdProductosService, getUsuarioConectado(request));
    } else if (registrarSeguimientoForm.getActualizarSeguimientoSeleccionado().booleanValue()) {
      String seguimientoSeleccionadoId = registrarSeguimientoForm.getSeguimientoSeleccionadoId().substring("ano".length());
      registrarSeguimientoForm.setAno(new Integer(seguimientoSeleccionadoId.substring(0, seguimientoSeleccionadoId.indexOf("periodo"))));
      registrarSeguimientoForm.setPeriodo(new Integer(seguimientoSeleccionadoId.substring(seguimientoSeleccionadoId.indexOf("periodo") + "periodo".length())));

      registrarSeguimientoForm.setActualizarSeguimientoSeleccionado(new Boolean(false));
    } else {
      Calendar fecha = Calendar.getInstance();

      fecha.setTime(FechaUtil.convertirStringToDate(registrarSeguimientoForm.getFecha(), "formato.fecha.corta"));
      registrarSeguimientoForm.setAno(new Integer(fecha.get(1)));
      registrarSeguimientoForm.setPeriodo(new Integer(PeriodoUtil.getPeriodoDeFecha(fecha, registrarSeguimientoForm.getFrecuencia().byteValue())));
    }

    List seguimientos = registrarSeguimientoForm.getPaginaSeguimientos().getLista();

    PrdSeguimiento seguimientoSeleccionado = null;

    for (Iterator iter = seguimientos.iterator(); iter.hasNext(); ) {
      PrdSeguimiento seguimiento = (PrdSeguimiento)iter.next();

      if ((seguimiento.getPk().getAno().equals(registrarSeguimientoForm.getAno())) && (seguimiento.getPk().getPeriodo().equals(registrarSeguimientoForm.getPeriodo()))) {
        seguimientoSeleccionado = seguimiento;
      }
    }

    if (seguimientoSeleccionado != null) {
      registrarSeguimientoForm.setSeguimientoSeleccionadoId("ano" + seguimientoSeleccionado.getPk().getAno() + "periodo" + seguimientoSeleccionado.getPk().getPeriodo());

      registrarSeguimientoForm.setFecha(VgcFormatter.formatearFecha(seguimientoSeleccionado.getFecha(), "formato.fecha.corta"));
      registrarSeguimientoForm.setAlerta(seguimientoSeleccionado.getAlerta());
      registrarSeguimientoForm.setSeguimiento(seguimientoSeleccionado.getSeguimiento());
    } else {
      registrarSeguimientoForm.setSeguimientoSeleccionadoId(null);
      registrarSeguimientoForm.setAlerta(AlertaIniciativaProducto.getAlertaEnEsperaComienzo());
      registrarSeguimientoForm.setSeguimiento(null);
    }

    RegistroSeguimientoUtil.setSeguimientosProductos(registrarSeguimientoForm, seguimientoSeleccionado);

    strategosPrdProductosService.close();

    saveMessages(request, messages);

    return mapping.findForward(forward);
  }

  private void eliminarSeguimiento(RegistrarSeguimientoForm registrarSeguimientoForm, StrategosPrdProductosService strategosPrdProductosService, Usuario usuario)
  {
    String seguimientoSeleccionadoId = registrarSeguimientoForm.getSeguimientoSeleccionadoId().substring("ano".length());
    Integer ano = new Integer(seguimientoSeleccionadoId.substring(0, seguimientoSeleccionadoId.indexOf("periodo")));
    Integer periodo = new Integer(seguimientoSeleccionadoId.substring(seguimientoSeleccionadoId.indexOf("periodo") + "periodo".length()));

    List seguimientos = registrarSeguimientoForm.getPaginaSeguimientos().getLista();

    PrdSeguimiento seguimientoSeleccionado = null;

    int index = 0;
    for (Iterator iter = seguimientos.iterator(); iter.hasNext(); ) {
      seguimientoSeleccionado = (PrdSeguimiento)iter.next();
      if ((seguimientoSeleccionado.getPk().getAno().equals(ano)) && (seguimientoSeleccionado.getPk().getPeriodo().equals(periodo))) {
        break;
      }
      index++;
    }

    if (seguimientoSeleccionado != null) {
      int resultado = strategosPrdProductosService.deletePrdSeguimiento(seguimientoSeleccionado, usuario);
      if (resultado == 10000) {
        seguimientos.remove(index);
        registrarSeguimientoForm.getPaginaSeguimientos().setTotal(seguimientos.size());
        if (seguimientos.size() > 0) {
          seguimientoSeleccionado = (PrdSeguimiento)seguimientos.get(0);
          registrarSeguimientoForm.setAno(seguimientoSeleccionado.getPk().getAno());
          registrarSeguimientoForm.setPeriodo(seguimientoSeleccionado.getPk().getPeriodo());
        }
        else {
          Calendar ahora = Calendar.getInstance();
          registrarSeguimientoForm.setFecha(VgcFormatter.formatearFecha(ahora.getTime(), "formato.fecha.corta"));
          registrarSeguimientoForm.setAno(new Integer(ahora.get(1)));
          registrarSeguimientoForm.setPeriodo(new Integer(PeriodoUtil.getPeriodoDeFecha(ahora, registrarSeguimientoForm.getFrecuencia().byteValue())));
        }
      }
    }
    registrarSeguimientoForm.setEliminarSeguimientoSeleccionado(new Boolean(false));
  }
}