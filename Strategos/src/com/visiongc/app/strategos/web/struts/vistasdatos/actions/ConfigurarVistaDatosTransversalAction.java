/**
 * 
 */
package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.reportes.model.Reporte.ReporteCorte;
import com.visiongc.app.strategos.reportes.model.Reporte.ReporteTipo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.vistasdatos.model.util.DatoCelda;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm.ReporteStatus;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class ConfigurarVistaDatosTransversalAction extends VgcAction
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
			return getForwardBack(request, 1, true);
		
		ReporteForm reporteForm = (ReporteForm)form;
		
	    if (request.getParameter("funcion") != null) 
	    {
	    	String funcion = request.getParameter("funcion");
	    	if (funcion.equals("preDiseno")) 
	    	{
	    		if (request.getParameter("data") != null)
	    		{
					request.getSession().setAttribute("configuracionReporte", request.getParameter("data").replace("[[num]]", "#").replace("[[por]]", "%").toString());					
		    	    request.setAttribute("ajaxResponse", "10000");

		    	    return mapping.findForward("ajaxResponse");
	    		}
	    	}

	    	if (funcion.equals("diseno") || funcion.equals("salvar")) 
	    	{
	    		String data = "";
	    		if (request.getParameter("data") != null)
					data = request.getParameter("data").replace("[[num]]", "#").replace("[[por]]", "%");
	    		else if (request.getSession().getAttribute("configuracionReporte") != null)
	    		{
					data = (String) request.getSession().getAttribute("configuracionReporte");
					request.getSession().removeAttribute("configuracionReporte");
	    		}
				
	    		if (!data.isEmpty())
	    		{
	    			reporteForm.setConfiguracion(data);
	    			ReadXmlProperties(reporteForm);
	    		}
	    	}
	    	
	    	if (funcion.equals("salvar") && reporteForm.getConfiguracion() != null) 
	    	{
	    		ActionMessages messages = getMessages(request);
	    		StrategosReportesService reportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();
	    		boolean nuevo = false;
	    		
	      		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");

	    		Long reporteId = ((request.getParameter("id") != null && request.getParameter("id") != "") ? new Long(request.getParameter("id")) : null);
	    		Reporte reporte = null;
	    		
	    		if (reporteId != null)
	    			reporte = (Reporte) reportesService.load(Reporte.class, reporteId);
	    		
	    		if (reporte == null) 
	    		{
	    			nuevo = true;
	    			reporte = new Reporte();

	    			Long organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));
	    			OrganizacionStrategos organizacion = (OrganizacionStrategos) reportesService.load(OrganizacionStrategos.class, organizacionId); 
	    			reporte.setOrganizacion(organizacion);
	    			reporte.setOrganizacionId(organizacionId);
	    			reporte.setReporteId(0L);
	    			reporte.setTipo(ReporteTipo.getTipoVistaDatos());
	    			reporte.setUsuario(usuario);
	    			reporte.setUsuarioId(usuario.getUsuarioId());
	    		}
	    		
	    		reporte.setNombre(reporteForm.getReporteNombre());
	    		reporte.setDescripcion(reporteForm.getDescripcion());
	    		reporte.setPublico(reporteForm.getPublico());
	    		reporte.setConfiguracion(reporteForm.getConfiguracion());
	    		reporte.setCorte(ReporteCorte.getCorteTransversal());
	    		
	      		int respuesta = reportesService.save(reporte, usuario);
	    		
	    		if (respuesta == 10000) 
	    		{
	    			reportesService.unlockObject(request.getSession().getId(), reporte.getReporteId());
	    			reporteForm.setEstatusSave(ReporteStatus.getStatusExito());
	    			if (nuevo)
	    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
	    			else
	    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
	    		} 
	    		else if (respuesta == 10003)
	    		{
	    			reporteForm.setEstatusSave(ReporteStatus.getStatusNoExito());
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
	    		}

	    		reportesService.close();

	    		saveMessages(request, messages);
	    		
	    		reporteForm.setAnchoTablaDatos(new Integer((reporteForm.getColumnas() + 1) * 200));
	    	    request.setAttribute("ajaxResponse", respuesta);

	    	    return mapping.findForward("ajaxResponse");
	    	}
	    }
	    else
	    {
	    	reporteForm.clear();
	    	Long reporteId = ((request.getParameter("reporteId") != null && request.getParameter("reporteId") != "") ? new Long(request.getParameter("reporteId")) : null);
	    	if (reporteId == null)
	    		setNew(reporteForm);
	    	else
	    	{
	    		reporteForm.setId(reporteId);
	    		StrategosReportesService reportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();
	    		Reporte reporte = (Reporte) reportesService.load(Reporte.class, reporteId);
	    		reportesService.close();
	    		if (reporte != null)
	    		{
	    			ActionMessages messages = getMessages(request);
	    			boolean verForm = getPermisologiaUsuario(request).tienePermiso("VISTA_DATOS_VIEW");
	    			boolean editarForm = getPermisologiaUsuario(request).tienePermiso("VISTA_DATOS_EDIT");
	    			
	    			if (verForm && !editarForm)
	    			{
	    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
	    				reporteForm.setBloqueado(true);
	    			}
	    			else if (!verForm && !editarForm)
	    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));
	    			
	    			reporteForm.setConfiguracion(reporte.getConfiguracion());
		    		reporteForm.setFrecuencias(Frecuencia.getFrecuencias());
		    		ReadXmlProperties(reporteForm);

		    		int numeroMaximoPeriodos = 0;
		    		if ((Integer.parseInt(reporteForm.getAnoInicial()) % 4 == 0) && (reporteForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
		    			numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(reporteForm.getFrecuencia().byteValue(), Integer.parseInt(reporteForm.getAnoInicial()));
		    		else if ((Integer.parseInt(reporteForm.getAnoFinal()) % 4 == 0) && (reporteForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
		    			numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(reporteForm.getFrecuencia().byteValue(), Integer.parseInt(reporteForm.getAnoFinal()));
		    		else 
		    			numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(reporteForm.getFrecuencia().byteValue(), Integer.parseInt(reporteForm.getAnoInicial()));
		    		reporteForm.setNumeroMaximoPeriodos(numeroMaximoPeriodos);
	    			
	    			saveMessages(request, messages);
	    		}
	    		else
	    			setNew(reporteForm);
	    	}
	    }
		
		reporteForm.setAnchoTablaDatos(new Integer((reporteForm.getColumnas() + 1) * 200));
		
		return mapping.findForward(forward);
	}
	
	private void setNew(ReporteForm reporteForm)
	{
		reporteForm.setFrecuencias(Frecuencia.getFrecuencias());
		reporteForm.setFrecuencia(Frecuencia.getFrecuenciaMensual());

		Calendar ahora = Calendar.getInstance();
		  
		reporteForm.setPeriodoInicial(new Integer("1"));
		reporteForm.setPeriodoFinal(new Integer("12"));
		reporteForm.setAnoInicial(Integer.toString(ahora.get(1)));
		reporteForm.setAnoFinal(reporteForm.getAnoInicial());
		reporteForm.setFilas(1);
		reporteForm.setColumnas(1);
		
		Calendar fecha = PeriodoUtil.getDateByPeriodo(reporteForm.getFrecuencia(), Integer.parseInt(reporteForm.getAnoInicial()), reporteForm.getPeriodoInicial(), true);
		reporteForm.setFechaDesde(VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta"));

		fecha = PeriodoUtil.getDateByPeriodo(reporteForm.getFrecuencia(), Integer.parseInt(reporteForm.getAnoFinal()), reporteForm.getPeriodoFinal(), false);
		reporteForm.setFechaHasta(VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta"));
		
		int numeroMaximoPeriodos = 0;
		if ((Integer.parseInt(reporteForm.getAnoInicial()) % 4 == 0) && (reporteForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
			numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(reporteForm.getFrecuencia().byteValue(), Integer.parseInt(reporteForm.getAnoInicial()));
		else if ((Integer.parseInt(reporteForm.getAnoFinal()) % 4 == 0) && (reporteForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
			numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(reporteForm.getFrecuencia().byteValue(), Integer.parseInt(reporteForm.getAnoFinal()));
		else 
			numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(reporteForm.getFrecuencia().byteValue(), Integer.parseInt(reporteForm.getAnoInicial()));

		reporteForm.setNumeroMaximoPeriodos(numeroMaximoPeriodos);
		reporteForm.setPublico(true);
		setMatrizDatos(reporteForm);
	}
	
  	private void setMatrizDatos(ReporteForm reporteForm)
  	{
  		List<List<DatoCelda>> matrizDatos = new ArrayList<List<DatoCelda>>();
  		List<DatoCelda> filaDatos = new ArrayList<DatoCelda>();
  		String valorCelda = null;
  		DatoCelda datoCelda = new DatoCelda();

  		if ((reporteForm.getFilas() != null) && (reporteForm.getFilas() > 0) && (reporteForm.getColumnas() != null) && (reporteForm.getColumnas() > 0))
  		{
  			for (int x = 0; x < reporteForm.getFilas(); x++) 
  			{
  				filaDatos = new ArrayList<DatoCelda>();
  				for (int y = 0; y < reporteForm.getColumnas(); y++)
  				{
  	  				valorCelda = "";
  	  				
  	  				datoCelda = new DatoCelda();
  	  				datoCelda.setValor(valorCelda);
  	  				datoCelda.setEsAlerta(new Boolean(false));
  	  				datoCelda.setAlineacion("center");
  	  				
  	  				filaDatos.add(datoCelda);
  				}

  				matrizDatos.add(filaDatos);
  			}
  		}
  		
  		reporteForm.setMatrizDatos(matrizDatos);
  	}
  	
	public void ReadXmlProperties(ReporteForm reporte) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory  =  DocumentBuilderFactory.newInstance();;
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(TextEncoder.deleteCharInvalid(reporte.getConfiguracion()))));
		NodeList lista = doc.getElementsByTagName("properties");
		 
		for (int i = 0; i < lista.getLength() ; i ++) 
		{
			Node node = lista.item(i);
			Element elemento = (Element) node;
			NodeList nodeLista = null;
			Node valor = null;
			
			if (elemento.getElementsByTagName("nombre").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("nombre").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				reporte.setReporteNombre(valor != null ? valor.getNodeValue() : "");
			}

			if (elemento.getElementsByTagName("descripcion").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("descripcion").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				reporte.setDescripcion(valor != null ? valor.getNodeValue() : "");
			}
			
			if (elemento.getElementsByTagName("publico").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("publico").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				reporte.setPublico(valor != null ? Boolean.parseBoolean(valor.getNodeValue()) : false);
			}
			
			if (elemento.getElementsByTagName("filas").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("filas").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				reporte.setFilas(valor != null ? Integer.parseInt(valor.getNodeValue()) : 1);
			}

			if (elemento.getElementsByTagName("columnas").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("columnas").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				reporte.setColumnas(valor != null ? Integer.parseInt(valor.getNodeValue()) : 1);
			}

			if (elemento.getElementsByTagName("frecuencia").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("frecuencia").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				reporte.setFrecuencia((valor != null && !valor.getNodeValue().equals("")) ? Byte.parseByte(valor.getNodeValue()) : Frecuencia.getFrecuenciaMensual());
			}
			
			if (elemento.getElementsByTagName("anoInicial").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("anoInicial").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				reporte.setAnoInicial((valor != null && !valor.getNodeValue().equals("")) ? valor.getNodeValue() : "");
			}

			if (elemento.getElementsByTagName("periodoInicial").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("periodoInicial").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				reporte.setPeriodoInicial((valor != null && !valor.getNodeValue().equals("")) ? Integer.parseInt(valor.getNodeValue()) : 1);
			}

			if (elemento.getElementsByTagName("anoFinal").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("anoFinal").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				reporte.setAnoFinal((valor != null && !valor.getNodeValue().equals("")) ? valor.getNodeValue() : "");
			}

			if (elemento.getElementsByTagName("periodoFinal").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("periodoFinal").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				reporte.setPeriodoFinal((valor != null && !valor.getNodeValue().equals("")) ? Integer.parseInt(valor.getNodeValue()) : 12);
			}
		}
		
		lista = doc.getElementsByTagName("property");
		if (lista.getLength() > 0)
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			Indicador indicador;
			List<List<DatoCelda>> matrizDatos = new ArrayList<List<DatoCelda>>();
	  		List<DatoCelda> filaDatos = new ArrayList<DatoCelda>();
	  		DatoCelda datoCelda = new DatoCelda();

	  		if ((reporte.getFilas() != null) && (reporte.getFilas() > 0) && (reporte.getColumnas() != null) && (reporte.getColumnas() > 0))
	  		{
	  			boolean hayCelda = false;
	  			for (int x = 0; x < reporte.getFilas(); x++) 
	  			{
	  				filaDatos = new ArrayList<DatoCelda>();
	  				for (int y = 0; y < reporte.getColumnas(); y++)
	  				{
	  					hayCelda = false;
	  					for (int i = 0; i < lista.getLength() ; i ++) 
	  					{
	  						Node node = lista.item(i);
	  						Element elemento = (Element) node;
	  						NodeList nodeLista = null;
	  						Node fila = null;
	  						Node columna = null;
	  						Node valor = null;
	  						
  							nodeLista = elemento.getElementsByTagName("fila").item(0).getChildNodes();
  							fila = (Node) nodeLista.item(0);

  							nodeLista = elemento.getElementsByTagName("columna").item(0).getChildNodes();
  							columna = (Node) nodeLista.item(0);
  							if (x == new Long(fila.getNodeValue()) && y == new Long(columna.getNodeValue()))
							{
  								hayCelda = true;
  								datoCelda = new DatoCelda();
  								if (elemento.getElementsByTagName("esEncabezado") != null && elemento.getElementsByTagName("esEncabezado").item(0) != null)
  								{
  									nodeLista = elemento.getElementsByTagName("esEncabezado").item(0).getChildNodes();
  	  	  							valor = (Node) nodeLista.item(0);
  	  	  							if (Boolean.parseBoolean(valor.getNodeValue()))
  	  	  							{
  	  	  								if (x == 0)
  	  	  									datoCelda.setAlineacion("center");
  	  	  								datoCelda.setEsEncabezado(true);
  	  	  	  							
  	  	  								nodeLista = elemento.getElementsByTagName("nombreCelda").item(0).getChildNodes();
  	  	  	  							valor = (Node) nodeLista.item(0);
  	  	  	  							if (valor != null)
  	  	  	  								datoCelda.setValor(valor.getNodeValue());
  	  	  	  							filaDatos.add(datoCelda);
  	  	  							}
  	  	  							else
  	  	  							{
  	  	  								datoCelda.setEsEncabezado(false);
  	  	  								nodeLista = elemento.getElementsByTagName("indicadorId").item(0).getChildNodes();
  	  	  	  							valor = (Node) nodeLista.item(0);
  	  	  	  							if (valor != null)
  	  	  	  							{
  	  	  	  								datoCelda.setValor(valor.getNodeValue());
  	  	  	  								indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(datoCelda.getValor()));
  	  	  	  								if (indicador != null)
  	  	  	  									datoCelda.setNombre(indicador.getNombre());
  	  	  	  							}
  	  	  	  							filaDatos.add(datoCelda);
  	  	  							}
  								}
  								else
  								{
  	  								if (x == 0)
  	  								{
  	  									datoCelda.setAlineacion("center");
  	  	  								datoCelda.setEsEncabezado(true);
  	  								}
  	  								else
  	  									datoCelda.setEsEncabezado(false);
  	  								datoCelda.setValor("");
  	  	  							filaDatos.add(datoCelda);
  								}
							}
	  					}
	  					if (!hayCelda)
	  					{
	  	  	  				datoCelda = new DatoCelda();
	  	  	  				datoCelda.setEsEncabezado(false);
	  	  	  				datoCelda.setValor("");
	  	  	  				if (y == 0 || x == 0)
	  	  	  					datoCelda.setAlineacion("center");
	  	  	  				
	  	  	  				filaDatos.add(datoCelda);
	  					}
	  				}

	  				matrizDatos.add(filaDatos);
	  			}
	  		}
	  		
	  		reporte.setMatrizDatos(matrizDatos);
	  		
	  		strategosIndicadoresService.close();
		}
		else
			setMatrizDatos(reporte);
	}
}
