document.addEventListener("DOMContentLoaded", () => {
    const resetButton = document.getElementById("reset-btn");

    resetButton.addEventListener("click", (event) => {
        const userConfirmed = window.confirm("Czy na pewno chcesz wyczyścić formularz?");
        if (!userConfirmed) {
            event.preventDefault();
        }
    });
});
