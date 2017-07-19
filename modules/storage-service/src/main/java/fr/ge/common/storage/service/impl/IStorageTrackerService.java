/**
 * 
 */
package fr.ge.common.storage.service.impl;

import fr.ge.core.exception.TechniqueException;

/**
 * Interface for services to call Tracker.
 *
 * @author $Author: aolubi
 * @version $Revision: 0 $
 */
public interface IStorageTrackerService {

  /**
   * Create a new storage ID.
   *
   * @return the final track id
   * @throws TechniqueException
   *           technique exception
   */
  String createStorageId() throws TechniqueException;

  /**
   * Add a new message to tracker.
   * 
   * @param storageId
   *          storage id
   * @param eventCode
   *          event code
   * @param params
   *          parameters to add to the message
   */
  void addMessage(final String storageId, final String eventCode, final Object... params);

  /**
   * Link into TRACKER a storage id with an existing reference uid.
   * 
   * @param storageId
   *          storage id
   * @param referenceId
   *          existing reference id
   * @throws TechniqueException
   *           technical exception
   */
  void linkReference(final String storageId, final String referenceId) throws TechniqueException;
}
