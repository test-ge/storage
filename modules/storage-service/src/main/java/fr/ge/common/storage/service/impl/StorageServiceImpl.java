/**
 * 
 */
package fr.ge.common.storage.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import fr.ge.common.storage.bean.StorageBean;
import fr.ge.common.storage.constante.ICodeExceptionConstante;
import fr.ge.common.storage.constante.ICodeMessageTracker;
import fr.ge.common.storage.constante.IStorageConstant;
import fr.ge.common.storage.service.IStorageService;
import fr.ge.common.storage.service.mapper.StorageMapper;
import fr.ge.common.storage.utils.StorageUtils;
import fr.ge.core.exception.TechniqueException;

/**
 * Service managing files into Storage.
 * 
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
public class StorageServiceImpl implements IStorageService {

  /** Le logger fonctionnel. */
  private static final Logger LOGGER_FONC = LoggerFactory.getLogger(StorageServiceImpl.class);

  /** Storage mapper. **/
  @Autowired
  private StorageMapper storageMapper;

  /** service to call tracker. */
  @Autowired
  private IStorageTrackerService storageTrackerService;

  @Override
  @Transactional(readOnly = false, value = "ge_storage")
  public StorageBean storeFile(final byte[] storageFileByte, final String storageStatus, final String storageFileName,
    final String storageReferenceId, final String storageRootDirectory) throws TechniqueException {

    // -->Validate JSON Format
    if (null == storageFileByte) {
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_INVALID_PARAMETER, "Unable to load storage upload file");
    }

    // byte[] storageFileByte = StorageUtils.transformInputStreamToByteArray(storageFile);

    // -->Generate a new track id
    String storageId = null;
    try {
      storageId = storageTrackerService.createStorageId();
    } catch (TechniqueException e) {
      throw e;
      // storageId = RandomStringUtils.randomAlphanumeric(18).toUpperCase();

    }

    // -->Store file (metadata, sha1, md5 file) in local disk
    String storageRelativePath = StorageUtils.createStorageFile(storageId, storageFileByte, storageStatus, storageFileName,
      storageReferenceId, storageRootDirectory);

    // -->Persistence execution into DB
    StorageBean storageBean = this.insertStorageDB(storageId, storageStatus, storageFileName, storageReferenceId,
      storageRelativePath);

    // -->Tracker notification
    String trackerDate = new SimpleDateFormat(IStorageConstant.TRACKER_DATE_PATTERN).format(Calendar.getInstance().getTime());
    storageTrackerService.addMessage(storageId, ICodeMessageTracker.STORAGE_FILE_POSTED, storageId, storageStatus, trackerDate);

    if (null != storageReferenceId && StringUtils.isNoneBlank(storageReferenceId)) {
      storageTrackerService.linkReference(storageId, storageReferenceId);
    }

    return storageBean;
  }

  /**
   * Mapping Storage bean to persist into DB.
   * 
   * @param storageSendBean
   *          storage information to persist
   * @param storageRelativePath
   *          storage relative path file
   * @throws TechniqueException
   *           Technical exception
   */
  private StorageBean insertStorageDB(final String storageId, final String storageStatus, final String storageFileName,
    final String storageReferenceId, final String storageRelativePath) throws TechniqueException {
    StorageBean storageBean = new StorageBean();
    try {
      storageBean.setId(storageId);
      storageBean.setDateCreation(Calendar.getInstance().getTime());
      storageBean.setOriginalfilename(storageFileName);
      storageBean.setPath(storageRelativePath);
      storageBean.setReferenceId(storageReferenceId);
      storageBean.setTray(storageStatus);
      LOGGER_FONC.debug("Persistence BDD with storage id = [{}]", storageId);
      storageMapper.insertStorage(storageBean);
    } catch (Exception e) {
      String messageBdd = "An error occured when persisting data into database for id " + storageId;
      LOGGER_FONC.error(messageBdd, e);
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_DB_ERROR, messageBdd, e);
    }
    return storageBean;
  }

  @Override
  @Transactional(readOnly = false, value = "ge_storage")
  public StorageBean remove(final String storageId, final String storageTray, final String storageRootDirectory)
    throws TechniqueException {
    if (null == storageId) {
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_INVALID_PARAMETER, "Passing null storage id");
    }
    if (null == storageTray) {
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_INVALID_PARAMETER, "Passing null storage tray");
    }
    final StorageBean storageBean = this.get(storageId, storageTray);

    final StringBuilder storageAbsoluteFilePath = new StringBuilder();
    storageAbsoluteFilePath.append(storageRootDirectory).append(File.separator).append(storageBean.getPath());

    // -->Deleting line from db
    storageMapper.deleteStorageByIdAndTray(storageId, storageTray);

    StorageUtils.remove(storageAbsoluteFilePath.toString());

    // -->Tracker notification
    String trackerDate = new SimpleDateFormat(IStorageConstant.TRACKER_DATE_PATTERN).format(Calendar.getInstance().getTime());
    storageTrackerService.addMessage(storageId, ICodeMessageTracker.STORAGE_FILE_REMOVED, storageId, storageBean.getTray(),
      trackerDate);

    return storageBean;
  }

  @Override
  @Transactional(readOnly = true, value = "ge_storage")
  public StorageBean get(final String storageId, final String storageTray) throws TechniqueException {
    if (null == storageId) {
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_INVALID_PARAMETER, "Passing null storage id");
    }
    if (null == storageTray) {
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_INVALID_PARAMETER, "Passing null storage tray");
    }
    StorageBean storageBean = null;
    try {
      storageBean = storageMapper.getStorageByIdAndTray(storageId, storageTray);
    } catch (Exception e) {
      LOGGER_FONC.error("An error occured when selecting storage record from db", e);
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_DB_ERROR, "Failed db research");
    }

    if (null == storageBean) {
      LOGGER_FONC.error("No result into db with id '" + storageId + "' and tray '" + storageTray + "'");
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_DB_ERROR, "Failed db research");
    }

    return storageBean;
  }

  @Override
  @Transactional(readOnly = true, value = "ge_storage")
  public List < StorageBean > getAll(final String storageTray) throws TechniqueException {
    if (null == storageTray) {
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_INVALID_PARAMETER, "Passing null storage tray");
    }
    List < StorageBean > listStorage = null;
    try {
      listStorage = storageMapper.getAll(storageTray);
    } catch (Exception e) {
      LOGGER_FONC.error("An error occured when selecting storage record from db", e);
      throw new TechniqueException(ICodeExceptionConstante.ERR_STORAGE_DB_ERROR, "Failed db research");
    }
    return listStorage;
  }
}
