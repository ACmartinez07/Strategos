package com.visiongc.app.strategos.web.struts.organizaciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion;
import com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.organizaciones.forms.EditarOrganizacionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class MostrarPropiedadesOrganizacionAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarOrganizacionForm editarOrganizacionForm = (EditarOrganizacionForm)form;

    String organizacionId = request.getParameter("organizacionId");

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;

    if ((organizacionId == null) || (organizacionId.equals(""))) {
      cancelar = true;
    }

    StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();

    if (cancelar)
    {
      strategosOrganizacionesService.unlockObject(request.getSession().getId(), editarOrganizacionForm.getOrganizacionId());

      strategosOrganizacionesService.close();

      return getForwardBack(request, 1, true);
    }

    OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(organizacionId));
    MemoOrganizacion oMemo = new MemoOrganizacion();
    if (organizacionStrategos != null)
    {
      editarOrganizacionForm.setNombre(organizacionStrategos.getNombre());
      String observaciones = "";
      for (Iterator iter = organizacionStrategos.getMemos().iterator(); iter.hasNext(); ) {
        MemoOrganizacion memo = (MemoOrganizacion)iter.next();
        if (memo.getPk().getTipo().intValue() == 1) {
          observaciones = memo.getDescripcion();
          break;
        }
      }
      editarOrganizacionForm.setObservaciones(observaciones);
      if (organizacionStrategos.getCreado() != null) 
      {
        editarOrganizacionForm.setCreado(VgcFormatter.formatearFecha(organizacionStrategos.getCreado(), "formato.fecha.larga"));
        if (organizacionStrategos.getUsuarioCreado() != null)
        	editarOrganizacionForm.setNombreUsuarioCreado(organizacionStrategos.getUsuarioCreado().getFullName());
      } 
      else 
        editarOrganizacionForm.setCreado(null);
      
      if (organizacionStrategos.getModificado() != null) 
      {
        editarOrganizacionForm.setModificado(VgcFormatter.formatearFecha(organizacionStrategos.getModificado(), "formato.fecha.larga"));
        if (organizacionStrategos.getUsuarioModificado() != null)
        	editarOrganizacionForm.setNombreUsuarioModificado(organizacionStrategos.getUsuarioModificado().getFullName());
      } 
      else 
        editarOrganizacionForm.setModificado(null);

      editarOrganizacionForm.setCreadoId(organizacionStrategos.getCreadoId());
      editarOrganizacionForm.setModificadoId(organizacionStrategos.getModificadoId());
      editarOrganizacionForm.setSoloLectura(organizacionStrategos.getSoloLectura());
    }

    strategosOrganizacionesService.close();

    saveMessages(request, messages);

    return mapping.findForward(forward);
  }
}