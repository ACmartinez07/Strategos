package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion.ObjetivoKey;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacionPK;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.EditarExplicacionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.util.WebUtil;
import com.visiongc.framework.model.Usuario;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

public class GuardarExplicacionAction
  extends VgcAction
{
  private static final String ACTION_KEY = "GuardarExplicacionAction";
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    String forward = mapping.getParameter();
    
    EditarExplicacionForm editarExplicacionForm = (EditarExplicacionForm)form;
    
    ActionMessages messages = getMessages(request);
    
    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarExplicacionAction.ultimoTs");
    if ((ts == null) || (ts.equals(""))) {
      cancelar = true;
    } else if ((ultimoTs != null) && (ultimoTs.equals(ts))) {
      cancelar = true;
    }
    StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
    if (cancelar)
    {
      strategosExplicacionesService.unlockObject(request.getSession().getId(), editarExplicacionForm.getExplicacionId());
      
      strategosExplicacionesService.close();
      
      return getForwardBack(request, 1, true);
    }
    Explicacion explicacion = new Explicacion();
    boolean nuevo = false;
    int respuesta = 10000;
    if ((editarExplicacionForm.getExplicacionId() != null) && (!editarExplicacionForm.getExplicacionId().equals(Long.valueOf("0"))))
    {
      explicacion = (Explicacion)strategosExplicacionesService.load(Explicacion.class, editarExplicacionForm.getExplicacionId());
    }
    else
    {
      nuevo = true;
      explicacion = new Explicacion();
      explicacion.setExplicacionId(new Long(0L));
      explicacion.setMemosExplicacion(new HashSet());
      explicacion.setAdjuntosExplicacion(new HashSet());
      explicacion.setCreado(new Date());
      explicacion.setCreadoId(((Usuario)request.getSession().getAttribute("usuario")).getUsuarioId());
      explicacion.setObjetoKey(editarExplicacionForm.getObjetoKey());
      explicacion.setObjetoId(editarExplicacionForm.getObjetoId());
      explicacion.setTipo(editarExplicacionForm.getTipo());
      if (((String)request.getSession().getAttribute("objetoKey")).equals("Indicador"))
      {
        explicacion.setObjetoKey(Explicacion.ObjetivoKey.getKeyIndicador());
        explicacion.setObjetoId(((Indicador)request.getSession().getAttribute("indicador")).getIndicadorId());
      }
      if (((String)request.getSession().getAttribute("objetoKey")).equals("Celda"))
      {
        explicacion.setObjetoKey(Explicacion.ObjetivoKey.getKeyCelda());
        explicacion.setObjetoId(((Celda)request.getSession().getAttribute("celda")).getCeldaId());
      }
      if (((String)request.getSession().getAttribute("objetoKey")).equals("Iniciativa"))
      {
        explicacion.setObjetoKey(Explicacion.ObjetivoKey.getKeyIniciativa());
        explicacion.setObjetoId(((Iniciativa)request.getSession().getAttribute("iniciativa")).getIniciativaId());
      }
      if (((String)request.getSession().getAttribute("objetoKey")).equals("Organizacion"))
      {
        explicacion.setObjetoKey(Explicacion.ObjetivoKey.getKeyOrganizacion());
        explicacion.setObjetoId(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
      }
      if (((String)request.getSession().getAttribute("objetoKey")).equals("Instrumento"))
      {
        explicacion.setObjetoKey(Explicacion.ObjetivoKey.getKeyInstrumento());
        explicacion.setObjetoId(((Instrumentos)request.getSession().getAttribute("instrumento")).getInstrumentoId());
      }
    }
    explicacion.setTitulo(editarExplicacionForm.getTitulo());
    explicacion.setPublico(WebUtil.getValorInputCheck(request, "publico"));
    explicacion.setFecha(FechaUtil.convertirStringToDate(editarExplicacionForm.getFecha(), VgcResourceManager.getResourceApp("formato.fecha.corta")));
    if (editarExplicacionForm.getFechaCompromiso() != null) {
      explicacion.setFechaCompromiso(FechaUtil.convertirStringToDate(editarExplicacionForm.getFechaCompromiso(), VgcResourceManager.getResourceApp("formato.fecha.corta")));
    }
    if (editarExplicacionForm.getFechaReal() != null) {
      explicacion.setFechaReal(FechaUtil.convertirStringToDate(editarExplicacionForm.getFechaReal(), VgcResourceManager.getResourceApp("formato.fecha.corta")));
    }
   
    explicacion.getAdjuntosExplicacion().clear();
    explicacion.getAdjuntosExplicacion().addAll(editarExplicacionForm.getAdjuntosExplicacion());
    
    
    explicacion.getMemosExplicacion().clear();
    if (editarExplicacionForm.getMemoDescripcion() != null)
    {
      MemoExplicacion memoExplicacion = new MemoExplicacion();
      memoExplicacion.setPk(new MemoExplicacionPK(explicacion.getExplicacionId(), new Byte((byte)0)));
      memoExplicacion.setMemo(editarExplicacionForm.getMemoDescripcion());
      explicacion.getMemosExplicacion().add(memoExplicacion);
    }
    if (editarExplicacionForm.getMemoCausas() != null)
    {
      MemoExplicacion memoExplicacion = new MemoExplicacion();
      memoExplicacion.setPk(new MemoExplicacionPK(explicacion.getExplicacionId(), new Byte((byte)1)));
      memoExplicacion.setMemo(editarExplicacionForm.getMemoCausas());
      explicacion.getMemosExplicacion().add(memoExplicacion);
    }
    if (editarExplicacionForm.getMemoCorrectivos() != null)
    {
      MemoExplicacion memoExplicacion = new MemoExplicacion();
      memoExplicacion.setPk(new MemoExplicacionPK(explicacion.getExplicacionId(), new Byte((byte)2)));
      memoExplicacion.setMemo(editarExplicacionForm.getMemoCorrectivos());
      explicacion.getMemosExplicacion().add(memoExplicacion);
    }
    if (editarExplicacionForm.getMemoImpactos() != null)
    {
      MemoExplicacion memoExplicacion = new MemoExplicacion();
      memoExplicacion.setPk(new MemoExplicacionPK(explicacion.getExplicacionId(), new Byte((byte)3)));
      memoExplicacion.setMemo(editarExplicacionForm.getMemoImpactos());
      explicacion.getMemosExplicacion().add(memoExplicacion);
    }
    if (editarExplicacionForm.getMemoPerspectivas() != null)
    {
      MemoExplicacion memoExplicacion = new MemoExplicacion();
      memoExplicacion.setPk(new MemoExplicacionPK(explicacion.getExplicacionId(), new Byte((byte)4)));
      memoExplicacion.setMemo(editarExplicacionForm.getMemoPerspectivas());
      explicacion.getMemosExplicacion().add(memoExplicacion);
    }
    if (editarExplicacionForm.getMemoUrl() != null)
    {
      MemoExplicacion memoExplicacion = new MemoExplicacion();
      memoExplicacion.setPk(new MemoExplicacionPK(explicacion.getExplicacionId(), new Byte((byte)5)));
      memoExplicacion.setMemo(editarExplicacionForm.getMemoUrl());
      explicacion.getMemosExplicacion().add(memoExplicacion);
    }
    if (editarExplicacionForm.getLogro1() != null)
    {
      MemoExplicacion memoExplicacion = new MemoExplicacion();
      memoExplicacion.setPk(new MemoExplicacionPK(explicacion.getExplicacionId(), new Byte((byte)6)));
      memoExplicacion.setMemo(editarExplicacionForm.getLogro1());
      explicacion.getMemosExplicacion().add(memoExplicacion);
    }
    if (editarExplicacionForm.getLogro2() != null)
    {
      MemoExplicacion memoExplicacion = new MemoExplicacion();
      memoExplicacion.setPk(new MemoExplicacionPK(explicacion.getExplicacionId(), new Byte((byte)7)));
      memoExplicacion.setMemo(editarExplicacionForm.getLogro2());
      explicacion.getMemosExplicacion().add(memoExplicacion);
    }
    if (editarExplicacionForm.getLogro3() != null)
    {
      MemoExplicacion memoExplicacion = new MemoExplicacion();
      memoExplicacion.setPk(new MemoExplicacionPK(explicacion.getExplicacionId(), new Byte((byte)8)));
      memoExplicacion.setMemo(editarExplicacionForm.getLogro3());
      explicacion.getMemosExplicacion().add(memoExplicacion);
    }
    if (editarExplicacionForm.getLogro4() != null)
    {
      MemoExplicacion memoExplicacion = new MemoExplicacion();
      memoExplicacion.setPk(new MemoExplicacionPK(explicacion.getExplicacionId(), new Byte((byte)9)));
      memoExplicacion.setMemo(editarExplicacionForm.getLogro4());
      explicacion.getMemosExplicacion().add(memoExplicacion);
    }
    respuesta = strategosExplicacionesService.saveExplicacion(explicacion, getUsuarioConectado(request));
    if (respuesta == 10000)
    {
      strategosExplicacionesService.unlockObject(request.getSession().getId(), editarExplicacionForm.getExplicacionId());
      forward = "exito";
      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearExplicacion";
      }
      else
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
      }
    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }
    strategosExplicacionesService.close();
    
    saveMessages(request, messages);
    
    request.getSession().setAttribute("GuardarExplicacionAction.ultimoTs", ts);
    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}
