/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.grafico.actions;

import java.io.IOException;
import java.io.StringReader;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.graficos.StrategosGraficosService;
import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.ColorUtil;
import com.visiongc.app.strategos.web.struts.graficos.forms.SeleccionarGraficoForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class SeleccionarGraficorReporteAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();
		
	    if (request.getParameter("funcion") != null) 
	    {
	    	String funcion = request.getParameter("funcion");
	    	if (funcion.equals("eliminar")) 
	    	{
	    		Long id = Long.parseLong(request.getParameter("Id"));
	    		
	    		Eliminar(id, request);
	    		
	    		request.setAttribute("ajaxResponse", "10000");
	    		return mapping.findForward("ajaxResponse");
	    	}
	    	
	    	if (funcion.equals("read")) 
	    	{
	    		Long id = Long.parseLong(request.getParameter("Id"));
	    		
	    		Read(id, request);
	    		
	    		request.setAttribute("ajaxResponse", "10000");
	    		return mapping.findForward("ajaxResponse");
	    	}
	    	
	    	if (funcion.equals("readFull")) 
	    	{
	    		Long id = Long.parseLong(request.getParameter("Id"));
	    		
	    		int respuesta = ReadFullProperties(id, request, form);
	    		
	    		if (respuesta != 10000)
	    			request.setAttribute("ajaxResponse", respuesta);
	    		
	    		return mapping.findForward("ajaxResponse");
	    	}
	    	
	    	if (funcion.equals("imprimir") || funcion.equals("saveImagen")) 
	    	{
		    	request.getSession().setAttribute("configuracionGrafico", request.getParameter("data").replace("[[por]]", "%").toString());
		    	
		    	request.setAttribute("ajaxResponse", "10000");
		    	return mapping.findForward("ajaxResponse");
	    	}
	    	
	    	if (funcion.equals("merge")) 
	    	{
				ActionMessages messages = getMessages(request);
				request.getSession().setAttribute("configuracionGrafico", new com.visiongc.app.strategos.web.struts.graficos.actions.SalvarGraficoAction().CheckLeyendaColor(request.getParameter("data").toString(), request.getParameter("dataXml").toString()));
		    	request.setAttribute("ajaxResponse", "");
			    saveMessages(request, messages);
		    	
		    	return mapping.findForward("ajaxResponse");
	    	}
	    }
		
		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
	    String ts = request.getParameter("ts");

		if (cancelar)
			return getForwardBack(request, 1, true);

		SeleccionarGraficoForm seleccionarGraficoForm = (SeleccionarGraficoForm)form;
		
		if (request.getParameter("nombreForma") != null) 
			seleccionarGraficoForm.setNombreForma(request.getParameter("nombreForma"));
		if (request.getParameter("nombreCampoOculto") != null) 
			seleccionarGraficoForm.setNombreCampoOculto(request.getParameter("nombreCampoOculto"));
		if (request.getParameter("funcionCierre") != null) 
			seleccionarGraficoForm.setFuncionCierre(request.getParameter("funcionCierre"));
		
	    if (!((ts == null) || (ts.equals(""))))
	    	forward = "finalizarForm";

	    if (forward.equals("finalizarForm")) 
	    	return getForwardBack(request, 1, true);
	    
	    return getForwardBack(request, 1, true);
	}
	
	private int Eliminar(Long id, HttpServletRequest request)
	{
		int result = 10000;
		
		ActionMessages messages = getMessages(request);
		
	    StrategosGraficosService strategosGraficosService = StrategosServiceFactory.getInstance().openStrategosGraficosService();

	    strategosGraficosService.unlockObject(request.getSession().getId(), id);

	    boolean bloqueado = !strategosGraficosService.lockForDelete(request.getSession().getId(), id);

	    Grafico grafico = (Grafico)strategosGraficosService.load(Grafico.class, new Long(id));
		
	    if (grafico != null)
	    {
	    	if (bloqueado)
	    	{
	    		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", grafico.getNombre()));
	    	}
	    	else
	    	{
	    		grafico.setGraficoId(Long.valueOf(id));
	    		int res = strategosGraficosService.deleteGrafico(grafico, getUsuarioConectado(request));

	    		if (res == 10004)
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", grafico.getNombre()));
	    		else
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", grafico.getNombre()));
	    	}
	    }
	    else 
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

	    strategosGraficosService.unlockObject(request.getSession().getId(), id);

	    strategosGraficosService.close();

	    saveMessages(request, messages);

		return result; 
	}
	
	private int Read(Long id, HttpServletRequest request)
	{
		int result = 10000;
		
		ActionMessages messages = getMessages(request);
		
	    StrategosGraficosService strategosGraficosService = StrategosServiceFactory.getInstance().openStrategosGraficosService();

	    Grafico grafico = (Grafico)strategosGraficosService.load(Grafico.class, new Long(id));
		
	    if (grafico != null)
	    	request.setAttribute("ajaxResponse", grafico.getNombre());
	    else 
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

	    strategosGraficosService.close();

	    saveMessages(request, messages);

		return result; 
	}
	
	private int ReadFullProperties(Long id, HttpServletRequest request, ActionForm form) throws ParserConfigurationException, SAXException, IOException
	{
		int result = 10000;
		
		ActionMessages messages = getMessages(request);
		
	    StrategosGraficosService strategosGraficosService = StrategosServiceFactory.getInstance().openStrategosGraficosService();

	    Grafico grafico = (Grafico)strategosGraficosService.load(Grafico.class, new Long(id));
		
	    if (grafico != null)
	    {
	    	String res = "";
	    	res = ReadXmlProperties(res, grafico);
	    	
	    	request.setAttribute("ajaxResponse", res);
	    }
	    else
	    {
	    	result = 10001;
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
		    saveMessages(request, messages);
	    }

	    strategosGraficosService.close();

		return result; 
	}
	
	public String ReadXmlProperties(String res, Grafico grafico) throws ParserConfigurationException, SAXException, IOException
	{
    	res = "id!,!" + ((grafico != null && grafico.getGraficoId() != null) ? grafico.getGraficoId().toString() : "");
    	res = res + "|" + "graficoNombre!,!" + grafico.getNombre();
    	
		DocumentBuilderFactory factory  =  DocumentBuilderFactory.newInstance();;
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(TextEncoder.deleteCharInvalid(grafico.getConfiguracion()))));
		NodeList lista = doc.getElementsByTagName("properties");
		 
		for (int i = 0; i < lista.getLength() ; i ++) 
		{
			Node node = lista.item(i);
			Element elemento = (Element) node;
			NodeList nodeLista = null;
			Node valor = null;
			
			if (elemento.getElementsByTagName("tipo").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("tipo").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				res = res + "|" + "tipo!,!" + (valor != null ? valor.getNodeValue() : "1");
			}

			if (elemento.getElementsByTagName("titulo").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("titulo").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				res = res + "|" + "titulo!,!" + (valor != null ? valor.getNodeValue() : "");
			}

			if (elemento.getElementsByTagName("showPath").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("showPath").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					res = res + "|" + "showPath!,!" + valor.getNodeValue();
				else
					res = res + "|" + "showPath!,!" + 0;
			}
			
			if (elemento.getElementsByTagName("tituloEjeY").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("tituloEjeY").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				res = res + "|" + "tituloEjeY!,!" + ((valor != null && !valor.getNodeValue().equals("")) ? valor.getNodeValue() : "");
			}
			
			if (elemento.getElementsByTagName("tituloEjeX").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("tituloEjeX").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				res = res + "|" + "tituloEjeX!,!" + ((valor != null && !valor.getNodeValue().equals("")) ? valor.getNodeValue() : "");
			}

			if (elemento.getElementsByTagName("ano").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("ano").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				res = res + "|" + "ano!,!" + ((valor != null && !valor.getNodeValue().equals("")) ? valor.getNodeValue() : "");
			}

			if (elemento.getElementsByTagName("periodo").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("periodo").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				res = res + "|" + "periodo!,!" + ((valor != null && !valor.getNodeValue().equals("")) ? valor.getNodeValue() : "");
			}
			
			if (elemento.getElementsByTagName("anoInicial").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("anoInicial").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				res = res + "|" + "anoInicial!,!" + ((valor != null && !valor.getNodeValue().equals("")) ? valor.getNodeValue() : "");
			}

			if (elemento.getElementsByTagName("periodoInicial").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("periodoInicial").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				res = res + "|" + "periodoInicial!,!" + ((valor != null && !valor.getNodeValue().equals("")) ? valor.getNodeValue() : "");
			}

			if (elemento.getElementsByTagName("anoFinal").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("anoFinal").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				res = res + "|" + "anoFinal!,!" + ((valor != null && !valor.getNodeValue().equals("")) ? valor.getNodeValue() : "");
			}

			if (elemento.getElementsByTagName("periodoFinal").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("periodoFinal").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				res = res + "|" + "periodoFinal!,!" + ((valor != null && !valor.getNodeValue().equals("")) ? valor.getNodeValue() : "");
			}

			if (elemento.getElementsByTagName("frecuencia").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("frecuencia").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				res = res + "|" + "frecuencia!,!" + ((valor != null && !valor.getNodeValue().equals("")) ? valor.getNodeValue() : "");
			}

			if (elemento.getElementsByTagName("frecuenciaAgrupada").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("frecuenciaAgrupada").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				res = res + "|" + "frecuenciaAgrupada!,!" + ((valor != null && !valor.getNodeValue().equals("")) ? valor.getNodeValue() : "");
			}
			
			if (elemento.getElementsByTagName("condicion").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("condicion").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					res = res + "|" + "condicion!,!" + valor.getNodeValue();
				else
					res = res + "|" + "condicion!,!" + 0;
			}

			if (elemento.getElementsByTagName("impVsAnoAnterior").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("impVsAnoAnterior").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					res = res + "|" + "impVsAnoAnterior!,!" + valor.getNodeValue();
				else
					res = res + "|" + "impVsAnoAnterior!,!" + 0;
			}
		}
		
		lista = doc.getElementsByTagName("indicador");
		if (lista.getLength() > 0)
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

			StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService(strategosIndicadoresService);
			List<?> seriesTiempo = strategosSeriesTiempoService.getSeriesTiempo(0, 0, "serieId", null, false, null).getLista();
			strategosSeriesTiempoService.close();

			Indicador indicador = new Indicador();
			boolean esPrimero = true;
			String Ids = "";
			String Nombres = "";
			String Serie = "";
			String SerieNombre = "";
			String SeriePlanId = "";
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
							if (!esPrimero)
							{
								Ids = Ids + "!;!";
								Nombres = Nombres + "!;!";
								Serie = Serie + "!;!";
								SeriePlanId = SeriePlanId + "!;!";
							}
	
							Ids = Ids + valor.getNodeValue();
							Serie = Serie + valor.getNodeValue();
							SeriePlanId = SeriePlanId + valor.getNodeValue();
							Nombres = Nombres + indicador.getNombre();
							SerieNombre = indicador.getNombre();
							boolean serieEncontrada = false;
							if (elemento.getElementsByTagName("serie").getLength() > 0)
							{
								nodeLista = elemento.getElementsByTagName("serie").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
	
					            for (Iterator<?> j = seriesTiempo.iterator(); j.hasNext(); ) 
					            {
					            	SerieTiempo serie = (SerieTiempo)j.next();
	
					            	if (serie.getSerieId().byteValue() == Long.parseLong(valor.getNodeValue())) 
					            	{
					            		Nombres = Nombres + "!@!" + serie.getNombre();
					            		SerieNombre = SerieNombre + " - " + serie.getNombre();
					            		serieEncontrada = true;
					            		break;
					            	}
					            }
								
								if (!serieEncontrada && SerieTiempo.getSerieMetaId().byteValue() == Long.parseLong(valor.getNodeValue()))
								{
				            		Nombres = Nombres + "!@!" + SerieTiempo.getSerieMeta().getNombre();
				            		SerieNombre = SerieNombre + " - " + SerieTiempo.getSerieMeta().getNombre();
								}
								Ids = Ids + "!@!" + valor.getNodeValue();
								Serie = Serie + "!@!" + valor.getNodeValue();
							}

							if (elemento.getElementsByTagName("planId").getLength() > 0)
							{
								nodeLista = elemento.getElementsByTagName("planId").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if (valor != null)
								{
									Perspectiva perspectiva = (Perspectiva)strategosIndicadoresService.load(Perspectiva.class, new Long(valor.getNodeValue()));
									if (perspectiva != null)
										SeriePlanId = SeriePlanId + "!@!" + perspectiva.getPlanId();
									else
										SeriePlanId = SeriePlanId + "!@!" + valor.getNodeValue();
								}
								else
									SeriePlanId = SeriePlanId + "!@!" + "";
							}
							else
								SeriePlanId = SeriePlanId + "!@!" + "";
							
							if (elemento.getElementsByTagName("leyenda").getLength() > 0)
							{
								nodeLista = elemento.getElementsByTagName("leyenda").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if (valor != null)
									Serie = Serie + "!@!" + valor.getNodeValue();
								else
									Serie = Serie + "!@!" + SerieNombre;
									
							}
							else
								Serie = Serie + "!@!" + SerieNombre;
	
							if (elemento.getElementsByTagName("color").getLength() > 0)
							{
								nodeLista = elemento.getElementsByTagName("color").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if (valor != null)
									Serie = Serie + "!@!" + valor.getNodeValue();
								else
									Serie = Serie + "!@!" + ColorUtil.getRndColorRGB();
							}
							else
								Serie = Serie + "!@!" + ColorUtil.getRndColorRGB();
	
							if (elemento.getElementsByTagName("visible").getLength() > 0)
							{
								nodeLista = elemento.getElementsByTagName("visible").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if (valor != null)
									Serie = Serie + "!@!" + valor.getNodeValue();
								else
									Serie = Serie + "!@!" + "1";
							}
							else
								Serie = Serie + "!@!" + "1";
							
							if (elemento.getElementsByTagName("tipoGrafico").getLength() > 0)
							{
								nodeLista = elemento.getElementsByTagName("tipoGrafico").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if (valor != null && Integer.parseInt(valor.getNodeValue()) < 3)
									Serie = Serie + "!@!" + valor.getNodeValue();
								else
									Serie = Serie + "!@!" + "-1";
							}
							else
								Serie = Serie + "!@!" + "-1";

							if (elemento.getElementsByTagName("showOrganizacion").getLength() > 0)
							{
								nodeLista = elemento.getElementsByTagName("showOrganizacion").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if (valor != null)
									Serie = Serie + "!@!" + valor.getNodeValue();
								else
									Serie = Serie + "!@!" + "0";
							}
							else
								Serie = Serie + "!@!" + "0";

							if (elemento.getElementsByTagName("nivelClase").getLength() > 0)
							{
								nodeLista = elemento.getElementsByTagName("nivelClase").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if (valor != null)
									Serie = Serie + "!@!" + valor.getNodeValue();
								else
									Serie = Serie + "!@!" + "";
									
							}
							else
								Serie = Serie + "!@!" + "";
							
							esPrimero = false;
						}
					}
					else
					{
						Ids = Ids + "" + "!@!" + "";
						Serie = Serie + "" + "!@!" + "" + "!@!" + "" + "!@!" + ColorUtil.getRndColorRGB() + "!@!" + "1" + "!@!" + "-1" + "!@!" + "0" + "!@!" + "";
						SeriePlanId = SeriePlanId + "" + "!@!" + "";
						Nombres = Nombres + "" + "!@!" + "";
					}
				}
			}
			
			res = res + "|" + "indicadorInsumoSeleccionadoIds!,!" + Ids + "|" + "indicadorInsumoSeleccionadoNombres!,!" + Nombres + "|" + "indicadorInsumoSeleccionadoSerie!,!" + Serie + "|" + "indicadorInsumoSeleccionadoSeriePlanId!,!" + SeriePlanId;
			
			strategosIndicadoresService.close();
		}

		return res;
	}
}