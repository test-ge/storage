package fr.ge.common.storage.service.mapper;

/**
 * Mapper for SchemaVersion table.
 *
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public interface SchemaVersionMapper {

  /**
   * Gets the actual database version.
   * 
   * @return the version
   */
  String getVersion();

}
