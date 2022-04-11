package com.visiongc.app.strategos.web.struts.calculos.forms;

import java.util.List;

import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class CalculoIndicadoresForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	private static final byte ORIGEN_ORGANIZACIONES = 1;
	private static final byte ORIGEN_INDICADORES = 2;
	private static final byte ORIGEN_PLANES = 3;
	private static final byte ORIGEN_INICIATIVAS = 4;

	private static final Byte ALCANCE_INDICADOR = 0;
	private static final Byte ALCANCE_CLASE = 1;
	private static final Byte ALCANCE_ORGANIZACION = 2;
	private static final Byte ALCANCE_ORGANIZACION_SELECCIONADA = 3;
	private static final Byte ALCANCE_ORGANIZACION_TODAS = 4;
	private static final Byte ALCANCE_PLAN = 5;
	private static final Byte ALCANCE_PERSPECTIVA = 6;
	private static final Byte ALCANCE_INICIATIVA = 7;
	
	private Byte origen;
	private String puntoEdicion;
	private Boolean porNombre;
	private Boolean periodosCero;
	private Boolean reportarIndicadores;
	private Boolean reportarErrores;
	private Byte alcance;
	private String ano;
	private String anoInicio;
	private String anoFin;
	private Byte mesInicial;
	private Byte mesFinal;
	private Byte frecuencia;
	private Long organizacionId;
	private Long claseId;
	private Long indicadorId;
	private Long serieId;
	private Long planId;
	private Long perspectivaId;
	private Long iniciativaId;
	private String nombreIndicador;
	private boolean seleccionandoIndicadores;
	private Boolean calculado;
	private Byte status = 0;
	private List<Frecuencia> frecuencias;

	public Byte getOrigen()
	{
		return this.origen;
	}

	public void setOrigen(Byte origen) 
	{
		this.origen = origen;
	}

	public Boolean getCalculado() 
	{
		return this.calculado;
	}

	public void setCalculado(Boolean calculado) 
	{
		this.calculado = calculado;
	}

	public boolean getSeleccionandoIndicadores() 
	{
		return this.seleccionandoIndicadores;
	}

	public void setSeleccionandoIndicadores(boolean seleccionandoIndicadores) 
	{
		this.seleccionandoIndicadores = seleccionandoIndicadores;
	}

	public String getPuntoEdicion() 
	{
		return this.puntoEdicion;
	}

	public void setPuntoEdicion(String puntoEdicion) 
	{
		this.puntoEdicion = puntoEdicion;
	}

	public Long getOrganizacionId() 
	{
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) 
	{
		this.organizacionId = organizacionId;
	}

	public Long getClaseId() 
	{
		return this.claseId;
	}

	public void setClaseId(Long claseId) 
	{
		this.claseId = claseId;
	}

	public Long getIndicadorId() 
	{
		return this.indicadorId;
	}

	public void setIndicadorId(Long indicadorId) 
	{
		this.indicadorId = indicadorId;
	}

	public Long getSerieId() 
	{
		return this.serieId;
	}

	public void setSerieId(Long serieId) 
	{
		this.serieId = serieId;
	}

	public Long getPlanId() 
	{
		return this.planId;
	}

	public void setPlanId(Long planId) 
	{
		this.planId = planId;
	}

	public Long getPerspectivaId() 
	{
		return this.perspectivaId;
	}

	public void setPerspectivaId(Long perspectivaId) 
	{
		this.perspectivaId = perspectivaId;
	}

	public Long getIniciativaId() 
	{
		return this.iniciativaId;
	}

	public void setIniciativaId(Long iniciativaId) 
	{
		this.iniciativaId = iniciativaId;
	}
	
	public String getNombreIndicador() 
	{
		return this.nombreIndicador;
	}

	public void setNombreIndicador(String nombreIndicador) 
	{
		this.nombreIndicador = nombreIndicador;
	}

	public Boolean getPorNombre() 
	{
		return this.porNombre;
	}

	public void setPorNombre(Boolean porNombre) 
	{
		this.porNombre = porNombre;
	}

	public Boolean getPeriodosCero() 
	{
		return this.periodosCero;
	}

	public void setPeriodosCero(Boolean periodosCero) 
	{
		this.periodosCero = periodosCero;
	}

	public Boolean getReportarIndicadores() 
	{
		return this.reportarIndicadores;
	}

	public void setReportarIndicadores(Boolean reportarIndicadores) 
	{
		this.reportarIndicadores = reportarIndicadores;
	}

	public Boolean getReportarErrores() 
	{
		return this.reportarErrores;
	}

	public void setReportarErrores(Boolean reportarErrores) 
	{
		this.reportarErrores = reportarErrores;
	}

	public String getAno() 
	{
		return this.ano;
	}

	public void setAno(String ano) 
	{
		this.ano = ano;
	}

	public Byte getMesInicial() 
	{
		return this.mesInicial;
	}

	public void setMesInicial(Byte mesInicial) 
	{
		this.mesInicial = mesInicial;
	}

	public Byte getAlcance() 
	{
		return this.alcance;
	}

	public void setAlcance(Byte alcance) 
	{
		this.alcance = alcance;
	}

	public Byte getOrigenOrganizaciones() 
	{
		return new Byte((byte) ORIGEN_ORGANIZACIONES);
	}

	public Byte getOrigenIndicadores() 
	{
		return new Byte((byte) ORIGEN_INDICADORES);
	}

	public Byte getOrigenPlanes() 
	{
		return new Byte((byte) ORIGEN_PLANES);
	}

	public Byte getOrigenIniciativas() 
	{
		return new Byte((byte) ORIGEN_INICIATIVAS);
	}

	public Byte getAlcanceIndicador() 
	{
		return ALCANCE_INDICADOR;
	}

	public Byte getAlcanceClase() 
	{
		return ALCANCE_CLASE;
	}

	public Byte getAlcanceOrganizacion() 
	{
		return ALCANCE_ORGANIZACION;
	}

	public Byte getAlcanceOrganizacionSeleccionada() 
	{
		return ALCANCE_ORGANIZACION_SELECCIONADA;
	}

	public Byte getAlcanceOrganizacionTodas() 
	{
		return ALCANCE_ORGANIZACION_TODAS;
	}

	public Byte getAlcancePlan() 
	{
		return ALCANCE_PLAN;
	}

	public Byte getAlcancePerspectiva() 
	{
		return ALCANCE_PERSPECTIVA;
	}

	public Byte getAlcanceIniciativa() 
	{
		return ALCANCE_INICIATIVA;
	}

	public Byte getMesFinal() 
	{
		return this.mesFinal;
	}

	public void setMesFinal(Byte mesFinal) 
	{
		this.mesFinal = mesFinal;
	}

	public Byte getFrecuencia() 
	{
		return this.frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) 
	{
		this.frecuencia = frecuencia;
	}

	public int getNumMaximoPeriodos() 
	{
		return 12;
	}

	public Long getOrgId() 
	{
		return this.serieId;
	}

	public Byte getStatus()
	{
		return this.status;
	}

	public void setStatus(Byte status) 
	{
		this.status = CalcularStatus.getCalcularStatus(status);
	}

	public List<Frecuencia> getFrecuencias() 
	{
		return this.frecuencias;
	}

	public void setFrecuencias(List<Frecuencia> frecuencias) 
	{
		this.frecuencias = frecuencias;
	}

	public String getAnoInicio() 
	{
		return this.anoInicio;
	}

	public void setAnoInicio(String anoInicio) 
	{
		this.anoInicio = anoInicio;
	}
	
	public String getAnoFin() 
	{
		return this.anoFin;
	}

	public void setAnoFin(String anoFin) 
	{
		this.anoFin = anoFin;
	}
	
	public void clear()
	{
		this.origen = null;
		this.puntoEdicion = null;
		this.porNombre = null;
		this.periodosCero = null;
		this.reportarIndicadores = null;
		this.reportarErrores = null;
		this.alcance = null;
		this.ano = null;
		this.anoInicio = null;
		this.anoFin = null;
		this.mesInicial = null;
		this.mesFinal = null;
		this.frecuencia = null;
		this.organizacionId = null;
		this.claseId = null;
		this.indicadorId = null;
		this.serieId = null;
		this.planId = null;
		this.perspectivaId = null;
		this.nombreIndicador = null;
		this.seleccionandoIndicadores = false;
		this.calculado = null;
		this.status = 0;
		this.frecuencias = Frecuencia.getFrecuencias();
		this.setShowPresentacion(false);
	}
	
	public static class CalcularStatus
	{
		private static final byte CALCULARSTATUS_SUCCESS = 0;
		private static final byte CALCULARSTATUS_NOSUCCESS = 1;
		private static final byte CALCULARSTATUS_CALCULADO = 2;
		private static final byte CALCULARSTATUS_NOCONFIGURADO = 3;
		
		private static Byte getCalcularStatus(Byte status)
		{
			if (status == CALCULARSTATUS_SUCCESS)
				return new Byte(CALCULARSTATUS_SUCCESS);
			else if (status == CALCULARSTATUS_NOSUCCESS)
				return new Byte(CALCULARSTATUS_NOSUCCESS);
			else if (status == CALCULARSTATUS_CALCULADO)
				return new Byte(CALCULARSTATUS_CALCULADO);
			else if (status == CALCULARSTATUS_NOCONFIGURADO)
				return new Byte(CALCULARSTATUS_NOCONFIGURADO);
			else
				return null;
		}
		
		public static Byte getCalcularStatusSuccess() 
		{
			return new Byte(CALCULARSTATUS_SUCCESS);
		}
		
		public static Byte getCalcularStatusNoSuccess() 
		{
			return new Byte(CALCULARSTATUS_NOSUCCESS);
		}

		public static Byte getCalcularStatusCalculado() 
		{
			return new Byte(CALCULARSTATUS_CALCULADO);
		}

		public static Byte getCalcularStatusNoConfigurado() 
		{
			return new Byte(CALCULARSTATUS_NOCONFIGURADO);
		}
	}
}