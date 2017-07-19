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
define([ 'jquery' ], function ($) {

    var Loader = {};

    var _backdrop;
    var _root;
    var _loader;
    var _isShown;

    $(function () {
        _root = $('body');
    });

    Loader.show = function () {
        if (_isShown) {
            return;
        }

        _isShown = true;
        _backdrop = $('<div class="loader-backdrop"></div>').appendTo(_root);

        _backdrop.fadeIn(function () {
            _loader = $('<div class="loader"><object type="image/svg+xml" data="' + baseUrl + '/images/ring.svg" /></div>').appendTo(_root);
        });
    };

    Loader.hide = function () {
        if (!_isShown) {
            return;
        }

        if (_loader) {
            _loader.remove();
            _loader = null;
        }

        if (_backdrop) {
            _backdrop.fadeOut(function () {
                _backdrop.remove();
                _backdrop = null;
            });
        }

        _isShown = false;
    };

    return Loader;

});
