package com.visiongc.app.strategos.foros.model;

import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.model.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Foro
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long foroId;
  private Long padreId;
  private String asunto;
  private String email;
  private String comentario;
  private Date creado;
  private Long creadoId;
  private Date modificado;
  private Long modificadoId;
  private Byte objetoKey;
  private Long objetoId;
  private Byte tipo;
  private Usuario usuarioCreado;
  private Usuario usuarioModificado;
  private String fechaFormateadaCreado;
  private String fechaFormateadaModificado;
  private Long numeroRespuestas;
  private Foro ultimaRepuestaForo;
  private Set<?> hijos;
  private Foro padre;
  
  public Foro(Long foroId, Long padreId, String asunto, String email, Byte tipo, String comentario, Date creado, Long creadoId, Byte objetoKey, Long objetoId, Set<?> hijos, Foro padre, Date modificado, Long modificadoId)
  {
    this.foroId = foroId;
    this.padreId = padreId;
    this.asunto = asunto;
    this.email = email;
    this.comentario = comentario;
    this.creado = creado;
    this.creadoId = creadoId;
    this.objetoKey = objetoKey;
    this.objetoId = objetoId;
    this.tipo = tipo;
    this.modificado = modificado;
    this.modificadoId = modificadoId;
  }
  

  public Foro() {}
  

  public Long getForoId()
  {
    return foroId;
  }
  
  public void setForoId(Long foroId) {
    this.foroId = foroId;
  }
  
  public Long getPadreId() {
    return padreId;
  }
  
  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }
  
  public String getAsunto() {
    return asunto;
  }
  
  public void setAsunto(String asunto) {
    this.asunto = asunto;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getComentario() {
    return comentario;
  }
  
  public void setComentario(String comentario) {
    this.comentario = comentario;
  }
  
  public Date getCreado() {
    return creado;
  }
  
  public void setCreado(Date creado) {
    this.creado = creado;
  }
  
  public Long getCreadoId() {
    return creadoId;
  }
  
  public void setCreadoId(Long creadoId) {
    this.creadoId = creadoId;
  }
  
  public Date getModificado() {
    return modificado;
  }
  
  public void setModificado(Date modificado) {
    this.modificado = modificado;
  }
  
  public Long getModificadoId() {
    return modificadoId;
  }
  
  public void setModificadoId(Long modificadoId) {
    this.modificadoId = modificadoId;
  }
  
  public Byte getObjetoKey() {
    return objetoKey;
  }
  
  public void setObjetoKey(Byte objetoKey) {
    this.objetoKey = objetoKey;
  }
  
  public Long getObjetoId() {
    return objetoId;
  }
  
  public void setObjetoId(Long objetoId) {
    this.objetoId = objetoId;
  }
  
  public Byte getTipo() {
    return tipo;
  }
  
  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }
  
  public Set<?> getHijos() {
    return hijos;
  }
  
  public void setHijos(Set<?> hijos) {
    this.hijos = hijos;
  }
  
  public Foro getPadre() {
    return padre;
  }
  
  public void setPadre(Foro padre) {
    this.padre = padre;
  }
  
  public Usuario getUsuarioCreado() {
    return usuarioCreado;
  }
  
  public void setUsuarioCreado(Usuario usuarioCreado) {
    this.usuarioCreado = usuarioCreado;
  }
  
  public Usuario getUsuarioModificado() {
    return usuarioModificado;
  }
  
  public void setUsuarioModificado(Usuario usuarioModificado) {
    this.usuarioModificado = usuarioModificado;
  }
  
  public String getFechaFormateadaCreado() {
    fechaFormateadaCreado = VgcFormatter.formatearFecha(creado, "formato.fecha.larga");
    return fechaFormateadaCreado;
  }
  
  public void setFechaFormateadaCreado(String fechaFormateadaCreado) {
    this.fechaFormateadaCreado = fechaFormateadaCreado;
  }
  
  public String getFechaFormateadaModificado() {
    fechaFormateadaModificado = VgcFormatter.formatearFecha(modificado, "formato.fecha.larga");
    return fechaFormateadaModificado;
  }
  
  public void setFechaFormateadaModificado(String fechaFormateadaModificado) {
    this.fechaFormateadaModificado = fechaFormateadaModificado;
  }
  
  public Long getNumeroRespuestas() {
    return numeroRespuestas;
  }
  
  public void setNumeroRespuestas(Long numeroRespuestas) {
    this.numeroRespuestas = numeroRespuestas;
  }
  
  public Foro getUltimaRepuestaForo() {
    return ultimaRepuestaForo;
  }
  
  public void setUltimaRepuestaForo(Foro ultimaRepuestaForo) {
    this.ultimaRepuestaForo = ultimaRepuestaForo;
  }
  
  public int compareTo(Object o) {
    Foro or = (Foro)o;
    return getForoId().compareTo(or.getForoId());
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Foro other = (Foro)obj;
    if (foroId == null) {
      if (foroId != null)
        return false;
    } else if (!foroId.equals(foroId))
      return false;
    return true;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("foroId", getForoId()).toString();
  }
}
