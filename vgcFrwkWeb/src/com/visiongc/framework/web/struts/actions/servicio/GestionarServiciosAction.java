package com.visiongc.framework.web.struts.actions.servicio;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Servicio.ServicioStatus;
import com.visiongc.framework.model.Servicio.ServicioTypeEvent;
import com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
public class GestionarServiciosAction
  extends VgcAction
{
  public GestionarServiciosAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.clear();
    navBar.agregarUrl(url, nombre);
  }
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	        throws Exception
	    {
	        super.execute(mapping, form, request, response);
	        getBarraAreas(request, "administracion").setBotonSeleccionado("servicios");
	        String forward = mapping.getParameter();
	        GestionarServiciosForm gestionarServiciosForm = (GestionarServiciosForm)form;
	        if(gestionarServiciosForm.getTiposEventos() == null)
	            gestionarServiciosForm.setTiposEventos(com.visiongc.framework.model.Servicio.ServicioTypeEvent.getTiposEventos());
	        if(gestionarServiciosForm.getTiposEstatus() == null)
	            gestionarServiciosForm.setTiposEstatus(com.visiongc.framework.model.Servicio.ServicioStatus.getTiposEstatus());
	        String atributoOrden = gestionarServiciosForm.getAtributoOrden();
	        String tipoOrden = gestionarServiciosForm.getTipoOrden();
	        int pagina = gestionarServiciosForm.getPagina();
	        if(atributoOrden == null)
	        {
	            atributoOrden = "fecha";
	            gestionarServiciosForm.setAtributoOrden(atributoOrden);
	        }
	        if(tipoOrden == null)
	        {
	            tipoOrden = "DESC";
	            gestionarServiciosForm.setTipoOrden(tipoOrden);
	        }
	        if(pagina < 1)
	            pagina = 1;
	        FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
	        Map filtros = new HashMap();
	        Date dateActual = new Date();
	        Calendar c = Calendar.getInstance();
	        c.setTime(dateActual);
	        String dia = (new StringBuilder()).append(c.get(5)).toString();
	        String mes = (new StringBuilder()).append(c.get(2) + 1).toString();
	        String ano = (new StringBuilder()).append(c.get(1)).toString();
	        String fechaActual = (new StringBuilder(String.valueOf(dia.length() >= 2 ? ((Object) (dia)) : ((Object) ((new StringBuilder("0")).append(dia).toString()))))).append("/").append(mes.length() >= 2 ? mes : (new StringBuilder("0")).append(mes).toString()).append("/").append(ano).toString();
	        if(gestionarServiciosForm.getFechaDesde() == null || gestionarServiciosForm.getFechaDesde() != null && gestionarServiciosForm.getFechaDesde().equals(""))
	            gestionarServiciosForm.setFechaDesde(fechaActual);
	        if(gestionarServiciosForm.getFechaHasta() == null || gestionarServiciosForm.getFechaHasta() != null && gestionarServiciosForm.getFechaHasta().equals(""))
	            gestionarServiciosForm.setFechaHasta(fechaActual);
	        if(gestionarServiciosForm.getFechaDesde() != null && !gestionarServiciosForm.getFechaDesde().equals(""))
	            filtros.put("fechaDesde", FechaUtil.convertirStringToDate(gestionarServiciosForm.getFechaDesde(), VgcResourceManager.getResourceApp("formato.fecha.corta")));
	        if(gestionarServiciosForm.getFechaHasta() != null && !gestionarServiciosForm.getFechaHasta().equals(""))
	            filtros.put("fechaHasta", FechaUtil.convertirStringToDate(gestionarServiciosForm.getFechaHasta(), VgcResourceManager.getResourceApp("formato.fecha.corta")));
	        if(gestionarServiciosForm.getTipoEvento() != null && gestionarServiciosForm.getTipoEvento().byteValue() != 0)
	            filtros.put("nombre", com.visiongc.framework.model.Servicio.ServicioTypeEvent.getNombre(gestionarServiciosForm.getTipoEvento().byteValue()));
	        if(gestionarServiciosForm.getTipoEstatus() != null && gestionarServiciosForm.getTipoEstatus().byteValue() != 0)
	            filtros.put("estatus", gestionarServiciosForm.getTipoEstatus());
	        if(gestionarServiciosForm.getUsuarioId() != null && gestionarServiciosForm.getUsuarioId().longValue() != 0L)
	            filtros.put("usuarioId", gestionarServiciosForm.getUsuarioId());
	        com.visiongc.commons.util.PaginaLista paginaServicios = frameworkService.getServicios(pagina, 30, atributoOrden, tipoOrden, filtros);
	        request.getSession().setAttribute("paginaServicios", paginaServicios);
	        frameworkService.close();
	        return mapping.findForward(forward);
	    }
}
