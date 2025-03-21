'use strict';

var changepresidentcount=0;
if (localStorage.getItem("changepresidentcounter")) {
    changepresidentcount = parseInt(localStorage.getItem("changepresidentcounter"));
}

document.addEventListener("DOMContentLoaded", () => {
    //document.writeln("Jacek Sutryk zatrzymany przez CBA. Zmień prezydenta już teraz!");
    const presidentElement = document.getElementById("president");
    const changeButton = document.getElementById("change-president-btn");
    const presidentCounterdisplay=document.getElementById("change-president-counter-display");
    updatedisplay();

    function updatedisplay()
    {
        localStorage.setItem("changepresidentcounter", changepresidentcount);
        presidentCounterdisplay.innerHTML="Prezydent został już zmieniony "+ changepresidentcount +" razy";
    }

    function changePresident() {
        const newPresident = prompt("Podaj imię i nazwisko nowego prezydenta:", "Nowy Prezydent");
        if (newPresident && newPresident.trim() !== "") {
            presidentElement.innerHTML = "Prezydent: " +newPresident;
            alert(`Zmieniono prezydenta na: ${newPresident}`);
            changepresidentcount += 1;
            updatedisplay();
        } else {
            alert("Nie podano poprawnych danych. Prezydent pozostaje bez zmian.");
        }
    }
    changeButton.addEventListener("click", changePresident);
    
    const censorbutton=document.getElementById("censore-btn");
    function censore(){
        changepresidentcount=0;
        updatedisplay();
    }
    censorbutton.addEventListener("click",censore);
});
