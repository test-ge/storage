/*
 * Copyright SCN Guichet Entreprises, Capgemini and contributors (2016-2017)
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
package fr.ge.common.storage.test;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class AbstractDbTest.
 *
 * @author aolubi
 */
public abstract class AbstractDbTest {

  /** The Constant LOGGER. */
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDbTest.class);

  /** The data source. */
  @Autowired
  private DataSource dataSource;

  /**
   * Inits the db.
   *
   * @param dataSetResourceNames
   *          the data set resource names
   * @throws Exception
   *           the exception
   */
  @Transactional
  protected void initDb(final String... dataSetResourceNames) throws Exception {
    LOGGER.info("Starting data initialization....");

    final DataSourceDatabaseTester databaseTester = new DataSourceDatabaseTester(this.dataSource);
    final List < IDataSet > dataSets = new ArrayList<>();
    for (final String dataSetResourceName : dataSetResourceNames) {
      final ReplacementDataSet dataSet = new ReplacementDataSet(
        new FlatXmlDataSetBuilder().build(this.getClass().getResourceAsStream(dataSetResourceName)));
      dataSet.addReplacementObject("[NULL]", null);
      dataSets.add(dataSet);
    }

    databaseTester.setDataSet(new CompositeDataSet(dataSets.toArray(new IDataSet[] {})));
    databaseTester.onSetup();

    LOGGER.info("Data initialization done.");
  }

  /**
   * Resource name.
   *
   * @param name
   *          the name
   * @return the string
   */
  protected String resourceName(final String name) {
    return String.format("%s-%s", this.getClass().getSimpleName(), name);
  }

}
