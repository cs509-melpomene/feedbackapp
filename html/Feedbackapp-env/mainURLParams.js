// get snippet ID from query parameter
var urlParams = new URLSearchParams(window.location.search);
console.log(urlParams.get('snippetID')); // true
export const urlParamsSnippetID = urlParams.get('snippetID');