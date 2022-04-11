/**
 * 
 */
package com.visiongc.app.strategos.web.struts.configuracion.actions;

import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.configuracion.EditarConfiguracionPaginaForm;

/**
 * @Descripción: Action Validar Configuración Pagina
 *
 * @author: Kerwin Arias (16/01/2012)
 *
 */
public class ValidarConfiguracionPaginaAction extends VgcAction {

	private static int TAMANO_MAXIMO_IMAGEN = (20480); /** 20K es el tamaño del archivo */

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) 
	{

		/** El Action no se agrega a la barra de navegación */

	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		/** Se ejecuta el servicio del Action padre (OBLIGATORIO !!!) */
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		/** Se obtiene el FormBean haciendo el casting respectivo */
		EditarConfiguracionPaginaForm editarConfiguracionPaginaForm = (EditarConfiguracionPaginaForm) form;

		/** Se obtiene la cola de mensajes */
		ActionMessages messages = this.getMessages(request);
		
		String respuesta = "Success";

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		boolean fromValidar = request.getParameter("fromValidar").equals("true");

		if (respuesta.equals("Success"))
			respuesta = ValidarImagen(request, editarConfiguracionPaginaForm, cancelar, "imagenSupIzq");
		if (respuesta.equals("Success"))
			respuesta = ValidarImagen(request, editarConfiguracionPaginaForm, cancelar, "imagenSupCen");
		if (respuesta.equals("Success"))
			respuesta = ValidarImagen(request, editarConfiguracionPaginaForm, cancelar, "imagenSupDer");

		if (respuesta.equals("Success"))
			respuesta = ValidarImagen(request, editarConfiguracionPaginaForm, cancelar, "imagenInfIzq");
		if (respuesta.equals("Success"))
			respuesta = ValidarImagen(request, editarConfiguracionPaginaForm, cancelar, "imagenInfCen");
		if (respuesta.equals("Success"))
			respuesta = ValidarImagen(request, editarConfiguracionPaginaForm, cancelar, "imagenInfDer");

		if (!fromValidar && !respuesta.equals("Success"))
		{
			String mensaje = "";
			String [] resp = respuesta.split(",");
			if (resp[0].equals("imagenInvalida"))
				mensaje = "action.guardarconfiguracion.validacion.imagenInvalida";
			else if (resp[0].equals("tamanoInvalido"))
				mensaje = "action.guardarconfiguracion.validacion.tamanoInvalido";
			else if (resp[0].equals("dimensionInvalida"))
				mensaje = "action.guardarconfiguracion.validacion.dimensionInvalida";
			else if (resp[0].equals("direcciondesconocida"))
				mensaje = "action.guardarconfiguracion.validacion.direcciondesconocida";
	
			String mensaje2 = "";
			if (resp[1].equals("imagenSupIzq"))
				mensaje2 = "imagen superior izquierda";
			else if (resp[1].equals("imagenSupCen"))
				mensaje2 = "imagen superior central";
			else if (resp[1].equals("imagenSupDer"))
				mensaje2 = "imagen superior derecha";
			else if (resp[1].equals("imagenInfIzq"))
				mensaje2 = "imagen inferior izquierda";
			else if (resp[1].equals("imagenInfCen"))
				mensaje2 = "imagen inferior central";
			else if (resp[1].equals("imagenInfDer"))
				mensaje2 = "imagen inferior derecha";
			
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(mensaje, mensaje2));
			
			/** Guarda la cola de mensajes */
			this.saveMessages(request, messages);
			
			/** Se retorna el forward destino a ser ejecutado despues del action */
			return mapping.findForward(resp[0]);
		}
		else
		{
			request.setAttribute("ajaxResponse", respuesta);
			return mapping.findForward("ajaxResponse");
		}
	}
	
	private String ValidarImagen(HttpServletRequest request, EditarConfiguracionPaginaForm editarConfiguracionPaginaForm, boolean cancelar, String nombreImagen) throws Exception
	{
		String respuesta = "Success";
		String imagen = request.getParameter(nombreImagen);
		FormFile archivo = null;
		if (imagen == null)
		{
			if (nombreImagen == "imagenSupIzq") 
				imagen = editarConfiguracionPaginaForm.getNombreImagenSupIzq();
			else if (nombreImagen == "imagenSupCen") 
				imagen = editarConfiguracionPaginaForm.getNombreImagenSupCen();
			else if (nombreImagen == "imagenSupDer") 
				imagen = editarConfiguracionPaginaForm.getNombreImagenSupDer();
			else if (nombreImagen == "imagenInfIzq") 
				imagen = editarConfiguracionPaginaForm.getNombreImagenInfIzq();
			else if (nombreImagen == "imagenInfCen") 
				imagen = editarConfiguracionPaginaForm.getNombreImagenInfCen();
			else if (nombreImagen == "imagenInfDer") 
				imagen = editarConfiguracionPaginaForm.getNombreImagenInfDer();

			archivo = (FormFile) editarConfiguracionPaginaForm.getMultipartRequestHandler().getFileElements().get(nombreImagen);
		}
		
		/** Verificación de llamada no repetida */
		if ((imagen == null) || (imagen.equals(""))) 
			cancelar = true;

		if (!cancelar)
		{
			File sarchivo = null;
			if (archivo != null)
			{
				if (!((archivo.getFileName().indexOf(".jpg") != -1) || (archivo.getFileName().indexOf(".png") != -1) || (archivo.getFileName().indexOf(".gif") != -1)))
					respuesta = "imagenInvalida" + "," + nombreImagen;
				else
				{
					if (archivo.getFileSize() == 0 || archivo.getFileSize() > (TAMANO_MAXIMO_IMAGEN))
						respuesta = "tamanoInvalido" + "," + nombreImagen;
					else
					{
						java.awt.Image image = javax.imageio.ImageIO.read(archivo.getInputStream()); 
						int height = image.getHeight(null);  
						int width = image.getWidth(null);
						
						if (height > 50 || width > 50)
							respuesta = "dimensionInvalida" + "," + nombreImagen;
					}
				}
			}
			else
			{
				sarchivo = new File(imagen);
				if (sarchivo.exists())
				{
					if (!((sarchivo.getName().indexOf(".jpg") != -1) || (sarchivo.getName().indexOf(".png") != -1) || (sarchivo.getName().indexOf(".gif") != -1)))
						respuesta = "imagenInvalida" + "," + nombreImagen;
					else
					{
						if (sarchivo.length() == 0 || sarchivo.length() > (TAMANO_MAXIMO_IMAGEN))
							respuesta = "tamanoInvalido" + "," + nombreImagen;
						else
						{
							java.awt.Image image = javax.imageio.ImageIO.read(sarchivo); 
							int height = image.getHeight(null);  
							int width = image.getWidth(null);
							
							if (height > 50 || width > 50)
								respuesta = "dimensionInvalida" + "," + nombreImagen;
						}
					}
				}
			}
		}
		
		return respuesta;
	}
}
