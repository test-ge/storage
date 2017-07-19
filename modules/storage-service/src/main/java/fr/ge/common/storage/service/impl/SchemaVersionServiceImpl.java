/**
 * 
 */
package fr.ge.common.storage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import fr.ge.common.storage.service.ISchemaVersionService;
import fr.ge.common.storage.service.mapper.SchemaVersionMapper;

/**
 * Implementation for ISchemaVersionService.
 * 
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public class SchemaVersionServiceImpl implements ISchemaVersionService {

  /** The schema version mapper. */
  @Autowired
  private SchemaVersionMapper schemaVersionMapper;

  @Override
  public String getVersion() {
    return schemaVersionMapper.getVersion();
  }
}
