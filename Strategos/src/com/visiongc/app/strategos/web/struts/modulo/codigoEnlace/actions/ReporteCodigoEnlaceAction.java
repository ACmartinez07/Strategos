/**
 * 
 */
package com.visiongc.app.strategos.web.struts.modulo.codigoEnlace.actions;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.modulo.codigoenlace.StrategosCodigoEnlaceService;
import com.visiongc.app.strategos.modulo.codigoenlace.model.CodigoEnlace;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;

/**
 * @author Kerwin
 *
 */
public class ReporteCodigoEnlaceAction extends VgcReporteBasicoAction
{
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("action.reporte.codigo.enlace.titulo");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
	    String atributoOrden = request.getParameter("atributoOrden");
	    String tipoOrden = request.getParameter("tipoOrden");
		if (atributoOrden == null) 
			atributoOrden = "codigo";
		if (tipoOrden == null) 
			tipoOrden = "ASC";
		else if (tipoOrden.toUpperCase().indexOf("ASC") != -1)
			tipoOrden = "ASC";
		else
			tipoOrden = "DESC";

	    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

	    MessageResources mensajes = getResources(request);

	    StrategosCodigoEnlaceService strategosCodigoEnlaceService = StrategosServiceFactory.getInstance().openStrategosCodigoEnlaceService();

	    TablaBasicaPDF tabla = null;
	    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
	    int[] columnas = new int[4];
	    columnas[0] = 30;
	    columnas[1] = 50;
	    columnas[2] = 10;
	    columnas[3] = 10;
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);

	    List<CodigoEnlace> codigoEnlaces = strategosCodigoEnlaceService.getCodigoEnlace(0, 0, atributoOrden, tipoOrden, false, null).getLista();

	    tabla.setFormatoFont(font.style());
	    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

	    tabla.agregarCelda(mensajes.getMessage("action.reporte.columna.codigo"));
	    tabla.agregarCelda(mensajes.getMessage("action.reporte.columna.descripcion"));
	    tabla.agregarCelda(mensajes.getMessage("action.reporte.columna.bi"));
	    tabla.agregarCelda(mensajes.getMessage("action.reporte.columna.categoria"));

	    tabla.setDefaultAlineacionHorizontal();
	    if ((codigoEnlaces != null) && (codigoEnlaces.size() > 0)) 
	    {
	    	for (Iterator<CodigoEnlace> iter = codigoEnlaces.iterator(); iter.hasNext(); ) 
	    	{
	    		CodigoEnlace codigoEnlace = (CodigoEnlace)iter.next();

	    		tabla.setDefaultAlineacionHorizontal();
	    		tabla.agregarCelda(codigoEnlace.getCodigo());
	    		tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
	    		tabla.agregarCelda(codigoEnlace.getNombre());
	    		if (codigoEnlace.getBi() != null)
	    			tabla.agregarCelda(codigoEnlace.getBi().toString());
	    		else
	    			tabla.agregarCelda("");
	    		if (codigoEnlace.getCategoria() != null)
	    			tabla.agregarCelda(codigoEnlace.getCategoria().toString());
	    		else
	    			tabla.agregarCelda("");
	    	}

	    	documento.add(tabla.getTabla());
	    }
	    else
	    {
	    	tabla.setColspan(3);
	    	font.setStyle(2);
	    	Paragraph texto = new Paragraph(mensajes.getMessage("jsp.gestionarcodigoenlace.noregistros"), font);
	    	texto.setAlignment(0);
	    	documento.add(texto);
    	}

	    documento.newPage();

	    strategosCodigoEnlaceService.close();
	}
}