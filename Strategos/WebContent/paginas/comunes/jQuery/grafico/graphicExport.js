function download(canvas, filename, type) 
{
    download_in_ie(canvas, filename) || download_with_link(canvas, filename, type);
}

// Works in IE10 and newer
function download_in_ie(canvas, filename) 
{
    return(navigator.msSaveOrOpenBlob && navigator.msSaveOrOpenBlob(canvas.msToBlob(), filename));
}

// Works in Chrome and FF. Safari just opens image in current window, since .download attribute is not supported
function download_with_link(canvas, filename, type) 
{
    var a = document.createElement('a');
    a.download = filename;
    a.href = canvas.toDataURL(type);
    document.body.appendChild(a);
    a.click();
    a.remove();
}			
