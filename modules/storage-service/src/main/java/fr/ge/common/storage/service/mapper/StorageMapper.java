package fr.ge.common.storage.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import fr.ge.common.storage.bean.StorageBean;

/**
 * {@link StorageSendBean} mapper.
 *
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public interface StorageMapper {

  /**
   * Inserts a file into db.
   * 
   * @param storageSendBean
   *          the storage information
   */
  void insertStorage(@Param("storageBean") final StorageBean storageBean);

  /**
   * Get a tray file from db.
   * 
   * @param storageId
   *          storage uid
   * @param storageTray
   *          storage tray
   * @return Storage bean
   */
  StorageBean getStorageByIdAndTray(@Param("storageId") final String storageId, @Param("storageTray") final String storageTray);

  /**
   * Delete a tray record from db.
   * 
   * @param storageId
   *          Storage uid
   * @param storageTray
   *          storage tray
   * @return Storage bean
   */
  void deleteStorageByIdAndTray(@Param("storageId") final String storageId, @Param("storageTray") final String storageTray);

  /**
   * Get all storage tray file from db.
   * 
   * @param storageTray
   *          Storage tray
   * @return List of Storage bean
   */
  List < StorageBean > getAll(String storageTray);

}
