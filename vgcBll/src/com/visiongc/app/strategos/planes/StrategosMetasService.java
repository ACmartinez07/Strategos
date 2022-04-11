package com.visiongc.app.strategos.planes;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.framework.model.Usuario;
import java.util.List;

public abstract interface StrategosMetasService
  extends StrategosService
{
  public abstract int deleteMeta(Meta paramMeta, Usuario paramUsuario);
  
  public abstract int saveMeta(Meta paramMeta, Usuario paramUsuario);
  
  public abstract int saveMetas(List paramList, Usuario paramUsuario);
  
  public abstract List getMetasAnuales(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Boolean paramBoolean);
  
  public abstract List getMetasAnualesParciales(Long paramLong1, Long paramLong2, Byte paramByte, Integer paramInteger1, Integer paramInteger2, Boolean paramBoolean);
  
  public abstract Meta getValorInicial(Long paramLong1, Long paramLong2);
  
  public abstract Meta getValorInicial(Long paramLong1, Long paramLong2, Byte paramByte1, Byte paramByte2, Integer paramInteger);
  
  public abstract List getMetasParcialesComoMediciones(Byte paramByte1, Byte paramByte2, Boolean paramBoolean1, Byte paramByte3, Boolean paramBoolean2, Byte paramByte4, Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List getMetasParcialesPorPeriodo(Long paramLong1, Byte paramByte1, Long paramLong2, Integer paramInteger, Byte paramByte2);
  
  public abstract List getMetasParciales(Long paramLong1, Long paramLong2, Byte paramByte1, Byte paramByte2, Byte paramByte3, Byte paramByte4, Byte paramByte5, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract int deleteMetas(Long paramLong1, Long paramLong2, Byte paramByte, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List getAnosConMetaParcial(Long paramLong1, Long paramLong2);
  
  public abstract int copyMetas(Long paramLong1, Long paramLong2, Long paramLong3, Usuario paramUsuario);
  
  public abstract List getMetasParaEjecutar(Long paramLong1, List<Long> paramList1, Long paramLong2, boolean paramBoolean1, Long paramLong3, Integer paramInteger1);
}
