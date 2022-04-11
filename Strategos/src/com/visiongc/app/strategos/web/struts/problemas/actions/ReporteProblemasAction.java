package com.visiongc.app.strategos.web.struts.problemas.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
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

public class ReporteProblemasAction extends VgcReporteBasicoAction
{
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("action.reporteproblemas.titulo");
  }

  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    String atributoOrden = request.getParameter("atributoOrden");
    String tipoOrden = request.getParameter("tipoOrden");

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    StrategosProblemasService strategosProblemasService = StrategosServiceFactory.getInstance().openStrategosProblemasService();

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[3];
    columnas[0] = 60;
    columnas[1] = 10;
    columnas[2] = 30;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    Map filtros = new HashMap();
    Long claseProblemasId = new Long((String)request.getSession().getAttribute("claseProblemasId"));
    filtros.put("claseId", claseProblemasId.toString());

    List problemas = strategosProblemasService.getProblemas(0, 0, atributoOrden, tipoOrden, false, filtros).getLista();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

    tabla.agregarCelda(mensajes.getMessage("action.reporteproblemas.nombre"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteproblemas.fecha"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteproblemas.estado"));

    tabla.setDefaultAlineacionHorizontal();
    if ((problemas != null) && (problemas.size() > 0)) {
      for (Iterator iter = problemas.iterator(); iter.hasNext(); )
      {
        Problema problema = (Problema)iter.next();

        tabla.setDefaultAlineacionHorizontal();
        tabla.agregarCelda(problema.getNombre());

        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
        tabla.agregarCelda(VgcFormatter.formatearFecha(problema.getFecha(), "formato.fecha.corta"));

        tabla.setDefaultAlineacionHorizontal();
        if ((problema.getEstadoId() != null) && (problema.getEstadoId().byteValue() != 0)) {
          EstadoAcciones estadoAcciones = (EstadoAcciones)strategosProblemasService.load(EstadoAcciones.class, problema.getEstadoId());
          tabla.agregarCelda(estadoAcciones.getNombre());
        } else {
          tabla.agregarCelda("---");
        }

      }

      documento.add(tabla.getTabla());
    }
    else
    {
      tabla.setColspan(3);
      font.setStyle(2);
      Paragraph texto = new Paragraph(mensajes.getMessage("action.reporteproblemas.noproblemas"), font);
      texto.setAlignment(0);
      documento.add(texto);
    }

    documento.newPage();

    strategosProblemasService.close();
  }
}