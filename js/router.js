let pageUrls = { 
    about: '/index.html?about', 
    contact:'/index.html?contact',
    gallery:'index.html?gallery'
};

function OnStartUp() {
    popStateHandler();
}

OnStartUp();

document.querySelector('#about-link').addEventListener('click', (event) => { 
    let stateObj = { page: 'about' };
    document.title = 'About';
    history.pushState(stateObj, "about", "?about"); 
    RenderAboutPage(); 
});

document.querySelector('#contact-link').addEventListener('click', (event) => {
    let stateObj = { page: 'contact' };
    document.title = 'Contact';
    history.pushState(stateObj, "contact", "?contact"); 
    RenderContactPage();
});

document.querySelector('#gallery-link').addEventListener('click', (event) => {
    let stateObj = { page: 'gallery' };
    document.title = 'Gallery';
    history.pushState(stateObj, "gallery", "?gallery"); 
    loadGallery();
});

function RenderAboutPage() { 
    document.querySelector('main').innerHTML = `
        <h1 class="title">About Me</h1>
        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry...</p>`;
}

function popStateHandler() { 
    let loc = window.location.href.toString().split(window.location.host)[1]; 
    
    if (loc === pageUrls.contact) { RenderContactPage(); }
    if (loc === pageUrls.about) { RenderAboutPage(); }
    if (loc === pageUrls.gallery) { loadGallery(); }
}

window.onpopstate = popStateHandler;

function RenderContactPage() { 
  const app = document.getElementById("app");
  app.innerHTML = `
    <h1 class="title">Contact</h1>
    <form id="contact-form" class="contact-form">
      <label>
        Imię:
        <input type="text" name="name" id="name" required>
      </label>
      <label>
        E-mail:
        <input type="email" name="email" id="email" required>
      </label>
      <label>
        Wiadomość:
        <textarea name="message" id="message" rows="5" required></textarea>
      </label>
      <div id="recaptcha-container"> </div>
      <button type="submit">Wyślij</button>
    </form>
    <div id="form-message"></div>
  `;

  if (window.grecaptcha) {
    grecaptcha.render("recaptcha-container", {
      sitekey: "6LcXbw4rAAAAAPcel-EzBLZSXCfJhRSFUZLbOzzv"
    });
  }
  
  // window.onRecaptchaLoadCallback = function () {
  //   grecaptcha.render("recaptcha-container", {
  //     sitekey: "6LcXbw4rAAAAAPcel-EzBLZSXCfJhRSFUZLbOzzv"
  //   });
  // };  

  document.getElementById("contact-form").addEventListener("submit", function (e) {
    e.preventDefault();

    const name = document.getElementById("name");
    const email = document.getElementById("email");
    const message = document.getElementById("message");
    const recaptchaResponse = grecaptcha.getResponse();

    if (!name.value.trim()) {
      alert("Podaj imię.");
      return;
    }
    if (!email.validity.valid) {
      alert("Podaj poprawny e-mail.");
      return;
    }
    if (!message.value.trim()) {
      alert("Wpisz wiadomość.");
      return;
    }
    if (!recaptchaResponse) {
      alert("Potwierdź, że nie jesteś robotem.");
      return;
    }

    // Tutaj możesz dodać wysyłkę formularza np. fetch() do backendu
    document.getElementById("form-message").textContent = "Formularz wysłany poprawnie!";
    document.getElementById("contact-form").reset();
    grecaptcha.reset();
  });
}

document.getElementById('theme-toggle').addEventListener('click', () => {
    document.body.classList.toggle('dark-mode');
});

// Gallery
async function loadGallery() {
  const app = document.getElementById("app");
  app.innerHTML = `
    <h1 class="title">Gallery</h1>
    <div id="gallery" class="gallery-grid"></div>
    <div id="modal" class="modal hidden">
      <span class="close-button" id="modal-close">× Zamknij</span>
      <img id="modal-img" src="" alt="Large view">
    </div>
  `;

  const observer = new IntersectionObserver((entries, obs) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        const img = entry.target;
        fetch(img.dataset.src)
          .then(res => res.blob())
          .then(blob => {
            img.src = URL.createObjectURL(blob);
            img.classList.add("loaded");
            obs.unobserve(img);
          });
      }
    });
  });

  const gallery = document.getElementById("gallery");
  for (let i = 0; i < 9; i++) {
    const img = document.createElement("img");
    img.className = "gallery-thumb";
    img.dataset.src = `https://picsum.photos/600/400`;

    observer.observe(img);

    img.addEventListener("click", () => {
      document.getElementById("modal-img").src = img.src;
      document.getElementById("modal").classList.remove("hidden");
    });

    gallery.appendChild(img);
  }

  // Zamknięcie modala
  document.getElementById("modal-close").addEventListener("click", () => {
    document.getElementById("modal").classList.add("hidden");
  });

  document.getElementById("modal").addEventListener("click", (e) => {
    if (e.target.id === "modal") {
      document.getElementById("modal").classList.add("hidden");
    }
  });
}
