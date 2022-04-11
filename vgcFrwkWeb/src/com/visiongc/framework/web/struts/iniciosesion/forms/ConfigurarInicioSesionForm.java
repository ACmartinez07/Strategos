package com.visiongc.framework.web.struts.iniciosesion.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class ConfigurarInicioSesionForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Integer minimoCaracteres = Integer.valueOf(5);
  private Integer maximoReintentos = Integer.valueOf(3);
  private Integer expiraContrasena = Integer.valueOf(0);
  private Integer periodoExpiracion = Integer.valueOf(3);
  private Integer duracionExpiracion = Integer.valueOf(999);
  private Integer advertenciaCaducidad = Integer.valueOf(5);
  private Integer reutilizacionContrasena = Integer.valueOf(1);
  private Integer tipoContrasena = Integer.valueOf(0);
  private Integer deshabilitarUsuarios = Integer.valueOf(999);
  private Boolean restriccionUsoDiaLunes;
  private Boolean restriccionUsoDiaMartes;
  private Boolean restriccionUsoDiaMiercoles;
  private Boolean restriccionUsoDiaJueves;
  private Boolean restriccionUsoDiaViernes;
  private Boolean restriccionUsoDiaSabado;
  private Boolean restriccionUsoDiaDomingo;
  private String restriccionUsoHoraDesde = "";
  private String restriccionUsoHoraHasta = "";
  private Integer habilitarAuditoria = Integer.valueOf(0);
  private Integer tipoConexion = Integer.valueOf(0);
  private String conexionPropiaValidacion = "";
  private String conexionPropiaParametros = "";
  private String conexionLDAPUser = "";
  private String conexionLDAPServidor = "";
  private String conexionLDAPPuerto = "";
  private String conexionLDAPDn = "";
  private String conexionMAILHost = "";
  private String conexionMAILPort = "";
  private String conexionMAILUser = "";
  private String conexionMAILPassword = "";
  
  public ConfigurarInicioSesionForm() {}
  
  public void clear() { minimoCaracteres = Integer.valueOf(5);
    maximoReintentos = Integer.valueOf(3);
    expiraContrasena = Integer.valueOf(0);
    periodoExpiracion = Integer.valueOf(3);
    duracionExpiracion = Integer.valueOf(999);
    advertenciaCaducidad = Integer.valueOf(5);
    reutilizacionContrasena = Integer.valueOf(1);
    tipoContrasena = Integer.valueOf(0);
    deshabilitarUsuarios = Integer.valueOf(999);
    restriccionUsoDiaLunes = new Boolean(true);
    restriccionUsoDiaMartes = new Boolean(true);
    restriccionUsoDiaMiercoles = new Boolean(true);
    restriccionUsoDiaJueves = new Boolean(true);
    restriccionUsoDiaViernes = new Boolean(true);
    restriccionUsoDiaSabado = new Boolean(true);
    restriccionUsoDiaDomingo = new Boolean(true);
    restriccionUsoHoraDesde = "";
    restriccionUsoHoraHasta = "";
    habilitarAuditoria = Integer.valueOf(0);
    tipoConexion = Integer.valueOf(0);
    conexionPropiaValidacion = "";
    conexionPropiaParametros = "";
    conexionLDAPUser = "";
    conexionLDAPServidor = "";
    conexionLDAPPuerto = "";
    conexionLDAPDn = "";
    conexionMAILHost = "";
    conexionMAILPort = "";
    conexionMAILUser = "";
    conexionMAILPassword = "";
  }
  
  public Integer getMinimoCaracteres()
  {
    return minimoCaracteres;
  }
  
  public void setMinimoCaracteres(Integer minimoCaracteres)
  {
    this.minimoCaracteres = minimoCaracteres;
  }
  
  public Integer getMaximoReintentos()
  {
    return maximoReintentos;
  }
  
  public void setMaximoReintentos(Integer maximoReintentos)
  {
    this.maximoReintentos = maximoReintentos;
  }
  
  public Integer getExpiraContrasena()
  {
    return expiraContrasena;
  }
  
  public void setExpiraContrasena(Integer expiraContrasena)
  {
    this.expiraContrasena = expiraContrasena;
  }
  
  public Integer getPeriodoExpiracion()
  {
    return periodoExpiracion;
  }
  
  public void setPeriodoExpiracion(Integer periodoExpiracion)
  {
    this.periodoExpiracion = periodoExpiracion;
  }
  
  public Integer getDuracionExpiracion()
  {
    return duracionExpiracion;
  }
  
  public void setDuracionExpiracion(Integer duracionExpiracion)
  {
    this.duracionExpiracion = duracionExpiracion;
  }
  
  public Integer getAdvertenciaCaducidad()
  {
    return advertenciaCaducidad;
  }
  
  public void setAdvertenciaCaducidad(Integer advertenciaCaducidad)
  {
    this.advertenciaCaducidad = advertenciaCaducidad;
  }
  
  public Integer getReutilizacionContrasena()
  {
    return reutilizacionContrasena;
  }
  
  public void setReutilizacionContrasena(Integer reutilizacionContrasena)
  {
    this.reutilizacionContrasena = reutilizacionContrasena;
  }
  
  public Integer getTipoContrasena()
  {
    return tipoContrasena;
  }
  
  public void setTipoContrasena(Integer tipoContrasena)
  {
    this.tipoContrasena = tipoContrasena;
  }
  
  public Integer getDeshabilitarUsuarios()
  {
    return deshabilitarUsuarios;
  }
  
  public void setDeshabilitarUsuarios(Integer deshabilitarUsuarios)
  {
    this.deshabilitarUsuarios = deshabilitarUsuarios;
  }
  
  public Boolean getRestriccionUsoDiaLunes()
  {
    return restriccionUsoDiaLunes;
  }
  
  public void setRestriccionUsoDiaLunes(Boolean restriccionUsoDiaLunes)
  {
    this.restriccionUsoDiaLunes = restriccionUsoDiaLunes;
  }
  
  public Boolean getRestriccionUsoDiaMartes()
  {
    return restriccionUsoDiaMartes;
  }
  
  public void setRestriccionUsoDiaMartes(Boolean restriccionUsoDiaMartes)
  {
    this.restriccionUsoDiaMartes = restriccionUsoDiaMartes;
  }
  
  public Boolean getRestriccionUsoDiaMiercoles()
  {
    return restriccionUsoDiaMiercoles;
  }
  
  public void setRestriccionUsoDiaMiercoles(Boolean restriccionUsoDiaMiercoles)
  {
    this.restriccionUsoDiaMiercoles = restriccionUsoDiaMiercoles;
  }
  
  public Boolean getRestriccionUsoDiaJueves()
  {
    return restriccionUsoDiaJueves;
  }
  
  public void setRestriccionUsoDiaJueves(Boolean restriccionUsoDiaJueves)
  {
    this.restriccionUsoDiaJueves = restriccionUsoDiaJueves;
  }
  
  public Boolean getRestriccionUsoDiaViernes()
  {
    return restriccionUsoDiaViernes;
  }
  
  public void setRestriccionUsoDiaViernes(Boolean restriccionUsoDiaViernes)
  {
    this.restriccionUsoDiaViernes = restriccionUsoDiaViernes;
  }
  
  public Boolean getRestriccionUsoDiaSabado()
  {
    return restriccionUsoDiaSabado;
  }
  
  public void setRestriccionUsoDiaSabado(Boolean restriccionUsoDiaSabado)
  {
    this.restriccionUsoDiaSabado = restriccionUsoDiaSabado;
  }
  
  public Boolean getRestriccionUsoDiaDomingo()
  {
    return restriccionUsoDiaDomingo;
  }
  
  public void setRestriccionUsoDiaDomingo(Boolean restriccionUsoDiaDomingo)
  {
    this.restriccionUsoDiaDomingo = restriccionUsoDiaDomingo;
  }
  
  public String getRestriccionUsoHoraDesde()
  {
    return restriccionUsoHoraDesde;
  }
  
  public void setRestriccionUsoHoraDesde(String restriccionUsoHoraDesde)
  {
    this.restriccionUsoHoraDesde = restriccionUsoHoraDesde;
  }
  
  public String getRestriccionUsoHoraHasta()
  {
    return restriccionUsoHoraHasta;
  }
  
  public void setRestriccionUsoHoraHasta(String restriccionUsoHoraHasta)
  {
    this.restriccionUsoHoraHasta = restriccionUsoHoraHasta;
  }
  
  public Integer getHabilitarAuditoria()
  {
    return habilitarAuditoria;
  }
  
  public void setHabilitarAuditoria(Integer habilitarAuditoria)
  {
    this.habilitarAuditoria = habilitarAuditoria;
  }
  
  public Integer getTipoConexion()
  {
    return tipoConexion;
  }
  
  public void setTipoConexion(Integer tipoConexion)
  {
    this.tipoConexion = tipoConexion;
  }
  
  public String getConexionPropiaValidacion()
  {
    return conexionPropiaValidacion;
  }
  
  public void setConexionPropiaValidacion(String conexionPropiaValidacion)
  {
    this.conexionPropiaValidacion = conexionPropiaValidacion;
  }
  
  public String getConexionPropiaParametros()
  {
    return conexionPropiaParametros;
  }
  
  public void setConexionPropiaParametros(String conexionPropiaParametros)
  {
    this.conexionPropiaParametros = conexionPropiaParametros;
  }
  
  public String getConexionLDAPUser()
  {
    return conexionLDAPUser;
  }
  
  public void setConexionLDAPUser(String conexionLDAPUser)
  {
    this.conexionLDAPUser = conexionLDAPUser;
  }
  
  public String getConexionLDAPServidor()
  {
    return conexionLDAPServidor;
  }
  
  public void setConexionLDAPServidor(String conexionLDAPServidor)
  {
    this.conexionLDAPServidor = conexionLDAPServidor;
  }
  
  public String getConexionLDAPPuerto()
  {
    return conexionLDAPPuerto;
  }
  
  public void setConexionLDAPPuerto(String conexionLDAPPuerto)
  {
    this.conexionLDAPPuerto = conexionLDAPPuerto;
  }
  
  public String getConexionLDAPDn()
  {
    return conexionLDAPDn;
  }
  
  public void setConexionLDAPDn(String conexionLDAPDn)
  {
    this.conexionLDAPDn = conexionLDAPDn;
  }
  
  public String getConexionMAILHost()
  {
    return conexionMAILHost;
  }
  
  public void setConexionMAILHost(String conexionMAILHost)
  {
    this.conexionMAILHost = conexionMAILHost;
  }
  
  public String getConexionMAILPort()
  {
    return conexionMAILPort;
  }
  
  public void setConexionMAILPort(String conexionMAILPort)
  {
    this.conexionMAILPort = conexionMAILPort;
  }
  
  public String getConexionMAILUser()
  {
    return conexionMAILUser;
  }
  
  public void setConexionMAILUser(String conexionMAILUser)
  {
    this.conexionMAILUser = conexionMAILUser;
  }
  
  public String getConexionMAILPassword()
  {
    return conexionMAILPassword;
  }
  
  public void setConexionMAILPassword(String conexionMAILPassword)
  {
    this.conexionMAILPassword = conexionMAILPassword;
  }
}
