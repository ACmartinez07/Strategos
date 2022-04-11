package com.visiongc.app.strategos.web.struts.problemas.acciones.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.web.struts.problemas.acciones.forms.GestionarAccionesForm;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class ReporteAccionesAction extends VgcReporteBasicoAction
{
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("action.reporteaccionescorrectivas.titulo");
  }

  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[1];
    columnas[0] = 100;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    ArbolesService nodosArbolService = FrameworkServiceFactory.getInstance().openArbolesService();

    Accion accionRoot = new Accion();
    GestionarAccionesForm gestionarAccionesForm = (GestionarAccionesForm)request.getSession().getAttribute("gestionarAccionesForm");
    Object[] arregloIdentificadores = new Object[2];
    arregloIdentificadores[0] = "problemaId";
    arregloIdentificadores[1] = gestionarAccionesForm.getProblemaId();
    accionRoot = (Accion)nodosArbolService.getNodoArbolRaiz(accionRoot, arregloIdentificadores);

    List accionesCorrectivas = nodosArbolService.crearReporteArbol(accionRoot, new Boolean(true));

    nodosArbolService.close();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

    tabla.agregarCelda(mensajes.getMessage("action.reporteaccionescorrectivas.nombre"));

    documento.add(tabla.getTabla());

    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    tabla.setAmplitudTabla(100);
    tabla.setColorBorde(255, 255, 255);
    tabla.crearTabla(columnas);

    tabla.setDefaultAlineacionHorizontal();
    if ((accionesCorrectivas != null) && (accionesCorrectivas.size() > 0)) {
      for (Iterator iter = accionesCorrectivas.iterator(); iter.hasNext(); ) {
        String nombreAccion = (String)iter.next();

        tabla.setDefaultAlineacionHorizontal();
        tabla.agregarCelda(nombreAccion);
      }

      documento.add(tabla.getTabla());
    }

    documento.newPage();
  }
}