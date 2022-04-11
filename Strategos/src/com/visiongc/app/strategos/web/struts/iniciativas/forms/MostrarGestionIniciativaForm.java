package com.visiongc.app.strategos.web.struts.iniciativas.forms;

import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.web.struts.forms.VisorListaForm;
import java.util.Date;
import java.util.List;

public class MostrarGestionIniciativaForm extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  private String nombrePlan;
  private Date comienzoProgramado;
  private Date finProgramado;
  private Double duracionProgramado;
  private Date comienzoReal;
  private Date finReal;
  private Double duracionReal;
  private Byte completado;
  private Byte tipoAlerta;
  private List indicadores;

  public String getNombrePlan()
  {
    return this.nombrePlan;
  }

  public void setNombrePlan(String nombrePlan) {
    this.nombrePlan = nombrePlan;
  }

  public Date getComienzoProgramado() {
    return this.comienzoProgramado;
  }

  public String getComienzoProgramadoFormateada() {
    return VgcFormatter.formatearFecha(this.comienzoProgramado, "formato.fecha.corta");
  }

  public void setComienzoProgramado(Date comienzoProgramado) {
    this.comienzoProgramado = comienzoProgramado;
  }

  public Date getFinProgramado() {
    return this.finProgramado;
  }

  public String getFinProgramadoFormateada() {
    return VgcFormatter.formatearFecha(this.finProgramado, "formato.fecha.corta");
  }

  public void setFinProgramado(Date finProgramado) {
    this.finProgramado = finProgramado;
  }

  public Double getDuracionProgramado() {
    return this.duracionProgramado;
  }

  public void setDuracionProgramado(Double duracionProgramado) {
    this.duracionProgramado = duracionProgramado;
  }

  public Date getComienzoReal() {
    return this.comienzoReal;
  }

  public String getComienzoRealFormateada() {
    return VgcFormatter.formatearFecha(this.comienzoReal, "formato.fecha.corta");
  }

  public void setComienzoReal(Date comienzoReal) {
    this.comienzoReal = comienzoReal;
  }

  public Date getFinReal() {
    return this.finReal;
  }

  public String getFinRealFormateada() {
    return VgcFormatter.formatearFecha(this.finReal, "formato.fecha.corta");
  }

  public void setFinReal(Date finReal) {
    this.finReal = finReal;
  }

  public Double getDuracionReal() {
    return this.duracionReal;
  }

  public void setDuracionReal(Double duracionReal) {
    this.duracionReal = duracionReal;
  }

  public Byte getCompletado() {
    return this.completado;
  }

  public void setCompletado(Byte completado) {
    this.completado = completado;
  }

  public Byte getTipoAlerta() {
    return this.tipoAlerta;
  }

  public void setTipoAlerta(Byte tipoAlerta) {
    this.tipoAlerta = tipoAlerta;
  }

  public List getIndicadores() {
    return this.indicadores;
  }

  public void setIndicadores(List indicadores) {
    this.indicadores = indicadores;
  }

  public void clear() {
    this.nombrePlan = null;
    this.comienzoProgramado = null;
    this.finProgramado = null;
    this.duracionProgramado = null;
    this.comienzoReal = null;
    this.finReal = null;
    this.duracionReal = null;
    this.completado = null;
    this.tipoAlerta = null;
    this.indicadores = null;
  }
}