/**
 * 
 */
package com.visiongc.app.strategos.web.struts.portafolios.actions;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.visiongc.app.strategos.graficos.StrategosGraficosService;
import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.graficos.util.AnoPeriodo;
import com.visiongc.app.strategos.indicadores.graficos.util.DatosSerie;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.portafolios.model.util.ValoresSerie;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.ColorUtil;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm.GraficoEstatus;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm.GraficoTipoIniciativa;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class CrearGraficoAction  extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		boolean cancelar = (request.getParameter("cancelar") != null ? Boolean.parseBoolean(request.getParameter("cancelar")) : false);
		if (cancelar)
		{
			if (request.getSession().getAttribute("configuracionGrafico") != null)
				request.getSession().removeAttribute("configuracionGrafico");
			return getForwardBack(request, 1, true);
		}

		Boolean virtual = (request.getParameter("virtual") != null ? Boolean.parseBoolean(request.getParameter("virtual")) : null);
		String source = (request.getParameter("source") != null ? request.getParameter("source") : null);
		String onFuncion = (request.getParameter("onFuncion") != null ? request.getParameter("onFuncion") : null);
		Byte frecuencia = (request.getParameter("frecuencia") != null ? Byte.parseByte(request.getParameter("frecuencia")) : Frecuencia.getFrecuenciaMensual());
		Calendar ahora = Calendar.getInstance();
		Integer ano = (request.getParameter("ano") != null ? Integer.parseInt(request.getParameter("ano")) : ahora.get(1));
		Integer periodo = (request.getParameter("periodo") != null ? Integer.parseInt(request.getParameter("periodo")) : 1);
		
		if (onFuncion != null && onFuncion.equals("onAplicar"))
		{
			if (onFuncion != null && onFuncion.equals("onAplicar"))
				request.getSession().setAttribute("configuracionGrafico", request.getParameter("data").replace("[[num]]", "#").replace("[[por]]", "%").toString());
			
			request.setAttribute("ajaxResponse", "10000");
    	    return mapping.findForward("ajaxResponse");
		}

		StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
		GraficoForm graficoForm = (GraficoForm)form;
		
		graficoForm.setVirtual(virtual);
		graficoForm.setSource(source);
		graficoForm.setFrecuencia(frecuencia);
		graficoForm.setAno(ano);
		graficoForm.setPeriodo(periodo);
		graficoForm.setFrecuenciasCompatibles(Frecuencia.getFrecuenciasCompatibles(frecuencia));
		
		Long planId = null;
		Long portafolioId = null;
		
		Grafico grafico = new Grafico();
		
		graficoForm.setVirtual(true);
		
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		StrategosPortafoliosService strategosPortafoliosService = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros = new HashMap<String, Object>();
		if (graficoForm.getSource().equals("Plan"))
			planId = ((request.getParameter("objetoId") != null && request.getParameter("objetoId") != "") ? Long.parseLong(request.getParameter("objetoId")) : null);
		else
			portafolioId = ((request.getParameter("portafolioId") != null && request.getParameter("portafolioId") != "") ? Long.parseLong(request.getParameter("portafolioId")) : null);
		
		if (planId != null)
		{
		    Plan plan = (Plan)strategosIniciativasService.load(Plan.class, planId);
	    	filtros.put("planId", plan.getPlanId().toString());
	    	String frecuencias = null;
	    	for (Iterator<Frecuencia> iter = graficoForm.getFrecuenciasCompatibles().iterator(); iter.hasNext();)
	    	{
	    		Frecuencia fre = (Frecuencia)iter.next();
				if (frecuencias == null)
					frecuencias = fre.getFrecuenciaId().toString() + ", ";
				else
					frecuencias = frecuencias + fre.getFrecuenciaId().toString() + ", ";
	    	}
			if (frecuencias != null)
			{
				frecuencias = frecuencias.substring(0, frecuencias.length() - 2);
				filtros.put("frecuencias", frecuencias);
			}
			else
				filtros.put("frecuencia", graficoForm.getFrecuencia());
		}
		else if (portafolioId != null)
		{
			filtros.put("portafolioId", portafolioId.toString());
			graficoForm.setPortafolioId(portafolioId);
			Portafolio portafolio = (Portafolio)strategosPortafoliosService.load(Portafolio.class, new Long(portafolioId));
			graficoForm.setPortafolio(portafolio);
		}

		filtros.put("historicoDate", "IS NULL");
		List<Iniciativa> iniciativas = strategosIniciativasService.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();
		setValores(graficoForm, iniciativas, strategosIniciativasService);

		String data = "";
		if (request.getParameter("data") != null)
		{
			data = request.getParameter("data").replace("[[num]]", "#").replace("[[por]]", "%");
			
			grafico.setNombre(request.getParameter("nombre").replace("[[por]]", "%"));
			grafico.setConfiguracion(data);
			grafico.setClassName(graficoForm.getSource());
		}
		else if (request.getSession().getAttribute("configuracionGrafico") != null)
		{
			data = (String) request.getSession().getAttribute("configuracionGrafico");
			request.getSession().removeAttribute("configuracionGrafico");
			
			grafico.setNombre(request.getParameter("nombre").replace("[[por]]", "%"));
			grafico.setConfiguracion(data);
			grafico.setClassName(graficoForm.getSource());
		}
		else
		{
			List<Object> objetos = new ArrayList<Object>();
			String[] ids = graficoForm.getObjetosIds().split(",");
			for (int i = 0; i < ids.length; i++)
				objetos.add(Long.parseLong(ids[i]));
			
			if (graficoForm.getSource().equals("Portafolio"))
			{
				Long celdaId = ((request.getParameter("objetoId") != null && request.getParameter("objetoId") != "") ? Long.parseLong(request.getParameter("objetoId")) : null);
				Celda celda = (Celda)strategosCeldasService.load(Celda.class, new Long(celdaId));
				if (celda != null && celda.getConfiguracion() != null)
				{
					grafico.setGraficoId(0L);
					grafico.setNombre(celda.getNombre() != null ? celda.getNombre() : celda.getTitulo());
					grafico.setConfiguracion(celda.getConfiguracion());
					grafico.setClassName(graficoForm.getSource());
				}
				else if (celda == null || celda.getConfiguracion() == null)
					SetGrafico(objetos, grafico, graficoForm.getSource(), planId, graficoForm.getTipoGrafico(), request);
			}
			else
				SetGrafico(objetos, grafico, graficoForm.getSource(), planId, graficoForm.getTipoGrafico(), request);
		}
		
		String res = "";
		String Ids = "";
		String Nombres = "";
		String SeriePlanId = "";
		boolean esPrimero = true;
		res = new com.visiongc.app.strategos.web.struts.graficos.actions.SeleccionarGraficoAction().ReadXmlProperties(res, grafico);
		for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
		{
			Iniciativa iniciativa = (Iniciativa)iter.next();
			if (!esPrimero)
			{
				Ids = Ids + "!;!";
				Nombres = Nombres + "!;!";
				SeriePlanId = SeriePlanId + "!;!";
			}
			Ids = Ids + iniciativa.getIniciativaId().toString();
			Nombres = Nombres + iniciativa.getNombre();
			if (planId != null)
				SeriePlanId = SeriePlanId + planId.toString();
			else
				SeriePlanId = SeriePlanId + "0";
			
			esPrimero = false;
		}
		res = res + "|" + "indicadorInsumoSeleccionadoIds!,!" + Ids + "|" + "indicadorInsumoSeleccionadoNombres!,!" + Nombres + "|" + "indicadorInsumoSeleccionadoSeriePlanId!,!" + SeriePlanId;			
		graficoForm.setRespuesta(res);
	
		GetObjeto(graficoForm, grafico, null, null, null);
		
		graficoForm.setClassName(grafico.getClassName());
		graficoForm.setFrecuencias(Frecuencia.getFrecuencias());

		boolean verForm = false;
		boolean editarForm = false;
		
		verForm = getPermisologiaUsuario(request).tienePermiso("INDICADOR_VIEWALL");
		editarForm = getPermisologiaUsuario(request).tienePermiso("INDICADOR_EDIT");
		
		if (!editarForm)
			editarForm = getPermisologiaUsuario(request).tienePermiso("INDICADOR_EVALUAR_GRAFICO_GRAFICO");

		if (verForm && !editarForm)
			graficoForm.setBloqueado(true);
		  
		GetGrafico(graficoForm, request);

		strategosPortafoliosService.close();
		strategosIniciativasService.close();

		Calendar fecha = PeriodoUtil.getDateByPeriodo(graficoForm.getFrecuencia(), graficoForm.getAno(), graficoForm.getPeriodo(), true);
		graficoForm.setFecha(VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta"));

		if (graficoForm.getSeries() != null && graficoForm.getSeries().size() == 0)
			graficoForm.getSeries().add(new DatosSerie());

		strategosCeldasService.close();
		
		return mapping.findForward(forward);
	}
	  
	private String getNombreLeyenda(GraficoForm graficoForm, DatosSerie serie)
	{
		String leyenda = "";
		
		if (serie.getShowOrganizacion() != null && serie.getShowOrganizacion())
		{
			StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
			OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(serie.getIndicador().getOrganizacionId()));
			strategosOrganizacionesService.close();
			graficoForm.setUbicacionOrganizacion(organizacionStrategos.getNombre());
			
			leyenda = leyenda + organizacionStrategos.getNombre();
		}
		
		if (serie.getNivelClase() != null)
		{
			String clases = "";
			Integer nivel = 1;
			StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
			ClaseIndicadores clase = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(serie.getIndicador().getClaseId()));
			clases =  "/" + clase.getNombre() + clases;
			if (serie.getNivelClase().intValue() > nivel.intValue() && clase.getPadreId() != null)
				clases = getPathClase(nivel, serie.getNivelClase(), clases, clase, strategosClasesIndicadoresService);
			strategosClasesIndicadoresService.close();
			graficoForm.setUbicacionClase(clases.substring(1, (clases.length() - 1)));
			
			leyenda = leyenda + clases;
		}
		
		if (leyenda.equals(""))
			leyenda = leyenda + serie.getNombreLeyenda();
		else
			leyenda = leyenda + "/" + serie.getNombreLeyenda();
			
		return leyenda;
	}

	private String getPathClase(Integer nivelDesde, Integer nivelHasta, String clases, ClaseIndicadores clase, StrategosClasesIndicadoresService strategosClasesIndicadoresService)
	{
		nivelDesde++;
		ClaseIndicadores clasePadre = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(clase.getPadreId()));
		clases =  "/" + clasePadre.getNombre() + clases;
		if (nivelHasta.intValue() > nivelDesde.intValue()  && clasePadre.getPadreId() != null)
			clases = getPathClase(nivelDesde, nivelHasta, clases, clasePadre, strategosClasesIndicadoresService);
	
		return clases;
	}
	  
	public ActionForm ReadGrafico(Long id, GraficoForm graficoForm, HttpServletRequest request) throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException
	{
		StrategosGraficosService strategosGraficosService = StrategosServiceFactory.getInstance().openStrategosGraficosService();
		Grafico grafico = (Grafico)strategosGraficosService.load(Grafico.class, new Long(id));
	  
		String res = "";
		res = new com.visiongc.app.strategos.web.struts.graficos.actions.SeleccionarGraficoAction().ReadXmlProperties(res, grafico);
		graficoForm.setRespuesta(res);
		graficoForm = (GraficoForm) GetObjeto(graficoForm, grafico, null, null, null);

		strategosGraficosService.close();

		return graficoForm; 
	}

	public ActionForm ReadCelda(Long id, GraficoForm graficoForm, HttpServletRequest request) throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException
	{
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		Celda celda = (Celda)strategosIndicadoresService.load(Celda.class, new Long(id));
		
		Grafico grafico = new Grafico();
		grafico.setGraficoId(celda.getCeldaId());
		grafico.setNombre(celda.getTitulo());
	  
		graficoForm = (GraficoForm) GetObjeto(graficoForm, grafico, null, null, null);

		strategosIndicadoresService.close();

		return graficoForm; 
	}
	  
	public ActionForm GetObjeto(GraficoForm graficoForm, Grafico grafico, String source, Celda celda, MessageResources mensajes) throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException
	{
		if (grafico != null)
		{
	    	graficoForm.setVirtual(true);
	    	graficoForm.setId(grafico.getGraficoId());
	    	graficoForm.setGraficoNombre(grafico.getNombre());
	    	Boolean nombreTitulo = false;
	    	
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
					
					if (valor != null)
						graficoForm.setTipo(Byte.parseByte((valor.getNodeValue())));
				}

				if (elemento.getElementsByTagName("titulo").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("titulo").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setTitulo(valor.getNodeValue());
				}

				if (elemento.getElementsByTagName("tituloEjeY").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("tituloEjeY").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setTituloEjeY(valor.getNodeValue());
				}
				
				if (elemento.getElementsByTagName("tituloEjeZ").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("tituloEjeZ").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setTituloEjeZ(valor.getNodeValue());
				}

				if (elemento.getElementsByTagName("tituloEjeX").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("tituloEjeX").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setTituloEjeX(valor.getNodeValue());
				}
				
				if (elemento.getElementsByTagName("anoInicial").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("anoInicial").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setAnoInicial(valor.getNodeValue());
				}

				if (elemento.getElementsByTagName("periodoInicial").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("periodoInicial").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setPeriodoInicial(Integer.valueOf(valor.getNodeValue()));
				}

				if (elemento.getElementsByTagName("anoFinal").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("anoFinal").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setAnoFinal(valor.getNodeValue());
				}

				if (elemento.getElementsByTagName("periodoFinal").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("periodoFinal").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setPeriodoFinal(Integer.valueOf(valor.getNodeValue()));
				}

				if (elemento.getElementsByTagName("ano").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("ano").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setAno(Integer.valueOf(valor.getNodeValue()));
				}

				if (elemento.getElementsByTagName("periodo").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("periodo").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setPeriodo(Integer.parseInt(valor.getNodeValue()));
				}

				if (elemento.getElementsByTagName("frecuencia").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("frecuencia").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setFrecuencia(Byte.parseByte(valor.getNodeValue()));
					else
						graficoForm.setFrecuencia((byte) 3);
				}
				
				if (elemento.getElementsByTagName("frecuenciaAgrupada").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("frecuenciaAgrupada").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setFrecuenciaAgrupada(Byte.parseByte(valor.getNodeValue()));
					else
						graficoForm.setFrecuenciaAgrupada((byte) 3);
				}
				
				if (elemento.getElementsByTagName("nombre").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("nombre").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setGraficoNombre(valor.getNodeValue());
				}

				if (elemento.getElementsByTagName("condicion").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("condicion").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setCondicion(valor.getNodeValue() == "1" || Integer.parseInt(valor.getNodeValue()) == 1 ? true : false);
					else
						graficoForm.setCondicion(false);
				}

				if (elemento.getElementsByTagName("verTituloImprimir").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("verTituloImprimir").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setVerTituloImprimir(valor.getNodeValue() == "1" || Integer.parseInt(valor.getNodeValue()) == 1 ? true : false);
					else
						graficoForm.setVerTituloImprimir(false);
				}
				
				if (elemento.getElementsByTagName("ajustarEscala").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("ajustarEscala").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setAjustarEscala(valor.getNodeValue() == "1" || Integer.parseInt(valor.getNodeValue()) == 1 ? true : false);
					else
						graficoForm.setAjustarEscala(false);
				}
				else
					graficoForm.setAjustarEscala(false);
				
				if (elemento.getElementsByTagName("tipoGrafico").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("tipoGrafico").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setTipoGrafico(Byte.parseByte(valor.getNodeValue()));
					else
						graficoForm.setTipoGrafico(GraficoTipoIniciativa.getGraficoTipoEstatus().byteValue());
				}
				
				if (elemento.getElementsByTagName("impVsAnoAnterior").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("impVsAnoAnterior").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null)
						graficoForm.setImpVsAnoAnterior(valor.getNodeValue() == "1" || Integer.parseInt(valor.getNodeValue()) == 1 ? true : false);
					else
						graficoForm.setImpVsAnoAnterior(false);
				}
			}
			
			lista = doc.getElementsByTagName("objeto");
			if (lista.getLength() > 0)
			{
				String nombreLeyenda = "";
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
							DatosSerie datosSerie = new DatosSerie();
							datosSerie.setId(new Long(valor.getNodeValue()));
					        if (elemento.getElementsByTagName("planId").getLength() > 0)
					        {
								nodeLista = elemento.getElementsByTagName("planId").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if (valor != null && !valor.getNodeValue().equals(""))
									datosSerie.setPlanId(new Long(valor.getNodeValue()));
								else
									datosSerie.setPlanId(null);
					        }
					        else
					        	datosSerie.setPlanId(null);
							        
					        if (elemento.getElementsByTagName("leyenda").getLength() > 0)
					        {
								nodeLista = elemento.getElementsByTagName("leyenda").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if (valor != null && !valor.getNodeValue().equals(""))
								{
									nombreLeyenda = valor.getNodeValue();
									datosSerie.setNombreLeyenda(nombreLeyenda);
								}
								else
									datosSerie.setNombreLeyenda(nombreLeyenda);
					        }
					        else
					        	datosSerie.setNombreLeyenda(nombreLeyenda);
						        
					        String color = null;
					        String colorDecimal = null;
					        String colorEntero = null;
					        if (graficoForm.getTipo() != 5 && graficoForm.getTipo() != 7)
					        {
						        if (elemento.getElementsByTagName("color").getLength() > 0)
						        {
									nodeLista = elemento.getElementsByTagName("color").item(0).getChildNodes();
									valor = (Node) nodeLista.item(0);
									if (valor != null && !valor.getNodeValue().equals(""))
										color = valor.getNodeValue();
									else
										color = ColorUtil.getRndColorRGB();
						        }
						        else
						        	color = ColorUtil.getRndColorRGB();

					        	colorDecimal = ColorUtil.convertRGBDecimal(color);
					        	colorEntero = colorDecimal;

					        	datosSerie.setColor(color);
					        	datosSerie.setColorDecimal(colorDecimal);
						        datosSerie.setColorEntero(colorEntero);
					        }
	
					        Boolean visualizar = true;
					        if (elemento.getElementsByTagName("visible").getLength() > 0)
					        {
								nodeLista = elemento.getElementsByTagName("visible").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if (valor != null && !valor.getNodeValue().equals(""))
								{
									visualizar = valor.getNodeValue() == "1" || Integer.parseInt(valor.getNodeValue()) == 1 ? true : false;
									datosSerie.setVisualizar(visualizar);
								}
								else
									datosSerie.setVisualizar(true);
					        }
					        else
					        	datosSerie.setVisualizar(true);

					        datosSerie.setPathClase(getNombreLeyenda(graficoForm, datosSerie));
					        nombreTitulo = ((graficoForm.getUbicacionOrganizacion() != null && !graficoForm.getUbicacionOrganizacion().equals("")) || (graficoForm.getUbicacionClase() != null && !graficoForm.getUbicacionClase().equals(""))); 
					        datosSerie.setBloquear(false);
					        datosSerie.setSerieAnoAnterior(false);

					        graficoForm.getSeries().add(datosSerie);
						}
						else
						{
							DatosSerie datosSerie = new DatosSerie();
							
							datosSerie.setNombreLeyenda("");
							datosSerie.setColor(ColorUtil.getRndColorRGB());
				        	datosSerie.setColorDecimal(ColorUtil.convertRGBDecimal(datosSerie.getColor()));
				        	datosSerie.setColorEntero(datosSerie.getColorDecimal());
							datosSerie.setTipoSerie(-1);
							datosSerie.setVisualizar(true);
							datosSerie.setPathClase("");
							datosSerie.setBloquear(false);
							datosSerie.setSerieAnoAnterior(false);
							
							graficoForm.getSeries().add(datosSerie);
						}
					}
				}
			}
			
			lista = doc.getElementsByTagName("iniciativa");
			if (lista.getLength() > 0)
			{
				StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
				List<Iniciativa> iniciativas = new ArrayList<Iniciativa>();
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
							Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(valor.getNodeValue()));
							iniciativas.add(iniciativa);
						}
					}
				}
				
				if (source != null && source.equals("Portafolio"))
				{
					grafico = new Grafico();
					grafico.setNombre(celda.getNombre());

					List<DatosSerie> series = new ArrayList<DatosSerie>();
					for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
					{
						Iniciativa iniciativa = (Iniciativa)iter.next();
						
						Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
						if (indicador != null)
						{
							SerieIndicador serie = null;
							for (Iterator<SerieIndicador> j = indicador.getSeriesIndicador().iterator(); j.hasNext(); )
							{
								serie = (SerieIndicador)j.next();
								if (serie.getPk().getSerieId().longValue() == SerieTiempo.getSerieRealId().longValue())
									break;
							}
					
							DatosSerie datosSerie = new DatosSerie();
					        datosSerie.setIndicador(indicador);
					        datosSerie.setSerieIndicador(serie);
							datosSerie.setNombreLeyenda(indicador.getNombre());
							datosSerie.setColor(ColorUtil.getRndColorRGB());
				        	datosSerie.setColorDecimal(ColorUtil.convertRGBDecimal(datosSerie.getColor()));
				        	datosSerie.setColorEntero(datosSerie.getColorDecimal());
							datosSerie.setTipoSerie(-1);
							datosSerie.setVisualizar(true);
							datosSerie.setShowOrganizacion(false);
							datosSerie.setNivelClase(null);
							datosSerie.setBloquear(false);
							datosSerie.setSerieAnoAnterior(false);
									
							series.add(datosSerie);
						}
					}

					celda.setDatosSeries(series);
					celda.setTipo(graficoForm.getTipo());
					celda.setTipoGrafico(graficoForm.getTipoGrafico());
					
					new com.visiongc.app.strategos.web.struts.portafolios.actions.VistaAction().setDatos(celda, mensajes, strategosIniciativasService);
					celda.setAnoInicial(graficoForm.getAno());
					celda.setAnoFinal(celda.getAnoInicial());
					celda.setPeriodoInicial(graficoForm.getPeriodo());
					celda.setPeriodoFinal(celda.getPeriodoInicial());
					celda.setTitulo(graficoForm.getTitulo());
					celda.setTituloEjeX(graficoForm.getTituloEjeX());
					celda.setTituloEjeY(graficoForm.getTituloEjeY());
					
					List<Object> objetos = new ArrayList<Object>();
					objetos.add(celda);
					new com.visiongc.app.strategos.web.struts.graficos.actions.GraficoAction().SetGrafico(objetos, grafico, "Portafolio", null, false, null);
					
					celda.setConfiguracion(grafico.getConfiguracion());
					celda.setNombre(celda.getTitulo());
				}
				else
					setValores(graficoForm, iniciativas, strategosIniciativasService);
				
				strategosIniciativasService.close();
			}

			if (nombreTitulo)
				graficoForm.setShowPath(true);
			else
			{
				graficoForm.setShowPath(false);
				graficoForm.setUbicacionClase(null);
				graficoForm.setUbicacionOrganizacion(null);
			}
		}

		return graficoForm; 
	}
	
	public void setValores(GraficoForm graficoForm, List<Iniciativa> iniciativas, StrategosIniciativasService strategosIniciativasService)
	{
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		
		ValoresSerie serie = null;
		List<ValoresSerie> estatus = new ArrayList<ValoresSerie>();

		// Agregar todos los estatus
		for (Iterator<GraficoEstatus> iter = GraficoEstatus.getListEstatus(graficoForm.getTipoGrafico()).iterator(); iter.hasNext();)
		{
			GraficoEstatus est = (GraficoEstatus)iter.next();
			serie = new ValoresSerie();
			serie.setSerieId(est.getId().toString());
			serie.setSerieNombre(est.getNombre());
			serie.setObjetos(new ArrayList<ObjetoClaveValor>());
			estatus.add(serie);
		}
		
		Long estado = null;
		ObjetoClaveValor objeto = null;
		String objetosIds = "";
		for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
		{
			Iniciativa iniciativa = (Iniciativa)iter.next();
			objetosIds = objetosIds + iniciativa.getIniciativaId().toString() + ",";
			
			int ano = graficoForm.getAno();
			int periodo = graficoForm.getPeriodo();
			if (iniciativa.getFrecuencia().byteValue() != graficoForm.getFrecuencia().byteValue())
			{
				Calendar fecha = PeriodoUtil.getDateByPeriodo(graficoForm.getFrecuencia(), graficoForm.getAno(), graficoForm.getPeriodo(), false);
				periodo = PeriodoUtil.getPeriodoDeFecha(fecha, iniciativa.getFrecuencia());
			}
			
			Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
			
			List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, ano, 000, periodo);
			List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, ano, 000, periodo);
			
			estado = null;
			objeto = new ObjetoClaveValor();
			objeto.setValor(iniciativa.getIniciativaId().toString());
			objeto.setClave(iniciativa.getNombre());
			if (graficoForm.getTipoGrafico().byteValue() == GraficoTipoIniciativa.getGraficoTipoTiposEstatus().byteValue())
			{
				  Double totalReal = null;
				  if (indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
				  {
					  for (Iterator<Medicion> iterMediciones = medicionesEjecutadas.iterator(); iterMediciones.hasNext(); ) 
					  {
						  Medicion medicion = (Medicion)iterMediciones.next();
						  if (medicion.getValor() != null && totalReal == null)
							  totalReal = 0D;
						  totalReal = totalReal + medicion.getValor();
					  }
				  }
				  else
				  {
					  Medicion medicionReal = null;
					  List<Medicion> medicionesAlPeriodoEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), periodo, ano, ano, periodo);
					  if (medicionesAlPeriodoEjecutadas.size() > 0)
						  medicionReal = medicionesAlPeriodoEjecutadas.get(0); 
					  if (medicionReal != null && medicionReal.getValor() != null && totalReal == null)
						  totalReal = medicionReal.getValor();
				  }
				  if (iniciativa.getEstatus() != null && iniciativa.getEstatus().getId().longValue() != EstatusType.getEstatusCencelado().longValue() && iniciativa.getEstatus().getId().longValue() != EstatusType.getEstatusSuspendido().longValue())
					  estado = new com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction().CalcularEstatus(totalReal);
				  else
					  estado = iniciativa.getEstatusId();
			}
			else
			{
				if (medicionesProgramadas.size() == 0)
					estado = GraficoEstatus.getEstatusSinIniciar();
				else if (medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0)
					estado = GraficoEstatus.getEstatusSinIniciarDesfasada();
				else if (graficoForm.getTipoGrafico().byteValue() == GraficoTipoIniciativa.getGraficoTipoEstatus().byteValue() && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D)
					estado = GraficoEstatus.getEstatusEnEjecucionSinRetrasos();
				else if (graficoForm.getTipoGrafico().byteValue() == GraficoTipoIniciativa.getGraficoTipoEstatus().byteValue() && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
					estado = GraficoEstatus.getEstatusEnEjecucionConRetrasosSuperables();
				else if (graficoForm.getTipoGrafico().byteValue() == GraficoTipoIniciativa.getGraficoTipoEstatus().byteValue() && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
					estado = GraficoEstatus.getEstatusEnEjecucionConRetrasosSignificativos();
				else if (graficoForm.getTipoGrafico().byteValue() == GraficoTipoIniciativa.getGraficoTipoPorcentajes().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() > 0D && iniciativa.getPorcentajeCompletado().doubleValue() < 25D)
					estado = GraficoEstatus.getEstatus0y25();
				else if (graficoForm.getTipoGrafico().byteValue() == GraficoTipoIniciativa.getGraficoTipoPorcentajes().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() > 25D && iniciativa.getPorcentajeCompletado().doubleValue() < 50D)
					estado = GraficoEstatus.getEstatus25y50();
				else if (graficoForm.getTipoGrafico().byteValue() == GraficoTipoIniciativa.getGraficoTipoPorcentajes().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() > 50D && iniciativa.getPorcentajeCompletado().doubleValue() < 75D)
					estado = GraficoEstatus.getEstatus50y75();
				else if (graficoForm.getTipoGrafico().byteValue() == GraficoTipoIniciativa.getGraficoTipoPorcentajes().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() > 75D && iniciativa.getPorcentajeCompletado().doubleValue() < 100D)
					estado = GraficoEstatus.getEstatus75y100();
				else if (iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D)
					estado = GraficoEstatus.getEstatusConcluidas();
				else
					estado = GraficoEstatus.getEstatusSinIniciar();
			}

			for (Iterator<ValoresSerie> iter2 = estatus.iterator(); iter2.hasNext();)
			{
				ValoresSerie est = (ValoresSerie)iter2.next();
				if (est != null && est.getSerieId() != null && ((Long)Long.parseLong(est.getSerieId())).longValue() == estado.longValue())
				{
					est.getObjetos().add(objeto);
					break;
				}
			}
		}
		strategosMedicionesService.close();
		
		if (!objetosIds.equals(""))
			objetosIds = objetosIds.substring(0, objetosIds.length() - 1);
		
		graficoForm.setObjetosIds(objetosIds);
		graficoForm.setValores(estatus);
	}
  
	public void GetGrafico(GraficoForm graficoForm, HttpServletRequest request) throws IOException
	{
		int numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(graficoForm.getFrecuencia().byteValue(), graficoForm.getAno());
		graficoForm.setNumeroMaximoPeriodos(numeroMaximoPeriodos);
	  
		Integer anoInicial = graficoForm.getAno();
		Integer periodoInicial = Integer.parseInt(graficoForm.getPeriodo().toString());
		
		List<AnoPeriodo> listaAnosPeriodos = AnoPeriodo.getListaAnosPeriodos(anoInicial, anoInicial, periodoInicial, periodoInicial, graficoForm.getNumeroMaximoPeriodos().intValue());
		graficoForm.setAnosPeriodos(listaAnosPeriodos);
	  
		StringBuffer sbEjeX = new StringBuffer();
		boolean firsOne = true;
		for (int index = 0; index < graficoForm.getAnosPeriodos().size(); index++)
		{
			AnoPeriodo periodo = (AnoPeriodo)graficoForm.getAnosPeriodos().get(index);

			if (!firsOne)
				sbEjeX.append(",");
			sbEjeX.append(periodo.getNombre());
			firsOne = false;
		}

		for (Iterator<ValoresSerie> iter2 = graficoForm.getValores().iterator(); iter2.hasNext();)
		{
			ValoresSerie est = (ValoresSerie)iter2.next();
			//dataset.addValue(est.getObjetos().size(), "Serie", est.getSerieNombre());
		}			
		
		StringBuffer sbSerieName = new StringBuffer();
		StringBuffer sbSerieData = new StringBuffer();
		firsOne = true;
		String format = "##0.00";
		for (Iterator<ValoresSerie> i = graficoForm.getValores().iterator(); i.hasNext(); )
		{
			ValoresSerie serie = (ValoresSerie)i.next();
			if (!firsOne)
			{
				sbSerieName.append(graficoForm.getSeparadorSeries());
				sbSerieData.append(graficoForm.getSeparadorSeries());
			}
			sbSerieName.append(serie.getSerieNombre());
			sbSerieData.append(serie.getValorFormateado(format, Double.parseDouble(((Integer)(serie.getObjetos().size())).toString())));
			firsOne = false;
		}
		
		graficoForm.setEjeX(sbEjeX.toString());
		graficoForm.setSerieName(sbSerieName.toString());
		graficoForm.setSerieData(sbSerieData.toString());
	}
	 
	public void SetGrafico(List<Object> objetos, Grafico grafico, String className, Long planId, Byte graficoTipo, HttpServletRequest request) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
	{
	    Calendar ahora = Calendar.getInstance();
        Integer ano = (request.getParameter("ano") != null ? Integer.parseInt(request.getParameter("ano")) : ahora.get(1));
		Integer periodo = (request.getParameter("periodo") != null ? Integer.parseInt(request.getParameter("periodo")) : 1);
		String tipo = (request.getParameter("tipo") != null ? request.getParameter("tipo") : "1");
		String nombre = (request.getParameter("graficoNombre") != null ? request.getParameter("graficoNombre").replace("[[por]]", "%") : "");
		String tituloEjeY = (request.getParameter("tituloEjeY") != null ? request.getParameter("tituloEjeY") : "");
		String tituloEjeX = (request.getParameter("tituloEjeX") != null ? request.getParameter("tituloEjeX") : "");
		Byte frecuencia = (request.getParameter("frecuencia") != null ? Byte.parseByte(request.getParameter("frecuencia")) : Frecuencia.getFrecuenciaMensual());
		String verTituloImprimir = (request.getParameter("verTituloImprimir") != null ? request.getParameter("verTituloImprimir") : "1");
		String ajustarEscala = (request.getParameter("ajustarEscala") != null ? request.getParameter("ajustarEscala") : "1");
		String titulo = (request.getParameter("titulo") != null ? request.getParameter("titulo").replace("[[por]]", "%") : "");
		Byte tipoGrafico = (request.getParameter("tipoGrafico") != null ? Byte.parseByte(request.getParameter("tipoGrafico")) : (graficoTipo != null ? graficoTipo : null));

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation implementation = builder.getDOMImplementation();
		Document document = implementation.createDocument(null, "grafico", null);
		document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

		Element raiz = document.createElement("properties");  // creamos el elemento raiz
		document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

		Element elemento = document.createElement("tipo"); //creamos un nuevo elemento
		Text text = document.createTextNode(tipo); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("titulo");
		text = document.createTextNode(titulo);
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("tituloEjeY");
		text = document.createTextNode(tituloEjeY);
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("tituloEjeX");
		text = document.createTextNode(tituloEjeX);
		elemento.appendChild(text);
		raiz.appendChild(elemento);
		
		elemento = document.createElement("ano");
		text = document.createTextNode(ano.toString());
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("periodo");
		text = document.createTextNode(periodo.toString());
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("frecuencia");
		text = document.createTextNode(frecuencia.toString());
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("nombre");
		text = document.createTextNode(nombre);
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("verTituloImprimir");
		text = document.createTextNode(verTituloImprimir);
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("ajustarEscala");
		text = document.createTextNode(ajustarEscala);
		elemento.appendChild(text);
		raiz.appendChild(elemento);
		
		Element indicadores = document.createElement("objetos");  // creamos el elemento raiz
		raiz.appendChild(indicadores);

		Element indicadorElement = document.createElement("objeto");  // creamos el elemento raiz
		indicadores.appendChild(indicadorElement);
		
		elemento = document.createElement("id");
		text = document.createTextNode(tipoGrafico.toString());
		elemento.appendChild(text);
		indicadorElement.appendChild(elemento);

		elemento = document.createElement("planId");
		text = document.createTextNode(planId != null ? planId.toString() : "");
		elemento.appendChild(text);
		indicadorElement.appendChild(elemento);
		
		elemento = document.createElement("leyenda");
		text = document.createTextNode(GraficoTipoIniciativa.getNombre(tipoGrafico));
		elemento.appendChild(text);
		indicadorElement.appendChild(elemento);

		elemento = document.createElement("color");
		text = document.createTextNode(ColorUtil.getRndColorRGB());
		elemento.appendChild(text);
		indicadorElement.appendChild(elemento);

		elemento = document.createElement("visible");
		text = document.createTextNode("1");
		elemento.appendChild(text);
		indicadorElement.appendChild(elemento);

		Source source = new DOMSource(document);
		
		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(source, result);

		grafico.setGraficoId(0L);
		grafico.setNombre(nombre);
		grafico.setConfiguracion(writer.toString().trim());
		if (objetos.size() == 1)
		{
  			Object objeto = objetos.get(0);
  			if (objeto.getClass().getName().equals("java.lang.Long")) 
  				grafico.setObjetoId((Long) objetos.get(0));
		}
		grafico.setClassName(className);
	}
}