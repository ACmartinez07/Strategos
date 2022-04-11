package com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPrdProductosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.forms.EditarProductoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarProductoAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarProductoForm editarProductoForm = (EditarProductoForm)form;

    ActionMessages messages = getMessages(request);

    String productoId = request.getParameter("productoId");

    boolean bloqueado = false;

    StrategosPrdProductosService strategosPrdProductosService = StrategosServiceFactory.getInstance().openStrategosPrdProductosService();

    if ((productoId != null) && (!productoId.equals("")) && (!productoId.equals("0")))
    {
      bloqueado = !strategosPrdProductosService.lockForUpdate(request.getSession().getId(), productoId, null);

      editarProductoForm.setBloqueado(new Boolean(bloqueado));

      PrdProducto prdProducto = (PrdProducto)strategosPrdProductosService.load(PrdProducto.class, new Long(productoId));

      if (prdProducto != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        editarProductoForm.setProductoId(prdProducto.getProductoId());
        editarProductoForm.setNombre(prdProducto.getNombre());

        Long responsableId = prdProducto.getResponsableId();
        if (responsableId != null) {
          editarProductoForm.setResponsableId(responsableId);
          editarProductoForm.setResponsable(prdProducto.getResponsable());
          editarProductoForm.setNombreResponsable(prdProducto.getResponsable().getNombre());
        }

        if (prdProducto.getFechaInicio() != null)
          editarProductoForm.setFechaInicio(VgcFormatter.formatearFecha(prdProducto.getFechaInicio(), "formato.fecha.corta"));
        else {
          editarProductoForm.setFechaInicio(null);
        }

        editarProductoForm.setDescripcion(prdProducto.getDescripcion());
        editarProductoForm.setResponsableId(prdProducto.getResponsableId());
      }
      else
      {
        strategosPrdProductosService.unlockObject(request.getSession().getId(), new Long(productoId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      editarProductoForm.clear();
    }

    strategosPrdProductosService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}