<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Creado por: Kerwin Arias (21/04/2012) -->
<script type="text/javascript">

	// Creado por: Kerwin Arias (21/04/2012)
	function sincronizarCamposTexto(campo, campoEspejo, campoEspejoLong, sincronizar) 
	{
		if (sincronizar) 
		{
			if (campo.value != null) 
			{
				if (campo.value.length > campoEspejoLong) 
					campoEspejo.value = campo.value.substring(0, campoEspejoLong -1);
				else 
					campoEspejo.value=campo.value;
			} 
			else 
				campoEspejo.value=null;
		}
	}
	
	// Creado por: Kerwin Arias (21/04/2012)
	function inicializarCheckboxSincronizadorCamposTexto(campo, campoEspejo, campoEspejoLong, checkboxSincronizar) 
	{
		if (campo.value != null) 
		{
			if (campo.value.length > campoEspejoLong) 
			{
				if (campoEspejo.value == campo.value.substring(0, campoEspejoLong -1)) 
					checkboxSincronizar.checked = true;
				else 
					checkboxSincronizar.checked = false;
			} 
			else 
			{
				if (campo.value == campoEspejo.value) 
					checkboxSincronizar.checked = true;
				else 
					checkboxSincronizar.checked = false;
			}
		} 
		else 
		{
			if (campoEspejo.value != null) 
				checkboxSincronizar.checked = false;
			else 
				checkboxSincronizar.checked = true;
		}
	}

</script>