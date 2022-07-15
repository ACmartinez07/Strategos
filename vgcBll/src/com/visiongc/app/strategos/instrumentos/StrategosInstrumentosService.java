package com.visiongc.app.strategos.instrumentos;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.instrumentos.model.IndicadorInstrumento;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoIniciativa;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.instrumentos.model.util.ConfiguracionInstrumento;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosInstrumentosService
  extends StrategosService
{
  public abstract PaginaLista getInstrumentos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteInstrumentos(Instrumentos paramInstrumento, Usuario paramUsuario);
  
  public abstract int saveInstrumentos(Instrumentos paramInstrumento, Usuario paramUsuario, Boolean paramBoolean);
  
  public abstract int asociarInstrumento(Long paramLong1, Long paramLong2, Usuario paramUsuario);
  
  public abstract int desasociarInstrumento(Long paramLong1, Long paramLong2, Usuario paramUsuario);  
  
  public abstract List<InstrumentoIniciativa> getIniciativasInstrumento(Long paramLong);
  
  public abstract int saveIniciativaInstrumento(List<InstrumentoIniciativa> paramList, Usuario paramUsuario);
  
  public abstract int updatePesos(InstrumentoIniciativa paramInstrumento, Usuario paramUsuario);
  
  public abstract int asociarIndicador(Instrumentos paramInstrumento, Usuario paramUsuario);
  
  public abstract int desasociarIndicadores(IndicadorInstrumento paramIndicadorInstrumento, Usuario paramUsuario);
  
  public abstract int updateIndicadorAutomatico(Instrumentos paramInstrumento, Byte paramByte, ConfiguracionInstrumento paramConfigurcionInstrumento, Usuario paramUsuario);
  
  public abstract Instrumentos getValoresOriginales(Long paramLong);
  
  public abstract ConfiguracionInstrumento getConfiguracionInstrumento();
  
}
