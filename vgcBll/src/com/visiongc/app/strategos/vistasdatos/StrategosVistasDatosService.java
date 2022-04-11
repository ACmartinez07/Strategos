package com.visiongc.app.strategos.vistasdatos;

import com.visiongc.app.strategos.StrategosService;

public abstract interface StrategosVistasDatosService
  extends StrategosService
{
  public abstract String getValorDimensiones(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Boolean paramBoolean);
}
