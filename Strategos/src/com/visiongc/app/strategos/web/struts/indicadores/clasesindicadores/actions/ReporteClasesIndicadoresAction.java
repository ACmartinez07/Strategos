package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.actions;

import com.lowagie.text.Document;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.framework.model.Usuario;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class ReporteClasesIndicadoresAction extends VgcReporteBasicoAction
{
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		Long organizacionId = new Long(request.getParameter("organizacionId"));

		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();

		OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosClasesIndicadoresService.load(OrganizacionStrategos.class, organizacionId);

		String[] args = new String[1];
		args[0] = organizacion.getNombre();
    
		strategosClasesIndicadoresService.close();
		
		return mensajes.getMessage("action.reporteclasesindicadores.titulo", args);
	}

  	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
  	{
	  	MessageResources mensajes = getResources(request);

	  	Long organizacionId = new Long(request.getParameter("organizacionId"));
	  	Boolean visible = request.getSession().getAttribute("claseVisible") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("claseVisible")) : true;

	  	StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();

	  	TablaBasicaPDF tabla = null;
	  	tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
	  	int[] columnas = new int[1];
	  	columnas[0] = 100;
	  	tabla.setAmplitudTabla(100);
	  	tabla.crearTabla(columnas);

	  	ClaseIndicadores claseRoot = strategosClasesIndicadoresService.getClaseRaiz(organizacionId, TipoClaseIndicadores.getTipoClaseIndicadores(), (Usuario)request.getSession().getAttribute("usuario"));

	  	tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

	  	tabla.agregarCelda(mensajes.getMessage("action.reporteclasesindicadores.nombre"));

	  	documento.add(tabla.getTabla());

	  	tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
	  	tabla.setAmplitudTabla(100);
	  	tabla.setColorBorde(255, 255, 255);
	  	tabla.crearTabla(columnas);

	  	tabla.setDefaultAlineacionHorizontal();

    	tabla.agregarCelda(claseRoot.getNombre());

    	imprimirHijos(claseRoot, strategosClasesIndicadoresService, tabla, 1, visible);

    	documento.add(tabla.getTabla());

    	strategosClasesIndicadoresService.close();

    	documento.newPage();
  	}

  	private void imprimirHijos(ClaseIndicadores clase, StrategosClasesIndicadoresService strategosClasesIndicadoresService, TablaBasicaPDF tabla, int nivel, Boolean visible) throws Exception 
  	{
  		List clasesHijas = strategosClasesIndicadoresService.getClasesHijas(clase.getClaseId(), visible);
  		String identacion = "";
  		for (int i = 0; i < nivel; i++) 
  			identacion = identacion + "      ";
  		for (Iterator iter = clasesHijas.iterator(); iter.hasNext(); ) 
  		{
  			ClaseIndicadores hija = (ClaseIndicadores)iter.next();

  			tabla.setDefaultAlineacionHorizontal();
  			tabla.agregarCelda(identacion + hija.getNombre());
  			imprimirHijos(hija, strategosClasesIndicadoresService, tabla, nivel + 1, visible);
  		}
  	}
}