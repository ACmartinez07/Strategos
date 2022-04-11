/**
 * 
 */
package com.visiongc.app.strategos.web.struts.indicadores.actions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Kerwin
 *
 */
public class VerArchivoLog extends DownloadAction
{
	protected DownloadAction.StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String contentType = "application/pdf";
	    byte[] myPdfBytes = (byte[])null;

	    ByteArrayOutputStream baosPDF = null;

	    Document doc = new Document();

	    baosPDF = new ByteArrayOutputStream();
	    PdfWriter docWriter = null;

	    docWriter = PdfWriter.getInstance(doc, baosPDF);

	    doc.addAuthor(getClass().getName());
	    doc.addCreationDate();
	    doc.addProducer();
	    doc.addCreator(getClass().getName());
	    doc.addTitle("Detalles");

	    doc.setPageSize(PageSize.LETTER.rotate());
	    doc.setMargins(30.0F, 45.0F, 30.0F, 30.0F);

	    doc.open();

	    StringBuffer log = null;
	    while (log == null) 
	    	log = (StringBuffer)request.getSession().getAttribute("verArchivoLog");

	    doc.add(new Phrase(log.toString(), new Font(1, 8.0F)));

	    if (doc != null) 
	    	doc.close();

	    if (docWriter != null) 
	    	docWriter.close();

	    myPdfBytes = baosPDF.toByteArray();
	    
	    request.getSession().removeAttribute("verArchivoLog");

	    return new ByteArrayStreamInfo(contentType, myPdfBytes);
	}

	protected class ByteArrayStreamInfo implements DownloadAction.StreamInfo 
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
	    	return this.contentType;
	    }

	    public InputStream getInputStream() throws IOException 
	    {
	    	return new ByteArrayInputStream(this.bytes);
	    }
	}
	
	private StringBuffer GetFileLog(HttpServletRequest request)
	{
		StringBuffer log = new StringBuffer();
		
		String direccion = request.getParameter("direccion").toString();
	    File file = new File(direccion);
	    BufferedReader entrada;
	    try 
	    {
	    	if (file.exists())
	    	{
		    	entrada = new BufferedReader(new FileReader( file ));
		    	while(entrada.ready())
		    	{
		    		log.append(entrada.readLine());
		    		log.append("\n");
		    	}
		    	
		    	entrada.close();
	    	}
	    	else
	    		log.append("\n");
	    	
	    }
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    }
		
		return log;
	}
}