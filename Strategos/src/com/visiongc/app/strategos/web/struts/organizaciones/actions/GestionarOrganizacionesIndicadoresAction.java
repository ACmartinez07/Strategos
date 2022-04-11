package com.visiongc.app.strategos.web.struts.organizaciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.web.struts.general.forms.EditarGeneralInformeForms;
import com.visiongc.app.strategos.web.struts.organizaciones.forms.GestionarOrganizacionesForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;

import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Modulo.ModuloType;
import com.visiongc.framework.web.struts.alertas.forms.AlertaForm;
import com.visiongc.framework.web.struts.forms.NavegadorForm;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class GestionarOrganizacionesIndicadoresAction extends VgcAction
{
	public static final String ACTION_KEY = "GestionarOrganizacionesAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.clear();
		navBar.agregarUrl(TreeviewWeb.getUrlTreeview(url), nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		getBarraAreas(request, "strategos").setBotonSeleccionado("organizaciones");

		String forward = mapping.getParameter();
		ActionMessages messages = getMessages(request);
		GestionarOrganizacionesForm gestionarOrganizacionesForm = (GestionarOrganizacionesForm)form;
		
		gestionarOrganizacionesForm.setMostrarMisionVision(new Boolean(request.getParameter("mostrarMisionVision")));
		
		request.getSession().setAttribute("alerta", new com.visiongc.framework.web.struts.alertas.actions.GestionarAlertasAction().getAlerta(getUsuarioConectado(request)));
		
		// Test Execute Store Procedure
		//CalculoIndicadorService calculoIndicadorService = CalculoServiceFactory.getInstance().openCalculoIndicadorService();
		//calculoIndicadorService.setCalcularIndicadores();
		//calculoIndicadorService.close();
		
		StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
		request.getSession().setAttribute("tipoDefaultSentEmail", strategosResponsablesService.getTipoCorreoPorDefectoSent(this.getUsuarioConectado(request).getUsuarioId()));
		strategosResponsablesService.close();
		
		NavegadorForm problema = new com.visiongc.app.strategos.web.struts.general.actions.EditarGeneralActions().getModulo(ModuloType.Problema.Problema, true);
		NavegadorForm riesgo = new com.visiongc.app.strategos.web.struts.general.actions.EditarGeneralActions().getModulo(ModuloType.Riesgo.Riesgo, false);  
		NavegadorForm plan = new com.visiongc.app.strategos.web.struts.general.actions.EditarGeneralActions().getModulo(ModuloType.Plan.Plan, true);
		NavegadorForm iniciativas = new com.visiongc.app.strategos.web.struts.general.actions.EditarGeneralActions().getModulo(ModuloType.Iniciativas.Iniciativas, true);
		NavegadorForm presentacionEjecutiva = new com.visiongc.app.strategos.web.struts.general.actions.EditarGeneralActions().getModulo(ModuloType.PresentacionEjecutiva.PresentacionEjecutiva, false);
		NavegadorForm portafolio = new com.visiongc.app.strategos.web.struts.general.actions.EditarGeneralActions().getModulo(ModuloType.Portafolio.Portafolio, false);
		NavegadorForm vistaDatos = new com.visiongc.app.strategos.web.struts.general.actions.EditarGeneralActions().getModulo(ModuloType.VistaDatos.VistaDatos, false);
		//NavegadorForm actividades = new com.visiongc.app.strategos.web.struts.general.actions.EditarGeneralActions().getModulo(ModuloType.Actividades.Actividades, true);
		
		gestionarOrganizacionesForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("ORGANIZACION_VIEWALL"));
		gestionarOrganizacionesForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("ORGANIZACION_EDIT"));
		
		// Control de Modulos
		EditarGeneralInformeForms informe = new com.visiongc.app.strategos.web.struts.general.actions.EditarGeneralActions().getInforme();
		request.getSession().setAttribute("activarInforme", informe);
		AlertaForm alerta = new AlertaForm();
		if (request.getSession().getAttribute("activarInformeAlerta") == null && informe.getMostrarAlerta())
		{
			alerta.setHayAlertas(true);
			gestionarOrganizacionesForm.setAlerta(informe.getAlerta());
			gestionarOrganizacionesForm.setDescripcion(informe.getDescripcion());
			request.getSession().setAttribute("activarInformeAlerta", alerta);
		}
		else if (request.getSession().getAttribute("activarInformeAlerta") != null)
		{
			gestionarOrganizacionesForm.setAlerta(null);
			gestionarOrganizacionesForm.setDescripcion(null);
			alerta.setHayAlertas(false);
			request.getSession().setAttribute("activarInformeAlerta", alerta);
		}
		if (problema != null && problema.getObjeto() != null)
			request.getSession().setAttribute("activarProblema", problema);
		if (riesgo != null && riesgo.getObjeto() != null)
			request.getSession().setAttribute("activarRiesgo", riesgo);
		if (plan != null && plan.getObjeto() != null)
			request.getSession().setAttribute("activarPlan", plan);
		if (iniciativas != null && iniciativas.getObjeto() != null)
			request.getSession().setAttribute("activarIniciativa", iniciativas);
		if (presentacionEjecutiva != null && presentacionEjecutiva.getObjeto() != null)
			request.getSession().setAttribute("activarPresentacionEjecutiva", presentacionEjecutiva);
		if (portafolio != null && portafolio.getObjeto() != null)
			request.getSession().setAttribute("activarPortafolio", portafolio);
		if (vistaDatos != null && vistaDatos.getObjeto() != null)
			request.getSession().setAttribute("activarVistaDatos", vistaDatos);

		String selectedOrgId = request.getParameter("selectedOrgId");
		String openOrgId = request.getParameter("openOrgId");
		String closeOrgId = request.getParameter("closeOrgId");

    	ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
    	
    	new com.visiongc.app.strategos.web.struts.configuracion.actions.VerificarData().SetData(getUsuarioConectado(request));

    	request.getSession().removeAttribute("planActivoId");
    	OrganizacionStrategos organizacion = null;
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		ConfiguracionUsuario foco = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Foco.Organizacion.Arbol", "OrganizacionId");
    	if (request.getSession().getAttribute("organizacion") == null)
    	{
    		organizacion = new OrganizacionStrategos();
    		organizacion = (OrganizacionStrategos)arbolesService.getNodoArbolRaiz(organizacion);

    		if (organizacion == null)
    		{
    			StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
    			organizacion = strategosOrganizacionesService.crearOrganizacionRaiz(getUsuarioConectado(request));
    			strategosOrganizacionesService.close();
    		}	

    		TreeviewWeb.publishTree("arbolOrganizaciones", organizacion.getOrganizacionId().toString(), "session", request, true);
    		arbolesService.refreshNodosArbol(organizacion, (String)request.getSession().getAttribute("arbolOrganizaciones"), TreeviewWeb.getSeparadorTokens(), true);

    		request.getSession().setAttribute("organizacionRoot", organizacion);
    		
    		if (foco != null && foco.getData() != null)
    		{
    			organizacion = SetFoco(new Long(foco.getData()), arbolesService, organizacion, request);
    			arbolesService.refreshNodosArbol((OrganizacionStrategos)request.getSession().getAttribute("organizacionRoot"), (String)request.getSession().getAttribute("arbolOrganizaciones"), TreeviewWeb.getSeparadorTokens(), true, organizacion.getOrganizacionId());
    		}
    	}
    	else
    	{
    		if (request.getAttribute("GestionarOrganizacionesAction.reloadId") != null) 
    			organizacion = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, (Long)request.getAttribute("GestionarOrganizacionesAction.reloadId"));
    		else if ((selectedOrgId != null) && (!selectedOrgId.equals(""))) 
    			organizacion = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, new Long(selectedOrgId));
    		else if ((openOrgId != null) && (!openOrgId.equals(""))) 
    		{
    			TreeviewWeb.publishTreeOpen("arbolOrganizaciones", openOrgId, "session", request);
    			organizacion = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, new Long(openOrgId));
    		} 
    		else if ((closeOrgId != null) && (!closeOrgId.equals(""))) 
    		{
    			TreeviewWeb.publishTreeClose("arbolOrganizaciones", closeOrgId, "session", request);
    			organizacion = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, new Long(closeOrgId));
    		} 
    		else 
    			organizacion = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, new Long((String)request.getSession().getAttribute("organizacionId")));
    		
    		Long reloadId;
    		if (organizacion == null) 
    		{
    			organizacion = (OrganizacionStrategos)request.getSession().getAttribute("organizacionRoot");
    			reloadId = organizacion.getOrganizacionId();
    			TreeviewWeb.publishTree("arbolOrganizaciones", organizacion.getOrganizacionId().toString(), "session", request, true);

    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
    		} 
    		else 
    		{
    			reloadId = organizacion.getOrganizacionId();
    			if (closeOrgId == null) 
    				TreeviewWeb.publishTreeOpen("arbolOrganizaciones", reloadId.toString(), "session", request);
    		}
    		
			if (foco == null)
			{
				foco = new ConfiguracionUsuario(); 
				ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
				pk.setConfiguracionBase("Strategos.Foco.Organizacion.Arbol");
				pk.setObjeto("OrganizacionId");
				pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
				foco.setPk(pk);
			}
			foco.setData(reloadId.toString());
			frameworkService.saveConfiguracionUsuario(foco, this.getUsuarioConectado(request));
    		
    		arbolesService.refreshNodosArbol((OrganizacionStrategos)request.getSession().getAttribute("organizacionRoot"), (String)request.getSession().getAttribute("arbolOrganizaciones"), TreeviewWeb.getSeparadorTokens(), true, reloadId);
    	}
    	frameworkService.close();

		request.getSession().setAttribute("organizacion", organizacion);
		request.getSession().setAttribute("organizacionId", organizacion.getOrganizacionId().toString());
		request.getSession().setAttribute("organizacionNombre", organizacion.getNombre());
		
		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		List<OrganizacionStrategos> organizacionesHijas = strategosOrganizacionesService.getOrganizacionHijas(organizacion.getOrganizacionId(), false);
		strategosOrganizacionesService.close();
		request.getSession().setAttribute("hijos", ((Boolean)(organizacionesHijas.size() > 0)).toString());

		setMemosOrganizacion(organizacion.getOrganizacionId(), gestionarOrganizacionesForm);

    	arbolesService.close();

    	saveMessages(request, messages);

    	return mapping.findForward(forward);
	}

	private void setMemosOrganizacion(Long organizacionId, GestionarOrganizacionesForm gestionarOrganizacionesForm)
	{
		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, organizacionId);
		gestionarOrganizacionesForm.setMision(null);
		gestionarOrganizacionesForm.setVision(null);
		gestionarOrganizacionesForm.setLineamientosEstrategicos(null);
		
		for (Iterator<?> i = organizacionStrategos.getMemos().iterator(); i.hasNext(); ) 
		{
			MemoOrganizacion oMemo = (MemoOrganizacion)i.next();
			Integer tipoMemo = oMemo.getPk().getTipo();
			if (tipoMemo.equals(new Integer(3)))
				gestionarOrganizacionesForm.setMision(oMemo.getDescripcion());
			else if (tipoMemo.equals(new Integer(4)))
				gestionarOrganizacionesForm.setVision(oMemo.getDescripcion());
			else if (tipoMemo.equals(new Integer(6))) 
				gestionarOrganizacionesForm.setLineamientosEstrategicos(oMemo.getDescripcion());
		}

		strategosOrganizacionesService.close();
	}
	
	private OrganizacionStrategos SetFoco(Long organizacionId, ArbolesService arbolesService, OrganizacionStrategos organizacionRoot, HttpServletRequest request) throws Exception
	{
		OrganizacionStrategos organizacion = null;
		if (organizacionRoot.getOrganizacionId().longValue() != organizacionId.longValue())
		{
			String [] idsPadres = GetPathPadre(organizacionId, arbolesService, organizacionId.toString()).split(",");
			for (int i = 0; i < idsPadres.length; i++)
				if (!idsPadres[i].equals(""))
					TreeviewWeb.publishTreeOpen("arbolOrganizaciones", idsPadres[i], "session", request);
			
			organizacion = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, organizacionId);
		}

		if (organizacion == null)
			organizacion = organizacionRoot;
		
		return organizacion;
	}
	
	private String GetPathPadre(Long organizacionId, ArbolesService arbolesService, String path)
	{
		OrganizacionStrategos organizacion = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, organizacionId);
		if (organizacion != null && organizacion.getPadreId() != null)
			path = GetPathPadre(organizacion.getPadreId(), arbolesService, organizacion.getPadreId().toString() + "," + path);
		
		return path;
	}
}