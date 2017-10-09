

$(document).ready(function () {

    initComponent();
    initWebSocket($("#webSocketUrl").val());
    initVue();

});

/*^_^*------主体逻辑------*^_^*/




/*^_^*------辅助逻辑------*^_^*/
//组件初始化，是一些界面交互逻辑，跟数据处理逻辑没有太大关系
function initComponent() {
    // fullpage组件初始化
    var $o1st = $("#1st");
    var $o2nd = $("#2nd");
    var $o3rd = $("#3rd");
    var $o4th = $("#4th");
    // var $o5th = $("#5th");

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
    $o3rd.on("click", function () {
        $.fn.fullpage.moveTo(1, 2);
    });
    $o4th.on("click", function () {
        $.fn.fullpage.moveTo(1, 3);
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
        }
    });
    $('.collapsible').collapsible();
    $('.tooltipped').tooltip({delay: 50});
    $("#slideMenu").sideNav();

    //实名制考勤组件初始化

}