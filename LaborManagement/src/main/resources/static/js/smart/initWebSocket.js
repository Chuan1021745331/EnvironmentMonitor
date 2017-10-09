function initWebSocket(url)
{
    webSocketUrl = url;
    Materialize.toast(webSocketUrl, 3000);
    /*
     * 检测浏览器是否支持websocket
     * 不支持则弹出提示框，并关闭当前页面
     */
    if ('WebSocket' in window) {
        webSocket = new WebSocket(webSocketUrl);
    }
    else {
        $('#checkBrowser').modal('open');
    }

    //websocket发生错误时的回调
    webSocket.onerror = function () {
    };

    //websocket成功连接时的回调函数
    webSocket.onopen = function (event) {
        statusVue.runStatus = "系统正在运行";
        statusVue.needRefresh = false;
    };

    //当有消息是时的回调函数
    webSocket.onmessage = function (event) {
        dataHandler(event.data);
    };

    //websocket关闭时的回调函数
    webSocket.onclose = function () {
        // alert("会话过期")
        statusVue.runStatus = "系统已关闭";
        statusVue.needRefresh = true;
    };

    //当浏览器关闭时的回调函数，需要在此关闭websocket
    window.onbeforeunload = function () {
        webSocket.close();
    }
}

function dataHandler(data) {
    //{"content":"在线","type":"STATUS"}
    data = JSON.parse(data);
    switch(data.type)
    {
        case "STATUS":
            console.log(data.content);
            if(data.content === "掉线")
            {
                statusVue.connectStatus = "与控制中心失去连接";
                statusVue.needReconnect = true;
            }
            else if(data.content === "在线")
            {
                statusVue.connectStatus = "已连接至控制中心";
                statusVue.needReconnect = false;
            }
            break;
        default:
            break;
    }

}
