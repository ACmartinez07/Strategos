package com.visiongc.framework.model;

import com.visiongc.commons.util.*;
import java.io.Serializable;
import java.util.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

// Referenced classes of package com.visiongc.framework.model:
//            Usuario

public class Message
    implements Serializable
{
    public static class MessageStatus
    {

        private static Byte getStatus(Byte status)
        {
            if(status.byteValue() == 1)
                return new Byte((byte)1);
            if(status.byteValue() == 2)
                return new Byte((byte)2);
            else
                return null;
        }

        public static Byte getStatusPendiente()
        {
            return new Byte((byte)1);
        }

        public static Byte getStatusProcesado()
        {
            return new Byte((byte)2);
        }

        public byte getTipoEstatusId()
        {
            return tipoEstatusId;
        }

        public void setTipoEstatusId(byte tipoEstatusId)
        {
            this.tipoEstatusId = tipoEstatusId;
        }

        public String getNombre()
        {
            return nombre;
        }

        public void setNombre(String nombre)
        {
            this.nombre = nombre;
        }

        public static List getTiposEstatus()
        {
            VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
            List tiposEstatus = new ArrayList();
            MessageStatus tiposEstatu = new MessageStatus();
            tiposEstatu.setTipoEstatusId((byte)1);
            tiposEstatu.nombre = messageResources.getResource("mensaje.status.pendiente");
            tiposEstatus.add(tiposEstatu);
            tiposEstatu = new MessageStatus();
            tiposEstatu.setTipoEstatusId((byte)2);
            tiposEstatu.nombre = messageResources.getResource("mensaje.status.procesado");
            tiposEstatus.add(tiposEstatu);
            return tiposEstatus;
        }

        public static String getNombre(byte status)
        {
            String nombre = "";
            VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
            if(status == 1)
                nombre = messageResources.getResource("mensaje.status.pendiente");
            else
            if(status == 2)
                nombre = messageResources.getResource("mensaje.status.procesado");
            return nombre;
        }

        private byte tipoEstatusId;
        private String nombre;
        private static final byte STATUS_PENDIENTE = 1;
        private static final byte STATUS_PROCESADO = 2;


        public MessageStatus()
        {
        }
    }

    public static class MessageType
    {

        private static Byte getType(Byte status)
        {
            if(status.byteValue() == 0)
                return new Byte((byte)0);
            else
                return null;
        }

        public static Byte getTypeAlerta()
        {
            return new Byte((byte)0);
        }

        private static final byte TYPE_ALERTA = 0;


        public MessageType()
        {
        }
    }

    public static class MessageTypeEvent
    {

        private static Byte getTypeEvent(Byte status)
        {
            if(status.byteValue() == 1)
                return new Byte((byte)1);
            if(status.byteValue() == 2)
                return new Byte((byte)2);
            else
                return null;
        }

        public static byte getMessageTypeEventCalcular()
        {
            return 1;
        }

        public static byte getMessageTypeEventImportar()
        {
            return 2;
        }

        public byte getTipoEventoId()
        {
            return tipoEventoId;
        }

        public void setTipoEventoId(byte tipoEventoId)
        {
            this.tipoEventoId = tipoEventoId;
        }

        public String getNombre()
        {
            return nombre;
        }

        public void setNombre(String nombre)
        {
            this.nombre = nombre;
        }

        public static List getTiposEventos()
        {
            VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
            List tiposEventos = new ArrayList();
            MessageTypeEvent tipoEvento = new MessageTypeEvent();
            tipoEvento.setTipoEventoId((byte)1);
            tipoEvento.nombre = messageResources.getResource("mensaje.tipoevento.calcular");
            tiposEventos.add(tipoEvento);
            tipoEvento = new MessageTypeEvent();
            tipoEvento.setTipoEventoId((byte)2);
            tipoEvento.nombre = messageResources.getResource("mensaje.tipoevento.importar");
            tiposEventos.add(tipoEvento);
            return tiposEventos;
        }

        public static String getNombre(byte tipoEvento)
        {
            String nombre = "";
            VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
            if(tipoEvento == 1)
                nombre = messageResources.getResource("mensaje.tipoevento.calcular");
            else
            if(tipoEvento == 2)
                nombre = messageResources.getResource("mensaje.tipoevento.importar");
            return nombre;
        }

        private byte tipoEventoId;
        private String nombre;
        private static final byte MESSAGE_TIPO_EVENTO_CALCULAR = 1;
        private static final byte MESSAGE_TIPO_EVENTO_IMPORTAR = 2;

        public MessageTypeEvent()
        {
        }
    }


    public Message(Long usuarioId, Date fecha, Byte estatus, String mensaje, Byte tipo, Usuario usuario, String fuente)
    {
        this.usuarioId = usuarioId;
        this.fecha = fecha;
        this.estatus = estatus;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.usuario = usuario;
        this.fuente = fuente;
    }

    public Message()
    {
    }

    public Long getUsuarioId()
    {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId)
    {
        this.usuarioId = usuarioId;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public Byte getEstatus()
    {
        return estatus;
    }

    public void setEstatus(Byte estatus)
    {
        this.estatus = MessageStatus.getStatus(estatus);
    }

    public String getMensaje()
    {
        return mensaje;
    }

    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
    }

    public Byte getTipo()
    {
        return tipo;
    }

    public void setTipo(Byte tipo)
    {
        this.tipo = MessageType.getType(tipo);
    }

    public String getFuente()
    {
        return fuente;
    }

    public void setFuente(String fuente)
    {
        this.fuente = fuente;
    }

    public String getEstatusNombre()
    {
        return MessageStatus.getNombre(estatus.byteValue());
    }

    public void setEstatusNombre(String estatusNombre)
    {
        this.estatusNombre = estatusNombre;
    }

    public String getSeparador()
    {
        return "!;!";
    }

    public String getId()
    {
        String servicioId = null;
        if(usuarioId != null)
            servicioId = usuarioId.toString();
        if(fecha != null)
            servicioId = (new StringBuilder(String.valueOf(servicioId))).append(getSeparador()).append(fecha.getTime()).toString();
        if(fuente != null)
            servicioId = (new StringBuilder(String.valueOf(servicioId))).append(getSeparador()).append(fuente).toString();
        return servicioId;
    }

    public String getResponsable()
    {
        String descripcion = usuario.getFullName();
        if(descripcion != null && descripcion.length() > 200)
            descripcion = descripcion.substring(0, 200);
        return descripcion;
    }

    public String getFechaFormateada()
    {
        return VgcFormatter.formatearFecha(getFecha(), "yyyy-MM-dd HH:mm:ss");
    }

    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
        {
            return false;
        } else
        {
            Message other = (Message)obj;
            return (new EqualsBuilder()).append(getUsuarioId(), other.getUsuarioId()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getUsuarioId()).append(getFecha()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Long usuarioId;
    private Date fecha;
    private Byte estatus;
    private String mensaje;
    private Usuario usuario;
    private Byte tipo;
    private String fuente;
    private String estatusNombre;
}