let myNick = '';
while (!myNick) {
    myNick = prompt('Wpisz swój nick:').trim();
}

const roomSelect = document.getElementById('room-select');
let myRoom = roomSelect.value;

roomSelect.addEventListener('change', () => {
    myRoom = roomSelect.value;
    connectToRoom();
});

let socket;

const form = document.getElementById('form');
const input = document.getElementById('input');
const messages = document.getElementById('messages');
const typingIndicator = document.getElementById('typing-indicator');
const imageInput = document.getElementById('imageInput');

form.addEventListener('submit', (e) => {
    e.preventDefault();
    const text = input.value.trim();
    if (!text) return;
    socket.emit('chat message sent', text, myNick);
    socket.emit('typing stop', myNick);
    input.value = '';
});

input.addEventListener('input', () => {
    if (input.value.trim().length > 0) {
        socket.emit('typing start', myNick);
    } else {
        socket.emit('typing stop', myNick);
    }
});

imageInput.addEventListener('change', () => {
    const file = imageInput.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = () => {
        const imageData = reader.result;
        socket.emit('image sent', imageData, myNick);
    };
    reader.readAsDataURL(file);
});

function connectToRoom() {
    if (socket) {
        socket.disconnect();
    }

    socket = io({ query: { nick: myNick, room: myRoom }});
    messages.innerHTML = '';

    socket.on('chat message recived', (msg, nick) => {
        addMessage(msg, nick, nick === myNick);
    });

    socket.on('system message', (text) => {
        const li = document.createElement('li');
        li.textContent = text;
        li.classList.add('system');
        messages.appendChild(li);
        window.scrollTo(0, document.body.scrollHeight);
    });

    socket.on('user typing', (nick) => {
        typingIndicator.textContent = `${nick} pisze...`;
    });

    socket.on('user stop typing', () => {
        typingIndicator.textContent = '';
    });

    socket.on('image received', (imageData, nick) => {
        addImage(imageData, nick, nick === myNick);
    });

    socket.on('room list', (roomList) => {
        const roomSelect = document.getElementById('room-select');
        // roomSelect.innerHTML = '';

        roomList.forEach(room => {
            const exists = Array.from(roomSelect.options).some(opt => opt.value === room);
            if (!exists) {
                const option = document.createElement('option');
                option.value = room;
                option.textContent = room;
                roomSelect.appendChild(option);
            }
        });
    });
}

function getTimeStamp() {
    const d = new Date();
    return d.toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'});
}

function addMessage(message, nick, isOwnMessage) {
    const li = document.createElement('li');
    li.classList.add(isOwnMessage ? 'ownmessage' : 'othermessage');

    const time = getTimeStamp();
    const spanNick = document.createElement('span');
    spanNick.textContent = `${nick} — ${time}`;
    spanNick.classList.add('nickname');
    li.appendChild(spanNick);

    const textNode = document.createTextNode(message);
    li.appendChild(textNode);

    messages.appendChild(li);
    window.scrollTo(0, document.body.scrollHeight);
}

// Image handling
function addImage(imageData, nick, isOwnMessage) {
    const li = document.createElement('li');
    li.classList.add(isOwnMessage ? 'ownmessage' : 'othermessage');
}

document.getElementById('create-room-btn').addEventListener('click', () => {
    let newRoomName = '';
    while (!newRoomName) {
        newRoomName = prompt('Wpisz nazwę nowego pokoju:').trim();
    }

    socket.emit('create room', newRoomName);
    const newOption = document.createElement('option');
    newOption.value = newRoomName;
    newOption.textContent = newRoomName;
    console.log(newRoomName, newOption);
    roomSelect.appendChild(newOption);
    roomSelect.value = newRoomName;
    roomSelect.dispatchEvent(new Event('change'));
    connectToRoom();
});

connectToRoom();