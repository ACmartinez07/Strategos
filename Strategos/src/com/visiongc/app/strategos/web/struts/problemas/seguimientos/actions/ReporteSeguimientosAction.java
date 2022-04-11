package com.visiongc.app.strategos.web.struts.problemas.seguimientos.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosSeguimientosService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.Seguimiento;
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

public class ReporteSeguimientosAction extends VgcReporteBasicoAction
{
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("action.reporteseguimientos.titulo");
  }

  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    String atributoOrden = request.getParameter("atributoOrden");
    String tipoOrden = request.getParameter("tipoOrden");

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    StrategosSeguimientosService strategosSeguimientosService = StrategosServiceFactory.getInstance().openStrategosSeguimientosService();

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[5];
    columnas[0] = 20;
    columnas[1] = 20;
    columnas[2] = 20;
    columnas[3] = 20;
    columnas[4] = 20;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    Map filtros = new HashMap();
    Accion accionCorrectiva = (Accion)request.getSession().getAttribute("accionCorrectiva");
    filtros.put("accionId", accionCorrectiva.getAccionId().toString());

    List seguimientos = strategosSeguimientosService.getSeguimientos(0, 0, atributoOrden, tipoOrden, false, filtros).getLista();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

    tabla.agregarCelda(mensajes.getMessage("action.reporteseguimientos.numeroreporte"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteseguimientos.fechasolicitud"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteseguimientos.fecharecepcion"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteseguimientos.estadoreportado"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteseguimientos.aprobado"));

    tabla.setDefaultAlineacionHorizontal();
    if ((seguimientos != null) && (seguimientos.size() > 0)) {
      for (Iterator iter = seguimientos.iterator(); iter.hasNext(); )
      {
        Seguimiento seguimiento = (Seguimiento)iter.next();
        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

        tabla.agregarCelda(seguimiento.getNumeroReporte().toString());

        tabla.agregarCelda(VgcFormatter.formatearFecha(seguimiento.getFechaEmision(), "formato.fecha.corta"));

        if (seguimiento.getFechaRecepcion() != null)
          tabla.agregarCelda(VgcFormatter.formatearFecha(seguimiento.getFechaRecepcion(), "formato.fecha.corta"));
        else {
          tabla.agregarCelda("---");
        }

        if ((seguimiento.getEstadoId() != null) && (seguimiento.getEstadoId().byteValue() != 0)) {
          EstadoAcciones estadoAcciones = (EstadoAcciones)strategosSeguimientosService.load(EstadoAcciones.class, seguimiento.getEstadoId());
          tabla.agregarCelda(estadoAcciones.getNombre());
        } else {
          tabla.agregarCelda("---");
        }

        if ((seguimiento.getAprobado() != null) && (seguimiento.getAprobado().booleanValue()))
          tabla.agregarCelda(mensajes.getMessage("comunes.si"));
        else if ((seguimiento.getAprobado() != null) && (!seguimiento.getAprobado().booleanValue())) {
          tabla.agregarCelda(mensajes.getMessage("comunes.no"));
        }

      }

      documento.add(tabla.getTabla());
    }
    else {
      tabla.setColspan(5);
      font.setStyle(2);
      Paragraph texto = new Paragraph(mensajes.getMessage("action.reporteseguimientos.noproblemas"), font);
      texto.setAlignment(0);
      documento.add(texto);
    }

    documento.newPage();

    strategosSeguimientosService.close();
  }
}