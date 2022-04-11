package com.visiongc.framework.web.struts.auditorias.actions;

import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.auditoria.AuditoriaMedicionService;
import com.visiongc.framework.auditoria.AuditoriaService;
import com.visiongc.framework.auditoria.model.AuditoriaDetalleMedicion;
import com.visiongc.framework.auditoria.model.AuditoriaEvento;
import com.visiongc.framework.auditoria.model.AuditoriaEventoPK;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.auditoria.model.ObjetoAuditable;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoEvento;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.auditorias.forms.GestionarAuditoriasForm;
import com.visiongc.framework.web.struts.auditorias.forms.GestionarAuditoriasMedicionForm;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;

import java.util.ArrayList;
import java.util.Calendar;
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




public final class GestionarAuditoriasMedicionDetalleAction
  extends VgcAction
{
  public GestionarAuditoriasMedicionDetalleAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.clear();
    navBar.agregarUrl(url, nombre);
  }
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    String forward = mapping.getParameter();
    
    MessageResources mensajes = getResources(request);

    List<AuditoriaDetalleMedicion> auditorias = new ArrayList(); 
    
    
    GestionarAuditoriasMedicionForm gestionarAuditoriasMedicionForm = (GestionarAuditoriasMedicionForm)form;
    
    AuditoriaMedicionService auditoriaMedicionService = FrameworkServiceFactory.getInstance().openAuditoriaMedicionService();
    
    String auditoriaId = request.getParameter("auditoriaId");
    
    AuditoriaMedicion auditoria = (AuditoriaMedicion)auditoriaMedicionService.load(AuditoriaMedicion.class, new Long(auditoriaId));
    
    gestionarAuditoriasMedicionForm.setAccion(auditoria.getAccion());
    
    String detalle = auditoria.getDetalle();
    
    String[] cadena= detalle.split("]");
    
    	for(int x=0; x<cadena.length; x++){
    		String[] cad = cadena[x].split(":");
    		AuditoriaDetalleMedicion auditoriaDetalle = new AuditoriaDetalleMedicion();
    		auditoriaDetalle.setAuditoriaMedicionId(new Long(auditoriaId));
    		List<String> detalles = obtenerDetalle(auditoriaDetalle, cad);
    		
    		if(detalles.size() >0){
    			auditoriaDetalle.setAno(new Integer(detalles.get(0)));
    			auditoriaDetalle.setPeriodo(new Integer(detalles.get(1)));
    			auditoriaDetalle.setValor(new Double(detalles.get(2)).longValue());
    			auditoriaDetalle.setSerie(detalles.get(3));
    			auditoriaDetalle.setSerieNombre(obtenerNombreSerie(auditoriaDetalle.getSerie()));
    			auditoriaDetalle.setAccion(detalles.get(4));
    			if(detalles.size() == 6){
    				auditoriaDetalle.setValorAnterior(new Double(detalles.get(5)).longValue());
    			}
    			auditorias.add(auditoriaDetalle);
    		}
    		    		
    	}
    
    	
    PaginaLista paginaAuditorias = new PaginaLista();
    
    paginaAuditorias.setLista(auditorias);
    
    
    request.setAttribute("paginaAuditoriasDetalle", paginaAuditorias);
        
    auditoriaMedicionService.close();
    
    return mapping.findForward(forward);
  }
  
  private List<String> obtenerDetalle(AuditoriaDetalleMedicion auditoriaDetalle, String[] cad){
	 
	 int index=0; 
	 int cont=1;
	 String signo =",";
	 List<String> detalles = new ArrayList();		
	 
	 for(int x=0; x<cad.length; x++) {
		String cadena= cad[x];
		String valor= "";
		index=cadena.indexOf(signo);
		
		if(index >0){
			valor = cadena.substring(0, index);
			detalles.add(valor);
		}else if(cont == cad.length){
			valor = cadena;
			detalles.add(valor);
		}
		cont++;
	 }
	  
	 return detalles; 
  }
  
  private String obtenerNombreSerie(String serie){
	  Long serieId = new Long(serie);
	  
	  String serieNombre = "";
	  
	  if(serieId == SerieTiempo.getSerieRealId().longValue()){
		serieNombre = "Real";  
	  }else if(serieId == SerieTiempo.getSerieProgramadoId().longValue()){
		serieNombre = "Programado";  
	  }else if(serieId == SerieTiempo.getSerieMetaId().longValue()){
		serieNombre = "Meta";  
	  }
	  	  
	  return serieNombre;
	  
  }
}
