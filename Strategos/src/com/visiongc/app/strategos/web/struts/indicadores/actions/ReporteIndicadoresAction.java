package com.visiongc.app.strategos.web.struts.indicadores.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class ReporteIndicadoresAction extends VgcReporteBasicoAction
{
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("action.reporteindicadores.titulo");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
		String atributoOrden = request.getParameter("atributoOrden");
		String tipoOrden = request.getParameter("tipoOrden");
		String organizacionId = request.getParameter("organizacionId");
		String claseId = request.getParameter("claseId");

		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

		MessageResources mensajes = getResources(request);

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		Map filtros = new HashMap();

		if ((organizacionId != null) && (!organizacionId.equals(""))) 
		{
			filtros.put("organizacionId", organizacionId);

			OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosIndicadoresService.load(OrganizacionStrategos.class, new Long(organizacionId));

			font.setSize(10.0F);
			Paragraph texto = new Paragraph(mensajes.getMessage("action.reporteindicadores.subtitulo.organizacion") + ": " + organizacion.getNombre(), font);
			texto.setAlignment(0);
			texto.setSpacingBefore(1.0F);
			documento.add(texto);
		}

		if ((claseId != null) && (!claseId.equals(""))) 
		{
			filtros.put("claseId", claseId);

			ClaseIndicadores clase = (ClaseIndicadores)strategosIndicadoresService.load(ClaseIndicadores.class, new Long(claseId));

			font.setSize(10.0F);
			Paragraph texto = new Paragraph(mensajes.getMessage("action.reporteindicadores.subtitulo.clase") + ": " + clase.getNombre(), font);
			texto.setAlignment(0);
			documento.add(texto);
		}

		TablaBasicaPDF tabla = null;
		tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[3];
		columnas[0] = 80;
		columnas[1] = 20;
		columnas[2] = 20;
		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);

		List indicadores = strategosIndicadoresService.getIndicadores(0, 0, atributoOrden, tipoOrden, false, filtros, null, null, false).getLista();
		
		tabla.setFormatoFont(font.style());
		tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
		
		tabla.agregarCelda("Nombre");
		tabla.agregarCelda("Frecuencia");
		tabla.agregarCelda("Naturaleza");

		tabla.setDefaultAlineacionHorizontal();
		if (indicadores.size() > 0) 
		{
			for (Iterator iter = indicadores.iterator(); iter.hasNext(); ) 
			{
				Indicador indicador = (Indicador)iter.next();

				tabla.setDefaultAlineacionHorizontal();
				tabla.agregarCelda(indicador.getNombre());
				
				tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
				tabla.agregarCelda(indicador.getFrecuenciaNombre());
				
				tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
				tabla.agregarCelda(indicador.getNaturalezaNombre());
			}

			documento.add(tabla.getTabla());
			
			font.setSize(10.0F);
			Paragraph texto = new Paragraph("Número de Indicadores: " + Integer.toString(indicadores.size()), font);
			texto.setAlignment(0);
			documento.add(texto);
		}
		else
		{
			font.setSize(10.0F);
			Paragraph texto = new Paragraph("No hay indicadores con las características solicitadas", font);
			texto.setAlignment(0);
			texto.setSpacingBefore(72.0F);
			documento.add(texto);
		}

		documento.newPage();

		strategosIndicadoresService.close();
	}
}