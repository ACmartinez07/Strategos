package com.visiongc.framework.web.struts.actions.grupos;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Grupo;
import com.visiongc.framework.usuarios.UsuariosService;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;


public class ReporteGruposAction
  extends VgcReporteBasicoAction
{
  public ReporteGruposAction() {}
  
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return VgcResourceManager.getResourceApp("reporte.framework.grupos.listagrupos.titulo");
  }
  



  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    String atributoOrden = request.getParameter("atributoOrden");
    String tipoOrden = request.getParameter("tipoOrden");
    

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
    MessageResources messageResources = getResources(request);
    

    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
    

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[4];
    columnas[0] = 43;
    columnas[1] = 13;
    columnas[2] = 22;
    columnas[3] = 22;
    tabla.crearTabla(columnas);
    

    List grupos = usuariosService.getGrupos(0, atributoOrden, tipoOrden).getLista();
    

    tabla.setAlineacionHorizontal(1);
    

    tabla.agregarCelda(messageResources.getMessage("reporte.framework.grupos.listagrupos.grupo"));
    tabla.agregarCelda(messageResources.getMessage("reporte.framework.grupos.listagrupos.nropermisos"));
    tabla.agregarCelda(messageResources.getMessage("reporte.framework.grupos.listagrupos.creado"));
    tabla.agregarCelda(messageResources.getMessage("reporte.framework.grupos.listagrupos.modificado"));
    
    tabla.setDefaultAlineacionHorizontal();
    if (grupos.size() > 0) {
      for (Iterator iter = grupos.iterator(); iter.hasNext();) {
        Grupo grupo = (Grupo)iter.next();
        

        tabla.setDefaultAlineacionHorizontal();
        tabla.agregarCelda(grupo.getGrupo());
        tabla.setAlineacionHorizontal(2);
        tabla.agregarCelda(Integer.toString(grupo.getPermisos().size()));
        tabla.agregarCelda(VgcFormatter.formatearFecha(grupo.getCreado(), "dd-MM-yyyy hh:mm:ss aa"));
        tabla.agregarCelda(VgcFormatter.formatearFecha(grupo.getModificado(), "dd-MM-yyyy hh:mm:ss aa"));
      }
      

      documento.add(tabla.getTabla());
    }
    else
    {
      tabla.setColspan(4);
      font.setSize(getConfiguracionPagina(request).getTamanoFuente().floatValue());
      font.setStyle(2);
      Paragraph texto = new Paragraph(messageResources.getMessage("reporte.framework.grupos.listagrupos.nogrupos"), font);
      texto.setAlignment(0);
      documento.add(texto);
    }
    

    documento.newPage();
    

    usuariosService.close();
  }
}
