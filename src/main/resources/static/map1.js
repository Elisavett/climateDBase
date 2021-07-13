//Цвета кругов
let colors = ["#ff0000", "#990066", "#FF6E4A", "#B8B428",
    "#3C18FF", "#343030", "#066", "#990000",
    "#622", "#4b6273", "#252850", "#346e24",
    "#480607", "#ACB78E", "#d3a7ff", "#1CA9C9",
    "#FFA000", "#34C924", "#2c4815", "#FF5349",
    "#FEFE22", "#025669", "#00FF00", "#534B4F",
    "#7F180D", "#00A86B", "#999950", "#BAACC7",
    "#31372B", "#003366", "#FF9218", "#FF496C",
    "#F5DEB3", "#F3DA0B", "#B7410E", "#B76E79",
    "#99FF99", "#92000A", "#846A20", "#BBBBBB",
    "#966A57", "#84C3BE", "#382C1E", "#B85D43",
    "#413D51", "#CADABA", "#317F43", "#8A2BE2",
    "#282828", "#6699CC", "#FF6E4A", "#7BA05B",
    "#714B23", "#CF3476", "#3B83BD", "#D8A903",
    "#FFDAB9", "#472A3F", "#915F6D", "#000080",
    "#CC6C5C", "#313830", "#310062", "#9B2F1F",
    "#C37629", "#03C03C", "#5B1E31", "#564042",
    "#371F1C", "#ffb900", "#82898F", "#ffffff",
    "#BA55D3", "#00035a", "#82898F", "#671c20",
    "#A12312", "#5DA130", "#45CEA2", "#FF7518",
    "#B57281", "#8A3324", "#48D1CC", "#5E490F",
    "#7D512D", "#EE9374", "#D79D41", "#30626B",
    "#D35339", "#8C4566", "#423C63", "#EA8DF7",
    "#F75394", "#123524", "#BEF574", "#806B2A",
    "#4D7198", "#6fffb8", "#4E1609", "#FFA474",
    "#008CF0", "#78A2B7", "#FFF8DC", "#FFCC00",
    "#2e3b4b", "#EBC2AF", "#5a4528", "#7FFF00",
    "#D2691E", "#CDB891", "#45322E", "#40826D",
    "#FF845C", "#93AA00", "#F13A13", "#00836E",
    "#08E8DE", "#FFB300", "#007CAD", "#CD00CD",
    "#99c5cc", "#f0ff83", "#1a5478", "#9a5aff",
];

//Yandex MAP

window.onload = function () {
    var json = document.getElementById('json').value;
    var temperature = document.getElementById('temp').value;
    let coordinates = JSON.parse(json);
    var isAuth = document.getElementById('authority') != null;

    //преобразую в число с плавающей точкой
    ymaps.ready(init);
    function init() {

        let max_lat = -90;
        let max_long = -180;
        let min_lat = 90;
        let min_long = 180;

        MyIconContentLayout = ymaps.templateLayoutFactory.createClass(
            '<div style="color: #000000; font-weight: bold; font-size: small">$[properties.iconContent]</div>'
        );

        for (let i = 0; i < coordinates.length; i++) {
            if(coordinates[i].coordinates !== undefined) {
                if (Number(coordinates[i].coordinates.latitude) > max_lat) max_lat = Number(coordinates[i].coordinates.latitude);
                if (Number(coordinates[i].coordinates.latitude) < min_lat) min_lat = Number(coordinates[i].coordinates.latitude);
                if (Number(coordinates[i].coordinates.longitude) > max_long) max_long = Number(coordinates[i].coordinates.longitude);
                if (Number(coordinates[i].coordinates.longitude) < min_long) min_long = Number(coordinates[i].coordinates.longitude);

            }
        }


        // Создание экземпляра карты и его привязка к контейнеру с
        // заданным id ("map").

        let delta = 2;
        let map = new ymaps.Map('map',
            {
            bounds: [[min_lat - delta, min_long - delta], [max_lat + delta, max_long + delta]],
            center: [(min_lat+max_lat)/2, (min_long+max_long)/2],
            controls: ['zoomControl']
        }, {
            minZoom: 1,
            maxZoom: 16,
            maxAnimationZoomDifference: 1,
            avoidFractionalZoom: false
        });
        for (let i = 0; i < coordinates.length; i++) {
            if (coordinates[i].coordinates !== undefined) {
                let authAdditionalParams = isAuth ? "<a target='_blank' href='/admin/editCoordinates/" + coordinates[i].coordinates.id + "'>Редактировать координаты</a>" : "";
                var myCircle = new ymaps.Placemark(
                    [coordinates[i].coordinates.latitude, coordinates[i].coordinates.longitude],

                    {
                        // Содержимое балуна. //
                        balloonContentBody:
                            " Пункт наблюдения: " + coordinates[i].name + "<br \/>" +
                            " Координаты: ш: " + coordinates[i].coordinates.latitude + "; д: " + coordinates[i].coordinates.longitude + "<br \/>" +

                            "<a target='_blank' href='/getObservationPointById?id=" + parseInt(coordinates[i].id) + "'>Ифнормация о пункте</a>" + "<br \/>" +
                            authAdditionalParams,
                        iconContent: temperature + "&#8451;"
                    }, {
                        preset: "islands#redStretchyIcon",
                        iconContentLayout: MyIconContentLayout

                    });

                // Добавляем круг на карту.
                map.geoObjects.add(myCircle);
            }
        }

    }

}