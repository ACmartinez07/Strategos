package com.visiongc.commons.struts.action;

import com.visiongc.commons.struts.action.util.ByteArrayStreamInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.apache.struts.actions.DownloadAction.StreamInfo;


public abstract class VgcDownloadAction extends DownloadAction
{

    public VgcDownloadAction()
    {
    }

    protected final org.apache.struts.actions.DownloadAction.StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        String contentType[] = new String[1];
        contentType[0] = "text/html";
        byte datos[] = getDatosBinarios(mapping, form, request, response, contentType);
        return new ByteArrayStreamInfo(contentType[0], datos);
    }

    protected abstract byte[] getDatosBinarios(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, String as[])
        throws Exception;
}
