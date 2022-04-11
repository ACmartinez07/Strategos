/**
 * 
 */
package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

import java.net.URL;
import java.util.List;
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
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.reportes.model.Reporte.ReporteCorte;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.vistasdatos.model.util.DatoCelda;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoDimension;
import com.visiongc.app.strategos.web.struts.reportes.actions.MostrarReporteAction;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.ConfigurarVistaDatosForm;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.ConfigurarVistaDatosForm.SourceType;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.web.util.WebUtil;

/**
 * @author Kerwin
 *
 */
public class ImprimirVistaDatosPDFAction extends VgcReporteBasicoAction
{
	private String titulo = null;
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		titulo = mensajes.getMessage("jsp.mostrarvistadatos.titulo") + " / " + request.getSession().getAttribute("organizacionNombre");
		return titulo;
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
	    documento.setPageSize(PageSize.LETTER.rotate());
	    
	    ConfigurarVistaDatosForm configurarVistaDatosForm = (ConfigurarVistaDatosForm)form;
		Long reporteId = ((request.getParameter("reporteId") != null && request.getParameter("reporteId") != "") ? new Long(request.getParameter("reporteId")) : null);
		Byte source = ((request.getParameter("source") != null && request.getParameter("source") != "") ? new Byte(request.getParameter("source")) : null);
		Byte corte = ((request.getParameter("corte") != null && request.getParameter("corte") != "") ? new Byte(request.getParameter("corte")) : null);
		
		if (corte != null && ReporteCorte.getCorteLongitudinal().byteValue() == corte.byteValue())
			getCorteLongitudinal(source, reporteId, configurarVistaDatosForm, request, documento);
		else
			getCorteTransversal(reporteId, request, documento);
	}
	
	private void getCorteTransversal(Long reporteId, HttpServletRequest request, Document documento) throws Exception
	{
	    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		
		if (reporteId != null)
		{
			StrategosReportesService reportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();
			Reporte reporte = null;
    		if (reporteId != null)
    			reporte = (Reporte) reportesService.load(Reporte.class, reporteId);
    		reportesService.close();
    		if (reporte != null)
    		{
    			ReporteForm reporteForm = new ReporteForm();
    			reporteForm.clear();
    			
    			new MostrarReporteAction().getReporte(reporteForm, reporte, reporteId, request);
			    
				String subTitulo = reporteForm.getReporteNombre();
				
				documento.add(new Paragraph(" "));
			    agregarSubTitulo(documento, getConfiguracionPagina(request), subTitulo, true, true, 13.0F);
				documento.add(new Paragraph(" "));
				
				if (reporteForm.getPeriodoInicial() != null && reporteForm.getPeriodoFinal() != null && reporteForm.getAnoInicial() != null && reporteForm.getAnoFinal() != null)
				{
					String periodo = "Desde " + PeriodoUtil.convertirPeriodoToTexto(reporteForm.getPeriodoInicial(), reporteForm.getFrecuencia(), new Integer(reporteForm.getAnoInicial())) + " Hasta " + PeriodoUtil.convertirPeriodoToTexto(reporteForm.getPeriodoFinal(), reporteForm.getFrecuencia(), new Integer(reporteForm.getAnoFinal()));    
				    agregarSubTitulo(documento, getConfiguracionPagina(request), periodo, true, true, 13.0F);
					documento.add(new Paragraph(" "));
				}
			    
				TablaBasicaPDF tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
				if (reporteForm.getMatrizDatos().size() > 0)
				{
				    int[] columnas = new int[((List<DatoCelda>)reporteForm.getMatrizDatos().get(0)).size()];
				    columnas[0] = 25;
				    for (int f = 1; f < columnas.length; f++)
				    	columnas[f] = 10;

				    tabla.setAnchoBorde(0);
				    tabla.setAmplitudTabla(100);
				    tabla.crearTabla(columnas);
				    tabla.setFormatoFont(font.style());
				    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
				    tabla.setTamanoFont(10);
				    tabla.setCellpadding(0);
				    tabla.setColorLetra(255, 255, 255);
				    tabla.setColorFondo(67, 67, 67);
				    tabla.setColorBorde(120, 114, 77);
				    tabla.setColspan(1);

				    String[] columnasTitulo = new String[columnas.length];  
					List<DatoCelda> filaDatos = (List<DatoCelda>)reporteForm.getMatrizDatos().get(0);
					for (int k = 0; k < filaDatos.size(); k++)
					{
						DatoCelda datoCelda = (DatoCelda)filaDatos.get(k);
						columnasTitulo[k] = datoCelda.getValor();
					}
				    
					tabla = saltoPaginaTabla(font, false, documento, tabla, columnas, columnasTitulo, "", "", request);
					
					int numeroLineas = 0;
					int tamanoLineas = 14;
					tabla.setTamanoFont(8);
					for (int f = 1; f < reporteForm.getMatrizDatos().size(); f++)
					{
		    			if (numeroLineas >= tamanoLineas || f == 0)
		    			{
		    				numeroLineas = 0;
		    				tabla = saltoPaginaTabla(font, true, documento, tabla, columnas, columnasTitulo, titulo, subTitulo, request);
		    			}
						
						filaDatos = (List<DatoCelda>)reporteForm.getMatrizDatos().get(f);
						for (int k = 0; k < filaDatos.size(); k++)
						{
							DatoCelda datoCelda = (DatoCelda)filaDatos.get(k);
							if (datoCelda.getValor() != null) 
								tabla.agregarCelda(datoCelda.getValor());
						}
						
						numeroLineas++;
					}
			    	
					documento.add(tabla.getTabla());
				}
    		}
		}
	}
	
	private void getCorteLongitudinal(Byte source, Long reporteId, ConfigurarVistaDatosForm configurarVistaDatosForm, HttpServletRequest request, Document documento) throws Exception
	{
	    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
	    
		if (source == null)
		{
			if (reporteId == null)
				configurarVistaDatosForm.setSource(SourceType.getSourceEditar());
			else
			{
				if (configurarVistaDatosForm == null)
					configurarVistaDatosForm = new ConfigurarVistaDatosForm();
				configurarVistaDatosForm.clear(); 
				configurarVistaDatosForm.setSource(SourceType.getSourceGestionar());
				new ConfigurarVistaDatosAction().cargarConfiguracion(configurarVistaDatosForm, request);
			}
		}
		else
		{
			if (configurarVistaDatosForm == null)
			{
				configurarVistaDatosForm = new ConfigurarVistaDatosForm();
				configurarVistaDatosForm.clear(); 
				configurarVistaDatosForm.setSource(SourceType.getSourceGestionar());
				new ConfigurarVistaDatosAction().cargarConfiguracion(configurarVistaDatosForm, request);
			}
		}

		String subTitulo = configurarVistaDatosForm.getNombre();
		
  		String valorSelectorOrganizacion = ((request.getParameter("organizacionId") != null && request.getParameter("organizacionId") != "") ? request.getParameter("organizacionId") : null);
  		if (valorSelectorOrganizacion != null && !valorSelectorOrganizacion.equals(""))
  		{
  			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
  			OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosIndicadoresService.load(OrganizacionStrategos.class, new Long(valorSelectorOrganizacion));
  			strategosIndicadoresService.close();
  			
  			valorSelectorOrganizacion = null;
  			if (organizacion != null)
  			{
  				valorSelectorOrganizacion = organizacion.getNombre();
  	  			configurarVistaDatosForm.setNombreSelector1(organizacion.getNombre());
  	  			configurarVistaDatosForm.setSelector1Id(new Long(TipoDimension.getTipoDimensionOrganizacion()));
  	  			configurarVistaDatosForm.setValorSelector1(organizacion.getOrganizacionId().toString());
  			}
  			
  		}
  		
  		String valorSelectorTiempo = ((request.getParameter("tiempo") != null && request.getParameter("tiempo") != "") ? request.getParameter("tiempo") : null);
  		if (valorSelectorTiempo != null && !valorSelectorTiempo.equals(""))
  		{
  			configurarVistaDatosForm.setNombreSelector1(valorSelectorTiempo);
  			configurarVistaDatosForm.setSelector1Id(new Long(TipoDimension.getTipoDimensionTiempo()));
  			configurarVistaDatosForm.setValorSelector1(valorSelectorTiempo);
  		}
  		
		new MostrarVistaDatosAction().setConfigurarVistaDatosForm(configurarVistaDatosForm, getResources(request));
	    
		documento.add(new Paragraph(" "));
	    agregarSubTitulo(documento, getConfiguracionPagina(request), subTitulo, true, true, 13.0F);
		documento.add(new Paragraph(" "));
		
		if (configurarVistaDatosForm.getPeriodoInicial() != null && configurarVistaDatosForm.getPeriodoFinal() != null && configurarVistaDatosForm.getAnoInicial() != null && configurarVistaDatosForm.getAnoFinal() != null)
		{
			String periodo = "Desde " + PeriodoUtil.convertirPeriodoToTexto(configurarVistaDatosForm.getPeriodoInicial(), configurarVistaDatosForm.getFrecuencia(), configurarVistaDatosForm.getAnoInicial()) + " Hasta " + PeriodoUtil.convertirPeriodoToTexto(configurarVistaDatosForm.getPeriodoFinal(), configurarVistaDatosForm.getFrecuencia(), configurarVistaDatosForm.getAnoFinal());    
		    agregarSubTitulo(documento, getConfiguracionPagina(request), periodo, true, true, 13.0F);
			documento.add(new Paragraph(" "));
		}
		
  		if (valorSelectorOrganizacion != null && !valorSelectorOrganizacion.equals(""))
  		{
			String organizacionSeleccionada = "Organizacion : " + valorSelectorOrganizacion;    
		    agregarSubTitulo(documento, getConfiguracionPagina(request), organizacionSeleccionada, true, true, 13.0F);
			documento.add(new Paragraph(" "));
  		}
	    
		TablaBasicaPDF tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
		if (configurarVistaDatosForm.getMatrizDatos().size() > 0)
		{
		    int[] columnas = new int[((List<DatoCelda>)configurarVistaDatosForm.getMatrizDatos().get(0)).size()];
		    columnas[0] = 25;
		    for (int f = 1; f < columnas.length; f++)
		    	columnas[f] = 10;

		    tabla.setAnchoBorde(0);
		    tabla.setAmplitudTabla(100);
		    tabla.crearTabla(columnas);
		    tabla.setFormatoFont(font.style());
		    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
		    tabla.setAlineacionVertical(TablaBasicaPDF.V_ALINEACION_MIDDLE);
		    tabla.setTamanoFont(10);
		    tabla.setCellpadding(0);
		    tabla.setColorLetra(255, 255, 255);
		    tabla.setColorFondo(67, 67, 67);
		    tabla.setColorBorde(120, 114, 77);
		    tabla.setColspan(1);

		    String[] columnasTitulo = new String[columnas.length];  
			List<DatoCelda> filaDatos = (List<DatoCelda>)configurarVistaDatosForm.getMatrizDatos().get(0);
			for (int k = 0; k < filaDatos.size(); k++)
			{
				DatoCelda datoCelda = (DatoCelda)filaDatos.get(k);
				columnasTitulo[k] = datoCelda.getValor();
			}
		    
			tabla = saltoPaginaTabla(font, false, documento, tabla, columnas, columnasTitulo, "", "", request);
			
			int numeroLineas = 0;
			int tamanoLineas = 14;
			tabla.setTamanoFont(8);
			Image imagenAlerta = null;
			for (int f = 1; f < configurarVistaDatosForm.getMatrizDatos().size(); f++)
			{
    			if (numeroLineas >= tamanoLineas || f == 0)
    			{
    				numeroLineas = 0;
    				tabla = saltoPaginaTabla(font, true, documento, tabla, columnas, columnasTitulo, titulo, subTitulo, request);
    			}
				
				filaDatos = (List<DatoCelda>)configurarVistaDatosForm.getMatrizDatos().get(f);
				for (int k = 0; k < filaDatos.size(); k++)
				{
					DatoCelda datoCelda = (DatoCelda)filaDatos.get(k);
					if (!datoCelda.getEsAlerta())
						tabla.agregarCelda(datoCelda.getValor());
					else if (datoCelda.getValor() != null) 
					{
				    	switch (new Integer(datoCelda.getValor())) 
				        { 
					        case 0:
						        imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaRoja5X5.gif"))); 
						        break;
					        case 2:
						    	imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaVerde5X5.gif"))); 
						    	break;
					        case 3:
					        	imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaAmarilla5X5.gif"))); 
					        	break;
					        case 1:
						        imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaBlanca5X5.gif")));
						        break;
				        }
				    	tabla.agregarCelda(imagenAlerta);
					}
					else
						tabla.agregarCelda(datoCelda.getValor());
				}
				
				numeroLineas++;
			}
	    	
			documento.add(tabla.getTabla());
		}
	}
}
