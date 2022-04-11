package com.visiongc.app.strategos.responsables.model.util;


public class ConfiguracionResponsable
{
  private Boolean enviarResponsableFijarMeta;
  
  private Boolean enviarResponsableLograrMeta;
  
  private Boolean enviarResponsableSeguimiento;
  
  private Boolean enviarResponsableCargarMeta;
  
  private Boolean enviarResponsableCargarEjecutado;
  private Byte tipoCorreoDefaultSent;
  
  public ConfiguracionResponsable() {}
  
  public Boolean getEnviarResponsableFijarMeta()
  {
    return enviarResponsableFijarMeta;
  }
  
  public void setEnviarResponsableFijarMeta(Boolean enviarResponsableFijarMeta)
  {
    this.enviarResponsableFijarMeta = enviarResponsableFijarMeta;
  }
  
  public Boolean getEnviarResponsableLograrMeta()
  {
    return enviarResponsableLograrMeta;
  }
  
  public void setEnviarResponsableLograrMeta(Boolean enviarResponsableLograrMeta)
  {
    this.enviarResponsableLograrMeta = enviarResponsableLograrMeta;
  }
  
  public Boolean getEnviarResponsableSeguimiento()
  {
    return enviarResponsableSeguimiento;
  }
  
  public void setEnviarResponsableSeguimiento(Boolean enviarResponsableSeguimiento)
  {
    this.enviarResponsableSeguimiento = enviarResponsableSeguimiento;
  }
  
  public Boolean getEnviarResponsableCargarMeta()
  {
    return enviarResponsableCargarMeta;
  }
  
  public void setEnviarResponsableCargarMeta(Boolean enviarResponsableCargarMeta)
  {
    this.enviarResponsableCargarMeta = enviarResponsableCargarMeta;
  }
  
  public Boolean getEnviarResponsableCargarEjecutado()
  {
    return enviarResponsableCargarEjecutado;
  }
  
  public void setEnviarResponsableCargarEjecutado(Boolean enviarResponsableCargarEjecutado)
  {
    this.enviarResponsableCargarEjecutado = enviarResponsableCargarEjecutado;
  }
  
  public Byte getTipoCorreoDefaultSent()
  {
    return tipoCorreoDefaultSent;
  }
  
  public void setTipoCorreoDefaultSent(Byte tipoCorreoDefaultSent)
  {
    this.tipoCorreoDefaultSent = tipoCorreoDefaultSent;
  }
}
