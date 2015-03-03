/**
 * Created by niels on 12/8/14.
 */

var mocks = require('protractor-http-mock');
var requestsMade = mocks.requestsMade;

var frontendRoot = (process.env.FRONTEND_ROOT || 'http://localhost:8080');
var backendRoot = (process.env.BACKEND_ROOT || 'http://localhost:2121');

// Get rid of Bootstrap modal fade animation
var disableModalAnimation = function() {
    browser.waitForAngular();
    browser.executeScript("$('.modal').removeClass('fade');");
};

describe("'Manage Users' view and controller", function() {

    /**
     *  Tested with following server responses:
     *      200: Success
     *      500: Server error
     *      0:   Connection refused
     *
     *  Tested with following user inputs:
     *      Valid
     *      Malformed
     *      Missing
     */

    // TODO: Test and handle edit validation and server error

    var mockusers = [
        { "_id": "547ee0785a9d622b468bf652", "modified": "", "created": "2014-12-03T10:05:44.760Z", "username": "paja", "email": "pprochazka72@gmail.com", "authMethod": "local", "role": "admin", "__v": 0 },
        { "_id": "547ee08b5a9d622b468bf653", "modified": "", "created": "2014-12-03T10:06:03.665Z", "username": "niels", "email": "nm@9la.dk", "authMethod": "local", "role": "raw", "__v": 0 },
        { "_id": "548305347b1c1b2d5f802a00", "modified": "", "created": "2014-12-06T13:31:32.240Z", "username": "bob", "email": "bobsaget@gihub.com", "authMethod": "local", "role": "gastro", "__v": 0 },
        { "_id": "548305427b1c1b2d5f802a01", "modified": "", "created": "2014-12-06T13:31:46.062Z", "username": "johndoe", "email": "johndoe@yahoo.com", "authMethod": "local", "role": "comp", "__v": 0 }
    ];

    var mockEndpoints;
    beforeEach(function() {
        mockEndpoints = [
            {
                // Not logged in already
                request: {  method: 'GET', path: backendRoot + '/isloggedin' },
                response: {
                    status: 401
                }
            },
            {
                // List of different types of users
                request: { method: 'GET', path: backendRoot + '/user' },
                response: {
                    status: 200,
                    data: mockusers
                }
            }
        ];
    });

    afterEach(function() {
        mocks.teardown();
    });

    it('Shows correct list of all users returned by backend', function() {
        // ARRANGE
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');

        // ASSERT
        var rowInfos = element.all(by.css("#users-table tr")).map(function(row) {
            return row.all(by.css("td span")).reduce(function(acc, cell) {
                cell.getText().then(function(text) {
                    acc.push(text);
                });
                return acc;
            }, []);
        });
        expect(rowInfos).toEqual([
            [  ], // <--- Header row
            [ '', 'paja', 'pprochazka72@gmail.com', '(encrypted)', '', '12th Dec 2014', 'a few seconds ago', 'Admin' ],
            [ '', 'niels', 'nm@9la.dk', '(encrypted)', '', '12th Dec 2014', 'a few seconds ago', 'Deactivated' ],
            [ '', 'bob', 'bobsaget@gihub.com', '(encrypted)', '', '12th Dec 2014', 'a few seconds ago', 'Gastronomist' ],
            [ '', 'johndoe', 'johndoe@yahoo.com', '(encrypted)', '', '12th Dec 2014', 'a few seconds ago', 'Company' ]
        ]);
    });
    it('Opens edit-mode on row correctly upon click of ROW edit button', function() {
        // ARRANGE
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        element.all(by.css('#users-table tr button[ng-click="startEdit(user)"]')).get(2).click();

        // ASSERT
        element.all(by.css('#users-table tr')).get(3).then(function(row) {
            // Inputs
            expect(row.all(by.css('td span')).get(1).getText()).toBe("bob");
            expect(row.all(by.model('cur.model.email')).first().isPresent()).toBe(true);
            expect(row.all(by.model('cur.model.password')).first().isPresent()).toBe(true);
            expect(row.all(by.model('cur.model.role')).first().isPresent()).toBe(true);
            // Save specific controls
            expect(row.all(by.css('button[ng-click="attemptSave(cur)"]')).first().isPresent()).toBe(true);
            expect(row.all(by.css('button[ng-click="discardEdits()"]')).first().isPresent()).toBe(true);
        });
        expect(element.all(by.model('cur.model.email')).count()).toBe(1);
        expect(element.all(by.model('cur.model.password')).count()).toBe(1);
        expect(element.all(by.model('cur.model.role')).count()).toBe(1);
        expect(element.all(by.css('button[ng-click="attemptSave(cur)"]')).count()).toBe(1); // NOTE: Only 1, because the save button at the bottom does not take a user object
        expect(element.all(by.css('button[ng-click="discardEdits()"]')).count()).toBe(2); // NOTE: 2 because there's also a discard button at the bottom of the page
    });
    it('Closes edit-mode on row correctly upon click of ROW abort button', function() {
        // ARRANGE
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        element.all(by.css('#users-table tr button[ng-click="startEdit(user)"]')).get(2).click();
        element(by.css('#users-table tr button[ng-click="discardEdits()"]')).click();

        // ASSERT
        expect(element.all(by.model('user.model.email')).count()).toBe(0);
        expect(element.all(by.model('user.model.password')).count()).toBe(0);
        expect(element.all(by.model('user.model.role')).count()).toBe(0);
        expect(element.all(by.css('button[ng-click="attemptSave(user)"]')).count()).toBe(0);
        expect(element.all(by.css('button[ng-click="discardEdits()"]')).count()).toBe(0);
    });
    it('Opens edit-mode on row correctly upon click of BOTTOM edit button', function() {
        // ARRANGE
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        element.all(by.css('#users-table tr input[ng-click="checkedChanged()"] + span')).get(2).click();
        element.all(by.css('button[ng-click="startEdit()"]')).click();

        // ASSERT
        element.all(by.css('#users-table tr')).get(3).then(function(row) {
            // Inputs
            expect(row.all(by.css('td span')).get(1).getText()).toBe("bob");
            expect(row.all(by.model('cur.model.email')).first().isPresent()).toBe(true);
            expect(row.all(by.model('cur.model.password')).first().isPresent()).toBe(true);
            expect(row.all(by.model('cur.model.role')).first().isPresent()).toBe(true);
            // Save specific controls
            expect(row.all(by.css('button[ng-click="attemptSave(cur)"]')).first().isPresent()).toBe(true);
            expect(row.all(by.css('button[ng-click="discardEdits()"]')).first().isPresent()).toBe(true);
        });
        expect(element.all(by.model('cur.model.email')).count()).toBe(1);
        expect(element.all(by.model('cur.model.password')).count()).toBe(1);
        expect(element.all(by.model('cur.model.role')).count()).toBe(1);
        expect(element.all(by.css('button[ng-click="attemptSave(cur)"]')).count()).toBe(1); // NOTE: Only 1, because the save button at the bottom does not take a cur object
        expect(element.all(by.css('button[ng-click="discardEdits()"]')).count()).toBe(2); // NOTE: 2 because there's also a discard button at the bottom of the page
    });
    it('Closes edit-mode on row correctly upon click of ROW abort button', function() {
        // ARRANGE
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        element.all(by.css('#users-table tr button[ng-click="startEdit(user)"]')).get(2).click();
        element(by.css('.bottom-button-group button[ng-click="discardEdits()"]')).click();

        // ASSERT
        expect(element.all(by.model('user.model.email')).count()).toBe(0);
        expect(element.all(by.model('user.model.password')).count()).toBe(0);
        expect(element.all(by.model('user.model.role')).count()).toBe(0);
        expect(element.all(by.css('button[ng-click="attemptSave(user)"]')).count()).toBe(0);
        expect(element.all(by.css('button[ng-click="discardEdits()"]')).count()).toBe(0);
    });
    it('Discards changes correctly when edit-mode is aborted', function() {
        // ARRANGE
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        element.all(by.css('#users-table tr button[ng-click="startEdit(user)"]')).get(2).click();   // Start editing row
        element.all(by.model('user.model.email')).sendKeys("mmm");                                  // Modify inputs
        element.all(by.model('user.model.password')).sendKeys("mmm");
        element.all(by.model("user.model.role"))
            .all(by.cssContainingText('option', 'Admin'))
            .click();
        element(by.css('.bottom-button-group button[ng-click="discardEdits()"]')).click();                           // Abort edit

        // ASSERT
        var row = element.all(by.css('#users-table tr')).get(3)     // Third row (0 is header row)
            .all(by.css("td span"))                                 // All of its cells
            .reduce(function(array, cell) {                         // Collect text and reduce into an array
                cell.getText().then(function(text) {
                    array.push(text);
                });
                return array;
            }, []);

        expect(row)
            .toEqual([ '', 'bob', 'bobsaget@gihub.com', '(encrypted)', '', '12th Dec 2014', 'a few seconds ago', 'Gastronomist' ]);
    });
    it('Submits changes correctly upon click of BOTTOM confirm button', function() {
        // ARRANGE
        mockEndpoints.push({
            // Confirm edit of bob row
            request: { method: 'PUT', path: backendRoot + '/user/bob' },
            response: {
                status: 200
            }
        });
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        element.all(by.css('#users-table tr button[ng-click="startEdit(user)"]')).get(2).click();   // Start editing row
        element.all(by.model('cur.model.email')).sendKeys("mmm");                                  // Modify inputs
        element.all(by.model('cur.model.password')).sendKeys("mmm");
        element.all(by.model("cur.model.role"))
            .all(by.cssContainingText('option', 'Admin'))
            .click();
        element(by.css('div button[ng-click="attemptSave(cur)"]')).click();                        // Confirm edit

        // ASSERT
        var row = element.all(by.css('#users-table tr')).get(3)     // Third row (0 is header row)
            .all(by.css("td span"))                                 // All of its cells
            .reduce(function(array, cell) {                         // Collect text and reduce into an array
                cell.getText().then(function(text) {
                    array.push(text);
                });
                return array;
            }, []);

        expect(row)
            .toEqual([ '', 'bob', 'bobsaget@gihub.commmm', '(encrypted)', '', '12th Dec 2014', 'a few seconds ago', 'Admin' ]);
        expect(requestsMade()).toEqual([
            { url : backendRoot + '/isloggedin', method : 'GET' },
            { url : backendRoot + '/user', method : 'GET' },
            { url : backendRoot + '/user/bob', method : 'PUT', data : { $query : {  }, $save : {  }, __v : 0, $get : {  }, password : 'mmm', $delete : {  }, modified : '', username : 'bob', $update : {  }, _id : '548305347b1c1b2d5f802a00', toJSON : {  }, email : 'bobsaget@gihub.commmm', created : '2014-12-06T13:31:32.240Z', authMethod : 'local', $remove : {  }, role : 'admin' } }
        ]);
    });
    it('Submits changes correctly upon click of ROW confirm button', function() {
        // ARRANGE
        mockEndpoints.push({
            // Confirm edit of bob row
            request: { method: 'PUT', path: backendRoot + '/user/bob' },
            response: {
                status: 200
            }
        });
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        element.all(by.css('#users-table tr button[ng-click="startEdit(user)"]')).get(2).click();   // Start editing row
        element.all(by.model('cur.model.email')).sendKeys("mmm");                                  // Modify inputs
        element.all(by.model('cur.model.password')).sendKeys("mmm");
        element.all(by.model("cur.model.role"))
            .all(by.cssContainingText('option', 'Admin'))
            .click();
        element(by.css('#users-table tr button[ng-click="attemptSave(cur)"]')).click();            // Confirm edit

        // ASSERT
        var row = element.all(by.css('#users-table tr')).get(3)     // Third row (0 is header row)
            .all(by.css("td span"))                                 // All of its cells
            .reduce(function(array, cell) {                         // Collect text and reduce into an array
                cell.getText().then(function(text) {
                    array.push(text);
                });
                return array;
            }, []);

        expect(row)
            .toEqual([ '', 'bob', 'bobsaget@gihub.commmm', '(encrypted)', '', '12th Dec 2014', 'a few seconds ago', 'Admin' ]);
        expect(requestsMade()).toEqual([
            { url : backendRoot + '/isloggedin', method : 'GET' },
            { url : backendRoot + '/user', method : 'GET' },
            { url : backendRoot + '/user/bob', method : 'PUT', data : { $query : {  }, $save : {  }, __v : 0, $get : {  }, password : 'mmm', $delete : {  }, modified : '', username : 'bob', $update : {  }, _id : '548305347b1c1b2d5f802a00', toJSON : {  }, email : 'bobsaget@gihub.commmm', created : '2014-12-06T13:31:32.240Z', authMethod : 'local', $remove : {  }, role : 'admin' } }
        ]);
    });
    it('Submits delete correctly upon click of ROW delete button', function() {
        // ARRANGE
        mockEndpoints.push({
            // Confirm deletion of bob row
            request: { method: 'DELETE', path: backendRoot + '/user/bob' },
            response: {
                status: 200
            }
        });
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        disableModalAnimation();
        element.all(by.css('#users-table tr button[ng-click="startDelete(user)"]')).get(2).click();
        element.all(by.css('button[ng-click="attemptDelete()"]')).click();

        // ASSERT
        var bobCells = element.all(by.css('#users-table tr td span'))
            .filter(function(elem) {    // Filter to cells which contain the text bob
                return elem.getText().then(function(text) {
                    return text == "bob";
                });
            });
        expect(bobCells.count()).toBe(0);    // Expecting result to be an empty list
        expect(requestsMade()).toEqual([
            { url : backendRoot + '/isloggedin', method : 'GET' },
            { url : backendRoot + '/user', method : 'GET' },
            { url : backendRoot + '/user/bob', method : 'DELETE' }
        ]);
    });
    it('Submits delete correctly upon click of BOTTOM delete button', function() {
        // ARRANGE
        mockEndpoints.push({
            // Confirm deletion of bob row
            request: { method: 'DELETE', path: backendRoot + '/user/bob' },
            response: {
                status: 200
            }
        });
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        disableModalAnimation();
        element.all(by.css('#users-table tr input[ng-click="checkedChanged()"] + span')).get(2).click();
        element.all(by.css('button[ng-click="startDelete()"]')).click();
        element.all(by.css('button[ng-click="attemptDelete()"]')).click();

        // ASSERT
        var bobCells = element.all(by.css('#users-table tr td span'))
            .filter(function(elem) {    // Filter to cells which contain the text bob
                return elem.getText().then(function(text) {
                    return text == "bob";
                });
            });
        expect(bobCells.count()).toBe(0);    // Expecting result to be an empty list
        expect(requestsMade()).toEqual([
            { url : backendRoot + '/isloggedin', method : 'GET' },
            { url : backendRoot + '/user', method : 'GET' },
            { url : backendRoot + '/user/bob', method : 'DELETE' }
        ]);
    });
    it('Aborts deletion correctly upon click of abort button', function() {
        // ARRANGE
        mockEndpoints.push({
            // Confirm deletion of bob row
            request: { method: 'DELETE', path: backendRoot + '/user/bob' },
            response: {
                status: 200
            }
        });
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        disableModalAnimation();
        element.all(by.css('#users-table tr button[ng-click="startDelete(user)"]')).get(2).click();
        element.all(by.css('.modal-footer button[data-dismiss="modal"]')).click();

        // ASSERT
        expect(element.all(by.css('#users-table tr')).count()).toBe(5); // NOTE: Including header
        expect(requestsMade()).toEqual([
            { url : backendRoot + '/isloggedin', method : 'GET' },
            { url : backendRoot + '/user', method : 'GET' }
            // No delete request, please!
        ]);
    });
    it('Displays target user correctly in deletion prompt', function() {
        // ARRANGE
        mockEndpoints.push({
            // Confirm deletion of bob row
            request: { method: 'DELETE', path: backendRoot + '/user/bob' },
            response: {
                status: 200
            }
        });
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        disableModalAnimation();
        element.all(by.css('#users-table tr button[ng-click="startDelete(user)"]')).get(2).click();

        // ASSERT
        var listedUsers = element.all(by.repeater("user in deleting"));
        expect(listedUsers.count()).toBe(1);
        expect(listedUsers.all(by.tagName("span")).first().getText()).toEqual("bob (bobsaget@gihub.com) ");
    });
    it('Submits MULTI-USER delete correctly', function() {
        // ARRANGE
        mockEndpoints.push({
                // Confirm deletion of bob row
                request: { method: 'DELETE', path: backendRoot + '/user/bob' },
                response: {
                    status: 200
                }
            }, {
                // Confirm deletion of johndoe row
                request: { method: 'DELETE', path: backendRoot + '/user/johndoe' },
                response: {
                    status: 200
                }
            });
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        disableModalAnimation();
        element.all(by.css('#users-table tr input[ng-click="checkedChanged()"] + span')).get(2).click();
        element.all(by.css('#users-table tr input[ng-click="checkedChanged()"] + span')).get(3).click();
        element.all(by.css('button[ng-click="startDelete()"]')).click();
        element.all(by.css('button[ng-click="attemptDelete()"]')).click();

        // ASSERT
        var bobjohnCells = element.all(by.css('#users-table tr td span'))
            .filter(function(elem) {    // Filter to cells which contain the text bob or johndoe
                return elem.getText().then(function(text) {
                    return text == "bob" || text == "johndoe";
                });
            });
        expect(bobjohnCells.count()).toBe(0);    // Expecting result to be an empty list
        expect(requestsMade()).toEqual([
            { url : backendRoot + '/isloggedin', method : 'GET' },
            { url : backendRoot + '/user', method : 'GET' },
            { url : backendRoot + '/user/bob', method : 'DELETE' },
            { url : backendRoot + '/user/johndoe', method : 'DELETE' }
        ]);
    });
    it('Displays target users correctly in MULTI-USER deletion prompt', function() {
        // ARRANGE
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        disableModalAnimation();
        element.all(by.css('#users-table tr input[ng-click="checkedChanged()"] + span')).get(2).click();
        element.all(by.css('#users-table tr input[ng-click="checkedChanged()"] + span')).get(3).click();
        element.all(by.css('button[ng-click="startDelete()"]')).click();

        // ASSERT
        var listedUsers = element.all(by.repeater("user in deleting"))
            .all(by.tagName("span"))
            .reduce(function(array, cell) {                         // Collect text and reduce into an array
                cell.getText().then(function(text) {
                    array.push(text);
                });
                return array;
            }, []);
        expect(listedUsers).toEqual(["bob (bobsaget@gihub.com) ", "johndoe (johndoe@yahoo.com) "]);
        expect(requestsMade()).toEqual([
            { url : backendRoot + '/isloggedin', method : 'GET' },
            { url : backendRoot + '/user', method : 'GET' }
        ]);
    });
    it('Upon failed deletion, displays target users correctly in MULTI-USER deletion prompt', function() {
        // ARRANGE
        mockEndpoints.push({
            // Confirm deletion of bob row
            request: { method: 'DELETE', path: backendRoot + '/user/bob' },
            response: {
                status: 200
            }
        }, {
            // Confirm deletion of johndoe row
            request: { method: 'DELETE', path: backendRoot + '/user/johndoe' },
            response: {
                status: 500
            }
        });
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        disableModalAnimation();
        element.all(by.css('#users-table tr input[ng-click="checkedChanged()"] + span')).get(2).click();
        element.all(by.css('#users-table tr input[ng-click="checkedChanged()"] + span')).get(3).click();
        element.all(by.css('button[ng-click="startDelete()"]')).click();
        element.all(by.css('button[ng-click="attemptDelete()"]')).click();

        // ASSERT
        var userItems = element.all(by.repeater("user in deleting"));

        var deleted = userItems.all(by.css(".deleted"));    // Bob was marked as deleted
        expect(deleted.count()).toBe(1);
        expect(deleted.first().getText()).toEqual("bob (bobsaget@gihub.com) ");

        var failed = userItems.filter(function(elem) {      // John was not, an error message was shown
            return elem.all(by.css(".text-danger")).count().then(function(count) {
                return count == 1;
            });
        });
        expect(failed.all(by.css(".ng-binding")).first().getText()).toEqual("johndoe (johndoe@yahoo.com) ");

        expect(requestsMade()).toEqual([
            { url : backendRoot + '/isloggedin', method : 'GET' },
            { url : backendRoot + '/user', method : 'GET' },
            { url : backendRoot + '/user/bob', method : 'DELETE' },
            { url : backendRoot + '/user/johndoe', method : 'DELETE' }
        ]);
    });
    it('After aborted MULTI-USER deletion, displays target user correctly in deletion prompt', function() {
        // ARRANGE
        mockEndpoints.push({
            // Confirm deletion of bob row
            request: { method: 'DELETE', path: backendRoot + '/user/bob' },
            response: {
                status: 200
            }
        });
        mocks(mockEndpoints);

        // ACT
        browser.get(frontendRoot + '/#/users');
        disableModalAnimation();
        element.all(by.css('#users-table tr input[ng-click="checkedChanged()"] + span')).get(2).click();
        element.all(by.css('#users-table tr input[ng-click="checkedChanged()"] + span')).get(3).click();
        element.all(by.css('button[ng-click="startDelete()"]')).click();
        element.all(by.css('.modal-footer button[data-dismiss="modal"]')).click();
        element.all(by.css('#users-table tr button[ng-click="startDelete(user)"]')).get(2).click();

        // ASSERT
        var listedUsers = element.all(by.repeater("user in deleting"));
        expect(listedUsers.count()).toBe(1);
        expect(listedUsers.all(by.tagName("span")).first().getText()).toEqual("bob (bobsaget@gihub.com) ");
        expect(requestsMade()).toEqual([
            { url : backendRoot + '/isloggedin', method : 'GET' },
            { url : backendRoot + '/user', method : 'GET' }
        ]);
    });
});