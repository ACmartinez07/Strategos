/**
 * 
 */
package com.visiongc.app.strategos.web.struts.presentaciones.vistas.forms;

import java.util.List;

import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class ShowVistaForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	
	private String alertaVacia;
	private Long paginaPreviaId;
	private Long paginaSiguienteId;
	private Long paginaId;
	private Long vistaId;
	private Pagina pagina;
	private Vista vista;
	private List<Celda> celdas;
	private Byte frecuencia;
	private Integer anchoMarco;
	private Integer anchoCelda;
	private Integer anchoPagina;
	private Integer altoCelda;
	private Integer altoPagina;
	private Integer paginas;

	public Integer getPaginas() 
	{
	    return this.paginas;
	}

	public void setPaginas(Integer paginas) 
	{
	    this.paginas = paginas;
	}

	public Integer getAltoPagina() 
	{
	    return this.altoPagina;
	}

	public void setAltoPagina(Integer altoPagina) 
	{
	    this.altoPagina = altoPagina;
	}

	public Integer getAltoCelda() 
	{
	    return this.altoCelda;
	}

	public void setAltoCelda(Integer altoCelda) 
	{
	    this.altoCelda = altoCelda;
	}

	public Integer getAnchoPagina() 
	{
	    return this.anchoPagina;
	}

	public void setAnchoPagina(Integer anchoPagina) 
	{
	    this.anchoPagina = anchoPagina;
	}

	public Integer getAnchoCelda() 
	{
	    return this.anchoCelda;
	}

	public void setAnchoCelda(Integer anchoCelda) 
	{
	    this.anchoCelda = anchoCelda;
	}
	
	public Integer getAnchoMarco() 
	{
	    return this.anchoMarco;
	}

	public void setAnchoMarco(Integer anchoMarco) 
	{
	    this.anchoMarco = anchoMarco;
	}

	public Byte getFrecuencia() 
	{
	    return this.frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) 
	{
	    this.frecuencia = frecuencia;
	}

	public List<Celda> getCeldas() 
	{
	    return this.celdas;
	}

	public void setCeldas(List<Celda> celdas) 
	{
	    this.celdas = celdas;
	}

	public Vista getVista() 
	{
	    return this.vista;
	}

	public void setVista(Vista vista) 
	{
	    this.vista = vista;
	}

	public Pagina getPagina() 
	{
	    return this.pagina;
	}

	public void setPagina(Pagina pagina) 
	{
	    this.pagina = pagina;
	}

	public Long getPaginaSiguienteId() 
	{
	    return this.paginaSiguienteId;
	}

	public void setPaginaSiguienteId(Long paginaSiguienteId) 
	{
	    this.paginaSiguienteId = paginaSiguienteId;
	}

	public Long getPaginaPreviaId() 
	{
	    return this.paginaPreviaId;
	}

	public void setPaginaPreviaId(Long paginaPreviaId) 
	{
	    this.paginaPreviaId = paginaPreviaId;
	}
	
	public String getAlertaVacia() 
	{
	    return this.alertaVacia;
	}

	public void setAlertaVacia(String alertaVacia) 
	{
	    this.alertaVacia = alertaVacia;
	}
	
	public String getSeparadorSeries() 
	{
		return new com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm().getSeparadorSeries();
	}
	
	public void setPaginaId(Long paginaId) 
	{
	    this.paginaId = paginaId;
	}

	public Long getPaginaId() 
	{
	    return this.paginaId;
	}

	public void setVistaId(Long vistaId) 
	{
	    this.vistaId = vistaId;
	}

	public Long getVistaId() 
	{
	    return this.vistaId;
	}

	public void clear()
	{
		this.alertaVacia = null;
		this.paginaPreviaId = null;
		this.paginaSiguienteId = null;
		this.pagina = null;
		this.vista = null;
		this.celdas = null;
		this.frecuencia = Frecuencia.getFrecuenciaAnual();
		this.anchoMarco = null;
		this.anchoCelda = null;
		this.anchoPagina = null;
		this.altoCelda = null;
		this.altoPagina = null;
		this.paginas = null;
		this.paginaId = null;
		this.vistaId = null;
	}
}
