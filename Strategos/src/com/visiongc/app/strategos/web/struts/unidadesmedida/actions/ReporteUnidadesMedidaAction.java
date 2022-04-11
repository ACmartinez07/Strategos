package com.visiongc.app.strategos.web.struts.unidadesmedida.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
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

public class ReporteUnidadesMedidaAction extends VgcReporteBasicoAction
{
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("action.reporteunidadesmedida.titulo");
  }

  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    String atributoOrden = request.getParameter("atributoOrden");
    String tipoOrden = request.getParameter("tipoOrden");

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[2];
    columnas[0] = 80;
    columnas[1] = 20;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    List unidadesMedida = strategosUnidadesService.getUnidadesMedida(0, 0, atributoOrden, tipoOrden, false, null).getLista();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

    tabla.agregarCelda(mensajes.getMessage("action.reporteunidadesmedida.nombre"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteunidadesmedida.monetaria"));

    tabla.setDefaultAlineacionHorizontal();
    if ((unidadesMedida != null) && (unidadesMedida.size() > 0)) {
      for (Iterator iter = unidadesMedida.iterator(); iter.hasNext(); ) {
        UnidadMedida unidad = (UnidadMedida)iter.next();

        tabla.setDefaultAlineacionHorizontal();
        tabla.agregarCelda(unidad.getNombre());

        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
        if ((unidad.getTipo() != null) && (unidad.getTipo().booleanValue()))
          tabla.agregarCelda(mensajes.getMessage("comunes.si"));
        else if ((unidad.getTipo() != null) && (!unidad.getTipo().equals("true"))) {
          tabla.agregarCelda(mensajes.getMessage("comunes.no"));
        }

      }

      documento.add(tabla.getTabla());
    }
    else
    {
      tabla.setColspan(3);
      font.setStyle(2);
      Paragraph texto = new Paragraph(mensajes.getMessage("action.reporteunidadesmedida.nounidades"), font);
      texto.setAlignment(0);
      documento.add(texto);
    }

    documento.newPage();

    strategosUnidadesService.close();
  }
}