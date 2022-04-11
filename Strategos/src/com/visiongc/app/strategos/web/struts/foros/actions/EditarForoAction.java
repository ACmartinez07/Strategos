package com.visiongc.app.strategos.web.struts.foros.actions;

import com.visiongc.app.strategos.foros.StrategosForosService;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.web.struts.foros.forms.EditarForoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class EditarForoAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarForoForm editarForoForm = (EditarForoForm)form;

    ActionMessages messages = getMessages(request);

    String padreId = request.getParameter("padreId");
    String tipo = request.getParameter("tipo");

    if ((padreId.equals("")) || (padreId.equals("0"))) {
      padreId = null;
    }

    if (tipo.equals("")) {
      tipo = null;
    }

    StrategosForosService strategosForosService = StrategosServiceFactory.getInstance().openStrategosForosService();

    editarForoForm.clear();

    if (padreId != null)
      editarForoForm.setPadreId(new Long(padreId));
    else {
      editarForoForm.setPadreId(null);
    }
    editarForoForm.setTipo(new Byte(tipo));
    editarForoForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
    editarForoForm.setNombreTipoObjetoKey((String)request.getSession().getAttribute("objetoKey"));

    if (editarForoForm.getNombreTipoObjetoKey().equals("Indicador")) {
      editarForoForm.setNombreObjetoKey(((Indicador)request.getSession().getAttribute("indicador")).getNombre());
      editarForoForm.setObjetoKey(new Byte((byte) 0));
      editarForoForm.setObjetoId(((Indicador)request.getSession().getAttribute("indicador")).getIndicadorId());
    }

    if (editarForoForm.getNombreTipoObjetoKey().equals("Celda"))
    {
      StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
      Celda celda = (Celda)strategosCeldasService.load(Celda.class, ((Celda)request.getSession().getAttribute("celda")).getCeldaId());

      String nombreObjetoKey = "";

      if (celda.getIndicadoresCelda() != null) {
        if ((celda.getIndicadoresCelda().size() == 0) || (celda.getIndicadoresCelda().size() > 1)) {
          nombreObjetoKey = celda.getTitulo();
        } else if (celda.getIndicadoresCelda().size() == 1) {
          IndicadorCelda indicadorCelda = (IndicadorCelda)celda.getIndicadoresCelda().toArray()[0];
          StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
          Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorCelda.getIndicador().getIndicadorId());
          nombreObjetoKey = indicador.getNombre();
        }
      }
      else nombreObjetoKey = celda.getTitulo();

      editarForoForm.setNombreObjetoKey(nombreObjetoKey);
      editarForoForm.setObjetoKey(new Byte((byte) 1));
      editarForoForm.setObjetoId(((Celda)request.getSession().getAttribute("celda")).getCeldaId());
    }

    strategosForosService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}