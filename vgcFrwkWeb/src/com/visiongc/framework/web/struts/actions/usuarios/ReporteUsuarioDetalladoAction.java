package com.visiongc.framework.web.struts.actions.usuarios;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaDetallesObjeto;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.UsuarioGrupo;
import com.visiongc.framework.usuarios.UsuariosService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class ReporteUsuarioDetalladoAction extends VgcReporteBasicoAction
{
  public ReporteUsuarioDetalladoAction() {}
  
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
  {
    return VgcResourceManager.getResourceApp("reporte.framework.usuarios.detallado.titulo");
  }
  
  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    Font fuente = getConfiguracionPagina(request).getFuente();
    MessageResources messageResources = getResources(request);
    
    UsuariosService us = FrameworkServiceFactory.getInstance().openUsuariosService(getLocale(request));
    
    Usuario usuario = (Usuario)us.load(Usuario.class, new Long(request.getParameter("usuarioId")));
    

    agregarSubTitulo(documento, getConfiguracionPagina(request), messageResources.getMessage("reporte.framework.usuarios.detallado.datosgenerales"), true);
    
    TablaDetallesObjeto tabla = new TablaDetallesObjeto(getConfiguracionPagina(request), 30, 70, 100, request);
    
    tabla.agregarDetalle(messageResources.getMessage("reporte.framework.usuarios.detallado.nombrecompleto"), usuario.getFullName());
    
    tabla.agregarDetalle(messageResources.getMessage("reporte.framework.usuarios.detallado.loginusuario"), usuario.getUId());
    
    if (usuario.getCreado() != null) {
      tabla.agregarDetalle(messageResources.getMessage("reporte.framework.usuarios.detallado.creado"), VgcFormatter.formatearFecha(usuario.getCreado(), "formato.timestamp"));
    }
    
    if (usuario.getModificado() != null) {
      tabla.agregarDetalle(messageResources.getMessage("reporte.framework.usuarios.detallado.modificado"), VgcFormatter.formatearFecha(usuario.getModificado(), "formato.timestamp"));
    }
    
    String detalle = "";
    if (usuario.getIsAdmin().booleanValue()) {
      detalle = getResources(request).getMessage("commons.yes");
    } else {
      detalle = getResources(request).getMessage("commons.no");
    }
    tabla.agregarDetalle(messageResources.getMessage("reporte.framework.usuarios.detallado.esadministrador"), detalle);
    
    if (usuario.getEstatus().intValue() == 0) {
      detalle = getResources(request).getMessage("jsp.framework.editarusuario.label.estatus.activo");
    }
    else if (usuario.getEstatus().intValue() == 1) {
      detalle = getResources(request).getMessage("jsp.framework.editarusuario.label.estatus.inactivo");
    }
    else if (usuario.getEstatus().intValue() == 2) {
      detalle = getResources(request).getMessage("jsp.framework.editarusuario.label.estatus.deshabilitado");
    }
    tabla.agregarDetalle(messageResources.getMessage("reporte.framework.usuarios.detallado.estatus"), detalle);
    
    if (usuario.getBloqueado().booleanValue()) {
      detalle = getResources(request).getMessage("commons.yes");
    } else {
      detalle = getResources(request).getMessage("commons.no");
    }
    tabla.agregarDetalle(messageResources.getMessage("reporte.framework.usuarios.detallado.bloqueado"), detalle);
    
    documento.add(tabla.getTabla());
    
    
    // grupos 
    
    
    if (!usuario.getIsAdmin().booleanValue())
    {
      Set grupos = usuario.getUsuarioGrupos();
      
      documento.add(lineaEnBlanco(fuente));
      documento.add(lineaEnBlanco(fuente));
      

      agregarSubTitulo(documento, getConfiguracionPagina(request), messageResources.getMessage("reporte.framework.usuarios.detallado.gruposorganizacion"), true);
      
      Long organizacionId = null;
      String nombreOrganizacion = "";
      ArrayList nombresGrupos = null;
      TablaBasicaPDF tablaGrupos = new TablaBasicaPDF(getConfiguracionPagina(request), request);
      
      int[] columnas = new int[2];
      
      columnas[0] = 60;
      columnas[1] = 40;
      boolean hayGrupos = false;
      
      tablaGrupos.crearTabla(columnas);
      
      tablaGrupos.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.detallado.organizacion"));
      tablaGrupos.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.detallado.grupopermisos"));
      for (Iterator i = grupos.iterator(); i.hasNext();) {
        UsuarioGrupo ug = (UsuarioGrupo)i.next();
        
        if (ug.getOrganizacion().getOrganizacionId() != organizacionId) {
          hayGrupos = true;
          if ((nombresGrupos != null) && (nombresGrupos.size() > 0)) {
            tablaGrupos.setRowspan(nombresGrupos.size());
            tablaGrupos.agregarCelda(nombreOrganizacion);
            tablaGrupos.setRowspan(1);
            for (Iterator iter = nombresGrupos.iterator(); iter.hasNext();) {
              tablaGrupos.agregarCelda(iter.next());
            }
          }
          Organizacion org = ug.getOrganizacion();
          organizacionId = org.getOrganizacionId();
          nombreOrganizacion = "";
          while (org.getPadreId() != null) {
            nombreOrganizacion = org.getNombre() + " \\ " + nombreOrganizacion;
            org = org.getPadre();
          }
          nombreOrganizacion = org.getNombre() + " \\ " + nombreOrganizacion;
          nombreOrganizacion = nombreOrganizacion.substring(0, nombreOrganizacion.length() - 3);
          
          nombresGrupos = new ArrayList();
        }
        nombresGrupos.add(ug.getGrupo().getGrupo());
      }
           
      if ((nombresGrupos != null) && (nombresGrupos.size() > 0)) {
        tablaGrupos.setRowspan(nombresGrupos.size());
        tablaGrupos.agregarCelda(nombreOrganizacion);
        tablaGrupos.setRowspan(1);
        for (Iterator iter = nombresGrupos.iterator(); iter.hasNext();) {
          tablaGrupos.agregarCelda(iter.next());
        }
      }
      
      if (hayGrupos) {
        documento.add(tablaGrupos.getTabla());
      } else {
        fuente.setSize(14.0F);
        fuente.setStyle(0);
        Paragraph texto = new Paragraph(messageResources.getMessage("reporte.framework.usuarios.detallado.nogrupos"), fuente);
        texto.setAlignment(0);
        texto.setSpacingBefore(10.0F);
        documento.add(texto);
      }
    }
    
    us.close();
  }
}
