package com.visiongc.framework.web.struts.actions.configuracion;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ObjetoBinario;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.forms.configuracion.EditarConfiguracionPaginaForm;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;













public class EditarConfiguracionPaginaAction
  extends VgcAction
{
  public EditarConfiguracionPaginaAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    String forward = mapping.getParameter();
    

    EditarConfiguracionPaginaForm editarConfiguracionPaginaForm = (EditarConfiguracionPaginaForm)form;
    

    ActionMessages messages = getMessages(request);
    

    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    
    String nombreConfiguracionBase = request.getParameter("configuracionBase");
    
    ConfiguracionPagina configuracionPagina = frameworkService.getConfiguracionPagina(nombreConfiguracionBase, getUsuarioConectado(request).getUsuarioId());
    
    editarConfiguracionPaginaForm.setTamanoMargenSuperior(VgcFormatter.formatearNumero(configuracionPagina.getTamanoMargenSuperior(), 1));
    editarConfiguracionPaginaForm.setTamanoMargenInferior(VgcFormatter.formatearNumero(configuracionPagina.getTamanoMargenInferior(), 1));
    editarConfiguracionPaginaForm.setTamanoMargenIzquierdo(VgcFormatter.formatearNumero(configuracionPagina.getTamanoMargenIzquierdo(), 1));
    editarConfiguracionPaginaForm.setTamanoMargenDerecho(VgcFormatter.formatearNumero(configuracionPagina.getTamanoMargenDerecho(), 1));
    
    editarConfiguracionPaginaForm.setEncabezadoIzquierdo(configuracionPagina.getEncabezadoIzquierdo());
    editarConfiguracionPaginaForm.setEncabezadoCentro(configuracionPagina.getEncabezadoCentro());
    editarConfiguracionPaginaForm.setEncabezadoDerecho(configuracionPagina.getEncabezadoDerecho());
    
    editarConfiguracionPaginaForm.setPiePaginaIzquierdo(configuracionPagina.getPiePaginaIzquierdo());
    editarConfiguracionPaginaForm.setPiePaginaCentro(configuracionPagina.getPiePaginaCentro());
    editarConfiguracionPaginaForm.setPiePaginaDerecho(configuracionPagina.getPiePaginaDerecho());
    
    editarConfiguracionPaginaForm.setNombreFuente(configuracionPagina.getNombreFuente());
    editarConfiguracionPaginaForm.setEstiloFuente(configuracionPagina.getEstiloFuente());
    editarConfiguracionPaginaForm.setTamanoFuente(configuracionPagina.getTamanoFuente());
    
    configuracionPagina.loadImagenes(false);
    
    if (configuracionPagina.getImagenSupIzq() != null)
      editarConfiguracionPaginaForm.setNombreImagenSupIzq(configuracionPagina.getImagenSupIzq().getNombre());
    if (configuracionPagina.getImagenSupCen() != null)
      editarConfiguracionPaginaForm.setNombreImagenSupCen(configuracionPagina.getImagenSupCen().getNombre());
    if (configuracionPagina.getImagenSupDer() != null)
      editarConfiguracionPaginaForm.setNombreImagenSupDer(configuracionPagina.getImagenSupDer().getNombre());
    if (configuracionPagina.getImagenInfIzq() != null)
      editarConfiguracionPaginaForm.setNombreImagenInfIzq(configuracionPagina.getImagenInfIzq().getNombre());
    if (configuracionPagina.getImagenInfCen() != null)
      editarConfiguracionPaginaForm.setNombreImagenInfCen(configuracionPagina.getImagenInfCen().getNombre());
    if (configuracionPagina.getImagenInfDer() != null) {
      editarConfiguracionPaginaForm.setNombreImagenInfDer(configuracionPagina.getImagenInfDer().getNombre());
    }
    ObjetoClaveValor elementoClaveValor = null;
    
    List<ObjetoClaveValor> fuentes = new ArrayList();
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("Courier");
    elementoClaveValor.setValor("Courier");
    fuentes.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("Helvetica");
    elementoClaveValor.setValor("Helvetica");
    fuentes.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("Times-Roman");
    elementoClaveValor.setValor("Times-Roman");
    fuentes.add(elementoClaveValor);
    
    editarConfiguracionPaginaForm.setFuentes(fuentes);
    
    List<ObjetoClaveValor> estilos = new ArrayList();
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("Normal");
    elementoClaveValor.setValor("Normal");
    estilos.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("Cursiva");
    elementoClaveValor.setValor("Cursiva");
    estilos.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("Negrita");
    elementoClaveValor.setValor("Negrita");
    estilos.add(elementoClaveValor);
    
    elementoClaveValor = new ObjetoClaveValor();
    elementoClaveValor.setClave("Negrita Cursiva");
    elementoClaveValor.setValor("Negrita Cursiva");
    estilos.add(elementoClaveValor);
    
    editarConfiguracionPaginaForm.setEstilos(estilos);
    
    List<ObjetoClaveValor> listaNumeros = new ArrayList();
    
    for (int i = 6; i <= 20; i++)
    {
      elementoClaveValor = new ObjetoClaveValor();
      elementoClaveValor.setClave(String.valueOf(i));
      elementoClaveValor.setValor(String.valueOf(i));
      listaNumeros.add(elementoClaveValor);
    }
    
    editarConfiguracionPaginaForm.setTamanosFuente(listaNumeros);
    

    frameworkService.close();
    

    saveMessages(request, messages);
    

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, false);
    }
    return mapping.findForward(forward);
  }
}
