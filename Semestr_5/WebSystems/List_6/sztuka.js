function displayResult(message) {
    document.getElementById("result-display").innerHTML = message;
}


function convertToInt() {
    const input = document.getElementById("number-input").value;
    const intValue = parseInt(input, 10);
    if (isNaN(intValue)) {
        displayResult("Podana wartość nie jest liczbą całkowitą.");
    } else {
        displayResult(`Przekonwertowano na liczbę całkowitą: ${intValue}`);
    }
}


function convertToFloat() {
    const input = document.getElementById("decimal-input").value;
    const floatValue = parseFloat(input);
    if (isNaN(floatValue)) {
        displayResult("Podana wartość nie jest liczbą zmiennoprzecinkową.");
    } else {
        displayResult(`Przekonwertowano na liczbę zmiennoprzecinkową: ${floatValue}`);
    }
}

function generateRandom() {
    const randomValue = Math.random() * 100;
    displayResult(`Wygenerowano losową liczbę: ${randomValue}`);
}


function generateRandomFloor() {
    const randomValue = Math.floor(Math.random() * 101);
    displayResult(`Wygenerowano losową liczbę całkowitą: ${randomValue}`);
}

document.getElementById("convert-int").addEventListener("click", convertToInt);
document.getElementById("convert-float").addEventListener("click", convertToFloat);
document.getElementById("generate-random").addEventListener("click", generateRandom);
document.getElementById("generate-random-floor").addEventListener("click", generateRandomFloor);
