/*
 * Copyright SCN Guichet Entreprises, Capgemini and contributors (2014-2016)
 *
 * This software is a computer program whose purpose is to maintain and
 * administrate standalone forms.
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */
'use strict';

module.exports = function(grunt) {

    var bower = require('./bower.json');

    var buildDir = grunt.option('destination') || 'target';
    var srcCssDir = grunt.option('less-src') || 'src/main/webapp/css';
    var srcJsDir = grunt.option('js-src') || 'src/main/webapp/js';
    var dstCssDir = grunt.option('css-dst') || '<%= project.build.dir %>/css';
    var dstCssTempDir = dstCssDir; // 'target/tmp-css';

    /**
     * Search for bower dependencies
     * 
     * @param dependencies
     *            as object (name => version)
     */
    function loadBowerDependencies(deps) {
        var dependencies = [];

        function attributeComparator(value, attrName) {
            return function(elm, idx, all) {
                return value == elm[attrName];
            }
        }

        function loadDependency(depName) {
            var nfo;
            try {
                nfo = require('./bower_components/' + depName + '/bower.json');
            } catch (error) {
                console.log('Main descriptor "bower.json" not found for ' + depName + ', searching for ".bower.json"');
                nfo = require('./bower_components/' + depName + '/.bower.json');
            }

            if (!nfo) {
                console.warn('No bower components found for "' + depName + "'");
                return;
            }

            if (dependencies.find(attributeComparator(depName, 'name'))) {
                return;
            }

            var obj = {
                name : nfo.name,
                version : nfo.version,
                css : [],
                js : [],
                less : [],
                unknown : []
            };

            dependencies = dependencies.concat(loadBowerDependencies(nfo.dependencies));

            var mains = nfo.main;

            if (!mains) {
                console.warn('No main file defined for lib ' + depName);
                return;
            }

            if (undefined === mains.forEach) {
                mains = [ mains ];
            }

            var extensionToHarvest = [ 'js', 'css', 'less' ];

            mains.forEach(function(main) {
                var ext = main.replace(/^.*\.([^.]+)$/, '$1');
                if (extensionToHarvest.indexOf(ext) < 0) {
                    obj['unknown'].push('bower_components/' + depName + '/' + main);
                } else {
                    obj[ext].push('bower_components/' + depName + '/' + main);
                }
            });

            dependencies.push(obj);
        }

        if (deps) {
            for ( var depName in deps) {
                loadDependency(depName);
            }
        }

        return dependencies;
    }

    var bowerDeps = loadBowerDependencies(bower.dependencies);
    var copyJsFiles = [];

    var jqueryUi = bowerDeps.find(function (elm) { return 'jquery-ui' == elm.name; });
    
    if (jqueryUi) {
        bowerDeps = bowerDeps.filter(function (elm) { return jqueryUi.name != elm.name; });
        
        console.log("warn " + bowerDeps);
        
        copyJsFiles.push({
            expand : true,
            cwd : 'bower_components/' + jqueryUi.name + '/ui/i18n',
            src : '*.js',
            dest : '<%= project.build.dir %>/js/i18n/' + jqueryUi.name
        });

        copyJsFiles.push({
            expand : true,
            cwd : 'bower_components/' + jqueryUi.name + '/ui',
            src : [ '*.js', 'widgets/*.js' ],
            dest : '<%= project.build.dir %>/js/external/' + jqueryUi.name
        });
    }

    var prism = bowerDeps.find(function (elm) { return 'prism' == elm.name; });

    if (prism) {
        prism.js.push('bower_components/prism/plugins/line-highlight/prism-line-highlight.js');
        prism.css.push('bower_components/prism/plugins/line-highlight/prism-line-highlight.css');

        prism.js.push('bower_components/prism/plugins/line-numbers/prism-line-numbers.js');
        prism.css.push('bower_components/prism/plugins/line-numbers/prism-line-numbers.css');
    }

//    copyJsFiles.push({
//        expand: true,
//        flatten: true,
//        cwd : 'bower_components/prism/plugins',
//        src : [ 'line-highlight/*.js', 'line-numbers/*.js' ],
//        dest : '<%= project.build.dir %>/js/external/prism'
//    });

    copyJsFiles.push({
        expand : true,
        flatten : true,
        src : bowerDeps.reduce(function(all, elm) {
            return all.concat(elm.js);
        }, []).reduce(function(all, elm) {
            return all.indexOf(elm) < 0 ? all.concat(elm) : all;
        }, []),
        dest : '<%= project.build.dir %>/js/external/'
    });

    var lessFiles = grunt.file.expandMapping([ '**/*.less', '**/*.css', '!mixins/**' ], dstCssTempDir, {
        cwd : srcCssDir
    }).map(function(file) {
        return {
            src : file.src,
            dest : file.dest.replace(/\.less$/, '.css')
        };
    });

    bowerDeps.forEach(function(dep) {
        [].concat(dep.css, dep.less).forEach(function(src) {
            var o = {
                src : src,
                dest : dstCssTempDir + '/libs/' + dep.name + '/' + src.replace(/.*\/([^/]+)\.[^.]+/, '$1.css')
            };

            lessFiles.push(o);
        });
    });

    /*
     * GRUNT configuration
     */
    grunt.initConfig({
        project : {
            build : {
                dir : grunt.option('destination') || 'target'
            },
            source : {
                dir : 'src/main',
                js : srcJsDir,
                css : srcCssDir
            }
        },
//        less : {
//            dist : {
//                options : {
//                    modifyVars : {
//                        'fa-font-path' : 'fonts',
//                        'fa-font-path' : 'fonts'
//                    },
//                    paths : [ '<%= project.source.css %>/mixins', 'bower_components/bootstrap/less', 'bower_components/font-awesome/less' ]
//                },
//                files : bowerDeps.map(function(elm) {
//                    return [].concat(elm.css, elm.less).map(function(src) {
//                        return {
//                            src : src,
//                            dest : dstCssTempDir + '/libs/' + elm.name + '/' + src.replace(/.*\/([^/]+)\.[^.]+/, '$1.css')
//                        };
//                    }).concat(grunt.file.expandMapping([ '**/*.less', '**/*.css', '!mixins/**' ], dstCssTempDir, {
//                        cwd : srcCssDir
//                    }).map(function(file) {
//                        return {
//                            src : file.src[0],
//                            dest : file.dest.replace(/\.less$/, '.css')
//                        };
//                    }));
//                }).reduce(function(all, elm) {
//                    return all.concat(elm);
//                }, []).reduce(function(all, elm) {
//                    return !all.find(function(e) {
//                        return e.src == elm.src;
//                    }) ? all.concat(elm) : all;
//                }, [])
//            }
//        },
        cssmin : {
            options : {
                keepSpecialComments : 0
            },
            dist : {
                files : [ {
                    expand : true,
                    cwd : dstCssTempDir,
                    src : [ '**/*.css', '!**/*.min.css' ],
                    dest : dstCssDir,
                    ext : '.min.css'
                } ]
            }
        },
        copy : {
            dist : {
                options : {
                    process : function(content, srcPath, dstPath) {
                        if (grunt.file.isMatch(['*/bootstrap/**/*.js', '*/pwstrength-bootstrap/**/*.js'], srcPath)) {
                            return 'define([ \'jquery\' ], function (jQuery) { ' + content + '});';
                        } else {
                            return content;
                        }
                    },
                    noProcess : [ '!**/*.js' ]
                },
                files : copyJsFiles
            },
            binaries : {
                files : [ {
                    expand : true,
                    cwd : 'bower_components',
                    src : [ 'font-awesome/fonts/**', 'bootstrap/fonts/**' ],
                    dest : '<%= project.build.dir %>/css/libs'
                }, {
                    expand : true,
                    cwd : 'bower_components/jquery-ui/themes/redmond/',
                    src : [ 'jquery-ui.css', 'images/**' ],
                    dest : '<%= project.build.dir %>/css/libs/jquery-ui'
                }, {
                    expand : true,
                    cwd : 'bower_components/intl-tel-input/build/',
                    src : [ 'img/**' ],
                    dest : '<%= project.build.dir %>/css/libs/intl-tel-input'
                } ]
            }
        },
        i18n : {
            dist : {
                cwd : 'src/main/resources',
                src : '**/*.po',
                dest : '<%= project.build.dir %>/js/i18n'
            }
        }
    });

    require('load-grunt-tasks')(grunt);

    // grunt.registerTask('default', [ 'less', 'browserify', 'concat', 'cssmin',
    // 'copy' ]);
    //grunt.registerTask('default', [ 'less', 'copy', 'cssmin', ]);
    grunt.registerTask('default', ['copy', 'cssmin', ]);

}
