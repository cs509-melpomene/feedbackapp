// get snippet ID from query parameter
var urlParams = new URLSearchParams(window.location.search);
console.log(urlParams.get('snippetID')); // true
export const urlParamsSnippetID = urlParams.get('snippetID');

export function updateNumbers(text){
    let numOfNumbers = text.replace(/[^\n]/g,"").length;
    let numbersStr = "";
    for (let i = 1; i <= numOfNumbers + 1; i++){ // adding 1 because last line might not have newline char
        numbersStr += i + "\n"
    }
    document.getElementById("numbers").value = numbersStr;
}
