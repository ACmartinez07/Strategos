package com.visiongc.app.strategos.web.struts.estadosacciones.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.estadosacciones.StrategosEstadosService;
import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class ReporteEstadoAccionesAction extends VgcReporteBasicoAction
{
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("action.reporteestadosacciones.titulo");
  }

  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    StrategosEstadosService strategosEstadosService = StrategosServiceFactory.getInstance().openStrategosEstadosService();

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[4];
    columnas[0] = 20;
    columnas[1] = 20;
    columnas[2] = 20;
    columnas[3] = 20;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    List estadoAcciones = strategosEstadosService.getEstadosAcciones(0, 0, "orden", "asc", false, null).getLista();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

    tabla.agregarCelda(mensajes.getMessage("action.reporteestadosacciones.nombre"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteestadosacciones.aplicaseguimiento"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteestadosacciones.orden"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteestadosacciones.condicion"));
    tabla.setDefaultAlineacionHorizontal();

    if ((estadoAcciones != null) && (estadoAcciones.size() > 0)) {
      for (Iterator iter = estadoAcciones.iterator(); iter.hasNext(); ) {
        EstadoAcciones estadoAccion = (EstadoAcciones)iter.next();

        tabla.setDefaultAlineacionHorizontal();

        tabla.agregarCelda(estadoAccion.getNombre());

        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
        if ((estadoAccion.getAplicaSeguimiento() != null) && (estadoAccion.getAplicaSeguimiento().booleanValue()))
          tabla.agregarCelda(mensajes.getMessage("comunes.si"));
        else if ((estadoAccion.getAplicaSeguimiento() != null) && (!estadoAccion.getAplicaSeguimiento().equals("true"))) {
          tabla.agregarCelda(mensajes.getMessage("comunes.no"));
        }

        tabla.agregarCelda(estadoAccion.getOrden().toString());

        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
        if ((estadoAccion.getCondicion() != null) && (estadoAccion.getCondicion().booleanValue()))
          tabla.agregarCelda(mensajes.getMessage("comunes.si"));
        else if ((estadoAccion.getCondicion() != null) && (!estadoAccion.getCondicion().equals("true"))) {
          tabla.agregarCelda(mensajes.getMessage("comunes.no"));
        }

      }

      documento.add(tabla.getTabla());
    }
    else
    {
      tabla.setColspan(4);
      font.setStyle(2);
      Paragraph texto = new Paragraph(" " + mensajes.getMessage("action.reporteestadosacciones.noestadosacciones"), font);
      texto.setAlignment(0);
      documento.add(texto);
    }

    documento.newPage();

    strategosEstadosService.close();
  }
}