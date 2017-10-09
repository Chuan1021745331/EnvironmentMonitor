function initSubSystemStatusVue()
{
    subSystemStatusVue = new Vue({
        el: '#subSystemStatusVue',
        data: {
            temperature: 39.0,
            humidity: 23.0,
            pm2p5: 35.2,
            pm10: 27.9,
            noise: 63.5,
            windSpeed: 23.0,
            windDirection: 274
        }
    });
}