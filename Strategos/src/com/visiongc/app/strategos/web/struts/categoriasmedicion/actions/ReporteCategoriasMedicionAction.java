package com.visiongc.app.strategos.web.struts.categoriasmedicion.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
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

public class ReporteCategoriasMedicionAction extends VgcReporteBasicoAction
{
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
  {
	  return mensajes.getMessage("action.reportecategoriasmedicion.titulo");
  }

  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
  {
    String atributoOrden = request.getParameter("atributoOrden");
    String tipoOrden = request.getParameter("tipoOrden");

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    StrategosCategoriasService strategosCategoriasService = StrategosServiceFactory.getInstance().openStrategosCategoriasService();

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[1];
    columnas[0] = 80;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    List<?> categoria = strategosCategoriasService.getCategoriasMedicion(0, 0, atributoOrden, tipoOrden, false, null).getLista();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

    tabla.agregarCelda(mensajes.getMessage("action.reportecategoriasmedicion.nombre"));

    tabla.setDefaultAlineacionHorizontal();
    if ((categoria != null) && (categoria.size() > 0)) {
      for (Iterator<?> iter = categoria.iterator(); iter.hasNext(); ) {
        CategoriaMedicion unidad = (CategoriaMedicion)iter.next();

        tabla.agregarCelda(unidad.getNombre());
      }

      documento.add(tabla.getTabla());
    }
    else
    {
      tabla.setColspan(3);
      font.setStyle(2);
      Paragraph texto = new Paragraph(" " + mensajes.getMessage("action.reportecategoriasmedicion.nocategorias"), font);
      texto.setAlignment(0);
      documento.add(texto);
    }

    documento.newPage();

    strategosCategoriasService.close();
  }
}