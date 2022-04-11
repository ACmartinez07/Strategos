package com.visiongc.app.strategos.web.struts.problemas.forms;

import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.Date;

public class EditarProblemaForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  public static final String SEPARADOR = "#%#";
  public static final String SEPARADOR_PADRE_HIJO = "#hijo#";
  private Long problemaId;
  private Long organizacionId;
  private Long claseId;
  private String nombreClase;
  private String fecha;
  private Long indicadorEfectoId;
  private Long iniciativaEfectoId;
  private String nombreObjetoEfecto;
  private Long responsableId;
  private Long auxiliarId;
  private Long estadoId;
  private String fechaEstimadaInicio;
  private String fechaRealInicio;
  private String fechaEstimadaFinal;
  private String fechaRealFinal;
  private String nombre;
  private String creado;
  private String modificado;
  private Long creadoId;
  private Long modificadoId;
  private Boolean readOnly;
  private String memoDescripcion;
  private String memoEstrategiaDeSolucion;
  private String memoConclusionesResultados;
  private String memoEspecificacion;
  private Responsable responsable;
  private Responsable auxiliar;
  private String nombreEstado;
  private String nombreUsuarioCreado;
  private String nombreUsuarioModificado;
  private String copiaNombre;
  private String causas;
  private String causaIdRoot;
  private String listaPadresHijosCausas;

  public String getSeparador()
  {
    return "#%#";
  }

  public String getSeparadorPadreHijo() {
    return "#hijo#";
  }

  public Long getProblemaId() {
    return this.problemaId;
  }

  public void setProblemaId(Long problemaId) {
    this.problemaId = problemaId;
  }

  public Long getOrganizacionId() {
    return this.organizacionId;
  }

  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }

  public Long getClaseId() {
    return this.claseId;
  }

  public void setClaseId(Long claseId) {
    this.claseId = claseId;
  }

  public String getFecha() {
    return this.fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
  }

  public Long getIndicadorEfectoId() {
    return this.indicadorEfectoId;
  }

  public void setIndicadorEfectoId(Long indicadorEfectoId) {
    this.indicadorEfectoId = indicadorEfectoId;
  }

  public Long getIniciativaEfectoId() {
    return this.iniciativaEfectoId;
  }

  public void setIniciativaEfectoId(Long iniciativaEfectoId) {
    this.iniciativaEfectoId = iniciativaEfectoId;
  }

  public String getNombreObjetoEfecto() {
    return this.nombreObjetoEfecto;
  }

  public void setNombreObjetoEfecto(String nombreObjetoEfecto) {
    this.nombreObjetoEfecto = nombreObjetoEfecto;
  }

  public Long getResponsableId() {
    return this.responsableId;
  }

  public void setResponsableId(Long responsableId) {
    this.responsableId = responsableId;
  }

  public Long getAuxiliarId() {
    return this.auxiliarId;
  }

  public void setAuxiliarId(Long auxiliarId) {
    this.auxiliarId = auxiliarId;
  }

  public Long getEstadoId() {
    return this.estadoId;
  }

  public void setEstadoId(Long estadoId) {
    this.estadoId = estadoId;
  }

  public String getFechaEstimadaInicio() {
    return this.fechaEstimadaInicio;
  }

  public void setFechaEstimadaInicio(String fechaEstimadaInicio) {
    this.fechaEstimadaInicio = fechaEstimadaInicio;
  }

  public String getFechaRealInicio() {
    return this.fechaRealInicio;
  }

  public void setFechaRealInicio(String fechaRealInicio) {
    this.fechaRealInicio = fechaRealInicio;
  }

  public String getFechaEstimadaFinal() {
    return this.fechaEstimadaFinal;
  }

  public void setFechaEstimadaFinal(String fechaEstimadaFinal) {
    this.fechaEstimadaFinal = fechaEstimadaFinal;
  }

  public String getFechaRealFinal() {
    return this.fechaRealFinal;
  }

  public void setFechaRealFinal(String fechaRealFinal) {
    this.fechaRealFinal = fechaRealFinal;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getCreado() {
    return this.creado;
  }

  public void setCreado(String creado) {
    this.creado = creado;
  }

  public String getModificado() {
    return this.modificado;
  }

  public void setModificado(String modificado) {
    this.modificado = modificado;
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

  public Boolean getReadOnly() {
    return this.readOnly;
  }

  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
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

  public String getMemoEstrategiaDeSolucion()
  {
    return this.memoEstrategiaDeSolucion;
  }

  public void setMemoEstrategiaDeSolucion(String memoEstrategiaDeSolucion) {
    if ((memoEstrategiaDeSolucion == null) || (memoEstrategiaDeSolucion.trim().equals("")))
      this.memoEstrategiaDeSolucion = null;
    else
      this.memoEstrategiaDeSolucion = memoEstrategiaDeSolucion.trim();
  }

  public String getMemoConclusionesResultados()
  {
    return this.memoConclusionesResultados;
  }

  public void setMemoConclusionesResultados(String memoConclusionesResultados) {
    if ((memoConclusionesResultados == null) || (memoConclusionesResultados.trim().equals("")))
      this.memoConclusionesResultados = null;
    else
      this.memoConclusionesResultados = memoConclusionesResultados.trim();
  }

  public String getMemoEspecificacion()
  {
    return this.memoEspecificacion;
  }

  public void setMemoEspecificacion(String memoEspecificacion) {
    if ((memoEspecificacion == null) || (memoEspecificacion.trim().equals("")))
      this.memoEspecificacion = null;
    else
      this.memoEspecificacion = memoEspecificacion.trim();
  }

  public Responsable getResponsable()
  {
    return this.responsable;
  }

  public void setResponsable(Responsable responsable) {
    this.responsable = responsable;
  }

  public Responsable getAuxiliar() {
    return this.auxiliar;
  }

  public void setAuxiliar(Responsable auxiliar) {
    this.auxiliar = auxiliar;
  }

  public String getNombreEstado() {
    return this.nombreEstado;
  }

  public void setNombreEstado(String nombreEstado) {
    this.nombreEstado = nombreEstado;
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

  public String getCopiaNombre() {
    return this.copiaNombre;
  }

  public void setCopiaNombre(String copiaNombre) {
    this.copiaNombre = copiaNombre;
  }

  public String getCausas() {
    return this.causas;
  }

  public void setCausas(String causas) {
    this.causas = causas;
  }

  public String getCausaIdRoot() {
    return this.causaIdRoot;
  }

  public void setCausaIdRoot(String causaIdRoot) {
    this.causaIdRoot = causaIdRoot;
  }

  public String getListaPadresHijosCausas() {
    return this.listaPadresHijosCausas;
  }

  public void setListaPadresHijosCausas(String listaPadresHijosCausas) {
    this.listaPadresHijosCausas = listaPadresHijosCausas;
  }

  public String getNombreClase() {
    return this.nombreClase;
  }

  public void setNombreClase(String nombreClase) {
    this.nombreClase = nombreClase;
  }

  public void clear() {
    this.problemaId = new Long(0L);
    this.organizacionId = new Long(0L);
    this.claseId = new Long(0L);
    this.fecha = VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta");
    this.indicadorEfectoId = new Long(0L);
    this.iniciativaEfectoId = new Long(0L);
    this.nombreObjetoEfecto = null;
    this.responsableId = new Long(0L);
    this.auxiliarId = new Long(0L);
    this.estadoId = new Long(0L);
    this.fechaEstimadaInicio = VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta");
    this.fechaRealInicio = null;
    this.fechaEstimadaFinal = VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta");
    this.fechaRealFinal = null;
    this.nombre = null;
    this.creado = null;
    this.modificado = null;
    this.creadoId = new Long(0L);
    this.modificadoId = new Long(0L);
    this.readOnly = new Boolean(false);
    this.memoDescripcion = null;
    this.memoEstrategiaDeSolucion = null;
    this.memoConclusionesResultados = null;
    this.memoEspecificacion = null;
    this.responsable = null;
    this.auxiliar = null;
    this.nombreUsuarioCreado = null;
    this.nombreUsuarioModificado = null;
    this.copiaNombre = null;
    this.causas = null;
    this.listaPadresHijosCausas = null;
    this.nombreClase = null;
    setBloqueado(new Boolean(false));
  }
}