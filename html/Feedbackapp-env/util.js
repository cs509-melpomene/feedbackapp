// get snippet ID from query parameter
var urlParams = new URLSearchParams(window.location.search);
console.log(urlParams.get('snippetID')); // true
export const urlParamsSnippetID = urlParams.get('snippetID');

export function updateNumbers(text){
    let numOfNumbers = text.split("\n").length;
    let numbersStr = "";
    for (let i = 1; i <= numOfNumbers; i++){
        numbersStr += i + "\n"
    }
    document.getElementById("numbers").innerHTML = numbersStr;
}

export function hideLoginDiv(){
    var blurredDiv = document.getElementById("blurredDiv");
    blurredDiv.hidden = true
}