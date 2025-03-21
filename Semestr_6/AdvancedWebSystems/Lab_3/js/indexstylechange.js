document.addEventListener("DOMContentLoaded", () => {
const bgColorInput = document.getElementById('bg-color');
const textColorInput = document.getElementById('text-color');
const fontFamilySelect = document.getElementById('font-family');
const resetButton = document.getElementById('reset-styles');
bgColorInput.value = '#ffffff';
textColorInput.value = '#000000';
fontFamilySelect.value = 'Arial, sans-serif';

bgColorInput.addEventListener('input', () => {
    document.body.style.backgroundColor = bgColorInput.value;
});


textColorInput.addEventListener('input', () => {
    document.body.style.color = textColorInput.value;
});

fontFamilySelect.addEventListener('change', () => {
    document.body.style.fontFamily = fontFamilySelect.value;
});

resetButton.addEventListener('click', () => {
    document.body.style.backgroundColor = 'white';
    document.body.style.color = 'black';
    document.body.style.fontFamily = 'Arial, sans-serif';
    bgColorInput.value = '#ffffff'; 
    textColorInput.value = '#000000'; 
    fontFamilySelect.value = 'Arial, sans-serif';

});
});