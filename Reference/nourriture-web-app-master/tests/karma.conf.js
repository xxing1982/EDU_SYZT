// Karma configuration
// Generated on Sat Dec 06 2014 10:50:31 GMT+0800 (CST)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '../',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    //
    // useful links to current frameworks:
    //  * Mocha         http://mochajs.org/#getting-started
    //  * Chai          http://chaijs.com/api/bdd/
    //  * Sinon         http://sinonjs.org/docs/
    //  * Sinon-chai    https://www.npmjs.org/package/karma-chai-sinon
    frameworks: ['mocha', 'chai-sinon'],


    // list of files / patterns to load in the browser (TODO: Use requirejs or some other script to obtain these from index.html. Otherwise we have to maintain two lists)
    files: [
        'node_modules/jquery/dist/jquery.js',
        'node_modules/bootstrap/dist/js/bootstrap.js',
        'node_modules/angular/angular.js',
        'node_modules/angular-route/angular-route.js',
        'node_modules/angular-resource/angular-resource.js',
        'node_modules/angular-mocks/angular-mocks.js',
        'node_modules/moment/moment.js',
        'node_modules/lodash/dist/lodash.js',
        'node_modules/async/lib/async.js',
        'js/controllers/mainCtrl.js', // Ensure this is loaded before any other controllers (TODO: Fix the need for this, each controller file should be independent)
        'js/**/*.js',
        'config.js',
        'tests/services/**/*.js'
    ],

    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
        'js/**/*.js' : ['coverage']
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: false,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false
  });
};
