/**
 * 
 */
package com.visiongc.app.strategos.web.struts.portafolios.actions;

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
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.CondicionType;

/**
 * @author Kerwin
 *
 */
public class ReportePortafoliosAction extends VgcReporteBasicoAction
{
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("action.reporteportafolio.titulo");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
		String atributoOrden = request.getParameter("atributoOrden");
		String tipoOrden = request.getParameter("tipoOrden");
		String nombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
		Byte selectCondicionType = (request.getParameter("selectCondicionType") != null && request.getParameter("selectCondicionType") != "") ? Byte.parseByte(request.getParameter("selectCondicionType")) : CondicionType.getFiltroCondicionActivo();

		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

		MessageResources mensajes = getResources(request);

		StrategosPortafoliosService strategosPortafoliosService = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();

		TablaBasicaPDF tabla = null;
		tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[1];
		columnas[0] = 100;
		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);
		
		Map<String, String> filtros = new HashMap<String, String>();

	    if (atributoOrden == null || atributoOrden.equals("")) 
	    	atributoOrden = "nombre";
	    if (tipoOrden == null || tipoOrden.equals("")) 
	    	tipoOrden = "ASC";
		
		if (nombre != null && !nombre.equals(""))
			filtros.put("nombre", nombre);
		if (selectCondicionType != null && 
				(selectCondicionType.byteValue() == CondicionType.getFiltroCondicionActivo() || selectCondicionType.byteValue() == CondicionType.getFiltroCondicionInactivo()))
			filtros.put("activo", selectCondicionType.toString());

		List<Portafolio> portafolios = strategosPortafoliosService.getPortafolios(0, 0, atributoOrden, tipoOrden, false, filtros).getLista();

		tabla.setFormatoFont(font.style());
		tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

		tabla.agregarCelda(mensajes.getMessage("action.reporteportafolio.nombre"));

		tabla.setDefaultAlineacionHorizontal();
		if ((portafolios != null) && (portafolios.size() > 0)) 
		{
			for (Iterator<Portafolio> iter = portafolios.iterator(); iter.hasNext(); ) 
			{
				Portafolio portafolio = (Portafolio)iter.next();
				
				tabla.setDefaultAlineacionHorizontal();
				tabla.agregarCelda(portafolio.getNombre());
			}

			documento.add(tabla.getTabla());
		}
		else
		{
			tabla.setColspan(1);
			font.setStyle(2);
			Paragraph texto = new Paragraph(mensajes.getMessage("action.reporteportafolio.no.hay.portafolios"), font);
			texto.setAlignment(0);
			documento.add(texto);
		}

		documento.newPage();

		strategosPortafoliosService.close();
	}
}