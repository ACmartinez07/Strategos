<%@ taglib uri="/tags/struts-html" prefix="html"%>

<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/jsComunes.jsp'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/componentes/validaciones/validacionesJs.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/js/jquery.min.js'/>"></script>

<style type="text/css">
	#dialog-overlay 
	{
		position:absolute;
		top:0px;
		left:0px;
		width:100%;
		height:100%;
		background-color: #fff;
	  	filter:alpha(opacity=60);
	  	-moz-opacity: 0.6;
	  	opacity: 0.6;
	  	cursor:wait;
		visibility: hidden;
	}
	
	#dialog-bottom
	{
		visibility: visible;
	}
	
	#dialog-box 
	{
		/* css3 drop shadow */
		-webkit-box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
		-moz-box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
		
		/* css3 border radius */
		-moz-border-radius: 5px;
	    -webkit-border-radius: 5px;
		
		background-color: #fff;
     	border:1px solid #000;
		/* styling of the dialog box, i have a fixed dimension for this demo */ 
		width:220px; 
		
		/* make sure it has the highest z-index */
		position:absolute; 
		z-index:5000; 
	
		/* hide it by default */
		display:none;
	}
	
	#dialog-box .dialog-content 
	{
		/* style the content */
		text-align:left; 
		margin:2px;
		color:#666; 
		font-family:arial;
		font-size:11px; 
	}
	
	#dialog-box .dialog-content ul 
	{
		margin:10px 0 10px 20px; 
		padding:0; 
	}
	
	a.button 
	{
		/* styles for button */
		margin:10px auto 0 auto;
		text-align:center;
		background-color: #e33100;
		display: block;
		width:50px;
		padding: 5px 10px 6px;
		color: #fff;
		text-decoration: none;
		font-weight: bold;
		line-height: 1;
		
		/* css3 implementation :) */
		-moz-border-radius: 5px;
		-webkit-border-radius: 5px;
		-moz-box-shadow: 0 1px 3px rgba(0,0,0,0.5);
		-webkit-box-shadow: 0 1px 3px rgba(0,0,0,0.5);
		text-shadow: 0 -1px 1px rgba(0,0,0,0.25);
		border-bottom: 1px solid rgba(0,0,0,0.25);
		position: relative;
		cursor: pointer;
		
	}
	
	a.button:hover 
	{
		background-color: #c33100;	
	}
	
	/* extra styling */
	#dialog-box .dialog-content p 
	{
		font-weight:700; margin:0;
	}
	
	#overlay 
	{
		visibility: hidden;
	    position: absolute;
	    left: 0px;
	    top: 0px;
	    width:100%;
	    height:100%;
	    text-align:center;
	    z-index: 1000;
	    /*background-image:url(background-trans.png);*/
	}
	
	#overlay div 
	{
    	width:300px;
     	margin: 100px auto;
     	background-color: #fff;
     	border:1px solid #000;
     	padding:15px;
     	text-align:center;
	}
	
	#dialog-titulo
	{
		color:#ffffff;
		padding: 5px;
		background-color: #666666;
		font-size:12px; 
		font-weight: bold;
		text-align:center;
  		font-weight: lighter;		
	}
	
	#dialog-message
	{
		padding: 5px;
		font-size:12px;
		text-decoration: none;
		font-family: Verdana;
	}
	
	#dialog-message a:link 
	{ 
		text-decoration: none;
		color :  #666666;
	} 

	#dialog-message a:active 
	{ 
		text-decoration: none;
		color :  #666666;
	}
	
	#dialog-message a:visited 
	{ 
		text-decoration: none;
		color :  #666666;
	}	

	#fondo
	{
		position:absolute;
		top:0px;
		left:0px;
		width:100%;
		height:100%;
		background-color: #fff;
	  	filter:alpha(opacity=60);
	  	-moz-opacity: 0.6;
	  	opacity: 0.6;
	  	cursor:wait;
	  	visibility: hidden;
	}
		
</style>

<script type="text/javascript">
	
	$(document).bind('keydown',function(e)
	{
	    if ( e.which == 27 ) 
	    {
	    	closePopUp();
	    	closeDialog();
	    };
	});

	function closeDialog()
	{
		if (_dialogOpen)
		{
			$('#dialog-overlay, #dialog-box').hide();
			$('#fondo').css({visibility: "hidden"});
		}
	}
	
	function closePopUp()
	{
		$('#overlay').css({visibility: "hidden"});
		$('#fondo').css({visibility: "hidden"});
	}
	
	function OnKeyPress()
	{
		closePopUp();
	}
	
	function getAbsoluteElementPosition(element) 
	{
		if (typeof element == "string")
			element = document.getElementById(element);
		    
		if (!element) return { top:0,left:0 };
		  
		var y = 0;
		var x = 0;
		while (element.offsetParent) 
		{
			x += element.offsetLeft;
		    y += element.offsetTop;
		    element = element.offsetParent;
		}
		
		return {top:y,left:x};
	}
	
	/// <summary>
	/// Public method that return an object with the x,y coordinates for the object based on the document container.
	/// </summary>
	/// <param name="object">The DOM object for which to find the coordinates.</param>
	/// <param name="relativeTo">String indicating the location of the coordinates relative to the object; by default the lower-left corner is returned.</param>
	/// <returns>Object with x, y coordinates as properties.</returns>
	function GetObjectCoordinates(object, relativeTo)
	{
		var o = object;
		var x = 0;
		var y = 0;
		
		if (o.offsetLeft != -1 && o.offsetTop != -1)
		{
			if (relativeTo == "center")
			{
				x = o.offsetLeft + Math.round(object.offsetWidth / 2);
				y = o.offsetTop + Math.round(object.offsetHeight / 2);

	        }
	        else if (relativeTo == "top-left")
	        {
	            x = o.offsetLeft + 4;
	            y = o.offsetTop;
	        }
	        else if (relativeTo == "top-right")
	        {
	            x = o.offsetLeft + object.offsetWidth;
	            y = o.offsetTop;
	        }
			else
			{
				x = o.offsetLeft + 4;
				y = o.offsetTop + o.offsetHeight;
			}
		}

		while (o.offsetParent != null)
		{
			o = o.offsetParent;
			x += o.offsetLeft - o.scrollLeft;
			y += o.offsetTop - o.scrollTop;
		}

		return { x: x, y: y };
	}
	
</script>	

<!-- Dialogo Box Solo Java Script -->
<div id="overlay">
   	<div>
       	<p>Aquí va el contenido que desea mostrar al usuario.</p>
       	<a href="javascript:closePopUp();" class="button">Close</a>
   	</div>
</div>

<!-- Dialogo Box --> 
<div id="dialog-overlay"></div>
<div id="dialog-box">
	<div class="dialog-content">
		<div id="dialog-titulo"></div>
		<div id="dialog-message"></div>
		<div id="dialog-bottom" align="right">
			<a href="javascript:closeDialog();" class="button">Close</a>
		</div>
	</div>
</div>
