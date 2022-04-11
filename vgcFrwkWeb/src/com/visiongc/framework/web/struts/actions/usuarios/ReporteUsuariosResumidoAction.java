package com.visiongc.framework.web.struts.actions.usuarios;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;
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
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class ReporteUsuariosResumidoAction extends VgcReporteBasicoAction
{
  public ReporteUsuariosResumidoAction() {}
  
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
  {
    return VgcResourceManager.getResourceApp("reporte.framework.usuarios.resumido.titulo");
  }
  

  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    String atributoOrden = request.getParameter("atributoOrden");
    String tipoOrden = request.getParameter("tipoOrden");
    
    Font fuente = getConfiguracionPagina(request).getFuente();
    MessageResources messageResources = getResources(request);
    
    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService(getLocale(request));
    

    TablaPDF tabla = null;
    tabla = new TablaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[6];
    columnas[0] = 21;
    columnas[1] = 27;
    columnas[2] = 10;
    columnas[3] = 15;
    columnas[4] = 21;
    columnas[5] = 21;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);
    

    List usuarios = usuariosService.getUsuarios(0, atributoOrden, tipoOrden).getLista();
    

    tabla.setAlineacionHorizontal(1);
    

    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.uid"));
    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.fullname"));
    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.administrador"));
    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.estatus"));
    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.creado"));
    tabla.agregarCelda(messageResources.getMessage("reporte.framework.usuarios.resumido.modificado"));
    
    tabla.setDefaultAlineacionHorizontal();
    if (usuarios.size() > 0) {
      for (Iterator iter = usuarios.iterator(); iter.hasNext();) {
        Usuario usuario = (Usuario)iter.next();
        

        tabla.setAlineacionHorizontal(0);
        tabla.agregarCelda(usuario.getUId());
        tabla.agregarCelda(usuario.getFullName());
        tabla.setAlineacionHorizontal(1);
        if (usuario.getIsAdmin().booleanValue()) {
          tabla.agregarCelda(VgcResourceManager.getResourceApp("comunes.si"));
        } else {
          tabla.agregarCelda(VgcResourceManager.getResourceApp("comunes.no"));
        }
        
        if (usuario.getEstatus().intValue() == 0) {
          tabla.agregarCelda(VgcResourceManager.getResourceApp("jsp.framework.editarusuario.label.estatus.activo"));
        }
        else if (usuario.getEstatus().intValue() == 1) {
          tabla.agregarCelda(VgcResourceManager.getResourceApp("jsp.framework.editarusuario.label.estatus.inactivo"));
        }
        else if (usuario.getEstatus().intValue() == 2) {
          tabla.agregarCelda(VgcResourceManager.getResourceApp("jsp.framework.editarusuario.label.estatus.deshabilitado"));
        }
        
        tabla.agregarCelda(VgcFormatter.formatearFecha(usuario.getCreado(), "dd-MM-yyyy hh:mm:ss aa"));
        tabla.agregarCelda(VgcFormatter.formatearFecha(usuario.getModificado(), "dd-MM-yyyy hh:mm:ss aa"));
      
      

        
      }
      documento.add(tabla.getTabla());
      
    }   
    
    else
    {
      tabla.setColspan(5);
      fuente.setSize(getConfiguracionPagina(request).getTamanoFuente().floatValue());
      fuente.setStyle(2);
      Paragraph texto = new Paragraph(messageResources.getMessage("reporte.framework.grupos.listagrupos.nogrupos"), fuente);
      texto.setAlignment(0);
      documento.add(texto);
    }
    

    documento.newPage();
    

    usuariosService.close();
  }
}

