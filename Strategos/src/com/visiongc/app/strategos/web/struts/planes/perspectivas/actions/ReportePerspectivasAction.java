package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class ReportePerspectivasAction extends VgcReporteBasicoAction
{
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("action.reporteperspectivas.titulo");
  }

  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    String planId = request.getParameter("planId");

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[1];
    columnas[0] = 100;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    ArbolesService nodosArbolService = FrameworkServiceFactory.getInstance().openArbolesService();
    Perspectiva perspectivaRoot = new Perspectiva();
    Object[] arregloIdentificadores = new Object[2];
    arregloIdentificadores[0] = "planId";
    arregloIdentificadores[1] = new Long(planId);
    perspectivaRoot = (Perspectiva)nodosArbolService.getNodoArbolRaiz(perspectivaRoot, arregloIdentificadores);
    List perspectivas = nodosArbolService.crearReporteArbol(perspectivaRoot, new Boolean(true));

    nodosArbolService.close();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

    tabla.agregarCelda(mensajes.getMessage("action.reporteperspectivas.nombre"));

    documento.add(tabla.getTabla());

    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    tabla.setAmplitudTabla(100);
    tabla.setColorBorde(255, 255, 255);
    tabla.crearTabla(columnas);

    tabla.setDefaultAlineacionHorizontal();
    if ((perspectivas != null) && (perspectivas.size() > 0)) {
      for (Iterator iter = perspectivas.iterator(); iter.hasNext(); ) {
        String perspectiva = (String)iter.next();

        tabla.setDefaultAlineacionHorizontal();
        tabla.agregarCelda(perspectiva);
      }

      documento.add(tabla.getTabla());
    }

    documento.newPage();
  }
}