/**
 * 
 */
package com.visiongc.app.strategos.web.struts.planes.modelos.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosModelosService;
import com.visiongc.app.strategos.planes.model.Modelo;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.message.MessageService;
import com.visiongc.framework.model.Message;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class ReporteModelosAction extends VgcReporteBasicoAction
{
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("action.reportemodelos.titulo");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
		Long planId = (request.getParameter("planId") != null ? Long.parseLong(request.getParameter("planId")) : null);
		String filtroNombre = (request.getParameter("filtroNombre") != null ? request.getParameter("filtroNombre") : "");
	    MessageResources mensajes = getResources(request);
	    
		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());

		StrategosModelosService strategosModelosService = StrategosServiceFactory.getInstance().openStrategosModelosService();
		Plan plan = (Plan) strategosModelosService.load(Plan.class, planId);
		
		font.setSize(10.0F);
	    Paragraph texto = new Paragraph(mensajes.getMessage("action.reportemodelos.subtitulo.plan") + ": " + plan.getNombre(), font);
	    texto.setAlignment(Element.ALIGN_CENTER);
	    texto.setSpacingBefore(1.0F);
	    documento.add(texto);

	    TablaBasicaPDF tabla = null;
	    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
	    int[] columnas = new int[2];
	    columnas[0] = 40;
	    columnas[1] = 60;
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);

	    font.setSize(8.0F);
	    tabla.setFormatoFont(font.style());
	    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

	    tabla.agregarCelda(mensajes.getMessage("jsp.gestionar.modelos.columna.nombre"));
	    tabla.agregarCelda(mensajes.getMessage("jsp.gestionar.modelos.columna.descripcion"));

	    tabla.setDefaultAlineacionHorizontal();

		Map<String, Object> filtros = new HashMap<String, Object>();

		if (filtroNombre != null && !filtroNombre.equals("")) 
			filtros.put("nombre", filtroNombre);
		
		filtros.put("planId", plan.getPlanId());

		PaginaLista paginaModelos = strategosModelosService.getModelos(0, 0, "nombre", "ASC", true, filtros);

		strategosModelosService.close();
	    
	    if (paginaModelos.getLista().size() > 0) 
	    {
	    	font.setSize(8.0F);
	    	fontBold.setSize(8.0F);
	    	fontBold.setStyle(font.BOLD);
	    	for (Iterator<Modelo> iter = paginaModelos.getLista().iterator(); iter.hasNext(); ) 
	    	{
	    		Modelo modelo = (Modelo)iter.next();
    			tabla.setFormatoFont(font.style());
	    		tabla.setDefaultAlineacionHorizontal();
	    		tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
	        	tabla.agregarCelda(modelo.getNombre());

	        	tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
	        	tabla.agregarCelda(modelo.getDescripcion());
	    	}

	    	documento.add(tabla.getTabla());

	    	font.setSize(10.0F);
	    	texto = new Paragraph("Número de Modelos: " + Integer.toString(paginaModelos.getLista().size()), font);
	    	texto.setAlignment(Element.ALIGN_CENTER);
	    	documento.add(texto);
	    
	    }
	    else
	    {
	    	font.setSize(10.0F);
	    	texto = new Paragraph("No hay modelos para el plan", font);
	    	texto.setAlignment(Element.ALIGN_CENTER);
	    	texto.setSpacingBefore(72.0F);
	    	documento.add(texto);
	    }
	}
}