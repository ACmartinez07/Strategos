package com.visiongc.framework.modulo;

import com.visiongc.commons.VgcService;
import com.visiongc.framework.model.Modulo;

public interface ModuloService
    extends VgcService
{

    public abstract Modulo getModulo(String s);
}