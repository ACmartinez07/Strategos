package com.visiongc.app.strategos.web.struts.planes.forms;

import com.visiongc.app.strategos.planes.model.util.TipoPlan;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.Calendar;

public class EditarPlanForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;

	private static final String SEPARADOR_VALORES = "!;!";
	
	private Long planId;
	private Long organizacionId;
	private Long planImpactaId;
	private String planImpactaNombre;
	private String nombre;
	private Integer anoInicial;
	private Integer anoFinal;
	private Byte tipo;
	private Boolean activo;
	private Byte revision;
	private Long metodologiaId;
	private String metodologiaNombre;
	private Long claseId;
	private String claseIndicadoresNombre;
	private Long nlAnoIndicadorId;
	private Long nlParIndicadorId;
	private Long serieIdVigente;
	private Boolean tienePerspectivas;
	private Long originalPlanId;
	private Boolean asociarIndicador;
	private Boolean asociarIniciativa;
	private Boolean copiarIndicador;
	private Boolean copiarIniciativa;
	private Boolean crearClaseAutomaticamente;
	private Long organizacionDestinoId;
	private String organizacionDestinoNombre;
	private Boolean copiar;
	private Boolean asociar;

	public Byte getTipo()
	{
		return this.tipo;
	}

	public void setTipo(Byte tipo) 
	{
		this.tipo = tipo;
	}

	public Long getPlanId() 
	{
		return this.planId;
	}

	public void setPlanId(Long planId) 
	{
		this.planId = planId;
	}

	public Long getOrganizacionId() 
	{
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) 
	{
		this.organizacionId = organizacionId;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public Integer getAnoInicial() 
	{
		return this.anoInicial;
	}

	public void setAnoInicial(Integer anoInicial) 
	{
		this.anoInicial = anoInicial;
	}

	public Integer getAnoFinal() 
	{
		return this.anoFinal;
	}

	public void setAnoFinal(Integer anoFinal) 
	{
		this.anoFinal = anoFinal;
	}

	public Byte getRevision() 
	{
		return this.revision;
	}

	public void setRevision(Byte revision) 
	{
		this.revision = revision;
	}

	public Long getMetodologiaId() 
	{
		return this.metodologiaId;
	}

	public void setMetodologiaId(Long metodologiaId) 
	{
		this.metodologiaId = metodologiaId;
	}

	public String getMetodologiaNombre() 
	{
		return this.metodologiaNombre;
	}

	public void setMetodologiaNombre(String metodologiaNombre) 
	{
		this.metodologiaNombre = metodologiaNombre;
	}

	public Long getOriginalPlanId() 
	{
		return this.originalPlanId;
	}

	public void setOriginalPlanId(Long originalPlanId) 
	{
		this.originalPlanId = originalPlanId;
	}

	public String getClaseIndicadoresNombre() 
	{
		return this.claseIndicadoresNombre;
	}

	public void setClaseIndicadoresNombre(String claseIndicadoresNombre) 
	{
		this.claseIndicadoresNombre = claseIndicadoresNombre;
	}

	public Long getPlanImpactaId() 
	{
		return this.planImpactaId;
	}

	public void setPlanImpactaId(Long planImpactaId) 
	{
		this.planImpactaId = planImpactaId;
	}

	public String getPlanImpactaNombre() 
	{
		return this.planImpactaNombre;
	}

	public void setPlanImpactaNombre(String planImpactaNombre) 
	{
		this.planImpactaNombre = planImpactaNombre;
	}

	public Byte getTipoPlanEstrategico() 
	{
		return TipoPlan.getTipoPlanEstrategico();
	}

	public Byte getTipoPlanOperativo() 
	{
		return TipoPlan.getTipoPlanOperativo();
	}

	public Boolean getActivo() 
	{
		return this.activo;
	}

	public void setActivo(Boolean activo) 
	{
		this.activo = activo;
	}

	public Long getNlAnoIndicadorId() 
	{
		return this.nlAnoIndicadorId;
	}

	public void setNlAnoIndicadorId(Long nlAnoIndicadorId) 
	{
		this.nlAnoIndicadorId = nlAnoIndicadorId;
	}

	public Long getNlParIndicadorId() 
	{
		return this.nlParIndicadorId;
	}

	public void setNlParIndicadorId(Long nlParIndicadorId) 
	{
		this.nlParIndicadorId = nlParIndicadorId;
	}

	public Long getSerieIdVigente() 
	{
		return this.serieIdVigente;
	}

	public void setSerieIdVigente(Long serieIdVigente) 
	{
		this.serieIdVigente = serieIdVigente;
	}

	public Boolean getTienePerspectivas() 
	{
		return this.tienePerspectivas;
	}

	public void setTienePerspectivas(Boolean tienePerspectivas) 
	{
		this.tienePerspectivas = tienePerspectivas;
	}

	public String getSeparadorValores() 
	{
		return SEPARADOR_VALORES;
	}

	public Long getClaseId() 
	{
		return this.claseId;
	}

	public void setClaseId(Long claseId) 
	{
		this.claseId = claseId;
	}

	public Boolean getCopiarIndicador() 
	{
		return this.copiarIndicador;
	}

	public void setCopiarIndicador(Boolean copiarIndicador) 
	{
		this.copiarIndicador = copiarIndicador;
	}

	public Boolean getCopiarIniciativa() 
	{
		return this.copiarIniciativa;
	}

	public void setCopiarIniciativa(Boolean copiarIniciativa) 
	{
		this.copiarIniciativa = copiarIniciativa;
	}

	public Boolean getAsociarIndicador() 
	{
		return this.asociarIndicador;
	}

	public void setAsociarIndicador(Boolean asociarIndicador) 
	{
		this.asociarIndicador = asociarIndicador;
	}

	public Boolean getAsociarIniciativa() 
	{
		return this.asociarIniciativa;
	}

	public void setAsociarIniciativa(Boolean asociarIniciativa) 
	{
		this.asociarIniciativa = asociarIniciativa;
	}
	
	public Boolean getCrearClaseAutomaticamente() 
	{
		return this.crearClaseAutomaticamente;
	}

	public void setCrearClaseAutomaticamente(Boolean crearClaseAutomaticamente) 
	{
		this.crearClaseAutomaticamente = crearClaseAutomaticamente;
	}

	public Long getOrganizacionDestinoId() 
	{
		return this.organizacionDestinoId;
	}

	public void setOrganizacionDestinoId(Long organizacionDestinoId) 
	{
		this.organizacionDestinoId = organizacionDestinoId;
	}

	public String getOrganizacionDestinoNombre() 
	{
		return this.organizacionDestinoNombre;
	}

	public void setOrganizacionDestinoNombre(String organizacionDestinoNombre) 
	{
		this.organizacionDestinoNombre = organizacionDestinoNombre;
	}
	
	public Boolean getCopiar() 
	{
		return this.copiar;
	}

	public void setCopiar(Boolean copiar) 
	{
		this.copiar = copiar;
	}

	public Boolean getAsociar() 
	{
		return this.asociar;
	}

	public void setAsociar(Boolean asociar) 
	{
		this.asociar = asociar;
	}
	
	public void clear() 
	{
		Calendar ahora = Calendar.getInstance();
		
		this.planId = new Long(0L);
		this.organizacionId = null;
		this.planImpactaId = null;
		this.planImpactaNombre = null;
		this.nombre = null;
		this.anoInicial = new Integer(ahora.get(1));
		this.anoFinal = this.anoInicial;
		this.tipo = null;
		this.activo = new Boolean(false);
		this.revision = new Byte((byte) 0);
		this.metodologiaId = null;
		this.metodologiaNombre = null;
		this.claseId = null;
		this.claseIndicadoresNombre = null;
		this.nlAnoIndicadorId = null;
		this.nlParIndicadorId = null;
		this.serieIdVigente = null;
		this.tienePerspectivas = new Boolean(false);
		this.originalPlanId = new Long(0L);
		this.copiarIndicador = new Boolean(false);
		this.copiarIniciativa = new Boolean(false);
		this.asociarIndicador = new Boolean(false);
		this.asociarIniciativa = new Boolean(false);
		this.crearClaseAutomaticamente = false;
		this.organizacionDestinoId = null;
		this.organizacionDestinoNombre = null;
		this.copiar = false;
		this.asociar = false;
	}
}