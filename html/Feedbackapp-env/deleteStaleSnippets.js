
export function deleteStaleSnippetsHTTPRequest(){
    let httpRequest = new XMLHttpRequest();
    httpRequest.open('POST', `https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippets`, true);
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    let numberDaysInput = document.getElementById('numberDays');
    console.log("numberDays.value " + numberDays.value)
    let body = {
        "action": "deleteStale",
        "nDays": numberDaysInput.value
    }
    httpRequest.send(JSON.stringify(body));
    httpRequest.onreadystatechange = deleteStaleSnippetsHTTPResponse(httpRequest);
}

function deleteStaleSnippetsHTTPResponse(httpRequest){
    return function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                return;
            }
            console.log("responseText: " + httpRequest.responseText)
        } else if (httpRequest.readyState === XMLHttpRequest.LOADING) {
            console.log("responseText: " + httpRequest.responseText)
        } else {
            console.log("httpRequest.readyState", httpRequest.readyState)
        }
    }
}
