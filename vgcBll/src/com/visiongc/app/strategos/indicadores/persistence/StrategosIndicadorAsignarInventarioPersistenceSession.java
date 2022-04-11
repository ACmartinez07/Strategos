package com.visiongc.app.strategos.indicadores.persistence;

import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.IndicadorAsignarInventario;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import java.util.List;

public abstract interface StrategosIndicadorAsignarInventarioPersistenceSession
  extends StrategosPersistenceSession
{
	 public abstract int deleteIndicadorInventario(IndicadorAsignarInventario indicadorInv)
			    throws Throwable;

	public abstract List<IndicadorAsignarInventario> getIndicadorInventario(Long paramLong1);
	
	public abstract boolean getInsumo(Long paramLong1);
	
	public abstract IndicadorAsignarInventario getIndicadorInventario(Long paramLong1, Long paramLong2);
	
	public abstract IndicadorAsignarInventario getIndicadorInsumo(Long paramLong1);
}
