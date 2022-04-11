
addary=new Array();          //red
addary[0]=new Array(0,1,0);  //red green
addary[1]=new Array(-1,0,0); //green
addary[2]=new Array(0,0,1);  //green blue
addary[3]=new Array(0,-1,0); //blue
addary[4]=new Array(1,0,0);  //red blue
addary[5]=new Array(0,0,-1); //red
addary[6]=new Array(255,1,1);
clrary=new Array(360);

for (var i=0;i<6;i++) 
{
	for (var j=0;j<60;j++) 
	{
		clrary[60*i+j]=new Array(3);
		for (var k=0;k<3;k++) 
		{
			clrary[60*i+j][k]=addary[6][k];
			addary[6][k]+=(addary[i][k]*4);
		}
	}
}

var tunIex=navigator.appName=="Microsoft Internet Explorer"?true:false;
var xVal = 0; var yVal = 0;
var layobj;
var theDiv;

function captureEvent(nombreDiv) 
{
	theDiv=document.getElementById(nombreDiv);

	if (!tunIex) 
		theDiv.addEventListener("mousedown", DispCoords, false);
}

function movedfirefox(e) 
{
	if (!tunIex) 
	{
		y=4*((!tunIex)?e.layerX:event.offsetX);
		x=4*((!tunIex)?e.layerY:event.offsetY);
		sx=x-512;
		sy=y-512;
		qx=(sx<0)?0:1;
		qy=(sy<0)?0:1;
		q=2*qy+qx;
		quad=new Array(-180,360,180,0);
		xa=Math.abs(sx);
		ya=Math.abs(sy);
		d=ya*45/xa;
		if (ya> xa) 
			d=90-(xa*45/ya);
		deg=Math.floor(Math.abs(quad[q]-d));
		n=0;
		sx=Math.abs(x-512);
		sy=Math.abs(y-512);
		r=Math.sqrt((sx*sx)+(sy*sy));
		if (x==512 & y==512) 
			c="000000";
		else 
		{
			for (var i=0;i<3;i++) 
			{
				r2=clrary[deg][i]*r/256;
				if (r>256) r2+=Math.floor(r-256);
				if (r2>255) r2=255;
				n=256*n+Math.floor(r2);
			}
			c=n.toString(16);
			while (c.length<6) c="0"+c;
		}
	
		txtHex.value="#" + c;
		colorRGB=HextoRGB(c);
		txtRGB.value=colorRGB;
		txtOBJ.style.backgroundColor="#" + c;
		disappearColorWheel(nombreDiv);
	}
	
	return false;
}

function DispCoords(eventType) 
{
	xVal = eventType.layerX;
	yVal = eventType.layerY;
}

function moved(nombreDiv, txtOBJ, txtRGB, txtHex) 
{
	e=document.getElementById(txtOBJ);		
	y=4*((tunIex)?event.offsetX:xVal);		
	x=4*((tunIex)?event.offsetY:yVal);
	sx=x-512;
	sy=y-512;
	qx=(sx<0)?0:1;
	qy=(sy<0)?0:1;
	q=2*qy+qx;
	quad=new Array(-180,360,180,0);
	xa=Math.abs(sx);
	ya=Math.abs(sy);
	d=ya*45/xa;
	if (ya>xa) 
		d=90-(xa*45/ya);
	deg=Math.floor(Math.abs(quad[q]-d));
	n=0;
	sx=Math.abs(x-512);
	sy=Math.abs(y-512);
	r=Math.sqrt((sx*sx)+(sy*sy));
	if (x==512 & y==512) 
		c="000000";
	else 
	{
		for (var i=0;i<3;i++) 
		{
			r2=clrary[deg][i]*r/256;
			if (r>256) r2+=Math.floor(r-256);
			if (r2>255) r2=255;
			n=256*n+Math.floor(r2);
		}
		c=n.toString(16);
		while(c.length<6) c="0" + c;
	}
	txtHex.value="#" + c;
	colorRGB=HextoRGB(c);
	txtRGB.value=colorRGB;
	txtOBJ.style.backgroundColor="#" + c;
	disappearColorWheel(nombreDiv);

	return false;
}

function disappearColorWheel(nombreDiv) 
{
	var theDiv;
	var d=document;
	if (d.all) 
		theDiv=d.all[nombreDiv].style;
	else if (d.layers) 
		theDiv=d.layers[nombreDiv];
	else 
		theDiv=d.getElementById(nombreDiv).style;
	
	theDiv.display='none';
}

function movenomove(hiddenfield) 
{
	if (hiddenfield.value=='') 
		hiddenfield.value='x';
	else 
		hiddenfield.value='';
	return false;
}

function selcolorfirefox(textfield) 
{
	if (!tunIex) 
	{
		if (document.layers) 
			textfield.value=document.layers["wheel"].bgColor;
		else 
			textfield.value=document.all["wheel"].style.backgroundColor;
		textfield.value=textfield.value.substring(4);
		textfield.value=RGBToHex(textfield.value.substring(0,textfield.value.length-1)+',');
		textfield.style.backgroundColor=textfield.value;
	}
	
	return false;
}

function RGBToHex(strRGB) 
{
	redf=0;
	greenf=0;
	bluef=0;
	redv='';
	greenv='';
	bluev='';
	for (var i=0;i<strRGB.length;i++) 
	{
		chr=strRGB.substring(i,i+1);
		if (chr==',') 
		{
			if (redf==0) 
				redf=1;
			else 
			{
				if (greenf==0) 
					greenf=1;
				else 
				{
					if (bluef==0) 
						bluef=1;
				}
			}
		} 
		else 
		{
			if (chr!=' ') 
			{
				if (redf==0) 
					redv=redv+chr;
				else 
				{
					if (greenf==0) 
						greenv=greenv+chr;
					else 
					{
						if (bluef==0) 
							bluev=bluev+chr;	
					}
				}
			}
		}
	}
	
	redr=NumToHex(redv);
	greenr=NumToHex(greenv);
	bluer=NumToHex(bluev);
	
	return '#'+redr+greenr+bluer;
}

function NumToHex(strNum) 
{
	for (var i=0;i<strNum.length;i++) 
	{
		chr=strNum.substring(i,i+1);		
		if (isNaN(chr) || (chr==' ')) 
			return 'xxx';
	}
	if (strNum>255) 
		return '';
	else 
	{
		base=strNum/16;
		rem=strNum%16;
		base=base-(rem/16);
		baseS=MakeHex(base);
		remS=MakeHex(rem);
		return baseS + '' + remS;
	}
}

function MakeHex(x) 
{
	if ((x>=0) && (x<=9))
		return x;
	else 
	{
		switch(x) 
		{
			case 10: return "A"; 
			case 11: return "B";  
			case 12: return "C";  
			case 13: return "D";  
			case 14: return "E";  
			case 15: return "F";  
		}
	}
}

function HextoRGB(s) 
{
	var couleur=s; 
    var rouge=couleur.substr(0,2);
    var vert=couleur.substr(2,2);
    var bleu=couleur.substr(4,2);
	
    rouge=Number(rouge); 
    vert=Number(vert); 
    bleu=Number(bleu); 

    var coulr=Number("0x"+couleur.substr(0,2)); 
    var coulg=Number("0x"+couleur.substr(2,2)); 
    var coulb=Number("0x"+couleur.substr(4,2)); 

	var r="PF"+coulr+"PF"+coulg+"PF"+coulb+"PF";
	
	return r;
}