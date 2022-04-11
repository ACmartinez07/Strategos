package com.visiongc.framework.model;

import com.visiongc.framework.arboles.NodoArbol;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Permiso
    implements Serializable, Comparable, NodoArbol
{

    public Permiso(String permisoId, String permiso, String padreId, Integer nivel, Integer grupo, Integer global, Set grupos, 
            Permiso padre, Set hijos)
    {
        this.permisoId = permisoId;
        this.permiso = permiso;
        this.padreId = padreId;
        this.nivel = nivel;
        this.grupo = grupo;
        this.global = global;
        this.grupos = grupos;
        this.padre = padre;
        this.hijos = hijos;
    }

    public Permiso()
    {
    }

    public Permiso(String permisoId, String permiso, Set grupos)
    {
        this.permisoId = permisoId;
        this.permiso = permiso;
        this.grupos = grupos;
    }

    public String getPermisoId()
    {
        return permisoId;
    }

    public void setPermisoId(String permisoId)
    {
        this.permisoId = permisoId;
    }

    public String getPermiso()
    {
        return permiso;
    }

    public void setPermiso(String permiso)
    {
        this.permiso = permiso;
    }

    public String getPadreId()
    {
        return padreId;
    }

    public void setPadreId(String padreId)
    {
        this.padreId = padreId;
    }

    public Integer getNivel()
    {
        return nivel;
    }

    public void setNivel(Integer nivel)
    {
        this.nivel = nivel;
    }

    public Integer getGrupo()
    {
        return grupo;
    }

    public void setGrupo(Integer grupo)
    {
        this.grupo = grupo;
    }

    public Integer getGlobal()
    {
        return global;
    }

    public void setGlobal(Integer global)
    {
        this.global = global;
    }

    public Set getGrupos()
    {
        return grupos;
    }

    public void setGrupos(Set grupos)
    {
        this.grupos = grupos;
    }

    public Permiso getPadre()
    {
        return padre;
    }

    public void setPadre(Permiso padre)
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

    public Boolean getHijosCargados()
    {
        return hijosCargados;
    }

    public void setHijosCargados(Boolean hijosCargados)
    {
        this.hijosCargados = hijosCargados;
    }

    public Collection getNodoArbolHijos()
    {
        return hijos;
    }

    public boolean getNodoArbolHijosCargados()
    {
        if(hijosCargados == null)
            hijosCargados = new Boolean(false);
        return hijosCargados.booleanValue();
    }

    public String getNodoArbolId()
    {
        if(getPermisoId() != null)
            return getPermisoId().toString();
        else
            return null;
    }

    public String getNodoArbolNombre()
    {
        return getPermiso();
    }

    public String getNodoArbolNombreCampoId()
    {
        return "permisoId";
    }

    public String getNodoArbolNombreCampoNombre()
    {
        return "permiso";
    }

    public String getNodoArbolNombreCampoPadreId()
    {
        return "padreId";
    }

    public String getNodoArbolNombreImagen(Byte tipo)
    {
        if(tipo.byteValue() == 1)
            return "Permiso";
        else
            return "";
    }

    public String getNodoArbolNombreToolTipImagen(Byte tipo)
    {
        return "";
    }

    public void setNodoArbolPadre(NodoArbol nodoArbol)
    {
        setPadre((Permiso)nodoArbol);
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
        hijosCargados = new Boolean(cargados);
    }

    public void setNodoArbolNombre(String nombre)
    {
        setPermiso(nombre);
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("permisoId", getPermisoId()).toString();
    }

    public int compareTo(Object o)
    {
        Permiso p = (Permiso)o;
        return getPermisoId().compareTo(p.getPermisoId());
    }

    static final long serialVersionUID = 0L;
    private String permisoId;
    private String permiso;
    private String padreId;
    private Integer nivel;
    private Integer grupo;
    private Integer global;
    private Set grupos;
    private Permiso padre;
    private Set hijos;
    private Boolean hijosCargados;
}