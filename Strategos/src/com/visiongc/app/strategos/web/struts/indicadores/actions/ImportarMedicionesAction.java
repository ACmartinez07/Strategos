/**
 * 
 */
package com.visiongc.app.strategos.web.struts.indicadores.actions;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.visiongc.app.strategos.indicadores.model.util.TipoMedicionImportar;
import com.visiongc.app.strategos.web.struts.indicadores.forms.ImportarMedicionesForm;
import com.visiongc.app.strategos.web.struts.indicadores.forms.ImportarMedicionesForm.ImportarStatus;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.Importacion.ImportacionType;
import com.visiongc.framework.util.FrameworkConnection;
import com.visiongc.framework.web.struts.actions.LogonAction;

/**
 * @author Kerwin
 *
 */
public class ImportarMedicionesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);
		String forward = mapping.getParameter();
		ImportarMedicionesForm importarMedicionesForm = (ImportarMedicionesForm)form;
		importarMedicionesForm.clear();

		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");

		// Presentacion
		ConfiguracionUsuario presentacion = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Wizar.Importar.ShowPresentacion", "ShowPresentacion");
		if (presentacion != null && presentacion.getData() != null)
			importarMedicionesForm.setShowPresentacion(presentacion.getData().equals("1") ? true : false);
		
		frameworkService.close();
		
		if (configuracion == null)
		{
			importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusNoConfigurado());
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
				importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusNoConfigurado());
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
				saveMessages(request, messages);
			}
			else
				importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusSuccess());
		}
		
		importarMedicionesForm.setTipoFuente(ImportacionType.getImportacionTypePlano());
		importarMedicionesForm.setTipoMedicion((byte) 0);
		importarMedicionesForm.setTipoImportacion(TipoMedicionImportar.getImportarEjecutadoReales());
		importarMedicionesForm.setSeparador("|");
		importarMedicionesForm.setLogMediciones(true);
		importarMedicionesForm.setLogErrores(true);
		importarMedicionesForm.setFileForm(null);
		importarMedicionesForm.setCalcularMediciones(true);

	  	return mapping.findForward(forward);
	}
}