package com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPrdProductosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.forms.EditarProductoForm;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.forms.GestionarProductosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarProductoAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarProductoAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarProductoForm editarProductoForm = (EditarProductoForm)form;

    GestionarProductosForm gestionarProductosForm = (GestionarProductosForm)request.getSession().getAttribute("gestionarProductosForm");

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarProductoAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosPrdProductosService strategosPrdProductosService = StrategosServiceFactory.getInstance().openStrategosPrdProductosService();

    if (cancelar)
    {
      strategosPrdProductosService.unlockObject(request.getSession().getId(), editarProductoForm.getProductoId());

      strategosPrdProductosService.close();

      return getForwardBack(request, 1, true);
    }

    PrdProducto prdProducto = new PrdProducto();
    boolean nuevo = false;
    int respuesta = 10000;

    if ((editarProductoForm.getProductoId() != null) && (!editarProductoForm.getProductoId().equals(Long.valueOf("0"))))
    {
      prdProducto = (PrdProducto)strategosPrdProductosService.load(PrdProducto.class, editarProductoForm.getProductoId());
    }
    else
    {
      nuevo = true;
      prdProducto = new PrdProducto();
      prdProducto.setProductoId(new Long(0L));
    }

    prdProducto.setNombre(editarProductoForm.getNombre());

    Long iniciativaId = gestionarProductosForm.getIniciativaId();

    prdProducto.setIniciativaId(iniciativaId);

    Date fechaInicio = new Date();
    String fecha = editarProductoForm.getFechaInicio();
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
      fechaInicio = simpleDateFormat.parse(fecha);
    }
    catch (Exception localException)
    {
    }
    if ((editarProductoForm.getFechaInicio() != null) && (!editarProductoForm.getFechaInicio().equals("")))
      prdProducto.setFechaInicio(fechaInicio);
    else {
      prdProducto.setFechaInicio(null);
    }

    if ((editarProductoForm.getDescripcion() != null) && (!editarProductoForm.getDescripcion().equals("")))
      prdProducto.setDescripcion(editarProductoForm.getDescripcion());
    else {
      prdProducto.setDescripcion(null);
    }

    if (editarProductoForm.getResponsableId().equals(new Long(0L)))
      prdProducto.setResponsableId(null);
    else {
      prdProducto.setResponsableId(editarProductoForm.getResponsableId());
    }

    prdProducto.setResponsable(editarProductoForm.getResponsable());

    respuesta = strategosPrdProductosService.saveProducto(prdProducto, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      strategosPrdProductosService.unlockObject(request.getSession().getId(), editarProductoForm.getProductoId());
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearProducto";
      }
      else
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
      }
    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }

    strategosPrdProductosService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarProductoAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}