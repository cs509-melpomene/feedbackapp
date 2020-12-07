import { deleteSnippetHTTPRequest } from './deleteSnippet.js';
import { deleteStaleSnippetsHTTPRequest } from './deleteStaleSnippets.js';

window.deleteSnippetHTTPRequest = deleteSnippetHTTPRequest
window.viewAllSnippetsHTTPRequest = viewAllSnippetsHTTPRequest
window.deleteStaleSnippetsHTTPRequest = deleteStaleSnippetsHTTPRequest

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
                for (let i = 0; i <= viewAllSnippets.length; i++){

                    if (viewAllSnippets[i] == undefined){
                        continue;
                    }
                    let SnippetID = viewAllSnippets[i].substring(0, 16)
                    let time = viewAllSnippets[i].substring(16)
                    let tableShow = `<tr>
                                             <td style="border-style:none solid  none none;">${SnippetID}</td>
                                            <td>${time}</td>
                                     </tr>`
                    let tableDelete = `<tr>
                                <td style="border-style:none solid  none none;">${SnippetID}</td>
                                <td style="border-style:none solid  none none;">${time}</td>
                                <td><input type="button" value="Delete Snippet" style="text-align: center; width: 100%;" onclick='deleteSnippetHTTPRequest("${SnippetID}")'></td>
                         </tr>`
                    let deleteTable = document.getElementById('DeleteTable')
                    let showTable = document.getElementById('ShowTable')
                    deleteTable.innerHTML += tableDelete
                    showTable.innerHTML += tableShow
                }
                return;
            }
        } else {
            console.log("bad")
        }
    }
}