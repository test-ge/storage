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
/*!
 * Depends:
 *      lib/func/translate.js
 */
define([ 'lib/i18n' ], function(i18n) {

    var TWICE = 2;
    var DURATION_MINUTE = 60;
    var DURATION_HOUR = 60 * DURATION_MINUTE;
    var DURATION_DAY = 24 * DURATION_HOUR;
    var DURATION_MONTH = 30 * DURATION_DAY;
    var DURATION_YEAR = 365 * DURATION_DAY;

    return function(value) {
        var duration = (new Date().getTime() - value) / 1000;

        if (duration <= 1) {
            return i18n('a moment ago');
        } else if (duration < DURATION_MINUTE) {
            return i18n('{0} secondes', Math.ceil(duration));
        } else if (duration < TWICE * DURATION_MINUTE) {
            return i18n('a minute');
        } else if (duration < DURATION_HOUR) {
            return i18n('{0} minutes', Math.ceil(duration / DURATION_MINUTE));
        } else if (duration < TWICE * DURATION_HOUR) {
            return i18n('an hour');
        } else if (duration < DURATION_DAY) {
            return i18n('{0} hours', Math.ceil(duration / DURATION_HOUR));
        } else if (duration < TWICE * DURATION_DAY) {
            return i18n('a day');
        } else if (duration < DURATION_MONTH) {
            return i18n('{0} days', Math.ceil(duration / DURATION_DAY));
        } else if (duration < TWICE * DURATION_MONTH) {
            return i18n('a month');
        } else if (duration < DURATION_YEAR) {
            return i18n('{0} month', Math.ceil(duration / DURATION_MONTH));
        } else if (duration < TWICE * DURATION_YEAR) {
            return i18n('a year');
        } else {
            return i18n('{0} years', Math.ceil(duration / DURATION_YEAR));
        }
    };

});
