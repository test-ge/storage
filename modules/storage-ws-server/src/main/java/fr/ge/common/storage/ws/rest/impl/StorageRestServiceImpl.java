/**
 * 
 */
package fr.ge.common.storage.ws.rest.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ge.common.storage.bean.StorageBean;
import fr.ge.common.storage.constante.ICodeMessageTracker;
import fr.ge.common.storage.constante.IStorageConstant;
import fr.ge.common.storage.service.IStorageService;
import fr.ge.common.storage.service.impl.IStorageTrackerService;
import fr.ge.common.storage.ws.bean.conf.StorageConfigurationBean;
import fr.ge.common.storage.ws.model.StoredFile;
import fr.ge.common.storage.ws.model.StoredTrayResult;
import fr.ge.common.storage.ws.rest.IStorageRestService;
import fr.ge.core.exception.TechniqueException;
import fr.ge.core.log.GestionnaireTrace;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Storage Service.
 * 
 * @author $Author: aolubi $
 * @version $Revision: 0 $
 */
@Api("Storage REST services")
@Path("/v1")
public class StorageRestServiceImpl implements IStorageRestService {

  /** Le logger fonctionnel. */
  private static final Logger LOGGER_FONC = GestionnaireTrace.getLoggerFonctionnel();

  /** The storage service. */
  @Autowired
  private IStorageService storageService;

  /** The storage tracker service. */
  @Autowired
  private IStorageTrackerService storageTrackerService;

  @Autowired
  private StorageConfigurationBean storageConfigurationBean = StorageConfigurationBean.getInstance();

  /**
   * {@inheritDoc}
   */
  @POST
  @Path("tray/{storageTray}/file")
  @Consumes({MediaType.MULTIPART_FORM_DATA })
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Record a file into Storage")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "storageUploadFile", value = "Storage upload file", required = true, dataType = "java.io.File", paramType = "form"),
    @ApiImplicitParam(name = "storageTray", value = "Storage tray", required = true, dataType = "string", paramType = "form"),
    @ApiImplicitParam(name = "storageReferenceUid", value = "Storage reference uid", required = false, dataType = "string", paramType = "form"),
    @ApiImplicitParam(name = "storageFilename", value = "Storage original filename", required = false, dataType = "string", paramType = "form") })
  public Response store(@PathParam("storageTray") final String storageTray, final Attachment storageFile,
    @QueryParam("storageFilename") final String storageFilename, @QueryParam("storageReferenceId") final String storageReferenceId

  ) {

    StoredFile storedFile = null;
    try {
      if (!isStatusValid(storageTray)) {
        return Response.status(400).build();
      }
      byte[] resourceAsBytes = storageFile.getObject() instanceof byte[] ? (byte[]) storageFile.getObject()
        : storageFile.getObject(byte[].class);
      if (ArrayUtils.isEmpty(resourceAsBytes)) {
        return Response.serverError().entity("Record content is empty").build();
      }

      final String storageRootDirectory = storageConfigurationBean.getStorageRootDirectory();
      final StorageBean storageBean = this.storageService.storeFile(resourceAsBytes, storageTray, storageFilename,
        storageReferenceId, storageRootDirectory);
      storedFile = this.mapStorageBeanToStoredFile(storageBean);
    } catch (TechniqueException e) {
      return Response.noContent().build();
    }

    return Response.ok(storedFile).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).build();
  }

  /**
   * Mapping a Storage Bean to a Stored file.
   * 
   * @param storageBean
   *          a Storage Bean
   * @return a stored file
   */
  private StoredFile mapStorageBeanToStoredFile(final StorageBean storageBean) {
    StoredFile storedFile = new StoredFile();
    storedFile.setId(storageBean.getId());
    storedFile.setName(storageBean.getOriginalfilename());
    storedFile.setPath(storageBean.getPath());
    storedFile.setTray(storageBean.getTray());
    storedFile.setDateCreation(storageBean.getDateCreation());
    storedFile.setReferenceId(storageBean.getReferenceId());
    return storedFile;
  }

  /**
   * Mapping a List of Storage Bean to a list of Stored file.
   * 
   * @param storageBean
   *          a Storage Bean
   * @return a stored file
   */
  private StoredTrayResult mapStorageListToStoredTrayResult(final String storageTray, final List < StorageBean > storageList) {
    List < StoredFile > storedFileList = new ArrayList < StoredFile >();
    if (null == storageList) {
      return null;
    }

    for (StorageBean storageBean : storageList) {
      final StoredFile storedFile = mapStorageBeanToStoredFile(storageBean);
      storedFileList.add(storedFile);
    }

    StoredTrayResult storedTrayResult = new StoredTrayResult();
    storedTrayResult.setStoredFiles(storedFileList);
    storedTrayResult.setTray(storageTray);
    return storedTrayResult;
  }

  @Override
  @DELETE
  @Path("tray/{storageTray}/file/{storageId}")
  @ApiOperation(value = "Remove a file from Storage")
  public StoredFile remove(@ApiParam("Storage id") @PathParam("storageId") final String storageId,
    @ApiParam("Storage tray") @PathParam("storageTray") final String storageTray) {
    StoredFile storedFile = null;
    try {
      if (!isStatusValid(storageTray)) {
        return null;
      }
      final StorageBean storageBean = this.storageService.remove(storageId, storageTray,
        storageConfigurationBean.getStorageRootDirectory());
      storedFile = this.mapStorageBeanToStoredFile(storageBean);
    } catch (TechniqueException e) {
      return null;
    }
    return storedFile;
  }

  @Override
  @GET
  @Path("tray/{storageTray}/file/{storageId}")
  @ApiOperation(value = "Download a file from Storage")
  public Response download(@ApiParam("Storage Id") @PathParam("storageId") final String storageId,
    @ApiParam("Storage tray") @PathParam("storageTray") final String storageTray) {
    StoredFile storedFile = null;
    try {
      if (!isStatusValid(storageTray)) {
        return Response.status(400).build();
      }
      final StorageBean storageBean = this.storageService.get(storageId, storageTray);
      storedFile = this.mapStorageBeanToStoredFile(storageBean);
    } catch (Exception e) {
      return Response.noContent().build();
    }

    String fileName = storedFile.getId();
    if (StringUtils.isNotBlank(storedFile.getName())) {
      fileName = storedFile.getName();
    }

    String trackerDate = new SimpleDateFormat(IStorageConstant.TRACKER_DATE_PATTERN).format(Calendar.getInstance().getTime());
    storageTrackerService.addMessage(storageId, ICodeMessageTracker.STORAGE_FILE_DOWNLOAD, storageId, trackerDate);

    FileInputStream fileInputStream = null;
    try {
      final String storagePath = storageConfigurationBean.getStorageRootDirectory() + File.separator + storedFile.getPath();
      fileInputStream = new FileInputStream(storagePath);
    } catch (FileNotFoundException e) {
      return Response.noContent().build();
    }

    return Response.ok(fileInputStream).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM)
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").build();
  }

  /**
   * Returns true if storage tray is accepted.
   * 
   * @param storageTray
   *          Storage tray
   * @return true if accepted
   */
  private boolean isStatusValid(final String storageTray) {
    if (StringUtils.isBlank(storageTray) || !storageConfigurationBean.getStorageStatus().contains(storageTray)) {
      LOGGER_FONC.error("Unable to recognize this tray : {}.", storageTray);
      return false;
    }
    return true;
  }

  @Override
  @GET
  @Produces({MediaType.APPLICATION_JSON })
  @Path("tray/{storageTray}")
  @ApiOperation(value = "Get all files from a tray")
  public StoredTrayResult get(@ApiParam("Storage tray") @PathParam("storageTray") final String storageTray) {
    StoredTrayResult storedTrayResult = null;
    List < StorageBean > storageList = null;
    try {
      if (!isStatusValid(storageTray)) {
        return null;
      }
      storageList = this.storageService.getAll(storageTray);
      storedTrayResult = this.mapStorageListToStoredTrayResult(storageTray, storageList);
    } catch (TechniqueException e) {
      LOGGER_FONC.error("An error occured during getting all files from a tray : {}.", storageTray);
      return null;
    }
    return storedTrayResult;
  }

}
