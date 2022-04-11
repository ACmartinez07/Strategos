package com.visiongc.app.strategos.planes;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosPlantillasPlanesService
  extends StrategosService
{
  public abstract PaginaLista getPlantillasPlanes(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deletePlantillaPlanes(PlantillaPlanes paramPlantillaPlanes, Usuario paramUsuario);
  
  public abstract int savePlantillaPlanes(PlantillaPlanes paramPlantillaPlanes, Usuario paramUsuario);
  
  public abstract List getNivelesPlantillaPlan(Long paramLong);
}
