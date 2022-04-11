package com.visiongc.framework.model;

import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.permisologia.OrganizacionPermisologia;
import java.io.Serializable;
import java.util.*;
import org.apache.commons.lang.builder.ToStringBuilder;

// Referenced classes of package com.visiongc.framework.model:
//            OrganizacionBasica

public class Organizacion extends OrganizacionBasica
    implements Serializable, NodoArbol, OrganizacionPermisologia
{

    public Organizacion(Long organizacionId, Long padreId, String nombre, String direccion, Date creado, Date modificado, Long creadoId, 
            Long modificadoId, Set UsuarioGrupos, Organizacion padre, Set hijos, boolean hijosCargados)
    {
        super.setOrganizacionId(organizacionId);
        super.setPadreId(padreId);
        super.setNombre(nombre);
        super.setDireccion(direccion);
        super.setCreado(creado);
        super.setModificado(modificado);
        super.setCreadoId(creadoId);
        super.setModificadoId(modificadoId);
        this.hijosCargados = hijosCargados;
        usuarioGrupos = UsuarioGrupos;
        this.padre = padre;
        this.hijos = hijos;
    }

    public Organizacion()
    {
    }

    public Organizacion(Long organizacionId, Long padreId, String nombre, Set UsuarioGrupos, Organizacion padre, Set hijos)
    {
        super.setOrganizacionId(organizacionId);
        super.setPadreId(padreId);
        super.setNombre(nombre);
        usuarioGrupos = UsuarioGrupos;
        this.padre = padre;
        this.hijos = hijos;
    }

    public Collection getNodoArbolHijos()
    {
        return hijos;
    }

    public boolean getNodoArbolHijosCargados()
    {
        return hijosCargados;
    }

    public String getNodoArbolId()
    {
        if(getOrganizacionId() != null)
            return getOrganizacionId().toString();
        else
            return null;
    }

    public String getNodoArbolNombre()
    {
        return getNombre();
    }

    public String getNodoArbolNombreCampoId()
    {
        return "organizacionId";
    }

    public String getNodoArbolNombreCampoNombre()
    {
        return "nombre";
    }

    public String getNodoArbolNombreCampoPadreId()
    {
        return "padreId";
    }

    public String getNodoArbolNombreImagen(Byte tipo)
    {
        if(tipo.byteValue() == 1)
            return "Organizacion";
        else
            return "";
    }

    public String getNodoArbolNombreToolTipImagen(Byte tipo)
    {
        return "";
    }

    public NodoArbol getNodoArbolPadre()
    {
        return padre;
    }

    public String getNodoArbolPadreId()
    {
        if(getPadreId() != null)
            return getPadreId().toString();
        else
            return null;
    }

    public void setNodoArbolHijos(Collection nodoArbolHijos)
    {
        hijos = (Set)nodoArbolHijos;
    }

    public void setNodoArbolHijosCargados(boolean cargados)
    {
        hijosCargados = cargados;
    }

    public void setNodoArbolNombre(String nombre)
    {
        setNombre(nombre);
    }

    public void setNodoArbolPadre(NodoArbol nodoArbol)
    {
        setPadre((Organizacion)nodoArbol);
    }

    public boolean getHijosCargados()
    {
        return hijosCargados;
    }

    public void setHijosCargados(boolean hijosCargados)
    {
        this.hijosCargados = hijosCargados;
    }

    public Set getUsuarioGrupos()
    {
        return usuarioGrupos;
    }

    public void setUsuarioGrupos(Set UsuarioGrupos)
    {
        usuarioGrupos = UsuarioGrupos;
    }

    public Organizacion getPadre()
    {
        return padre;
    }

    public void setPadre(Organizacion padre)
    {
        this.padre = padre;
    }

    public Set getHijos()
    {
        return hijos;
    }

    public void setHijos(Set hijos)
    {
        this.hijos = hijos;
    }

    public int compareTo(Object o)
    {
        Organizacion or = (Organizacion)o;
        return getOrganizacionId().compareTo(or.getOrganizacionId());
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("organizacionId", getOrganizacionId()).toString();
    }

    static final long serialVersionUID = 0L;
    private boolean hijosCargados;
    private Set usuarioGrupos;
    private Organizacion padre;
    private Set hijos;
}