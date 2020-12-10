
export function deleteStaleSnippetsHTTPRequest(){
    let httpRequest = new XMLHttpRequest();
    httpRequest.open('POST', `https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippets`, true);
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    let numberDaysInput = document.getElementById('numberDays');
    console.log("numberDays.value " + numberDaysInput.value)
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

export function fromDateToString(date){
    let options = {
        year: 'numeric', month: 'numeric', day: 'numeric',
        hour: 'numeric', minute: 'numeric', second: 'numeric'
    }
    return new Intl.DateTimeFormat('en', options).format(new Date(date))
}

export function nDaysChange(){
    console.log("nDaysChange change")
    let numberDaysInput = document.getElementById('numberDays');
    console.log("numberDaysInput.value " + numberDaysInput.value)
    let numberDays = parseInt(numberDaysInput.value);
    if (isNaN(numberDays)){
        console.log("not an integer, returning");
        return;
    }

    console.log("an integer, continuing")
    let todaysDate = new Date()
    let currentMillis = Date.parse(todaysDate);
    let dayInMillis = 1000 * 60 * 60 * 24;
    let diffDaysInMillis = numberDays * dayInMillis;
    let oldDay = currentMillis - diffDaysInMillis;

    let deleteTable = document.getElementById('ShowTable')
    deleteTable.innerHTML = `
        <tr style="border-style:solid;">
            <th style="border-style:solid;">Snippet ID</th>
            <th style="border-style:solid;">Snippet Time</th>
        </tr>
    `;
    window.globalSnippets.filter((currentSnippet) => {
        let time = new Date(currentSnippet.time)
        return time <= oldDay
    }).forEach((snippetDisplay) => {
        let SnippetID = snippetDisplay.snippetID
        let time = fromDateToString(snippetDisplay.time);
        let tableShow = `
            <tr>
                <td style="border-style:none solid  none none;">${SnippetID}</td>
                <td>${time}</td>
            </tr>
            `;
        deleteTable.innerHTML += tableShow;
    });
}