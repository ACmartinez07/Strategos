package com.visiongc.app.strategos.web.struts.presentaciones.celdas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCeldaPK;
import com.visiongc.app.strategos.presentaciones.model.util.TipoCelda;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.presentaciones.celdas.forms.EditarCeldaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.web.NavigationBar;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarCeldaAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarCeldaForm editarCeldaForm = (EditarCeldaForm)form;

    ActionMessages messages = getMessages(request);

    String celdaId = request.getParameter("celdaId");
    String fila = request.getParameter("fila");
    String columna = request.getParameter("columna");

    boolean bloqueado = false;

    StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
    StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService();

    if ((celdaId != null) && (!celdaId.equals("")) && (!celdaId.equals("0")))
    {
      bloqueado = !strategosCeldasService.lockForUpdate(request.getSession().getId(), celdaId, null);

      editarCeldaForm.setBloqueado(new Boolean(bloqueado));

      Celda celda = (Celda)strategosCeldasService.load(Celda.class, new Long(celdaId));

      if (celda != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        if (celda.getTitulo() != null)
          editarCeldaForm.setTitulo(celda.getTitulo());
        else {
          editarCeldaForm.setTitulo(null);
        }

        Calendar ahora = Calendar.getInstance();
        editarCeldaForm.setMesInicio((byte) 1);
        editarCeldaForm.setAnoInicio(ahora.get(1));
        editarCeldaForm.setMesFin((byte) 12);
        editarCeldaForm.setAnoFin(ahora.get(1));
        editarCeldaForm.setFila(celda.getFila());
        editarCeldaForm.setColumna(celda.getColumna());

        if (celda.getIndicadoresCelda() != null) {
          String nombreIndicadoresSeries = "";
          String idsIndicadoresSeries = "";
          String separadorSeries = editarCeldaForm.getSeparadorSeries();
          String separadorIndicador = editarCeldaForm.getSeparadorIndicadores();

          for (Iterator k = celda.getIndicadoresCelda().iterator(); k.hasNext(); ) {
            IndicadorCelda indicadorCelda = (IndicadorCelda)k.next();
            Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorCelda.getIndicador().getIndicadorId());
            SerieTiempo serieTiempo = (SerieTiempo)strategosSeriesTiempoService.load(SerieTiempo.class, indicadorCelda.getSerie().getSerieId());
            nombreIndicadoresSeries = nombreIndicadoresSeries + indicador.getNombre() + separadorSeries + serieTiempo.getNombre() + separadorIndicador;
            String planId = "";
            if (indicadorCelda.getPlanId() != null) {
              planId = "planId" + indicadorCelda.getPlanId().toString();
            }
            idsIndicadoresSeries = idsIndicadoresSeries + indicadorCelda.getPk().getIndicadorId() + planId + separadorSeries + indicadorCelda.getPk().getSerieId() + separadorIndicador;
          }
          if ((nombreIndicadoresSeries != "") && (idsIndicadoresSeries != "")) {
            editarCeldaForm.setNombresIndicadoresSeries(nombreIndicadoresSeries.substring(0, nombreIndicadoresSeries.length() - separadorIndicador.length()));
            editarCeldaForm.setIdsIndicadoresSeries(idsIndicadoresSeries.substring(0, idsIndicadoresSeries.length() - separadorIndicador.length()));
          }
        }

      }
      else
      {
        strategosCeldasService.unlockObject(request.getSession().getId(), new Long(celdaId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }
    }
    else
    {
      editarCeldaForm.clear();
      editarCeldaForm.setFila(new Byte(fila));
      editarCeldaForm.setColumna(new Byte(columna));

      Calendar ahora = Calendar.getInstance();
      editarCeldaForm.setMesInicio((byte) 1);
      editarCeldaForm.setAnoInicio(ahora.get(1));
      editarCeldaForm.setMesFin((byte) 12);
      editarCeldaForm.setAnoFin(ahora.get(1));
    }

    List tiposCelda = TipoCelda.getTiposCelda();
    editarCeldaForm.setTiposCelda(tiposCelda);

    int anoActual = FechaUtil.getAno(new Date());
    List anos = PeriodoUtil.getListaNumeros(new Integer(anoActual), new Byte((byte) 5));   
    editarCeldaForm.setGrupoAnos(anos);

    List meses = PeriodoUtil.getListaMeses();
    editarCeldaForm.setMeses(meses);

    editarCeldaForm.setFrecuencias(Frecuencia.getFrecuencias());

    strategosCeldasService.close();
    strategosClasesIndicadoresService.close();
    strategosIndicadoresService.close();
    strategosSeriesTiempoService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}