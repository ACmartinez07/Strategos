var globalPosicionCursor = 0;
			
function insertAtCursorPosition(myField, myValue) {
	if (document.selection) {
		insertStringExplorer(myValue, myField);
	}
	else if (myField.selectionStart || myField.selectionStart == '0') {
		var startPos = myField.selectionStart;
		var endPos = myField.selectionEnd;
		myField.value = myField.value.substring(0, startPos) + myValue
			+ myField.value.substring(endPos, myField.value.length);
		myField.selectionStart = startPos + myValue.length;
		myField.selectionEnd = startPos + myValue.length;
	} else {
		myField.value += myValue;
		myField.selectionStart = startPos + myValue.length;
		myField.selectionEnd = startPos + myValue.length;
	}
}

function setPosicionCursor(campoText) {
	globalPosicionCursor = getPosicionCursorEmail(campoText);
}			

function getPosicionCursorEmail(textElement) {				
	var sOldText = textElement.value;
	var objRange = document.selection.createRange();
	var sOldRange = objRange.text;
	var sWeirdString = '#%~';
	objRange.text = sOldRange + sWeirdString;
	objRange.moveStart('character', (0 - sOldRange.length - sWeirdString.length));
	var sNewText = textElement.value;
	objRange.text = sOldRange;
	for (i=0; i <= sNewText.length; i++) {
		var sTemp = sNewText.substring(i, i + sWeirdString.length);
		if (sTemp == sWeirdString) {
			var cursorPos = (i - sOldRange.length);
			return cursorPos;
		}
	}
}

function insertStringExplorer(stringToInsert, campoTexto) {
	var firstPart = campoTexto.value.substring(0, globalPosicionCursor);
	var secondPart = campoTexto.value.substring(globalPosicionCursor, campoTexto.value.length);
	campoTexto.value = firstPart + stringToInsert + secondPart;
	globalPosicionCursor = globalPosicionCursor + stringToInsert.length;
}