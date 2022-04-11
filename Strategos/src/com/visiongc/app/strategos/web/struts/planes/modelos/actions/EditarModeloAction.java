package com.visiongc.app.strategos.web.struts.planes.modelos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosModelosService;
import com.visiongc.app.strategos.planes.model.Modelo;
import com.visiongc.app.strategos.planes.model.ModeloPK;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.web.struts.planes.modelos.forms.EditarModeloForm;
import com.visiongc.app.strategos.web.struts.planes.modelos.forms.EditarModeloForm.SourceType;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.xmldata.XmlControl;
import com.visiongc.commons.util.xmldata.XmlNodo;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarModeloAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();

		EditarModeloForm editarModeloForm = (EditarModeloForm)form;

		ActionMessages messages = getMessages(request);

		Long planId = request.getParameter("planId") != null ? new Long(request.getParameter("planId")) : editarModeloForm.getPlanId();
		Long modeloId = request.getParameter("modeloId") != null ? new Long(request.getParameter("modeloId")) : editarModeloForm.getModeloId();
		String editar = request.getParameter("editar") == null ? "0" : request.getParameter("editar");
		String previsualizar = request.getParameter("previsualizar") == null ? "0" : request.getParameter("previsualizar");
		String evitarrecargar = request.getParameter("evitarrecargar") == null ? "0" : request.getParameter("evitarrecargar");
		Byte source = request.getParameter("source") != null ? new Byte(request.getParameter("source")) : SourceType.getSourceGestionar();
		
		if (planId != null)
			request.getSession().setAttribute("modeloPlanId", planId.toString());
		else if (request.getSession().getAttribute("modeloPlanId") != null)
		{
			planId = new Long((String) request.getSession().getAttribute("modeloPlanId"));
			editar = "1";
			previsualizar = "0";
			evitarrecargar = "0";
		}
		
		boolean bloqueado = false;
		
		StrategosModelosService strategosModelosService = StrategosServiceFactory.getInstance().openStrategosModelosService();

		ModeloPK pk = new ModeloPK();
		pk.setPlanId(planId);
		pk.setModeloId(modeloId);
		
		Modelo modelo = (Modelo)strategosModelosService.load(Modelo.class, pk);

		if (modelo != null)
		{
			if (editar.equals("1")) 
				bloqueado = !strategosModelosService.lockForUpdate(request.getSession().getId(), planId, null);

			editarModeloForm.setBloqueado(new Boolean(bloqueado));
			
			if (modelo != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));

				editarModeloForm.setNombre(modelo.getNombre());
				editarModeloForm.setDescripcion(modelo.getDescripcion());
				if (evitarrecargar.equals("0")) 
					editarModeloForm.setBinario(modelo.getBinario());
			}
		}
		else
		{
			if (evitarrecargar.equals("0"))
			{
				editarModeloForm.clear();
				editarModeloForm.setBinario(null);
			}
		}

		editarModeloForm.setPlanId(planId);
		editarModeloForm.setEditar(editar);
		editarModeloForm.setPrevisualizar(previsualizar);
		editarModeloForm.setDatosArbolPlan(getArbolXml(planId, request));
		editarModeloForm.setBinario(actualizarAlertas(editarModeloForm.getBinario(), strategosModelosService));
		editarModeloForm.setSource(source);
		if (editar.equals("0"))
			editarModeloForm.setBloqueado(true);
		
		strategosModelosService.close();

		saveMessages(request, messages);
		
		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);
		
		return mapping.findForward(forward);
	}

	private String getArbolXml(Long planId, HttpServletRequest request) throws Exception
	{
		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
		
		Object[] arregloIdentificadores = new Object[2];
		arregloIdentificadores[0] = "planId";
		arregloIdentificadores[1] = planId;

		Perspectiva perspectivaRoot = new Perspectiva();

		perspectivaRoot = (Perspectiva)arbolesService.getNodoArbolRaiz(perspectivaRoot, arregloIdentificadores);

		XmlNodo rootNodoArbol = new XmlNodo();
		XmlControl xmlControl = new XmlControl();

		rootNodoArbol.setValorAtributo("nombre", perspectivaRoot.getNombre().trim());
		rootNodoArbol.setValorAtributo("id", perspectivaRoot.getPerspectivaId().toString());
		rootNodoArbol.setValorAtributo("alerta", perspectivaRoot.getAlertaParcial() == null ? "" : perspectivaRoot.getAlertaParcial().toString());
		rootNodoArbol.setValorAtributo("url", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/planes/perspectivas/visualizarPerspectiva.action?perspectivaId=" + perspectivaRoot.getPerspectivaId().toString() + "&amp;mostrarObjetosAsociados=true&amp;vinculoCausaEfecto=1");

		addNodoXml(perspectivaRoot, rootNodoArbol, arbolesService, true, request);
		
		return xmlControl.buildXml(rootNodoArbol);
	}

	private void addNodoXml(Perspectiva nodoPerspectiva, XmlNodo nodoPadreArbol, ArbolesService arbolesService, boolean esRaiz, HttpServletRequest request) throws Exception
	{
		List<?> nodosHijos = arbolesService.getNodosArbolHijos(nodoPerspectiva);
		XmlNodo nodoArbol = new XmlNodo();

		nodoArbol.setValorAtributo("nombre", nodoPerspectiva.getNombre().trim());
		nodoArbol.setValorAtributo("id", nodoPerspectiva.getPerspectivaId().toString());
		nodoArbol.setValorAtributo("alerta", nodoPerspectiva.getAlertaParcial() == null ? "" : nodoPerspectiva.getAlertaParcial().toString());
		nodoArbol.setValorAtributo("url", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/planes/perspectivas/visualizarPerspectiva.action?perspectivaId=" + nodoPerspectiva.getPerspectivaId().toString() + "&amp;mostrarObjetosAsociados=true&amp;vinculoCausaEfecto=1");

		for (Iterator<?> i = nodosHijos.iterator(); i.hasNext(); )
		{
			Perspectiva nodoPerspectivaHijo = (Perspectiva)i.next();

			if (esRaiz)
				addNodoXml(nodoPerspectivaHijo, nodoPadreArbol, arbolesService, false, request);
			else 
				addNodoXml(nodoPerspectivaHijo, nodoArbol, arbolesService, false, request);
		}
		
		if (!esRaiz)
			nodoPadreArbol.addElemLista(nodoArbol, nodoArbol.getValorAtributo("id"));
	}

	private String actualizarAlertas(String binario, StrategosModelosService strategosModelosService)
	{
		String binarioResultante = binario;
		int pos = -1;
		int pos2 = -1;
		int pos3 = -1;
		int pos4 = -1;
		int pos5 = -1;

		String patronIdentificador = "perspectivaId%3D";
		String patronSeparador = "%26";
		String patronAlerta = "\"alerta\" val=\"";
		String patronComillas = "\"";
		
		if (binarioResultante == null) 
			return binarioResultante;

		do
		{
			pos = pos5 + 1;
			pos = binarioResultante.indexOf(patronIdentificador, pos);
			if (pos > -1) 
			{
				pos2 = binarioResultante.indexOf(patronSeparador, pos);
				Long perspectivaId = new Long(binarioResultante.substring(pos + patronIdentificador.length(), pos2));
				Perspectiva perspectiva = (Perspectiva)strategosModelosService.load(Perspectiva.class, perspectivaId);
				Byte valorAlerta = perspectiva != null ? perspectiva.getAlertaParcial() : null;
				String alerta = valorAlerta == null ? "" : valorAlerta.toString();
				
				pos3 = binarioResultante.indexOf(patronAlerta, pos);
				pos4 = pos3 + patronAlerta.length();
				pos5 = binarioResultante.indexOf(patronComillas, pos4);
				binarioResultante = binarioResultante.substring(0, pos4) + alerta + binarioResultante.substring(pos5);
			}
		}
		while (pos > -1);

		return binarioResultante;
	}
}