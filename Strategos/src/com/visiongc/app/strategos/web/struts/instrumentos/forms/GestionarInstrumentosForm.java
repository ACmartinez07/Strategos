/**
 * 
 */
package com.visiongc.app.strategos.web.struts.instrumentos.forms;

import java.util.List;

import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.commons.util.CondicionType;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.forms.VisorListaForm;

/**
 * @author Kerwin
 *
 */
public class GestionarInstrumentosForm  extends VisorListaForm 
{
	static final long serialVersionUID = 0;

	private String respuesta;
	private String seleccionadoId;
	private String seleccionadoNombre;
	private Long instrumentoId;
	private Long iniciativaId;
	private String anchoPorDefecto;
	private String altoPorDefecto;
	private Long organizacionId;
	private Long tiposConvenioId;
	private Long cooperanteId;
	private String anio;
	private Byte estatus;
	private String nombreCorto;
	private List<TipoConvenio> convenios;
	private List<Cooperante> cooperantes;
	
	private String claseId;
	private String indicadorId;		
	private String source;
	private Double peso;
	
	
	
	

	public String getRespuesta() 
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}
	
  	public String getSeleccionadoId() 
  	{
  		return this.seleccionadoId;
  	}

  	public void setSeleccionadoId(String seleccionadoId) 
  	{
  		this.seleccionadoId = seleccionadoId;
  	}

  	public String getSeleccionadoNombre() 
  	{
  		return this.seleccionadoNombre;
  	}

  	public void setSeleccionadoNombre(String seleccionadoNombre) 
  	{
  		this.seleccionadoNombre = seleccionadoNombre;
  	}

  	public Long getInstrumentoId() {
		return instrumentoId;
	}

	public void setInstrumentoId(Long instrumentoId) {
		this.instrumentoId = instrumentoId;
	}

	public Long getIniciativaId() 
  	{
  		return this.iniciativaId;
  	}

  	public void setIniciativaId(Long iniciativaId) 
  	{
  		this.iniciativaId = iniciativaId;
  	}
  	
	public String getAnchoPorDefecto() 
	{
		return this.anchoPorDefecto;
	}

	public void setAnchoPorDefecto(String anchoPorDefecto) 
	{
		this.anchoPorDefecto = anchoPorDefecto;
	}
	
	public String getAltoPorDefecto() 
	{
		return this.altoPorDefecto;
	}

	public void setAltoPorDefecto(String altoPorDefecto) 
	{
		this.altoPorDefecto = altoPorDefecto;
	}
	
  	public Long getOrganizacionId() 
  	{
  		return this.organizacionId;
  	}

  	public void setOrganizacionId(Long organizacionId) 
  	{
  		this.organizacionId = organizacionId;
  	}
  	  	  	
 	public Long getTiposConvenioId() {
		return tiposConvenioId;
	}

	public void setTiposConvenioId(Long tiposConvenioId) {
		this.tiposConvenioId = tiposConvenioId;
	}

	public Long getCooperanteId() {
		return cooperanteId;
	}

	public void setCooperanteId(Long cooperanteId) {
		this.cooperanteId = cooperanteId;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public Byte getEstatus() {
		return estatus;
	}

	public void setEstatus(Byte estatus) {
		this.estatus = estatus;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
		
	public List<TipoConvenio> getConvenios() {
		return convenios;
	}

	public void setConvenios(List<TipoConvenio> convenios) {
		this.convenios = convenios;
	}

	public List<Cooperante> getCooperantes() {
		return cooperantes;
	}

	public void setCooperantes(List<Cooperante> cooperantes) {
		this.cooperantes = cooperantes;
	}

	public String getClaseId() 
	{
		return this.claseId;
	}

	public void setClaseId(String claseId) 
	{
		this.claseId = claseId;
	}

	public String getIndicadorId() 
	{
		return this.indicadorId;
	}

	public void setIndicadorId(String indicadorId) 
	{
		this.indicadorId = indicadorId;
	}
  			

	
	public String getSource() 
	{
		return this.source;
	}

	public void setSource(String source) 
	{
		this.source = source;
	}
	public void clear() 
	{
		this.respuesta = "";
		this.seleccionadoId = null;
		this.seleccionadoNombre = null;
		this.instrumentoId = null;
		this.iniciativaId = null;
		this.anchoPorDefecto = null;
		this.altoPorDefecto = null;
		this.organizacionId = null;
		this.tiposConvenioId = null;
		this.cooperanteId = null;
		this.anio = null;
		this.estatus = null;
		this.nombreCorto = null;
		
		this.claseId = null;
		this.indicadorId =  null;		
		this.source = null;
		
		
		FiltroForm filtro = new FiltroForm();
		filtro.setCondicion(CondicionType.getFiltroCondicionActivo());
		filtro.setNombre(null);
		this.setFiltro(filtro);
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}
}
