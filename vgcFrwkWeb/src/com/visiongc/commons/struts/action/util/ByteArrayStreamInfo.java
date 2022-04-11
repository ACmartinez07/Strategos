package com.visiongc.commons.struts.action.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.struts.actions.DownloadAction.StreamInfo;


public class ByteArrayStreamInfo
  implements org.apache.struts.actions.DownloadAction.StreamInfo
{
  protected String contentType;
  protected byte[] bytes;
  
  public ByteArrayStreamInfo(String contentType, byte[] bytes)
  {
    this.contentType = contentType;
    this.bytes = bytes;
  }
  
  public String getContentType()
  {
    return contentType;
  }
  
  public InputStream getInputStream() throws IOException
  {
    return new ByteArrayInputStream(bytes);
  }
}
