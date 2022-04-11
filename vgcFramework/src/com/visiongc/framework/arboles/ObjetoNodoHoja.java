package com.visiongc.framework.arboles;


public interface ObjetoNodoHoja
{

    public abstract String getNombreCampoNombre();

    public abstract String getNombreCampoId();

    public abstract String getNombreCampoPadreId();

    public abstract Byte getTipoNodoHoja();

    public abstract Class getClaseIntermedia();

    public abstract String getNombreCampoObjetoIdIntermedio();

    public abstract String getNombreCampoPadreIdIntermedio();
}