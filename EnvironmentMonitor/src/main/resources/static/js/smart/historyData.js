/**
 * Created by 104 on 2017/10/24.
 */
/*
 历史数据管理
 */
var historyVue = new Vue({
    el: '#choosejedate',
    data: {
        startTime: "",
        endTime: ""
    },

    methods: function () {
        this.mounted()
    },
    mounted: function () {
        var _this = this;
        $.jeDate("#starttime", {
            format: "YYYY-MM-DD hh:mm:ss",
            festival: false,
            isinitVal: true,
            okfun: function (obj) {
                console.log(obj.elem);     //得到当前输入框的ID
                console.log(obj.val);      //得到日期生成的值，如：2017-06-16
                _this.startTime = obj.val;
                alert();
                //得到日期时间对象，range为false
                console.log(obj.date);     //这里是对象
            }
        });
        $.jeDate("#endtime", {
            format: "YYYY-MM-DD hh:mm:ss",
            isinitVal: true,
            festival: false,
            okfun: function (obj) {
                console.log(obj.elem);     //得到当前输入框的ID
                console.log(obj.val);      //得到日期生成的值，如：2017-06-16
                _this.endTime = obj.val;
                //得到日期时间对象，range为false
                console.log(obj.date);     //这里是对象
                var timestr = obj.date.YYYY + obj.date.MM + obj.date.DD + obj.date.hh + obj.date.mm + obj.date.ss;
                alert(timestr);
            }
        })
    },

});

