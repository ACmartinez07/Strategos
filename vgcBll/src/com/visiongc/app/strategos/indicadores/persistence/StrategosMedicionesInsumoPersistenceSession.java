package com.visiongc.app.strategos.indicadores.persistence;

import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionInsumo;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import java.util.List;

public abstract interface StrategosMedicionesInsumoPersistenceSession
  extends StrategosPersistenceSession
{
	 public abstract int deleteMedicionInsumo(MedicionInsumo medicion)
			    throws Throwable;

	public abstract List<MedicionInsumo> getMedicionesInsumo(Long paramLong1,
			Long paramLong2, Long paramLong3);
}
