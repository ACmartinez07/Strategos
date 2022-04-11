package com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPrdProductosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimiento;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoPK;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoProductoPK;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.forms.RegistrarSeguimientoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.web.NavigationBar;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarRegistroSeguimientoAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarregistroSeguimientoAction";

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

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarregistroSeguimientoAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosPrdProductosService strategosPrdProductosService = StrategosServiceFactory.getInstance().openStrategosPrdProductosService();

    if (cancelar)
    {
      strategosPrdProductosService.close();

      return getForwardBack(request, 1, true);
    }

    PrdSeguimientoPK seguimientoPK = new PrdSeguimientoPK();
    seguimientoPK.setAno(registrarSeguimientoForm.getAno());
    seguimientoPK.setPeriodo(registrarSeguimientoForm.getPeriodo());
    seguimientoPK.setIniciativaId(registrarSeguimientoForm.getIniciativaId());
    PrdSeguimiento seguimiento = (PrdSeguimiento)strategosPrdProductosService.load(PrdSeguimiento.class, seguimientoPK);

    if (seguimiento == null) {
      seguimiento = new PrdSeguimiento();
      seguimiento.setPk(seguimientoPK);
      seguimiento.setSeguimientosProductos(new HashSet());
    } else {
      seguimiento.getSeguimientosProductos().clear();
    }

    seguimiento.setAlerta(registrarSeguimientoForm.getAlerta());

    seguimiento.setFecha(FechaUtil.convertirStringToDate(registrarSeguimientoForm.getFecha(), "formato.fecha.corta"));

    seguimiento.setSeguimiento(registrarSeguimientoForm.getSeguimiento());

    setSeguimientosProductos(seguimiento, registrarSeguimientoForm);

    int respuesta = strategosPrdProductosService.savePrdSeguimiento(seguimiento, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      strategosPrdProductosService.unlockObject(request.getSession().getId(), "");
      forward = "exito";

      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }

    strategosPrdProductosService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarregistroSeguimientoAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }

  private void setSeguimientosProductos(PrdSeguimiento seguimiento, RegistrarSeguimientoForm registrarSeguimientoForm) throws Exception
  {
    String[] seguimientosProductos = registrarSeguimientoForm.getSeguimientosProductos().split("#");

    for (int i = 0; i < seguimientosProductos.length; i++) {
      PrdSeguimientoProducto seguimientoProducto = new PrdSeguimientoProducto();
      PrdSeguimientoProductoPK seguimientoProductoPK = new PrdSeguimientoProductoPK();

      seguimientoProductoPK.setIniciativaId(registrarSeguimientoForm.getIniciativaId());
      seguimientoProductoPK.setAno(registrarSeguimientoForm.getAno());
      seguimientoProductoPK.setPeriodo(registrarSeguimientoForm.getPeriodo());

      String strSegProducto = seguimientosProductos[i];
      int index = strSegProducto.indexOf("alerta");
      seguimientoProductoPK.setProductoId(new Long(strSegProducto.substring("productoId".length(), index)));

      seguimientoProducto.setPk(seguimientoProductoPK);
      strSegProducto = strSegProducto.substring(index + "alerta".length());
      index = strSegProducto.indexOf("programado");
      seguimientoProducto.setAlerta(new Byte(strSegProducto.substring(0, index)));
      strSegProducto = strSegProducto.substring(index + "programado".length());
      index = strSegProducto.indexOf("reprogramado");
      seguimientoProducto.setFechaInicio(FechaUtil.convertirStringToDate(strSegProducto.substring(0, index), "formato.fecha.corta"));
      strSegProducto = strSegProducto.substring(index + "reprogramado".length());
      seguimientoProducto.setFechaFin(FechaUtil.convertirStringToDate(strSegProducto.substring(0), "formato.fecha.corta"));
      seguimiento.getSeguimientosProductos().add(seguimientoProducto);
    }
  }
}