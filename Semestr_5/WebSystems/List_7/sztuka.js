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

const anchors = document.anchors;
document.getElementById('highlight-first').addEventListener('click', () => {
    const firstAnchor = anchors.item(0);
    if (firstAnchor) {
        firstAnchor.style.color = 'red';
        const newText = document.createTextNode(' - pierwsza kotwica w dokumencie');
        firstAnchor.appendChild(newText);
    }
});

document.getElementById('replace-text').addEventListener('click', () => {
    if (anchors.length > 0) {
        const elem = anchors.namedItem('elem2');
        const newName = document.createTextNode(prompt("Wprowadź nową nazwę:"));
        const oldName = elem.firstChild;
        if (oldName) {
            elem.replaceChild(newName, oldName);
        } else {
            elem.appendChild(newName);
        }
    }
});

document.getElementById('find-parent').addEventListener('click', () => {
    const lastElem = anchors.item(anchors.length - 1);
    const parent = lastElem.parentNode.parentNode;
    parent.style.display='none';
});

const pressedHistory = Array();
const specialButtons = [0, 0, 0];
document.addEventListener('keydown', (event) => {
    pressedHistory.push(event.key);
    document.getElementById('key').textContent = pressedHistory;
    document.getElementById('keyCode').textContent = event.keyCode;
    
    if (event.altKey && specialButtons[0] == 0) {
        document.getElementById('altKey').textContent = 'Tak';
        specialButtons[0] = 1;
    }
    if (event.ctrlKey && specialButtons[1] == 0) {
        document.getElementById('ctrlKey').textContent = 'Tak';
        specialButtons[1] = 1;
    }
    if (event.shiftKey && specialButtons[2] == 0) {
        document.getElementById('shiftKey').textContent = 'Tak';
        specialButtons[2] = 1;
    }
});

const area = document.getElementById('area');
document.addEventListener('mousemove', (event) => {
    document.getElementById('clientX').textContent = event.clientX;
    document.getElementById('clientY').textContent = event.clientY;

    document.getElementById('screenX').textContent = event.screenX;
    document.getElementById('screenY').textContent = event.screenY;
});

area.addEventListener('mousedown', () => {
    area.classList.add('highlight');
});

area.addEventListener('mouseover', () => {
    area.style.backgroundColor = '#e0e0e0';
});

area.addEventListener('mouseout', () => {
    area.style.backgroundColor = '';
});
