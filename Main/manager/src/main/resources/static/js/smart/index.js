$(document).ready(function () {

    initComponent();
    initWebSocket($("#webSocketUrl").val());
    initSubSystemStatusVue();

});

/*^_^*------辅助逻辑------*^_^*/
//组件初始化，是一些界面交互逻辑，跟数据处理逻辑没有太大关系
function initComponent() {
    // fullpage组件初始化
    var $o1st = $("#1st");
    var $o2nd = $("#2nd");

    $('#fullpage').fullpage({
        sectionsColor: ['#f2f2f2'],
        controlArrows: false
    });

    $o1st.on("click", function () {
        $.fn.fullpage.moveTo(1, 0);
    });
    $o2nd.on("click", function () {
        $.fn.fullpage.moveTo(1, 1);
    });

    //materialize组件初始化
    $('.modal').modal({
        complete: function()
        {
            var id = $(this).attr("id");
            if(id === "checkBrowser")
            {
                closeCurrentPage();
            }
            //Materialize.toast($(this).attr("id"));
        }
    });
    $('.collapsible').collapsible();
    $('.tooltipped').tooltip({delay: 50});

}