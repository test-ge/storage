require.config({
    baseUrl : baseUrl + 'js/external',
    paths: {
        app: '../app',
        lib: '../lib',
        i18n: '../i18n',
        rest: '../../rest'
    },
    shim : {
        'bootstrap': {
            deps: [ 'jquery' ]
        }
    },
    map : {
        '*' : {
            'jquery' : 'jquery-amd',
            'parsley' : 'lib/parsley',
            'datatables.net' : 'lib/datatables',
            'highlight' : 'highlight.pack'
        },
        'jquery-amd' : {
            'jquery' : 'jquery'
        },
        'lib/parsley' : {
            'parsley' : 'parsley'
        },
        'lib/datatables' : {
            'datatables.net' : 'dataTables.bootstrap'
        },
        'dataTables.bootstrap' : {
            'datatables.net' : 'jquery.dataTables'
        }
    }
});

define('jquery-amd', [ 'jquery' ], function() {
    return jQuery.noConflict(true);
});
