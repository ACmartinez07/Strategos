package com.visiongc.framework.auditoria.model;

import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoEvento;
import com.visiongc.framework.model.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.*;

// Referenced classes of package com.visiongc.framework.auditoria.model:
//            AuditoriaEventoPK, ObjetoAuditable

public class AuditoriaEvento
    implements Serializable
{

    public AuditoriaEvento()
    {
    }

    public AuditoriaEventoPK getPk()
    {
        return pk;
    }

    public void setPk(AuditoriaEventoPK pk)
    {
        this.pk = pk;
    }

    public Long getFechaMiliSeg()
    {
        Long fechaMs = null;
        if(pk != null)
            fechaMs = new Long(pk.getFecha().getTime());
        return fechaMs;
    }

    public String getFechaFormateada()
    {
        String fechaFormateada = "";
        if(pk != null)
            fechaFormateada = VgcFormatter.formatearFecha(pk.getFecha(), "yyyy-MM-dd HH:mm:ss");
        return fechaFormateada;
    }

    public String getTipoEventoNombre()
    {
        String nombre = "";
        if(pk != null)
            nombre = AuditoriaTipoEvento.getNombre(pk.getTipoEvento().byteValue());
        return nombre;
    }

    public String getNombreClase()
    {
        String nombre = "";
        if(objetoAuditable != null)
            nombre = objetoAuditable.getNombreClase().substring(objetoAuditable.getNombreClase().lastIndexOf(".") + 1);
        return nombre;
    }

    public String getNombreClaseFormateado()
    {
        return nombreClaseFormateado;
    }

    public void setNombreClaseFormateado(String nombreClaseFormateado)
    {
        this.nombreClaseFormateado = nombreClaseFormateado;
    }

    public String getInstanciaNombre()
    {
        return instanciaNombre;
    }

    public void setInstanciaNombre(String instanciaNombre)
    {
        this.instanciaNombre = instanciaNombre;
    }

    public Long getObjetoId()
    {
        return objetoId;
    }

    public void setObjetoId(Long objetoId)
    {
        this.objetoId = objetoId;
    }

    public Long getUsuarioId()
    {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId)
    {
        this.usuarioId = usuarioId;
    }

    public ObjetoAuditable getObjetoAuditable()
    {
        return objetoAuditable;
    }

    public void setObjetoAuditable(ObjetoAuditable objetoAuditable)
    {
        this.objetoAuditable = objetoAuditable;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public Set getAtributosMemo()
    {
        return atributosMemo;
    }

    public void setAtributosMemo(Set atributosMemo)
    {
        this.atributosMemo = atributosMemo;
    }

    public Set getAtributosString()
    {
        return atributosString;
    }

    public void setAtributosString(Set atributosString)
    {
        this.atributosString = atributosString;
    }

    public Set getAtributosFecha()
    {
        return atributosFecha;
    }

    public void setAtributosFecha(Set atributosFecha)
    {
        this.atributosFecha = atributosFecha;
    }

    public Set getAtributosEntero()
    {
        return atributosEntero;
    }

    public void setAtributosEntero(Set atributosEntero)
    {
        this.atributosEntero = atributosEntero;
    }

    public Set getAtributosFlotante()
    {
        return atributosFlotante;
    }

    public void setAtributosFlotante(Set atributosFlotante)
    {
        this.atributosFlotante = atributosFlotante;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("pk", getPk()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof AuditoriaEvento))
        {
            return false;
        } else
        {
            AuditoriaEvento castOther = (AuditoriaEvento)other;
            return (new EqualsBuilder()).append(getPk(), castOther.getPk()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getPk()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private AuditoriaEventoPK pk;
    private Long objetoId;
    private String instanciaNombre;
    private String nombreClaseFormateado;
    private Long usuarioId;
    private ObjetoAuditable objetoAuditable;
    private Usuario usuario;
    private Set atributosString;
    private Set atributosMemo;
    private Set atributosFecha;
    private Set atributosEntero;
    private Set atributosFlotante;
}