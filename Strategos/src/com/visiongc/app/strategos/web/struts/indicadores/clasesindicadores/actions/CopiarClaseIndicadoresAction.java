package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.web.struts.indicadores.actions.CopiarIndicadorAction;
import com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.forms.EditarClaseIndicadoresForm;
import com.visiongc.app.strategos.web.struts.util.ObjetosCopia;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class CopiarClaseIndicadoresAction
  extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    String forward = mapping.getParameter();
    
    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    if (cancelar) {
      return getForwardBack(request, 1, true);
    }
    if ((ts != null) && (!ts.equals(""))) {
      forward = "finalizarCopiarClase";
    }
    ActionMessages messages = getMessages(request);
    
    EditarClaseIndicadoresForm editarClaseIndicadoresForm = (EditarClaseIndicadoresForm)form;
    
    String showPresentacion = request.getParameter("showPresentacion") != null ? request.getParameter("showPresentacion").toString() : "0";
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    ConfiguracionUsuario presentacion = new ConfiguracionUsuario();
    ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
    pk.setConfiguracionBase("Strategos.Wizar.Clase.Copiar.ShowPresentacion");
    pk.setObjeto("ShowPresentacion");
    pk.setUsuarioId(getUsuarioConectado(request).getUsuarioId());
    presentacion.setPk(pk);
    presentacion.setData(showPresentacion);
    frameworkService.saveConfiguracionUsuario(presentacion, getUsuarioConectado(request));
    frameworkService.close();
    
    int respuesta = 10000;
    StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
    respuesta = SalvarClase(editarClaseIndicadoresForm, strategosClasesIndicadoresService, request);
    if (respuesta == 10000)
    {
      strategosClasesIndicadoresService.unlockObject(request.getSession().getId(), editarClaseIndicadoresForm.getClaseId());
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.copia.ok"));
      forward = "finalizarCopiarClase";
      
      request.setAttribute("GestionarClasesIndicadoresAction.reloadId", editarClaseIndicadoresForm.getPadreId().toString());
    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }
    strategosClasesIndicadoresService.close();
    
    saveMessages(request, messages);
    
    request.getSession().setAttribute("GuardarClaseIndicadoresAction.ultimoTs", ts);
    if (forward.equals("finalizarCopiarClase")) {
      return getForwardBack(request, 1, true);
    }
    return getForwardBack(request, 1, true);
  }
  
  public int SalvarClase(EditarClaseIndicadoresForm editarClaseIndicadoresForm, StrategosClasesIndicadoresService strategosClasesIndicadoresService, HttpServletRequest request)
  {
    int respuesta = 10000;
    List<ObjetosCopia> clasesCopiadas = new ArrayList();
    
    Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
    ClaseIndicadores claseFuente = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, editarClaseIndicadoresForm.getClaseId());
    ClaseIndicadores claseDestino = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, editarClaseIndicadoresForm.getClaseIdDestino());
    
    ClaseIndicadores claseCopia = new ClaseIndicadores();
    
    claseCopia.setClaseId(new Long(0L));
    claseCopia.setPadreId(editarClaseIndicadoresForm.getClaseIdDestino());
    claseCopia.setNombre(editarClaseIndicadoresForm.getNuevoNombre());
    claseCopia.setOrganizacionId(claseDestino.getOrganizacionId());
    claseCopia.setDescripcion(claseFuente.getDescripcion());
    claseCopia.setEnlaceParcial(claseFuente.getEnlaceParcial());
    claseCopia.setVisible(claseFuente.getVisible());
    claseCopia.setTipo(TipoClaseIndicadores.getTipoClaseIndicadores());
    
    respuesta = strategosClasesIndicadoresService.saveClaseIndicadores(claseCopia, usuario);
    if (respuesta == 10003)
    {
      Map<String, Object> filtros = new HashMap();
      
      filtros.put("organizacionId", claseCopia.getOrganizacionId().toString());
      filtros.put("nombre", claseCopia.getNombre());
      filtros.put("padreId", claseCopia.getPadreId());
      List<ClaseIndicadores> clases = strategosClasesIndicadoresService.getClases(filtros);
      if (clases.size() > 0)
      {
        claseCopia = (ClaseIndicadores)clases.get(0);
        respuesta = 10000;
      }
    }
    clasesCopiadas.add(new ObjetosCopia(claseFuente.getClaseId(), claseCopia.getClaseId(), claseCopia.getOrganizacionId()));
    if (respuesta == 10000) {
      respuesta = CopiarClaseHijas(claseFuente, claseCopia, clasesCopiadas, strategosClasesIndicadoresService, editarClaseIndicadoresForm.getCopiarIndicadores(), editarClaseIndicadoresForm.getCopiarMediciones().booleanValue(), editarClaseIndicadoresForm.getCopiarInsumos().booleanValue(), claseCopia.getTipo(), request);
    }
    if ((respuesta == 10000) && (editarClaseIndicadoresForm.getCopiarIndicadores().booleanValue())) {
      respuesta = new CopiarIndicadorAction().CopiarIndicadores(clasesCopiadas, editarClaseIndicadoresForm.getCopiarMediciones().booleanValue(), editarClaseIndicadoresForm.getCopiarInsumos().booleanValue(), request);
    }
    return respuesta;
  }
  
  public int CopiarClaseHijas(ClaseIndicadores clasePadreOrigen, ClaseIndicadores clasePadreDestino, List<ObjetosCopia> clasesCopiadas, StrategosClasesIndicadoresService strategosClasesIndicadoresService, Boolean copiarIndicadores, boolean copiarMediciones, boolean copiarInsumos, Byte tipo, HttpServletRequest request)
  {
    int respuesta = 10000;
    
    Map<String, Object> filtros = new HashMap();
    
    filtros.put("padreId", clasePadreOrigen.getClaseId());
    filtros.put("tipo", tipo);
    
    List<ClaseIndicadores> clasesHijas = strategosClasesIndicadoresService.getClases(filtros);
    if ((clasesHijas != null) && (clasesHijas.size() > 0)) {
      for (Iterator<?> iter = clasesHijas.iterator(); iter.hasNext();)
      {
        ClaseIndicadores clase = (ClaseIndicadores)iter.next();
        ClaseIndicadores claseCopia = new ClaseIndicadores();
        
        claseCopia.setClaseId(new Long(0L));
        claseCopia.setPadreId(clasePadreDestino.getClaseId());
        claseCopia.setNombre(clase.getNombre());
        claseCopia.setOrganizacionId(clasePadreDestino.getOrganizacionId());
        claseCopia.setDescripcion(clase.getDescripcion());
        claseCopia.setEnlaceParcial(clase.getEnlaceParcial());
        claseCopia.setVisible(clase.getVisible());
        claseCopia.setTipo(TipoClaseIndicadores.getTipoClaseIndicadores());
        
        respuesta = strategosClasesIndicadoresService.saveClaseIndicadores(claseCopia, getUsuarioConectado(request));
        clasesCopiadas.add(new ObjetosCopia(clase.getClaseId(), claseCopia.getClaseId(), claseCopia.getOrganizacionId()));
        if (respuesta == 10000) {
          respuesta = CopiarClaseHijas(clase, claseCopia, clasesCopiadas, strategosClasesIndicadoresService, copiarIndicadores, copiarMediciones, copiarInsumos, tipo, request);
        }
        if (respuesta != 10000) {
          return respuesta;
        }
      }
    }
    return respuesta;
  }
}
