package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.ProtegerLiberarIniciativasForm;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm.ProtegerLiberarAccion;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm.ProtegerLiberarOrigen;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm.ProtegerLiberarSeleccion;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm.ProtegerLiberarStatus;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.util.FrameworkConnection;

public class ProtegerLiberarIniciativaAction extends VgcAction{
	
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		
		MessageResources mensajes = getResources(request);

		
		ProtegerLiberarIniciativasForm protegerLiberarIniciativasForm = (ProtegerLiberarIniciativasForm)form;
		
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
		ActionMessages messages = getMessages(request);
		
		// status  
		
		if (configuracion == null)
		{
			protegerLiberarIniciativasForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoConfigurado());
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
			saveMessages(request, messages);
		}
		else
		{
			//XML
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
	        DocumentBuilder db = dbf.newDocumentBuilder(); 
	        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8"))); 
	        doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("properties");
			Element eElement = (Element) nList.item(0);
			/** Se obtiene el FormBean haciendo el casting respectivo */
			String url = VgcAbstractService.getTagValue("url", eElement);;
			String driver = VgcAbstractService.getTagValue("driver", eElement);
			String user = VgcAbstractService.getTagValue("user", eElement);
			String password = VgcAbstractService.getTagValue("password", eElement);

			if (!new FrameworkConnection().testConnection(url, driver, user, new com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm().getPasswordConexionDecriptado(password)))
			{
				protegerLiberarIniciativasForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoConfigurado());
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.mediciones.status.configuracion.noexiste"));
				saveMessages(request, messages);
			}
			else{
				protegerLiberarIniciativasForm.setStatus(ProtegerLiberarStatus.getImportarStatusSuccess());
			} 
		}
		
		
		// logica de la accion 
		
		Byte origen = request.getParameter("origen") != null ? Byte.parseByte(request.getParameter("origen")) : null;
		Boolean proteger = request.getParameter("proteger") != null ? Boolean.parseBoolean(request.getParameter("proteger")) : null;
		Long organizacionId = request.getParameter("organizacionId") != null ? Long.parseLong(request.getParameter("organizacionId")) : null;
		Long iniciativaId = request.getParameter("iniciativaId") != null ? Long.parseLong(request.getParameter("iniciativaId")) : null;
		Long planId= new Long(0);
				
		if(request.getParameter("planId") != null && request.getParameter("planId") != ""){
			planId= Long.parseLong(request.getParameter("planId"));
		}	
		
	
		
		request.getSession().removeAttribute("actualizarFormaProteger");
		
		Calendar ahora = Calendar.getInstance();
		protegerLiberarIniciativasForm.setAno(ahora.get(1));
		protegerLiberarIniciativasForm.setMesInicial(1);
		protegerLiberarIniciativasForm.setMesFinal(12);
		protegerLiberarIniciativasForm.setOrigen(origen);
		protegerLiberarIniciativasForm.setPlanId(planId);
		protegerLiberarIniciativasForm.setSerieId((byte)0);
		protegerLiberarIniciativasForm.setIniciativaId(iniciativaId);
		if (proteger)
			protegerLiberarIniciativasForm.setAccion(ProtegerLiberarAccion.getAccionBloquear());
		else if (!proteger)
			protegerLiberarIniciativasForm.setAccion(ProtegerLiberarAccion.getAccionLiberar());
		
		protegerLiberarIniciativasForm.setOrganizacionId(organizacionId);
		protegerLiberarIniciativasForm.setTipoSeleccion(protegerLiberarIniciativasForm.getSeleccionIniciativa());
		protegerLiberarIniciativasForm.setAltoForma("600px");
		
		StrategosTipoProyectoService strategosTiposProyectoService = StrategosServiceFactory.getInstance().openStrategosTipoProyectoService();
		Map<String, String> filtrosTipo = new HashMap();
		PaginaLista paginaTipos = strategosTiposProyectoService.getTiposProyecto(0, 0, "tipoProyectoId", "asc", true, filtrosTipo);
		strategosTiposProyectoService.close();
		
		if(paginaTipos.getLista().size() > 0) {
			protegerLiberarIniciativasForm.setTipos(paginaTipos.getLista());
		}
		
		
		
		
	  	return mapping.findForward(forward);
	}
	
}
