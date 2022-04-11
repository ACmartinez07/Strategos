package com.visiongc.commons.struts;

import com.visiongc.framework.model.Permiso;

public class StrutsActionRight extends Permiso
{

    public StrutsActionRight(String actionName, boolean noAplica, boolean aplicaOrganizacion, String permisoId)
    {
        this.noAplica = false;
        this.aplicaOrganizacion = false;
        super.setPermisoId(permisoId);
        this.actionName = actionName;
        this.noAplica = noAplica;
        this.aplicaOrganizacion = aplicaOrganizacion;
    }

    public String getActionName()
    {
        return actionName;
    }

    public void setActionName(String actionName)
    {
        this.actionName = actionName;
    }

    public boolean getNoAplica()
    {
        return noAplica;
    }

    public void setNoAplica(boolean noAplica)
    {
        this.noAplica = noAplica;
    }

    public boolean getAplicaOrganizacion()
    {
        return aplicaOrganizacion;
    }

    public void setAplicaOrganizacion(boolean aplicaOrganizacion)
    {
        this.aplicaOrganizacion = aplicaOrganizacion;
    }

    static final long serialVersionUID = 0L;
    private String actionName;
    private boolean noAplica;
    private boolean aplicaOrganizacion;
}