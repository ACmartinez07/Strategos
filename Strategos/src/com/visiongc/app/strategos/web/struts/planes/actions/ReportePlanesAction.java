package com.visiongc.app.strategos.web.struts.planes.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class ReportePlanesAction extends VgcReporteBasicoAction
{
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("action.reporteplanes.titulo");
  }

  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    String atributoOrden = request.getParameter("atributoOrden");
    String tipoOrden = request.getParameter("tipoOrden");
    String tipo = request.getParameter("tipo");
    String organizacionId = (String)request.getSession().getAttribute("organizacionId");

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[4];
    columnas[0] = 80;
    columnas[1] = 20;
    columnas[2] = 20;
    columnas[3] = 20;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    Map filtros = new HashMap();

    if ((organizacionId != null) && (!organizacionId.equals(""))) {
      filtros.put("organizacionId", organizacionId);
    }

    filtros.put("tipo", tipo);

    List planes = strategosPlanesService.getPlanes(0, 0, atributoOrden, tipoOrden, false, filtros).getLista();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

    tabla.agregarCelda(mensajes.getMessage("action.reporteplanes.nombre"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteplanes.anoinicial"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteplanes.anofinal"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteplanes.revision"));

    tabla.setDefaultAlineacionHorizontal();
    if ((planes != null) && (planes.size() > 0)) {
      for (Iterator iter = planes.iterator(); iter.hasNext(); ) {
        Plan plan = (Plan)iter.next();

        tabla.setDefaultAlineacionHorizontal();
        tabla.agregarCelda(plan.getNombre());

        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
        tabla.agregarCelda(plan.getAnoInicial().toString());
        tabla.agregarCelda(plan.getAnoFinal().toString());
        tabla.agregarCelda("[" + plan.getRevisionNombre() + "]");
      }

      documento.add(tabla.getTabla());
    }
    else
    {
      tabla.setColspan(4);
      font.setStyle(2);
      Paragraph texto = new Paragraph(" " + mensajes.getMessage("action.reporteplanes.noplanes"), font);
      texto.setAlignment(0);
      documento.add(texto);
    }

    documento.newPage();

    strategosPlanesService.close();
  }
}