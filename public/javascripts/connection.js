$(document).ready(function() { 
    var host = window.location.host
    var address = "ws://" + host + "/connect";
    var websocket = new Object()

    window.onbeforeunload = function () {
        websocket.onclose = function () {}; // disable onclose handler first
        websocket.close()
    }

    websocket = new WebSocket(address);

    websocket.onopen = function (evt) {
        onOpen(evt)
    };
    websocket.onclose = function (evt) {
        onClose(evt)
    };
    websocket.onmessage = function (evt) {
        onMessage(evt)
    };

    function onOpen(evt) {
        websocket.send("connected");
    }

    function onClose(evt) {

    }

    function disableSnakesButton(){

    }
    
    function onMessage(evt) {
        console.log(evt.data);
    }
    
    
    // keyboard events to trigger game
    document.onkeydown = checkKey;

    function checkKey(e) {
        e = e || window.event;

        if (e.keyCode == '38') {
            websocket.send("up");
        }
        else if (e.keyCode == '40') {
           websocket.send("down");
        }
        else if (e.keyCode == '37') {
           websocket.send("left");
        }
        else if (e.keyCode == '39') {
            websocket.send("right");
        } else if (e.keyCode == '13') {
            websocket.send("enter");
        }
    }
    
    
    
});