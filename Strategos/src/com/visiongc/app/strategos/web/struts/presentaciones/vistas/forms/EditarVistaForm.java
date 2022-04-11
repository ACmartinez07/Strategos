package com.visiongc.app.strategos.web.struts.presentaciones.vistas.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.Calendar;
import java.util.List;

public class EditarVistaForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
  
	private Long vistaId;
	private Long organizacionId;
	private String nombre;
	private String descripcion;
	private String fechaInicio;
	private String fechaFin;
	private Integer anoInicio;
	private Integer anoFinal;
	private Byte mesInicio;
	private Byte mesFinal;
	private List meses;
	private List anos;
	public static final String SEPARADOR_PERIODOS = "/";
	private Boolean visible;
	
	Calendar fechaActual = Calendar.getInstance();
	int anoActual = this.fechaActual.get(1);

	public Long getVistaId()
	{
		return this.vistaId;
	}

	public void setVistaId(Long vistaId) 
	{
		this.vistaId = vistaId;
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

	public String getDescripcion() 
	{
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}

	public String getFechaInicio() 
	{
		return this.fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) 
	{
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() 
	{
		return this.fechaFin;
	}

	public void setFechaFin(String fechaFin) 
	{
		this.fechaFin = fechaFin;
	}

	public Boolean getVisible() 
	{
		return this.visible;
	}

	public void setVisible(Boolean visible) 
	{
		this.visible = visible;
	}

	public Integer getAnoInicio() 
	{
		return this.anoInicio;
	}

	public void setAnoInicio(Integer anoInicio) 
	{
		this.anoInicio = anoInicio;
	}

	public Integer getAnoFinal() 
	{
		return this.anoFinal;
	}

	public void setAnoFinal(Integer anoFinal) 
	{
		this.anoFinal = anoFinal;
	}

	public Byte getMesInicio() 
	{
		return this.mesInicio;
	}

	public void setMesInicio(Byte mesInicio) 
	{
		this.mesInicio = mesInicio;
	}

	public Byte getMesFinal() 
	{
		return this.mesFinal;
	}

	public void setMesFinal(Byte mesFinal) 
	{
		this.mesFinal = mesFinal;
	}

	public static String getSeparadorPeriodos() 
	{
		return "/";
	}

	public List getMeses()
	{
		return this.meses;
	}

	public void setMeses(List meses) 
	{
		this.meses = meses;
	}

	public List getAnos() 
	{
		return this.anos;
	}

	public void setAnos(List anos) 
	{
		this.anos = anos;
	}

	public void clear()
	{
		this.vistaId = new Long(0L);
		this.organizacionId = new Long(0L);
		this.nombre = null;
		this.descripcion = null;
		this.fechaInicio = null;
		this.fechaFin = null;
		this.visible = new Boolean(true);
		this.anoInicio = new Integer(this.anoActual);
		this.anoFinal = new Integer(this.anoActual);
	}
}