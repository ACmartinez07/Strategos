package com.visiongc.app.strategos.web.struts.instrumentos.actions;


import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
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

public class EliminarCooperanteAction extends VgcAction
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

    String cooperanteId = request.getParameter("cooperanteId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarCooperanteAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((cooperanteId == null) || (cooperanteId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(cooperanteId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }
    
    StrategosCooperantesService strategosCooperanteService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();
    
    bloqueado = !strategosCooperanteService.lockForDelete(request.getSession().getId(), cooperanteId);
    
    Cooperante cooperante = (Cooperante)strategosCooperanteService.load(Cooperante.class, new Long(cooperanteId));
    

    if (cooperante != null)
    {
      if (bloqueado)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", cooperante.getNombre()));
      }
      else
      {
        cooperante.setCooperanteId(Long.valueOf(cooperanteId));
        int res = strategosCooperanteService.deleteCooperantes(cooperante, getUsuarioConectado(request));

        strategosCooperanteService.unlockObject(request.getSession().getId(), cooperanteId);

        if (res == 10004)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", cooperante.getNombre()));
        }
        else
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", cooperante.getNombre()));
        }

      }

    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosCooperanteService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarCooperanteAction.ultimoTs", cooperanteId + "&" + ts);

    return getForwardBack(request, 1, true);
  }
}