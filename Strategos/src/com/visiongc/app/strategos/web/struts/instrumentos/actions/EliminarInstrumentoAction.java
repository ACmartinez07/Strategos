package com.visiongc.app.strategos.web.struts.instrumentos.actions;


import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
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

public class EliminarInstrumentoAction extends VgcAction
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

    String instrumentoId = request.getParameter("instrumentoId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarInstrumentoAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((instrumentoId == null) || (instrumentoId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(instrumentoId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }
    
    StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
    
    bloqueado = !strategosInstrumentosService.lockForDelete(request.getSession().getId(), instrumentoId);
    
    Instrumentos instrumento = (Instrumentos)strategosInstrumentosService.load(Instrumentos.class, new Long(instrumentoId));
    

    if (instrumento != null)
    {
      if (bloqueado)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", instrumento.getNombreCorto()));
      }
      else
      {
        instrumento.setInstrumentoId(Long.valueOf(instrumentoId));
        int res = strategosInstrumentosService.deleteInstrumentos(instrumento, getUsuarioConectado(request));

        strategosInstrumentosService.unlockObject(request.getSession().getId(), instrumentoId);

        if (res == 10004)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", instrumento.getNombreCorto()));
        }
        else
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", instrumento.getNombreCorto()));
        }

      }

    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosInstrumentosService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarInstrumentoAction.ultimoTs", instrumentoId + "&" + ts);

    return getForwardBack(request, 2, true);
  }
}