function initWebSocket(url) {

    webSocketUrl = url;
    Materialize.toast(webSocketUrl, 3000);

    if ('WebSocket' in window) {
        webSocket = new WebSocket(webSocketUrl);
    }
    else {
        $('#checkBrowser').modal('open');
    }

    //Error callback
    webSocket.onerror = function () {
    };

    //socket opened callback
    webSocket.onopen = function (event) {
    };

    //message received callback
    webSocket.onmessage = function (event) {
        dataHandler(event.data);
    };

    //socket closed callback
    webSocket.onclose = function () {
    };

    //when browser window closed, close the socket, to prevent server exception
    window.onbeforeunload = function () {
        webSocket.close();
    }
}

function dataHandler(data)
{
    data = JSON.parse(data);
    console.log(data);
}