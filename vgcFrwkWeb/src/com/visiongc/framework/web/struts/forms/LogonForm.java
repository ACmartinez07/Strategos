package com.visiongc.framework.web.struts.forms;

import java.util.Random;
import org.apache.struts.validator.ValidatorForm;

public class LogonForm
  extends ValidatorForm
{
  private String challenge = null;
  private String username = null;
  private String password = null;
  private Boolean isConnected = Boolean.valueOf(false);
  private String pwdEncript = null;
  private String tipoExplorer = null;
  
  private int tamanoMinClave = 0;
  
  static final long serialVersionUID = 0L;
  

  public LogonForm()
  {
    Random gen = new Random();
    
    int intGen = gen.nextInt();
    if (intGen < 0) {
      intGen = -intGen;
    }
    challenge = String.valueOf(intGen);
    intGen = gen.nextInt();
    
    if (intGen < 0)
      intGen = -intGen;
    challenge = (String.valueOf(intGen) + "." + challenge);
  }
  
  public String getChallenge()
  {
    return challenge;
  }
  
  public void setChallenge(String challenge)
  {
    this.challenge = challenge;
  }
  
  public String getPassword()
  {
    return password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getUsername()
  {
    return username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public int getTamanoMinClave()
  {
    return tamanoMinClave;
  }
  
  public void setTamanoMinClave(int tamanoMinClave)
  {
    this.tamanoMinClave = tamanoMinClave;
  }
  
  public Boolean getIsConnected()
  {
    return isConnected;
  }
  
  public void setIsConnected(Boolean isConnected)
  {
    this.isConnected = isConnected;
  }
  
  public String getPwdEncript()
  {
    return pwdEncript;
  }
  
  public void setPwdEncript(String pwdEncript)
  {
    this.pwdEncript = pwdEncript;
  }
  
  public String getTipoExplorer()
  {
    return tipoExplorer;
  }
  
  public void setTipoExplorer(String tipoExplorer)
  {
    this.tipoExplorer = tipoExplorer;
  }
}
