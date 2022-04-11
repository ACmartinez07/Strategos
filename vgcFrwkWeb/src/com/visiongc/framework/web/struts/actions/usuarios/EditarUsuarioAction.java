package com.visiongc.framework.web.struts.actions.usuarios;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.arboles.ComparatorNodoArbol;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.UsuarioGrupo;
import com.visiongc.framework.model.UsuarioGrupoPK;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.forms.usuarios.EditarUsuarioForm;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class EditarUsuarioAction
  extends VgcAction
{
  public EditarUsuarioAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    

    String forward = mapping.getParameter();
    

    EditarUsuarioForm editarUsuarioForm = (EditarUsuarioForm)form;
    

    ActionMessages messages = getMessages(request);
    
    String usuarioId = request.getParameter("usuarioId");
    
    editarUsuarioForm.setCopiar(mapping.getPath().indexOf("copiar") > -1);
    
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Configuracion.Login");
    
    if (configuracion != null)
    {

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
      doc.getDocumentElement().normalize();
      NodeList nList = doc.getElementsByTagName("Configuracion");
      Element eElement = (Element)nList.item(0);
      editarUsuarioForm.setReutilizacionContrasena(Integer.valueOf(Integer.parseInt(getTagValue("reutilizacionContrasena", eElement))));
      
      editarUsuarioForm.setRestriccionUsoDiaLunes(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaLunes", eElement))));
      editarUsuarioForm.setRestriccionUsoDiaMartes(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaMartes", eElement))));
      editarUsuarioForm.setRestriccionUsoDiaMiercoles(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaMiercoles", eElement))));
      editarUsuarioForm.setRestriccionUsoDiaJueves(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaJueves", eElement))));
      editarUsuarioForm.setRestriccionUsoDiaViernes(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaViernes", eElement))));
      editarUsuarioForm.setRestriccionUsoDiaSabado(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaSabado", eElement))));
      editarUsuarioForm.setRestriccionUsoDiaDomingo(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaDomingo", eElement))));
      editarUsuarioForm.setRestriccionUsoHoraDesde(getTagValue("restriccionUsoHoraDesde", eElement));
      editarUsuarioForm.setRestriccionUsoHoraHasta(getTagValue("restriccionUsoHoraHasta", eElement));
    }
    else
    {
      editarUsuarioForm.setRestriccionUsoDiaLunes(Boolean.valueOf(true));
      editarUsuarioForm.setRestriccionUsoDiaMartes(Boolean.valueOf(true));
      editarUsuarioForm.setRestriccionUsoDiaMiercoles(Boolean.valueOf(true));
      editarUsuarioForm.setRestriccionUsoDiaJueves(Boolean.valueOf(true));
      editarUsuarioForm.setRestriccionUsoDiaViernes(Boolean.valueOf(true));
      editarUsuarioForm.setRestriccionUsoDiaSabado(Boolean.valueOf(true));
      editarUsuarioForm.setRestriccionUsoDiaDomingo(Boolean.valueOf(true));
      editarUsuarioForm.setRestriccionUsoHoraDesde("");
      editarUsuarioForm.setRestriccionUsoHoraHasta("");
    }
    
    boolean bloqueado = false;
    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
    
    if ((usuarioId != null) && (!usuarioId.equals("")) && (!usuarioId.equals("0")))
    {

      if (!editarUsuarioForm.isCopiar())
      {

        bloqueado = !usuariosService.lockForUpdate(request.getSession().getId(), usuarioId, null);
      }
      
      editarUsuarioForm.setBloqueado(new Boolean(bloqueado));
      

      Usuario usuario = (Usuario)usuariosService.load(Usuario.class, new Long(usuarioId));
      
      if (usuario != null)
      {
        if (bloqueado)
        {
          String[] nombre = new String[1];
          
          nombre[0] = usuario.getFullName();
          
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.editarusuario.locked", nombre));
        }
        
        Set grupos = usuario.getUsuarioGrupos();
        String listaGrupos = "";
 
       
        for (Iterator i = grupos.iterator(); i.hasNext();)
        {
          UsuarioGrupo ug = (UsuarioGrupo)i.next();
          listaGrupos = listaGrupos + ug.getPk().getOrganizacionId() + ":" + ug.getPk().getGrupoId()+ ";";
        
        }
        
        if (!editarUsuarioForm.isCopiar())
        {
          editarUsuarioForm.setUsuarioId(usuario.getUsuarioId());
          editarUsuarioForm.setUId(usuario.getUId());
        }
        else
        {
          editarUsuarioForm.setUsuarioId(new Long(0L));
          editarUsuarioForm.setUId(getResources(request).getMessage("action.framework.editarusuario.copyof") + " " + usuario.getUId());
        }
        
        editarUsuarioForm.setFullName(usuario.getFullName());
        editarUsuarioForm.setPwd(usuario.getPwdPlain());
        editarUsuarioForm.setPwdConfirm(usuario.getPwdPlain());
        editarUsuarioForm.setIsAdmin(usuario.getIsAdmin());  
        editarUsuarioForm.setBloqueado(usuario.getBloqueado());
        editarUsuarioForm.setEstatus(usuario.getEstatus());
        editarUsuarioForm.setDeshabilitado(usuario.getDeshabilitado());
        editarUsuarioForm.setForzarCambiarpwd(usuario.getForzarCambiarpwd());
        editarUsuarioForm.setGrupos(listaGrupos);
        

        ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(usuario.getUsuarioId(), "com.visiongc.framework.web.configuracion.Usuarios", "RestriccionUso");
        
        if (configuracionUsuario != null)
        {

          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          DocumentBuilder db = dbf.newDocumentBuilder();
          Document doc = db.parse(new ByteArrayInputStream(configuracionUsuario.getData().getBytes("UTF-8")));
          doc.getDocumentElement().normalize();
          NodeList nList = doc.getElementsByTagName("Configuracion");
          Element eElement = (Element)nList.item(0);
          
          editarUsuarioForm.setRestriccionUsoDiaLunes(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaLunes", eElement))));
          editarUsuarioForm.setRestriccionUsoDiaMartes(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaMartes", eElement))));
          editarUsuarioForm.setRestriccionUsoDiaMiercoles(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaMiercoles", eElement))));
          editarUsuarioForm.setRestriccionUsoDiaJueves(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaJueves", eElement))));
          editarUsuarioForm.setRestriccionUsoDiaViernes(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaViernes", eElement))));
          editarUsuarioForm.setRestriccionUsoDiaSabado(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaSabado", eElement))));
          editarUsuarioForm.setRestriccionUsoDiaDomingo(Boolean.valueOf(Boolean.parseBoolean(getTagValue("restriccionUsoDiaDomingo", eElement))));
          editarUsuarioForm.setRestriccionUsoHoraDesde(getTagValue("restriccionUsoHoraDesde", eElement));
          editarUsuarioForm.setRestriccionUsoHoraHasta(getTagValue("restriccionUsoHoraHasta", eElement));
        }
        

      }
      else
      {

        usuariosService.unlockObject(request.getSession().getId(), new Long(usuarioId));
        
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.editarusuario.notfound"));
        
        forward = "noencontrado";
      }
      
    }
    else
    {
      editarUsuarioForm.clear();
      if (!usuariosService.checkLicencia(request))
      {
        usuariosService.close();
        
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.framework.editarusuario.limite.exedido"));
        saveMessages(request, messages);
        
        return getForwardBack(request, 1, false);
      }
    }
    
    String organizacionesId = obtenerGruposId(request, frameworkService);
    
    editarUsuarioForm.setOrganizacionesId(organizacionesId);
    
    publishListaGrupos(request, frameworkService);
    publishArbolOrganizaciones(request, frameworkService);
    frameworkService.close();
    usuariosService.close();
    
    saveMessages(request, messages);
    

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, false);
    }
    return mapping.findForward(forward);
  }
  
  private String obtenerGruposId(HttpServletRequest request, FrameworkService frameworkService){
	 
	  StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
	  
	  String organizacionesId= "";
	
	  PaginaLista pagina =strategosOrganizacionesService.getOrganizaciones(0, 30, null, null, false, null);
	  
	  List<Organizacion> organizaciones = pagina.getLista();
      
      
      if (organizaciones.size() > 1)
      {
            
        for (Iterator i = organizaciones.iterator(); i.hasNext();){
        	OrganizacionStrategos org = (OrganizacionStrategos)i.next();
        	organizacionesId += org.getOrganizacionId() + ","; 
        }
        
      }
      
      return organizacionesId;
  }
  
  private static String getTagValue(String sTag, Element eElement)
  {
    NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
    Node nValue = nlList.item(0);
    
    if (nValue != null) {
      return nValue.getNodeValue();
    }
    return "";
  }
  

  private void publishListaGrupos(HttpServletRequest request, FrameworkService frameworkService)
  {
    PaginaLista paginaGrupos = new PaginaLista();
    
    paginaGrupos.setNroPagina(1);
    paginaGrupos.setLista(frameworkService.getGrupos());
    paginaGrupos.setTotal(paginaGrupos.getLista().size());
    request.getSession().setAttribute("editarUsuarioPaginaGrupos", paginaGrupos);
  }
  

  private void publishArbolOrganizaciones(HttpServletRequest request, FrameworkService frameworkService)
  {
    try
    {
      Organizacion organizacionRoot = null;
      
      List organizaciones = frameworkService.getOrganizacionesRoot(true);
      
      if (organizaciones.size() > 1)
      {
        organizacionRoot = new Organizacion();
        Set hijos = new TreeSet(new ComparatorNodoArbol("nombre"));
        
        for (Iterator i = organizaciones.iterator(); i.hasNext();)
          hijos.add(i.next());
        organizacionRoot.setOrganizacionId(new Long(0L));
        organizacionRoot.setNombre(getResources(request).getMessage("action.framework.editarusuario.orgrootdummy"));
        organizacionRoot.setPadreId(null);
        organizacionRoot.setHijos(hijos);
      }
      else {
        organizacionRoot = (Organizacion)organizaciones.get(0);
      }
      request.getSession().setAttribute("editarUsuarioOrganizacionRoot", organizacionRoot);
      
      request.getSession().setAttribute("editarUsuarioFullPathOrganizaciones", getFullPathOrganizaciones(organizacionRoot));
      

      TreeviewWeb.publishTree("editarUsuarioArbolOrganizaciones", organizacionRoot.getOrganizacionId().toString(), "session", request, true);
      
      abrirArbolOrganizaciones(organizacionRoot.getHijos(), "editarUsuarioArbolOrganizaciones", "session", request);

    }
    catch (Throwable t)
    {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
  }
  
  private void abrirArbolOrganizaciones(Set conj, String nameObject, String scope, HttpServletRequest request)
  {
    try
    {
      for (Iterator i = conj.iterator(); i.hasNext();)
      {
        Organizacion hijo = (Organizacion)i.next();
        
        if (hijo.getHijos().size() > 0)
        {
          TreeviewWeb.publishTree(nameObject, hijo.getOrganizacionId().toString(), scope, request);
          abrirArbolOrganizaciones(hijo.getHijos(), nameObject, scope, request);
        }
      }
    }
    catch (Throwable t)
    {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
  }
  
  private String getFullPathOrganizaciones(Organizacion root)
  {
    String resultado = root.getOrganizacionId() + ":";
    
    if (root.getHijos().size() > 0)
    {
      for (Iterator i = root.getHijos().iterator(); i.hasNext();) {
        resultado = resultado + getFullPathOrganizacionesAux((Organizacion)i.next());
      }
    }
    return resultado;
  }
  
  private String getFullPathOrganizacionesAux(Organizacion hija)
  {
    String resultado = hija.getOrganizacionId() + ":";
    
    if (hija.getHijos().size() > 0)
    {
      for (Iterator i = hija.getHijos().iterator(); i.hasNext();) {
        resultado = resultado + getFullPathOrganizacionesAux((Organizacion)i.next());
      }
    }
    return resultado;
  }
}
