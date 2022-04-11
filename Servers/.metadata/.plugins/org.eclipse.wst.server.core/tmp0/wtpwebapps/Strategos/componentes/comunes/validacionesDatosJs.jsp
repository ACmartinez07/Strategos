// <script type="text/javascript">

function validarCampoNumerico(objetoInput) {

	var valido = !isNaN(objetoInput.value);

	if (!valido) {
		objetoInput.value = objetoInput.value.substring(0, objetoInput.value.length - 1);
	}
}

// </script>
