let randomNumber = Math.floor(Math.random() * 100) + 1;
let attempts = 0;
const maxAttempts = 10;

const guessInput = document.getElementById('guess');
const submitButton = document.getElementById('submit');
const feedback = document.getElementById('feedback');
const attemptsDisplay = document.getElementById('attempts');
const restartButton = document.getElementById('restart');
const resultsDisplay = document.getElementById('results');

const attemptsMemory = [];

submitButton.addEventListener('click', () => {
    const userGuess = parseInt(guessInput.value);

    if (isNaN(userGuess) || userGuess < 1 || userGuess > 100) {
        feedback.textContent = "Proszę podać liczbę z zakresu 1-100!";
        feedback.style.color = "red";
        return;
    }

    attempts++;
    attemptsDisplay.textContent = `Próby: ${attempts}/${maxAttempts}`;
    
    attemptsMemory.push(userGuess);
    
    if (userGuess === randomNumber) {
        feedback.textContent = `Gratulacje! Odgadłeś liczbę ${randomNumber} w ${attempts} próbie(ach).`;
        feedback.style.color = "green";
        endGame();
    } else if (userGuess < randomNumber) {
        feedback.textContent = "Podana liczba jest za mała!";
        feedback.style.color = "orange";
    } else {
        feedback.textContent = "Podana liczba jest za duża!";
        feedback.style.color = "orange";
    }

    if (attempts === maxAttempts && userGuess !== randomNumber) {
        feedback.textContent = `Przegrałeś! Wylosowana liczba to ${randomNumber}.`;
        feedback.style.color = "red";
        endGame();
    }

    guessInput.value = "";
});

function endGame() {
    guessInput.disabled = true;
    submitButton.disabled = true;
    restartButton.classList.remove('hidden');
    
    var results = "Twoje próby: ";
    
    for (let i = 0; i < attemptsMemory.length; i++) {
        results += attemptsMemory[i];
        results += " ";
    }
    resultsDisplay.textContent = results;
}

restartButton.addEventListener('click', () => {
    randomNumber = Math.floor(Math.random() * 100) + 1;
    attempts = 0;
    attemptsDisplay.textContent = `Próby: 0/${maxAttempts}`;
    feedback.textContent = "";
    guessInput.disabled = false;
    submitButton.disabled = false;
    restartButton.classList.add('hidden');
    guessInput.value = "";
});

