/**
 * 
 */
package com.visiongc.app.strategos.web.struts.graficos.actions;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
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
import com.visiongc.app.strategos.indicadores.graficos.util.DatosAlerta;
import com.visiongc.app.strategos.indicadores.graficos.util.DatosSerie;
import com.visiongc.app.strategos.indicadores.graficos.util.TipoSerieGrafico;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.util.MetaAnualParciales;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.StrategosVistasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.reportes.StrategosReportesGraficoService;
import com.visiongc.app.strategos.reportes.model.ReporteGrafico;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.ColorUtil;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.util.SerieUtil;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm.GraficoTipo;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm.GraficoTipoIniciativa;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class GraficoAction extends VgcAction
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

		long graficoId = ((request.getParameter("graficoId") != null && request.getParameter("graficoId") != "") ? Long.parseLong(request.getParameter("graficoId")) : 0L);
		Boolean virtual = ((request.getParameter("virtual") != null && request.getParameter("virtual") != "") ? Boolean.parseBoolean(request.getParameter("virtual")) : null);
		String source = ((request.getParameter("source") != null && request.getParameter("source") != "") ? request.getParameter("source") : null);
		Long claseId = ((request.getParameter("claseId") != null && request.getParameter("claseId") != "") ? Long.parseLong(request.getParameter("claseId")) : null);
		Long planId = ((request.getParameter("planId") != null && request.getParameter("planId") != "") ? Long.parseLong(request.getParameter("planId")) : null);
		String onFuncion = ((request.getParameter("onFuncion") != null && request.getParameter("onFuncion") != "") ? request.getParameter("onFuncion") : null);
		

		if (onFuncion != null && onFuncion.equals("onAplicar"))
		{
			if (onFuncion != null && onFuncion.equals("onAplicar"))
				request.getSession().setAttribute("configuracionGrafico", request.getParameter("data").replace("[[num]]", "#").replace("[[por]]", "%").toString());
			
			request.setAttribute("ajaxResponse", "10000");
    	    return mapping.findForward("ajaxResponse");
		}

		StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
		StrategosGraficosService strategosGraficosService = StrategosServiceFactory.getInstance().openStrategosGraficosService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosVistasService strategosVistasService = StrategosServiceFactory.getInstance().openStrategosVistasService();
		StrategosPaginasService strategosPaginasService = StrategosServiceFactory.getInstance().openStrategosPaginasService();
		StrategosPortafoliosService strategosPortafoliosService = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		
		MessageResources mensajes = getResources(request);
		
		if (onFuncion != null && onFuncion.equals("onUndo"))
		{
			long id = ((request.getParameter("graficoUndoId") != null && request.getParameter("graficoUndoId") != "") ? Long.parseLong(request.getParameter("graficoUndoId")) : 0L);
			if (source.equals("Celda"))
			{
				Celda celda = (Celda)strategosCeldasService.load(Celda.class, new Long(id));
				if (celda != null)
				{
					celda.setConfiguracion(null);
					strategosCeldasService.saveCelda(celda, getUsuarioConectado(request));
				}
			}
			else
			{
				Grafico grafico = (Grafico)strategosGraficosService.load(Grafico.class, new Long(id));
				if (grafico != null)
					strategosGraficosService.deleteGrafico(grafico, getUsuarioConectado(request));
			}
		}
		
		GraficoForm graficoForm = (GraficoForm)form;
		
		graficoForm.setShowDuppont(false);
		graficoForm.setIndicadorId(null);
		graficoForm.setVirtual(virtual);
		graficoForm.setSource(source);
		graficoForm.setClaseId(claseId);
		graficoForm.setPlanId(planId);
		
		String funcion = ((request.getParameter("funcion") != null && request.getParameter("funcion") != "") ? request.getParameter("funcion") : null);
				
		if(funcion !=null && funcion.equals("reporte?defaultLoader=true")) {
			Long reporteId = ((request.getParameter("reporteId") != null && request.getParameter("reporteId") != "") ? Long.parseLong(request.getParameter("reporteId")) : null);
			graficoForm.setEsReporteGrafico(true);
			
			graficoForm.setReporteId(reporteId);
		}
		
    	String url = request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + "/temp/uploads/" ;
		url = request.getScheme() + "://" + url.replaceAll("//", "/");
		graficoForm.setUrl(url);
		  
		if (graficoForm.getSource().equals("Indicador") || graficoForm.getSource().equals("Celda") || graficoForm.getSource().equals("Portafolio"))
		{
			Grafico grafico = new Grafico();
			 
			graficoForm.setVirtual(true);
			String objetosIds = (request.getParameter("objetoId") != null && !request.getParameter("objetoId").equals("") ? request.getParameter("objetoId").replace("ID", "") : "");
			Long objetoId = 0L;
			boolean multipleObjetos = false;
			List<Object> objetos = new ArrayList<Object>();
			if (!objetosIds.equals("") && objetosIds.indexOf(",") == -1)
				objetoId = Long.parseLong(objetosIds);
			else
			{
				multipleObjetos = true;
				Byte frecuencia = null;
				if (graficoForm.getSource().equals("Indicador"))
				{
					String[] ids = objetosIds.split(",");
					
					for (int i = 0; i < ids.length; i++)
					{
						Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(Long.parseLong(ids[i])));
						if (frecuencia == null)
							frecuencia = indicador.getFrecuencia();
						if (frecuencia.byteValue() != indicador.getFrecuencia().byteValue())
						{
							ActionMessages messages = getMessages(request);
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.grafico.alert.multiplesindicadores.error"));
							saveMessages(request, messages);
							
							return mapping.findForward("gestionarIndicadoresAction");
						}
						
						objetos.add(indicador);
					}
				}
				graficoForm.setObjetosIds(objetosIds);
			}

			String data = "";
			if (request.getParameter("data") != null)
				data = request.getParameter("data").replace("[[num]]", "#").replace("[[por]]", "%");
			else if (request.getSession().getAttribute("configuracionGrafico") != null)
			{
				data = (String) request.getSession().getAttribute("configuracionGrafico");
				request.getSession().removeAttribute("configuracionGrafico");
			}
			if (data.equals(""))
			{
				if (graficoForm.getSource().equals("Celda") || graficoForm.getSource().equals("Portafolio"))
				{
					Long vistaId = (request.getParameter("vistaId") != null ? Long.parseLong(request.getParameter("vistaId")) : 0L);
					Long paginaId = (request.getParameter("paginaId") != null ? Long.parseLong(request.getParameter("paginaId")) : 0L);
					Vista vista = (Vista)strategosVistasService.load(Vista.class, new Long(vistaId));
					Pagina pagina = (Pagina)strategosPaginasService.load(Pagina.class, new Long(paginaId));
					Celda celda = (Celda)strategosCeldasService.load(Celda.class, new Long(objetoId));
					if (celda != null && pagina == null)
						pagina = (Pagina)strategosPaginasService.load(Pagina.class, new Long(celda.getPaginaId()));
					if (pagina != null)
						paginaId = pagina.getPaginaId();

					Map<String, String> filtros = new HashMap<String, String>();
					String atributoOrden = "celdaId";
					String tipoOrden = "ASC";
					filtros.put("paginaId", paginaId.toString());
					PaginaLista paginaCeldas = strategosCeldasService.getCeldas(0, 0, atributoOrden, tipoOrden, true, filtros, getUsuarioConectado(request));
					
					String previaCeldaId = "0";
					String siguienteCeldaId = "0";

					List<Celda> listaCeldas = new ArrayList<Celda>();
					listaCeldas.addAll(paginaCeldas.getLista());
					Integer indice = new Integer(0);
					
					if (celda != null && graficoForm.getSource().equals("Portafolio"))
					{
						Long portafolioId = (request.getParameter("portafolioId") != null ? Long.parseLong(request.getParameter("portafolioId")) : 0L);
						Portafolio portafolio = (Portafolio)strategosPortafoliosService.load(Portafolio.class, new Long(portafolioId));
						celda.setTipoGrafico(GraficoTipoIniciativa.getGraficoPorcentajePortafolio());
						
						if (celda.getConfiguracion() != null)
						{
							celda.setDatosSeries(new ArrayList<DatosSerie>());
							new com.visiongc.app.strategos.web.struts.portafolios.actions.VistaAction().setDatos(celda, mensajes, strategosIniciativasService);
						}
						else
						{
							Calendar ahora = Calendar.getInstance();
							Integer ano = ahora.get(1);
							Integer periodo = 1;

							celda.setTipo(GraficoTipo.getTipoColumna());
							celda.setAnoInicial(ano);
							celda.setPeriodoInicial(periodo);
							
							Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, portafolio.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
							List<Medicion> mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(000), new Integer(9999), new Integer(000), new Integer(999));
							Integer anoDesde = null;
							Integer anoHasta = null;
							Integer periodoDesde = null;
							Integer periodoHasta = null;
							
					    	for (Iterator<Medicion> iterAportes = mediciones.iterator(); iterAportes.hasNext(); )
					    	{
					    		Medicion aporte = (Medicion)iterAportes.next();
						    		if (anoDesde == null)
						    			anoDesde = aporte.getMedicionId().getAno();
					    		if (anoHasta == null)
					    			anoHasta = aporte.getMedicionId().getAno();
					    		if (periodoDesde == null)
					    			periodoDesde = aporte.getMedicionId().getPeriodo();
					    		if (periodoHasta == null)
					    			periodoHasta = aporte.getMedicionId().getPeriodo();
					    		
					    		if (anoDesde.intValue() > aporte.getMedicionId().getAno())
					    			anoDesde = aporte.getMedicionId().getAno();
					    		if (anoHasta.intValue() < aporte.getMedicionId().getAno())
					    			anoHasta = aporte.getMedicionId().getAno();
					    		if (periodoDesde.intValue() > aporte.getMedicionId().getPeriodo())
					    			periodoDesde = aporte.getMedicionId().getPeriodo();
					    		if (periodoHasta.intValue() < aporte.getMedicionId().getPeriodo())
					    			periodoHasta = aporte.getMedicionId().getPeriodo();
					    	}
				    		if (anoDesde == null)
				    			anoDesde = ano;
				    		if (anoHasta == null)
				    			anoHasta = ano;
				    		if (periodoDesde == null)
				    			periodoDesde = periodo;
				    		if (periodoHasta == null)
				    			periodoHasta = periodo;
				    		
							List<DatosSerie> series = new ArrayList<DatosSerie>();
							for (Iterator<SerieIndicador> j = indicador.getSeriesIndicador().iterator(); j.hasNext(); )
							{
								SerieIndicador serie = (SerieIndicador)j.next();

								DatosSerie datosSerie = new DatosSerie();
						        datosSerie.setIndicador(indicador);
						        datosSerie.setSerieIndicador(serie);
								datosSerie.setNombreLeyenda(indicador.getNombre()  + " - " + serie.getSerieTiempo().getNombre());
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

							celda.setFrecuencia(portafolio.getFrecuencia());
							celda.setFrecuenciaAgrupada(portafolio.getFrecuencia());
							celda.setAjustarEscala("1");
							celda.setVerTituloImprimir("1");
							celda.setCondicion("0");
							celda.setNombre(celda.getTitulo());
							celda.setTituloEjeY(indicador.getUnidad().getNombre());
							celda.setAnoInicial(anoDesde);
							celda.setAnoFinal(anoHasta);
							celda.setPeriodoInicial(periodoDesde);
							celda.setPeriodoFinal(periodoHasta);
							celda.setDatosSeries(series);
						}
						
						graficoForm.setPortafolio(portafolio);
						graficoForm.setPortafolioId(portafolio.getId());
						graficoForm.setFrecuencias(Frecuencia.getFrecuencias());
						graficoForm.setFrecuencia(portafolio.getFrecuencia());
						graficoForm.setAno(celda.getAnoInicial());
						graficoForm.setPeriodo(celda.getPeriodoInicial());
						graficoForm.setFrecuenciasCompatibles(Frecuencia.getFrecuenciasCompatibles(Frecuencia.getFrecuenciaDiaria()));
						graficoForm.setVirtual(true);
						graficoForm.setTipoGrafico(celda.getTipoGrafico());
						graficoForm.setVirtual(true);
						graficoForm.setMostrarLeyendas(celda.getVerLeyenda() != null ? celda.getVerLeyenda() : false);
						graficoForm.setMostrarTooltips(false);
						graficoForm.setTamanoLeyenda(8);
						graficoForm.setClassName(graficoForm.getSource());
						graficoForm.setFrecuencias(Frecuencia.getFrecuencias());
						graficoForm.setMostrarTooltips(false);
						graficoForm.setTamanoLeyenda(8);
						graficoForm.setSource("Celda");

						objetos = new ArrayList<Object>();
						objetos.add(celda);
						grafico = new Grafico();
						SetGrafico(objetos, grafico, graficoForm.getSource(), null, true, request);

						if (celda.getConfiguracion() == null)
							celda.setConfiguracion(grafico.getConfiguracion());
						graficoForm.setMostrarLeyendas(celda.getVerLeyenda() != null ? celda.getVerLeyenda() : false);
					}
					
					for (Iterator<Celda> i = listaCeldas.iterator(); i.hasNext(); )
					{
						Celda celdaint = (Celda)i.next();
						if (celdaint.getCeldaId() == celda.getCeldaId())
						{
							celda.setIndice(indice);
							break;
						}
						indice = new Integer(indice.intValue() + 1);
					}
					
					if (celda != null)
					{
						grafico.setGraficoId(celda.getCeldaId());
						grafico.setNombre(celda.getTitulo());
						grafico.setConfiguracion(celda.getConfiguracion());
						grafico.setObjetoId(celda.getCeldaId());
						grafico.setClassName("Celda");
					}

					if (celda.getConfiguracion() == null || celda.getConfiguracion().equals(""))
					{
						objetos.add(celda);
						SetGrafico(objetos, grafico, graficoForm.getSource(), null, false, request);
						if (celda != null && graficoForm.getSource().equals("Portafolio"))
						{
							celda.setConfiguracion(grafico.getConfiguracion());
							celda.setEjeX(graficoForm.getEjeX());
							celda.setSerieName(graficoForm.getSerieName());
							celda.setSerieData(graficoForm.getSerieData());
							celda.setSerieColor(graficoForm.getSerieColor());
							celda.setSerieTipo(graficoForm.getSerieTipo());
							celda.setRangoAlertas(graficoForm.getRangoAlertas());
							celda.setIsAscendente(graficoForm.getIsAscendente());
						}
					}

					int anteriorCeldaIndice = celda.getIndice().intValue() - 1;
					int proximaCeldaIndice = celda.getIndice().intValue() + 1;

					if (anteriorCeldaIndice < 0) 
						anteriorCeldaIndice = listaCeldas.size() - 1;

					if (proximaCeldaIndice > listaCeldas.size() - 1) 
						proximaCeldaIndice = 0;

					if (((Celda)listaCeldas.get(anteriorCeldaIndice)).getCeldaId() != null) 
						previaCeldaId = ((Celda)listaCeldas.get(anteriorCeldaIndice)).getCeldaId().toString();

					if (((Celda)listaCeldas.get(proximaCeldaIndice)).getCeldaId() != null) 
						siguienteCeldaId = ((Celda)listaCeldas.get(proximaCeldaIndice)).getCeldaId().toString();

					celda.setIndice(celda.getIndice()+1);
					graficoForm.setPaginaId(paginaId);
					graficoForm.setVistaId(vistaId);
					graficoForm.setPreviaCeldaId(new Long(previaCeldaId));
					graficoForm.setSiguienteCeldaId(new Long(siguienteCeldaId));
					graficoForm.setVista(vista);
					graficoForm.setPagina(pagina);
					graficoForm.setCelda(celda);
				}
				else
				{
					if (!multipleObjetos)
					{
						Map<String, String> filtros = new HashMap<String, String>();
						filtros.put("organizacionId", (new Long((String)request.getSession().getAttribute("organizacionId"))).toString());
						filtros.put("usuarioId", ((Usuario)request.getSession().getAttribute("usuario")).getUsuarioId().toString());
						filtros.put("objetoId", (new Long(objetoId).toString()));
		
						PaginaLista paginaGraficos = strategosGraficosService.getGraficos(0, 0, "nombre", "ASC", true, filtros);
						if (paginaGraficos.getLista() != null && paginaGraficos.getLista().size() > 0) 
							grafico = (Grafico)paginaGraficos.getLista().get(0);
						else
						{
							objetos.add(objetoId);
							SetGrafico(objetos, grafico, graficoForm.getSource(), planId, false, request);
						}
					}
					else
					{
						if (!multipleObjetos)
							objetos.add(objetoId);
						else if (objetos.size() == 0)
						{
							String[] ids = objetosIds.split(",");
							for (int i = 0; i < ids.length; i++)
								objetos.add(Long.parseLong(ids[i]));
						}
						
						SetGrafico(objetos, grafico, graficoForm.getSource(), planId, false, request);
					}
				}
			}
			else
			{
				Long portafolioId = ((request.getParameter("portafolioId") != null && !request.getParameter("portafolioId").equals("")) ? Long.parseLong(request.getParameter("portafolioId")) : null);
				if (portafolioId != null)
				{
					Portafolio portafolio = (Portafolio)strategosPortafoliosService.load(Portafolio.class, new Long(portafolioId));
					if (portafolio != null)
					{
						graficoForm.setPortafolio(portafolio);
						graficoForm.setPortafolioId(portafolio.getId());
					}
				}
				grafico.setGraficoId(Long.parseLong(request.getParameter("id")));
				grafico.setNombre(request.getParameter("nombre"));
				grafico.setConfiguracion(data);
				grafico.setObjetoId(objetoId);
				grafico.setClassName(graficoForm.getSource());
				
				if (graficoForm.getSource().equals("Celda") || graficoForm.getSource().equals("Portafolio"))
				{
					Long vistaId = ((request.getParameter("vistaId") != null && !request.getParameter("vistaId").equals("")) ? Long.parseLong(request.getParameter("vistaId")) : 0L);
					Long paginaId = ((request.getParameter("paginaId") != null && !request.getParameter("paginaId").equals("")) ? Long.parseLong(request.getParameter("paginaId")) : 0L);

					Vista vista = (Vista)strategosVistasService.load(Vista.class, new Long(vistaId));
					Pagina pagina = (Pagina)strategosPaginasService.load(Pagina.class, new Long(paginaId));
					Celda celda = (Celda)strategosCeldasService.load(Celda.class, new Long(objetoId));

					Map<String, String> filtros = new HashMap<String, String>();
					String atributoOrden = "celdaId";
					String tipoOrden = "ASC";
					filtros.put("paginaId", paginaId.toString());
					PaginaLista paginaCeldas = strategosCeldasService.getCeldas(1, 30, atributoOrden, tipoOrden, true, filtros, getUsuarioConectado(request));
					
					String previaCeldaId = "0";
					String siguienteCeldaId = "0";

					List<Celda> listaCeldas = new ArrayList<Celda>();
					listaCeldas.addAll(paginaCeldas.getLista());
					Integer indice = new Integer(0);
					
					if (listaCeldas != null)
					{
						for (Iterator<Celda> i = listaCeldas.iterator(); i.hasNext(); )
						{
							Celda celdaint = (Celda)i.next();
							if (celdaint.getCeldaId() == celda.getCeldaId())
							{
								celda.setIndice(indice);
								break;
							}
							indice = new Integer(indice.intValue() + 1);
						}
					}

					int anteriorCeldaIndice = celda.getIndice().intValue() - 1;
					int proximaCeldaIndice = celda.getIndice().intValue() + 1;

					if (anteriorCeldaIndice < 0) 
						anteriorCeldaIndice = listaCeldas.size() - 1;

					if (proximaCeldaIndice > listaCeldas.size() - 1) 
						proximaCeldaIndice = 0;

					if (((Celda)listaCeldas.get(anteriorCeldaIndice)).getCeldaId() != null) 
						previaCeldaId = ((Celda)listaCeldas.get(anteriorCeldaIndice)).getCeldaId().toString();

					if (((Celda)listaCeldas.get(proximaCeldaIndice)).getCeldaId() != null) 
						siguienteCeldaId = ((Celda)listaCeldas.get(proximaCeldaIndice)).getCeldaId().toString();

					celda.setIndice(celda.getIndice()+1);
					graficoForm.setPaginaId(paginaId);
					graficoForm.setVistaId(vistaId);
					graficoForm.setPreviaCeldaId(new Long(previaCeldaId));
					graficoForm.setSiguienteCeldaId(new Long(siguienteCeldaId));
					graficoForm.setVista(vista);
					graficoForm.setPagina(pagina);
					graficoForm.setCelda(celda);
				}
			}
		  
			
				
				String res = "";
				res = new com.visiongc.app.strategos.web.struts.graficos.actions.SeleccionarGraficoAction().ReadXmlProperties(res, grafico);
				graficoForm.setRespuesta(res);
			
				GetObjeto(graficoForm, grafico); 
				
				graficoForm.setObjetoId(grafico.getObjetoId());
				graficoForm.setClassName(grafico.getClassName());
				
												
			
			
		}		// reporte grafico procesos		
		else
		{
			if (graficoForm.getVirtual() == null)
				graficoForm.setVirtual(false);
			  
			if (!graficoForm.getVirtual())
				ReadGrafico(graficoId, graficoForm, request);
			else
			{
				Grafico grafico = new Grafico();
				  
				grafico.setGraficoId(Long.parseLong(request.getParameter("id")));
				grafico.setNombre(request.getParameter("nombre"));
				String data = "";
				if (request.getParameter("data") != null)
					data = request.getParameter("data").replace("[[num]]", "#").replace("[[por]]", "%");
				else if (request.getSession().getAttribute("configuracionGrafico") != null)
					data = (String) request.getSession().getAttribute("configuracionGrafico");
				grafico.setConfiguracion(data);
				  
				String res = "";
				res = new com.visiongc.app.strategos.web.struts.graficos.actions.SeleccionarGraficoAction().ReadXmlProperties(res, grafico);
				graficoForm.setRespuesta(res);
	
				GetObjeto(graficoForm, grafico);
			}
		}
		  
		graficoForm.setFrecuencias(Frecuencia.getFrecuencias());
		graficoForm.setTiposSerie(TipoSerieGrafico.getTiposSerie());

		boolean verForm = false;
		boolean editarForm = false;
		
		if (graficoForm.getSource().equals("Celda") || graficoForm.getSource().equals("Portafolio"))
		{
			verForm = getPermisologiaUsuario(request).tienePermiso("VISTA_VIEWALL");
			editarForm = getPermisologiaUsuario(request).tienePermiso("VISTA_EDIT");
		}
		else
		{
			verForm = getPermisologiaUsuario(request).tienePermiso("INDICADOR_VIEWALL");
			editarForm = getPermisologiaUsuario(request).tienePermiso("INDICADOR_EDIT");
		}
		
		if (!editarForm)
			editarForm = getPermisologiaUsuario(request).tienePermiso("INDICADOR_EVALUAR_GRAFICO_GRAFICO");

		if (verForm && !editarForm)
			graficoForm.setBloqueado(true);
		  
		GetGrafico(graficoForm, request);

		Calendar fecha = PeriodoUtil.getDateByPeriodo(graficoForm.getFrecuencia(), Integer.parseInt(graficoForm.getAnoInicial()), graficoForm.getPeriodoInicial(), true);
		graficoForm.setFechaDesde(VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta"));

		fecha = PeriodoUtil.getDateByPeriodo(graficoForm.getFrecuencia(), Integer.parseInt(graficoForm.getAnoFinal()), graficoForm.getPeriodoFinal(), false);
		graficoForm.setFechaHasta(VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta"));

		if (graficoForm.getSeries() != null && graficoForm.getSeries().size() == 0)
			graficoForm.getSeries().add(new DatosSerie());

		strategosCeldasService.close();
		strategosGraficosService.close();
		strategosIndicadoresService.close();
		strategosVistasService.close();
		strategosPaginasService.close();
		strategosPortafoliosService.close();
		strategosMedicionesService.close();
		strategosIniciativasService.close();
		
		return mapping.findForward(forward);
	}
	  
	private void getData(GraficoForm graficoForm)
	{
		getData(graficoForm, false, false, true, null);
	}
	  
	private void getData(GraficoForm graficoForm, Boolean includeValorLabel, Boolean includePorcentajeLabel, Boolean includeValorSerie, Long includeIndicadorId)
	{
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		
		double totalValor = 0D;
		String ultimoPeriodo = null;
		Long id = 0L;
		Indicador indicador = null;
		
		SerieUtil serieGrafico = new SerieUtil();
		Long indicadorId = 0L;
		graficoForm.setMostrarCondicion(true);
		for (Iterator<DatosSerie> i = graficoForm.getSeries().iterator(); i.hasNext(); )
		{
			DatosSerie serie = (DatosSerie)i.next();
			if (includeIndicadorId != null && includeIndicadorId.longValue() != serie.getIndicador().getIndicadorId())
				continue;

			boolean hayIndicador = true;
			if (serie.getIndicador() == null)
			{
				hayIndicador = false;
				indicador = null;
			}
			if (indicadorId.longValue() == 0L && hayIndicador)
				indicadorId = serie.getIndicador().getIndicadorId();

			if (hayIndicador && serie.getIndicador().getIndicadorId() != null)
				indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, serie.getIndicador().getIndicadorId());
			
			if (indicador != null)
			{
				if (graficoForm.getIndicadores() != null && !graficoForm.getIndicadores().contains(indicador))
					graficoForm.getIndicadores().add(indicador);
				else
				{
					graficoForm.setIndicadores(new ArrayList<Indicador>());
					graficoForm.getIndicadores().add(indicador);
				}
			}
			
			if (hayIndicador && indicadorId.longValue() != serie.getIndicador().getIndicadorId().longValue())
			{
				graficoForm.setMostrarCondicion(false);
				graficoForm.setCondicion(false);
			}

			List<Medicion> mediciones = new ArrayList<Medicion>();
			List<MetaAnualParciales> metas = new ArrayList<MetaAnualParciales>();
			Long planId = 0L;
			if (serie.getPlanId() != null && serie.getPlanId().longValue() != 0L)
				planId = serie.getPlanId();

			boolean acumular = (hayIndicador && serie.getIndicador().getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && serie.getIndicador().getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue());
			Integer anoInicial = Integer.parseInt(graficoForm.getAnoInicial());
			Integer anoFinal = Integer.parseInt(graficoForm.getAnoFinal());
			Integer periodoInicial = Integer.parseInt(graficoForm.getPeriodoInicial().toString());
			Integer periodoFinal = Integer.parseInt(graficoForm.getPeriodoFinal().toString());
			if (serie.getSerieAnoAnterior() != null && serie.getSerieAnoAnterior())
			{
				anoInicial = anoInicial - 1;
				anoFinal = anoFinal - 1;
			}
			
			Medicion medicion = new Medicion();
			Meta meta = new Meta();
			Meta metaReal = null;
			MetaAnualParciales metaAnualParciales = new MetaAnualParciales();
			Boolean buscandoMedicion = false;
			
			if (hayIndicador)
			{
				StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
				metas = (List<MetaAnualParciales>) strategosMetasService.getMetasAnualesParciales(serie.getIndicador().getIndicadorId(), planId, graficoForm.getFrecuencia(), anoInicial, anoFinal, graficoForm.getAcumular());
				strategosMetasService.close();
			}
			
			if (hayIndicador && !serie.getSerieIndicador().getPk().getSerieId().equals(SerieTiempo.getSerieMetaId()) && !serie.getSerieIndicador().getPk().getSerieId().equals(SerieTiempo.getSerieValorInicialId()))
			{
				buscandoMedicion = true;
				mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(serie.getIndicador().getIndicadorId(), serie.getSerieIndicador().getPk().getSerieId(), anoInicial, anoFinal, periodoInicial, periodoFinal, graficoForm.getFrecuenciaAgrupada(), graficoForm.getFrecuencia(), acumular, graficoForm.getAcumular());
				if (serie.getSerieAnoAnterior() != null && serie.getSerieAnoAnterior())
				{
					for (int indexMedicion = mediciones.size() - 1; indexMedicion >= 0; indexMedicion--)
					{
						medicion = (Medicion)mediciones.get(indexMedicion);
						medicion.getMedicionId().setAno(medicion.getMedicionId().getAno()+1);
					}
				}
			}
			else if (hayIndicador)
			{
				buscandoMedicion = false;
				if (serie.getSerieAnoAnterior() != null && serie.getSerieAnoAnterior())
				{
					for (int indexMedicion = metas.size() - 1; indexMedicion >= 0; indexMedicion--) 
					{
						metaAnualParciales = (MetaAnualParciales)metas.get(indexMedicion);
						
						for (int indexMeta = metaAnualParciales.getMetasParciales().size() - 1; indexMeta >= 0; indexMeta--)
						{
							meta = (Meta)metaAnualParciales.getMetasParciales().get(indexMeta);
							meta.getMetaId().setAno(meta.getMetaId().getAno() + 1);
						}
					}
				}
			}

			AnoPeriodo periodo = new AnoPeriodo();
			medicion = new Medicion();
			meta = new Meta();
			metaAnualParciales = new MetaAnualParciales();
			Double valor = null;
			for (int index = 0; index < graficoForm.getAnosPeriodos().size(); index++)
			{
				periodo = (AnoPeriodo)graficoForm.getAnosPeriodos().get(index);
				valor = null;
				totalValor = 0;
				for (int indexMedicion = mediciones.size() - 1; indexMedicion >= 0; indexMedicion--) 
				{
					medicion = (Medicion)mediciones.get(indexMedicion);
				  	totalValor = totalValor + (medicion.getValor() != null ? medicion.getValor() : 0); 
				  	if (medicion.getMedicionId().getAno().intValue() == periodo.getAno().intValue() && medicion.getMedicionId().getPeriodo().intValue() == periodo.getPeriodo().intValue())
				  	{
			  			valor = medicion.getValor();
			  			if (valor != null)
			  				ultimoPeriodo = medicion.getMedicionId().getAno().toString() + "-" + medicion.getMedicionId().getPeriodo().toString(); 
				  	}
				}

				if (!buscandoMedicion)
				{
					for (int indexMedicion = metas.size() - 1; indexMedicion >= 0; indexMedicion--) 
					{
						metaAnualParciales = (MetaAnualParciales)metas.get(indexMedicion);
						
						for (int indexMeta = metaAnualParciales.getMetasParciales().size() - 1; indexMeta >= 0; indexMeta--)
						{
							meta = (Meta)metaAnualParciales.getMetasParciales().get(indexMeta);
							
						  	totalValor = totalValor + (meta.getValor() != null ? meta.getValor() : 0); 
						  	if (meta.getMetaId().getAno().intValue() == periodo.getAno().intValue() && meta.getMetaId().getPeriodo().intValue() == periodo.getPeriodo().intValue())
						  	{
						  		metaReal = meta;
					  			valor = meta.getValor();
						  	}
						}
					}
				}
				
				if (serie.getVisualizar())
				{
					if (includeValorSerie && hayIndicador)
					{
						serieGrafico = new SerieUtil();
						serieGrafico.setSerieId(serie.getSerieIndicador().getPk().getSerieId());
						serieGrafico.setIndicadorId(serie.getIndicador().getIndicadorId());
						serieGrafico.setValor(valor);
						serieGrafico.setSerieAnoAnterior(serie.getSerieAnoAnterior());
					  
						periodo.getSeries().add(serieGrafico);
					}
				}

				if (serie.getVisualizar().booleanValue() && graficoForm.getCondicion().booleanValue() && hayIndicador)
				{
					Double ejecutado = null;
					Double programado = null;
					Double programadoInferior = null;
					Double ejecutadoAnterior = null;
					Medicion medicionReal = null;
					Medicion medicionProgramado = null;
					Medicion medicionProgramadoInferior = null;
					List<Medicion> medicionesReales = new ArrayList<Medicion>();
					List<Medicion> medicionesProgramadas = new ArrayList<Medicion>();
					if (planId != 0 && metaReal == null)
					{
						for (int indexMedicion = metas.size() - 1; indexMedicion >= 0; indexMedicion--) 
						{
							metaAnualParciales = (MetaAnualParciales)metas.get(indexMedicion);
							
							for (int indexMeta = metaAnualParciales.getMetasParciales().size() - 1; indexMeta >= 0; indexMeta--)
							{
								meta = (Meta)metaAnualParciales.getMetasParciales().get(indexMeta);
							  	if (meta.getMetaId().getAno().intValue() == periodo.getAno().intValue() && meta.getMetaId().getPeriodo().intValue() == periodo.getPeriodo().intValue())
							  	{
							  		metaReal = meta;
							  		break;
							  	}
							}
							if (metaReal != null)
								break;
						}
					}
					
					if (serie.getSerieIndicador().getPk().getSerieId().byteValue() == SerieTiempo.getSerieRealId().byteValue())
					{
						medicionReal = new Medicion(new MedicionPK(indicadorId, periodo.getAno(), periodo.getPeriodo(), SerieTiempo.getSerieRealId()), valor, new Boolean(false));
						if (metaReal == null || (metaReal != null && metaReal.getValor() == null))
						{
							if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue())
							{
								if (!acumular)
								{
									medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieMaximoId(), periodo.getAno(), periodo.getAno(), periodo.getPeriodo(), periodo.getPeriodo(), null);
									medicionProgramado = medicionesProgramadas.size() > 0 ? medicionesProgramadas.get(0) : null;
								}
								else
								{
									medicionesProgramadas = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicadorId, SerieTiempo.getSerieMaximoId(), anoInicial, periodo.getAno(), periodoInicial, periodo.getPeriodo(), graficoForm.getFrecuenciaAgrupada(), graficoForm.getFrecuencia(), acumular, graficoForm.getAcumular());
									medicionProgramado = medicionesProgramadas.size() > 0 ? medicionesProgramadas.get((medicionesProgramadas.size() - 1)) : null;
								}
							}
							else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue())
							{
								if (!acumular)
								{
									medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieMinimoId(), periodo.getAno(), periodo.getAno(), periodo.getPeriodo(), periodo.getPeriodo(), null);
									medicionProgramado = medicionesProgramadas.size() > 0 ? medicionesProgramadas.get(0) : null;
								}
								else
								{
									medicionesProgramadas = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicadorId, SerieTiempo.getSerieMinimoId(), anoInicial, periodo.getAno(), periodoInicial, periodo.getPeriodo(), graficoForm.getFrecuenciaAgrupada(), graficoForm.getFrecuencia(), acumular, graficoForm.getAcumular());
									medicionProgramado = medicionesProgramadas.size() > 0 ? medicionesProgramadas.get((medicionesProgramadas.size() - 1)) : null;
								}
							}
							else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue())
							{
								if (!acumular)
								{
									medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieMaximoId(), periodo.getAno(), periodo.getAno(), periodo.getPeriodo(), periodo.getPeriodo(), null);
									medicionProgramado = medicionesProgramadas.size() > 0 ? medicionesProgramadas.get(0) : null;
									medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieMinimoId(), periodo.getAno(), periodo.getAno(), periodo.getPeriodo(), periodo.getPeriodo(), null);
									medicionProgramadoInferior = medicionesProgramadas.size() > 0 ? medicionesProgramadas.get(0) : null;
								}
								else
								{
									medicionesProgramadas = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicadorId, SerieTiempo.getSerieMaximoId(), anoInicial, periodo.getAno(), periodoInicial, periodo.getPeriodo(), graficoForm.getFrecuenciaAgrupada(), graficoForm.getFrecuencia(), acumular, graficoForm.getAcumular());
									medicionProgramado = medicionesProgramadas.size() > 0 ? medicionesProgramadas.get((medicionesProgramadas.size() - 1)) : null;
									medicionesProgramadas = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicadorId, SerieTiempo.getSerieMinimoId(), anoInicial, periodo.getAno(), periodoInicial, periodo.getPeriodo(), graficoForm.getFrecuenciaAgrupada(), graficoForm.getFrecuencia(), acumular, graficoForm.getAcumular());
									medicionProgramadoInferior = medicionesProgramadas.size() > 0 ? medicionesProgramadas.get((medicionesProgramadas.size() - 1)) : null;
								}
							}
							else
							{
								if (!acumular)
								{
									medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieProgramadoId(), periodo.getAno(), periodo.getAno(), periodo.getPeriodo(), periodo.getPeriodo(), null);
									medicionProgramado = medicionesProgramadas.size() > 0 ? medicionesProgramadas.get(0) : null;
								}
								else
								{
									medicionesProgramadas = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicadorId, SerieTiempo.getSerieProgramadoId(), anoInicial, periodo.getAno(), periodoInicial, periodo.getPeriodo(), graficoForm.getFrecuenciaAgrupada(), graficoForm.getFrecuencia(), acumular, graficoForm.getAcumular());
									medicionProgramado = medicionesProgramadas.size() > 0 ? medicionesProgramadas.get((medicionesProgramadas.size() - 1)) : null;
								}
							}
						}
					}
					else
					{
						if (!acumular)
						{
							medicionesReales = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieRealId(), periodo.getAno(), periodo.getAno(), periodo.getPeriodo(), periodo.getPeriodo(), null);
							medicionReal = medicionesReales.size() > 0 ? medicionesReales.get(0) : null;
							if (metaReal == null || (metaReal != null && metaReal.getValor() == null))
								medicionProgramado = new Medicion(new MedicionPK(indicadorId, periodo.getAno(), periodo.getPeriodo(), serie.getSerieIndicador().getPk().getSerieId()), valor, new Boolean(false));						
						}
						else
						{
							medicionesReales = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicadorId, SerieTiempo.getSerieRealId(), anoInicial, periodo.getAno(), periodoInicial, periodo.getPeriodo(), graficoForm.getFrecuenciaAgrupada(), graficoForm.getFrecuencia(), acumular, graficoForm.getAcumular());
							medicionReal = medicionesReales.size() > 0 ? medicionesReales.get(0) : null;
							if (metaReal == null || (metaReal != null && metaReal.getValor() == null))
								medicionProgramado = new Medicion(new MedicionPK(indicadorId, periodo.getAno(), periodo.getPeriodo(), serie.getSerieIndicador().getPk().getSerieId()), valor, new Boolean(false));						
						}
					}

					if (medicionReal != null && medicionReal.getValor() != null)
					{
						ejecutado = medicionReal.getValor();
						if (medicionProgramado != null && medicionProgramado.getValor() != null)
							programado = medicionProgramado.getValor();
						if (medicionProgramadoInferior != null && medicionProgramadoInferior.getValor() != null)
							programadoInferior = medicionProgramadoInferior.getValor();
						if (metaReal != null && metaReal.getValor() != null)
						{
							programado = metaReal.getValor();
							metaReal = null;
						}
						
						if (programado == null)
						{
	  						int anoAnterior = periodo.getAno();
	  						int periodoAnterior = periodo.getPeriodo();
	  						int numeroMaximoPorPeriodo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), anoAnterior);
	  						if (periodoAnterior == 1)
	  						{
	  							anoAnterior = anoAnterior - 1;
	  							periodoAnterior = numeroMaximoPorPeriodo;
	  						}
	  						else
	  							periodoAnterior = periodoAnterior - 1;
	  						List<Medicion> medicionesEjecutadoAnterior = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieRealId(), anoAnterior, anoAnterior, periodoAnterior, periodoAnterior, null);
	  						if (medicionesEjecutadoAnterior.size() > 0)
	  							ejecutadoAnterior = medicionesEjecutadoAnterior.get(0).getValor();
						}
					}
					
					Byte alerta = AlertaIndicador.getAlertaNoDefinible();
					if (metaReal == null)
					{
						boolean hayProgramado = false;
						for (Iterator<SerieIndicador> j = indicador.getSeriesIndicador().iterator(); j.hasNext(); )
						{
							SerieIndicador ser = (SerieIndicador)j.next();
							if (ser.getPk().getSerieId().longValue() == SerieTiempo.getSerieProgramado().getSerieId().longValue())
							{
								hayProgramado = true;
								break;
							}
						}
						if (hayProgramado)
							ejecutadoAnterior = null;
					}
					
					if (ejecutado != null && indicador != null && (programado != null || ejecutadoAnterior != null))
					{
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, periodo.getAno(), periodo.getPeriodo(), (programado != null ? programado : ejecutadoAnterior), strategosMedicionesService);
	  					Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, periodo.getAno(),periodo.getPeriodo(), (programado != null ? programado : ejecutadoAnterior), zonaVerde, strategosMedicionesService);
	  					alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, ejecutado, programado, programadoInferior, ejecutadoAnterior);
					}
					
					if (serie.getSerieIndicador().getPk().getSerieId().byteValue() == SerieTiempo.getSerieRealId().byteValue())
					{
						DatosAlerta datosAlerta = new DatosAlerta();
		  				datosAlerta.setAlerta(alerta);
						periodo.setAlerta(datosAlerta);
					}
				}
			}
			
			id = id + 1L;
			serie.setId(id);
		}
		
		if (graficoForm.getIndicadores() != null && graficoForm.getIndicadores().size() == 1)
			graficoForm.setIndicadores(null);
	  
		graficoForm.setUltimoPeriodo(ultimoPeriodo);
		strategosIndicadoresService.close();
		strategosMedicionesService.close();
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
			graficoForm.setUbicacionClase(clases.substring(1, (clases.length())));
			
			leyenda = leyenda + clases;
		}
		
		if (leyenda.equals(""))
			leyenda = leyenda + serie.getNombreLeyenda();
		else
			leyenda = leyenda + "/" + serie.getNombreLeyenda();
		
		boolean exist = true;
		int correlativo = 1;
		while (exist) 
		{
			if (correlativo == 1)
				exist = existeLeyenda(graficoForm, leyenda);
			else
				exist = existeLeyenda(graficoForm, (correlativo + " " + leyenda));
			
			if (exist)
				correlativo++;
			else if (correlativo > 1)
				leyenda = correlativo + " " + leyenda;
		}
		
		return leyenda;
	}
	
	private boolean existeLeyenda(GraficoForm graficoForm, String leyenda)
	{
		for (Iterator<DatosSerie> i = graficoForm.getSeries().iterator(); i.hasNext(); )
		{
			DatosSerie insumo = (DatosSerie)i.next();
			if (insumo.getPathClase() != null && insumo.getPathClase().equalsIgnoreCase(leyenda))
				return true;
		}
		
		return false;
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
	  
	public ActionForm ReadGrafico(Long id, GraficoForm graficoForm, HttpServletRequest request) throws ParserConfigurationException, SAXException, IOException
	{
		StrategosGraficosService strategosGraficosService = StrategosServiceFactory.getInstance().openStrategosGraficosService();
		Grafico grafico = new Grafico();
	  
		String res = "";
		res = new com.visiongc.app.strategos.web.struts.graficos.actions.SeleccionarGraficoAction().ReadXmlProperties(res, grafico);
		graficoForm.setRespuesta(res);
		graficoForm = (GraficoForm) GetObjeto(graficoForm, grafico);

		strategosGraficosService.close();

		return graficoForm; 
	}
	
	public ActionForm ReadCelda(Long id, GraficoForm graficoForm, HttpServletRequest request) throws ParserConfigurationException, SAXException, IOException
	{
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		Celda celda = (Celda)strategosIndicadoresService.load(Celda.class, new Long(id));
		
		Grafico grafico = new Grafico();
		grafico.setGraficoId(celda.getCeldaId());
		grafico.setNombre(celda.getTitulo());
	  
		graficoForm = (GraficoForm) GetObjeto(graficoForm, grafico);

		strategosIndicadoresService.close();

		return graficoForm; 
	}
	  
	public ActionForm GetObjeto(GraficoForm graficoForm, Grafico grafico) throws ParserConfigurationException, SAXException, IOException
	{
		if (grafico != null)
		{
	    	graficoForm.setVirtual(true);
	    	graficoForm.setId(grafico.getGraficoId());
	    	graficoForm.setGraficoNombre(grafico.getNombre());
	    	Boolean nombreTitulo = false;
	    	Long claseComparativaId = 0L;
	    	
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
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setTipo(Byte.parseByte((valor.getNodeValue())));
				}

				if (elemento.getElementsByTagName("titulo").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("titulo").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setTitulo(valor.getNodeValue());
				}

				if (elemento.getElementsByTagName("tituloEjeY").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("tituloEjeY").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setTituloEjeY(valor.getNodeValue());
				}
				
				if (elemento.getElementsByTagName("tituloEjeZ").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("tituloEjeZ").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setTituloEjeZ(valor.getNodeValue());
				}

				if (elemento.getElementsByTagName("tituloEjeX").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("tituloEjeX").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setTituloEjeX(valor.getNodeValue());
				}

				if (elemento.getElementsByTagName("anoInicial").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("anoInicial").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setAnoInicial(valor.getNodeValue());
				}

				if (elemento.getElementsByTagName("periodoInicial").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("periodoInicial").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setPeriodoInicial(Integer.parseInt(valor.getNodeValue()));
				}

				if (elemento.getElementsByTagName("anoFinal").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("anoFinal").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setAnoFinal(valor.getNodeValue());
				}

				if (elemento.getElementsByTagName("periodoFinal").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("periodoFinal").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setPeriodoFinal(Integer.parseInt(valor.getNodeValue()));
				}

				if (elemento.getElementsByTagName("frecuencia").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("frecuencia").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setFrecuencia(Byte.parseByte(valor.getNodeValue()));
					else
						graficoForm.setFrecuencia((byte) 3);
				}
				graficoForm.setFrecuenciaNombre(Frecuencia.getNombre(graficoForm.getFrecuencia()));

				if (elemento.getElementsByTagName("frecuenciaAgrupada").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("frecuenciaAgrupada").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setFrecuenciaAgrupada(Byte.parseByte(valor.getNodeValue()));
					else
						graficoForm.setFrecuenciaAgrupada((byte) 3);
				}
				
				if (elemento.getElementsByTagName("condicion").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("condicion").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setCondicion(valor.getNodeValue() == "1" || Integer.parseInt(valor.getNodeValue()) == 1 ? true : false);
					else
						graficoForm.setCondicion(false);
				}
				
				if (elemento.getElementsByTagName("verTituloImprimir").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("verTituloImprimir").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setVerTituloImprimir(valor.getNodeValue() == "1" || Integer.parseInt(valor.getNodeValue()) == 1 ? true : false);
					else
						graficoForm.setVerTituloImprimir(false);
				}
				
				if (elemento.getElementsByTagName("ajustarEscala").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("ajustarEscala").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setAjustarEscala(valor.getNodeValue() == "1" || Integer.parseInt(valor.getNodeValue()) == 1 ? true : false);
					else
						graficoForm.setAjustarEscala(false);
				}
				else
					graficoForm.setAjustarEscala(false);

				if (elemento.getElementsByTagName("acumular").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("acumular").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setAcumular(valor.getNodeValue() == "1" || Integer.parseInt(valor.getNodeValue()) == 1 ? true : false);
					else
						graficoForm.setAcumular(null);
				}
				else
					graficoForm.setAcumular(null);

				if (elemento.getElementsByTagName("impVsAnoAnterior").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("impVsAnoAnterior").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					
					if (valor != null && !valor.getNodeValue().equals(""))
						graficoForm.setImpVsAnoAnterior(valor.getNodeValue() == "1" || Integer.parseInt(valor.getNodeValue()) == 1 ? true : false);
					else
						graficoForm.setImpVsAnoAnterior(false);
				}
				else
					graficoForm.setImpVsAnoAnterior(false);
			}
			
			lista = doc.getElementsByTagName("indicador");
			if (lista.getLength() > 0)
			{
				StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

				StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService(strategosIndicadoresService);
				List<?> seriesTiempo = strategosSeriesTiempoService.getSeriesTiempo(0, 0, "serieId", null, false, null).getLista();
				strategosSeriesTiempoService.close();
				
				String nombreLeyenda = "";
				Long indicadorId = 0L;
				for (int i = 0; i < lista.getLength() ; i ++) 
				{
					Node node = lista.item(i);
					Element elemento = (Element) node;
					NodeList nodeLista = null;
					Node valor = null;
					Indicador indicador = new Indicador();
					
					if (elemento.getElementsByTagName("id").getLength() > 0)
					{
						nodeLista = elemento.getElementsByTagName("id").item(0).getChildNodes();
						valor = (Node) nodeLista.item(0);
						if (valor != null && !valor.getNodeValue().equals(""))
						{
							indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(valor.getNodeValue()));
							if (indicador != null)
							{
								DatosSerie datosSerie = null;
								indicador.setSeriesIndicador(new HashSet<SerieIndicador>());
								indicador.getSeriesIndicador().clear();
								if (claseComparativaId == 0L)
									claseComparativaId = indicador.getClaseId();
								if (indicadorId != null && indicadorId == 0L)
									indicadorId = indicador.getIndicadorId();
								if (indicadorId != null && indicadorId.longValue() != indicador.getIndicadorId().longValue())
									indicadorId = null;
								
								if (elemento.getElementsByTagName("serie").getLength() > 0)
								{
									datosSerie = new DatosSerie();
									
									nodeLista = elemento.getElementsByTagName("serie").item(0).getChildNodes();
									valor = (Node) nodeLista.item(0);
	
									SerieIndicador serieIndicador = new SerieIndicador();
							        serieIndicador.setPk(new SerieIndicadorPK());
							        serieIndicador.getPk().setSerieId(new Long(valor.getNodeValue()));
							        boolean serieEncontrada = false;
							        Long planId = null;
							        Integer tipoGrafico = -1; 
							        Boolean visualizar = true;
							        String color = null;
							        String colorDecimal = null;
							        String colorEntero = null;
							        
						            for (Iterator<?> j = seriesTiempo.iterator(); j.hasNext(); ) 
						            {
						            	SerieTiempo serie = (SerieTiempo)j.next();
	
						            	if (serie.getSerieId().byteValue() == Long.parseLong(valor.getNodeValue())) 
						            	{
						            		serieIndicador.setNombre(serie.getNombre());
						            		nombreLeyenda = indicador.getNombre() + " - " + serie.getNombre();
						            		serieEncontrada = true;
						            		break;
						            	}
						            }
							        
									if (!serieEncontrada && SerieTiempo.getSerieMetaId().byteValue() == Long.parseLong(valor.getNodeValue()))
									{
					            		serieIndicador.setNombre(SerieTiempo.getSerieMeta().getNombre());
					            		nombreLeyenda = indicador.getNombre() + " - " + SerieTiempo.getSerieMeta().getNombre();
									}
						            
							        datosSerie.setSerieIndicador(serieIndicador);
							        datosSerie.setIndicador(indicador);
							        
							        if (elemento.getElementsByTagName("planId").getLength() > 0)
							        {
										nodeLista = elemento.getElementsByTagName("planId").item(0).getChildNodes();
										valor = (Node) nodeLista.item(0);
										if (valor != null && !valor.getNodeValue().equals(""))
										{
											Perspectiva perspectiva = (Perspectiva)strategosIndicadoresService.load(Perspectiva.class, new Long(valor.getNodeValue()));
											if (perspectiva != null)
											{
												planId = perspectiva.getPlanId();
												datosSerie.setPlanId(planId);
											}
											else
											{
												planId = new Long(valor.getNodeValue());
												datosSerie.setPlanId(planId);
											}
										}
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
	
							        if (elemento.getElementsByTagName("tipoGrafico").getLength() > 0)
							        {
										nodeLista = elemento.getElementsByTagName("tipoGrafico").item(0).getChildNodes();
										valor = (Node) nodeLista.item(0);
										if (valor != null && !valor.getNodeValue().equals(""))
										{
											tipoGrafico = Integer.parseInt(valor.getNodeValue());
											datosSerie.setTipoSerie(tipoGrafico);
										}
										else
											datosSerie.setTipoSerie(-1);
							        }
							        else
							        	datosSerie.setTipoSerie(-1);
	
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

							        if (elemento.getElementsByTagName("showOrganizacion").getLength() > 0)
							        {
										nodeLista = elemento.getElementsByTagName("showOrganizacion").item(0).getChildNodes();
										valor = (Node) nodeLista.item(0);
										if (valor != null && !valor.getNodeValue().equals(""))
											datosSerie.setShowOrganizacion(valor.getNodeValue() == "1" || Integer.parseInt(valor.getNodeValue()) == 1 ? true : false);
										else
											datosSerie.setShowOrganizacion(false);
							        }
							        else
							        	datosSerie.setShowOrganizacion(false);

							        if (elemento.getElementsByTagName("nivelClase").getLength() > 0)
							        {
										nodeLista = elemento.getElementsByTagName("nivelClase").item(0).getChildNodes();
										valor = (Node) nodeLista.item(0);
										if (valor != null && !valor.getNodeValue().equals(""))
											datosSerie.setNivelClase(valor.getNodeValue() != "" ? Integer.parseInt(valor.getNodeValue()) : null);
										else
											datosSerie.setNivelClase(null);
							        }
							        else
							        	datosSerie.setNivelClase(null);
							        datosSerie.setPathClase(getNombreLeyenda(graficoForm, datosSerie));
							        nombreTitulo = ((graficoForm.getUbicacionOrganizacion() != null && !graficoForm.getUbicacionOrganizacion().equals("")) || (graficoForm.getUbicacionClase() != null && !graficoForm.getUbicacionClase().equals(""))); 
							        if (claseComparativaId.longValue() != indicador.getClaseId().longValue())
							        	nombreTitulo = false;
							        
							        datosSerie.setBloquear(false);
							        datosSerie.setSerieAnoAnterior(false);

							        graficoForm.getSeries().add(datosSerie);
							        
							        if (graficoForm.getImpVsAnoAnterior())
							        {
							        	datosSerie = new DatosSerie();

										datosSerie.setSerieIndicador(serieIndicador);
								        datosSerie.setIndicador(indicador);
								        datosSerie.setPlanId(planId);
								        datosSerie.setNombreLeyenda(nombreLeyenda + " Año Anterior");

								        color = ColorUtil.getRndColorRGB();
							        	colorDecimal = ColorUtil.convertRGBDecimal(color);
							        	colorEntero = colorDecimal;
								        datosSerie.setColor(color);
							        	datosSerie.setColorDecimal(colorDecimal);
								        datosSerie.setColorEntero(colorEntero);

										datosSerie.setTipoSerie(tipoGrafico);
							        	datosSerie.setVisualizar(visualizar);
								        datosSerie.setBloquear(true);
								        datosSerie.setSerieAnoAnterior(true);
								        datosSerie.setPathClase(getNombreLeyenda(graficoForm, datosSerie));
							        	
							        	graficoForm.getSeries().add(datosSerie);
							        }
								}
							}
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
							datosSerie.setShowOrganizacion(false);
							datosSerie.setNivelClase(null);
							datosSerie.setPathClase("");
							datosSerie.setBloquear(false);
							datosSerie.setSerieAnoAnterior(false);
							
							graficoForm.getSeries().add(datosSerie);
						}
					}
				}
				
				if (indicadorId != null)
				{
					Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(indicadorId));
					if (indicador != null)
					{
						graficoForm.setIndicadorId(indicador.getIndicadorId());
						if (indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaFormula().byteValue())
							graficoForm.setShowDuppont(true);						
						if (graficoForm.getAcumular() == null)
							graficoForm.setAcumular(indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue());
					}
				}
				if (graficoForm.getAcumular() == null)
					graficoForm.setAcumular(false);
				
				strategosIndicadoresService.close();
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
  
	public void GetGrafico(GraficoForm graficoForm, HttpServletRequest request) throws IOException
	{
		int numeroMaximoPeriodos = 0;
		if ((Integer.parseInt(graficoForm.getAnoInicial()) % 4 == 0) && (graficoForm.getFrecuenciaAgrupada().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
			numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(graficoForm.getFrecuenciaAgrupada().byteValue(), Integer.parseInt(graficoForm.getAnoInicial()));
		else if ((Integer.parseInt(graficoForm.getAnoFinal()) % 4 == 0) && (graficoForm.getFrecuenciaAgrupada().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
			numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(graficoForm.getFrecuenciaAgrupada().byteValue(), Integer.parseInt(graficoForm.getAnoFinal()));
		else 
			numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(graficoForm.getFrecuenciaAgrupada().byteValue(), Integer.parseInt(graficoForm.getAnoInicial()));

		graficoForm.setNumeroMaximoPeriodos(numeroMaximoPeriodos);
	  
		Integer anoInicial = Integer.parseInt(graficoForm.getAnoInicial());
		Integer anoFinal = Integer.parseInt(graficoForm.getAnoFinal());
		Integer periodoInicial = Integer.parseInt(graficoForm.getPeriodoInicial().toString());
		Integer periodoFinal = Integer.parseInt(graficoForm.getPeriodoFinal().toString());
		
		List<AnoPeriodo> listaAnosPeriodos = AnoPeriodo.getListaAnosPeriodos(anoInicial, anoFinal, periodoInicial, periodoFinal, graficoForm.getNumeroMaximoPeriodos().intValue());
		graficoForm.setAnosPeriodos(listaAnosPeriodos);
		graficoForm.setShowImage(false);
		
		getData(graficoForm);
		
		StringBuffer sbEjeX = new StringBuffer();
		boolean firsOne = true;
		AnoPeriodo periodoAno = null;
		for (int index = 0; index < graficoForm.getAnosPeriodos().size(); index++)
		{
			AnoPeriodo periodo = (AnoPeriodo)graficoForm.getAnosPeriodos().get(index);
			periodoAno = periodo;
			
			if (!firsOne)
				sbEjeX.append(",");
			sbEjeX.append(periodo.getNombre());
			firsOne = false;
		}

		StringBuffer sbSerieName = new StringBuffer();
		StringBuffer sbSerieData = new StringBuffer();
		StringBuffer sbSerieColor = new StringBuffer();
		StringBuffer sbSerieTipo = new StringBuffer();
		firsOne = true;
		String format = "##0.00";
		if (graficoForm.getTipo().byteValue() == GraficoTipo.getTipoGauge().byteValue())
		{
			String seriePrimera = null;
			for (Iterator<DatosSerie> i = graficoForm.getSeries().iterator(); i.hasNext(); )
			{
				DatosSerie serie = (DatosSerie)i.next();
				if (serie.getVisualizar() && serie.getSerieIndicador().getPk().getSerieId().equals(SerieTiempo.getSerieRealId()))
				{
					seriePrimera = serie.getIndicador().getIndicadorId().toString() + '-' + SerieTiempo.getSerieRealId().toString();
					break;
				}
			}
			
			if (seriePrimera == null)
			{
				for (Iterator<DatosSerie> i = graficoForm.getSeries().iterator(); i.hasNext(); )
				{
					DatosSerie serie = (DatosSerie)i.next();
					if (serie.getVisualizar() && !serie.getSerieIndicador().getPk().getSerieId().equals(SerieTiempo.getSerieMetaId()))
					{
						seriePrimera = serie.getIndicador().getIndicadorId().toString() + '-' + serie.getSerieIndicador().getPk().getSerieId().toString();
						break;
					}
				}
			}
			
			Double ejecutado = null;
			Indicador indicador = null;
			for (Iterator<DatosSerie> i = graficoForm.getSeries().iterator(); i.hasNext(); )
			{
				DatosSerie serie = (DatosSerie)i.next();
				String id = serie.getIndicador().getIndicadorId().toString() + '-' + serie.getSerieIndicador().getPk().getSerieId().toString();
				if (serie.getVisualizar() && id.equals(seriePrimera))
				{
					if (serie.getIndicador() != null)
						graficoForm.setShowImage(true);
					
					if (!firsOne)
					{
						sbSerieName.append(graficoForm.getSeparadorSeries());
						sbSerieData.append(graficoForm.getSeparadorSeries());
					}
					sbSerieName.append(serie.getNombreLeyenda());

					boolean firsItem = true;
					for (int index = 0; index < graficoForm.getAnosPeriodos().size(); index++)
					{
						AnoPeriodo periodo = (AnoPeriodo)graficoForm.getAnosPeriodos().get(index);
						for (int indexSerie = 0; indexSerie < periodo.getSeries().size(); indexSerie++)
						{
							SerieUtil serieGrafico = (SerieUtil)periodo.getSeries().get(indexSerie);
							if (serieGrafico.getSerieId().longValue() == serie.getSerieIndicador().getPk().getSerieId().longValue() && serieGrafico.getIndicadorId().longValue() == serie.getIndicador().getIndicadorId().longValue())
							{
								if (!firsItem)
									sbSerieData.append(",");
								if (serieGrafico.getValor() != null)
								{
									ejecutado = serieGrafico.getValor();
									sbSerieData.append(serieGrafico.getValorFormateado(format));
								}
								else
									sbSerieData.append("null");
								firsItem = false;
							}
						}
					}
					indicador = serie.getIndicador();
					firsOne = false;
					break;
				}
			}
			
			Double meta = null;
			for (Iterator<DatosSerie> i = graficoForm.getSeries().iterator(); i.hasNext(); )
			{
				DatosSerie serie = (DatosSerie)i.next();
				String id = serie.getIndicador().getIndicadorId().toString() + '-' + serie.getSerieIndicador().getPk().getSerieId().toString();
				if (serie.getVisualizar() && !id.equals(seriePrimera))
				{
					if (serie.getIndicador() != null)
						graficoForm.setShowImage(true);
					
					if (!firsOne)
					{
						sbSerieName.append(graficoForm.getSeparadorSeries());
						sbSerieData.append(graficoForm.getSeparadorSeries());
					}
					sbSerieName.append(serie.getNombreLeyenda());

					boolean firsItem = true;
					for (int index = 0; index < graficoForm.getAnosPeriodos().size(); index++)
					{
						AnoPeriodo periodo = (AnoPeriodo)graficoForm.getAnosPeriodos().get(index);
						for (int indexSerie = 0; indexSerie < periodo.getSeries().size(); indexSerie++)
						{
							SerieUtil serieGrafico = (SerieUtil)periodo.getSeries().get(indexSerie);
							if (serieGrafico.getSerieId().longValue() == serie.getSerieIndicador().getPk().getSerieId().longValue() && serieGrafico.getIndicadorId().longValue() == serie.getIndicador().getIndicadorId().longValue())
							{
								if (!firsItem)
									sbSerieData.append(",");
								if (serieGrafico.getValor() != null)
								{
									meta = serieGrafico.getValor();
									sbSerieData.append(serieGrafico.getValorFormateado(format));
								}
								else
									sbSerieData.append("null");
								firsItem = false;
							}
						}
					}
					firsOne = false;
					break;
				}
			}
			
			if (ejecutado != null && indicador != null && meta != null && periodoAno != null)
			{
				StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
				StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
				Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, periodoAno.getAno(), periodoAno.getPeriodo(), meta, strategosMedicionesService);
				Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, periodoAno.getAno(),periodoAno.getPeriodo(), meta, zonaVerde, strategosMedicionesService);
				strategosIndicadoresService.close();
				strategosMedicionesService.close();
				
				if (zonaAmarilla != null && zonaVerde != null)
				{
  					if (indicador.getCaracteristica().equals(Caracteristica.getCaracteristicaRetoAumento()))
  					{
  						StringBuffer rangoAlertas = new StringBuffer();
  						rangoAlertas.append("0," + zonaAmarilla.toString());
  						rangoAlertas.append(graficoForm.getSeparadorSeries());
  						rangoAlertas.append(zonaAmarilla.toString() + "," + zonaVerde.toString());
  						rangoAlertas.append(graficoForm.getSeparadorSeries());
  						rangoAlertas.append(zonaVerde.toString() + "," + meta.toString());
  						
  						graficoForm.setRangoAlertas(rangoAlertas.toString());
  						graficoForm.setIsAscendente(true);
  					}
  					else
  					{
  						StringBuffer rangoAlertas = new StringBuffer();
  						meta = meta + zonaAmarilla + zonaVerde;
  						
  						rangoAlertas.append(meta.toString() + "," + zonaAmarilla.toString());
  						rangoAlertas.append(graficoForm.getSeparadorSeries());
  						rangoAlertas.append(zonaAmarilla.toString() + "," + zonaVerde.toString());
  						rangoAlertas.append(graficoForm.getSeparadorSeries());
  						rangoAlertas.append(zonaVerde.toString() + ",0");
  						
  						graficoForm.setRangoAlertas(rangoAlertas.toString());
  						graficoForm.setIsAscendente(false);
  					}
				}
				else if (meta != null)
				{
					zonaVerde = (meta / 3) * 2;
					zonaAmarilla = (meta / 3);

					StringBuffer rangoAlertas = new StringBuffer();
					rangoAlertas.append("0," + zonaAmarilla.toString());
					rangoAlertas.append(graficoForm.getSeparadorSeries());
					rangoAlertas.append(zonaAmarilla.toString() + "," + zonaVerde.toString());
					rangoAlertas.append(graficoForm.getSeparadorSeries());
					rangoAlertas.append(zonaVerde.toString() + "," + meta.toString());
					
					graficoForm.setRangoAlertas(rangoAlertas.toString());
					graficoForm.setIsAscendente(true);
				}
			}
			else if (meta != null)
			{
				Double zonaVerde = (meta / 3) * 2;
				Double zonaAmarilla = (meta / 3);

				StringBuffer rangoAlertas = new StringBuffer();
				rangoAlertas.append("0," + zonaAmarilla.toString());
				rangoAlertas.append(graficoForm.getSeparadorSeries());
				rangoAlertas.append(zonaAmarilla.toString() + "," + zonaVerde.toString());
				rangoAlertas.append(graficoForm.getSeparadorSeries());
				rangoAlertas.append(zonaVerde.toString() + "," + meta.toString());
				
				graficoForm.setRangoAlertas(rangoAlertas.toString());
				graficoForm.setIsAscendente(true);
			}
			else
			{
				if (indicador.getCaracteristica().equals(Caracteristica.getCaracteristicaRetoAumento()))
				{
					StringBuffer rangoAlertas = new StringBuffer();
					rangoAlertas.append("0,120");
					rangoAlertas.append(graficoForm.getSeparadorSeries());
					rangoAlertas.append("120,160");
					rangoAlertas.append(graficoForm.getSeparadorSeries());
					rangoAlertas.append("160,200");
					
					graficoForm.setRangoAlertas(rangoAlertas.toString());
					graficoForm.setIsAscendente(true);
				}
				else
				{
					StringBuffer rangoAlertas = new StringBuffer();
					rangoAlertas.append("200,160");
					rangoAlertas.append(graficoForm.getSeparadorSeries());
					rangoAlertas.append("160,120");
					rangoAlertas.append(graficoForm.getSeparadorSeries());
					rangoAlertas.append("120,0");
					
					graficoForm.setRangoAlertas(rangoAlertas.toString());
					graficoForm.setIsAscendente(false);
				}
			}
		}
		else
		{
			for (Iterator<DatosSerie> i = graficoForm.getSeries().iterator(); i.hasNext(); )
			{
				DatosSerie serie = (DatosSerie)i.next();
				if (!serie.getVisualizar())
					continue;
				if (serie.getIndicador() != null)
					graficoForm.setShowImage(true);
				
				if (!firsOne)
				{
					sbSerieName.append(graficoForm.getSeparadorSeries());
					sbSerieData.append(graficoForm.getSeparadorSeries());
					sbSerieColor.append(graficoForm.getSeparadorSeries());
					sbSerieTipo.append(graficoForm.getSeparadorSeries());
				}
				sbSerieName.append(serie.getNombreLeyenda());
				sbSerieColor.append(serie.getColor());
				sbSerieTipo.append(serie.getTipoSerie());

				boolean firsItem = true;
				for (int index = 0; index < graficoForm.getAnosPeriodos().size(); index++)
				{
					AnoPeriodo periodo = (AnoPeriodo)graficoForm.getAnosPeriodos().get(index);
					for (int indexSerie = 0; indexSerie < periodo.getSeries().size(); indexSerie++)
					{
						SerieUtil serieGrafico = (SerieUtil)periodo.getSeries().get(indexSerie);
						if (serieGrafico.getSerieId().longValue() == serie.getSerieIndicador().getPk().getSerieId().longValue() && 
								serieGrafico.getIndicadorId().longValue() == serie.getIndicador().getIndicadorId().longValue() &&
								serieGrafico.getSerieAnoAnterior().booleanValue() == serie.getSerieAnoAnterior().booleanValue())
						{
							if (!firsItem)
								sbSerieData.append(",");
							if (serieGrafico.getValor() != null)
								sbSerieData.append(serieGrafico.getValorFormateado(format));
							else
								sbSerieData.append("null");
							firsItem = false;
						}
					}
				}
				firsOne = false;
			}
		}
		
		graficoForm.setEjeX(sbEjeX.toString());
		graficoForm.setSerieName(sbSerieName.toString());
		graficoForm.setSerieData(sbSerieData.toString());
		graficoForm.setSerieColor(sbSerieColor.toString());
		graficoForm.setSerieTipo(sbSerieTipo.toString());
	}
	 
	public void SetGrafico(List<Object> objetos, Grafico grafico, String className, Long planId, Boolean desdePortafolio, HttpServletRequest request) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
	{
		
		Long reporteId = ((request.getParameter("reporteId") != null && request.getParameter("reporteId") != "") ? Long.parseLong(request.getParameter("reporteId")) : null);
		String funcion = ((request.getParameter("funcion") != null && request.getParameter("funcion") != "") ? request.getParameter("funcion") : null);
		
		StrategosReportesGraficoService reportesGraficoService = StrategosServiceFactory.getInstance().openStrategosReportesGraficoService();
		ReporteGrafico reporte = new ReporteGrafico();
		List<Long> seriesReporte = new ArrayList<Long>();
		
		if(funcion !=null && funcion.equals("reporte?defaultLoader=true")) {
			reporte= reportesGraficoService.obtenerReporte(new Long(reporteId));
			if(reporte != null && reporte.getTiempo() != null) {
				seriesReporte = obtenerSeriesId(reporte.getTiempo()); 	
			}						
		}
		
		List<DatosSerie> series = null;
	    Calendar ahora = Calendar.getInstance();
        Integer anoInicial = ahora.get(1);
        Integer anoFinal = anoInicial;
		Integer periodoInicial = 1;
		Integer periodoFinal = 12;
		String tipo = "1";
		String nombre = "";
		String tituloEjeY = "";
		String tituloEjeX = "";
		Byte frecuencia = 3;
		Byte frecuenciaAgrupada = 3;
		String condicion = "0";
		String verTituloImprimir = "1";
		String ajustarEscala = "1";
		String titulo = "";
		String impVsAnoAnterior = "0";

		int numeroMaximoPeriodos = 0;
		if (className.equals("Indicador"))
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

			StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService(strategosIndicadoresService);	
			List<SerieTiempo> seriesTiempo = strategosSeriesTiempoService.getSeriesTiempo(0, 0, "serieId", null, false, null).getLista();
			strategosSeriesTiempoService.close();
			
  			Object objeto = objetos.get(0);
  			boolean esObjetoIndicador = true;
  			if (objeto.getClass().getName().equals("java.lang.Long")) 
  				esObjetoIndicador = false;

  			Long objetoId;
  			Indicador indicador = null;
			series = new ArrayList<DatosSerie>();
			for (Iterator<Object> i = objetos.iterator(); i.hasNext(); )
			{
				if (!esObjetoIndicador)
				{
					objetoId = (Long)i.next();
	  				indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(objetoId));
				}
				else
					indicador = (Indicador)i.next();
				
				if (indicador != null)
				{
					if (objetos.size() == 1)
					{
						nombre = indicador.getNombre();
						titulo = nombre;
					}
					frecuencia = indicador.getFrecuencia();
					frecuenciaAgrupada = frecuencia;
					
					if ((anoInicial % 4 == 0) && (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
						numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), anoInicial);
					else if ((anoInicial % 4 == 0) && (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
						numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), anoInicial);
					else 
						numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), anoInicial);
					
					periodoFinal = numeroMaximoPeriodos;
					DatosSerie datosSerie = new DatosSerie();
					if (indicador.getUnidad() == null && indicador.getUnidadId() != null)
					{
					    StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService(strategosIndicadoresService);
					    indicador.setUnidad((UnidadMedida)strategosUnidadesService.load(UnidadMedida.class, new Long(indicador.getUnidadId())));
					    strategosUnidadesService.close();
					    if (objetos.size() == 1)
					    	tituloEjeY = indicador.getUnidad().getNombre();
					}
					else if (indicador.getUnidad() != null && objetos.size() == 1)
						tituloEjeY = indicador.getUnidad().getNombre();
		
					
					if(seriesReporte.size() >0) {
						
						
						for (Iterator<SerieIndicador> j = indicador.getSeriesIndicador().iterator(); j.hasNext(); )
						{
							SerieIndicador serie = (SerieIndicador)j.next();
			
							String serieNombre = "";
					        for (Iterator<SerieTiempo> k = seriesTiempo.iterator(); k.hasNext(); ) 
					        {
					            SerieTiempo serieTiempo = (SerieTiempo)k.next();
				
					            if (serieTiempo.getSerieId().equals(serie.getPk().getSerieId())) 
					            {
					            	serieNombre = serieTiempo.getNombre();
					            	break;
					            }
					        }
					        
					        Boolean agregar = serieValida(seriesReporte, serie.getSerieTiempo().getSerieId());
					        
					        String org = "";
					        
					        datosSerie = new DatosSerie();
					        datosSerie.setIndicador(indicador);
					        datosSerie.setSerieIndicador(serie);
					        org = buscarOrganizacion(indicador.getOrganizacionId());
							datosSerie.setNombreLeyenda(indicador.getNombre() + " - " + serieNombre + " - " + org);
							datosSerie.setColor(ColorUtil.getRndColorRGB());
				        	datosSerie.setColorDecimal(ColorUtil.convertRGBDecimal(datosSerie.getColor()));
				        	datosSerie.setColorEntero(datosSerie.getColorDecimal());
							datosSerie.setTipoSerie(-1);
							datosSerie.setVisualizar(true);
							datosSerie.setShowOrganizacion(false);
							datosSerie.setNivelClase(null);
							datosSerie.setBloquear(false);
							datosSerie.setSerieAnoAnterior(false);
							
							if(agregar) {
								series.add(datosSerie);
							}
							
						}
						
					}else {
						
						for (Iterator<SerieIndicador> j = indicador.getSeriesIndicador().iterator(); j.hasNext(); )
						{
							SerieIndicador serie = (SerieIndicador)j.next();
			
							String serieNombre = "";
					        for (Iterator<SerieTiempo> k = seriesTiempo.iterator(); k.hasNext(); ) 
					        {
					            SerieTiempo serieTiempo = (SerieTiempo)k.next();
				
					            if (serieTiempo.getSerieId().equals(serie.getPk().getSerieId())) 
					            {
					            	serieNombre = serieTiempo.getNombre();
					            	break;
					            }
					        }
					        
					        String org = "";
					        
					        datosSerie = new DatosSerie();
					        datosSerie.setIndicador(indicador);
					        datosSerie.setSerieIndicador(serie);
					        org = buscarOrganizacion(indicador.getOrganizacionId());
							datosSerie.setNombreLeyenda(indicador.getNombre() + " - " + serieNombre + " - " + org);
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
					
					
					
					
					if (planId != null && planId.longValue() != 0L)
					{
						boolean hayMeta = false;
						for (Iterator<DatosSerie> j = series.iterator(); j.hasNext(); )
						{
							DatosSerie serie = (DatosSerie)j.next();
							if (serie.getIndicador().getIndicadorId().longValue() == indicador.getIndicadorId().longValue() && serie.getSerieIndicador().getPk().getSerieId().longValue() == SerieTiempo.getSerieMetaId().longValue())
							{
								hayMeta = true;
								break;
							}
						}
						
						if (!hayMeta)
						{
							SerieTiempo serie = new SerieTiempo();
							serie.setNombre(SerieTiempo.getSerieMeta().getNombre());
							serie.setSerieId(SerieTiempo.getSerieMetaId());
							
							SerieIndicador serieIndicador = new SerieIndicador();
					        serieIndicador.setPk(new SerieIndicadorPK());
					        serieIndicador.getPk().setSerieId(SerieTiempo.getSerieMetaId());
					        serieIndicador.getPk().setIndicadorId(indicador.getIndicadorId());
					        serieIndicador.setIndicador(indicador);
					        serieIndicador.setNombre(SerieTiempo.getSerieMeta().getNombre());
					        serieIndicador.setSerieTiempo(serie);
							
					        datosSerie = new DatosSerie();
					        datosSerie.setIndicador(indicador);
					        datosSerie.setSerieIndicador(serieIndicador);
							datosSerie.setNombreLeyenda(indicador.getNombre() + " - " + SerieTiempo.getSerieMeta().getNombre());
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
				}
				else
				{
					if ((anoInicial % 4 == 0) && (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
						numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), anoInicial);
					else if ((anoInicial % 4 == 0) && (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
						numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), anoInicial);
					else 
						numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), anoInicial);
				}
			}
			strategosIndicadoresService.close();
		}
		else if (className.equals("Celda"))
		{
  			Object objeto = objetos.get(0);
  			boolean esObjetoCelda = true;
  			if (objeto.getClass().getName().equals("java.lang.Long")) 
  				esObjetoCelda = false;

  			Celda celda = null;
  			if (!esObjetoCelda)
  			{
  				Long objetoId = (Long) objetos.get(0);
  				StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
  				celda = (Celda)strategosCeldasService.load(Celda.class, new Long(objetoId));
  				strategosCeldasService.close();
  			}
  			else
  				celda = (Celda) objetos.get(0);
  			
			if (celda != null)
			{
				nombre = celda.getTitulo() != null ? celda.getTitulo() : "";
				titulo = nombre;
			}
			
			if ((anoInicial % 4 == 0) && (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
				numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), anoInicial);
			else if ((anoInicial % 4 == 0) && (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
				numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), anoInicial);
			else 
				numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), anoInicial);

			if (desdePortafolio)
			{
		        anoInicial = celda.getAnoInicial();
		        anoFinal = celda.getAnoFinal();
				periodoInicial = celda.getPeriodoInicial();
				periodoFinal = celda.getPeriodoFinal();
				tipo = celda.getTipo().toString();
				nombre = celda.getNombre();
				tituloEjeY = celda.getTituloEjeY();
				if (celda.getTituloEjeX() != null)
					tituloEjeX = celda.getTituloEjeX();
				frecuencia = celda.getFrecuencia();
				frecuenciaAgrupada = celda.getFrecuenciaAgrupada();
				condicion = celda.getCondicion();
				verTituloImprimir = celda.getVerTituloImprimir();
				ajustarEscala = celda.getAjustarEscala();
				series = celda.getDatosSeries();
			}
		}
		else if (className.equals("Portafolio"))
		{
  			Object objeto = objetos.get(0);
  			boolean esObjetoCelda = true;
  			if (objeto.getClass().getName().equals("java.lang.Long")) 
  				esObjetoCelda = false;

  			Celda celda = null;
  			if (!esObjetoCelda)
  			{
  				Long objetoId = (Long) objetos.get(0);
  				StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
  				celda = (Celda)strategosCeldasService.load(Celda.class, new Long(objetoId));
  				strategosCeldasService.close();
  			}
  			else
  				celda = (Celda) objetos.get(0);
  			
			if (celda != null)
			{
				nombre = celda.getTitulo() != null ? celda.getTitulo() : "";
				titulo = nombre;
			}
			
			if ((anoInicial % 4 == 0) && (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
				numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), anoInicial);
			else if ((anoInicial % 4 == 0) && (frecuencia.byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
				numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), anoInicial);
			else 
				numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), anoInicial);
			
	        anoInicial = celda.getAnoInicial();
	        anoFinal = celda.getAnoFinal();
			periodoInicial = celda.getPeriodoInicial();
			periodoFinal = celda.getPeriodoFinal();
			tipo = celda.getTipo().toString();
			nombre = celda.getNombre();
			tituloEjeY = celda.getTituloEjeY();
			if (celda.getTituloEjeX() != null)
				tituloEjeX = celda.getTituloEjeX();
			frecuencia = celda.getFrecuencia();
			frecuenciaAgrupada = celda.getFrecuenciaAgrupada();
			condicion = celda.getCondicion();
			verTituloImprimir = celda.getVerTituloImprimir();
			ajustarEscala = celda.getAjustarEscala();
			className = "Celda";
			series = celda.getDatosSeries();
		}
		    
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

		//titulo
		
		if(reporte != null && reporte.getNombre() != null) {
			
			elemento = document.createElement("titulo");
			text = document.createTextNode(reporte.getNombre());
			elemento.appendChild(text);
			raiz.appendChild(elemento);
		}else {
			
			elemento = document.createElement("titulo");
			text = document.createTextNode(titulo);
			elemento.appendChild(text);
			raiz.appendChild(elemento);
		}
				

		elemento = document.createElement("tituloEjeY");
		text = document.createTextNode(tituloEjeY);
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("tituloEjeX");
		text = document.createTextNode(tituloEjeX);
		elemento.appendChild(text);
		raiz.appendChild(elemento);
		
		//año inicial
		
		if(reporte != null && reporte.getPeriodoIni() != null) {
			
			elemento = document.createElement("anoInicial");
			text = document.createTextNode(reporte.getPeriodoIni().substring(0, 4));
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
		}else {
			
			elemento = document.createElement("anoInicial");
			text = document.createTextNode(anoInicial.toString());
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
		}
		
		//periodo inicial
		
		if(reporte != null && reporte.getPeriodoIni() != null) {
			
			elemento = document.createElement("periodoInicial");
			text = document.createTextNode(reporte.getPeriodoIni().substring(5));
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
		}else {
			
			elemento = document.createElement("periodoInicial");
			text = document.createTextNode(periodoInicial.toString());
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
		}
		
		//año final

		if(reporte != null && reporte.getPeriodoFin() != null) {
			
			elemento = document.createElement("anoFinal");
			text = document.createTextNode(reporte.getPeriodoFin().substring(0, 4));
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
		}else {
			
			elemento = document.createElement("anoFinal");
			text = document.createTextNode(anoFinal.toString());
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
		}
		
		//periodo  final

		if (numeroMaximoPeriodos > 30)
			periodoFinal = 30;
		
		if(reporte != null && reporte.getPeriodoFin() != null) {
			
			elemento = document.createElement("periodoFinal");
			text = document.createTextNode(reporte.getPeriodoFin().substring(5));
			elemento.appendChild(text);
			raiz.appendChild(elemento);			
			
		}else {
			
			elemento = document.createElement("periodoFinal");
			text = document.createTextNode(periodoFinal.toString());
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
		}
		
		
        
		elemento = document.createElement("frecuencia");
		text = document.createTextNode(frecuencia.toString());
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("frecuenciaAgrupada");
		text = document.createTextNode(frecuenciaAgrupada.toString());
		elemento.appendChild(text);
		raiz.appendChild(elemento);
		
		elemento = document.createElement("nombre");
		text = document.createTextNode(nombre);
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("condicion");
		text = document.createTextNode(condicion);
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
		
		elemento = document.createElement("impVsAnoAnterior");
		text = document.createTextNode(impVsAnoAnterior);
		elemento.appendChild(text);
		raiz.appendChild(elemento);
		
		Element indicadores = document.createElement("indicadores");  // creamos el elemento raiz
		raiz.appendChild(indicadores);

		if (series != null)
		{
			for (Iterator<DatosSerie> i = series.iterator(); i.hasNext(); )
			{
				DatosSerie serie = (DatosSerie)i.next();

				Element indicadorElement = document.createElement("indicador");  // creamos el elemento raiz
				indicadores.appendChild(indicadorElement);
				
				elemento = document.createElement("id");
				text = document.createTextNode(serie.getIndicador().getIndicadorId().toString());
				elemento.appendChild(text);
				indicadorElement.appendChild(elemento);
	
				elemento = document.createElement("serie");
				text = document.createTextNode(serie.getSerieIndicador().getPk().getSerieId().toString());
				elemento.appendChild(text);
				indicadorElement.appendChild(elemento);
	
				elemento = document.createElement("leyenda");
				text = document.createTextNode(serie.getNombreLeyenda());
				elemento.appendChild(text);
				indicadorElement.appendChild(elemento);
	
				elemento = document.createElement("color");
				text = document.createTextNode(serie.getColor());
				elemento.appendChild(text);
				indicadorElement.appendChild(elemento);

				elemento = document.createElement("tipoGrafico");
				text = document.createTextNode(serie.getTipoSerie().toString());
				elemento.appendChild(text);
				indicadorElement.appendChild(elemento);
	
				elemento = document.createElement("visible");
				text = document.createTextNode((serie.getVisualizar() ? "1" : "0"));
				elemento.appendChild(text);
				indicadorElement.appendChild(elemento);
				
				if (planId != null && planId.longValue() != 0L)
				{
					elemento = document.createElement("planId");
					text = document.createTextNode(planId.toString());
					elemento.appendChild(text);
					indicadorElement.appendChild(elemento);
				}
			}
		}
		else
		{
			Element indicadorElement = document.createElement("indicador");  // creamos el elemento raiz
			indicadores.appendChild(indicadorElement);
			
			elemento = document.createElement("id");
			indicadorElement.appendChild(elemento);

			elemento = document.createElement("serie");
			indicadorElement.appendChild(elemento);

			elemento = document.createElement("leyenda");
			indicadorElement.appendChild(elemento);

			elemento = document.createElement("color");
			indicadorElement.appendChild(elemento);

			elemento = document.createElement("tipoGrafico");
			text = document.createTextNode("-1");
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);

			elemento = document.createElement("visible");
			text = document.createTextNode("1");
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);

			if (planId != null && planId.longValue() != 0L)
			{
				elemento = document.createElement("planId");
				text = document.createTextNode(planId.toString());
				elemento.appendChild(text);
				indicadorElement.appendChild(elemento);
			}
		}
        
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
	
	public String buscarOrganizacion(Long organizacionId) {
		
		String cadena = "";		
		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		
		OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, organizacionId);
		
		if(organizacion != null) {
			cadena = organizacion.getNombre();
		}
		
		strategosOrganizacionesService.close();
		return cadena;
	}
	
	public List<Long> obtenerSeriesId(String seriesReporte){
		
		String seriesReportes="";
		int ser=0;
		ser = seriesReporte.length();
		List<Long> series = new ArrayList<Long>();
		
		if(ser >0) {
			
			seriesReportes = seriesReporte.substring(0, ser-1);
			String[] cad = seriesReportes.split(",");
			for(int x=0; x<cad.length; x++) {
				
				String detalle= cad[x];
				series.add(new Long(detalle));
			}
		}
		
		return series;
		
	}
	
	public Boolean serieValida(List<Long> series, Long serieId) {
		
		Boolean respuesta= false;
		
		for(Long ser: series) {
			if(ser.equals(serieId)) {
				return true;
			}
		}
		
		return respuesta;
	}
	
}