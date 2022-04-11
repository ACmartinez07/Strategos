package com.visiongc.app.strategos.test;

import java.io.PrintStream;

public class Test2
{
  public Test2() {}
  
  public static void main(String[] args)
  {
    try
    {
      com.visiongc.app.strategos.planes.StrategosModelosService strategosModelosService = com.visiongc.app.strategos.impl.StrategosServiceFactory.getInstance().openStrategosModelosService();
      
      System.out.println("ok");
    }
    catch (Exception e)
    {
      System.out.println("ERROR: " + e.getMessage());
    }
  }
  
  private static void conteo(Integer c)
  {
    if (c.intValue() < 3) {
      c = new Integer(c.intValue() + 1);
      conteo(c);
    }
  }
}
