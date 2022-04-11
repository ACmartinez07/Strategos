/**
 * 
 */
package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.TipoCalculoEstadoIniciativa;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPrdProductosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimiento;
import com.visiongc.app.strategos.planificacionseguimiento.model.util.AlertaIniciativaProducto;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.util.WebUtil;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.message.MessageService;
import com.visiongc.framework.model.Message;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class ReporteVistaDatosAction extends VgcReporteBasicoAction
{
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("jsp.reporte.vistadatos.titulo");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
		documento.setPageSize(PageSize.LETTER.rotate());

	    String organizacionId = (String)request.getSession().getAttribute("organizacionId");
	    String atributoOrden = request.getParameter("atributoOrden");
	    String tipoOrden = request.getParameter("tipoOrden");
	    if ((atributoOrden == null) || (atributoOrden.equals(""))) 
	    	atributoOrden = "nombre";
	    if ((tipoOrden == null) || (tipoOrden.equals(""))) 
	    	tipoOrden = "ASC";

	    MessageResources mensajes = getResources(request);

	    Map<String, Object> filtros = new HashMap<String, Object>();

	    if ((organizacionId != null) && (!organizacionId.equals(""))) 
	    	filtros.put("organizacionId", organizacionId);

	    StrategosReportesService strategosReportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();
	    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
	    PaginaLista paginaVistas = strategosReportesService.getReportes(0, 30, atributoOrden, tipoOrden, true, filtros, usuario.getUsuarioId());
	    strategosReportesService.close();

		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());

		font.setSize(10.0F);
	    Paragraph texto = new Paragraph(mensajes.getMessage("action.reportealertas.subtitulo.usuario") + ": " + usuario.getFullName(), font);
	    texto.setAlignment(0);
	    texto.setSpacingBefore(1.0F);
	    documento.add(texto);

	    TablaBasicaPDF tabla = null;
	    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
	    int[] columnas = new int[3];
	    columnas[0] = 20;
	    columnas[1] = 20;
	    columnas[2] = 60;
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);

	    font.setSize(8.0F);
	    tabla.setFormatoFont(font.style());
	    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

	    tabla.agregarCelda(mensajes.getMessage("jsp.gestionarvistasdatos.columna.nombre"));
	    tabla.agregarCelda(mensajes.getMessage("jsp.gestionarvistasdatos.columna.public"));
	    tabla.agregarCelda(mensajes.getMessage("jsp.gestionarvistasdatos.columna.descripcion"));

	    tabla.setDefaultAlineacionHorizontal();
	    
	    if (paginaVistas.getLista().size() > 0) 
	    {
	    	font.setSize(8.0F);
	    	fontBold.setSize(8.0F);
	    	fontBold.setStyle(font.BOLD);
	    	for (Iterator<Reporte> iter = paginaVistas.getLista().iterator(); iter.hasNext(); ) 
	    	{
	    		Reporte reporte = (Reporte)iter.next();
    			tabla.setFormatoFont(font.style());
	    		tabla.setDefaultAlineacionHorizontal();
	    		tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
	        	tabla.agregarCelda(reporte.getNombre());

	        	tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
	        	if (reporte.getPublico())
	        		tabla.agregarCelda(mensajes.getMessage("comunes.si"));
	        	else
	        		tabla.agregarCelda(mensajes.getMessage("comunes.no"));

	        	tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
	        	tabla.agregarCelda(reporte.getDescripcion());
	    	}

	    	documento.add(tabla.getTabla());
	    }
	    else
	    {
	    	font.setSize(10.0F);
	    	texto = new Paragraph("No hay Vistas creadas para el usuario", font);
	    	texto.setAlignment(0);
	    	texto.setSpacingBefore(72.0F);
	    	documento.add(texto);
	    }

	    documento.newPage();
	}
}