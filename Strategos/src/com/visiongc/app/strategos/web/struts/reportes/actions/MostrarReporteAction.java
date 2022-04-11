/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.xml.sax.SAXException;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.vistasdatos.model.util.DatoCelda;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.app.strategos.web.struts.vistasdatos.actions.ConfigurarVistaDatosTransversalAction;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.util.WebUtil;

/**
 * @author Kerwin
 *
 */
public final class MostrarReporteAction extends VgcAction
{
	public static final String ACTION_KEY = "MostrarReporteAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		ActionMessages messages = getMessages(request);

		ReporteForm reporteForm = (ReporteForm)form;
		
		Long reporteId = ((request.getParameter("reporteId") != null && request.getParameter("reporteId") != "") ? new Long(request.getParameter("reporteId")) : null);
		if (reporteId != null)
		{
			StrategosReportesService reportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();
			Reporte reporte = null;
    		if (reporteId != null)
    			reporte = (Reporte) reportesService.load(Reporte.class, reporteId);
    		reportesService.close();
    		if (reporte != null)
			    getReporte(reporteForm, reporte, reporteId, request);
    		else
    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
		}

		saveMessages(request, messages);

		return mapping.findForward(forward);
	}
	
	public void getReporte(ReporteForm reporteForm, Reporte reporte, Long reporteId, HttpServletRequest request) throws ParserConfigurationException, SAXException, IOException
	{
		if (request.getParameter("funcion") != null) 
	    {
	    	String funcion = request.getParameter("funcion");
	    	if (funcion.equals("refresh"))
	    	{
	    		String anoInicial = reporteForm.getAnoInicial();
	    		String anoFinal = reporteForm.getAnoFinal();
	    		Integer periodoInicial = reporteForm.getPeriodoInicial();
	    		Integer periodoFinal = reporteForm.getPeriodoFinal();
	    		Boolean acumular = WebUtil.getValorInputCheck(request, "acumular");
	    		
	    		new ConfigurarVistaDatosTransversalAction().ReadXmlProperties(reporteForm);
	    		
	    		reporteForm.setAnoInicial(anoInicial);
	    		reporteForm.setAnoFinal(anoFinal);
	    		reporteForm.setPeriodoInicial(periodoInicial);
	    		reporteForm.setPeriodoFinal(periodoFinal);
	    		reporteForm.setAcumular(acumular);
	    	}
	    	else
	    		Read(reporteForm, reporte, reporteId);
	    }
	    else
	    {
	    	Read(reporteForm, reporte, reporteId);
	    	
			Boolean acumular = (request.getParameter("acumular") != null ? Boolean.parseBoolean(request.getParameter("acumular")) : false);
			String anoInicial = request.getParameter("anoInicial") != null ? request.getParameter("anoInicial") : reporteForm.getAnoInicial();
			String anoFinal = request.getParameter("anoFinal") != null ? request.getParameter("anoFinal") : reporteForm.getAnoFinal();
			Integer periodoInicial = request.getParameter("periodoInicial") != null ? Integer.parseInt(request.getParameter("periodoInicial")) : reporteForm.getPeriodoInicial();
			Integer periodoFinal = request.getParameter("periodoFinal") != null ? Integer.parseInt(request.getParameter("periodoFinal")) : reporteForm.getPeriodoFinal();
			
			reporteForm.setAcumular(acumular);
			reporteForm.setAnoInicial(anoInicial);
			reporteForm.setAnoFinal(anoFinal);
			reporteForm.setPeriodoInicial(periodoInicial);
			reporteForm.setPeriodoFinal(periodoFinal);
	    }
	    
		List<Medicion> mediciones = null;
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		for (int f = 0; f < reporteForm.getMatrizDatos().size(); f++)
		{
			List<DatoCelda> filaDatos = (List<DatoCelda>)reporteForm.getMatrizDatos().get(f);
			for (int k = 0; k < filaDatos.size(); k++)
			{
				DatoCelda datoCelda = (DatoCelda)filaDatos.get(k);
				if (!datoCelda.getEsEncabezado() && datoCelda.getValor() != null)
				{
    				mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(new Long(datoCelda.getValor()), SerieTiempo.getSerieRealId(), Integer.parseInt(reporteForm.getAnoInicial()), Integer.parseInt(reporteForm.getAnoFinal()), Integer.parseInt(reporteForm.getPeriodoInicial().toString()), Integer.parseInt(reporteForm.getPeriodoFinal().toString()), reporteForm.getFrecuencia(), reporteForm.getFrecuencia(), true, true);
    				Medicion medicion = new Medicion();
    				Double valor = 0D;
    				for (int indexMedicion = mediciones.size() - 1; indexMedicion >= 0; indexMedicion--) 
    				{
    				  	medicion = (Medicion)mediciones.get(indexMedicion);
    				  	if (medicion.getValor() != null && medicion.getValor().doubleValue() > valor.doubleValue())
    				  		valor = medicion.getValor();
    				}
    				datoCelda.setValor(VgcFormatter.formatearNumero(valor));
				}
			}
		}
		strategosMedicionesService.close();
	}
	
	private void Read(ReporteForm reporteForm, Reporte reporte, Long reporteId) throws ParserConfigurationException, SAXException, IOException
	{
		reporteForm.clear(); 
		reporteForm.setId(reporteId);
		
		if (reporte != null)
		{
			reporteForm.setConfiguracion(reporte.getConfiguracion());
			reporteForm.setFrecuencias(Frecuencia.getFrecuencias());
			new ConfigurarVistaDatosTransversalAction().ReadXmlProperties(reporteForm);

    		int numeroMaximoPeriodos = 0;
    		if ((Integer.parseInt(reporteForm.getAnoInicial()) % 4 == 0) && (reporteForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
    			numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(reporteForm.getFrecuencia().byteValue(), Integer.parseInt(reporteForm.getAnoInicial()));
    		else if ((Integer.parseInt(reporteForm.getAnoFinal()) % 4 == 0) && (reporteForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
    			numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(reporteForm.getFrecuencia().byteValue(), Integer.parseInt(reporteForm.getAnoFinal()));
    		else 
    			numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(reporteForm.getFrecuencia().byteValue(), Integer.parseInt(reporteForm.getAnoInicial()));
    		reporteForm.setNumeroMaximoPeriodos(numeroMaximoPeriodos);
    		reporteForm.setAnchoTablaDatos(new Integer((reporteForm.getColumnas() + 1) * 200));
		}
	}
}
