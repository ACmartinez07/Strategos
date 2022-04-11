package com.visiongc.framework.model;

import com.visiongc.commons.util.*;
import java.io.Serializable;
import java.util.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

// Referenced classes of package com.visiongc.framework.model:
//            Usuario

public class Servicio
    implements Serializable
{
    public static class ServicioStatus
    {

        private static Byte getServicioStatus(Byte status)
        {
            if(status.byteValue() == 1)
                return new Byte((byte)1);
            if(status.byteValue() == 2)
                return new Byte((byte)2);
            if(status.byteValue() == 3)
                return new Byte((byte)3);
            if(status.byteValue() == 4)
                return new Byte((byte)4);
            if(status.byteValue() == 5)
                return new Byte((byte)5);
            if(status.byteValue() == 6)
                return new Byte((byte)6);
            else
                return null;
        }

        public static List getTiposEstatus()
        {
            VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
            List tiposEstatus = new ArrayList();
            ServicioStatus tiposEstatu = new ServicioStatus();
            tiposEstatu.setTipoEstatusId((byte)1);
            tiposEstatu.nombre = messageResources.getResource("status.success");
            tiposEstatus.add(tiposEstatu);
            tiposEstatu = new ServicioStatus();
            tiposEstatu.setTipoEstatusId((byte)2);
            tiposEstatu.nombre = messageResources.getResource("status.no.success");
            tiposEstatus.add(tiposEstatu);
            tiposEstatu = new ServicioStatus();
            tiposEstatu.setTipoEstatusId((byte)3);
            tiposEstatu.nombre = messageResources.getResource("status.en.proceso");
            tiposEstatus.add(tiposEstatu);
            tiposEstatu = new ServicioStatus();
            tiposEstatu.setTipoEstatusId((byte)4);
            tiposEstatu.nombre = messageResources.getResource("status.cancelado");
            tiposEstatus.add(tiposEstatu);
            tiposEstatu = new ServicioStatus();
            tiposEstatu.setTipoEstatusId((byte)5);
            tiposEstatu.nombre = messageResources.getResource("status.iniciado");
            tiposEstatus.add(tiposEstatu);
            tiposEstatu = new ServicioStatus();
            tiposEstatu.setTipoEstatusId((byte)6);
            tiposEstatu.nombre = messageResources.getResource("status.visto");
            tiposEstatus.add(tiposEstatu);
            return tiposEstatus;
        }

        private static String getServicioStatusNombre(Byte status, VgcMessageResources messageResources)
        {
            if(status.byteValue() == 1)
                return messageResources.getResource("status.success");
            if(status.byteValue() == 2)
                return messageResources.getResource("status.no.success");
            if(status.byteValue() == 3)
                return messageResources.getResource("status.en.proceso");
            if(status.byteValue() == 4)
                return messageResources.getResource("status.cancelado");
            if(status.byteValue() == 5)
                return messageResources.getResource("status.iniciado");
            if(status.byteValue() == 6)
                return messageResources.getResource("status.visto");
            else
                return null;
        }

        public static Byte getServicioStatusSuccess()
        {
            return new Byte((byte)1);
        }

        public static Byte getServicioStatusNoSuccess()
        {
            return new Byte((byte)2);
        }

        public static Byte getServicioStatusEnProceso()
        {
            return new Byte((byte)3);
        }

        public static Byte getServicioStatusCancelado()
        {
            return new Byte((byte)4);
        }

        public static Byte getServicioStatusIniciado()
        {
            return new Byte((byte)5);
        }

        public static Byte getServicioStatusVisto()
        {
            return new Byte((byte)6);
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

        private byte tipoEstatusId;
        private String nombre;
        private static final byte SERVICIOSTATUS_SUCCESS = 1;
        private static final byte SERVICIOSTATUS_NOSUCCESS = 2;
        private static final byte SERVICIOSTATUS_ENPROCESO = 3;
        private static final byte SERVICIOSTATUS_CANCELADO = 4;
        private static final byte SERVICIOSTATUS_INICIADO = 5;
        private static final byte SERVICIOSTATUS_VISTO = 6;



        public ServicioStatus()
        {
        }
    }

    public static class ServicioTypeEvent
    {

        private static Byte getTypeEvent(Byte status)
        {
            if(status.byteValue() == 1)
                return new Byte((byte)1);
            if(status.byteValue() == 2)
                return new Byte((byte)2);
            if(status.byteValue() == 3)
                return new Byte((byte)3);
            else
                return null;
        }

        public static byte getServicioTypeEventCalcular()
        {
            return 1;
        }

        public static byte getServicioTypeEventImportar()
        {
            return 2;
        }
        
        public static byte getServicioTypeEventProtegerLiberar()
        {
            return 3;
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
            ServicioTypeEvent tipoEvento = new ServicioTypeEvent();
            tipoEvento.setTipoEventoId((byte)1);
            tipoEvento.nombre = messageResources.getResource("servicio.tipoevento.calcular");
            tiposEventos.add(tipoEvento);
            tipoEvento = new ServicioTypeEvent();
            tipoEvento.setTipoEventoId((byte)2);
            tipoEvento.nombre = messageResources.getResource("servicio.tipoevento.importar");
            tiposEventos.add(tipoEvento);
            tipoEvento = new ServicioTypeEvent();
            tipoEvento.setTipoEventoId((byte)3);
            tipoEvento.nombre = messageResources.getResource("servicio.tipoevento.protegerliberar");
            tiposEventos.add(tipoEvento);
            return tiposEventos;
        }

        public static String getNombre(byte tipoEvento)
        {
            String nombre = "";
            VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
            if(tipoEvento == 1)
                nombre = messageResources.getResource("servicio.tipoevento.calcular");
            else
            if(tipoEvento == 2)
                nombre = messageResources.getResource("servicio.tipoevento.importar");
            if(tipoEvento == 3)
                nombre = messageResources.getResource("servicio.tipoevento.protegerliberar");
            return nombre;
        }

        private byte tipoEventoId;
        private String nombre;
        private static final byte SERVICIO_TIPO_EVENTO_CALCULAR = 1;
        private static final byte SERVICIO_TIPO_EVENTO_IMPORTAR = 2;
        private static final byte SERVICIO_TIPO_EVENTO_PROTEGERLIBERAR = 3;

        public ServicioTypeEvent()
        {
        }
    }


    public Servicio(Long usuarioId, Date fecha, String nombre, Byte estatus, String mensaje, Usuario usuario, String log)
    {
        this.usuarioId = usuarioId;
        this.fecha = fecha;
        this.nombre = nombre;
        this.estatus = estatus;
        this.mensaje = mensaje;
        this.usuario = usuario;
        this.log = log;
        messageResources = VgcResourceManager.getMessageResources("Framework");
    }

    public Servicio()
    {
        messageResources = VgcResourceManager.getMessageResources("Framework");
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

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public int getEstatus()
    {
        return estatus.byteValue();
    }

    public void setEstatus(Byte estatus)
    {
        this.estatus = ServicioStatus.getServicioStatus(estatus);
    }

    public String getMensaje()
    {
        return mensaje;
    }

    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
    }

    public String getLog()
    {
        return log;
    }

    public void setLog(String log)
    {
        this.log = log;
    }

    public String getSeparador()
    {
        return "!;!";
    }

    public String getServicioId()
    {
        String servicioId = null;
        if(usuarioId != null)
            servicioId = usuarioId.toString();
        if(fecha != null)
            servicioId = (new StringBuilder(String.valueOf(servicioId))).append(getSeparador()).append(fecha.getTime()).toString();
        if(nombre != null)
            servicioId = (new StringBuilder(String.valueOf(servicioId))).append(getSeparador()).append(nombre).toString();
        return servicioId;
    }

    public String getDescripcionCorta()
    {
        String descripcion = mensaje;
        if(descripcion != null && descripcion.length() > 200)
            descripcion = descripcion.substring(0, 200);
        return descripcion;
    }

    public String getLogCorta()
    {
        String descripcion = log;
        if(descripcion != null && descripcion.length() > 200)
            descripcion = descripcion.substring(0, 200);
        return descripcion;
    }

    public String getResponsable()
    {
        String descripcion = usuario.getFullName();
        if(descripcion != null && descripcion.length() > 200)
            descripcion = descripcion.substring(0, 200);
        return descripcion;
    }

    public String getStatusNombre()
    {
        return ServicioStatus.getServicioStatusNombre(estatus, messageResources);
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
            Servicio other = (Servicio)obj;
            return (new EqualsBuilder()).append(getUsuarioId(), other.getUsuarioId()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getUsuarioId()).append(getFecha()).append(getNombre()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Long usuarioId;
    private Date fecha;
    private String nombre;
    private Byte estatus;
    private String mensaje;
    private Usuario usuario;
    private String log;
    private VgcMessageResources messageResources;
}