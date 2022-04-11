package com.visiongc.app.strategos.causas;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.causas.model.Causa;
import com.visiongc.framework.model.Usuario;

public abstract interface StrategosCausasService
  extends StrategosService
{
  public abstract int deleteCausa(Causa paramCausa, Usuario paramUsuario);
  
  public abstract int saveCausa(Causa paramCausa, Usuario paramUsuario);
  
  public abstract Causa crearCausaRaiz(Usuario paramUsuario);
}
