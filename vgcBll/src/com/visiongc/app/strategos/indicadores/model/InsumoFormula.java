package com.visiongc.app.strategos.indicadores.model;

import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class InsumoFormula
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private InsumoFormulaPK pk;
  private Indicador padre;
  private Indicador insumo;
  private SerieTiempo serieTiempo;
  private SerieTiempo insumoSerieTiempo;
  private Formula formula;
  private int macro;
  private String valor;
  
  public InsumoFormula(InsumoFormulaPK pk, Indicador padre, Indicador insumo, SerieTiempo serieTiempo, SerieTiempo insumoSerieTiempo, Formula formula)
  {
    this.pk = pk;
    this.padre = padre;
    this.insumo = insumo;
    this.serieTiempo = serieTiempo;
    this.insumoSerieTiempo = insumoSerieTiempo;
    this.formula = formula;
  }
  

  public InsumoFormula() {}
  

  public InsumoFormula(InsumoFormulaPK pk)
  {
    this.pk = pk;
  }
  
  public InsumoFormulaPK getPk()
  {
    return pk;
  }
  
  public void setPk(InsumoFormulaPK pk)
  {
    this.pk = pk;
  }
  
  public Indicador getPadre()
  {
    return padre;
  }
  
  public void setPadre(Indicador padre)
  {
    this.padre = padre;
  }
  
  public Indicador getInsumo()
  {
    return insumo;
  }
  
  public void setInsumo(Indicador insumo)
  {
    this.insumo = insumo;
  }
  
  public SerieTiempo getSerieTiempo()
  {
    return serieTiempo;
  }
  
  public void setSerieTiempo(SerieTiempo serieTiempo)
  {
    this.serieTiempo = serieTiempo;
  }
  
  public SerieTiempo getInsumoSerieTiempo()
  {
    return insumoSerieTiempo;
  }
  
  public void setInsumoSerieTiempo(SerieTiempo insumoSerieTiempo)
  {
    serieTiempo = insumoSerieTiempo;
  }
  
  public Formula getFormula()
  {
    return formula;
  }
  
  public void setFormula(Formula formula)
  {
    this.formula = formula;
  }
  
  public int getMacro()
  {
    return macro;
  }
  
  public void setMacro(int macro)
  {
    this.macro = macro;
  }
  
  public String getValor()
  {
    return valor;
  }
  
  public void setValor(String valor)
  {
    this.valor = valor;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
}
