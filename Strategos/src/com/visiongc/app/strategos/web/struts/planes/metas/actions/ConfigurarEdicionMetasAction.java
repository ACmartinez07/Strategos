package com.visiongc.app.strategos.web.struts.planes.metas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.MetasIndicador;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.planes.metas.forms.EditarMetasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.commons.web.NavigationBar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class ConfigurarEdicionMetasAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
	
	    String forward = mapping.getParameter();
	
	    EditarMetasForm editarMetasForm = (EditarMetasForm)form;
	
	    ActionMessages messages = getMessages(request);
	
	    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
	
	    if (cancelar)
	    {
	    	request.getSession().removeAttribute("editarMetasForm");
	    	return getForwardBack(request, 1, true);
	    }
	
	    Long[] indicadorId = new Long[0];
	    Byte frecuencia = null;
	    List<Byte> frecuenciasSeleccionadas = new ArrayList<Byte>();
	
	    editarMetasForm.clear();
	
	    String organizacionId = (String)request.getSession().getAttribute("organizacionId");
	    String strIndicadorId = request.getParameter("indicadorId");
	    String planId = request.getParameter("planId");
	    editarMetasForm.setPerspectivaId(new Long(request.getParameter("perspectivaId")));
	    if ((request.getParameter("verIndicadoresLogroPlan") != null) && (!request.getParameter("verIndicadoresLogroPlan").equals(""))) 
	    	editarMetasForm.setVerIndicadoresLogroPlan(new Boolean(request.getParameter("verIndicadoresLogroPlan")));
	
	    if (((strIndicadorId != null ? 1 : 0) & (strIndicadorId.equals("") ? 0 : 1)) != 0) 
	    {
	    	String[] ids = strIndicadorId.split(",");
	    	indicadorId = new Long[ids.length];
	    	for (int i = 0; i < ids.length; i++) 
	    		indicadorId[i] = new Long(ids[i]);
	    }
	
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	
	    Plan plan = (Plan)strategosIndicadoresService.load(Plan.class, new Long(planId));
	
	    Perspectiva perspectiva = (Perspectiva)strategosIndicadoresService.load(Perspectiva.class, editarMetasForm.getPerspectivaId());
	
	    OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosIndicadoresService.load(OrganizacionStrategos.class, new Long(organizacionId));
	
	    editarMetasForm.setPlanId(plan.getPlanId());
	    editarMetasForm.setNombrePlan(plan.getNombre());
	    editarMetasForm.setNombreOrganizacion(organizacion.getNombre());
	
	    if (indicadorId.length > 0)
	    {
	    	for (int i = indicadorId.length - 1; i > -1; i--) 
	    	{
	    		Long id = indicadorId[i];
	
	    		Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, id);
	
	    		if (indicador == null)
	    			continue;
	    		if (frecuencia == null) 
	    			frecuencia = indicador.getFrecuencia();
	    		if (!frecuenciasSeleccionadas.contains(indicador.getFrecuencia())) 
	    			frecuenciasSeleccionadas.add(indicador.getFrecuencia());
	
	    		MetasIndicador metasIndicador = new MetasIndicador();
	    		metasIndicador.setIndicador(indicador);
	    		editarMetasForm.getMetasIndicadores().add(metasIndicador);
	    	}
	
	    }
	    else
	    {
	    	frecuencia = Frecuencia.getFrecuenciaMensual();
	
	    	if (perspectiva.getPadreId() == null) 
	    	{
	    		if (strategosIndicadoresService.getNumeroIndicadoresPorPlan(editarMetasForm.getPlanId(), null) == 0)
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.configuraredicionmetas.mensajeplan.noindicadores"));
	    	}
	    	else if (strategosIndicadoresService.getNumeroIndicadoresPorPerspectiva(perspectiva.getPerspectivaId(), null) == 0)
	    	{
	    		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.configuraredicionmetas.mensajeperspectiva.noindicadores"));
	    	}	
	    }
	
	    List<Frecuencia> frecuencias = new ArrayList<Frecuencia>();
	    if (frecuenciasSeleccionadas.size() > 0) 
	    {
	    	for (Iterator<Frecuencia> iter = Frecuencia.getFrecuencias().iterator(); iter.hasNext(); ) 
	    	{
	    		Frecuencia frec = (Frecuencia)iter.next();
	    		if (frecuenciasSeleccionadas.contains(frec.getFrecuenciaId()))
	    			frecuencias.add(frec);
	    	}
	    }
	    else 
	    	frecuencias = Frecuencia.getFrecuencias();
	
	    int anoActual = Calendar.getInstance().get(1);
		if (plan != null && plan.getAnoFinal() < anoActual)
		{
			anoActual = plan.getAnoFinal();
			ObjetoClaveValor elementoClaveValor = new ObjetoClaveValor();
			List<ObjetoClaveValor> listaNumeros = new ArrayList<ObjetoClaveValor>();
			for (int i = plan.getAnoInicial().intValue(); i <= plan.getAnoFinal(); i++) 
			{
				elementoClaveValor = new ObjetoClaveValor();
				elementoClaveValor.setClave(String.valueOf(i));
				elementoClaveValor.setValor(String.valueOf(i));
				listaNumeros.add(elementoClaveValor);
			}
			editarMetasForm.setAnos(listaNumeros);
			editarMetasForm.setAnoDesde(anoActual);
			editarMetasForm.setAnoHasta(anoActual);
		}
		else
			editarMetasForm.setAnos(PeriodoUtil.getListaNumeros(new Integer(anoActual), new Byte((byte) 10)));
		
	    editarMetasForm.setFrecuencias(frecuencias);
	    editarMetasForm.setFrecuencia(frecuencia);
	
	    strategosIndicadoresService.close();
	
	    saveMessages(request, messages);
	
	    return mapping.findForward(forward);
	}
}