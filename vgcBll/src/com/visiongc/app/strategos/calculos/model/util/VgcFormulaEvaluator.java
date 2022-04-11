package com.visiongc.app.strategos.calculos.model.util;

import com.bestcode.mathparser.IMathParser;
import com.bestcode.mathparser.MathParserFactory;
import java.util.ArrayList;
import java.util.List;

public class VgcFormulaEvaluator
{
  private IMathParser parser;
  
  public static List getListaFunciones()
  {
    ArrayList lista = new ArrayList();
    
    VgcFuncionFormula funcion = new VgcFuncionFormula();
    funcion.setNombre("ABS");
    funcion.setInterfaz("ABS()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("ATAN");
    funcion.setInterfaz("ATAN()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("CEILING");
    funcion.setInterfaz("CEILING()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("COS");
    funcion.setInterfaz("COS()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("COSH");
    funcion.setInterfaz("COSH()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("EXP");
    funcion.setInterfaz("EXP()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("FLOOR");
    funcion.setInterfaz("FLOOR()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("IF");
    funcion.setInterfaz("IF()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("LN");
    funcion.setInterfaz("LN()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("LOG");
    funcion.setInterfaz("LOG()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("LOG10");
    funcion.setInterfaz("LOG10()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("MAX");
    funcion.setInterfaz("MAX()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("MIN");
    funcion.setInterfaz("MIN()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("MOD");
    funcion.setInterfaz("MOD()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("PI");
    funcion.setInterfaz("PI");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("SIGN");
    funcion.setInterfaz("SIGN()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("SIN");
    funcion.setInterfaz("SIN()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("SINH");
    funcion.setInterfaz("SINH()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("SQRT");
    funcion.setInterfaz("SQRT()");
    lista.add(funcion);
    
    funcion = new VgcFuncionFormula();
    funcion.setNombre("TAN");
    funcion.setInterfaz("TAN()");
    lista.add(funcion);
    
    return lista;
  }
  
  public VgcFormulaEvaluator() {
    parser = MathParserFactory.createVgcParser();
  }
  
  public void setExpresion(String expresion) {
    expresion = expresion.replaceAll(";", ",");
    parser.setExpression(expresion);
  }
  
  public double evaluar() throws Exception {
    return parser.evaluate();
  }
  
  public boolean expresionEsValida() {
    try {
      parser.parse();
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
