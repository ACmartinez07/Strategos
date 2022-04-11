/**
 * 
 */
package com.visiongc.app.strategos.web.struts.graficos.actions;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.visiongc.app.strategos.graficos.StrategosGraficosService;
import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.reportes.StrategosReportesGraficoService;
import com.visiongc.app.strategos.reportes.model.ReporteGrafico;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.ColorUtil;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class SalvarGraficoAction extends VgcAction
{
	Grafico grafico = null;
	
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
	    Long graficoId = !request.getParameter("id").toString().equals("") ? Long.parseLong(request.getParameter("id").toString()) : new Long(0L);
	    String configuracion = request.getParameter("data").replace("[[por]]", "%").toString();
	    String nombre = request.getParameter("nombre").replace("[[por]]", "%").toString();
	    Long organizacionId = (new Long((String)request.getSession().getAttribute("organizacionId")));
	    Long usuarioId = ((Usuario)request.getSession().getAttribute("usuario")).getUsuarioId();
		String source = (request.getParameter("source") != null ? request.getParameter("source") : null);
		Long planId = ((request.getParameter("planId") != null && request.getParameter("planId") != "") ? (new Long((String)request.getParameter("planId")).longValue() != 0 ? Long.parseLong(request.getParameter("planId")) : null) : null);
		
		String showPresentacion = request.getParameter("showPresentacion") != null ? request.getParameter("showPresentacion").toString() : "0";
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		ConfiguracionUsuario presentacion = new ConfiguracionUsuario();
		ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
		pk.setConfiguracionBase("Strategos.Wizar.Grafico.Asistente.ShowPresentacion");
		pk.setObjeto("ShowPresentacion");
		pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
		presentacion.setPk(pk);
		presentacion.setData(showPresentacion);
		frameworkService.saveConfiguracionUsuario(presentacion, this.getUsuarioConectado(request));
		frameworkService.close();
		
	    Byte status = StatusUtil.getStatusNoSuccess();
	    int respuesta;
	    if (source.equals("Celda") || source.equals("Portafolio"))
	    {
	    	Long celdaId = (request.getParameter("objetoId") != null ? Long.parseLong(request.getParameter("objetoId")) : 0L);
	    	respuesta = SaveCelda(celdaId, configuracion, nombre, organizacionId, usuarioId, source, form, request);
	    	graficoId = celdaId; 
	    }
	    else
	    	respuesta = SaveGrafico(graficoId, configuracion, nombre, organizacionId, usuarioId, source, planId, request);
	    if (respuesta == 10000)
	    {
	    	status = StatusUtil.getStatusSuccess();
	    	if (grafico != null)
	    		graficoId = grafico.getGraficoId();
	    }
	    else if (respuesta == 10003)
	    	status = StatusUtil.getStatusRegistroDuplicado();
	    
	    String res = graficoId.toString() + "|" + status.toString();
	    request.setAttribute("ajaxResponse", res);

	    return mapping.findForward("ajaxResponse");
	}

	public int SaveCelda(Long celdaId, String configuracion, String nombre, Long organizacionId, Long usuarioId, String source, ActionForm form, HttpServletRequest request) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
	{
		int respuesta = 10000;

		StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
		Celda celda = (Celda)strategosCeldasService.load(Celda.class, new Long(celdaId));

		if (celda != null)
		{
		    String data = CheckLeyendaColor(configuracion.replace("[[num]]", "#"), celda.getConfiguracion() != null ? celda.getConfiguracion() : "");
		    data = data.replace("[[num]]", "#");
		    
		    if (source.equals("Portafolio"))
		    {
			    GraficoForm graficoForm = (GraficoForm)request.getSession().getAttribute("graficoForm");
			    if (graficoForm == null)
			    	graficoForm = new GraficoForm(); 
			    Grafico graficoLocal = new Grafico();
			    MessageResources mensajes = getResources(request);
			    graficoLocal.setConfiguracion(data);
			    celda.setConfiguracion(data);
			    new com.visiongc.app.strategos.web.struts.portafolios.actions.CrearGraficoAction().GetObjeto(graficoForm, graficoLocal, source, celda, mensajes);
		    }
		    else
			    celda.setConfiguracion(data);
		    celda.setCeldaId(celdaId);
		    celda.setTitulo(nombre);

		    respuesta = strategosCeldasService.saveCelda(celda, getUsuarioConectado(request));
		}

	    strategosCeldasService.close();

		return respuesta;
	}
	
	public int SaveGrafico(Long graficoId, String configuracion, String nombre, Long organizacionId, Long usuarioId, String source, Long planId, HttpServletRequest request) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
	{
		int respuesta = 10000;

		//Long reporteId = (request.getParameter("reporteId") != null ? Long.parseLong(request.getParameter("reporteId")) : 0L);
		
	    StrategosGraficosService strategosGraficosService = StrategosServiceFactory.getInstance().openStrategosGraficosService();
	   /*
	    StrategosReportesGraficoService reportesService = StrategosServiceFactory.getInstance().openStrategosReportesGraficoService();
	    ReporteGrafico reporte = null;
	    
	    if(reporteId != null && reporteId.longValue() != 0) {
	    	
	    	reporte = (ReporteGrafico)reportesService.load(ReporteGrafico.class, reporteId);
	    	
	    	if(reporte != null) {
	    		
	    		if (configuracion.indexOf("[[num]]") != -1)
		    		configuracion = configuracion.replace("[[num]]", "#");
	    		
	    		reporte.setConfiguracionGrafico(CheckLeyendaColor(configuracion, configuracion));
	    		respuesta = reportesService.save(reporte,  getUsuarioConectado(request));
	    		
	    	}
	    	
	    	
	    }else {
	    	*/
	    
		    if (graficoId.longValue() != 0)
		    {
		    	grafico = (Grafico)strategosGraficosService.load(Grafico.class, new Long(graficoId));
	
		    	Grafico graficoOriginal = (Grafico)strategosGraficosService.load(Grafico.class, new Long(grafico.getGraficoId()));
			    String data = CheckLeyendaColor(configuracion.replace("[[num]]", "#"), graficoOriginal.getConfiguracion());
			    data = data.replace("[[num]]", "#");
			    grafico.setConfiguracion(data);
		    }
		    else
		    {
		    	grafico = new Grafico(); 
		    	grafico.setOrganizacionId(organizacionId);
		    	grafico.setUsuarioId(usuarioId);
		    	if (configuracion.indexOf("[[num]]") != -1)
		    		configuracion = configuracion.replace("[[num]]", "#");
			    grafico.setConfiguracion(CheckLeyendaColor(configuracion, configuracion));
	
			    if (source.equals("Indicador") || source.equals("Celda"))
				{
					long objetoId = (request.getParameter("objetoId") != null ? Long.parseLong(request.getParameter("objetoId")) : 0L);
				    grafico.setObjetoId(objetoId);
				    grafico.setClassName(source);
				}
				else
				{
				    grafico.setObjetoId(null);
				    grafico.setClassName(null);
				}
		    }
		    
		    grafico.setGraficoId(graficoId);
		    grafico.setNombre(nombre);
	
		    respuesta = strategosGraficosService.saveGrafico(grafico, getUsuarioConectado(request));
	
		    strategosGraficosService.close();
		   // reportesService.close();
	    
	    
		return respuesta;
	}
	
	public String CheckLeyendaColor(String configuracion, String configuracionMerge) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
	{
		if (configuracionMerge.equals(""))
			return configuracion;
		
		DocumentBuilderFactory factory  =  DocumentBuilderFactory.newInstance();;
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(TextEncoder.deleteCharInvalid(configuracion))));
		NodeList lista = doc.getElementsByTagName("indicador");

		DocumentBuilderFactory factoryGrafico  =  DocumentBuilderFactory.newInstance();;
        DocumentBuilder builderGrafico = factoryGrafico.newDocumentBuilder();
        Document docGrafico = builderGrafico.parse(new InputSource(new StringReader(configuracionMerge)));
		NodeList listaGrafico = docGrafico.getElementsByTagName("indicador");
		
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService(strategosIndicadoresService);
		List<?> seriesTiempo = strategosSeriesTiempoService.getSeriesTiempo(0, 0, "serieId", null, false, null).getLista();
		strategosSeriesTiempoService.close();

		String nombreLeyenda = "";
		
		Long indicadorId = 0L;
		Boolean change = false;
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
				indicadorId = Long.parseLong(valor.getNodeValue());
				
				for (int j = 0; j < listaGrafico.getLength() ; j ++) 
				{
					Node nodeGrafico = listaGrafico.item(j);
					Element elementoGrafico = (Element) nodeGrafico;
					NodeList nodeListaGrafico = null;
					Node valorGrafico = null;
					
					if (elementoGrafico.getElementsByTagName("id").getLength() > 0)
					{
						nodeListaGrafico = elementoGrafico.getElementsByTagName("id").item(0).getChildNodes();
						valorGrafico = (Node) nodeListaGrafico.item(0);
						if (Long.parseLong(valorGrafico.getNodeValue()) == indicadorId)
						{
							Long serieId = null;
							if (elemento.getElementsByTagName("serie").getLength() > 0)
							{
								nodeLista = elemento.getElementsByTagName("serie").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								serieId = new Long(valor.getNodeValue());
							}

							if (elemento.getElementsByTagName("leyenda").getLength() > 0)
							{
								nodeLista = elemento.getElementsByTagName("leyenda").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if ((valor == null && elementoGrafico.getElementsByTagName("leyenda").getLength() > 0) || (valor.getNodeValue() == "" && elementoGrafico.getElementsByTagName("leyenda").getLength() > 0))
								{
									nodeListaGrafico = elementoGrafico.getElementsByTagName("leyenda").item(0).getChildNodes();
									valorGrafico = (Node) nodeListaGrafico.item(0);
									if (valorGrafico != null)
									{
										change = true;
										if (valor == null)
											elemento.getElementsByTagName("leyenda").item(0).setTextContent(valorGrafico.getNodeValue());
										else
											valor.setNodeValue(valorGrafico.getNodeValue());
									}
									else
									{
										Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(indicadorId));
										if (indicador != null)
										{
											indicador.setSeriesIndicador(new HashSet<SerieIndicador>());
											indicador.getSeriesIndicador().clear();
											
											if (serieId != null)
											{
												SerieIndicador serieIndicador = new SerieIndicador();
										        serieIndicador.setPk(new SerieIndicadorPK());
										        serieIndicador.getPk().setSerieId(serieId);
										        nombreLeyenda = "";
									            for (Iterator<?> j1 = seriesTiempo.iterator(); j1.hasNext(); ) 
									            {
									            	SerieTiempo serie = (SerieTiempo)j1.next();

									            	if (serie.getSerieId().byteValue() == serieId) 
									            	{
									            		serieIndicador.setNombre(serie.getNombre());
									            		nombreLeyenda = indicador.getNombre() + " - " + serie.getNombre();
									            		break;
									            	}
									            }

												change = true;
												if (valor == null)
													elemento.getElementsByTagName("leyenda").item(0).setTextContent(nombreLeyenda);
												else
													valor.setNodeValue(nombreLeyenda);
											}
										}
									}
								}
							}

							if (elemento.getElementsByTagName("color").getLength() > 0)
							{
								nodeLista = elemento.getElementsByTagName("color").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if ((valor == null && elementoGrafico.getElementsByTagName("color").getLength() > 0) || (valor.getNodeValue() == "" && elementoGrafico.getElementsByTagName("color").getLength() > 0))
								{
									nodeListaGrafico = elementoGrafico.getElementsByTagName("color").item(0).getChildNodes();
									valorGrafico = (Node) nodeListaGrafico.item(0);
									if (valorGrafico != null)
									{
										change = true;
										if (valor == null)
											elemento.getElementsByTagName("color").item(0).setTextContent(valorGrafico.getNodeValue());
										else
											valor.setNodeValue(valorGrafico.getNodeValue());
									}
									else
									{
										if (valor == null)
											elemento.getElementsByTagName("color").item(0).setTextContent(ColorUtil.getRndColorRGB());
										else
											valor.setNodeValue(ColorUtil.getRndColorRGB());
									}
								}
							}
							break;
						}
					}					
				}
			}
		}
		
		strategosIndicadoresService.close();
		
		if (change)
		{
			Source source = new DOMSource(doc);
			
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
			
			return writer.toString();
		}
		else
			return configuracion;
	}
}