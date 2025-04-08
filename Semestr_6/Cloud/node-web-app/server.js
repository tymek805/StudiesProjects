const express = require('express');
const bodyParser = require("body-parser");
const path = require('path');
const morgan = require('morgan');
const app = express();
const fs = require("fs");
// const port = process.env.PORT || 8080;

app.set('views', path.join(__dirname, 'views'));

// Middleware
app.use(morgan('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, 'public')));

// Ustawienie silnika szablonów EJS
app.set('view engine', 'ejs');

// Obsługa 404 
app.use((err, req, res, next) => {
    res.status(404).render('404', { title: 'Nie znaleziono' });
});

// Obsługa innych błędów 
app.use((err, req, res, next) => {
    console.error(err.stack);
    res.status(500).render('error', { title: 'Błąd serwera', message: err.stack });
});

// Główna trasa
app.get('/', (req, res) => {
    res.render('index', { title: 'Strona główna', message: 'Witaj świecie!' });
});

app.post('/', (req, res) => {
    res.send('Got a POST request');
});

app.put('/user', (req, res) => {
    res.send('Got a PUT request at /user');
});

app.delete('/user', (req, res) => {
    res.send('Got a DELETE request at /user');
});

app.get('/contact', (req, res) => {
    res.render('contact', { title: 'Kontakt' });
}); 

app.post('/submit-form', (req, res) => {
    console.log(req.body);
    res.send('Formularz został wysłany!');
});

function getImages() {
    const imageDir = path.join(__dirname, "/public/images");
    return fs.readdirSync(imageDir).filter(file => /\.(jpg|jpeg|png|gif)$/i.test(file));
}

app.get('/gallery', (req, res) => {
    const images = getImages();
    res.render("gallery", { title: "Galeria", images });
}); 

// Start serwera
// app.listen(port, () => {
//     console.log(`Aplikacja nasłuchuje na porcie ${port}`);
// });

module.exports = app;