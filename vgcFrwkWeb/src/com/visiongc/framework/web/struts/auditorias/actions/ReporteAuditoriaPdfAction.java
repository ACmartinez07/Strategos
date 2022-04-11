package com.visiongc.framework.web.struts.auditorias.actions;

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

public class ReporteAuditoriaPdfAction extends VgcReporteBasicoAction
{
  public ReporteAuditoriaPdfAction() {}
  
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
  {
    return VgcResourceManager.getResourceApp("reporte.framework.auditorias.detallado.titulo");
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
	  
	 AuditoriaMedicionService auditoriaMedicionService = FrameworkServiceFactory.getInstance().openAuditoriaMedicionService();
	  
	 List<AuditoriaMedicion> auditorias = new ArrayList();
	 
	 Map<String, Object> filtros = new HashMap();
	    
		
		String usuario = request.getParameter("usuario");
		String fechaDesde = request.getParameter("fechaDesde");
		String fechaHasta = request.getParameter("fechaHasta");
		String accion = request.getParameter("accion");
		String organizacion = request.getParameter("organizacion");
		
		String atributoOrden = request.getParameter("atributoOrden");
	    
	    String tipoOrden = request.getParameter("tipoOrden");
		
	    String[] ordenArray = new String[1];
	    String[] tipoOrdenArray = new String[1];
	    ordenArray[0] = atributoOrden;
	    tipoOrdenArray[0] = tipoOrden;
	      
	    if ((fechaDesde != null) && (!fechaDesde.equals("")))
	        filtros.put("fechaDesde", FechaUtil.convertirStringToDate(fechaDesde, VgcResourceManager.getResourceApp("formato.fecha.corta")));
	    if ((fechaHasta != null) && (!fechaHasta.equals("")))
	        filtros.put("fechaHasta", FechaUtil.convertirStringToDate(fechaHasta, VgcResourceManager.getResourceApp("formato.fecha.corta")));
	    if ((usuario != null) && (!usuario.equals("")))
	    	filtros.put("usuario", usuario);
	    if ((accion != null) && (!accion.equals("")))
	    	filtros.put("accion", accion);
	    if ((organizacion != null) && (!organizacion.equals("")))
	    	filtros.put("organizacion", organizacion);
		
		 
		auditorias= auditoriaMedicionService.getAuditoriasMedicion(ordenArray, tipoOrdenArray, true, filtros);
	 
	 documento.add(lineaEnBlanco(fuente));
	 
	 for(Iterator<AuditoriaMedicion> iter = auditorias.iterator(); iter.hasNext(); ){
		
		AuditoriaMedicion auditoria = iter.next();
		
		TablaPDF tabla = null;
	    tabla = new TablaPDF(getConfiguracionPagina(request), request);
	    int[] columnas = new int[9];
	    columnas[0] = 21;
	    columnas[1] = 17;
	    columnas[2] = 14;
	    columnas[3] = 25;
	    columnas[4] = 21;
	    columnas[5] = 21;
	    columnas[6] = 21;
	    columnas[7] = 10;
	    columnas[8] = 10;
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);
	    
	    tabla.setAlineacionHorizontal(1);
	    

	    tabla.agregarCelda(messageResources.getMessage("reporte.framework.auditorias.detalle.fecha"));
	    tabla.agregarCelda(messageResources.getMessage("reporte.framework.auditorias.detalle.usuario"));
	    tabla.agregarCelda(messageResources.getMessage("reporte.framework.auditorias.detalle.accion"));
	    tabla.agregarCelda(messageResources.getMessage("reporte.framework.auditorias.detalle.organizacion"));
	    tabla.agregarCelda(messageResources.getMessage("reporte.framework.auditorias.detalle.indicador"));
	    tabla.agregarCelda(messageResources.getMessage("reporte.framework.auditorias.detalle.clase"));
	    tabla.agregarCelda(messageResources.getMessage("reporte.framework.auditorias.detalle.iniciativa"));
	    tabla.agregarCelda(messageResources.getMessage("reporte.framework.auditorias.detalle.periodo.inicial"));
	    tabla.agregarCelda(messageResources.getMessage("reporte.framework.auditorias.detalle.periodo.final"));
	    
	    tabla.setDefaultAlineacionHorizontal();
	    
	    tabla.setAlineacionHorizontal(0);
	    
	    tabla.agregarCelda(VgcFormatter.formatearFecha(auditoria.getFechaEjecucion(), "dd-MM-yyyy hh:mm:ss aa"));
	    tabla.agregarCelda(auditoria.getUsuario());
	    tabla.agregarCelda(auditoria.getAccion());
	    tabla.agregarCelda(auditoria.getOrganizacion());
	    tabla.agregarCelda(auditoria.getIndicador());
	    tabla.agregarCelda(auditoria.getClase());
	    tabla.agregarCelda(auditoria.getIniciativa());
	    tabla.agregarCelda(auditoria.getPeriodo());
	    tabla.agregarCelda(auditoria.getPeriodoFinal());
       
      
	 }
	 
	 documento.newPage();
	 auditoriaMedicionService.close();
	 
  }
  
  
}
