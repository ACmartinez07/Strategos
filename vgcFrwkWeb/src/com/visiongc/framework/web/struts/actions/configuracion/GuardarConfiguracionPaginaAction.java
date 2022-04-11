package com.visiongc.framework.web.struts.actions.configuracion;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ObjetoBinario;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.forms.configuracion.EditarConfiguracionPaginaForm;
import java.awt.Image;
import java.io.File;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;



public class GuardarConfiguracionPaginaAction
  extends VgcAction
{
  private static final String ACTION_KEY = "GuardarConfiguracionPaginaAction";
  private static int TAMANO_MAXIMO_IMAGEN = 20480;
  

  public GuardarConfiguracionPaginaAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    String forward = mapping.getParameter();
    

    EditarConfiguracionPaginaForm editarConfiguracionPaginaForm = (EditarConfiguracionPaginaForm)form;
    

    ActionMessages messages = getMessages(request);
    




    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarConfiguracionPaginaAction.ultimoTs");
    

    if ((ts == null) || (ts.equals(""))) {
      cancelar = true;
    } else if (ultimoTs != null)
    {
      if (ultimoTs.equals(ts)) {
        cancelar = true;
      }
    }
    if (cancelar)
    {
      request.setAttribute("ajaxResponse", "");
      return mapping.findForward("ajaxResponse");
    }
    
    String respuesta = Validar(request, editarConfiguracionPaginaForm, messages);
    if (!respuesta.equals("Success"))
    {

      return mapping.findForward(respuesta);
    }
    

    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    
    String nombreConfiguracionBase = editarConfiguracionPaginaForm.getConfiguracionBase();
    
    ConfiguracionPagina configuracionPagina = frameworkService.getConfiguracionPagina(nombreConfiguracionBase, getUsuarioConectado(request).getUsuarioId());
    
    configuracionPagina.setNombreConfiguracionBase(nombreConfiguracionBase);
    configuracionPagina.setTamanoMargenSuperior(new Double(VgcFormatter.parsearNumeroFormateado(editarConfiguracionPaginaForm.getTamanoMargenSuperior())));
    configuracionPagina.setTamanoMargenInferior(new Double(VgcFormatter.parsearNumeroFormateado(editarConfiguracionPaginaForm.getTamanoMargenInferior())));
    configuracionPagina.setTamanoMargenIzquierdo(new Double(VgcFormatter.parsearNumeroFormateado(editarConfiguracionPaginaForm.getTamanoMargenIzquierdo())));
    configuracionPagina.setTamanoMargenDerecho(new Double(VgcFormatter.parsearNumeroFormateado(editarConfiguracionPaginaForm.getTamanoMargenDerecho())));
    
    configuracionPagina.setEncabezadoIzquierdo(editarConfiguracionPaginaForm.getEncabezadoIzquierdo());
    configuracionPagina.setEncabezadoCentro(editarConfiguracionPaginaForm.getEncabezadoCentro());
    configuracionPagina.setEncabezadoDerecho(editarConfiguracionPaginaForm.getEncabezadoDerecho());
    
    configuracionPagina.setPiePaginaIzquierdo(editarConfiguracionPaginaForm.getPiePaginaIzquierdo());
    configuracionPagina.setPiePaginaCentro(editarConfiguracionPaginaForm.getPiePaginaCentro());
    configuracionPagina.setPiePaginaDerecho(editarConfiguracionPaginaForm.getPiePaginaDerecho());
    
    configuracionPagina.setNombreFuente(editarConfiguracionPaginaForm.getNombreFuente());
    configuracionPagina.setEstiloFuente(editarConfiguracionPaginaForm.getEstiloFuente());
    configuracionPagina.setTamanoFuente(editarConfiguracionPaginaForm.getTamanoFuente());
    
    respuesta = setImagenes(editarConfiguracionPaginaForm, configuracionPagina, request, messages);
    if (!respuesta.equals("Success")) {
      return mapping.findForward(respuesta);
    }
    int resultado = frameworkService.saveConfiguracionPagina(configuracionPagina, getUsuarioConectado(request));
    
    if (resultado == 10000) {
      forward = "finalizarConfiguracionPagina";
    } else if (resultado == 10003)
    {

      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarconfiguracion.duplicado"));
    }
    

    frameworkService.close();
    

    saveMessages(request, messages);
    

    request.getSession().setAttribute("GuardarConfiguracionPaginaAction.ultimoTs", ts);
    

    return mapping.findForward(forward);
  }
  
  private String setImagenes(EditarConfiguracionPaginaForm editarConfiguracionPaginaForm, ConfiguracionPagina configuracionPagina, HttpServletRequest request, ActionMessages messages) throws Exception
  {
    String forward = "Success";
    forward = setImagen(1, editarConfiguracionPaginaForm, configuracionPagina, request, messages);
    if (forward != "Success") {
      return forward;
    }
    forward = setImagen(2, editarConfiguracionPaginaForm, configuracionPagina, request, messages);
    if (forward != "Success") {
      return forward;
    }
    forward = setImagen(3, editarConfiguracionPaginaForm, configuracionPagina, request, messages);
    if (forward != "Success") {
      return forward;
    }
    forward = setImagen(4, editarConfiguracionPaginaForm, configuracionPagina, request, messages);
    if (forward != "Success") {
      return forward;
    }
    forward = setImagen(5, editarConfiguracionPaginaForm, configuracionPagina, request, messages);
    if (forward != "Success") {
      return forward;
    }
    forward = setImagen(6, editarConfiguracionPaginaForm, configuracionPagina, request, messages);
    if (forward != "Success") {
      return forward;
    }
    return forward;
  }
  
  private String setImagen(int numero, EditarConfiguracionPaginaForm editarConfiguracionPaginaForm, ConfiguracionPagina configuracionPagina, HttpServletRequest request, ActionMessages messages) throws Exception
  {
    ObjetoBinario objetoBinario = new ObjetoBinario();
    String nombreParametro = "imagen";
    if (numero == 1)
    {
      nombreParametro = nombreParametro + "SupIzq";
      objetoBinario.setNombre(editarConfiguracionPaginaForm.getNombreImagenSupIzq());
      configuracionPagina.setImagenSupIzq(objetoBinario);
    }
    else if (numero == 2)
    {
      nombreParametro = nombreParametro + "SupCen";
      objetoBinario.setNombre(editarConfiguracionPaginaForm.getNombreImagenSupCen());
      configuracionPagina.setImagenSupCen(objetoBinario);
    }
    else if (numero == 3)
    {
      nombreParametro = nombreParametro + "SupDer";
      objetoBinario.setNombre(editarConfiguracionPaginaForm.getNombreImagenSupDer());
      configuracionPagina.setImagenSupDer(objetoBinario);
    }
    else if (numero == 4)
    {
      nombreParametro = nombreParametro + "InfIzq";
      objetoBinario.setNombre(editarConfiguracionPaginaForm.getNombreImagenInfIzq());
      configuracionPagina.setImagenInfIzq(objetoBinario);
    }
    else if (numero == 5)
    {
      nombreParametro = nombreParametro + "InfCen";
      objetoBinario.setNombre(editarConfiguracionPaginaForm.getNombreImagenInfCen());
      configuracionPagina.setImagenInfCen(objetoBinario);
    }
    else if (numero == 6)
    {
      nombreParametro = nombreParametro + "InfDer";
      objetoBinario.setNombre(editarConfiguracionPaginaForm.getNombreImagenInfDer());
      configuracionPagina.setImagenInfDer(objetoBinario);
    }
    
    FormFile archivo = (FormFile)editarConfiguracionPaginaForm.getMultipartRequestHandler().getFileElements().get(nombreParametro);
    
    if (archivo != null)
    {
      byte[] dataArchivo = archivo.getFileData();
      
      if (dataArchivo.length > 0)
      {
        String respuesta = "Success";
        String tipo = archivo.getContentType();
        int tamano = archivo.getFileSize();
        
        if ((!tipo.equals("image/jpeg")) && (!tipo.equals("image/pjpeg")) && (!tipo.equals("image/x-png")) && (!tipo.equals("image/gif"))) {
          respuesta = "imagenInvalida," + nombreParametro;
        } else if ((tamano == 0) || (tamano > TAMANO_MAXIMO_IMAGEN)) {
          respuesta = "tamanoInvalido," + nombreParametro;
        }
        else {
          Image image = ImageIO.read(archivo.getInputStream());
          int height = image.getHeight(null);
          int width = image.getWidth(null);
          
          if ((height > 50) || (width > 50)) {
            respuesta = "dimensionInvalida," + nombreParametro;
          }
        }
        if (!respuesta.equals("Success"))
        {
          String mensaje = "";
          String[] resp = respuesta.split(",");
          if (resp[0].equals("imagenInvalida")) {
            mensaje = "action.guardarconfiguracion.validacion.imagenInvalida";
          } else if (resp[0].equals("tamanoInvalido")) {
            mensaje = "action.guardarconfiguracion.validacion.tamanoInvalido";
          } else if (resp[0].equals("dimensionInvalida")) {
            mensaje = "action.guardarconfiguracion.validacion.dimensionInvalida";
          } else if (resp[0].equals("direcciondesconocida")) {
            mensaje = "action.guardarconfiguracion.validacion.direcciondesconocida";
          }
          String mensaje2 = "";
          if (resp[1].equals("imagenSupIzq")) {
            mensaje2 = "imagen superior izquierda";
          } else if (resp[1].equals("imagenSupCen")) {
            mensaje2 = "imagen superior central";
          } else if (resp[1].equals("imagenSupDer")) {
            mensaje2 = "imagen superior derecha";
          } else if (resp[1].equals("imagenInfIzq")) {
            mensaje2 = "imagen inferior izquierda";
          } else if (resp[1].equals("imagenInfCen")) {
            mensaje2 = "imagen inferior central";
          } else if (resp[1].equals("imagenInfDer")) {
            mensaje2 = "imagen inferior derecha";
          }
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(mensaje, mensaje2));
          

          saveMessages(request, messages);
          
          return "imagenInvalida";
        }
        
        objetoBinario.setMimeType(archivo.getContentType());
        objetoBinario.setData(dataArchivo);
        objetoBinario.setNombre(archivo.getFileName());
      }
    }
    
    return "Success";
  }
  
  public String Validar(HttpServletRequest request, EditarConfiguracionPaginaForm editarConfiguracionPaginaForm, ActionMessages messages) throws Exception
  {
    String respuesta = "Success";
    
    if (respuesta.equals("Success"))
      respuesta = ValidarImagen(request, editarConfiguracionPaginaForm, "imagenSupIzq");
    if (respuesta.equals("Success"))
      respuesta = ValidarImagen(request, editarConfiguracionPaginaForm, "imagenSupCen");
    if (respuesta.equals("Success"))
      respuesta = ValidarImagen(request, editarConfiguracionPaginaForm, "imagenSupDer");
    if (respuesta.equals("Success"))
      respuesta = ValidarImagen(request, editarConfiguracionPaginaForm, "imagenInfIzq");
    if (respuesta.equals("Success"))
      respuesta = ValidarImagen(request, editarConfiguracionPaginaForm, "imagenInfCen");
    if (respuesta.equals("Success")) {
      respuesta = ValidarImagen(request, editarConfiguracionPaginaForm, "imagenInfDer");
    }
    if (!respuesta.equals("Success"))
    {
      String mensaje = "";
      String[] resp = respuesta.split(",");
      if (resp[0].equals("imagenInvalida")) {
        mensaje = "action.guardarconfiguracion.validacion.imagenInvalida";
      } else if (resp[0].equals("tamanoInvalido")) {
        mensaje = "action.guardarconfiguracion.validacion.tamanoInvalido";
      } else if (resp[0].equals("dimensionInvalida")) {
        mensaje = "action.guardarconfiguracion.validacion.dimensionInvalida";
      } else if (resp[0].equals("direcciondesconocida")) {
        mensaje = "action.guardarconfiguracion.validacion.direcciondesconocida";
      }
      String mensaje2 = "";
      if (resp[1].equals("imagenSupIzq")) {
        mensaje2 = "imagen superior izquierda";
      } else if (resp[1].equals("imagenSupCen")) {
        mensaje2 = "imagen superior central";
      } else if (resp[1].equals("imagenSupDer")) {
        mensaje2 = "imagen superior derecha";
      } else if (resp[1].equals("imagenInfIzq")) {
        mensaje2 = "imagen inferior izquierda";
      } else if (resp[1].equals("imagenInfCen")) {
        mensaje2 = "imagen inferior central";
      } else if (resp[1].equals("imagenInfDer")) {
        mensaje2 = "imagen inferior derecha";
      }
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(mensaje, mensaje2));
      

      saveMessages(request, messages);
      
      return "imagenInvalida";
    }
    
    return respuesta;
  }
  
  private String ValidarImagen(HttpServletRequest request, EditarConfiguracionPaginaForm editarConfiguracionPaginaForm, String nombreImagen) throws Exception
  {
    String respuesta = "Success";
    String imagen = request.getParameter(nombreImagen);
    FormFile archivo = null;
    File sarchivo = null;
    if (imagen == null)
    {
      if (nombreImagen == "imagenSupIzq") {
        imagen = editarConfiguracionPaginaForm.getNombreImagenSupIzq();
      } else if (nombreImagen == "imagenSupCen") {
        imagen = editarConfiguracionPaginaForm.getNombreImagenSupCen();
      } else if (nombreImagen == "imagenSupDer") {
        imagen = editarConfiguracionPaginaForm.getNombreImagenSupDer();
      } else if (nombreImagen == "imagenInfIzq") {
        imagen = editarConfiguracionPaginaForm.getNombreImagenInfIzq();
      } else if (nombreImagen == "imagenInfCen") {
        imagen = editarConfiguracionPaginaForm.getNombreImagenInfCen();
      } else if (nombreImagen == "imagenInfDer") {
        imagen = editarConfiguracionPaginaForm.getNombreImagenInfDer();
      }
      archivo = (FormFile)editarConfiguracionPaginaForm.getMultipartRequestHandler().getFileElements().get(nombreImagen);
      if ((imagen != null) && (!imagen.equals("")))
      {
        sarchivo = new File(imagen);
        if (!sarchivo.exists()) {
          return respuesta;
        }
      }
    }
    
    if ((imagen != null) && (!imagen.equals("")))
    {
      sarchivo = null;
      if (archivo != null)
      {
        if ((archivo.getFileName().indexOf(".jpg") == -1) && (archivo.getFileName().indexOf(".png") == -1) && (archivo.getFileName().indexOf(".gif") == -1)) {
          respuesta = "imagenInvalida," + nombreImagen;

        }
        else if ((archivo.getFileSize() == 0) || (archivo.getFileSize() > TAMANO_MAXIMO_IMAGEN)) {
          respuesta = "tamanoInvalido," + nombreImagen;
        }
        else {
          Image image = ImageIO.read(archivo.getInputStream());
          int height = image.getHeight(null);
          int width = image.getWidth(null);
          
          if ((height > 50) || (width > 50)) {
            respuesta = "dimensionInvalida," + nombreImagen;
          }
        }
      }
      else
      {
        sarchivo = new File(imagen);
        if (sarchivo.exists())
        {
          if ((sarchivo.getName().indexOf(".jpg") == -1) && (sarchivo.getName().indexOf(".png") == -1) && (sarchivo.getName().indexOf(".gif") == -1)) {
            respuesta = "imagenInvalida," + nombreImagen;

          }
          else if ((sarchivo.length() == 0L) || (sarchivo.length() > TAMANO_MAXIMO_IMAGEN)) {
            respuesta = "tamanoInvalido," + nombreImagen;
          }
          else {
            Image image = ImageIO.read(sarchivo);
            int height = image.getHeight(null);
            int width = image.getWidth(null);
            
            if ((height > 50) || (width > 50)) {
              respuesta = "dimensionInvalida," + nombreImagen;
            }
          }
        }
        else {
          respuesta = "direcciondesconocida," + nombreImagen;
        }
      }
    }
    return respuesta;
  }
}
