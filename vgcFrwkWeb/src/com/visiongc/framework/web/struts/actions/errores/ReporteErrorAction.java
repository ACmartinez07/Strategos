package com.visiongc.framework.web.struts.actions.errores;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.commons.report.TablaDetallesObjeto;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Error;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class ReporteErrorAction extends VgcReporteBasicoAction
{
  public ReporteErrorAction() {}
  
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
  {
    return VgcResourceManager.getResourceApp("reporte.framework.errores.error.titulo");
  }
  




  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    Font fuente = getConfiguracionPagina(request).getFuente();
    MessageResources messageResources = getResources(request);
    
    FrameworkService fs = FrameworkServiceFactory.getInstance().openFrameworkService(getLocale(request));
    
    Map filtros = new HashMap();
    
    Date fecha = new Date();
    
    fecha.setTime(Long.parseLong(request.getParameter("errTimestamp")));
    
    filtros.put("objetoCompleto", new Boolean(true));
    filtros.put("errTimestamp", fecha);
    
    PaginaLista pagina = fs.getErrores(0, 0, "errTimestamp", "desc", filtros);
    
    documento.add(new Paragraph(" "));
    
    agregarSubTitulo(documento, getConfiguracionPagina(request), messageResources.getMessage("reporte.framework.errores.error.datosgenerales"), true);
    
    if (pagina.getLista().size() == 0) {
      Paragraph texto = new Paragraph(messageResources.getMessage("reporte.framework.errores.error.noencontrado"), fuente);
      texto.setAlignment(0);
      texto.setSpacingAfter(-15.0F);
      documento.add(texto);
    }
    else {
      for (Iterator iter = pagina.getLista().iterator(); iter.hasNext();) {
        Error error = (Error)iter.next();
        detallesError(documento, fuente, error, messageResources, request);
      }
    }
    
    fs.close();
  }
  
  private void detallesError(Document documento, Font font, Error error, MessageResources messageResources, HttpServletRequest request) throws Exception
  {
    TablaDetallesObjeto tabla = new TablaDetallesObjeto(getConfiguracionPagina(request), 30, 70, 100, request);
    
    if ((error.getErrTimestamp() != null) && (!error.getErrTimestamp().equals(""))) {
      tabla.agregarDetalle(messageResources.getMessage("reporte.framework.errores.error.fecha"), error.getErrTimestamp().toString());
    }
    if ((error.getErrNumber() != null) && (!error.getErrNumber().equals(""))) {
      tabla.agregarDetalle(messageResources.getMessage("reporte.framework.errores.error.numero"), error.getErrNumber().toString());
    }
    if ((error.getErrDescription() != null) && (!error.getErrDescription().equals(""))) {
      tabla.agregarDetalle(messageResources.getMessage("reporte.framework.errores.error.descripcion"), error.getErrDescription());
    }
    if ((error.getErrCause() != null) && (!error.getErrCause().equals(""))) {
      tabla.agregarDetalle(messageResources.getMessage("reporte.framework.errores.error.causa"), error.getErrCause());
    }
    if ((error.getErrSource() != null) && (!error.getErrSource().equals(""))) {
      tabla.agregarDetalle(messageResources.getMessage("reporte.framework.errores.error.fuente"), error.getErrSource());
    }
    if ((error.getErrUserId() != null) && (!error.getErrUserId().equals(""))) {
      tabla.agregarDetalle(messageResources.getMessage("reporte.framework.errores.error.usuario"), error.getErrUserId());
    }
    
    documento.add(tabla.getTabla());
    
    if ((error.getErrStackTrace() != null) && (!error.getErrStackTrace().equals("")))
    {
      documento.add(lineaEnBlanco(font));
      font.setSize(14.0F);
      font.setStyle(1);
      Paragraph texto = new Paragraph(messageResources.getMessage("reporte.framework.errores.error.trazapila"), font);
      texto.setAlignment(0);
      documento.add(texto);
      font.setSize(8.0F);
      font.setStyle(0);
      texto = new Paragraph(error.getErrStackTrace(), font);
      texto.setAlignment(0);
      documento.add(texto);
    }
  }
}
