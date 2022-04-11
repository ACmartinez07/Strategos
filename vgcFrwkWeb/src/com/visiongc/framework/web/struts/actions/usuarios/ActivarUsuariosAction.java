package com.visiongc.framework.web.struts.actions.usuarios;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.forms.usuarios.EditarUsuarioForm;

public final class ActivarUsuariosAction extends VgcAction {

	public void updateNavigationBar(NavigationBar navBar, String url,
			String nombre) {

		/**
		 * Se agrega el url porque este es un nivel de navegación válido
		 */

		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		/** Se ejecuta el servicio del Action padre (obligatorio!!!) */
		super.execute(mapping, form, request, response);

		
		 EditarUsuarioForm editarUsuarioForm = (EditarUsuarioForm)form;
		 String forward = mapping.getParameter();
		 String atributoOrden = request.getParameter("atributoOrden");
		 String tipoOrden = request.getParameter("tipoOrden");
		 UsuariosService us = FrameworkServiceFactory.getInstance().openUsuariosService(getLocale(request));
		 Usuario gestor = getUsuarioConectado(request);
		 
		 
		 String usuariosId= (request.getParameter("usuarios"));	
		  
		 String[] idUsuarios = usuariosId.split(",");
		 
		 Integer estatus = null;
		 
		 for (int i = 0; i < idUsuarios.length; i++){
			 
			 Usuario usuario=(Usuario)us.load(Usuario.class, new Long(Long.parseLong(idUsuarios[i])));
			 if(usuario.getEstatus()==0){ 
				 estatus=(1);
				 usuario.setEstatus(estatus);		// inactiva usuario	
				 
			}
			else{
				estatus=(0);
				usuario.setEstatus(estatus);		// activa usuario
			}
			 
			us.saveUsuario(usuario,gestor);
		 }
		
		forward="exito";
		 
		us.close();
	 
		/** Código de lógica de Negocio del action	*/

		/** Otherwise, return "success" */
		return mapping.findForward(forward);
	}
}
