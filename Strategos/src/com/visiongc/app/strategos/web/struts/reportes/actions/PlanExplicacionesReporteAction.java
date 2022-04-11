/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.awt.Color;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion.ObjetivoKey;
import com.visiongc.app.strategos.explicaciones.model.util.TipoMemoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.util.WebUtil;

/**
 * @author Kerwin
 *
 */
public class PlanExplicacionesReporteAction  extends VgcReporteBasicoAction
{
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;
	
	protected String agregarTitulo(HttpServletRequest request,	MessageResources mensajes) throws Exception 
	{
		return mensajes.getMessage("jsp.reportes.plan.explicacion.reporte.titulo");
	}
	
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception 
	{	
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		
		reporte.setObjetoStatus(request.getParameter("objetoKey"));
		reporte.setPlanId(request.getParameter("planId") != null ? Long.parseLong(request.getParameter("planId")) : null);
		reporte.setFechaDesde(request.getParameter("fechaDesde"));
		reporte.setFechaHasta(request.getParameter("fechaHasta"));
		String orientacion = request.getParameter("orientacion");
		if (orientacion != null && orientacion.equals("H"))
			inicioTamanoPagina = 41;
		
		Reporte(reporte, documento, request, mensajes);
	}
	
	private void Reporte(ReporteForm reporte, Document documento, HttpServletRequest request, MessageResources mensajes) throws Exception
	{
	    StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
	    
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan(); 
		strategosPlanesService.close();
	    
	    Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, reporte.getPlanId());
	    reporte.setPlantillaPlanes((PlantillaPlanes)strategosPerspectivasService.load(PlantillaPlanes.class, new Long(plan.getMetodologiaId())));
	    reporte.setAnoInicial(plan.getAnoInicial().toString());
		reporte.setAnoFinal(plan.getAnoFinal().toString());
		reporte.setMesInicial("1");
		reporte.setMesFinal("12");

	    //Raiz del plan
	    lineas = 2;
	    tamanoPagina = inicioTamanoPagina;
	    Perspectiva perspectiva = null;
	    //if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivo().byteValue())
	    	//perspectiva = (Perspectiva) strategosPerspectivasService.load(Perspectiva.class, reporte.getObjetoSeleccionadoId());
	    //else
    	perspectiva = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());
		perspectiva.setConfiguracionPlan(configuracionPlan);
	    
		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
		
	    //Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(8);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(8);
		fontBold.setStyle(Font.BOLD);

	    Paragraph texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.meta.organizacion") + " : " + ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre(), font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea(lineas, inicioLineas);

		String objeto = mensajes.getMessage("jsp.reportes.plan.explicacion.reporte.tipo.todos"); 
		if (reporte.getObjetoStatus() != null && reporte.getObjetoStatus().equals("0"))
			objeto = reporte.getPlantillaPlanes().getNombreIndicadorSingular();
		else if (reporte.getObjetoStatus() != null && reporte.getObjetoStatus().equals("1"))
			objeto = reporte.getPlantillaPlanes().getNombreIniciativaSingular();
	    texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.explicacion.reporte.tipo.objeto") + " : " + objeto, font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea(lineas, inicioLineas);
		
		texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.explicacion.reporte.fecha.desde") + " : " + (reporte.getFechaDesde() != null ? reporte.getFechaDesde() : "todos") + " a " + mensajes.getMessage("jsp.reportes.plan.explicacion.reporte.fecha.hasta") + " : " + (reporte.getFechaHasta() != null ? reporte.getFechaHasta() : "todos") , font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea(lineas, inicioLineas);
		
		Integer nivel = 0;
		if (perspectiva.getPadreId() == null || perspectiva.getPadreId() == 0L)
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.meta.plan") + " : " + perspectiva.getNombreCompleto(), fontBold);
		else
		{
			Perspectiva perspectivaRaiz = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());
			nivel = new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().buscarNivelPerspectiva(0, perspectivaRaiz.getPerspectivaId(), perspectiva.getPerspectivaId(), strategosPerspectivasService);
			texto = new Paragraph(new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, (nivel != null ? nivel : 1)) + " : " + perspectiva.getNombreCompleto(), fontBold);
		}
	    texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea(lineas, inicioLineas);
		documento.add(lineaEnBlanco(font));
		lineas = getNumeroLinea(lineas, inicioLineas);

	    Map<String, Object> filtros = new HashMap<String, Object>();

	    filtros.put("padreId", perspectiva.getPerspectivaId());
	    String[] orden = new String[1];
	    String[] tipoOrden = new String[1];
	    orden[0] = "nombre";
	    tipoOrden[0] = "asc";
		List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);
		if (perspectivas.size() > 0)
		{
			if (perspectiva.getPadreId() == null || perspectiva.getPadreId() == 0L)
				nivel = 0;
			else
				nivel++;
			for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext();) 
			{
				Perspectiva perspectivaHija = (Perspectiva)iter.next();
				perspectiva.setConfiguracionPlan(configuracionPlan);
				
		        // nombre de la perspectiva primer nivel 
				if (lineas >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}
				
				texto = new Paragraph(new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, nivel) + " : " + perspectivaHija.getNombreCompleto(), font);
				texto.setAlignment(Element.ALIGN_LEFT);
				documento.add(texto);
				lineas = getNumeroLinea(lineas, inicioLineas);

				buildReporte(nivel, reporte, font, perspectivaHija, configuracionPlan, documento, strategosIndicadoresService, strategosIniciativasService, strategosPerspectivasService, mensajes, request);
			}
		}
		else
			dibujarInformacionPerspectiva(nivel, reporte, font, perspectiva, documento, strategosIndicadoresService, strategosIniciativasService, mensajes, request);
		
		strategosIndicadoresService.close();
		strategosIniciativasService.close();
	    strategosPerspectivasService.close();
	}

	private void buildReporte(int nivel, ReporteForm reporte, Font font, Perspectiva perspectiva, ConfiguracionPlan configuracionPlan, Document documento, StrategosIndicadoresService strategosIndicadoresService, StrategosIniciativasService strategosIniciativasService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
	    //Lista de perspectivas del primer nivel
		Paragraph texto;
	    Map<String, Object> filtros = new HashMap<String, Object>();

	    filtros.put("padreId", perspectiva.getPerspectivaId());
	    String[] orden = new String[1];
	    String[] tipoOrden = new String[1];
	    orden[0] = "nombre";
	    tipoOrden[0] = "asc";
		List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);
		
		if (perspectivas.size() > 0)
		{
			for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext();) 
			{
				Perspectiva perspectivaHija = (Perspectiva)iter.next();
				perspectiva.setConfiguracionPlan(configuracionPlan);
				
		        // nombre de la perspectiva primer nivel 
				if (lineas >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}

				texto = new Paragraph(new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, (nivel + 1)) + " : " + perspectivaHija.getNombreCompleto(), font);
				texto.setAlignment(Element.ALIGN_LEFT);
				documento.add(texto);
				lineas = getNumeroLinea(lineas, inicioLineas);
				
				buildReporte((nivel + 1), reporte, font, perspectivaHija, configuracionPlan, documento, strategosIndicadoresService, strategosIniciativasService, strategosPerspectivasService, mensajes, request);
			}
		}
		else
			dibujarInformacionPerspectiva(nivel, reporte, font, perspectiva, documento, strategosIndicadoresService, strategosIniciativasService, mensajes, request);
	}
	
	private void dibujarInformacionPerspectiva(int nivel , ReporteForm reporte, Font font, Perspectiva perspectiva, Document documento, StrategosIndicadoresService strategosIndicadoresService, StrategosIniciativasService strategosIniciativasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		Paragraph texto;
		if (reporte.getObjetoStatus().equals("-1") || reporte.getObjetoStatus().equals(ObjetivoKey.getKeyIndicador().toString()))
		{
			texto = new Paragraph(reporte.getPlantillaPlanes().getNombreIndicadorPlural(), font);
			texto.setAlignment(Element.ALIGN_LEFT);
			documento.add(texto);
			lineas = getNumeroLinea(lineas, inicioLineas);

	        // nombre de la perspectiva primer nivel 
			if (lineas >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}
			dibujarInformacionIndicador(nivel, reporte, font, perspectiva, documento, strategosIndicadoresService, mensajes, request);
		}
		if (lineas >= tamanoPagina)
		{
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}
		documento.add(lineaEnBlanco(font));
		lineas = getNumeroLinea(lineas, inicioLineas);

		if (reporte.getObjetoStatus().equals("-1") || reporte.getObjetoStatus().equals(ObjetivoKey.getKeyIniciativa().toString()))
		{
			texto = new Paragraph(reporte.getPlantillaPlanes().getNombreIniciativaPlural(), font);
			texto.setAlignment(Element.ALIGN_LEFT);
			documento.add(texto);
			lineas = getNumeroLinea(lineas, inicioLineas);

	        // nombre de la perspectiva primer nivel 
			if (lineas >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}
			dibujarInformacionIniciativa(nivel, reporte, font, perspectiva, documento, strategosIniciativasService, mensajes, request);
		}
		if (lineas >= tamanoPagina)
		{
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}

		documento.add(lineaEnBlanco(font));
		lineas = getNumeroLinea(lineas, inicioLineas);
	}
	
	private void dibujarInformacionIndicador(int nivel, ReporteForm reporte, Font font, Perspectiva perspectiva, Document documento, StrategosIndicadoresService strategosIndicadoresService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		TablaBasicaPDF tabla;
		StringBuilder string;
		boolean tablaIniciada = false;
		
		filtros = new HashMap<String, Object>();
		filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
		List<Indicador> indicadores = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, true).getLista();
		
		if (indicadores.size() > 0)
		{
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

			tabla = crearTabla(ObjetivoKey.getKeyIndicador(), reporte, font, mensajes, documento, request);
			
			for (Iterator<Indicador> iter = indicadores.iterator(); iter.hasNext();)
			{
				tablaIniciada = false;
				Indicador indicador = (Indicador)iter.next();
				
    		    // Alerta
				Byte alerta = strategosPlanesService.getAlertaIndicadorPorPlan(indicador.getIndicadorId(), reporte.getPlanId());
				texto = new Paragraph("", font);
				
				String url=obtenerCadenaRecurso(request);
				
				if (alerta == null) 
	    		{
	    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif"));
	    			texto.add(new Chunk(image, 0, 0));
	    		}
	    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
	    		{
	    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif"));
	    			texto.add(new Chunk(image, 0, 0));
	    		}
	    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
	    		{
	    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif"));
	    			texto.add(new Chunk(image, 0, 0));
	    		}
	    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
	    		{
	    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif"));
	    			texto.add(new Chunk(image, 0, 0));
	    		}
				tabla.agregarCelda(texto);
				
				// Nombre
				string = new StringBuilder();
				string.append(indicador.getNombre());
				string.append("\n");
				string.append("\n");
				tabla.agregarCelda(string.toString());

    		    // Responsable
				if (indicador.getResponsableSeguimientoId() != null)
				{
					Responsable responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(indicador.getResponsableSeguimientoId()));
					if (responsable != null)
						tabla.agregarCelda(responsable.getNombre());
					else
						tabla.agregarCelda("");
				}
				else
					tabla.agregarCelda("");
    		    
    		    // Explicacion
				Explicacion explicacion = BuscarExplicacion(reporte, indicador.getIndicadorId(), ObjetivoKey.getKeyIndicador(), strategosExplicacionesService);
				if (explicacion != null && explicacion.getMemosExplicacion() != null && explicacion.getMemosExplicacion().size() > 0)
				{
					String descripcion = null;
					for (Iterator<?> i = explicacion.getMemosExplicacion().iterator(); i.hasNext(); ) 
					{
						MemoExplicacion memoExplicacion = (MemoExplicacion)i.next();
						Byte tipoMemo = memoExplicacion.getPk().getTipo();
						if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_DESCRIPCION)))
						{
							descripcion = memoExplicacion.getMemo();
							break;
						}
					}
					if (descripcion != null && !descripcion.equals(""))
					{
						string = new StringBuilder();
						string.append(descripcion);
						string.append("\n");
						string.append("\n");
						tabla.agregarCelda(string.toString(), TablaBasicaPDF.H_ALINEACION_JUSTIFIED);
					}
					else
						tabla.agregarCelda("");
				}
				else
					tabla.agregarCelda("");
				
				lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
				{
					lineas = inicioLineas;
					documento.add(tabla.getTabla());
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
					tabla = crearTabla(ObjetivoKey.getKeyIndicador(), reporte, font, mensajes, documento, request);
					tablaIniciada = true;
				}

				if (!tablaIniciada)
				{
					lineas = getNumeroLinea(lineas, inicioLineas);
					if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
					{
						lineas = inicioLineas;
						documento.add(tabla.getTabla());
						tamanoPagina = inicioTamanoPagina;
						saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
						tabla = crearTabla(ObjetivoKey.getKeyIndicador(), reporte, font, mensajes, documento, request);
						tablaIniciada = true;
					}
				}
			}
			
			if (!tablaIniciada)
				documento.add(tabla.getTabla());
		}
		else
		{
		    font.setColor(0, 0, 255);
		    if (lineas >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.explicacion.reporte.noindicadores", new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, (nivel)).toLowerCase()), font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			lineas = getNumeroLinea(lineas, inicioLineas);

			font.setColor(0, 0, 0);
		}
		
		strategosPlanesService.close();
		strategosResponsablesService.close();
		strategosExplicacionesService.close();
	}
	
	private Explicacion BuscarExplicacion(ReporteForm reporte, Long objetoId, Byte objetoKey, StrategosExplicacionesService strategosExplicacionesService)
	{
		Map<String, String> filtros = new HashMap<String, String>();

		filtros.put("tipo", "0");
		filtros.put("objetoId", objetoId.toString());
		filtros.put("objetoKey", objetoKey.toString());
		if ((reporte.getFechaDesde() != null) && (!reporte.getFechaDesde().equals("")))
			filtros.put("fechaDesde", reporte.getFechaDesde());
		if ((reporte.getFechaHasta() != null) && (!reporte.getFechaHasta().equals("")))
			filtros.put("fechaHasta", reporte.getFechaHasta());

		PaginaLista paginaExplicaciones = strategosExplicacionesService.getExplicaciones(1, 100, null, null, true, filtros);
		Explicacion explicacion = null;
		if (paginaExplicaciones.getLista().size() > 0)
		{
			explicacion = (Explicacion)paginaExplicaciones.getLista().get(0);
			explicacion = (Explicacion)strategosExplicacionesService.load(Explicacion.class, explicacion.getExplicacionId());
		}
		
		return explicacion;
	}
	
	private TablaBasicaPDF crearTabla(Byte tipoObjeto, ReporteForm reporte, Font font, MessageResources mensajes, Document documento, HttpServletRequest request) throws Exception
	{
		if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
		{
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}
	    
		boolean tablaIniciada = false;
	    String[][] columnas = new String[4][2];
	    StringBuilder string;
	    columnas[0][0] = "15";
	    columnas[0][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.alerta");
	    
	    columnas[1][0] = "100";
	    if (tipoObjeto.byteValue() == ObjetivoKey.getKeyIndicador().byteValue())
	    	columnas[1][1] = reporte.getPlantillaPlanes().getNombreIndicadorSingular();
	    else
	    	columnas[1][1] = reporte.getPlantillaPlanes().getNombreIniciativaSingular();

	    columnas[2][0] = "70";
	    columnas[2][1] = mensajes.getMessage("jsp.reportes.plan.explicacion.reporte.columna.responsable.seguimiento");

	    columnas[3][0] = "130";
	    string = new StringBuilder();
		string.append(mensajes.getMessage("jsp.reportes.plan.explicacion.reporte.columna.explicacion"));
		string.append("\n");
		string.append("\n");
	    columnas[3][1] = string.toString();
	    
		TablaBasicaPDF tabla = inicializarTabla(font, columnas, null, null, true, new Color(255, 255, 255), new Color(128, 128, 128), request);

		lineas = getNumeroLinea(lineas, inicioLineas);
		if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
		{
			lineas = inicioLineas;
			documento.add(tabla.getTabla());
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
			tabla = crearTabla(tipoObjeto, reporte, font, mensajes, documento, request);
			tablaIniciada = true;
		}

		if (!tablaIniciada)
		{
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(tipoObjeto, reporte, font, mensajes, documento, request);
			}
		}
		
	    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
	    tabla.setAlineacionVertical(TablaBasicaPDF.V_ALINEACION_TOP);
	    tabla.setFont(Font.NORMAL);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.setColorLetra(0, 0, 0);
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setTamanoFont(7);
	    
		return tabla;
	}
	
	private void dibujarInformacionIniciativa(int nivel, ReporteForm reporte, Font font, Perspectiva perspectiva, Document documento, StrategosIniciativasService strategosIniciativasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();

		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		TablaBasicaPDF tabla;
		StringBuilder string;
		boolean tablaIniciada = false;
		
		filtros = new HashMap<String, Object>();
		filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
		filtros.put("historicoDate", "IS NULL");
		
		List<Iniciativa> inicitivas = strategosIniciativasService.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

		if (inicitivas.size() > 0)
		{
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

			tabla = crearTabla(ObjetivoKey.getKeyIniciativa(), reporte, font, mensajes, documento, request);
			
			for (Iterator<Iniciativa> iter = inicitivas.iterator(); iter.hasNext();)
			{
				Iniciativa iniciativa = (Iniciativa)iter.next();
				tablaIniciada = false;
				
    		    // Alerta
				Byte alerta = iniciativa.getAlerta();
				texto = new Paragraph("", font);	
				
				String url=obtenerCadenaRecurso(request);
				
				if (alerta == null) 
	    		{
	    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif"));
	    			texto.add(new Chunk(image, 0, 0));
	    		}
	    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
	    		{
	    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif"));
	    			texto.add(new Chunk(image, 0, 0));
	    		}
	    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
	    		{
	    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif"));
	    			texto.add(new Chunk(image, 0, 0));
	    		}
	    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
	    		{
	    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif"));
	    			texto.add(new Chunk(image, 0, 0));
	    		}
				tabla.agregarCelda(texto);
				
				// Nombre
				string = new StringBuilder();
				string.append(iniciativa.getNombre());
				string.append("\n");
				string.append("\n");
				tabla.agregarCelda(string.toString());

    		    // Responsable
				if (iniciativa.getResponsableSeguimientoId() != null)
				{
					Responsable responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(iniciativa.getResponsableSeguimientoId()));
					if (responsable != null)
						tabla.agregarCelda(responsable.getNombre());
					else
						tabla.agregarCelda("");
				}
				else
					tabla.agregarCelda("");
    		    
    		    // Explicacion
				Explicacion explicacion = BuscarExplicacion(reporte, iniciativa.getIniciativaId(), ObjetivoKey.getKeyIniciativa(), strategosExplicacionesService);
				if (explicacion != null && explicacion.getMemosExplicacion() != null && explicacion.getMemosExplicacion().size() > 0)
				{
					String descripcion = null;
					for (Iterator<?> i = explicacion.getMemosExplicacion().iterator(); i.hasNext(); ) 
					{
						MemoExplicacion memoExplicacion = (MemoExplicacion)i.next();
						Byte tipoMemo = memoExplicacion.getPk().getTipo();
						if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_DESCRIPCION)))
						{
							descripcion = memoExplicacion.getMemo();
							break;
						}
					}
					if (descripcion != null && !descripcion.equals(""))
					{
						string = new StringBuilder();
						string.append(descripcion);
						string.append("\n");
						string.append("\n");
						tabla.agregarCelda(string.toString(), TablaBasicaPDF.H_ALINEACION_JUSTIFIED);
					}
					else
						tabla.agregarCelda("");
				}
				else
					tabla.agregarCelda("");

				lineas = getNumeroLinea(lineas, inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
				{
					lineas = inicioLineas;
					documento.add(tabla.getTabla());
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
					tabla = crearTabla(ObjetivoKey.getKeyIniciativa(), reporte, font, mensajes, documento, request);
					tablaIniciada = true;
				}

				if (!tablaIniciada)
				{
					lineas = getNumeroLinea(lineas, inicioLineas);
					if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
					{
						lineas = inicioLineas;
						documento.add(tabla.getTabla());
						tamanoPagina = inicioTamanoPagina;
						saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
						tabla = crearTabla(ObjetivoKey.getKeyIniciativa(), reporte, font, mensajes, documento, request);
						tablaIniciada = true;
					}
				}
			}
			
			if (!tablaIniciada)
				documento.add(tabla.getTabla());
		}
		else
		{
		    font.setColor(0, 0, 255);
		    if (lineas >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.explicacion.reporte.noindicadores", new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, (nivel)).toLowerCase()), font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			lineas = getNumeroLinea(lineas, inicioLineas);

			font.setColor(0, 0, 0);
		}
		
		strategosResponsablesService.close();
		strategosExplicacionesService.close();
	}
	
	private String obtenerCadenaRecurso(HttpServletRequest request){
		String result= null;
		if(request.getServerPort()==80 && request.getScheme().equals("http")){
			
		    result = request.getServerName() + "/" + request.getContextPath();
		    result = "https" + "://" + result.replaceAll("//", "/");
			
		}else{
			result = request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath();
		    result = request.getScheme() + "://" + result.replaceAll("//", "/");
		   
		}
		return result;
		
	}
	
}