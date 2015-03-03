/**
 * Created by niels on 12/6/14.
 *
 * Unit tests for login view and controller
 */

var mocks = require('protractor-http-mock');

var frontendRoot = (process.env.FRONTEND_ROOT || 'http://localhost:8080');
var backendRoot = (process.env.BACKEND_ROOT || 'http://localhost:2121');

describe("Test login view and controller", function() {

    /**
     *  Tested with following server responses:
     *      200: Success
     *      401: Unauthorized
     *      500: Server error
     *      0:   Connection refused
     */

    var mockEndpoints;
    beforeEach(function() {
        mockEndpoints = [
            // Not logged in already
            {
                request: {  method: 'GET', path: backendRoot + '/isloggedin' },
                response: {
                    status: 401
                }
            }
        ];
    });

    afterEach(function() {
        mocks.teardown();
    });

    it('Shows empty login form', function() {
        mocks(mockEndpoints);

        browser.get(frontendRoot + '/#/login');

        expect(element(by.model('credentials.username')).isPresent()).toBe(true);   // Username input
        expect(element(by.model('credentials.password')).isPresent()).toBe(true);   // Password input
        expect(element(by.css('[ng-click="attemptLogin(credentials)"]')).isPresent()).toBe(true);  // Login button
    });

    it('Navigates to front page upon successful login', function() {
        mockEndpoints.push({
            // Successful login
            request: { method: 'POST', path: backendRoot + '/login' },
            response: {
                status: 200,
                data: { username: "bob", role:"admin" }
            }
        });
        mocks(mockEndpoints);

        browser.get(frontendRoot + '/#/login');

        element(by.model('credentials.username')).sendKeys("bob");
        element(by.model('credentials.password')).sendKeys("secret");

        element(by.css('#buttonLogin')).click();

        expect(browser.getCurrentUrl()).toEqual(frontendRoot + "/#/");
    });

    it('Shows appropriate alert on invalid credentials', function() {
        mockEndpoints.push({
            // Unauthorized login (invalid credentials)
            request: { method: 'POST', path: backendRoot + '/login' },
            response: {
                status: 401
            }
        });
        mocks(mockEndpoints);

        browser.get(frontendRoot + '/#/login');

        element(by.model('credentials.username')).sendKeys("bob");
        element(by.model('credentials.password')).sendKeys("secret");

        element(by.css('#buttonLogin')).click();

        expect(element(by.css("#alertInvalidCredentials")).isPresent()).toBe(true);

    });

    it('Shows appropriate alert on unexpected error', function() {
        mockEndpoints.push({
            // Unexpected error (Internal server error/bug)
            request: { method: 'POST', path: backendRoot + '/login' },
            response: {
                status: 500
            }
        });
        mocks(mockEndpoints);

        browser.get(frontendRoot + '/#/login');

        element(by.model('credentials.username')).sendKeys("bob");
        element(by.model('credentials.password')).sendKeys("secret");

        element(by.css('[ng-click="attemptLogin(credentials)"]')).click();

        expect(element(by.css("#alertUnexpectedError")).isPresent()).toBe(true);
    });

    it('Shows appropriate alert on connection issues', function() {
        mockEndpoints.push({
            // Connection refused (Client lacks connection or server is offline)
            request: { method: 'POST', path: backendRoot + '/login' },
            response: {
                status: 0
            }
        });
        mocks(mockEndpoints);

        browser.get(frontendRoot + '/#/login');

        element(by.model('credentials.username')).sendKeys("bob");
        element(by.model('credentials.password')).sendKeys("secret");

        element(by.css('[ng-click="attemptLogin(credentials)"]')).click();

        expect(element(by.css("#alertConncetionIssues")).isPresent()).toBe(true);
    });

    it('Shows appropriate alert on missing username', function() {
        mocks(mockEndpoints);

        browser.get(frontendRoot + '/#/login');

        element(by.model('credentials.password')).sendKeys("secret");

        element(by.css('[ng-click="attemptLogin(credentials)"]')).click();

        expect(element(by.css("#alertMissingUsername")).isDisplayed()).toBe(true);
    });

    it('Shows appropriate alert on missing password', function() {
        mocks(mockEndpoints);

        browser.get(frontendRoot + '/#/login');

        element(by.model('credentials.username')).sendKeys("bob");

        element(by.css('[ng-click="attemptLogin(credentials)"]')).click();

        expect(element(by.css("#alertMissingPassword")).isDisplayed()).toBe(true);
    });

    it('Shows appropriate alert on missing username and password', function() {
        mocks(mockEndpoints);

        browser.get(frontendRoot + '/#/login');

        element(by.css('[ng-click="attemptLogin(credentials)"]')).click();

        expect(element(by.css("#alertMissingPassword")).isDisplayed()).toBe(true);
        expect(element(by.css("#alertMissingUsername")).isDisplayed()).toBe(true);
    });

    it('Shows appropriate alert on malformed username', function() {
        mocks(mockEndpoints);

        browser.get(frontendRoot + '/#/login');

        element(by.model('credentials.username')).sendKeys("bob++");
        element(by.model('credentials.password')).sendKeys("secret");

        element(by.css('[ng-click="attemptLogin(credentials)"]')).click();

        expect(element(by.css("#alertMalformedUsername")).isDisplayed()).toBe(true);
    });
});

