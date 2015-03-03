/**
 * Created by niels on 11/11/14.
 */
function MainViewModel() {
    // Data
    var self = this;
    self.searchTerm = ko.observable();
    self.searchResults = ko.observableArray([{"gtin":"0033383000015","picture":"http://9la.dk/img/product/gtin-003/0033383000015.jpg","calories":"130","carbs":"34","protein":"1","fat":"0","category":"Apple","bsin":"8RB56Z","name":"Red Delicious WAXF 2 1/2\" 3lb"},{"gtin":"0033383001531","picture":"http://9la.dk/img/product/gtin-003/0033383001531.jpg","calories":"130","carbs":"34","protein":"1","fat":"0","category":"Apple","bsin":"8RB56Z","name":"Granny Smith WAXF 2 1/2\" 3lb"},{"gtin":"0033383000039","picture":"http://9la.dk/img/product/gtin-003/0033383000039.jpg","calories":"130","carbs":"34","protein":"1","fat":"0","category":"Apple","bsin":"8RB56Z","name":"Red Delicious WAXF 2 1/2\" 5lb"},{"gtin":"0033383010243","picture":"http://9la.dk/img/product/gtin-003/0033383010243.jpg","calories":"130","carbs":"34","protein":"1","fat":"0","category":"Apple","bsin":"8RB56Z","name":"Cripps Pink WAXF 3lb 2 ½”"},{"gtin":"0033383000053","picture":"http://9la.dk/img/product/gtin-003/0033383000053.jpg","calories":"130","carbs":"34","protein":"1","fat":"0","category":"Apple","bsin":"8RB56Z","name":"Red Delicious WAXF 2 1/4\" 3lb"},{"gtin":"0033383000817","picture":"http://9la.dk/img/product/gtin-003/0033383000817.jpg","calories":"130","carbs":"34","protein":"1","fat":"0","category":"Apple","bsin":"8RB56Z","name":"Golden Delicious WAXF 2 1/2\" 3lb"},{"gtin":"0033383007410","picture":"http://9la.dk/img/product/gtin-003/0033383007410.jpg","calories":"130","carbs":"34","protein":"1","fat":"0","category":"Apple","bsin":"8RB56Z","name":"Gala WAXF 2 1/2\" 3lb"},{"gtin":"0033383007014","picture":"http://9la.dk/img/product/gtin-003/0033383007014.jpg","calories":"130","carbs":"34","protein":"1","fat":"0","category":"Apple","bsin":"8RB56Z","name":"Fuji WAXF 2 1/2\" 3lb"}]);
    self.searchResultsCompanies = ko.observableArray([{"bsin":"8RB56Z","name":"Washington Fruit & Produce Co.","website":"http://www.washfruit.com"}]);
    self.searchField = ko.observable("name");
    self.quickModeEnabled = ko.observable(false);
    self.replaceFields = ko.observable({calories: null, carbs: null, protein: null, fat: null});

    // UI
    self.searching = ko.observable(false);

    // Behaviours
    self.search = function() {
        var url = "/search/" +
            self.searchField() + "/" +
            self.searchTerm() + "?quick=" +
            self.quickModeEnabled();

        self.searching(true);
        $.get(url, null, function(result) {
            self.searching(false);
            self.searchResults(result.products);
            self.searchResultsCompanies(result.companies);
        });
    };

    self.copyItemToClipboard = function (item) {
        delete item.gtin;
        item.company = item.bsin;
        delete item.bsin;
        delete item.category;
        window.prompt("Copy to clipboard: Ctrl+C, Enter", JSON.stringify(item));
    }
}

ko.applyBindings(new MainViewModel());