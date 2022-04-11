package com.visiongc.framework.web.struts.configuracion.login.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;








public class SettingForm
  extends EditarObjetoForm
{
  private Long id;
  private String clave = "Strategos.Configuracion.Login";
  private String valor;
  private Integer tamanoMinimo = Integer.valueOf(5);
  private Integer maximoReintentos = Integer.valueOf(3);
  private Boolean passwordExpira = Boolean.valueOf(true);
  private Integer periodoExpiracion = Integer.valueOf(3);
  private Integer duracionExpira = Integer.valueOf(3);
  private Integer numeroPasswordViejos = Integer.valueOf(3);
  private Integer tipoFuerza = Integer.valueOf(0);
  private Integer periodoVigenciaMinima = Integer.valueOf(0);
  private Integer duracionVigenciaMinima = Integer.valueOf(1);
  private Integer intentosFallidos = Integer.valueOf(3);
  private Integer advertenciaCaducidad = Integer.valueOf(5);
  private Integer sinActividad = Integer.valueOf(30);
  private String tipo = "String";
  
  public SettingForm() {}
  
  public Long getId() { return id; }
  

  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getClave()
  {
    return clave;
  }
  
  public String getValor()
  {
    return valor;
  }
  
  public void setValor(String valor)
  {
    this.valor = valor;
  }
  

  public void clear()
  {
    valor = "";
    tamanoMinimo = Integer.valueOf(5);
    maximoReintentos = Integer.valueOf(3);
    passwordExpira = Boolean.valueOf(true);
    periodoExpiracion = Integer.valueOf(3);
    duracionExpira = Integer.valueOf(3);
    numeroPasswordViejos = Integer.valueOf(3);
    tipoFuerza = Integer.valueOf(0);
    periodoVigenciaMinima = Integer.valueOf(0);
    duracionVigenciaMinima = Integer.valueOf(1);
    intentosFallidos = Integer.valueOf(3);
    advertenciaCaducidad = Integer.valueOf(5);
    sinActividad = Integer.valueOf(30);
    tipo = "String";
  }
}
