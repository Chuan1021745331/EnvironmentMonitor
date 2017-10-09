function initVue()
{
    statusVue = new Vue({
        el: '#statusVue',
        data: {
            runStatus: "等待系统启动……",
            connectStatus: "与控制中心失去连接",
            needRefresh: true,
            needReconnect: true,
            normalClass: 'white-text',
            errorClass: 'red-text'
        }
    });
}
