package com.visiongc.framework.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PersonaBasica
    implements Serializable
{

    public PersonaBasica(Long personaId, String cedula, String nombre, Date fechaNacimiento, String sexo, String direccion, String telefono, 
            String email, String password, Long usuarioId)
    {
        this.personaId = personaId;
        this.cedula = cedula;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.usuarioId = usuarioId;
    }

    public PersonaBasica()
    {
    }

    public PersonaBasica(Long personaId, String cedula, String nombre)
    {
        this.personaId = personaId;
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public Long getPersonaId()
    {
        return personaId;
    }

    public void setPersonaId(Long personaId)
    {
        this.personaId = personaId;
    }

    public String getCedula()
    {
        return cedula;
    }

    public void setCedula(String cedula)
    {
        this.cedula = cedula;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento()
    {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento)
    {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo()
    {
        return sexo;
    }

    public void setSexo(String sexo)
    {
        this.sexo = sexo;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Long getUsuarioId()
    {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId)
    {
        this.usuarioId = usuarioId;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("personaId", getPersonaId()).toString();
    }

    static final long serialVersionUID = 0L;
    private Long personaId;
    private String cedula;
    private String nombre;
    private Date fechaNacimiento;
    private String sexo;
    private String direccion;
    private String telefono;
    private String email;
    private String password;
    private Long usuarioId;
}