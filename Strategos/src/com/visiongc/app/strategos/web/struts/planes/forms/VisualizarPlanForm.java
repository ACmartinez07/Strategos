package com.visiongc.app.strategos.web.struts.planes.forms;

import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.web.struts.planes.util.TipoVistaPlan;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.framework.model.Usuario;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;

public class VisualizarPlanForm extends ActionForm
{
	static final long serialVersionUID = 0L;
	
	private Long organizacionId;
	private Long planId;
	private String nombrePlan;
	private Plan plan;
	private PlantillaPlanes plantillaPlan;
	private Long claseId;
	private Boolean esUsuarioResponsable;
	private Integer ano;
	private Byte tipoVista;
	private Boolean mostrarTipoVistaDetallada;
	private Boolean mostrarTipoVistaEjecutiva;
	private Boolean mostrarTipoVistaResumen;
	private Boolean mostrarTipoVistaArbol;
	private List<ObjetoClaveValor> anos;
	private ConfiguracionPlan configuracionPlan;
	
	public Long getOrganizacionId()
	{
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) 
	{
		this.organizacionId = organizacionId;
	}

	public Long getPlanId() 
	{
		return this.planId;
	}

	public void setPlanId(Long planId) 
	{
		this.planId = planId;
	}

	public String getNombrePlan() 
	{
		return this.nombrePlan;
	}

	public void setNombrePlan(String nombrePlan) 
	{
		this.nombrePlan = nombrePlan;
	}

	public Plan getPlan() 
	{
		return this.plan;
	}

	public void setPlan(Plan plan) 
	{
		this.plan = plan;
	}

	public PlantillaPlanes getPlantillaPlan() 
	{
		return this.plantillaPlan;
	}

	public void setPlantillaPlan(PlantillaPlanes plantillaPlan) 
	{
		this.plantillaPlan = plantillaPlan;
	}

	public Long getClaseId() 
	{
		return this.claseId;
	}

	public void setClaseId(Long claseId) 
	{
		this.claseId = claseId;
	}

	public Boolean getEsUsuarioResponsable() 
	{
		return this.esUsuarioResponsable;
	}

	public void setEsUsuarioResponsable(Boolean esUsuarioResponsable) 
	{
		this.esUsuarioResponsable = esUsuarioResponsable;
	}

	public Integer getAno() 
	{
		return this.ano;
	}

	public void setAno(Integer ano) 
	{
		this.ano = ano;
	}

	public Byte getTipoVista() 
	{
		return this.tipoVista;
	}

	public void setTipoVista(Byte tipoVista) 
	{
		this.tipoVista = tipoVista;
	}

	public Boolean getMostrarTipoVistaDetallada() 
	{
		return this.mostrarTipoVistaDetallada;
	}

	public void setMostrarTipoVistaDetallada(Boolean mostrarTipoVistaDetallada) 
	{
		this.mostrarTipoVistaDetallada = mostrarTipoVistaDetallada;
	}

	public Boolean getMostrarTipoVistaEjecutiva() 
	{
		return this.mostrarTipoVistaEjecutiva;
	}

	public void setMostrarTipoVistaEjecutiva(Boolean mostrarTipoVistaEjecutiva) 
	{
		this.mostrarTipoVistaEjecutiva = mostrarTipoVistaEjecutiva;
	}

	public Boolean getMostrarTipoVistaResumen() 
	{
		return this.mostrarTipoVistaResumen;
	}

	public void setMostrarTipoVistaResumen(Boolean mostrarTipoVistaResumen) 
	{
		this.mostrarTipoVistaResumen = mostrarTipoVistaResumen;
	}

	public Boolean getMostrarTipoVistaArbol() 
	{
		return this.mostrarTipoVistaArbol;
	}

	public void setMostrarTipoVistaArbol(Boolean mostrarTipoVistaArbol) 
	{
		this.mostrarTipoVistaArbol = mostrarTipoVistaArbol;
	}

	public List<ObjetoClaveValor> getAnos() 
	{
		return this.anos;
	}

	public void setAnos(List<ObjetoClaveValor> anos) 
	{
		this.anos = anos;
	}

	public Byte getIndicadorNaturalezaFormula() 
	{
		return Naturaleza.getNaturalezaFormula();
	}

	public Byte getTipoVistaDetallada() 
	{
		return TipoVistaPlan.getTipoVistaPlanDetallada();
	}

	public Byte getTipoVistaResumen() 
	{
		return TipoVistaPlan.getTipoVistaPlanResumen();
	}

	public Byte getTipoVistaEjecutiva() 
	{
		return TipoVistaPlan.getTipoVistaPlanEjecutiva();
	}

	public Byte getTipoVistaArbol() 
	{
		return TipoVistaPlan.getTipoVistaPlanArbol();
	}

	public String getNombreTipoVistaDetallada() 
	{
		return TipoVistaPlan.getNombre(TipoVistaPlan.getTipoVistaPlanDetallada().byteValue());
	}

	public String getNombreTipoVistaResumen() 
	{
		return TipoVistaPlan.getNombre(TipoVistaPlan.getTipoVistaPlanResumen().byteValue());
	}

	public String getNombreTipoVistaEjecutiva() 
	{
		return TipoVistaPlan.getNombre(TipoVistaPlan.getTipoVistaPlanEjecutiva().byteValue());
	}

	public String getNombreTipoVistaArbol() 
	{
		return TipoVistaPlan.getNombre(TipoVistaPlan.getTipoVistaPlanArbol().byteValue());
	}

  	public void inicializar(HttpServletRequest request) 
  	{
  		if ((this.organizacionId == null) || (this.organizacionId.longValue() == 0L)) 
  			this.organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));
  		else 
  		{
  			long orgId = Long.parseLong((String)request.getSession().getAttribute("organizacionId"));
  			if (this.organizacionId.longValue() != orgId) 
  				this.organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));
  		}
	  
  		if (this.tipoVista == null)
  			this.tipoVista = TipoVistaPlan.getTipoVistaPlanDetallada();
  	}
  	
	public ConfiguracionPlan getConfiguracionPlan() 
	{
		return this.configuracionPlan;
	}

	public void setConfiguracionPlan(ConfiguracionPlan configuracionPlan) 
	{
		this.configuracionPlan = configuracionPlan;
	}

  	public void clear()
  	{
  	}
}