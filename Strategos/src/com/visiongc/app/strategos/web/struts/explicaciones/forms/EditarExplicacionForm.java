package com.visiongc.app.strategos.web.struts.explicaciones.forms;

import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditarExplicacionForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
  
	private Long explicacionId;
	private String fecha;
	private Long creadoId;
	private Long modificadoId;
	private Byte objetoKey;
	private Long objetoId;
	private String titulo;
	private String nombreUsuarioCreado;
	private String nombreUsuarioModificado;
	private String fechaCreado;
	private String fechaModificado;
	private Long numeroAdjuntos;
	private String nombreObjetoKey;
	private String nombreTipoObjetoKey;
	private String nombreOrganizacion;
	private String memoDescripcion;
	private String memoCausas;
	private String memoCorrectivos;
	private String memoImpactos;
	private String memoPerspectivas;
	private String memoUrl;
	private List adjuntosExplicacion;
	private Integer tipo;
	private String fechaCompromiso;
	private String fechaReal;
	private String logro1;
	private String logro2;
	private String logro3;
	private String logro4;
	private Boolean onlyView;
	private Boolean publico;
	private Boolean addPublico;
  
	public Long getExplicacionId()
	{
		return this.explicacionId;
	}

  public void setExplicacionId(Long explicacionId) {
    this.explicacionId = explicacionId;
  }

  public String getFecha() {
    return this.fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
  }

  public Long getCreadoId() {
    return this.creadoId;
  }

  public void setCreadoId(Long creadoId) {
    this.creadoId = creadoId;
  }

  public Long getModificadoId() {
    return this.modificadoId;
  }

  public void setModificadoId(Long modificadoId) {
    this.modificadoId = modificadoId;
  }

  public Byte getObjetoKey() {
    return this.objetoKey;
  }

  public void setObjetoKey(Byte objetoKey) {
    this.objetoKey = objetoKey;
  }

  public Long getObjetoId() {
    return this.objetoId;
  }

  public void setObjetoId(Long objetoId) {
    this.objetoId = objetoId;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getNombreUsuarioCreado() {
    return this.nombreUsuarioCreado;
  }

  public void setNombreUsuarioCreado(String nombreUsuarioCreado) {
    this.nombreUsuarioCreado = nombreUsuarioCreado;
  }

  public String getNombreUsuarioModificado() {
    return this.nombreUsuarioModificado;
  }

  public void setNombreUsuarioModificado(String nombreUsuarioModificado) {
    this.nombreUsuarioModificado = nombreUsuarioModificado;
  }

  public String getFechaCreado() {
    return this.fechaCreado;
  }

  public void setFechaCreado(String fechaCreado) {
    this.fechaCreado = fechaCreado;
  }

  public String getFechaModificado() {
    return this.fechaModificado;
  }

  public void setFechaModificado(String fechaModificado) {
    this.fechaModificado = fechaModificado;
  }

  public Long getNumeroAdjuntos() {
    return this.numeroAdjuntos;
  }

  public void setNumeroAdjuntos(Long numeroAdjuntos) {
    this.numeroAdjuntos = numeroAdjuntos;
  }

  public String getNombreObjetoKey() {
    return this.nombreObjetoKey;
  }

  public void setNombreObjetoKey(String nombreObjetoKey) {
    this.nombreObjetoKey = nombreObjetoKey;
  }

  public String getNombreOrganizacion() {
    return this.nombreOrganizacion;
  }

  public void setNombreOrganizacion(String nombreOrganizacion) {
    this.nombreOrganizacion = nombreOrganizacion;
  }

  public String getNombreTipoObjetoKey() {
    return this.nombreTipoObjetoKey;
  }

  public void setNombreTipoObjetoKey(String nombreTipoObjetoKey) {
    this.nombreTipoObjetoKey = nombreTipoObjetoKey;
  }

  public String getMemoDescripcion() {
    return this.memoDescripcion;
  }

  public void setMemoDescripcion(String memoDescripcion) {
    if ((memoDescripcion == null) || (memoDescripcion.trim().equals("")))
      this.memoDescripcion = null;
    else
      this.memoDescripcion = memoDescripcion.trim();
  }

  public String getMemoCausas()
  {
    return this.memoCausas;
  }

  public void setMemoCausas(String memoCausas) {
    if ((memoCausas == null) || (memoCausas.trim().equals("")))
      this.memoCausas = null;
    else
      this.memoCausas = memoCausas.trim();
  }

  public String getMemoCorrectivos()
  {
    return this.memoCorrectivos;
  }

  public void setMemoCorrectivos(String memoCorrectivos) {
    if ((memoCorrectivos == null) || (memoCorrectivos.trim().equals("")))
      this.memoCorrectivos = null;
    else
      this.memoCorrectivos = memoCorrectivos.trim();
  }

  public String getMemoImpactos()
  {
    return this.memoImpactos;
  }

  public void setMemoImpactos(String memoImpactos) {
    if ((memoImpactos == null) || (memoImpactos.trim().equals("")))
      this.memoImpactos = null;
    else
      this.memoImpactos = memoImpactos.trim();
  }

  public String getMemoPerspectivas()
  {
    return this.memoPerspectivas;
  }

  public void setMemoPerspectivas(String memoPerspectivas) {
    if ((memoPerspectivas == null) || (memoPerspectivas.trim().equals("")))
      this.memoPerspectivas = null;
    else
      this.memoPerspectivas = memoPerspectivas.trim();
  }

  public String getMemoUrl()
  {
    return this.memoUrl;
  }

  public void setMemoUrl(String memoUrl) {
    if ((memoUrl == null) || (memoUrl.trim().equals("")))
      this.memoUrl = null;
    else
      this.memoUrl = memoUrl.trim();
  }

  public List getAdjuntosExplicacion()
  {
    return this.adjuntosExplicacion;
  }

  public void setAdjuntosExplicacion(List adjuntosExplicacion) {
    this.adjuntosExplicacion = adjuntosExplicacion;
  }

  public Integer getTipo()
  {
    return this.tipo;
  }

  public void setTipo(Integer tipo) 
  {
    this.tipo = tipo;
  }
  
	public String getFechaCompromiso() 
	{
		return this.fechaCompromiso;
	}

	public void setFechaCompromiso(String fechaCompromiso) 
	{
		this.fechaCompromiso = fechaCompromiso;
	}
	
	public String getFechaReal() 
	{
		return this.fechaReal;
	}

	public void setFechaReal(String fechaReal) 
	{
		this.fechaReal = fechaReal;
	}
	
	public String getLogro1()
	{
		return this.logro1;
	}

	public void setLogro1(String logro1) 
	{
		if ((logro1 == null) || (logro1.trim().equals("")))
			this.logro1 = null;
	    else
	    	this.logro1 = logro1.trim();
	}

	public String getLogro2()
	{
		return this.logro2;
	}

	public void setLogro2(String logro2) 
	{
		if ((logro2 == null) || (logro2.trim().equals("")))
			this.logro2 = null;
	    else
	    	this.logro2 = logro2.trim();
	}

	public String getLogro3()
	{
		return this.logro3;
	}

	public void setLogro3(String logro3) 
	{
		if ((logro3 == null) || (logro3.trim().equals("")))
			this.logro3 = null;
	    else
	    	this.logro3 = logro3.trim();
	}

	public String getLogro4()
	{
		return this.logro4;
	}

	public void setLogro4(String logro4) 
	{
		if ((logro4 == null) || (logro4.trim().equals("")))
			this.logro4 = null;
	    else
	    	this.logro4 = logro4.trim();
	}

	public Boolean getOnlyView()
	{
		return this.onlyView;
	}

	public void setOnlyView(Boolean onlyView) 
	{
    	this.onlyView = onlyView;
	}

	public Boolean getPublico()
	{
		return this.publico;
	}

	public void setPublico(Boolean publico) 
	{
    	this.publico = publico;
	}

	public Boolean getAddPublico()
	{
		return this.addPublico;
	}

	public void setAddPublico(Boolean addPublico) 
	{
    	this.addPublico = addPublico;
	}
	
	public void clear() 
	{
		this.explicacionId = new Long(0L);
		this.fecha = null;
		this.creadoId = new Long(0L);
		this.modificadoId = new Long(0L);
		this.objetoKey = null;
		this.objetoId = new Long(0L);
		this.titulo = null;
		this.nombreUsuarioCreado = null;
		this.nombreUsuarioModificado = null;
		this.fechaCreado = null;
		this.fechaModificado = null;
		this.numeroAdjuntos = new Long(0L);
		this.nombreObjetoKey = null;
		this.nombreOrganizacion = null;
		this.nombreTipoObjetoKey = null;
		this.memoDescripcion = null;
		this.memoCausas = null;
		this.memoCorrectivos = null;
		this.memoImpactos = null;
		this.memoPerspectivas = null;
		this.memoUrl = null;
		this.adjuntosExplicacion = new ArrayList();
		this.tipo = null;
		this.fechaCompromiso = null;
    	Date ahora = new Date();
		this.fechaReal = VgcFormatter.formatearFecha(ahora, "formato.fecha.corta");;
		this.logro1 = null;
		this.logro2 = null;
		this.logro3 = null;
		this.logro4 = null;
		this.onlyView = null;
		this.publico = null;
		this.addPublico = null;
		setBloqueado(new Boolean(false));
	}
}