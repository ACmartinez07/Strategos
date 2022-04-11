package com.visiongc.app.strategos.web.struts.tipoproyecto.actions;


import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EliminarTiposProyectoAction extends VgcAction
{
  private static final String ACTION_KEY = "EliminarTiposProyectoAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String tipoProyectoId = request.getParameter("tipoProyectoId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarTiposProyectoAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((tipoProyectoId == null) || (tipoProyectoId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(tipoProyectoId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }

    StrategosTipoProyectoService strategosTipoProyectoService = StrategosServiceFactory.getInstance().openStrategosTipoProyectoService();

    bloqueado = !strategosTipoProyectoService.lockForDelete(request.getSession().getId(), tipoProyectoId);

    TipoProyecto tipoProyecto = (TipoProyecto)strategosTipoProyectoService.load(TipoProyecto.class, new Long(tipoProyectoId));

    if (tipoProyecto != null)
    {
      if (bloqueado)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", tipoProyecto.getNombre()));
      }
      else
      {
        tipoProyecto.setTipoProyectoId(Long.valueOf(tipoProyectoId));
        int res = strategosTipoProyectoService.deleteTipoProyecto(tipoProyecto, getUsuarioConectado(request));

        strategosTipoProyectoService.unlockObject(request.getSession().getId(), tipoProyectoId);

        if (res == 10004)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", tipoProyecto.getNombre()));
        }
        else
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", tipoProyecto.getNombre()));
        }

      }

    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosTipoProyectoService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarTiposProyectoAction.ultimoTs", tipoProyectoId + "&" + ts);

    return getForwardBack(request, 1, true);
  }
}