$(document).ready(function() {
    const NEW_GAME = "NewGame";
    const UPDATE = "Update";
    const GAME_WON = "GameWon";
    const GAME_LOST = "GameLost";
    
    const MENU = "Menu";
    
    const PLAYGROUND_WALL = "|";
    const PLAYGROUND_SWAMP = ".";
    const PLAYGROUND_FIRE = "X";
    const PLAYGROUND_PORTAL = "P";
    const PLAYGROUND_GRASS = " ";
    
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
    
    function onMessage(evt) {
        if (0 == evt.data.indexOf(NEW_GAME)) {
            newGame(evt.data.substring(NEW_GAME.length + 1));
        } else if (0 == evt.data.indexOf(UPDATE)){
            console.log("update.");
        } else if (0 == evt.data.indexOf(GAME_WON)){
            showScreen(GAME_WON);
        } else if (0 == evt.data.indexOf(GAME_LOST)){
            showScreen(GAME_LOST);
        }
    }
    
    function showScreenHelper(i, j, picture) { 
        return function() {
            document.getElementById(j + ',' + i).style.backgroundImage="url(assets/images/screen/" + picture + ".png)"; 
            document.getElementById(j + ',' + i).style.backgroundPosition = (j * -32) + "px" + " " + (i*-32) + "px";
        } 
    }
        
    
    function showScreen(picture) {
        console.log(picture);
        for (i = 0; i < PLAYGROUND_HEIGHT; i++) {
            for (j = 0; j < PLAYGROUND_WIDTH; j++) {
                // defer the execution of anonymous function for 
                // 50 ms and go to next line of code.
                var call = showScreenHelper(i, j, picture);
                setTimeout(call, i * 10);   
            }
        }
    }

    function newGame(playground) {
        var lines = playground.split("\n");

        for (i in lines) {
            for (j in lines[i]) {
                if (PLAYGROUND_WALL === lines[i][j]) {
                    document.getElementById(j + ',' + i).style.backgroundImage="url(assets/images/sprite/default/wall.png)";
                } else if (PLAYGROUND_SWAMP === lines[i][j]) {
                    document.getElementById(j + ',' + i).style.backgroundImage="url(assets/images/sprite/default/swamp.png)"
                } else if (PLAYGROUND_PORTAL === lines[i][j]) {
                    document.getElementById(j + ',' + i).style.backgroundImage="url(assets/images/sprite/default/portal.png)"
                } else if (PLAYGROUND_FIRE === lines[i][j]) {
                    document.getElementById(j + ',' + i).style.backgroundImage="url(assets/images/sprite/default/fire.png)"
                } else {
                    document.getElementById(j + ',' + i).style.backgroundImage="url(assets/images/sprite/default/grass.png)"
                }
            }
            i++;
            j++;
        }
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
    
    showScreen(MENU);
    
    
});