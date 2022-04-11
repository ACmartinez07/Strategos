package com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPrdProductosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EliminarProductoAction extends VgcAction
{
  private static final String ACTION_KEY = "EliminarProdcutoAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String productoId = request.getParameter("productoId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarProdcutoAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((productoId == null) || (productoId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(productoId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }

    StrategosPrdProductosService strategosPrdProductosService = StrategosServiceFactory.getInstance().openStrategosPrdProductosService();

    bloqueado = !strategosPrdProductosService.lockForDelete(request.getSession().getId(), productoId);

    PrdProducto prdProducto = (PrdProducto)strategosPrdProductosService.load(PrdProducto.class, new Long(productoId));

    if (prdProducto != null)
    {
      if (bloqueado)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", prdProducto.getNombre()));
      }
      else
      {
        prdProducto.setProductoId(Long.valueOf(productoId));
        Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
        int res = strategosPrdProductosService.deleteProducto(prdProducto, usuario);

        strategosPrdProductosService.unlockObject(request.getSession().getId(), productoId);

        if (res == 10004)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", prdProducto.getNombre()));
        }
        else
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", prdProducto.getNombre()));
        }
      }

    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosPrdProductosService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarProdcutoAction.ultimoTs", productoId + "&" + ts);

    return getForwardBack(request, 1, true);
  }
}