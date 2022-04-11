package com.visiongc.app.strategos.util;

import java.io.Serializable;






public class GraficoUtil
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte GRAFICO_NOTIPO = 0;
  private static final byte GRAFICO_LINEA = 1;
  private static final byte GRAFICO_COLUMNA = 2;
  private static final byte GRAFICO_BARRA = 3;
  private static final byte GRAFICO_AREA = 4;
  private static final byte GRAFICO_TORTA = 5;
  private static final byte GRAFICO_PARETO = 6;
  private static final byte GRAFICO_MULTIPLETORTA = 7;
  private static final byte GRAFICO_COMBINADO = 8;
  private static final byte GRAFICO_CASCADA = 9;
  private static final byte GRAFICO_TORTA3D = 10;
  private static final byte GRAFICO_BARRA3D = 11;
  private static final byte GRAFICO_BARRAAPILADA3D = 12;
  private static final byte GRAFICO_LINEA3D = 13;
  
  public GraficoUtil() {}
  
  public static Byte getGraficoNoTipo()
  {
    return new Byte((byte)0);
  }
  
  public static Byte getGraficoLinea()
  {
    return new Byte((byte)1);
  }
  
  public static Byte getGraficoColumna()
  {
    return new Byte((byte)2);
  }
  
  public static Byte getGraficoBarra()
  {
    return new Byte((byte)3);
  }
  
  public static Byte getGraficoArea()
  {
    return new Byte((byte)4);
  }
  
  public static Byte getGraficoTorta()
  {
    return new Byte((byte)5);
  }
  
  public static Byte getGraficoPareto()
  {
    return new Byte((byte)6);
  }
  
  public static Byte getGraficoMultipleTorta()
  {
    return new Byte((byte)7);
  }
  
  public static Byte getGraficoCombinado()
  {
    return new Byte((byte)8);
  }
  
  public static Byte getGraficoCascada()
  {
    return new Byte((byte)9);
  }
  
  public static Byte getGraficoTorta3D()
  {
    return new Byte((byte)10);
  }
  
  public static Byte getGraficoBarra3D()
  {
    return new Byte((byte)11);
  }
  
  public static Byte getGraficoBarraApilada3D()
  {
    return new Byte((byte)12);
  }
  
  public static Byte getGraficoLinea3D()
  {
    return new Byte((byte)13);
  }
}
