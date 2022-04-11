/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.grafico.actions;

import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;

/**
 * @author Kerwin
 *
 */
public class ReadGraficoReporteAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GraficoForm graficoForm = (GraficoForm)form;
		if (request.getParameter("funcion") != null && request.getParameter("funcion").toString().equals("Asistente"))
		{
			// Presentacion
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			ConfiguracionUsuario presentacion = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Wizar.Grafico.Asistente.ShowPresentacion", "ShowPresentacion");
			if (presentacion != null && presentacion.getData() != null)
				graficoForm.setShowPresentacion(presentacion.getData().equals("1") ? true : false);
			frameworkService.close();
		}
		  
		if (request.getParameter("xml") != null && !request.getParameter("xml").equals(""))
		{
			String res = "";
			Grafico grafico = new Grafico();
			grafico.setGraficoId(0L);
			grafico.setNombre("");
			grafico.setConfiguracion(request.getParameter("xml"));
			res = new com.visiongc.app.strategos.web.struts.graficos.actions.SeleccionarGraficoAction().ReadXmlProperties(res, grafico);
			graficoForm.setFrecuencia(CheckIndicadores(grafico));
			if (graficoForm.getFrecuencia() == null)
			{
				graficoForm.setFrecuencia((byte) 3);
				graficoForm.setRespuesta("Error");
			}
			else
				graficoForm.setRespuesta(res);
		}
		else
			graficoForm.setFrecuencia((byte) 3);
		graficoForm.setFrecuencias(Frecuencia.getFrecuencias());
		  
		Calendar ahora = Calendar.getInstance();
		  
		graficoForm.setPeriodoInicial(new Integer("1"));
		graficoForm.setPeriodoFinal(new Integer("12"));
		graficoForm.setNumeroMaximoPeriodos(12);
		graficoForm.setAnoInicial(Integer.toString(ahora.get(1)));
		graficoForm.setAnoFinal(graficoForm.getAnoInicial());

		return mapping.findForward(forward);
	}
	  
	public Byte CheckIndicadores(Grafico grafico) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory  =  DocumentBuilderFactory.newInstance();;
    	DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(TextEncoder.deleteCharInvalid(grafico.getConfiguracion()))));
		NodeList lista = doc.getElementsByTagName("properties");
		lista = doc.getElementsByTagName("indicador");
		Byte frecuencia = null;
		if (lista.getLength() > 0)
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

			Indicador indicador = new Indicador();
				
			for (int i = 0; i < lista.getLength() ; i ++) 
			{
				Node node = lista.item(i);
				Element elemento = (Element) node;
				NodeList nodeLista = null;
				Node valor = null;
				
				if (elemento.getElementsByTagName("id").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("id").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					if (valor != null)
					{
						indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(valor.getNodeValue()));
						if (indicador != null)
						{
							if (frecuencia == null)
								frecuencia = indicador.getFrecuencia();
							
							if (frecuencia.byteValue() != indicador.getFrecuencia().byteValue())
							{
								frecuencia = null;
								break;
							}
						}
					}
				}

			}
			
			strategosIndicadoresService.close();
		}

		return frecuencia;
	}	  
}