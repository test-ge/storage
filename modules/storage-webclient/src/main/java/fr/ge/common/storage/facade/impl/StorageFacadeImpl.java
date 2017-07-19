/**
 * 
 */
package fr.ge.common.storage.facade.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import fr.ge.common.storage.constant.ICodeExceptionStorageConstant;
import fr.ge.common.storage.facade.IStorageFacade;
import fr.ge.common.storage.utils.StorageTreeUtils;
import fr.ge.common.storage.ws.model.StoredFile;
import fr.ge.common.storage.ws.model.StoredTrayResult;
import fr.ge.common.storage.ws.model.StoredTrayTree;
import fr.ge.common.storage.ws.rest.IStorageRestService;
import fr.ge.core.exception.TechniqueException;

/**
 * Interface of the facade to call storage ws and handle the errors.
 *
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public class StorageFacadeImpl implements IStorageFacade {

  @Autowired
  private IStorageRestService storageRestService;

  /**
   * Mutateur sur l'attribut {@link #storageRestService}.
   *
   * @param storageRestService
   *          la nouvelle valeur de l'attribut storageRestService
   */
  public void setStorageRestService(IStorageRestService storageRestService) {
    this.storageRestService = storageRestService;
  }

  @Override
  public StoredTrayResult getTray(final String storageTray) throws TechniqueException {

    StoredTrayResult storedTrayResult = null;
    try {
      storedTrayResult = storageRestService.get(storageTray);
    } catch (Exception e) {
      throw new TechniqueException(ICodeExceptionStorageConstant.ERR_STORAGE_WS_GET_TRAY);
    }
    if (null == storedTrayResult) {
      throw new TechniqueException(ICodeExceptionStorageConstant.ERR_STORAGE_WS_GET_TRAY);
    }
    return storedTrayResult;
  }

  @Override
  public Response download(final String storageId, final String storageTray) throws TechniqueException {
    Response response = null;
    try {
      response = storageRestService.download(storageId, storageTray);
    } catch (Exception e) {
      throw new TechniqueException(ICodeExceptionStorageConstant.ERR_STORAGE_WS_DOWNLOAD_FILE);
    }
    if (null == response) {
      throw new TechniqueException(ICodeExceptionStorageConstant.ERR_STORAGE_WS_DOWNLOAD_FILE);
    }
    return response;
  }

  @Override
  public void remove(String storageId, String storageTray) throws TechniqueException {
    StoredFile storedFile = null;
    try {
      storedFile = storageRestService.remove(storageId, storageTray);
    } catch (Exception e) {
      throw new TechniqueException(ICodeExceptionStorageConstant.ERR_STORAGE_WS_REMOVE_FILE);
    }
    if (null == storedFile) {
      throw new TechniqueException(ICodeExceptionStorageConstant.ERR_STORAGE_WS_REMOVE_FILE);
    }
  }

  @Override
  public StoredTrayTree generateTree(final String storageTray) throws TechniqueException {
    final StoredTrayResult storedTrayResult = this.getTray(storageTray);
    return StorageTreeUtils.generateTree(storedTrayResult);
  }

}
