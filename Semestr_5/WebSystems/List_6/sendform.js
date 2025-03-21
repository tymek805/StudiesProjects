document.addEventListener("DOMContentLoaded", () => {
    const forms = document.querySelectorAll("form");
    
    forms.forEach(form => {
        form.addEventListener("submit", (event) => {
            event.preventDefault(); 
            window.alert("Pomyślnie przesłano formularz!");
            form.submit();
        });
    });
});