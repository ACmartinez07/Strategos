/**
 * 
 */
package com.visiongc.app.strategos.web.struts.portafolios.actions;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.graficos.util.DatosSerie;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planificacionseguimiento.model.util.AlertaIniciativaProducto;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.ColorUtil;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm.GraficoTipo;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm.GraficoTipoIniciativa;
import com.visiongc.app.strategos.web.struts.presentaciones.vistas.forms.ShowVistaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.util.WebUtil;

/**
 * @author Kerwin
 *
 */
public class VistaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = new ActionMessages();
		String forward = mapping.getParameter();

		Long portafolioId = (request.getParameter("id") != null && request.getParameter("id") != "") ? Long.parseLong(request.getParameter("id")) : null;
		
		StrategosPortafoliosService strategosPortafoliosService = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		Portafolio portafolio = null;
		List<Iniciativa> iniciativas = new ArrayList<Iniciativa>();
		
		boolean hayPagina = true;
		boolean hayIniciativas = true;
		List<Pagina> paginas = null;
		if (portafolioId != null)
		{
			portafolio = (Portafolio)strategosPortafoliosService.load(Portafolio.class, new Long(portafolioId));
			if (portafolio != null)
			{
				Map<String, String> filtros = new HashMap<String, String>();		
				
				filtros.put("historicoDate", "IS NULL");
				filtros.put("portafolioId", portafolio.getId().toString());
				
				iniciativas = (List<Iniciativa>)strategosIniciativasService.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();
				
				StrategosPaginasService strategosPaginasService = StrategosServiceFactory.getInstance().openStrategosPaginasService();
			    filtros = new HashMap<String, String>();
			    filtros.put("portafolioId", portafolio.getId().toString());
			    PaginaLista paginaPaginas = strategosPaginasService.getPaginas(0, 0, "numero", "ASC", true, filtros);
			    strategosPaginasService.close();
				paginas = paginaPaginas.getLista();
				if (paginas.size() <=0)
					hayPagina = false;
				if (iniciativas.size() == 0)
					hayIniciativas = false;
			}
		}
		
		Integer anchoCelda = new Integer(0);
		Integer altoCelda = new Integer(0);

		Integer anchoPagina = new Integer(0);
		Integer altoPagina = new Integer(0);

		if (portafolio != null && hayPagina && hayIniciativas) 
		{
			Vista vista = new Vista(portafolio.getId(), portafolio.getOrganizacionId(), portafolio.getNombre(), null, null, null, true);
			vista.setPaginas(new HashSet<Pagina>());
			vista.getPaginas().addAll(paginas);
			Pagina pagina = null;
			pagina = (Pagina)paginas.toArray()[0];

			anchoCelda = pagina.getAncho();
			altoCelda = pagina.getAlto();

			anchoPagina = new Integer(pagina.getAncho().intValue() * pagina.getColumnas().intValue());
			altoPagina = new Integer(pagina.getAlto().intValue() * pagina.getFilas().intValue());
			
			List<Celda> listaCeldas = new ArrayList<Celda>();
			listaCeldas = getListaCeldas(pagina.getPaginaId(), request);
			if (listaCeldas.size() == 0)
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.mostrar.portafolio.nocelda"));
				saveMessages(request, messages);
				hayPagina = false;
			}
			else
			{
				pagina.setCeldas(new HashSet<Celda>());
				pagina.getCeldas().addAll(listaCeldas);

				GetImgByCelda(request, listaCeldas, iniciativas, portafolio, strategosIniciativasService);

				Long[] controlPaginacion = obtenerControlPaginacion(paginas, pagina);

				ShowVistaForm showVistaForm = (ShowVistaForm)form;
				showVistaForm.clear();
				
				showVistaForm.setPaginaPreviaId(controlPaginacion[0]);
				showVistaForm.setPaginaSiguienteId(controlPaginacion[1]);
				showVistaForm.setAnchoPagina(anchoPagina);
				showVistaForm.setAlertaVacia(new URL(WebUtil.getUrl(request, "/paginas/strategos/presentaciones/vistas/imagenes/alertaEmpty.gif")).toString());
				showVistaForm.setAltoPagina(altoPagina);
				showVistaForm.setAnchoCelda(anchoCelda);
				showVistaForm.setAltoCelda(altoCelda);
				showVistaForm.setPagina(pagina);
				showVistaForm.setPaginas(((vista != null && vista.getPaginas() != null) ? vista.getPaginas().size() : 0));
				showVistaForm.setVista(vista);
				showVistaForm.setCeldas(listaCeldas);
				showVistaForm.setFrecuencia(Frecuencia.getFrecuenciaAnual());
				showVistaForm.setAnchoMarco(new Integer(anchoPagina.intValue() + 100));
				showVistaForm.setPaginaId(pagina.getPaginaId());
				showVistaForm.setVistaId(vista != null ? vista.getVistaId() : 0);
			}
		}
		else 
		{
			if (!hayIniciativas)
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.mostrar.portafolio.noiniciativa"));
				saveMessages(request, messages);
				hayPagina = false;
			}
			else
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.mostrar.portafolio.nopagina"));
				saveMessages(request, messages);
				hayPagina = false;
			}
		}

		strategosIniciativasService.close();
		strategosPortafoliosService.close();
		
		if (!hayPagina)
			return getForwardBack(request, 2, true);
		else
			return mapping.findForward(forward);
	}
	
	private List<Celda> getListaCeldas(Long paginaId, HttpServletRequest request)
	{
		StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();

		Map<String, String> filtros = new HashMap<String, String>();
		String atributoOrden = "celdaId";
		String tipoOrden = "ASC";
		int pagina = 1;
		filtros.put("paginaId", paginaId.toString());

		PaginaLista paginaCeldas = strategosCeldasService.getCeldas(pagina, 30, atributoOrden, tipoOrden, true, filtros, getUsuarioConectado(request));
		strategosCeldasService.close();
		
		for (Iterator<Celda> i = paginaCeldas.getLista().iterator(); i.hasNext(); )
		{
			Celda celda = (Celda)i.next();
			celda.setTipo(GraficoTipo.getTipoColumna());
			if (celda.getFila().byteValue() == 1 && celda.getColumna().byteValue() == 1)
				celda.setTipoGrafico(GraficoTipoIniciativa.getGraficoTipoEstatus());
			else if (celda.getFila().byteValue() == 1 && celda.getColumna().byteValue() == 2)
				celda.setTipoGrafico(GraficoTipoIniciativa.getGraficoTipoPorcentajes());
			else if (celda.getFila().byteValue() == 2 && celda.getColumna().byteValue() == 1)
				celda.setTipoGrafico(GraficoTipoIniciativa.getGraficoTipoTiposEstatus());
			else if (celda.getFila().byteValue() == 2 && celda.getColumna().byteValue() == 2)
				celda.setTipoGrafico(GraficoTipoIniciativa.getGraficoPorcentajePortafolio());
		}
		
		return paginaCeldas.getLista();
	}
	
	public void GetImgGrafico(HttpServletRequest request, Celda celda, List<Iniciativa> iniciativas, Portafolio portafolio, GraficoForm graficoForm, Integer ano, Integer periodo, StrategosIniciativasService strategosIniciativasService) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
	{
		StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
		
		List<DatosSerie> series = null;
		Grafico grafico;
		Byte frecuencia = Frecuencia.getFrecuenciaMensual();
		MessageResources mensajes = getResources(request);
		Indicador indicador = null;

		if (celda.getTipoGrafico().byteValue() == GraficoTipoIniciativa.getGraficoPorcentajePortafolio().byteValue())
			graficoForm.setFrecuencia(portafolio.getFrecuencia());
		else
			graficoForm.setFrecuencia(frecuencia);
		graficoForm.setAno(ano);
		graficoForm.setPeriodo(periodo);
		graficoForm.setFrecuenciasCompatibles(Frecuencia.getFrecuenciasCompatibles(Frecuencia.getFrecuenciaDiaria()));
		graficoForm.setVirtual(true);
		graficoForm.setTipoGrafico(celda.getTipoGrafico());
		
		if (celda.getTipoGrafico().byteValue() == GraficoTipoIniciativa.getGraficoPorcentajePortafolio().byteValue())
		{
			graficoForm.setVirtual(true);
			graficoForm.setMostrarLeyendas(celda.getVerLeyenda() != null ? celda.getVerLeyenda() : false);
			graficoForm.setMostrarTooltips(false);
			graficoForm.setTamanoLeyenda(8);
			graficoForm.setClassName(graficoForm.getSource());
			graficoForm.setFrecuencias(Frecuencia.getFrecuencias());
			graficoForm.setMostrarTooltips(false);
			graficoForm.setTamanoLeyenda(8);
			graficoForm.setSource("Celda");

			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, portafolio.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
			strategosIndicadoresService.close();
			
			if (celda.getConfiguracion() != null)
			{
				celda.setDatosSeries(new ArrayList<DatosSerie>());
				setDatos(celda, mensajes, strategosIniciativasService);
			}
			else
			{
				Integer anoInicial = 0000;
				Integer anoFinal = 9999;
				Integer periodoInicial = 000;
				Integer periodoFinal = 000;

				StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
				List<Medicion> mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), anoInicial, anoFinal, periodoInicial, periodoFinal);
				strategosMedicionesService.close();
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
	    		
				series = new ArrayList<DatosSerie>();
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
			
			List<Object> objetos = new ArrayList<Object>();			
			objetos.add(celda);
			grafico = new Grafico();
			new com.visiongc.app.strategos.web.struts.graficos.actions.GraficoAction().SetGrafico(objetos, grafico, graficoForm.getSource(), null, true, request);
			if (celda.getConfiguracion() == null)
			{
				celda.setConfiguracion(grafico.getConfiguracion());
				celda.setNombre(celda.getTitulo());
				strategosCeldasService.saveCelda(celda, getUsuarioConectado(request));
			}

			graficoForm.setMostrarLeyendas(celda.getVerLeyenda() != null ? celda.getVerLeyenda() : false);
			
			new com.visiongc.app.strategos.web.struts.graficos.actions.GraficoAction().GetObjeto(graficoForm, grafico);
			new com.visiongc.app.strategos.web.struts.graficos.actions.GraficoAction().GetGrafico(graficoForm, request);
			
			celda.setTipo(graficoForm.getTipo());
			celda.setEjeX(graficoForm.getEjeX());
			celda.setSerieName(graficoForm.getSerieName());
			celda.setSerieData(graficoForm.getSerieData());
			celda.setSerieColor(graficoForm.getSerieColor());
			celda.setSerieTipo(graficoForm.getSerieTipo());
			celda.setRangoAlertas(graficoForm.getRangoAlertas());
			celda.setIsAscendente(graficoForm.getIsAscendente());
			celda.setTitulo(graficoForm.getTitulo());

			Byte alerta = indicador.getAlerta();
			celda.setShowDuppont(false);
			celda.setShowImage(false);
			celda.setShowImage(true);
			if (indicador != null && indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaFormula().byteValue())
			{
				celda.setShowDuppont(true);
				celda.setIndicadorId(indicador.getIndicadorId().toString());
			}
			
			if (alerta != null)
			{
				celda.setShowAlerta(true);
				String imagenAlerta = new URL(WebUtil.getUrl(request, "/paginas/strategos/presentaciones/vistas/imagenes/alertaBlanca.gif")).toString();
				if (alerta.byteValue() == AlertaIniciativaProducto.getAlertaVerde().byteValue())
	            	imagenAlerta = new URL(WebUtil.getUrl(request, "/paginas/strategos/presentaciones/vistas/imagenes/alertaVerde.gif")).toString();
	            else if (alerta.byteValue() == AlertaIniciativaProducto.getAlertaAmarilla().byteValue())
	            	imagenAlerta = new URL(WebUtil.getUrl(request, "/paginas/strategos/presentaciones/vistas/imagenes/alertaAmarilla.gif")).toString();
	            else if (alerta.byteValue() == AlertaIniciativaProducto.getAlertaRoja().byteValue()) 
	            	imagenAlerta = new URL(WebUtil.getUrl(request, "/paginas/strategos/presentaciones/vistas/imagenes/alertaRoja.gif")).toString();
				
				celda.setAlerta(imagenAlerta);
			}
			else
				celda.setShowAlerta(false);
		}
		else
		{
			if (celda.getConfiguracion() != null)
			{
				celda.setDatosSeries(new ArrayList<DatosSerie>());
				setDatos(celda, mensajes, strategosIniciativasService);
				graficoForm.setAno(celda.getAnoInicial());
				graficoForm.setPeriodo(celda.getPeriodoInicial());
			}

			new com.visiongc.app.strategos.web.struts.portafolios.actions.CrearGraficoAction().setValores(graficoForm, iniciativas, strategosIniciativasService);
			
			List<Object> objetos = new ArrayList<Object>();
			String[] ids = graficoForm.getObjetosIds().split(",");
			for (int j = 0; j < ids.length; j++)
				objetos.add(Long.parseLong(ids[j]));

			grafico = new Grafico();
			grafico.setNombre(celda.getNombre());

			if (celda.getConfiguracion() == null)
				new com.visiongc.app.strategos.web.struts.portafolios.actions.CrearGraficoAction().SetGrafico(objetos, grafico, graficoForm.getSource(), null, graficoForm.getTipoGrafico(), request);

			String res = "";
			String Ids = "";
			String Nombres = "";
			String SeriePlanId = "";
			boolean esPrimero = true;
			series = new ArrayList<DatosSerie>();
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
				
				indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
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
				esPrimero = false;
			}
			
			if (celda.getConfiguracion() == null)
			{
				celda.setFrecuencia(frecuencia);
				celda.setFrecuenciaAgrupada(frecuencia);
				celda.setAjustarEscala("1");
				celda.setVerTituloImprimir("1");
				celda.setCondicion("0");
				celda.setNombre(celda.getTitulo());
				celda.setTituloEjeY(mensajes.getMessage("jsp.vista.titulo.ejey"));
				celda.setAnoInicial(ano);
				celda.setAnoFinal(ano);
				celda.setPeriodoInicial(periodo);
				celda.setPeriodoFinal(periodo);
				celda.setDatosSeries(series);
			}

			objetos = new ArrayList<Object>();
			objetos.add(celda);
			new com.visiongc.app.strategos.web.struts.graficos.actions.GraficoAction().SetGrafico(objetos, grafico, "Portafolio", null, false, request);
			if (celda.getConfiguracion() == null)
			{
				celda.setConfiguracion(grafico.getConfiguracion());
				celda.setNombre(celda.getTitulo());
				strategosCeldasService.saveCelda(celda, getUsuarioConectado(request));
			}
			res = new com.visiongc.app.strategos.web.struts.graficos.actions.SeleccionarGraficoAction().ReadXmlProperties(res, grafico);
			res = res + "|" + "indicadorInsumoSeleccionadoIds!,!" + Ids + "|" + "indicadorInsumoSeleccionadoNombres!,!" + Nombres + "|" + "indicadorInsumoSeleccionadoSeriePlanId!,!" + SeriePlanId;			
			graficoForm.setRespuesta(res);
		
			new com.visiongc.app.strategos.web.struts.portafolios.actions.CrearGraficoAction().GetObjeto(graficoForm, grafico, null, null, null);
			
			graficoForm.setClassName(grafico.getClassName());
			graficoForm.setFrecuencias(Frecuencia.getFrecuencias());

			new com.visiongc.app.strategos.web.struts.portafolios.actions.CrearGraficoAction().GetGrafico(graficoForm, request);

			Calendar fecha = PeriodoUtil.getDateByPeriodo(graficoForm.getFrecuencia(), graficoForm.getAno(), graficoForm.getPeriodo(), true);
			graficoForm.setFecha(VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta"));
			
			graficoForm.setMostrarLeyendas(celda.getVerLeyenda() != null ? celda.getVerLeyenda() : false);
			graficoForm.setMostrarTooltips(false);
			graficoForm.setTamanoLeyenda(8);
			graficoForm.setSource("Celda");
			
			celda.setEjeX(graficoForm.getEjeX());
			celda.setSerieName(graficoForm.getSerieName());
			celda.setSerieData(graficoForm.getSerieData());
			celda.setSerieColor(graficoForm.getSerieColor());
			celda.setSerieTipo(graficoForm.getSerieTipo());
			celda.setRangoAlertas(graficoForm.getRangoAlertas());
			celda.setIsAscendente(graficoForm.getIsAscendente());
			
			Byte alerta = null;
			Long indicadorId = null;
			Boolean hayUnIndicador = true;
			for (Iterator<DatosSerie> j = celda.getDatosSeries().iterator(); j.hasNext(); )
			{
				DatosSerie serie = (DatosSerie)j.next();
				if (serie.getIndicador() != null)
				{
					if (indicadorId == null)
						indicadorId = serie.getIndicador().getIndicadorId();
					
					if (indicadorId.longValue() == serie.getIndicador().getIndicadorId() && (serie.getPlanId() == null || serie.getPlanId() == 0L))
					{
						if (serie.getIndicador() != null)
							alerta = indicador.getAlerta();
					}
					else
						hayUnIndicador = false;
				}
			}
			
			celda.setShowDuppont(false);
			celda.setShowImage(false);
			if (indicadorId != null)
				celda.setShowImage(true);
			if (hayUnIndicador && indicadorId != null)
			{
				if (indicador == null)
					indicador = (Indicador)strategosIniciativasService.load(Indicador.class, indicadorId);
				
				if (indicador != null && indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaFormula().byteValue())
				{
					celda.setShowDuppont(true);
					celda.setIndicadorId(indicadorId.toString());
				}
			}
			
			if (alerta != null)
			{
				celda.setShowAlerta(hayUnIndicador);
				String imagenAlerta = new URL(WebUtil.getUrl(request, "/paginas/strategos/presentaciones/vistas/imagenes/alertaBlanca.gif")).toString();
				if (alerta.byteValue() == AlertaIniciativaProducto.getAlertaVerde().byteValue())
	            	imagenAlerta = new URL(WebUtil.getUrl(request, "/paginas/strategos/presentaciones/vistas/imagenes/alertaVerde.gif")).toString();
	            else if (alerta.byteValue() == AlertaIniciativaProducto.getAlertaAmarilla().byteValue())
	            	imagenAlerta = new URL(WebUtil.getUrl(request, "/paginas/strategos/presentaciones/vistas/imagenes/alertaAmarilla.gif")).toString();
	            else if (alerta.byteValue() == AlertaIniciativaProducto.getAlertaRoja().byteValue()) 
	            	imagenAlerta = new URL(WebUtil.getUrl(request, "/paginas/strategos/presentaciones/vistas/imagenes/alertaRoja.gif")).toString();
				
				celda.setAlerta(imagenAlerta);
			}
			else
				celda.setShowAlerta(false);
		}
		
		strategosCeldasService.close();
	}
	
	private void GetImgByCelda(HttpServletRequest request, List<Celda> celdas, List<Iniciativa> iniciativas, Portafolio portafolio, StrategosIniciativasService strategosIniciativasService) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
	{
		GraficoForm graficoForm;
		Calendar ahora = Calendar.getInstance();
		Integer ano = ahora.get(1);
		Integer periodo = 1;
		
		for (Iterator<Celda> i = celdas.iterator(); i.hasNext(); )
		{
			Celda celda = (Celda)i.next();
			graficoForm = new GraficoForm();
			
			GetImgGrafico(request, celda, iniciativas, portafolio, graficoForm, ano, periodo, strategosIniciativasService);
		}
	}
	
	private Long[] obtenerControlPaginacion(List<Pagina> paginas, Pagina pagina)
	{
		Long[] controlPaginacion = new Long[2];
		int mayorIndice = paginas.size() - 1;
		int indicePaginaPrevia = pagina.getNumero().intValue() - 2;
		int indicePaginaSiguiente = pagina.getNumero().intValue();

		if (indicePaginaPrevia < 0) 
			indicePaginaPrevia = mayorIndice;

		if (indicePaginaSiguiente > mayorIndice) 
			indicePaginaSiguiente = 0;

		controlPaginacion[0] = ((Pagina)paginas.toArray()[indicePaginaPrevia]).getPaginaId();
		controlPaginacion[1] = ((Pagina)paginas.toArray()[indicePaginaSiguiente]).getPaginaId();

		return controlPaginacion;
	}
	
	public void setDatos(Celda celda, MessageResources mensajes, StrategosIniciativasService strategosIniciativasService) throws ParserConfigurationException, SAXException, IOException
	{
		Calendar ahora = Calendar.getInstance();
		Integer ano = ahora.get(1);
		Integer periodo = 1;
		
		DocumentBuilderFactory factory  =  DocumentBuilderFactory.newInstance();;
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(TextEncoder.deleteCharInvalid(celda.getConfiguracion()))));
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
					celda.setTipo(Byte.parseByte(valor.getNodeValue()));
				else
					celda.setTipo(GraficoTipo.getTipoColumna());
			}
			else
				celda.setTipo(GraficoTipo.getTipoColumna());

			if (elemento.getElementsByTagName("titulo").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("titulo").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setTitulo(valor.getNodeValue());
				else
					celda.setTitulo(celda.getNombre());
			}
			else
				celda.setTitulo(celda.getNombre());

			if (elemento.getElementsByTagName("tituloEjeY").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("tituloEjeY").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setTituloEjeY(valor.getNodeValue());
				else
					celda.setTituloEjeY(mensajes.getMessage("jsp.vista.titulo.ejey"));
			}
			else
				celda.setTituloEjeY(mensajes.getMessage("jsp.vista.titulo.ejey"));

			if (elemento.getElementsByTagName("tituloEjeX").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("tituloEjeX").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setTituloEjeX(valor.getNodeValue());
				else
					celda.setTituloEjeX("");
			}
			else
				celda.setTituloEjeX("");

			if (elemento.getElementsByTagName("nombre").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("nombre").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setNombre(valor.getNodeValue());
			}

			if (elemento.getElementsByTagName("verTituloImprimir").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("verTituloImprimir").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setVerTituloImprimir(valor.getNodeValue());
				else
					celda.setVerTituloImprimir("1");
			}
			else
				celda.setVerTituloImprimir("1");

			if (elemento.getElementsByTagName("ajustarEscala").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("ajustarEscala").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setAjustarEscala(valor.getNodeValue());
				else
					celda.setAjustarEscala("1");
			}
			else
				celda.setAjustarEscala("1");

			if (elemento.getElementsByTagName("ano").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("ano").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setAnoInicial(Integer.parseInt(valor.getNodeValue()));
				else
					celda.setAnoInicial(ano);
			}
			else
				celda.setAnoInicial(ano);
			celda.setAnoFinal(celda.getAnoInicial());

			if (elemento.getElementsByTagName("periodo").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("periodo").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setPeriodoInicial(Integer.parseInt(valor.getNodeValue()));
				else
					celda.setPeriodoInicial(periodo);
			}
			else
				celda.setPeriodoInicial(periodo);
			celda.setPeriodoFinal(celda.getPeriodoInicial());
			
			if (elemento.getElementsByTagName("anoInicial").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("anoInicial").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setAnoInicial(Integer.parseInt(valor.getNodeValue()));
				else
					celda.setAnoInicial(ano);
			}
			else
				celda.setAnoInicial(ano);

			if (elemento.getElementsByTagName("periodoInicial").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("periodoInicial").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setPeriodoInicial(Integer.parseInt(valor.getNodeValue()));
				else
					celda.setPeriodoInicial(periodo);
			}
			else
				celda.setPeriodoInicial(periodo);

			if (elemento.getElementsByTagName("anoFinal").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("anoFinal").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setAnoFinal(Integer.parseInt(valor.getNodeValue()));
				else
					celda.setAnoFinal(ano);
			}
			else
				celda.setAnoFinal(ano);

			if (elemento.getElementsByTagName("periodoFinal").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("periodoFinal").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setPeriodoFinal(Integer.parseInt(valor.getNodeValue()));
				else
					celda.setPeriodoFinal(periodo);
			}
			else
				celda.setPeriodoFinal(periodo);
			
			if (elemento.getElementsByTagName("frecuencia").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("frecuencia").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setFrecuencia(Byte.parseByte(valor.getNodeValue()));
				else
					celda.setFrecuencia(Frecuencia.getFrecuenciaMensual());
			}
			else
				celda.setFrecuencia(Frecuencia.getFrecuenciaMensual());

			if (elemento.getElementsByTagName("frecuenciaAgrupada").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("frecuenciaAgrupada").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setFrecuenciaAgrupada(Byte.parseByte(valor.getNodeValue()));
				else
					celda.setFrecuenciaAgrupada(Frecuencia.getFrecuenciaMensual());
			}
			else
				celda.setFrecuenciaAgrupada(Frecuencia.getFrecuenciaMensual());
			
			if (elemento.getElementsByTagName("condicion").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("condicion").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && !valor.getNodeValue().equals(""))
					celda.setCondicion(valor.getNodeValue());
				else
					celda.setCondicion("0");
			}
			else
				celda.setCondicion("0");
		}
		
		lista = doc.getElementsByTagName("indicador");
		if (lista.getLength() > 0)
		{
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
						Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, new Long(valor.getNodeValue()));
						if (indicador != null)
						{
							Long serieNodo = null;
					        if (elemento.getElementsByTagName("serie").getLength() > 0)
					        {
								nodeLista = elemento.getElementsByTagName("serie").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if (valor != null && !valor.getNodeValue().equals(""))
									serieNodo = Long.parseLong(valor.getNodeValue());
								else
									serieNodo = SerieTiempo.getSerieRealId();
					        }
							else
								serieNodo = SerieTiempo.getSerieRealId();
							
							SerieIndicador serie = null;
							for (Iterator<SerieIndicador> j = indicador.getSeriesIndicador().iterator(); j.hasNext(); )
							{
								serie = (SerieIndicador)j.next();
								if (serie.getPk().getSerieId().longValue() == serieNodo.longValue())
									break;
							}

							DatosSerie datosSerie = new DatosSerie();
					        datosSerie.setIndicador(indicador);
					        datosSerie.setSerieIndicador(serie);
							datosSerie.setTipoSerie(-1);
							
							datosSerie.setNombreLeyenda(indicador.getNombre());
					        if (elemento.getElementsByTagName("leyenda").getLength() > 0)
					        {
								nodeLista = elemento.getElementsByTagName("leyenda").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if (valor != null && !valor.getNodeValue().equals(""))
									datosSerie.setNombreLeyenda(valor.getNodeValue());
					        }
						        
					        String color = null;
					        String colorDecimal = null;
					        String colorEntero = null;
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

							datosSerie.setVisualizar(true);
					        if (elemento.getElementsByTagName("visible").getLength() > 0)
					        {
								nodeLista = elemento.getElementsByTagName("visible").item(0).getChildNodes();
								valor = (Node) nodeLista.item(0);
								if (valor != null && !valor.getNodeValue().equals(""))
									datosSerie.setVisualizar(valor.getNodeValue() == "1" || Integer.parseInt(valor.getNodeValue()) == 1 ? true : false);
					        }
							
							datosSerie.setShowOrganizacion(false);
							datosSerie.setNivelClase(null);
							datosSerie.setBloquear(false);
							datosSerie.setSerieAnoAnterior(false);
							
							celda.getDatosSeries().add(datosSerie);
						}
					}
				}
			}
		}
	}
}