/**
 * 
 */
package com.visiongc.app.strategos.web.struts.transacciones.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Font;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.web.struts.transacciones.forms.TransaccionForm;

import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;

import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Columna;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.framework.model.Columna.ColumnaTipo;
import com.visiongc.framework.transaccion.TransaccionService;

/**
 * @author Kerwin
 *
 */
public class ImprimirTransaccionAction extends VgcReporteBasicoAction
{
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioTamanoPagina = 40;
	private String tituloReporte = null;

	protected String agregarTitulo(HttpServletRequest request,	MessageResources mensajes) throws Exception 
	{
		tituloReporte = request.getParameter("nombre") != null ? request.getParameter("nombre") : null;
	    if (tituloReporte == null)
	    	tituloReporte = mensajes.getMessage("jsp.reporte.transaccion.imprimir.titulo");
		return tituloReporte;
	}
	
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception 
	{
		Long transaccionId = (request.getParameter("transaccionId") != null ? Long.parseLong(request.getParameter("transaccionId")) : 0L);
		String fechaInicial = (request.getParameter("fechaInicial") != null ? request.getParameter("fechaInicial") : null);
		String fechaFinal = (request.getParameter("fechaFinal") != null ? request.getParameter("fechaFinal") : null);
		Integer numeroRegistros = ((request.getParameter("numeroRegistros") != null && request.getParameter("numeroRegistros") != "") ? Integer.parseInt(request.getParameter("numeroRegistros")) : null);

		TransaccionService transaccionService = FrameworkServiceFactory.getInstance().openTransaccionService();
		Transaccion transaccion = (Transaccion) transaccionService.load(Transaccion.class, transaccionId);
		transaccionService.close();

		TransaccionForm transaccionForm = new TransaccionForm();
		transaccionForm.clear();
		transaccionForm.setTransaccionId(transaccionId);
		
		transaccionForm.setFechaInicial(fechaInicial);
		transaccionForm.setFechaFinal(fechaFinal);
		transaccionForm.setNumeroRegistros(numeroRegistros);
		if (transaccion != null)
		{
			if (transaccion.getTabla() != null)
				transaccionForm.setTransaccion(transaccion);
			
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			Indicador indicador;
			transaccionForm.setFrecuencia(transaccion.getFrecuencia());
			transaccionForm.setNombre(transaccion.getNombre());
			if (transaccion.getTabla() != null)
			{
				transaccionForm.setTransaccion(transaccion);
				for (Iterator<Columna> i = transaccion.getTabla().getColumnas().iterator(); i.hasNext(); )
				{
					Columna columna = (Columna)i.next();
					if (columna.getTipo().byteValue() == ColumnaTipo.getTipoDate().byteValue())
					{
						transaccionForm.setHayColumnaFecha(true);
						if (transaccion.getIndicadorId() != null)
						{
							indicador = (Indicador) strategosIndicadoresService.load(Indicador.class, transaccion.getIndicadorId());
							if (indicador != null)
							{
								transaccionForm.setIndicadorTransaccionesId(indicador.getIndicadorId());
								transaccionForm.setIndicadorTransaccionesNombre(indicador.getNombre());
							}
						}
					}
					
					if (columna.getTipo().byteValue() == ColumnaTipo.getTipoFloat().byteValue())
					{
						transaccionForm.setHayColumnaMonto(true);
						if (columna.getIndicadorId() != null)
						{
							indicador = (Indicador) strategosIndicadoresService.load(Indicador.class, columna.getIndicadorId());
							if (indicador != null)
							{
								transaccionForm.setIndicadorMontoId(indicador.getIndicadorId());
								transaccionForm.setIndicadorMontoNombre(indicador.getNombre());
							}
						}
					}
				}
			}
			strategosIndicadoresService.close();
		}
		
		List<Columna> columnas = new ArrayList<Columna>();
		String atributos = request.getParameter("xmlAtributos") != null && !request.getParameter("xmlAtributos").equals("") ? request.getParameter("xmlAtributos") : "";
		if (!atributos.equals(""))
		{
			String[] tipos = atributos.split("\\|");
			for (int i = 0; i < tipos.length; i++)
			{
				String[] campos = tipos[i].split(",");
				if (campos.length == 6)
				{
					for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
					{
						Columna columna = (Columna)col.next();
						if (columna.getNombre().equals(campos[1]))
						{
							columna.setOrden(campos[0]);
							columna.setVisible(campos[3].equals("1") ? true : false);
							columna.setAncho(campos[4]);
							columna.setAgrupar(campos[5].equals("1") ? true : false);
							
							columnas.add(columna);
						}
					}
				}
			}
			transaccionForm.getTransaccion().getTabla().setColumnas(columnas);
		}
		
		String tipoPresentacion = request.getParameter("tipoPresentacion");
		if (tipoPresentacion == null) 
			tipoPresentacion = "pdf";
		
		// Configurar font
		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
		font.setSize(8);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(8);
		fontBold.setStyle(Font.BOLD);

		List<List<Columna>> datos = new ReporteTransaccionAction().getDatos(false, request, transaccionForm, getMessages(request));
		int totalColumnas = 0;
		for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
		{
			Columna columna = (Columna)col.next();
			if (columna.getVisible())
				totalColumnas++;
		}

	    lineas = 2;
	    tamanoPagina = inicioTamanoPagina;
		TablaBasicaPDF tabla = inicializarTabla(font, totalColumnas, request);
		imprimirEncabezado(tabla, transaccionForm);
	    
	    for (Iterator<List<Columna>> iter = datos.iterator(); iter.hasNext();)
	    {
	    	List<Columna> columna = (List<Columna>)iter.next();
	    	for (Iterator<Columna> col = columna.iterator(); col.hasNext(); )
	    	{
				Columna c = (Columna)col.next();
				if (c.getVisible())
					tabla.agregarCelda(c.getValorArchivo());
	    	}
	    	if (tipoPresentacion.equals("pdf"))
	    	{
				lineas++;
				lineas++;
	    	}
			if (lineas >= tamanoPagina)
			{
				documento.add(tabla.getTabla());
				tabla = saltarPagina(documento, true, font, totalColumnas, tituloReporte, request);
				lineas = 2;
				imprimirEncabezado(tabla, transaccionForm);
			}
	    }
		
	    if (lineas < tamanoPagina)
	    	documento.add(tabla.getTabla());

	    MessageResources mensajes = getResources(request);
	    documento.add(lineaEnBlanco(font));
	    lineas++;
		if (lineas >= tamanoPagina)
		{
			saltarPagina(documento, false, font, null, null, request);
			lineas = 2;
		}
	    documento.add(lineaEnBlanco(font));
	    lineas++;
		if (lineas >= tamanoPagina)
		{
			saltarPagina(documento, false, font, null, null, request);
			lineas = 2;
		}
	    tabla = inicializarTabla(font, 2, request);
		tabla.setFont(Font.BOLD);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.setColorLetra(255, 255, 255);
	    tabla.setColorFondo(128, 128, 128);
		tabla.agregarCelda(mensajes.getMessage("jsp.reporte.transaccion.reporte.montoconsolidado"));
		tabla.agregarCelda(mensajes.getMessage("jsp.reporte.transaccion.reporte.totaloperacion"));
		lineas++;
		lineas++;
		if (lineas >= tamanoPagina)
		{
			documento.add(tabla.getTabla());
			tabla = saltarPagina(documento, true, font, totalColumnas, tituloReporte, request);
			lineas = 2;
		}
	    tabla.setFont(Font.NORMAL);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.setColorLetra(0, 0, 0);
	    tabla.setColorFondo(255, 255, 255);
		tabla.agregarCelda(transaccionForm.getTotalConsolidado());
		tabla.agregarCelda(transaccionForm.getTotalOperacion());
		lineas++;
		lineas++;
		if (lineas >= tamanoPagina)
		{
			documento.add(tabla.getTabla());
			tabla = saltarPagina(documento, true, font, totalColumnas, tituloReporte, request);
			lineas = 2;
		}
	    
	    if (lineas < tamanoPagina)
	    	documento.add(tabla.getTabla());
	}
	
	private void imprimirEncabezado(TablaBasicaPDF tabla, TransaccionForm transaccionForm) throws Exception
	{
		tabla.setFont(Font.BOLD);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.setColorLetra(255, 255, 255);
	    tabla.setColorFondo(128, 128, 128);
		for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
		{
			Columna columna = (Columna)col.next();
			if (columna.getVisible())
				tabla.agregarCelda(columna.getAlias());
		}
		lineas++;
		lineas++;
	    tabla.setFont(Font.NORMAL);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.setColorLetra(0, 0, 0);
	    tabla.setColorFondo(255, 255, 255);
	}
}
