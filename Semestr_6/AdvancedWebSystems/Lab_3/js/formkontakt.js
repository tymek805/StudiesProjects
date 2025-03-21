document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form")

    form.addEventListener("reset", function (event) {
        const confirmation = confirm("Czy na pewno chcesz wyczyścić formularz?");
        if (!confirmation) {
            event.preventDefault(); 
        }
    });
});


document.addEventListener('DOMContentLoaded', () => {
    const inputs = document.querySelectorAll('#name, #surname, #email, #message');

    inputs.forEach(input => {
        input.addEventListener('focus', (event) => {
            showHelp(event.target);
        });

        input.addEventListener('blur', (event) => {
            hideHelp(event.target);
        });
    });

    function showHelp(input) {
        const helpText = getHelpText(input.id);

        if (helpText) {
            let helpElement = document.getElementById(input.id + '-help');
            if(helpElement)
                helpElement.textContent = helpText;
                helpElement.style.display = 'block';
        }
    }

    function hideHelp(input) {
        const helpElement = document.getElementById(input.id + '-help');
        if (helpElement) {
            helpElement.style.display = 'none'; 
        }
    }

    function getHelpText(inputId) {
        switch (inputId) {
            case 'name':
                return 'Wprowadź swoje imię.';
            case 'surname':
                return 'Wprowadź swoje nazwisko.';
            case 'email':
                return 'Podaj swój adres e-mail.';
            case 'message':
                return 'Napisz swoją wiadomość (maksymalnie 200 znaków).';
            default:
                return null;
        }
    }
});
