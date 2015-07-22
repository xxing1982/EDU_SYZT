//readingTrainingCtrl.js

ctrls.controller("readingTrainingTestingController", ['$scope', 'SpeedTesting', '$rootScope', function ($scope, SpeedTesting, $rootScope) {

    $scope.state = {
        main: 'test',
        sub: 'start'

    };

    $scope.current = 0;

    $scope.questions = {};
    $scope.article = {};
    $scope.current = 0;

    var readingTime = {
        start: 0,
        stop: 0
    };

    $scope.comment = "再接再励";
    $scope.wordsPerMin = 0;

    $scope.beginReading = function () {
        SpeedTesting.getArticleWithQuestion(function (data) {
            $scope.article = data.article;
            $scope.questions = data.questions;
            readingTime.start = new Date().getTime();
            $scope.state.sub = 'paper';
        })
    };

    $scope.endReading = function () {
        $scope.state.sub = 'question';
        readingTime.stop = new Date().getTime();
        $scope.questions.current = $scope.questions[$scope.current];

        var tm = parseInt((readingTime.stop - readingTime.start) / 1000);

        if (tm < 1) {
            tm = 1
        }
        $scope.myAnswer.time = tm;

        $scope.wordsPerMin = parseInt($scope.article.content.length / tm) * 60
    };

    $scope.myAnswer = {
        "articleId": 0,
        "studentId": $rootScope.id,
        "examType": "SPEED",
        "time": 0,
        "answers": [],
        "questions": []
    };

    $scope.nextQuestion = function () {

        if (!$scope.isActiveA && !$scope.isActiveB && !$scope.isActiveC && !$scope.isActiveD) {
            $rootScope.modal = {};
            $rootScope.modal.title = "提示";
            $rootScope.modal.content = "请选择一项";
            $('#alert-modal').modal();
            return;
        }

        $scope.isActiveA = false;
        $scope.isActiveB = false;
        $scope.isActiveC = false;
        $scope.isActiveD = false;

        if ($scope.current < $scope.questions.length - 1) {
            $scope.current++;
            $scope.questions.current = $scope.questions[$scope.current];
        }
    };

    $scope.submitSpeedTest = function () {
        if (!$scope.isActiveA && !$scope.isActiveB && !$scope.isActiveC && !$scope.isActiveD) {
            $rootScope.modal = {};
            $rootScope.modal.title = "提示";
            $rootScope.modal.content = "请选择一项";
            $('#alert-modal').modal();
            return;
        }

        $scope.myAnswer.articleId = $scope.article.id;

        SpeedTesting.submitSpeedTesting($scope.myAnswer, function (data) {
            $scope.grade = data;
            $scope.state.main = 'grade';
            console.log(data)
        })
    };

    $scope.Select = function (data, option) {
        var answer = {};
        answer.studentId = $rootScope.id;
        answer.option = data;
        answer.question = $scope.questions[$scope.current];
        $scope.myAnswer.answers[$scope.current] = answer;
        $scope.myAnswer.questions[$scope.current] = $scope.questions[$scope.current];

        $scope.isActiveA = false;
        $scope.isActiveB = false;
        $scope.isActiveC = false;
        $scope.isActiveD = false;
        switch (option) {
            case 'A':
                $scope.isActiveA = true;
                break;
            case 'B':
                $scope.isActiveB = true;
                break;
            case 'C':
                $scope.isActiveC = true;
                break;
            case 'D':
                $scope.isActiveD = true;
                break;
        }
    };

}]);

ctrls.controller("readingTrainingTrainingController",
    ['$scope', 'SpeedTraining', '$interval', "$rootScope",
        function ($scope, SpeedTraining, $interval, $rootScope) {

            var stop;

            $scope.line_selections = [];
            $scope.word_selections = [];
            $scope.level_selections = [];

            $scope.line_selection_status = 0;
            $scope.word_selection_status = 0;
            $scope.level_selection_status = 0;

            $scope.articles = [];
            $scope.loading = false;

            $scope.showArticle = false;

            $scope.selectIndex = -1;
            $scope.selectedArticle = {};
            $scope.article = '';

            $scope.lines_to_show = [];


            var config = {
                level: 10,
                word: 10,
                line: 10,
                wordPerLine: 20
            };

            $scope.articleLimit = 20;

            var filter = {
                line: '1',
                word: '100'
            };


            $scope.setPBorder = function (index) {
                if (index % 4 == 3) {
                    return {border: 'none'}
                }
            };


            $scope.setDivBorder = function (index) {
                if (index >= $scope.articleLimit - 4) {
                    return {'border-bottom': 'none'}
                }
            };

            function updateLines() {
                filter.line = $scope.line_selections[$scope.line_selection_status].id + 1;
            }

            function updateWords() {
                filter.word = ($scope.word_selections[$scope.word_selection_status].id + 1) * 100;
            }

            function updateLevel() {
                if ($scope.level_selection_status == 0) {
                    SpeedTraining.getTrainArticle(function (data) {
                        $scope.articles = data;
                    })
                }
                else {
                    SpeedTraining.getTrainArticleByLevel($scope.level_selection_status, function (data) {
                        $scope.articles = data;
                    })
                }
            }

            $scope.selectArticleItem = function (index, article) {
                $scope.selectedIndex = index;
                $scope.selectedArticle = article;
                console.log(index);
                console.log(article.id);
            };

            $scope.startTraining = function () {

                if ($scope.selectedIndex == -1 || $scope.selectedArticle == null) {
                    $rootScope.modal = {};
                    $rootScope.modal.title = "提示";
                    $rootScope.modal.content = "请选择一项";
                    $('#alert-modal').modal();
                    return;
                }

                $scope.loading = true;
                $scope.showAction = true;
                SpeedTraining.getArticleByID($scope.selectedArticle.id, function (data) {
                    $scope.loading = false;
                    $scope.article = data.content;
                    $scope.topic = data.topic;
                    $scope.showArticle = true;
                    $scope.article_line_array = data.content;

                    startReading();
                })
            };


            $scope.stopReading = function () {
                if (angular.isDefined(stop)) {
                    $interval.cancel(stop);
                    stop = undefined;

                    $rootScope.modal = {};
                    $rootScope.modal.title = "提示";
                    $rootScope.modal.content = "训练已经结束，点击\"确定\"返回";
                    $('#alert-modal').modal();

                    $rootScope.modal.click = function () {
                        $scope.lines_to_show = [];
                        $scope.showArticle = false;
                    };
                }
            };

            function startReading() {

                var i = 1;

                if (angular.isDefined(stop)) return;

                var intr = parseInt(60 / (filter.word / config.wordPerLine) * filter.line);

                var temp = $scope.article.substring(0, filter.line * config.wordPerLine);

                for (var j = 0; j < filter.line; j++) {
                    $scope.lines_to_show.push({content: temp.substring(j * config.wordPerLine, (j + 1) * config.wordPerLine)});
                }

                stop = $interval(function () {
                    $scope.lines_to_show = [];
                    if (filter.line * config.wordPerLine * i < $scope.article.length) {
                        var temp = $scope.article.substring(i * filter.line * config.wordPerLine, (i + 1) * filter.line * config.wordPerLine);
                        for (var j = 0; j < filter.line; j++) {
                            $scope.lines_to_show.push({content: temp.substring(j * config.wordPerLine, (j + 1) * config.wordPerLine)});
                        }
                        i++;
                    } else {
                        $scope.stopReading();
                    }
                }, intr * 1000);
            }

            $scope.allArticles = [];


            initSelections();
            function initSelections() {

                // 行数
                for (var i = 0; i < config.line; i++) {
                    $scope.line_selections.push({
                        id: i,
                        name: (i + 1) + '  行  ',
                        callback: updateLines
                    });
                }

                //每分钟显示字数
                for (i = 0; i < config.word; i++) {
                    $scope.word_selections.push({
                        id: i,
                        name: (i + 1) * 100 + ' 字 ',
                        callback: updateWords
                    })
                }

                //级数
                $scope.level_selections.push({
                    id: 0,
                    name: '所有等级',
                    callback: updateLevel
                });

                for (i = 1; i < config.level; i++) {
                    $scope.level_selections.push({
                        id: i,
                        name: '等级  ' + i,
                        callback: updateLevel
                    });
                }
            }

            updateLevel();
        }]);


ctrls.controller("readingTrainingStatisticsController", ['$scope', "$rootScope", 'SpeedList', function ($scope, $rootScope, SpeedList) {

    $scope.personalStatArray = [];


    function drawLineChart() {

        var data = {
            labels: [],
            datasets: [
                {
                    label: "My Second dataset",
                    fillColor: "rgba(151,187,205,1)",
                    strokeColor: "rgba(34,172,56,1)",
                    pointColor: "rgba(34,172,56,1)",
                    pointStrokeColor: "rgba(34,172,56,1)",
                    pointHighlightFill: "rgba(34,172,56,1)",
                    pointHighlightStroke: "rgba(151,187,205,1)",
                    data: []
                }
            ]
        };

        function initData() {
            for (var i = 0; i < $scope.personalStatArray.length; i++) {
                data.labels.push($scope.personalStatArray[i].creationTime);
                data.datasets[0].data.push($scope.personalStatArray[i].speed);
            }
        }


        var options = {

            ///Boolean - Whether grid lines are shown across the chart
            scaleShowGridLines: true,

            //String - Colour of the grid lines
            scaleGridLineColor: "rgba(0,0,0,.05)",

            //Number - Width of the grid lines
            scaleGridLineWidth: 3,

            //Boolean - Whether to show horizontal lines (except X axis)
            scaleShowHorizontalLines: true,

            //Boolean - Whether to show vertical lines (except Y axis)
            scaleShowVerticalLines: false,

            //Boolean - Whether the line is curved between points
            bezierCurve: false,

            //Number - Tension of the bezier curve between points
            bezierCurveTension: 0.4,

            //Boolean - Whether to show a dot for each point
            pointDot: true,

            //Number - Radius of each point dot in pixels
            pointDotRadius: 5,

            //Number - Pixel width of point dot stroke
            pointDotStrokeWidth: 2,

            //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
            pointHitDetectionRadius: 20,

            //Boolean - Whether to show a stroke for datasets
            datasetStroke: true,

            //Number - Pixel width of dataset stroke
            datasetStrokeWidth: 2,

            //Boolean - Whether to fill the dataset with a colour
            datasetFill: false,

            //String - A legend template
            legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].strokeColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"

        };

        initData();

        var ctx = document.getElementById("LineChart").getContext("2d");

        var myLineChart = new Chart(ctx).Line(data, options);

    }

    $scope.config = {
        countryNum: 100,
        schoolNum: 100,
        personNum: 10
    };


    $scope.schoolList = [];
    $scope.countryList = [];

    $scope.switch = {
        state: 'stat'
    };

    $scope.showStat = function () {
        $scope.switch.state = 'stat';
        SpeedList.getPersonalSpeedList($rootScope.id, $scope.config.personNum, function (data) {
            $scope.personalStatArray = data;
            drawLineChart();
        })
    };

    $scope.showSchoolRank = function () {
        $scope.switch.state = 'rank';
        SpeedList.getSpeedListFromSchool($rootScope.student.campusId, $scope.config.schoolNum, function (data) {
            $scope.schoolList = data;
        })
    };

    $scope.showCountryRank = function () {
        $scope.switch.state = 'country';
        SpeedList.getSpeedList($scope.config.countryNum, function (data) {
            $scope.countryList = data;
        })
    };

    $scope.showStat();

}]);

