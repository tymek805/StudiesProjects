const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);
const {Server} = require("socket.io");
const io = new Server(server);

const rooms = ['general'];

app.use(express.static(__dirname));

app.get('/', (req, res) => {
    res.sendFile(__dirname + '/index.html');
});

io.on('connection', (socket) => {
    const {nick, room} = socket.handshake.query;
    console.log(`${nick} connected`);

    socket.emit('room list', rooms);
    socket.join(room);

    socket.to(room).emit('system message', `${nick} dołączył(a) do pokoju ${room}`);

    socket.on('chat message sent', (msg, nick) => {
        console.log(`message [${nick}]: ${msg}`);
        io.to(room).emit('chat message recived', msg, nick);
    });

    socket.on('typing start', (nick) => {
        socket.to(room).emit('user typing', nick);
    });

    socket.on('typing stop', (nick) => {
        socket.to(room).emit('user stop typing', nick);
    });

    socket.on('disconnect', () => {
        console.log(`${nick} disconnected from room ${room}`);
        socket.to(room).emit('user stop typing', nick);
        socket.to(room).emit('system message', `${nick} opuścił(a) czat`);
    });

    socket.on('image sent', (imageData, nick) => {
        console.log(`image from [${nick}]`);
        io.to(room).emit('image received', imageData, nick);
    });

    socket.on('create room', (newRoomName) => {
        if (!rooms.includes(newRoomName)) {
            rooms.push(newRoomName);
            console.log(`Room created: '${newRoomName}'`);
            io.emit('room list', rooms);
        }
    });
});

server.listen(3000, () => {
    console.log('listening on http://localhost:3000');
});
