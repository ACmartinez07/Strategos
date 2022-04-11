/**
 * 
 */
package com.visiongc.app.strategos.web.struts.configuracion.actions;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.util.ConfiguracionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.ConfiguracionNegativo;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.util.ConfiguracionIniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.CorreoIniciativa;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.util.ConfiguracionResponsable;
import com.visiongc.app.strategos.web.struts.configuracion.forms.EditarConfiguracionSistemaForm;

import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class EditarConfiguracionSistemaAction extends VgcAction 
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) 
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarConfiguracionSistemaForm editarConfiguracionSistemaForm = (EditarConfiguracionSistemaForm) form;
		if (request.getParameter("funcion") != null)
		{
			String funcion = request.getParameter("funcion");
	    	if (funcion.equals("salvar")) 
	    	{
	    		String pantalla = request.getParameter("Pantalla");
	    		
	    		ActionMessages messages = this.getMessages(request);
	    		int respuesta = VgcReturnCode.DB_OK;
	    		if (pantalla == null)
	    		{
		    		respuesta = setConfiguracionIniciativa(editarConfiguracionSistemaForm, request);
		    		if (respuesta == VgcReturnCode.DB_OK)
		    			respuesta = setConfiguracionPlan(editarConfiguracionSistemaForm, request);
		    		if (respuesta == VgcReturnCode.DB_OK)
		    			respuesta = setConfiguracionResponsable(editarConfiguracionSistemaForm, request);
		    		if (respuesta == VgcReturnCode.DB_OK)
		    			respuesta = setConfiguracionNegativo(editarConfiguracionSistemaForm, request);		
		    		if (respuesta == VgcReturnCode.DB_OK)
		    			respuesta = setConfiguracionCorreoIniciativa(editarConfiguracionSistemaForm, request);
		    		if (respuesta == VgcReturnCode.DB_OK)
		    			respuesta = setConfiguracionIndicador(editarConfiguracionSistemaForm, request);
		    		
		    		if (respuesta == VgcReturnCode.DB_OK)
		    		{
		    			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_SAVE);
		    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
		    		}
		    		else
		    		{
		    			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_SAVE_ERROR);
		    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.no.ok"));
		    		}
				    saveMessages(request, messages);		
	    		}
	    		else if (pantalla.equals("Email"))
	    		{
	    			respuesta = setCorreoDefecto(editarConfiguracionSistemaForm, request);
		    		if (respuesta == VgcReturnCode.DB_OK)
		    			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_SAVE);
		    		else
		    			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_SAVE_ERROR);
	    		}

	    		return mapping.findForward(forward);
	    	}
		}

		editarConfiguracionSistemaForm.clear();
		int respuesta = getConfiguracionIniciativa(editarConfiguracionSistemaForm);
		if (respuesta == VgcReturnCode.DB_OK)
			getConfiguracionPlan(editarConfiguracionSistemaForm);
		if (respuesta == VgcReturnCode.DB_OK)
			getConfiguracionResponsable(editarConfiguracionSistemaForm, this.getUsuarioConectado(request));
		if (respuesta == VgcReturnCode.DB_OK)
			getConfiguracionNegativo(editarConfiguracionSistemaForm);
		if (respuesta == VgcReturnCode.DB_OK)
			getConfiguracionCorreoIniciativa(editarConfiguracionSistemaForm);
		if (respuesta == VgcReturnCode.DB_OK)
			getConfiguracionIndicador(editarConfiguracionSistemaForm);
		
		return mapping.findForward(forward);
	}
	
	private int setConfiguracionIniciativa(EditarConfiguracionSistemaForm editarConfiguracionSistemaForm, HttpServletRequest request)
	{
		int respuesta = VgcReturnCode.DB_OK;
		MessageResources mensajes = getResources(request);
		
		try
		{
			Configuracion configuracion = new Configuracion();
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "iniciativa", null);
			document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

			Element raiz = document.createElement("properties");  // creamos el elemento raiz
			document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

			Element elemento = document.createElement("nombre"); //creamos un nuevo elemento
			Text text = document.createTextNode(editarConfiguracionSistemaForm.getIniciativaNombre()); //Ingresamos la info
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			Element indicadores = document.createElement("indicadores");  // creamos el elemento raiz
			raiz.appendChild(indicadores);

			// Indicador de Avance
			Element indicadorElement = document.createElement("indicador");  // creamos el elemento raiz
			indicadores.appendChild(indicadorElement);

			elemento = document.createElement("tipo");
			text = document.createTextNode(TipoFuncionIndicador.getTipoFuncionSeguimiento().toString());
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);
			
			elemento = document.createElement("nombre");
			text = document.createTextNode(editarConfiguracionSistemaForm.getIniciativaIndicadorAvanceNombre() != null ? editarConfiguracionSistemaForm.getIniciativaIndicadorAvanceNombre() : mensajes.getMessage("jsp.configuracion.sistema.iniciativas.indicador.avance.nombre"));
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);

			elemento = document.createElement("crear");
			text = document.createTextNode((editarConfiguracionSistemaForm.getIniciativaIndicadorAvanceMostrar() != null && !editarConfiguracionSistemaForm.getIniciativaIndicadorAvanceMostrar()) ? "0" : "1");
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);
			
			elemento = document.createElement("anteponer");
			text = document.createTextNode(editarConfiguracionSistemaForm.getIniciativaIndicadorAvanceAnteponer() ? "1" : "0");
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);

			// Indicador de Presupuesto
			indicadorElement = document.createElement("indicador"); 
			indicadores.appendChild(indicadorElement);

			elemento = document.createElement("tipo");
			text = document.createTextNode(TipoFuncionIndicador.getTipoFuncionPresupuesto().toString());
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);
			
			elemento = document.createElement("nombre");
			text = document.createTextNode(editarConfiguracionSistemaForm.getIniciativaIndicadorPresupuestoNombre() != null ? editarConfiguracionSistemaForm.getIniciativaIndicadorPresupuestoNombre() : mensajes.getMessage("jsp.configuracion.sistema.iniciativas.indicador.presupuesto.nombre"));
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);

			elemento = document.createElement("crear");
			text = document.createTextNode(editarConfiguracionSistemaForm.getIniciativaIndicadorPresupuestoMostrar() ? "1" : "0");
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);

			// Indicador de Eficacia
			indicadorElement = document.createElement("indicador"); 
			indicadores.appendChild(indicadorElement);

			elemento = document.createElement("tipo");
			text = document.createTextNode(TipoFuncionIndicador.getTipoFuncionEficacia().toString());
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);
			
			elemento = document.createElement("nombre");
			text = document.createTextNode(editarConfiguracionSistemaForm.getIniciativaIndicadorEficaciaNombre() != null ? editarConfiguracionSistemaForm.getIniciativaIndicadorEficaciaNombre() : mensajes.getMessage("jsp.configuracion.sistema.iniciativas.indicador.eficacia.nombre"));
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);

			elemento = document.createElement("crear");
			text = document.createTextNode(editarConfiguracionSistemaForm.getIniciativaIndicadorEficaciaMostrar() ? "1" : "0");
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);
			
			// Indicador de Eficiencia
			indicadorElement = document.createElement("indicador"); 
			indicadores.appendChild(indicadorElement);

			elemento = document.createElement("tipo");
			text = document.createTextNode(TipoFuncionIndicador.getTipoFuncionEficiencia().toString());
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);
			
			elemento = document.createElement("nombre");
			text = document.createTextNode(editarConfiguracionSistemaForm.getIniciativaIndicadorEficienciaNombre() != null ? editarConfiguracionSistemaForm.getIniciativaIndicadorEficienciaNombre() : mensajes.getMessage("jsp.configuracion.sistema.iniciativas.indicador.eficiencia.nombre"));
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);

			elemento = document.createElement("crear");
			text = document.createTextNode(editarConfiguracionSistemaForm.getIniciativaIndicadorEficienciaMostrar() ? "1" : "0");
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);
			
			Source source = new DOMSource(document);
			
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

    		configuracion = new Configuracion(); 
			configuracion.setParametro("Strategos.Configuracion.Iniciativas");
			configuracion.setValor(writer.toString().trim());
			if (frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request)) != VgcReturnCode.DB_OK)
				respuesta = VgcReturnCode.FORM_READY_ERROR;
			frameworkService.close();
		}
		catch (Exception e) 
		{
			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_READY_ERROR);
		}
		
		return respuesta;
	}

	private int setConfiguracionPlan(EditarConfiguracionSistemaForm editarConfiguracionSistemaForm, HttpServletRequest request)
	{
		int respuesta = VgcReturnCode.DB_OK;

		try
		{
			Configuracion configuracion = new Configuracion();
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "plan", null);
			document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

			Element raiz = document.createElement("properties");  // creamos el elemento raiz
			document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

			Element elemento = document.createElement("alertaAnual"); //creamos un nuevo elemento
			Text text = document.createTextNode((editarConfiguracionSistemaForm.getPlanObjetivoAlertaAnualMostrar() != null && !editarConfiguracionSistemaForm.getPlanObjetivoAlertaAnualMostrar()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("alertaParcial"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getPlanObjetivoAlertaParcialMostrar() != null && !editarConfiguracionSistemaForm.getPlanObjetivoAlertaParcialMostrar()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("logroAnual"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getPlanObjetivoLogroAnualMostrar() != null && !editarConfiguracionSistemaForm.getPlanObjetivoLogroAnualMostrar()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
			elemento = document.createElement("logroParcial"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getPlanObjetivoLogroParcialMostrar() != null && !editarConfiguracionSistemaForm.getPlanObjetivoLogroParcialMostrar()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
			Source source = new DOMSource(document);
			
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

    		configuracion = new Configuracion(); 
			configuracion.setParametro("Strategos.Configuracion.Planes");
			configuracion.setValor(writer.toString().trim());
			if (frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request)) != VgcReturnCode.DB_OK)
				respuesta = VgcReturnCode.FORM_READY_ERROR;
			frameworkService.close();
		}
		catch (Exception e) 
		{
			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_READY_ERROR);
		}
		
		return respuesta;
	}
	
	public int getConfiguracionIniciativa(EditarConfiguracionSistemaForm editarConfiguracionSistemaForm)
	{
		int respuesta = VgcReturnCode.DB_OK;
		
		try
		{
			StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			ConfiguracionIniciativa configuracionIniciativa = strategosIniciativasService.getConfiguracionIniciativa(); 
			strategosIniciativasService.close();
	
			if (configuracionIniciativa != null)
			{
				editarConfiguracionSistemaForm.setIniciativaNombre(configuracionIniciativa.getIniciativaNombre());
				
				editarConfiguracionSistemaForm.setIniciativaIndicadorAvanceNombre(configuracionIniciativa.getIniciativaIndicadorAvanceNombre());
				editarConfiguracionSistemaForm.setIniciativaIndicadorAvanceMostrar(configuracionIniciativa.getIniciativaIndicadorAvanceMostrar());
				editarConfiguracionSistemaForm.setIniciativaIndicadorAvanceAnteponer(configuracionIniciativa.getIniciativaIndicadorAvanceAnteponer());

				editarConfiguracionSistemaForm.setIniciativaIndicadorPresupuestoNombre(configuracionIniciativa.getIniciativaIndicadorPresupuestoNombre());
				editarConfiguracionSistemaForm.setIniciativaIndicadorPresupuestoMostrar(configuracionIniciativa.getIniciativaIndicadorPresupuestoMostrar());

				editarConfiguracionSistemaForm.setIniciativaIndicadorEficaciaNombre(configuracionIniciativa.getIniciativaIndicadorEficaciaNombre());
				editarConfiguracionSistemaForm.setIniciativaIndicadorEficaciaMostrar(configuracionIniciativa.getIniciativaIndicadorEficaciaMostrar());

				editarConfiguracionSistemaForm.setIniciativaIndicadorEficienciaNombre(configuracionIniciativa.getIniciativaIndicadorEficienciaNombre());
				editarConfiguracionSistemaForm.setIniciativaIndicadorEficienciaMostrar(configuracionIniciativa.getIniciativaIndicadorEficienciaMostrar());
			}
		}
		catch (Exception e) 
		{
			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_READY_ERROR);
		}
		
		return respuesta;
	}
	
	public int getConfiguracionPlan(EditarConfiguracionSistemaForm editarConfiguracionSistemaForm)
	{
		int respuesta = VgcReturnCode.DB_OK;
		
		try
		{
			StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
			ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan(); 
			strategosPlanesService.close();
	
			if (configuracionPlan != null)
			{
				editarConfiguracionSistemaForm.setPlanObjetivoAlertaAnualMostrar(configuracionPlan.getPlanObjetivoAlertaAnualMostrar());
				editarConfiguracionSistemaForm.setPlanObjetivoAlertaParcialMostrar(configuracionPlan.getPlanObjetivoAlertaParcialMostrar());
				editarConfiguracionSistemaForm.setPlanObjetivoLogroAnualMostrar(configuracionPlan.getPlanObjetivoLogroAnualMostrar());
				editarConfiguracionSistemaForm.setPlanObjetivoLogroParcialMostrar(configuracionPlan.getPlanObjetivoLogroParcialMostrar());
			}
		}
		catch (Exception e) 
		{
			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_READY_ERROR);
		}
		
		return respuesta;
	}
	
	public int getConfiguracionResponsable(EditarConfiguracionSistemaForm editarConfiguracionSistemaForm, Usuario usuario)
	{
		int respuesta = VgcReturnCode.DB_OK;
		
		try
		{
			StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
			ConfiguracionResponsable configuracionResponsable = strategosResponsablesService.getConfiguracionResponsable(); 
			if (configuracionResponsable != null)
				configuracionResponsable.setTipoCorreoDefaultSent(strategosResponsablesService.getTipoCorreoPorDefectoSent(usuario.getUsuarioId()));
			strategosResponsablesService.close();
	
			if (configuracionResponsable != null)
			{
				editarConfiguracionSistemaForm.setEnviarResponsableFijarMeta(configuracionResponsable.getEnviarResponsableFijarMeta());
				editarConfiguracionSistemaForm.setEnviarResponsableLograrMeta(configuracionResponsable.getEnviarResponsableLograrMeta());
				editarConfiguracionSistemaForm.setEnviarResponsableSeguimiento(configuracionResponsable.getEnviarResponsableSeguimiento());
				editarConfiguracionSistemaForm.setEnviarResponsableCargarMeta(configuracionResponsable.getEnviarResponsableCargarMeta());
				editarConfiguracionSistemaForm.setEnviarResponsableCargarEjecutado(configuracionResponsable.getEnviarResponsableCargarEjecutado());
				editarConfiguracionSistemaForm.setTipoCorreoPorDefecto(configuracionResponsable.getTipoCorreoDefaultSent());
			}
		}
		catch (Exception e) 
		{
			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_READY_ERROR);
		}
		
		return respuesta;
	}
	
	private int setConfiguracionResponsable(EditarConfiguracionSistemaForm editarConfiguracionSistemaForm, HttpServletRequest request)
	{
		int respuesta = VgcReturnCode.DB_OK;

		try
		{
			Configuracion configuracion = new Configuracion();
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "responsable", null);
			document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

			Element raiz = document.createElement("properties");  // creamos el elemento raiz
			document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

			Element elemento = document.createElement("fijarMeta"); //creamos un nuevo elemento
			Text text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableFijarMetaInv() != null && !editarConfiguracionSistemaForm.getEnviarResponsableFijarMetaInv()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("lograrMeta"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableLograrMetaInv() != null && !editarConfiguracionSistemaForm.getEnviarResponsableLograrMetaInv()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("seguimiento"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableSeguimientoInv() != null && !editarConfiguracionSistemaForm.getEnviarResponsableSeguimientoInv()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("cargarMeta"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableCargarMetaInv() != null && !editarConfiguracionSistemaForm.getEnviarResponsableCargarMetaInv()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("cargarEjecutado"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableCargarEjecutadoInv() != null && !editarConfiguracionSistemaForm.getEnviarResponsableCargarEjecutadoInv()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
			Source source = new DOMSource(document);
			
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

    		configuracion = new Configuracion(); 
			configuracion.setParametro("Strategos.Configuracion.Responsable");
			configuracion.setValor(writer.toString().trim());
			if (frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request)) != VgcReturnCode.DB_OK)
				respuesta = VgcReturnCode.FORM_READY_ERROR;
			frameworkService.close();
		}
		catch (Exception e) 
		{
			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_READY_ERROR);
		}
		
		return respuesta;
	}
	
	private int setConfiguracionIndicador(EditarConfiguracionSistemaForm editarConfiguracionSistemaForm, HttpServletRequest request)
	{
		int respuesta = VgcReturnCode.DB_OK;

		try
		{
			Configuracion configuracion = new Configuracion();
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "plan", null);
			document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

			Element raiz = document.createElement("properties");  // creamos el elemento raiz
			document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

			Element elemento = document.createElement("nivel"); //creamos un nuevo elemento
			Text text = document.createTextNode((editarConfiguracionSistemaForm.getIndicadorNivel() != null ? editarConfiguracionSistemaForm.getIndicadorNivel().toString() : "2"));
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			Source source = new DOMSource(document);
			
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

    		configuracion = new Configuracion(); 
			configuracion.setParametro("Strategos.Configuracion.Indicadores");
			configuracion.setValor(writer.toString().trim());
			if (frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request)) != VgcReturnCode.DB_OK)
				respuesta = VgcReturnCode.FORM_READY_ERROR;
			frameworkService.close();
		}
		catch (Exception e) 
		{
			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_READY_ERROR);
		}
		
		return respuesta;
	}
	
	public int getConfiguracionIndicador(EditarConfiguracionSistemaForm editarConfiguracionSistemaForm)
	{
		int respuesta = VgcReturnCode.DB_OK;
		
		try
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			ConfiguracionIndicador configuracionIndicador = strategosIndicadoresService.getConfiguracionIndicador(); 
			strategosIndicadoresService.close();
	
			if (configuracionIndicador != null)
				editarConfiguracionSistemaForm.setIndicadorNivel(configuracionIndicador.getIndicadorNivel());
		}
		catch (Exception e) 
		{
			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_READY_ERROR);
		}
		
		return respuesta;
	}
	
	private int setCorreoDefecto(EditarConfiguracionSistemaForm editarConfiguracionSistemaForm, HttpServletRequest request)
	{
		int respuesta = VgcReturnCode.DB_OK;

		try
		{
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "plan", null);
			document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

			Element raiz = document.createElement("properties");  // creamos el elemento raiz
			document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

			Element elemento = document.createElement("tipoDefaultSentEmail"); //creamos un nuevo elemento
			Text text = document.createTextNode((editarConfiguracionSistemaForm.getTipoCorreoPorDefecto() != null ? editarConfiguracionSistemaForm.getTipoCorreoPorDefecto().toString() : editarConfiguracionSistemaForm.getCorreoLocal().toString()));
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			Source source = new DOMSource(document);
			
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

    		ConfiguracionUsuario configuracion = new ConfiguracionUsuario();
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase("Strategos.Configuracion.TipoCorreoPorDefectoSent");
			pk.setObjeto("TipoCorreo");
			pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
			configuracion.setPk(pk);
			configuracion.setData(writer.toString().trim());
			respuesta = frameworkService.saveConfiguracionUsuario(configuracion, this.getUsuarioConectado(request));
			if (respuesta != VgcReturnCode.DB_OK)
				respuesta = VgcReturnCode.FORM_READY_ERROR;
			frameworkService.close();
		}
		catch (Exception e) 
		{
			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_READY_ERROR);
		}
		
		return respuesta;
	}
	

	
		
	
	public int getConfiguracionNegativo(EditarConfiguracionSistemaForm editarConfiguracionSistemaForm)
	{
		int respuesta = VgcReturnCode.DB_OK;
		
		try
		{
			StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
			ConfiguracionNegativo configuracionNegativo = strategosMedicionesService.getConfiguracionNegativo();
			
			if(configuracionNegativo != null) {
				
				editarConfiguracionSistemaForm.setTexto(configuracionNegativo.getTexto());
				editarConfiguracionSistemaForm.setTitulo(configuracionNegativo.getTitulo());
				editarConfiguracionSistemaForm.setEnviarResponsableCargarEjecutadoInv(configuracionNegativo.getEnviarResponsableCargarEjecutado());
				editarConfiguracionSistemaForm.setEnviarResponsableCargarMetaInv(configuracionNegativo.getEnviarResponsableCargarMeta());
				editarConfiguracionSistemaForm.setEnviarResponsableFijarMetaInv(configuracionNegativo.getEnviarResponsableFijarMeta());
				editarConfiguracionSistemaForm.setEnviarResponsableLograrMetaInv(configuracionNegativo.getEnviarResponsableLograrMeta());
				editarConfiguracionSistemaForm.setEnviarResponsableNegativoInv(configuracionNegativo.getEnviarResponsableNegativo());
				editarConfiguracionSistemaForm.setEnviarResponsableSeguimientoInv(configuracionNegativo.getEnviarResponsableSeguimiento());
				
			}
			
			
			strategosMedicionesService.close();
			
			
		}
		catch (Exception e) 
		{
			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_READY_ERROR);
		}
		
		return respuesta;
	}
	
	
	
	private int setConfiguracionNegativo(EditarConfiguracionSistemaForm editarConfiguracionSistemaForm, HttpServletRequest request)
	{
		int respuesta = VgcReturnCode.DB_OK;

		try
		{
			Configuracion configuracion = new Configuracion();
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "negativo", null);
			document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

			Element raiz = document.createElement("properties");  // creamos el elemento raiz
			document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

			Element elemento = document.createElement("fijarMeta"); //creamos un nuevo elemento
			Text text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableFijarMetaInv() != null && !editarConfiguracionSistemaForm.getEnviarResponsableFijarMetaInv()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("lograrMeta"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableLograrMetaInv() != null && !editarConfiguracionSistemaForm.getEnviarResponsableLograrMetaInv()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("seguimiento"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableSeguimientoInv() != null && !editarConfiguracionSistemaForm.getEnviarResponsableSeguimientoInv()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("cargarMeta"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableCargarMetaInv() != null && !editarConfiguracionSistemaForm.getEnviarResponsableCargarMetaInv()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("cargarEjecutado"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableCargarEjecutadoInv() != null && !editarConfiguracionSistemaForm.getEnviarResponsableCargarEjecutadoInv()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
			elemento = document.createElement("validarNegativo"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableNegativoInv() != null && !editarConfiguracionSistemaForm.getEnviarResponsableNegativoInv()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
			
			elemento = document.createElement("titulo"); //creamos un nuevo elemento
			if(editarConfiguracionSistemaForm.getTitulo() != null) {
				text = document.createTextNode(editarConfiguracionSistemaForm.getTitulo());
			}else {
				text = document.createTextNode("");
			}		
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
			
			elemento = document.createElement("texto"); //creamos un nuevo elemento
			
			if(editarConfiguracionSistemaForm.getTexto() != null){
				text = document.createTextNode(editarConfiguracionSistemaForm.getTexto());
			}else {
				text = document.createTextNode("");
			}
			
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
			Source source = new DOMSource(document);
			
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

    		configuracion = new Configuracion(); 
			configuracion.setParametro("Strategos.Configuracion.Negativo");
			configuracion.setValor(writer.toString().trim());
			if (frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request)) != VgcReturnCode.DB_OK)
				respuesta = VgcReturnCode.FORM_READY_ERROR;
			frameworkService.close();
		}
		catch (Exception e) 
		{
			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_READY_ERROR);
		}
		
		return respuesta;
	}
	
	
	public int getConfiguracionCorreoIniciativa(EditarConfiguracionSistemaForm editarConfiguracionSistemaForm)
	{
		int respuesta = VgcReturnCode.DB_OK;
		
		try
		{
			StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			CorreoIniciativa correoIniciativa = strategosIniciativasService.getCorreoIniciativa();
			
			if(correoIniciativa != null) {
				
				editarConfiguracionSistemaForm.setTexto1(correoIniciativa.getTexto());
				editarConfiguracionSistemaForm.setTitulo1(correoIniciativa.getTitulo());
				editarConfiguracionSistemaForm.setCorreo1(correoIniciativa.getCorreo());
				editarConfiguracionSistemaForm.setDia1(correoIniciativa.getDia());
				editarConfiguracionSistemaForm.setHora1(correoIniciativa.getHora());
				editarConfiguracionSistemaForm.setEnviarResponsableCargarEjecutadoInv1(correoIniciativa.getEnviarResponsableCargarEjecutado());
				editarConfiguracionSistemaForm.setEnviarResponsableCargarMetaInv1(correoIniciativa.getEnviarResponsableCargarMeta());
				editarConfiguracionSistemaForm.setEnviarResponsableFijarMetaInv1(correoIniciativa.getEnviarResponsableFijarMeta());
				editarConfiguracionSistemaForm.setEnviarResponsableLograrMetaInv1(correoIniciativa.getEnviarResponsableLograrMeta());
				editarConfiguracionSistemaForm.setEnviarResponsableSeguimientoInv1(correoIniciativa.getEnviarResponsableSeguimiento());
				
			}
			
			
			strategosIniciativasService.close();
			
			
		}
		catch (Exception e) 
		{
			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_READY_ERROR);
		}
		
		return respuesta;
	}
	
	
	private int setConfiguracionCorreoIniciativa(EditarConfiguracionSistemaForm editarConfiguracionSistemaForm, HttpServletRequest request)
	{
		int respuesta = VgcReturnCode.DB_OK;

		try
		{
			Configuracion configuracion = new Configuracion();
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "correoini", null);
			document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

			Element raiz = document.createElement("properties");  // creamos el elemento raiz
			document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

			Element elemento = document.createElement("fijarMeta"); //creamos un nuevo elemento
			Text text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableFijarMetaInv1() != null && !editarConfiguracionSistemaForm.getEnviarResponsableFijarMetaInv1()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("lograrMeta"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableLograrMetaInv1() != null && !editarConfiguracionSistemaForm.getEnviarResponsableLograrMetaInv1()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("seguimiento"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableSeguimientoInv1() != null && !editarConfiguracionSistemaForm.getEnviarResponsableSeguimientoInv1()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("cargarMeta"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableCargarMetaInv1() != null && !editarConfiguracionSistemaForm.getEnviarResponsableCargarMetaInv1()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);

			elemento = document.createElement("cargarEjecutado"); //creamos un nuevo elemento
			text = document.createTextNode((editarConfiguracionSistemaForm.getEnviarResponsableCargarEjecutadoInv1() != null && !editarConfiguracionSistemaForm.getEnviarResponsableCargarEjecutadoInv1()) ? "0" : "1");
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
			
			elemento = document.createElement("titulo"); //creamos un nuevo elemento
			if(editarConfiguracionSistemaForm.getTitulo1() != null) {
				text = document.createTextNode(editarConfiguracionSistemaForm.getTitulo1());
			}else {
				text = document.createTextNode("");
			}		
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
			elemento = document.createElement("dia"); //creamos un nuevo elemento
			
			if(editarConfiguracionSistemaForm.getDia1() != null){
				text = document.createTextNode(editarConfiguracionSistemaForm.getDia1().toString());
			}else {
				text = document.createTextNode("");
			}			
			elemento.appendChild(text);
			raiz.appendChild(elemento);		
			
			
			elemento = document.createElement("texto"); //creamos un nuevo elemento
			
			if(editarConfiguracionSistemaForm.getTexto1() != null){
				text = document.createTextNode(editarConfiguracionSistemaForm.getTexto1());
			}else {
				text = document.createTextNode("");
			}
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
			elemento = document.createElement("hora"); //creamos un nuevo elemento
			
			if(editarConfiguracionSistemaForm.getHora1() != null){
				text = document.createTextNode(editarConfiguracionSistemaForm.getHora1());
			}else {
				text = document.createTextNode("");
			}
			
			elemento.appendChild(text);
			raiz.appendChild(elemento);
			
			Source source = new DOMSource(document);
			
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

    		configuracion = new Configuracion(); 
			configuracion.setParametro("Strategos.Configuracion.Correo.Iniciativa");
			configuracion.setValor(writer.toString().trim());
			if (frameworkService.saveConfiguracion(configuracion, getUsuarioConectado(request)) != VgcReturnCode.DB_OK)
				respuesta = VgcReturnCode.FORM_READY_ERROR;
			frameworkService.close();
		}
		catch (Exception e) 
		{
			editarConfiguracionSistemaForm.setStatus(VgcReturnCode.FORM_READY_ERROR);
		}
		
		return respuesta;
	}
	
}
