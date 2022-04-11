package com.visiongc.app.strategos.web.struts.organizaciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion;
import com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.indicadores.actions.CopiarIndicadorAction;
import com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.actions.CopiarClaseIndicadoresAction;
import com.visiongc.app.strategos.web.struts.iniciativas.actions.CopiarIniciativaAction;
import com.visiongc.app.strategos.web.struts.organizaciones.forms.EditarOrganizacionForm;
import com.visiongc.app.strategos.web.struts.planes.actions.CopiarPlanAction;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class CopiarOrganizacionAction
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
      forward = "finalizarCopiarOrganizacion";
    }
    EditarOrganizacionForm editarOrganizacionForm = (EditarOrganizacionForm)form;
    
    ActionMessages messages = getMessages(request);
    
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    if (!strategosIndicadoresService.checkLicencia(request))
    {
      strategosIndicadoresService.close();
      
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.limite.exedido"));
      saveMessages(request, messages);
      
      return getForwardBack(request, 1, false);
    }
    strategosIndicadoresService.close();
    
    String showPresentacion = request.getParameter("showPresentacion") != null ? request.getParameter("showPresentacion").toString() : "0";
    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    ConfiguracionUsuario presentacion = new ConfiguracionUsuario();
    ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
    pk.setConfiguracionBase("Strategos.Wizar.Organizacion.Copiar.ShowPresentacion");
    pk.setObjeto("ShowPresentacion");
    pk.setUsuarioId(getUsuarioConectado(request).getUsuarioId());
    presentacion.setPk(pk);
    presentacion.setData(showPresentacion);
    frameworkService.saveConfiguracionUsuario(presentacion, getUsuarioConectado(request));
    frameworkService.close();
    
    int respuesta = 10000;
    StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
    respuesta = CopiarOrganizacion(editarOrganizacionForm, strategosOrganizacionesService, request);
    if (respuesta == 10000)
    {
      strategosOrganizacionesService.unlockObject(request.getSession().getId(), editarOrganizacionForm.getOrganizacionId());
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.copia.ok"));
      forward = "finalizarCopiarOrganizacion";
      
      request.setAttribute("GestionarOrganizacionesAction.reloadId", editarOrganizacionForm.getPadreId());
    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }
    strategosOrganizacionesService.close();
    
    saveMessages(request, messages);
    
    request.getSession().setAttribute("GuardarOrganizacionAction.ultimoTs", ts);
    if (forward.equals("finalizarCopiarOrganizacion")) {
      return getForwardBack(request, 1, true);
    }
    return getForwardBack(request, 1, true);
  }
  
  private int CopiarOrganizacion(EditarOrganizacionForm editarOrganizacionForm, StrategosOrganizacionesService strategosOrganizacionesService, HttpServletRequest request)
  {
    int respuesta = 10000;
    
    OrganizacionStrategos organizacionStrategos = new OrganizacionStrategos();
    OrganizacionStrategos organizacionCopiaStrategos = new OrganizacionStrategos();
    
    organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, editarOrganizacionForm.getOrganizacionId());
    OrganizacionStrategos organizacionStrategosPadre = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, editarOrganizacionForm.getPadreId());
    
    organizacionCopiaStrategos = new OrganizacionStrategos();
    organizacionCopiaStrategos.setOrganizacionId(new Long(0L));
    organizacionCopiaStrategos.setPadreId(organizacionStrategosPadre.getOrganizacionId());
    organizacionCopiaStrategos.setMemos(new HashSet());
    organizacionCopiaStrategos.setNombre(editarOrganizacionForm.getNuevoNombre());
    organizacionCopiaStrategos.setDireccion(organizacionStrategos.getDireccion());
    organizacionCopiaStrategos.setFax(organizacionStrategos.getFax());
    organizacionCopiaStrategos.setMesCierre(organizacionStrategos.getMesCierre());
    organizacionCopiaStrategos.setRif(organizacionStrategos.getRif());
    organizacionCopiaStrategos.setTelefono(organizacionStrategos.getTelefono());
    organizacionCopiaStrategos.setPorcentajeZonaAmarillaMinMaxIndicadores(organizacionStrategos.getPorcentajeZonaAmarillaMinMaxIndicadores());
    organizacionCopiaStrategos.setPorcentajeZonaVerdeMetaIndicadores(organizacionStrategos.getPorcentajeZonaVerdeMetaIndicadores());
    organizacionCopiaStrategos.setPorcentajeZonaAmarillaMetaIndicadores(organizacionStrategos.getPorcentajeZonaAmarillaMetaIndicadores());
    organizacionCopiaStrategos.setPorcentajeZonaVerdeIniciativas(organizacionStrategos.getPorcentajeZonaVerdeIniciativas());
    organizacionCopiaStrategos.setPorcentajeZonaAmarillaIniciativas(organizacionStrategos.getPorcentajeZonaAmarillaIniciativas());
    organizacionCopiaStrategos.setVisible(organizacionStrategos.getVisible());
    organizacionCopiaStrategos.getMemos().clear();
    for (Iterator<MemoOrganizacion> i = organizacionStrategos.getMemos().iterator(); i.hasNext();)
    {
      MemoOrganizacion memoOrganizacion = new MemoOrganizacion();
      MemoOrganizacion oMemo = (MemoOrganizacion)i.next();
      Integer tipoMemo = oMemo.getPk().getTipo();
      if (tipoMemo.equals(new Integer(0))) {
        memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionCopiaStrategos.getOrganizacionId(), new Integer(0)));
      } else if (tipoMemo.equals(new Integer(1))) {
        memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionCopiaStrategos.getOrganizacionId(), new Integer(1)));
      } else if (tipoMemo.equals(new Integer(2))) {
        memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionCopiaStrategos.getOrganizacionId(), new Integer(2)));
      } else if (tipoMemo.equals(new Integer(3))) {
        memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionCopiaStrategos.getOrganizacionId(), new Integer(3)));
      } else if (tipoMemo.equals(new Integer(4))) {
        memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionCopiaStrategos.getOrganizacionId(), new Integer(4)));
      } else if (tipoMemo.equals(new Integer(5))) {
        memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionCopiaStrategos.getOrganizacionId(), new Integer(5)));
      } else if (tipoMemo.equals(new Integer(6))) {
        memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionCopiaStrategos.getOrganizacionId(), new Integer(6)));
      } else if (tipoMemo.equals(new Integer(7))) {
        memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionCopiaStrategos.getOrganizacionId(), new Integer(7)));
      } else if (tipoMemo.equals(new Integer(8))) {
        memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionCopiaStrategos.getOrganizacionId(), new Integer(8)));
      } else if (tipoMemo.equals(new Integer(9))) {
        memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionCopiaStrategos.getOrganizacionId(), new Integer(9)));
      }
      memoOrganizacion.setDescripcion(oMemo.getDescripcion());
      organizacionCopiaStrategos.getMemos().add(memoOrganizacion);
    }
    respuesta = strategosOrganizacionesService.saveOrganizacion(organizacionCopiaStrategos, getUsuarioConectado(request));
    if ((respuesta == 10000) && (editarOrganizacionForm.getCopiarIndicadores().booleanValue())) {
      respuesta = CopiarClase(organizacionStrategos, organizacionCopiaStrategos, request, editarOrganizacionForm.getCopiarIndicadores().booleanValue(), editarOrganizacionForm.getCopiarMediciones().booleanValue(), editarOrganizacionForm.getCopiarInsumos().booleanValue());
    }
    if ((respuesta == 10000) && (editarOrganizacionForm.getCopiarIniciativas().booleanValue())) {
      respuesta = CopiarIniciativas(organizacionStrategos, organizacionCopiaStrategos, request);
    }
    if ((respuesta == 10000) && (editarOrganizacionForm.getCopiarOrganizacionHija().booleanValue())) {
      respuesta = CopiarOrganizacionHijas(organizacionStrategos, organizacionCopiaStrategos, strategosOrganizacionesService, editarOrganizacionForm.getCopiarIndicadores().booleanValue(), editarOrganizacionForm.getCopiarMediciones().booleanValue(), editarOrganizacionForm.getCopiarPlanes().booleanValue(), editarOrganizacionForm.getCopiarInsumos().booleanValue(), editarOrganizacionForm.getCopiarIniciativas().booleanValue(), request);
    }
    if ((respuesta == 10000) && (editarOrganizacionForm.getCopiarPlanes().booleanValue())) {
      respuesta = CopiarPlanes(organizacionStrategos, organizacionCopiaStrategos, request);
    }
    return respuesta;
  }
  
  private int CopiarClase(OrganizacionStrategos organizacionOrigen, OrganizacionStrategos organizacionDestino, HttpServletRequest request, boolean copiarIndicadores, boolean copiarMediciones, boolean copiarInsumos)
  {
    int respuesta = 10000;
    
    StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
    ClaseIndicadores claseRoot = strategosClasesIndicadoresService.getClaseRaiz(organizacionOrigen.getOrganizacionId(), TipoClaseIndicadores.getTipoClaseIndicadores(), getUsuarioConectado(request));
    List<ObjetosCopia> clasesCopiadas = new ArrayList();
    
    ClaseIndicadores claseCopia = new ClaseIndicadores();
    claseCopia.setPadreId(null);
    
    claseCopia.setClaseId(new Long(0L));
    claseCopia.setNombre(claseRoot.getNombre());
    claseCopia.setOrganizacionId(organizacionDestino.getOrganizacionId());
    claseCopia.setDescripcion(claseRoot.getDescripcion());
    claseCopia.setEnlaceParcial(claseRoot.getEnlaceParcial());
    claseCopia.setVisible(claseRoot.getVisible());
    claseCopia.setTipo(TipoClaseIndicadores.getTipoClaseIndicadores());
    
    respuesta = strategosClasesIndicadoresService.saveClaseIndicadores(claseCopia, getUsuarioConectado(request));
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
    clasesCopiadas.add(new ObjetosCopia(claseRoot.getClaseId(), claseCopia.getClaseId(), claseCopia.getOrganizacionId()));
    if (respuesta == 10000) {
      respuesta = new CopiarClaseIndicadoresAction().CopiarClaseHijas(claseRoot, claseCopia, clasesCopiadas, strategosClasesIndicadoresService, Boolean.valueOf(copiarIndicadores), copiarMediciones, copiarInsumos, claseCopia.getTipo(), request);
    }
    if ((respuesta == 10000) && (copiarIndicadores)) {
      respuesta = new CopiarIndicadorAction().CopiarIndicadores(clasesCopiadas, copiarMediciones, copiarInsumos, request);
    }
    strategosClasesIndicadoresService.close();
    
    return respuesta;
  }
  
  public int CopiarOrganizacionHijas(OrganizacionStrategos organizacionOrigen, OrganizacionStrategos organizacionDestino, StrategosOrganizacionesService strategosOrganizacionesService, boolean copiarIndicadores, boolean copiarMediciones, boolean copiarPlanes, boolean copiarInsumos, boolean copiarIniciativas, HttpServletRequest request)
  {
    int respuesta = 10000;
    
    List<?> organizacionesHijas = strategosOrganizacionesService.getOrganizacionHijas(organizacionOrigen.getOrganizacionId(), true);
    if ((organizacionesHijas != null) && (organizacionesHijas.size() > 0)) {
      for (Iterator<?> iter = organizacionesHijas.iterator(); iter.hasNext();)
      {
        OrganizacionStrategos orgOrig = (OrganizacionStrategos)iter.next();
        OrganizacionStrategos orgCopia = new OrganizacionStrategos();
        
        orgCopia.setOrganizacionId(new Long(0L));
        orgCopia.setPadreId(organizacionDestino.getOrganizacionId());
        orgCopia.setMemos(new HashSet());
        orgCopia.setNombre(orgOrig.getNombre());
        orgCopia.setDireccion(orgOrig.getDireccion());
        orgCopia.setFax(orgOrig.getFax());
        orgCopia.setMesCierre(orgOrig.getMesCierre());
        orgCopia.setRif(orgOrig.getRif());
        orgCopia.setTelefono(orgOrig.getTelefono());
        orgCopia.setPorcentajeZonaAmarillaMinMaxIndicadores(orgOrig.getPorcentajeZonaAmarillaMinMaxIndicadores());
        orgCopia.setPorcentajeZonaVerdeMetaIndicadores(orgOrig.getPorcentajeZonaVerdeMetaIndicadores());
        orgCopia.setPorcentajeZonaAmarillaMetaIndicadores(orgOrig.getPorcentajeZonaAmarillaMetaIndicadores());
        orgCopia.setPorcentajeZonaVerdeIniciativas(orgOrig.getPorcentajeZonaVerdeIniciativas());
        orgCopia.setPorcentajeZonaAmarillaIniciativas(orgOrig.getPorcentajeZonaAmarillaIniciativas());
        orgCopia.setVisible(orgOrig.getVisible());
        orgCopia.getMemos().clear();
        for (Iterator<MemoOrganizacion> i = orgOrig.getMemos().iterator(); i.hasNext();)
        {
          MemoOrganizacion memoOrganizacion = new MemoOrganizacion();
          MemoOrganizacion oMemo = (MemoOrganizacion)i.next();
          Integer tipoMemo = oMemo.getPk().getTipo();
          if (tipoMemo.equals(new Integer(0))) {
            memoOrganizacion.setPk(new MemoOrganizacionPK(orgCopia.getOrganizacionId(), new Integer(0)));
          } else if (tipoMemo.equals(new Integer(1))) {
            memoOrganizacion.setPk(new MemoOrganizacionPK(orgCopia.getOrganizacionId(), new Integer(1)));
          } else if (tipoMemo.equals(new Integer(2))) {
            memoOrganizacion.setPk(new MemoOrganizacionPK(orgCopia.getOrganizacionId(), new Integer(2)));
          } else if (tipoMemo.equals(new Integer(3))) {
            memoOrganizacion.setPk(new MemoOrganizacionPK(orgCopia.getOrganizacionId(), new Integer(3)));
          } else if (tipoMemo.equals(new Integer(4))) {
            memoOrganizacion.setPk(new MemoOrganizacionPK(orgCopia.getOrganizacionId(), new Integer(4)));
          } else if (tipoMemo.equals(new Integer(5))) {
            memoOrganizacion.setPk(new MemoOrganizacionPK(orgCopia.getOrganizacionId(), new Integer(5)));
          } else if (tipoMemo.equals(new Integer(6))) {
            memoOrganizacion.setPk(new MemoOrganizacionPK(orgCopia.getOrganizacionId(), new Integer(6)));
          } else if (tipoMemo.equals(new Integer(7))) {
            memoOrganizacion.setPk(new MemoOrganizacionPK(orgCopia.getOrganizacionId(), new Integer(7)));
          } else if (tipoMemo.equals(new Integer(8))) {
            memoOrganizacion.setPk(new MemoOrganizacionPK(orgCopia.getOrganizacionId(), new Integer(8)));
          } else if (tipoMemo.equals(new Integer(9))) {
            memoOrganizacion.setPk(new MemoOrganizacionPK(orgCopia.getOrganizacionId(), new Integer(9)));
          }
          memoOrganizacion.setDescripcion(oMemo.getDescripcion());
          orgCopia.getMemos().add(memoOrganizacion);
        }
        respuesta = strategosOrganizacionesService.saveOrganizacion(orgCopia, getUsuarioConectado(request));
        if ((respuesta == 10000) && (copiarIndicadores)) {
          respuesta = CopiarClase(orgOrig, orgCopia, request, copiarIndicadores, copiarMediciones, copiarInsumos);
        }
        if ((respuesta == 10000) && (copiarIniciativas)) {
          respuesta = CopiarIniciativas(orgOrig, orgCopia, request);
        }
        if (respuesta == 10000) {
          respuesta = CopiarOrganizacionHijas(orgOrig, orgCopia, strategosOrganizacionesService, copiarIndicadores, copiarMediciones, copiarPlanes, copiarInsumos, copiarIniciativas, request);
        }
        if ((respuesta == 10000) && (copiarPlanes)) {
          respuesta = CopiarPlanes(orgOrig, orgCopia, request);
        }
        if (respuesta != 10000) {
          return respuesta;
        }
      }
    }
    return respuesta;
  }
  
  public int CopiarPlanes(OrganizacionStrategos organizacionOrigen, OrganizacionStrategos organizacionDestino, HttpServletRequest request)
  {
    int respuesta = new CopiarPlanAction().Copiar(organizacionOrigen.getOrganizacionId(), organizacionDestino.getOrganizacionId(), request);
    return respuesta;
  }
  
  public int CopiarIniciativas(OrganizacionStrategos organizacionOrigen, OrganizacionStrategos organizacionDestino, HttpServletRequest request)
  {
    int respuesta = new CopiarIniciativaAction().Copiar(organizacionOrigen.getOrganizacionId(), organizacionDestino.getOrganizacionId(), request);
    return respuesta;
  }
}
