/**
 * 
 */
package com.visiongc.app.strategos.web.struts.iniciativas.estatus.actions;

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
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;

/**
 * @author Kerwin
 *
 */
public class ReporteIniciativaEstatusAction extends VgcReporteBasicoAction
{
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("action.reporte.estatus.iniciativa.titulo");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
		String nombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";

		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

		MessageResources mensajes = getResources(request);

		StrategosIniciativaEstatusService strategosIniciativaEstatusService = StrategosServiceFactory.getInstance().openStrategosIniciativaEstatusService();

		TablaBasicaPDF tabla = null;
		tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[1];
		columnas[0] = 100;
		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);
		
		Map<String, String> filtros = new HashMap<String, String>();

		if (nombre != null && !nombre.equals(""))
			filtros.put("nombre", nombre);

		List<IniciativaEstatus> iniciativaEstatus = strategosIniciativaEstatusService.getIniciativaEstatus(0, 0, "id", "asc", true, filtros).getLista();;
		
		tabla.setFormatoFont(font.style());
		tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

		tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre"));

		tabla.setDefaultAlineacionHorizontal();
		if ((iniciativaEstatus != null) && (iniciativaEstatus.size() > 0)) 
		{
			for (Iterator<IniciativaEstatus> iter = iniciativaEstatus.iterator(); iter.hasNext(); ) 
			{
				IniciativaEstatus iniciativaEstatu = (IniciativaEstatus)iter.next();
				
				tabla.setDefaultAlineacionHorizontal();
				tabla.agregarCelda(iniciativaEstatu.getNombre());
			}

			documento.add(tabla.getTabla());
		}
		else
		{
			tabla.setColspan(1);
			font.setStyle(2);
			Paragraph texto = new Paragraph(mensajes.getMessage("action.reporte.estatus.iniciativa.noestatus"), font);
			texto.setAlignment(0);
			documento.add(texto);
		}

		documento.newPage();

		strategosIniciativaEstatusService.close();
	}
}