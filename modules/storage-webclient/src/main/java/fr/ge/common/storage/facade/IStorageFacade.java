/**
 * 
 */
package fr.ge.common.storage.facade;

import javax.ws.rs.core.Response;

import fr.ge.common.storage.ws.model.StoredTrayResult;
import fr.ge.common.storage.ws.model.StoredTrayTree;
import fr.ge.core.exception.TechniqueException;

/**
 * Interface of the facade to call storage WS and handle the errors.
 *
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public interface IStorageFacade {

  /**
   * Get all storage file in a specific tray.
   * 
   * @param storageTray
   *          The storage tray
   * @return All storage file in a tray
   * @throws TechniqueException
   *           The technical exception
   */
  StoredTrayResult getTray(final String storageTray) throws TechniqueException;

  /**
   * Download a storage file from a tray
   * 
   * @param storageId
   *          Storage file id
   * @param storageTray
   *          Storage tray
   * @return The response
   * @throws TechniqueException
   *           The technical exception
   */
  Response download(final String storageId, final String storageTray) throws TechniqueException;

  /**
   * Delete a storage file from a tray
   * 
   * @param storageId
   *          Storage file id
   * @param storageTray
   *          Storage tray
   * @return The response
   * @throws TechniqueException
   *           The technical exception
   */
  void remove(final String storageId, final String storageTray) throws TechniqueException;

  /**
   * Generate a storage tree from a specific tray.
   * 
   * @param storageTray
   *          The storage tray
   * @return A storage tree
   * @throws TechniqueException
   *           The technical exception
   */
  StoredTrayTree generateTree(final String storageTray) throws TechniqueException;

}
