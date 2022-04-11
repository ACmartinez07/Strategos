package com.visiongc.app.strategos.web.struts.plancuentas.forms;

import com.visiongc.app.strategos.plancuentas.model.GrupoMascaraCodigoPlanCuentas;
import com.visiongc.framework.web.struts.forms.VisorArbolForm;
import java.util.List;

public class GestionarCuentasForm extends VisorArbolForm
{
  static final long serialVersionUID = 0L;
  private List maximoNivelGrupoCuenta;
  private Integer nivelSeleccionadoArbol;
  private GrupoMascaraCodigoPlanCuentas grupoMascaraCodigoPlanCuentasNuevo;
  private GrupoMascaraCodigoPlanCuentas grupoMascaraCodigoPlanCuentasModificar;

  public List getMaximoNivelGrupoCuenta()
  {
    return this.maximoNivelGrupoCuenta;
  }

  public void setMaximoNivelGrupoCuenta(List maximoNivelGrupoCuenta) {
    this.maximoNivelGrupoCuenta = maximoNivelGrupoCuenta;
  }

  public Integer getNivelSeleccionadoArbol() {
    return this.nivelSeleccionadoArbol;
  }

  public void setNivelSeleccionadoArbol(Integer nivelSeleccionadoArbol) {
    this.nivelSeleccionadoArbol = nivelSeleccionadoArbol;
  }

  public GrupoMascaraCodigoPlanCuentas getGrupoMascaraCodigoPlanCuentasNuevo() {
    return this.grupoMascaraCodigoPlanCuentasNuevo;
  }

  public void setGrupoMascaraCodigoPlanCuentasNuevo(GrupoMascaraCodigoPlanCuentas grupoMascaraCodigoPlanCuentasNuevo) {
    this.grupoMascaraCodigoPlanCuentasNuevo = grupoMascaraCodigoPlanCuentasNuevo;
  }

  public GrupoMascaraCodigoPlanCuentas getGrupoMascaraCodigoPlanCuentasModificar() {
    return this.grupoMascaraCodigoPlanCuentasModificar;
  }

  public void setGrupoMascaraCodigoPlanCuentasModificar(GrupoMascaraCodigoPlanCuentas grupoMascaraCodigoPlanCuentasModificar) {
    this.grupoMascaraCodigoPlanCuentasModificar = grupoMascaraCodigoPlanCuentasModificar;
  }
}