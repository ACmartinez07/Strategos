package com.visiongc.app.strategos.web.struts.presentaciones.paginas.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
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

public class ReportePaginasAction extends VgcReporteBasicoAction
{
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("action.reportepaginas.titulo");
  }

  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    String atributoOrden = request.getParameter("atributoOrden");
    String tipoOrden = request.getParameter("tipoOrden");

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    StrategosPaginasService strategosPaginasService = StrategosServiceFactory.getInstance().openStrategosPaginasService();

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[3];
    columnas[0] = 33;
    columnas[1] = 34;
    columnas[2] = 33;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    Map filtros = new HashMap();
    String vistaId = (String)request.getSession().getAttribute("vistaId");

    if ((vistaId == null) || (vistaId.equals(""))) {
      vistaId = "0";
    }

    filtros.put("vistaId", vistaId);

    List paginas = strategosPaginasService.getPaginas(0, 0, atributoOrden, tipoOrden, false, filtros).getLista();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

    tabla.agregarCelda(mensajes.getMessage("action.reportepaginas.descripcion"));
    tabla.agregarCelda(mensajes.getMessage("action.reportepaginas.filas"));
    tabla.agregarCelda(mensajes.getMessage("action.reportepaginas.columnas"));
    tabla.setDefaultAlineacionHorizontal();

    if ((paginas != null) && (paginas.size() > 0)) {
      for (Iterator iter = paginas.iterator(); iter.hasNext(); ) {
        Pagina pagina = (Pagina)iter.next();

        tabla.setDefaultAlineacionHorizontal();

        tabla.agregarCelda(pagina.getDescripcion());

        tabla.agregarCelda(pagina.getFilas().toString());

        tabla.agregarCelda(pagina.getColumnas().toString());
      }

      documento.add(tabla.getTabla());
    }
    else
    {
      tabla.setColspan(3);
      font.setStyle(2);
      Paragraph texto = new Paragraph(" " + mensajes.getMessage("action.reportepaginas.nopaginas"), font);
      texto.setAlignment(0);
      documento.add(texto);
    }

    documento.newPage();

    strategosPaginasService.close();
  }
}