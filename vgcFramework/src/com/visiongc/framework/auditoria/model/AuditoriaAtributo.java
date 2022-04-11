package com.visiongc.framework.auditoria.model;

import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoEvento;
import com.visiongc.framework.model.Usuario;
import org.apache.commons.lang.builder.*;

// Referenced classes of package com.visiongc.framework.auditoria.model:
//            AuditoriaAtributoPK, ObjetoAuditable

public abstract class AuditoriaAtributo
{

    public AuditoriaAtributo()
    {
    }

    public AuditoriaAtributoPK getPk()
    {
        return pk;
    }

    public void setPk(AuditoriaAtributoPK pk)
    {
        this.pk = pk;
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
        if(tipoEvento != null)
            nombre = AuditoriaTipoEvento.getNombre(tipoEvento.byteValue());
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

    public Byte getTipoEvento()
    {
        return tipoEvento;
    }

    public void setTipoEvento(Byte tipoEvento)
    {
        this.tipoEvento = tipoEvento;
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

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public abstract String getValorString();

    public abstract String getValorStringCorto();

    public abstract String getValorAnteriorString();

    public abstract String getValorAnteriorStringCorto();

    public String toString()
    {
        return (new ToStringBuilder(this)).append("pk", getPk()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof AuditoriaAtributo))
        {
            return false;
        } else
        {
            AuditoriaAtributo castOther = (AuditoriaAtributo)other;
            return (new EqualsBuilder()).append(getPk(), castOther.getPk()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getPk()).toHashCode();
    }

    protected AuditoriaAtributoPK pk;
    protected Long objetoId;
    protected Long usuarioId;
    protected Byte tipoEvento;
    private ObjetoAuditable objetoAuditable;
    private String nombreClaseFormateado;
    private Usuario usuario;
    private String titulo;
}