/**
 * 
 */
package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.GestionarExplicacionesForm;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;

/**
 * @author Kerwin
 *
 */
public class ReporteExplicacionesPdfAction  extends VgcReporteBasicoAction
{
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;

	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("jsp.reporte.explicacion.organizacion") + " : " + ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre();
	}

  	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
  	{
		documento.setPageSize(PageSize.LETTER.rotate().rotate());
		MessageResources mensajes = getResources(request);
		lineas = 2;
		tamanoPagina = inicioTamanoPagina;

		String objetoId = request.getParameter("objetoId");
		String objetoKey = request.getParameter("objetoKey");
		Integer tipo = request.getParameter("tipo") != null ? Integer.parseInt(request.getParameter("tipo")) : 1;
		String titulo = request.getParameter("titulo");

		GestionarExplicacionesForm gestionarExplicacionesForm = new GestionarExplicacionesForm();
		gestionarExplicacionesForm.setTipo(tipo);
		gestionarExplicacionesForm.setObjetoId(Long.parseLong(objetoId));
		gestionarExplicacionesForm.setTipoObjetoKey(objetoKey);
		gestionarExplicacionesForm.setFiltroTitulo(titulo);
		
		if (objetoKey.equals("Indicador"))
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(objetoId));
			gestionarExplicacionesForm.setNombreObjetoKey(indicador.getNombre());
			strategosIndicadoresService.close();
		} 
		else if (objetoKey.equals("Celda"))
		{
			StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
			Celda celda = (Celda)strategosCeldasService.load(Celda.class, new Long(objetoId));

			String nombreObjetoKey = "";
			if (celda.getIndicadoresCelda() != null) 
			{
				if ((celda.getIndicadoresCelda().size() == 0) || (celda.getIndicadoresCelda().size() > 1)) 
					nombreObjetoKey = celda.getTitulo();
				else if (celda.getIndicadoresCelda().size() == 1) 
				{
					IndicadorCelda indicadorCelda = (IndicadorCelda)celda.getIndicadoresCelda().toArray()[0];
					StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
					Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorCelda.getIndicador().getIndicadorId());
					nombreObjetoKey = indicador.getNombre();
					strategosIndicadoresService.close();
				}
			}
			else nombreObjetoKey = celda.getTitulo();
			gestionarExplicacionesForm.setNombreObjetoKey(nombreObjetoKey);
			
			strategosCeldasService.close();
		} 
		else  if (objetoKey.equals("Iniciativa"))
		{
			StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(objetoId));
			
			gestionarExplicacionesForm.setNombreObjetoKey(iniciativa.getNombre());
			
			strategosIniciativasService.close();
		} 
		else if (objetoKey.equals("Organizacion"))
		{
			OrganizacionStrategos organizacion = ((OrganizacionStrategos)request.getSession().getAttribute("organizacion"));
			
			gestionarExplicacionesForm.setNombreObjetoKey(organizacion.getNombre());
		}
		
		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
		
	    //Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(8);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(8);
		fontBold.setStyle(Font.BOLD);

		documento.add(lineaEnBlanco(font));
		lineas = getNumeroLinea(lineas, inicioLineas);
		String subTitulo = mensajes.getMessage("jsp.reporte.explicacion.objeto") + " : " + gestionarExplicacionesForm.getNombreObjetoKey();
		agregarSubTitulo(documento, getConfiguracionPagina(request), subTitulo, true, true, 13.0F);		
		lineas = getNumeroLinea(lineas, inicioLineas);
		
		documento.add(lineaEnBlanco(font));
		lineas = getNumeroLinea(lineas, inicioLineas);
		
		dibujarInformacion(font, documento, mensajes, gestionarExplicacionesForm, request);
  	}
  	
	private void dibujarInformacion(Font font, Document documento, MessageResources mensajes, GestionarExplicacionesForm gestionarExplicacionesForm, HttpServletRequest request) throws Exception
	{
		String atributoOrden = "fecha";
		String tipoOrden = "DESC";
		int pagina = 1;

		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();

		Map<String, Comparable> filtros = new HashMap<String, Comparable>();

		if (gestionarExplicacionesForm.getTipo() != null) 
			filtros.put("tipo", gestionarExplicacionesForm.getTipo());
		if ((gestionarExplicacionesForm.getFiltroTitulo() != null) && (!gestionarExplicacionesForm.getFiltroTitulo().equals(""))) 
			filtros.put("titulo", gestionarExplicacionesForm.getFiltroTitulo());
		if ((gestionarExplicacionesForm.getFiltroAutor() != null) && (!gestionarExplicacionesForm.getFiltroAutor().equals(""))) 
			filtros.put("autor", gestionarExplicacionesForm.getFiltroAutor());
		if ((gestionarExplicacionesForm.getObjetoId() != null) && (!gestionarExplicacionesForm.getObjetoId().equals("")) && (gestionarExplicacionesForm.getObjetoId().byteValue() != 0)) 
			filtros.put("objetoId", gestionarExplicacionesForm.getObjetoId().toString());
		if (gestionarExplicacionesForm.getTipoObjetoKey().equals("Indicador"))
			filtros.put("objetoKey", "0");
		if (gestionarExplicacionesForm.getTipoObjetoKey().equals("Celda"))
			filtros.put("objetoKey", "1");

		PaginaLista paginaExplicaciones = strategosExplicacionesService.getExplicaciones(pagina, 30, atributoOrden, tipoOrden, true, filtros);
		
		Paragraph texto;
		TablaBasicaPDF tabla;
		StringBuilder string;

		if (paginaExplicaciones.getLista().size() > 0)
		{
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

			tabla = crearTabla(font, mensajes, documento, request);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

		    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
		    tabla.setAlineacionVertical(TablaBasicaPDF.V_ALINEACION_TOP);
		    tabla.setFont(Font.NORMAL);
		    tabla.setFormatoFont(Font.NORMAL);
		    tabla.setColorLetra(0, 0, 0);
		    tabla.setColorFondo(255, 255, 255);
		    tabla.setTamanoFont(7);
			
			for (Iterator<Explicacion> iter = paginaExplicaciones.getLista().iterator(); iter.hasNext();)
			{
				Explicacion explicacion = (Explicacion)iter.next();
				
				// Autor
				string = new StringBuilder();
				string.append(explicacion.getUsuarioCreado().getFullName());
				string.append("\n");
				string.append("\n");
				tabla.agregarCelda(string.toString());

    		    // Fecha
				tabla.agregarCelda(explicacion.getFechaFormateada());
    		    
    		    // Titulo
				tabla.agregarCelda(explicacion.getTitulo());
				lineas = getNumeroLinea(lineas, inicioLineas);
				if (lineas >= tamanoPagina)
				{
					lineas = inicioLineas;
					documento.add(tabla.getTabla());
					tamanoPagina = inicioTamanoPagina;
					tabla = saltarPagina(documento, true, font, tabla.getTabla().columns(), null, request);
				}

				lineas = getNumeroLinea(lineas, inicioLineas);
				if (lineas >= tamanoPagina)
				{
					lineas = inicioLineas;
					documento.add(tabla.getTabla());
					tamanoPagina = inicioTamanoPagina;
					tabla = saltarPagina(documento, true, font, tabla.getTabla().columns(), null, request);
				}
			}
			
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
			texto = new Paragraph(mensajes.getMessage("jsp.reporte.explicacion.noexplicaciones", gestionarExplicacionesForm.getNombreObjetoKey()), font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			lineas = getNumeroLinea(lineas, inicioLineas);

			font.setColor(0, 0, 0);
		}
	}
	
	private TablaBasicaPDF crearTabla(Font font, MessageResources mensajes, Document documento, HttpServletRequest request) throws Exception
	{
	    if (lineas >= tamanoPagina)
		{
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}
	    
	    String[][] columnas = new String[3][2];
	    StringBuilder string;
	    columnas[0][0] = "30";
	    columnas[0][1] = mensajes.getMessage("jsp.reporte.explicacion.autor");
	    
	    columnas[1][0] = "15";
	    columnas[1][1] = mensajes.getMessage("jsp.reporte.explicacion.fecha");

	    columnas[2][0] = "60";
	    string = new StringBuilder();
		string.append(mensajes.getMessage("jsp.reporte.explicacion.titulo"));
		string.append("\n");
		string.append("\n");
	    columnas[2][1] = string.toString();
	    
		TablaBasicaPDF tabla = inicializarTabla(font, columnas, null, null, true, new Color(255, 255, 255), new Color(128, 128, 128), request);

		lineas = getNumeroLinea(lineas, inicioLineas);
		if (lineas >= tamanoPagina)
		{
			lineas = inicioLineas;
			documento.add(tabla.getTabla());
			tamanoPagina = inicioTamanoPagina;
			tabla = saltarPagina(documento, true, font, tabla.getTabla().columns(), null, request);
		}
		lineas = getNumeroLinea(lineas, inicioLineas);
		if (lineas >= tamanoPagina)
		{
			lineas = inicioLineas;
			documento.add(tabla.getTabla());
			tamanoPagina = inicioTamanoPagina;
			tabla = saltarPagina(documento, true, font, tabla.getTabla().columns(), null, request);
		}
	    
		return tabla;
	}
}