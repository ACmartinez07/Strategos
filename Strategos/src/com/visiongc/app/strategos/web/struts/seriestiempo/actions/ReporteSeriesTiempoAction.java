package com.visiongc.app.strategos.web.struts.seriestiempo.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
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

public class ReporteSeriesTiempoAction extends VgcReporteBasicoAction
{
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("action.reporteseriestiempo.titulo");
  }

  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    String atributoOrden = request.getParameter("atributoOrden");
    String tipoOrden = request.getParameter("tipoOrden");

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService();

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[3];
    columnas[0] = 75;
    columnas[1] = 15;
    columnas[2] = 10;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    List seriesTiempo = strategosSeriesTiempoService.getSeriesTiempo(0, 0, atributoOrden, tipoOrden, false, null).getLista();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

    tabla.agregarCelda(mensajes.getMessage("action.reporteseriestiempo.nombre"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteseriestiempo.identificador"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteseriestiempo.fija"));

    tabla.setDefaultAlineacionHorizontal();
    if ((seriesTiempo != null) && (seriesTiempo.size() > 0)) {
      for (Iterator iter = seriesTiempo.iterator(); iter.hasNext(); ) {
        SerieTiempo serie = (SerieTiempo)iter.next();

        tabla.setDefaultAlineacionHorizontal();
        tabla.agregarCelda(serie.getNombre());

        tabla.agregarCelda(serie.getIdentificador());

        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
        if ((serie.getFija() != null) && (serie.getFija().booleanValue()))
          tabla.agregarCelda(mensajes.getMessage("comunes.si"));
        else if ((serie.getFija() != null) && (!serie.getFija().equals("true"))) {
          tabla.agregarCelda(mensajes.getMessage("comunes.no"));
        }

      }

      documento.add(tabla.getTabla());
    }
    else
    {
      tabla.setColspan(3);
      font.setStyle(2);
      Paragraph texto = new Paragraph(mensajes.getMessage("action.reporteseriestiempo.noseries"), font);
      texto.setAlignment(0);
      documento.add(texto);
    }

    documento.newPage();

    strategosSeriesTiempoService.close();
  }
}