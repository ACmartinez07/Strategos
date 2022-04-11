package com.visiongc.framework.web.struts.auditorias.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.auditoria.AuditoriaMedicionService;
import com.visiongc.framework.auditoria.AuditoriaService;
import com.visiongc.framework.auditoria.AuditoriasService;
import com.visiongc.framework.auditoria.model.Auditoria;
import com.visiongc.framework.auditoria.model.AuditoriaAtributo;
import com.visiongc.framework.auditoria.model.AuditoriaAtributoPK;
import com.visiongc.framework.auditoria.model.AuditoriaDetalleMedicion;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.auditoria.model.ObjetoAuditable;
import com.visiongc.framework.auditoria.model.util.AuditoriaDetalle;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoEvento;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.web.struts.auditorias.forms.GestionarAuditoriasForm;
import com.visiongc.framework.web.struts.auditorias.forms.GestionarAuditoriasMedicionForm;
import com.visiongc.framework.web.struts.auditorias.forms.GestionarAuditoriasPorAtributoForm;

import java_cup.parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.json.JSONObject;


public final class GestionarAuditoriasPorAtributoAction
  extends VgcAction
{
  public GestionarAuditoriasPorAtributoAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.agregarUrl(url, nombre);
  }
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
	  super.execute(mapping, form, request, response);
	    
	    String forward = mapping.getParameter();
	    
	    MessageResources mensajes = getResources(request);

	    GestionarAuditoriasPorAtributoForm gestionarAuditoriasPorAtributoForm = (GestionarAuditoriasPorAtributoForm)form;
	    
	    
	    
	   
	    AuditoriasService auditoriasService = FrameworkServiceFactory.getInstance().openAuditoriasService();
	    
	    List<AuditoriaDetalle> auditorias = new ArrayList(); 
	    
	    
	    GestionarAuditoriasPorAtributoForm gestionarAuditoriasForm = (GestionarAuditoriasPorAtributoForm)form;
	    
	  
	    String auditoriaId = request.getParameter("auditoriaId");
	    
	    Auditoria auditoria = (Auditoria)auditoriasService.load(Auditoria.class, new Long(auditoriaId));
	    
	    gestionarAuditoriasForm.setAuditoriaId(new Long(auditoriaId));
	    gestionarAuditoriasForm.setEntidad(auditoria.getEntidad());
	    
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss a");

	    	 
	    
	    
	    gestionarAuditoriasForm.setFechaDesde(ft.format(auditoria.getFechaEjecucion()));
	    gestionarAuditoriasForm.setFechaHasta(auditoria.getFechaEjecucion().toString());
	    gestionarAuditoriasForm.setTipoEvento(auditoria.getTipoEvento());
	    gestionarAuditoriasForm.setUsuarioNombre(auditoria.getUsuario());
	    gestionarAuditoriasForm.setTiposEventos(AuditoriaTipoEvento.getTiposEventos());
	    
	    String detalle = auditoria.getDetalle();
	    
	   	String[] cad = detalle.split(",");
	   	auditorias=obtenerDetalle(auditoria, cad);
	  
	    
	    PaginaLista paginaAuditorias = new PaginaLista();
	    
	    paginaAuditorias.setLista(auditorias);
	    
	    
	    request.setAttribute("paginaAuditoriasDetalle", paginaAuditorias);
	        
	    auditoriasService.close();
	    
	    return mapping.findForward(forward);
  }
  
  
  private List<AuditoriaDetalle> obtenerDetalle(Auditoria aud, String[] cad){
		 
	     List<AuditoriaDetalle> auditorias= new ArrayList();	
	  
		 int index=0; 
		 int size=0;
		 List<String> atributos = new ArrayList();
		 List<String> valores = new ArrayList();
				 
		 
		 String signo =":";
			
		 
		 for(int x=0; x<cad.length; x++) {
			 
			AuditoriaDetalle auditoria = new AuditoriaDetalle(); 
			 
			auditoria.setClaseNombre(aud.getEntidad());
			auditoria.setAuditoriaId(aud.getAuditoriaId());
			auditoria.setFecha(aud.getFechaEjecucion().toString());
						
			String cadena= cad[x];

			index=cadena.indexOf(signo);
			size=cadena.length();
			
			String valor ="";
			String atributo ="";
			
			
			

			if(x==0) {
				atributo=cadena.substring(1,index);
			}else{
				atributo=cadena.substring(0,index);
			}
			
			
			if(x == cad.length-1) {
				valor= cadena.substring(index+1, size-1);
			}else{
				valor= cadena.substring(index+1, size);
			}
			
			
			auditoria.setAtributo(atributo);
			auditoria.setValor(valor);
			
			auditorias.add(auditoria);
		 }
		 
		 return auditorias;
		
  }
  
  

}
