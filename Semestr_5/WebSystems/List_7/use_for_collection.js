'use strict';

// Images
const images = document.images;
const imageListContainer = document.createElement('section');
imageListContainer.id = 'image-list-section';
imageListContainer.innerHTML = '<h3>Lista obrazów na stronie:</h3>';

const imageList = document.createElement('ul');
for (let i = 0; i < images.length; i++) {
    const img = images.item(i);
    const listItem = document.createElement('li');
    
    listItem.innerHTML = `
        <p><strong>Obraz ${i + 1}:</strong> (${img.alt || '(brak opisu)'})</p>
    `;
    imageList.appendChild(listItem);
}
imageListContainer.appendChild(imageList);

const imageButton = document.createElement('button');
imageButton.textContent = 'Remove duplicates!';
imageButton.addEventListener('click', () => {
    const seenAlts = new Set();
    const listItems = imageList.children;
    
    for (let i = 0; i < listItems.length; i++) {
        const listItem = listItems[i];
        const altText = listItem.alt || '(brak opisu)';

        if (seenAlts.has(altText)) {
            imageList.removeChild(listItem);
        } else {
            seenAlts.add(altText);
        }
    }
});

imageListContainer.appendChild(imageButton);
document.getElementById('nav-header').appendChild(imageListContainer);


// Links:
const deactivateButton = document.getElementById('deactivate-links');
deactivateButton.addEventListener('click', () => {
    const links = document.links;
  
    for (const link of links) {
    link.addEventListener('click', (event) => {
      event.preventDefault();
      alert('Ten link został dezaktywowany.');
    });
    }

    deactivateButton.disabled = true;
    deactivateButton.textContent = 'Linki dezaktywowane';
});
document.getElementById('nav-header').insertBefore(deactivateButton, document.getElementById('nav-list'));




console.log(document.anchors.length);

