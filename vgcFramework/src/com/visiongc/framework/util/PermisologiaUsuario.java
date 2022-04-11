// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 31/08/2018 9:39:09 a. m.
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   PermisologiaUsuario.java

package com.visiongc.framework.util;

import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.PermisoUsuario;
import com.visiongc.framework.model.Usuario;
import java.util.*;

public class PermisologiaUsuario
{

    public PermisologiaUsuario(long usuarioId)
    {
        this.usuarioId = 0L;
        isAdmin = false;
        loaded = false;
        permisos = new HashMap();
        loadPermisos(usuarioId);
    }

    public PermisologiaUsuario(Usuario u, Long organizacionId)
    {
        usuarioId = 0L;
        isAdmin = false;
        loaded = false;
        permisos = new HashMap();
        loadPermisosCatcha(u, organizacionId);
    }

    public long getUsuarioId()
    {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId)
    {
        if(usuarioId != this.usuarioId)
            loadPermisos(usuarioId);
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    public boolean loadPermisos(long usuarioId)
    {
        String permisoId = "";
        HashMap organizaciones = null;
        permisos = new HashMap();
        this.usuarioId = usuarioId;
        FrameworkService fs = FrameworkServiceFactory.getInstance().openFrameworkService();
        Usuario u = (Usuario)fs.load(Usuario.class, new Long(usuarioId));
        if(u != null)
        {
            isAdmin = u.getIsAdmin().booleanValue();
            List lista = fs.getPermisosPorUsuario(usuarioId);
            PermisoUsuario pu;
            for(Iterator i = lista.iterator(); i.hasNext(); organizaciones.put(pu.getOrganizacionId(), null))
            {
                pu = (PermisoUsuario)i.next();
                if(!pu.getPermisoId().equals(permisoId))
                {
                    permisoId = pu.getPermisoId();
                    organizaciones = new HashMap();
                    permisos.put(permisoId, organizaciones);
                }
            }

        } else
        {
            isAdmin = false;
        }
        fs.close();
        loaded = true;
        return loaded;
    }

    public boolean loadPermisosCatcha(Usuario u, Long organizacionId)
    {
        String permisoId = "";
        HashMap organizaciones = null;
        permisos = new HashMap();
        usuarioId = u.getUsuarioId().longValue();
        if(u != null)
        {
            isAdmin = false;
            permisoId = "PLAN";
            organizaciones = new HashMap();
            organizaciones.put(organizacionId, null);
            permisos.put(permisoId, organizaciones);
            permisoId = "VISTA_VIEWALL";
            organizaciones = new HashMap();
            organizaciones.put(organizacionId, null);
            permisos.put(permisoId, organizaciones);
            permisoId = "INDICADOR_VIEWALL";
            organizaciones = new HashMap();
            organizaciones.put(organizacionId, null);
            permisos.put(permisoId, organizaciones);
        }
        loaded = true;
        return loaded;
    }

    public boolean tienePermiso(String permisoId)
    {
        boolean tienePermiso = isAdmin;
        if(!tienePermiso)
            tienePermiso = permisos.containsKey(permisoId);
        return tienePermiso;
    }

    public boolean tienePermiso(String permisoId, long organizacionId)
    {
        boolean tienePermiso = isAdmin;
        if(!tienePermiso)
        {
            HashMap organizaciones = (HashMap)permisos.get(permisoId);
            if(organizaciones != null)
                tienePermiso = organizaciones.containsKey(new Long(organizacionId));
        }
        return tienePermiso;
    }

    private long usuarioId;
    private boolean isAdmin;
    private boolean loaded;
    private HashMap permisos;
}