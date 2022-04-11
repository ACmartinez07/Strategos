package com.visiongc.framework.web.struts.forms;

import org.apache.struts.upload.FormFile;









public class LicenciaForm
  extends EditarObjetoForm
{
  private static final long serialVersionUID = 1L;
  private FormFile fileForm;
  private Byte status;
  private String respuesta;
  
  public LicenciaForm() {}
  
  public FormFile getFileForm()
  {
    return fileForm;
  }
  
  public void setFileForm(FormFile fileForm)
  {
    this.fileForm = fileForm;
  }
  
  public Byte getStatus()
  {
    return status;
  }
  
  public void setStatus(Byte status)
  {
    this.status = LicenciaStatus.getLicenciaStatus(status);
  }
  
  public String getRespuesta()
  {
    return respuesta;
  }
  
  public void setRespuesta(String respuesta)
  {
    this.respuesta = respuesta;
  }
  
  public static class LicenciaStatus {
    private static final byte LICENCIA_STATUS_SAVE_SUCCESS = 0;
    private static final byte LICENCIA_STATUS_SAVE_NOSUCCESS = 1;
    
    public LicenciaStatus() {}
    
    private static Byte getLicenciaStatus(Byte status) {
      if (status.byteValue() == 0)
        return new Byte((byte)0);
      if (status.byteValue() == 1) {
        return new Byte((byte)1);
      }
      return null;
    }
    
    public static Byte getLicenciaStatusSaveSuccess()
    {
      return new Byte((byte)0);
    }
    
    public static Byte getLicenciaStatusSaveNoSuccess()
    {
      return new Byte((byte)1);
    }
  }
  
  public void clear()
  {
    status = null;
    respuesta = null;
    fileForm = null;
  }
}
