package com.visiongc.app.strategos.indicadores;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionInsumo;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.framework.model.Usuario;
import java.util.List;

public abstract interface StrategosMedicionesInsumoService
  extends StrategosService
{
  public abstract int deleteMedicionInsumo(MedicionInsumo paramMedicion, Usuario paramUsuario);
  
  public abstract int saveMedicionInsumo(MedicionInsumo paramMedicion, Usuario paramUsuario);
  
  public abstract List<MedicionInsumo> getMedicionInsumo(Long paramLong1, Long paramLong2, Long paramLong3);
  
}
