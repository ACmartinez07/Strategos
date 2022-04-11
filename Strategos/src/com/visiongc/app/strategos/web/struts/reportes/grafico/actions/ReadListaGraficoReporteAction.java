/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.grafico.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.graficos.StrategosGraficosService;
import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.app.strategos.web.struts.graficos.forms.SeleccionarGraficoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class ReadListaGraficoReporteAction extends VgcAction
{
	  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	  {
	  }

	  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	  {
		  super.execute(mapping, form, request, response);

		  String forward = mapping.getParameter();

		  SeleccionarGraficoForm seleccionarGraficoForm = (SeleccionarGraficoForm)form;

		  if (seleccionarGraficoForm.getAtributoOrden() == null) 
			  seleccionarGraficoForm.setAtributoOrden("nombre");
		    
		  if (seleccionarGraficoForm.getTipoOrden() == null) 
			  seleccionarGraficoForm.setTipoOrden("ASC");

		  Map<String, String> filtros = new HashMap<String, String>();
		  filtros.put("organizacionId", (new Long((String)request.getSession().getAttribute("organizacionId"))).toString());
		  filtros.put("usuarioId", ((Usuario)request.getSession().getAttribute("usuario")).getUsuarioId().toString());
		  filtros.put("objetoId", "NULL");
		  
		  StrategosGraficosService graficosService = StrategosServiceFactory.getInstance().openStrategosGraficosService();
		  PaginaLista paginaGraficos = graficosService.getGraficos(0, 0, seleccionarGraficoForm.getAtributoOrden(), seleccionarGraficoForm.getTipoOrden(), true, filtros);
		  graficosService.close();
		  
		  request.setAttribute("paginaGraficos", paginaGraficos);

		  if (paginaGraficos.getLista() != null && paginaGraficos.getLista().size() > 0) 
		  {
		      Grafico grafico = (Grafico)paginaGraficos.getLista().get(0);
		      seleccionarGraficoForm.setSeleccionados(grafico.getGraficoId().toString());
		      seleccionarGraficoForm.setValoresSeleccionados(grafico.getNombre());
		  } 
		  else 
			  seleccionarGraficoForm.setSeleccionados(null);

		  StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		  OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long((String)request.getSession().getAttribute("organizacionId")));
		  seleccionarGraficoForm.setRutaCompletaOrganizacion(organizacionStrategos.getNombre());
		  strategosOrganizacionesService.close();
		  
		  return mapping.findForward(forward);
	}
}