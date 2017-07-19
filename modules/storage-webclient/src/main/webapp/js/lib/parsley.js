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
define([ 'parsley', 'lib/i18n' ], function(parsley, i18n) {

    parsley.getErrorMessage = function getErrorMessage(constraint) {
        var catalog = parsley._validatorRegistry.catalog;
        var message;

        // Type constraints are a bit different, we have to match their requirements too to find right error message
        if ('type' === constraint.name) {
            var typeMessages = catalog.en[constraint.name] || {};
            message = i18n(typeMessages[constraint.requirements]);
        } else message = this.formatMessage(i18n(catalog.en[constraint.name]), constraint.requirements);

        return message || catalog.en.defaultMessage;
    };

    return parsley;

});
