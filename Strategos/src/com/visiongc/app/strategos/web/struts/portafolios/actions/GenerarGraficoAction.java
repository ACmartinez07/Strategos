/**
 * 
 */
package com.visiongc.app.strategos.web.struts.portafolios.actions;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.util.TipoObjeto;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm.GraficoTipo;
import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm.GraficoTipoIniciativa;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class GenerarGraficoAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		MessageResources mensajes = getResources(request);
		  
		GraficoForm graficoForm = (GraficoForm)form;
		
		Long planId = request.getParameter("planId") != null && request.getParameter("planId") != "" ? Long.parseLong(request.getParameter("planId")) : null;
		String source = request.getParameter("source");
		Byte tipoGrafico = request.getParameter("tipoGrafico") != null ? Byte.parseByte(request.getParameter("tipoGrafico")) : null;
		
		if (request.getParameter("funcion") != null)
		{
			String funcion = request.getParameter("funcion");
	    	if (funcion.equals("configurar")) 
	    	{
	    		graficoForm.clear();
	    		if (source.equals("Plan") && planId != null)
	    		{
	    			graficoForm.setTipoObjeto(TipoObjeto.getObjetoPlan());

	    			StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
	    			Plan plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));
	    			strategosPlanesService.close();
	    			graficoForm.setObjetoNombre(plan.getNombre());
	    		}

	    		Calendar ahora = Calendar.getInstance();
	    		graficoForm.setAno(ahora.get(1));
	    		graficoForm.setPeriodo(1);
	    		graficoForm.setObjetoId(planId);
	    		graficoForm.setTipoGrafico(tipoGrafico);
	    		graficoForm.setOrganizacionNombre(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
	    		graficoForm.setOrganizacionId(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
	    		graficoForm.setFecha(VgcFormatter.formatearFecha(ahora.getTime(), "formato.fecha.corta"));
	    		graficoForm.setSource(source);
	    		
	    		graficoForm.setTipo(GraficoTipo.getTipoColumna());
	    		if (tipoGrafico.byteValue() == GraficoTipoIniciativa.getGraficoTipoEstatus().byteValue())
	    			graficoForm.setTitulo(mensajes.getMessage("jsp.editar.grafico.portafolio.estatus.titulo"));
	    		else if (tipoGrafico.byteValue() == GraficoTipoIniciativa.getGraficoTipoPorcentajes().byteValue())
	    			graficoForm.setTitulo(mensajes.getMessage("jsp.editar.grafico.portafolio.porcentajes.titulo"));
	    		graficoForm.setTituloEjeY("Número de Iniciativas");
	    		graficoForm.setGraficoNombre(graficoForm.getTitulo());
	    		graficoForm.setVerTituloImprimir(true);
	    		graficoForm.setAjustarEscala(true);

	    		return mapping.findForward(forward);
	    	}
	    	else if (funcion.equals("imprimir") || funcion.equals("saveImagen") || funcion.equals("aplicar")) 
	    	{
	    		request.getSession().setAttribute("configuracionGrafico", request.getParameter("data").replace("[[num]]", "#").replace("[[por]]", "%").toString());
		    	
		    	request.setAttribute("ajaxResponse", "10000");
		    	return mapping.findForward("ajaxResponse");
	    	}
		}

		return mapping.findForward(forward);
	}	
}