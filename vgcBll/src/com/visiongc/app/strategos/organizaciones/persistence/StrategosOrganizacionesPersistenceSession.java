package com.visiongc.app.strategos.organizaciones.persistence;

import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import java.util.List;
import java.util.Map;

public abstract interface StrategosOrganizacionesPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract ListaMap getDependenciasOrganizaciones(OrganizacionStrategos paramOrganizacionStrategos);
  
  public abstract PaginaLista getOrganizaciones(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract List<?> getOrganizaciones(String[] paramArrayOfString1, String[] paramArrayOfString2, Map<?, ?> paramMap);
}
