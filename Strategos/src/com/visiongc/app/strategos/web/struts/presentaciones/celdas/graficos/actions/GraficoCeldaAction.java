package com.visiongc.app.strategos.web.struts.presentaciones.celdas.graficos.actions;

import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.graficos.util.DatosSerie;
import com.visiongc.app.strategos.presentaciones.StrategosVistasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.web.NavigationBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.xml.sax.SAXException;

public final class GraficoCeldaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = new ActionMessages();

		messages = getMessages(request);

		String forward = mapping.getParameter();

		boolean cancel = (request.getParameter("cancelar") != null ? Boolean.parseBoolean(request.getParameter("cancelar")) : false);
		Boolean virtual = (request.getParameter("virtual") != null ? Boolean.parseBoolean(request.getParameter("virtual")) : null);

		if (cancel) 
			return getForwardBack(request, 2, true);

		String vistaId = request.getParameter("vistaId");
		String paginaId = request.getParameter("paginaId");
		String celdaId = request.getParameter("celdaId");

		GraficoForm graficoForm = (GraficoForm)form;
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		graficoForm.setVirtual(virtual);
		if (graficoForm.getVirtual() == null)
			graficoForm.setVirtual(false);
		
		Celda celda = (Celda)strategosIndicadoresService.load(Celda.class, new Long(celdaId));

		Pagina pagina = new Pagina();

		String previaCeldaId = "0";
		String siguienteCeldaId = "0";

		if (paginaId != null) 
		{
			pagina = (Pagina)strategosIndicadoresService.load(Pagina.class, new Long(paginaId));

			List<Celda> listaCeldas = getCeldasGrafico(pagina);

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
		}

		Vista vista = new Vista();

		if (vistaId != null) 
			vista = (Vista)strategosIndicadoresService.load(Vista.class, new Long(vistaId));

		strategosIndicadoresService.close();
		
		graficoForm.setVistaId(new Long(vistaId));
		graficoForm.setPaginaId(new Long(paginaId));
		graficoForm.setId(new Long(celdaId));
		graficoForm.setPreviaCeldaId(new Long(previaCeldaId));
		graficoForm.setSiguienteCeldaId(new Long(siguienteCeldaId));
		graficoForm.setSeries(new ArrayList<DatosSerie>());

    	GetImgGrafico(request, celda, graficoForm);
    	
    	saveMessages(request, messages);
    	return mapping.findForward(forward);
	}

	private ListaMap getCeldasGrafico(Pagina pagina)
	{
		List<Celda> listaDataCeldas = new ArrayList<Celda>();
		listaDataCeldas.addAll(pagina.getCeldas());
		ListaMap listaCeldas = new ListaMap();

		Celda celda = new Celda();

		Integer indice = new Integer(0);

		if (listaDataCeldas != null)
		{
			for (Iterator<Celda> i = listaDataCeldas.iterator(); i.hasNext(); )
			{
				celda = (Celda)i.next();
				
				if ((celda.getIndicadoresCelda() != null) && (celda.getIndicadoresCelda().size() > 0)) 
				{
					celda.setIndice(indice);
					listaCeldas.add(celda, celda.getCeldaId().toString());
				}
				
				indice = new Integer(indice.intValue() + 1);
			}
		}

		return listaCeldas;
	}
	
	private void GetImgGrafico(HttpServletRequest request, Celda celda, GraficoForm graficoForm) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
	{
		  Grafico grafico = new Grafico();
		  
		  if (!graficoForm.getVirtual())
		  {
			  grafico.setGraficoId(celda.getCeldaId());
			  grafico.setNombre(celda.getTitulo());
		  }
		  else
		  {
			  Grafico graficoOriginal = new Grafico();
			  graficoOriginal.setGraficoId(celda.getCeldaId());
			  graficoOriginal.setNombre(celda.getTitulo());
			  
			  grafico.setGraficoId(Long.parseLong(request.getParameter("id")));
			  grafico.setNombre(request.getParameter("nombre"));
			  grafico.setConfiguracion(new com.visiongc.app.strategos.web.struts.graficos.actions.SalvarGraficoAction().CheckLeyendaColor(request.getParameter("data"), graficoOriginal.getConfiguracion()));

		  }
		  graficoForm.setVirtual(true);

		  new com.visiongc.app.strategos.web.struts.graficos.actions.GraficoAction().GetObjeto(graficoForm, grafico);
		  new com.visiongc.app.strategos.web.struts.graficos.actions.GraficoAction().GetGrafico(graficoForm, request);
	}
}