package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteComiteEjecutivoForm;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.arboles.ComparatorNodoArbol;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.web.struts.actions.LogonAction;

public class ReporteComiteEjecutivoAction extends VgcAction {

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) 
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    super.execute(mapping, form, request, response);
	    
	    String forward = mapping.getParameter();  
	    
	    ActionMessages messages = getMessages(request);
	    
	    boolean cancelar = (request.getParameter("cancelar") != null ? Boolean.parseBoolean(request.getParameter("cancelar")) : false);
		if (cancelar)
			return getForwardBack(request, 1, true);
		
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		Configuracion configuracion = new Configuracion();
		
		//Buscar en tabla configuracion
		ReporteComiteEjecutivoForm reporteComiteEjecutivoForm = (ReporteComiteEjecutivoForm) form;
		configuracion = frameworkService.getConfiguracion("Strategos.Reportes.ComiteEjecutivo.Parametros");
		
		if (configuracion != null)
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
	        DocumentBuilder db = dbf.newDocumentBuilder(); 
	        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8"))); 
	        doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Parametros");
			Element eElement = (Element) nList.item(0);
					
			reporteComiteEjecutivoForm.setFecha(VgcAbstractService.getTagValue("fecha", eElement));
			reporteComiteEjecutivoForm.setVista(Integer.parseInt(VgcAbstractService.getTagValue("vista", eElement)));
			reporteComiteEjecutivoForm.setOrganizaciones(VgcAbstractService.getTagValue("organizaciones", eElement));
			reporteComiteEjecutivoForm.setClases(VgcAbstractService.getTagValue("clases", eElement));
			reporteComiteEjecutivoForm.setIndicadores(VgcAbstractService.getTagValue("indicadores", eElement));
		}
		
	    publishArbolOrganizaciones(request, frameworkService);
	    publishArbolClases(request);
	    publishListaGrupos(request);
		frameworkService.close();

		this.saveMessages(request, messages);
	    
	    return mapping.findForward(forward);
	}
	
	private void publishArbolClases(HttpServletRequest request)  
	{
		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
		Long organizacionId = new Long(53033);
		 
		 ClaseIndicadores claseRoot = strategosClasesIndicadoresService.getClaseRaiz(organizacionId, TipoClaseIndicadores.getTipoClaseIndicadores(), null);
		 		 		  
		request.getSession().setAttribute("parametrosReporteComiteEjecutivoClases", claseRoot); //por ahora
		try 
		{
			TreeviewWeb.publishTree("parametrosReporteComiteEjecutivoArbolClases", claseRoot.getClaseId().toString(), "session", request, true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		abrirArbolClases(claseRoot.getHijos(), "parametrosReporteComiteEjecutivoArbolClases", "session", request);
		strategosClasesIndicadoresService.close();
	}
	
	private void publishListaGrupos(HttpServletRequest request) 
	{
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		Map<String, Object> filtros = new HashMap<String, Object>();

		Long claseId = new Long(61925);
		Long organizacionId = new Long(53033);

		filtros.put("organizacionId", organizacionId);
		filtros.put("claseId", claseId);
		filtros.put("frecuencia", Frecuencia.getFrecuenciaDiaria());
		
		PaginaLista paginaGrupos = new PaginaLista();

		paginaGrupos.setNroPagina(1);
		paginaGrupos.setLista(strategosIndicadoresService.getIndicadores(filtros));
		paginaGrupos.setTotal(paginaGrupos.getLista().size());
		request.getSession().setAttribute("parametrosComiteEjecutivoIndicadores", paginaGrupos);

		strategosIndicadoresService.close();
	}
	
	private void publishArbolOrganizaciones(HttpServletRequest request, FrameworkService frameworkService) 
	{
		try 
		{
			/**
			 * Carga de objetos iniciales de la ficha del usuario Se obtiene el
			 * arbol de organizaciones completo
			 */

			Organizacion organizacionRoot = null;

			List organizaciones = frameworkService.getOrganizacionesRoot(true);

			if (organizaciones.size() > 1) 
			{
				organizacionRoot = new Organizacion();
				Set hijos = new TreeSet(new ComparatorNodoArbol("nombre"));

				for (Iterator i = organizaciones.iterator(); i.hasNext();) 
					hijos.add(i.next());
				organizacionRoot.setOrganizacionId(new Long(0));
				organizacionRoot.setNombre(this.getResources(request).getMessage("action.framework.editarusuario.orgrootdummy"));
				organizacionRoot.setPadreId(null);
				organizacionRoot.setHijos(hijos);
			} 
			else 
				organizacionRoot = (Organizacion) organizaciones.get(0);

			request.getSession().setAttribute("parametrosReporteComiteEjecutivoOrganizacionRoot", organizacionRoot);

			/** Se publica el arbol de organizaciones */
			TreeviewWeb.publishTree("parametrosReporteComiteEjecutivoArbolOrganizaciones", organizacionRoot.getOrganizacionId().toString(), "session", request, true);			

			abrirArbolOrganizaciones(organizacionRoot.getHijos(), "parametrosReporteComiteEjecutivoArbolOrganizaciones", "session", request);

		} 
		catch (Throwable t) 
		{
			throw new ChainedRuntimeException(t.getMessage(), t);
		}
	}

	private void abrirArbolClases(Set conj, String nameObject, String scope, HttpServletRequest request) 
	{
		try 
		{
			for (Iterator i = conj.iterator(); i.hasNext();) 
			{
				ClaseIndicadores hijo = (ClaseIndicadores) i.next();

				if (hijo.getHijos().size() > 0) 
				{
					TreeviewWeb.publishTree(nameObject, hijo.getClaseId().toString(), scope, request);
					abrirArbolClases(hijo.getHijos(), nameObject, scope, request);
				}
			}
		} 
		catch (Throwable t) 
		{
			throw new ChainedRuntimeException(t.getMessage(), t);
		}
	}
	
	private void abrirArbolOrganizaciones(Set conj, String nameObject, String scope, HttpServletRequest request) 
	{
		try {
			for (Iterator i = conj.iterator(); i.hasNext();) 
			{
				Organizacion hijo = (Organizacion) i.next();

				if (hijo.getHijos().size() > 0) 
				{
					TreeviewWeb.publishTree(nameObject, hijo.getOrganizacionId().toString(), scope, request);
					abrirArbolOrganizaciones(hijo.getHijos(), nameObject, scope, request);
				}
			}
		} 
		catch (Throwable t) 
		{
			throw new ChainedRuntimeException(t.getMessage(), t);
		}
	}
}
