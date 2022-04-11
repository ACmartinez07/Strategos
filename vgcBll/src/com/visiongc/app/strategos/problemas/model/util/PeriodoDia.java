package com.visiongc.app.strategos.problemas.model.util;

import java.util.Calendar;

public class PeriodoDia
{
  public PeriodoDia() {}
  
  public String saludoEmail()
  {
    Calendar calendar = Calendar.getInstance();
    String saludoEmail = "";
    com.visiongc.commons.util.VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos", 1);
    
    if (calendar.get(9) == 0) {
      saludoEmail = messageResources.getResource("configuracion.mensajeemail.saludo.buenosdias");
    } else {
      saludoEmail = messageResources.getResource("configuracion.mensajeemail.saludo.buenastardes");
    }
    return saludoEmail;
  }
}
