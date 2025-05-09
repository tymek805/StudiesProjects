window.onload = () => {
    'use strict';
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker.register('./sw.js')
            .then(() => console.log('Service Worker registered successfully.'))
            .catch((error) => console.error('Service Worker registration failed:', error));
    }
};

document.addEventListener("DOMContentLoaded", () => {
    const main = document.querySelector("main");

    fetch("https://api.thecatapi.com/v1/images/search?limit=9")
        .then(response => response.json())
        .then(data => {
            const gallery = document.createElement("div");
            gallery.className = "gallery";

            data.forEach(cat => {
                const img = document.createElement("img");
                img.src = cat.url;
                img.alt = "Cute cat";
                img.className = "cat-image";
                gallery.appendChild(img);
            });

            main.appendChild(gallery);
        })
        .catch(error => {
            console.error("Error fetching cat images:", error);
            const errorMessage = document.createElement("p");
            errorMessage.textContent = "Failed to load cat images 😿";
            main.appendChild(errorMessage);
        });
});
