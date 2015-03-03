/**
 * Created by niels on 12/7/14.
 */

exports.config = {
    seleniumAddress: (process.env.SELENIUM_URL || 'http://localhost:4444/wd/hub'),
    specs: ['partials/*.js'],
    capabilities: {
        'browserName': 'chrome',
        'chromeOptions': { }
        //'args': [ '--verbose --enable-logging --v=1 --log-path=chromedriver.log' ]
    },
    /* // Default mocks
    mocks: {
        dir: 'mocks',
        default: ['default']
    },*/
    onPrepare: function(){
        // Backend mocking
        require('protractor-http-mock').config = {
            rootDirectory: __dirname, // default value: process.cwd()
            protractorConfig: 'protractor.conf.js' // default value: 'protractor.conf'
        };
        // XML output
        if(process.env.ENABLE_XML_OUTPUT) {
            require('jasmine-reporters');
            jasmine.getEnv().addReporter(
                new jasmine.JUnitXmlReporter('tests/results', true, true));
        }
    }
};