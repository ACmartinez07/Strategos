/**
 * 
 */
package com.visiongc.app.strategos.web.struts.portafolios.actions;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;
import org.xml.sax.SAXException;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;

import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.app.strategos.portafolios.model.util.ValoresSerie;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm;

import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.ObjetoClaveValor;

import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;

/**
 * @author Kerwin
 *
 */
public class ImprimirGraficoAction extends VgcReporteBasicoAction
{
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
	    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

	    MessageResources mensajes = getResources(request);

        GraficoForm graficoForm = new GraficoForm();
	    
	    GetGrafico(request, graficoForm);

	    if (graficoForm.getVerTituloImprimir())
	    {
	    	ConfiguracionPagina configuracionPagina = getConfiguracionPagina(request);
	    	agregarSubTitulo(documento, configuracionPagina, graficoForm.getGraficoNombre(), false, true);

	    	Font fuente = configuracionPagina.getFuente();
			fuente.setSize(VgcFormatoReporte.TAMANO_FUENTE_SUBTITULO);
			fuente.setStyle(Font.BOLD);
			Paragraph texto = new Paragraph("", fuente);
			texto.setAlignment(Element.ALIGN_CENTER);
			texto.setLeading(VgcFormatoReporte.TAMANO_FUENTE_SUBTITULO);
			documento.add(texto);
			documento.add(texto);
	    }
	    
	    //java.awt.Image originalImage = chart.createBufferedImage(width+30, height+30);
	    //com.lowagie.text.Image image1 = com.lowagie.text.Image.getInstance(originalImage, Color.white);
        //documento.add(image1);

	    TablaBasicaPDF tabla = null;
	    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
	    int numeroColumnas = 1;
	    int[] columnas = new int[1 + numeroColumnas];
	    columnas[0] = 15;
	    int tamano = (80 - columnas[0]) / (numeroColumnas != 0 ? numeroColumnas : 1);
	    for (int i = 1; i < columnas.length; i++)
	    	columnas[i] = tamano; 

	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);

	    tabla.setFormatoFont(font.style());
	    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

	    tabla.agregarCelda(mensajes.getMessage("jsp.asistente.grafico.tabla.iniciativa.estatus"));
	    tabla.agregarCelda(mensajes.getMessage("jsp.asistente.grafico.tabla.iniciativa.nombre"));

	    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
	    for (Iterator<ValoresSerie> i = graficoForm.getValores().iterator(); i.hasNext(); )
	    {
	    	ValoresSerie valores = (ValoresSerie)i.next();
	    	
	    	tabla.agregarCelda(valores.getSerieNombre());
	    	String objetos = "";
	    	boolean esPrimero = true;
	    	for (Iterator<ObjetoClaveValor> j = valores.getObjetos().iterator(); j.hasNext(); )
	    	{
	    		ObjetoClaveValor objeto = (ObjetoClaveValor)j.next();
	    		if (!esPrimero)
	    			objetos = objetos + "\n";
	    		objetos = objetos + objeto.getClave();
	    		esPrimero = false;
	    	}
	    	tabla.agregarCelda(objetos);
	    }
	    
	    documento.add(tabla.getTabla());
	}
	
	public void GetGrafico(HttpServletRequest request, GraficoForm graficoForm) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
	{
		  Grafico grafico = new Grafico();
		  
		  grafico.setGraficoId(Long.parseLong(request.getParameter("id")));
		  grafico.setNombre(request.getParameter("nombre").replace("[[por]]", "%"));
		  grafico.setConfiguracion(((String) request.getSession().getAttribute("configuracionGrafico")).replace("[[num]]", "#"));
		  request.getSession().removeAttribute("configuracionGrafico");
		  graficoForm.setVirtual(true);

		  new com.visiongc.app.strategos.web.struts.portafolios.actions.CrearGraficoAction().GetObjeto(graficoForm, grafico, null, null, null);
		  new com.visiongc.app.strategos.web.struts.portafolios.actions.CrearGraficoAction().GetGrafico(graficoForm, request);
	}
}