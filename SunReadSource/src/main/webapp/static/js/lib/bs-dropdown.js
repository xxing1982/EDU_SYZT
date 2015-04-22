
ctrls.directive('bsDropdown', function ($interpolate, $compile) {
    return {
        restrict: 'E',
        scope: {
            items: '=dropdownData',
            doSelect: '&selectVal',
            selectedItem: '=preselectedItem',
            callback: '=callback'
        },
        link: function (scope, element, attrs) {
            var html = '';
            switch (attrs.menuType) {
                case "button":
                    html += '<div class="btn-group"><button class="btn button-label">请选择</button><button class="btn dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>';
                    break;
                default:
                    html += '<div class="dropdown"><a class="dropdown-toggle" role="button" data-toggle="dropdown"  href="javascript:;">Dropdown<b class="caret"></b></a>';
                    break;
            }
            html += '<ul class="dropdown-menu"><li ng-repeat="item in items"><a tabindex="-1" data-ng-click="selectVal(item)">{{item.name}}</a></li></ul></div>';
            element.append($compile(html)(scope));
            for (var i = 0; i < scope.items.length; i++) {
                if (scope.items[i].id === scope.selectedItem) {
                    scope.bSelectedItem = scope.items[i];
                    break;
                }
            }
            scope.selectVal = function (item) {
                if (item !== undefined) {
                    switch (attrs.menuType) {
                        case "button":
                            $('button.button-label', element).html(item.name);
                            break;
                        default:
                            $('a.dropdown-toggle', element).html('<b class="caret"></b> ' + item.name);
                            break;
                    }
                    scope.doSelect({
                        selectedVal: item.id
                    });
                    if (item.callback && typeof item.callback === 'function'){
                        item.callback();
                    }
                }
            };
            scope.selectVal(scope.bSelectedItem);
            
            // recompilation
            $compile(element.contents())(scope); 
        }
    };
});