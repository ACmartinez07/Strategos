package com.visiongc.app.strategos.web.struts.indicadores.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Font;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadorAsignarInventarioService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.IndicadorAsignarInventario;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.indicadores.forms.IndicadorInventarioForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

public class TransformarInventarioSalvarAction extends VgcAction {
	
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);
		String forward = mapping.getParameter();
		IndicadorInventarioForm importarMedicionesForm = (IndicadorInventarioForm)form;
		
		String atributoOrden = request.getParameter("atributoOrden");
		String tipoOrden = request.getParameter("tipoOrden");
		
		MessageResources mensajes = getResources(request);

		Usuario usuario = getUsuarioConectado(request);
		
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosIndicadorAsignarInventarioService strategosIndicadoresInventarioService = StrategosServiceFactory.getInstance().openStrategosIndicadorAsignarInventarioService();
		
		Map filtros = new HashMap();
		
		List indicadores = strategosIndicadoresService.getIndicadores(0, 0, atributoOrden, tipoOrden, false, filtros, null, null, false).getLista();
		
		if(indicadores.size() >0){
			for (Iterator iter = indicadores.iterator(); iter.hasNext(); ) 
			{
				Indicador indicador = (Indicador)iter.next();
				if(indicador.getAsignarInventario()){	
					 if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaFormula()))
				     {
						 List<InsumoFormula> insumos=null;
						 insumos=getInsumosFormula(indicador.getIndicadorId(),SerieTiempo.getSerieRealId(),strategosIndicadoresService);  
						 descomponerFormula(indicador, insumos, strategosIndicadoresInventarioService, usuario);
				     }
				}
			}
		}
		
		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.transformar.convertir.sucess"));
		saveMessages(request, messages);
	
		strategosIndicadoresService.close();
		strategosIndicadoresInventarioService.close();

	  	return mapping.findForward(forward);
	}
	
	public List<InsumoFormula> getInsumosFormula(Long indicadorId, Long serieId, StrategosIndicadoresService strategosIndicadoresService){
		 List<InsumoFormula> insumos = strategosIndicadoresService.getInsumosFormula(indicadorId, serieId);
		 return insumos;
	}
	
	public void descomponerFormula(Indicador indicador, List<InsumoFormula> insumos, StrategosIndicadorAsignarInventarioService strategosIndicadoresInventarioService, Usuario usuario){
		
		
		for (Iterator<InsumoFormula> i = insumos.iterator(); i.hasNext();)
	    {
	      InsumoFormula insumo = (InsumoFormula)i.next();
	      IndicadorAsignarInventario indicadorInventario = new IndicadorAsignarInventario();
	      indicadorInventario.setIndicadorId(indicador.getIndicadorId());
	      indicadorInventario.setIndicadorInsumoId(insumo.getPk().getIndicadorId());
	      strategosIndicadoresInventarioService.saveIndicadorInventario(indicadorInventario, usuario);
	      
	    }
	}
}
