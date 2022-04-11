package com.visiongc.app.strategos.plancuentas;

import com.visiongc.app.strategos.plancuentas.model.Cuenta;
import com.visiongc.app.strategos.plancuentas.model.MascaraCodigoPlanCuentas;
import com.visiongc.commons.VgcService;
import com.visiongc.framework.model.Usuario;
import java.util.List;

public abstract interface StrategosCuentasService
  extends VgcService
{
  public abstract int deleteCuenta(Cuenta paramCuenta, Usuario paramUsuario);
  
  public abstract int saveCuenta(Cuenta paramCuenta, Usuario paramUsuario);
  
  public abstract List getMascaras();
  
  public abstract int definirMascara(MascaraCodigoPlanCuentas paramMascaraCodigoPlanCuentas, Usuario paramUsuario);
  
  public abstract List getMaximoNivelGrupo();
  
  public abstract Cuenta crearCuentaRaiz(Usuario paramUsuario);
}
