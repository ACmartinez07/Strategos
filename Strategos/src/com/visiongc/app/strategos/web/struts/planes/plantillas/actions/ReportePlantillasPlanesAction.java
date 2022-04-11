package com.visiongc.app.strategos.web.struts.planes.plantillas.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlantillasPlanesService;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
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

public class ReportePlantillasPlanesAction extends VgcReporteBasicoAction
{
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("action.reporteplantillasplanes.titulo");
  }

  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    String atributoOrden = request.getParameter("atributoOrden");
    String tipoOrden = request.getParameter("tipoOrden");

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    StrategosPlantillasPlanesService strategosPlantillasPlanesService = StrategosServiceFactory.getInstance().openStrategosPlantillasPlanesService();

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[2];
    columnas[0] = 80;
    columnas[1] = 20;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    List plantillasPlanes = strategosPlantillasPlanesService.getPlantillasPlanes(0, 0, atributoOrden, tipoOrden, false, null).getLista();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

    tabla.agregarCelda(mensajes.getMessage("action.reporteplantillasplanes.nombre"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteplantillasplanes.descripcion"));

    tabla.setDefaultAlineacionHorizontal();
    if ((plantillasPlanes != null) && (plantillasPlanes.size() > 0)) {
      for (Iterator iter = plantillasPlanes.iterator(); iter.hasNext(); ) {
        PlantillaPlanes plantillaPlanes = (PlantillaPlanes)iter.next();

        tabla.setDefaultAlineacionHorizontal();
        tabla.agregarCelda(plantillaPlanes.getNombre());
        tabla.agregarCelda(plantillaPlanes.getDescripcion());
      }

      documento.add(tabla.getTabla());
    }
    else
    {
      tabla.setColspan(2);
      font.setStyle(2);
      Paragraph texto = new Paragraph(" " + mensajes.getMessage("action.reporteplantillasplanes.noplantillasplanes"), font);
      texto.setAlignment(0);
      documento.add(texto);
    }

    documento.newPage();

    strategosPlantillasPlanesService.close();
  }
}