package com.visiongc.framework.web.struts.actions.usuarios;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaDetallesObjeto;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.framework.auditoria.AuditoriaMedicionService;
import com.visiongc.framework.auditoria.model.AuditoriaDetalleMedicion;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Grupo;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.UsuarioGrupo;
import com.visiongc.framework.usuarios.UsuariosService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class ReporteUsuarioGrupoPdfAction extends VgcReporteBasicoAction
{
  public ReporteUsuarioGrupoPdfAction() {}
  
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
  {
    return VgcResourceManager.getResourceApp("reporte.framework.usuarios.grupo.titulo");
  }
  
  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
	 int lineas = 0;
	 int tamanoPagina = 0;
	 int inicioLineas = 1;
	 int inicioTamanoPagina = 57;
	 int maxLineasAntesTabla = 4; 
	  
	 Font fuente = getConfiguracionPagina(request).getFuente();
	 MessageResources messageResources = getResources(request); 
	 
	 String estatus = request.getParameter("estatus");
	 String grupo = request.getParameter("selectGrupos");
	 
	 UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
	 
	 String atributoOrden = "";
	    
	 String tipoOrden = "ASC";
		
	 String[] ordenArray = new String[1];
	 String[] tipoOrdenArray = new String[1];
	 ordenArray[0] = atributoOrden;
	 tipoOrdenArray[0] = tipoOrden;
	   
	 List<Usuario> usuarios = new ArrayList();
	 List<Grupo> grupos = new ArrayList();	 
	 
	 Map<String, Object> filtros = new HashMap();
	 
	 documento.add(lineaEnBlanco(fuente));
	 
	 //grupo seleccionado
	 if((grupo != null) && (!grupo.equals("") && !grupo.equals("0"))) {
		 
		 if ((estatus != null) && (!estatus.equals("") && !estatus.equals("2")))
		    	filtros.put("estatus", estatus);
		 
		 filtros.put("grupoId", grupo);
		 
		 Paragraph texto;
		 fuente.setSize(10);
		 fuente.setStyle(Font.BOLD);
		 
		 Grupo grupoNombre = (Grupo)usuariosService.load(Grupo.class,  new Long(grupo));
		 
		 texto = new Paragraph("Grupo: "+ grupoNombre.getGrupo(), fuente);
		 texto.setAlignment(Element.ALIGN_LEFT);
		 texto.setIndentationLeft(16);
		 documento.add(texto);
			
		 documento.add(lineaEnBlanco(fuente));
		 
		 usuarios= usuariosService.getUsuarios(0, atributoOrden, tipoOrden, true, filtros).getLista();
		 
		 if(usuarios == null || usuarios.size() == 0) {
			 
			 fuente.setSize(8);
			 fuente.setStyle(Font.NORMAL);
			 texto = new Paragraph("No existen usuarios asociados al Grupo", fuente);
			 texto.setAlignment(Element.ALIGN_LEFT);
			 texto.setIndentationLeft(16);
			 documento.add(texto);
				
			 documento.add(lineaEnBlanco(fuente));
			 
		 }else {
			 
			 TablaPDF tabla = null;
			    tabla = new TablaPDF(getConfiguracionPagina(request), request);
			    int[] columnas = new int[7];
			    columnas[0] = 21;
			    columnas[1] = 25;
			    columnas[2] = 14;
			    columnas[3] = 15;
			    columnas[4] = 15;
			    columnas[5] = 25;
			    columnas[6] = 25;
			   
			    tabla.setAmplitudTabla(100);
			    tabla.crearTabla(columnas);
			    
			    tabla.setAlineacionHorizontal(1);
			    

			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.editarusuario.label.uid"));
			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.editarusuario.label.fullname"));
			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.editarusuario.label.isadmin"));
			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.editarusuario.label.estatus"));
			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.editarusuario.label.estatus.bloqueado"));
			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.propiedadesusuario.pestana.general.creado"));
			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.propiedadesusuario.pestana.general.modificado"));
			      
			    tabla.setDefaultAlineacionHorizontal();
			    
			    tabla.setAlineacionHorizontal(0);
			 
				 for(Iterator<Usuario> iter = usuarios.iterator(); iter.hasNext(); ){
						
						Usuario usuario = iter.next();
						
					    
					    tabla.agregarCelda(usuario.getUId());
					    tabla.agregarCelda(usuario.getFullName());
					    
					    
					    if(usuario.getIsAdmin() != null && usuario.getIsAdmin() == true) {
					    	tabla.agregarCelda("Si");
					    }else {
					    	tabla.agregarCelda("No");
					    }
					    
					    if(usuario.getEstatus() != null) {
					    	switch(usuario.getEstatus()) {
						    	case 0:
						    		tabla.agregarCelda("Activo");
						    		break;
						    	case 1:
						    		tabla.agregarCelda("Inactivo");
						    		break;
					    	}
					    }
					    
					   
					    if(usuario.getBloqueado() != null && usuario.getBloqueado() == true) {
					    	tabla.agregarCelda("Si");
					    }else {
					    	tabla.agregarCelda("No");
					    }
					    	
					    tabla.agregarCelda(VgcFormatter.formatearFecha(usuario.getCreado(), "dd-MM-yyyy hh:mm:ss aa"));
					    tabla.agregarCelda(VgcFormatter.formatearFecha(usuario.getModificado(), "dd-MM-yyyy hh:mm:ss aa"));    
		
						
				 }
				 

			     documento.add(tabla.getTabla());
				    
			 
		 }
		 
		 documento.add(lineaEnBlanco(fuente));
		
		 
	 }else {//todos los grupos
		 
		 PaginaLista paginaGrupos = usuariosService.getGrupos(0, "grupo", "ASC");
		    
		 if(paginaGrupos != null && paginaGrupos.getLista().size() > 0) {   	
		   	grupos = paginaGrupos.getLista();	
		 }
		 
		 //se recorren los grupos
		 for(Grupo gru: grupos) {
			 
			 if ((estatus != null) && (!estatus.equals("") && !estatus.equals("2")))
			    	filtros.put("estatus", estatus);
			 
			 filtros.put("grupoId", gru.getGrupoId());
			 
			 Paragraph texto;
			 fuente.setSize(10);
			 fuente.setStyle(Font.BOLD);
			 
			 Grupo grupoNombre = (Grupo)usuariosService.load(Grupo.class,  gru.getGrupoId());
			 
			 texto = new Paragraph("Grupo: "+ grupoNombre.getGrupo(), fuente);
			 texto.setAlignment(Element.ALIGN_LEFT);
			 texto.setIndentationLeft(16);
			 documento.add(texto);
				
			 documento.add(lineaEnBlanco(fuente));
			 
			 usuarios= usuariosService.getUsuarios(0, atributoOrden, tipoOrden, true, filtros).getLista();
			 
			 if(usuarios == null || usuarios.size() == 0) {
				 
				 fuente.setSize(8);
				 fuente.setStyle(Font.NORMAL);
				 texto = new Paragraph("No existen usuarios asociados al Grupo", fuente);
				 texto.setAlignment(Element.ALIGN_LEFT);
				 texto.setIndentationLeft(16);
				 documento.add(texto);
					
				 documento.add(lineaEnBlanco(fuente));
				 
			 }else {
			  
			 	TablaPDF tabla = null;
			    tabla = new TablaPDF(getConfiguracionPagina(request), request);
			    int[] columnas = new int[7];
			    columnas[0] = 21;
			    columnas[1] = 25;
			    columnas[2] = 14;
			    columnas[3] = 15;
			    columnas[4] = 15;
			    columnas[5] = 25;
			    columnas[6] = 25;
			   
			    tabla.setAmplitudTabla(100);
			    tabla.crearTabla(columnas);
			    
			    tabla.setAlineacionHorizontal(1);
			    

			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.editarusuario.label.uid"));
			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.editarusuario.label.fullname"));
			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.editarusuario.label.isadmin"));
			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.editarusuario.label.estatus"));
			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.editarusuario.label.estatus.bloqueado"));
			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.propiedadesusuario.pestana.general.creado"));
			    tabla.agregarCelda(messageResources.getMessage("jsp.framework.propiedadesusuario.pestana.general.modificado"));
			      
			    tabla.setDefaultAlineacionHorizontal();
			    
			    tabla.setAlineacionHorizontal(0);
			 
				 for(Iterator<Usuario> iter = usuarios.iterator(); iter.hasNext(); ){
						
						Usuario usuario = iter.next();
						
					    
					    tabla.agregarCelda(usuario.getUId());
					    tabla.agregarCelda(usuario.getFullName());
					    
					    
					    if(usuario.getIsAdmin() != null && usuario.getIsAdmin() == true) {
					    	tabla.agregarCelda("Si");
					    }else {
					    	tabla.agregarCelda("No");
					    }
					    
					    if(usuario.getEstatus() != null) {
					    	switch(usuario.getEstatus()) {
						    	case 0:
						    		tabla.agregarCelda("Activo");
						    		break;
						    	case 1:
						    		tabla.agregarCelda("Inactivo");
						    		break;
					    	}
					    }
					    
					   
					    if(usuario.getBloqueado() != null && usuario.getBloqueado() == true) {
					    	tabla.agregarCelda("Si");
					    }else {
					    	tabla.agregarCelda("No");
					    }
					    	
					    tabla.agregarCelda(VgcFormatter.formatearFecha(usuario.getCreado(), "dd-MM-yyyy hh:mm:ss aa"));
					    tabla.agregarCelda(VgcFormatter.formatearFecha(usuario.getModificado(), "dd-MM-yyyy hh:mm:ss aa"));    
		
						
				 }
				 
				 documento.add(tabla.getTabla());
			 }
			    
		     documento.add(lineaEnBlanco(fuente));
			 
		 }
		 
	 }
	 
	 documento.newPage();	 
	 usuariosService.close();
	
	 
  }
 
}
