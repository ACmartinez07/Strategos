package com.visiongc.framework.web.struts.alertas.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.message.MessageService;
import com.visiongc.framework.model.Message;
import com.visiongc.framework.model.Usuario;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;




public class ReporteAlertasAction
  extends VgcReporteBasicoAction
{
  public ReporteAlertasAction() {}
  
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("action.reportealertas.titulo");
  }
  
  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
  {
    Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
    MessageResources mensajes = getResources(request);
    
    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
    Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
    
    font.setSize(10.0F);
    Paragraph texto = new Paragraph(mensajes.getMessage("action.reportealertas.subtitulo.usuario") + ": " + usuario.getFullName(), font);
    texto.setAlignment(0);
    texto.setSpacingBefore(1.0F);
    documento.add(texto);
    
    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[4];
    columnas[0] = 20;
    columnas[1] = 20;
    columnas[2] = 40;
    columnas[3] = 20;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);
    
    font.setSize(8.0F);
    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(1);
    
    tabla.agregarCelda(mensajes.getMessage("jsp.gestionaralertas.columna.fecha"));
    tabla.agregarCelda(mensajes.getMessage("jsp.gestionaralertas.columna.estatus"));
    tabla.agregarCelda(mensajes.getMessage("jsp.gestionaralertas.columna.mensaje"));
    tabla.agregarCelda(mensajes.getMessage("jsp.gestionaralertas.columna.fuente"));
    
    tabla.setDefaultAlineacionHorizontal();
    
    MessageService frameworkMessageService = FrameworkServiceFactory.getInstance().openMessageService();
    
    Map<String, Object> filtros = new HashMap();
    
    filtros.put("tipo", "0");
    filtros.put("usuarioId", usuario.getUsuarioId());
    
    PaginaLista paginaAlertas = frameworkMessageService.getMessages(1, 0, "fecha", "ASC", true, filtros);
    frameworkMessageService.close();
    
    if (paginaAlertas.getLista().size() > 0)
    {
      font.setSize(8.0F);
      fontBold.setSize(8.0F);
      fontBold.setStyle(1);
      for (Iterator<Message> iter = paginaAlertas.getLista().iterator(); iter.hasNext();)
      {
        Message alerta = (Message)iter.next();
        if (alerta.getEstatus().byteValue() == 1) {
          tabla.setFormatoFont(fontBold.style());
        } else
          tabla.setFormatoFont(font.style());
        tabla.setDefaultAlineacionHorizontal();
        tabla.agregarCelda(alerta.getFechaFormateada());
        
        tabla.setAlineacionHorizontal(1);
        tabla.agregarCelda(alerta.getEstatusNombre());
        
        tabla.setAlineacionHorizontal(0);
        tabla.agregarCelda(alerta.getMensaje());
        
        tabla.setAlineacionHorizontal(0);
        tabla.agregarCelda(alerta.getFuente());
      }
      
      documento.add(tabla.getTabla());
      
      font.setSize(10.0F);
      texto = new Paragraph("Número de Alertas: " + Integer.toString(paginaAlertas.getLista().size()), font);
      texto.setAlignment(0);
      documento.add(texto);

    }
    else
    {
      font.setSize(10.0F);
      texto = new Paragraph("No hay alertas para el usuario", font);
      texto.setAlignment(0);
      texto.setSpacingBefore(72.0F);
      documento.add(texto);
    }
    
    documento.newPage();
  }
}
