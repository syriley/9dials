var io = require('socket.io').listen(9001);

var studio = io
  .of('/studio')
  .on('connection', function (socket) {
    socket.broadcast.emit('mouseBroadcast','test');
    studio.emit('ready', 'ok');
    socket.on('mouseMoved', function(data) {
        console.log("move event fired");
        socket.broadcast.emit('mouseBroadcast',data);
    });
  });
