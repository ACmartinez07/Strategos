package com.visiongc.framework.web.struts.actions.usuarios;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
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

public class ReporteUsuariosOrganizacionPdfAction extends VgcReporteBasicoAction
{
  public ReporteUsuariosOrganizacionPdfAction() {}
  
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
  {
    return VgcResourceManager.getResourceApp("reporte.framework.usuarios.organizacion.titulo");
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
	 String organizacionId = request.getParameter("organizacionId");
	 
	 UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
	 
	 String atributoOrden = "";
	    
	 String tipoOrden = "ASC";
		
	 String[] ordenArray = new String[1];
	 String[] tipoOrdenArray = new String[1];
	 ordenArray[0] = atributoOrden;
	 tipoOrdenArray[0] = tipoOrden;
	   
	 List<Usuario> usuarios = new ArrayList();
	 List<Grupo> grupos = new ArrayList();	 
	 
	 StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		  
	 Map<String, Object> filtros = new HashMap();
	 
	 documento.add(lineaEnBlanco(fuente));
	 
	 
	 List<OrganizacionStrategos> organizaciones = organizacionservice.getOrganizaciones(0, 0, "organizacionId", "ASC", true, filtros).getLista();
	 
	 	// Organizacion seleccionada
	    if(organizacionId != null && organizacionId != "") {
	    	
	    	OrganizacionStrategos org = (OrganizacionStrategos)organizacionservice.load(OrganizacionStrategos.class,  new Long(organizacionId));
			if(org != null) {
				
				if ((estatus != null) && (!estatus.equals("") && !estatus.equals("2")))
			    	filtros.put("estatus", estatus);
			 
				filtros.put("organizacionId", org.getOrganizacionId());
				
				Paragraph texto;
				fuente.setSize(10);
				fuente.setStyle(Font.BOLD);
								
				texto = new Paragraph("Organización: "+ org.getNombre(), fuente);
				texto.setAlignment(Element.ALIGN_LEFT);
				texto.setIndentationLeft(16);
				documento.add(texto);
					
				documento.add(lineaEnBlanco(fuente));
				
				usuarios= usuariosService.getUsuarios(1, atributoOrden, tipoOrden, true, filtros).getLista();
				 
				 if(usuarios == null || usuarios.size() == 0) {
					 
					 fuente.setSize(8);
					 fuente.setStyle(Font.NORMAL);
					 texto = new Paragraph("No existen usuarios asociados a la Organización", fuente);
					 texto.setAlignment(Element.ALIGN_LEFT);
					 texto.setIndentationLeft(16);
					 documento.add(texto);
						
					 documento.add(lineaEnBlanco(fuente));
					 
				 }else {
					 
					 TablaPDF tabla = null;
					    tabla = new TablaPDF(getConfiguracionPagina(request), request);
					    int[] columnas = new int[6];
					    columnas[0] = 21;
					    columnas[1] = 25;
					    columnas[2] = 14;
					    columnas[3] = 15;
					    columnas[4] = 15;
					    columnas[5] = 25;
		
					   
					    tabla.setAmplitudTabla(100);
					    tabla.crearTabla(columnas);
					    
					    tabla.setAlineacionHorizontal(1);
					    

					    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.uid"));
					    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.fullname"));
					    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.administrador"));
					    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.estatus"));
					    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.detallado.bloqueado"));	    
					    tabla.agregarCelda(messageResources.getMessage("reporte.framework.grupos.listagrupos.grupo"));
					    
					      
					    tabla.setDefaultAlineacionHorizontal();
					    
					    tabla.setAlineacionHorizontal(0);
					 
						 for(Iterator<Usuario> iterUsuario = usuarios.iterator(); iterUsuario.hasNext(); ){
								
								Usuario usuario = iterUsuario.next();
								
							    
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
							    	
							    tabla.agregarCelda("");
							 				
								
						 }
						 

					     documento.add(tabla.getTabla());
					 
				 }
				
			}
			
			documento.add(lineaEnBlanco(fuente));
	    	
	    }else {
	    
	    	for (Iterator<OrganizacionStrategos> iter = organizaciones.iterator(); iter.hasNext();)
			{
	    		OrganizacionStrategos organizacion = (OrganizacionStrategos)iter.next();
	    		
	    		if ((estatus != null) && (!estatus.equals("") && !estatus.equals("2")))
			    	filtros.put("estatus", estatus);
			 
				filtros.put("organizacionId", organizacion.getOrganizacionId());
				
				Paragraph texto;
				fuente.setSize(10);
				fuente.setStyle(Font.BOLD);
								
				texto = new Paragraph("Organización: "+ organizacion.getNombre(), fuente);
				texto.setAlignment(Element.ALIGN_LEFT);
				texto.setIndentationLeft(16);
				documento.add(texto);
					
				documento.add(lineaEnBlanco(fuente));
				
				usuarios= usuariosService.getUsuarios(1, atributoOrden, tipoOrden, true, filtros).getLista();
				 
				 if(usuarios == null || usuarios.size() == 0) {
					 
					 fuente.setSize(8);
					 fuente.setStyle(Font.NORMAL);
					 texto = new Paragraph("No existen usuarios asociados a la Organización", fuente);
					 texto.setAlignment(Element.ALIGN_LEFT);
					 texto.setIndentationLeft(16);
					 documento.add(texto);
						
					 documento.add(lineaEnBlanco(fuente));
					 
				 }else {
					 
					 TablaPDF tabla = null;
					    tabla = new TablaPDF(getConfiguracionPagina(request), request);
					    int[] columnas = new int[6];
					    columnas[0] = 21;
					    columnas[1] = 25;
					    columnas[2] = 14;
					    columnas[3] = 15;
					    columnas[4] = 15;
					    columnas[5] = 25;
		
					   
					    tabla.setAmplitudTabla(100);
					    tabla.crearTabla(columnas);
					    
					    tabla.setAlineacionHorizontal(1);
					    

					    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.uid"));
					    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.fullname"));
					    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.administrador"));
					    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.estatus"));
					    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.detallado.bloqueado"));	    
					    tabla.agregarCelda(messageResources.getMessage("reporte.framework.grupos.listagrupos.grupo"));
					    
					      
					    tabla.setDefaultAlineacionHorizontal();
					    
					    tabla.setAlineacionHorizontal(0);
					 
						 for(Iterator<Usuario> iterUsuario = usuarios.iterator(); iterUsuario.hasNext(); ){
								
								Usuario usuario = iterUsuario.next();
								
							    
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
							    	
							    tabla.agregarCelda("");
							 				
								
						 }
						 

					     documento.add(tabla.getTabla());
					 
				 }
				
			}
	    	
	    	documento.add(lineaEnBlanco(fuente));
	    }
	 	 
	 
  }
  
  private String obtenerGruposUsuario(Long organizacionId, Long usuarioId, Usuario user) {
	  String cadena= "";
	  
	  List<Long> gruposId = new ArrayList<Long>();
	  List<UsuarioGrupo> usuariosGrupo = new ArrayList<UsuarioGrupo>();
	  UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
	  
	  if(user.getUsuarioGrupos() != null) {
		  usuariosGrupo = (List<UsuarioGrupo>) user.getUsuarioGrupos();
		  
		  //añade los ids de los grupos
		  for(Iterator<UsuarioGrupo> iterUsuario = usuariosGrupo.iterator(); iterUsuario.hasNext(); ){	
			  UsuarioGrupo usuarioGrupo = iterUsuario.next();
			  
			  if(usuarioGrupo != null && usuarioGrupo.getPk().getOrganizacionId().equals(organizacionId) && usuarioGrupo.getPk().getUsuarioId().equals(usuarioId)) {
				  boolean existe = gruposId.contains(usuarioGrupo.getGrupo().getGrupoId());
				  
				  if (!existe) {
						gruposId.add(usuarioGrupo.getGrupo().getGrupoId());
				  } 
			  }
			  
		  }
		  
		  for(Iterator<Long> iter = gruposId.iterator(); iter.hasNext(); ){	
			  Long grupo = iter.next();
			  
			  Grupo grupoNombre = (Grupo)usuariosService.load(Grupo.class,  new Long(grupo));
			  
			  if(grupoNombre != null) {
				  cadena+= grupoNombre.getGrupo();
			  }
			  
		  }
		  
		  
	  }
	  
	  usuariosService.close();
	  
	  
	  return cadena;
  }
  
 
}
