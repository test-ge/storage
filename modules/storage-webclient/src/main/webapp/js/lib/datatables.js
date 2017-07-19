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
define([ 'jquery', 'datatables.net', 'lib/template', 'lib/func', 'lib/springFormParam' ], function($, DataTable, Template, func, sfp) {

    var defaultOptions = {
        retrieve : true,
        autoWidth : false,
        language : {
            info : 'Affichage de _START_ &agrave; _END_ pour un total de _TOTAL_ entr&eacute;es',
            infoEmpty : 'Aucune entr&eacute;e &agrave; afficher',
            loadingRecords : 'Chargement ...',
            paginate : {
                first : '<i class="fa fa-angle-double-left"></i>',
                previous : '<i class="fa fa-angle-left"></i>',
                next : '<i class="fa fa-angle-right"></i>',
                last : '<i class="fa fa-angle-double-right"></i>'
            },
            processing : 'Chargement ...',
            zeroRecords : 'Aucune entr&eacute;e trouv&eacute;e'
        }
    };

    function linkRenderer(link, target) {
        var tpl = func.renderer('<a href="' + link + '"' + (target ? ' target="' + target + '"' : '') + '>{{__data__}}</a>');
        return function(data, type, row) {
            return tpl($.extend({
                __data__ : data
            }, row));
        };
    }

    function templateRenderer(templateName) {
        var tpl = Template.find(templateName);
        return function(data, type, row) {
            return tpl.render(row);
        };
    }

    function errorRenderer(data, type, row) {
        return '&lt;No renderer&gt;';
    }

    var oldDataTable = $.fn.DataTable;

    $.fn.DataTable = function(opts) {
        var newOpts = $.extend({}, defaultOptions, opts || {});

        return oldDataTable.call($(this).each(function() {
            var tbl = $(this), ajax = tbl.data('ajax'), opts = newOpts;
            if (!oldDataTable.isDataTable($(this))) {
                if (ajax) {
                    if ($.isFunction(window[ajax])) {
                        opts = $.extend(true, {}, opts, {
                            serverSide : true,
                            ajax : window[ajax]
                        });
                        tbl.removeAttr('data-ajax').removeData('ajax');
                    } else {
                        opts = $.extend(true, {}, opts, {
                            serverSide : true,
                            ajax : {
                                url : ajax,
                                type : 'GET',
                                dataType : 'json'
                            }
                        });
                    }
                }

                if (tbl.data('order')) {
                    opts.order = JSON.parse(tbl.data('order').replace(/'/g, '"'));
                    tbl.removeAttr('data-order').removeData('order');
                }

                $('thead th', tbl).each(function(idx, elem) {
                    var th = $(elem), link = th.data('link'), render = th.data('render'), templateName = th.data('template');
                    th.data('render', null).removeAttr('data-render');
                    th.data('template', null).removeAttr('data-template');
                    if (templateName) {
                        th.data('render', templateRenderer(templateName));
                    } else if (render) {
                        th.data('render', func[render] || window[render] || errorRenderer);
                    } else if (link) {
                        th.data('render', linkRenderer(link, th.data('target')));
                    }
                });
            }
            oldDataTable.call(tbl, opts);
        }));
    }

    return $.fn.DataTable;

});
