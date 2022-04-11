package com.visiongc.app.strategos.web.struts.reportes.grafico.actions;

import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.grafico.forms.SeleccionarTiempoGraficoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoValorNombre;
import com.visiongc.commons.web.NavigationBar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarTiempoGraficoAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    SeleccionarTiempoGraficoForm seleccionarTiempoForm = (SeleccionarTiempoGraficoForm)form;

    String atributoOrden = seleccionarTiempoForm.getAtributoOrden();
    String tipoOrden = seleccionarTiempoForm.getTipoOrden();

    seleccionarTiempoForm.setFuncionCierre(request.getParameter("funcionCierre"));

    if ((seleccionarTiempoForm.getFrecuenciaBloqueada() == null) || (seleccionarTiempoForm.getFrecuenciaBloqueada().equals(new Byte("0"))))
    {
      seleccionarTiempoForm.setFrecuenciaBloqueada(request.getParameter("frecuenciaBloqueada") == null ? new Byte("0") : new Byte(request.getParameter("frecuenciaBloqueada")));
    }

    if (request.getParameter("frecuencia") != null) {
      byte frecuencia = request.getParameter("frecuencia") == null ? Frecuencia.getFrecuenciaMensual().byteValue() : new Byte(request.getParameter("frecuencia")).byteValue();
      seleccionarTiempoForm.setFrecuencia(new Byte(frecuencia));
    }

    seleccionarTiempoForm.setNombreFrecuencia(Frecuencia.getNombre(seleccionarTiempoForm.getFrecuencia().byteValue()));

    if (atributoOrden == null) {
      atributoOrden = "nombre";
      seleccionarTiempoForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      seleccionarTiempoForm.setTipoOrden(tipoOrden);
    }

    if (seleccionarTiempoForm.getFuncionCierre() != null) {
      if (!seleccionarTiempoForm.getFuncionCierre().equals("")) {
        if (seleccionarTiempoForm.getFuncionCierre().indexOf(";") < 0)
          seleccionarTiempoForm.setFuncionCierre(seleccionarTiempoForm.getFuncionCierre() + ";");
      }
      else {
        seleccionarTiempoForm.setFuncionCierre(null);
      }

    }

    if ((seleccionarTiempoForm.getAnoInicial() == null) || (seleccionarTiempoForm.getAnoInicial().equals(new Integer(0)))) {
      seleccionarTiempoForm.setAnoInicial(new Integer(Calendar.getInstance().get(1)));
      seleccionarTiempoForm.setAnoFinal(new Integer(Calendar.getInstance().get(1)));

      seleccionarTiempoForm.setPeriodoInicial(new Integer(1));
      seleccionarTiempoForm.setPeriodoFinal(new Integer(12));
    }

    int numeroMaximoPeriodos = 0;

    if ((seleccionarTiempoForm.getAnoInicial().intValue() % 4 == 0) && (seleccionarTiempoForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
      numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(seleccionarTiempoForm.getFrecuencia().byteValue(), seleccionarTiempoForm.getAnoInicial().intValue());
    else if ((seleccionarTiempoForm.getAnoFinal().intValue() % 4 == 0) && (seleccionarTiempoForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
      numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(seleccionarTiempoForm.getFrecuencia().byteValue(), seleccionarTiempoForm.getAnoFinal().intValue());
    else {
      numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(seleccionarTiempoForm.getFrecuencia().byteValue(), seleccionarTiempoForm.getAnoInicial().intValue());
    }

    if (seleccionarTiempoForm.getPeriodoFinal().intValue() > numeroMaximoPeriodos) {
      seleccionarTiempoForm.setPeriodoFinal(new Integer(numeroMaximoPeriodos));
    }

    if (seleccionarTiempoForm.getPeriodoInicial().intValue() > numeroMaximoPeriodos) {
      seleccionarTiempoForm.setPeriodoInicial(new Integer(1));
    }

    seleccionarTiempoForm.setAnosPeriodos(getListaAnosPeriodos(seleccionarTiempoForm.getPeriodoInicial(), seleccionarTiempoForm.getAnoInicial(), seleccionarTiempoForm.getPeriodoFinal(), seleccionarTiempoForm.getAnoFinal(), numeroMaximoPeriodos));

    if (seleccionarTiempoForm.getAnosPeriodos().size() > 0) {
      seleccionarTiempoForm.setSeleccionados(((ObjetoValorNombre)seleccionarTiempoForm.getAnosPeriodos().get(0)).getValor());
      seleccionarTiempoForm.setValoresSeleccionados(((ObjetoValorNombre)seleccionarTiempoForm.getAnosPeriodos().get(0)).getNombre());
    } else {
      seleccionarTiempoForm.setSeleccionados("");
      seleccionarTiempoForm.setValoresSeleccionados("");
    }

    seleccionarTiempoForm.setListaAnos(getListaAnos(new Integer(Calendar.getInstance().get(1))));
    seleccionarTiempoForm.setListaPeriodos(getListaPeriodos(new Integer(numeroMaximoPeriodos)));

    if (seleccionarTiempoForm.getSeleccionMultiple() == null) {
      seleccionarTiempoForm.setSeleccionMultiple(new Byte("0"));
    }

    if (request.getParameter("seleccionMultiple") != null) {
      seleccionarTiempoForm.setSeleccionMultiple(new Byte(request.getParameter("seleccionMultiple")));
    }

    seleccionarTiempoForm.setFrecuencias(Frecuencia.getFrecuencias());

    request.setAttribute("seleccionMultiple", request.getParameter("seleccionMultiple"));

    return mapping.findForward(forward);
  }

  private List getListaAnos(Integer anoBase)
  {
    List listaAnos = new ArrayList();

    ObjetoValorNombre elementoAno = new ObjetoValorNombre();
    for (int ano = anoBase.intValue() - 10; ano <= anoBase.intValue() + 10; ano++) {
      elementoAno = new ObjetoValorNombre();
      elementoAno.setNombre(String.valueOf(ano));
      elementoAno.setValor(String.valueOf(ano));

      listaAnos.add(elementoAno);
    }

    return listaAnos;
  }

  private List getListaPeriodos(Integer periodoFinal)
  {
    List listaPeriodos = new ArrayList();

    ObjetoValorNombre elementoPeriodo = new ObjetoValorNombre();
    for (int periodo = 1; periodo <= periodoFinal.intValue(); periodo++) {
      elementoPeriodo = new ObjetoValorNombre();
      elementoPeriodo.setNombre(String.valueOf(periodo));
      elementoPeriodo.setValor(String.valueOf(periodo));

      listaPeriodos.add(elementoPeriodo);
    }

    return listaPeriodos;
  }

  private List getListaAnosPeriodos(Integer periodoInicial, Integer anoInicial, Integer periodoFinal, Integer anoFinal, int numeroMaximoPeriodos) {
    List listaAnosPeriodos = new ArrayList();
    int inicio = 1;
    int fin = 1;

    for (int ano = anoInicial.intValue(); ano <= anoFinal.intValue(); ano++) {
      if (anoInicial.intValue() != anoFinal.intValue()) {
        if (ano == anoInicial.intValue()) {
          inicio = periodoInicial.intValue();
          fin = numeroMaximoPeriodos;
        } else if (ano == anoFinal.intValue()) {
          inicio = 1;
          fin = periodoFinal.intValue();
        } else {
          inicio = 1;
          fin = numeroMaximoPeriodos;
        }
      } else {
        inicio = periodoInicial.intValue();
        fin = periodoFinal.intValue();
      }
      for (int periodo = inicio; periodo <= fin; periodo++) {
        ObjetoValorNombre elementoAnoPeriodo = new ObjetoValorNombre();
        elementoAnoPeriodo.setNombre(String.valueOf(periodo) + '/' + String.valueOf(ano));
        elementoAnoPeriodo.setValor(String.valueOf(periodo) + '_' + String.valueOf(ano));

        listaAnosPeriodos.add(elementoAnoPeriodo);
      }
    }

    return listaAnosPeriodos;
  }
}