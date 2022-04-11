package com.visiongc.framework.web.struts.auditorias.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.auditoria.AuditoriaMedicionService;
import com.visiongc.framework.auditoria.AuditoriaService;
import com.visiongc.framework.auditoria.model.AuditoriaEvento;
import com.visiongc.framework.auditoria.model.AuditoriaEventoPK;
import com.visiongc.framework.auditoria.model.ObjetoAuditable;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoEvento;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.web.struts.auditorias.forms.GestionarAuditoriasForm;
import com.visiongc.framework.web.struts.auditorias.forms.GestionarAuditoriasMedicionForm;
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




public final class GestionarAuditoriasMedicionAction
  extends VgcAction
{
  public GestionarAuditoriasMedicionAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.clear();
    navBar.agregarUrl(url, nombre);
  }
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    getBarraAreas(request, "administracion").setBotonSeleccionado("auditoriasMedicion");
    

    String forward = mapping.getParameter();
    
    MessageResources mensajes = getResources(request);
    

    GestionarAuditoriasMedicionForm gestionarAuditoriasMedicionForm = (GestionarAuditoriasMedicionForm)form;
    
    Date dateActual = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(dateActual);
    String dia = (new StringBuilder()).append(c.get(5)).toString();
    String mes = (new StringBuilder()).append(c.get(2) + 1).toString();
    String ano = (new StringBuilder()).append(c.get(1)).toString();
    String fechaActual = (dia.length() < 2 ? "0" + dia : dia) + "/" + (mes.length() < 2 ? "0" + mes : mes) + "/" + ano;
    
    if ((gestionarAuditoriasMedicionForm.getFechaDesde() == null) || ((gestionarAuditoriasMedicionForm.getFechaDesde() != null) && (gestionarAuditoriasMedicionForm.getFechaDesde().equals(""))))
        gestionarAuditoriasMedicionForm.setFechaDesde(fechaActual);
    if ((gestionarAuditoriasMedicionForm.getFechaHasta() == null) || ((gestionarAuditoriasMedicionForm.getFechaHasta() != null) && (gestionarAuditoriasMedicionForm.getFechaHasta().equals("")))) {
        gestionarAuditoriasMedicionForm.setFechaHasta(fechaActual);
    }
    
    String atributoOrden = gestionarAuditoriasMedicionForm.getAtributoOrden();
    
    String tipoOrden = gestionarAuditoriasMedicionForm.getTipoOrden();
    
    int pagina = gestionarAuditoriasMedicionForm.getPagina();
    
    if (pagina < 1)
        pagina = 1;
    
    AuditoriaMedicionService auditoriaMedicionService = FrameworkServiceFactory.getInstance().openAuditoriaMedicionService();
    
    Map<String, Object> filtros = new HashMap();
    
    String[] ordenArray = new String[1];
    String[] tipoOrdenArray = new String[1];
    ordenArray[0] = atributoOrden;
    tipoOrdenArray[0] = tipoOrden;
      
    if ((gestionarAuditoriasMedicionForm.getFechaDesde() != null) && (!gestionarAuditoriasMedicionForm.getFechaDesde().equals("")))
        filtros.put("fechaDesde", FechaUtil.convertirStringToDate(gestionarAuditoriasMedicionForm.getFechaDesde(), VgcResourceManager.getResourceApp("formato.fecha.corta")));
    if ((gestionarAuditoriasMedicionForm.getFechaHasta() != null) && (!gestionarAuditoriasMedicionForm.getFechaHasta().equals("")))
        filtros.put("fechaHasta", FechaUtil.convertirStringToDate(gestionarAuditoriasMedicionForm.getFechaHasta(), VgcResourceManager.getResourceApp("formato.fecha.corta")));
    if ((gestionarAuditoriasMedicionForm.getUsuario() != null) && (!gestionarAuditoriasMedicionForm.getUsuario().equals("")))
    	filtros.put("usuario", gestionarAuditoriasMedicionForm.getUsuario());
    if ((gestionarAuditoriasMedicionForm.getAccion() != null) && (!gestionarAuditoriasMedicionForm.getAccion().equals("")))
    	filtros.put("accion", gestionarAuditoriasMedicionForm.getAccion());
    if ((gestionarAuditoriasMedicionForm.getOrganizacion() != null) && (!gestionarAuditoriasMedicionForm.getOrganizacion().equals("")))
    	filtros.put("organizacion", gestionarAuditoriasMedicionForm.getOrganizacion());
    
   
    PaginaLista paginaAuditorias = auditoriaMedicionService.getAuditoriaMedicion(pagina, 30, ordenArray, tipoOrdenArray, true, filtros);
    
   
    request.setAttribute("paginaAuditorias", paginaAuditorias);
    
    auditoriaMedicionService.close();
    
    return mapping.findForward(forward);
  }
}
