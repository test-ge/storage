/**
 * 
 */
package fr.ge.common.storage.service;

import java.util.List;

import fr.ge.common.storage.bean.StorageBean;
import fr.ge.core.exception.TechniqueException;

/**
 * Persistence service for Storge bubble.
 * 
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public interface IStorageService {

  /**
   * Method allowing to store a file.
   * 
   * @param storageFile
   *          the byte array
   * @param storageStatus
   *          the storage status
   * @param storageFileName
   *          the storage original file name
   * @param storageReferenceId
   *          the storage existing reference uid
   * @param storageRootDirectory
   *          the storage root directory
   * @return Storage bean
   * @throws TechniqueException
   *           Technical exception
   */
  StorageBean storeFile(final byte[] storageFile, final String storageStatus, final String storageFileName,
    final String storageReferenceId, final String storageRootDirectory) throws TechniqueException;

  /**
   * Method allowing to remove a file.
   * 
   * @param storageId
   *          Storage uid
   * @param storageTray
   *          Storage tray
   * @param storageRootDirectory
   *          the storage root directory
   * @return Storage bean
   * @throws TechniqueException
   *           Technical exception
   */
  StorageBean remove(final String storageId, final String storageTray, final String storageRootDirectory)
    throws TechniqueException;

  /**
   * Get a storage file information from db.
   * 
   * @param storageId
   *          Storage uid
   * @param storageTray
   *          Storage tray
   * @return Storage bean
   * @throws TechniqueException
   *           Technical exception
   */
  StorageBean get(final String storageId, final String storageTray) throws TechniqueException;

  /**
   * Get all storage tray file from db.
   * 
   * @param storageTray
   *          Storage tray
   * @return List of Storage bean
   * @throws TechniqueException
   *           Technical exception
   */
  List < StorageBean > getAll(final String storageTray) throws TechniqueException;
}
