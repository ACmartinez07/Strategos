package com.visiongc.framework.web.struts.alertas.actions;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.message.MessageService;
import com.visiongc.framework.model.Message.MessageStatus;
import com.visiongc.framework.model.Message.MessageType;
import com.visiongc.framework.model.Message.MessageTypeEvent;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.alertas.forms.AlertaForm;
import com.visiongc.framework.web.struts.alertas.forms.GestionarAlertasForm;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;




public class GestionarAlertasAction
  extends VgcAction
{
  public GestionarAlertasAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.agregarUrlSinParametros(url, nombre, new Integer(2));
  }
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    super.execute(mapping, form, request, response);
    
    String forward = mapping.getParameter();
    
    GestionarAlertasForm gestionarAlertasForm = (GestionarAlertasForm)form;
    
    String atributoOrden = gestionarAlertasForm.getAtributoOrden();
    String tipoOrden = gestionarAlertasForm.getTipoOrden();
    int pagina = gestionarAlertasForm.getPagina();
    
    if (atributoOrden == null)
    {
      atributoOrden = "fecha";
      gestionarAlertasForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null)
    {
      tipoOrden = "DESC";
      gestionarAlertasForm.setTipoOrden(tipoOrden);
    }
    
    if (pagina < 1) {
      pagina = 1;
    }
    if (gestionarAlertasForm.getTiposEventos() == null)
      gestionarAlertasForm.setTiposEventos(com.visiongc.framework.model.Message.MessageTypeEvent.getTiposEventos());
    if (gestionarAlertasForm.getTiposEstatus() == null) {
      gestionarAlertasForm.setTiposEstatus(com.visiongc.framework.model.Message.MessageStatus.getTiposEstatus());
    }
    Usuario usuario = getUsuarioConectado(request);
    MessageService frameworkMessageService = FrameworkServiceFactory.getInstance().openMessageService();
    
    Map<String, Object> filtros = new HashMap();
    
    Date dateActual = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(dateActual);
    String dia = (new StringBuilder()).append(c.get(5)).toString();
    String mes = (new StringBuilder()).append(c.get(2) + 1).toString();
    String ano = (new StringBuilder()).append(c.get(1)).toString();
    String fechaActual = (dia.length() < 2 ? "0" + dia : dia) + "/" + (mes.length() < 2 ? "0" + mes : mes) + "/" + ano;
    
    if ((gestionarAlertasForm.getFechaDesde() == null) || ((gestionarAlertasForm.getFechaDesde() != null) && (gestionarAlertasForm.getFechaDesde().equals(""))))
      gestionarAlertasForm.setFechaDesde(fechaActual);
    if ((gestionarAlertasForm.getFechaHasta() == null) || ((gestionarAlertasForm.getFechaHasta() != null) && (gestionarAlertasForm.getFechaHasta().equals(""))))
      gestionarAlertasForm.setFechaHasta(fechaActual);
    if ((gestionarAlertasForm.getFechaDesde() != null) && (!gestionarAlertasForm.getFechaDesde().equals("")))
      filtros.put("fechaDesde", FechaUtil.convertirStringToDate(gestionarAlertasForm.getFechaDesde(), VgcResourceManager.getResourceApp("formato.fecha.corta")));
    if ((gestionarAlertasForm.getFechaHasta() != null) && (!gestionarAlertasForm.getFechaHasta().equals("")))
      filtros.put("fechaHasta", FechaUtil.convertirStringToDate(gestionarAlertasForm.getFechaHasta(), VgcResourceManager.getResourceApp("formato.fecha.corta")));
    if ((gestionarAlertasForm.getTipoEvento() != null) && (gestionarAlertasForm.getTipoEvento().byteValue() != 0))
      filtros.put("fuente", com.visiongc.framework.model.Message.MessageTypeEvent.getNombre(gestionarAlertasForm.getTipoEvento().byteValue()));
    if ((gestionarAlertasForm.getTipoEstatus() != null) && (gestionarAlertasForm.getTipoEstatus().byteValue() != 0)) {
      filtros.put("estatus", gestionarAlertasForm.getTipoEstatus());
    }
    filtros.put("tipo",com.visiongc.framework.model.Message.MessageType.getTypeAlerta());
    filtros.put("usuarioId", usuario.getUsuarioId());
    
    PaginaLista paginaAlertas = frameworkMessageService.getMessages(pagina, 29, atributoOrden, tipoOrden, true, filtros);
    
    request.setAttribute("paginaAlertas", paginaAlertas);
    
    frameworkMessageService.close();
    
    request.getSession().setAttribute("alerta", getAlerta(getUsuarioConectado(request)));
    
    return mapping.findForward(forward);
  }
  
  public AlertaForm getAlerta(Usuario usuario)
  {
    AlertaForm alerta = new AlertaForm();
    
    MessageService frameworkMessageService = FrameworkServiceFactory.getInstance().openMessageService();
    
    String atributoOrden = "fecha";
    String tipoOrden = "ASC";
    int pagina = 1;
    
    Map<String, Object> filtros = new HashMap();
    
    filtros.put("tipo", com.visiongc.framework.model.Message.MessageType.getTypeAlerta());
    filtros.put("estatus", com.visiongc.framework.model.Message.MessageStatus.getStatusPendiente());
    filtros.put("usuarioId", usuario.getUsuarioId());
    
    PaginaLista paginaAlertas = frameworkMessageService.getMessages(pagina, 20, atributoOrden, tipoOrden, true, filtros);
    alerta.setHayAlertas(Boolean.valueOf(paginaAlertas.getLista().size() > 0));
    
    frameworkMessageService.close();
    
    return alerta;
  }
}
