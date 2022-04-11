package com.visiongc.framework.web.struts.validators;

import com.visiongc.commons.util.VgcResourceManager;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

public class VgcFrameworkValidator
{
  public VgcFrameworkValidator() {}
  
  public static boolean validateCaracteresNoPermitidos(Object bean, ValidatorAction validatorAction, Field field, ActionMessages errors, Validator validator, HttpServletRequest request)
  {
    String texto = null;
    boolean hayCaracteresNoPermitidos = false;
    

    String caracteresNoPermitidos = VgcResourceManager.getResourceApp("validacion.framework.caracteresnopermitidos");
    
    if (isString(bean)) {
      texto = (String)bean;
    } else {
      texto = ValidatorUtils.getValueAsString(bean, field.getProperty());
    }
    
    String caracter = null;
    for (int i = 0; i < texto.length(); i++) {
      caracter = texto.substring(i, i + 1);
      if (caracteresNoPermitidos.indexOf(caracter) > -1) {
        errors.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));
        hayCaracteresNoPermitidos = true;
        break;
      }
    }
    
    if (hayCaracteresNoPermitidos) {
      return true;
    }
    return false;
  }
  


  public static boolean validateCaracteresNoPermitidosApp(Object bean, ValidatorAction validatorAction, Field field, ActionMessages errors, Validator validator, HttpServletRequest request)
  {
    String texto = null;
    boolean hayCaracteresNoPermitidos = false;
    String caracteresNoPermitidosApp = VgcResourceManager.getResourceApp("validacion.app.caracteresnopermitidos");
    
    if ((caracteresNoPermitidosApp == null) || (caracteresNoPermitidosApp.length() == 0)) {
      return false;
    }
    
    if (isString(bean)) {
      texto = (String)bean;
    } else {
      texto = ValidatorUtils.getValueAsString(bean, field.getProperty());
    }
    
    String caracter = null;
    for (int i = 0; i < texto.length(); i++) {
      caracter = texto.substring(i, i + 1);
      if (caracteresNoPermitidosApp.indexOf(caracter) > -1) {
        errors.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));
        hayCaracteresNoPermitidos = true;
        break;
      }
    }
    
    if (hayCaracteresNoPermitidos) {
      return true;
    }
    return false;
  }
  

  protected static boolean isString(Object o)
  {
    return o == null ? true : String.class.isInstance(o);
  }
}
