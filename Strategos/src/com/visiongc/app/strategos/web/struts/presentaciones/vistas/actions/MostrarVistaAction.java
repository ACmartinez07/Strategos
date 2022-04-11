package com.visiongc.app.strategos.web.struts.presentaciones.vistas.actions;

import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.graficos.util.DatosSerie;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.util.AlertaIniciativaProducto;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.StrategosVistasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm;
import com.visiongc.app.strategos.web.struts.presentaciones.vistas.forms.ShowVistaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.util.WebUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.xml.sax.SAXException;

public class MostrarVistaAction extends VgcAction
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

		boolean cancel = (request.getParameter("cancel") != null) && ((request.getParameter("cancel").equals("true")) || (request.getParameter("cancel").equals("1")));

		messages = getMessages(request);

		if (cancel) 
			return getForwardBack(request, 2, true);

		String vistaId = request.getParameter("vistaId");
		String paginaId = request.getParameter("paginaId");

		StrategosVistasService strategosVistasService = StrategosServiceFactory.getInstance().openStrategosVistasService();
		Vista vista = (Vista)strategosVistasService.load(Vista.class, new Long(vistaId));
		strategosVistasService.close();

		StrategosPaginasService strategosPaginasService = StrategosServiceFactory.getInstance().openStrategosPaginasService();
	    Map<String, String> filtros = new HashMap<String, String>();
	    filtros.put("vistaId", vistaId.toString());
	    PaginaLista paginaPaginas = strategosPaginasService.getPaginas(1, 30, "numero", "ASC", true, filtros);
	    strategosPaginasService.close();
		
		boolean hayPagina = true;
		List<Pagina> paginas = paginaPaginas.getLista();
		if (paginas.size() <=0)
			hayPagina = false;

		Integer anchoCelda = new Integer(0);
		Integer altoCelda = new Integer(0);

		Integer anchoPagina = new Integer(0);
		Integer altoPagina = new Integer(0);

		if (hayPagina) 
		{
			vista.setPaginas(new HashSet<Pagina>());
			vista.getPaginas().addAll(paginas);
			Pagina pagina = null;
			if (paginaId == null)
			{
				pagina = (Pagina)paginas.toArray()[0];
				paginaId = pagina.getPaginaId().toString();
			}
			
			List<Celda> listaCeldas = new ArrayList<Celda>();
			if (paginaId != null) 
			{
				if (pagina == null)
				{
					for (Iterator<Pagina> i = paginas.iterator(); i.hasNext(); )
					{
						Pagina paginaList = (Pagina)i.next();
						if (paginaList.getPaginaId().longValue() == new Long(paginaId).longValue())
						{
							pagina = paginaList;
							break;
						}
					}
				}
				
				anchoCelda = pagina.getAncho();
				altoCelda = pagina.getAlto();

				anchoPagina = new Integer(pagina.getAncho().intValue() * pagina.getColumnas().intValue());
				altoPagina = new Integer(pagina.getAlto().intValue() * pagina.getFilas().intValue());
				listaCeldas = getListaCeldas(pagina.getPaginaId(), request);
		
				if (listaCeldas.size() == 0)
				{
					if (setCeldas(pagina, listaCeldas, request, messages) != 10000)
						return getForwardBack(request, 2, true);
				}
				pagina.setCeldas(new HashSet());
				pagina.getCeldas().addAll(listaCeldas);

				GetImgGrafico(request, listaCeldas);
			}

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
		else 
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.mostrarvista.nopagina"));
			saveMessages(request, messages);
			return getForwardBack(request, 2, true);
		}

		saveMessages(request, messages);
		
		return mapping.findForward(forward);
	}
	
	private int setCeldas(Pagina pagina, List<Celda> lista, HttpServletRequest request, ActionMessages messages) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
	{
		StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
		Celda celda = new Celda();
		int respuesta = 10000;
		for (byte i = 1; i <= pagina.getFilas(); i = (byte)(i + 1)) 
		{
			for (byte j = 1; j <= pagina.getColumnas(); j = (byte)(j + 1)) 
			{
				celda = new Celda();
				celda.setCeldaId(new Long(0L));
				celda.setFila(new Byte(i));
				celda.setColumna(new Byte(j));
				celda.setPaginaId(pagina.getPaginaId());
				celda.setIndicadoresCelda(new HashSet<Object>());
				celda.setTitulo(null);
				
				respuesta = strategosCeldasService.saveCelda(celda, getUsuarioConectado(request));

				if (respuesta == 10003)
				{
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
					saveMessages(request, messages);
					break;
				}
				
				lista.add(celda);
			}

			if (respuesta != 10000)
				break;
		}

		strategosCeldasService.close();
		return respuesta;
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
		
		return paginaCeldas.getLista();
	}

	private void GetImgGrafico(HttpServletRequest request, List<Celda> celdas) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
	{
		Grafico grafico;
		GraficoForm graficoForm;
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		
		for (Iterator<Celda> i = celdas.iterator(); i.hasNext(); )
		{
			Celda celda = (Celda)i.next();
			grafico = new Grafico();
		  
			if (celda.getConfiguracion() == null || celda.getConfiguracion().equals("")) 
			{
				List<Object> objetos = new ArrayList<Object>();
				objetos.add((long) 0);
				new com.visiongc.app.strategos.web.struts.graficos.actions.GraficoAction().SetGrafico(objetos, grafico, "Celda", null, false, request);
			}
			else
			{
				grafico.setGraficoId(celda.getCeldaId());
				grafico.setNombre(celda.getTitulo());
				grafico.setConfiguracion(celda.getConfiguracion());
				grafico.setObjetoId(celda.getCeldaId());
				grafico.setClassName("Celda");
			}
		  
			//Boolean leyenda = WebUtil.getValorInputCheck(request, "celda_" + celda.getFila().toString() + "_" + celda.getColumna().toString());
			graficoForm = new GraficoForm(); 
			graficoForm.setVirtual(true);
			graficoForm.setMostrarLeyendas(celda.getVerLeyenda() != null ? celda.getVerLeyenda() : false);
			graficoForm.setMostrarTooltips(false);
			graficoForm.setTamanoLeyenda(8);
			graficoForm.setSource("Celda");

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

			Byte alerta = null;
			Long indicadorId = null;
			Boolean hayUnIndicador = true;
			Indicador indicador = null;
			for (Iterator<DatosSerie> j = graficoForm.getSeries().iterator(); j.hasNext(); )
			{
				DatosSerie serie = (DatosSerie)j.next();
				if (serie.getIndicador() != null)
				{
					if (indicadorId == null)
						indicadorId = serie.getIndicador().getIndicadorId();
					
					if (indicadorId.longValue() == serie.getIndicador().getIndicadorId() && (serie.getPlanId() == null || serie.getPlanId() == 0L))
					{
						indicador = (Indicador)strategosPlanesService.load(Indicador.class, serie.getIndicador().getIndicadorId());
						if (indicador != null)
							alerta = indicador.getAlerta();
					}
					else if (indicadorId.longValue() == serie.getIndicador().getIndicadorId() && (serie.getPlanId() != null || serie.getPlanId() != 0L))
						alerta = strategosPlanesService.getAlertaIndicadorPorPlan(serie.getIndicador().getIndicadorId(), serie.getPlanId());
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
					indicador = (Indicador)strategosPlanesService.load(Indicador.class, indicadorId);
				
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
		
		strategosPlanesService.close();
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
}