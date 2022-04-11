package com.visiongc.app.strategos.indicadores.model;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.PrioridadIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.servicio.strategos.calculos.util.CalculoUtil;
import com.visiongc.servicio.strategos.planes.model.util.TipoIndicadorEstado;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Indicador
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long indicadorId;
  private Long claseId;
  private Long organizacionId;
  private String nombre;
  private String descripcion;
  private Byte naturaleza;
  private Byte frecuencia;
  private String comportamiento;
  private Long unidadId;
  private String codigoEnlace;
  private String nombreCorto;
  private Byte prioridad;
  private Boolean mostrarEnArbol;
  private String fuente;
  private String orden;
  private Byte indicadorAsociadoTipo;
  private Long indicadorAsociadoId;
  private Byte indicadorAsociadoRevision;
  private Boolean soloLectura;
  private Byte caracteristica;
  private Byte alerta;
  private String fechaUltimaMedicion;
  private Double ultimaMedicion;
  private Double ultimoProgramado;
  private Double ultimoProgramadoInferior;
  private Double metaAnual;
  private Double metaParcial;
  private Double estadoAnual;
  private Double estadoParcial;
  private Byte tipoFuncion;
  private Long responsableNotificacionId;
  private Long responsableFijarMetaId;
  private Long responsableLograrMetaId;
  private Long responsableSeguimientoId;
  private Long responsableCargarMetaId;
  private Long responsableCargarEjecutadoId;
  private Responsable responsableNotificacion;
  private Responsable responsableFijarMeta;
  private Responsable responsableLograrMeta;
  private Responsable responsableSeguimiento;
  private Responsable responsableCargarMeta;
  private Responsable responsableCargarEjecutado;
  private Boolean guia;
  private Byte corte;
  private String enlaceParcial;
  private Integer alertaMinMax;
  private Double alertaMetaZonaVerde;
  private Double alertaMetaZonaAmarilla;
  private Byte alertaTipoZonaVerde;
  private Byte alertaTipoZonaAmarilla;
  private Boolean alertaValorVariableZonaVerde;
  private Boolean alertaValorVariableZonaAmarilla;
  private Long alertaIndicadorIdZonaVerde;
  private Long alertaIndicadorIdZonaAmarilla;
  private Boolean valorInicialCero;
  private Byte numeroDecimales;
  private Byte tipoCargaMedicion;
  private Byte tipoCargaMeta;
  private Long parametroSuperiorIndicadorId;
  private Long parametroInferiorIndicadorId;
  private Double parametroSuperiorIndicadorValorPeriodo;
  private Double parametroInferiorIndicadorValorPeriodo;
  private Double parametroSuperiorValorFijo;
  private Double parametroInferiorValorFijo;
  private String url;
  private Date fechaBloqueoEjecutado;
  private Date fechaBloqueoMeta;
  private Boolean multidimensional;
  private Set seriesIndicador;
  private OrganizacionStrategos organizacion;
  private ClaseIndicadores clase;
  private UnidadMedida unidad;
  private List escalaCualitativa;
  private Set formulas;
  private InsumoFormula insumoFormula;
  private Boolean nodoFinal;
  private String formulaSerieReal;
  private Integer index;
  private Long planId;
  private Long perspectivaId;
  private Boolean estaBloqueado;
  private Double peso;
  private Byte tipoSumaMedicion;
  private Double ultimaMedicionAnoAnterior;
  private Boolean asignarInventario;
  private String fechaInicial;
  private String fechaFinal;
  private Long actividadId;
  private Boolean isPocentaje;
  
//estos son campos temporales, no tienen mapeo de base de datos
  private String fechaesperado;
  private String observacion;
  private String organizacionNombre;
  
  public Indicador() {}
  
  public Indicador(Long indicadorId, Long claseId, Long organizacionId, String nombre, Byte naturaleza, Byte frecuencia)
  {
    this.indicadorId = indicadorId;
    this.claseId = claseId;
    this.organizacionId = organizacionId;
    this.nombre = nombre;
    this.naturaleza = naturaleza;
    this.frecuencia = frecuencia;
  }
  
  public Long getIndicadorId()
  {
    return this.indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId)
  {
    this.indicadorId = indicadorId;
  }
  
  public Long getClaseId()
  {
    return this.claseId;
  }
  
  public void setClaseId(Long claseId)
  {
    this.claseId = claseId;
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
  
  public Byte getNaturaleza()
  {
    return this.naturaleza;
  }
  
  public void setNaturaleza(Byte naturaleza)
  {
    this.naturaleza = naturaleza;
  }
  
  public String getNaturalezaNombre()
  {
    if (this.naturaleza != null) {
      return Naturaleza.getNombre(this.naturaleza.byteValue());
    }
    return "";
  }
  
  public Byte getFrecuencia()
  {
    return this.frecuencia;
  }
  
  public void setFrecuencia(Byte frecuencia)
  {
    this.frecuencia = frecuencia;
  }
  
  public String getFrecuenciaNombre()
  {
    if (this.frecuencia != null) {
      return Frecuencia.getNombre(this.frecuencia.byteValue());
    }
    return "";
  }
  
  public String getComportamiento()
  {
    return this.comportamiento;
  }
  
  public void setComportamiento(String comportamiento)
  {
    this.comportamiento = comportamiento;
  }
  
  public Long getUnidadId()
  {
    return this.unidadId;
  }
  
  public void setUnidadId(Long unidadId)
  {
    this.unidadId = unidadId;
  }
  
  public String getCodigoEnlace()
  {
    return this.codigoEnlace;
  }
  
  public void setCodigoEnlace(String codigoEnlace)
  {
    this.codigoEnlace = codigoEnlace;
  }
  
  public String getNombreCorto()
  {
    return this.nombreCorto;
  }
  
  public void setNombreCorto(String nombreCorto)
  {
    this.nombreCorto = nombreCorto;
  }
  
  public Byte getPrioridad()
  {
    return this.prioridad;
  }
  
  public void setPrioridad(Byte prioridad)
  {
    this.prioridad = prioridad;
  }
  
  public String getPrioridadNombre()
  {
    String resultado = "";
    if (this.prioridad != null) {
      resultado = PrioridadIndicador.getNombre(this.prioridad.byteValue());
    }
    return resultado;
  }
  
  public Boolean getMostrarEnArbol()
  {
    return this.mostrarEnArbol;
  }
  
  public void setMostrarEnArbol(Boolean mostrarEnArbol)
  {
    this.mostrarEnArbol = mostrarEnArbol;
  }
  
  public String getFuente()
  {
    return this.fuente;
  }
  
  public void setFuente(String fuente)
  {
    this.fuente = fuente;
  }
  
  public String getOrden()
  {
    return this.orden;
  }
  
  public void setOrden(String orden)
  {
    this.orden = orden;
  }
  
  public Byte getIndicadorAsociadoTipo()
  {
    return this.indicadorAsociadoTipo;
  }
  
  public void setIndicadorAsociadoTipo(Byte indicadorAsociadoTipo)
  {
    this.indicadorAsociadoTipo = indicadorAsociadoTipo;
  }
  
  public Long getIndicadorAsociadoId()
  {
    return this.indicadorAsociadoId;
  }
  
  public void setIndicadorAsociadoId(Long indicadorAsociadoId)
  {
    this.indicadorAsociadoId = indicadorAsociadoId;
  }
  
  public Byte getIndicadorAsociadoRevision()
  {
    return this.indicadorAsociadoRevision;
  }
  
  public void setIndicadorAsociadoRevision(Byte indicadorAsociadoRevision)
  {
    this.indicadorAsociadoRevision = indicadorAsociadoRevision;
  }
  
  public Boolean getSoloLectura()
  {
    return this.soloLectura;
  }
  
  public void setSoloLectura(Boolean soloLectura)
  {
    this.soloLectura = soloLectura;
  }
  
  public Byte getCaracteristica()
  {
    return this.caracteristica;
  }
  
  public void setCaracteristica(Byte caracteristica)
  {
    this.caracteristica = caracteristica;
  }
  
  public String getCaracteristicaNombre()
  {
    String resultado = "";
    if (this.caracteristica != null) {
      resultado = Caracteristica.getNombre(this.caracteristica.byteValue());
    }
    return resultado;
  }
  
  public Byte getAlerta()
  {
    return this.alerta;
  }
  
  public void setAlerta(Byte alerta)
  {
    this.alerta = alerta;
  }
  
  public String getFechaUltimaMedicion()
  {
    return this.fechaUltimaMedicion;
  }
  
  public void setFechaUltimaMedicion(String fechaUltimaMedicion)
  {
    this.fechaUltimaMedicion = fechaUltimaMedicion;
  }
    
  public String getFechaesperado() {
	return fechaesperado;
  }

  public void setFechaesperado(String fechaesperado) {
	this.fechaesperado = fechaesperado;
  }

  public String getObservacion() {
	return observacion;
  }

  public void setObservacion(String observacion) {
	this.observacion = observacion;
  }
  
   public String getOrganizacionNombre() {
	return organizacionNombre;
   }

   public void setOrganizacionNombre(String organizacionNombre) {
	this.organizacionNombre = organizacionNombre;
   }

public Integer getFechaUltimaMedicionPeriodo()
  {
    Integer periodo = null;
    if (this.fechaUltimaMedicion != null) {
      for (int index = 0; index < this.fechaUltimaMedicion.length(); index++)
      {
        String caracter = this.fechaUltimaMedicion.substring(index, index + 1);
        if ("1234567890".indexOf(caracter) < 0)
        {
          periodo = new Integer(this.fechaUltimaMedicion.substring(0, index));
          break;
        }
      }
    }
    return periodo;
  }
  
  public Integer getFechaUltimaMedicionAno()
  {
    Integer ano = null;
    if (this.fechaUltimaMedicion != null) {
      for (int index = 0; index < this.fechaUltimaMedicion.length(); index++)
      {
        String caracter = this.fechaUltimaMedicion.substring(index, index + 1);
        if ("1234567890".indexOf(caracter) < 0)
        {
          ano = new Integer(this.fechaUltimaMedicion.substring(index + 1));
          break;
        }
      }
    }
    return ano;
  }
  
  public Double getUltimaMedicion()
  {
    return this.ultimaMedicion;
  }
  
  public void setUltimaMedicion(Double ultimaMedicion)
  {
    this.ultimaMedicion = ultimaMedicion;
  }
  
  public String getUltimaMedicionFormateada()
  {
    if ((!this.naturaleza.equals(Naturaleza.getNaturalezaCualitativoNominal())) && (!this.naturaleza.equals(Naturaleza.getNaturalezaCualitativoOrdinal())) && (this.numeroDecimales != null)) {
      return VgcFormatter.formatearNumero(this.ultimaMedicion, this.numeroDecimales.intValue());
    }
    if ((this.ultimaMedicion != null) && ((this.naturaleza.equals(Naturaleza.getNaturalezaCualitativoNominal())) || (this.naturaleza.equals(Naturaleza.getNaturalezaCualitativoOrdinal()))))
    {
      StrategosCategoriasService strategosCategoriasService = StrategosServiceFactory.getInstance().openStrategosCategoriasService();
      CategoriaMedicion categoriaMedicion = (CategoriaMedicion)strategosCategoriasService.load(CategoriaMedicion.class, new Long(this.ultimaMedicion.longValue()));
      strategosCategoriasService.close();
      if (categoriaMedicion != null) {
        return categoriaMedicion.getNombre();
      }
    }
    return VgcFormatter.formatearNumero(this.ultimaMedicion, 2);
  }
  
  public Double getMetaAnual()
  {
    return this.metaAnual;
  }
  
  public void setMetaAnual(Double metaAnual)
  {
    this.metaAnual = metaAnual;
  }
  
  public String getMetaAnualFormateada()
  {
    if (this.numeroDecimales != null) {
      return VgcFormatter.formatearNumero(this.metaAnual, this.numeroDecimales.intValue());
    }
    return VgcFormatter.formatearNumero(this.metaAnual, 2);
  }
  
  public Double getMetaParcial()
  {
    return this.metaParcial;
  }
  
  public void setMetaParcial(Double metaParcial)
  {
    this.metaParcial = metaParcial;
  }
  
  public String getMetaParcialFormateada()
  {
    if (this.numeroDecimales != null) {
      return VgcFormatter.formatearNumero(this.metaParcial, this.numeroDecimales.intValue());
    }
    return VgcFormatter.formatearNumero(this.metaParcial, 2);
  }
  
  public Double getEstadoAnual()
  {
    return this.estadoAnual;
  }
  
  public void setEstadoAnual(Double estadoAnual)
  {
    this.estadoAnual = estadoAnual;
  }
  
  public String getEstadoAnualFormateado()
  {
    if (this.numeroDecimales != null) {
      return VgcFormatter.formatearNumero(this.estadoAnual, this.numeroDecimales.intValue());
    }
    return VgcFormatter.formatearNumero(this.estadoAnual, 2);
  }
  
  public Double getEstadoParcial()
  {
    return this.estadoParcial;
  }
  
  public void setEstadoParcial(Double estadoParcial)
  {
    this.estadoParcial = estadoParcial;
  }
  
  public String getEstadoParcialFormateado()
  {
    if (this.numeroDecimales != null) {
      return VgcFormatter.formatearNumero(this.estadoParcial, this.numeroDecimales.intValue());
    }
    return VgcFormatter.formatearNumero(this.estadoParcial, 2);
  }
  
  public Double getUltimoProgramado()
  {
    return this.ultimoProgramado;
  }
  
  public void setUltimoProgramado(Double ultimoProgramado)
  {
    this.ultimoProgramado = ultimoProgramado;
  }
  
  public Double getUltimoProgramadoInferior()
  {
    return this.ultimoProgramadoInferior;
  }
  
  public void setUltimoProgramadoInferior(Double ultimoProgramadoInferior)
  {
    this.ultimoProgramadoInferior = ultimoProgramadoInferior;
  }
  
  public String getUltimoProgramadoFormateado()
  {
    if (this.numeroDecimales != null) {
      return VgcFormatter.formatearNumero(this.ultimoProgramado, this.numeroDecimales.intValue());
    }
    return VgcFormatter.formatearNumero(this.ultimoProgramado, 2);
  }
  
  public String getPorcentajeCumplimientoParcialFormateado()
  {
    return getPorcentajeCumplimientoFormateado(null, null);
  }
  
  public String getPorcentajeCumplimientoAnualFormateado()
  {
    if (this.ultimaMedicionAnoAnterior == null) {
      return null;
    }
    return getPorcentajeCumplimientoFormateado(this.ultimaMedicionAnoAnterior, null);
  }
  
  public String getPorcentajeCumplimientoFormateado(Double ultimaMedicionAnoAnterior, Byte tipo)
  {
    Double valor = Double.valueOf(0.0D);
    if (this.valorInicialCero.booleanValue()) {
      ultimaMedicionAnoAnterior = Double.valueOf(0.0D);
    } 
    else{
    	if(ultimaMedicionAnoAnterior == null && this.ultimaMedicion !=null && this.ultimoProgramado !=null && this.ultimaMedicion == 100 && this.ultimoProgramado == 100){
    		ultimaMedicionAnoAnterior = Double.valueOf(0.0D);
    	}
    }
    
    if (getCaracteristica() == null) {
      return null;
    }
    if (getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoAumento().byteValue())
    {
      if (this.parametroInferiorValorFijo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorRetoAumento(ultimaMedicionAnoAnterior, this.ultimaMedicion, this.ultimoProgramado, this.parametroInferiorValorFijo, tipo == null ? TipoIndicadorEstado.getTipoIndicadorEstadoAnual() : ultimaMedicionAnoAnterior == null ? TipoIndicadorEstado.getTipoIndicadorEstadoParcial() : tipo, null, null, null, null);
      }
      else if (this.parametroInferiorIndicadorValorPeriodo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorRetoAumento(ultimaMedicionAnoAnterior, this.ultimaMedicion, this.ultimoProgramado, this.parametroInferiorIndicadorValorPeriodo, tipo == null ? TipoIndicadorEstado.getTipoIndicadorEstadoAnual() : ultimaMedicionAnoAnterior == null ? TipoIndicadorEstado.getTipoIndicadorEstadoParcial() : tipo, null, null, null, null);
      }
      else
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorRetoAumento(ultimaMedicionAnoAnterior, this.ultimaMedicion, this.ultimoProgramado, null, tipo == null ? TipoIndicadorEstado.getTipoIndicadorEstadoAnual() : ultimaMedicionAnoAnterior == null ? TipoIndicadorEstado.getTipoIndicadorEstadoParcial() : tipo, null, null, null, null);
      }
    }
    else if (getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoDisminucion().byteValue())
    {
      if (this.parametroSuperiorValorFijo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorRetoDisminucion(ultimaMedicionAnoAnterior, this.ultimaMedicion, this.ultimoProgramado, this.parametroSuperiorValorFijo, null, null, null, null);
      }
      else if (this.parametroSuperiorIndicadorValorPeriodo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorRetoDisminucion(ultimaMedicionAnoAnterior, this.ultimaMedicion, this.ultimoProgramado, this.parametroSuperiorIndicadorValorPeriodo, null, null, null, null);
      }
      else
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorRetoDisminucion(ultimaMedicionAnoAnterior, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null, null);
      }
    }
    else if (getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue())
    {
      if (this.parametroSuperiorValorFijo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionValorMaximo(this.parametroSuperiorValorFijo, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null);
      }
      else if (this.parametroSuperiorIndicadorValorPeriodo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionValorMaximo(this.parametroSuperiorIndicadorValorPeriodo, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null);
      }
      else
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionValorMaximo(null, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null);
      }
    }
    else if (getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue())
    {
      if (this.parametroInferiorValorFijo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionValorMinimo(this.parametroInferiorValorFijo, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null);
      }
      else if (this.parametroInferiorIndicadorValorPeriodo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionValorMinimo(this.parametroInferiorIndicadorValorPeriodo, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null);
      }
      else
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionValorMinimo(null, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null);
      }
    }
    else if (getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue()) {
      if ((this.parametroSuperiorValorFijo != null) && (this.parametroInferiorValorFijo != null))
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionDeBandas(this.parametroSuperiorValorFijo, this.parametroInferiorValorFijo, null, null, this.ultimaMedicion);
      }
      else if ((this.parametroSuperiorIndicadorValorPeriodo != null) && (this.parametroInferiorIndicadorValorPeriodo != null))
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionDeBandas(this.parametroSuperiorIndicadorValorPeriodo, this.parametroInferiorIndicadorValorPeriodo, null, null, this.ultimaMedicion);
      }
      else
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionDeBandas(null, null, this.ultimoProgramado, this.ultimoProgramadoInferior, this.ultimaMedicion);
      }
    }
    if (valor == null) {
      return null;
    }
    if (this.numeroDecimales != null) {
      return VgcFormatter.formatearNumero(valor, this.numeroDecimales.intValue());
    }
    return VgcFormatter.formatearNumero(valor, 2);
  }
  
  public Double getPorcentajeCumplimiento(Double ultimaMedicionAnoAnterior, Byte tipo)
  {
    Double valor = Double.valueOf(0.0D);
    if (this.valorInicialCero.booleanValue()) {
      ultimaMedicionAnoAnterior = Double.valueOf(0.0D);
    }
    if (getCaracteristica() == null) {
      return null;
    }
    if (getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoAumento().byteValue())
    {
      if (this.parametroInferiorValorFijo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorRetoAumento(ultimaMedicionAnoAnterior, this.ultimaMedicion, this.ultimoProgramado, this.parametroInferiorValorFijo, tipo == null ? TipoIndicadorEstado.getTipoIndicadorEstadoAnual() : ultimaMedicionAnoAnterior == null ? TipoIndicadorEstado.getTipoIndicadorEstadoParcial() : tipo, null, null, null, null);
      }
      else if (this.parametroInferiorIndicadorValorPeriodo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorRetoAumento(ultimaMedicionAnoAnterior, this.ultimaMedicion, this.ultimoProgramado, this.parametroInferiorIndicadorValorPeriodo, tipo == null ? TipoIndicadorEstado.getTipoIndicadorEstadoAnual() : ultimaMedicionAnoAnterior == null ? TipoIndicadorEstado.getTipoIndicadorEstadoParcial() : tipo, null, null, null, null);
      }
      else
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorRetoAumento(ultimaMedicionAnoAnterior, this.ultimaMedicion, this.ultimoProgramado, null, tipo == null ? TipoIndicadorEstado.getTipoIndicadorEstadoAnual() : ultimaMedicionAnoAnterior == null ? TipoIndicadorEstado.getTipoIndicadorEstadoParcial() : tipo, null, null, null, null);
      }
    }
    else if (getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoDisminucion().byteValue())
    {
      if (this.parametroSuperiorValorFijo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorRetoDisminucion(ultimaMedicionAnoAnterior, this.ultimaMedicion, this.ultimoProgramado, this.parametroSuperiorValorFijo, null, null, null, null);
      }
      else if (this.parametroSuperiorIndicadorValorPeriodo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorRetoDisminucion(ultimaMedicionAnoAnterior, this.ultimaMedicion, this.ultimoProgramado, this.parametroSuperiorIndicadorValorPeriodo, null, null, null, null);
      }
      else
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorRetoDisminucion(ultimaMedicionAnoAnterior, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null, null);
      }
    }
    else if (getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue())
    {
      if (this.parametroSuperiorValorFijo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionValorMaximo(this.parametroSuperiorValorFijo, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null);
      }
      else if (this.parametroSuperiorIndicadorValorPeriodo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionValorMaximo(this.parametroSuperiorIndicadorValorPeriodo, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null);
      }
      else
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionValorMaximo(null, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null);
      }
    }
    else if (getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue())
    {
      if (this.parametroInferiorValorFijo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionValorMinimo(this.parametroInferiorValorFijo, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null);
      }
      else if (this.parametroInferiorIndicadorValorPeriodo != null)
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionValorMinimo(this.parametroInferiorIndicadorValorPeriodo, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null);
      }
      else
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionValorMinimo(null, this.ultimaMedicion, this.ultimoProgramado, null, null, null, null);
      }
    }
    else if (getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue()) {
      if ((this.parametroSuperiorValorFijo != null) && (this.parametroInferiorValorFijo != null))
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionDeBandas(this.parametroSuperiorValorFijo, this.parametroInferiorValorFijo, null, null, this.ultimaMedicion);
      }
      else if ((this.parametroSuperiorIndicadorValorPeriodo != null) && (this.parametroInferiorIndicadorValorPeriodo != null))
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionDeBandas(this.parametroSuperiorIndicadorValorPeriodo, this.parametroInferiorIndicadorValorPeriodo, null, null, this.ultimaMedicion);
      }
      else
      {
        new CalculoUtil();valor = CalculoUtil.calcularPorcentajeLogroIndicadorCondicionDeBandas(null, null, this.ultimoProgramado, this.ultimoProgramadoInferior, this.ultimaMedicion);
      }
    }
    if (valor == null) {
      return null;
    }
    return valor;
  }
  
  public Byte getTipoFuncion()
  {
    return this.tipoFuncion;
  }
  
  public void setTipoFuncion(Byte tipoFuncion)
  {
    this.tipoFuncion = tipoFuncion;
  }
  
  public String getTipoFuncionNombre()
  {
    String resultado = "";
    if (this.tipoFuncion != null) {
      resultado = TipoFuncionIndicador.getNombre(this.tipoFuncion.byteValue());
    }
    return resultado;
  }
  
  public Long getResponsableFijarMetaId()
  {
    return this.responsableFijarMetaId;
  }
  
  public void setResponsableFijarMetaId(Long responsableFijarMetaId)
  {
    this.responsableFijarMetaId = responsableFijarMetaId;
  }
  
  public Long getResponsableLograrMetaId()
  {
    return this.responsableLograrMetaId;
  }
  
  public void setResponsableLograrMetaId(Long responsableLograrMetaId)
  {
    this.responsableLograrMetaId = responsableLograrMetaId;
  }
  
  public Long getResponsableSeguimientoId()
  {
    return this.responsableSeguimientoId;
  }
  
  public void setResponsableSeguimientoId(Long responsableSeguimientoId)
  {
    this.responsableSeguimientoId = responsableSeguimientoId;
  }
  
  public Long getResponsableCargarMetaId()
  {
    return this.responsableCargarMetaId;
  }
  
  public void setResponsableCargarMetaId(Long responsableCargarMetaId)
  {
    this.responsableCargarMetaId = responsableCargarMetaId;
  }
  
  public Long getResponsableCargarEjecutadoId()
  {
    return this.responsableCargarEjecutadoId;
  }
  
  public void setResponsableCargarEjecutadoId(Long responsableCargarEjecutadoId)
  {
    this.responsableCargarEjecutadoId = responsableCargarEjecutadoId;
  }
  
  public Responsable getResponsableFijarMeta()
  {
    return this.responsableFijarMeta;
  }
  
  public void setResponsableFijarMeta(Responsable responsableFijarMeta)
  {
    this.responsableFijarMeta = responsableFijarMeta;
  }
  
  public Responsable getResponsableLograrMeta()
  {
    return this.responsableLograrMeta;
  }
  
  public void setResponsableLograrMeta(Responsable responsableLograrMeta)
  {
    this.responsableLograrMeta = responsableLograrMeta;
  }
  
  public Responsable getResponsableSeguimiento()
  {
    return this.responsableSeguimiento;
  }
  
  public void setResponsableSeguimiento(Responsable responsableSeguimiento)
  {
    this.responsableSeguimiento = responsableSeguimiento;
  }
  
  public Responsable getResponsableCargarMeta()
  {
    return this.responsableCargarMeta;
  }
  
  public void setResponsableCargarMeta(Responsable responsableCargarMeta)
  {
    this.responsableCargarMeta = responsableCargarMeta;
  }
  
  public Responsable getResponsableCargarEjecutado()
  {
    return this.responsableCargarEjecutado;
  }
  
  public void setResponsableCargarEjecutado(Responsable responsableCargarEjecutado)
  {
    this.responsableCargarEjecutado = responsableCargarEjecutado;
  }
  
  public Boolean getGuia()
  {
    return this.guia;
  }
  
  public void setGuia(Boolean guia)
  {
    this.guia = guia;
  }
  
  public String getGuiaNombre()
  {
    String resultado = TipoIndicador.getNombre(TipoIndicador.getTipoIndicadorResultado().byteValue());
    if ((this.guia != null) && (this.guia.booleanValue())) {
      resultado = TipoIndicador.getNombre(TipoIndicador.getTipoIndicadorGuia().byteValue());
    }
    return resultado;
  }
  
  public Byte getCorte()
  {
    return this.corte;
  }
  
  public void setCorte(Byte corte)
  {
    this.corte = corte;
  }
  
  public String getCorteNombre()
  {
    String resultado = "";
    if (this.corte != null)
    {
      resultado = TipoCorte.getNombre(this.corte.byteValue());
      if (this.tipoCargaMedicion != null) {
        resultado = resultado + " " + TipoMedicion.getNombre(this.tipoCargaMedicion.byteValue());
      }
    }
    return resultado;
  }
  
  public String getEnlaceParcial()
  {
    return this.enlaceParcial;
  }
  
  public void setEnlaceParcial(String enlaceParcial)
  {
    this.enlaceParcial = enlaceParcial;
  }
  
  public Integer getAlertaMinMax()
  {
    return this.alertaMinMax;
  }
  
  public void setAlertaMinMax(Integer alertaMinMax)
  {
    this.alertaMinMax = alertaMinMax;
  }
  
  public Double getAlertaMetaZonaVerde()
  {
    return this.alertaMetaZonaVerde;
  }
  
  public void setAlertaMetaZonaVerde(Double alertaMetaZonaVerde)
  {
    this.alertaMetaZonaVerde = alertaMetaZonaVerde;
  }
  
  public Double getAlertaMetaZonaAmarilla()
  {
    return this.alertaMetaZonaAmarilla;
  }
  
  public void setAlertaMetaZonaAmarilla(Double alertaMetaZonaAmarilla)
  {
    this.alertaMetaZonaAmarilla = alertaMetaZonaAmarilla;
  }
  
  public Byte getAlertaTipoZonaVerde()
  {
    return this.alertaTipoZonaVerde;
  }
  
  public void setAlertaTipoZonaVerde(Byte alertaTipoZonaVerde)
  {
    this.alertaTipoZonaVerde = alertaTipoZonaVerde;
  }
  
  public Byte getAlertaTipoZonaAmarilla()
  {
    return this.alertaTipoZonaAmarilla;
  }
  
  public void setAlertaTipoZonaAmarilla(Byte alertaTipoZonaAmarilla)
  {
    this.alertaTipoZonaAmarilla = alertaTipoZonaAmarilla;
  }
  
  public Boolean getAlertaValorVariableZonaVerde()
  {
    return this.alertaValorVariableZonaVerde;
  }
  
  public void setAlertaValorVariableZonaVerde(Boolean alertaValorVariableZonaVerde)
  {
    this.alertaValorVariableZonaVerde = alertaValorVariableZonaVerde;
  }
  
  public Boolean getAlertaValorVariableZonaAmarilla()
  {
    return this.alertaValorVariableZonaAmarilla;
  }
  
  public void setAlertaValorVariableZonaAmarilla(Boolean alertaValorVariableZonaAmarilla)
  {
    this.alertaValorVariableZonaAmarilla = alertaValorVariableZonaAmarilla;
  }
  
  public Long getAlertaIndicadorIdZonaVerde()
  {
    return this.alertaIndicadorIdZonaVerde;
  }
  
  public void setAlertaIndicadorIdZonaVerde(Long alertaIndicadorIdZonaVerde)
  {
    this.alertaIndicadorIdZonaVerde = alertaIndicadorIdZonaVerde;
  }
  
  public Long getAlertaIndicadorIdZonaAmarilla()
  {
    return this.alertaIndicadorIdZonaAmarilla;
  }
  
  public void setAlertaIndicadorIdZonaAmarilla(Long alertaIndicadorIdZonaAmarilla)
  {
    this.alertaIndicadorIdZonaAmarilla = alertaIndicadorIdZonaAmarilla;
  }
  
  public Boolean getValorInicialCero()
  {
    return this.valorInicialCero;
  }
  
  public void setValorInicialCero(Boolean valorInicialCero)
  {
    this.valorInicialCero = valorInicialCero;
  }
  
  public Byte getNumeroDecimales()
  {
    if (this.numeroDecimales == null) {
      this.numeroDecimales = new Byte((byte)2);
    }
    return this.numeroDecimales;
  }
  
  public void setNumeroDecimales(Byte numeroDecimales)
  {
    this.numeroDecimales = numeroDecimales;
  }
  
  public Byte getTipoCargaMedicion()
  {
    return this.tipoCargaMedicion;
  }
  
  public void setTipoCargaMedicion(Byte tipoCargaMedicion)
  {
    this.tipoCargaMedicion = tipoCargaMedicion;
  }
  
  public Byte getTipoSumaMedicion()
  {
    return this.tipoSumaMedicion;
  }
  
  public void setTipoSumaMedicion(Byte tipoSumaMedicion)
  {
    this.tipoSumaMedicion = tipoSumaMedicion;
  }
  
  public Byte getTipoCargaMeta()
  {
    if (this.tipoCargaMeta == null) {
      this.tipoCargaMeta = this.tipoCargaMedicion;
    }
    return this.tipoCargaMeta;
  }
  
  public void setTipoCargaMeta(Byte tipoCargaMeta)
  {
    this.tipoCargaMeta = tipoCargaMeta;
  }
  
  public Long getParametroSuperiorIndicadorId()
  {
    return this.parametroSuperiorIndicadorId;
  }
  
  public void setParametroSuperiorIndicadorId(Long parametroSuperiorIndicadorId)
  {
    this.parametroSuperiorIndicadorId = parametroSuperiorIndicadorId;
  }
  
  public Long getParametroInferiorIndicadorId()
  {
    return this.parametroInferiorIndicadorId;
  }
  
  public void setParametroInferiorIndicadorId(Long parametroInferiorIndicadorId)
  {
    this.parametroInferiorIndicadorId = parametroInferiorIndicadorId;
  }
  
  public Double getParametroSuperiorIndicadorValorPeriodo()
  {
    return this.parametroSuperiorIndicadorValorPeriodo;
  }
  
  public void setParametroSuperiorIndicadorValorPeriodo(Double parametroSuperiorIndicadorValorPeriodo)
  {
    this.parametroSuperiorIndicadorValorPeriodo = parametroSuperiorIndicadorValorPeriodo;
  }
  
  public Double getParametroInferiorIndicadorValorPeriodo()
  {
    return this.parametroInferiorIndicadorValorPeriodo;
  }
  
  public void setParametroInferiorIndicadorValorPeriodo(Double parametroInferiorIndicadorValorPeriodo)
  {
    this.parametroInferiorIndicadorValorPeriodo = parametroInferiorIndicadorValorPeriodo;
  }
  
  public Double getParametroSuperiorValorFijo()
  {
    return this.parametroSuperiorValorFijo;
  }
  
  public void setParametroSuperiorValorFijo(Double parametroSuperiorValorFijo)
  {
    this.parametroSuperiorValorFijo = parametroSuperiorValorFijo;
  }
  
  public Double getParametroInferiorValorFijo()
  {
    return this.parametroInferiorValorFijo;
  }
  
  public void setParametroInferiorValorFijo(Double parametroInferiorValorFijo)
  {
    this.parametroInferiorValorFijo = parametroInferiorValorFijo;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public Date getFechaBloqueoEjecutado()
  {
    return this.fechaBloqueoEjecutado;
  }
  
  public void setFechaBloqueoEjecutado(Date fechaBloqueoEjecutado)
  {
    this.fechaBloqueoEjecutado = fechaBloqueoEjecutado;
  }
  
  public Date getFechaBloqueoMeta()
  {
    return this.fechaBloqueoMeta;
  }
  
  public void setFechaBloqueoMeta(Date fechaBloqueoMeta)
  {
    this.fechaBloqueoMeta = fechaBloqueoMeta;
  }
  
  public Boolean getMultidimensional()
  {
    return this.multidimensional;
  }
  
  public void setMultidimensional(Boolean multidimensional)
  {
    this.multidimensional = multidimensional;
  }
  
  public OrganizacionStrategos getOrganizacion()
  {
    return this.organizacion;
  }
  
  public void setOrganizacion(OrganizacionStrategos organizacion)
  {
    this.organizacion = organizacion;
  }
  
  public Set getSeriesIndicador()
  {
    return this.seriesIndicador;
  }
  
  public void setSeriesIndicador(Set seriesIndicador)
  {
    this.seriesIndicador = seriesIndicador;
  }
  
  public Set getFormulas()
  {
    return this.formulas;
  }
  
  public void setFormulas(Set formulas)
  {
    this.formulas = formulas;
  }
  
  public ClaseIndicadores getClase()
  {
    return this.clase;
  }
  
  public void setClase(ClaseIndicadores clase)
  {
    this.clase = clase;
  }
  
  public List getEscalaCualitativa()
  {
    return this.escalaCualitativa;
  }
  
  public void setEscalaCualitativa(List escalaCualitativa)
  {
    this.escalaCualitativa = escalaCualitativa;
  }
  
  public UnidadMedida getUnidad()
  {
    return this.unidad;
  }
  
  public void setUnidad(UnidadMedida unidad)
  {
    this.unidad = unidad;
  }
  
  public InsumoFormula getInsumoFormula()
  {
    return this.insumoFormula;
  }
  
  public void setInsumoFormula(InsumoFormula insumoFormula)
  {
    this.insumoFormula = insumoFormula;
  }
  
  public Boolean getNodoFinal()
  {
    return this.nodoFinal;
  }
  
  public void setNodoFinal(Boolean nodoFinal)
  {
    this.nodoFinal = nodoFinal;
  }
  
  public String getFormulaSerieReal()
  {
    return this.formulaSerieReal;
  }
  
  public void setFormulaSerieReal(String formulaSerieReal)
  {
    this.formulaSerieReal = formulaSerieReal;
  }
  
  public Integer getIndex()
  {
    return this.index;
  }
  
  public void setIndex(Integer index)
  {
    this.index = index;
  }
  
  public Long getPerspectivaId()
  {
    return this.perspectivaId;
  }
  
  public void setPerspectivaId(Long perspectivaId)
  {
    this.perspectivaId = perspectivaId;
  }
  
  public Long getPlanId()
  {
    return this.planId;
  }
  
  public void setPlanId(Long planId)
  {
    this.planId = planId;
  }
  
  public Boolean getEstaBloqueado()
  {
    return this.estaBloqueado;
  }
  
  public void setEstaBloqueado(Boolean estaBloqueado)
  {
    this.estaBloqueado = estaBloqueado;
  }
  
  public Double getPeso()
  {
    return this.peso;
  }
  
  public String getPesoFormateado()
  {
    return VgcFormatter.formatearNumero(this.peso, 2);
  }
  
  public void setPeso(Double peso)
  {
    this.peso = peso;
  }
  
  public boolean isAlertaNoDefinible()
  {
    return this.alerta == AlertaIndicador.getAlertaNoDefinible();
  }
  
  public boolean isAlertaVerde()
  {
    if (this.alerta == AlertaIndicador.getAlertaNoDefinible()) {
      return false;
    }
    return this.alerta.equals(AlertaIndicador.getAlertaVerde());
  }
  
  public boolean isAlertaAmarilla()
  {
    if (this.alerta == AlertaIndicador.getAlertaNoDefinible()) {
      return false;
    }
    return this.alerta.equals(AlertaIndicador.getAlertaAmarilla());
  }
  
  public boolean isAlertaRoja()
  {
    if (this.alerta == AlertaIndicador.getAlertaNoDefinible()) {
      return false;
    }
    return this.alerta.equals(AlertaIndicador.getAlertaRoja());
  }
  
  public boolean isAlertaInalterada()
  {
    if (this.alerta == AlertaIndicador.getAlertaNoDefinible()) {
      return false;
    }
    return this.alerta.equals(AlertaIndicador.getAlertaInalterada());
  }
  
  public boolean isAlertaAzul()
  {
    if (this.alerta == AlertaIndicador.getAlertaNoDefinible()) {
      return false;
    }
    return this.alerta.equals(AlertaIndicador.getAlertaAzul());
  }
  
  public boolean isAlertaNegra()
  {
    if (this.alerta == AlertaIndicador.getAlertaNoDefinible()) {
      return false;
    }
    return this.alerta.equals(AlertaIndicador.getAlertaNegra());
  }
  
  public Byte getMesCierre()
  {
    byte mesCierre = 12;
    if (getOrganizacion() != null) {
      mesCierre = getOrganizacion().getMesCierre().byteValue();
    }
    return new Byte(mesCierre);
  }
  
  public Byte getMesInicio()
  {
    Byte mesCierre = getMesCierre();
    int mesInicio = 1;
    if (mesCierre.byteValue() < 12) {
      mesInicio = mesCierre.byteValue() + 1;
    }
    return new Byte(Integer.toString(mesInicio));
  }
  
  public Double getUltimaMedicionAnoAnterior()
  {
    return this.ultimaMedicionAnoAnterior;
  }
  
  public void setUltimaMedicionAnoAnterior(Double ultimaMedicionAnoAnterior)
  {
    this.ultimaMedicionAnoAnterior = ultimaMedicionAnoAnterior;
  }
  
  public Boolean getAsignarInventario()
  {
    return this.asignarInventario;
  }
  
  public void setAsignarInventario(Boolean asignarInventario)
  {
    this.asignarInventario = asignarInventario;
  }
  
  public String getFechaInicial()
  {
    return this.fechaInicial;
  }
  
  public void setFechaInicial(String fechaInicial)
  {
    this.fechaInicial = fechaInicial;
  }
  
  public String getFechaFinal()
  {
    return this.fechaFinal;
  }
  
  public void setFechaFinal(String fechaFinal)
  {
    this.fechaFinal = fechaFinal;
  }
  
  public Boolean getIsPocentaje()
  {
    return this.isPocentaje;
  }
  
  public void setIsPocentaje(Boolean isPocentaje)
  {
    this.isPocentaje = isPocentaje;
  }
  
  public Long getActividadId()
  {
    return this.actividadId;
  }
  
  public void setActividadId(Long actividadId)
  {
    this.actividadId = actividadId;
  }
  
  
  
  public Long getResponsableNotificacionId() {
	return responsableNotificacionId;
  }

  public void setResponsableNotificacionId(Long responsableNotificacionId) {
	this.responsableNotificacionId = responsableNotificacionId;
  }

  public Responsable getResponsableNotificacion() {
	return responsableNotificacion;
  }

  public void setResponsableNotificacion(Responsable responsableNotificacion) {
	this.responsableNotificacion = responsableNotificacion;
  }

public int compareTo(Object o)
  {
    Indicador or = (Indicador)o;
    return getIndicadorId().compareTo(or.getIndicadorId());
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("indicadorId", getIndicadorId()).toString();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Indicador other = (Indicador)obj;
    if (this.indicadorId == null)
    {
      if (other.indicadorId != null) {
        return false;
      }
    }
    else if (!this.indicadorId.equals(other.indicadorId)) {
      return false;
    }
    return true;
  }
  
  public com.visiongc.servicio.strategos.indicadores.model.Indicador convertService()
  {
    return new com.visiongc.servicio.strategos.indicadores.model.Indicador(this.indicadorId, this.nombre, this.codigoEnlace, this.frecuencia, this.corte, this.organizacion.getMesCierre(), this.tipoCargaMedicion, 
      this.valorInicialCero, this.caracteristica, this.naturaleza, this.tipoSumaMedicion, this.organizacionId, this.claseId, this.alertaIndicadorIdZonaVerde, 
      this.alertaIndicadorIdZonaAmarilla, this.alertaMinMax, this.alertaMetaZonaVerde, this.alertaMetaZonaAmarilla, this.alertaTipoZonaVerde, 
      this.alertaTipoZonaAmarilla, this.alertaValorVariableZonaVerde, this.alertaValorVariableZonaAmarilla, this.parametroSuperiorIndicadorId, 
      this.parametroInferiorIndicadorId, this.parametroSuperiorValorFijo, this.parametroInferiorValorFijo, this.tipoFuncion, this.asignarInventario, this.unidadId);
  }
}
