<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (24/11/2012) -->

<bean:define toScope="page" id="appGlobalHayMensajes" value="false"></bean:define>
<logic:messagesPresent>
	<bean:define toScope="page" id="appGlobalHayMensajes" value="true"></bean:define>
</logic:messagesPresent>
<logic:messagesPresent message="true">
	<bean:define toScope="page" id="appGlobalHayMensajes" value="true"></bean:define>
</logic:messagesPresent>

<logic:equal scope="page" name="appGlobalHayMensajes" value="true">
	<script type="text/javascript">
		function mostrarCombos(visible) 
		{
			var elementosForma = null;
			var forma = null;
	
			for (var f = 0; f < document.forms.length; f++) 
			{	   
				forma = document.forms[f];
				elementosForma = forma.elements;
				for (var i = 0; i < elementosForma.length; i++) 
				{
					if (elementosForma[i].type == 'select-one') 
					{
				    	var obj = elementosForma[i];
						if (visible) 
							obj.style.display="block";
						else 
							obj.style.display="none";
				   }
				}
			}
		}
	
		mostrarCombos(false);
	</script>
</logic:equal>
