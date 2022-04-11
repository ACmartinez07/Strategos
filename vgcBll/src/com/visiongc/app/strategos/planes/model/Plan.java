package com.visiongc.app.strategos.planes.model;

import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.framework.arboles.NodoArbol;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Plan implements Serializable, NodoArbol
{
  static final long serialVersionUID = 0L;
  private Long planId;
  private Long organizacionId;
  private Long planImpactaId;
  private String nombre;
  private Long padreId;
  private Integer anoInicial;
  private Integer anoFinal;
  private Byte tipo;
  private Boolean activo;
  private Byte revision;
  private String esquema;
  private Long metodologiaId;
  private Long claseId;
  private Long claseIdIndicadoresTotales;
  private Long indTotalImputacionId;
  private Long indTotalIniciativaId;
  private Byte alerta;
  private Double ultimaMedicionAnual;
  private Double ultimaMedicionParcial;
  private Long nlAnoIndicadorId;
  private Long nlParIndicadorId;
  private String fechaUltimaMedicion;
  private Long serieIdVigente;
  private PlantillaPlanes metodologia;
  private OrganizacionStrategos organizacion;
  private ClaseIndicadores clase;
  private Plan planImpacta;
  private Collection nodoArbolHijos;
  private NodoArbol nodoArbolPadre;
  private Boolean hijosCargados;
  private Set hijos;
  private Plan padre;
  
  public Plan() {}
  
  public Long getPlanId()
  {
    return planId;
  }
  
  public void setPlanId(Long planId) {
    this.planId = planId;
  }
  
  public Long getOrganizacionId() {
    return organizacionId;
  }
  
  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }
  
  public Long getPlanImpactaId() {
    return planImpactaId;
  }
  
  public void setPlanImpactaId(Long planImpactaId) {
    this.planImpactaId = planImpactaId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public Long getPadreId() {
    return padreId;
  }
  
  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }
  
  public Integer getAnoInicial() {
    return anoInicial;
  }
  
  public void setAnoInicial(Integer anoInicial) {
    this.anoInicial = anoInicial;
  }
  
  public Integer getAnoFinal() {
    return anoFinal;
  }
  
  public void setAnoFinal(Integer anoFinal) {
    this.anoFinal = anoFinal;
  }
  
  public Boolean getActivo() {
    return activo;
  }
  
  public void setActivo(Boolean activo) {
    this.activo = activo;
  }
  
  public Byte getRevision() {
    return revision;
  }
  
  public void setRevision(Byte revision) {
    this.revision = revision;
  }
  
  public String getRevisionNombre() {
    if ((revision != null) && (revision.byteValue() < 11)) {
      return com.visiongc.app.strategos.model.util.Revision.getNombre(revision.byteValue());
    }
    return revision.toString();
  }
  
  public String getEsquema()
  {
    return esquema;
  }
  
  public void setEsquema(String esquema) {
    this.esquema = esquema;
  }
  
  public Long getMetodologiaId() {
    return metodologiaId;
  }
  
  public void setMetodologiaId(Long metodologiaId) {
    this.metodologiaId = metodologiaId;
  }
  
  public Long getClaseId() {
    return claseId;
  }
  
  public void setClaseId(Long claseId) {
    this.claseId = claseId;
  }
  
  public Long getClaseIdIndicadoresTotales() {
    return claseIdIndicadoresTotales;
  }
  
  public void setClaseIdIndicadoresTotales(Long claseIdIndicadoresTotales) {
    this.claseIdIndicadoresTotales = claseIdIndicadoresTotales;
  }
  
  public Long getIndTotalImputacionId() {
    return indTotalImputacionId;
  }
  
  public void setIndTotalImputacionId(Long indTotalImputacionId) {
    this.indTotalImputacionId = indTotalImputacionId;
  }
  
  public Long getIndTotalIniciativaId() {
    return indTotalIniciativaId;
  }
  
  public void setIndTotalIniciativaId(Long indTotalIniciativaId) {
    this.indTotalIniciativaId = indTotalIniciativaId;
  }
  
  public Byte getAlerta() {
    return alerta;
  }
  
  public void setAlerta(Byte alerta) {
    this.alerta = alerta;
  }
  
  public Double getUltimaMedicionAnual() {
    return ultimaMedicionAnual;
  }
  
  public void setUltimaMedicionAnual(Double ultimaMedicionAnual) {
    this.ultimaMedicionAnual = ultimaMedicionAnual;
  }
  
  public Double getUltimaMedicionParcial() {
    return ultimaMedicionParcial;
  }
  
  public void setUltimaMedicionParcial(Double ultimaMedicionParcial) {
    this.ultimaMedicionParcial = ultimaMedicionParcial;
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
  
  public String getFechaUltimaMedicion() {
    return fechaUltimaMedicion;
  }
  
  public void setFechaUltimaMedicion(String fechaUltimaMedicion) {
    this.fechaUltimaMedicion = fechaUltimaMedicion;
  }
  
  public OrganizacionStrategos getOrganizacion() {
    return organizacion;
  }
  
  public void setOrganizacion(OrganizacionStrategos organizacion) {
    this.organizacion = organizacion;
  }
  
  public PlantillaPlanes getMetodologia() {
    return metodologia;
  }
  
  public void setMetodologia(PlantillaPlanes metodologia) {
    this.metodologia = metodologia;
  }
  
  public Byte getTipo() {
    return tipo;
  }
  
  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }
  
  public ClaseIndicadores getClase() {
    return clase;
  }
  
  public void setClase(ClaseIndicadores clase) {
    this.clase = clase;
  }
  
  public Plan getPlanImpacta() {
    return planImpacta;
  }
  
  public void setPlanImpacta(Plan planImpacta) {
    this.planImpacta = planImpacta;
  }
  
  public Long getSerieIdVigente() {
    return serieIdVigente;
  }
  
  public void setSerieIdVigente(Long serieIdVigente) {
    this.serieIdVigente = serieIdVigente;
  }
  
  public Set getHijos() {
    return hijos;
  }
  
  public void setHijos(Set hijos) {
    this.hijos = hijos;
  }
  
  public Plan getPadre() {
    return padre;
  }
  
  public void setPadre(Plan padre) {
    this.padre = padre;
  }
  
  public void setNodoArbolHijos(Collection nodoArbolHijos) {
    this.nodoArbolHijos = nodoArbolHijos;
  }
  
  public Collection getNodoArbolHijos() {
    return nodoArbolHijos;
  }
  
  public String getNodoArbolId() {
    if (planId != null) {
      return planId.toString();
    }
    return null;
  }
  
  public String getNodoArbolNombre()
  {
    return nombre;
  }
  
  public String getNodoArbolNombreImagen(Byte tipo)
  {
    if (tipo.byteValue() == 1) {
      return "Plan";
    }
    return "";
  }
  
  public String getNodoArbolNombreToolTipImagen(Byte tipo)
  {
    return "";
  }
  
  public boolean getNodoArbolHijosCargados() {
    if (hijosCargados == null) {
      hijosCargados = new Boolean(false);
    }
    return hijosCargados.booleanValue();
  }
  
  public String getNodoArbolNombreCampoId() {
    return "planId";
  }
  
  public String getNodoArbolNombreCampoNombre() {
    return "nombre";
  }
  
  public String getNodoArbolNombreCampoPadreId() {
    return "padreId";
  }
  
  public void setNodoArbolPadre(NodoArbol nodoArbol) {
    nodoArbolPadre = nodoArbol;
  }
  
  public NodoArbol getNodoArbolPadre() {
    return nodoArbolPadre;
  }
  
  public String getNodoArbolPadreId() {
    if (padreId != null) {
      return padreId.toString();
    }
    return null;
  }
  
  public void setNodoArbolHijosCargados(boolean cargados)
  {
    if (hijosCargados == null) {
      hijosCargados = new Boolean(false);
    }
    hijosCargados = new Boolean(cargados);
  }
  
  public void setNodoArbolNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public int compareTo(Object o) {
    Plan or = (Plan)o;
    return getPlanId().compareTo(or.getPlanId());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("planId", getPlanId()).toString();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Plan other = (Plan)obj;
    return new EqualsBuilder().append(getPlanId(), other.getPlanId()).isEquals();
  }
}
