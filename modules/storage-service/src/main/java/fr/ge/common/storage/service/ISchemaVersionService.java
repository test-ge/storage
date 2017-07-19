/*
 * 
 */
package fr.ge.common.storage.service;

/**
 * Interface to get the database version of "ge_exchange".
 * 
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public interface ISchemaVersionService {

  /**
   * Return the database version of "ge_exchange".
   * 
   * @return the version
   */
  String getVersion();

}
