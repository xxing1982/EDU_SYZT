ctrls.controller("statisticsClassesCategoriesController", ['$rootScope', '$scope', function ($rootScope, $scope) {

    // Utils functions
    var colorList = [ 0xEABE94, 0xCCDF9F, 0xB3DCDF, 0xEDD685, 0xE99999 ];
    var colorIndex = 0;
    function generateRamdomColor(){
        var index = colorIndex % colorList.length;
        var divider = Math.floor( colorIndex / colorList.length ) * 0.3 + 1;
        var subtration = 0x000F20 * Math.floor( colorIndex / colorList.length ) ;
        colorIndex ++ ;
        return "#" + ( Math.floor( ( colorList[ index ] - subtration ) / divider ) ).toString(16).toUpperCase();
    }          
    
    
    // Get the context of the canvas element we want to select
    var radar_ctx = document.getElementById("myRadarChart").getContext("2d");

    // For a radar chart
    var myRadarChart;
                                                      
    function drawRadar(radar_data){
        var radar_options = options = {
            
        //Boolean - Whether to show lines for each scale point
        scaleShowLine : true,

        //Boolean - Whether we show the angle lines out of the radar
        angleShowLineOut : true,

        //Boolean - Whether to show labels on the scale
        scaleShowLabels : false,

        // Boolean - Whether the scale should begin at zero
        scaleBeginAtZero : true,

        //String - Colour of the angle line
        angleLineColor : "rgba(0,0,0,.1)",

        //Number - Pixel width of the angle line
        angleLineWidth : 1,

        //String - Point label font declaration
        pointLabelFontFamily : "'微软雅黑'",

        //String - Point label font weight
        pointLabelFontStyle : "normal",

        //Number - Point label font size in pixels
        pointLabelFontSize : 14,

        //String - Point label font colour
        pointLabelFontColor : "#666",

        //Boolean - Whether to show a dot for each point
        pointDot : true,

        //Number - Radius of each point dot in pixels
        pointDotRadius : 3,

        //Number - Pixel width of point dot stroke
        pointDotStrokeWidth : 1,

        //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
        pointHitDetectionRadius : 20,

        //Boolean - Whether to show a stroke for datasets
        datasetStroke : true,

        //Number - Pixel width of dataset stroke
        datasetStrokeWidth : 2,

        //Boolean - Whether to fill the dataset with a colour
        datasetFill : true,

        //String - A legend template
        legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].strokeColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>",
            
        // Boolean - Determines whether to draw tooltips on the canvas or not
        showTooltips: false
    };
        
        if ( myRadarChart ) { 
            myRadarChart.clear();
            myRadarChart.destroy();
        }
        myRadarChart = new Chart(radar_ctx).Radar(radar_data, radar_options);                   
    }
             
    // The catelogries get from server
    $scope.radarData = {
        labels: ["分类1", "分类2", "分类3", "分类5", "分类4", "分类6", "分类7"],
        datasets: [
            {
                fillColor: "rgba(0,0,0,0)",
                strokeColor: generateRamdomColor(),
                data: [65, 59, 90, 81, 56, 55, 40]
            },
            {
                fillColor: "rgba(0,0,0,0)",
                strokeColor: generateRamdomColor(),
                data: [28, 48, 40, 19, 96, 27, 44]
            },
            {
                fillColor: "rgba(0,0,0,0)",
                strokeColor: generateRamdomColor(),
                data: [33, 48, 40, 23, 32, 32, 66]
            },
            {
                fillColor: "rgba(0,0,0,0)",
                strokeColor: generateRamdomColor(),
                data: [39, 48, 40, 32, 96, 32, 22]
            },
            {
                fillColor: "rgba(0,0,0,0)",
                strokeColor: generateRamdomColor(),
                data: [32, 48, 40, 32, 65, 75, 22]
            },
            {
                fillColor: "rgba(0,0,0,0)",
                strokeColor: generateRamdomColor(),
                data: [28, 43, 67, 1, 96, 27, 100]
            },
            {
                fillColor: "rgba(0,0,0,0)",
                strokeColor: generateRamdomColor(),
                data: [43, 48, 43, 34, 96, 27, 100]
            }
    ]};
    drawRadar($scope.radarData);
}]);