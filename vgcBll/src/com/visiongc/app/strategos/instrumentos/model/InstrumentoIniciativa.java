package com.visiongc.app.strategos.instrumentos.model;

import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class InstrumentoIniciativa
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private InstrumentoIniciativaPK pk;
  private Iniciativa iniciativa;
  private Instrumentos instrumento;
  private List<Medicion> mediciones;
  
  
  public InstrumentoIniciativa(InstrumentoIniciativaPK pk)
  {
    this.pk = pk;
  }
  

  public InstrumentoIniciativa() {}
  

  public InstrumentoIniciativaPK getPk()
  {
    return pk;
  }
  
  public void setPk(InstrumentoIniciativaPK pk)
  {
    this.pk = pk;
  }
  
  public Iniciativa getIniciativa()
  {
    return iniciativa;
  }
  
  public void setIniciativa(Iniciativa iniciativa)
  {
    this.iniciativa = iniciativa;
  }
    
  public Instrumentos getInstrumento() {
	return instrumento;
  }

  public void setInstrumento(Instrumentos instrumento) {
	this.instrumento = instrumento;
  }

  public List<Medicion> getMediciones()
  {
    return mediciones;
  }
  
  public void setMediciones(List<Medicion> mediciones)
  {
    this.mediciones = mediciones;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof InstrumentoIniciativa))
      return false;
    InstrumentoIniciativa castOther = (InstrumentoIniciativa)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
}
