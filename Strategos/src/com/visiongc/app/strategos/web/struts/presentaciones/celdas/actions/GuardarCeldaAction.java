package com.visiongc.app.strategos.web.struts.presentaciones.celdas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCeldaPK;
import com.visiongc.app.strategos.web.struts.presentaciones.celdas.forms.EditarCeldaForm;
import com.visiongc.app.strategos.web.struts.presentaciones.celdas.forms.GestionarCeldasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class GuardarCeldaAction extends VgcAction
{
	private static final String ACTION_KEY = "GuardarCeldaAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarCeldaForm editarCeldaForm = (EditarCeldaForm)form;
		GestionarCeldasForm gestionarCeldasForm = (GestionarCeldasForm)request.getSession().getAttribute("gestionarCeldasForm");

		ActionMessages messages = getMessages(request);

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("GuardarCeldaAction.ultimoTs");

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts))) 
			cancelar = true;

		StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();

		if (cancelar)
		{
			strategosCeldasService.unlockObject(request.getSession().getId(), editarCeldaForm.getCeldaId());
			
			strategosCeldasService.close();

			return getForwardBack(request, 1, true);
		}

		Celda celda = new Celda();
		boolean nuevo = false;
		int respuesta = 10000;

		if ((editarCeldaForm.getCeldaId() != null) && (!editarCeldaForm.getCeldaId().equals(Long.valueOf("0"))))
			celda = (Celda)strategosCeldasService.load(Celda.class, editarCeldaForm.getCeldaId());
		else
		{
			nuevo = false;
			celda = new Celda();
			celda.setCeldaId(new Long(0L));
			celda.setPaginaId(gestionarCeldasForm.getPaginaId());
			celda.setIndicadoresCelda(new HashSet());
		}

		if ((editarCeldaForm.getTitulo() != null) && (!editarCeldaForm.getTitulo().equals("")))
			celda.setTitulo(editarCeldaForm.getTitulo());
		else
			celda.setTitulo(null);

		celda.setFila(editarCeldaForm.getFila());
		celda.setColumna(editarCeldaForm.getColumna());

		respuesta = strategosCeldasService.saveCelda(celda, getUsuarioConectado(request));

		if (respuesta == 10000)
		{
			strategosCeldasService.unlockObject(request.getSession().getId(), editarCeldaForm.getCeldaId());
			forward = "exito";

			if (nuevo)
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
				forward = "crearCelda";
			}
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
		}
		else if (respuesta == 10003)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));

		strategosCeldasService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("GuardarCeldaAction.ultimoTs", ts);

		if (forward.equals("exito")) 
			return getForwardBack(request, 1, true);
		
		return mapping.findForward(forward);
	}
}