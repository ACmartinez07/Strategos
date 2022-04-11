package com.visiongc.app.strategos.web.struts.presentaciones.vistas.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.presentaciones.StrategosVistasService;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class ReporteVistasAction extends VgcReporteBasicoAction
{
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("action.reportevistas.titulo");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
		String atributoOrden = request.getParameter("atributoOrden");
		String tipoOrden = request.getParameter("tipoOrden");

		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

		MessageResources mensajes = getResources(request);

		StrategosVistasService strategosVistasService = StrategosServiceFactory.getInstance().openStrategosVistasService();

		TablaBasicaPDF tabla = null;
		tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[3];
		columnas[0] = 33;
		columnas[1] = 34;
		columnas[2] = 33;
		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);
		
		Map<String, String> filtros = new HashMap<String, String>();
		String organizacionId = (String)request.getSession().getAttribute("organizacionId");

		if ((organizacionId == null) || (organizacionId.equals(""))) 
			organizacionId = "0";

		filtros.put("organizacionId", organizacionId);

		List<Vista> vistas = strategosVistasService.getVistas(0, 0, atributoOrden, tipoOrden, false, filtros).getLista();

		tabla.setFormatoFont(font.style());
		tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
		tabla.agregarCelda(mensajes.getMessage("action.reportevistas.nombre"));
		tabla.agregarCelda(mensajes.getMessage("action.reportevistas.fechainicio"));
		tabla.agregarCelda(mensajes.getMessage("action.reportevistas.fechafin"));
		tabla.setDefaultAlineacionHorizontal();

		if ((vistas != null) && (vistas.size() > 0)) 
		{
			for (Iterator<Vista> iter = vistas.iterator(); iter.hasNext(); ) 
			{
				Vista vista = (Vista)iter.next();

				tabla.setDefaultAlineacionHorizontal();
				tabla.agregarCelda(vista.getNombre());
				tabla.agregarCelda(vista.getFechaInicio() != null ? vista.getFechaInicio().toString() : "");
				tabla.agregarCelda(vista.getFechaFin() != null ? vista.getFechaFin().toString() : "");
			}

			documento.add(tabla.getTabla());
		}
		else
		{
			tabla.setColspan(3);
			font.setStyle(2);
			Paragraph texto = new Paragraph(" " + mensajes.getMessage("action.reportevistas.nopaginas"), font);
			texto.setAlignment(0);
			documento.add(texto);
		}

		documento.newPage();
		
		strategosVistasService.close();
	}
}