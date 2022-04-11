package com.visiongc.framework.web.struts.auditorias.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.auditoria.AuditoriaService;
import com.visiongc.framework.auditoria.AuditoriasService;
import com.visiongc.framework.auditoria.AuditoriaService;
import com.visiongc.framework.auditoria.model.AuditoriaEvento;
import com.visiongc.framework.auditoria.model.AuditoriaEventoPK;
import com.visiongc.framework.auditoria.model.ObjetoAuditable;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoEvento;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.web.struts.auditorias.forms.GestionarAuditoriasForm;
import com.visiongc.framework.web.struts.auditorias.forms.GestionarAuditoriasForm;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;
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




public final class GestionarAuditoriasAction
  extends VgcAction
{
  public GestionarAuditoriasAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.clear();
    navBar.agregarUrl(url, nombre);
  }
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    
	  super.execute(mapping, form, request, response);
	    

	    getBarraAreas(request, "administracion").setBotonSeleccionado("auditorias");
	    

	    String forward = mapping.getParameter();
	    
	    MessageResources mensajes = getResources(request);
	    

	    GestionarAuditoriasForm gestionarAuditoriasForm = (GestionarAuditoriasForm)form;
	    
	    Date dateActual = new Date();
	    Calendar c = Calendar.getInstance();
	    c.setTime(dateActual);
	    String dia = (new StringBuilder()).append(c.get(5)).toString();
	    String mes = (new StringBuilder()).append(c.get(2) + 1).toString();
	    String ano = (new StringBuilder()).append(c.get(1)).toString();
	    String fechaActual = (dia.length() < 2 ? "0" + dia : dia) + "/" + (mes.length() < 2 ? "0" + mes : mes) + "/" + ano;
	    
	    if ((gestionarAuditoriasForm.getFechaDesde() == null) || ((gestionarAuditoriasForm.getFechaDesde() != null) && (gestionarAuditoriasForm.getFechaDesde().equals(""))))
	        gestionarAuditoriasForm.setFechaDesde(fechaActual);
	    if ((gestionarAuditoriasForm.getFechaHasta() == null) || ((gestionarAuditoriasForm.getFechaHasta() != null) && (gestionarAuditoriasForm.getFechaHasta().equals("")))) {
	        gestionarAuditoriasForm.setFechaHasta(fechaActual);
	    }
	    
	    String atributoOrden = gestionarAuditoriasForm.getAtributoOrden();
	    
	    String tipoOrden = gestionarAuditoriasForm.getTipoOrden();
	    
	    int pagina = gestionarAuditoriasForm.getPagina();
	    
	    if (pagina < 1)
	        pagina = 1;
	    if (gestionarAuditoriasForm.getTiposEventos() == null) {
	        gestionarAuditoriasForm.setTiposEventos(AuditoriaTipoEvento.getTiposEventos());
	    }
	    
	    AuditoriasService auditoriaService = FrameworkServiceFactory.getInstance().openAuditoriasService();
	    
	    Map<String, Object> filtros = new HashMap();
	    
	    String[] ordenArray = new String[1];
	    String[] tipoOrdenArray = new String[1];
	    ordenArray[0] = atributoOrden;
	    tipoOrdenArray[0] = tipoOrden;
	      
	    if ((gestionarAuditoriasForm.getFechaDesde() != null) && (!gestionarAuditoriasForm.getFechaDesde().equals("")))
	        filtros.put("fechaDesde", FechaUtil.convertirStringToDate(gestionarAuditoriasForm.getFechaDesde(), VgcResourceManager.getResourceApp("formato.fecha.corta")));
	    if ((gestionarAuditoriasForm.getFechaHasta() != null) && (!gestionarAuditoriasForm.getFechaHasta().equals("")))
	        filtros.put("fechaHasta", FechaUtil.convertirStringToDate(gestionarAuditoriasForm.getFechaHasta(), VgcResourceManager.getResourceApp("formato.fecha.corta")));
	    if ((gestionarAuditoriasForm.getUsuario() != null) && (!gestionarAuditoriasForm.getUsuario().equals("")))
	    	filtros.put("usuario", gestionarAuditoriasForm.getUsuario());
	    if ((gestionarAuditoriasForm.getTipoEvento() != null) && (!gestionarAuditoriasForm.getTipoEvento().equals(""))) 
	    	filtros.put("tipoEvento", gestionarAuditoriasForm.getTipoEvento());
	    if ((gestionarAuditoriasForm.getEntidad() != null) && (!gestionarAuditoriasForm.getEntidad().equals("")))
	    	filtros.put("entidad", gestionarAuditoriasForm.getEntidad());
	    
	   
	    PaginaLista paginaAuditorias = auditoriaService.getAuditoria(pagina, 30, ordenArray, tipoOrdenArray, true, filtros);
	    
	   
	    request.setAttribute("paginaAuditorias", paginaAuditorias);
	    
	    auditoriaService.close();
	    
	    return mapping.findForward(forward);
	  
  }
}
