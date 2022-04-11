package com.visiongc.app.strategos.planes.model;

import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.model.util.AlertaObjetivo;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planes.model.util.TipoPerspectiva;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.framework.arboles.NodoArbol;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Perspectiva implements java.io.Serializable, NodoArbol
{
  static final long serialVersionUID = 0L;
  private Long perspectivaId;
  private Long planId;
  private Long responsableId;
  private String nombre;
  private String nombreObjetoPerspectiva;
  private Long padreId;
  private String descripcion;
  private Integer orden;
  private Byte frecuencia;
  private String titulo;
  private Byte tipo;
  private String fechaUltimaMedicion;
  private Double ultimaMedicionAnual;
  private Double ultimaMedicionParcial;
  private Date creado;
  private Date modificado;
  private Long creadoId;
  private Long modificadoId;
  private Byte alertaParcial;
  private Byte alertaAnual;
  private Integer ano;
  private Long claseId;
  private Long nlAnoIndicadorId;
  private Long nlParIndicadorId;
  private Byte tipoCalculo;
  private Byte nivel;
  private Set indicadoresPerspectiva;
  private List listaIndicadores;
  private List listaIndicadoresGuia;
  private List listaIniciativas;
  private List<Perspectiva> listaHijos;
  private Set indicadoresPlan;
  private Perspectiva padre;
  private Set<Perspectiva> hijos;
  private Boolean hijosCargados;
  private Plan plan;
  private Collection nodoArbolHijos;
  private List<String> listaPerspectivasMetod;
  private List<String> listaPadres;
  private Set<PerspectivaRelacion> relacion;
  private OrganizacionStrategos organizacion;
  private List<Perspectiva> perspectivasAsociadas;
  private ConfiguracionPlan configuracionPlan;
  
  public Perspectiva() {}
  
  public Long getPerspectivaId()
  {
    return perspectivaId;
  }
  
  public void setPerspectivaId(Long perspectivaId)
  {
    this.perspectivaId = perspectivaId;
  }
  
  public Long getPlanId()
  {
    return planId;
  }
  
  public void setPlanId(Long planId)
  {
    this.planId = planId;
  }
  
  public Long getResponsableId()
  {
    return responsableId;
  }
  
  public void setResponsableId(Long responsableId)
  {
    this.responsableId = responsableId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public String getNombreObjetoPerspectiva()
  {
    return nombreObjetoPerspectiva;
  }
  
  public void setNombreObjetoPerspectiva(String nombreObjetoPerspectiva)
  {
    this.nombreObjetoPerspectiva = nombreObjetoPerspectiva;
  }
  
  public Long getPadreId()
  {
    return padreId;
  }
  
  public void setPadreId(Long padreId)
  {
    this.padreId = padreId;
  }
  
  public String getDescripcion()
  {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }
  
  public Integer getOrden()
  {
    return orden;
  }
  
  public void setOrden(Integer orden)
  {
    this.orden = orden;
  }
  
  public Byte getFrecuencia()
  {
    return frecuencia;
  }
  
  public void setFrecuencia(Byte frecuencia)
  {
    this.frecuencia = frecuencia;
  }
  
  public String getTitulo()
  {
    return titulo;
  }
  
  public void setTitulo(String titulo)
  {
    this.titulo = titulo;
  }
  
  public Byte getTipo()
  {
    return tipo;
  }
  
  public void setTipo(Byte tipo)
  {
    this.tipo = tipo;
  }
  
  public String getFechaUltimaMedicion()
  {
    return fechaUltimaMedicion;
  }
  
  public void setFechaUltimaMedicion(String fechaUltimaMedicion)
  {
    this.fechaUltimaMedicion = fechaUltimaMedicion;
  }
  
  public Double getUltimaMedicionAnual()
  {
    return ultimaMedicionAnual;
  }
  
  public String getUltimaMedicionAnualFormateado()
  {
    return VgcFormatter.formatearNumero(ultimaMedicionAnual, 2);
  }
  
  public void setUltimaMedicionAnual(Double ultimaMedicionAnual)
  {
    this.ultimaMedicionAnual = ultimaMedicionAnual;
  }
  
  public Double getUltimaMedicionParcial()
  {
    return ultimaMedicionParcial;
  }
  
  public String getUltimaMedicionParcialFormateado()
  {
    return VgcFormatter.formatearNumero(ultimaMedicionParcial, 2);
  }
  
  public void setUltimaMedicionParcial(Double ultimaMedicionParcial)
  {
    this.ultimaMedicionParcial = ultimaMedicionParcial;
  }
  
  public Byte getNivel()
  {
    return nivel;
  }
  
  public void setNivel(Byte nivel)
  {
    this.nivel = nivel;
  }
  
  public Date getCreado()
  {
    return creado;
  }
  
  public void setCreado(Date creado)
  {
    this.creado = creado;
  }
  
  public Date getModificado()
  {
    return modificado;
  }
  
  public void setModificado(Date modificado)
  {
    this.modificado = modificado;
  }
  
  public Long getCreadoId()
  {
    return creadoId;
  }
  
  public void setCreadoId(Long creadoId)
  {
    this.creadoId = creadoId;
  }
  
  public Long getModificadoId()
  {
    return modificadoId;
  }
  
  public void setModificadoId(Long modificadoId)
  {
    this.modificadoId = modificadoId;
  }
  
  public Byte getAlertaParcial()
  {
    return alertaParcial;
  }
  
  public void setAlertaParcial(Byte alertaParcial)
  {
    this.alertaParcial = alertaParcial;
  }
  
  public Byte getAlertaAnual()
  {
    return alertaAnual;
  }
  
  public void setAlertaAnual(Byte alertaAnual)
  {
    this.alertaAnual = alertaAnual;
  }
  
  public Integer getAno() {
    return ano;
  }
  
  public void setAno(Integer ano) {
    this.ano = ano;
  }
  
  public Long getClaseId() {
    return claseId;
  }
  
  public void setClaseId(Long claseId) {
    this.claseId = claseId;
  }
  
  public Long getNlAnoIndicadorId() {
    return nlAnoIndicadorId;
  }
  
  public void setNlAnoIndicadorId(Long nlAnoIndicadorId) {
    this.nlAnoIndicadorId = nlAnoIndicadorId;
  }
  
  public Long getNlParIndicadorId() {
    return nlParIndicadorId;
  }
  
  public void setNlParIndicadorId(Long nlParIndicadorId) {
    this.nlParIndicadorId = nlParIndicadorId;
  }
  
  public Byte getTipoCalculo() {
    return tipoCalculo;
  }
  
  public void setTipoCalculo(Byte tipoCalculo) {
    this.tipoCalculo = tipoCalculo;
  }
  
  public Perspectiva getPadre() {
    return padre;
  }
  
  public void setPadre(Perspectiva padre) {
    this.padre = padre;
  }
  
  public Set<Perspectiva> getHijos()
  {
    return hijos;
  }
  
  public void setHijos(Set<Perspectiva> hijos)
  {
    this.hijos = hijos;
  }
  
  public Boolean getHijosCargados()
  {
    return hijosCargados;
  }
  
  public void setHijosCargados(Boolean hijosCargados)
  {
    this.hijosCargados = hijosCargados;
  }
  
  public Plan getPlan() {
    return plan;
  }
  
  public void setPlan(Plan plan) {
    this.plan = plan;
  }
  
  public Set getIndicadoresPerspectiva() {
    return indicadoresPerspectiva;
  }
  
  public void setIndicadoresPerspectiva(Set indicadoresPerspectiva) {
    this.indicadoresPerspectiva = indicadoresPerspectiva;
  }
  
  public List getListaIndicadores() {
    return listaIndicadores;
  }
  
  public void setListaIndicadores(List listaIndicadores) {
    this.listaIndicadores = listaIndicadores;
  }
  
  public void setListaPerspectivasMetod(List<String> listaPerspectivasMetod) {
    this.listaPerspectivasMetod = listaPerspectivasMetod;
  }
  
  public List<String> getListaPerspectivasMetod() {
    return listaPerspectivasMetod;
  }
  
  public List<String> getListaPadres() {
    return listaPadres;
  }
  
  public void setListaPadres(List<String> listaPadres) {
    this.listaPadres = listaPadres;
  }
  
  public List getListaIndicadoresGuia() {
    return listaIndicadoresGuia;
  }
  
  public void setListaIndicadoresGuia(List listaIndicadoresGuia) {
    this.listaIndicadoresGuia = listaIndicadoresGuia;
  }
  
  public List getListaIniciativas()
  {
    return listaIniciativas;
  }
  
  public void setListaIniciativas(List listaIniciativas)
  {
    this.listaIniciativas = listaIniciativas;
  }
  
  public List<Perspectiva> getListaHijos()
  {
    return listaHijos;
  }
  
  public void setListaHijos(List<Perspectiva> listaHijos)
  {
    this.listaHijos = listaHijos;
  }
  
  public Set getIndicadoresPlan()
  {
    return indicadoresPlan;
  }
  
  public void setIndicadoresPlan(Set indicadoresPlan)
  {
    this.indicadoresPlan = indicadoresPlan;
  }
  
  public OrganizacionStrategos getOrganizacion()
  {
    return organizacion;
  }
  
  public void setOrganizacion(OrganizacionStrategos organizacion)
  {
    this.organizacion = organizacion;
  }
  
  public Set<PerspectivaRelacion> getRelacion()
  {
    return relacion;
  }
  
  public void setRelacion(Set<PerspectivaRelacion> relacion)
  {
    this.relacion = relacion;
  }
  
  public List<Perspectiva> getPerspectivasAsociadas()
  {
    return perspectivasAsociadas;
  }
  
  public void setPerspectivasAsociadas(List<Perspectiva> perspectivasAsociadas)
  {
    this.perspectivasAsociadas = perspectivasAsociadas;
  }
  
  public List getHijosHoja()
  {
    return new ArrayList();
  }
  

  public void setHijosHoja(List hijosHoja) {}
  

  public String getNodoArbolId()
  {
    if (perspectivaId != null)
    {
      return perspectivaId.toString();
    }
    
    return null;
  }
  
  public String getNodoArbolNombre()
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
    
    String nombreArbol = "";
    if (configuracionPlan != null)
    {
      if ((!configuracionPlan.getPlanObjetivoLogroAnualMostrar().booleanValue()) && (!configuracionPlan.getPlanObjetivoLogroParcialMostrar().booleanValue())) {
        nombreArbol = nombre;
      } else if ((configuracionPlan.getPlanObjetivoLogroAnualMostrar().booleanValue()) && (configuracionPlan.getPlanObjetivoLogroParcialMostrar().booleanValue()))
      {
        nombreArbol = ultimaMedicionParcial != null ? nombre + " <font style=\"color:#4c4cff;font-weight: Bold;\" title=\"" + messageResources.getResource("jsp.gestionarperspectivas.arbol.logro.parcial") + "\">(" + VgcFormatter.formatearNumero(ultimaMedicionParcial, 2) + " %)</font>" : nombre;
        nombreArbol = nombreArbol + (ultimaMedicionAnual != null ? " <font style=\"color:#0e207f;font-weight: Bold;\" title=\"" + messageResources.getResource("jsp.gestionarperspectivas.arbol.logro.anual") + "\">(" + VgcFormatter.formatearNumero(ultimaMedicionAnual, 2) + " %)</font>" : "");
      }
      else if (configuracionPlan.getPlanObjetivoLogroAnualMostrar().booleanValue()) {
        nombreArbol = ultimaMedicionAnual != null ? nombre + " <font style=\"color:#0e207f;font-weight: Bold;\" title=\"" + messageResources.getResource("jsp.gestionarperspectivas.arbol.logro.anual") + "\">(" + VgcFormatter.formatearNumero(ultimaMedicionAnual, 2) + " %)</font>" : nombre;
      } else if (configuracionPlan.getPlanObjetivoLogroParcialMostrar().booleanValue()) {
        nombreArbol = ultimaMedicionParcial != null ? nombre + " <font style=\"color:#4c4cff;font-weight: Bold;\" title=\"" + messageResources.getResource("jsp.gestionarperspectivas.arbol.logro.parcial") + "\">(" + VgcFormatter.formatearNumero(ultimaMedicionParcial, 2) + " %)</font>" : nombre;
      }
    }
    else {
      nombreArbol = ultimaMedicionParcial != null ? nombre + " <font style=\"color:#4c4cff;font-weight: Bold;\" title=\"" + messageResources.getResource("jsp.gestionarperspectivas.arbol.logro.parcial") + "\">(" + VgcFormatter.formatearNumero(ultimaMedicionParcial, 2) + " %)</font>" : nombre;
      nombreArbol = nombreArbol + (ultimaMedicionAnual != null ? " <font style=\"color:#0e207f;font-weight: Bold;\" title=\"" + messageResources.getResource("jsp.gestionarperspectivas.arbol.logro.anual") + "\">(" + VgcFormatter.formatearNumero(ultimaMedicionAnual, 2) + " %)</font>" : "");
    }
    
    return nombreArbol;
  }
  
  public String getNombreCompleto()
  {
    String nombreArbol = "";
    
    if (configuracionPlan != null)
    {
      if ((!configuracionPlan.getPlanObjetivoLogroAnualMostrar().booleanValue()) && (!configuracionPlan.getPlanObjetivoLogroParcialMostrar().booleanValue())) {
        nombreArbol = nombre;
      } else if ((configuracionPlan.getPlanObjetivoLogroAnualMostrar().booleanValue()) && (configuracionPlan.getPlanObjetivoLogroParcialMostrar().booleanValue()))
      {
        nombreArbol = ultimaMedicionParcial != null ? nombre + " (" + VgcFormatter.formatearNumero(ultimaMedicionParcial, 2) + " %)" : nombre;
        nombreArbol = nombreArbol + (ultimaMedicionAnual != null ? " (" + VgcFormatter.formatearNumero(ultimaMedicionAnual, 2) + " %)" : "");
      }
      else if (configuracionPlan.getPlanObjetivoLogroAnualMostrar().booleanValue()) {
        nombreArbol = ultimaMedicionAnual != null ? nombre + " (" + VgcFormatter.formatearNumero(ultimaMedicionAnual, 2) + " %)" : nombre;
      } else if (configuracionPlan.getPlanObjetivoLogroParcialMostrar().booleanValue()) {
        nombreArbol = ultimaMedicionParcial != null ? nombre + " (" + VgcFormatter.formatearNumero(ultimaMedicionParcial, 2) + " %)" : nombre;
      }
    }
    else {
      nombreArbol = ultimaMedicionParcial != null ? nombre + " (" + VgcFormatter.formatearNumero(ultimaMedicionParcial, 2) + " %)" : nombre;
      nombreArbol = nombreArbol + (ultimaMedicionAnual != null ? " (" + VgcFormatter.formatearNumero(ultimaMedicionAnual, 2) + " %)" : "");
    }
    
    return nombreArbol;
  }
  
  public boolean getNodoArbolHijosCargados()
  {
    if (hijosCargados == null)
    {
      hijosCargados = new Boolean(false);
    }
    
    return hijosCargados.booleanValue();
  }
  
  public String getNodoArbolNombreCampoId()
  {
    return "perspectivaId";
  }
  
  public String getNodoArbolNombreCampoNombre()
  {
    return "nombre";
  }
  
  public String getNodoArbolNombreCampoPadreId()
  {
    return "padreId";
  }
  
  public void setNodoArbolPadre(NodoArbol nodoArbol)
  {
    setPadre((Perspectiva)nodoArbol);
  }
  
  public NodoArbol getNodoArbolPadre()
  {
    return getPadre();
  }
  
  public String getNodoArbolPadreId()
  {
    if (padreId != null) {
      return padreId.toString();
    }
    return null;
  }
  
  public void setNodoArbolHijos(Collection nodoArbolHijos)
  {
    this.nodoArbolHijos = nodoArbolHijos;
  }
  
  public Collection getNodoArbolHijos()
  {
    return nodoArbolHijos;
  }
  
  public void setNodoArbolHijosCargados(boolean cargados)
  {
    hijosCargados = new Boolean(cargados);
  }
  
  public ConfiguracionPlan getConfiguracionPlan()
  {
    return configuracionPlan;
  }
  
  public void setConfiguracionPlan(ConfiguracionPlan configuracionPlan)
  {
    this.configuracionPlan = configuracionPlan;
  }
  
  public void setNodoArbolNombre(String nombre)
  {
    if (nombre.indexOf("(") == -1) {
      this.nombre = nombre;
    }
  }
  
  public String getNodoArbolNombreImagen(Byte tipo) {
    String nombreImagen = "";
    
    if (tipo.byteValue() == 1)
    {
      if ((configuracionPlan != null) && (configuracionPlan.getPlanObjetivoAlertaParcialMostrar().booleanValue()) && (!configuracionPlan.getPlanObjetivoAlertaAnualMostrar().booleanValue()))
      {
        nombreImagen = "Perspectiva";
        
        if ((this.tipo != null) && (this.tipo.byteValue() == TipoPerspectiva.getTipoPerspectivaObjetivo().byteValue())) {
          nombreImagen = "Objetivo";
        }
        nombreImagen = nombreImagen + AlertaObjetivo.getNombreImagen(alertaParcial);
      }
      else if ((configuracionPlan != null) && (configuracionPlan.getPlanObjetivoAlertaAnualMostrar().booleanValue()) && (!configuracionPlan.getPlanObjetivoAlertaParcialMostrar().booleanValue()))
      {
        nombreImagen = "Perspectiva";
        
        if ((this.tipo != null) && (this.tipo.byteValue() == TipoPerspectiva.getTipoPerspectivaObjetivo().byteValue())) {
          nombreImagen = "Objetivo";
        }
        nombreImagen = nombreImagen + AlertaObjetivo.getNombreImagen(alertaAnual);
      }
      else if ((configuracionPlan != null) && (configuracionPlan.getPlanObjetivoAlertaParcialMostrar().booleanValue()) && (configuracionPlan.getPlanObjetivoAlertaAnualMostrar().booleanValue()))
      {
        nombreImagen = "Perspectiva";
        
        if ((this.tipo != null) && (this.tipo.byteValue() == TipoPerspectiva.getTipoPerspectivaObjetivo().byteValue())) {
          nombreImagen = "Objetivo";
        }
        nombreImagen = nombreImagen + AlertaObjetivo.getNombreImagen(alertaParcial);
      }
      else if ((configuracionPlan != null) && (!configuracionPlan.getPlanObjetivoAlertaParcialMostrar().booleanValue()) && (!configuracionPlan.getPlanObjetivoAlertaAnualMostrar().booleanValue()))
      {
        nombreImagen = "Perspectiva";
        if ((this.tipo != null) && (this.tipo.byteValue() == TipoPerspectiva.getTipoPerspectivaObjetivo().byteValue())) {
          nombreImagen = "Objetivo";
        }
      }
      else {
        nombreImagen = "Perspectiva";
        
        if ((this.tipo != null) && (this.tipo.byteValue() == TipoPerspectiva.getTipoPerspectivaObjetivo().byteValue())) {
          nombreImagen = "Objetivo";
        }
        nombreImagen = nombreImagen + AlertaObjetivo.getNombreImagen(alertaParcial);
      }
      

    }
    else if ((configuracionPlan != null) && (configuracionPlan.getPlanObjetivoAlertaParcialMostrar().booleanValue()) && (configuracionPlan.getPlanObjetivoAlertaAnualMostrar().booleanValue()))
    {
      nombreImagen = "Perspectiva";
      
      if ((this.tipo != null) && (this.tipo.byteValue() == TipoPerspectiva.getTipoPerspectivaObjetivo().byteValue())) {
        nombreImagen = "Objetivo";
      }
      nombreImagen = nombreImagen + AlertaObjetivo.getNombreImagen(alertaAnual);
    }
    

    return nombreImagen;
  }
  
  public String getImagenAlertaParcial()
  {
    String nombreImagen = "";
    nombreImagen = "Perspectiva";
    
    if ((tipo != null) && (tipo.byteValue() == TipoPerspectiva.getTipoPerspectivaObjetivo().byteValue())) {
      nombreImagen = "Objetivo";
    }
    if ((configuracionPlan != null) && (configuracionPlan.getPlanObjetivoAlertaParcialMostrar().booleanValue())) {
      nombreImagen = nombreImagen + AlertaObjetivo.getNombreImagen(alertaParcial);
    }
    return nombreImagen;
  }
  
  public String getToolTipImagenAlertaParcial()
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
    String toolTip = "";
    
    if ((configuracionPlan != null) && (configuracionPlan.getPlanObjetivoAlertaParcialMostrar().booleanValue())) {
      toolTip = messageResources.getResource("jsp.gestionarperspectivas.arbol.alerta.parcial");
    }
    return toolTip;
  }
  
  public String getImagenAlertaAnual()
  {
    String nombreImagen = "";
    nombreImagen = "Perspectiva";
    
    if ((tipo != null) && (tipo.byteValue() == TipoPerspectiva.getTipoPerspectivaObjetivo().byteValue())) {
      nombreImagen = "Objetivo";
    }
    if ((configuracionPlan != null) && (configuracionPlan.getPlanObjetivoAlertaAnualMostrar().booleanValue())) {
      nombreImagen = nombreImagen + AlertaObjetivo.getNombreImagen(alertaAnual);
    }
    return nombreImagen;
  }
  
  public String getToolTipImagenAlertaAnual()
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
    String toolTip = "";
    
    if ((configuracionPlan != null) && (configuracionPlan.getPlanObjetivoAlertaAnualMostrar().booleanValue())) {
      toolTip = messageResources.getResource("jsp.gestionarperspectivas.arbol.alerta.anual");
    }
    return toolTip;
  }
  
  public String getNodoArbolNombreToolTipImagen(Byte tipo)
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
    String toolTip = "";
    
    if (tipo.byteValue() == 1)
    {
      if ((configuracionPlan != null) && (configuracionPlan.getPlanObjetivoAlertaParcialMostrar().booleanValue()) && (!configuracionPlan.getPlanObjetivoAlertaAnualMostrar().booleanValue())) {
        toolTip = messageResources.getResource("jsp.gestionarperspectivas.arbol.alerta.parcial");
      } else if ((configuracionPlan != null) && (configuracionPlan.getPlanObjetivoAlertaAnualMostrar().booleanValue()) && (!configuracionPlan.getPlanObjetivoAlertaParcialMostrar().booleanValue())) {
        toolTip = messageResources.getResource("jsp.gestionarperspectivas.arbol.alerta.anual");
      } else if ((configuracionPlan != null) && (configuracionPlan.getPlanObjetivoAlertaParcialMostrar().booleanValue()) && (configuracionPlan.getPlanObjetivoAlertaAnualMostrar().booleanValue())) {
        toolTip = messageResources.getResource("jsp.gestionarperspectivas.arbol.alerta.parcial");
      } else if ((configuracionPlan != null) && (!configuracionPlan.getPlanObjetivoAlertaParcialMostrar().booleanValue()) && (!configuracionPlan.getPlanObjetivoAlertaAnualMostrar().booleanValue())) {
        toolTip = "";
      } else {
        toolTip = messageResources.getResource("jsp.gestionarperspectivas.arbol.alerta.parcial");
      }
    }
    else if ((configuracionPlan != null) && (configuracionPlan.getPlanObjetivoAlertaParcialMostrar().booleanValue()) && (configuracionPlan.getPlanObjetivoAlertaAnualMostrar().booleanValue())) {
      toolTip = messageResources.getResource("jsp.gestionarperspectivas.arbol.alerta.anual");
    }
    return toolTip;
  }
  
  public int compareTo(Object o)
  {
    Perspectiva or = (Perspectiva)o;
    return getPerspectivaId().compareTo(or.getPerspectivaId());
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("perspectivaId", getPerspectivaId()).toString();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass()) {
      return false;
    }
    Perspectiva other = (Perspectiva)obj;
    
    return new EqualsBuilder().append(getPerspectivaId(), other.getPerspectivaId()).isEquals();
  }
}
