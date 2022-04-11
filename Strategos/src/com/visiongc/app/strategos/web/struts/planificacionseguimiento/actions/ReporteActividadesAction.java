package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
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

public class ReporteActividadesAction extends VgcReporteBasicoAction
{
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("action.reporteactividades.titulo");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
		String atributoOrden = request.getParameter("atributoOrden");
		String tipoOrden = request.getParameter("tipoOrden");
		String proyectoId = request.getParameter("proyectoId");

		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

		MessageResources mensajes = getResources(request);

		StrategosPryActividadesService strategosActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

		TablaBasicaPDF tabla = null;
		tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[1];
		columnas[0] = 100;
		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);
		
		Map<String, String> filtros = new HashMap<String, String>();

		if ((proyectoId != null) && (!proyectoId.equals(""))) 
			filtros.put("proyectoId", proyectoId);

		List<?> actividades = strategosActividadesService.getActividades(0, 0, atributoOrden, tipoOrden, false, filtros).getLista();

		tabla.setFormatoFont(font.style());
		tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

		tabla.agregarCelda(mensajes.getMessage("action.reporteactividades.nombre"));

		tabla.setDefaultAlineacionHorizontal();
		if ((actividades != null) && (actividades.size() > 0)) 
		{
			for (Iterator<?> iter = actividades.iterator(); iter.hasNext(); ) 
			{
				PryActividad actividad = (PryActividad)iter.next();
				
				tabla.setDefaultAlineacionHorizontal();
				tabla.agregarCelda(actividad.getNombre());
			}

			documento.add(tabla.getTabla());
		}
		else
		{
			tabla.setColspan(1);
			font.setStyle(2);
			Paragraph texto = new Paragraph(mensajes.getMessage("action.reporteactividades.noactividades"), font);
			texto.setAlignment(0);
			documento.add(texto);
		}

		documento.newPage();

		strategosActividadesService.close();
	}
}