package com.visiongc.commons.struts.action;

import com.visiongc.commons.struts.action.util.ByteArrayStreamInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public abstract class VgcLoadDocumentAction extends org.apache.struts.actions.DownloadAction
{
  public VgcLoadDocumentAction() {}
  
  protected final org.apache.struts.actions.DownloadAction.StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    String contentType = "text/html";
    
    byte[] documento = getDocument(mapping, form, request, response, contentType);
    
    return new ByteArrayStreamInfo(contentType, documento);
  }
  
  protected abstract byte[] getDocument(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception;
}
