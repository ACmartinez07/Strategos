package com.visiongc.app.strategos.web.struts.problemas.clasesproblemas.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class ReporteClasesProblemasAction extends VgcReporteBasicoAction
{
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("action.reporteclasesproblemas.titulo");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
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

		ClaseProblemas claseProblemasRoot = new ClaseProblemas();
		String organizacionId = (String)request.getSession().getAttribute("organizacionId");
		Object[] arregloIdentificadores = new Object[4];
		arregloIdentificadores[0] = "organizacionId";
		arregloIdentificadores[1] = new Long(organizacionId);
		arregloIdentificadores[2] = "tipo";
		arregloIdentificadores[3] = new Integer(Integer.parseInt(request.getParameter("tipo")));
		
		claseProblemasRoot = (ClaseProblemas)nodosArbolService.getNodoArbolRaiz(claseProblemasRoot, arregloIdentificadores);

		List<?> clasesProblemas = nodosArbolService.crearReporteArbol(claseProblemasRoot, new Boolean(true));

		nodosArbolService.close();

		tabla.setFormatoFont(font.style());
		tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

		tabla.agregarCelda(mensajes.getMessage("action.reporteclasesproblemas.nombre"));

		documento.add(tabla.getTabla());

		tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
		tabla.setAmplitudTabla(100);
		tabla.setColorBorde(255, 255, 255);
		tabla.crearTabla(columnas);

		tabla.setDefaultAlineacionHorizontal();
		if ((clasesProblemas != null) && (clasesProblemas.size() > 0)) 
		{
			for (Iterator<?> iter = clasesProblemas.iterator(); iter.hasNext(); ) 
			{
				String nombreClase = (String)iter.next();

				tabla.setDefaultAlineacionHorizontal();
				tabla.agregarCelda(nombreClase);
			}

			documento.add(tabla.getTabla());
		}

		documento.newPage();
	}
}