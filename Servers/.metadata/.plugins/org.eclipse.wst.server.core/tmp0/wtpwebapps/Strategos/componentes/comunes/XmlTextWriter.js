// *********************************** //  
// XML by Subgurim (www.subgurim.net)//

/***************************************************************/
// Constructor
function XmlTextWriter()
{
    // Propiedad que almacenará el XML en sí
    // Property that will store our XML text
    this.xml = '';
    
    // Variable auxiliar donde se almacenarán los elementos en que estemos trabajando (al vuelo)
    // Auxiliar variable that will store the elements we are working on (on the fly)
    this.elementos = new Array();
    
    // Indica si el texto de salida debe ser formateado (bien visible para el ojo humano) 
    // Tells us if the text will be formatted
    this.Formatting = false;
    
    // Si se quiere formato, cantidad de veces que se añadirá el indentChar
    // If we want the xml to be formatted, this indicates the times that that indentChar would be added
    this.Indentation = 1;
    
    // Indent character
    // Carácter que se usará como formateador
    this.IndentChar = '\t';
}

function XmlTextReader(xml)
{
	var xmlDoc=undefined;
	try
	{
	    if (document.all) //IE
    	{
	    	xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		    xmlDoc.async = false;
		    xmlDoc.loadXML(xml);
        }
	    else //firefox
	        xmlDoc = document.implementation.createDocument(xml, "text/xml");
	}
	catch(e)
	{
	    try 
	    { 
	    	if (typeof DOMParser != "undefined") // Mozilla, Firefox, Chrome and related browsers 
	            return (new DOMParser()).parseFromString(xml, "application/xml");
	    	else //otros safari, chrome
    		{
	            var url = "data:text/xml;charset=utf-8," + encodeURIComponent(xml);
		    	var xmlhttp = new XMLHttpRequest();
	            xmlhttp.open("GET", url, false);
	            xmlhttp.send();
	            xmlDoc = xmlhttp.responseXML;
	            return xmlDoc;
    		}
	    }
	    catch (e)
	    {
	        return undefined;
	    }
	 
	}
	return xmlDoc;
}

function CrearXml()
{
	try
	{
	    if (document.all) //IE
	    	return new ActiveXObject("Microsoft.XMLDOM");
	    else if (typeof DOMParser != "undefined") // Mozilla, Firefox, Chrome and related browsers 
            return (new DOMParser()).parseFromString(null, "application/xml");
	    else //firefox
	        return document.implementation.createDocument("", "text/xml", null);
	}
	catch(e)
	{
	    try 
	    { 
	    	if (typeof DOMParser != "undefined") // Mozilla, Firefox, Chrome and related browsers 
	            return (new DOMParser()).parseFromString(null, "application/xml");
	    	else //otros safari, chrome
		    	return new XMLHttpRequest();
	    }
	    catch (e)
	    {
	        return undefined;
	    }
	}
}


/***************************************************************/
// WriteStartDocument
//
XmlTextWriter.prototype.WriteStartDocument = function(encoding, version)
{
	var _version = '1.0';
    var _encoding = 'UTF-8';
    
    if (encoding) _encoding = encoding;
    if (version) _version = version;
   
    this.xml = '<?xml version="' + _version + '" encoding="' + _encoding + '"?>';
};


// WriteEndDocument
XmlTextWriter.prototype.WriteEndDocument = function()
{
	while (this.elementos.length > 0)
        this.WriteEndElement();
};

/***************************************************************/
// WriteElementString
XmlTextWriter.prototype.WriteElementString = function(name, content, ns)
{
	if (!name) return;
    this.elementos.push(name);
    if (ns) name = ns + ':' + name;

    var _xml;
    
    if (!content)
        _xml= this.tab() + '<' + name + ' />';
    else 
        _xml= this.tab() + '<'+ name + '>' + this.tab(true) + content + this.tab() + '</' + name + '>';
    
    this.xml += _xml;
    this.elementos.pop();
};


// WriteElementStringCDATA
XmlTextWriter.prototype.WriteElementStringCDATA = function(name, content, ns)
{
	content = this.toCDATA(content);
    return this.WriteElementString(name, content, ns);
};



// WriteStartElement
XmlTextWriter.prototype.WriteStartElement = function(name, ns)
{   
	if (!name) return;
    if (ns) name = ns + ':' + name;
    this.elementos.push(name);    

    var _xml = this.tab() + '<'+ name + '>';
    
    this.xml += _xml;
};


// WriteEndElement
XmlTextWriter.prototype.WriteEndElement = function()
{
	var name = this.elementos.pop();
    
    if (name)
    {
        var _xml = this.tab(true) + '</'+ name + '>';
    
        this.xml += _xml;
    }
};


/***************************************************************/
// WriteString
XmlTextWriter.prototype.WriteString = function(content)
{
	this.xml += this.tab(true) + content;
};

// WriteValue
XmlTextWriter.prototype.WriteValue = function(content)
{
	return this.WriteString(content);
};

// WriteCDATA
XmlTextWriter.prototype.WriteCDATA = function(content)
{
	content = this.toCDATA(content);
    return this.WriteString(content);
};

// WriteComment
XmlTextWriter.prototype.WriteComment = function(content)
{
	content = '<!-- ' + content + ' -->';
    return this.WriteString(content);
};

/***************************************************************/
// WriteAttributeString
XmlTextWriter.prototype.WriteAttributeString = function(name, value)
{
	var last = this.xml.lastIndexOf('>');
    var lastBar = this.xml.lastIndexOf('/');
    var lastMinor = this.xml.lastIndexOf('<');
    var lastCData = this.xml.lastIndexOf('<![CDATA[');
    
    if ((lastMinor > lastBar) && (lastMinor > lastCData))
    {    
        var att = ' ' + name + '=' + '"' + value + '"';
        var prev = this.xml.substring(0, last);
        var post = this.xml.substring(last);
        this.xml = prev.concat(att, post);
    }
};


/***************************************************************/
// NON-ASP.NET FUNCTIONS

// tab
XmlTextWriter.prototype.tab = function(isContent)
{
	if (!this.Formatting)
        return '';
        
    var n = this.elementos.length - 1;    
    if (isContent)
        n++;
    
    var total = n * this.Indentation;
    
    var retorno = '';
    for (var i = 0; i < total; i++)
    {
        retorno += this.IndentChar;
    }
    
    return '\n' + retorno;
};

// toCDATA
XmlTextWriter.prototype.toCDATA = function(str)
{
	var prefijo = '<![CDATA[';
    var sufijo = ']]>';
    if (str.search(/prefijo/i) < 0)
    {
        str = prefijo + str + sufijo;
    }
    
    return str;
};

XmlTextWriter.prototype.replaceAll = function ( str, from, to ) 
{
	var idx = str.indexOf( from );

    while ( idx > -1 ) {
        str = str.replace( from, to );
        idx = str.indexOf( from );
    }

    return str;
};


XmlTextWriter.prototype.unFormatted = function()
{
	var _retorno = this.replaceAll(this.xml,'\n','');
    _retorno = this.replaceAll(_retorno,this.IndentChar,'');
    return _retorno;
};


// escapeXML
// Returns and HttpUtility.EncodeUrl value of the XML
XmlTextWriter.prototype.escapeXML = function()
{
	return escape(this.xml);
};

// parseXML (code modified from http://www.webreference.com/programming/javascript/definitive2/2.html)
// Parse the XML document contained in the string argument and return a Document object that represents it.
XmlTextWriter.prototype.parseXML = function() 
{
    var _xml = this.unFormatted();

    if (typeof DOMParser != "undefined") 
    {
        // Mozilla, Firefox, and related browsers
        return (new DOMParser()).parseFromString(_xml, "application/xml");
    }
    else if (typeof ActiveXObject != "undefined") 
    {
        // Internet Explorer.
        var ieXML = new ActiveXObject("Microsoft.XMLDOM");
        ieXML.loadXML(_xml);            // Parse text into it
        return ieXML;                   // Return it
    }
    else 
    {
        // As a last resort, try loading the document from a data: URL
        // This is supposed to work in Safari. Thanks to Manos Batsis and
        // his Sarissa library (sarissa.sourceforge.net) for this technique.
        var url = "data:text/xml;charset=utf-8," + encodeURIComponent(_xml);
        var request = new XMLHttpRequest();
        request.open("GET", url, false);
        request.send(null);
        return request.responseXML;
    }
};

function HtmlDecode(html)
{
    if (html == null)
        return "";

    return String(html)
            .replace(/&quot;/gi, '"')
            .replace(/&lt;/gi, '<')
            .replace(/&gt;/gi, '>')
            .replace(/&#39;/gi, "'")
            .replace(/&amp;/gi, '&');	// & MUST ALWAYS be decoded last
}