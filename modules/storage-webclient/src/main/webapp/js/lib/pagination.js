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

/**
 *
 * Options : 
 *    - source : function (pageOffset, cb) { ... }
 *           pageOffser : page offset, starting at 0
 *           cb : callback as function (data) where data is a search result object
 *       ??? instead return a deferrer
 *
 *    - renderItem : function (item, idx) { ... }
 *           item : data to display as plain object
 *           idx : item offset, starting at 0
 *        return jQuery object or plain HTML
 *
 *     - renderItems : function (container, data) { ... }
 *           container : ...
 *           data : search result object
 *
 */
define([ 'jquery' ], function ($) {

    var CTX = 'pagination.context';
    var DEFAULT_OPTS = {
        pageOffset : 0,
        renderItems : defaultRenderItems,
        showPagination : true
    };

    function goToPage(container, pageOffset) {
        if (undefined === pageOffset) {
            pageOffset = container.data('page-offset');
        }

        console.debug('go to page', pageOffset);
        container.data('page-offset', pageOffset);
        container.data(CTX).source(pageOffset, function (searchResult) {
            renderPage(container, searchResult);
        });
    }

    function renderPage(container, searchResult) {
        container.data(CTX).renderItems(container, searchResult);
        if(false !== container.data(CTX).showPagination){
            renderNavbar(container, searchResult);
        }
    }

    function defaultRenderItems(container, searchResult) {
        container.empty();
        searchResult.content.forEach(function (item, idx) {
            var row = container.data(CTX).renderItem(item, idx);
            if (row) {
                container.append(row);
            }
        });
    }

    function buildPaginationEvent(container, pageOffset) {
        return function (evt) {
            evt.preventDefault();
            goToPage(container, pageOffset);
        };
    }

    function emptyPaginationEvent(evt) {
        evt.preventDefault();
    }

    function buildPaginationButton(container, content, pageOffset) {
        var lnk = $('<a href="#"></a>').html(content);

        if (undefined !== pageOffset) {
            lnk.on('click', buildPaginationEvent(container, pageOffset));
        } else {
            lnk.on('click', emptyPaginationEvent);
        }

        return $('<li class="page_link"></li>').append(lnk);
    }

    function renderNavbar(container, searchResult) {
        console.debug('render navigation bar with', searchResult);
        var currentPage = Math.floor(searchResult.startIndex / searchResult.maxResults);
        var numberOfPages = Math.ceil(searchResult.totalResults / searchResult.maxResults);
        var navbar = $('<ul class="pagination"></ul>');
        var previousPage = Math.max(0, currentPage - 1),
            nextPage = Math.min(currentPage + 1, numberOfPages - 1),
            lastPage = numberOfPages - 1;

        navbar.append(buildPaginationButton(container, '<i class="fa fa-angle-double-left"></i>', 0 == currentPage ? undefined : 0));
        navbar.append(buildPaginationButton(container, '<i class="fa fa-angle-left"></i>', previousPage == currentPage ? undefined : previousPage));

        navbar.append(buildPaginationButton(container, currentPage + 1));

        navbar.append(buildPaginationButton(container, '<i class="fa fa-angle-right"></i>', nextPage == currentPage ? undefined : nextPage));
        navbar.append(buildPaginationButton(container, '<i class="fa fa-angle-double-right"></i>', lastPage == currentPage ? undefined : lastPage));

        container.data(CTX).navbar.empty().append(navbar);
    }

    function initialize(self, opts) {
        var ctx = $.extend({}, DEFAULT_OPTS, opts);

        if (!ctx.source) {
            console.warn('No source defined !!!');
            return;
        } else if (!ctx.renderItem && !opts.renderItems) {
            console.warn('No renderer defined !!!');
            return;
        }

        return self.each(function (idx, item) {
            var container = $(item);

            ctx.navbar = $('<nav class="page_navigation" aria-label="Page navigation"></nav>').appendTo(container.parent());
            container.data(CTX, ctx);

            goToPage(container, ctx.pageOffset);
        });
    }

    function execute(self, cmd, opts) {
        if (undefined === self.data(CTX)) {
            console.info('pagination not initialized');
            return self;
        }

        if ('refresh' === cmd) {
            goToPage(self);
        } else {
            console.info('unknown command "%s"', cmd);
        }

        return self;
    }

    return $.fn.pagination = function (cmd, opts) {
        if ($.isPlainObject(cmd)) {
            return initialize(this, cmd);
        } else {
            return execute(this, cmd, opts);
        }
    };

});
