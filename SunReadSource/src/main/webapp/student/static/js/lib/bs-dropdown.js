
ctrls.directive('bsDropdown', function ($compile) {
    return {
        restrict: 'E',
        scope: {
            items: '=dropdownData',
            doSelect: '&selectVal',
            selectedItem: '=preselectedItem',
            callback: '=callback',
            disabled: '=disabled'
        },
        link: function (scope, element, attrs) {
            var html = '';
            switch (attrs.menuType) {
                case "button":
                    html += '<div class="btn-group"><button class="btn button-label" ng-class="{ \'disabled\': disabled }">{{items[selectedItem].name || "请选择"}}</button><button class="btn dropdown-toggle" ng-class="{ \'disabled\': disabled }" data-toggle="dropdown"><span class="caret"></span></button>';
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
            scope.selectVal = function (item, noCb) {
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
                    if ( noCb === undefined && this.items && item.callback && typeof item.callback === 'function'){
                        item.callback();
                    }
                }
            };
            scope.selectVal(scope.bSelectedItem);
            
            var updateDropdown = function(oldValue, newValue) {
                
                // Update the bSelectedItem
                if (scope.items.length === 0 ) {
                    scope.bSelectedItem = undefined;
                }
                for (var i = 0; i < scope.items.length; i++) {
                    if (scope.items[i].id === scope.selectedItem) {
                        scope.bSelectedItem = scope.items[i];
                        break;
                    }
                }
                
                // Update the front end
                // FIXME why the items trigger the selectVal
                scope.selectVal(scope.bSelectedItem, typeof newValue === 'object' || oldValue === newValue ? true : undefined );
            };
            
            scope.$watch("selectedItem", updateDropdown);
            scope.$watch("items", updateDropdown);
        }
    };
});