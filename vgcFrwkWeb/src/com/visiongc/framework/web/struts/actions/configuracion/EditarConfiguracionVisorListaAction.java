package com.visiongc.framework.web.struts.actions.configuracion;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.commons.util.xmldata.XmlNodo;
import com.visiongc.commons.util.xmldata.XmlUtil;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.configuracion.VgcConfiguracionPorUsuario;
import com.visiongc.framework.configuracion.VgcObjetoConfigurable;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.configuracion.VgcConfiguracionesBaseWeb;
import com.visiongc.framework.web.struts.forms.configuracion.EditarConfiguracionVisorListaForm;
import com.visiongc.framework.web.struts.taglib.interfaz.util.ColumnaVisorListaInfo;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;










public class EditarConfiguracionVisorListaAction
  extends VgcAction
{
  public EditarConfiguracionVisorListaAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    String forward = mapping.getParameter();
    

    EditarConfiguracionVisorListaForm editarConfiguracionVisorListaForm = (EditarConfiguracionVisorListaForm)form;
    

    ActionMessages messages = getMessages(request);
    




    String nombreConfiguracionBase = request.getParameter("nombreConfiguracionBase");
    String nombreVisorLista = request.getParameter("nombreVisorLista");
    Boolean esPropio = Boolean.valueOf(request.getParameter("esPropio") != null ? Boolean.parseBoolean(request.getParameter("esPropio")) : false);
    String altoPagina = request.getParameter("alto") != null ? Integer.valueOf(Integer.parseInt(request.getParameter("alto")) - 20).toString() : null;
    String anchoPagina = request.getParameter("ancho") != null ? Integer.valueOf(Integer.parseInt(request.getParameter("ancho")) - 40).toString() : null;
    
    editarConfiguracionVisorListaForm.setNombreConfiguracionBase(nombreConfiguracionBase);
    editarConfiguracionVisorListaForm.setNombreVisorLista("visorLista." + nombreVisorLista);
    editarConfiguracionVisorListaForm.setTituloVisorLista(request.getParameter("tituloVisorLista"));
    editarConfiguracionVisorListaForm.setAlto(altoPagina);
    editarConfiguracionVisorListaForm.setAncho(anchoPagina);
    editarConfiguracionVisorListaForm.setEsPropio(esPropio);
    

    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(getUsuarioConectado(request).getUsuarioId(), nombreConfiguracionBase, "visorLista." + nombreVisorLista);
    
    XmlNodo configuracion = null;
    XmlNodo configuracionBase = null;
    VgcObjetoConfigurable visorLista = null;
    Configuracion configuracionGeneral = null;
    
    if (configuracionUsuario == null)
    {
      configuracionGeneral = frameworkService.getConfiguracion("visorLista." + nombreVisorLista);
      if (configuracionGeneral != null) {
        configuracionBase = XmlUtil.readXml(configuracionGeneral.getValor());
      }
    }
    if (!esPropio.booleanValue())
    {
      VgcConfiguracionesBaseWeb configuracionesBaseWeb = (VgcConfiguracionesBaseWeb)VgcConfiguracionPorUsuario.getInstance(nombreConfiguracionBase).getConfiguracionesBase();
      visorLista = configuracionesBaseWeb.getObjetoConfigurable(nombreConfiguracionBase + ".objetoConfigurable.visorLista." + nombreVisorLista, request);
    }
    
    if ((visorLista != null) && (configuracionGeneral == null)) {
      configuracionBase = visorLista.getConfiguracionBase();
    }
    if ((!esPropio.booleanValue()) && (configuracionBase == null)) {
      throw new ChainedRuntimeException("No existe la configuracion base del objeto 'visorLista." + nombreVisorLista + "' en la configuracionBase '" + nombreConfiguracionBase + "'");
    }
    if ((configuracionUsuario != null) && (!esPropio.booleanValue()))
    {
      configuracion = XmlUtil.readXml(configuracionUsuario.getData());
      if ((visorLista != null) && (!visorLista.configuracionesSonIguales(configuracionBase, configuracion))) {
        configuracion = configuracionBase;
      }
    } else if (!esPropio.booleanValue()) {
      configuracion = configuracionBase;
    }
    PaginaLista paginaColumnas = new PaginaLista();
    paginaColumnas.setLista(new ArrayList());
    if (!esPropio.booleanValue())
    {
      ListaMap columnas = ((XmlNodo)configuracion.getElemLista("columnas")).getLista();
      for (Iterator<?> i = columnas.iterator(); i.hasNext();)
      {
        XmlNodo confColumna = (XmlNodo)i.next();
        
        ColumnaVisorListaInfo columna = new ColumnaVisorListaInfo();
        columna.setOrden(confColumna.getValorAtributo("orden"));
        columna.setNombre(confColumna.getId());
        columna.setTitulo(confColumna.getValorAtributo("titulo"));
        
        String[] ancho = confColumna.getValorAtributo("ancho").split("px");
        
        columna.setAncho(ancho[0]);
        columna.setVisible(confColumna.getValorAtributo("visible"));
        




        paginaColumnas.getLista().add(columna);
      }
    }
    else
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(TextEncoder.deleteCharInvalid(configuracionUsuario.getData()))));
      NodeList lista = doc.getElementsByTagName("columnas");
      
      Integer orden = Integer.valueOf(0);
      for (int i = 0; i < lista.getLength(); i++)
      {
        Node node = lista.item(i);
        Element elemento = (Element)node;
        NodeList nodeLista = null;
        Node valor = null;
        
        ColumnaVisorListaInfo columna = new ColumnaVisorListaInfo();
        
        orden = Integer.valueOf(orden.intValue() + 1);
        String atributo = "";
        if (elemento.getElementsByTagName("titulo").getLength() > 0)
        {
          nodeLista = elemento.getElementsByTagName("titulo").item(0).getChildNodes();
          valor = nodeLista.item(0);
          if (valor != null) atributo = valor.getNodeValue(); else atributo = "nodefinida";
        }
        columna.setNombre(atributo);
        columna.setTitulo(atributo);
        
        atributo = "";
        if (elemento.getElementsByTagName("orden").getLength() > 0)
        {
          nodeLista = elemento.getElementsByTagName("orden").item(0).getChildNodes();
          valor = nodeLista.item(0);
          if (valor != null) atributo = valor.getNodeValue(); else atributo = orden.toString();
        }
        columna.setOrden(atributo);
        
        atributo = "";
        if (elemento.getElementsByTagName("visible").getLength() > 0)
        {
          nodeLista = elemento.getElementsByTagName("visible").item(0).getChildNodes();
          valor = nodeLista.item(0);
          if (valor != null) atributo = valor.getNodeValue(); else atributo = "true";
        }
        columna.setVisible(atributo);
        
        atributo = "";
        if (elemento.getElementsByTagName("ancho").getLength() > 0)
        {
          nodeLista = elemento.getElementsByTagName("ancho").item(0).getChildNodes();
          valor = nodeLista.item(0);
          if (valor != null) atributo = valor.getNodeValue(); else atributo = "150";
        }
        columna.setAncho(atributo);
        
        paginaColumnas.getLista().add(columna);
      }
    }
    
    paginaColumnas.setNroPagina(0);
    paginaColumnas.setTotal(paginaColumnas.getLista().size());
    
    int alto = (paginaColumnas.getLista().size() + 1) * 30;
    
    if (alto > 320) {
      alto = 320;
    }
    int altoContenedor = alto + 80;
    int altoVisor = alto;
    if ((nombreVisorLista.equals("visorIndicadores")) || (nombreVisorLista.equals("visorIndicadoresPlan")) || (nombreVisorLista.equals("visorActividades")) || (nombreVisorLista.equals("visorIniciativas")) || (nombreVisorLista.equals("visorPortafolios")))
    {
      altoContenedor = alto + 160;
      altoVisor = alto + 100;
    }
    
    request.setAttribute("altoContenedor", Integer.toString(altoContenedor) + "px");
    request.setAttribute("altoVisor", Integer.toString(altoVisor) + "px");
    request.setAttribute("paginaColumnas", paginaColumnas);
    

    frameworkService.close();
    

    saveMessages(request, messages);
    

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, false);
    }
    return mapping.findForward(forward);
  }
}
