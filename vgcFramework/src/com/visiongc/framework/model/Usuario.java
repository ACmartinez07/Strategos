package com.visiongc.framework.model;

import com.visiongc.commons.util.Password;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.io.Serializable;
import java.util.*;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Usuario
    implements Serializable
{

    public Usuario(Long usuarioId, String fullName, String UId, Boolean isAdmin, Boolean isConnected, Boolean isSystem, Date timeStamp, 
            Date creado, Date modificado, Long creadoId, Long modificadoId, String instancia, Integer estatus, Boolean bloqueado, 
            String pwd, Set usuarioGrupos, Map configuracionUsuario, Date ultimaModifPwd, Boolean deshabilitado, Boolean forzarCambiarpwd)
    {
        permitirConeccionVirtual = Boolean.valueOf(false);
        this.usuarioId = usuarioId;
        this.fullName = fullName;
        this.UId = UId;
        this.isAdmin = isAdmin;
        this.isConnected = isConnected;
        this.isSystem = isSystem;
        this.timeStamp = timeStamp;
        this.creado = creado;
        this.modificado = modificado;
        this.creadoId = creadoId;
        this.modificadoId = modificadoId;
        this.instancia = instancia;
        this.estatus = estatus;
        this.bloqueado = bloqueado;
        this.pwd = pwd;
        this.usuarioGrupos = usuarioGrupos;
        this.configuracionUsuario = configuracionUsuario;
        this.ultimaModifPwd = ultimaModifPwd;
        this.deshabilitado = deshabilitado;
        this.forzarCambiarpwd = forzarCambiarpwd;
    }

    public Usuario()
    {
        permitirConeccionVirtual = Boolean.valueOf(false);
    }

    public Usuario(Long usuarioId, String fullName, String UId, Boolean isAdmin, Boolean isSystem, Set usuarioGrupos, Map configuracionUsuario)
    {
        permitirConeccionVirtual = Boolean.valueOf(false);
        this.usuarioId = usuarioId;
        this.fullName = fullName;
        this.UId = UId;
        this.isAdmin = isAdmin;
        this.isSystem = isSystem;
        this.usuarioGrupos = usuarioGrupos;
        this.configuracionUsuario = configuracionUsuario;
    }

    public long getId()
    {
        return getUsuarioId().longValue();
    }

    public void setId(long id)
    {
        setUsuarioId(new Long(id));
    }

    public Long getUsuarioId()
    {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId)
    {
        this.usuarioId = usuarioId;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getUId()
    {
        return UId;
    }

    public void setUId(String UId)
    {
        this.UId = UId;
    }

    public Boolean getIsAdmin()
    {
        return isAdmin;
    }

    public void setIsAdmin(Boolean inIsAdmin)
    {
        isAdmin = inIsAdmin;
    }

    public Boolean getIsSystem()
    {
        return isSystem;
    }

    public void setIsSystem(Boolean inIsSystem)
    {
        isSystem = inIsSystem;
    }

    public Boolean getIsConnected()
    {
        return isConnected;
    }

    public void setIsConnected(Boolean isConnected)
    {
        this.isConnected = isConnected;
    }

    public Date getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp)
    {
        this.timeStamp = timeStamp;
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

    public String getInstancia()
    {
        return instancia;
    }

    public void setInstancia(String instancia)
    {
        this.instancia = instancia;
    }

    public String getPwd()
    {
        return pwd;
    }

    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }

    public Date getUltimaModifPwd()
    {
        return ultimaModifPwd;
    }

    public void setUltimaModifPwd(Date ultimaModifPwd)
    {
        this.ultimaModifPwd = ultimaModifPwd;
    }

    public String getPwdPlain()
    {
        try
        {
            return Password.decriptPassWord(pwd);
        }
        catch(Throwable t)
        {
            throw new ChainedRuntimeException(t.getMessage(), t);
        }
    }

    public void setPwdPlain(String pwd)
    {
        try
        {
            if(pwd != null && !pwd.equals(""))
                this.pwd = Password.encriptPassWord(pwd);
            else
                this.pwd = null;
        }
        catch(Throwable t)
        {
            throw new ChainedRuntimeException(t.getMessage(), t);
        }
    }

    public Set getUsuarioGrupos()
    {
        return usuarioGrupos;
    }

    public void setUsuarioGrupos(Set usuarioGrupos)
    {
        this.usuarioGrupos = usuarioGrupos;
    }

    public Map getConfiguracionUsuario()
    {
        return configuracionUsuario;
    }

    public void setConfiguracionUsuario(Map configuracionUsuario)
    {
        this.configuracionUsuario = configuracionUsuario;
    }

    public Set getSesiones()
    {
        return sesiones;
    }

    public void setSesiones(Set sesiones)
    {
        this.sesiones = sesiones;
    }

    public int getNroSesiones()
    {
        return getSesiones().size();
    }

    public Boolean getBloqueado()
    {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado)
    {
        if(bloqueado == null)
            this.bloqueado = new Boolean(false);
        else
            this.bloqueado = bloqueado;
    }

    public Integer getEstatus()
    {
        return estatus;
    }

    public void setEstatus(Integer estatus)
    {
        this.estatus = estatus;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("usuarioId", getUsuarioId()).toString();
    }

    public Boolean getDeshabilitado()
    {
        return deshabilitado;
    }

    public void setDeshabilitado(Boolean deshabilitado)
    {
        if(deshabilitado == null)
            this.deshabilitado = new Boolean(false);
        else
            this.deshabilitado = deshabilitado;
    }

    public Boolean getForzarCambiarpwd()
    {
        return forzarCambiarpwd;
    }

    public void setForzarCambiarpwd(Boolean forzarCambiarpwd)
    {
        this.forzarCambiarpwd = forzarCambiarpwd;
    }

    public Boolean getPermitirConeccionVirtual()
    {
        return permitirConeccionVirtual;
    }

    public void setPermitirConeccionVirtual(Boolean permitirConeccionVirtual)
    {
        this.permitirConeccionVirtual = permitirConeccionVirtual;
    }

    static final long serialVersionUID = 0L;
    private Long usuarioId;
    private String fullName;
    private String UId;
    private Boolean isAdmin;
    private Boolean isConnected;
    private Boolean isSystem;
    private Date timeStamp;
    private Date creado;
    private Date modificado;
    private Long creadoId;
    private Long modificadoId;
    private String instancia;
    private String pwd;
    private Date ultimaModifPwd;
    private Set usuarioGrupos;
    private Map configuracionUsuario;
    private Set sesiones;
    private Boolean bloqueado;
    private Integer estatus;
    private Boolean deshabilitado;
    private Boolean forzarCambiarpwd;
    private Boolean permitirConeccionVirtual;
}