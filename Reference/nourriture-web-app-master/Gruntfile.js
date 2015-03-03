/**
 * Created by niels on 12/2/14.
 */

module.exports = function(grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        version: grunt.option('buildnumber'),
        copy: {
            main: {
                src: ['index.html', 'config.js', 'partials/**', 'img/**'],
                dest: 'dist/package/'
            },
            fonts: {
                expand: true,
                src: ['css/fonts/*', 'node_modules/font-awesome/fonts/*'],
                dest: 'dist/package/fonts/',
                flatten: true,
                filter: 'isFile'
            }
        },
        useminPrepare: {
            build: {
                src: 'index.html',
                options: {
                    dest: 'dist/package'
                }
            }
        },
        usemin: {
            html: 'dist/package/index.html'
        },
        compress: {
            build: {
                options: {
                    archive: 'dist/<%= pkg.name %>-<%= version %>.zip'
                },
                files: [
                    {expand: true, cwd: 'dist/package', src: ['**'], dest: '<%= pkg.name %>/'}
                ]
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-usemin');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-compress');

    grunt.registerTask('build', [
        'copy:main',
        'copy:fonts',
        'useminPrepare',
        'concat:generated',
        'cssmin:generated',
        'uglify:generated',
        'usemin',
        'compress'
    ]);
}