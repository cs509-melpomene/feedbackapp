import { deleteSnippetHTTPRequest } from './deleteSnippet.js';
import { 
    deleteStaleSnippetsHTTPRequest,
    nDaysChange,
    fromDateToString
} from './deleteStaleSnippets.js';

window.deleteSnippetHTTPRequest = deleteSnippetHTTPRequest
window.viewAllSnippetsHTTPRequest = viewAllSnippetsHTTPRequest
window.deleteStaleSnippetsHTTPRequest = deleteStaleSnippetsHTTPRequest
window.nDaysChange = nDaysChange

viewAllSnippetsHTTPRequest()
console.log("Hello")
export function viewAllSnippetsHTTPRequest(){
    let httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', `https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippets`, true);
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    httpRequest.send();
    httpRequest.onreadystatechange = viewAllSnippetsHTTPResponse(httpRequest);
}

function viewAllSnippetsHTTPResponse(httpRequest){
    return function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                console.log("responseText: " + httpRequest.responseText)
                let viewAllSnippets = JSON.parse(httpRequest.responseText).snippets
                window.globalSnippets = []
                for (let i = 0; i <= viewAllSnippets.length; i++){

                    if (viewAllSnippets[i] == undefined){
                        continue;
                    }
                    let SnippetID = viewAllSnippets[i].substring(0, 16)
                    let time = fromDateToString(viewAllSnippets[i].substring(16))
                    window.globalSnippets.push({
                        snippetID: SnippetID,
                        time: time
                    });
                }

                window.globalSnippets = window.globalSnippets.sort((e1, e2) => {
                    let time1 = Date.parse(e1.time)
                    let time2 = Date.parse(e2.time)
                    if (time1 < time2) return -1
                    if (time2 < time1) return 1
                    return 0
                })

                window.globalSnippets.forEach((viewAllSnippet) =>{
                    let SnippetID = viewAllSnippet.snippetID
                    let time = viewAllSnippet.time;
                    let tableShow = `
                        <tr>
                            <td style="border-style:none solid  none none;">${SnippetID}</td>
                            <td>${time}</td>
                        </tr>
                    `;
                    let tableDelete = `
                        <tr>
                            <td style="border-style:none solid  none none;">${SnippetID}</td>
                            <td style="border-style:none solid  none none;">${time}</td>
                            <td><input type="button" value="Delete Snippet" style="text-align: center; width: 100%;" onclick='deleteSnippetHTTPRequest("${SnippetID}")'></td>
                        </tr>
                    `;
                    let deleteTable = document.getElementById('DeleteTable')
                    let showTable = document.getElementById('ShowTable')
                    deleteTable.innerHTML += tableDelete
                    showTable.innerHTML += tableShow
                })
                return;
            }
        } else {
            console.log("bad")
        }
    }
}
